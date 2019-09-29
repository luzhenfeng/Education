package com.nhsoft.check.entity;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class RightTwoEntity {
    public ObservableField<String> leftText=new ObservableField<>("床号:");
    public ObservableInt leftTextShow=new ObservableInt(View.GONE);
    public ObservableField<String> rightText=new ObservableField<>("#1");
    public ObservableBoolean rightTextSelect=new ObservableBoolean(false);
    public BindingCommand<TextView> onClick;
}
