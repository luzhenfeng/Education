package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.RightThreeEntity;
import com.nhsoft.check.entity.RightTwoEntity;

import priv.lzf.mvvmhabit.base.MultiItemViewModel;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class RightThreeItemViewModel extends MultiItemViewModel<CheckBaseViewModel> {

    public ObservableField<RightThreeEntity> entity=new ObservableField<>();

    public RightThreeItemViewModel(@NonNull CheckBaseViewModel viewModel,RightThreeEntity entity) {
        super(viewModel);
        this.entity.set(entity);
    }

}
