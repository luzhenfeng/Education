
package com.megvii.facetrack.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import com.megvii.facetrack.R;


public class FaceView extends View {
    private static final int RES_ID = R.drawable.face_rect;

    Paint localPaint = null;
    private Bitmap bmp;
    private Rect resRect;
    private RectF desRest;

    private int width, height;

    private Matrix mat = new Matrix();

    public FaceView(Context context, int width, int height, int resId) {
        super(context);
        init(context, width, height, resId);
    }

    private void init(Context context, int width, int height, int resId) {
        if (0 >= resId) {
            resId = RES_ID;
        }
        this.width = width;
        this.height = height;
        localPaint = new Paint();
        bmp = BitmapFactory.decodeResource(context.getResources(), resId);
        resRect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        desRest = new RectF(0, 0, 0, 0);
    }

    public void onRefresh(RectF faceRect, int imageWidth, int imageHeight) {
        mat.setScale(-1, 1);
        mat.postTranslate((float) imageWidth, 0f);
        mat.postScale((float) width / imageWidth, (float) height / imageHeight);
        mat.mapRect(desRest, faceRect);

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (desRest == null)
            return;
        canvas.drawBitmap(bmp, resRect, desRest, localPaint);
    }
}
