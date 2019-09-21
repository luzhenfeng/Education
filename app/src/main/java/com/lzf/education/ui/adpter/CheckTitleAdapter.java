package com.lzf.education.ui.adpter;

import android.view.View;

import com.lzf.education.R;
import com.lzf.education.base.BaseAdapter;
import com.lzf.education.base.BaseViewHolder;
import com.lzf.education.model.CheckTitleModel;
import com.lzf.education.ui.viewholder.CheckTitleViewHolder;

import java.util.List;


/**
 * Created by lzf on 2019/9/21.
 * Describe:
 */

public class CheckTitleAdapter extends BaseAdapter<CheckTitleModel> {

    public CheckTitleAdapter(List<CheckTitleModel> dataList) {
        super(dataList);
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.item_check_title;
    }

    @Override
    public BaseViewHolder getHolder(View v, int viewType) {
        return new CheckTitleViewHolder(v, viewType);
    }
}
