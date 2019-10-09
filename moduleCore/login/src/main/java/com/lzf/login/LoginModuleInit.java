package com.lzf.login;

import android.app.Application;


import com.lzf.greendao.db.DaoManager;
import com.nhsoft.base.base.IModuleInit;

import priv.lzf.mvvmhabit.utils.KLog;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/6.
 */
public class LoginModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        DaoManager.getInstance().init(application);
        KLog.e("登录层初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("登录层初始化 -- onInitLow");
        return false;
    }
}
