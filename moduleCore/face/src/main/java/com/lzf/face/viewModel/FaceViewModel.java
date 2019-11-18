package com.lzf.face.viewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;

import com.lzf.face.util.IntentDataHelper;
import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.data.RetrofitFaceClient;
import com.lzf.http.data.RetrofitLoginFaceClient;
import com.lzf.http.data.source.http.service.ApiService;
import com.lzf.http.entity.FaceLoginModel;
import com.lzf.http.entity.FaceResultModel;
import com.megvii.facetrack.FaceTrackListener;
import com.megvii.facetrack.FaceTrackOption;
import com.megvii.facetrack.FaceTracker;
import com.megvii.facetrack.MVFace;
import com.megvii.facetrack.camera.MVCameraPreview;
import com.nhsoft.base.base.ConstantMessage;
import com.nhsoft.utils.utils.FileUtil;

import java.io.File;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.http.BaseResponse;
import priv.lzf.mvvmhabit.http.ResponseThrowable;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.RxUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/1.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class FaceViewModel extends BaseViewModel<Repository> implements FaceTrackListener {

    public FaceTrackOption option;
    public FaceTracker faceTracker;
    public FaceViewModel(@NonNull Application application) {
        super(application);
        model= Injection.provideFaceRepository();
        option=new FaceTrackOption.Builder()
//                .setFrontCamera(false)
                .build();
        faceTracker = new FaceTracker();
    }

    public void startTrack(Activity activity,MVCameraPreview cameraPreview) {
        faceTracker.start(activity, cameraPreview, option, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (model.getFaceToken().equals("")){
            faceLogin();
        }
    }

    @Override
    public void onTrackCompleted(MVFace face) {
        // 质量判断
        String errorMessage = face.getErrorMessage();
        KLog.e(errorMessage);
//        cameraPreview.setErrorMessage(errorMessage);
        if (!TextUtils.isEmpty(errorMessage)) {
//            takePicture.setVisibility(View.GONE);
            return;
        }
        // 有没有人脸
        if (null == face.getCropFace() || 0 == face.getCropFace().size()) {
//            takePicture.setVisibility(View.GONE);
            return;
        }
        IntentDataHelper.setFaceList(face.getCropFace());
        IntentDataHelper.setBigFaceList(face.getOriginalFace());
        if (option.isEnableAutoStop()) {
            recognize(getFile(getBitmap(face)));

        }
    }

    @SuppressLint("MissingPermission")
    private void faceLogin(){
        addSubscribe(RetrofitLoginFaceClient.getInstance().create(ApiService.class)
                .faceLogin("admin@demsino.com","123456", (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)?Build.getSerial(): Build.SERIAL,2)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())// 网络错误的异常转换, 这里可以换成自己的ExceptionHandle)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponse<FaceLoginModel>>() {
                    @Override
                    public void accept(BaseResponse<FaceLoginModel> faceLoginModelBaseResponse) throws Exception {
                        if (faceLoginModelBaseResponse.isFaceOk()){
                            FaceLoginModel faceLoginModel=faceLoginModelBaseResponse.getData();
                            model.saveFaceToken(faceLoginModel.getScreen_token());
                        }else {
                            ToastUtils.showShort("刷脸初始化失败");
                            dismissDialog();
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        ToastUtils.showShort("刷脸初始化失败");
                        dismissDialog();
                        finish();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        dismissDialog();
                        finish();
                    }
                }));
    }

    private void recognize(File file){
        HashMap<String, RequestBody> requestBodyHashMap = new HashMap<>();
        requestBodyHashMap.put("screen_token", RequestBody.create(MediaType.parse("multipart/form-data"), model.getFaceToken()));
        addSubscribe(RetrofitFaceClient.getInstance().create(ApiService.class)
                .recognize(requestBodyHashMap,setPart("image",file))
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer())// 网络错误的异常转换, 这里可以换成自己的ExceptionHandle)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<FaceResultModel>() {
                    @Override
                    public void accept(FaceResultModel response) throws Exception {
                        if (response.getError()==0){
                            model.saveFaceId(response.getPerson().getSubject_id()+"");
//                            Messenger.getDefault().send(response.getPerson().getSubject_id(), ConstantMessage.TOKEN_FACEVIEWMODEL_RESULT);
                            ToastUtils.showShort("识别成功");
                        }else {
                            model.saveFaceId("");
                            ToastUtils.showShort("识别失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        ToastUtils.showShort("识别失败");
                        model.saveFaceId("");
                        dismissDialog();
                        finish();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        dismissDialog();
                        finish();
                    }
                }));
    }

    public  MultipartBody.Part setPart(String param, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(param, file.getName(), requestFile);
        return body;
    }

    private Bitmap getBitmap(MVFace face){
        byte[] faceImage = face.getCropFace().get(0);
        return BitmapFactory.decodeByteArray(faceImage, 0, faceImage.length);
    }


    private File getFile(Bitmap bitmap){
        FileUtil.saveBitmapFile(getApplication(),bitmap,"face.jpg");
        return new File(getApplication().getExternalCacheDir().getPath(),"face.jpg");
    }

    @Override
    public void onStop() {
        super.onStop();
        faceTracker.stop();
    }
}
