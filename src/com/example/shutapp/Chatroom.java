package com.example.shutapp;

import java.util.List;

import android.location.Location;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.util.Log;

//An object of this class represents a chatroom
public class Chatroom {
	
	private final String CHATROOM_NAME;
	private List<User> members;
	
	//The representation of this objects gps location
	private Location gps_location;
	
	
	public Chatroom(String name){
		members = new ArrayList<User>();
		CHATROOM_NAME = name;
		
		//When a chatroom is initialized, it's added to Chatrooms
		Chatrooms.add(CHATROOM_NAME, this);
	}
	
	public String getName(){
		return CHATROOM_NAME;
	}
	
	public boolean addMember(User user) {
		return members.add(user);
	}
	
	public boolean removeMember(User user) {
		return members.remove(user);
	}
	
	public void setLocation(Location loc) {
		gps_location = loc;
	}
	
	public Location getLocation(){
		return gps_location;
	}
	
	//saves a message (written in this chatroom) to this
	//chatroom's private txt file
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
	
	//returns the last message written in this chatroom
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
