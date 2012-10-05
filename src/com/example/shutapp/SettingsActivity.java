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
