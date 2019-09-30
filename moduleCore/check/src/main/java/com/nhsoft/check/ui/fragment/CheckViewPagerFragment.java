package com.nhsoft.check.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.nhsoft.base.base.BasePagerFragment;
import com.nhsoft.base.base.viewModel.ViewPagerViewModel;
import com.nhsoft.base.databinding.FragmentBasePagerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/27.
 */
public class CheckViewPagerFragment extends BasePagerFragment<FragmentBasePagerBinding, ViewPagerViewModel> {
    @Override
    protected List<Fragment> pagerFragment() {
        List<Fragment> list = new ArrayList<>();
        list.add(new CheckInteriorFragment());
        list.add(new CheckRoutineFragment());
        list.add(new CheckRoutineFragment());
        list.add(new CheckRoutineFragment());
        list.add(new CheckRoutineFragment());
        list.add(new CheckRoutineFragment());
        return list;
    }

    @Override
    protected List<String> pagerTitleString() {
        List<String> list = new ArrayList<>();
        list.add("内务检查");
        list.add("大扫除");
        list.add("晨读");
        list.add("内务提醒");
        list.add("常规");
        list.add("妨碍检查");
        return list;
    }

    @Override
    public void initData() {
        super.initData();
        binding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
//        binding.viewPager.setOffscreenPageLimit(0);
    }
}
