package com.nhsoft.check.viewModel;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.nhsoft.check.R;
import com.nhsoft.check.databinding.ItemRightOneBinding;
import com.nhsoft.check.entity.RightOneEntity;

import priv.lzf.mvvmhabit.base.BaseApplication;
import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/28.
 */
public class RightOneItemViewModel extends MultiItemViewModel<CheckBaseViewModel> {


    public ObservableField<RightOneEntity> entity=new ObservableField<>();

    public RightOneItemViewModel(@NonNull CheckBaseViewModel viewModel,RightOneEntity entity) {
        super(viewModel);
        this.entity.set(entity);
        bindingCommand();
    }

    public void bindingCommand(){
        entity.get().onSelectClass=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                viewModel.onSelectClass(viewModel.getRightItemPosition(RightOneItemViewModel.this));
            }
        });
        entity.get().onItemClick=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                viewModel.onRight1ItemClick(viewModel.getRightItemPosition(RightOneItemViewModel.this));
            }
        });
    }


}
