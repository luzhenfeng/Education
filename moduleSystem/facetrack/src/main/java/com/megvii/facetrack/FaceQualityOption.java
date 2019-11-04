package com.megvii.facetrack;

import java.io.Serializable;

/**
 * @author by licheng on 2017/8/16.
 */

public class FaceQualityOption implements Serializable {

    public static final int DEFAULT_VALUE_FACE_MIN = 30;
    public static final int DEFAULT_VALUE_PITCH = 30;
    public static final int DEFAULT_VALUE_ROLL = 30;
    public static final int DEFAULT_VALUE_YAW = 30;
    public static final int DEFAULT_VALUE_BRIGHT_MAX = 210;
    public static final int DEFAULT_VALUE_BRIGHT_MIN = 75;
    public static final int DEFAULT_VALUE_BRIGHT_STD = 60;
    public static final float DEFAULT_VALUE_QUALITY = 0.5f;
    public static final boolean DEFAULT_VALUE_MONO = false;
    public static final boolean DEFAULT_VALUE_GLASS = true;
    public static final boolean DEFAULT_CLOSE_EYE = false;
    public static final boolean DEFAULT_VALUE_DARK_GLASS = false;

    private static final long serialVersionUID = 8431634521157945874L;

    private int faceMin;

    private float pitch;
    private float roll;
    private float yaw;

    private float brightMax;
    private float brightMin;
    private float brightStd;

    private float quality;

    private boolean mono;
    private boolean glass;
    private boolean darkGlass;
    private boolean closeEye;

    public FaceQualityOption() {
    }

    public FaceQualityOption(int faceMin, float pitch, float roll, float yaw, float brightMax,
                             float brightMin, float brightStd, float quality, boolean mono,
                             boolean glass, boolean darkGlass, boolean closeEye) {
        this.faceMin = faceMin;
        this.pitch = pitch / 100f;
        this.roll = roll / 100f;
        this.yaw = yaw / 100f;
        this.brightMax = brightMax;
        this.brightMin = brightMin;
        this.brightStd = brightStd;
        this.quality = quality;
        this.mono = mono;
        this.glass = glass;
        this.darkGlass = darkGlass;
        this.closeEye = closeEye;
    }

    public int getFaceMin() {
        return faceMin;
    }

    public void setFaceMin(int faceMin) {
        this.faceMin = faceMin;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getBrightMax() {
        return brightMax;
    }

    public void setBrightMax(float brightMax) {
        this.brightMax = brightMax;
    }

    public float getBrightMin() {
        return brightMin;
    }

    public void setBrightMin(float brightMin) {
        this.brightMin = brightMin;
    }

    public float getBrightStd() {
        return brightStd;
    }

    public void setBrightStd(float brightStd) {
        this.brightStd = brightStd;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public boolean isMono() {
        return mono;
    }

    public void setMono(boolean mono) {
        this.mono = mono;
    }

    public boolean isGlass() {
        return glass;
    }

    public void setGlass(boolean glass) {
        this.glass = glass;
    }

    public boolean isDarkGlass() {
        return darkGlass;
    }

    public void setDarkGlass(boolean darkGlass) {
        this.darkGlass = darkGlass;
    }

    public boolean isCloseEye() {
        return closeEye;
    }

    public void setCloseEye(boolean closeEye) {
        this.closeEye = closeEye;
    }

    @Override
    public String toString() {
        return "FaceQualityOption{" +
                "faceMin=" + faceMin +
                ", pitch=" + pitch +
                ", roll=" + roll +
                ", yaw=" + yaw +
                ", brightMax=" + brightMax +
                ", brightMin=" + brightMin +
                ", brightStd=" + brightStd +
                ", quality=" + quality +
                ", mono=" + mono +
                ", glass=" + glass +
                ", darkGlass=" + darkGlass +
                ", closeEye=" + closeEye +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FaceQualityOption that = (FaceQualityOption) o;

        if (faceMin != that.faceMin) return false;
        if (Float.compare(that.pitch, pitch) != 0) return false;
        if (Float.compare(that.roll, roll) != 0) return false;
        if (Float.compare(that.yaw, yaw) != 0) return false;
        if (Float.compare(that.brightMax, brightMax) != 0) return false;
        if (Float.compare(that.brightMin, brightMin) != 0) return false;
        if (Float.compare(that.brightStd, brightStd) != 0) return false;
        if (Float.compare(that.quality, quality) != 0) return false;
        if (mono != that.mono) return false;
        if (glass != that.glass) return false;
        if (darkGlass != that.darkGlass) return false;
        return closeEye == that.closeEye;

    }

    @Override
    public int hashCode() {
        int result = faceMin;
        result = 31 * result + (pitch != +0.0f ? Float.floatToIntBits(pitch) : 0);
        result = 31 * result + (roll != +0.0f ? Float.floatToIntBits(roll) : 0);
        result = 31 * result + (yaw != +0.0f ? Float.floatToIntBits(yaw) : 0);
        result = 31 * result + (brightMax != +0.0f ? Float.floatToIntBits(brightMax) : 0);
        result = 31 * result + (brightMin != +0.0f ? Float.floatToIntBits(brightMin) : 0);
        result = 31 * result + (brightStd != +0.0f ? Float.floatToIntBits(brightStd) : 0);
        result = 31 * result + (quality != +0.0f ? Float.floatToIntBits(quality) : 0);
        result = 31 * result + (mono ? 1 : 0);
        result = 31 * result + (glass ? 1 : 0);
        result = 31 * result + (darkGlass ? 1 : 0);
        result = 31 * result + (closeEye ? 1 : 0);
        return result;
    }
}
