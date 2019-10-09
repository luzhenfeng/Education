package com.lzf.http.data.source;

import com.lzf.http.entity.CheckModel;
import com.lzf.http.entity.LoginModel;

import java.util.List;

import io.reactivex.Observable;
import priv.lzf.mvvmhabit.http.BaseResponse;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public interface HttpDataSource{
    //模拟登录
    Observable<BaseResponse<LoginModel>> login(String username, String password);

    //模拟登录
    Observable<BaseResponse<List<CheckModel>>> checkObject(String token);
}
