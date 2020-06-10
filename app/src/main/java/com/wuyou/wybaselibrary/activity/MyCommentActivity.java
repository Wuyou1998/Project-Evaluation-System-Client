package com.wuyou.wybaselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.helper.RecordHelper;
import com.wuyou.wybaselibrary.model.comment.MyCommentModel;
import com.wuyou.wybaselibrary.net.NetCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyCommentActivity extends BaseActivity implements NetCallback<List<MyCommentModel>> {
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.lay_empty)
    LinearLayout lay_empty;
    @BindView(R.id.txt_empty)
    TextView txt_empty;
    @BindView(R.id.rv_search)
    RecyclerView rv_search;
    private Adapter adapter;

    public static void show(Context context) {
        context.startActivity(new Intent(context, MyCommentActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_comment;
    }

    @Override
    protected void initView() {
        super.initView();
        lay_empty.setVisibility(View.VISIBLE);
        rv_search.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);
        rv_search.setAdapter(adapter);
        rv_search.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        RecordHelper.getAllMyComments(this);
    }

    @Override
    public void onSuccess(List<MyCommentModel> data) {
        lay_empty.setVisibility(View.GONE);
        rv_search.setVisibility(View.VISIBLE);
        adapter.replace(data);
    }

    @Override
    public void onFail(String message) {
        txt_empty.setText(message);
    }


    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private Context context;
        private List<MyCommentModel> myCommentModels;

        public Adapter(Context context) {
            this.context = context;
            myCommentModels = new ArrayList<>();
        }

        public void replace(List<MyCommentModel> myCommentModels) {
            this.myCommentModels.clear();
            this.myCommentModels.addAll(myCommentModels);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MyCommentModel myCommentModel = myCommentModels.get(position);
            holder.tv_title.setText(myCommentModel.getProjectName());
            holder.tv_subTitle.setText(myCommentModel.getComment());
            holder.itemView.setOnClickListener(v -> {
                DetailActivity.show(context, myCommentModel.getProjectName());
            });
        }

        @Override
        public int getItemCount() {
            return myCommentModels.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_subTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(android.R.id.text1);
            tv_subTitle = itemView.findViewById(android.R.id.text2);
        }
    }
}
