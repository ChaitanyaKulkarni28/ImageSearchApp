package com.example.imagesearchapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper{
    public static final String DB_NAME = "comment.db";
    public static final String TABLE_NAME = "comments";

    public static final String COL_1 = "id";
    public static final String COL_2 = "comm";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (id text, comm text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean onInsert(String id, String comm){
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, comm);
        long v = db1.insert(TABLE_NAME, null, contentValues);
        if (v != -1){
            return true;
        }
        else {
            return false;
        }
    }

    public Cursor getAllData(String comm_id) {
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor res =  db1.rawQuery( "select * from "+TABLE_NAME+" where id = '"+comm_id+"'", null );
        return res;
    }
}
