package com.example.nightfury.cse_ku_apl_2017_true_caller;

/**
 * Created by Night Fury on 4/16/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{

    //BackGround bcground;
    public Database(Context context) {
        super(context, "truecaller.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contacts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS contacts");
        onCreate(db);
    }
    public void insertData(String name, String number)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("number",number);
        db.insert("contacts",null,contentValues);

    }
    public  void delete_local_database()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.rawQuery("DELETE FROM contacts",null);

        db.delete("contacts",null,null);
    }
    public Cursor showAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM contacts",null);
        return res;

    }

}
