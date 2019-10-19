package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.PhotoEntity;
import com.nhsoft.check.entity.PhotoItemEntity;

import priv.lzf.mvvmhabit.base.ItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by lzf on 2019/10/18.
 * Describe:
 */

public class PhotoItemViewModel extends ItemViewModel<PhotoViewModel> {

    public ObservableField<PhotoItemEntity> entity=new ObservableField<>();
    public PhotoItemViewModel(@NonNull PhotoViewModel viewModel, PhotoItemEntity entity) {
        super(viewModel);
        this.entity.set(entity);
        bindingCommand();
    }

    public void bindingCommand() {

        entity.get().onClickImage=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                if (!entity.get().showError.get()){
                    viewModel.uc.onClickImage.call();
                }
            }
        });
        entity.get().onClickClear=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                if (viewModel.realityNum.get()==viewModel.limit.get()){
                    viewModel.addDefaultPic();
                }
                viewModel.removePosition(viewModel.getItemPosition(PhotoItemViewModel.this));

            }
        });
    }
}
