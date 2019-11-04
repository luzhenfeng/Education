package com.megvii.facetrack;

import java.util.List;

/**
 * @author by licheng on 2017/7/17.
 */
public class MVFace {
    private List<byte[]> cropFace;
    private List<byte[]> originalFace;

    private MVFaceRect faceRect;

    private String errorMessage;

    public List<byte[]> getCropFace() {
        return cropFace;
    }

    public void setCropFace(List<byte[]> cropFace) {
        this.cropFace = cropFace;
    }

    public List<byte[]> getOriginalFace() {
        return originalFace;
    }

    public void setOriginalFace(List<byte[]> originalFace) {
        this.originalFace = originalFace;
    }

    public MVFaceRect getFaceRect() {
        return faceRect;
    }

    public void setFaceRect(MVFaceRect faceRect) {
        this.faceRect = faceRect;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
