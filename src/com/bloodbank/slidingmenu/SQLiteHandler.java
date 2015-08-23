package com.bloodbank.slidingmenu;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHandler extends SQLiteOpenHelper {
	 
    private static final String TAG = SQLiteHandler.class.getSimpleName();
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "bloodbank";
 
    
    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
           String CREATE_LOGIN_TABLE ="CREATE TABLE userinfo ("+
        		  "id varchar(22) ,"+
        		  "name varchar(128) ,"+
        		  "email varchar(80) ,"+
        		  "gender varchar(8) ,"+
        		  "bgroup varchar(8) ,"+
        		  "phone varchar(32) ,"+
        		  "country varchar(200) ,"+
        		  "state varchar(128) ,"+
        		  "city varchar(128) ,"+
        		  "address varchar(512) ,"+
        		  "candonate varchar(10) ,"+
        		  "description varchar(1024) ,"+
        		  "weight varchar(16) ,"+
        		  "age varchar(10) ,"+
        		  "deviceid text"+
        		  "latitude text ,"+
        		  "longitude text )";

        Log.d(TAG, CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_LOGIN_TABLE);
 
        Log.d(TAG, "Database tables created");
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS userinfo");
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String id, String name, String email,String gender
    		,String bgroup,String phone,String country,String state ,
    		String city,String address,String candonate,String description,String weight,String age,String deviceid,String latitude,String longitude
    		,String createdDate,String modifiedDate) {
    	
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        
        values.put("id", id); // Email
        values.put("name", name);
        values.put("email", email);
        values.put("gender", gender);
        values.put("bgroup", bgroup);
        values.put("phone", phone);
        values.put("country", country);
        values.put("state", state);
        values.put("city", city);
        values.put("address", address);
        values.put("candonate", candonate);
        values.put("description", description);
        values.put("weight", weight);
        values.put("age", age);
        values.put("deviceid", deviceid);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        // Inserting Row
        long sql_id = db.insert("userinfo", null, values);
        db.close(); // Closing database connection
 
        Log.d(TAG, "New user inserted into sqlite: " + sql_id);
    }
 
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM userinfo";
 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("id", cursor.getString(0));
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("gender", cursor.getString(3));
            user.put("bgroup", cursor.getString(4));
            user.put("phone", cursor.getString(5));
            user.put("country", cursor.getString(6));
            user.put("state", cursor.getString(7));
            user.put("city", cursor.getString(8));
            user.put("address", cursor.getString(9));
            user.put("candonate", cursor.getString(10));
            user.put("description", cursor.getString(11));
            user.put("weight", cursor.getString(12));
            user.put("age", cursor.getString(13));
            user.put("deviceid", cursor.getString(14));
            user.put("latitude", cursor.getString(15));
            user.put("longitude", cursor.getString(16));
           
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
 
        return user;
    }
 
    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM userinfo";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
 
        // return row count
        return rowCount;
    }
 
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        if(db!=null)
        {
        	
        
        	db.delete("userinfo", null, null);
        	db.close();
        }
 
        Log.d(TAG, "Deleted all user info from sqlite");
    }
 
}