package com.lzf.login.entity;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;

import com.lzf.login.R;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/20.
 */
public class SelectMCodeItemEntity {
    public ObservableField<String> mCodeName=new ObservableField<>("");
    public ObservableField<String> mCode=new ObservableField<>();
    public Drawable image;
    public BindingCommand onItemClick;
}
