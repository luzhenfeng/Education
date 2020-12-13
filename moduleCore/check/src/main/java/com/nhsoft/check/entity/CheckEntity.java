package com.nhsoft.check.entity;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.nhsoft.utils.utils.DateUtil;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;


/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/27.
 */
public class CheckEntity {
    public ObservableField<String> floor=new ObservableField<>("1号楼");
    public ObservableField<String> room=new ObservableField<>("001");
//    public ObservableField<String> floorNum=new ObservableField<>("全部");//第几层
    public ObservableField<String> date=new ObservableField<>(DateUtil.getCurrentTime());
    public ObservableField<String> fractionNum=new ObservableField<>("0");
    public ObservableField<String> uploadNum=new ObservableField<>("0");
    public ObservableField<String> cameraNum=new ObservableField<>("0");
    public ObservableField<String> fraction=new ObservableField<>("");
    public ObservableInt showFractionNum=new ObservableInt(View.GONE);
    public ObservableInt showUploadNum=new ObservableInt(View.GONE);
    public ObservableInt showCameraNum=new ObservableInt(View.GONE);
    public BindingCommand ivFraction;
    public BindingCommand ivScan;
    public BindingCommand ivUpload;
    public BindingCommand ivCamera;
    public BindingCommand ivHome;
    public BindingCommand tvSubmit;
    public BindingCommand tvFloor;
    public BindingCommand tvRoom;
    public BindingCommand tvTime;
//    public BindingCommand tvFloorNum;
}
