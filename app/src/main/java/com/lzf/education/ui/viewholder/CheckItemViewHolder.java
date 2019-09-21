package com.lzf.education.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzf.education.R;
import com.lzf.education.base.BaseViewHolder;
import com.lzf.education.event.CheckItemEvent;
import com.lzf.education.model.CheckItemModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lzf on 2019/9/21.
 * Describe:
 */

public class CheckItemViewHolder extends BaseViewHolder<CheckItemModel> {

    @BindView(R.id.tv_item_num)
    TextView mTvItemNum;
    @BindView(R.id.tv_item_content)
    TextView mTvItemContent;
    @BindView(R.id.tv_item_class)
    TextView mTvItemClass;
    @BindView(R.id.iv_item_image)
    ImageView mIvItemImage;

    public CheckItemViewHolder(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void setData(final List<CheckItemModel> data, final int position) {
        mTvItemNum.setText(position+1+"");
        mTvItemContent.setText(data.get(position).getItemContent());
        mTvItemClass.setText(data.get(position).getItemClass());
        mIvItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/9/21 弹出选择班级的弹出框
                EventBus.getDefault().post(new CheckItemEvent(position,data.get(position)));
            }
        });
        mTvItemClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/9/21 弹出选择班级的弹出框
                EventBus.getDefault().post(new CheckItemEvent(position,data.get(position)));
            }
        });
    }

}
