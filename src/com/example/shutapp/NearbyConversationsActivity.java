package com.example.shutapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NearbyConversationsActivity extends Activity implements OnItemClickListener{

	public final static String EXTRA_MESSAGE = "com.example.shutapp.MESSAGE";
	private List<Chatroom> nearbyCR;
	private ArrayAdapter<String> adapter;
	private String clickedChatroom = "No chatroom";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_conversations);
		initiateTestRooms();
		createArrayAdapter(initTestArray());


	}

	private void initiateTestRooms() {
		Chatroom cr1 = new Chatroom("Chatroom1");Chatroom cr2 = new Chatroom("Chatroom2");Chatroom cr3 = new Chatroom("Chatroom3");
		Chatroom cr4 = new Chatroom("Chatroom4");Chatroom cr5 = new Chatroom("Chatroom5");
		nearbyCR = new ArrayList<Chatroom>();
		nearbyCR.add(cr1); nearbyCR.add(cr2); nearbyCR.add(cr3); nearbyCR.add(cr4); nearbyCR.add(cr5);

	}

	private String[] initTestArray() {
		String[] testArray = new String[nearbyCR.size()];
		int i = 0;
		for(Chatroom cr : nearbyCR){
			testArray[i] = cr.getName();
			i++;
		}
		return testArray;
	}

	private void createArrayAdapter(String[] myStringArray) {
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);
		ListView listView = (ListView) findViewById(R.id.listOfNearbyConversations);
		listView.setAdapter(adapter);


		listView.setOnItemClickListener(this); 
	}

	/*new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("listan", "you clicked item " + position);
				toChatActivity(view);
			}

			public void toChatActivity(View view){
				Intent intentToRedirect = new Intent();
				startActivity(intentToRedirect);
				overridePendingTransition(0, 0);
			}

		});

	}

	 */

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
		clickedChatroom = nearbyCR.get(position).getName();
		toChatActivity(view);
		
	}
	
	

}
