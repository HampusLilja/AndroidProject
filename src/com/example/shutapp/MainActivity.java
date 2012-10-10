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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
		
		//DATABASETESTS
		DatabaseHandler db = new DatabaseHandler(this);
		Log.d("Insert: ", "Inserting ..");
		/*
		db.addChatroom(new Chatroom("chatroom1" , 11.0, 11.0, 1000));
		db.addChatroom(new Chatroom("chatroom2" , 22.0, 22.0, 1000));
		db.addChatroom(new Chatroom("chatroom3" , 33.0, 33.0, 1000));
		*/
		
		Log.d("Reading: ", "Reading all chatrooms..");
		List<Chatroom> chatrooms = db.getAllChatrooms();
		
		for(Chatroom cr : chatrooms){
			Log.d("Chatroom", "Name: " + cr.getName() + " ,Latitude: " + cr.getLatitude() + " ,Longitude: " + cr.getLongitude() + " ,Radius: " + cr.getRadius());
		}
		//if it is the first time you run this app then you will have to
		//initiate the settingsfile
		if(Parser.checkFileExistance(StringLiterals.FILENAME_SETTINGS, this))
			Settings.setNickname(Parser.readAtIndex(StringLiterals.NICKNAME_INDEX, StringLiterals.FILENAME_SETTINGS, this), this); 
		else
			Settings.initiateSettingsFile(this);

		registerClient();

	}

	/**
	 * checks the current device, checks the 
	 * manifest for the appropriate rights, and then retrieves a registration id
	 * from the GCM cloud. 
	 *
	 */
	public void registerClient() {

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
				GCMRegistrar.register(this, PROJECT_ID);
				regId = GCMRegistrar.getRegistrationId(this);

				Log.d(TAG, "sendregtoserver has been initialized");
				registrationStatus = "Registration of regid done";

			} else {
				registrationStatus = "Already registered";
			}

		} catch (Exception e) {
			e.printStackTrace();
			registrationStatus = e.getMessage();
		}

		Log.d(TAG, registrationStatus);
		Log.d(TAG, regId);
		MiscResources.REGID = regId;

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
	
	private void downloadDatabase(){
		try {
	        //set the download URL, a url that points to a file on the internet
	        //this is the file to be downloaded
	        URL url = new URL("http://somewhere.com/some/webhosted/file");

	        //create the new connection
	        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

	        //set up some things on the connection
	        urlConnection.setRequestMethod("GET");
	        urlConnection.setDoOutput(true);

	        //and connect!
	        urlConnection.connect();

	        //set the path where we want to save the file
	        //in this case, going to save it on the root directory of the
	        //sd card.
	        File SDCardRoot = Environment.getExternalStorageDirectory();
	        //create a new file, specifying the path, and the filename
	        //which we want to save the file as.
	        File file = new File(SDCardRoot,"somefile.ext");

	        //this will be used to write the downloaded data into the file we created
	        FileOutputStream fileOutput = new FileOutputStream(file);

	        //this will be used in reading the data from the internet
	        InputStream inputStream = urlConnection.getInputStream();

	        //this is the total size of the file
	        int totalSize = urlConnection.getContentLength();
	        //variable to store total downloaded bytes
	        int downloadedSize = 0;

	        //create a buffer...
	        byte[] buffer = new byte[1024];
	        int bufferLength = 0; //used to store a temporary size of the buffer

	        //now, read through the input buffer and write the contents to the file
	        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
	                //add the data in the buffer to the file in the file output stream (the file on the sd card
	                fileOutput.write(buffer, 0, bufferLength);
	                //add up the size so we know how much is downloaded
	                downloadedSize += bufferLength;
	                //this is where you would do something to report the prgress, like this maybe
	                //updateProgress(downloadedSize, totalSize);

	        }
	        //close the output stream when done
	        fileOutput.close();

	//catch some possible errors...
	} catch (MalformedURLException e) {
	        e.printStackTrace();
	} catch (IOException e) {
	        e.printStackTrace();
	}
	}


}




