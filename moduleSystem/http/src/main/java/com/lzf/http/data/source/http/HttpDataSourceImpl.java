package com.lzf.http.data.source.http;


import android.annotation.SuppressLint;
import android.os.Build;

import com.lzf.http.data.source.HttpDataSource;
import com.lzf.http.data.source.http.service.ApiService;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.AppListModel;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.entity.LoginModel;
import com.lzf.http.entity.SycnListModel;
import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.utils.utils.EncryptionUtil;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import priv.lzf.mvvmhabit.http.BaseResponse;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public class HttpDataSourceImpl implements HttpDataSource {
    private ApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(ApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @SuppressLint("MissingPermission")
    @Override
    public Observable<BaseResponse<LoginModel>> login(String username, String password) {
        String nonce=EncryptionUtil.getNonce();
        String timestamp=EncryptionUtil.getTimestamp();
        String serial=Build.SERIAL;
        return apiService.login(username, EncryptionUtil.MD5(password),EncryptionUtil.signatureString(Constant.APPSECRET,timestamp,nonce),timestamp,nonce,Constant.APPID,serial);
    }

    @Override
    public Observable<BaseResponse<List<FloorModel>>> checkObject(String token) {
        return apiService.checkObject(token);
    }

    @Override
    public Observable<BaseResponse<List<AppListModel>>> getAppList(String token) {
        return apiService.getAppList(token);
    }

    @Override
    public Observable<BaseResponse<List<AllCategoryModel>>> getAllCategoryList(String token) {
        return apiService.getAllCategoryList(token);
    }

    @Override
    public Observable<BaseResponse<List<SycnListModel>>> getSyncList(String token) {
        return apiService.getSyncList(token);
    }

    @Override
    public Observable<BaseResponse> createCheck(String token, RequestBody model) {
        return apiService.createCheck(token,model);
    }
}
