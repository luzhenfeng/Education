package com.nhsoft.check.viewModel;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.PopupSelectClassBinding;
import com.nhsoft.check.entity.PopupItemViewEntity;
import com.nhsoft.check.message.ConstantMessage;
import com.nhsoft.check.utils.CustomPopWindowUtil;

import java.util.ArrayList;
import java.util.List;

import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.base.BaseModel;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.utils.MaterialDialogUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

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

    public void isShowButton(int visiable){
        mPopupViewModel.isShowButton.set(visiable);
        mSelectClassBinding.setViewModel(mPopupViewModel);
    }


}
