package com.lzf.http.data.source;

import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.AppListModel;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.entity.LoginModel;
import com.lzf.http.entity.SycnListModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import priv.lzf.mvvmhabit.http.BaseResponse;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public interface HttpDataSource{
    //模拟登录
    Observable<BaseResponse<LoginModel>> login(String username, String password);

    //模拟登录
    Observable<BaseResponse<List<FloorModel>>> checkObject(String token);

    //模拟登录
    Observable<BaseResponse<List<AppListModel>>> getAppList(String token);

    //模拟登录
    Observable<BaseResponse<List<AllCategoryModel>>> getAllCategoryList(String token);

    //模拟登录
    Observable<BaseResponse<List<SycnListModel>>> getSyncList(String token);

    //模拟登录
    Observable<BaseResponse> getStudentAvatars(String token);

    //创建检查记录
    Observable<BaseResponse> createCheck(String token, RequestBody model);


}
