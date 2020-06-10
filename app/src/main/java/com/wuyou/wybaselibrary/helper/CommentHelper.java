package com.wuyou.wybaselibrary.helper;

import com.wuyou.wybaselibrary.Factory.Factory;
import com.wuyou.wybaselibrary.model.RspModel;
import com.wuyou.wybaselibrary.model.comment.CommentAddModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.net.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentHelper {
    public static void addComment(CommentAddModel model, NetCallback<Object> callback) {
        Factory.runOnAsync(() -> {
            Call<RspModel<Object>> call = Network.remote().addComment(model);
            call.enqueue(new Callback<RspModel<Object>>() {
                @Override
                public void onResponse(Call<RspModel<Object>> call, Response<RspModel<Object>> response) {
                    if (response.body() != null && response.body().getCode() == RspModel.OPERATION_SUCCESS) {
                        callback.onSuccess(null);
                    } else {
                        callback.onFail("添加评论出错！");
                    }
                }

                @Override
                public void onFailure(Call<RspModel<Object>> call, Throwable t) {
                    callback.onFail("添加评论出错！");
                }
            });
        });
    }
}
