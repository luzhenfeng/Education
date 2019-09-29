package com.nhsoft.check.viewModel;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.nhsoft.check.R;
import com.nhsoft.check.entity.HeadEntity;
import com.nhsoft.check.entity.LeftEntity;
import com.nhsoft.check.entity.RightOneEntity;

import priv.lzf.mvvmhabit.base.MultiItemViewModel;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/28.
 */
public class CheckRoutineViewModel extends CheckBaseViewModel{

    private String[] mCheckTitleModels = {"地面及卫生工具", "门窗电器", "床上", "床下", "脸盆架", "鞋/鞋架/箱架", "其它"};

    public CheckRoutineViewModel(@NonNull Application application) {
        super(application);
    }
    /**
     * 初始化左边条目
     */
    public void initLeftItem(){
        if (entity.get().observableLeftList.size()==0){
            for (String s:mCheckTitleModels){
                LeftEntity leftEntity=new LeftEntity();
                leftEntity.title.set(s);
                leftEntity.image= ContextCompat.getDrawable(getApplication(), R.drawable.inspect7);
                if ( entity.get().observableLeftList.size()==0)
                    leftEntity.mDrawable= ContextCompat.getDrawable(getApplication(),R.color.white);
                LeftItemViewModel leftItemViewModel=new LeftItemViewModel(this,leftEntity);
                entity.get().observableLeftList.add(leftItemViewModel);
            }
        }
    }

    /**
     * 初始化左边条目
     */
    public void initRightItem(){
        if (entity.get().observableRightList.size()==0){
            HeadEntity headEntity=new HeadEntity();
            headEntity.title.set("地面及卫生工具(11项)");
            headEntity.image=ContextCompat.getDrawable(getApplication(),R.drawable.inspect7);
            MultiItemViewModel item = new HeadViewModel(this,headEntity);
            //条目类型为头布局
            item.multiItemType(MultiRecycleType_Head);
            entity.get().observableRightList.add(item);
            for (int i=0;i<5;i++){
                RightOneEntity rightOneEntity=new RightOneEntity();
                rightOneEntity.index.set(i+1);
                rightOneEntity.content.set("卫生角旁边发现垃圾");
                rightOneEntity.classes.set("机电2班");
                MultiItemViewModel rightOneItem = new RightOneItemViewModel(this,rightOneEntity);
                rightOneItem.multiItemType(MultiRecycleType_Right1);
                entity.get().observableRightList.add(rightOneItem);
            }
        }
    }
}
