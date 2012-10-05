package com.example.shutapp.test;
/*
import com.example.shutapp.GpsActivity;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;


public class GpsTest extends ActivityInstrumentationTestCase2<GpsActivity>{
	private GpsActivity gpsActivity;
	
	@SuppressWarnings({ "deprecation"})
	public GpsTest() {
		super("com.example.shutapp.GpsActivity", GpsActivity.class);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		gpsActivity = (GpsActivity) getActivity();
		testLocation();
		testOverlay();
		gpsActivity.onLocationChanged(null);
		
	}
	public void testLocation(){
		Assert.assertNotNull("Latitude is null", gpsActivity.getCurrentGeoPoint().getLatitudeE6());
		Assert.assertNotNull("Longitude is null", gpsActivity.getCurrentGeoPoint().getLongitudeE6());
	}
	
	public void testOverlay(){
		gpsActivity.newOverlay();
		//Assert.assertNotNull("No overlay is added",);
	}
	
	@Override
	protected void tearDown(){
	gpsActivity.finish();
	}
}*/
