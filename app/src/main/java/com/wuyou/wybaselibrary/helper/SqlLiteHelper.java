package com.wuyou.wybaselibrary.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.wuyou.wybaselibrary.application.BaseApplication;


public class SqlLiteHelper extends SQLiteOpenHelper {
    public static final String path = "push.db";

    private static SqlLiteHelper helper;

    static {
        helper = new SqlLiteHelper(BaseApplication.getBaseApplicationContext(),
                SqlLiteHelper.path, null, 1);
    }

    public static SqlLiteHelper getInstance() {
        return helper;
    }


    public SqlLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMessageSql = "create table message_tb (_id integer primary key autoincrement," +
                "message TINYTEXT)";
        String createCollectionSql = "create table collection_tb (_id integer primary key autoincrement," +
                "name TINYTEXT)";
        String createHistorySql = "create table history_tb (_id integer primary key autoincrement," +
                "name TINYTEXT)";
        db.execSQL(createMessageSql);
        db.execSQL(createCollectionSql);
        db.execSQL(createHistorySql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
