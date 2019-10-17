package com.nhsoft.check.photo;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.nhsoft.check.R;

import java.util.List;

/**
 * Created by NBMY on 2018/10/10.
 */

public class PhotoViewHolder extends BaseViewHolder<PhotoBean> {
    private ImageView image;
    private ImageView iv_error;

    private int limit;
    OnClickPic onClickPic;



    public PhotoViewHolder(View itemView, int viewType) {
        super(itemView, viewType);
        image = itemView.findViewById(R.id.image);
        iv_error = itemView.findViewById(R.id.iv_error);
    }

    @Override
    public void setData(final List<PhotoBean> data, final int position) {
//        GlideUtil.loadPhoto(data.get(position).getImagePath()).into(image);
        RequestOptions options=new RequestOptions()
                .placeholder(R.drawable.add_photo2)
                .error(R.drawable.add_photo2);
        Glide.with(context)
                .load(data.get(position).getImagePath())
                .apply(options)
                .into(image);
        iv_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size()==limit&&!data.get(limit-1).isClick()){//如果移除之后币limit少1则添加一张点击添加的图片
                    PhotoBean photoBean=new PhotoBean();
                    photoBean.setShowError(false);
                    photoBean.setClick(true);
                    data.add(photoBean);
                }
                data.remove(position);
                onClickPic.realityNum(data.size()-1);
                notifyChange.notifyChange();
            }
        });
        if (data.get(position).isShowError()){
            iv_error.setVisibility(View.VISIBLE);
        }else {
            iv_error.setVisibility(View.GONE);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).isClick()){
                    onClickPic.onClickPic();
                }
            }
        });

    }

    public void setLimit(int limit) {
        this.limit = limit;
    }



    public interface OnClickPic{
        void onClickPic();//点击图片
        void realityNum(int realityNum);//移除图片剩几张
    }
    public void setOnClickPic(OnClickPic onClickPic){
        this.onClickPic=onClickPic;
    }
}
