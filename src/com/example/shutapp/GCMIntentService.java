/** this class should handle all GCM messaging (Intents) */

package com.example.shutapp;

import static com.example.shutapp.MiscResources.*;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.*;

	public class GCMIntentService extends GCMBaseIntentService {

		
	     
	    private static final String TAG = "GCMIntentService";
	    
	
	    public GCMIntentService()
	    {
	        super(SENDER_ID);
	        Log.d(TAG, "GCMIntentService initiated");
	    }
	     
	    @Override
	    protected void onError(Context ctx, String error) {
	        // Do something here if error!
	        Log.d(TAG, "Error: " + error);
	         
	    }
	 
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
	     
	     
	    @Override
	    protected void onRegistered(Context ctx, String regId) {
	        // send regId to your server
	        Log.d(TAG, regId);
	         
	    }
	 
	    @Override
	    protected void onUnregistered(Context ctx, String regId) {
	        // send to server to remove that regId
	         
	    }

}
