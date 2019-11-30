package com.lzf.face.ui;

import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzf.face.BR;
import com.lzf.face.R;
import com.lzf.face.databinding.ActivityFaceBinding;
import com.lzf.face.viewModel.FaceViewModel;
import com.nhsoft.base.router.RouterActivityPath;

import priv.lzf.mvvmhabit.base.BaseActivity;

@RequiresApi(api = Build.VERSION_CODES.O)
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

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.cameraPreview.setErrorMessage(s);
            }
        });
    }
}
