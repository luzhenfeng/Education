package com.nhsoft.check.ui.fragment;


import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhsoft.base.base.adapter.RecyclerViewBindingAdapter;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.FragmentCheckListBinding;
import com.nhsoft.check.databinding.PopupSelectClassBinding;
import com.nhsoft.check.viewModel.CheckListViewModel;
import com.nhsoft.check.viewModel.PopupViewModel;
import com.nhsoft.pxview.constant.Constant;

import priv.lzf.mvvmhabit.base.BaseFragment;
import priv.lzf.mvvmhabit.utils.ToastUtils;
import priv.lzf.mvvmhabit.widget.CustomPopWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListFragment extends BaseFragment<FragmentCheckListBinding, CheckListViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_check_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.type.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
//                if (integer.intValue()==1){
//                View view=LayoutInflater.from(getContext()).inflate(R.layout.popup_select_class,null);
//                view.setTag("layout/popup_select_class_0");
//                PopupSelectClassBinding binding= DataBindingUtil.bind(view);
//                PopupViewModel popupViewModel=new PopupViewModel();
//                binding.setViewModel(popupViewModel);
//                new CustomPopWindow.PopupWindowBuilder(getContext())
//                        .setView(binding.getRoot())
//                        .size(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
//                        .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
//                        .setBgDarkAlpha(0.7f) // 控制亮度
//                        .create()
//                        .showAtLocation(getView(), Gravity.BOTTOM, 0,(int)(Constant.mScreenWidthScale * 252));
//                }
            }
        });
        viewModel.uc.setTabs.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                setTabs();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        binding.setAdapter(new RecyclerViewBindingAdapter());
        binding.setRightAdapter(new RecyclerViewBindingAdapter());
    }

    private void setTabs(){
        if (viewModel.entity.get().tabs.get().size()>5){
            binding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        for (String s:viewModel.entity.get().tabs.get()){
            binding.tabs.addTab(binding.tabs.newTab().setText(s));
        }
    }
}
