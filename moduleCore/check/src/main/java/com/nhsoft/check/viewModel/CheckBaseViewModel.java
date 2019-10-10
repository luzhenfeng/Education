package com.nhsoft.check.viewModel;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.utils.HttpDataUtil;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.entity.CheckBaseEntity;
import com.nhsoft.check.entity.LeftEntity;
import com.nhsoft.check.message.CheckInformation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
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
public class CheckBaseViewModel extends BaseViewModel<Repository> {

    protected static final String MultiRecycleType_Head = "head";
    protected static final String MultiRecycleType_Right1 = "right1";
    protected static final String MultiRecycleType_Right2 = "right2";
    protected static final String MultiRecycleType_Right3 = "right3";
    protected static final String MultiRecycleType_Right4 = "right4";

    public ObservableInt selectPos=new ObservableInt(0);

    public ObservableField<CheckBaseEntity> entity=new ObservableField<>();

    //所有楼
    public List<FloorModel> mFloorModelList=new ArrayList<>();

    //用户所对应的分类
    public List<AllCategoryModel> userCategoryList=new ArrayList<>();

    //当前分类
    public AllCategoryModel mAllCategoryModel;

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc=new UIChangeObservable();

    public class UIChangeObservable {
//        public ObservableBoolean showSelectClassPopupWindow=new ObservableBoolean(false);
        public SingleLiveEvent<Integer> type = new SingleLiveEvent<>();
        //TabLayout切换
        public SingleLiveEvent<String> onTabSelectedCommand = new SingleLiveEvent<>();
        //TabLayout设置
        public SingleLiveEvent setTabs = new SingleLiveEvent<>();
    }


    public CheckBaseViewModel(@NonNull Application application) {
        super(application);
        model= Injection.provideDemoRepository();
        initMessenger();
        entity.set(new CheckBaseEntity());
        bindingCommand();
        itemBinding();
    }
    @Override
    public void bindingCommand() {
        super.bindingCommand();
        entity.get().onClickAdd=new BindingCommand<ImageView>(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("添加");
            }
        });
        entity.get().onTabSelectedCommand=new BindingCommand<TabLayout.Tab>(new BindingConsumer<TabLayout.Tab>() {
            @Override
            public void call(TabLayout.Tab tab) {
                uc.onTabSelectedCommand.setValue(tab.getText().toString());
                getCurrentCategory(tab.getText().toString());
            }
        });
    }

    public void initMessenger(){
        Messenger.getDefault().register(this, CheckViewModel.TOKEN_CHECKVIEWMODEL_INFORMATION, CheckInformation.class, new BindingConsumer<CheckInformation>() {
            @Override
            public void call(CheckInformation checkInformation) {
                mFloorModelList=checkInformation.mFloorModelList;
                userCategoryList=checkInformation.userCategoryList;
                entity.get().tabs.set(getTabs());
                getCurrentCategory(entity.get().tabs.get().get(0));
                uc.setTabs.call();
            }
        });
    }

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
     * 获取TabLayout的列表
     * @return
     */
    public List<String> getTabs(){
        return HttpDataUtil.getUserCategoryNameList(userCategoryList);
    }

    /**
     * 获取当前类
     */
    public void getCurrentCategory(String categoryName){
        mAllCategoryModel=HttpDataUtil.getCurrentCategory(categoryName,userCategoryList);
        setLeftItem();
        isShowStudent();
    }

    /**
     * 初始化左边条目
     */
    public void setLeftItem(){
        entity.get().observableLeftList.clear();
        selectPos.set(0);
        if (entity.get().observableLeftList.size()==0){
            for (String s:HttpDataUtil.getChidrenCategoryNameList(mAllCategoryModel)){
                LeftEntity leftEntity=new LeftEntity();
                leftEntity.title.set(s);
                leftEntity.image= ContextCompat.getDrawable(getApplication(), R.drawable.inspect7);
                if ( entity.get().observableLeftList.size()==0)
                    leftEntity.mDrawable= ContextCompat.getDrawable(getApplication(),R.color.white);
                LeftItemViewModel leftItemViewModel=new LeftItemViewModel(this,leftEntity);
                entity.get().observableLeftList.add(leftItemViewModel);
            }
            addLeftCustom();
        }
    }

    /**
     * 左边栏目加一个自定义
     */
    public void addLeftCustom(){
        LeftEntity leftEntity=new LeftEntity();
        leftEntity.title.set("自定义");
        leftEntity.image= ContextCompat.getDrawable(getApplication(), R.drawable.inspect7);
        if ( entity.get().observableLeftList.size()==0)
            leftEntity.mDrawable= ContextCompat.getDrawable(getApplication(),R.color.white);
        LeftItemViewModel leftItemViewModel=new LeftItemViewModel(this,leftEntity);
        entity.get().observableLeftList.add(leftItemViewModel);
    }

    /**
     * 是否显示学生
     */
    public void isShowStudent(){
        entity.get().isShowStudent.set(mAllCategoryModel.isShowperson()? View.VISIBLE:View.GONE);
    }



    /**
     * 选择班级
     */
    public void onSelectClassImage(){
        uc.type.setValue(1);
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
