package com.wuyou.wybaselibrary.net;

public interface NetCallback<T> {
    void onSuccess(T data);
    void onFail(String message);
}
