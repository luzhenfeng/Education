package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.RightFiveEntity;

import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/13.
 */
public class RightFiveItemViewModel extends MultiItemViewModel<CheckBaseViewModel> {

    public ObservableField<RightFiveEntity> entity=new ObservableField<>();
    public RightFiveItemViewModel(@NonNull CheckBaseViewModel viewModel,RightFiveEntity entity) {
        super(viewModel);
        this.entity.set(entity);
        bindingCommand();
    }

    public void bindingCommand(){
        entity.get().onClick1=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text1Select.set(!entity.get().text1Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
        entity.get().onClick2=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text2Select.set(!entity.get().text2Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
        entity.get().onClick3=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text3Select.set(!entity.get().text3Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
        entity.get().onClick4=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text4Select.set(!entity.get().text4Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
        entity.get().onClick5=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text5Select.set(!entity.get().text5Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
        entity.get().onClick6=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text6Select.set(!entity.get().text6Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
        entity.get().onClick7=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text7Select.set(!entity.get().text7Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
        entity.get().onClick8=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text8Select.set(!entity.get().text8Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
        entity.get().onClick9=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text9Select.set(!entity.get().text9Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
        entity.get().onClick10=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                entity.get().text10Select.set(!entity.get().text10Select.get());
                viewModel.onRight5SelectBed(viewModel.getRightItemPosition(RightFiveItemViewModel.this));
            }
        });
    }
}
