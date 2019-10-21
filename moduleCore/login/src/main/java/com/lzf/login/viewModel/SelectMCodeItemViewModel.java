package com.lzf.login.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lzf.login.entity.SelectMCodeItemEntity;
import com.nhsoft.base.router.RouterActivityPath;

import priv.lzf.mvvmhabit.base.ItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/20.
 */
public class SelectMCodeItemViewModel extends ItemViewModel<SelectMCodeViewModel> {

    public ObservableField<SelectMCodeItemEntity> entity=new ObservableField<>();

    public SelectMCodeItemViewModel(@NonNull SelectMCodeViewModel viewModel,SelectMCodeItemEntity entity) {
        super(viewModel);
        this.entity.set(entity);
        bindingCommand();
    }

    public void bindingCommand() {
        entity.get().onItemClick=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                viewModel.login(entity.get().mCode.get());
            }
        });
    }

}
