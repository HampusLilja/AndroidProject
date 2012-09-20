package com.example.shutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ChatActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_chat, menu);
        return true;
    }
    
  /*  public void toChatActivity(View view){
    	Intent intentToRedirect = new Intent(this, ChatActivity.class);
    	startActivity(intentToRedirect);
    } */
    
    public void toMapActivity(View view){
    	Intent intentToRedirect = new Intent(this, MapActivity.class);
    	startActivity(intentToRedirect);
    } 
    
    public void toNearbyConversationsActivity(View view){
    	Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
    	startActivity(intentToRedirect);
    } 
}
