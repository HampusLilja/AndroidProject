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
package com.example.shutapp;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gcm.GCMRegistrar;

/**
 * The Main Activity containing Splash.
 * @author Group12
 *
 */
public class MainActivity extends Activity {

	// This tag is used in Log.x() calls
	private static final String TAG = "MainActivity";

	private static final long SPLASHDELAY = 3000;

	private String registrationStatus = "Not yet registered";

	/**
	 * Sets the content view. 
	 *
	 * @param savedInstanceState the state of the saved instance
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DatabaseHandler db = new DatabaseHandler(this);
		db.downloadAndCopyDB();

		//if it is the first time you run this app then you will have to
		//initiate the settingsfile
		if(Parser.checkFileExistance(StringLiterals.FILENAME_SETTINGS, this)){
			Settings.setNickname(Parser.readAtIndex(
					StringLiterals.NICKNAME_INDEX,
					StringLiterals.FILENAME_SETTINGS, this), this);
		}else{
			Settings.initiateSettingsFile(this);
		}

		registerClient();

		TimerTask splashTask = new TimerTask() {

			@Override
			public void run() {
				finish();
				redirectFromMain(findViewById(R.layout.activity_main));
			}
		};
		Timer timer = new Timer();
		timer.schedule(splashTask, SPLASHDELAY);

	}

	/**
	 * checks the current device, checks the 
	 * manifest for the appropriate rights, and then retrieves a registration id
	 * from the GCM cloud. 
	 *
	 */
	public void registerClient() {
		// This string will hold the lengthy registration id that 
		//comes from GCMRegistrar.register()
		String regId = "";

		try {
			// Check that the device supports GCM 
			GCMRegistrar.checkDevice(this);

			// Check the manifest to be sure the app has all the required
			// permissions.
			GCMRegistrar.checkManifest(this);

			// Get the existing registration id, if it exists.
			regId = GCMRegistrar.getRegistrationId(this);

			if (regId.equals("")) {

				// register this device for this project
				GCMRegistrar.register(this, StringLiterals.PROJECT_ID);
				regId = GCMRegistrar.getRegistrationId(this);

				Log.d(TAG, "sendregtoserver has been initialized");
				registrationStatus = "Registration of regid done";

			} else {
				registrationStatus = "Already registered";
			}

		} catch (Exception e) {
			registrationStatus = e.getMessage();
		}

		Log.d(TAG, registrationStatus);
		Log.d(TAG, regId);
		Settings.setREGID(regId);

	}

	/**
	 * Called when the redirect button is pressed, redirected to another activity.
	 *
	 * @param view a view of the text model
	 */
	public void redirectFromMain(View view){
		Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}



}
