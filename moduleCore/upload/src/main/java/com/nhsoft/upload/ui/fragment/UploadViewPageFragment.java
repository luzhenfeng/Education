package com.nhsoft.upload.ui.fragment;

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
        list.add(new UploadFragment());
        list.add(new UploadFragment());
        list.add(new UploadFragment());
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
}
