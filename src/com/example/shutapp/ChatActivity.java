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

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

//Got a lot of help and inspiration from http://avilyne.com/?p=267

/**
 * A class for Chat Activity.
 * @author Group12
 *
 */
public class ChatActivity extends Activity {

	// This intent filter will be set to filter on the string "GCM_RECEIVED_ACTION"
	private IntentFilter gcmFilter;
	// This TextView is our chatlog
	private TextView tvChatLogHistory;
	// This ScrollView is going to contain our chatlog
	private ScrollView svChatLog;
	// The chatroom wich this activity currently handles
	private Chatroom chatroom;

	/**
	 * This broadcastreceiver instance will receive messages broadcast
	 * with the action "GCM_RECEIVED_ACTION" via the gcmFilter.
	 */
	private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

		/**
		 * onReceive.
		 * @param context
		 * @param intent
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			String receivedMessage = "No broadcast message";

			receivedMessage = intent.getExtras().getString("gcm");
			if (receivedMessage != null) {
				translateMessage(receivedMessage);
			}
		}
	};

	/**
	 * Creates an environment for chatActivity. 
	 *
	 * @param savedInstanceState	the state of the saved instance 	
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		//prevents androidkeyboard from autopopping when entering activity
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		svChatLog = (ScrollView) findViewById(R.id.sv_chat_log);
		tvChatLogHistory = (TextView) findViewById(R.id.tvChatLog);

		//scrollview autoscrolls down
		svChatLog.post(new Runnable(){
			/**
			 * Run.
			 */
			public void run(){
				svChatLog.scrollTo(0, tvChatLogHistory.getHeight());
			}
		});

		//both threads are needed
		svChatLog.post(new Runnable(){
			/**
			 * Run.
			 */
			public void run(){
				svChatLog.fullScroll(ScrollView.FOCUS_DOWN);
			}
		});

		// Create our IntentFilter, which will be used in conjunction with a
		// broadcast receiver.
		gcmFilter = new IntentFilter();
		gcmFilter.addAction("GCM_RECEIVED_ACTION");

		DatabaseHandler db = new DatabaseHandler(this);
		//check if you currently are in a chatroom, if not you are redirected
		//to nearbyChatroomsactivity
		try{
			chatroom = db.getChatroom(Settings.getCurrentChatroom());
			if(chatroom != null){

				if(!Parser.checkFileExistance(chatroom.getName(), this)){
					Parser.initiateFile(chatroom.getName(), this);
				}

				new HttpMessage(
						StringLiterals.JOIN_CHATROOM_MESSAGE_TYPE,
						chatroom.getName(), Settings.getREGID(),
						null, null, null);
				appendSome(StringLiterals.LINE_BUFFER);
				tvChatLogHistory.append(chatroom.readLog(this));
			}

		} catch(Exception e){
			String exception = e.toString();
			Log.e("TAG", "Not joind a chat room" + exception);
			toNearbyConversationsActivity(findViewById(R.layout.activity_chat));
		}

		registerReceiver(gcmReceiver, gcmFilter);
	}

	/**
	 * this is a fuckugly dummysolution to the 
	 * "start writing in the bottom" - problem.
	 * @param n the amount of lines you want to buffeer
	 */
	private void appendSome(int n){
		for(int i = 1; i<=n; i++){
			tvChatLogHistory.append("\n");
		}
	}

	/**
	 * If our activity is paused, it is important to UN-register any
	 * broadcast receivers.
	 */
	@Override
	protected void onPause() {

		unregisterReceiver(gcmReceiver);
		super.onPause();
	}

	/**
	 * When an activity is resumed, be sure to register any
	 * broadcast receivers with the appropriate intent.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(gcmReceiver, gcmFilter);

	}

	/**
	 * Leav the chat room.
	 */
	@Override
	public void onStop(){
		if(chatroom != null){
			Log.d("Chatroom", "Leaving chatroom " + chatroom.getName());
			new HttpMessage(StringLiterals.LEAVE_CHATROOM_MESSAGE_TYPE,
					chatroom.getName(), Settings.getREGID(),
					null, null, null);
		}
		super.onStop();

	} 

	/**
	 * Is called when the send button is 
	 * pressed and sends the message.
	 *
	 * @param view	a view of the text model
	 */
	public void sendMessage(View view){
		// This EditText is used for messageinput
		EditText etMessageInput;

		etMessageInput = (EditText) findViewById(R.id.written_msg);
		String messageToSend = etMessageInput.getText().toString();
		if(messageToSend.equals("") || messageToSend == null){
			return;
		}
		new HttpMessage(StringLiterals.CHATROOM_MESSAGE_TYPE,
				chatroom.getName(), Settings.getNickname(),
				messageToSend, null, null);
		etMessageInput.setText("");
	}

	/**
	 * Appends a username and message to the correct format
	 * to the textview representating the chatlog.
	 * @param username Username to be appended
	 * @param message Message to be appended
	 */
	private void appendToChatLogHistory(String username, String message) {
		if (username != null && message != null) {
			if(username.equals("SERVER")){
				Spannable wordToSpan = new SpannableString(
						username + ": " + message + "\n");        
				wordToSpan.setSpan(new ForegroundColorSpan(Color.BLUE),
						0, wordToSpan.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				tvChatLogHistory.append(wordToSpan);
			}else{
				tvChatLogHistory.append(username + ": ");
				tvChatLogHistory.append(message + "\n");
			}
		}
	}

	/**
	 * 
	 * @param msg Message to be translated
	 */
	private void translateMessage(String msg) {
		String[] temp;
		temp = msg.split(":");
		String username = temp[0];
		String message;
		if(temp.length > 2){
			StringBuffer sb = new StringBuffer();
			for(int i = 1; i < temp.length; i++){
				sb.append(temp[i]);
				sb.append(":");
			}
			sb.deleteCharAt(sb.length()-1);
			message = sb.toString();
		}else{
			message = temp[1];
		}
		if(!username.equals("SERVER")){
			chatroom.saveMessage(username + ": " + message, this);
		}
		appendToChatLogHistory(username, message);

	}
	/**
	 * Redirects the user to ChatActivity.java.
	 *
	 * @param view	a view of the text model
	 */
	public void toGpsActivity(View view){
		Intent intent = new Intent(this, GpsActivity.class);
		startActivity(intent);
		overridePendingTransition(0, 0);
	} 

	/**
	 * Redirects the user to NearbyConversationsActivity.java.
	 *
	 * @param view	a view of the text model
	 */
	public void toNearbyConversationsActivity(View view){
		Intent intent = new Intent(this, NearbyConversationsActivity.class);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}

	/**
	 * Redirects the user to GpsACtivity.java.
	 *
	 * @param view	a view of the text model
	 */
	public void toChatActivity(View view){
		Intent intentToRedirect = new Intent(this, ChatActivity.class);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}
	/**
	 * Redirects the user to SettingsActivity.java.
	 *
	 * @param view	a view of the text model
	 */
	public void toSettingsActivity(View view){
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(0, 0);
	} 

	/**
	 * Saves the message.
	 *
	 * @param view	a view of the text model
	 * @return chatmessage A string of the send message 
	 */
	public String saveMessage(View view){
		EditText thetext = (EditText)findViewById(R.id.written_msg);
		return thetext.getText().toString();

	}
}
