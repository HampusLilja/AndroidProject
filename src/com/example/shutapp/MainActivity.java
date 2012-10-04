package com.example.shutapp;

import com.example.shutapp.Chatroom;


import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class MainActivity extends Activity {
	
	private String token;
 
    
 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if( Settings.getNickname().equals("") ){
        	setContentView(R.layout.activity_main);  	
        } else {
        //manageAccount(); // You have to comment this for the application to work in the AVD!
        testChatroom();
    	Intent i = new Intent(MainActivity.this, NearbyConversationsActivity.class);
    	startActivity(i);
    	overridePendingTransition(0, 0);      
        }
        
    }

    private void testChatroom() {
    	Location loc = new Location("loc");
    	loc.setLatitude(57.697261);
    	loc.setLongitude(11.97975);
		Chatroom cr = new Chatroom("chatrum1", loc);
		cr.saveMessage("Chatroom works", this);
		cr.saveMessage("asdasdasdasds", this);
		TextView textview = (TextView) findViewById(R.id.textView1);
    	textview.setText(cr.getLastMessage(this));
	}




	//this method handles the authentication of the users gmail accounts
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
    	
    
    	
    	
    	//to identify the gmail account on the screen
    	TextView textview = (TextView) findViewById(R.id.textView1);
    	textview.setText(theAcc.name);
    }
    
    //called when the redirectbutton is pressed, redirected to another activity
    public void redirectFromMain(View view){
    	EditText nickname_input = (EditText)findViewById(R.id.nickname_input);
    	Settings.setNickname(nickname_input.getText().toString());
    	Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    //asynchronous class that handles the account token verification
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
    //if an error should occur
    public class OnError implements Handler.Callback {

		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			return false;
		}
    	//to do
    }
    
    
}




