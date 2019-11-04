package com.megvii.facetrack.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author  by licheng on 2017/7/5.
 */
public class CameraPreview extends TextureView implements TextureView.SurfaceTextureListener, CameraManager.ICameraListener {

    public static final float PERSENT_20 = 0.20f;
    public static final float PERSENT_25 = 0.25f;
    public static final float PERSENT_30 = 0.30f;
    public static final float PERSENT_35 = 0.35f;
    public static final float PERSENT_40 = 0.40f;
    public static final float PERSENT_45 = 0.45f;
    public static final float PERSENT_50 = 0.50f;
    public static final float PERSENT_55 = 0.55f;
    public static final float PERSENT_60 = 0.60f;
    public static final float PERSENT_65 = 0.65f;
    public static final float PERSENT_70 = 0.70f;
    public static final float PERSENT_75 = 0.75f;
    public static final float PERSENT_80 = 0.80f;
    public static final float PERSENT_85 = 0.85f;
    public static final float PERSENT_90 = 0.90f;
    public static final float PERSENT_95 = 0.95f;

    private float size = 1.0f;

    private CameraManager cameraManager;

    public CameraPreview(Context context) {
        super(context);
        init();
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setSurfaceTextureListener(this);
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (null != cameraManager) {
            cameraManager.startPreview(surface);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (null != cameraManager) {
            cameraManager.closeCamera();
        }
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onCameraInitCompleted(CameraManager cameraManager, int width, int height) {
        this.cameraManager = cameraManager;
//        ViewParent parentView = getParent();
//        if (parentView instanceof LinearLayout) {
//            setLayoutParams(new LinearLayout.LayoutParams(width, height));
//        } else if (parentView instanceof RelativeLayout) {
//            setLayoutParams(new RelativeLayout.LayoutParams(width, height));
//        } else if (parentView instanceof FrameLayout) {
//            setLayoutParams(new FrameLayout.LayoutParams(width, height));
//        } else {
//            Log.e("CameraPreview", "init failed");
//        }
    }
}
