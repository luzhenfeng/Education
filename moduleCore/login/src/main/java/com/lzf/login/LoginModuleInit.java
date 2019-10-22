package com.lzf.login;

import android.app.Application;


import com.lzf.greendao.db.DaoManager;
import com.lzf.http.data.RetrofitClient;
import com.nhsoft.base.base.IModuleInit;
import com.nhsoft.pxview.constant.Constant;

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
        Constant.baseUrl= SPUtils.getInstance().getString("baseUrl","http://work.nbnz.net/api/");
        KLog.e("登录层初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("登录层初始化 -- onInitLow");
        return false;
    }
}
