package com.wuyou.wybaselibrary.model.device;

public class DeviceBindModel {
    private String userName;
    private String pushId;

    public DeviceBindModel(String userName, String pushId) {
        this.userName = userName;
        this.pushId = pushId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
