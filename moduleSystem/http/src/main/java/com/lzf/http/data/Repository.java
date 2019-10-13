package com.lzf.http.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lzf.http.data.source.HttpDataSource;
import com.lzf.http.data.source.LocalDataSource;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.AppListModel;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.entity.LoginModel;
import com.lzf.http.entity.SycnListModel;

import java.util.List;

import io.reactivex.Observable;
import priv.lzf.mvvmhabit.base.BaseModel;
import priv.lzf.mvvmhabit.http.BaseResponse;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public class Repository extends BaseModel implements HttpDataSource, LocalDataSource {
    private volatile static Repository INSTANCE = null;
    private final HttpDataSource mHttpDataSource;

    private final LocalDataSource mLocalDataSource;

    private Repository(@NonNull HttpDataSource httpDataSource,
                           @NonNull LocalDataSource localDataSource) {
        this.mHttpDataSource = httpDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static Repository getInstance(HttpDataSource httpDataSource,
                                             LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository(httpDataSource, localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<BaseResponse<LoginModel>> login(String username, String password) {
        return mHttpDataSource.login(username,password);
    }

    @Override
    public Observable<BaseResponse<List<FloorModel>>> checkObject(String token) {
        return mHttpDataSource.checkObject(token);
    }

    @Override
    public Observable<BaseResponse<List<AppListModel>>> getAppList(String token) {
        return mHttpDataSource.getAppList(token);
    }

    @Override
    public Observable<BaseResponse<List<AllCategoryModel>>> getAllCategoryList(String token) {
        return mHttpDataSource.getAllCategoryList(token);
    }

    @Override
    public Observable<BaseResponse<List<SycnListModel>>> getSyncList(String token) {
        return mHttpDataSource.getSyncList(token);
    }

    @Override
    public Observable<BaseResponse> createCheck(String token, String model) {
        return mHttpDataSource.createCheck(token,model);
    }

    @Override
    public void saveUserName(String userName) {
        mLocalDataSource.saveUserName(userName);
    }

    @Override
    public String getUserName() {
        return mLocalDataSource.getUserName();
    }

    @Override
    public void savePassword(String password) {
        mLocalDataSource.savePassword(password);
    }

    @Override
    public String getPassword() {
        return mLocalDataSource.getPassword();
    }

    @Override
    public void saveToken(String token) {
        mLocalDataSource.saveToken(token);
    }

    @Override
    public String getToken() {
        return mLocalDataSource.getToken();
    }

    @Override
    public boolean insertUser(LoginModel loginModel) {
        return mLocalDataSource.insertUser(loginModel);
    }

    @Override
    public void saveCheckObjectVersion(int version) {
        mLocalDataSource.saveCheckObjectVersion(version);
    }

    @Override
    public int getCheckObjectVersion() {
        return mLocalDataSource.getCheckObjectVersion();
    }

    @Override
    public void saveCheckCategoryVersion(int version) {
        mLocalDataSource.saveCheckCategoryVersion(version);
    }

    @Override
    public int getCheckCategoryVersion() {
        return mLocalDataSource.getCheckCategoryVersion();
    }

    @Override
    public void saveCodes(String codes) {
        mLocalDataSource.saveCodes(codes);
    }

    @Override
    public String getCodes() {
        return mLocalDataSource.getCodes();
    }

    @Override
    public boolean insertCheckModel(CheckModel checkModel) {
        return mLocalDataSource.insertCheckModel(checkModel);
    }
}
