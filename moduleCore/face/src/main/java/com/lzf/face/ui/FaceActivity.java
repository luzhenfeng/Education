package com.lzf.face.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzf.face.BR;
import com.lzf.face.R;
import com.lzf.face.databinding.ActivityFaceBinding;
import com.lzf.face.viewModel.FaceViewModel;
import com.nhsoft.base.router.RouterActivityPath;

import priv.lzf.mvvmhabit.base.BaseActivity;

@Route(path = RouterActivityPath.Face.PAGER_FACE)
public class FaceActivity extends BaseActivity<ActivityFaceBinding, FaceViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_face;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.startTrack(this,binding.cameraPreview);
    }
}
