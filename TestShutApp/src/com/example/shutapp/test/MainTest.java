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
import com.example.shutapp.*;
import com.example.shutapp.R;
import com.jayway.android.robotium.solo.Solo;
import android.widget.EditText;
import android.widget.TextView;

public class MainTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private Solo solo;
	  
	public MainTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
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
	
	public void testWelcomeText() {
		TextView welcomeText = solo.getText(0);
		String actual = welcomeText.getText().toString();
		String expected = solo.getString(R.string.main_information_temp);
		assertEquals("Wrong welcome text", expected, actual);
	}
	
	public void testEnterNameAndRedirectToChatRooms() {
		//EditText chatRoomName = (EditText) solo.getView(R.id.nickname_input);
		//solo.typeText(chatRoomName, "User");
		
		solo.clickOnButton("Send");
		
		//Assert that NearbyConversationActivity activity is opened
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
	}
	

}
