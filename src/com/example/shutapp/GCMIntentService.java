/** this class should handle all GCM messaging (Intents) */

package com.example.shutapp;

import static com.example.shutapp.MiscResources.*;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.*;

	public class GCMIntentService extends GCMBaseIntentService {

		
	     
	    private static final String TAG = "GCMIntentService";
	    
	    /**
	     * Constructor for GCMIntentService
	     */
	    public GCMIntentService()
	    {
	        super(SENDER_ID);
	        Log.d(TAG, "GCMIntentService initiated");
	    }
	    /**
	     * If an error occurs
	     *
	     * @param ctx		a list of properties
	     * @param error 	A string that describes the error
	     */ 
	    @Override
	    protected void onError(Context ctx, String error) {
	        // Do something here if error!
	        Log.d(TAG, "Error: " + error);
	         
	    }
	    /**
	     * Sends a message
	     *
	     * @param ctx 		a list of properties
	     * @param intent	an abstract description of an operation to be performed
	     */
	    @Override
	    protected void onMessage(Context ctx, Intent intent) {
	    	Log.d(TAG, "Message has been received");
	        String message = intent.getStringExtra("message");
	        sendGCMIntent(ctx, message);
	         
	    }
	 
	     
	    private void sendGCMIntent(Context ctx, String message) {
	        Intent broadcastIntent = new Intent();
	        broadcastIntent.setAction("GCM_RECEIVED_ACTION");
	        broadcastIntent.putExtra("gcm", message);
	        ctx.sendBroadcast(broadcastIntent);
	         
	    }
	     
	    /**
	     * Sends regId to the server 
	     *
	     * @param ctx		a list of properties
	     * @param regId		a string containing the registration id
	     */ 
	    @Override
	    protected void onRegistered(Context ctx, String regId) {
	        // send regId to your server
	        Log.d(TAG, regId);
	         
	    }
	    /**
	     * add regId to the server
	     *
	     * @param ctx 		a list of properties
	     * @param regId		a string containing the registration id
	     */
	    @Override
	    protected void onUnregistered(Context ctx, String regId) {
	        // send to server to remove that regId
	         
	    }

}
