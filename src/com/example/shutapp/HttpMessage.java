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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

/**
 * A class to handle the Http messages.
 * @author Group 12
 *
 */
public class HttpMessage implements Runnable{

	private List<NameValuePair> nameValuePairs;
	/**
	 * Constructor.
	 *
	 * @param messageType 	1 if CHATROOM_MESSAGE_TYPE, 2 if DBREGISTER_MESSAGE_TYPE
	 * @param var1			
	 * @param var2			
	 * @param var3			
	 */
	public HttpMessage(int messageType, String var1, String var2, 
			String var3, String var4, String var5){
		nameValuePairs = new ArrayList<NameValuePair>(2);
		switch(messageType){

		case StringLiterals.CHATROOM_MESSAGE_TYPE:
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_CHATROOM_MESSAGE_VAR_ACTION, 
					StringLiterals.HTTP_CHATROOM_MESSAGE_VALUE_ACTION));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_CHATROOM_MESSAGE_VAR_CHATROOM, var1));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_CHATROOM_MESSAGE_VAR_USER, var2));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_CHATROOM_MESSAGE_VAR_MESSAGE, var3));
			break;

		case StringLiterals.CREATE_CHATROOM_MESSAGE_TYPE:
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_CREATE_CHATROOM_MESSAGE_VAR_ACTION,
					StringLiterals.HTTP_CREATE_CHATROOM_MESSAGE_VALUE_ACTION));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_CREATE_CHATROOM_MESSAGE_VAR_NAME,
					var1));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_CREATE_CHATROOM_MESSAGE_VAR_LAT, var3));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_CREATE_CHATROOM_MESSAGE_VAR_LONG,
					var4));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_CREATE_CHATROOM_MESSAGE_VAR_RADIUS,
					var5));
			break;

		case StringLiterals.JOIN_CHATROOM_MESSAGE_TYPE:
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_JOIN_CHATROOM_MESSAGE_VAR_ACTION,
					StringLiterals.HTTP_JOIN_CHATROOM_MESSAGE_VALUE_ACTION));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_JOIN_CHATROOM_MESSAGE_VAR_NAME, var1));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_JOIN_CHATROOM_MESSAGE_VAR_REGID, var2));
			break;

		case StringLiterals.LEAVE_CHATROOM_MESSAGE_TYPE:
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_LEAVE_CHATROOM_MESSAGE_VAR_ACTION,
					StringLiterals.HTTP_LEAVE_CHATROOM_MESSAGE_VALUE_ACTION));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_LEAVE_CHATROOM_MESSAGE_VAR_NAME, var1));
			nameValuePairs.add(new BasicNameValuePair(
					StringLiterals.HTTP_LEAVE_CHATROOM_MESSAGE_VAR_REGID,
					var2));
			break;


		}
		new Thread(this).start();
	}

	/**
	 * Run application.
	 */
	public void run() {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(StringLiterals.SERVER_ADRESS);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			String exception = e.toString();
			Log.e("Excpetion", exception);
		} catch (IOException e) {
			String exception = e.toString();
			Log.e("Excpetion", exception);
		}

	}

}
