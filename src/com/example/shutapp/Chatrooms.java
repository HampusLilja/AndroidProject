package com.example.shutapp;

import java.util.Collection;
import java.util.HashMap;

import java.util.Map;

//We use this class to store the chatrooms, and make them
//reachable throughout our project
public abstract class Chatrooms {

	private static Map<String, Chatroom> crMap;
	
	public static void add(String name, Chatroom cr){
		if(crMap == null){
			crMap = new HashMap<String, Chatroom>();
		}
		crMap.put(name, cr);
	}
	
	public static Chatroom getByName(String name){
		return crMap.get(name);
	}
	
	public static Collection<Chatroom> getAll(){
		return crMap.values();
	}
}
