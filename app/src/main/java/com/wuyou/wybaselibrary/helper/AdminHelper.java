package com.wuyou.wybaselibrary.helper;

import com.wuyou.wybaselibrary.model.RspModel;
import com.wuyou.wybaselibrary.model.user.UserLoginModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.net.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHelper {
    public static void reset(UserLoginModel model, NetCallback<Object> callback) {
        Call<RspModel<Object>> call = Network.remote().reset(model);
        call.enqueue(new Callback<RspModel<Object>>() {
            @Override
            public void onResponse(Call<RspModel<Object>> call, Response<RspModel<Object>> response) {
                if (response.body() != null && response.body().getCode() == RspModel.OPERATION_SUCCESS)
                    callback.onSuccess(response.body().getResult());
                else {
                    if (response.body() != null)
                        callback.onFail(response.body().getMessage());
                    else
                        callback.onFail("重置出错");
                }
            }

            @Override
            public void onFailure(Call<RspModel<Object>> call, Throwable t) {
                callback.onFail("重置出错");
            }
        });
    }
}
