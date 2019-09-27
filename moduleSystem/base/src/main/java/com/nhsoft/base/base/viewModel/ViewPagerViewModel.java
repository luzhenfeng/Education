package com.nhsoft.base.base.viewModel;

import android.app.Application;
import android.support.annotation.NonNull;

import priv.lzf.mvvmhabit.base.BaseModel;
import priv.lzf.mvvmhabit.base.BaseViewModel;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/27.
 */
public class ViewPagerViewModel<M extends BaseModel> extends BaseViewModel<M> {
    public ViewPagerViewModel(@NonNull Application application) {
        super(application);
    }
}
