package com.lzf.http.data.source.http;


import com.lzf.greendao.entity.LoginModel;
import com.lzf.http.data.source.HttpDataSource;
import com.lzf.http.data.source.http.service.ApiService;
import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.utils.utils.EncryptionUtil;

import io.reactivex.Observable;
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

    @Override
    public Observable<BaseResponse<LoginModel>> login(String username, String password) {
        String nonce=EncryptionUtil.getNonce();
        String timestamp=EncryptionUtil.getTimestamp();
        return apiService.login(username, EncryptionUtil.MD5(password),EncryptionUtil.signatureString(Constant.APPSECRET,timestamp,nonce),timestamp,nonce,Constant.APPID);
    }
}
