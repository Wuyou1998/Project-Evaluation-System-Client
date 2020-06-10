package com.wuyou.wybaselibrary.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wuyou.wybaselibrary.helper.SqlLiteHelper;

import java.util.LinkedList;
import java.util.List;

public class MessageDao {
    public static void insertMessage(String message) {
        SQLiteDatabase db = SqlLiteHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("message", message);
        db.insert("message_tb", null, values);
    }

    public static List<String> getMessageList() {
        LinkedList<String> list = new LinkedList<>();

        SQLiteDatabase db = SqlLiteHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query(true, "message_tb", null, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                int index = cursor.getColumnIndexOrThrow("message");
                list.addFirst(cursor.getString(index));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}
