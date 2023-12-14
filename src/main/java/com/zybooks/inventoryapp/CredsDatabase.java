/*
 * CS-360 Mobile Architecture & Programming - 23EW2
 * Project Three
 * Instructor: Jerome DiMarzio
 * Author: Greg Isajewicz
 */


package com.zybooks.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CredsDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_credentials.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    public CredsDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_USERNAME + " TEXT PRIMARY KEY, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_USERNAME + " = ? AND " +
                COL_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}
