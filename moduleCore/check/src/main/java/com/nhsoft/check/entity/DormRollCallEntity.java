package com.nhsoft.check.entity;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import com.nhsoft.check.viewModel.DormLeftItemViewModel;
import com.nhsoft.check.viewModel.DormRightItemViewModel;
import com.nhsoft.utils.utils.DateUtil;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/29.
 */
public class DormRollCallEntity {
    public ObservableField<String> floor=new ObservableField<>("1号楼");
    public ObservableField<String> date=new ObservableField<>(DateUtil.getCurrentTime());
//    public ObservableField<String> noUpdateNum=new ObservableField<>("");

    //给左边RecyclerView添加ObservableList
    public ObservableList<DormLeftItemViewModel> observableLeftList = new ObservableArrayList<>();
    //给左边RecyclerView添加ItemBinding
    public ItemBinding<DormLeftItemViewModel> itemLeftBinding ;

    //给右边RecyclerView添加ObservableList
    public ObservableList<DormRightItemViewModel> observableRightList = new ObservableArrayList<>();
    //右边RecyclerView多布局添加ItemBinding
    public ItemBinding<DormRightItemViewModel> itemRightBinding ;

    public BindingCommand tvFloor;
    public BindingCommand tvDate;
    public BindingCommand tvTime;
    public BindingCommand submit;
    public BindingCommand noUpdateNumSubmit;
    public BindingCommand throwStudent;
}
