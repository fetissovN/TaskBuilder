package com.example.nick.taskbuilder.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    static final int VERSION = 1;
    static final String DB_NAME = "tasks";
    static final String DB_TABLE_NAME = "all_tasks";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +DB_TABLE_NAME + "("
                + "_id integer primary key autoincrement,"
                + "name text,"
                + "description text,"
                + "starts integer,"
                + "expires integer,"
                + "priority text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
