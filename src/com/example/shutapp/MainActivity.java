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

import static com.example.shutapp.MiscResources.PROJECT_ID;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {


	// This tag is used in Log.x() calls
	private static final String TAG = "MainActivity";

	// This string will hold the lengthy registration id that comes from GCMRegistrar.register()
	private String regId = "";
	
	private String registrationStatus = "Not yet registered";
	/**
	 * Sets the content view and 
	 *
	 * @param savedInstanceState the state of the saved instance
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Parser.clean(StringLiterals.FILENAME_SETTINGS, this);

		String tempName = Parser.readAtIndex(StringLiterals.NICKNAME_INDEX, StringLiterals.FILENAME_SETTINGS, this);
		if(tempName != null || !tempName.equals(StringLiterals.EMPTY_STRING))
			Settings.setNickname(tempName, this); 
		else
			Settings.initiateSettingsFile(this);
		
		registerClient();
		
		Log.d("regid", regId);




		// Parser.clean("test", this);
		//testing parser
		// Parser.write("test1", "test", this);
		// Parser.write("test2", "test", this);
		// Parser.write("test3", "test", this);
		// Parser.writeAtIndex(1, "testindex", "test", this);
		// Parser.write("test4", "test", this);

		// Log.d("PARSER", Parser.readFirst("test", this));
		// Log.d("PARSER", Parser.readAll("test", this));
		// Log.d("PARSER", Parser.readAtIndex(1, "test", this));

		//Settings.setNickname("Prassel");
		/*
        if( Settings.getNickname().equals("") ){
        	setContentView(R.layout.activity_main);  	
        } else {
        //manageAccount(); // You have to comment this for the application to work in the AVD!
       testChatroom();
    	Intent i = new Intent(MainActivity.this, NearbyConversationsActivity.class);
    	startActivity(i);
    	overridePendingTransition(0, 0);      
        }
		 */
	}

	/*private void testChatroom() {
    	Location loc = new Location("loc");
    	loc.setLatitude(57.697261);
    	loc.setLongitude(11.97975);
		Chatroom cr = new Chatroom("chatrum1", loc);
		cr.saveMessage("Chatroom works", this);
		cr.saveMessage("asdasdasdasds", this);
		TextView textview = (TextView) findViewById(R.id.textView1);
    	textview.setText(cr.getLastMessage(this));
	}*/
	
	/**
	 * checks the current device, checks the 
	 * manifest for the appropriate rights, and then retrieves a registration id
	 * from the GCM cloud. 
	 *
	 */
	public void registerClient() {

		try {
			// Check that the device supports GCM (should be in a try / catch)
			GCMRegistrar.checkDevice(this);

			// Check the manifest to be sure this app has all the required
			// permissions.
			GCMRegistrar.checkManifest(this);

			// Get the existing registration id, if it exists.
			regId = GCMRegistrar.getRegistrationId(this);

			if (regId.equals("")) {

				//registrationStatus = "Registering...";

				//tvRegStatusResult.setText(registrationStatus);

				// register this device for this project
				GCMRegistrar.register(this, PROJECT_ID);
				regId = GCMRegistrar.getRegistrationId(this);

				

				//registrationStatus = "Registration Acquired";

				// This is actually a dummy function.  At this point, one
				// would send the registration id, and other identifying
				// information to your server, which should save the id
				// for use when broadcasting messages.
				sendRegistrationToServer();

				Log.d(TAG, "sendregtoserver has been initialized");
				//registrationStatus = "Registration of regid done";

			} else {
				//registrationStatus = "Already registered";

			}

			MiscResources.REGID = regId;
		} catch (Exception e) {

			e.printStackTrace();
			registrationStatus = e.getMessage();

		}

		Log.d(TAG, registrationStatus);
		//tvRegStatusResult.setText(registrationStatus);

		// This is part of our CHEAT.  For this demo, you'll need to
		// capture this registration id so it can be used in our demo web
		// service.
		Log.d(TAG, regId);

	}

	private void sendRegistrationToServer() {

		new HttpMessage(StringLiterals.DBREGISTER_MESSAGE_TYPE, Settings.getNickname(), regId, null, null, null);


	}

	/**
	 * Called when the redirect button is pressed, redirected to another activity 
	 *
	 * @param view a view of the text model
	 */
	public void redirectFromMain(View view){
		Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}

	/**
	 * Registers broadcast receivers when the activity is resumed 
	 */
	/*
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(gcmReceiver, gcmFilter);

	}
	 */

}




