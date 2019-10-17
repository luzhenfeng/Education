package com.nhsoft.check.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.nhsoft.base.base.BaseToolBarActivity;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.base.databinding.ActivityBaseToolBarBinding;
import com.nhsoft.base.router.RouterActivityPath;
import com.nhsoft.check.ui.fragment.PhotoFragment;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/18.
 */
@Route(path = RouterActivityPath.Check.PAGER_PHOTO)
public class PhotoActivity extends BaseToolBarActivity<ActivityBaseToolBarBinding, ToolbarViewModel> {

    private PhotoFragment mPhotoFragment;
    @Override
    protected void initToolBar() {
        viewModel.setTitleText("查看图片");
    }

    @Override
    protected Fragment initFragment() {
        mPhotoFragment=new PhotoFragment();
        return mPhotoFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPhotoFragment.onActivityResult(requestCode,resultCode,data);
//        KLog.e("data",resultCode);
    }
}
