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

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GpsActivity extends MapActivity implements LocationListener {
	private final static int ZOOMLEVEL = 17; // 1 to 21
	private final static float RADIUS = 1000; // size of the chat room radius
	
	private GeoPoint currentGeoPoint = null;
	private Location currentLocation = new Location("current");
	
	private MapController mapControl;
	private MyLocationOverlay compass;
	private MapOverlay itemizedoverlay;
	private List<Overlay> mapOverlays;

	private Drawable drawableArrow;
	private LocationManager locationManager;
	private RadiusOverlay radiusOverlay;
	private Criteria criteria;
	
	/**
	 * Creates an environment for a GPS map
	 *
	 * @param savedInstanceState the state of the saved instance
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		MapView mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);   //true = being able to zoom with buttons
		mapView.getController().setZoom(ZOOMLEVEL); //sets zoomlevel from the startup

		mapControl = mapView.getController();
		mapOverlays = mapView.getOverlays();
		compass = new MyLocationOverlay(GpsActivity.this, mapView);
		mapOverlays.add(compass);
		drawableArrow = this.getResources().getDrawable(R.drawable.maparrow);
		
		criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);       
        Location startLocation = locationManager.getLastKnownLocation(bestProvider);
        
		if (startLocation != null){
			updatePosition(startLocation.getLatitude(), startLocation.getLongitude());
		} else {
			Log.e("geoPointS","Unable to get startlocation");
			updatePosition(57.691469,11.977469);
		}

		drawAllCircles();

	}

	private void updatePosition(double latitude, double longitude) {
		currentLocation.setLatitude(latitude);
		currentLocation.setLongitude(longitude);
		currentGeoPoint = new GeoPoint((int)(latitude*1E6), (int)(longitude*1E6)); //converting to micro-degrees with 1E6
		mapControl.animateTo(currentGeoPoint);
	}
	/**
	 * Get the current geopoint
	 *
	 * @return the location of this object as a geopoint.
	 */
	public GeoPoint getCurrentGeoPoint() {
		return currentGeoPoint;
	}
	/**
	 * Gets the current location
	 *
	 * @return the location of this object as a location.
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}
	
	private void setShadedCircleOnLocation(double latitude, double longitude, boolean nearby) {
		radiusOverlay = new RadiusOverlay(this, latitude, longitude, RADIUS, nearby);
		mapOverlays.add(radiusOverlay);
	}
	
	private void drawAllCircles() {
		try{
			for(Chatroom room : Chatrooms.getAll()) {
				Location chatRoomLocation = room.getLocation();
			
				boolean nearby = inRangeOfChatRoom(currentLocation, chatRoomLocation);
				setShadedCircleOnLocation(room.getLocation().getLatitude(), 
						room.getLocation().getLongitude(), nearby);
			}
		} catch(Exception e) {
			System.out.println("No Chat Rooms to print on map");
		}
	}
	
	private boolean inRangeOfChatRoom(Location myLocation, Location chatRoomLocation) {
		int dist = (int) (RADIUS - myLocation.distanceTo(chatRoomLocation));
		return (dist >= 0);
	}
	/**
	 * Add a new overlay
	 */
	public void newOverlay(){
		itemizedoverlay = new MapOverlay(drawableArrow, this);
		OverlayItem currentOverlay = new OverlayItem(currentGeoPoint,"Current Location","Here is my current location!!!");
		itemizedoverlay.addOverlay(currentOverlay);
		mapOverlays.add(itemizedoverlay);
	} 
	/**
	 * Handles the events if the location is changed(updates position and add new overlay) 
	 * 
	 * @param location the new location
	 */
	public void onLocationChanged(Location location) {
		Log.e("geoPointS", "location changed: lat="+String.valueOf(location.getLatitude())+", lon="+String.valueOf(location.getLongitude()));
		updatePosition(location.getLatitude(), location.getLongitude());

		if(itemizedoverlay!=null) {
			mapOverlays.remove(itemizedoverlay);
		} 
		newOverlay();		
	}
	/**
	 * Disables the compass and remove updates from the location manager
	 */
	@Override
	protected void onPause() {
		compass.disableCompass();
		super.onPause();
		locationManager.removeUpdates(this);
	}
	/**
	 * Enables the compass, adds overlay and updates the users position
	 */
	@Override
	protected void onResume() {
		super.onResume();
		compass.enableCompass();
		newOverlay();
		locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, false), 1*1000, 1, this);        
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
	}

	public void onProviderDisabled(String arg0) {
		Log.e("geoPointS", "provider disabled " + arg0);
	}
	public void onProviderEnabled(String arg0) {
		Log.e("geoPointS", "provider enabled " + arg0);
	}
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		Log.e("geoPointS", "status changed to " + arg0 + " [" + arg1 + "]");
	}
	/**
	 * Initialize the contents of the Activity's standard options menu
	 *
	 * @param menu	a menu object
	 * @return true
	 */
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
	/**
	 * Redirects the user to ChatActivity.java
	 *
	 * @param view	a view of the text model
	 */
	public void toChatActivity(View view){
		Intent intentToRedirect = new Intent(this, ChatActivity.class);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}
	/**
	 * Redirects the user to NearbyConversationsActivity.java
	 *
	 * @param view	a view of the text model
	 */
	public void toNearbyConversationsActivity(View view){
		Intent intentToRedirect = new Intent(this, NearbyConversationsActivity.class);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}
	/**
	 * Redirects the user to GpsACtivity.java
	 *
	 * @param view	a view of the text model
	 */
	public void toGpsActivity(View view){
		Intent intentToRedirect = new Intent(this, GpsActivity.class);
		startActivity(intentToRedirect);
		overridePendingTransition(0, 0);
	}
	/**
	 * Redirects the user to SettingsActivity.java
	 *
	 * @param view	a view of the text model
	 */
	public void toSettingsActivity(View view){
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(0, 0);
	} 
}
