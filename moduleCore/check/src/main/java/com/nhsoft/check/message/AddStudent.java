package com.nhsoft.check.message;

import com.lzf.http.entity.FloorModel;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/18.
 */
public class AddStudent {
    private FloorModel.StudentsBean studentsBean;

    public FloorModel.StudentsBean getStudentsBean() {
        return studentsBean;
    }

    public void setStudentsBean(FloorModel.StudentsBean studentsBean) {
        this.studentsBean = studentsBean;
    }
}
