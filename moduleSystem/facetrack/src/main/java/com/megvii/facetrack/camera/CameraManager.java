package com.megvii.facetrack.camera;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import java.io.IOException;

/**
 * 作为所有Camera的统一入口类
 * <p>
 * Created by licheng on 2017/7/4.
 */
public class CameraManager {

    private static final String TAG = "CameraManager";

    private Camera camera;

    private Camera.Size bestPreviewSize;

    private Camera.PreviewCallback callback;

    private ICameraListener cameraListener;

    private int rotation;

    private Activity activity;

    public CameraManager(Activity activity, Camera.PreviewCallback callback, ICameraListener cameraListener) {
        this.activity = activity;
        this.callback = callback;
        this.cameraListener = cameraListener;
    }

    public void openCamera(boolean isFront) {
        int cameraId = isFront ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
        try {
            camera = Camera.open(cameraId);
        } catch (Exception e) {
            int count = Camera.getNumberOfCameras();
            if (count > 0) {
                cameraId = 0;
                camera = Camera.open(cameraId);
            } else {
                cameraId = -1;
                camera = null;
            }
        }
        if (null != camera) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, cameraInfo);
            Camera.Parameters params = camera.getParameters();
            // 最佳预览尺寸
            bestPreviewSize = CameraUtil.getBestPreviewSize(camera.getParameters(), 1280, 720);
            params.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
            rotation = CameraUtil.getCameraRotation(activity, cameraId);
            camera.setDisplayOrientation(rotation);
            camera.setParameters(params);
            camera.setPreviewCallback(callback);
            // 根据屏幕尺寸计算合适的预览框大小
            Point point = CameraUtil.geAdjustSize(activity, camera);
            // 相机初始化结束
            if (null != cameraListener) {
                cameraListener.onCameraInitCompleted(this, point.x, point.y);
            }
        }
    }

    public void startPreview(SurfaceTexture surface) {
        if (null != camera) {
            try {
                camera.setPreviewTexture(surface);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return;
            }
            camera.startPreview(); // 启动预览
        }
    }

    public void closeCamera() {
        if (null != camera) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    public Camera.Size getBestPreviewSize() {
        return bestPreviewSize;
    }

    public int getRotation() {
        return rotation;
    }

    public interface ICameraListener {
        void onCameraInitCompleted(CameraManager cameraManager, int width, int height);
    }
}
