package com.lzf.login.entity;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.lzf.login.BR;
import com.lzf.login.R;
import com.lzf.login.viewModel.SelectMCodeItemViewModel;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/20.
 */
public class SelectMCodeEntity {
    //给RecyclerView添加ObservableList
    public ObservableList<SelectMCodeItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<SelectMCodeItemViewModel> itemBinding ;
}
