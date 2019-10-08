package com.lzf.http.data.source.http.service;

import com.lzf.greendao.entity.LoginModel;

import io.reactivex.Observable;
import priv.lzf.mvvmhabit.http.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("Auth/login")
    Observable<BaseResponse<LoginModel>> login(@Field("username") String username,
                                               @Field("password") String password,
                                               @Field("signature") String signature,
                                               @Field("timestamp") String timestamp,
                                               @Field("nonce") String nonce,
                                               @Field("appid") String appid);
}
