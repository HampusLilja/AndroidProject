package com.example.shutapp.test;

import com.example.shutapp.ChatActivity;
import com.example.shutapp.MapActivity;
import com.example.shutapp.NearbyConversationsActivity;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

public class NearbyConversationTest extends
		ActivityInstrumentationTestCase2<NearbyConversationsActivity> {
	
	private NearbyConversationsActivity nActivity;
	private Button cButton;
	private Button sButton;
	private Button mButton;
	private TextView resultView;
	private ActivityMonitor activityChatMonitor;
	private ActivityMonitor activityMapMonitor;
	
	public NearbyConversationTest() {
		super("com.example.shutapp.MainActivity", NearbyConversationsActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// register next activity that need to be monitored.
		activityChatMonitor = getInstrumentation().addMonitor(ChatActivity.class.getName(), null, false);
		activityMapMonitor = getInstrumentation().addMonitor(MapActivity.class.getName(), null, false);

	    nActivity = getActivity();

	    mButton =
	    		(Button) nActivity.findViewById(
	    				com.example.shutapp.R.id.map_button
	    				);
	    
	    cButton =
	    		(Button) nActivity.findViewById(
	    				com.example.shutapp.R.id.chat_button
	    				);
	    
	    sButton =
	    		(Button) nActivity.findViewById(
	    				com.example.shutapp.R.id.settings_button
	    				);
	    
	    resultView = (TextView) nActivity.findViewById(
	    		com.example.shutapp.R.id.textView1); 
		
	}
	
	public void testPreConditions() {
		assertNotNull("Can´t find the Map Button", mButton);
		assertNotNull("Can´t find the Chat Button", cButton);
		assertNotNull("Can´t find the Settings Button", sButton);
	}
	
	public void testChatButton() {
		
		nActivity.runOnUiThread(
				new Runnable() {
					public void run() {
						cButton.requestFocus();
						cButton.performClick();
					}
				});

		wait(4);
		
		ChatActivity cActivity = (ChatActivity) getInstrumentation().waitForMonitorWithTimeout(activityChatMonitor, 5);
		
		// next activity is opened and captured.
		assertNotNull("Could´t open Activity", cActivity);
		cActivity.finish();

		resultView = (TextView) cActivity.findViewById(
	    		com.example.shutapp.R.id.textView1); 
		
		assertNotNull("Can´t find the Text View", resultView);
	    assertEquals("Wrong text",
	    		"ChatActivity", resultView.getText());
	
	}
	
	public void testMapButton() {
		
		nActivity.runOnUiThread(
				new Runnable() {
					public void run() {
						mButton.requestFocus();
						mButton.performClick();
					}
				});

		wait(4);
		
		MapActivity mActivity = (MapActivity) getInstrumentation().waitForMonitorWithTimeout(activityMapMonitor, 5);
		
		// next activity is opened and captured.
		assertNotNull("Could´t open Activity", mActivity);
		mActivity.finish();

		resultView = (TextView) mActivity.findViewById(
	    		com.example.shutapp.R.id.textView1); 
		
		assertNotNull("Can´t find the Text View", resultView);
	    assertEquals("Wrong text",
	    		"Hi this is map activity!", resultView.getText());
	
	}
	
	public void wait(int sec){
		
		try {
			Thread.sleep(sec*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
