package com.wuyou.wybaselibrary.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.activity.DetailActivity;
import com.wuyou.wybaselibrary.dao.CollectionDao;
import com.wuyou.wybaselibrary.model.project.ProjectDetailModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeListFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView list;

    private Adapter adapter;


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        assert getContext() != null;
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext());
        list.setAdapter(adapter);
    }

    public void setData(List<ProjectDetailModel> modelList) {
        adapter.setModelList(modelList);
    }

    private static class Adapter extends RecyclerView.Adapter {
        private Context context;
        private static final int CELL_NORMAL = 1001;
        private static final int CELL_NO_IMAGE = 1002;
        private List<ProjectDetailModel> modelList;

        public Adapter(Context context) {
            this.context = context;
            modelList = new ArrayList<>();
        }

        public void setModelList(List<ProjectDetailModel> modelList) {
            this.modelList.clear();
            this.modelList.addAll(modelList);
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return modelList.size();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case CELL_NORMAL:
                    view = LayoutInflater.from(context).inflate(R.layout.cell_project_list, parent,
                            false);
                    return new ViewHolder(view);
                case CELL_NO_IMAGE:
                    view = LayoutInflater.from(context).inflate(R.layout.cell_project_list_no_image, parent,
                            false);
                    return new NoImageViewHolder(view);
                default:
                    TextView textView = new TextView(context);
                    textView.setText("没有这种类型的cell");
                    view = textView;
                    break;

            }

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ProjectDetailModel model = modelList.get(position);
            if (getItemViewType(position) == CELL_NORMAL) {
                ViewHolder holderNormal = (ViewHolder) holder;
                holderNormal.bindData(model);
                holderNormal.cell_more.setOnClickListener(v -> {
                    showPopMenu(context, v, () -> {
                        CollectionDao.insertCollection(model.getProjectName());
                    });
                });
            }

            if (getItemViewType(position) == CELL_NO_IMAGE) {
                NoImageViewHolder noImageViewHolder = (NoImageViewHolder) holder;
                noImageViewHolder.bindData(model);
                noImageViewHolder.cell_more.setOnClickListener(v -> {
                    showPopMenu(context, v, () -> {
                        CollectionDao.insertCollection(model.getProjectName());

                    });
                });
            }


            holder.itemView.setOnClickListener(v -> {
                DetailActivity.show(context, model.getProjectName());
            });

        }

        @Override
        public int getItemViewType(int position) {
            ProjectDetailModel model = modelList.get(position);
            if (!TextUtils.isEmpty(model.getImage()) && !model.getImage().equals("null"))
                return CELL_NORMAL;
            else
                return CELL_NO_IMAGE;
        }
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cell_avatar;
        TextView cell_title, cell_user_name, cell_content, tv_reason, textView4;
        ImageView cell_cover_image;
        Button cell_share, cell_more;
        CheckBox cell_cb_favorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cell_avatar = itemView.findViewById(R.id.cell_avatar);
            cell_title = itemView.findViewById(R.id.cell_title);
            cell_user_name = itemView.findViewById(R.id.cell_user_name);
            cell_content = itemView.findViewById(R.id.cell_content);
            tv_reason = itemView.findViewById(R.id.tv_reason);
            cell_cover_image = itemView.findViewById(R.id.cell_cover_image);
            cell_share = itemView.findViewById(R.id.cell_share);
            cell_more = itemView.findViewById(R.id.cell_more);
            cell_cb_favorite = itemView.findViewById(R.id.cell_cb_favorite);
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
            Glide.with(itemView).load(model.getImage()).into(cell_cover_image);

        }
    }

    private static class NoImageViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cell_avatar;
        TextView cell_title, cell_user_name, cell_content, tv_reason, textView4;
        Button cell_share, cell_more;
        CheckBox cell_cb_favorite;

        public NoImageViewHolder(@NonNull View itemView) {
            super(itemView);
            cell_avatar = itemView.findViewById(R.id.cell_avatar);
            cell_title = itemView.findViewById(R.id.cell_title);
            cell_user_name = itemView.findViewById(R.id.cell_user_name);
            cell_content = itemView.findViewById(R.id.cell_content);
            tv_reason = itemView.findViewById(R.id.tv_reason);
            cell_share = itemView.findViewById(R.id.cell_share);
            cell_more = itemView.findViewById(R.id.cell_more);
            cell_cb_favorite = itemView.findViewById(R.id.cell_cb_favorite);
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

    public static void showPopMenu(Context context, View view, Runnable r) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.pop, popupMenu.getMenu());
        popupMenu.show();
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(item -> {
            // 控件每一个item的点击事件
            if (item.getItemId() == R.id.menu_collect) {
                r.run();
            }
            return true;
        });
    }
}
