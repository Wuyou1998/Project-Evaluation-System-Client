package com.wuyou.wybaselibrary.activity;

import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wuyou.wybaselibrary.Account;
import com.wuyou.wybaselibrary.R;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        super.initView();
        Glide.with(this)
                .load(R.mipmap.school)
                .placeholder(R.mipmap.school)
                .transform(new BlurTransformation(14, 2))
                .into(iv_bg);
    }

    @Override
    protected void initData() {
        super.initData();
        new Handler().postDelayed(() -> {
            if (Account.isLogin()) {
                if (Account.needAddAvatar()) {
                    UserInfoActivity.show(this);
                } else {
                    HomeActivity.show(this);
                }
            } else {
                LoginActivity.show(this);
            }
            finish();
        }, 2500);
    }
}
