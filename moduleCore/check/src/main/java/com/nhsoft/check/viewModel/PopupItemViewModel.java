package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.PopupItemViewEntity;
import com.nhsoft.check.utils.CustomPopWindowUtil;

import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

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
        bindingCommand();
    }

    public void bindingCommand() {
        entity.get().onItemClick =new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                viewModel.setSelectPos(viewModel.getItemPosition(PopupItemViewModel.this));
            }
        });
    }
}
