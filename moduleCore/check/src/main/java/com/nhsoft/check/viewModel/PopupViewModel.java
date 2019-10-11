package com.nhsoft.check.viewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.nhsoft.check.BR;
import com.nhsoft.check.R;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/11.
 */
public class PopupViewModel {
    public ObservableField<String> title=new ObservableField<>("选择班级");
    public BindingCommand onClick;
    //给左边RecyclerView添加ObservableList
    public ObservableList<PopupItemViewModel> observableList = new ObservableArrayList<>();
    //给左边RecyclerView添加ItemBinding
    public ItemBinding<PopupItemViewModel> itemBinding =ItemBinding.of(BR.viewModel, R.layout.item_select_class);
}
