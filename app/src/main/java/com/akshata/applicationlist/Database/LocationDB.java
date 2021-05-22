package com.akshata.applicationlist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationDB extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "File_name.db"; // just use a name.


    public LocationDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_LIST = "main_list";
    public static final String TABLE_LIST_ID = "id";
    public static final String TABLE_LIST_NAME = "name";

    public static final String TABLE_CHILD_LIST = "child_list";
    public static final String TABLE_CHILD_LIST_ID = "id";
    public static final String TABLE_PARENT_LIST_ID = "parent_id";
    public static final String TABLE_CHILD_LIST_NAME = "name";

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MAIN_LIST_TABLE = "CREATE TABLE " + TABLE_LIST + "("
                + TABLE_LIST_ID + " INTEGER PRIMARY KEY,"
                + TABLE_LIST_NAME + " TEXT" + ")";
        String CREATE_TABLE_CHILD_LIST = "CREATE TABLE " + TABLE_CHILD_LIST + "("
                + TABLE_CHILD_LIST_ID + " INTEGER PRIMARY KEY,"
                + TABLE_PARENT_LIST_ID + " INTEGER,"
                + TABLE_CHILD_LIST_NAME + " TEXT" + ")";

        db.execSQL(CREATE_MAIN_LIST_TABLE);
        db.execSQL(CREATE_TABLE_CHILD_LIST);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHILD_LIST);

        onCreate(db);
    }


    public void addListItem(String   item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_LIST_NAME, item);
        db.insert(TABLE_LIST, null, values);
        db.close();
    }

    public long addChildListItem(String activity, String packagename) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_CHILD_LIST_NAME, activity);
        values.put(TABLE_PARENT_LIST_ID, packagename);
        db.insert(TABLE_LIST, null, values);
        db.close();
        return 0;
    }

    public void deleteParentItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIST, TABLE_LIST_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteChildItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHILD_LIST, TABLE_CHILD_LIST_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }



//    public List<ParentListItem> getAllParentListItem() {
//        List<ParentListItem> result = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_LIST, null, null, null, null, null, null);
//
//        while (cursor.moveToNext()) {
//            ParentListItem item = new ParentListItem(TABLE_LIST);
//            item.setName(getStringByColumName(cursor, TABLE_CHILD_LIST_ID));
//            item.setName(getStringByColumName(cursor, TABLE_LIST_NAME));
//            result.add(item);
//        }
//        cursor.close();
//        db.close();
//        return result;
//    }



    public int getIntByColumName(Cursor cursor, String tableColumn) {
        return cursor.getInt(cursor.getColumnIndex(tableColumn));
    }


    public double getDoubleByColumName(Cursor cursor, String tableColumn) {
        return cursor.getDouble(cursor.getColumnIndex(tableColumn));
    }


    public String getStringByColumName(Cursor cursor, String tableColumn) {
        return cursor.getString(cursor.getColumnIndex(tableColumn));
    }


}
