/*
 ** Copyright (C)  2012  Tomas Arnesson, Hampus Lilja, Mattias Herzog & Mathias Dosé
 ** Permission is granted to copy, distribute and/or modify this document
 ** under the terms of the GNU Free Documentation License, Version 1.3
 ** or any later version published by the Free Software Foundation;
 ** with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 ** A copy of the license is included in the section entitled "LICENSE.txt".
 */
/*
** This file is part of ShutApp.
**
** ShutApp is free software: you can redistribute it and/or modify
** it under the terms of the GNU General Public License as published by
** the Free Software Foundation, either version 3 of the License, or
** (at your option) any later version.
**
** ShutApp is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
** GNU General Public License for more details.
**
** You should have received a copy of the GNU General Public License
** along with ShutApp.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.example.shutapp.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.example.shutapp.NearbyConversationsActivity;
import com.example.shutapp.R;
import com.jayway.android.robotium.solo.Solo;



public class ChatRoomTest extends ActivityInstrumentationTestCase2<NearbyConversationsActivity>{
	
	private Solo solo;

	public ChatRoomTest() {
		super(NearbyConversationsActivity.class);

	}
	
	@Override
	public void setUp() throws Exception {
		//setUp() is run before a test case is started. 
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	@Override
	public void tearDown() throws Exception {
		//tearDown() is run after a test case has finished. 
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		solo.finishOpenedActivities();
	}


	public void testCreatedChatRoomShowsInList() throws Exception {
		//Assert that NearbyConversationActivity activity is opened
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		
		Button chatRoomButton = (Button) solo.getView(R.id.nearby_conversations_button);
		
		solo.clickOnMenuItem("Create new chatroom"); 
		
		EditText chatRoomName = (EditText) solo.getView(R.id.chatroom_name);
		solo.typeText(chatRoomName, "ChatRoom1");
		solo.clickOnButton("OK");
		
		//Have to reload the activity for the created chat room to show
		//Have to click on view if there is no text on button or you do not know the index
		solo.clickOnView(chatRoomButton);
		 
		//Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
		solo.takeScreenshot();
		boolean expected = true;
		boolean actual = solo.searchText("ChatRoom1");
		//Assert that ChatRoom1 are found
		assertEquals("ChatRoom1 are not found", expected, actual); 

	}

	public void testEnterChatActivityFromChatRoom() throws Exception {
		// Click on the first list line
		solo.clickInList(1); 
		// redirected to a new activity

		// Check if the new activity is ChatActivity
		solo.assertCurrentActivity("Expected Chat activity", "ChatActivity");
		solo.goBack();
		//Assert that NearbyConversationActivity activity is opened
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		
	}
	
	public void testButtons() throws Exception {
		Button chatRoomButton = (Button) solo.getView(R.id.nearby_conversations_button);
		//Button chatButton = (Button) solo.getView(R.id.chat_button);
		Button mapButton = (Button) solo.getView(R.id.map_button);
		Button settingsButton = (Button) solo.getView(R.id.settings_button);
		
		solo.clickOnView(chatRoomButton);
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		solo.goBack();
		/*
		solo.clickOnView(chatButton);
		solo.assertCurrentActivity("Expected Chat activity", "ChatActivity");
		solo.goBack();
		*/
		solo.clickOnView(mapButton);
		solo.assertCurrentActivity("Expected Gps activity", "GpsActivity");
		solo.goBack();
		
		solo.clickOnView(settingsButton);
		solo.assertCurrentActivity("Expected Settings activity", "SettingsActivity");
		solo.goBack();
		
		
		
	}

}
