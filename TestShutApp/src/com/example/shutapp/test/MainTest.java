package com.example.shutapp.test;

import android.test.ActivityInstrumentationTestCase2;
import com.example.shutapp.*;

import android.app.Instrumentation.ActivityMonitor;

import android.widget.Button;
import android.widget.TextView;

public class MainTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private MainActivity mActivity;
	
	private Button mButton;
	private TextView resultView;
	private ActivityMonitor activityMonitor;
	  
	@SuppressWarnings("deprecation")
	public MainTest() {
		super("com.example.shutapp.MainActivity", MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// register next activity that need to be monitored.
		activityMonitor = getInstrumentation().addMonitor(NearbyConversationsActivity.class.getName(), null, false);

	    mActivity = getActivity();

	    mButton =
	      (Button) mActivity.findViewById(
	        com.example.shutapp.R.id.button1
	      );
	    
	    resultView = (TextView) mActivity.findViewById(
	    		com.example.shutapp.R.id.textView1); 
		
	}
	
	public void testPreConditions() {
		assertNotNull("Can´t find the Button", mButton);
		assertNotNull("Can´t find the Text View", resultView);
	}
	
	public void testRedirectButton() {
		
		mActivity.runOnUiThread(
				new Runnable() {
					public void run() {
						mButton.requestFocus();
						mButton.performClick();
					}
				});

		wait(6);
		
		NearbyConversationsActivity nActivity = (NearbyConversationsActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
		
		// next activity is opened and captured.
		assertNotNull("Could´t open Activity", nActivity);
		nActivity.finish();

		resultView = (TextView) nActivity.findViewById(
	    		com.example.shutapp.R.id.textView1); 
		
	
	}
	
	public void wait(int sec){
		
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	/*@Override
	protected void tearDown() {
		try {
			super.tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}
