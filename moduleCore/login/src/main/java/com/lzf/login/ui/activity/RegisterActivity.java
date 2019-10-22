package com.lzf.login.ui.activity;

import android.support.v4.app.Fragment;
import android.view.View;

import com.lzf.login.ui.fragment.RegisterFragment;
import com.nhsoft.base.base.BaseToolBarActivity;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.base.databinding.ActivityBaseToolBarBinding;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/21.
 */
public class RegisterActivity extends BaseToolBarActivity<ActivityBaseToolBarBinding, ToolbarViewModel> {
    @Override
    protected void initToolBar() {
        viewModel.setTitleText("注册");
        viewModel.setLeftIconVisible(View.GONE);
    }

    @Override
    protected Fragment initFragment() {
        return new RegisterFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
