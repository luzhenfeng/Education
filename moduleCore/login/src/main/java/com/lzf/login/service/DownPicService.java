package com.lzf.login.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.utils.utils.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.http.DownLoadManager;
import priv.lzf.mvvmhabit.http.NetworkUtil;
import priv.lzf.mvvmhabit.http.download.ProgressCallBack;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.SPUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

public class DownPicService extends Service {
    public List<String> downUrls=new ArrayList<>();
    public DownPicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downUrls=getDownUrls();
        if (downUrls!=null&&downUrls.size()>0){
            if (getPicIndex()<downUrls.size()-1){
                int index=getPicIndex();
                if (index<downUrls.size()){
                    upLoadPic(downUrls,getPicIndex());
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        DownLoadManager.getInstance().dispose();
        super.onDestroy();
    }

    public List<String> getDownUrls(){
        String text=FileUtil.load(getApplication(), Constant.avatarsFileName);
        return new Gson().fromJson(text,new TypeToken<List<String>>(){}.getType());
    }

    public List<String> getErrorDownUrls(String picErrors){
        String[] picError=picErrors.split(",");
        List<String> picErrorList= Arrays.asList(picError);
        return picErrorList;
    }

    public int getPicIndex(){
        return SPUtils.getInstance().getInt("picIndex",0);
    }


    public void upLoadPic(List<String> paths,int index) {
        if (NetworkUtil.isNetworkAvailable(getApplicationContext())){
            DownLoadManager.getInstance().load(paths.get(index), new ProgressCallBack<ResponseBody>(getApplication().getExternalCacheDir().getPath() + "PhotoFile", paths.get(index).split("PhotoFile/")[1]) {
                @Override
                public void onSuccess(ResponseBody responseBody) {
                    KLog.e("Success:"+index);
                    if (index+1 == paths.size()) {
                        stopService();
                        return;
                    }
                    SPUtils.getInstance().put("picIndex",index+1);
                    upLoadPic(downUrls,getPicIndex());
                }

                @Override
                public void progress(long progress, long total) {

                }

                @Override
                public void onError(Throwable e) {
                    KLog.e("error:"+index);
                    stopService();
                }
            });
        }
    }

    public void stopService(){
        Intent intent = new Intent(getApplication(), DownPicService.class);
        //开启服务
        AppManager.getAppManager().currentActivity().stopService(intent);
    }


}
