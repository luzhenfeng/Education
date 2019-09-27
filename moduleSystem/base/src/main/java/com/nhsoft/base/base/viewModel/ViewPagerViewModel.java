package com.nhsoft.base.base.viewModel;

import android.app.Application;
import android.support.annotation.NonNull;

import priv.lzf.mvvmhabit.base.BaseModel;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/27.
 */
public class ViewPagerViewModel<M extends BaseModel> extends BaseViewModel<M> {

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //ViewPager切换监听
        public SingleLiveEvent<Integer> onPageSelectedCommand = new SingleLiveEvent<>();
    }

    public ViewPagerViewModel(@NonNull Application application) {
        super(application);
    }
    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer index) {
            ToastUtils.showShort("ViewPager切换：" + index);
            uc.onPageSelectedCommand.setValue(index);
        }
    });
}
