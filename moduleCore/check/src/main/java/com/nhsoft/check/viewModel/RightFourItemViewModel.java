package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.RightFourEntity;

import priv.lzf.mvvmhabit.base.MultiItemViewModel;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class RightFourItemViewModel extends MultiItemViewModel<CheckBaseViewModel> {

    public ObservableField<RightFourEntity> entity=new ObservableField<>();

    public RightFourItemViewModel(@NonNull CheckBaseViewModel viewModel,RightFourEntity entity) {
        super(viewModel);
        this.entity.set(entity);
    }
}
