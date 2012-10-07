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

import static com.example.shutapp.MiscResources.PROJECT_ID;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

//Got a lot of help and inspiration from http://avilyne.com/?p=267

public class ChatActivity extends Activity {

	// This tag is used in Log.x() calls
	private static final String TAG = "MainActivity";

		// This string will hold the lengthy registration id that comes from GCMRegistrar.register()
		private String regId = "";

		// These strings are hopefully self-explanatory
		private String registrationStatus = "Not yet registered";
		private String broadcastMessage = "No broadcast message";

		// This intent filter will be set to filter on the string "GCM_RECEIVED_ACTION"
		private IntentFilter gcmFilter;

		// textviews used to show the status of our app's registration, and the latest broadcast message.
		private TextView tvBroadcastMessage;
		private EditText etMessageInput;
		private TextView tvChatLogHistory;
		
		private ScrollView svChatLog;
		//private LinearLayout llChatLog;
		private Chatroom chatroom;
		// This broadcastreceiver instance will receive messages broadcast
		// with the action "GCM_RECEIVED_ACTION" via the gcmFilter

		// A BroadcastReceiver must override the onReceive() event.
		private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				broadcastMessage = intent.getExtras().getString("gcm");
				if (broadcastMessage != null) {
					// display our received message
					translateMessage(broadcastMessage);
					//appendToChatLogHistory("test", broadcastMessage);
				}
			}


		};
		/**
		 * Creates an environment for chatActivity 
		 *
		 * @param savedInstanceState	the state of the saved instance 	
		 * 
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_chat);

			Intent intent = getIntent();
			String chatroomName = intent.getStringExtra(NearbyConversationsActivity.EXTRA_MESSAGE);

			chatroom = Chatrooms.getByName(chatroomName);
			getWindow().setSoftInputMode(
				      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			/* NO NEED FOR THESE ATM
			tvChatroomLabel = (TextView) findViewById(R.id.tv_chatroom_label);
			tvChatroomLabel.setText(chatroom.getName());
		
			tvBroadcastMessage = (TextView) findViewById(R.id.tv_message);
			tvRegStatusResult = (TextView) findViewById(R.id.tv_reg_status_result);
			*/

			svChatLog = (ScrollView) findViewById(R.id.sv_chat_log);
			//llChatLog = (LinearLayout) findViewById(R.id.ll_chat_log);
			tvChatLogHistory = (TextView) findViewById(R.id.tvChatLog);
			//tvChatLogHistory.setBottom(ViewGroup.LayoutParams.MATCH_PARENT);
			//tvChatLogHistory.setGravity(Gravity.BOTTOM);

			//svChatLog.addView(tvChatLogHistory);

			svChatLog.post(new Runnable(){
				public void run(){
					svChatLog.scrollTo(0, tvChatLogHistory.getHeight());
				}
			});
			
			svChatLog.post(new Runnable(){
				public void run(){
					svChatLog.fullScroll(ScrollView.FOCUS_DOWN);
				}
			});

			// Create our IntentFilter, which will be used in conjunction with a
			// broadcast receiver.
			gcmFilter = new IntentFilter();
			gcmFilter.addAction("GCM_RECEIVED_ACTION");

			registerClient();
			appendSome(25);
			appendToChatLogHistory(chatroom.getName(), "Welcome to this chatroom!");

		}
		
		//this is a fuckugly dummysolution to the "start writing in the bottom" - problem
		private void appendSome(int n){
			for(int i = 1; i<=n; i++){
				tvChatLogHistory.append("\n");
			}
		}
		
		//dummytestmethod
		/*private void makeSomeNoise() {
			for(int i= 0; i<=50; i++){
				appendToChatLogHistory(chatroom.getName(), "I do this spam because im a stupid dummymethod" +i);
			}
			
		}*/

		/**
		 * checks the current device, checks the 
		 * manifest for the appropriate rights, and then retrieves a registration id
		 * from the GCM cloud. 
		 *
		 */
		public void registerClient() {

			try {
				// Check that the device supports GCM (should be in a try / catch)
				GCMRegistrar.checkDevice(this);

				// Check the manifest to be sure this app has all the required
				// permissions.
				GCMRegistrar.checkManifest(this);

				// Get the existing registration id, if it exists.
				regId = GCMRegistrar.getRegistrationId(this);

				if (regId.equals("")) {

					//registrationStatus = "Registering...";

					//tvRegStatusResult.setText(registrationStatus);

					// register this device for this project
					GCMRegistrar.register(this, PROJECT_ID);
					regId = GCMRegistrar.getRegistrationId(this);

					//registrationStatus = "Registration Acquired";

					// This is actually a dummy function.  At this point, one
					// would send the registration id, and other identifying
					// information to your server, which should save the id
					// for use when broadcasting messages.
					sendRegistrationToServer();

					Log.d(TAG, "sendregtoserver has been initialized");
					//registrationStatus = "Registration of regid done";

				} else {
					//registrationStatus = "Already registered";

				}


			} catch (Exception e) {

				e.printStackTrace();
				registrationStatus = e.getMessage();

			}

			Log.d(TAG, registrationStatus);
			//tvRegStatusResult.setText(registrationStatus);

			// This is part of our CHEAT.  For this demo, you'll need to
			// capture this registration id so it can be used in our demo web
			// service.
			Log.d(TAG, regId);

		}

		private void sendRegistrationToServer() {

			new HttpMessage(StringLiterals.DBREGISTER_MESSAGE_TYPE, Settings.getNickname(), regId, null);


		}


		/**
		 * Saves the broadcast message to prevent that it gets wiped out during re-orientation 
		 *
		 * @param savedInstanceState	 the state of the saved instance
		 * 
		 */
		@Override
		public void onSaveInstanceState(Bundle savedInstanceState) {

			super.onSaveInstanceState(savedInstanceState);

			savedInstanceState.putString("BroadcastMessage", broadcastMessage);

		}

		// When an activity is re-created, the os generates an onRestoreInstanceState()
		// event, passing it a bundle that contains any values that you may have put
		// in during onSaveInstanceState()
		// We can use this mechanism to re-display our last broadcast message.
		/**
		 * Restores the broadcast message
		 *
		 * @param savedInstanceState	 the state of the saved instance
		 * 
		 */
		@Override
		public void onRestoreInstanceState(Bundle savedInstanceState) {

			super.onRestoreInstanceState(savedInstanceState);

			broadcastMessage = savedInstanceState.getString("BroadcastMessage");
			tvBroadcastMessage.setText(broadcastMessage);

		}

		/**
		 * Un-registers broadcast receivers when the activity is paused 
		 */
		@Override
		protected void onPause() {

			unregisterReceiver(gcmReceiver);
			super.onPause();
		}

		/**
		 * Registers broadcast receivers when the activity is resumed 
		 */
		@Override
		protected void onResume() {
			super.onResume();
			registerReceiver(gcmReceiver, gcmFilter);

		}
		/**
		 * Called when the activity is about to be destroyed
		 */
		@Override
		public void onDestroy() {

			GCMRegistrar.onDestroy(this);

			super.onDestroy();
		}

		/**
		 * Is called when the send button is pressed and sends the message
		 *
		 * @param view	a view of the text model
		 */
		public void sendMessage(View view){
			etMessageInput = (EditText) findViewById(R.id.written_msg);
			String messageToSend = etMessageInput.getText().toString();
			if(messageToSend.equals("") || messageToSend == null)
				return;
			new HttpMessage(StringLiterals.CHATROOM_MESSAGE_TYPE, chatroom.getName(), Settings.getNickname(), messageToSend);
			etMessageInput.setText("");
		}

		private void appendToChatLogHistory(String username, String message) {
			if (username != null && message != null) {
				tvChatLogHistory.append(username + ": ");								
				tvChatLogHistory.append(message + "\n");	
			}
		}

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
			}
			else{
				message = temp[1];
			}

			appendToChatLogHistory(username, message);

		}
		/**
		 * Redirects the user to ChatActivity.java
		 *
		 * @param view	a view of the text model
		 */
		public void toGpsActivity(View view){
			Intent intent = new Intent(this, GpsActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);
		} 

		/**
		* Redirects the user to NearbyConversationsActivity.java
		*
 		* @param view	a view of the text model
 		*/
		public void toNearbyConversationsActivity(View view){
			Intent intent = new Intent(this, NearbyConversationsActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);
		}

		/**
		 * Redirects the user to GpsACtivity.java
 		*
 		* @param view	a view of the text model
 		*/
		public void toChatActivity(View view){
			Intent intentToRedirect = new Intent(this, ChatActivity.class);
			startActivity(intentToRedirect);
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

		/**
		 * Saves the message
		 *
		 * @param view	a view of the text model
		 * @return chatmessage A string of the send message 
		 */
		public String saveMessage(View view){
			EditText thetext = (EditText)findViewById(R.id.written_msg);
			String chatmessage = thetext.getText().toString();
			return chatmessage;

		}
	}



