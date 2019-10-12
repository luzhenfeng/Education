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
import com.nhsoft.check.entity.HeadEntity;
import com.nhsoft.check.entity.LeftEntity;
import com.nhsoft.check.entity.RightFourEntity;
import com.nhsoft.check.entity.RightOneEntity;
import com.nhsoft.check.entity.RightThreeEntity;
import com.nhsoft.check.entity.RightTwoEntity;
import com.nhsoft.check.message.CheckInformation;
import com.nhsoft.check.message.ConstantMessage;

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
public class CheckBaseViewModel extends BasePopupViewModel {

    public static final String MultiRecycleType_Head = "head";
    public static final String MultiRecycleType_Right1 = "right1";
    public static final String MultiRecycleType_Right2 = "right2";
    public static final String MultiRecycleType_Right3 = "right3";
    public static final String MultiRecycleType_Right4 = "right4";

    public ObservableInt selectPos=new ObservableInt(0);

    public ObservableField<CheckBaseEntity> entity=new ObservableField<>();

    //所有楼
    public List<FloorModel> mFloorModelList=new ArrayList<>();

    //用户所对应的分类
    public List<AllCategoryModel> userCategoryList=new ArrayList<>();

    //当前分类
    public AllCategoryModel mAllCategoryModel;

    //当前分类的所有子分类
    public List<AllCategoryModel.ChidrensBean> mChidrensBeanList=new ArrayList<>();

    //当前子分类
    public AllCategoryModel.ChidrensBean mChidrensBean;

    //当前分类下的全部检查条目
    public List<AllCategoryModel.ItemsBean> mAllItemsList=new ArrayList<>();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc=new UIChangeObservable();

    public class UIChangeObservable {
//        public ObservableBoolean showSelectClassPopupWindow=new ObservableBoolean(false);
        public SingleLiveEvent<Integer> type = new SingleLiveEvent<>();
        //TabLayout设置
        public SingleLiveEvent setTabs = new SingleLiveEvent<>();
    }


    public CheckBaseViewModel(@NonNull Application application) {
        super(application);
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
//                uc.onTabSelectedCommand.setValue(tab.getText().toString());
                getCurrentCategory(tab.getText().toString());
                Messenger.getDefault().send(mAllCategoryModel,ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_ONTABSELECTEDCOMMAND);
            }
        });
    }

    public void initMessenger(){
        //传递信息
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKVIEWMODEL_INFORMATION, CheckInformation.class, new BindingConsumer<CheckInformation>() {
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
        mChidrensBeanList=HttpDataUtil.getChidrenCategoryList(mAllCategoryModel);
        setLeftItem();
        isShowStudent();
        getAllItemsList();
        setRightItem();
    }

    /**
     * 获取当前分类的全部子分类
     */
    public void getAllItemsList(){
        mAllItemsList=HttpDataUtil.getAllChiledrenItemsBeanList(mAllCategoryModel);
    }

    /**
     * 是否显示学生
     */
    public void isShowStudent(){
        entity.get().isShowStudent.set(mAllCategoryModel.isShowperson()? View.VISIBLE:View.GONE);
    }

    /**
     * 初始化左边条目
     */
    public void setLeftItem(){
        entity.get().observableLeftList.clear();
        selectPos.set(0);
        if (entity.get().observableLeftList.size()==0){
            for (String s:HttpDataUtil.getChidrenCategoryNameList(mChidrensBeanList)){
                LeftEntity leftEntity=new LeftEntity();
                leftEntity.title.set(s);
                leftEntity.image= ContextCompat.getDrawable(getApplication(), R.drawable.inspect7);
                if ( entity.get().observableLeftList.size()==0)
                    leftEntity.mDrawable= ContextCompat.getDrawable(getApplication(),R.color.white);
                LeftItemViewModel leftItemViewModel=new LeftItemViewModel(this,leftEntity);
                entity.get().observableLeftList.add(leftItemViewModel);
            }
            mChidrensBean=mChidrensBeanList.get(0);
        }
    }

    /**
     * 左边条目选中
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
            mChidrensBean=mChidrensBeanList.get(pos);
        }
    }

    /**
     * 设置右边条目
     */
    public void setRightItem() {
        entity.get().observableRightList.clear();
        for (AllCategoryModel.ChidrensBean chidrensBean:mChidrensBeanList){
            setHeadItem(chidrensBean);
            List<AllCategoryModel.ItemsBean> itemsBeanList=HttpDataUtil.getItemsBeanList(chidrensBean,mAllItemsList);
            for (int i=0;i<itemsBeanList.size();i++){
                AllCategoryModel.ItemsBean itemsBean=itemsBeanList.get(i);
                setRight1Item(i,itemsBean);
                if (itemsBean.getShowbed()==1){
                    for (int j=0;j<entity.get().gridCount.get()-1;j++){
                        setRight2Item(j);
                    }
                }
            }
        }
        setRight4Item();
    }

    /**
     * 右侧列表添加头部类型item
     * @param chidrensBean 全部条目
     */
    public void setHeadItem(AllCategoryModel.ChidrensBean chidrensBean){
        List<AllCategoryModel.ItemsBean> itemsBeanList=HttpDataUtil.getItemsBeanList(chidrensBean,mAllItemsList);
        HeadEntity headEntity = new HeadEntity();
        headEntity.title.set(chidrensBean.getName()+"("+itemsBeanList.size()+")");
        headEntity.image = ContextCompat.getDrawable(getApplication(), R.drawable.inspect7);
        MultiItemViewModel item = new HeadViewModel(this, headEntity);
        //条目类型为头布局
        item.multiItemType(MultiRecycleType_Head);
        entity.get().observableRightList.add(item);
    }

    /**
     * 右侧列表添加条目类型1
     * @param pos 序号
     * @param itemsBean 条目
     */
    public void setRight1Item(int pos,AllCategoryModel.ItemsBean itemsBean){
        RightOneEntity rightOneEntity = new RightOneEntity();
        rightOneEntity.index.set(pos + 1);
        rightOneEntity.content.set(itemsBean.getName());
        rightOneEntity.classes.set("");
        MultiItemViewModel rightOneItem = new RightOneItemViewModel(this, rightOneEntity);
        rightOneItem.multiItemType(MultiRecycleType_Right1);
        entity.get().observableRightList.add(rightOneItem);
    }


    /**
     * 右侧列表条目类型2
     * @param pos
     */
    public void setRight2Item(int pos){
        RightTwoEntity rightTwoEntity = new RightTwoEntity();
        rightTwoEntity.leftText.set("床上:");
        rightTwoEntity.rightText.set("#" + (pos + 1));
        rightTwoEntity.rightTextSelect.set(false);
        if (pos == 0) {
            rightTwoEntity.leftTextShow.set(View.VISIBLE);
        } else {
            rightTwoEntity.leftTextShow.set(View.GONE);
        }
        MultiItemViewModel rightTwoItem = new RightTwoItemViewModel(this, rightTwoEntity);
        rightTwoItem.multiItemType(MultiRecycleType_Right2);
        entity.get().observableRightList.add(rightTwoItem);
    }

    /**
     * 右侧列表条目类型4
     */
    public void setRight4Item(){
        RightFourEntity rightFourEntity = new RightFourEntity();
        MultiItemViewModel rightFourItem = new RightFourItemViewModel(this, rightFourEntity);
        rightFourItem.multiItemType(MultiRecycleType_Right4);
        entity.get().observableRightList.add(rightFourItem);
    }

    /**
     * 选择班级
     */
    public void onSelectClassImage(){
        uc.type.setValue(1);
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
