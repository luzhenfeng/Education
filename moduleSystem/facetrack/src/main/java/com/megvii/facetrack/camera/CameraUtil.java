package com.megvii.facetrack.camera;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by licheng on 2017/7/5.
 */
class CameraUtil {

    /**
     * 通过传入的宽高算出最接近于宽高值的相机大小
     */
    static Camera.Size getBestPreviewSize(Camera.Parameters camPara, final int width, final int height) {
        List<Camera.Size> allSupportedSize = camPara.getSupportedPreviewSizes();
        ArrayList<Camera.Size> widthLargerSize = new ArrayList<>();
        for (Camera.Size tmpSize : allSupportedSize) {
            if (tmpSize.width > tmpSize.height) {
                widthLargerSize.add(tmpSize);
            }
        }

        Collections.sort(widthLargerSize, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                int off_one = Math.abs(lhs.width * lhs.height - width * height);
                int off_two = Math.abs(rhs.width * rhs.height - width * height);
                return off_one - off_two;
            }
        });
        return widthLargerSize.get(0);
    }

    /**
     * 获取照相机旋转角度
     */
    static int getCameraRotation(Activity activity, int cameraId) {
        int rotateAngle;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotateAngle = (info.orientation + degrees) % 360;
            rotateAngle = (360 - rotateAngle) % 360; // compensate the mirror
        } else { // back-facing
            rotateAngle = (info.orientation - degrees + 360) % 360;
        }
        return rotateAngle;
    }

    /**
     * 通过屏幕参数、相机预览尺寸计算布局参数
     */
    static Point geAdjustSize(Context context, Camera camera) {
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, metrics);
        } catch (Exception e) {
            e.printStackTrace();
        }
        float scale;
        if (isPortrait(context)) {
            scale = Math.min(metrics.widthPixels * 1.0f / previewSize.height,
                    metrics.heightPixels * 1.0f / previewSize.width);
        } else {
            scale = Math.min(metrics.heightPixels * 1.0f / previewSize.height,
                    metrics.widthPixels * 1.0f / previewSize.width);
        }
        int layout_width = (int) (scale * previewSize.height);
        int layout_height = (int) (scale * previewSize.width);
        if (isPortrait(context)) {
            return new Point(layout_width, layout_height);
        } else {
            return new Point(layout_height, layout_width);
        }
    }

    static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
