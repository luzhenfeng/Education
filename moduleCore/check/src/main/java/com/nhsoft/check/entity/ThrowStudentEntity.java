package com.nhsoft.check.entity;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.design.widget.TabLayout;

import com.nhsoft.check.viewModel.ThrowStudentItemViewModel;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/12/10.
 */
public class ThrowStudentEntity {
    //给左边RecyclerView添加ObservableList
    public ObservableList<ThrowStudentItemViewModel> observableList = new ObservableArrayList<>();
    //给左边RecyclerView添加ItemBinding
    public ItemBinding<ThrowStudentItemViewModel> itemBinding ;
    public BindingCommand<TabLayout.Tab> onTabSelectedCommand;

}
