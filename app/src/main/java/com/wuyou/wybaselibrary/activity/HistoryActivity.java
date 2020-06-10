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
import com.wuyou.wybaselibrary.dao.HistoryDao;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

    public class HistoryActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.lay_empty)
    LinearLayout lay_empty;
    @BindView(R.id.txt_empty)
    TextView txt_empty;
    @BindView(R.id.rv_title)
    RecyclerView rv_title;
    private Adapter adapter;

    public static void show(Context context) {
        context.startActivity(new Intent(context, HistoryActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history_and_collection;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("浏览历史");
        lay_empty.setVisibility(View.VISIBLE);
        rv_title.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);
        rv_title.setAdapter(adapter);
        rv_title.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        List<String> list = HistoryDao.getHistoryList();
        if (list.size() > 0) {
            lay_empty.setVisibility(View.GONE);
            rv_title.setVisibility(View.VISIBLE);
            adapter.replace(list);
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ViewHolder> {
        private Context context;
        private LinkedList<String> list;

        public Adapter(Context context) {
            this.context = context;
            this.list = new LinkedList<>();
        }

        public void replace(List<String> newList) {
            list.clear();
            list.addAll(newList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String projectName = list.get(position);
            holder.text1.setText(projectName);
            holder.itemView.setOnClickListener(v->{
                DetailActivity.show(context,projectName);
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
        }
    }
}
