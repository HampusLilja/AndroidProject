package com.example.shutapp;

import java.util.Collection;
import java.util.HashMap;

import java.util.Map;

//We use this class to store the chatrooms, and make them
//reachable throughout our project
public abstract class Chatrooms {

	private static Map<String, Chatroom> crMap;
	/**
	 * Add a name and chatroom to a list of rooms
	 *
	 * @param name	the name of the room
	 * @param cr	the chat room
	 */
	public static void add(String name, Chatroom cr){
		if(crMap == null){
			crMap = new HashMap<String, Chatroom>();
		}
		crMap.put(name, cr);
	}
	/**
	 * Get a chatroom based on the name
	 * 
	 * @param name	the name of the room
	 * @return chatroom the chatroom containing the name from @param
	 */
	public static Chatroom getByName(String name){
		return crMap.get(name);
	}
	/**
	 * Get all the chat rooms
	 *
	 * @return crMap.values() all the chat rooms
	 */
	public static Collection<Chatroom> getAll(){
		return crMap.values();
	}
}
