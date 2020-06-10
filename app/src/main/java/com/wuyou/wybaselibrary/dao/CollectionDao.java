package com.wuyou.wybaselibrary.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.wuyou.wybaselibrary.application.BaseApplication;
import com.wuyou.wybaselibrary.helper.SqlLiteHelper;

import java.util.LinkedList;
import java.util.List;

public class CollectionDao {
    public static void insertCollection(String projectName) {
        SQLiteDatabase db = SqlLiteHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query("collection_tb", null, "name=" +"'"+ projectName+"'", null, null, null, null);
        if (cursor.moveToFirst()) {
            //已经收藏过，不需要再操作
            return;
        }
        cursor.close();
        ContentValues values = new ContentValues();
        values.put("name", projectName);
        db.insert("collection_tb", null, values);
    }

    public static List<String> getCollectionList() {
        LinkedList<String> list = new LinkedList<>();

        SQLiteDatabase db = SqlLiteHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query("collection_tb", null, null, null, null, null, null);
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
