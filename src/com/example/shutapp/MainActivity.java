package com.example.shutapp;

import static com.example.shutapp.MiscResources.SENDER_ID;

import android.app.Activity;
import com.google.android.gcm.GCMRegistrar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
=======
>>>>>>> GCM_Compability
import android.util.Log;
import java.util.LinkedList;
import java.util.List;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private String token;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manageAccount();
        /* register id if not already done, this is a must*/
        /*GCMRegistrar.checkDevice(this);*/
        /*GCMRegistrar.checkManifest(this);*/
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
          GCMRegistrar.register(this, SENDER_ID);
        } else {
          Log.v("ShutAppLOGG", "Already registered");
        }
    }
    
    public void manageAccount(){
    	AccountManager am = AccountManager.get(this);
    	Bundle options = new Bundle();
    	
    	Account[] accounts = am.getAccountsByType("com.google");
    	List<String> possibleEmails = new LinkedList<String>();
    	
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




