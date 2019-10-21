package com.nhsoft.check.entity;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.lzf.http.entity.AllCategoryModel;

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
    //后面选择框图片
    public Drawable image;
    //是否选中
    public ObservableBoolean isSelect=new ObservableBoolean(false);
    //右侧点击选择班级
    public BindingCommand onItemClick;
    //焦点
    public ObservableBoolean hasFou=new ObservableBoolean(false);
    //条目
    public ObservableField<AllCategoryModel.ItemsBean> items=new ObservableField<>();
    //是否显示床号
    public ObservableInt showbed=new ObservableInt(0);
    //是否显示次数
    public ObservableBoolean showCount=new ObservableBoolean(false);
    //次数 加号
    public BindingCommand onItemAddClick;
    //次数 减号
    public BindingCommand onItemMinusClick;
    //次数
    public ObservableInt count=new ObservableInt(1);
}
