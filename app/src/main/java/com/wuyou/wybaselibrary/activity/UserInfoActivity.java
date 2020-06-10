package com.wuyou.wybaselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.wuyou.wybaselibrary.Account;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.application.BaseApplication;
import com.wuyou.wybaselibrary.helper.UserHelper;
import com.wuyou.wybaselibrary.model.user.UserCardModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.widgets.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.EditText;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class UserInfoActivity extends BaseActivity implements GalleryFragment.OnSelectedListener,
        NetCallback<UserCardModel> {
    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;

    @BindView(R.id.iv_sex_man)
    ImageView iv_sex_man;

    @BindView(R.id.edt_desc)
    EditText edt_desc;

    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    private boolean isMan = true;
    private String avatarPath = null;
    private UserCardModel currentUserModel = Account.outPutUserCard();

    public static void show(Context context) {
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_user_info;
    }

    @Override
    protected void initView() {
        super.initView();
        Glide.with(this)
                .load(R.mipmap.school_1)
                .placeholder(R.mipmap.school_1)
                .transform(new BlurTransformation(14, 2))
                .into(iv_bg);
        if (!TextUtils.isEmpty(currentUserModel.getAvatar())) {
            Glide.with(this).load(currentUserModel.getAvatar()).placeholder(R.drawable.default_portrait).into(iv_avatar);
        }
        if (!TextUtils.isEmpty(currentUserModel.getState())) {
            edt_desc.setText(Account.getAccountState());
        }
    }

    @OnClick(R.id.iv_avatar)
    void OnAvatarClick() {
        GalleryFragment galleryFragment = new GalleryFragment();
        galleryFragment.setOnSelectedListener(this);
        galleryFragment.show(getSupportFragmentManager(), GalleryFragment.class.getName());
    }

    @OnClick(R.id.iv_sex_man)
    void onSexClick() {
        isMan = !isMan;
        iv_sex_man.setImageResource(isMan ? R.drawable.ic_sex_man : R.drawable.ic_sex_woman);
        iv_sex_man.getBackground().setLevel(isMan ? 0 : 1);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (TextUtils.isEmpty(avatarPath)) {
            showToast("还未选择头像！");
            return;
        }
        currentUserModel.setAvatar(avatarPath);
        currentUserModel.setState(edt_desc.getText().toString().trim());
        UserHelper.update(currentUserModel, this);
        WaitDialog.show(this, "请稍候");
    }


    @Override
    public void onSelectedImage(String path) {
        if (!TextUtils.isEmpty(path)) {
            UCrop.Options options = new UCrop.Options();
            //设置图片处理的格式 JPEG
            options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
            //设置压缩后的图片精度
            options.setCompressionQuality(96);
            //设置状态栏颜色，新版的uCrop状态栏颜色为白色导致看不清文字
            options.setStatusBarColor(this.getResources().getColor(R.color.colorDark));
            UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(BaseApplication.getAvatarTempFile()))
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(512, 512)
                    .withOptions(options)
                    .start(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //是当前fragment能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //获取uri进行加载
            final Uri resultUri = UCrop.getOutput(Objects.requireNonNull(data));
            if (resultUri != null) {
                loadAvatar(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            showToast("出错了...");
        }
    }

    private void loadAvatar(Uri resultUri) {
        avatarPath = resultUri.getPath();
        Glide.with(this).load(resultUri).into(iv_avatar);
    }

    @Override
    public void onSuccess(UserCardModel data) {
        TipDialog.show(this, "修改成功！", TipDialog.TYPE.SUCCESS);
        Account.setWithUserCard(data);
        HomeActivity.show(this);
        finish();
    }


    @Override
    public void onFail(String message) {
        TipDialog.show(this, message, TipDialog.TYPE.ERROR);
    }
}
