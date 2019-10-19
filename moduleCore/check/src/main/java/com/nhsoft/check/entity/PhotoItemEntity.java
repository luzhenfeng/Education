package com.nhsoft.check.entity;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import java.io.Serializable;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by lzf on 2019/10/18.
 * Describe:
 */

public class PhotoItemEntity {
//    private String imagePath;
//    private boolean showError=true;//是否显示错号
//    private boolean isClick;//是否能点击
    public ObservableField<String> imagePath=new ObservableField<>("");
    public ObservableBoolean showError=new ObservableBoolean(false);
    public ObservableBoolean isClick=new ObservableBoolean(false);
    public BindingCommand onClickImage;
    public BindingCommand onClickClear;
}
