package com.nhsoft.check.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;


import com.google.gson.Gson;
import com.lzf.takephoto.app.TakePhoto;
import com.lzf.takephoto.app.TakePhotoImpl;
import com.lzf.takephoto.model.InvokeParam;
import com.lzf.takephoto.model.TContextWrap;
import com.lzf.takephoto.model.TResult;
import com.lzf.takephoto.permission.InvokeListener;
import com.lzf.takephoto.permission.PermissionManager;
import com.lzf.takephoto.permission.TakePhotoInvocationHandler;
import com.nhsoft.check.popup.SelectPhotoPopupWindow;

import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.base.BaseFragment;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.PhotoHelper;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/15.
 */
public abstract class BaseTokePhotoFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<V,VM> implements TakePhoto.TakeResultListener {
    protected boolean isCamera;

    private final static int MAXSIZE=102400;
    private final static int MAXPIXEL=800;
    private final static int WIDTH=800;
    private final static int HEIGTH=800;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        PhotoHelper.getInstance().setTakeResultListener(this).getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        PhotoHelper.getInstance().getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        PhotoHelper.getInstance().getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, PhotoHelper.getInstance().getInvokeParam(), this);
    }


    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(getContext(), "Error:" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        Toast.makeText(getContext(), "takeCancel:", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        PhotoHelper.getInstance().destory();
    }

    /**
     * 不剪切
     * @param number 选择图片的数量
     */
    protected void showPopup(final int number) {
        SelectPhotoPopupWindow selectPhotoPopupWindow = new SelectPhotoPopupWindow(getContext(), new SelectPhotoPopupWindow.SelectPhotoPopupWindowListener() {
            @Override
            public void onClickCameraPhoto() {
                PhotoHelper.getInstance().setConfig(MAXSIZE, MAXPIXEL);
                PhotoHelper.getInstance().onPickFromCapture();
                isCamera = true;
            }

            @Override
            public void onClickAlbumPhoto() {
                PhotoHelper.getInstance().setConfig(MAXSIZE, MAXPIXEL);
                PhotoHelper.getInstance().onPickMultiple(number);
                isCamera = false;
            }
        });
        selectPhotoPopupWindow.showPopupWindow();
    }
    /**
     * 剪切
     * @param number 选择图片的数量
     */
    protected void showCutPopup(final int number) {
        SelectPhotoPopupWindow selectPhotoPopupWindow = new SelectPhotoPopupWindow(getContext(), new SelectPhotoPopupWindow.SelectPhotoPopupWindowListener() {
            @Override
            public void onClickCameraPhoto() {
                PhotoHelper.getInstance().setConfig(MAXSIZE, MAXPIXEL);
                PhotoHelper.getInstance().onPickFromCaptureWithCrop(WIDTH,HEIGTH);
                isCamera = true;
            }

            @Override
            public void onClickAlbumPhoto() {
                PhotoHelper.getInstance().setConfig(MAXSIZE, MAXPIXEL);
                PhotoHelper.getInstance().onPickMultipleWithCrop(number,WIDTH,HEIGTH);
                isCamera = false;
            }
        });
        selectPhotoPopupWindow.showPopupWindow();
    }
}
