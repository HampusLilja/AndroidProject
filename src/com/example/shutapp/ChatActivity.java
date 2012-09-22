package com.example.shutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ChatActivity extends Activity {

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
    
    public void toSettingsActivity(View view){
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    	overridePendingTransition(0, 0);
    } 
}
