package com.lzf.login;

import android.app.Application;


import com.lzf.greendao.db.DaoManager;
import com.lzf.http.data.RetrofitClient;
import com.lzf.login.ui.activity.LoginActivity;
import com.nhsoft.base.base.IModuleInit;
import com.nhsoft.pxview.constant.Constant;

import priv.lzf.mvvmhabit.crash.CaocConfig;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.SPUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/6.
 */
public class LoginModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        DaoManager.getInstance().init(application);
//        Constant.baseUrl= SPUtils.getInstance().getString("baseUrl","http://work.nbnz.net/api/");
        Constant.baseUrl= SPUtils.getInstance().getString("baseUrl","http://10.44.48.71:8080/api/");
        Constant.baseFaceUrl= SPUtils.getInstance().getString("baseFaceUrl","http://192.168.0.50");
//        initCrash();
        KLog.e("登录层初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("登录层初始化 -- onInitLow");
        return false;
    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.drawable.customactivityoncrash_error_image) //错误图标
                .restartActivity(LoginActivity.class) //重新启动后的activity
                .errorActivity(MyErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new CustomEventListener()) //崩溃后的错误监听
                .apply();
    }
}
