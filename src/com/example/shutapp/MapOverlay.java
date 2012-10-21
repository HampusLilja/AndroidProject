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

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to place a item on a map.
 * @author Group 12
 *
 */
@SuppressWarnings("rawtypes")
public class MapOverlay extends ItemizedOverlay{

	private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;


	/**
	 * Constructor.
	 *
	 * @param defaultMarker  "something that can be drawn."
	 */
	public MapOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}
	/**
	 * Constructor.
	 *
	 * @param defaultMarker  "something that can be drawn"
	 * @param context	a list of properties
	 */
	public MapOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}
	/**
	 * Adds overlay to list of overlays.
	 *
	 * @param overlay the overlay that is about to be added
	 */
	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	/**
	 * Show information when the overlay is tapped.
	 *
	 * @return true 
	 */
	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
	/**
	 * Create item.
	 *
	 * @return mOverlays.get(i)	an element with the requested index 	
	 */
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}
	/**
	 * The size of the array that stores overlay items.
	 *
	 * @return mOverlays.size()		amount of overlay items stored
	 */
	@Override
	public int size() {
		return mOverlays.size();
	}

}
