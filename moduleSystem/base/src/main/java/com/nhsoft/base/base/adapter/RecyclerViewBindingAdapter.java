package com.nhsoft.base.base.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.pxview.utils.RelayoutViewTool;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/26.
 */
public class RecyclerViewBindingAdapter<T> extends BindingRecyclerViewAdapter<T> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewDataBinding binding) {
        if (Constant.isScale){
            RelayoutViewTool.relayoutViewWithScale(binding.getRoot(),Constant.mScreenWidthScale);
        }
        return super.onCreateViewHolder(binding);
    }
}
