package com.zybooks.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class InventoryDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final String TABLE_NAME = "inventory_items";
    private static final String COL_ITEM_NAME = "item_name";
    private static final String COL_QUANTITY = "quantity";

    public InventoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ITEM_NAME + " TEXT PRIMARY KEY, " +
                COL_QUANTITY + " INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add a new item to the database table
    public boolean addInventoryItem(InventoryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ITEM_NAME, item.getItemName());
        contentValues.put(COL_QUANTITY, item.getQuantity());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // Delete an item from the database table
    public boolean deleteInventoryItem(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COL_ITEM_NAME + "=?";
        String[] whereArgs = {itemName};

        int result = db.delete(TABLE_NAME, whereClause, whereArgs);

        return result > 0;
    }

    // Method Returns a list of all inventory items in DB to be used in the app display
    public List<InventoryItem> getAllInventoryItems() {
        List<InventoryItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow(COL_ITEM_NAME));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTITY));

                InventoryItem item = new InventoryItem(itemName, quantity);
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return itemList;
    }

    // Updates the item object in the database when user changes it - in this case the quantity
    public boolean updateInventoryItem(InventoryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_QUANTITY, item.getQuantity());

        String whereClause = COL_ITEM_NAME + "=?";
        String[] whereArgs = {item.getItemName()};

        int result = db.update(TABLE_NAME, contentValues, whereClause, whereArgs);

        return result > 0;
    }

    //FIXME: May not need checkInventoryItem - review for removal
    public boolean checkInventoryItem(String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_ITEM_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{itemName});

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}