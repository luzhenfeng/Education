package com.lzf.http.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lzf.http.data.source.HttpDataSource;
import com.lzf.http.data.source.LocalDataSource;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.entity.LoginModel;

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
    public Observable<BaseResponse<List<CheckModel>>> checkObject(String token) {
        return mHttpDataSource.checkObject(token);
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
}
