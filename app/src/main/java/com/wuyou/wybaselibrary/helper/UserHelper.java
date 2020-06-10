package com.wuyou.wybaselibrary.helper;

import android.text.TextUtils;

import com.wuyou.wybaselibrary.Factory.Factory;
import com.wuyou.wybaselibrary.model.RspModel;
import com.wuyou.wybaselibrary.model.user.UserCardModel;
import com.wuyou.wybaselibrary.model.user.UserLoginModel;
import com.wuyou.wybaselibrary.model.user.UserRegisterModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.net.Network;
import com.wuyou.wybaselibrary.net.UploadHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHelper {
    //登录
    public static void login(UserLoginModel model, NetCallback<UserCardModel> callback) {
        Factory.runOnAsync(() -> {
            Call<RspModel<UserCardModel>> call = Network.remote().login(model);
            call.enqueue(new Callback<RspModel<UserCardModel>>() {
                @Override
                public void onResponse(Call<RspModel<UserCardModel>> call, Response<RspModel<UserCardModel>> response) {
                    if (response.body() != null && response.body().getCode() == RspModel.OPERATION_SUCCESS)
                        callback.onSuccess(response.body().getResult());
                    else {
                        if (response.body() != null)
                            callback.onFail(response.body().getMessage());
                        else
                            callback.onFail("请求出错");
                    }
                }

                @Override
                public void onFailure(Call<RspModel<UserCardModel>> call, Throwable t) {
                    callback.onFail("请求出错");
                }
            });

        });
    }

    //注册
    public static void register(UserRegisterModel model, NetCallback<UserRegisterModel> callback) {
        Factory.runOnAsync(() -> {
            Call<RspModel<UserRegisterModel>> call = Network.remote().register(model);
            call.enqueue(new Callback<RspModel<UserRegisterModel>>() {
                @Override
                public void onResponse(Call<RspModel<UserRegisterModel>> call, Response<RspModel<UserRegisterModel>> response) {
                    if (response.body() != null && response.body().getCode() == RspModel.OPERATION_SUCCESS)
                        //注册成功是没有返回值的
                        callback.onSuccess(null);
                    else {
                        if (response.body() != null)
                            callback.onFail(response.body().getMessage());
                        else
                            callback.onFail("请求出错");
                    }
                }

                @Override
                public void onFailure(Call<RspModel<UserRegisterModel>> call, Throwable t) {
                    callback.onFail("请求出错");
                }
            });
        });
    }

    public static void update(UserCardModel model, NetCallback<UserCardModel> callback) {
        Factory.runOnAsync(() -> {
            String url = UploadHelper.uploadAvatar(model.getAvatar());
            if (TextUtils.isEmpty(url)) {
                callback.onFail("文件上传出错！");
                return;
            }
            model.setAvatar(url);
            Call<RspModel<UserCardModel>> call = Network.remote().update(model);
            call.enqueue(new Callback<RspModel<UserCardModel>>() {
                @Override
                public void onResponse(Call<RspModel<UserCardModel>> call, Response<RspModel<UserCardModel>> response) {
                    if (response.body() != null && response.body().getCode() == RspModel.OPERATION_SUCCESS)
                        callback.onSuccess(response.body().getResult());
                    else {
                        if (response.body() != null)
                            callback.onFail(response.body().getMessage());
                        else
                            callback.onFail("请求出错");
                    }
                }

                @Override
                public void onFailure(Call<RspModel<UserCardModel>> call, Throwable t) {
                    callback.onFail("请求出错");
                }
            });
        });
    }
}
