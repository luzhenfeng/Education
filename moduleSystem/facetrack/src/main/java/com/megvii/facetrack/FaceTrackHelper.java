package com.megvii.facetrack;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.text.TextUtils;

import com.megvii.facetrack.camera.MVCameraPreview;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import megvii.megfaceandroid.MegfaceFace;
import megvii.megfaceandroid.types.MegfaceImage;
import megvii.megfaceandroid.util.MegfaceUtil;

/**
 * @author by licheng on 2017/7/14.
 */
class FaceTrackHelper {

    private FaceTrackOption option;
    private FaceTracker faceTracker;
    private FaceTrackListener faceTrackListener;

    private MVFace result = new MVFace();
    private List<byte[]> cropFace = new ArrayList<>();
    private List<byte[]> originalFace = new ArrayList<>();
    private MVFaceRect faceRect = new MVFaceRect();
    //  private List<Long> faceIds = new ArrayList<>();

    private MVCameraPreview preview;

    private List<RectF> rectFs = new ArrayList<>();

    FaceTrackHelper(FaceTracker faceTracker, FaceTrackOption option, MVCameraPreview preview, FaceTrackListener faceTrackListener) {
        this.faceTracker = faceTracker;
        this.option = option;
        this.preview = preview;
        this.faceTrackListener = faceTrackListener;
    }

    void doFaceTracked(List<MegfaceFace> faces, MegfaceImage megfaceImage, MegfaceImage original) {
        // 脸部的框框数据
        countFaceRectFs(faces);
        // 刷新UI
        refreshPreviewIfNeed(megfaceImage);
        // 质量判断
        String errorMessage = QualityFilter.filter(faces.get(0), option.getQualityOption());
        result.setErrorMessage(errorMessage);
        faceTrackListener.onTrackCompleted(result);
        if (!TextUtils.isEmpty(errorMessage)) {
            return;
        }
        if (0 < faces.size()) {
            // 加入到返回值
            faceRect.setFaceRectFs(rectFs);
            // 脸部图片数据
            addFaceIfNeed(faces, megfaceImage, original);
            doReturn();
        }
    }

    private void doReturn() {
        // 图片还没有到达上限
        if (option.canAddTrackCount(cropFace.size())) {
            if (option.isModeTakePicture()) { // 拍照模式，无数据也返回，要控制图片的显示
                faceTrackListener.onTrackCompleted(result);
            }
            return;
        }
        result.setCropFace(cropFace);
        result.setOriginalFace(originalFace);
        result.setFaceRect(faceRect);
        faceTrackListener.onTrackCompleted(result);
        if (option.isEnableAutoStop()) {
            faceTracker.stop();
        }
        cropFace.clear();
        originalFace.clear();
    }

    private void addFaceIfNeed(List<MegfaceFace> faceList, MegfaceImage megfaceImage, MegfaceImage original) {
        if (null == faceList || 0 == faceList.size()) {
            return;
        }
        if (option.canAddTrackCount(cropFace.size())) {
            // 得到按照人脸位置中的坐标切出的图
            MegfaceImage smallFace = MegfaceUtil.cropFaceNv21Image(faceList.get(0), megfaceImage);
            cropFace.add(faceImage2Bitmap(smallFace));
            // 得到一张包含脸及脸周围的大图
            MegfaceImage bigFace = MegfaceUtil.cropBigFace(faceList.get(0), megfaceImage, 1.0f);
            originalFace.add(faceImage2Bitmap(bigFace));
        }
    }

    private void refreshPreviewIfNeed(MegfaceImage megfaceImage) {
        if (null != preview && option.showRectView()) {
            preview.refresh(rectFs, megfaceImage.width, megfaceImage.height);
        }
    }

    private List<RectF> countFaceRectFs(List<MegfaceFace> faces) {
        rectFs.clear();
        for (MegfaceFace face : faces) {
            RectF srcRect = new RectF(face.rect.left, face.rect.top, face.rect.right, face.rect.bottom);
            rectFs.add(srcRect);
        }
        return rectFs;
    }

    private static byte[] faceImage2Bitmap(MegfaceImage megfaceImage) {
        YuvImage yuv = new YuvImage(megfaceImage.image, ImageFormat.NV21, megfaceImage.width,
                megfaceImage.height, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, megfaceImage.width, megfaceImage.height), 100, os);
        return os.toByteArray();
    }

    boolean enableTrackNow() {
        return option.isEnableTrackNow();
    }

    public void setPreview(MVCameraPreview preview) {
        this.preview = preview;
    }
}
