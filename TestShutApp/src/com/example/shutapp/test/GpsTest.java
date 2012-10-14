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

import com.example.shutapp.GpsActivity;
import com.example.shutapp.R;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class GpsTest extends ActivityInstrumentationTestCase2<GpsActivity>{
	private Solo solo;
	
	@SuppressWarnings("deprecation")
	public GpsTest() {
		super("com.example.shutapp.GpsActivity", GpsActivity.class);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());	
	}
	@Override
	protected void tearDown(){
		solo.finishOpenedActivities();
	}
	public void testRedirectToChatActivity() throws Exception{
		/*
		Button chatButton = (Button) solo.getView(R.id.chat_button);
		solo.clickOnView(chatButton);
		solo.assertCurrentActivity("Expected Chat activity", "ChatActivity");
		solo.goBack();
		*/
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
		solo.assertCurrentActivity("Expected NearbyConversations activity", "NearbyConversationsActivity");
		solo.goBack();
	}
	
}
