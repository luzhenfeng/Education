package com.nhsoft.check.viewModel;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.nhsoft.check.R;
import com.nhsoft.check.entity.HeadEntity;
import com.nhsoft.check.entity.LeftEntity;
import com.nhsoft.check.entity.RightFourEntity;
import com.nhsoft.check.entity.RightOneEntity;
import com.nhsoft.check.entity.RightThreeEntity;
import com.nhsoft.check.entity.RightTwoEntity;

import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.base.MultiItemViewModel;

/**
 * Created by lzf on 2019/10/10.
 * Describe:
 */

public class CheckListViewModel extends CheckBaseViewModel {


    private String[] mCheckTitleModels = {"地面及卫生工具", "门窗电器", "床上", "床下", "脸盆架", "鞋/鞋架/箱架", "其它"};

    public CheckListViewModel(@NonNull Application application) {
        super(application);
    }
//    /**
//     * 初始化左边条目
//     */
//    public void initLeftItem(){
//
//        if (entity.get().observableLeftList.size()==0){
//            for (String s:mCheckTitleModels){
//                LeftEntity leftEntity=new LeftEntity();
//                leftEntity.title.set(s);
//                leftEntity.image= ContextCompat.getDrawable(getApplication(), R.drawable.inspect7);
//                if ( entity.get().observableLeftList.size()==0)
//                    leftEntity.mDrawable= ContextCompat.getDrawable(getApplication(),R.color.white);
//                LeftItemViewModel leftItemViewModel=new LeftItemViewModel(this,leftEntity);
//                entity.get().observableLeftList.add(leftItemViewModel);
//            }
//        }
//    }

    /**
     * 初始化左边条目
     */
    public void initRightItem(){
        entity.get().gridCount.set(11);
        entity.get().gridStartPos.set(8);
        entity.get().gridEndPos.set(entity.get().gridStartPos.get()+entity.get().gridCount.get()-1);
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
            HeadEntity headEntity1=new HeadEntity();
            headEntity1.title.set("床上(11项)");
            headEntity1.image=ContextCompat.getDrawable(getApplication(),R.drawable.inspect7);
            MultiItemViewModel item1 = new HeadViewModel(this,headEntity1);
            //条目类型为头布局
            item1.multiItemType(MultiRecycleType_Head);
            entity.get().observableRightList.add(item1);

            RightOneEntity rightOneEntity=new RightOneEntity();
            rightOneEntity.index.set(1);
            rightOneEntity.content.set("床上有杂物");
            rightOneEntity.classes.set("");
            MultiItemViewModel rightOneItem = new RightOneItemViewModel(this,rightOneEntity);
            rightOneItem.multiItemType(MultiRecycleType_Right1);
            entity.get().observableRightList.add(rightOneItem);

            for (int i=0;i<entity.get().gridCount.get()-1;i++){
                RightTwoEntity rightTwoEntity=new RightTwoEntity();
                rightTwoEntity.leftText.set("床上:");
                rightTwoEntity.rightText.set("#"+(i+1));
                rightTwoEntity.rightTextSelect.set(false);
                if (i==0){
                    rightTwoEntity.leftTextShow.set(View.VISIBLE);
                }else {
                    rightTwoEntity.leftTextShow.set(View.GONE);
                }
                MultiItemViewModel rightTwoItem = new RightTwoItemViewModel(this,rightTwoEntity);
                rightTwoItem.multiItemType(MultiRecycleType_Right2);
                entity.get().observableRightList.add(rightTwoItem);
            }

            RightThreeEntity rightThreeEntity=new RightThreeEntity();
            MultiItemViewModel rightThreeItem = new RightThreeItemViewModel(this,rightThreeEntity);
            rightThreeItem.multiItemType(MultiRecycleType_Right3);
            entity.get().observableRightList.add(rightThreeItem);


            HeadEntity headEntity2=new HeadEntity();
            headEntity2.title.set("自定义(1项)");
            headEntity2.image=ContextCompat.getDrawable(getApplication(),R.drawable.inspect7);
            MultiItemViewModel item2 = new HeadViewModel(this,headEntity2);
            //条目类型为头布局
            item2.multiItemType(MultiRecycleType_Head);
            entity.get().observableRightList.add(item2);

            RightFourEntity rightFourEntity=new RightFourEntity();
            MultiItemViewModel rightFourItem = new RightFourItemViewModel(this,rightFourEntity);
            rightFourItem.multiItemType(MultiRecycleType_Right4);
            entity.get().observableRightList.add(rightFourItem);
        }
    }
}
