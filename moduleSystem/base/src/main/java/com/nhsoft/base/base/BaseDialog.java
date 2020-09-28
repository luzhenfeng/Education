package com.nhsoft.base.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.pxview.utils.RelayoutViewTool;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.base.IBaseView;
import priv.lzf.mvvmhabit.base.ViewModelFactory;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.utils.KLog;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/12/4.
 */
public abstract class BaseDialog<V extends ViewDataBinding, VM extends BaseViewModel> extends AlertDialog implements IBaseView {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    public BaseDialog(@NonNull Context context) {
        this(context,0);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void setContentView(int layoutResID) {
        View mView = View.inflate(getContext(), layoutResID, null);
        this.setContentView(mView);
    }

    @Override
    public void setContentView(@NonNull View view) {
        if (Constant.isScale){
            RelayoutViewTool.relayoutViewWithScale(view, Constant.mScreenWidthScale);
        }
        binding= DataBindingUtil.bind(view);
        super.setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        //私有的ViewModel与View的契约事件回调逻辑
//        registorUIChangeLiveDataCallBack();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
    }


    @Override
    public void dismiss() {
        super.dismiss();
        //解除Messenger注册
        Messenger.getDefault().unregister(viewModel);
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if(binding != null){
            binding.unbind();
        }
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(modelClass);

        }
        binding.setVariable(viewModelId, viewModel);
//        //让ViewModel拥有View的生命周期感应
//        getLifecycle().addObserver(viewModel);
//        //注入RxLifecycle生命周期
//        viewModel.injectLifecycleProvider(this);
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Class<T> cls) {
        T t= ViewModelFactory.getInstance(AppManager.getAppManager().currentActivity().getApplication()).create(cls);
        KLog.e(t.getClass().getName());
        return t;
    }


    @Override
    public void initParam() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }
}
