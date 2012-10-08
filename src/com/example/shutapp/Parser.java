package com.example.shutapp;

import java.io.*;
import java.net.*;
import android.content.Context;
import android.os.Environment;
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
