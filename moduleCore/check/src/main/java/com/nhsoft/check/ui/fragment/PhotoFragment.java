package com.nhsoft.check.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.takephoto.model.TResult;
import com.nhsoft.check.R;
import com.nhsoft.check.photo.PhotoAdapter;
import com.nhsoft.check.photo.PhotoBean;
import com.nhsoft.check.photo.PhotoViewHolder;

import java.util.ArrayList;
import java.util.List;

import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.utils.SPUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends BaseTokePhotoFragment implements PhotoViewHolder.OnClickPic{
    private RecyclerView rv_photo;

    private List<PhotoBean> photoBeanList = new ArrayList<>();
    private PhotoAdapter adapter;

    private int realityNum;
    private int limit = 9;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_photo=view.findViewById(R.id.rv_photo);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv_photo.setLayoutManager(manager);
        adapter = new PhotoAdapter(photoBeanList, limit);
        rv_photo.setAdapter(adapter);
        adapter.setOnClickPic(this);
        setPhoto();
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().put("photos",setPhotos());
                SPUtils.getInstance().put("realityNum",realityNum);
                AppManager.getAppManager().currentActivity().finish();
            }
        });
    }

    private String setPhotos(){
        String photo="";
        List<PhotoBean> photoBeans=photoBeanList.subList(0,realityNum);
        for (PhotoBean photoBean:photoBeans){
            if (photoBeans.indexOf(photoBean)==realityNum-1){
                photo+=photoBean.getImagePath();
            }else {
                photo+=photoBean.getImagePath()+",";
            }
        }
        return photo;
    }

    @Override
    public void takeSuccess(TResult result) {
        if (isCamera) {
            String path = result.getImage().getCompressPath();
            addPhotoBean(path);
            adapter.notifyDataSetChanged();
        } else {
            setAlbumPic(result);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickPic() {
        showCutPopup(limit - realityNum);
    }

    @Override
    public void realityNum(int realityNum) {
        this.realityNum = realityNum;
    }

    private void addDefaultPic() {
        PhotoBean photoBean = new PhotoBean();
        photoBean.setShowError(false);
        photoBean.setClick(true);
        photoBeanList.add(photoBean);
        adapter.notifyDataSetChanged();
    }

    private void addPhotoBean(String path) {
        PhotoBean photoBean = new PhotoBean();
        photoBean.setImagePath(path);
        photoBeanList.add(photoBeanList.size() - 1, photoBean);
        realityNum = photoBeanList.size() - 1;
        if (realityNum == limit) {
            photoBeanList.remove(limit);//移除最后一个
        }
    }

    private void setAlbumPic(TResult result) {
        for (int i = 0; i < result.getImages().size(); i++) {
            addPhotoBean(result.getImages().get(i).getCompressPath());
        }
    }

    public void setPhoto(){
        addDefaultPic();
        realityNum=SPUtils.getInstance().getInt("realityNum");
        String pathes=SPUtils.getInstance().getString("photos");
        if (pathes.equals("")){
            return;
        }
        String[] paths=pathes.split(",");
        for (String s:paths){
            addPhotoBean(s);
        }
    }

}
