package com.lzf.face.viewModel;

import android.app.Activity;
import android.app.Application;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.lzf.face.util.IntentDataHelper;
import com.megvii.facetrack.FaceTrackListener;
import com.megvii.facetrack.FaceTrackOption;
import com.megvii.facetrack.FaceTracker;
import com.megvii.facetrack.MVFace;
import com.megvii.facetrack.camera.MVCameraPreview;

import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/1.
 */
public class FaceViewModel extends BaseViewModel implements FaceTrackListener {

    public FaceTrackOption option;
    public FaceTracker faceTracker;
    public FaceViewModel(@NonNull Application application) {
        super(application);
        option=new FaceTrackOption.Builder()
                .build();
        faceTracker = new FaceTracker();
    }

    public void startTrack(Activity activity,MVCameraPreview cameraPreview) {
        faceTracker.start(activity, cameraPreview, option, this);
    }

    @Override
    public void onTrackCompleted(MVFace face) {
        // 质量判断
        String errorMessage = face.getErrorMessage();
        KLog.e(errorMessage);
//        cameraPreview.setErrorMessage(errorMessage);
        if (!TextUtils.isEmpty(errorMessage)) {
//            takePicture.setVisibility(View.GONE);
            return;
        }
        // 有没有人脸
        if (null == face.getCropFace() || 0 == face.getCropFace().size()) {
//            takePicture.setVisibility(View.GONE);
            return;
        }
        IntentDataHelper.setFaceList(face.getCropFace());
        IntentDataHelper.setBigFaceList(face.getOriginalFace());
        if (option.isEnableAutoStop()) {
            ToastUtils.showShort("成功");
            finish();
        }
    }



    @Override
    public void onStop() {
        super.onStop();
        faceTracker.stop();
    }
}
