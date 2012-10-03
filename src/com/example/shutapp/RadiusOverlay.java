package com.example.shutapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class RadiusOverlay extends Overlay {
	
	private Context context;
	private double latitude;
	private double longitude;
	private float radius;
	
	public RadiusOverlay(Context context, double latitude, double longitude, float radius ) {
		super();
		this.context = context;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		
	}
	
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		
		Point point = new Point();
		GeoPoint geoPoint = new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
		
		Projection projection = mapView.getProjection();
		projection.toPixels(geoPoint, point);
		float projectedRadius = projection.metersToEquatorPixels(radius);
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setARGB(100, 100, 100, 100);
		
		canvas.drawCircle((float)point.x, (float)point.y, projectedRadius, paint);
	}

}
