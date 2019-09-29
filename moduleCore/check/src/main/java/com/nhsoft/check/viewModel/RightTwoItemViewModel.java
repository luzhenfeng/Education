package com.nhsoft.check.viewModel;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.nhsoft.check.databinding.ItemRightTwoBinding;
import com.nhsoft.check.entity.RightTwoEntity;

import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class RightTwoItemViewModel extends MultiItemViewModel<CheckBaseViewModel> {

    public ObservableField<RightTwoEntity> entity=new ObservableField<>();

    public RightTwoItemViewModel(@NonNull CheckBaseViewModel viewModel,RightTwoEntity entity) {
        super(viewModel);
        this.entity.set(entity);
        bindingCommand();
    }

    public void bindingCommand(){
        entity.get().onClick=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().rightTextSelect.set(!entity.get().rightTextSelect.get());
            }
        });
    }

}
