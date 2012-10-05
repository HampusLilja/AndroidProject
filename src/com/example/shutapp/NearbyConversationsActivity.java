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

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class NearbyConversationsActivity extends Activity implements OnItemClickListener{

	public final static String EXTRA_MESSAGE = "com.example.shutapp.MESSAGE";
	//private List<Chatroom> testNearbyCR;
	private List<String> nearbyChatRoomNames;
	private String clickedChatroom = "No chatroom";
	private Location currentLocation = new Location("current");

	/**
	 * Creates an environment for NearbyConversationActivity 
	 *
	 * @param savedInstanceState	the state of the saved instance
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_conversations);
		
		/* Use the LocationManager class to obtain GPS locations */
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationListener();
		locationManager.requestLocationUpdates(locationManager.getBestProvider(new Criteria(), false), 1*1000, 0, locationListener);
		
		nearbyChatRoomNames = new ArrayList<String>();
		initiateChatRooms();
		createArrayAdapter();
		
		String bestProvider = locationManager.getBestProvider(new Criteria(), false);       
        Location startLocation = locationManager.getLastKnownLocation(bestProvider);
        
		if (startLocation != null){
			updatePosition(startLocation.getLatitude(), startLocation.getLongitude());
		} else {
			Log.e("geoPointS","Unable to get startlocation");
			updatePosition(57.691469,11.977469);
		}
		
		// 
		// Test Rooms
		//
		//initiateTestRooms();
		//createTestArrayAdapter(initTestArray());
	}
	/**
	 * Initiate chat rooms
	 */
	private void initiateChatRooms() {
		
		try {
			List<Chatroom> nearbyChatRoom = new ArrayList<Chatroom>();
			for(Chatroom room : Chatrooms.getAll()){
				nearbyChatRoom.add(room);
				nearbyChatRoomNames.add(room.getName());
			}
		} catch(Exception e) {
			System.out.println("No known chat rooms");
		}

	}
	/**
	 * Add a chat room
	 *
	 * @param name 	a string containing the name of the room 
	 */
	public void addChatroom(String name){
		Chatroom cr = new Chatroom(name, currentLocation);
		Chatrooms.add(cr.getName(), cr);
		nearbyChatRoomNames.add(name);
	}
	/**
	 * Create an array adapter
	 */
	private void createArrayAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nearbyChatRoomNames);
		ListView listView = (ListView) findViewById(R.id.listOfNearbyConversations);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this); 
	}
	/**
	 * Show a dialog when creating a chat room
	 *
	 * @param view	a view of the text model
	 */
	public void showCreateChatroomDialog(View view){
		final Dialog newChatroomDialog = new Dialog(this);
		newChatroomDialog.setContentView(R.layout.create_chatroom_dialog);
		final EditText etChatroomInput = (EditText) newChatroomDialog.findViewById(R.id.chatroom_name);
		
		Button btnDialogCreateChatroom = (Button) newChatroomDialog.findViewById(R.id.create_chatroom);
		btnDialogCreateChatroom.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view){
				String chatRoomToBeAdded = etChatroomInput.getText().toString();
				if(chatRoomToBeAdded.equals("") || chatRoomToBeAdded == null)
					return;
				addChatroom(chatRoomToBeAdded);
				newChatroomDialog.cancel();
				
			}
		});
		newChatroomDialog.show();
	}
	/**
	 * Update the position
	 *
	 * @param latitude		value with new latitude
	 * @param longitude 	value with new longitude
	 */
	private void updatePosition(double latitude, double longitude) {
		currentLocation.setLatitude(latitude);
		currentLocation.setLongitude(longitude);

		String Text = "My current location is: " + 
		" Latitud = " + currentLocation.getLatitude() + " Longitud = " + 
				currentLocation.getLongitude();

		Toast.makeText( getApplicationContext(),
				Text, Toast.LENGTH_SHORT).show();
	}
	/**
	 * Redirects the user to toNearbyConversationsActivity.java
	 *
	 * @param view	a view of the text model
	 */
	public void toNearbyConversationsActivity(View view){
		Intent intent = new Intent(this, NearbyConversationsActivity.class);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}
	/**
	 * Redirects the user to ChatActivity.java
	 *
	 * @param view	a view of the text model
	 */
	public void toChatActivity(View view){
		Intent intentToRedirect = new Intent(this, ChatActivity.class);
		intentToRedirect.putExtra(EXTRA_MESSAGE, clickedChatroom);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}
	/**
	 * Redirects the user to GpsActivity.java
	 *
	 * @param view	a view of the text model
	 */
	public void toGpsActivity(View view){
		Intent intentToRedirect = new Intent(this, GpsActivity.class);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}
	/**
	 * Redirects the user to SettingsActivity.java
	 *
	 * @param view	a view of the text model
	 */
	public void toSettingsActivity(View view){
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}
	/**
	 * When an item is clicked
	 *
	 * @param arg0		
	 * @param view		a view of the text model
	 * @param position	the position of the room
	 * @param arg3		
	 */
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		Log.d("listan", "you clicked item " + position);
		clickedChatroom = nearbyChatRoomNames.get(position);
		toChatActivity(view);
		
	}
	/**
	 * Class My Location Listener
	 */
	public class MyLocationListener implements LocationListener	{
		
		public MyLocationListener(){
			
		}
		
		public void onLocationChanged(Location loc) {
			updatePosition(loc.getLatitude(), loc.getLongitude());
		}

		public void onProviderDisabled(String provider)	{
			Toast.makeText( getApplicationContext(),
			"Gps Disabled",
			Toast.LENGTH_SHORT ).show();
		}

		public void onProviderEnabled(String provider) {
			Toast.makeText( getApplicationContext(),
					"Gps Enabled", Toast.LENGTH_SHORT).show();
		}

		public void onStatusChanged(String provider, int status, Bundle extras)	{

		}

	}/* End of Class MyLocationListener */
	
	// 
	// Methodes for test rooms
	//
	/*
	private void initiateTestRooms() {
		Location loc1 = new Location("loc1"), loc2 = new Location("loc2"), 
				loc3 = new Location("loc3"), loc4 = new Location("loc4"), loc5 = new Location("loc5");
		
		loc1.setLatitude(57.7012596130371);
		loc1.setLongitude(11.9670495986938);
		loc2.setLatitude(57.699242);
		loc2.setLongitude(11.986369);
		loc3.setLatitude(57.687583);
		loc3.setLongitude(11.980708);
		loc4.setLatitude(57.689431);
		loc4.setLongitude(11.974092);
		loc5.setLatitude(57.695697);
		loc5.setLongitude(11.974067);
		Chatroom cr1 = new Chatroom("Chatroom1", loc1);Chatroom cr2 = new Chatroom("Chatroom2", loc2);Chatroom cr3 = new Chatroom("Chatroom3", loc3);
		Chatroom cr4 = new Chatroom("Chatroom4", loc4);Chatroom cr5 = new Chatroom("Chatroom5", loc5);
		testNearbyCR = new ArrayList<Chatroom>();
		testNearbyCR.add(cr1); testNearbyCR.add(cr2); testNearbyCR.add(cr3); testNearbyCR.add(cr4); testNearbyCR.add(cr5);

	}
	
	private String[] initTestArray() {
		String[] testArray = new String[testNearbyCR.size()];
		int i = 0;
		for(Chatroom cr : testNearbyCR){
			testArray[i] = cr.getName();
			i++;
		}
		return testArray;
	}
	
	private void createTestArrayAdapter(String[] myStringArray) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);
		ListView listView = (ListView) findViewById(R.id.listOfNearbyConversations);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this); 
	}
	*/
}
