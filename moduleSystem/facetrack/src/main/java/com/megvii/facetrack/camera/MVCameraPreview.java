package com.megvii.facetrack.camera;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.megvii.bus.R;

import java.util.List;

/**
 * @author by licheng on 2017/7/17.
 */

public class MVCameraPreview extends RelativeLayout {

    private static final boolean WAITING = false;
    private static final boolean WORKING = true;

    private FrameLayout faceRectContainer;
    private CameraPreview cameraPreview;
    private TextView txtErrorView;

    private Context context;

    private int workingCount = 0;
    private int resourceId = 0;

    private SparseBooleanArray workerList = new SparseBooleanArray();
    private SparseArray<FaceView> faceViewList = new SparseArray<>(5);

    public MVCameraPreview(Context context) {
        super(context);
        init(context);
    }

    public MVCameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MVCameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_mv_camera_preview, this, true);
        cameraPreview = (CameraPreview) findViewById(R.id.preview);
        faceRectContainer = (FrameLayout) findViewById(R.id.face_container);
        txtErrorView = (TextView) findViewById(R.id.errorMessage);
    }

    public void refresh(List<RectF> faceRectFs, int imageWidth, int imageHeight) {
        faceRectContainer.removeAllViews();
        if (null == faceRectFs || 0 == faceRectFs.size()) {
            return;
        }

        resetViewWaiting();
        for (int i = 0; i < (faceRectFs.size() - getWaitingCount()) && 5 > faceViewList.size(); i++) {
            FaceView faceView = new FaceView(context, cameraPreview.getMeasuredWidth(), cameraPreview.getMeasuredHeight(), resourceId);
            int id = faceViewList.size() + 1;
            faceViewList.append(id, faceView);
            workerList.append(id, WAITING); // 新加入一个View,处于等待状态
        }

        for (RectF faceRect : faceRectFs) {
            FaceView faceView;
            int viewId = getWorkerId();
            faceView = faceViewList.get(viewId);
            if (null == faceView) {
                return;
            }
            workerList.put(viewId, WORKING);
            workingCount++;
            faceView.onRefresh(faceRect, imageWidth, imageHeight);
            faceRectContainer.addView(faceView);
        }
        invalidate();
    }

    private void resetViewWaiting() {
        for (int i = 1; i < workerList.size() + 1; i++) {
            workerList.put(i, WAITING);
        }
        workingCount = 0;
    }

    private int getWaitingCount() {
        return workerList.size() - workingCount;
    }

    private int getWorkerId() {
        for (int i = 1; i < workerList.size() + 1; i++) {
            if (!workerList.get(i)) { // 没有工作
                return i;
            }
        }
        return 0;
    }

    public CameraPreview getCameraPreview() {
        return cameraPreview;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setErrorMessage(String errorMessage) {
        txtErrorView.setText(errorMessage);
    }
}
