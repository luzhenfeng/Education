package com.nhsoft.upload.ui.activity;

import android.support.v4.app.Fragment;
import android.view.View;

import com.nhsoft.base.base.BaseToolBarActivity;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.upload.databinding.ActivityUploadBinding;
import com.nhsoft.upload.ui.fragment.UploadFragment;
import com.nhsoft.upload.ui.fragment.UploadViewPageFragment;

public class UploadActivity extends BaseToolBarActivity<ActivityUploadBinding,ToolbarViewModel> {
    @Override
    protected void initToolBar() {
        viewModel.setRightText("编辑");
        viewModel.setTitleText("上传文件");
        viewModel.setRightTextVisible(View.VISIBLE);
    }

    @Override
    protected Fragment initFragment() {
        return new UploadFragment();
    }


//    @Override
//    public int initContentView(Bundle savedInstanceState) {
//        return R.layout.activity_upload;
//    }
//
//    @Override
//    public int initVariableId() {
//        return BR.viewModel;
//    }
//
//
//    @Override
//    public void initData() {
//        super.initData();
//        viewModel.initToolbar();
//        // 使用 TabLayout 和 ViewPager 相关联
//        binding.tabLayout.setupWithViewPager(binding.viewPager);
//        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
//        //给ViewPager设置adapter
//        binding.setAdapter(new ViewPagerBindingAdapter());
//    }
}
