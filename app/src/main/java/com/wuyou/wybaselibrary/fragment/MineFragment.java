package com.wuyou.wybaselibrary.fragment;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kongzue.dialog.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.InputDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.wuyou.wybaselibrary.Account;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.activity.AdminActivity;
import com.wuyou.wybaselibrary.activity.CollectionActivity;
import com.wuyou.wybaselibrary.activity.HistoryActivity;
import com.wuyou.wybaselibrary.activity.LoginActivity;
import com.wuyou.wybaselibrary.activity.MyCommentActivity;
import com.wuyou.wybaselibrary.activity.SearchActivity;
import com.wuyou.wybaselibrary.activity.UserInfoActivity;
import com.wuyou.wybaselibrary.utils.SPUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.flutter.embedding.android.FlutterActivity;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MineFragment extends BaseFragment {

    @BindView(R.id.iv_avatar_bg)
    ImageView iv_avatar_bg;
    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_state)
    TextView tv_user_state;
    @BindView(R.id.ll_manage)
    LinearLayout ll_manage;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        refreshView();
    }

    public void refreshView(){
        initAvatar();
        tv_user_name.setText(Account.getUserName());
        tv_user_state.setText(Account.getAccountState());
        if (Account.getType() == 1) {
            ll_manage.setVisibility(View.VISIBLE);
        } else {
            ll_manage.setVisibility(View.GONE);
        }
    }

    private void initAvatar() {
        String avatar = Account.getAccountAvatar();
        if (avatar == null || !avatar.contains("http")) {
            Glide.with(this)
                    .load(R.drawable.default_portrait)
                    .dontAnimate()
                    //加载过程中的图片显示
                    .placeholder(R.drawable.default_portrait)
                    //加载失败中的图片显示
                    //如果重试3次（下载源代码可以根据需要修改）还是无法成功加载图片，则用错误占位符图片显示。
                    .error(R.drawable.default_portrait)
                    //第二个参数是圆角半径，第三个是模糊程度，2-5之间个人感觉比较好。
                    .transform(new BlurTransformation(14, 2))
                    .into(iv_avatar_bg);
            Glide.with(this).load(R.drawable.default_portrait).into(iv_avatar);
        } else {
            Glide.with(this)
                    .load(avatar)
                    .dontAnimate()
                    .placeholder(R.drawable.default_portrait)
                    .error(R.drawable.default_portrait)
                    .transform(new BlurTransformation(14, 2))
                    .into(iv_avatar_bg);
            Glide.with(this).load(avatar).placeholder(R.drawable.default_portrait).into(iv_avatar);
        }

    }

    @OnClick(R.id.iv_avatar)
    void changeUserInfo() {
        UserInfoActivity.show(Objects.requireNonNull(getContext()));
    }

    @OnClick(R.id.ll_project)
    void onMyProjectClick() {
        SearchActivity.show(getContext(), Account.getUserName(), false);
    }

    @OnClick(R.id.ll_comment)
    void myCommentsClick() {
        MyCommentActivity.show(Objects.requireNonNull(getContext()));
    }

    @OnClick(R.id.cl_share)
    void onShareClick() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Boss直评，一个好用的项目评审软件，" +
                "2020届物联网工程毕业生吴优作品。下载地址：www.wuyou.top.cn");
        shareIntent = Intent.createChooser(shareIntent, "分享给朋友");
        startActivity(shareIntent);
    }

    @OnClick(R.id.cl_check_update)
    void onCheckUpdateClick() {
        WaitDialog.show((AppCompatActivity) getContext(), "检查更新中");
        new Handler().postDelayed(() -> {
            Toast.makeText(getContext(), "已是最新版本！", Toast.LENGTH_SHORT).show();
            WaitDialog.dismiss();
        }, 1500);
    }

    @OnClick(R.id.cl_feedback)
    void onFeedbackClick() {
        InputDialog.show((AppCompatActivity) Objects.requireNonNull(getContext()), "反馈", "在下面输入你的意见吧")
                .setOnOkButtonClickListener(new OnInputDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v, String inputStr) {
                        if (TextUtils.isEmpty(inputStr)) {
                            Toast.makeText(getContext(), "内容不能为空哦", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        Toast.makeText(getContext(), "已收到您的反馈，我们会做的更好！", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
    }

    @OnClick(R.id.cl_about)
    void onAboutClick() {
        //去Flutter页面
        startActivity(
                FlutterActivity.createDefaultIntent(Objects.requireNonNull(getContext()))
        );
    }

    @OnClick(R.id.ll_collect)
    void onCollectionClick() {
        CollectionActivity.show(Objects.requireNonNull(getContext()));
    }

    @OnClick(R.id.ll_manage)
    void onManageClick() {
        AdminActivity.show(Objects.requireNonNull(getContext()));
    }

    @OnClick(R.id.ll_history)
    void onHistoryClick() {
        HistoryActivity.show(Objects.requireNonNull(getContext()));
    }

    @OnClick(R.id.tv_exit_account)
    void onLogOutClick() {
        String pushId = Account.getPushId();
        SPUtil.clear();
        if (!TextUtils.isEmpty(pushId)) {
            Account.setPushId(pushId);
        }
        LoginActivity.show(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(getActivity()).finish();
    }

}
