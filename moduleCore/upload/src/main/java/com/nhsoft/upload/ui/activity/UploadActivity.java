package com.nhsoft.upload.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.nhsoft.upload.BR;
import com.nhsoft.upload.R;
import com.nhsoft.upload.adapter.ViewPagerBindingAdapter;
import com.nhsoft.upload.databinding.ActivityUploadBinding;
import com.nhsoft.upload.viewModel.UploadViewModel;

import priv.lzf.mvvmhabit.base.BaseActivity;

public class UploadActivity extends BaseActivity<ActivityUploadBinding, UploadViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_upload;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    @Override
    public void initData() {
        super.initData();
        viewModel.initToolbar();
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        //给ViewPager设置adapter
        binding.setAdapter(new ViewPagerBindingAdapter());

    }
}
