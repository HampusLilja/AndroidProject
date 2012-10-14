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
package com.example.shutapp.test;

import junit.framework.Assert;
import junit.framework.TestCase;
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
import android.graphics.drawable.Drawable;

import com.example.shutapp.*;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
/*
public class MapOverlayTest extends TestCase{
	private MapOverlay mo;
	private OverlayItem oi;
	
	protected void setUp() {
		Drawable drawable = null; 
		mo = new MapOverlay(drawable);  //THIS Needs changing, nullpointer
							
		GeoPoint gp = new GeoPoint(13,2);
		oi = new OverlayItem(gp, "Test", "test");
		mo.addOverlay(oi);
	    }
	
	public void testAddOverlay(){
		Assert.assertEquals(oi, mo.getItem(0));
	}
	public void  testSize(){
		Assert.assertEquals(1, mo.size());
	}
	
}
*/