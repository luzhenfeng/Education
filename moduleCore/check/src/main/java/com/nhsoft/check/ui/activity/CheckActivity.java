package com.nhsoft.check.ui.activity;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nhsoft.base.base.BaseToolBarActivity;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.base.databinding.ActivityBaseToolBarBinding;
import com.nhsoft.base.router.RouterActivityPath;
import com.nhsoft.check.ui.fragment.CheckFragment;

@Route(path = RouterActivityPath.Check.PAGER_CHECK)
public class CheckActivity extends BaseToolBarActivity<ActivityBaseToolBarBinding, ToolbarViewModel> {

    @Override
    protected void initToolBar() {
        viewModel.setTitleText("常规检查");
    }

    @Override
    protected Fragment initFragment() {
        return new CheckFragment();
    }
}
