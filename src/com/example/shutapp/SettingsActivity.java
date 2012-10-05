package com.example.shutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class SettingsActivity extends Activity {
	
	/**
	 * Creates an environment for SettingsActivity
	 *
	 * @param savedInstanceState  the state of the saved instance
	 * 
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
     * Redirects the user to NearbyConversationsActivity.java
     *	
     * @param view	a view of the text model
     */
    public void toNearbyConversationsActivity(View view){
    	Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    } 
    /**
     * Redirects the user to GpsACtivity.java
     *
     * @param view	a view of the text model
     */
    public void toGpsActivity(View view){
    	Intent intent = new Intent(this, GpsActivity.class);
    	startActivity(intent);
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

    
}
