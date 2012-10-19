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

public abstract class StringLiterals {
	private static final String ACTION = "action";
	private static final int FIFTY = 50;
	
	public static final int LINE_BUFFER = 25;
	public static final int DATABASE_BUFFER = FIFTY;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int KILO_BYTE = 1024;
	public static final String EMPTY_STRING = "";
	
	public static final int CHATROOM_MESSAGE_TYPE = 1;
	public static final int CREATE_CHATROOM_MESSAGE_TYPE = 2;
	public static final int JOIN_CHATROOM_MESSAGE_TYPE = THREE;
	public static final int LEAVE_CHATROOM_MESSAGE_TYPE = FOUR;
	
	public static final int FULL_COLOR = 100;
	public static final int HALF_COLOR = FIFTY;
	
	public static final String HTTP_CHATROOM_MESSAGE_VAR_ACTION = ACTION;
	public static final String HTTP_CHATROOM_MESSAGE_VALUE_ACTION = "chatRoomMsg";
	public static final String HTTP_CHATROOM_MESSAGE_VAR_CHATROOM = "chatRoom";
	public static final String HTTP_CHATROOM_MESSAGE_VAR_USER = "user";
	public static final String HTTP_CHATROOM_MESSAGE_VAR_MESSAGE = "Message";
	
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_ACTION = ACTION;
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VALUE_ACTION = "createNewChatRoom";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_NAME = "NAME";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_MEMBERS = "MEMBERS";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_LAT = "LAT";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_LONG = "LONG";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_RADIUS = "RADIUS";
	
	public static final String HTTP_JOIN_CHATROOM_MESSAGE_VAR_ACTION = ACTION;
	public static final String HTTP_JOIN_CHATROOM_MESSAGE_VALUE_ACTION = "joinChatroom";
	public static final String HTTP_JOIN_CHATROOM_MESSAGE_VAR_NAME = "name";
	public static final String HTTP_JOIN_CHATROOM_MESSAGE_VAR_REGID = "regid";
	
	public static final String HTTP_LEAVE_CHATROOM_MESSAGE_VAR_ACTION = ACTION;
	public static final String HTTP_LEAVE_CHATROOM_MESSAGE_VALUE_ACTION = "leaveChatroom";
	public static final String HTTP_LEAVE_CHATROOM_MESSAGE_VAR_NAME = "name";
	public static final String HTTP_LEAVE_CHATROOM_MESSAGE_VAR_REGID = "regid";
	
	public static final String SERVER_ADRESS = "http://109.225.112.99:8084/GCM_Server/GCM";
	public static final String SERVER_DB_URL = "http://109.225.112.99:8084/GCM_Server/shutappdb_v3.db";
	
	public static final String SENDER_ID = "AIzaSyAGbXeI9lkNaqDp_oFove5dmUNqEsD5FOA";
	public static final String PROJECT_ID = "971948008542";
	
	public static final int NICKNAME_INDEX = 0;
	public static final int SATELLITE_INDEX = 1;
	
	public static final String STANDARD_NICKNAME = "Anonymous";
	
	public static final String FILENAME_SETTINGS = "SETTINGS";
	
	public static final double LOCATION_TO_GEOPOINT_CONVERTER = 1E6;
	public static final double START_LATITUDE = 57.691469;
	public static final double START_LONGITUDE = 11.977469;
	public static final int ZOOM_LEVEL = 17;
	public static final float RADIUS = 1000;
	
	public static final int LOCATION_UPDATE_INTERVALL = 5000;
			
	public static final boolean SATELLITE = false;

	

	

	

	
	
	
	
}
