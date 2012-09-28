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
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GpsActivity extends MapActivity implements LocationListener {
	private final int zoomLevel = 17; // 1 to 21
	private GeoPoint gP;
	private MapController mControl;
	private MyLocationOverlay compass;
	private MapOverlay itemizedoverlay;
	private List<Overlay> mapOverlays;
	private Drawable drawable;
	private LocationManager lm;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		MapView mapView = (MapView) findViewById(R.id.mapView);

		mapView.setBuiltInZoomControls(true);   //true = being able to zoom with buttons
		mapView.getController().setZoom(zoomLevel); //sets zoomlevel from the startup

		mControl = mapView.getController();
		
		Location startLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);		
		
		double lat = 57.7012596130371;
		double lon = 11.9670495986938;
		if( startLocation != null ) {
			lat = startLocation.getLatitude();
			lon = startLocation.getLongitude();
		}

		updatePosition(lat, lon);

		mapOverlays = mapView.getOverlays();
		compass = new MyLocationOverlay(GpsActivity.this, mapView);
		drawable = this.getResources().getDrawable(R.drawable.maparrow);
		itemizedoverlay = new MapOverlay(drawable, this);

		OverlayItem overlayitem = new OverlayItem(gP, null, null);
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
		mapOverlays.add(compass);

	}

	private void updatePosition(double latitude, double longitude) {
		gP = new GeoPoint((int)(latitude*1E6), (int)(longitude*1E6)); //converting to micro-degrees with 1E6
		mControl.animateTo(gP);
	}

	public void onLocationChanged(Location arg0) {
		double latitude = arg0.getLatitude();
		double longitude = arg0.getLongitude();
		Log.e("GPS", "location changed: lat="+String.valueOf(latitude)+", lon="+String.valueOf(longitude));
		updatePosition(latitude, longitude);

		if(itemizedoverlay!=null) {
			mapOverlays.remove(itemizedoverlay);

			itemizedoverlay = new MapOverlay(drawable, this);
			OverlayItem currentOverlay = new OverlayItem(gP,"Current Location","Here is my current location!!!");
			itemizedoverlay.addOverlay(currentOverlay);

			mapOverlays.add(itemizedoverlay);
		} else {
			itemizedoverlay = new MapOverlay(drawable, this);

			OverlayItem currentOverlay = new OverlayItem(gP,"Current Location","Here is my current location!!!");
			itemizedoverlay.addOverlay(currentOverlay);
			mapOverlays.add(itemizedoverlay);
		}
	}

	@Override
	protected void onPause() {
		compass.disableCompass();
		super.onPause();
		lm.removeUpdates(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		compass.enableCompass();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);        
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
