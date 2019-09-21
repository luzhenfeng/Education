package com.lzf.education;

import android.app.Application;

import com.lzf.education.constant.Constant;
import com.lzf.education.constant.ConstantSystem;
import com.lzf.education.utils.ScreenWHUtil;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/19.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        getScreenWidthScale();
    }
    private float getScreenWidthScale(){
        int width=ScreenWHUtil.getWidth(getApplicationContext());
        Constant.mScreenWidthScale=width/ ConstantSystem.UI_WIDTH;
        Constant.isScale=Constant.mScreenWidthScale==1?false:true;
        return Constant.mScreenWidthScale;
    }
}
