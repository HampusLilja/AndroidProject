package com.example.shutapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
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

public class NearbyConversationsActivity extends Activity implements OnItemClickListener{

	public final static String EXTRA_MESSAGE = "com.example.shutapp.MESSAGE";
	//private List<Chatroom> nearbyCR;
	private ArrayAdapter<String> adapter;
	private List<String> nearbyCRnames;
	private String clickedChatroom = "No chatroom";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_conversations);
		nearbyCRnames = new ArrayList<String>();
		createArrayAdapter();


	}
<<<<<<< HEAD
	
	
	
	public void addChatroom(String name){
		Chatroom cr = new Chatroom(name);
		Chatrooms.add(cr.getName(), cr);
		nearbyCRnames.add(name);
=======

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
		nearbyCR = new ArrayList<Chatroom>();
		nearbyCR.add(cr1); nearbyCR.add(cr2); nearbyCR.add(cr3); nearbyCR.add(cr4); nearbyCR.add(cr5);

>>>>>>> circleMaking
	}


	private void createArrayAdapter() {
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nearbyCRnames);
		ListView listView = (ListView) findViewById(R.id.listOfNearbyConversations);
		listView.setAdapter(adapter);


		listView.setOnItemClickListener(this); 
	}


	public void toNearbyConversationsActivity(View view){
		Intent intent = new Intent(this, NearbyConversationsActivity.class);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}

	public void toChatActivity(View view){
		Intent intentToRedirect = new Intent(this, ChatActivity.class);
		intentToRedirect.putExtra(EXTRA_MESSAGE, clickedChatroom);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}

	public void toGpsActivity(View view){
		Intent intentToRedirect = new Intent(this, GpsActivity.class);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}

	public void toSettingsActivity(View view){
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}

	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		Log.d("listan", "you clicked item " + position);
		clickedChatroom = nearbyCRnames.get(position);
		toChatActivity(view);
		
	}
	
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
	

	

}
