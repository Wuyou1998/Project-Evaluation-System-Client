package com.wuyou.wybaselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kongzue.dialog.v3.WaitDialog;
import com.wuyou.wybaselibrary.Account;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.helper.UserHelper;
import com.wuyou.wybaselibrary.model.user.UserCardModel;
import com.wuyou.wybaselibrary.model.user.UserLoginModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.utils.HashUtil;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class LoginActivity extends BaseActivity implements NetCallback<UserCardModel> {

    @BindView(R.id.edt_userName)
    EditText edt_userName;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.tv_go_register)
    TextView tv_go_register;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    public static void show(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "信息不完整！", Toast.LENGTH_SHORT).show();
            return;
        }
        UserLoginModel model = new UserLoginModel(userName, HashUtil.getMD5String(password));
        String pushId = Account.getPushId();
        if (!TextUtils.isEmpty(pushId)) {
            model.setPushId(pushId);
        }
        WaitDialog.show(this, "登录中");
        UserHelper.login(model, this);
    }

    @OnClick(R.id.tv_go_register)
    void goToRegister() {
        RegisterActivity.show(this);
    }

    @Override
    public void onSuccess(UserCardModel data) {
        WaitDialog.dismiss();
        // MessageDialog.show(this, "结果", data.toString());
        Account.setWithUserCard(data);
        if (Account.needAddAvatar()) {
            UserInfoActivity.show(this);
        } else {
            HomeActivity.show(this);
        }
        finish();
    }

    @Override
    public void onFail(String message) {
        WaitDialog.dismiss();
        showToast(message);
    }
}
