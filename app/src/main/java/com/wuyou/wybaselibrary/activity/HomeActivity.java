package com.wuyou.wybaselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kongzue.dialog.v3.FullScreenDialog;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.fragment.HomeFragment;
import com.wuyou.wybaselibrary.fragment.MessageFragment;
import com.wuyou.wybaselibrary.fragment.MineFragment;
import com.wuyou.wybaselibrary.helper.ProjectHelper;
import com.wuyou.wybaselibrary.model.project.ProjectCreateModel;
import com.wuyou.wybaselibrary.net.NetCallback;
import com.wuyou.wybaselibrary.widgets.GalleryFragment;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        FullScreenDialog.OnBindView, GalleryFragment.OnSelectedListener, NetCallback<ProjectCreateModel> {
    @BindView(R.id.btn_add)
    FloatActionButton btn_add;
    @BindView(R.id.bottomNav)
    BottomNavigationView bottomNav;

    Button btn_submit;
    TextView tv_image_name, tv_file_name;
    CardView cardView;
    ImageView iv_image_name;
    EditText edt_title, edt_content;
    Spinner sp_level;

    private Fragment lastFragment;

    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;

    private ProjectCreateModel projectCreateModel;
    private FullScreenDialog fullScreenDialog;

    private String imagePath, filePath;


    private static final int GET_FILE_CODE = 0x001;

    public static void show(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mineFragment != null)
            mineFragment.refreshView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        super.initView();
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setSelectedItemId(R.id.menu_home_list);

    }

    @OnClick(R.id.btn_add)
    void onFloatButtonClick() {
        projectCreateModel = new ProjectCreateModel();
        fullScreenDialog = FullScreenDialog
                .show(this, R.layout.dialog_create_project, this)
                .setCancelButton("取消")
                .setTitle("创建项目");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_list:
                floatButtonDoAnimation(0);
                if (lastFragment != null) {
                    if (lastFragment == homeFragment)
                        return true;
                    getSupportFragmentManager().beginTransaction().detach(lastFragment).commit();
                }
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_home_container, homeFragment, HomeFragment
                            .class.getName()).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().attach(homeFragment).commit();
                }

                lastFragment = homeFragment;

                return true;
            case R.id.menu_home_message:
                floatButtonDoAnimation(1);
                if (lastFragment != null) {
                    if (lastFragment == messageFragment)
                        return true;
                    getSupportFragmentManager().beginTransaction().detach(lastFragment).commit();
                }
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_home_container, messageFragment, MessageFragment
                            .class.getName()).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().attach(messageFragment).commit();
                }

                lastFragment = messageFragment;
                return true;
            case R.id.menu_home_mine:
                floatButtonDoAnimation(2);
                if (lastFragment != null) {
                    if (lastFragment == mineFragment)
                        return true;
                    getSupportFragmentManager().beginTransaction().detach(lastFragment).commit();
                }
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fl_home_container, mineFragment, MineFragment
                            .class.getName()).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().attach(mineFragment).commit();
                }

                lastFragment = mineFragment;
                return true;
        }

        return false;
    }

    private void floatButtonDoAnimation(int index) {

        float transY = 0;
        float rotation = 0;

        if (index != 0) {
            transY = Ui.dipToPx(getResources(), 78);
        } else {
            rotation = 360;
        }

        //TODO 连续点击button位置偏移 计划采用handler发送信息与移除信息来处理

        //开始动画，旋转，Y轴位移，弹性插值器
        btn_add.animate()
                .rotation(rotation)
                .translationY(transY)
                .setDuration(700)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .start();
    }

    @Override
    public void onBind(FullScreenDialog dialog, View rootView) {
        tv_image_name = rootView.findViewById(R.id.tv_image_name);
        iv_image_name = rootView.findViewById(R.id.iv_image_name);
        iv_image_name.setOnClickListener(v -> {
            cardView.setVisibility(View.GONE);
            tv_image_name.setText("点击上传");
            projectCreateModel.setImage(null);
        });
        tv_image_name.setOnClickListener(v -> {
            GalleryFragment galleryFragment = new GalleryFragment();
            galleryFragment.setOnSelectedListener(this).show(getSupportFragmentManager(), GalleryFragment.class.getName());
        });
        cardView = rootView.findViewById(R.id.cardView);
        tv_file_name = rootView.findViewById(R.id.tv_file_name);
        tv_file_name.setOnClickListener(v -> {
            if (tv_file_name.getText().equals("点击上传")) {
                new MaterialFilePicker()
                        .withActivity(this)
                        .withRequestCode(GET_FILE_CODE)
                        .withFilterDirectories(true) // Set directories filterable (false by default)
                        .withHiddenFiles(false) // Show hidden files and folders
                        .start();
            } else {
                tv_file_name.setText("点击上传");
                projectCreateModel.setFileUrl(null);
            }
        });
        edt_title = rootView.findViewById(R.id.edt_title);
        edt_content = rootView.findViewById(R.id.det_content);
        new Handler().postDelayed(() -> {
            edt_title.setFocusable(true);
            edt_title.setFocusableInTouchMode(true);
            edt_title.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }, 800);
        sp_level = rootView.findViewById(R.id.sp_level);
        sp_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectCreateModel.setLevel(position - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_submit = rootView.findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(v -> {
            String title = edt_title.getText().toString().trim();
            String content = edt_content.getText().toString().trim();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) || projectCreateModel.getLevel() == -1) {
                TipDialog.show(this, "信息不完整", TipDialog.TYPE.ERROR);
                return;
            }
            projectCreateModel.setProjectName(title);
            projectCreateModel.setContent(content);
            ProjectHelper.createProject(projectCreateModel, imagePath, filePath, this);
            WaitDialog.show(this, "请稍候");
        });
    }


    @Override
    public void onSelectedImage(String path) {
        tv_image_name.setText(path);
        imagePath = path;
        cardView.setVisibility(View.VISIBLE);
        Glide.with(this).load(path).into(iv_image_name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FILE_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            tv_file_name.setText(filePath);
            this.filePath = filePath;
        }
    }

    @Override
    public void onSuccess(ProjectCreateModel data) {
        TipDialog.show(this, "创建成功", TipDialog.TYPE.SUCCESS);
        fullScreenDialog.doDismiss();
    }

    @Override
    public void onFail(String message) {
        TipDialog.show(this, message, TipDialog.TYPE.ERROR);
    }
}
