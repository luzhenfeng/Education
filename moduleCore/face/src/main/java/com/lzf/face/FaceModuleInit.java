package com.lzf.face;

import android.app.Application;

import com.lzf.greendao.db.DaoManager;
import com.megvii.facetrack.QualityFilter;
import com.nhsoft.base.base.IModuleInit;
import com.nhsoft.pxview.constant.Constant;

import priv.lzf.mvvmhabit.crash.CaocConfig;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.SPUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/6.
 */
public class FaceModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        QualityFilter.init(application);
        KLog.e("刷脸层层初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        KLog.e("刷脸层初始化 -- onInitLow");
        return false;
    }

}
