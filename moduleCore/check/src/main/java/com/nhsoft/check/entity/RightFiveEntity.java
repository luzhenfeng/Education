package com.nhsoft.check.entity;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.TextView;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/13.
 */
public class RightFiveEntity {
    //上一个条目id
    public ObservableField<String> id=new ObservableField<>("");
    public ObservableField<String> text1=new ObservableField<>("#1");
    public ObservableField<String> text2=new ObservableField<>("#2");
    public ObservableField<String> text3=new ObservableField<>("#3");
    public ObservableField<String> text4=new ObservableField<>("#4");
    public ObservableField<String> text5=new ObservableField<>("#5");
    public ObservableField<String> text6=new ObservableField<>("#6");
    public ObservableField<String> text7=new ObservableField<>("#7");
    public ObservableField<String> text8=new ObservableField<>("#8");
    public ObservableField<String> text9=new ObservableField<>("#9");
    public ObservableField<String> text10=new ObservableField<>("#10");
    public BindingCommand<TextView> onClick1;
    public BindingCommand<TextView> onClick2;
    public BindingCommand<TextView> onClick3;
    public BindingCommand<TextView> onClick4;
    public BindingCommand<TextView> onClick5;
    public BindingCommand<TextView> onClick6;
    public BindingCommand<TextView> onClick7;
    public BindingCommand<TextView> onClick8;
    public BindingCommand<TextView> onClick9;
    public BindingCommand<TextView> onClick10;
    public ObservableBoolean text1Select=new ObservableBoolean(false);
    public ObservableBoolean text2Select=new ObservableBoolean(false);
    public ObservableBoolean text3Select=new ObservableBoolean(false);
    public ObservableBoolean text4Select=new ObservableBoolean(false);
    public ObservableBoolean text5Select=new ObservableBoolean(false);
    public ObservableBoolean text6Select=new ObservableBoolean(false);
    public ObservableBoolean text7Select=new ObservableBoolean(false);
    public ObservableBoolean text8Select=new ObservableBoolean(false);
    public ObservableBoolean text9Select=new ObservableBoolean(false);
    public ObservableBoolean text10Select=new ObservableBoolean(false);

}
