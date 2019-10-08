package com.nhsoft.base.config;

/**
 * Created by lzf on 2019/9/16.
 * Describe:组件生命周期反射类名管理，在这里注册需要初始化的组件，通过反射动态调用各个组件的初始化方法
 * 注意：以下模块中初始化的Module类不能被混淆
 */

public class ModuleLifecycleReflexs {
    private static final String BaseInit = "com.nhsoft.base.base.BaseModuleInit";
    //上传文件模块
    private static final String UploadInit = "com.nhsoft.upload.UploadModuleInit";
    //检查模块
    private static final String CheckInit = "com.nhsoft.check.CheckModuleInit";

    //检查模块
    private static final String LoginInit = "com.lzf.login.LoginModuleInit";

    public static String[] initModuleNames = {BaseInit, UploadInit,CheckInit,LoginInit};
}
