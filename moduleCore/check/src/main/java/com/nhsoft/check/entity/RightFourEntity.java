package com.nhsoft.check.entity;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class RightFourEntity {
    //序号
    public ObservableInt index=new ObservableInt(1);
    //扣分
    public ObservableInt deduction=new ObservableInt(0);
    public ObservableField<String> editText=new ObservableField<>("");
}
