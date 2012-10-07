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

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private String token;
 
	/**
	 * Sets the content view and 
	 *
	 * @param savedInstanceState the state of the saved instance
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Settings.initiateSettingsFile(this);
        //Parser.clean("test", this);
        //testing parser
        Parser.write("test1", "test", this);
        Parser.write("test2", "test", this);
       // Parser.write("test3", "test", this);
       // Parser.write("test4", "test", this);
        
        //Log.d("PARSER", Parser.readFirst("test", this));
        Log.d("PARSER", Parser.readAll("test", this));
        
        //Settings.setNickname("Prassel");
/*
        if( Settings.getNickname().equals("") ){
        	setContentView(R.layout.activity_main);  	
        } else {
        //manageAccount(); // You have to comment this for the application to work in the AVD!
       testChatroom();
    	Intent i = new Intent(MainActivity.this, NearbyConversationsActivity.class);
    	startActivity(i);
    	overridePendingTransition(0, 0);      
        }
        */
    }

    /*private void testChatroom() {
    	Location loc = new Location("loc");
    	loc.setLatitude(57.697261);
    	loc.setLongitude(11.97975);
		Chatroom cr = new Chatroom("chatrum1", loc);
		cr.saveMessage("Chatroom works", this);
		cr.saveMessage("asdasdasdasds", this);
		TextView textview = (TextView) findViewById(R.id.textView1);
    	textview.setText(cr.getLastMessage(this));
	}*/

    /**
     * Handles the authentication of the users gmail accounts
     */
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
    
    /**
     * Called when the redirect button is pressed, redirected to another activity 
     *
     * @param view a view of the text model
     */
    public void redirectFromMain(View view){
    	EditText nickname_input = (EditText)findViewById(R.id.nickname_input);
    	Settings.setNickname(nickname_input.getText().toString(), this);
    	Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    /**
     * Asynchronous class that handles the account token verification 
     *
     * @param view a view of the text model
     */
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

    /**
 	* Handle errors 
 	*/
    public class OnError implements Handler.Callback {
    	
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			return false;
		}
    	//to do
    }
    
    
}




