package com.wuyou.wybaselibrary.utils;


import android.content.Context;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

public class PermissionUtil {

    public static final String PERMISSION_CAMERA = Permission.CAMERA;
    public static final String PERMISSION_STORAGE = Permission.WRITE_EXTERNAL_STORAGE;

    public static void doSomethingAfterCheckPermission(Context context, String permission, Runnable onSuccess, Runnable onFail) {
        AndPermission.with(context)
                .runtime()
                .permission(permission)
                .onGranted(permissions -> {
                    if (onSuccess != null)
                        onSuccess.run();
                })
                .onDenied(permissions -> {
                    if (onFail != null)
                        onFail.run();
                })
                .start();
    }

    public static boolean checkPermission(Context context, String permission) {
        return AndPermission.hasPermissions(context, permission);
    }
}
