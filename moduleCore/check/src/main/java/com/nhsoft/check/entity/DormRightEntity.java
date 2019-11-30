package com.nhsoft.check.entity;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/29.
 */
public class DormRightEntity {
    public ObservableField<String> name=new ObservableField<>("");
    public ObservableField<String> userid=new ObservableField<>("");
    public ObservableInt bedno=new ObservableInt(0);
    public ObservableInt type=new ObservableInt(0);//0无选择，1到，2缺，3假，4晚，5出
    //头像
    public ObservableField<String> headPic=new ObservableField<>("");
    public ObservableInt pos=new ObservableInt(1);

    public BindingCommand<String> select;//1到

}
