package com.example.bhuvaneshvar.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDBHelper extends SQLiteOpenHelper
{

    public StudentDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    // db is referencing to created db when created object(that created db)
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table STUDENT(ID integer primary key autoincrement , NAME text , ROLLNUMBER integer unique, EMAIL text , MOBILE integer);");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists STUDENT");
        onCreate(db);
    }
}
