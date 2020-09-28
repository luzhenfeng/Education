package com.lzf.http.data;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lzf.greendao.entity.DormCheckModel;
import com.lzf.greendao.entity.StudentModel;
import com.lzf.http.data.source.HttpDataSource;
import com.lzf.http.data.source.LocalDataSource;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.AppListModel;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.entity.HeadModel;
import com.lzf.http.entity.LeaveStudentModel;
import com.lzf.http.entity.LoginModel;
import com.lzf.http.entity.SycnListModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
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
    public Observable<BaseResponse<List<HeadModel>>> getStudentAvatars(String token) {
        return mHttpDataSource.getStudentAvatars(token);
    }

    @Override
    public Observable<BaseResponse> createCheck(String token, RequestBody model) {
        return mHttpDataSource.createCheck(token,model);
    }

    @Override
    public Observable<BaseResponse> createNightRollCall(String token, RequestBody model) {
        return mHttpDataSource.createNightRollCall(token,model);
    }

    @Override
    public Observable<BaseResponse<List<LeaveStudentModel>>> getLeave(String token, String date) {
        return mHttpDataSource.getLeave(token,date);
    }

    @Override
    public void saveBaseUrl(String baseUrl) {
        mLocalDataSource.saveBaseUrl(baseUrl);
    }

    @Override
    public String getBaseUrl() {
        return mLocalDataSource.getBaseUrl();
    }

    @Override
    public void saveBaseFaceUrl(String baseFaceUrl) {
        mLocalDataSource.saveBaseFaceUrl(baseFaceUrl);
    }

    @Override
    public String getBaseFaceUrl() {
        return mLocalDataSource.getBaseFaceUrl();
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
    public void saveFaceToken(String token) {
        mLocalDataSource.saveFaceToken(token);
    }

    @Override
    public String getFaceToken() {
        return  mLocalDataSource.getFaceToken();
    }

    @Override
    public void saveFaceId(String id) {
        mLocalDataSource.saveFaceId(id);
    }

    @Override
    public String getFaceId() {
        return mLocalDataSource.getFaceId();
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
    public void saveAvatarsVersion(int version) {
        mLocalDataSource.saveAvatarsVersion(version);
    }

    @Override
    public int getAvatarsVersion() {
        return mLocalDataSource.getAvatarsVersion();
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
    public void saveCode(String code) {
        mLocalDataSource.saveCode(code);
    }

    @Override
    public String getCode() {
        return mLocalDataSource.getCode();
    }

    @Override
    public boolean insertCheckModel(CheckModel checkModel) {
        return mLocalDataSource.insertCheckModel(checkModel);
    }

    @Override
    public boolean insertDormCheckModel(DormCheckModel dormCheckModel, List<StudentModel> studentModelList) {
        return mLocalDataSource.insertDormCheckModel(dormCheckModel,studentModelList);
    }

    @Override
    public void savePhotos(String photos) {
        mLocalDataSource.savePhotos(photos);
    }

    @Override
    public String getPhotos() {
        return mLocalDataSource.getPhotos();
    }

    @Override
    public void deleteMonthAgoDormCheckModeList() {
        mLocalDataSource.deleteMonthAgoDormCheckModeList();
    }

    @Override
    public void deleteMonthAgoChecksModeList() {
        mLocalDataSource.deleteMonthAgoChecksModeList();
    }
}
