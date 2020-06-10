package com.wuyou.wybaselibrary.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.dao.MessageDao;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MessageFragment extends BaseFragment {
    public static final String ACTION_NEED_RELOAD_DATA = "NEED_RELOAD_DATA";
    @BindView(R.id.lay_empty)
    LinearLayout lay_empty;
    @BindView(R.id.rv_message)
    RecyclerView rv_message;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    private Adapter adapter;
    private MessageSignalReceiver receiver;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Glide.with(this)
                .load(R.mipmap.bg_src_morning)
                .placeholder(R.mipmap.bg_src_morning)
                .transform(new BlurTransformation(14, 2))
                .into(iv_bg);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_NEED_RELOAD_DATA);
        receiver = new MessageSignalReceiver(new WeakReference<>(this));
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, intentFilter);
        lay_empty.setVisibility(View.VISIBLE);
        rv_message.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext());
        rv_message.setAdapter(adapter);
        rv_message.setVisibility(View.GONE);

    }

    @Override
    protected void initData() {
        super.initData();
        refreshData();

    }

    public void refreshData() {
        List<String> messageList = MessageDao.getMessageList();
        if (messageList.size() > 0) {
            rv_message.setVisibility(View.VISIBLE);
            lay_empty.setVisibility(View.GONE);
            adapter.replace(messageList);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
    }

    private static class MessageSignalReceiver extends BroadcastReceiver {

        WeakReference<MessageFragment> weakReference;

        public MessageSignalReceiver(WeakReference<MessageFragment> weakReference) {
            this.weakReference = weakReference;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            weakReference.get().refreshData();
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
            holder.text1.setText(list.get(position));
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
