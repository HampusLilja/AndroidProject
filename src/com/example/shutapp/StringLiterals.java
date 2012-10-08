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
	
	public static final String EMPTY_STRING = "";
	
	public static final int CHATROOM_MESSAGE_TYPE = 1;
	public static final int DBREGISTER_MESSAGE_TYPE = 2;
	public static final int CREATE_CHATROOM_MESSAGE_TYPE = 3;
	
	public static final String HTTP_CHATROOM_MESSAGE_VAR_ACTION = "action";
	public static final String HTTP_CHATROOM_MESSAGE_VALUE_ACTION = "chatRoomMsg";
	public static final String HTTP_CHATROOM_MESSAGE_VAR_CHATROOM = "chatRoom";
	public static final String HTTP_CHATROOM_MESSAGE_VAR_USER = "user";
	public static final String HTTP_CHATROOM_MESSAGE_VAR_MESSAGE = "Message";
	
	public static final String HTTP_DBREGISTER_MESSAGE_VAR_ACTION = "action";
	public static final String HTTP_DBREGISTER_MESSAGE_VALUE_ACTION = "addtodb";
	public static final String HTTP_DBREGISTER_MESSAGE_VAR_NICK = "DBNICK";
	public static final String HTTP_DBREGISTER_MESSAGE_VAR_REGID = "DBREGID";
	
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_ACTION = "action";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VALUE_ACTION = "createNewChatRoom";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_NAME = "NAME";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_MEMBERS = "MEMBERS";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_LAT = "LAT";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_LONG = "LONG";
	public static final String HTTP_CREATE_CHATROOM_MESSAGE_VAR_RADIUS = "RADIUS";
	
	
	
	public static final String SERVER_ADRESS = "http://109.225.112.99:8084/GCM_Server/GCM";
	public static final String SERVER_DB_URL = "http://109.225.112.99:8084/GCM_Server/shutappdb";
	public static final int NICKNAME_CASE = 0;
	
	public static final int NICKNAME_INDEX = 0;
	
	public static final String STANDARD_NICKNAME = "Anonymous";
	
	public static final String FILENAME_SETTINGS = "SETTINGS";
	
	
}
