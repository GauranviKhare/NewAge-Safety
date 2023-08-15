package com.example.newagesafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.RowId;

public class DatabaseHandler extends SQLiteOpenHelper
{
    public static final String Name="name";
    public DatabaseHandler(Context context) {
        super(context, "Data", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        //sqldb.execSQL("create table Contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR , number VARCHAR)");
        sqldb.execSQL("create table Contacts (name VARCHAR PRIMARY KEY,number VARCHAR )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqldb ,int i,int i1) {
        sqldb.execSQL("Drop table if exists Contacts");
    }

    public boolean addContacts(String name, String number)
    {
        SQLiteDatabase sqldb=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("number",number);
        long result=sqldb.insert("Contacts",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getText()
    {
        SQLiteDatabase sqldb=this.getWritableDatabase();
        Cursor cursor=sqldb.rawQuery("Select * from Contacts",null);
        return cursor;
    }

    public void deleteContact(String o)
    {
        SQLiteDatabase sqldb=this.getWritableDatabase();
        sqldb.delete("Contacts", Name+"=?",new String[]{String.valueOf(o)});
        Log.d("dbGK","deleted"+Name);
        sqldb.close();
    }

}


