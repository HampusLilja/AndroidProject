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

import java.util.Collection;
import java.util.HashMap;

import java.util.Map;

//We use this class to store the chatrooms, and make them
//reachable throughout our project
public abstract class Chatrooms {
	
	private static Chatroom currentChatroom;
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
	
	/**
	 * Sets a new current chatroom
	 * @param cr The new current chatroom
	 * 
	 */
	public static void setCurrentChatroom(Chatroom cr){
		currentChatroom = cr;
	}
	
	/**
	 * @return the current chatroom
	 */
	public static Chatroom getCurrentChatroom(){
		return currentChatroom;
	}
}
