package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.nhsoft.check.entity.RightOneEntity;

import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.utils.ToastUtils;

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
        entity.get().onSelectClass=new BindingCommand<ImageView>(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("选择班级");
            }
        });
    }


}
