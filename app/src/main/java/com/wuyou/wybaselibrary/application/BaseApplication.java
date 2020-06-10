package com.wuyou.wybaselibrary.application;

import android.app.Application;
import android.content.Context;

import com.kongzue.dialog.util.DialogSettings;

import java.io.File;

public class BaseApplication extends Application {
    private static Context baseApplicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplicationContext = this.getApplicationContext();
        DialogSettings.isUseBlur = true;
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        DialogSettings.init();
        com.igexin.sdk.PushManager.getInstance().initialize(this);
    }

    public static Context getBaseApplicationContext() {
        return baseApplicationContext;
    }

    /**
     * 获取头像临时缓存地址
     *
     * @return 头像临时缓存地址
     */
    public static File getAvatarTempFile() {
        //得到头像目录的缓存地址
        File dir = new File(baseApplicationContext.getCacheDir(), "avatar");
        //创建所有的对应的文件夹
        dir.mkdirs();
        //删除旧的缓存文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                file.delete();
            }
        }
        //返回一个当前时间戳的目录文件地址
        File path = new File(dir, System.currentTimeMillis() + "jpg");
        return path.getAbsoluteFile();
    }
}
