package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.LeftEntity;

import priv.lzf.mvvmhabit.base.ItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/27.
 */
public class LeftItemViewModel extends ItemViewModel<CheckBaseViewModel> {

    public ObservableField<LeftEntity> entity=new ObservableField<>();

    public LeftItemViewModel(@NonNull CheckBaseViewModel viewModel,LeftEntity entity) {
        super(viewModel);
        this.entity.set(entity);
        bindingCommand();
    }


    public void bindingCommand() {
        entity.get().itemClick=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                viewModel.setSelectPos(viewModel.getItemPosition(LeftItemViewModel.this));
            }
        });
    }
}
