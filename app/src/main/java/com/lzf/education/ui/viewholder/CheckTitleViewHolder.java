package com.lzf.education.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzf.education.R;
import com.lzf.education.base.BaseViewHolder;
import com.lzf.education.event.CheckTitleEvent;
import com.lzf.education.model.CheckTitleModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;


/**
 * Created by lzf on 2019/9/21.
 * Describe:
 */

public class CheckTitleViewHolder extends BaseViewHolder<CheckTitleModel> {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private static int selectPos=0;

    public CheckTitleViewHolder(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void setData(final List<CheckTitleModel> data, final int position) {
        ivImage.setImageDrawable(context.getResources().getDrawable(data.get(position).getDrawable()));
        tvTitle.setText(data.get(position).getTitle());
        if (selectPos==position){
            itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }else {
            itemView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPos=position;
                EventBus.getDefault().post(new CheckTitleEvent(selectPos,data.get(selectPos)));
            }
        });
    }
}
