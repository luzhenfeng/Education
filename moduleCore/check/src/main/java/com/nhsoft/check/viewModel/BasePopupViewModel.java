package com.nhsoft.check.viewModel;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.nhsoft.check.databinding.PopupSelectClassBinding;
import com.nhsoft.check.entity.PopupItemViewEntity;
import com.nhsoft.check.utils.CustomPopWindowUtil;

import java.util.List;

import priv.lzf.mvvmhabit.base.BaseModel;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/11.
 */
public class BasePopupViewModel<M extends BaseModel> extends BaseViewModel<M> {

    //弹出框的binding
    public PopupSelectClassBinding mSelectClassBinding;

    //弹出框的viewModel
    public PopupViewModel mPopupViewModel=new PopupViewModel();

    public BasePopupViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void bindingCommand() {
        super.bindingCommand();
        mPopupViewModel.onClick=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                CustomPopWindowUtil.getInstance().dismiss();
            }
        });
    }

    public void setPopupBinding(Context context){
        mSelectClassBinding= (PopupSelectClassBinding) CustomPopWindowUtil.getInstance().getViewDataBinding(context);
    }

    public PopupSelectClassBinding getPopupBinding(){
        return mSelectClassBinding;
    }

    public void setTitle(String title){
        mPopupViewModel.title.set(title);
        mSelectClassBinding.setViewModel(mPopupViewModel);
    }

    public void setData(){
        mPopupViewModel.observableList.clear();
        for (int i=0;i<10;i++){
            PopupItemViewEntity entity=new PopupItemViewEntity();
            entity.text.set(i+"");
            PopupItemViewModel popupItemViewModel=new PopupItemViewModel(mPopupViewModel,entity);
            mPopupViewModel.observableList.add(popupItemViewModel);
        }
        mSelectClassBinding.setViewModel(mPopupViewModel);
    }
}
