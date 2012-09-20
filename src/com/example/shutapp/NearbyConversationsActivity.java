package com.example.shutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class NearbyConversationsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_conversations);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_nearby_conversations, menu);
        return true;
    }
    
    public void toChatActivity(View view){
    	Intent intentToRedirect = new Intent(this, ChatActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    
    public void toMapActivity(View view){
    	Intent intentToRedirect = new Intent(this, MapActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    
  /* public void toNearbyConversationsActivity(View view){
    	Intent intentToRedirect = new Intent(this, NearbyConversations.class);
    	startActivity(intentToRedirect);
    } */
}
