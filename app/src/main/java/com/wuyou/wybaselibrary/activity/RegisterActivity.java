package com.wuyou.wybaselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.helper.UserHelper;
import com.wuyou.wybaselibrary.model.user.UserRegisterModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.utils.HashUtil;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class RegisterActivity extends BaseActivity implements NetCallback<UserRegisterModel> {
    @BindView(R.id.edt_userName)
    EditText edt_userName;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.tv_go_login)
    TextView tv_go_login;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    public static void show(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        super.initView();
        Glide.with(this)
                .load(R.mipmap.school_1)
                .placeholder(R.mipmap.school_1)
                .transform(new BlurTransformation(14, 2))
                .into(iv_bg);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        String userName = edt_userName.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String phone = edt_phone.getText().toString().trim();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "信息不完整！", Toast.LENGTH_SHORT).show();
            return;
        }
        UserRegisterModel model = new UserRegisterModel(userName, HashUtil.getMD5String(password), phone);
        UserHelper.register(model, this);
        WaitDialog.show(this, "登录中");
    }

    @OnClick(R.id.tv_go_login)
    void goToLogin() {
        LoginActivity.show(this);
    }

    @Override
    public void onSuccess(UserRegisterModel data) {
        //WaitDialog.dismiss();
        //注册成功是没有返回值的
        TipDialog.show(this, "注册成功", TipDialog.TYPE.SUCCESS);
        finish();
    }

    @Override
    public void onFail(String message) {

        //WaitDialog.dismiss();
        TipDialog.show(this, "操作失败", TipDialog.TYPE.ERROR);
    }
}
