package com.nhsoft.check.viewModel;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.utils.HttpDataUtil;
import com.nhsoft.check.entity.CheckEntity;
import com.nhsoft.check.message.CheckInformation;
import com.nhsoft.check.message.ConstantMessage;
import com.nhsoft.check.utils.CustomPopWindowUtil;

import java.util.ArrayList;
import java.util.List;

import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * Created by lzf on 2019/9/27.
 * Describe:
 */

public class CheckViewModel extends BasePopupViewModel<Repository> {


    public ObservableField<CheckEntity> entity=new ObservableField<>();

    //所有分类
    public List<AllCategoryModel> mAllCategoryModelList=new ArrayList<>();

    //所有楼
    public List<FloorModel> mFloorModelList=new ArrayList<>();

    //用户所对应的分类
    public List<AllCategoryModel> userCategoryList=new ArrayList<>();

    //当前分类下的所有楼名
    public List<String> mFloorNameList=new ArrayList<>();

    //当前楼所有房间
    public List<FloorModel.RoomModel> mRoomModelList=new ArrayList<>();

    //当前楼所有房间名
    public List<String> mRoomNameList=new ArrayList<>();

    //当前楼
    public FloorModel mFloorModel;

    //当前房间
    public FloorModel.RoomModel mRoomModel;

    //当前分类
    public AllCategoryModel mAllCategoryModel;

    //当前楼的所有学生
    public List<FloorModel.StudentsBean> mAllStudentList=new ArrayList<>();

    //当前房间的 所有学生
    public List<FloorModel.StudentsBean> mStudentList=new ArrayList<>();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc=new UIChangeObservable();

    public class UIChangeObservable {
        //        public ObservableBoolean showSelectClassPopupWindow=new ObservableBoolean(false);
        public SingleLiveEvent<Integer> selectType = new SingleLiveEvent<>();

    }

    public CheckViewModel(@NonNull Application application) {
        super(application);
        model= Injection.provideDemoRepository();
        mAllCategoryModelList= HttpDataUtil.getAllCategoryList(application);
        mFloorModelList=HttpDataUtil.getFloorList(application);
        userCategoryList= HttpDataUtil.getUserCategoryList(model.getCodes(),mAllCategoryModelList);
        entity.set(new CheckEntity());
        bindingCommand();
        initMessenger();
        if (userCategoryList.size()!=0){
            setFloorNameList(userCategoryList.get(0).getCategory());
        }
//        sentInformationMessage();
    }

    @Override
    public void bindingCommand(){
        super.bindingCommand();
        //扣
        entity.get().ivFraction=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("扣");
            }
        });

        //扫描
        entity.get().ivScan=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("扫描");
            }
        });

        //上传文件
        entity.get().ivUpload=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("上传文件");
            }
        });

        //相机
        entity.get().ivCamera=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("相机");
            }
        });

        //home
        entity.get().ivHome=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("home");
            }
        });

        //提交
        entity.get().tvSubmit=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("提交");
            }
        });
        //选择楼号
        entity.get().tvFloor=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                uc.selectType.setValue(1);
            }
        });
        //选择房间
        entity.get().tvRoom=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                uc.selectType.setValue(2);
            }
        });
    }


    public void initMessenger(){
        //选择弹出框选中条目
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_POPUPVIEWMODEL_SELECTITEM, Integer.class, new BindingConsumer<Integer>() {
            @Override
            public void call(Integer i) {
                if (uc.selectType.getValue().intValue()==1){
                    String name=mFloorNameList.get(i.intValue());
                    entity.get().floor.set(name);
                    getFloorModel(name);
                    setRoomNameList();
                }else if (uc.selectType.getValue().intValue()==2){
                    String name=mRoomNameList.get(i.intValue());
                    entity.get().room.set(name);
                    getRoom(name);
                }
                CustomPopWindowUtil.getInstance().dismiss();
            }
        });

        //点击TabLayout
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_ONTABSELECTEDCOMMAND, AllCategoryModel.class, new BindingConsumer<AllCategoryModel>() {
            @Override
            public void call(AllCategoryModel allCategoryModel) {
               mAllCategoryModel=allCategoryModel;
               setFloorNameList(mAllCategoryModel.getCategory());
            }
        });
    }


    public void sentInformationMessage(){
        CheckInformation checkInformation=new CheckInformation();
        checkInformation.mFloorModelList=mFloorModelList;
        checkInformation.userCategoryList=userCategoryList;
        Messenger.getDefault().send(checkInformation, ConstantMessage.TOKEN_CHECKVIEWMODEL_INFORMATION);
    }

    /**
     * 设置楼名列表
     * @param category
     */
    public void setFloorNameList(int category){
        mFloorNameList=HttpDataUtil.getFloorNameList(category,mFloorModelList);
        if (mFloorNameList.size()!=0){
            entity.get().floor.set(mFloorNameList.get(0));
            getFloorModel(mFloorNameList.get(0));
            setRoomNameList();
        }
    }

    /**
     * 设置房间名列表
     */
    public void setRoomNameList(){
        mRoomNameList=HttpDataUtil.getRoomNameList(mFloorModel);
        if (mRoomNameList.size()!=0){
            entity.get().room.set(mRoomNameList.get(0));
            getRoom(mRoomNameList.get(0));
        }
    }

    /**
     * 获取楼名对应的楼
     * @param name 楼名
     */
    public void getFloorModel(String name){
        mFloorModel=HttpDataUtil.getFloor(name,mFloorModelList);
        getAllStudent();
    }

    /**
     * 获取当前房间
     * @param name 房间名
     */
    public void getRoom(String name){
        mRoomModel=HttpDataUtil.getRoom(name,mFloorModel);
        getStudent();
    }

    /**
     * 获取当前楼所有的学生
     */
    public void getAllStudent(){
        mAllStudentList=HttpDataUtil.getAllStudentList(mFloorModel);
    }

    /**
     * 获取当前房间的所有学生
     */
    public void getStudent(){
        mStudentList=HttpDataUtil.getStudent(mFloorModel.getCategory(),mRoomModel.getId(),mAllStudentList);
    }



    /**
     * 右上角数量
     * @param num
     * @return
     */
    private String getText(int num){
        if (num<=99){
            return String.valueOf(num);
        }
        return "99+";
    }

    /**
     * 右上角是否显示
     * @param num
     * @return
     */
    private boolean isVisible(int num){
        if (num<=0){
            return false;
        }
        return true;
    }

}
