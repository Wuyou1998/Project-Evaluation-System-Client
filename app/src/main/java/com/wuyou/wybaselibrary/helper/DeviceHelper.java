package com.wuyou.wybaselibrary.helper;

import android.util.Log;

import com.wuyou.wybaselibrary.Factory.Factory;
import com.wuyou.wybaselibrary.model.RspModel;
import com.wuyou.wybaselibrary.model.device.DeviceBindModel;
import com.wuyou.wybaselibrary.net.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceHelper {
    private static final String TAG = "DeviceHelper";

    public static void bind(DeviceBindModel model) {
        Factory.runOnAsync(() -> {
            Call<RspModel<Object>> call = Network.remote().bind(model);
            call.enqueue(new Callback<RspModel<Object>>() {
                @Override
                public void onResponse(Call<RspModel<Object>> call, Response<RspModel<Object>> response) {
                    if (response.body() != null && response.body().getCode() == RspModel.OPERATION_SUCCESS)
                        Log.e(TAG, "onResponse: 绑定成功");
                    else {
                        Log.e(TAG, "onResponse: 绑定失败");
                    }
                }

                @Override
                public void onFailure(Call<RspModel<Object>> call, Throwable t) {
                    Log.e(TAG, "onResponse: 绑定失败");
                }
            });
        });
    }
}
