package com.example.shutapp;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public abstract class Parser {
	
	public static void write(String text, String filename, Context ctx){
		FileOutputStream fos;
		String temp = readAll(filename, ctx) + text + "\n";
		//String temp = text + "\n";
		try {
			fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(temp.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
	           e.printStackTrace();
	    }catch(IOException e){
	            e.printStackTrace();
	    }
	}
	
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
	
	public static void clean(String filename, Context ctx){
		FileOutputStream fos;
		String temp = "";
		try {
			fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(temp.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
	           e.printStackTrace();
	    }catch(IOException e){
	            e.printStackTrace();
	    }
	}
	
	public static String readFirst(String filename, Context ctx){
		String firstLine = "";
		try {
			InputStream is = ctx.openFileInput(filename);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(is));
			firstLine = bfr.readLine();
		} catch (FileNotFoundException e) {
			Log.d("Chatroom" , "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e){
			Log.d("Chatroom" , "IOException");
			e.printStackTrace();
		}
		return firstLine;
	}
	
	public static String readAtIndex(int index, String filename, Context ctx){
		//index++;
		String tempLine = "";
		try {
			InputStream is = ctx.openFileInput(filename);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(is));
			
			for(int i=0; i<=index; i++){
				tempLine = bfr.readLine();
				//Log.d("readAtIndex" , tempLine);
			}
		} catch (FileNotFoundException e) {
			Log.d("Chatroom" , "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e){
			Log.d("Chatroom" , "IOException");
			e.printStackTrace();
		}
		return tempLine;
	}
	
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
			Log.d("Chatroom" , "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e){
			Log.d("Chatroom" , "IOException");
			e.printStackTrace();
		}
		return allText;
	}

}