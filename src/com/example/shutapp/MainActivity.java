package com.example.shutapp;

import static com.example.shutapp.MiscResources.*;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {
	
	private String token;
	
	 // Replace the xxx with the project id generated from the Google console when
    // you defined a Google APIs project.
    //private static final String PROJECT_ID = "shutappchalmers";
 
    // This tag is used in Log.x() calls
    private static final String TAG = "MainActivity";
 
    // This string will hold the lengthy registration id that comes
    // from GCMRegistrar.register()
    private String regId = "";
 
    // These strings are hopefully self-explanatory
    private String registrationStatus = "Not yet registered";
    private String broadcastMessage = "No broadcast message";
 
    // This intent filter will be set to filter on the string "GCM_RECEIVED_ACTION"
    IntentFilter gcmFilter;
 
    // textviews used to show the status of our app's registration, and the latest
    // broadcast message.
    TextView tvRegStatusResult;
    TextView tvBroadcastMessage;
 
    // This broadcastreceiver instance will receive messages broadcast
    // with the action "GCM_RECEIVED_ACTION" via the gcmFilter
     
    // A BroadcastReceiver must override the onReceive() event.
    private BroadcastReceiver gcmReceiver = new BroadcastReceiver() {
 
        @Override
        public void onReceive(Context context, Intent intent) {
 
            broadcastMessage = intent.getExtras().getString("gcm");
 
            if (broadcastMessage != null) {
                // display our received message
                tvBroadcastMessage.setText(broadcastMessage);
            }
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //manageAccount(); // You have to comment this for the application to work in the AVD!
        
        /* register id if not already done, this is a must*/
        /*GCMRegistrar.checkDevice(this);*/
        /*GCMRegistrar.checkManifest(this);*/
        
     /*   final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
          GCMRegistrar.register(this, SENDER_ID);
        } else {
          Log.v("ShutAppLOGG", "Already registered");
        } */
        
        tvBroadcastMessage = (TextView) findViewById(R.id.tv_message);
        tvRegStatusResult = (TextView) findViewById(R.id.tv_reg_status_result);
 
        // Create our IntentFilter, which will be used in conjunction with a
        // broadcast receiver.
        gcmFilter = new IntentFilter();
        gcmFilter.addAction("GCM_RECEIVED_ACTION");
 
        registerClient();
    }
    
 // This registerClient() method checks the current device, checks the
    // manifest for the appropriate rights, and then retrieves a registration id
    // from the GCM cloud.  If there is no registration id, GCMRegistrar will
    // register this device for the specified project, which will return a
    // registration id.
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
 
                registrationStatus = "Registering...";
 
                tvRegStatusResult.setText(registrationStatus);
 
                // register this device for this project
                GCMRegistrar.register(this, PROJECT_ID);
                regId = GCMRegistrar.getRegistrationId(this);
 
                registrationStatus = "Registration Acquired";
 
                // This is actually a dummy function.  At this point, one
                // would send the registration id, and other identifying
                // information to your server, which should save the id
                // for use when broadcasting messages.
                sendRegistrationToServer();
 
            } else {
 
                registrationStatus = "Already registered";
 
            }
             
             
        } catch (Exception e) {
             
            e.printStackTrace();
            registrationStatus = e.getMessage();
             
        }
 
        Log.d(TAG, registrationStatus);
        tvRegStatusResult.setText(registrationStatus);
         
        // This is part of our CHEAT.  For this demo, you'll need to
        // capture this registration id so it can be used in our demo web
        // service.
        Log.d(TAG, regId);
 
    }
    
    private void sendRegistrationToServer() {
        // This is an empty placeholder for an asynchronous task to post the
        // registration
        // id and any other identifying information to your server.
    }
 
    // If the user changes the orientation of his phone, the current activity
    // is destroyed, and then re-created.  This means that our broadcast message
    // will get wiped out during re-orientation.
    // So, we save the broadcastmessage during an onSaveInstanceState()
    // event, which is called prior to the destruction of the activity.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
 
        super.onSaveInstanceState(savedInstanceState);
 
        savedInstanceState.putString("BroadcastMessage", broadcastMessage);
 
    }
 
    // When an activity is re-created, the os generates an onRestoreInstanceState()
    // event, passing it a bundle that contains any values that you may have put
    // in during onSaveInstanceState()
    // We can use this mechanism to re-display our last broadcast message.
     
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
         
        super.onRestoreInstanceState(savedInstanceState);
 
        broadcastMessage = savedInstanceState.getString("BroadcastMessage");
        tvBroadcastMessage.setText(broadcastMessage);
 
    }
 
    // If our activity is paused, it is important to UN-register any
    // broadcast receivers.
    @Override
    protected void onPause() {
         
        unregisterReceiver(gcmReceiver);
        super.onPause();
    }
     
    // When an activity is resumed, be sure to register any
    // broadcast receivers with the appropriate intent
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(gcmReceiver, gcmFilter);
 
    }
 // NOTE the call to GCMRegistrar.onDestroy()
    @Override
    public void onDestroy() {
 
        GCMRegistrar.onDestroy(this);
 
        super.onDestroy();
    }
 

    public void manageAccount(){
    	AccountManager am = AccountManager.get(this);
    	Bundle options = new Bundle();
    	
    	Account[] accounts = am.getAccountsByType("com.google");
    	//List<String> possibleEmails = new LinkedList<String>();
    	
    	//this is just for testing, the user should be able to choose what gmail acc he wants to use
    	Account theAcc = accounts[0];
    	
    	am.getAuthToken(
    		    theAcc,                         // Account retrieved using getAccountsByType()
    		    "Manage your tasks",            // Auth scope
    		    options,                        // Authenticator-specific options
    		    this,                           // Your activity
    		    new OnTokenAcquired(),          // Callback called when a token is successfully acquired
    		    new Handler(new OnError()));    // Callback called if an error occurs
    	
    
    	
    	
    	
    	TextView textview = (TextView) findViewById(R.id.textView1);
    	textview.setText(theAcc.name);
    }
    

    public void redirectFromMain(View view){
    	Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        public void run(AccountManagerFuture<Bundle> result) {
        	Intent launch = null;
			try {
				launch = (Intent) result.getResult().get(AccountManager.KEY_INTENT);
			} catch (OperationCanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AuthenticatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (launch != null) {
                startActivityForResult(launch, 0);
                return;
            }
            
            // Get the result of the operation from the AccountManagerFuture.
            Bundle bundle = null;
			try {
				bundle = result.getResult();
			} catch (OperationCanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AuthenticatorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
            // The token is a named value in the bundle. The name of the value
            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
            token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            System.out.println(token);
            
            
        }
    }
    
    public class OnError implements Handler.Callback {

		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			return false;
		}
    	//to do
    }
    
    
}




