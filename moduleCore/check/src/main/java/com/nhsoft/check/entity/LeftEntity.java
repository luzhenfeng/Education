package com.nhsoft.check.entity;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;

import com.nhsoft.check.R;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by lzf on 2019/9/21.
 * Describe:
 */

public class LeftEntity {
    //条目中的文字
    public ObservableField<String> title=new ObservableField<>();
    //条目中的图片
    public Drawable image;
    //条目的背景
    public Drawable mDrawable;
    //条目的点击事件
    public BindingCommand itemClick;
}
