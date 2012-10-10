package com.example.shutapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
    
    // Database Name
    private static final String DATABASE_NAME = "chatroomsManager";
    
    // Chatrooms table name
    private static final String TABLE_CHATROOMS = "chatrooms";
    
    // Chatrooms Table Columns names
    //private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_RADIUS = "radius";
    
	
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CHATROOMS + "("
				+ KEY_NAME + " TEXT PRIMARY KEY,"
                + KEY_LATITUDE + " REAL," + KEY_LONGITUDE + " REAL," + KEY_RADIUS + " INTEGER" + ")" ;
        db.execSQL(CREATE_CONTACTS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATROOMS);
 
        // Create tables again
        onCreate(db);
		
	}
	
	public void addChatroom(Chatroom chatroom){
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, chatroom.getName()); // Chatroom Name
	    values.put(KEY_LATITUDE, chatroom.getLatitude()); // Chatroom Latitude
	    values.put(KEY_LONGITUDE, chatroom.getLongitude()); // Chatroom Longitude
	    values.put(KEY_RADIUS, chatroom.getRadius()); // Chatroom Radius
	 
	    // Inserting Row
	    db.insert(TABLE_CHATROOMS, null, values);
	    db.close(); // Closing database connection
	}
	
	public Chatroom getChatroom(String name){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_CHATROOMS, new String[] { KEY_NAME,
				KEY_LATITUDE, KEY_LONGITUDE, KEY_RADIUS }, KEY_NAME + "=?",
				new String[] { name }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		
		Chatroom chatroom = new Chatroom(cursor.getString(0),
				cursor.getDouble(1), cursor.getDouble(2),
							cursor.getInt(3));
		// return chatroom
		return chatroom;
	}

	public List<Chatroom> getAllChatrooms(){
		List<Chatroom> chatroomList = new ArrayList<Chatroom>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_CHATROOMS;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            Chatroom chatroom = new Chatroom();
	            chatroom.setName(cursor.getString(0));
	            chatroom.setLatitude(cursor.getDouble(1));
	            chatroom.setLongitude(cursor.getDouble(2));
	            chatroom.setRadius(cursor.getInt(3));
	            // Adding chatroom to list
	            chatroomList.add(chatroom);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return chatroomList;
	}
	
	public int getChatroomsCount(){
		String countQuery = "SELECT  * FROM " + TABLE_CHATROOMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
	}
	
	public int updateChatrooms(Chatroom chatroom){
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_NAME, chatroom.getName()); // Chatroom Name
	    values.put(KEY_LATITUDE, chatroom.getLatitude()); // Chatroom Latitude
	    values.put(KEY_LONGITUDE, chatroom.getLongitude()); // Chatroom Longitude
	    values.put(KEY_RADIUS, chatroom.getRadius()); // Chatroom Radius
	 
	    // updating row
	    return db.update(TABLE_CHATROOMS, values, KEY_NAME + " = ?",
	            new String[] { chatroom.getName() });
	}
	
	public void deleteContact(Chatroom chatroom){
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_CHATROOMS, KEY_NAME + " = ?",
	            new String[] { chatroom.getName() });
	    db.close();
	}
	
}
