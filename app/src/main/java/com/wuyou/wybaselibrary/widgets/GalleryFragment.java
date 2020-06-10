package com.wuyou.wybaselibrary.widgets;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wuyou.wybaselibrary.R;

import java.util.Objects;

public class GalleryFragment extends BottomSheetDialogFragment implements GalleryView.SelectedChangeListener  {
    private GalleryView galleryView;
    private OnSelectedListener onSelectedListener;

    public GalleryFragment() {
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(Objects.requireNonNull(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryView = root.findViewById(R.id.gv_gallery);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        galleryView.setUp(LoaderManager.getInstance(this), this);
    }

    @Override
    public void onSelectedCountChanged(int count) {
        //选中一张图片
        if (count > 0)
            //隐藏
            dismiss();
        if (onSelectedListener != null) {
            //得到所有选中图片的路径
            String[] paths = galleryView.getSelectedPath();
            //返回第一张图片路径
            onSelectedListener.onSelectedImage(paths[0]);
            //取消和唤起者之间的引用，加快内存回收
            onSelectedListener = null;
        }

    }


    /**
     * 设置事件监听 返回自己
     *
     * @param onSelectedListener 监听器
     * @return 自己本身
     */
    public GalleryFragment setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
        return this;
    }

    /**
     * 选中图片的监听器
     */
    public interface OnSelectedListener {
        void onSelectedImage(String path);
    }
}
