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

import android.location.Location;

import android.content.Context;

//An object of this class represents a chatroom
public class Chatroom {
	
	private String chatRoomName;
	
	//The representation of this objects gps location
	private Location gpsLocation;
	
	private double latitude;
	

	private double longitude;
	
	private float radius;
	
	public Chatroom(){
		
	}
	
	public Chatroom(String name, double latitude, double longitude, float radius){
		this.chatRoomName = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		
	}
	/**
	 * Constructor for Chatroom, add a room to Chatrooms
	 *
	 * @param name  The name of the chat room
	 * @param loc	the location of the chat room
	 */
	public Chatroom(String name, Location loc, Context ctx){
		chatRoomName = name;
		gpsLocation = loc;
		latitude = loc.getLatitude();
		longitude = loc.getLongitude();
		radius = StringLiterals.RADIUS;
		//Initiates chatlogfile
		Parser.initiateFile(chatRoomName, ctx);

		//When a chatroom is initialized, it's added to Chatrooms
		//Chatrooms.add(CHATROOM_NAME, this);
		
		//tells the server that this chatroom is created
		new HttpMessage(StringLiterals.CREATE_CHATROOM_MESSAGE_TYPE, chatRoomName, Settings.getREGID(), 
				Double.toString(gpsLocation.getLatitude()),
				Double.toString(gpsLocation.getLongitude()), 
				"1000");	
	}
	
	/**
	 * Get the name of the room
	 */
	public String getName(){
		return chatRoomName;
	}
	
	public void setName(String name){
		chatRoomName = name;
	}

	/**
	 * Set the location of the room
	 */
	public void setLocation(Location loc) {
		gpsLocation = loc;
	}
	/**
	 * Get the location of the room
	 * @return gps_location	the location of the room
	 */
	public Location getLocation(){
		gpsLocation = new Location("ChatroomLocation");
		gpsLocation.setLatitude(latitude);
		gpsLocation.setLongitude(longitude);
		return gpsLocation;
	}
	
	/**
	 * Saves a message to the chatroom's private txt tile
	 * @param msg	the message which is about to be saved
	 * @param ctx	a list of properties
	 */
	public void saveMessage(String msg, Context ctx){
		Parser.write(msg, chatRoomName, ctx);
	}
	
	/**
	 * Get the last sent message
	 *
	 * @param ctx a list of properties
	 * @return theConv a String of the last message("Error Occurred" if no message was able to be saved) 
	 */
	public String getLastMessage(Context ctx){
		return null;

	}
	
	public String readLog(Context ctx){
		return Parser.readAll(chatRoomName, ctx);
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	
}
