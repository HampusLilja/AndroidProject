package com.example.shutapp;

public abstract class Settings {
	private static String nickname;
	
	
	public static void setNickname(String name){
		nickname = name;
	}
	public static String getNickname(){
		return nickname;
	}
}
