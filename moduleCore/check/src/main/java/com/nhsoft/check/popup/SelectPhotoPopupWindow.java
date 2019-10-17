package com.nhsoft.check.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.nhsoft.check.R;
import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.pxview.utils.RelayoutViewTool;

/**
 * Created by Administrator on 2017-11-15.
 */

public class SelectPhotoPopupWindow extends PopupWindow implements View.OnClickListener{

    Button btn_camera_photo;
    Button btn_album_photo;
    Button btn_cancel;
    Context context;
    PopupWindow popupWindow;
    SelectPhotoPopupWindowListener selectPhotoPopupWindowListener;



    public SelectPhotoPopupWindow(Context context, SelectPhotoPopupWindowListener selectPhotoPopupWindowListener){
        this.context=context;
        this.selectPhotoPopupWindowListener=selectPhotoPopupWindowListener;
    }

    public void showPopupWindow(){
        View popup= LayoutInflater.from(context).inflate(R.layout.select_photo_popup_window,null);
        if (Constant.isScale){
            RelayoutViewTool.relayoutViewWithScale(popup, Constant.mScreenWidthScale);
        }
        btn_camera_photo=popup.findViewById(R.id.btn_camera_photo);
        btn_album_photo=popup.findViewById(R.id.btn_album_photo);
        btn_cancel=popup.findViewById(R.id.btn_cancel);
        btn_album_photo.setOnClickListener(this);
        btn_camera_photo.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        popupWindow = new PopupWindow(popup, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) context).getWindow().setAttributes(lp);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        //popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.update();
        popupWindow.setOnDismissListener(new OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
        popupWindow.showAtLocation(popup, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_camera_photo) {
            selectPhotoPopupWindowListener.onClickCameraPhoto();
            popupWindow.dismiss();
        } else if (id == R.id.btn_album_photo) {
            selectPhotoPopupWindowListener.onClickAlbumPhoto();
            popupWindow.dismiss();
        } else if (id == R.id.btn_cancel) {
            popupWindow.dismiss();
        }
    }
    public interface SelectPhotoPopupWindowListener{
        void onClickCameraPhoto();
        void onClickAlbumPhoto();
    }




    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

}
