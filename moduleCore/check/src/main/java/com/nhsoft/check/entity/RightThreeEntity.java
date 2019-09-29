package com.nhsoft.check.entity;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class RightThreeEntity {
    //序号
    public ObservableInt index=new ObservableInt(2);
    public ObservableField<String> leftText=new ObservableField<>("卫生角旁边发现垃圾:");
    public ObservableField<String> rightText=new ObservableField<>("个。");
    public ObservableField<String> editText=new ObservableField<>("");
}
