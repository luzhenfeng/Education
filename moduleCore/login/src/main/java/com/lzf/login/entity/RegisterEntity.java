package com.lzf.login.entity;

import android.databinding.ObservableField;
import android.os.Build;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/21.
 */
public class RegisterEntity {
    public ObservableField<String> serverNet=new ObservableField<>("");
    public ObservableField<String> faceServerNet=new ObservableField<>("");
    public ObservableField<String> serial=new ObservableField<>(Build.SERIAL);
    public BindingCommand save;
}
