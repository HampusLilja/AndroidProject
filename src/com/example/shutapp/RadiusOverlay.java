/*
 ** Copyright (C)  2012  Tomas Arnesson, Hampus Lilja, Mattias Herzog & Mathias Dos�
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

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * A class to create the circles on the map.
 * @author Group 12
 *
 */
public class RadiusOverlay extends Overlay {

	private double latitude;
	private double longitude;
	private float radius;
	private boolean green;

	/**
	 * Constructor for RadiusOverlay.
	 *
	 * @param latitude	the latitude value
	 * @param longitude	the longitude value
	 * @param radius	how large radius the circle has	
	 * @param nearby	if the user is within the radius or not
	 */
	public RadiusOverlay(double latitude, double longitude, float radius, boolean nearby ) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.green = nearby;

	}
	/**
	 * Draw a circle formed overlay.
	 *
	 * @param canvas	a canvas
	 * @param mapView	a map view of the text model
	 * @param shadow	
	 */
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

		Point point = new Point();
		GeoPoint geoPoint = new GeoPoint((int) (latitude * 
				StringLiterals.LOCATION_TO_GEOPOINT_CONVERTER), 
				(int) (longitude * StringLiterals.LOCATION_TO_GEOPOINT_CONVERTER));

		Projection projection = mapView.getProjection();
		projection.toPixels(geoPoint, point);
		float projectedRadius = projection.metersToEquatorPixels(radius);

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		if(green){
			paint.setARGB(StringLiterals.HALF_COLOR, StringLiterals.HALF_COLOR, 
					StringLiterals.FULL_COLOR, StringLiterals.HALF_COLOR);
		}else{
			paint.setARGB(StringLiterals.HALF_COLOR, StringLiterals.FULL_COLOR, 
					StringLiterals.HALF_COLOR, StringLiterals.HALF_COLOR);
		}


		canvas.drawCircle((float)point.x, (float)point.y, projectedRadius, paint);
	}

}
