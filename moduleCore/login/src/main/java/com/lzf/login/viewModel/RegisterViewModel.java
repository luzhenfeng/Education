package com.lzf.login.viewModel;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.data.RetrofitClient;
import com.lzf.login.entity.RegisterEntity;
import com.nhsoft.base.base.ConstantMessage;
import com.nhsoft.pxview.constant.Constant;

import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/21.
 */
public class RegisterViewModel extends BaseViewModel<Repository> {


    public ObservableField<RegisterEntity> entity=new ObservableField<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        model= Injection.provideDemoRepository(Constant.baseUrl);
        this.entity.set(new RegisterEntity());
        entity.get().serverNet.set(model.getBaseUrl());
        entity.get().faceServerNet.set(model.getBaseFaceUrl());
        bindingCommand();
    }

    @Override
    public void bindingCommand() {
        super.bindingCommand();
        entity.get().save=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                save();
            }
        });
    }

    public void save(){
        if (entity.get().serverNet.get()==null||entity.get().serverNet.get().equals("")){
            ToastUtils.showShort("请输入服务器地址");
            return;
        }
        if (entity.get().faceServerNet.get()==null||entity.get().faceServerNet.get().equals("")){
            ToastUtils.showShort("请输入人脸识别服务器地址");
            return;
        }
        model.saveBaseUrl(entity.get().serverNet.get());
        model.saveBaseFaceUrl(entity.get().faceServerNet.get());
        Constant.baseUrl=model.getBaseUrl();
        Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_REGISTERVIEWMODEL_NETWORK);
        ToastUtils.showShort("保存成功,请退出程序重新登录");
    }
}
