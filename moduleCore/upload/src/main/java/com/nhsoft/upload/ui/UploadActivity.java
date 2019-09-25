package com.nhsoft.upload.ui;

import android.os.Bundle;

import com.nhsoft.upload.BR;
import com.nhsoft.upload.R;
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
    }
}
