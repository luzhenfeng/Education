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
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.nhsoft.check.entity.RightFiveEntity;
import com.nhsoft.check.entity.RightFourEntity;
import com.nhsoft.check.entity.RightOneEntity;
import com.nhsoft.check.entity.RightTwoEntity;
import com.nhsoft.check.message.CheckInformation;
import com.nhsoft.check.message.CheckStudent;
import com.nhsoft.check.message.ConstantMessage;
import com.nhsoft.check.message.SelectChange;
import com.nhsoft.check.message.Subject;
import com.nhsoft.check.utils.CustomPopWindowUtil;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.MaterialDialogUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/27.
 */
public class CheckBaseViewModel extends BasePopupViewModel<Repository> {

    public static final String MultiRecycleType_Head = "head";
    public static final String MultiRecycleType_Right1 = "right1";
    public static final String MultiRecycleType_Right2 = "right2";
    public static final String MultiRecycleType_Right3 = "right3";
    public static final String MultiRecycleType_Right4 = "right4";
    public static final String MultiRecycleType_Right5 = "right5";

    public ObservableInt selectPos = new ObservableInt(0);

    //选条目班级的position
    public ObservableInt selectRightPos=new ObservableInt(-1);

    public ObservableField<CheckBaseEntity> entity = new ObservableField<>();

    //所有楼
    public List<FloorModel> mFloorModelList = new ArrayList<>();

    //当前楼
    public FloorModel mFloorModel;

    //用户所对应的分类
    public List<AllCategoryModel> userCategoryList = new ArrayList<>();

    //当前分类
    public AllCategoryModel mAllCategoryModel;

    //当前子分类
    public AllCategoryModel.ChidrensBean mChidrensBean;

    //当前分类下的全部检查条目
    public List<AllCategoryModel.ItemsBean> mAllItemsList = new ArrayList<>();

    //当前分类的所有子分类
    public List<AllCategoryModel.ChidrensBean> mChildrenBeanList = new ArrayList<>();

    //当前房间
    public FloorModel.RoomModel mRoomModel;

    //当前房间的 所有学生
    public List<FloorModel.StudentsBean> mStudentList = new ArrayList<>();

    //选中的学生
    public List<FloorModel.StudentsBean> mSelectSudentList=new ArrayList<>();

    //选中的检查项目
    public List<AllCategoryModel.ItemsBean> mSelectItemsBeanList=new ArrayList<>();

    //是否有选中的条目
    public ObservableBoolean isSelect=new ObservableBoolean(false);

    //TabLayout选中的条目标记
    public ObservableInt tabPosition=new ObservableInt(0);

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //        public ObservableBoolean showSelectClassPopupWindow=new ObservableBoolean(false);
        public SingleLiveEvent<Integer> type = new SingleLiveEvent<>();
        //TabLayout设置
        public SingleLiveEvent setTabs = new SingleLiveEvent<>();

        //设置TabLayout选中的条目
        public SingleLiveEvent<Integer> selectTab = new SingleLiveEvent<>();

        //设置右边要滑动到的位置
        public SingleLiveEvent<Integer> scrollPosition = new SingleLiveEvent<>();
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
        entity.get().onClickAdd = new BindingCommand<ImageView>(new BindingAction() {
            @Override
            public void call() {
                uc.type.setValue(3);
            }
        });
        entity.get().onTabSelectedCommand = new BindingCommand<TabLayout.Tab>(new BindingConsumer<TabLayout.Tab>() {
            @Override
            public void call(TabLayout.Tab tab) {
                if (!isSelect.get()&&mSelectSudentList.size()==0){
                    tabPosition.set(tab.getPosition());
                    TextView t= (TextView) tab.getCustomView();
                    getCurrentCategory(t.getText().toString());
                    Messenger.getDefault().send(mAllCategoryModel, ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_ONTABSELECTEDCOMMAND);
                }else {
                    uc.selectTab.setValue(tabPosition.get());
                    if (tabPosition.get()!=tab.getPosition()){
                        showClearDialog(tab.getPosition());
                    }
                }
            }
        });
        mPopupViewModel.onClick=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                if (mPopupViewModel.title.get().equals("选择学生")){
                    setSelectSudentList(mPopupViewModel.observableList);
                    entity.get().students.set(getStudents());
                    sentCheckItemMessager();
                    CustomPopWindowUtil.getInstance().dismiss();
                }else if (mPopupViewModel.title.get().equals("选择检查项班级")){
                    RightOneItemViewModel rightOneItemViewModel= (RightOneItemViewModel) entity.get().observableRightList.get(selectRightPos.get());
                    rightOneItemViewModel.entity.get().classes.set(setSelectCheckClass(rightOneItemViewModel));
                    sentCheckItemMessager();
                    CustomPopWindowUtil.getInstance().dismiss();
                }
            }
        });

        mPopupViewModel.onClickSelectAll=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                if (mPopupViewModel.selectAllStr.get().equals("全选")){
                    mPopupViewModel.selectAllStr.set("取消全选");
                    mPopupViewModel.selectAll(true);
                }else {
                    mPopupViewModel.selectAllStr.set("全选");
                    mPopupViewModel.selectAll(false);
                }
            }
        });
    }

    /**
     * 获取当前选择条目班级的条目
     * @return
     */
    public AllCategoryModel.ItemsBean  getCurrenItem(){
        RightOneItemViewModel rightOneItemViewModel= (RightOneItemViewModel) entity.get().observableRightList.get(selectRightPos.get());
        return rightOneItemViewModel.entity.get().items.get();
    }

    /**
     * 选择条目的班级
     */
    public String setSelectCheckClass(RightOneItemViewModel rightOneItemViewModel){
        String text="";
        String ids="";
        List<PopupItemViewModel> popupItemViewModelList=mPopupViewModel.observableList;
        AllCategoryModel.ItemsBean itemsBean=rightOneItemViewModel.entity.get().items.get();
        for (PopupItemViewModel popupItemViewModel:popupItemViewModelList){
            if (popupItemViewModel.entity.get().isSelect.get()){
                text+=popupItemViewModel.entity.get().text.get()+",";
                ids+=popupItemViewModel.entity.get().id.get()+",";
            }
        }
        itemsBean.setClassId(ids.equals("")?null:ids);
        itemsBean.setClassName(text.equals("")?null:text);
        return text.equals("")?"选择班级":text.substring(0,text.length()-1);
    }


    public void initMessenger() {
        //传递信息
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKVIEWMODEL_INFORMATION, CheckInformation.class, new BindingConsumer<CheckInformation>() {
            @Override
            public void call(CheckInformation checkInformation) {
                mFloorModelList = checkInformation.mFloorModelList;
                mFloorModel=checkInformation.mFloorModel;
                userCategoryList = checkInformation.userCategoryList;
                mRoomModel=checkInformation.mRoomModel;
                entity.get().tabs.set(getTabs());
                getCurrentCategory(entity.get().tabs.get().get(0));
                uc.setTabs.call();
            }
        });
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKVIEWMODEL_STUDENT, CheckStudent.class, new BindingConsumer<CheckStudent>() {
            @Override
            public void call(CheckStudent checkStudent) {
                mStudentList=checkStudent.mStudentList;
                mRoomModel=checkStudent.mRoomModel;
                setRightItem();
            }
        });
        //清除数据
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKVIEWMODEL_CLEARDATA, new BindingAction() {
            @Override
            public void call() {
                clearData();
                setRightItem();
            }
        });
        //接收切换的消息
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKVIEWMODEL_CHANGE, new BindingAction() {
            @Override
            public void call() {
                if (isSelect.get()||mSelectSudentList.size()!=0){
                    clearData();
                }
            }
        });
        //接收提交
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKVIEWMODEL_SUBJECT, new BindingAction() {
            @Override
            public void call() {
                sentSubjectMessager();
            }
        });
        //刷脸返回的值
        Messenger.getDefault().register(this, com.nhsoft.base.base.ConstantMessage.TOKEN_FACEVIEWMODEL_RESULT, Integer.class, new BindingConsumer<Integer>() {
            @Override
            public void call(Integer integer) {

            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!model.getFaceId().equals("")){
//            FloorModel.StudentsBean studentsBean=HttpDataUtil.findStudent(Integer.parseInt(model.getFaceId()),mFloorModel,mFloorModelList);
//            if (studentsBean!=null){
//                String id=null;
//                if (studentsBean.getDormid()!=null){
//                    id=studentsBean.getDormid();
//                }else {
//                    id=studentsBean.getClassid();
//                }
//                if (id!=null){
//                    if (isCurrentRoomStudent(studentsBean)){
//                        for (FloorModel.StudentsBean studentsBean1:mSelectSudentList){
//                            if (studentsBean1.getUserid().equals(studentsBean.getUserid())){
//                                return;
//                            }
//                        }
//                        mSelectSudentList.add(studentsBean);
//                        entity.get().students.set(getStudents());
//                    }else {
//                        Messenger.getDefault().send(studentsBean, com.nhsoft.base.base.ConstantMessage.TOKEN_FACEVIEWMODEL_RESULT1);
//                        mSelectSudentList.clear();
//                        mSelectSudentList.add(studentsBean);
//                        entity.get().students.set(getStudents());
//                    }
//
//                }
//
//            }
//        }
//    }

    /**
     * 刷脸返回的是否当前房间的学生
     * @param studentsBean
     * @return
     */
    public boolean isCurrentRoomStudent(FloorModel.StudentsBean studentsBean){
        for (FloorModel.StudentsBean studentsBean1:mStudentList){
            if (studentsBean1.getUserid().equals(studentsBean.getUserid())){
                return true;
            }
        }
        return false;
    }

    public void itemBinding() {
        entity.get().itemLeftBinding = ItemBinding.of(BR.viewModel, R.layout.item_left);
        entity.get().itemRightBinding = ItemBinding.of(new OnItemBind<MultiItemViewModel>() {
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
                } else if (MultiRecycleType_Right3.equals(itemType)) {
                    itemBinding.set(BR.viewModel, R.layout.item_right_three);
                } else if (MultiRecycleType_Right4.equals(itemType)) {
                    itemBinding.set(BR.viewModel, R.layout.item_right_four);
                }else if (MultiRecycleType_Right5.equals(itemType)){
                    itemBinding.set(BR.viewModel, R.layout.item_right_five);
                }
            }
        });
    }


    /**
     * 获取TabLayout的列表
     *
     * @return
     */
    public List<String> getTabs() {
        return HttpDataUtil.getUserCategoryNameList(userCategoryList);
    }

    /**
     * 获取当前类
     */
    public void getCurrentCategory(String categoryName) {
        mAllCategoryModel = HttpDataUtil.getCurrentCategory(categoryName, userCategoryList);
        mChildrenBeanList = HttpDataUtil.getChidrenCategoryList(mAllCategoryModel);
        setLeftItem();
        isShowStudent();
        getAllItemsList();
        setRightItem();
    }

    /**
     * 获取当前分类的全部子分类
     */
    public void getAllItemsList() {
        mAllItemsList = HttpDataUtil.getAllChiledrenItemsBeanList(mAllCategoryModel);
    }

    /**
     * 是否显示学生
     */
    public void isShowStudent() {
        entity.get().isShowStudent.set(mAllCategoryModel.isShowperson() ? View.VISIBLE : View.GONE);
    }

    /**
     * 初始化左边条目
     */
    public void setLeftItem() {
        entity.get().observableLeftList.clear();
        selectPos.set(0);
        if (entity.get().observableLeftList.size() == 0) {
            for (String s : HttpDataUtil.getChidrenCategoryNameList(mChildrenBeanList)) {
                LeftEntity leftEntity = new LeftEntity();
                leftEntity.title.set(s);
                leftEntity.image = ContextCompat.getDrawable(getApplication(), R.drawable.inspect7);
                if (entity.get().observableLeftList.size() == 0)
                    leftEntity.mDrawable = ContextCompat.getDrawable(getApplication(), R.color.white);
                LeftItemViewModel leftItemViewModel = new LeftItemViewModel(this, leftEntity);
                entity.get().observableLeftList.add(leftItemViewModel);
            }
            mChidrensBean = mChildrenBeanList.get(0);
        }
    }

    /**
     * 左边条目选中
     *
     * @param pos
     */
    public void setSelectPos(int pos) {
        if (selectPos.get() != pos) {
            LeftItemViewModel oldLeftItemViewModel = entity.get().observableLeftList.get(selectPos.get());
            oldLeftItemViewModel.entity.get().mDrawable = ContextCompat.getDrawable(getApplication(), android.R.color.transparent);
            entity.get().observableLeftList.set(selectPos.get(), oldLeftItemViewModel);

            LeftItemViewModel newLeftItemViewModel = entity.get().observableLeftList.get(pos);
            newLeftItemViewModel.entity.get().mDrawable = ContextCompat.getDrawable(getApplication(), R.color.white);
            entity.get().observableLeftList.set(pos, newLeftItemViewModel);
            selectPos.set(pos);
            mChidrensBean = mChildrenBeanList.get(pos);
        }
        uc.scrollPosition.setValue(setScorllPosition(pos));
    }

    /**
     * 设置右边条目
     */
    public void setRightItem() {
        entity.get().observableRightList.clear();
        for (AllCategoryModel.ChidrensBean chidrensBean : mChildrenBeanList) {
            setHeadItem(chidrensBean);
            List<AllCategoryModel.ItemsBean> itemsBeanList = HttpDataUtil.getItemsBeanList(chidrensBean, mAllItemsList);
            for (int i = 0; i < itemsBeanList.size(); i++) {
                AllCategoryModel.ItemsBean itemsBean = itemsBeanList.get(i);
                setRight1Item(i, itemsBean,chidrensBean.getShowbed());
                if (chidrensBean.getShowbed() == 1) {
                    setRight5Item(itemsBean.getId(),mRoomModel.getTotalcount());
                }
            }
        }
        setRight4Item();
    }


    public int setScorllPosition(int pos){
        int index=0;
        for (AllCategoryModel.ChidrensBean chidrensBean : mChildrenBeanList) {
            if (mChildrenBeanList.indexOf(chidrensBean)==pos){
                return index;
            }
            index++;
            List<AllCategoryModel.ItemsBean> itemsBeanList = HttpDataUtil.getItemsBeanList(chidrensBean, mAllItemsList);
            for (int i = 0; i < itemsBeanList.size(); i++) {
                index++;
                if (chidrensBean.getShowbed() == 1) {
                    index++;
                }
            }
        }
        return index;
    }

    /**
     * 右侧列表添加头部类型item
     *
     * @param chidrensBean 全部条目
     */
    public void setHeadItem(AllCategoryModel.ChidrensBean chidrensBean) {
        List<AllCategoryModel.ItemsBean> itemsBeanList = HttpDataUtil.getItemsBeanList(chidrensBean, mAllItemsList);
        HeadEntity headEntity = new HeadEntity();
        headEntity.title.set(chidrensBean.getName() + "(" + (itemsBeanList.size()==0?1:itemsBeanList.size()) + ")");
        headEntity.image = ContextCompat.getDrawable(getApplication(), R.drawable.inspect7);
        MultiItemViewModel item = new HeadViewModel(this, headEntity);
        //条目类型为头布局
        item.multiItemType(MultiRecycleType_Head);
        entity.get().observableRightList.add(item);
    }

    /**
     * 右侧列表添加条目类型1
     *
     * @param pos       序号
     * @param itemsBean 条目
     */
    public void setRight1Item(int pos, AllCategoryModel.ItemsBean itemsBean,int showbed) {
        RightOneEntity rightOneEntity = new RightOneEntity();
        rightOneEntity.index.set(pos + 1);
        rightOneEntity.content.set(itemsBean.getName());
        rightOneEntity.image=ContextCompat.getDrawable(getApplication(), R.drawable.check_box_aaaaaa);
        itemsBean.setClassId(null);
        itemsBean.setClassName(null);
        rightOneEntity.items.set(itemsBean);
        rightOneEntity.showbed.set(showbed);
        if (mRoomModel.getChildrens()!=null){
            if (mRoomModel.getChildrens().size()>1&&showbed==0&&entity.get().isShowStudent.get()!=View.VISIBLE){
                rightOneEntity.classes.set("选择班级");
            }
            if (itemsBean.getRuletype()==3||itemsBean.getRuletype()==6){
                rightOneEntity.showCount.set(true);
            }
            MultiItemViewModel rightOneItem = new RightOneItemViewModel(this, rightOneEntity);
            rightOneItem.multiItemType(MultiRecycleType_Right1);
            entity.get().observableRightList.add(rightOneItem);
        }
    }


    /**
     * 右侧列表条目类型2
     *
     * @param pos
     */
    public void setRight2Item(int pos) {
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
    public void setRight4Item() {
        RightFourEntity rightFourEntity = new RightFourEntity();
        rightFourEntity.image=ContextCompat.getDrawable(getApplication(), R.drawable.check_box_aaaaaa);
        MultiItemViewModel rightFourItem = new RightFourItemViewModel(this, rightFourEntity);
        rightFourItem.multiItemType(MultiRecycleType_Right4);
        entity.get().observableRightList.add(rightFourItem);
    }

    /**
     * 右侧列表条目类型5
     * @param id 上一条的id
     * @param num 床的数量
     */
    public void setRight5Item(String id,int num) {
        RightFiveEntity rightFiveEntity = new RightFiveEntity();
        rightFiveEntity.id.set(id);
        rightFiveEntity.totalCount.set(num);
        MultiItemViewModel rightFiveItem = new RightFiveItemViewModel(this, rightFiveEntity);
        rightFiveItem.multiItemType(MultiRecycleType_Right5);
        entity.get().observableRightList.add(rightFiveItem);
    }

    /**
     * 获取选中的学生
     * @param popupItemViewModelList
     */
    public void setSelectSudentList(List<PopupItemViewModel> popupItemViewModelList){
        mSelectSudentList.clear();
        for (PopupItemViewModel popupItemViewModel:popupItemViewModelList){
            if (popupItemViewModel.entity.get().isSelect.get()){
                mSelectSudentList.add(getStudent(popupItemViewModel));
            }
        }
        if (mSelectSudentList.size()>0){
            isSelect.set(true);
        }else {
            isSelect.set(false);
        }
    }

    /**
     * 根据userid获取学生
     * @param popupItemViewModel
     * @return
     */
    public FloorModel.StudentsBean getStudent(PopupItemViewModel popupItemViewModel){
        FloorModel.StudentsBean student=new FloorModel.StudentsBean();
        for (FloorModel.StudentsBean studentsBean:mStudentList){
            if (studentsBean.getUserid().equals(popupItemViewModel.entity.get().id.get())){
                student=studentsBean;
            }
        }
        return student;
    }

    /**
     * 获取违规学生的全部名字，中间、隔开
     * @return
     */
    public String getStudents(){
        String names="";
        for (FloorModel.StudentsBean studentsBean:mSelectSudentList){
            if (mSelectSudentList.indexOf(studentsBean)==mSelectSudentList.size()-1){
                names+=studentsBean.getStudentname();
            }else {
                names+=(studentsBean.getStudentname()+"、");
            }
        }
        return names;
    }


    /**
     * 右侧Right1 条目店中
     */
    public void onRight1ItemClick(int pos) {
        RightOneItemViewModel rightOneItemViewModel= (RightOneItemViewModel) entity.get().observableRightList.get(pos);
        if (rightOneItemViewModel.entity.get().isSelect.get()){
            rightOneItemViewModel.entity.get().image=ContextCompat.getDrawable(getApplication(), R.drawable.check_box_aaaaaa);
            rightOneItemViewModel.entity.get().isSelect.set(false);
            rightOneItemViewModel.entity.get().count.set(1);
            removeRightItemSelect(rightOneItemViewModel.entity.get());
            if (rightOneItemViewModel.entity.get().showbed.get()==1){
                clearBed(pos);
            }
            if (mRoomModel.getChildrens().size()>1&&rightOneItemViewModel.entity.get().items.get().getShowbed()==0&&entity.get().isShowStudent.get()!=View.VISIBLE){
                rightOneItemViewModel.entity.get().items.get().setClassName(null);
                rightOneItemViewModel.entity.get().items.get().setClassId(null);
                rightOneItemViewModel.entity.get().classes.set("选择班级");
            }
        }else {
            rightOneItemViewModel.entity.get().image=ContextCompat.getDrawable(getApplication(), R.drawable.check_box_select);
            rightOneItemViewModel.entity.get().isSelect.set(true);
            rightOneItemViewModel.entity.get().items.get().setItemcount(rightOneItemViewModel.entity.get().count.get());
            mSelectItemsBeanList.add(rightOneItemViewModel.entity.get().items.get());
        }
        rightOneItemViewModel.entity.get().hasFou.set(true);
        entity.get().observableRightList.set(pos,rightOneItemViewModel);
        sentCheckItemMessager();
    }

    public void onSelectClass(int pos){
        selectRightPos.set(pos);
        uc.type.setValue(4);
    }

    /**
     * 右侧Right4 扣分
     */
    public void onRight4ItemClick(int pos) {
        RightFourItemViewModel rightFourItemViewModel= (RightFourItemViewModel) entity.get().observableRightList.get(pos);
        if (isSelectFourItem(mSelectItemsBeanList,rightFourItemViewModel)){
            sentCheckItemMessager();
        }
    }

    /**
     * 移除条目1选中
     * @param entity
     */
    public void removeRightItemSelect(RightOneEntity entity){
        for (AllCategoryModel.ItemsBean itemsBean:mSelectItemsBeanList){
            if (itemsBean.getId()!=null){
                if (itemsBean.getId().equals(entity.items.get().getId())){
                    mSelectItemsBeanList.remove(itemsBean);
                    return;
                }
            }
        }
    }

    /**
     * 右侧Right4 条目店中
     */
    public void onRight4ImageItemClick(int pos) {
        RightFourItemViewModel rightFourItemViewModel= (RightFourItemViewModel) entity.get().observableRightList.get(pos);
        if (rightFourItemViewModel.entity.get().isSelect.get()){
            rightFourItemViewModel.entity.get().image=ContextCompat.getDrawable(getApplication(), R.drawable.check_box_aaaaaa);
            rightFourItemViewModel.entity.get().isSelect.set(false);
            removeSelectFourItem(mSelectItemsBeanList);
        }else {
            rightFourItemViewModel.entity.get().image=ContextCompat.getDrawable(getApplication(), R.drawable.check_box_select);
            rightFourItemViewModel.entity.get().isSelect.set(true);
            rightFourItemViewModel.entity.get().deduction.set(rightFourItemViewModel.entity.get().deduction.get());
            mSelectItemsBeanList.add(addOrUpdateSelectFourItem(rightFourItemViewModel));
        }
        entity.get().observableRightList.set(pos,rightFourItemViewModel);
        sentCheckItemMessager();
    }


    /**
     * 条目4选中添加或者更新到选中列表
     * @param rightFourItemViewModel 条目4
     * @return
     */
    public AllCategoryModel.ItemsBean addOrUpdateSelectFourItem(RightFourItemViewModel rightFourItemViewModel){
        AllCategoryModel.ItemsBean itemsBean=new AllCategoryModel.ItemsBean();
        itemsBean.setName(rightFourItemViewModel.entity.get().editText.get());
        itemsBean.setScore(Double.parseDouble(rightFourItemViewModel.entity.get().deduction.get()));
        return itemsBean;
    }

    /**
     * 移除选中的条目4
     * @param mSelectItemsBeanList
     */
    public void removeSelectFourItem(List<AllCategoryModel.ItemsBean> mSelectItemsBeanList){
        for (AllCategoryModel.ItemsBean itemsBean:mSelectItemsBeanList){
            if (itemsBean.getId()==null){
                mSelectItemsBeanList.remove(itemsBean);
                return;
            }
        }
    }

    /**
     * 条目4是否选中
     * @param mSelectItemsBeanList
     * @return
     */
    public boolean isSelectFourItem(List<AllCategoryModel.ItemsBean> mSelectItemsBeanList,RightFourItemViewModel rightFourItemViewModel){
        for (AllCategoryModel.ItemsBean itemsBean:mSelectItemsBeanList){
            if (itemsBean.getId()==null){
                mSelectItemsBeanList.set(mSelectItemsBeanList.indexOf(itemsBean),addOrUpdateSelectFourItem(rightFourItemViewModel));
                return true;
            }
        }
        return false;
    }


    /**
     * 选中条目的总分数的分数
     * @return
     */
    public double score(List<AllCategoryModel.ItemsBean> mSelectItemsBeanList){
        double score=0d;
        for (AllCategoryModel.ItemsBean itemsBean:mSelectItemsBeanList){
            if (itemsBean.getShowbed()==0){
                if (mRoomModel.getChildrens().size()>1&&entity.get().isShowStudent.get()!=View.VISIBLE){
                    String classsIds=itemsBean.getClassId();
                    if (classsIds==null||classsIds.equals("")){

                    }else {
                        int num=classsIds.split(",").length;
                        score+=(itemsBean.getScore()*num);
                    }
                }else {
                    score+=itemsBean.getScore();
                }
            }else {
                String beds=itemsBean.getBednos();
                if (beds==null||beds.equals("")){

                }else {
                    int num=beds.split(",").length;
                    score+=(itemsBean.getScore()*num);
                }
            }
        }
        if (entity.get().isShowStudent.get()==View.VISIBLE){
            score=score*mSelectSudentList.size();
        }
        return score;
    }


    /**
     *条目5床选择改变
     */
    public void onRight5SelectBed(int pos){
        RightFiveItemViewModel rightFiveItemViewModel= (RightFiveItemViewModel) entity.get().observableRightList.get(pos);
        updateSelectItemsBeanList(rightFiveItemViewModel.entity.get());
        sentCheckItemMessager();
    }

    public boolean isSelectStep(int pos){
        RightOneItemViewModel rightOneItemViewModel= (RightOneItemViewModel) entity.get().observableRightList.get(pos-1);
        if (rightOneItemViewModel.entity.get().isSelect.get()){
            return true;
        }else {
            ToastUtils.showShort("请先选择对应检查项，再选择床号！");
            return false;
        }
    }



    public void clearBed(int pos){
        RightFiveItemViewModel rightFiveItemViewModel= (RightFiveItemViewModel) entity.get().observableRightList.get(pos+1);
        rightFiveItemViewModel.entity.get().text1Select.set(false);
        rightFiveItemViewModel.entity.get().text2Select.set(false);
        rightFiveItemViewModel.entity.get().text3Select.set(false);
        rightFiveItemViewModel.entity.get().text4Select.set(false);
        rightFiveItemViewModel.entity.get().text5Select.set(false);
        rightFiveItemViewModel.entity.get().text6Select.set(false);
        rightFiveItemViewModel.entity.get().text7Select.set(false);
        rightFiveItemViewModel.entity.get().text8Select.set(false);
        rightFiveItemViewModel.entity.get().text9Select.set(false);
        rightFiveItemViewModel.entity.get().text10Select.set(false);
        rightFiveItemViewModel.entity.get().text11Select.set(false);
        rightFiveItemViewModel.entity.get().text12Select.set(false);
        rightFiveItemViewModel.entity.get().text13Select.set(false);
        rightFiveItemViewModel.entity.get().text14Select.set(false);
        rightFiveItemViewModel.entity.get().text15Select.set(false);
        rightFiveItemViewModel.entity.get().text16Select.set(false);
        rightFiveItemViewModel.entity.get().text17Select.set(false);
        rightFiveItemViewModel.entity.get().text18Select.set(false);
        rightFiveItemViewModel.entity.get().text19Select.set(false);
        rightFiveItemViewModel.entity.get().text20Select.set(false);
    }

    /**
     * 更新选中条目中的床位
     * @param entity
     */
    public void updateSelectItemsBeanList(RightFiveEntity entity){
        for (AllCategoryModel.ItemsBean itemsBean:mSelectItemsBeanList){
            if (itemsBean.getId().equals(entity.id.get())){
                mSelectItemsBeanList.get(mSelectItemsBeanList.indexOf(itemsBean)).setBednos(getBedNos(entity));
                return;
            }
        }
    }

    /**
     * 获取选中的床名字组成
     * @param entity
     * @return
     */
    public String getBedNos(RightFiveEntity entity){
        String bedNos="";
        if (entity.text1Select.get()){
            bedNos+=entity.text1.get()+",";
        }
        if (entity.text2Select.get()){
            bedNos+=entity.text2.get()+",";
        }
        if (entity.text3Select.get()){
            bedNos+=entity.text3.get()+",";
        }
        if (entity.text4Select.get()){
            bedNos+=entity.text4.get()+",";
        }
        if (entity.text5Select.get()){
            bedNos+=entity.text5.get()+",";
        }
        if (entity.text6Select.get()){
            bedNos+=entity.text6.get()+",";
        }
        if (entity.text7Select.get()){
            bedNos+=entity.text7.get()+",";
        }
        if (entity.text8Select.get()){
            bedNos+=entity.text8.get()+",";
        }
        if (entity.text9Select.get()){
            bedNos+=entity.text9.get()+",";
        }
        if (entity.text10Select.get()){
            bedNos+=entity.text10.get()+",";
        }
        if (entity.text11Select.get()){
            bedNos+=entity.text11.get()+",";
        }
        if (entity.text12Select.get()){
            bedNos+=entity.text12.get()+",";
        }
        if (entity.text13Select.get()){
            bedNos+=entity.text13.get()+",";
        }
        if (entity.text14Select.get()){
            bedNos+=entity.text14.get()+",";
        }
        if (entity.text15Select.get()){
            bedNos+=entity.text15.get()+",";
        }
        if (entity.text16Select.get()){
            bedNos+=entity.text16.get()+",";
        }
        if (entity.text17Select.get()){
            bedNos+=entity.text17.get()+",";
        }
        if (entity.text18Select.get()){
            bedNos+=entity.text18.get()+",";
        }
        if (entity.text19Select.get()){
            bedNos+=entity.text19.get()+",";
        }
        if (entity.text20Select.get()){
            bedNos+=entity.text20.get()+",";
        }
        if (!bedNos.equals("")){
            bedNos=bedNos.substring(0,bedNos.length()-1);
        }
        return bedNos;
    }

    /**
     * 发送改变Item选中状态数据到CheckViewModel,还有床号选择的变化
     */
    public void sentCheckItemMessager(){
        SelectChange selectChange=new SelectChange();
        selectChange.score=score(mSelectItemsBeanList);
        selectChange.mum=mSelectItemsBeanList.size();
        if (mSelectItemsBeanList.size()>0){
            isSelect.set(true);
        }else {
            isSelect.set(false);
        }
        Messenger.getDefault().send(selectChange,ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_SELECTITEM);
    }


    /**
     *发送提交的数据
     */
    public void sentSubjectMessager(){
        Subject subject=new Subject();
        subject.mSelectItemsBeanList=mSelectItemsBeanList;
        subject.mSelectSudentList=mSelectSudentList;
        if (mAllCategoryModel.isShowperson()&&mSelectSudentList.size()==0){
            ToastUtils.showShort("请选择学生");
            return;
        }
        if (!hasBed()){
            ToastUtils.showShort("选中的检查项未选择床位");
            return;
        }
        if (!isSelectClass()){
            ToastUtils.showShort("选中的检查项未选择班级");
            return;
        }
        if (!isCustomHasText()){
            ToastUtils.showShort("选中的自定义条目请输入内容");
            return;
        }
        Messenger.getDefault().send(subject,ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_SUBJECT);
    }


    /**
     * 选中的检查项有床位的至少选一个
     * @return
     */
    public boolean hasBed(){
        for (AllCategoryModel.ItemsBean itemsBean:mSelectItemsBeanList){
            if (itemsBean.getShowbed()==1){
                if (itemsBean.getBednos()==null||itemsBean.getBednos().equals("")){
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 自定义条目选中需要输入内容
     * @return
     */
    public boolean isCustomHasText(){
        for (AllCategoryModel.ItemsBean itemsBean:mSelectItemsBeanList){
            if (itemsBean.getId()==null){
                RightFourItemViewModel rightFourItemViewModel= (RightFourItemViewModel) entity.get().observableRightList.get(entity.get().observableRightList.size()-1);
                if (rightFourItemViewModel.entity.get().editText.get()==null||rightFourItemViewModel.entity.get().editText.get().equals("")){
                    return false;
                }else {
                    itemsBean.setName(rightFourItemViewModel.entity.get().editText.get());
                    return true;
                }
            }
        }
        return true;
    }


    /**
     * 选中带有班级的要选班级
     * @return
     */
    public boolean isSelectClass(){
        for (AllCategoryModel.ItemsBean itemsBean:mSelectItemsBeanList){
            if (mRoomModel.getChildrens().size()>1&&itemsBean.getShowbed()==0&&entity.get().isShowStudent.get()!=View.VISIBLE){
                if (itemsBean.getClassId()==null){
                    return false;
                }
            }
        }
        return true;
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


    /**
     * 获取右边条目下标
     * @param multiItemViewModel
     * @return
     */
    public int getRightItemPosition(MultiItemViewModel multiItemViewModel) {
        return entity.get().observableRightList.indexOf(multiItemViewModel);
    }

    /**
     * 切换弹出框确定清楚数据
     */
    public void showClearDialog(final int clickPos){
        MaterialDialogUtils.showBasicDialog(AppManager.getAppManager().currentActivity(),"请先提交","不提交跳转之后当前界面数据清空")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        clearData();
                        Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_CLEARDATA);
                        uc.selectTab.setValue(Integer.valueOf(clickPos));
                    }
                }).show();
    }

    /**
     * 清楚数据
     */
    public void clearData(){
        mSelectSudentList.clear();
        mSelectItemsBeanList.clear();
        isSelect.set(false);
        entity.get().students.set("");
    }

}
