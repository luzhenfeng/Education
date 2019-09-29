package com.nhsoft.check.entity;

import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/28.
 */
public class HeadEntity {
    //条目中的文字
    public ObservableField<String> title=new ObservableField<>();
    //条目中的图片
    public Drawable image;
}
