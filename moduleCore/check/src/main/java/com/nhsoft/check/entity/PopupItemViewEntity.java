package com.nhsoft.check.entity;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;


/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/11.
 */
public class PopupItemViewEntity {
    public ObservableField<String> text=new ObservableField<>("机电2");
    //右侧图片
    public Drawable selectState;
    //条目点击
    public BindingCommand onItemClick;
}
