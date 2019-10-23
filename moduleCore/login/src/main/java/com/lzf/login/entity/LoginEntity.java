package com.lzf.login.entity;

import android.databinding.ObservableField;
import android.widget.Button;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/3.
 */
public class LoginEntity {
    public ObservableField<String> username=new ObservableField<>("");
    public ObservableField<String> password=new ObservableField<>("");
    public BindingCommand<Button> login;
    public BindingCommand<Button> down;
    public BindingCommand register;
}
