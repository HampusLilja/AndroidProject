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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.util.Log;

/**
 * This class is used for writing/reading
 * to/from the textfiles surrounding this
 * application.
 *
 */
public abstract class Parser {

	private static String chatR = "ChatRoom";
	private static String ioE = "IOException";
	private static String fnfE = "FileNotFoundException";


	/**
	 * Creates an empty file.
	 * @param filename
	 * @param ctx
	 */
	public static void initiateFile(String filename, Context ctx){
		FileOutputStream fos;
		String temp = "";
		try {
			fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(temp.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			String exception = e.toString();
			Log.d(chatR , fnfE + exception);
		}catch(IOException e){
			String exception = e.toString();
			Log.d(chatR , ioE + exception);
		}
	}
	/**
	 * This method writes text to a new line
	 * at the end of wished file.
	 * @param text The text to be written
	 * @param filename Target filename
	 * @param ctx Context of called activity
	 */
	public static void write(String text, String filename, Context ctx){
		FileOutputStream fos;
		String temp = readAll(filename, ctx) + text + "\n";
		try {
			fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(temp.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			String exception = e.toString();
			Log.d(chatR , fnfE + exception);
		}catch(IOException e){
			String exception = e.toString();
			Log.d(chatR , ioE + exception);
		}
	}

	/**
	 * Writes a text to the specified line in wished file.
	 * @param index Index of the line to be changed.
	 * @param text The text to be written.
	 * @param filename Target filename.
	 * @param ctx Context of called activity.
	 */
	public  static void writeAtIndex(int index, String text, String filename, Context ctx){
		String temp = readAll(filename, ctx);
		String[] textLines = temp.split("\n");
		textLines[index] = text;
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i<textLines.length; i++){
			textLines[i] = textLines[i] + "\n";
			sb.append(textLines[i]);
		}
		clean(filename, ctx);
		write(sb.toString(), filename, ctx);

	}

	/**
	 * Cleans wished file for all data.
	 * @param filename Target filename.
	 * @param ctx Context of called activity
	 */
	public static void clean(String filename, Context ctx){
		FileOutputStream fos;
		String temp = "";
		try {
			fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(temp.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			String exception = e.toString();
			Log.d(chatR , fnfE + exception);
		}catch(IOException e){
			String exception = e.toString();
			Log.d(chatR , ioE + exception);
		}
	}

	/**
	 * Reads the first line of the file.
	 * @param filename Target filename.
	 * @param ctx Context of called activity
	 * @return the first line of the file.
	 */
	public static String readFirst(String filename, Context ctx){
		String firstLine = "";
		try {
			InputStream is = ctx.openFileInput(filename);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(is));
			firstLine = bfr.readLine();
		} catch (FileNotFoundException e) {
			String exception = e.toString();
			Log.d(chatR , fnfE + exception);
		} catch (IOException e){
			String exception = e.toString();
			Log.d(chatR , ioE + exception);
		}
		return firstLine;
	}

	/**
	 * Reads the line at wished index.
	 * @param index Index of the line to be read.
	 * @param filename Target filename.
	 * @param ctx Context of called activity.
	 */
	public static String readAtIndex(int index, String filename, Context ctx){
		String tempLine = "";
		try {
			InputStream is = ctx.openFileInput(filename);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(is));

			for(int i=0; i<=index; i++){
				tempLine = bfr.readLine();
			}
		} catch (FileNotFoundException e) {
			String exception = e.toString();
			Log.d(chatR , fnfE + exception);
		} catch (IOException e){
			String exception = e.toString();
			Log.d(chatR , ioE + exception);
		}
		return tempLine;
	}

	/**
	 * Reads the whole file.
	 * @param filename Target filename.
	 * @param ctx Context of called activity.
	 * @return Returns a string representing all data in the file
	 */
	public static String readAll(String filename, Context ctx){
		String allText = "";
		try {
			InputStream is = ctx.openFileInput(filename);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(is));
			String tempLine;
			while((tempLine = bfr.readLine()) != null){
				allText += tempLine + "\n";
			}
		} catch (FileNotFoundException e) {
			String exception = e.toString();
			Log.d(chatR , fnfE + exception);
		} catch (IOException e){
			String exception = e.toString();
			Log.d(chatR , ioE + exception);
		}
		return allText;
	}

	/**
	 * Check if file exists.
	 * @param filename
	 * @param ctx
	 * @return yes or no
	 */
	public static boolean checkFileExistance(String filename, Context ctx){
		File file = ctx.getFileStreamPath(filename);
		return file.exists();
	}

}
