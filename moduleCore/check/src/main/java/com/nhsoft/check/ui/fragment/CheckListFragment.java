package com.nhsoft.check.ui.fragment;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhsoft.base.base.adapter.RecyclerViewBindingAdapter;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.FragmentCheckListBinding;
import com.nhsoft.check.viewModel.CheckListViewModel;

import priv.lzf.mvvmhabit.base.BaseFragment;
import priv.lzf.mvvmhabit.utils.ToastUtils;

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
//        viewModel.uc.onTabSelectedCommand.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String string) {
//                ToastUtils.showShort(string);
//            }
//        });
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
