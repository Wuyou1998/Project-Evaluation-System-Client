package com.wuyou.wybaselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kongzue.dialog.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.InputDialog;
import com.kongzue.dialog.v3.TipDialog;
import com.wuyou.wybaselibrary.Account;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.dao.HistoryDao;
import com.wuyou.wybaselibrary.helper.CommentHelper;
import com.wuyou.wybaselibrary.helper.ProjectHelper;
import com.wuyou.wybaselibrary.model.comment.CommentAddModel;
import com.wuyou.wybaselibrary.model.project.ApprovalModel;
import com.wuyou.wybaselibrary.model.project.ProjectCommentModel;
import com.wuyou.wybaselibrary.model.project.ProjectDetailModel;
import com.wuyou.wybaselibrary.model.project.ProjectDetailPageModel;
import com.wuyou.wybaselibrary.net.NetCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends BaseActivity implements OnInputDialogButtonClickListener,
        NetCallback<ProjectDetailPageModel> {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_cover)
    ImageView iv_cover;
    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_state)
    TextView tv_user_state;
    @BindView(R.id.tv_project_state)
    TextView tv_project_state;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.tv_file_name)
    TextView tv_file_name;
    @BindView(R.id.rv_comment_list)
    RecyclerView rv_comment_list;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fab_accept)
    FloatingActionButton fab_accept;
    @BindView(R.id.fab_reject)
    FloatingActionButton fab_reject;

    public static final String PROJECT_NAME = "projectName";

    private String projectName;
    private List<ProjectCommentModel> commentModels = new ArrayList<>();
    ;
    private Adapter adapter;

    public static void show(Context context, String projectName) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.PROJECT_NAME, projectName);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        super.initView();

        fab_accept.setOnClickListener(v -> {
            InputDialog.show(this, "审批通过？", "在下面输入你的意见吧")
                    .setOnOkButtonClickListener(new OnInputDialogButtonClickListener() {
                        @Override
                        public boolean onClick(BaseDialog baseDialog, View v, String inputStr) {
                            if (TextUtils.isEmpty(inputStr)) {
                                Toast.makeText(DetailActivity.this, "内容不能为空哦", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            //通过
                            ApprovalModel model = new ApprovalModel();
                            model.setProjectName(projectName);
                            model.setState(1);
                            model.setReason(inputStr);
                            ProjectHelper.approval(model, new NetCallback<Object>() {
                                @Override
                                public void onSuccess(Object data) {
                                    showToast("操作成功，正在刷新页面");
                                    DetailActivity.show(DetailActivity.this, projectName);
                                    finish();
                                }

                                @Override
                                public void onFail(String message) {
                                    showToast(message);
                                }
                            });
                            return false;
                        }
                    });
        });

        fab_reject.setOnClickListener(v -> {
            InputDialog.show(this, "审批不通过？", "在下面输入你的意见吧")
                    .setOnOkButtonClickListener(new OnInputDialogButtonClickListener() {
                        @Override
                        public boolean onClick(BaseDialog baseDialog, View v, String inputStr) {
                            if (TextUtils.isEmpty(inputStr)) {
                                Toast.makeText(DetailActivity.this, "内容不能为空哦", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            //不通过
                            ApprovalModel model = new ApprovalModel();
                            model.setProjectName(projectName);
                            model.setState(2);
                            model.setReason(inputStr);
                            ProjectHelper.approval(model, new NetCallback<Object>() {
                                @Override
                                public void onSuccess(Object data) {
                                    showToast("操作成功，正在刷新页面");
                                    DetailActivity.show(DetailActivity.this, projectName);
                                    finish();
                                }

                                @Override
                                public void onFail(String message) {
                                    showToast(message);
                                }
                            });
                            return false;
                        }
                    });
        });


        rv_comment_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);
        rv_comment_list.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        projectName = getIntent().getStringExtra(PROJECT_NAME);
        if (TextUtils.isEmpty(projectName))
            return;
        HistoryDao.insertHistory(projectName);
        ProjectHelper.getProjectDetail(projectName, this);
        //WaitDialog.show(this, "请稍候");
    }

    @OnClick(R.id.fab)
    void OnFloatingButtonClick() {
        InputDialog.show(this, "评论", "在此输入你对这个项目的意见吧")
                .setOnOkButtonClickListener(this).setCancelButton("取消");
    }

    @OnClick(R.id.tv_file_name)
    void onFileClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(tv_file_name.getText().toString()));
        startActivity(intent);
    }

    @Override
    public boolean onClick(BaseDialog baseDialog, View v, String inputStr) {
        if (TextUtils.isEmpty(inputStr)) {
            showToast("内容不能为空");
        }
        CommentAddModel model = new CommentAddModel();
        model.setProjectName(projectName);
        model.setContent(inputStr);
        CommentHelper.addComment(model, new NetCallback<Object>() {
            @Override
            public void onSuccess(Object data) {
                showToast("评论成功");
                ProjectCommentModel commentModel = new ProjectCommentModel();
                commentModel.setPerson(Account.getUserName());
                commentModel.setAvatar(Account.getAccountAvatar());
                commentModel.setComment(model.getContent());
                commentModels.add(commentModel);
                if (rv_comment_list.getVisibility() == View.GONE) {
                    textView6.setVisibility(View.VISIBLE);
                    rv_comment_list.setVisibility(View.VISIBLE);
                }
                adapter.replace(commentModels);

            }

            @Override
            public void onFail(String message) {
                showToast(message);
            }
        });
        return false;
    }

    @Override
    public void onSuccess(ProjectDetailPageModel data) {
        // WaitDialog.dismiss();
        ProjectDetailModel model = data.getDetail();
        toolbar.setTitleMarginEnd(10);
        toolbar.setTitle(model.getProjectName());
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Glide.with(this).load(model.getAvatar()).placeholder(R.drawable.default_portrait).into(iv_avatar);
        Glide.with(this).load(model.getImage()).placeholder(R.color.colorAccent).into(iv_cover);
        tv_user_name.setText(model.getAuthor());
        tv_user_state.setText(model.getUserState());
        tv_content.setText(model.getContent());
        if (model.getState() == 0) {
            tv_project_state.setText("待审批");
            tv_project_state.setTextColor(getResources().getColor(R.color.yellow_700));
            if (Account.getType()==1){
                fab_accept.setVisibility(View.VISIBLE);
                fab_reject.setVisibility(View.VISIBLE);
            }
        } else if (model.getState() == 1) {
            tv_project_state.setText("已通过");
            tv_project_state.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (model.getState() == 2) {
            tv_project_state.setText("已拒绝");
            tv_project_state.setTextColor(getResources().getColor(R.color.red_700));
        }
        if (TextUtils.isEmpty(model.getFileUrl()) || model.getFileUrl().equals("null")) {
            textView5.setVisibility(View.GONE);
            tv_file_name.setVisibility(View.GONE);
        } else {
            textView5.setVisibility(View.VISIBLE);
            tv_file_name.setText(model.getFileUrl());
        }

        commentModels = data.getComments();
        if (commentModels == null || commentModels.size() == 0) {
            textView6.setVisibility(View.GONE);
            rv_comment_list.setVisibility(View.GONE);
        } else {
            textView6.setVisibility(View.VISIBLE);
            rv_comment_list.setVisibility(View.VISIBLE);
            adapter.replace(commentModels);
        }
    }

    @Override
    public void onFail(String message) {
        TipDialog.show(this, message, TipDialog.TYPE.ERROR);
    }


    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private Context context;
        private List<ProjectCommentModel> commentModels;

        public Adapter(Context context) {
            this.context = context;
            commentModels = new ArrayList<>();
        }

        public void replace(List<ProjectCommentModel> commentModels) {
            this.commentModels.clear();
            this.commentModels.addAll(commentModels);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cell_comment, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectCommentModel model = commentModels.get(position);
            Glide.with(context).load(model.getAvatar()).placeholder(R.drawable.default_portrait).into(holder.iv_avatar);
            holder.tv_user_name.setText(model.getPerson());
            holder.tv_content.setText(model.getComment());
        }

        @Override
        public int getItemCount() {
            return commentModels.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_avatar;
        TextView tv_user_name;
        TextView tv_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
