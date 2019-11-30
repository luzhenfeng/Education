package com.nhsoft.check.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nhsoft.base.base.BaseToolBarActivity;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.base.databinding.ActivityBaseToolBarBinding;
import com.nhsoft.base.router.RouterActivityPath;
import com.nhsoft.check.R;
import com.nhsoft.check.ui.fragment.DormRollCallFragment;

import priv.lzf.mvvmhabit.base.BaseViewModel;

@Route(path = RouterActivityPath.Check.PAGER_Dorm)
public class DormRollCallActivity extends BaseToolBarActivity<ActivityBaseToolBarBinding, ToolbarViewModel> {
//    private DormRollCallFragment mDormRollCallFragment;

    @Override
    protected void initToolBar() {
        viewModel.setTitleText("寝室点名");
    }

    @Override
    protected Fragment initFragment() {
        return new DormRollCallFragment();
    }
}
