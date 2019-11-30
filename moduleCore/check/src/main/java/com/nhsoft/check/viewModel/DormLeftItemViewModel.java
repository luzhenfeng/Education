package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.DormLeftEntity;

import priv.lzf.mvvmhabit.base.ItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/29.
 */
public class DormLeftItemViewModel extends ItemViewModel<DormRollCallViewModel> {


    public ObservableField<DormLeftEntity> entity=new ObservableField<>();

    public DormLeftItemViewModel(@NonNull DormRollCallViewModel viewModel,DormLeftEntity entity) {
        super(viewModel);
        this.entity.set(entity);
        bindingCommand();
//        initData();
    }

    public void bindingCommand() {
        entity.get().itemClick=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                viewModel.setSelectPos(viewModel.getItemPosition(DormLeftItemViewModel.this));
            }
        });
    }

//    public void initData() {
//        entity.get().title.set(viewModel.mRoomModelList.get(viewModel.getItemPosition(DormLeftItemViewModel.this)).getName());
//    }

}
