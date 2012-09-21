package com.example.shutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NearbyConversationsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_conversations);
        createArrayAdapter(initTestArray());
    }
    
    private String[] initTestArray() {
		String[] testArray = {"Chatrum1", "Chatrum2", "Chatrum3", "Chatrum4"};
		return testArray;
	}

	private void createArrayAdapter(String[] myStringArray) {
    	ArrayAdapter adapter = new ArrayAdapter<String>(this, 
    	        android.R.layout.simple_list_item_1, myStringArray);
    	ListView listView = (ListView) findViewById(R.id.listOfNearbyConversations);
    	listView.setAdapter(adapter);
		
	}

	public void toChatActivity(View view){
    	Intent intentToRedirect = new Intent(this, ChatActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    
    public void toMapActivity(View view){
    	Intent intentToRedirect = new Intent(this, MapActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    
    public void toSettingsActivity(View view){
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    	overridePendingTransition(0, 0);
    } 
    
}
