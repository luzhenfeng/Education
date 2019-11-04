package com.megvii.facetrack;

import android.app.Activity;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.util.Log;

import com.megvii.facetrack.camera.CameraManager;
import com.megvii.facetrack.camera.CameraPreview;
import com.megvii.facetrack.camera.MVCameraPreview;
import com.megvii.facetrack.utils.ITracker;

import java.util.ArrayList;
import java.util.List;

import megvii.megfaceandroid.MegfaceAttribute;
import megvii.megfaceandroid.MegfaceCompleteTracker;
import megvii.megfaceandroid.MegfaceFace;
import megvii.megfaceandroid.types.MegfaceImage;

/**
 * @author by licheng on 2017/7/5.
 */
public class FaceTracker implements ITracker, Camera.PreviewCallback, MegfaceCompleteTracker.MegfaceTrackerListener {

    private CameraManager cameraManager;

    private MegfaceCompleteTracker tracker;

    private boolean isRunning;

    private FaceTrackHelper helper;

    @Override
    public void start(Activity activity, @NonNull MVCameraPreview preview, FaceTrackListener listener) {
        start(activity, preview, new FaceTrackOption.Builder().build(), listener);
    }

    @Override
    public void start(Activity activity, @NonNull MVCameraPreview preview, FaceTrackOption option, FaceTrackListener listener) {
        if (!isRunning) {
            if (null == option) {
                option = new FaceTrackOption.Builder().build();
            }
            isRunning = true;
            helper = new FaceTrackHelper(this, option, preview, listener);
            preview.setRotation(option.getRotation());
            preview.setResourceId(option.getResourceId());
            startTracker(activity);
            startCamera(activity, preview.getCameraPreview(), option.isOpenFrontCamera());
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (helper.enableTrackNow()) {
            MegfaceImage image = new MegfaceImage(data.clone(), cameraManager.getBestPreviewSize().width,
                    cameraManager.getBestPreviewSize().height, cameraManager.getRotation(), false);
            tracker.track(image);
        }
    }

    @Override
    public void onDetect(List<MegfaceFace> list, MegfaceImage megfaceImage, MegfaceImage original) {
        if (isRunning) {
            if (null != list && 0 < list.size()) {
                helper.doFaceTracked(list, megfaceImage, original);
            }
        }
    }

    private void startTracker(Activity activity) {
        try {
            ArrayList<MegfaceAttribute.MegfaceAttributeType> attrs = new ArrayList<>();
            attrs.add(MegfaceAttribute.MegfaceAttributeType.QUALITY);
            attrs.add(MegfaceAttribute.MegfaceAttributeType.POSE);
            attrs.add(MegfaceAttribute.MegfaceAttributeType.BRIGHTNESS);
            attrs.add(MegfaceAttribute.MegfaceAttributeType.EYESTATUS);
            attrs.add(MegfaceAttribute.MegfaceAttributeType.MONO);
            tracker = new MegfaceCompleteTracker(activity, "tracker_mobile_v3_fast.bin", this, 1, attrs);
        } catch (Exception e) {
            Log.e("tracker", "Tracker init fail");
            return;
        }
        tracker.start();
    }

    private void startCamera(Activity activity, CameraPreview preview, boolean isFront) {
        cameraManager = new CameraManager(activity, this, preview);
        cameraManager.openCamera(isFront);
    }

    public void takePicture() {
        stop();
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
            tracker.close();
            cameraManager.closeCamera();
            cameraManager = null;
            tracker = null;
        }
    }
}
