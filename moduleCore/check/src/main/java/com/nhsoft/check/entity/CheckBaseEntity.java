package com.nhsoft.check.entity;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.viewModel.LeftItemViewModel;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.base.MultiItemViewModel;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/27.
 */
public class CheckBaseEntity {
    //给左边RecyclerView添加ObservableList
    public ObservableList<LeftItemViewModel> observableLeftList = new ObservableArrayList<>();
    //给左边RecyclerView添加ItemBinding
    public ItemBinding<LeftItemViewModel> itemLeftBinding ;

    //给右边RecyclerView添加ObservableList
    public ObservableList<MultiItemViewModel> observableRightList = new ObservableArrayList<>();
    //右边RecyclerView多布局添加ItemBinding
    public ItemBinding<MultiItemViewModel> itemRightBinding ;

}
