package com.paulosgf.samadhi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "msgdb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MESSAGES = "msgs";
    private static final String KEY_ID = "id";
    private static final String KEY_MESSAGE = "msg";

    private static final String CREATE_TABLE_MESSAGES = "CREATE TABLE "
            + TABLE_MESSAGES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_MESSAGES);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MESSAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_MESSAGES + "'");
        onCreate(db);
    }

    public long addMessage(String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);
        // insert row in messages table
        long insert = db.insert(TABLE_MESSAGES, null, values);

        return insert;
    }

    public ArrayList<MessageModel> getAllMsgs() {
        ArrayList<MessageModel> msgModelArrayList = new ArrayList<MessageModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                MessageModel userModel = new MessageModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                userModel.setMessage(c.getString(c.getColumnIndex(KEY_MESSAGE)));
                // adding to Messages list
                msgModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        return msgModelArrayList;
    }

    public int updateMessage(int id, String message) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);
        // update row in messages table based on value
        return db.update(TABLE_MESSAGES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteMessage(int id) {

        // delete row in messages table based on id
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGES, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

}
