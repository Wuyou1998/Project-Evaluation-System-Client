package com.wuyou.wybaselibrary.Factory;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Factory {
    private static final String TAG = "Factory";
    //全局的线程池
    private static final Executor executor;
    //全局的Gson
    private static final Gson gson;
    //全局的handler
    private static Handler handler;

    static {
        //新建一个4线程线程池
        executor = Executors.newFixedThreadPool(4);
        //Gson初始化,设置时间格式，设置过滤器
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        //新建一个用于回调到UI线程的handler
        handler = new Handler(Looper.getMainLooper());
    }


    /**
     * 异步运行的方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        //拿到单例，拿到线程池，之后异步执行
        executor.execute(runnable);
    }

    /**
     * 在UI线程中执行
     * @param runnable Runnable
     */
    public static  void  runOnUiThread(Runnable runnable){
        handler.post(runnable);
    }

    public static Gson getGson() {
        return gson;
    }

}
