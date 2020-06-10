package com.wuyou.wybaselibrary.model;

public class RspModel<T> {
    public static final int OPERATION_SUCCESS = 1000;
    public static final int OPERATION_FAIL = 1001;

    public static final int BIND_PUSHID_SUCCESS = 2001;

    public static final int USER_NOT_LOGIN = 3001;
    public static final int USERNAME_HAS_BE_REGISTERED = 3002;
    public static final int USER_REGISTER_SUCCESS = 3003;
    public static final int USER_REGISTER_FAIL = 3004;
    public static final int USER_NOT_REGISTER = 3005;
    public static final int USER_LOGIN_SUCCESS = 3006;
    public static final int USER_UPDATE_SUCCESS = 3007;
    public static final int USER_UPDATE_FAIL = 3008;

    public static final int PROJECT_CREATE_SUCCESS = 4001;
    public static final int PROJECT_CREATE_FAIL = 4002;
    public static final int PROJECT_HAS_EXIST = 4003;
    public static final int PROJECT_UPDATE_SUCCESS = 4004;
    public static final int PROJECT_UPDATE_FAIL = 4005;


    private int code;
    private String message;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
