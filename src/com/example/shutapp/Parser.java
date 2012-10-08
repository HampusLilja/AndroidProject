package com.example.shutapp;

import java.io.*;
import java.net.*;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * This class is used for writing/reading
 * to/from the textfiles surrounding this
 * application.
 *
 */
public abstract class Parser {
	
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
	           e.printStackTrace();
	    }catch(IOException e){
	            e.printStackTrace();
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
	           e.printStackTrace();
	    }catch(IOException e){
	            e.printStackTrace();
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
			Log.d("Chatroom" , "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e){
			Log.d("Chatroom" , "IOException");
			e.printStackTrace();
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
			Log.d("Chatroom" , "FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e){
			Log.d("Chatroom" , "IOException");
			e.printStackTrace();
		}
		return allText;
	}
	public static void updateLocalDB(){
		try {
            URL url = new URL(StringLiterals.SERVER_DB_URL);
            URLConnection connection = url.openConnection();
            connection.connect();

            // Downloading the latest DB
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream("/sdcard/shutappdb");

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
        }
	}

}
