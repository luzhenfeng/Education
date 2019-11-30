package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.DormRightEntity;

import priv.lzf.mvvmhabit.base.ItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/29.
 */
public class DormRightItemViewModel extends ItemViewModel<DormRollCallViewModel> {

    public ObservableField<DormRightEntity> entity=new ObservableField<>();
    public DormRightItemViewModel(@NonNull DormRollCallViewModel viewModel,DormRightEntity entity) {
        super(viewModel);
        this.entity.set(entity);
        bindingCommand();
    }

    public void bindingCommand() {
        entity.get().select=new BindingCommand(new BindingConsumer<String>() {
            @Override
            public void call(String s) {
                if (s.equals("到")){
                    entity.get().type.set(1);
                }else if (s.equals("缺")){
                    entity.get().type.set(2);
                }else if (s.equals("假")){
                    entity.get().type.set(3);
                }else if (s.equals("晚")){
                    entity.get().type.set(4);
                }else if (s.equals("出")){
                    entity.get().type.set(5);
                }
            }
        });
    }

}
