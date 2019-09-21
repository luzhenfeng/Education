package com.lzf.education.ui.adpter;

import android.view.View;
import android.widget.TextView;

import com.lzf.education.R;
import com.lzf.education.base.BaseAdapter;
import com.lzf.education.base.BaseViewHolder;
import com.lzf.education.model.CheckItemModel;
import com.lzf.education.ui.viewholder.CheckItemViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lzf on 2019/9/21.
 * Describe:
 */

public class CheckItemAdapter extends BaseAdapter<CheckItemModel> {


    public CheckItemAdapter(List<CheckItemModel> dataList) {
        super(dataList);
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.item_check_item;
    }

    @Override
    public BaseViewHolder getHolder(View v, int viewType) {
        return new CheckItemViewHolder(v, viewType);
    }

}
