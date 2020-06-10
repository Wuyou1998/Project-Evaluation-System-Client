package com.wuyou.wybaselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wuyou.wybaselibrary.application.BaseApplication;

import java.util.HashSet;
import java.util.Set;

public class SPUtil {

    public static final String SP_COOKIE = "SP_COOKIE";
    public static final String SP_SQL_VERSION = "SP_SQL_VERSION";

    private static SharedPreferences sp = BaseApplication.getBaseApplicationContext()
            .getSharedPreferences("config", Context.MODE_PRIVATE);

    public static synchronized void putInt(String key, int i) {
        sp.edit().putInt(key, i).apply();
    }

    public static synchronized void putString(String key, String s) {
        sp.edit().putString(key, s).apply();
    }

    public static synchronized void putBoolean(String key, boolean b) {
        sp.edit().putBoolean(key, b).apply();
    }

    public static synchronized void putCookie(String key, HashSet<String> set) {
        sp.edit().putStringSet(key, set).commit();
    }

    public static synchronized void putStringSet(String key, Set<String> set) {
        sp.edit().putStringSet(key, set).apply();
    }

    public static synchronized int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public static synchronized boolean getBoolean(String key, boolean b) {
        return sp.getBoolean(key, b);
    }

    public static synchronized String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public static synchronized Set<String> getStringSet(String key, Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    public static synchronized HashSet<String> getCookie(String key, HashSet<String> defaultValue) {
        return (HashSet<String>) sp.getStringSet(key, defaultValue);
    }

    public static synchronized void clear() {
        sp.edit().clear().apply();
    }


}
