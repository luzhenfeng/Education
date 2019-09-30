package com.nhsoft.check.viewModel;

import android.app.Application;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.nhsoft.check.R;
import com.nhsoft.check.entity.HeadEntity;
import com.nhsoft.check.entity.LeftEntity;
import com.nhsoft.check.entity.RightOneEntity;

import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/27.
 */
public class CheckInteriorViewModel extends CheckBaseViewModel {



    public BindingCommand<ImageView> onClickAdd=new BindingCommand<>(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("添加");
            addLeft();
        }
    });

    public CheckInteriorViewModel(@NonNull Application application) {
        super(application);
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }


    /**
     * 初始化左边条目
     */
    public void initLeftItem(){
        if (entity.get().observableLeftList.size()==0){
            LeftEntity leftEntity=new LeftEntity();
            leftEntity.title.set("常规");
            leftEntity.image=ContextCompat.getDrawable(getApplication(),R.drawable.inspect7);
            leftEntity.mDrawable= ContextCompat.getDrawable(getApplication(),R.color.white);
            LeftItemViewModel leftItemViewModel=new LeftItemViewModel(this,leftEntity);
            entity.get().observableLeftList.add(leftItemViewModel);
        }
    }
    public void addLeft(){
        for (int i=0;i<10;i++){
            LeftEntity leftEntity=new LeftEntity();
            leftEntity.title.set("常规"+i);
            leftEntity.image=ContextCompat.getDrawable(getApplication(),R.drawable.inspect8);
            LeftItemViewModel leftItemViewModel=new LeftItemViewModel(this,leftEntity);
            entity.get().observableLeftList.add(leftItemViewModel);
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Messenger.getDefault().unregister(this);
//    }

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
