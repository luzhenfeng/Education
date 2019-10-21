package com.lzf.login.ui.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lzf.login.ui.fragment.SelectMCodeFragment;
import com.nhsoft.base.base.BaseToolBarActivity;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.base.databinding.ActivityBaseToolBarBinding;

import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.utils.MaterialDialogUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/19.
 */
public class SelectMCodeActivity extends BaseToolBarActivity<ActivityBaseToolBarBinding, ToolbarViewModel> {
    @Override
    protected void initToolBar() {
        viewModel.setLeftIconVisible(View.GONE);
        viewModel.setTitleText("鄞州职高德育管理");
    }

    @Override
    protected Fragment initFragment() {
        return new SelectMCodeFragment();
    }

    @Override
    public void onBackPressed() {
        MaterialDialogUtils.showBasicDialogNoTitle(this,"是否确认退出")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                        AppManager.getAppManager().AppExit();
                    }
                }).show();
    }
}
