package priv.lzf.mvvmhabit.utils;

import android.net.Uri;


import com.lzf.takephoto.app.TakePhoto;
import com.lzf.takephoto.app.TakePhotoImpl;
import com.lzf.takephoto.compress.CompressConfig;
import com.lzf.takephoto.model.CropOptions;
import com.lzf.takephoto.model.InvokeParam;
import com.lzf.takephoto.model.TContextWrap;
import com.lzf.takephoto.permission.InvokeListener;
import com.lzf.takephoto.permission.PermissionManager;
import com.lzf.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

import priv.lzf.mvvmhabit.base.AppManager;


/**
 * Created by NBMY on 2018/10/9.
 */

public class PhotoHelper implements InvokeListener {


    private InvokeParam invokeParam;
    public TakePhoto takePhoto;
    TakePhoto.TakeResultListener takeResultListener;

    private int maxSize=50*1024;
    private int maxPixel=800;
    CompressConfig config=new CompressConfig.Builder()
            .setMaxSize(maxSize)
            .setMaxPixel(maxPixel)
            .create();
    private static PhotoHelper photoHelper;

    private PhotoHelper(){}

    public static synchronized PhotoHelper getInstance() {
        if (photoHelper==null){
            photoHelper=new PhotoHelper();
        }
        return photoHelper;
    }



    /**
     * 获取TakePhoto实例 * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(AppManager.getAppManager().currentActivity(), takeResultListener));
        }
        return takePhoto;
    }

    public PhotoHelper setTakeResultListener(TakePhoto.TakeResultListener takeResultListener){
        this.takeResultListener=takeResultListener;
        return this;
    }



    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(AppManager.getAppManager().currentActivity()), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    public void destory(){
        photoHelper=null;
    }
    public InvokeParam getInvokeParam(){
        return invokeParam;
    }


    //图片保存的路径
    private Uri getImageCropUri() {
        Uri imageUri = null;
        File file = new File(AppManager.getAppManager().currentActivity().getCacheDir(), System.currentTimeMillis() + ".jpg");
        imageUri = Uri.fromFile(file);
        return imageUri;
    }

    public void setConfig(int maxSize,int maxPixel){
       config =new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(maxPixel)
                .create();
    }

    /**
     * 从相册中获取图片（不裁剪） 压缩
     */
    public void onPickFromGallery(){
        getTakePhoto().onPickFromGallery();
        getTakePhoto().onEnableCompress(config,true);
    }
    /**
     * 从相册中获取图片（裁剪） 压缩
     */
    public void onPickFromGalleryWithCrop(int x,int y){
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(x).setAspectY(y).setWithOwnCrop(true).create();
        getTakePhoto().onPickFromGalleryWithCrop(getImageCropUri(),cropOptions);
        getTakePhoto().onEnableCompress(config,true);
    }

    /**
     * 从相机中获取图片（不裁剪） 压缩
     */
    public void onPickFromCapture(){
        getTakePhoto().onPickFromCapture(getImageCropUri());
        getTakePhoto().onEnableCompress(config,true);
    }

    /**
     * 从相机中获取图片（裁剪） 压缩
     */
    public void onPickFromCaptureWithCrop(int x,int y){
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(x).setAspectY(y).setWithOwnCrop(true).create();
        getTakePhoto().onPickFromCaptureWithCrop(getImageCropUri(),cropOptions);
        getTakePhoto().onEnableCompress(config,true);
    }


    /**
     * 从相册中获取多张图片（不裁剪） 压缩
     */
    public void onPickMultiple(int limit){
        getTakePhoto().onPickMultiple(limit);
        getTakePhoto().onEnableCompress(config,true);
    }

    /**
     * 从相册中获取多张图片（裁剪） 压缩
     */
    public void onPickMultipleWithCrop(int limit,int x,int y){
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(x).setAspectY(y).setWithOwnCrop(true).create();
        getTakePhoto().onPickMultipleWithCrop(limit,cropOptions);
        getTakePhoto().onEnableCompress(config,true);
    }

}
