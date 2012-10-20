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

import java.util.ArrayList;
import java.util.List;

import com.example.shutapp.NearbyConversationsActivity;
import com.example.shutapp.R;
import com.example.shutapp.Settings;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class NearbyConversationTest extends
		ActivityInstrumentationTestCase2<NearbyConversationsActivity> {
	private Solo solo;

	@SuppressWarnings("deprecation")
	public NearbyConversationTest() {
		super("com.example.shutapp.MainActivity", NearbyConversationsActivity.class);
	}
	
	@Override
	protected void setUp(){
		try{
			super.setUp();
			solo = new Solo(getInstrumentation(), getActivity());
		} catch(Exception e){
			String exception = e.toString();
			Log.e("TAG", "Could´t setUp or create Solo" + exception);
		}
	}
	@Override
	protected void tearDown(){
		solo.finishOpenedActivities();
	}
	public void testCreateChatRoom() throws Exception{
		Button createRoomButton = (Button) solo.getView(R.id.create_new_chatroom_button);
		solo.clickOnView(createRoomButton);
		solo.typeText(0, "Testing");
		solo.clickLongOnText("OK");
		//Have to reload the activity for the created chat room to show
		//Have to click on view if there is no text on button or you do not know the index
		Button chatRoomButton = (Button) solo.getView(R.id.nearby_conversations_button);
		solo.clickOnView(chatRoomButton);
		 
		//Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
		solo.takeScreenshot();
		boolean expected = true;
		boolean actual = solo.searchText("Testing");
		//Assert that ChatRoom1 are found
		assertEquals("ChatRoom1 are not found", expected, actual);
		
	}

	public void testEnterChatActivityFromNearbyChatRoom() throws Exception {
		// Click on the first list line
		solo.clickInList(1); 
		// redirected to a new activity

		// Check if the new activity is ChatActivity
		solo.assertCurrentActivity("Expected Chat activity", "ChatActivity");
		solo.goBack();
		//Assert that NearbyConversationActivity activity is opened
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		
	}
	
	public void testDisplayAllChatRoomAndCantEnterFarAwayChatRooms() throws Exception {
		View nearbyListView = solo.getView((R.id.nearby_conversations_button));
		View allListView = solo.getView((R.layout.chatroom_row));
		List<TextView> rows = new ArrayList<TextView>();
		Button settingsButton = (Button) solo.getView(R.id.settings_button);
		Button chatRoomButton = (Button) solo.getView(R.id.nearby_conversations_button);
		
		rows = solo.getCurrentTextViews(nearbyListView);
		int onlyNearby = rows.size();

		solo.clickOnView(settingsButton);
		solo.assertCurrentActivity("Expected Settings activity", "SettingsActivity");
		solo.clickLongInList(3);
		assertTrue(Settings.allChatRoomsDisplayed());
		
		solo.clickOnView(chatRoomButton);
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		
		rows = solo.getCurrentTextViews(allListView);
		int all = rows.size();
		
		assertTrue(all + " chatrooms are not more then " + onlyNearby, all > onlyNearby);
		Log.d("TAG", "all = " + all + " nearby = " + onlyNearby);
		
		int indexForInvalidChatroom = onlyNearby + 1;
		
		solo.clickInList(indexForInvalidChatroom);
		
		//Assert that NearbyConversationActivity activity is still opened
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		
	}
	
	public void testRedirectToChatActivity() throws Exception{
		Button chatButton = (Button) solo.getView(R.id.chat_button);
		solo.clickOnView(chatButton);
		solo.assertCurrentActivity("Expected Chat activity", "ChatActivity");
		solo.goBack();
	}
	public void testRedirectToGpsActivity() throws Exception {
		Button mapButton = (Button) solo.getView(R.id.map_button);
		solo.clickOnView(mapButton);
		solo.assertCurrentActivity("Expected Gps activity", "GpsActivity");
		solo.goBack();
	}
	public void testRedirectToSettingsActivity() throws Exception{
		Button settingsButton = (Button) solo.getView(R.id.settings_button);
		solo.clickOnView(settingsButton);
		solo.assertCurrentActivity("Expected Settings activity", "SettingsActivity");
		solo.goBack();
	}
	public void testRedirectToNearbyConversationsActivity() throws Exception{
		Button chatRoomButton = (Button) solo.getView(R.id.nearby_conversations_button);
		solo.clickOnView(chatRoomButton);
		solo.waitForActivity("NearbyConversationsActivity");
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		solo.goBack();
	}
}
