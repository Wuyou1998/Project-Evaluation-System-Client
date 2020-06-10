package com.wuyou.wybaselibrary.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.wuyou.wybaselibrary.R;
import com.wuyou.wybaselibrary.activity.SearchActivity;
import com.wuyou.wybaselibrary.helper.PageHelper;
import com.wuyou.wybaselibrary.model.page.MainPageModel;
import com.wuyou.wybaselibrary.net.NetCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class HomeFragment extends BaseFragment implements NetCallback<MainPageModel>, MaterialSearchBar.OnSearchActionListener {
    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.tabBar)
    TabLayout tabBar;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    HomeListFragment penddingFragment, passedFragment, rejectedFragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Glide.with(this)
                .load(R.mipmap.bg_src_morning)
                .placeholder(R.mipmap.bg_src_morning)
                .transform(new BlurTransformation(14, 2))
                .into(iv_bg);
        penddingFragment = new HomeListFragment();
        passedFragment = new HomeListFragment();
        rejectedFragment = new HomeListFragment();
        List<HomeListFragment> homeListFragments = new ArrayList<>();
        homeListFragments.add(penddingFragment);
        homeListFragments.add(passedFragment);
        homeListFragments.add(rejectedFragment);
        viewPager.setAdapter(new Adapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, homeListFragments));
        tabBar.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabBar.getTabAt(1)).select();
        searchBar.setOnSearchActionListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        PageHelper.getMainPage(this);
        WaitDialog.show((AppCompatActivity) getContext(), "请稍候");
    }

    @Override
    public void onSuccess(MainPageModel data) {
        penddingFragment.setData(data.getPenddingProjects());
        passedFragment.setData(data.getPassedProjects());
        rejectedFragment.setData(data.getRejectedProjects());
        WaitDialog.dismiss();
    }

    @Override
    public void onFail(String message) {
        TipDialog.show((AppCompatActivity) getContext(), message, TipDialog.TYPE.ERROR);
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        //searchBar状态改变时回调，用不到
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        if (!TextUtils.isEmpty(text.toString().trim())) {
            SearchActivity.show(getContext(), text.toString().trim(), true);
            searchBar.closeSearch();
        }


    }

    @Override
    public void onButtonClicked(int buttonCode) {
        //用不到 ，也没了解触发时机
    }

    private static class Adapter extends FragmentPagerAdapter {
        List<HomeListFragment> homeListFragments;
        List<String> titles = Arrays.asList("待审批", "推荐", "未通过");

        public Adapter(@NonNull FragmentManager fm, int behavior, List<HomeListFragment> homeListFragments) {
            super(fm, behavior);
            this.homeListFragments = homeListFragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return homeListFragments.get(position);
        }

        @Override
        public int getCount() {
            return homeListFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
