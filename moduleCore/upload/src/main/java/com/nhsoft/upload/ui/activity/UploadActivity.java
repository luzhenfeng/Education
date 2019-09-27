package com.nhsoft.upload.ui.activity;

import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nhsoft.base.base.BaseToolBarActivity;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.base.databinding.ActivityBaseToolBarBinding;
import com.nhsoft.base.router.RouterActivityPath;
import com.nhsoft.upload.ui.fragment.UploadFragment;

@Route(path = RouterActivityPath.Upload.PAGER_UPLOAD)
public class UploadActivity extends BaseToolBarActivity<ActivityBaseToolBarBinding,ToolbarViewModel> {
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
}
