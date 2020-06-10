package com.wuyou.wybaselibrary.helper;

import com.wuyou.wybaselibrary.Factory.Factory;
import com.wuyou.wybaselibrary.model.RspModel;
import com.wuyou.wybaselibrary.model.page.MainPageModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.net.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageHelper {
    //主页
    public static void getMainPage(NetCallback<MainPageModel> callback) {
        Factory.runOnAsync(() -> {
            Call<RspModel<MainPageModel>> call = Network.remote().getMainPageData();
            call.enqueue(new Callback<RspModel<MainPageModel>>() {
                @Override
                public void onResponse(Call<RspModel<MainPageModel>> call, Response<RspModel<MainPageModel>> response) {
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
                public void onFailure(Call<RspModel<MainPageModel>> call, Throwable t) {
                    callback.onFail("请求出错");
                }
            });
        });
    }
}
