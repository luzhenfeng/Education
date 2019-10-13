package com.nhsoft.check.entity;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class RightFourEntity {
    //序号
    public ObservableInt index=new ObservableInt(1);
    //扣分
    public ObservableField<String> deduction=new ObservableField("0");

    public ObservableField<String> editText=new ObservableField<>("");

    public BindingCommand onClick;

    public BindingCommand onClickImage;

    public ObservableBoolean isSelect=new ObservableBoolean(false);

    //后面选择框图片
    public Drawable image;

}
