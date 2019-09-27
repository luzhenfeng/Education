package com.nhsoft.upload.adapter;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.pxview.utils.RelayoutViewTool;
import com.nhsoft.upload.databinding.ItemViewpagerBinding;
import com.nhsoft.upload.viewModel.ViewPagerItemViewModel;

import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;

/**
 * Created by lzf on 2019/9/26.
 * Describe:
 */

public class ViewPagerBindingAdapter extends BindingViewPagerAdapter<ViewPagerItemViewModel>{
    @Override
    public void onBindBinding(final ViewDataBinding binding, int variableId, int layoutRes, final int position, ViewPagerItemViewModel item) {
        if (Constant.isScale){
            RelayoutViewTool.relayoutViewWithScale(binding.getRoot(),Constant.mScreenWidthScale);
        }
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        //这里可以强转成ViewPagerItemViewModel对应的ViewDataBinding，
        ItemViewpagerBinding _binding = (ItemViewpagerBinding) binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
