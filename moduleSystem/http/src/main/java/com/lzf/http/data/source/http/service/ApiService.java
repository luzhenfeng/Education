package com.lzf.http.data.source.http.service;

import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.AppListModel;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.entity.HeadModel;
import com.lzf.http.entity.LoginModel;
import com.lzf.http.entity.SycnListModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import priv.lzf.mvvmhabit.http.BaseResponse;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
                                               @Field("appid") String appid,
                                               @Field("deviceno") String deviceno);

    @GET("mq/GetCheckObjects")
    Observable<BaseResponse<List<FloorModel>>> checkObject(@Query("token") String token);


    @GET("Auth/GetAppList")
    Observable<BaseResponse<List<AppListModel>>> getAppList(@Query("token") String token);

    @GET("mq/GetAllCategoryList")
    Observable<BaseResponse<List<AllCategoryModel>>> getAllCategoryList(@Query("token") String token);

    @GET("mq/GetSyncList")
    Observable<BaseResponse<List<SycnListModel>>> getSyncList(@Query("token") String token);

    @GET("mq/GetStudentAvatars")
    Observable<BaseResponse<List<HeadModel>>> getStudentAvatars(@Query("token") String token);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("mq/CreateCheck")
    Observable<BaseResponse> createCheck(@Query("token") String token, @Body RequestBody model);

    //错误发送
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("robot/send?access_token=c509149c7a6fc25a31aebf4837256221559b72ffea225b75d78992c2b58aa33d")
    Observable<Object> sendError(@Body RequestBody route);


    @FormUrlEncoded
    @POST("/auth/login")
    Observable<BaseResponse<LoginModel>> faceLogin(@Field("username") String username,
                                               @Field("password") String password,
                                               @Field("signature") String signature,
                                               @Field("timestamp") String timestamp,
                                               @Field("nonce") String nonce,
                                               @Field("appid") String appid,
                                               @Field("deviceno") String deviceno);

}
