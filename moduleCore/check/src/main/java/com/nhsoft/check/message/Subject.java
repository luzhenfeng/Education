package com.nhsoft.check.message;

import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.FloorModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/13.
 */
public class Subject {
    //选中的学生
    public List<FloorModel.StudentsBean> mSelectSudentList=new ArrayList<>();

    //选中的检查项目
    public List<AllCategoryModel.ItemsBean> mSelectItemsBeanList=new ArrayList<>();
}
