package com.megvii.facetrack.utils;

import android.app.Activity;

import com.megvii.facetrack.FaceTrackListener;
import com.megvii.facetrack.FaceTrackOption;
import com.megvii.facetrack.camera.MVCameraPreview;

/**
 * @author by licheng on 2017/7/17.
 */

public interface ITracker {
    void start(Activity activity, MVCameraPreview preview, FaceTrackListener listener);

    void start(Activity activity, MVCameraPreview preview, FaceTrackOption option, FaceTrackListener listener);
}
