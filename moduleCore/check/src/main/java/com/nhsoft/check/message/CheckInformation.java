package com.nhsoft.check.message;

import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.FloorModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/10.
 */
public class CheckInformation {
    //所有楼
    public List<FloorModel> mFloorModelList=new ArrayList<>();
    //用户所对应的分类
    public List<AllCategoryModel> userCategoryList=new ArrayList<>();

}
