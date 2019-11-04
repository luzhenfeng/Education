package com.megvii.facetrack;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author by licheng on 2017/7/14.
 */
public class FaceTrackOption implements Parcelable {
    private static final boolean ENABLE = true;
    private static final int MAX_TRACK_FACE_COUNT = 9;
    private static final int DEFAULT_TRACK_FACE_COUNT = 1;

    public static final int MODE_NORMAL = 0;
    public static final int MODE_TAKE_PICTURE = 1;

    private boolean enableAutoStop = ENABLE; // 抓拍成功后自动停止抓拍
    private boolean enableTrackNow = ENABLE; // 启动相机后，立即启动抓拍功能
    private boolean openFrontCamera = ENABLE; // 启动前置摄像头

    private int faceTrackCount = DEFAULT_TRACK_FACE_COUNT; // 抓拍数量
    private int mode = MODE_NORMAL; // 启动模式（1. 普通模式，一直返回数据。2. 拍照模式，只有调用方法才返回）
    private int resourceId = 0; // 自定义人脸框
    private int rotation = 0; // 旋转角度：一些特殊设备会有用。比如后置摄像头装前面。
    private FaceQualityOption qualityOption; // 质量参数

    public boolean isEnableAutoStop() {
        return enableAutoStop;
    }

    public boolean isEnableTrackNow() {
        return enableTrackNow;
    }

    public boolean showRectView() {
        return 0 < resourceId;
    }

    public boolean isOpenFrontCamera() {
        return openFrontCamera;
    }

    public int getFaceTrackCount() {
        return faceTrackCount;
    }

    public int getMode() {
        return mode;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getRotation() {
        return rotation;
    }

    public FaceQualityOption getQualityOption() {
        return qualityOption;
    }

    public boolean canAddTrackCount(int count) {
        return MAX_TRACK_FACE_COUNT > count && count < faceTrackCount;
    }

    public boolean isModeTakePicture() {
        return this.mode == MODE_TAKE_PICTURE;
    }

    public static final class Builder {

        private FaceTrackOption faceTrackOption;

        public Builder() {
            faceTrackOption = new FaceTrackOption();
        }

        public FaceTrackOption build() {
            return faceTrackOption;
        }

        public Builder setEnableAutoStop(boolean mEnableAutoStop) {
            if (MODE_TAKE_PICTURE != faceTrackOption.mode) {
                faceTrackOption.enableAutoStop = mEnableAutoStop;
            }
            return this;
        }

        public Builder setEnableTrackNow(boolean mEnableTrackNow) {
            faceTrackOption.enableTrackNow = mEnableTrackNow;
            return this;
        }

        public Builder setFrontCamera(boolean openFrontCamera) {
            faceTrackOption.openFrontCamera = openFrontCamera;
            return this;
        }

        public Builder setFaceTrackCount(int faceTrackCount) {
            if (MAX_TRACK_FACE_COUNT >= faceTrackCount) {
                faceTrackOption.faceTrackCount = faceTrackCount;
            }
            return this;
        }

        public Builder setMode(int mode) {
            faceTrackOption.mode = mode;
            if (MODE_TAKE_PICTURE == mode) {
                // 拍照模式下，抓到脸后不能自动关闭
                faceTrackOption.enableAutoStop = false;
            }
            return this;
        }

        public Builder setResourceId(int resourceId) {
            faceTrackOption.resourceId = resourceId;
            return this;
        }

        public Builder setRotation(int rotation) {
            faceTrackOption.rotation = rotation;
            return this;
        }

        public Builder setQualityOption(FaceQualityOption qualityOption) {
            faceTrackOption.qualityOption = qualityOption;
            return this;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (enableAutoStop ? 1 : 0));
        dest.writeByte((byte) (enableTrackNow ? 1 : 0));
        dest.writeByte((byte) (openFrontCamera ? 1 : 0));
        dest.writeInt(faceTrackCount);
        dest.writeInt(mode);
        dest.writeInt(resourceId);
        dest.writeInt(rotation);
        dest.writeSerializable(qualityOption);
    }

    public static final Parcelable.Creator<FaceTrackOption> CREATOR = new Creator<FaceTrackOption>() {
        @Override
        public FaceTrackOption createFromParcel(Parcel source) {
            return new FaceTrackOption(source);
        }

        @Override
        public FaceTrackOption[] newArray(int size) {
            return new FaceTrackOption[0];
        }
    };

    private FaceTrackOption() {
    }

    private FaceTrackOption(Parcel in) {
        enableAutoStop = in.readByte() == 1;
        enableTrackNow = in.readByte() == 1;
        openFrontCamera = in.readByte() == 1;
        faceTrackCount = in.readInt();
        mode = in.readInt();
        resourceId = in.readInt();
        rotation = in.readInt();
        qualityOption = (FaceQualityOption) in.readSerializable();
    }
}
