package com.wuyou.wybaselibrary.model.user;

public class UserCardModel {
    //userName, state, avatar,
    //        realName, contact
    private String userName;
    private String state;
    private String avatar;
    private int type;
    private String realName;
    private String contact;

    public UserCardModel() {

    }

    public UserCardModel(String userName, String state, String avatar, int type, String realName, String contact) {
        this.userName = userName;
        this.state = state;
        this.avatar = avatar;
        this.type = type;
        this.realName = realName;
        this.contact = contact;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "UserCardModel{" +
                "userName='" + userName + '\'' +
                ", state='" + state + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", realName='" + realName + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
