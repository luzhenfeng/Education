package com.nhsoft.check.entity;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/28.
 */
public class RightOneEntity {
    //内容
    public ObservableField<String> content=new ObservableField<>();
    //班级
    public ObservableField<String> classes=new ObservableField<>();
    //序号
    public ObservableInt index=new ObservableInt();
    //右侧点击选择班级
    public BindingCommand<ImageView> onSelectClass;

}
