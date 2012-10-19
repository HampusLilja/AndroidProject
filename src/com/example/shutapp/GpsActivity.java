/*
 ** Copyright (C)  2012  Tomas Arnesson, Hampus Lilja, Mattias Herzog & Mathias Dosé
 ** Permission is granted to copy, distribute and/or modify this document
 ** under the terms of the GNU Free Documentation License, Version 1.3
 ** or any later version published by the Free Software Foundation;
 ** with no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts.
 ** A copy of the license is included in the section entitled "LICENSE.txt".
 */
/*
** This file is part of ShutApp.
**
** ShutApp is free software: you can redistribute it and/or modify
** it under the terms of the GNU General Public License as published by
** the Free Software Foundation, either version 3 of the License, or
** (at your option) any later version.
**
** ShutApp is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
** GNU General Public License for more details.
**
** You should have received a copy of the GNU General Public License
** along with ShutApp.  If not, see <http://www.gnu.org/licenses/>.
*/
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
	
	private GeoPoint currentGeoPoint = null;
	private Location currentLocation = new Location("current");
	
	private String geoP = "geoPointS";
	
	private MapView mapView;
	private MapController mapControl;
	private MyLocationOverlay compass;
	private MapOverlay itemizedoverlay;
	private List<Overlay> mapOverlays;

	private Drawable drawableArrow;
	private LocationManager locationManager;

	private Criteria criteria;
	
	private DatabaseHandler db;
	
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

		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);   //true = being able to zoom with buttons
		mapView.getController().setZoom(StringLiterals.ZOOM_LEVEL); //sets zoomlevel from the startup, 1 to 21

		
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
			Log.e(geoP,"Unable to get startlocation");
			updatePosition(StringLiterals.START_LATITUDE, StringLiterals.START_LONGITUDE);
		}
		db = new DatabaseHandler(this);

		drawAllCircles();

	}

	private void updatePosition(double latitude, double longitude) {
		currentLocation.setLatitude(latitude);
		currentLocation.setLongitude(longitude);
		currentGeoPoint = new GeoPoint((int)(latitude*StringLiterals.LOCATION_TO_GEOPOINT_CONVERTER), 
				(int)(longitude*StringLiterals.LOCATION_TO_GEOPOINT_CONVERTER)); //converting to micro-degrees with 1E6
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
		RadiusOverlay radiusOverlay;
		
		radiusOverlay = new RadiusOverlay(latitude, longitude, StringLiterals.RADIUS, nearby);
		mapOverlays.add(radiusOverlay);
	}
	
	private void drawAllCircles() {
		try{
			List<Chatroom> chatRooms = db.getAllChatrooms();
			for(Chatroom room : chatRooms) {
				Location chatRoomLocation = room.getLocation();
			
				boolean nearby = inRangeOfChatRoom(currentLocation, chatRoomLocation);
				if(Settings.allChatRoomsDisplayed()){
					setShadedCircleOnLocation(room.getLocation().getLatitude(), 
							room.getLocation().getLongitude(), nearby);
				} else {
					if(nearby){
						setShadedCircleOnLocation(room.getLocation().getLatitude(), 
								room.getLocation().getLongitude(), nearby);
					}
				}
				
			}
		} catch(Exception e) {
			String exception = e.toString();
			Log.e("TAG", "No Chat Rooms to print on map" + exception);
		}
	}
	
	private boolean inRangeOfChatRoom(Location myLocation, Location chatRoomLocation) {
		float dist = (StringLiterals.RADIUS/2 - myLocation.distanceTo(chatRoomLocation));
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
		Log.e(geoP, "location changed: lat="+ location.getLatitude()+", lon=" + location.getLongitude());
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
		mapView.setSatellite(Settings.isSatellite());
		compass.enableCompass();
		newOverlay();
		locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, false), 
				StringLiterals.LOCATION_UPDATE_INTERVALL, 0, this);        
	}

	public void onProviderDisabled(String arg0) {
		Log.e(geoP, "provider disabled " + arg0);
	}
	public void onProviderEnabled(String arg0) {
		Log.e(geoP, "provider enabled " + arg0);
	}
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		Log.e(geoP, "status changed to " + arg0 + " [" + arg1 + "]");
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
