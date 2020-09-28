package com.lzf.login.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzf.greendao.utils.DataUtils;
import com.lzf.login.ui.fragment.LoginFragment;
import com.nhsoft.base.base.BaseToolBarActivity;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.base.databinding.ActivityBaseToolBarBinding;
import com.nhsoft.base.router.RouterActivityPath;


@Route(path = RouterActivityPath.Login.PAGER_LOGIN)
public class LoginActivity extends BaseToolBarActivity<ActivityBaseToolBarBinding, ToolbarViewModel> {

    @Override
    protected void initToolBar() {
        viewModel.setLeftIconVisible(View.INVISIBLE);
        viewModel.setTitleText("德育日常管理平台");
    }

    @Override
    protected Fragment initFragment() {
        return new LoginFragment();
    }
}
