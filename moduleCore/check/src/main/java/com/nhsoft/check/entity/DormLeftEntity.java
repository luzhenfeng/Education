package com.nhsoft.check.entity;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/29.
 */
public class DormLeftEntity {
    //条目中的文字
    public ObservableField<String> title=new ObservableField<>();
    //是否选中
    public ObservableBoolean select=new ObservableBoolean(false);
    //条目的点击事件
    public BindingCommand itemClick;
}
