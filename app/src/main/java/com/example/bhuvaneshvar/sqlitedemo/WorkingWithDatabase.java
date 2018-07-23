package com.example.bhuvaneshvar.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class WorkingWithDatabase
{
    public static final String DATABASE_NAME = "myCollegeFriendsInfo.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "STUDENT";

    public SQLiteDatabase db;

    private Context context;
    private StudentDBHelper studentDBHelper;

    public WorkingWithDatabase(Context _Context)
    {
        context = _Context;
        studentDBHelper = new StudentDBHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //to open database
    public WorkingWithDatabase open() throws SQLException
    {
        db = studentDBHelper.getWritableDatabase();
        return this;
    }

    //to close database
    public void close()
    {
        db.close();
    }

    //to get db instance
    public SQLiteDatabase getDataBaseInstance()
    {
        return db;
    }


    //to insert record
    public void insertIntry(Data_is_here studentData)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",studentData.name);
        contentValues.put("ROLLNUMBER",studentData.rollNumber);
        contentValues.put("EMAIL",studentData.Email);
        contentValues.put("MOBILE",studentData.Mobile);

        //now insert these record

        db.insert(TABLE_NAME,null,contentValues);
        Toast.makeText(context,"ADDED",Toast.LENGTH_LONG).show();

    }


    //to fetch data
    public Cursor getAllFromDataBase()
    {
        return db.query(TABLE_NAME,null,null,null,null,null,null);
    }



    //to delete data by RollNumber
    public void  delete(String[] itemToDelete)
    {
        String qry = "ROLLNUMBER =?";
        long dataToDelete = Long.parseLong(itemToDelete[0]);

        int chkifDel = db.delete(TABLE_NAME,qry, new String[]{String.valueOf(dataToDelete)});
        if (!(chkifDel<0))
        {
            Toast.makeText(context,"DELETED",Toast.LENGTH_LONG).show();

        }
        else
            Toast.makeText(context,"SOMETHING WENT WRONG",Toast.LENGTH_SHORT).show();
    }


    //update query
    public void updateDatabse(ContentValues values , String OLDROLLNUMBER)
    {
        String qry = "ROLLNUMBER = ?";
       int a =  db.update("STUDENT",values,qry, new String[]{OLDROLLNUMBER});
       if (a>0)
           Toast.makeText(context,"Updated",Toast.LENGTH_LONG).show();

    }
}
