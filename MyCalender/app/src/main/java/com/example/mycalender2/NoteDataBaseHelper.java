package com.example.mycalender2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDataBaseHelper extends SQLiteOpenHelper {

    public NoteDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static interface TableCreateInterface {

        public void onCreate(SQLiteDatabase db);

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Note.getInstance().onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Note.getInstance().onUpgrade(db,oldVersion,newVersion);
    }
}
