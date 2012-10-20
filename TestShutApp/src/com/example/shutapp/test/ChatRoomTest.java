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
import android.util.Log;
import android.widget.Button;

import com.example.shutapp.NearbyConversationsActivity;
import com.example.shutapp.R;
import com.jayway.android.robotium.solo.Solo;



public class ChatRoomTest extends ActivityInstrumentationTestCase2<NearbyConversationsActivity>{
	
	private Solo solo;

	/**
	 * Constructs the activity to be tested
	 */
	public ChatRoomTest() {
		super(NearbyConversationsActivity.class);

	}
	
	/**
	 * Sets up the preconditions for the test
	 */
	@Override
	public void setUp(){
		//setUp() is run before a test case is started. 
		//This is where the solo object is created.
		try{
			super.setUp();
			solo = new Solo(getInstrumentation(), getActivity());
		} catch(Exception e){
			String exception = e.toString();
			Log.e("TAG", "Could´t setUp or create Solo" + exception);
		}
	}
	
	/**
	 * Tears down the test rig when all test are run
	 */
	@Override
	public void tearDown(){
		//tearDown() is run after a test case has finished. 
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		solo.finishOpenedActivities();
	}
	
	/**
	 * Test to enter a valid chat room
	 */
	public void testEnterChatActivityFromChatRoom() throws Exception {
		//Create a new room to make sure we can join a room
		Button createRoomButton = (Button) solo.getView(R.id.create_new_chatroom_button);
		solo.clickOnView(createRoomButton);
		solo.typeText(0, "Testing");
		solo.clickLongOnText("OK");
		
		//Refresh the page
		Button chatRoomButton = (Button) solo.getView(R.id.nearby_conversations_button);
		solo.clickOnView(chatRoomButton);
		// Click on the first list line
		solo.clickInList(0);

		// Check if the new activity is ChatActivity
		solo.assertCurrentActivity("Expected Chat activity", "ChatActivity");
		//write message
		solo.typeText(0, "Test");
		Button sendButton = (Button) solo.getView(R.id.send_button);
		solo.clickOnView(sendButton);
		//end write message
		
		solo.goBack();
		//Assert that NearbyConversationActivity activity is opened
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		
	}
	
	/**
	 * Test the "Chat Activity" Button
	 */
	public void testRedirectToChatActivity() throws Exception{
		
		Button chatButton = (Button) solo.getView(R.id.chat_button);
		solo.clickOnView(chatButton);
		solo.assertCurrentActivity("Expected Chat activity", "ChatActivity");
		solo.goBack();
		
	}
	
	/**
	 * Test the "Gps Activity" Button
	 */
	public void testRedirectToGpsActivity() throws Exception {
		Button mapButton = (Button) solo.getView(R.id.map_button);
		solo.clickOnView(mapButton);
		solo.assertCurrentActivity("Expected Gps activity", "GpsActivity");
		solo.goBack();
	}
	
	/**
	 * Test the "Setting Activity" Button
	 */
	public void testRedirectToSettingsActivity() throws Exception{
		Button settingsButton = (Button) solo.getView(R.id.settings_button);
		solo.clickOnView(settingsButton);
		solo.assertCurrentActivity("Expected Settings activity", "SettingsActivity");
		solo.goBack();
	}
	
	/**
	 * Test the "Nearby Conversations Activity" Button
	 */
	public void testRedirectToNearbyConversationsActivity() throws Exception{
		Button chatRoomButton = (Button) solo.getView(R.id.nearby_conversations_button);
		solo.clickOnView(chatRoomButton);
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		solo.goBack();
	}

}
