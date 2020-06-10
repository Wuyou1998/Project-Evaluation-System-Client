package com.wuyou.wybaselibrary;

import android.text.TextUtils;

import com.wuyou.wybaselibrary.model.user.UserCardModel;
import com.wuyou.wybaselibrary.utils.SPUtil;

public class Account {
    public static final String ACCOUNT_USER_NAME = "ACCOUNT_USER_NAME";
    public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String ACCOUNT_STATE = "ACCOUNT_STATE";
    public static final String ACCOUNT_AVATAR = "ACCOUNT_AVATAR";
    public static final String ACCOUNT_REAL_NAME = "ACCOUNT_REAL_NAME";
    public static final String ACCOUNT_CONTACT = "ACCOUNT_CONTACT";
    public static final String ACCOUNT_PUSH_ID = "ACCOUNT_PUSH_ID";


    private String userName;
    private int type;
    private String state;
    private String avatar;
    private String realName;
    private String contact;
    private String pushId;

    public static void setWithUserCard(UserCardModel model) {
        SPUtil.putString(ACCOUNT_USER_NAME, model.getUserName());
        SPUtil.putInt(ACCOUNT_TYPE, model.getType());
        SPUtil.putString(ACCOUNT_AVATAR, model.getAvatar());
        SPUtil.putString(ACCOUNT_STATE, model.getState());
        SPUtil.putString(ACCOUNT_REAL_NAME, model.getRealName());
        SPUtil.putString(ACCOUNT_CONTACT, model.getContact());
    }

    public static String getAccountAvatar() {
        return SPUtil.getString(ACCOUNT_AVATAR, null);
    }

    public static String getAccountState() {
        return SPUtil.getString(ACCOUNT_STATE, "未设置");
    }

    public static String getUserName() {

        return SPUtil.getString(ACCOUNT_USER_NAME, "未设置");
    }

    public static int getType() {

        return SPUtil.getInt(ACCOUNT_TYPE, -1);
    }

    public static void setPushId(String pushId) {
        SPUtil.putString(ACCOUNT_PUSH_ID, pushId);
    }

    public static String getPushId() {
        return SPUtil.getString(ACCOUNT_PUSH_ID, null);
    }

    public static boolean needAddAvatar() {
        String avatar = getAccountAvatar();
        return TextUtils.isEmpty(avatar)||avatar.equals("undefined");
    }

    public static String getAccountRealName() {
        return SPUtil.getString(Account.ACCOUNT_REAL_NAME, "未设置");
    }

    public static String getAccountContact() {
        return SPUtil.getString(Account.ACCOUNT_CONTACT, "未设置");
    }

    public static boolean isBind() {
        return !(getPushId() == null);
    }

    public static boolean isLogin() {
        return !(TextUtils.isEmpty(getUserName()) || getType() == -1);
    }

    public static UserCardModel outPutUserCard() {
        return new UserCardModel(getUserName(), getAccountState(), getAccountAvatar(), getType(),
                getAccountRealName(), getAccountContact());
    }
}
