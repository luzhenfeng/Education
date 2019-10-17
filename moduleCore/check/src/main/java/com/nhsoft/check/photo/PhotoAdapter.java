package com.nhsoft.check.photo;

import android.view.View;

import com.nhsoft.check.R;

import java.util.List;


/**
 * Created by NBMY on 2018/10/10.
 */

public class PhotoAdapter extends BaseAdapter<PhotoBean> {
    private int limit;
    PhotoViewHolder.OnClickPic onClickPic;
    public PhotoAdapter(List<PhotoBean> dataList, int limit) {
        super(dataList);
        this.limit=limit;
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.item_photo;
    }

    @Override
    public BaseViewHolder getHolder(View v, int viewType) {
        PhotoViewHolder photoViewHolder=new PhotoViewHolder(v,viewType);
        photoViewHolder.setLimit(limit);
        photoViewHolder.setOnClickPic(onClickPic);
        return photoViewHolder;
    }

    public void setOnClickPic(PhotoViewHolder.OnClickPic onClickPic){
        this.onClickPic=onClickPic;
    }
}
