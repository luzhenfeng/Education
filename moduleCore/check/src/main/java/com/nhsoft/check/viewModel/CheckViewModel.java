package com.nhsoft.check.viewModel;

import android.app.Application;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import com.nhsoft.check.entity.CheckEntity;
import com.nhsoft.utils.utils.DateUtil;

import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * Created by lzf on 2019/9/27.
 * Describe:
 */

public class CheckViewModel extends BaseViewModel {

    public ObservableField<CheckEntity> entity=new ObservableField<>();

    public CheckViewModel(@NonNull Application application) {
        super(application);
        entity.set(new CheckEntity());
        bindingCommand();
    }

    @Override
    public void bindingCommand(){
        //扣
        entity.get().ivFraction=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("扣");
            }
        });

        //扫描
        entity.get().ivScan=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("扫描");
            }
        });

        //上传文件
        entity.get().ivUpload=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("上传文件");
            }
        });

        //相机
        entity.get().ivCamera=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("相机");
            }
        });

        //home
        entity.get().ivHome=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("home");
            }
        });

        //提交
        entity.get().tvSubmit=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("提交");
            }
        });
    }



    /**
     * 右上角数量
     * @param num
     * @return
     */
    private String getText(int num){
        if (num<=99){
            return String.valueOf(num);
        }
        return "99+";
    }

    /**
     * 右上角是否显示
     * @param num
     * @return
     */
    private boolean isVisible(int num){
        if (num<=0){
            return false;
        }
        return true;
    }

}
