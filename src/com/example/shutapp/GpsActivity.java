package com.example.shutapp;

import java.util.List;
import android.os.Bundle;
import android.content.Intent;
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

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GpsActivity extends MapActivity {
	private final int zoomLevel = 17; // 1 to 21
	private GeoPoint gP;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        MapView mapView = (MapView) findViewById(R.id.mapView);
        
        mapView.setBuiltInZoomControls(true);   //true = being able to zoom with buttons
        mapView.getController().setZoom(zoomLevel); //sets zoomlevel from the startup
    
        final MapController mControl = mapView.getController();
        LocationManager locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        
     // TEMP BELOW (must have gp-value here or else FATAL?)
        double lon = 11.9670495986938;
        double lat = 57.7012596130371;
        gP = new GeoPoint((int)(lat*1E6), (int)(lon*1E6)); //converting to micro-degrees with 1E6
        mControl.animateTo(gP);
     // End Temp
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.maparrow);
        MapOverlay itemizedoverlay = new MapOverlay(drawable, this);
        
        OverlayItem overlayitem = new OverlayItem(gP, null, null);
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        LocationListener locList = new LocationListener(){

			public void onLocationChanged(Location location) {
				gP = new GeoPoint((int)location.getLatitude(), (int)location.getLongitude());
        		mControl.animateTo(gP); //animateTo or setCenter
				
			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
        };
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locList); //TEMP! 0 = min.time ,0 = min.distance
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
    
    public void toSettingsActivity(View view){
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    	overridePendingTransition(0, 0);
    } 
}
