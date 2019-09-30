package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.RightOneEntity;

import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/28.
 */
public class RightOneItemViewModel extends MultiItemViewModel<CheckBaseViewModel> {


    public final static int CheckInteriorViewModel=1;
    public final static int CheckRoutineViewModel=2;

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
                viewModel.onSelectClassImage();
            }
        });
    }


}
