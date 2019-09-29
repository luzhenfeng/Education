package com.nhsoft.check.viewModel;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.HeadEntity;

import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.base.MultiItemViewModel;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/28.
 */
public class HeadViewModel extends MultiItemViewModel<CheckBaseViewModel> {

    public ObservableField<HeadEntity> entity=new ObservableField<>();

    public HeadViewModel(@NonNull CheckBaseViewModel viewModel,HeadEntity headEntity) {
        super(viewModel);
        entity.set(headEntity);
    }
}
