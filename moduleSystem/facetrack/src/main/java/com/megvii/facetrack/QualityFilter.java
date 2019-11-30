package com.megvii.facetrack;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import megvii.megfaceandroid.MegfaceAttribute;
import megvii.megfaceandroid.MegfaceAttributeBrightness;
import megvii.megfaceandroid.MegfaceAttributeEyeStatus;
import megvii.megfaceandroid.MegfaceAttributeMono;
import megvii.megfaceandroid.MegfaceAttributePose;
import megvii.megfaceandroid.MegfaceAttributeQuality;
import megvii.megfaceandroid.MegfaceFace;

/**
 * @author by licheng on 2017/8/16.
 */

public class QualityFilter {
    public static final String KEY_FACE_MIN = "faceMin";
    public static final String KEY_PITCH = "pitch";
    public static final String KEY_ROLL = "roll";
    public static final String KEY_YAW = "yaw";
    public static final String KEY_BRIGHT_MAX = "bright_max";
    public static final String KEY_BRIGHT_MIN = "bright_min";
    public static final String KEY_BRIGHT_STD = "bright_std";
    public static final String KEY_QUALITY = "quality";
    public static final String KEY_MONO = "mono";
    public static final String KEY_GLASS = "glass";
    public static final String KEY_DARK_GLASS = "dark_glass";
    public static final String KEY_CLOSE_EYE = "close_eye";

    public static final int MGF_EYESTATUS_NOGLASSES_EYEOPEN = 0;      // 无眼镜，睁眼
    public static final int MGF_EYESTATUS_NOGLASSES_EYECLOSE = 1;     // 无眼镜，闭眼
    public static final int MGF_EYESTATUS_NORMALGLASSES_EYEOPEN = 2;  // 有眼镜，睁眼
    public static final int MGF_EYESTATUS_NORMALGLASSES_EYECLOSE = 3; // 有眼镜，闭眼
    public static final int MGF_EYESTATUS_DARKGLASSES = 4;            // 墨镜
    public static final int MGF_EYESTATUS_OTHER_OCCLUSION = 5;        // 其他遮挡

    public static FaceQualityOption QUALITY;
    private static SharedPreferences sp;

    public static void init(Context context) {
        sp = context.getSharedPreferences("quality_value", Context.MODE_PRIVATE);
        update();
    }

    public static void update() {
        // 最小脸尺寸
        int faceMin = sp.getInt(KEY_FACE_MIN, FaceQualityOption.DEFAULT_VALUE_FACE_MIN);
        // 人脸
        int pitch = sp.getInt(KEY_PITCH, FaceQualityOption.DEFAULT_VALUE_PITCH);
        int roll = sp.getInt(KEY_ROLL, FaceQualityOption.DEFAULT_VALUE_ROLL);
        int yaw = sp.getInt(KEY_YAW, FaceQualityOption.DEFAULT_VALUE_YAW);
        // 亮度
        int brightMax = sp.getInt(KEY_BRIGHT_MAX, FaceQualityOption.DEFAULT_VALUE_BRIGHT_MAX);
        int brightMin = sp.getInt(KEY_BRIGHT_MIN, FaceQualityOption.DEFAULT_VALUE_BRIGHT_MIN);
        int brightStd = sp.getInt(KEY_BRIGHT_STD, FaceQualityOption.DEFAULT_VALUE_BRIGHT_STD);
        // 模糊度
        float quality = sp.getFloat(KEY_QUALITY, FaceQualityOption.DEFAULT_VALUE_QUALITY);
        // 戴眼镜
        boolean glass = sp.getBoolean(KEY_GLASS, FaceQualityOption.DEFAULT_VALUE_GLASS);
        // 戴墨镜
        boolean darGlass = sp.getBoolean(KEY_DARK_GLASS, FaceQualityOption.DEFAULT_VALUE_DARK_GLASS);
        // 黑白照片
        boolean mono = sp.getBoolean(KEY_MONO, FaceQualityOption.DEFAULT_VALUE_MONO);
        // 闭眼
        boolean closeEye = sp.getBoolean(KEY_CLOSE_EYE, FaceQualityOption.DEFAULT_CLOSE_EYE);
        QUALITY = new FaceQualityOption(faceMin, pitch, roll, yaw, brightMax, brightMin, brightStd, quality, mono, glass, darGlass, closeEye);
    }

    public static String filter(MegfaceFace megfaceFace, FaceQualityOption qualityOption) {
        if (null == megfaceFace.attributes) {
            return "属性为空";
        }
        if (null == qualityOption) {
            qualityOption = QUALITY;
            if (null == qualityOption) {
                return "请调用 QualityFilter.init() 初始化";
            }
        }

        MegfaceAttributePose attributePose = (MegfaceAttributePose) megfaceFace.attributes.get(MegfaceAttribute.MegfaceAttributeType.POSE);
        MegfaceAttributeQuality attributeQuality = (MegfaceAttributeQuality) megfaceFace.attributes.get(MegfaceAttribute.MegfaceAttributeType.QUALITY);
        MegfaceAttributeBrightness attributeBrightness = (MegfaceAttributeBrightness) megfaceFace.attributes.get(MegfaceAttribute.MegfaceAttributeType.BRIGHTNESS);
        MegfaceAttributeMono attributeMono = (MegfaceAttributeMono) megfaceFace.attributes.get(MegfaceAttribute.MegfaceAttributeType.MONO);
        MegfaceAttributeEyeStatus attributeEyeStatus = (MegfaceAttributeEyeStatus) megfaceFace.attributes.get(MegfaceAttribute.MegfaceAttributeType.EYESTATUS);

        int faceMin1 = megfaceFace.rect.right - megfaceFace.rect.left;
        int faceMin2 = megfaceFace.rect.bottom - megfaceFace.rect.top;
        Log.e("aaa1",qualityOption.getRoll()+"");
        Log.e("aaa2", Math.abs(attributePose.roll)+"");

        if (qualityOption.getFaceMin() > Math.min(faceMin1, faceMin2)) {
            Log.e("aaa","aaaaaaa1");
            return "脸太小，请靠近镜头";
        }
        if (0 > Float.compare(qualityOption.getPitch(), Math.abs(attributePose.pitch))) {
            if (attributePose.pitch < 0) {
                Log.e("aaa","aaaaaaa2");
                return "仰头角度过大";
            } else {
                Log.e("aaa","aaaaaaa3");
                return "低头角度过大";
            }
        }

//        if (0 > Float.compare(qualityOption.getRoll(), Math.abs(attributePose.roll))) {
////            if (qualityOption)
//            if (attributePose.roll < 0) {
//                Log.e("aaa","aaaaaaa4");
//                return "左歪头角度过大";
//            } else {
//                Log.e("aaa","aaaaaaa5");
//                return "右歪头角度过大";
//            }
//        }

        if (0 > Float.compare(qualityOption.getYaw(), Math.abs(attributePose.yaw))) {
            if (attributePose.yaw < 0) {
                Log.e("aaa","aaaaaaa6");
                return "右摇头角度过大";
            } else {
                Log.e("aaa","aaaaaaa7");
                return "左摇头角度过大";
            }
        }

        if (0 < Float.compare(qualityOption.getQuality(), Math.abs(attributeQuality.quality))) {
            Log.e("aaa","aaaaaaa8");
            return "清晰度不够";
        }

        if (qualityOption.isMono()) { // 禁止黑白，分数必须小于 0.95
            if (0 > Float.compare(0.95f, Math.abs(attributeMono.mono))) {
                Log.e("aaa","aaaaaaa9");
                return "这是是黑白照片";
            }
        }

        if (!qualityOption.isGlass()) { // 禁止戴眼镜
            if (attributeEyeStatus.leftEye == MGF_EYESTATUS_NORMALGLASSES_EYEOPEN
                    || attributeEyeStatus.leftEye == MGF_EYESTATUS_NORMALGLASSES_EYECLOSE
                    || attributeEyeStatus.rightEye == MGF_EYESTATUS_NORMALGLASSES_EYEOPEN
                    || attributeEyeStatus.rightEye == MGF_EYESTATUS_NORMALGLASSES_EYECLOSE) {
                Log.e("aaa","aaaaaaa10");
                return "戴了眼镜，请摘掉眼镜";
            }
        }

        if (!qualityOption.isDarkGlass()) { // 禁止戴墨镜
            if (attributeEyeStatus.leftEye == MGF_EYESTATUS_DARKGLASSES
                    || attributeEyeStatus.leftEye == MGF_EYESTATUS_OTHER_OCCLUSION
                    || attributeEyeStatus.rightEye == MGF_EYESTATUS_DARKGLASSES
                    || attributeEyeStatus.rightEye == MGF_EYESTATUS_OTHER_OCCLUSION) {
                Log.e("aaa","aaaaaaa11");
                return "戴了墨镜或其它遮挡物，请摘掉";
            }
        }

        if (!qualityOption.isCloseEye()) { // 禁止闭眼
            if (attributeEyeStatus.leftEye == MGF_EYESTATUS_NOGLASSES_EYECLOSE
                    || attributeEyeStatus.leftEye == MGF_EYESTATUS_NORMALGLASSES_EYECLOSE
                    || attributeEyeStatus.rightEye == MGF_EYESTATUS_NOGLASSES_EYECLOSE
                    || attributeEyeStatus.rightEye == MGF_EYESTATUS_NORMALGLASSES_EYECLOSE) {
                Log.e("aaa","aaaaaaa12");
                return "闭眼睛了，请睁开眼睛";
            }
        }

        if (attributeBrightness.brightness > qualityOption.getBrightMax()) {
            Log.e("aaa","aaaaaaa13");
            return "亮度太高";
        }

        if (attributeBrightness.brightness < qualityOption.getBrightMin()) {
            Log.e("aaa","aaaaaaa14");
            return "亮度太低";
        }

        if (attributeBrightness.std > qualityOption.getBrightStd()) {
            Log.e("aaa","aaaaaaa15");
            return "标准差太高";
        }
        Log.e("aaa","aaaaaaa");

        return null;
    }
}
