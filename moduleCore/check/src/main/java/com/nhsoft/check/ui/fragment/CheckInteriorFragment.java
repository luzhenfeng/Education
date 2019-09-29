package com.nhsoft.check.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nhsoft.base.base.adapter.RecyclerViewBindingAdapter;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.FragmentCheckInteriorBinding;
import com.nhsoft.check.viewModel.CheckInteriorViewModel;

import priv.lzf.mvvmhabit.base.BaseFragment;

/**
 * 内务检查
 */
public class CheckInteriorFragment extends BaseFragment<FragmentCheckInteriorBinding, CheckInteriorViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_check_interior;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.setAdapter(new RecyclerViewBindingAdapter());
        viewModel.initLeftItem();
        binding.setRightAdapter(new RecyclerViewBindingAdapter());
        viewModel.initRightItem();
    }
}
