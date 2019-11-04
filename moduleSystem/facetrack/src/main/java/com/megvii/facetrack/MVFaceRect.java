package com.megvii.facetrack;

import android.graphics.RectF;

import java.util.List;

/**
 * @author by licheng on 2017/7/17.
 */
public class MVFaceRect {
    private List<RectF> faceRectFs;
    private int imageWidth;
    private int imageHeight;

    public List<RectF> getFaceRectFs() {
        return faceRectFs;
    }

    public void setFaceRectFs(List<RectF> faceRectFs) {
        this.faceRectFs = faceRectFs;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
}
