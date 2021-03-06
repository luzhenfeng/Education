package com.nhsoft.check.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.nhsoft.base.base.BaseToolBarActivity;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.base.databinding.ActivityBaseToolBarBinding;
import com.nhsoft.base.router.RouterActivityPath;
import com.nhsoft.check.ui.fragment.CheckFragment;

import priv.lzf.mvvmhabit.utils.KLog;

@Route(path = RouterActivityPath.Check.PAGER_CHECK)
public class CheckActivity extends BaseToolBarActivity<ActivityBaseToolBarBinding, ToolbarViewModel> {

    private CheckFragment mCheckFragment;

    @Override
    protected void initToolBar() {
        viewModel.setTitleText("常规检查");
    }

    @Override
    protected Fragment initFragment() {
        mCheckFragment=new CheckFragment();
        return mCheckFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCheckFragment.onActivityResult(requestCode,resultCode,data);
//        KLog.e("data",resultCode);
    }
}
