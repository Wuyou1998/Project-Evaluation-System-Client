package com.wuyou.wybaselibrary.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wuyou.wybaselibrary.helper.SqlLiteHelper;

import java.util.LinkedList;
import java.util.List;

public class HistoryDao {
    public static void insertHistory(String projectName) {
        SQLiteDatabase db = SqlLiteHelper.getInstance().getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", projectName);
        db.insert("history_tb", null, values);
    }

    public static List<String> getHistoryList() {
        LinkedList<String> list = new LinkedList<>();

        SQLiteDatabase db = SqlLiteHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query("history_tb", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                int index = cursor.getColumnIndexOrThrow("name");
                list.addFirst(cursor.getString(index));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}
