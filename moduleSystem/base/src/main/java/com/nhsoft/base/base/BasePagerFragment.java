package com.nhsoft.base.base;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhsoft.base.BR;
import com.nhsoft.base.R;
import com.nhsoft.base.base.adapter.BaseFragmentPagerAdapter;
import com.nhsoft.base.base.viewModel.ViewPagerViewModel;
import com.nhsoft.base.databinding.FragmentBasePagerBinding;

import java.util.List;

import priv.lzf.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BasePagerFragment<V extends FragmentBasePagerBinding, VM extends ViewPagerViewModel> extends BaseFragment<V, VM> {


    private List<Fragment> mFragments;
    private List<String> titlePager;

    protected abstract List<Fragment> pagerFragment();

    protected abstract List<String> pagerTitleString();

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_base_pager;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        mFragments = pagerFragment();
        titlePager = pagerTitleString();
        //设置Adapter
        BaseFragmentPagerAdapter pagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), mFragments, titlePager);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
    }

}
