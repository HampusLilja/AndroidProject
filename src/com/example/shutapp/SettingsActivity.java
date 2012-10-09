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

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingsActivity extends Activity implements OnItemClickListener{
	
	private List<String> settingsList;
	private ArrayAdapter<String> adapter;
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
        initiateSettingsList();
        initiateArrayAdapter();
    }
    
    
    /**
     * Private method that creates the arraylist that represents
     * all our settings avaible
     */
    private void initiateSettingsList() {
    	
    	//Adds the nickname list item
    	if(settingsList == (null))
    		settingsList = new ArrayList<String>();
		settingsList.add("Nickname:" + "\t" + Settings.getNickname());
		
	}
    
    /**
     * When list is updated, e.g with new username this method 
     * needs to be run.
     */
    private void updateSettingsList(){
    	settingsList.removeAll(settingsList);
    	initiateSettingsList();
    }


	/**
	 * Create an array adapter
	 */
	private void initiateArrayAdapter() {
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settingsList);
		ListView listView = (ListView) findViewById(R.id.settingsList);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this); 
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


	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		switch(position){
		//nickname case
		case StringLiterals.NICKNAME_INDEX:
			clickOnNicknameItem();
			break;
		}
		
	}


	/**
	 * Makes the dialog popup for changing nickname
	 */
	private void clickOnNicknameItem() {
			final Dialog changeNicknameDialog = new Dialog(this);
			changeNicknameDialog.setContentView(R.layout.change_nickname_dialog);
			final EditText etChatroomInput = (EditText) changeNicknameDialog.findViewById(R.id.et_change_nickname);
			
			Button btnDialogChangeNickname = (Button) changeNicknameDialog.findViewById(R.id.btn_change_nickname);
			btnDialogChangeNickname.setOnClickListener(new OnClickListener() {
				
				public void onClick(View view){
					String nameToBeChanged = etChatroomInput.getText().toString();
					if(nameToBeChanged.equals("") || nameToBeChanged == null)
						return;
					Settings.setNickname(nameToBeChanged, SettingsActivity.this);
					updateSettingsList();
					changeNicknameDialog.cancel();
					
				}
			});
			changeNicknameDialog.show();
		
	} 

    
}
