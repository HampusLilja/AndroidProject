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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class RadiusOverlay extends Overlay {
	
	//private Context context;
	private double latitude;
	private double longitude;
	private float radius;
	private boolean green;
	
	/**
	 * Constructor for RadiusOverlay
	 *
	 * @param view		a view of the text model	
	 * @param latitude	the latitude value
	 * @param longitude	the longitude value
	 * @param radius	how large radius the circle has	
	 * @param nearby	if the user is within the radius or not
	 */
	public RadiusOverlay(Context context, double latitude, double longitude, float radius, boolean nearby ) {
		super();
		//this.context = context;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		green = nearby;
		
	}
	/**
	 * Draw a circle formed overlay
	 *
	 * @param canvas	a canvas
	 * @param mapView	a map view of the text model
	 * @param shadow	
	 */
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		
		Point point = new Point();
		GeoPoint geoPoint = new GeoPoint((int) (latitude * 1E6), (int) (longitude * 1E6));
		
		Projection projection = mapView.getProjection();
		projection.toPixels(geoPoint, point);
		float projectedRadius = projection.metersToEquatorPixels(radius);
		
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		if(green){
			paint.setARGB(50, 50, 100, 50);
		}else{
			paint.setARGB(50, 100, 50, 50);
		}
		
		
		canvas.drawCircle((float)point.x, (float)point.y, projectedRadius, paint);
	}

}
