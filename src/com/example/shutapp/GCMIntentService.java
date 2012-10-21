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

/** this class should handle all GCM messaging (Intents) */
package com.example.shutapp;

import com.google.android.gcm.GCMBaseIntentService;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * A class to handle the GCM Intent.
 * @author Group12
 *
 */
public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

	/**
	 * Constructor for GCMIntentService.
	 */
	public GCMIntentService() {
		super(StringLiterals.SENDER_ID);
		Log.d(TAG, "GCMIntentService initiated");
	}
	/**
	 * If an error occurs.
	 *
	 * @param ctx		a list of properties
	 * @param error 	A string that describes the error
	 */ 
	@Override
	protected void onError(Context ctx, String error) {
		Log.d(TAG, "Error: " + error);

	}

	/**
	 * Sends a message.
	 *
	 * @param ctx 		a list of properties
	 * @param intent	an abstract description of an operation to be performed
	 */
	@Override
	protected void onMessage(Context ctx, Intent intent) {
		Log.d(TAG, "Message has been received");
		String message = intent.getStringExtra("message");
		sendGCMIntent(ctx, message);

	}

	/**
	 * Send the GCM Intent.
	 * @param ctx
	 * @param message
	 */
	private void sendGCMIntent(Context ctx, String message) {
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction("GCM_RECEIVED_ACTION");
		broadcastIntent.putExtra("gcm", message);
		ctx.sendBroadcast(broadcastIntent);

	}

	/**
	 * Sends regId to the server.
	 *
	 * @param ctx		a list of properties
	 * @param regId		a string containing the registration id
	 */ 
	@Override
	protected void onRegistered(Context ctx, String regId) {
		// send regId to your server
		Log.d(TAG, regId);
		Settings.setREGID(regId);

	}

	/**
	 * add regId to the server.
	 *
	 * @param ctx 		a list of properties
	 * @param regId		a string containing the registration id
	 */
	@Override
	protected void onUnregistered(Context ctx, String regId) {
		// send to server to remove that regId

	}

}
