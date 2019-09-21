package com.lzf.education.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/20.
 */

public  abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
    protected OnViewClickListener mOnViewClickListener = null;
    protected OnViewLongClickListener mOnViewLongClickListener = null;
    protected int viewType;
    protected Context context;

    public BaseViewHolder(View itemView,int viewType) {
        super(itemView);
        this.viewType=viewType;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        ButterKnife.bind(this,itemView);
    }

    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }
    public interface OnViewLongClickListener{
        void onViewLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }

    public void setOnViewLongClickListener(OnViewLongClickListener listener) {
        this.mOnViewLongClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener.onViewClick(view, this.getLayoutPosition());
        }
    }
    public void setContext(Context context){
        this.context=context;
    }



    @Override
    public boolean onLongClick(View view) {
        if (mOnViewLongClickListener != null) {
            mOnViewLongClickListener.onViewLongClick(view, this.getLayoutPosition());
        }
        return false;
    }

    public abstract void setData(List<T> data, int position);

}

