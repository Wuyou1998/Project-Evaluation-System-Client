package com.wuyou.wybaselibrary.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.helper.ProjectHelper;
import com.wuyou.wybaselibrary.model.project.ProjectDetailModel;
import com.wuyou.wybaselibrary.net.NetCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends BaseActivity implements NetCallback<List<ProjectDetailModel>> {
    public static final String SEARCH_KEY_WORD = "SEARCH_KEY_WORD";
    public static final String SEARCH_KEY_TYPE = "SEARCH_KEY_TYPE";
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.lay_empty)
    LinearLayout lay_empty;
    @BindView(R.id.txt_empty)
    TextView txt_empty;
    @BindView(R.id.rv_search)
    RecyclerView rv_search;
    private Adapter adapter;

    public static void show(Context context, String key, boolean isProjectName) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(SEARCH_KEY_WORD, key);
        intent.putExtra(SEARCH_KEY_TYPE, isProjectName);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        super.initView();
        String key = getIntent().getStringExtra(SEARCH_KEY_WORD);
        if (TextUtils.isEmpty(key)) {
            finish();
            return;
        }
        tv_search.setText(key);
        lay_empty.setVisibility(View.VISIBLE);
        rv_search.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);
        rv_search.setAdapter(adapter);
        rv_search.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        String key = getIntent().getStringExtra(SEARCH_KEY_WORD);
        boolean isProName = getIntent().getBooleanExtra(SEARCH_KEY_TYPE, false);
        if (TextUtils.isEmpty(key)) {
            finish();
            return;
        }

        WaitDialog.show(this, "请稍候");
        if (isProName) {
            ProjectHelper.searchProjectByName(key, this);
        } else {
            ProjectHelper.searchProjectByUser(key, this);
        }
    }

    @Override
    public void onSuccess(List<ProjectDetailModel> data) {
        WaitDialog.dismiss();
        if (data == null || data.size() == 0) {
            txt_empty.setText("没有相关的结果哦");
        } else {
            lay_empty.setVisibility(View.GONE);
            rv_search.setVisibility(View.VISIBLE);
            adapter.replace(data);
        }
    }

    @Override
    public void onFail(String message) {
        TipDialog.show(this, message, TipDialog.TYPE.ERROR);
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private Context context;
        private List<ProjectDetailModel> data;

        public Adapter(Context context) {
            this.context = context;
            data = new ArrayList<>();
        }

        public void replace(List<ProjectDetailModel> data) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cell_project_list_no_image, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectDetailModel model = data.get(position);
            holder.bindData(model);
            holder.itemView.setOnClickListener(v -> {
                DetailActivity.show(context, model.getProjectName());
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cell_avatar;
        TextView cell_title, cell_user_name, cell_content, tv_reason, textView4;
        Button cell_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cell_avatar = itemView.findViewById(R.id.cell_avatar);
            cell_title = itemView.findViewById(R.id.cell_title);
            cell_user_name = itemView.findViewById(R.id.cell_user_name);
            cell_content = itemView.findViewById(R.id.cell_content);
            tv_reason = itemView.findViewById(R.id.tv_reason);
            cell_more = itemView.findViewById(R.id.cell_more);
            textView4 = itemView.findViewById(R.id.textView4);
        }

        @SuppressLint("SetTextI18n")
        public void bindData(ProjectDetailModel model) {
            if (TextUtils.isEmpty(model.getAvatar()) || model.getAvatar().equals("null")) {
                Glide.with(itemView).load(R.drawable.default_portrait).into(cell_avatar);
            } else {
                Glide.with(itemView).load(model.getAvatar()).into(cell_avatar);
            }
            String level = "校级·";
            if (model.getLevel() == 1)
                level = "省部级·";
            if (model.getLevel() == 2)
                level = "国家级·";
            cell_title.setText(level + model.getProjectName());
            cell_content.setText(model.getContent());
            cell_user_name.setText(model.getAuthor());
            if (model.getState() == 0) {
                textView4.setVisibility(View.GONE);
                tv_reason.setVisibility(View.GONE);
            } else {
                textView4.setVisibility(View.VISIBLE);
                tv_reason.setVisibility(View.VISIBLE);
                if (model.getState() == 1) {
                    textView4.setTextColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
                    tv_reason.setTextColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
                    textView4.setText("通过理由:");
                    tv_reason.setText((TextUtils.isEmpty(model.getReason()) || model.getReason().equals("null")) ?
                            "符合条件" : model.getReason());
                } else {
                    textView4.setTextColor(itemView.getContext().getResources().getColor(R.color.red_700));
                    tv_reason.setTextColor(itemView.getContext().getResources().getColor(R.color.red_700));
                    textView4.setText("拒绝理由:");
                    tv_reason.setText((TextUtils.isEmpty(model.getReason()) || model.getReason().equals("null")) ?
                            "条件不符合" : model.getReason());
                }
            }


        }
    }
}
