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
	public Chatroom(String name, Location loc){
		members = new ArrayList<User>();
		CHATROOM_NAME = name;
		gps_location = loc;
		//When a chatroom is initialized, it's added to Chatrooms
		Chatrooms.add(CHATROOM_NAME, this);
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
		FileOutputStream fos;
		try {
			fos = ctx.openFileOutput(CHATROOM_NAME, Context.MODE_PRIVATE);
			fos.write(msg.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
	           e.printStackTrace();
	    }catch(IOException e){
	            e.printStackTrace();
	    }
	}
	
	/**
	 * Get the last sent message
	 *
	 * @param ctx a list of properties
	 * @return theConv a String of the last message("Error Occurred" if no message was able to be saved) 
	 */
	public String getLastMessage(Context ctx){
		String theConv = "Error Occurred";
		try {
			InputStream is = ctx.openFileInput(CHATROOM_NAME);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(is));
			theConv = bfr.readLine();
		} catch (FileNotFoundException e) {
			Log.d("Chatroom" , "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e){
			Log.d("Chatroom" , "IOException");
			e.printStackTrace();
		}
		return theConv;

	}
	
	
}
