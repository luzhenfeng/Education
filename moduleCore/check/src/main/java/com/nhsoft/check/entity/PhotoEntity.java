package com.nhsoft.check.entity;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.nhsoft.check.viewModel.PhotoItemViewModel;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by lzf on 2019/10/18.
 * Describe:
 */

public class PhotoEntity {
    //给RecyclerView添加ObservableList
    public ObservableList<PhotoItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<PhotoItemViewModel> itemBinding ;
    //确认键点击事件
    public BindingCommand onClick;
}
