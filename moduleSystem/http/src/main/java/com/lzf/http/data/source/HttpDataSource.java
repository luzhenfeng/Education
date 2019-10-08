package com.lzf.http.data.source;

import com.lzf.greendao.entity.LoginModel;

import io.reactivex.Observable;
import priv.lzf.mvvmhabit.http.BaseResponse;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public interface HttpDataSource{
    //模拟登录
    Observable<BaseResponse<LoginModel>> login(String username, String password);
}
