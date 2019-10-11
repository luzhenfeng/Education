package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.PopupItemViewEntity;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/11.
 */
public class PopupItemViewModel{
    protected PopupViewModel viewModel;

    public ObservableField<PopupItemViewEntity> entity=new ObservableField<>();

    public PopupItemViewModel(@NonNull PopupViewModel viewModel,PopupItemViewEntity entity) {
        this.viewModel = viewModel;
        this.entity.set(entity);
    }

}
