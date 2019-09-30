package com.nhsoft.check.viewModel;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.entity.CheckBaseEntity;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import priv.lzf.mvvmhabit.base.BaseModel;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/27.
 */
public class CheckBaseViewModel extends BaseViewModel {

    protected static final String MultiRecycleType_Head = "head";
    protected static final String MultiRecycleType_Right1 = "right1";
    protected static final String MultiRecycleType_Right2 = "right2";
    protected static final String MultiRecycleType_Right3 = "right3";
    protected static final String MultiRecycleType_Right4 = "right4";

//    public static final String TOKEN_CHECKBASEVIEWMODEL_SHOWSELECTCLASS = "tokenCheckBaseViewModelShowSelectClass";

    public ObservableInt selectPos=new ObservableInt(0);

    public ObservableField<CheckBaseEntity> entity=new ObservableField<>();


    //封装一个界面发生改变的观察者
    public UIChangeObservable uc=new UIChangeObservable();

    public class UIChangeObservable {
        public ObservableBoolean showSelectClassPopupWindow=new ObservableBoolean(false);
        public SingleLiveEvent<Integer> type = new SingleLiveEvent<>();
    }



    public CheckBaseViewModel(@NonNull Application application) {
        super(application);
        entity.set(new CheckBaseEntity());
        itemBinding();
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        registerMessenger();
    }


    /**
     * 选择班级
     */
    public void onSelectClassImage(){
        uc.showSelectClassPopupWindow.set(!uc.showSelectClassPopupWindow.get());
        uc.type.setValue(1);
    }



//    public void registerMessenger(){
//        Messenger.getDefault().register(this, CheckInteriorViewModel.TOKEN_CHECKBASEVIEWMODEL_SHOWSELECTCLASS, Integer.class, new BindingConsumer<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                uc.type.setValue(integer);
////                uc.showSelectClassObservable.set(!uc.showSelectClassObservable.get());
//            }
//        });
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Messenger.getDefault().unregister(this);
//    }

    public void itemBinding(){
        entity.get().itemLeftBinding = ItemBinding.of(BR.viewModel, R.layout.item_left);
        entity.get().itemRightBinding= ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
            @Override
            public void onItemBind(ItemBinding itemBinding, int position, MultiItemViewModel item) {
                //通过item的类型, 动态设置Item加载的布局
                String itemType = (String) item.getItemType();
                if (MultiRecycleType_Head.equals(itemType)) {
                    //设置头布局
                    itemBinding.set(BR.viewModel, R.layout.item_head);
                } else if (MultiRecycleType_Right1.equals(itemType)) {
                    itemBinding.set(BR.viewModel, R.layout.item_right_one);
                } else if (MultiRecycleType_Right2.equals(itemType)) {
                    itemBinding.set(BR.viewModel, R.layout.item_right_two);
                }else if (MultiRecycleType_Right3.equals(itemType)){
                    itemBinding.set(BR.viewModel, R.layout.item_right_three);
                }else if (MultiRecycleType_Right4.equals(itemType)){
                    itemBinding.set(BR.viewModel, R.layout.item_right_four);
                }
            }
        });

    }


    /**
     * 左边条目选中状态更改
     * @param pos
     */
    public void setSelectPos(int pos){
        if (selectPos.get()!=pos){
            LeftItemViewModel oldLeftItemViewModel=entity.get().observableLeftList.get(selectPos.get());
            oldLeftItemViewModel.entity.get().mDrawable= ContextCompat.getDrawable(getApplication(),android.R.color.transparent);
            entity.get().observableLeftList.set(selectPos.get(),oldLeftItemViewModel);

            LeftItemViewModel newLeftItemViewModel=entity.get().observableLeftList.get(pos);
            newLeftItemViewModel.entity.get().mDrawable=ContextCompat.getDrawable(getApplication(),R.color.white);
            entity.get().observableLeftList.set(pos,newLeftItemViewModel);
            selectPos.set(pos);
        }
    }

    /**
     * 获取左边条目下标
     *
     * @param leftItemViewModel
     * @return
     */
    public int getItemPosition(LeftItemViewModel leftItemViewModel) {
        return entity.get().observableLeftList.indexOf(leftItemViewModel);
    }

}
