package com.megvii.facetrack;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * @author by licheng on 2017/8/3.
 */

public class BitmapUtil {

    public static Bitmap convertBmp(Bitmap bmp) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1); // 镜像水平翻转
        return Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

}
