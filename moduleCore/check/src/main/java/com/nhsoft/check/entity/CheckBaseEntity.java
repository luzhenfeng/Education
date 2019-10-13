package com.nhsoft.check.entity;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;

import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.viewModel.LeftItemViewModel;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

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
    //grid分成几份 默认10张床（value值比床数大一）
    public ObservableInt gridCount=new ObservableInt(11);
    //grid是开始位置
    public ObservableInt gridStartPos=new ObservableInt();
    //grid是结束位置
    public ObservableInt gridEndPos=new ObservableInt();
    //是否显示学生
    public ObservableInt isShowStudent=new ObservableInt(View.VISIBLE);
    //tab列表
    public ObservableField<List<String>> tabs=new ObservableField<>();
    //tabLayout切换
    public BindingCommand<TabLayout.Tab> onTabSelectedCommand;
    //违规学生姓名
    public ObservableField<String> students=new ObservableField<>("");
    //添加学生
    public BindingCommand<ImageView> onClickAdd;
}
