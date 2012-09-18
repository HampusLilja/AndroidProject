package com.example.shutapp;

import android.app.Activity;
import android.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void redirectFromMain(View view){
    	Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
    	startActivity(intentToRedirect);
    }
    
}
