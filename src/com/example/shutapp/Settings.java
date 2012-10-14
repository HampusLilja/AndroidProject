/*
 ** Copyright (C)  2012  Tomas Arnesson, Hampus Lilja, Mattias Herzog & Mathias Dos�
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

import android.content.Context;

/**
 * This class is a static representation of the
 * settings avaible in the application.
 */
public abstract class Settings {
	private static String nickname;
	
	/**
	 * Initiates the settingsfile for first time users.
	 * @param ctx
	 */
	public static void initiateSettingsFile(Context ctx){
		Parser.initiateFile(StringLiterals.FILENAME_SETTINGS, ctx);
		Parser.writeAtIndex(StringLiterals.NICKNAME_INDEX, StringLiterals.STANDARD_NICKNAME,
				StringLiterals.FILENAME_SETTINGS, ctx);
		setNickname(StringLiterals.STANDARD_NICKNAME, ctx);
	}
	/**
	 * Set the nick name
	 *
	 * @param name 	a string with the new nick name
	 */
	public static void setNickname(String name, Context ctx){
		nickname = name;
		Parser.writeAtIndex(StringLiterals.NICKNAME_INDEX, name, StringLiterals.FILENAME_SETTINGS, ctx);

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
