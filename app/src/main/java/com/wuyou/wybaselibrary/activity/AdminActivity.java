package com.wuyou.wybaselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.kongzue.dialog.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.InputDialog;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.helper.AdminHelper;
import com.wuyou.wybaselibrary.model.user.UserLoginModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.utils.HashUtil;

import butterknife.OnClick;


public class AdminActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_admin;
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context, AdminActivity.class));
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @OnClick(R.id.btn_reset)
    void resetUserPassword() {
        InputDialog.show(this, "重置密码", "输入要重置密码的用户名，其密码将会被重置为123456")
                .setOnOkButtonClickListener(new OnInputDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v, String inputStr) {
                        if (TextUtils.isEmpty(inputStr)) {
                            Toast.makeText(AdminActivity.this, "内容不能为空哦", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        UserLoginModel model = new UserLoginModel(inputStr, HashUtil.getMD5String("123456"));
                        AdminHelper.reset(model, new NetCallback<Object>() {
                            @Override
                            public void onSuccess(Object data) {
                                showToast("重置成功");
                            }

                            @Override
                            public void onFail(String message) {
                                showToast(message);
                            }
                        });
                        return false;
                    }
                });

    }
}
