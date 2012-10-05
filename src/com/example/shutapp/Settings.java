package com.example.shutapp;

public abstract class Settings {
	private static String nickname="";
	
	/**
	 * Set the nick name
	 *
	 * @param name 	a string with the new nick name
	 */
	public static void setNickname(String name){
		nickname = name;
	}
	/**
	 * Get the nick name
	 *
	 * @return nickname 	a string with the current nick name
	 */
	public static String getNickname(){
		return nickname;
	}
}
