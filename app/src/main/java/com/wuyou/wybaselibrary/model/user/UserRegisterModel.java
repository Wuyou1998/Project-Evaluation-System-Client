package com.wuyou.wybaselibrary.model.user;

public class UserRegisterModel {
    // userName, password, type, state, avatar,
    //        realName, contact, pushId

    private String userName;
    private String password;
    private int type;
    private String state;
    private String avatar;
    private String realName;
    private String contact;
    private String pushId;

    public UserRegisterModel(String userName, String password, String contact) {
        this.userName = userName;
        this.password = password;
        this.contact = contact;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    @Override
    public String toString() {
        return "UserRegisterModel{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", state='" + state + '\'' +
                ", avatar='" + avatar + '\'' +
                ", realName='" + realName + '\'' +
                ", contact='" + contact + '\'' +
                ", pushId='" + pushId + '\'' +
                '}';
    }
}
