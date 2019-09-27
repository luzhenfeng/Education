package com.nhsoft.upload.ui.fragment;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
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
public class UploadViewPageFragment extends BasePagerFragment<FragmentBasePagerBinding, ViewPagerViewModel> {


    @Override
    protected List<Fragment> pagerFragment() {
        List<Fragment> list = new ArrayList<>();
        list.add(new UploadOneFragment());
        list.add(new UploadOneFragment());
        list.add(new UploadOneFragment());
        return list;
    }

    @Override
    protected List<String> pagerTitleString() {
        List<String> list = new ArrayList<>();
        list.add("未上传");
        list.add("已上传");
        list.add("全部");
        return list;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.onPageSelectedCommand.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

            }
        });
    }
}
