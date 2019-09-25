package com.nhsoft.pxview.base;

import android.app.Application;

import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.pxview.constant.ConstantSystem;
import com.nhsoft.pxview.utils.ScreenWHUtil;


/**
 * Created by lzf on 2019/9/23.
 * Describe:
 */

public class PXApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        getScreenWidthScale();
    }
    private float getScreenWidthScale(){
        int width= ScreenWHUtil.getWidth(getApplicationContext());
        Constant.mScreenWidthScale=width/ ConstantSystem.UI_WIDTH;
        Constant.isScale=Constant.mScreenWidthScale==1?false:true;
        return Constant.mScreenWidthScale;
    }
    
}
