package com.nhsoft.upload.ui.fragment;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nhsoft.upload.BR;
import com.nhsoft.upload.R;
import com.nhsoft.upload.adapter.RecyclerViewBindingAdapter;
import com.nhsoft.upload.databinding.FragmentUploadBinding;
import com.nhsoft.upload.viewModel.UploadViewModel;

import priv.lzf.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends BaseFragment<FragmentUploadBinding, UploadViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_upload;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.onTabSelectedCommand.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        binding.setAdapter(new RecyclerViewBindingAdapter());
        viewModel.requestNetWork();
        setTabs();
    }

    private void setTabs(){
        for (String s:viewModel.tabs){
            binding.tabs.addTab(binding.tabs.newTab().setText(s));
        }
    }
}
