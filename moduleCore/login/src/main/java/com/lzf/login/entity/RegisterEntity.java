package com.lzf.login.entity;

import android.annotation.SuppressLint;
import android.databinding.ObservableField;
import android.os.Build;
import android.support.annotation.RequiresApi;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/21.
 */

@RequiresApi(api = Build.VERSION_CODES.O)
public class RegisterEntity {
    public ObservableField<String> serverNet=new ObservableField<>("");
    public ObservableField<String> faceServerNet=new ObservableField<>("");
    @SuppressLint("MissingPermission")
    public ObservableField<String> serial=new ObservableField<>((Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)?Build.getSerial(): Build.SERIAL);
    public BindingCommand save;
}
