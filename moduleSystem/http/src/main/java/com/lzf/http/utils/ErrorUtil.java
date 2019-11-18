package com.lzf.http.utils;

import com.google.gson.Gson;
import com.lzf.http.data.RetrofitErrorClient;
import com.lzf.http.data.source.http.service.ApiService;
import com.nhsoft.utils.utils.DateUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class ErrorUtil {

//+",\n商户编码:" + SharePreferencesUtils.getInstance().getString(ConstantPreference.BOOK_CODE)
//                + ",\n商户名:" + SharePreferencesUtils.getInstance().getString(ConstantPreference.MERCHANT_NAME)
//                + ",\n集市:" + SharePreferencesUtils.getInstance().getString(ConstantPreference.MARKET)
//                + ",\n档口:" + SharePreferencesUtils.getInstance().getString(ConstantPreference.BOOTH)
//                + ",\nMac地址:" + Constant.MAC_ADDRESS

    public static RequestBody setRequestBody(String text) {
        ErrorBean errorBean = new ErrorBean();
        errorBean.setMsgtype("text");
        ErrorBean.TextBean textBean = new ErrorBean.TextBean();
        String error = "时间:" + DateUtil.getCurrentTime()
                +",\n版本:"+ 1
                + ",\n错误信息:" + text;
        textBean.setContent(error);
        errorBean.setText(textBean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(errorBean));
        return body;
    }

    public static void request(String text) {
//        KLog.e(AppManager.getAppManager().currentActivity().getLocalClassName());
//        String text= CustomActivityOnCrash.getAllErrorDetailsFromIntent(AppManager.getAppManager().currentActivity(), AppManager.getAppManager().currentActivity().getIntent());
        RetrofitErrorClient.getInstance().create(ApiService.class).sendError(setRequestBody(text))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

}
