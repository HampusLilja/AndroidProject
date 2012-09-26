package com.example.shutapp;

import java.util.List;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GpsActivity extends MapActivity implements LocationListener {
	private final int zoomLevel = 17; // 1 to 21
	private GeoPoint gP;
	//private LocationManager lm;
	private MapController mControl;
	private double Latitude = 57.7012596130371;
	private double Longitude = 11.9670495986938;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
      
        MapView mapView = (MapView) findViewById(R.id.mapView);
        
        mapView.setBuiltInZoomControls(true);   //true = being able to zoom with buttons
        mapView.getController().setZoom(zoomLevel); //sets zoomlevel from the startup
    
        mControl = mapView.getController();

        Location startLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double startLongitude = startLocation.getLongitude();
        double startLatitude = startLocation.getLatitude();
        updatePosition(startLatitude, startLongitude);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.maparrow);
        MapOverlay itemizedoverlay = new MapOverlay(drawable, this);
        
        OverlayItem overlayitem = new OverlayItem(gP, null, null);
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        
    }
    
    private void updatePosition(double latitude, double longitude) {
    	gP = new GeoPoint((int)(latitude*1E6), (int)(longitude*1E6)); //converting to micro-degrees with 1E6
        mControl.animateTo(gP);
	}

	public void onLocationChanged(Location arg0) {
    	double latitude = arg0.getLatitude();
        double longitude = arg0.getLongitude();
    	String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);
        Log.e("GPS", "location changed: lat="+lat+", lon="+lon);
        updatePosition(latitude, longitude);
    }
    
    public void onProviderDisabled(String arg0) {
        Log.e("GPS", "provider disabled " + arg0);
    }
    public void onProviderEnabled(String arg0) {
        Log.e("GPS", "provider enabled " + arg0);
    }
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        Log.e("GPS", "status changed to " + arg0 + " [" + arg1 + "]");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}        
        
    
    public void toChatActivity(View view){
    	Intent intentToRedirect = new Intent(this, ChatActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    
    
    public void toNearbyConversationsActivity(View view){
    	Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    
    public void toGpsActivity(View view){
    	Intent intentToRedirect = new Intent(this, GpsActivity.class);
    	startActivity(intentToRedirect);
    	overridePendingTransition(0, 0);
    }
    
    public void toSettingsActivity(View view){
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    	overridePendingTransition(0, 0);
    } 
}
