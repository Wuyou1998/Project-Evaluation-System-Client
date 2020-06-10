package com.wuyou.wybaselibrary.helper;

import android.text.TextUtils;

import com.wuyou.wybaselibrary.Factory.Factory;
import com.wuyou.wybaselibrary.model.RspModel;
import com.wuyou.wybaselibrary.model.project.ApprovalModel;
import com.wuyou.wybaselibrary.model.project.ProjectCreateModel;
import com.wuyou.wybaselibrary.model.project.ProjectDetailModel;
import com.wuyou.wybaselibrary.model.project.ProjectDetailPageModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.net.Network;
import com.wuyou.wybaselibrary.net.UploadHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectHelper {
    //创建项目
    public static void createProject(ProjectCreateModel model, String imagePath, String filePath, NetCallback<ProjectCreateModel> callback) {
        Factory.runOnAsync(() -> {
            if (!TextUtils.isEmpty(imagePath)) {
                String imageUrl = UploadHelper.uploadImage(imagePath);
                if (TextUtils.isEmpty(imageUrl)) {
                    callback.onFail("图片上传失败！");
                    return;
                }
                model.setImage(imageUrl);
            }

            if (!TextUtils.isEmpty(filePath)) {
                String fileUrl = UploadHelper.uploadFile(filePath);
                if (TextUtils.isEmpty(fileUrl)) {
                    callback.onFail("文件上传失败！");
                    return;
                }
                model.setFileUrl(fileUrl);
            }


            Call<RspModel<ProjectCreateModel>> call = Network.remote().createProject(model);
            call.enqueue(new Callback<RspModel<ProjectCreateModel>>() {
                @Override
                public void onResponse(Call<RspModel<ProjectCreateModel>> call, Response<RspModel<ProjectCreateModel>> response) {
                    if (response.body() != null && response.body().getCode() == RspModel.OPERATION_SUCCESS)
                        //项目创建没有返回值
                        callback.onSuccess(null);
                    else {
                        if (response.body() != null)
                            callback.onFail(response.body().getMessage());
                        else
                            callback.onFail("请求出错");
                    }
                }

                @Override
                public void onFailure(Call<RspModel<ProjectCreateModel>> call, Throwable t) {
                    callback.onFail("请求出错");
                }
            });

        });
    }

    public static void getProjectDetail(String projectName, NetCallback<ProjectDetailPageModel> callback) {
        if (TextUtils.isEmpty(projectName))
            callback.onFail("请求出错！");
        Call<RspModel<ProjectDetailPageModel>> call = Network.remote().getProjectDetail(projectName);
        call.enqueue(new Callback<RspModel<ProjectDetailPageModel>>() {
            @Override
            public void onResponse(Call<RspModel<ProjectDetailPageModel>> call, Response<RspModel<ProjectDetailPageModel>> response) {
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
            public void onFailure(Call<RspModel<ProjectDetailPageModel>> call, Throwable t) {
                callback.onFail("请求出错！");
            }
        });
    }

    public static void searchProjectByName(String projectName, NetCallback<List<ProjectDetailModel>> callback) {
        Factory.runOnAsync(() -> {
            Call<RspModel<List<ProjectDetailModel>>> call = Network.remote().searchByProjectName(projectName);
            call.enqueue(new Callback<RspModel<List<ProjectDetailModel>>>() {
                @Override
                public void onResponse(Call<RspModel<List<ProjectDetailModel>>> call, Response<RspModel<List<ProjectDetailModel>>> response) {
                    if (response.body() != null && response.body().getCode() == RspModel.OPERATION_SUCCESS)
                        //项目创建没有返回值
                        callback.onSuccess(response.body().getResult());
                    else {
                        if (response.body() != null)
                            callback.onFail(response.body().getMessage());
                        else
                            callback.onFail("请求出错");
                    }
                }

                @Override
                public void onFailure(Call<RspModel<List<ProjectDetailModel>>> call, Throwable t) {
                    callback.onFail("请求出错");
                }
            });
        });
    }

    public static void searchProjectByUser(String userName, NetCallback<List<ProjectDetailModel>> callback) {
        Factory.runOnAsync(() -> {
            Call<RspModel<List<ProjectDetailModel>>> call = Network.remote().searchByUserName(userName);
            call.enqueue(new Callback<RspModel<List<ProjectDetailModel>>>() {
                @Override
                public void onResponse(Call<RspModel<List<ProjectDetailModel>>> call, Response<RspModel<List<ProjectDetailModel>>> response) {
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
                public void onFailure(Call<RspModel<List<ProjectDetailModel>>> call, Throwable t) {
                    callback.onFail("请求出错");
                }
            });
        });
    }

    public static void approval(ApprovalModel model, NetCallback<Object> callback) {
        Factory.runOnAsync(() -> {
            Call<RspModel<Object>> call = Network.remote().approval(model);
            call.enqueue(new Callback<RspModel<Object>>() {
                @Override
                public void onResponse(Call<RspModel<Object>> call, Response<RspModel<Object>> response) {
                    if (response.body() != null && response.body().getCode() == RspModel.OPERATION_SUCCESS)
                        //项目创建没有返回值
                        callback.onSuccess(null);
                    else {
                        if (response.body() != null)
                            callback.onFail(response.body().getMessage());
                        else
                            callback.onFail("审批出错");
                    }
                }

                @Override
                public void onFailure(Call<RspModel<Object>> call, Throwable t) {
                    callback.onFail("审批出错");
                }
            });
        });
    }
}
