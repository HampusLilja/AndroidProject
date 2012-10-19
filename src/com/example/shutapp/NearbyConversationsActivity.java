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

	private List<String> nearbyChatRoomNames;
	private List<String> farAwayChatRoomNames;
	private List<String> allChatRoomNames;
	private List<Chatroom> allChatRoom;
	private int numberOfGreenChatRooms;

	private Location currentLocation = new Location("current");
	
	private DatabaseHandler db;
	/**
	 * Creates an environment for NearbyConversationActivity 
	 *
	 * @param savedInstanceState	the state of the saved instance
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_conversations);
		Log.d("onCreate", "get here");
		db = new DatabaseHandler(this);
		/* Use the LocationManager class to obtain GPS locations */
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationListener();
		locationManager.requestLocationUpdates(locationManager.getBestProvider(new Criteria(), false), 
				StringLiterals.LOCATION_UPDATE_INTERVALL, 0, locationListener);
		
		
		
		
		
		String bestProvider = locationManager.getBestProvider(new Criteria(), false);       
        Location startLocation = locationManager.getLastKnownLocation(bestProvider);
        
		if (startLocation != null){
			updatePosition(startLocation.getLatitude(), startLocation.getLongitude());
		} else {
			Log.e("geoPointS","Unable to get startlocation");
			updatePosition(StringLiterals.START_LATITUDE, StringLiterals.START_LONGITUDE);
		}
		
		nearbyChatRoomNames = new ArrayList<String>();
		farAwayChatRoomNames = new ArrayList<String>();
		allChatRoomNames = new ArrayList<String>();
		initiateChatRooms();
		
	}
	
	/**
	 * Initiate chat rooms
	 */
	private void initiateChatRooms() {
		try {
			allChatRoom = db.getAllChatrooms();
			for(Chatroom room : allChatRoom){
				if(inRangeOfChatRoom(room)){
					nearbyChatRoomNames.add(room.getName());
				} else {
					farAwayChatRoomNames.add(room.getName());
				}
			}
			numberOfGreenChatRooms = nearbyChatRoomNames.size();
			allChatRoomNames.addAll(nearbyChatRoomNames);
			allChatRoomNames.addAll(farAwayChatRoomNames);
		} catch(Exception e) {
			String exception = e.toString();
			Log.e("TAG", "No known chat rooms" + exception);
		}

	}
	/**
	 * Add a chat room
	 *
	 * @param name 	a string containing the name of the room 
	 */
	public void addChatroom(String name){
		new Chatroom(name, currentLocation, this);
		updateChatroomList(findViewById(R.layout.activity_nearby_conversations));
	}
	/**
	 * Create an array adapter only showing nearby chat rooms
	 */
	private void createOnlyNearbyChatRoomArrayAdapter() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nearbyChatRoomNames);
		ListView listView = (ListView) findViewById(R.id.listOfNearbyConversations);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this); 
	}
	/**
	 * Create an array adapter with all chat rooms
	 */
	private void createAllChatRoomsArrayAdapter() {
		String[] stringArray = allChatRoomNames.toArray(new String[allChatRoomNames.size()]); 
		
		ArrayAdapter<String> adapter = new ListChatRoomRowArrayAdapter(this, stringArray, numberOfGreenChatRooms);
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
				if(chatRoomToBeAdded.equals("") || chatRoomToBeAdded == null){
					return;
				}
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
	}
	/**
	 * Redirects the user to toNearbyConversationsActivity.java
	 *
	 * @param view	a view of the text model
	 */
	public void updateChatroomList(View view){
		Intent intent = new Intent(this, MainActivity.class);
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
		String clickedChatroom = "";
		
		Log.d("listan", "you clicked item " + position);
		if( position < numberOfGreenChatRooms ){
			clickedChatroom = nearbyChatRoomNames.get(position);
			Settings.setCurrentChatroom(clickedChatroom);
			toChatActivity(view);
		} else {
			Log.d("listan", "you clicked on a forbidden item ");
		}

		
	}
	
	private boolean inRangeOfChatRoom(Chatroom cr) {
		float dist = (cr.getRadius()/2 - currentLocation.distanceTo(cr.getLocation()));
		return (dist >= 0);
	}
	
	/**
	 * Lists all the chat Rooms
	 */
	@Override
	protected void onResume() {
		super.onResume();

		if(Settings.allChatRoomsDisplayed()){
			createAllChatRoomsArrayAdapter();
		} else {
			createOnlyNearbyChatRoomArrayAdapter();
		}
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
	
	

}
