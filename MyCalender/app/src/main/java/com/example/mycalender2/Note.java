package com.example.mycalender2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class Note implements NoteDataBaseHelper.TableCreateInterface {

    public static String tableName = "Note";
    public static String _id = "_id";
    public static String title = "title";
    public static String content = "content";
    public static String time = "date";

    private Note(){}

    private static Note note = new Note();

    public static Note getInstance(){
        return note;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "
                + Note.tableName
                + " (  "
                + "_id integer primary key autoincrement, "
                + Note.title + " TEXT, "
                + Note.content + " TEXT, "
                + Note.time + " TEXT "
                + ");";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( oldVersion < newVersion ) {
            String sql = "DROP TABLE IF EXISTS " + Note.tableName;
            db.execSQL( sql );
            this.onCreate( db );
        }
    }

    public static void insertNote( NoteDataBaseHelper dbHelper, ContentValues userValues ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert( Note.tableName, null, userValues );
        db.close();
    }

    public static void deleteNote( NoteDataBaseHelper dbHelper, int _id ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(  Note.tableName, Note._id + "=?",new String[] { _id + "" }  );
        db.close();

    }


    public static void updateNote( NoteDataBaseHelper dbHelper,  int _id, ContentValues infoValues ) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(Note.tableName, infoValues, Note._id + " =? ", new String[]{ _id + "" });
        db.close();
    }


    public static HashMap<String, Object> getNote(NoteDataBaseHelper dbHelper, int _id ){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        HashMap<String, Object> NoteMap = new HashMap<String, Object>();

        Cursor cursor = db.query( Note.tableName, null, Note._id + " =? ", new String[]{ _id + "" }, null, null, null);
        cursor.moveToFirst();
        NoteMap.put(Note.title, cursor.getLong(cursor.getColumnIndex(Note.title)));
        NoteMap.put(Note.content, cursor.getString(cursor.getColumnIndex(Note.content)));
        NoteMap.put(Note.time, cursor.getString(cursor.getColumnIndex(Note.time)));

        return NoteMap;

    }


    public static Cursor getAllNotes(NoteDataBaseHelper dbHelper) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Note.tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

}
