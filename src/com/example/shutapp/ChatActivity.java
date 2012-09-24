package com.example.shutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;


public class ChatActivity extends Activity {
	//private String chatmessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
    
    public void toGpsActivity(View view){
    	Intent intent = new Intent(this, GpsActivity.class);
    	startActivity(intent);
    	overridePendingTransition(0, 0);
    } 
    
    public void toNearbyConversationsActivity(View view){
    	Intent intent = new Intent(this, NearbyConversationsActivity.class);
    	startActivity(intent);
    	overridePendingTransition(0, 0);
    }
    
	public void toChatActivity(View view){
    	Intent intentToRedirect = new Intent(this, ChatActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    
    public void toSettingsActivity(View view){
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    	overridePendingTransition(0, 0);
    } 

    /* save the message written in dialog*/
    public String saveMessage(View view){
    	EditText thetext = (EditText)findViewById(R.id.written_msg);
    	String chatmessage = thetext.getText().toString();
    	return chatmessage;
    	   	
    }
}

