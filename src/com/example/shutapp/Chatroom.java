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

import android.location.Location;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


import android.content.Context;
import android.util.Log;

//An object of this class represents a chatroom
public class Chatroom {
	
	private final String CHATROOM_NAME;
	private List<User> members;
	
	//The representation of this objects gps location
	private Location gps_location;
	
	/**
	 * Constructor for Chatroom, add a room to Chatrooms
	 *
	 * @param name  The name of the chat room
	 * @param loc	the location of the chat room
	 */
	public Chatroom(String name, Location loc, Context ctx){
		members = new ArrayList<User>();
		CHATROOM_NAME = name;
		gps_location = loc;
		//Initiates chatlogfile
		Parser.initiateFile(CHATROOM_NAME, ctx);
		Parser.write(StringLiterals.EMPTY_STRING, CHATROOM_NAME, ctx);
		//When a chatroom is initialized, it's added to Chatrooms
		Chatrooms.add(CHATROOM_NAME, this);
		//tells the server that this chatroom is created
		Log.d("chatroom", MiscResources.REGID);
		new HttpMessage(StringLiterals.CREATE_CHATROOM_MESSAGE_TYPE, CHATROOM_NAME, MiscResources.REGID, 
				Double.toString(gps_location.getLatitude()),
				Double.toString(gps_location.getLongitude()), 
				"1000");
	}
	
	/**
	 * Get the name of the room
	 */
	public String getName(){
		return CHATROOM_NAME;
	}
	/**
	 * Add a member to the room
	 *
	 * @param user The user who is about to be added
	 * @return true if the add is successful, otherwise false
	 */
	public boolean addMember(User user) {
		return members.add(user);
	}
	/**
	 * Remove a member from the room
	 *
	 * @param user The user who is about to be removed
	 * @return true if the remove is successful, otherwise false
	 */
	public boolean removeMember(User user) {
		return members.remove(user);
	}
	/**
	 * Set the location of the room
	 */
	public void setLocation(Location loc) {
		gps_location = loc;
	}
	/**
	 * Get the location of the room
	 * @return gps_location	the location of the room
	 */
	public Location getLocation(){
		return gps_location;
	}
	
	/**
	 * Saves a message to the chatroom's private txt tile
	 * @param msg	the message which is about to be saved
	 * @param ctx	a list of properties
	 */
	public void saveMessage(String msg, Context ctx){
		Parser.write(msg, CHATROOM_NAME, ctx);
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
		return Parser.readAll(CHATROOM_NAME, ctx);
	}
	
	
}
