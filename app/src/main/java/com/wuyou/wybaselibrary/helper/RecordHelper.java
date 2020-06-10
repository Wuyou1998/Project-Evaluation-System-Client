package com.wuyou.wybaselibrary.helper;


import com.wuyou.wybaselibrary.Factory.Factory;
import com.wuyou.wybaselibrary.model.RspModel;
import com.wuyou.wybaselibrary.model.comment.MyCommentModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.net.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordHelper {
    public static void getAllMyComments(NetCallback<List<MyCommentModel>> callback) {
        Factory.runOnAsync(() -> {
            Call<RspModel<List<MyCommentModel>>> call = Network.remote().getAllMyComment();
            call.enqueue(new Callback<RspModel<List<MyCommentModel>>>() {
                @Override
                public void onResponse(Call<RspModel<List<MyCommentModel>>> call, Response<RspModel<List<MyCommentModel>>> response) {
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
                public void onFailure(Call<RspModel<List<MyCommentModel>>> call, Throwable t) {
                    callback.onFail("请求出错");
                }
            });
        });
    }
}
