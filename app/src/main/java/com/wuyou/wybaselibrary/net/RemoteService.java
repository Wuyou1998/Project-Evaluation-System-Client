package com.wuyou.wybaselibrary.net;

import com.wuyou.wybaselibrary.model.RspModel;
import com.wuyou.wybaselibrary.model.comment.CommentAddModel;
import com.wuyou.wybaselibrary.model.comment.MyCommentModel;
import com.wuyou.wybaselibrary.model.device.DeviceBindModel;
import com.wuyou.wybaselibrary.model.page.MainPageModel;
import com.wuyou.wybaselibrary.model.project.ApprovalModel;
import com.wuyou.wybaselibrary.model.project.ProjectCreateModel;
import com.wuyou.wybaselibrary.model.project.ProjectDetailModel;
import com.wuyou.wybaselibrary.model.project.ProjectDetailPageModel;
import com.wuyou.wybaselibrary.model.user.UserCardModel;
import com.wuyou.wybaselibrary.model.user.UserLoginModel;
import com.wuyou.wybaselibrary.model.user.UserRegisterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RemoteService {

    //user 部分
    @POST("api/user/login")
    Call<RspModel<UserCardModel>> login(@Body UserLoginModel model);

    @POST("api/user/register")
    Call<RspModel<UserRegisterModel>> register(@Body UserRegisterModel model);

    @POST("api/user/update")
    Call<RspModel<UserCardModel>> update(@Body UserCardModel model);

    @POST("api/user/reset")
    Call<RspModel<Object>> reset(@Body UserLoginModel model);

    //project 部分
    @POST("api/project/create")
    Call<RspModel<ProjectCreateModel>> createProject(@Body ProjectCreateModel model);

    @POST("api/project/approval")
    Call<RspModel<Object>> approval(@Body ApprovalModel model);

    @GET("api/project/detail")
    Call<RspModel<ProjectDetailPageModel>> getProjectDetail(@Query("projectName") String projectName);

    //主页
    @GET("api/page/main")
    Call<RspModel<MainPageModel>> getMainPageData();

    @POST("api/record/comment")
    Call<RspModel<Object>> addComment(@Body CommentAddModel model);

    @GET("api/project/search")
    Call<RspModel<List<ProjectDetailModel>>> searchByProjectName(@Query("projectName") String projectName);

    @GET("api/project/search")
    Call<RspModel<List<ProjectDetailModel>>> searchByUserName(@Query("userName") String userName);

    @GET("api/record/mine")
    Call<RspModel<List<MyCommentModel>>> getAllMyComment();

    //device 推送
    @POST("/api/device/bind")
    Call<RspModel<Object>> bind(@Body DeviceBindModel model);

}
