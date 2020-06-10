package com.wuyou.wybaselibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected View mRoot;
    protected Unbinder unbinder;
    protected boolean needRequestData = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            View root = inflater.inflate(getContentLayoutId(), container, false);
            initView(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                //当fragment被回收，mRoot还没被回收，将mRoot从其父布局移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (needRequestData) {
            initData();
            needRequestData = false;
        }
    }


    /**
     * 得到当前资源文件id
     *
     * @return 资源文件id
     */
    protected abstract int getContentLayoutId();

    //初始化UI
    protected void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    //初始化数据
    protected void initData() {

    }

}

