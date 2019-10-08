package com.nhsoft.app1;



import android.content.Context;
import android.support.multidex.MultiDex;

import com.nhsoft.base.config.ModuleLifecycleConfig;

import priv.lzf.mvvmhabit.base.BaseApplication;

/**
 * Created by goldze on 2018/6/21 0021.
 */

public class AppApplication extends BaseApplication {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化组件(靠前)
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
        //....
        //初始化组件(靠后)
        ModuleLifecycleConfig.getInstance().initModuleLow(this);
    }
}
