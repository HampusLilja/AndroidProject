package com.example.shutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void toChatActivity(View view){
    	Intent intentToRedirect = new Intent(this, ChatActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    
    
    public void toNearbyConversationsActivity(View view){
    	Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    } 
    
    public void toGpsActivity(View view){
    	Intent intent = new Intent(this, GpsActivity.class);
    	startActivity(intent);
    	overridePendingTransition(0, 0);
    }
    
    public void toSettingsActivity(View view){
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    	overridePendingTransition(0, 0);
    } 

    
}
