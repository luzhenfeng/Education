package com.nhsoft.check.viewModel;

import android.Manifest;
import android.app.Application;
import android.app.DatePickerDialog;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.lzf.greendao.entity.ChecksModel;
import com.lzf.greendao.service.ChecksModelService;
import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.utils.HttpDataUtil;
import com.nhsoft.base.router.RouterActivityPath;
import com.nhsoft.check.entity.CheckEntity;
import com.nhsoft.check.entity.PhotoItemEntity;
import com.nhsoft.check.message.AddStudent;
import com.nhsoft.check.message.CheckInformation;
import com.nhsoft.check.message.CheckStudent;
import com.nhsoft.check.message.ConstantMessage;
import com.nhsoft.check.message.SelectChange;
import com.nhsoft.check.message.Subject;
import com.nhsoft.check.ui.activity.PhotoActivity;
import com.nhsoft.check.utils.CustomPopWindowUtil;
import com.nhsoft.utils.utils.DateUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;
import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;
import priv.lzf.mvvmhabit.http.BaseResponse;
import priv.lzf.mvvmhabit.http.ResponseThrowable;
import priv.lzf.mvvmhabit.utils.MaterialDialogUtils;
import priv.lzf.mvvmhabit.utils.RxUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * Created by lzf on 2019/9/27.
 * Describe:
 */

public class CheckViewModel extends BasePopupViewModel<Repository> {
    //所有分类
    public List<AllCategoryModel> mAllCategoryModelList = new ArrayList<>();

    //所有楼
    public List<FloorModel> mFloorModelList = new ArrayList<>();

    //用户所对应的分类
    public List<AllCategoryModel> userCategoryList = new ArrayList<>();

    //当前分类下的所有楼名
    public List<String> mFloorNameList = new ArrayList<>();

    //当前分类下的所有楼
    public List<FloorModel> mCurrentFloorModelList=new ArrayList<>();

    //当前楼所有房间
    public List<FloorModel.RoomModel> mRoomModelList = new ArrayList<>();

    //当前楼所有房间名
    public List<String> mRoomNameList = new ArrayList<>();

    //当前楼所有楼层名称
    public List<String> mFloorNames = new ArrayList<>();

    //选中的楼层
    public ObservableInt mSelectFloor=new ObservableInt(0);

    //当前楼
    public FloorModel mFloorModel;

    //当前房间
    public FloorModel.RoomModel mRoomModel;

    //当前分类
    public AllCategoryModel mAllCategoryModel;

    //当前楼的所有学生
    public List<FloorModel.StudentsBean> mAllStudentList = new ArrayList<>();

    //当前房间的 所有学生
    public List<FloorModel.StudentsBean> mStudentList = new ArrayList<>();

    //选中的学生
    public List<FloorModel.StudentsBean> mSelectSudentList = new ArrayList<>();

    //选中的检查项目
    public List<AllCategoryModel.ItemsBean> mSelectItemsBeanList = new ArrayList<>();

    //是否有选中的条目
    public ObservableBoolean isSelect = new ObservableBoolean(false);

    public ObservableField<CheckEntity> entity = new ObservableField<>();

    public ObservableBoolean isFirst = new ObservableBoolean(true);

    //选中的图片
    public List<PhotoItemEntity> mPhotoItemEntities = new ArrayList<>();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Integer> selectType = new SingleLiveEvent<>();
        public SingleLiveEvent face = new SingleLiveEvent();
        public SingleLiveEvent success=new SingleLiveEvent();
        public SingleLiveEvent time=new SingleLiveEvent();
    }

    public CheckViewModel(@NonNull Application application) {
        super(application);
        model = Injection.provideDemoRepository();
        model.savePhotos("");
        mAllCategoryModelList = HttpDataUtil.getAllCategoryList(application);
        mFloorModelList = HttpDataUtil.getFloorList(application);
        userCategoryList = HttpDataUtil.getUserCategoryList(model.getCode(), mAllCategoryModelList);
        entity.set(new CheckEntity());
        bindingCommand();
        initMessenger();
        if (userCategoryList.size() != 0) {
            mAllCategoryModel = userCategoryList.get(0);
            setFloorNameList(userCategoryList.get(0).getCategory());
        }
//        sentInformationMessage();
    }

    @Override
    public void bindingCommand() {
        super.bindingCommand();
        //扣
        entity.get().ivFraction = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
//                ToastUtils.showShort("扣");
//                uc.success.call();
            }
        });

        //扫描
        entity.get().ivScan = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
//                ToastUtils.showShort("此功能暂未开放");
                uc.face.call();
            }
        });

        //上传文件
        entity.get().ivUpload = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ARouter.getInstance().build(RouterActivityPath.Upload.PAGER_UPLOAD).navigation();
            }
        });

        //相机
        entity.get().ivCamera = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
//                ARouter.getInstance().build(RouterActivityPath.Check.PAGER_CHECK).navigation();
                startActivity(PhotoActivity.class);
            }
        });

        //home
        entity.get().ivHome = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                ToastUtils.showShort("此功能暂未开放");

            }
        });

        //提交
        entity.get().tvSubmit = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                if (isSelect.get()) {
                    Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_CHECKVIEWMODEL_SUBJECT);
                } else {
                    ToastUtils.showShort("当前无数据，无需提交");
                }
            }
        });
        //选择楼号
        entity.get().tvFloor = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                if (isSelect.get() || !entity.get().cameraNum.get().equals("0")) {
                    showClearDialog(1);
                } else {
                    uc.selectType.setValue(1);
                    Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_CHECKVIEWMODEL_CHANGE);
                }
            }
        });
        //选择房间
        entity.get().tvRoom = new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                if (isSelect.get() || !entity.get().cameraNum.get().equals("0")) {
                    showClearDialog(2);
                } else {
                    mFloorNames=HttpDataUtil.getFloorNameList(mFloorModel);
                    uc.selectType.setValue(3);
                }
            }
        });
//        entity.get().tvFloorNum=new BindingCommand(new BindingAction() {
//            @Override
//            public void call() {
//                uc.selectType.setValue(2);
//            }
//        });

        entity.get().tvTime=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                uc.time.call();
            }
        });

    }

    public void setSelectClass(){
        uc.selectType.setValue(2);
        Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_CHECKVIEWMODEL_CHANGE);
    }


    public void initMessenger() {
        //选择弹出框选中条目
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_POPUPVIEWMODEL_SELECTITEM, Integer.class, new BindingConsumer<Integer>() {
            @Override
            public void call(Integer i) {
                if (uc.selectType.getValue().intValue() == 1) {
                    String name = mFloorNameList.get(i.intValue());
                    entity.get().floor.set(name);
                    getFloorModel(name);
                    setRoomNameList();
                    CustomPopWindowUtil.getInstance().dismiss();
                } else if (uc.selectType.getValue().intValue() == 2) {
                    String name = mRoomNameList.get(i.intValue());
                    entity.get().room.set(name);
                    getRoom(name);
                    CustomPopWindowUtil.getInstance().dismiss();
                } else if (uc.selectType.getValue().intValue() == 3){
                    String name= mFloorNames.get(i);
                    int floor=0;
                    if (!name.equals("全部")){
                        floor=Integer.valueOf(name);
                    }
                    mRoomNameList = HttpDataUtil.getFloorRoomNameList(floor,mFloorModel);
                    CustomPopWindowUtil.getInstance().dismiss();
                    setSelectClass();
                }
            }
        });

        //点击TabLayout
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_ONTABSELECTEDCOMMAND, AllCategoryModel.class, new BindingConsumer<AllCategoryModel>() {
            @Override
            public void call(AllCategoryModel allCategoryModel) {
                if (mAllCategoryModel.getCategory() == allCategoryModel.getCategory()) {
                    mAllCategoryModel = allCategoryModel;
                } else {
                    mAllCategoryModel = allCategoryModel;
                    setFloorNameList(mAllCategoryModel.getCategory());
                }
//                setFloorNameList(mAllCategoryModel.getCategory());
            }
        });

        //接收分数
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_SELECTITEM, SelectChange.class, new BindingConsumer<SelectChange>() {
            @Override
            public void call(SelectChange selectChange) {
                if (selectChange.mum > 0) {
                    isSelect.set(true);
                    entity.get().fractionNum.set(String.valueOf(selectChange.mum));
                    entity.get().showFractionNum.set(View.VISIBLE);
                } else {
                    isSelect.set(false);
                    entity.get().showFractionNum.set(View.GONE);
                }
                DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                entity.get().fraction.set("已扣" + decimalFormat.format(selectChange.score) + "分");
            }
        });

        //接收提交数据
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_SUBJECT, Subject.class, new BindingConsumer<Subject>() {
            @Override
            public void call(Subject subject) {
                mSelectSudentList = subject.mSelectSudentList;
                mSelectItemsBeanList = subject.mSelectItemsBeanList;
                // TODO: 2019/10/13
                CheckModel checkModel = HttpDataUtil.getCheckModel(mAllCategoryModel, mRoomModel, mSelectSudentList, mSelectItemsBeanList, setPhotoes());
                checkModel.setCheckDate(entity.get().date.get());
                boolean isSave = model.insertCheckModel(checkModel);
                if (isSave) {
                    upload(checkModel);
                } else {
                    ToastUtils.showShort("不能提交相同违规记录");
                }
            }
        });

        //清除数据
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_CLEARDATA, new BindingAction() {
            @Override
            public void call() {
                clearData();
            }
        });

        //接收图片
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_PHOTOVIEWMODEL_PHOTOPATHS, Integer.class, new BindingConsumer<Integer>() {
            @Override
            public void call(Integer integer) {
                if (integer.intValue() > 0) {
                    entity.get().cameraNum.set(integer.toString());
                    entity.get().showCameraNum.set(View.VISIBLE);
                } else {
                    entity.get().cameraNum.set("0");
                    entity.get().showCameraNum.set(View.GONE);
                }
            }
        });
    }

    public void getFaceStdent(int faceId){
        final FloorModel.StudentsBean studentsBean=HttpDataUtil.findStudent(faceId,mFloorModel,mCurrentFloorModelList);
        if (studentsBean!=null){
            String id=null;
            if (studentsBean.getDormid()!=null){
                id=studentsBean.getDormid();
            }else {
                id=studentsBean.getClassid();
            }
            if (id!=null){
                Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_CHECKVIEWMODEL_FACE);
                if (isCurrentRoomStudent(studentsBean)){
                    for (FloorModel.StudentsBean studentsBean1:mSelectSudentList){
                        if (studentsBean1.getUserid().equals(studentsBean.getUserid())){
                            ToastUtils.showShort("该学生已添加");
                            return;
                        }
                    }
                    // TODO: 2019/11/18 添加当前学生
                    Messenger.getDefault().send(getAddStudent(studentsBean), com.nhsoft.base.base.ConstantMessage.TOKEN_FACEVIEWMODEL_ADDSTUDENT);
//                    mSelectSudentList.add(studentsBean);
//                    entity.get().students.set(getStudents());
                }else {
                    clearData();
                    Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_CHECKVIEWMODEL_CLEARDATA);
                    FloorModel.RoomModel roomModel = HttpDataUtil.findRoom(id, mFloorModel, mFloorModelList);
                    if (isCurrentFloorRoom(id)){
                        entity.get().room.set(roomModel.getName());
                        getRoom(roomModel.getName());//选择房间
                        // TODO: 2019/11/18 添加当前学生
                        Messenger.getDefault().send(getAddStudent(studentsBean), com.nhsoft.base.base.ConstantMessage.TOKEN_FACEVIEWMODEL_ADDSTUDENT);
                    }else {
                        FloorModel floorModel1 = null;
                        for (FloorModel floorModel:mCurrentFloorModelList){
                            if (floorModel.getId().equals(studentsBean.getFid())){
                                floorModel1=floorModel;
                            }
                        }
                        if (floorModel1!=null){
                            entity.get().floor.set(floorModel1.getName());
                            getFloorModel(floorModel1.getName());//选择楼层
                            setRoomNameList();
                            // TODO: 2019/11/18 添加当前学生
                            Messenger.getDefault().send(getAddStudent(studentsBean), com.nhsoft.base.base.ConstantMessage.TOKEN_FACEVIEWMODEL_ADDSTUDENT);
                        }
                    }
//                    Messenger.getDefault().send(studentsBean, com.nhsoft.base.base.ConstantMessage.TOKEN_FACEVIEWMODEL_RESULT1);
//                    mSelectSudentList.clear();
//                    mSelectSudentList.add(studentsBean);
//                    entity.get().students.set(getStudents());
                }

            }
        }
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_CHECKBASEVIEWMODEL_FACE, Subject.class, new BindingConsumer<Subject>() {
            @Override
            public void call(Subject subject) {
                mSelectSudentList=subject.mSelectSudentList;
                mSelectItemsBeanList=subject.mSelectItemsBeanList;
            }
        });
    }

    private AddStudent getAddStudent(FloorModel.StudentsBean studentsBean){
        AddStudent addStudent=new AddStudent();
        addStudent.setStudentsBean(studentsBean);
        return addStudent;
    }

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

    /**
     * 刷脸返回的是否当前楼的房间
     * @param id 当前刷脸返回学生的房间id
     * @return
     */
    public boolean isCurrentFloorRoom(String id){
        for (FloorModel.RoomModel roomModel:mFloorModel.getChildrens()){
            if (roomModel.getId().equals(id)){
                return true;
            }
        }
        return false;
    }


    /**
     * 获取图片路径
     *
     * @return
     */
    public List<String> setPhotoes() {
        List<String> photoList = new ArrayList<>();
        String photoes = model.getPhotos();
        if (photoes.equals("")) {
            return photoList;
        }
        String[] photo = photoes.split(",");
        for (String s : photo) {
            photoList.add(s);
        }
        return photoList;
    }


    public void sentInformationMessage() {
        CheckInformation checkInformation = new CheckInformation();
        checkInformation.mFloorModelList = mFloorModelList;
        checkInformation.userCategoryList = userCategoryList;
        checkInformation.mRoomModel = mRoomModel;
        checkInformation.mFloorModel=mFloorModel;
        Messenger.getDefault().send(checkInformation, ConstantMessage.TOKEN_CHECKVIEWMODEL_INFORMATION);
        setStudentMeseenger();
    }

    /**
     * 设置楼名列表
     *
     * @param category
     */
    public void setFloorNameList(int category) {
        mFloorNameList = HttpDataUtil.getFloorNameList(category, mFloorModelList);
        mCurrentFloorModelList=HttpDataUtil.getFloorModel(category,mFloorModelList);
        if (mFloorNameList.size() != 0) {
            entity.get().floor.set(mFloorNameList.get(0));
            getFloorModel(mFloorNameList.get(0));
            setRoomNameList();
        }
    }

    /**
     * 设置房间名列表
     */
    public void setRoomNameList() {
        mRoomNameList = HttpDataUtil.getFloorRoomNameList(0,mFloorModel);
        if (mRoomNameList.size() != 0) {
            entity.get().room.set(mRoomNameList.get(0));
            getRoom(mRoomNameList.get(0));
        }
    }

    /**
     * 获取楼名对应的楼
     *
     * @param name 楼名
     */
    public void getFloorModel(String name) {
        mFloorModel = HttpDataUtil.getFloor(name, mFloorModelList);
        getAllStudent();
    }

    /**
     * 获取当前房间
     *
     * @param name 房间名
     */
    public void getRoom(String name) {
        mRoomModel = HttpDataUtil.getRoom(name, mFloorModel);
        getStudent();
    }

    /**
     * 获取当前楼所有的学生
     */
    public void getAllStudent() {
        mAllStudentList = HttpDataUtil.getAllStudentList(mFloorModel);
    }

    /**
     * 获取当前房间的所有学生
     */
    public void getStudent() {
        mStudentList = HttpDataUtil.getStudent(mFloorModel.getCategory(), mRoomModel.getId(), mAllStudentList);
        setStudentMeseenger();
    }

    /**
     * 传递当前房间全部房间
     */
    public void setStudentMeseenger() {
        CheckStudent checkStudent = new CheckStudent();
        checkStudent.mStudentList = mStudentList;
        checkStudent.mRoomModel = mRoomModel;
        Messenger.getDefault().send(checkStudent, ConstantMessage.TOKEN_CHECKVIEWMODEL_STUDENT);
    }


    /**
     * 切换弹出框确定清楚数据
     */
    public void showClearDialog(final int type) {
        MaterialDialogUtils.showBasicDialog(AppManager.getAppManager().currentActivity(), "请先提交", "不提交跳转之后当前界面数据清空")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        clearData();
                        Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_CHECKVIEWMODEL_CLEARDATA);
                        uc.selectType.setValue(type);
                    }
                }).show();
    }

    /**
     * 清楚数据
     */
    public void clearData() {
        mSelectSudentList.clear();
        mSelectItemsBeanList.clear();
        isSelect.set(false);
        entity.get().fractionNum.set(String.valueOf(0));
        entity.get().showFractionNum.set(View.GONE);
        entity.get().fraction.set("");
        entity.get().date.set(DateUtil.getCurrentTime());
        model.savePhotos("");
        entity.get().cameraNum.set("0");
        entity.get().showCameraNum.set(View.GONE);
    }


    public void upload(CheckModel checkModel) {
        checkModel.setPhotos(HttpDataUtil.getPhotoBase64(checkModel.getPhotos()));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(checkModel));
        addSubscribe(model.createCheck(model.getToken(), body)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle);
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.isOk()) {
                            ChecksModel checksModel = ChecksModelService.getInstance().getMaxChecksModel();
                            checksModel.setIsUpdate(true);
                            boolean isUpdate = ChecksModelService.getInstance().updateChecksModel(checksModel);
//                            if (isUpdate){
//
//                            }
                        } else {
                            ToastUtils.showShort(response.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
//                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                        uc.success.call();
                        clearData();
                        Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_CHECKVIEWMODEL_CLEARDATA);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        uc.success.call();
                        clearData();
                        Messenger.getDefault().sendNoMsg(ConstantMessage.TOKEN_CHECKVIEWMODEL_CLEARDATA);
                        //关闭对话框
                        dismissDialog();
                    }
                }));
    }


    public void getPermission(Fragment fragment) {
        //请求打开相机权限
        RxPermissions rxPermissions = new RxPermissions(fragment);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            ARouter.getInstance().build(RouterActivityPath.Face.PAGER_FACE).navigation(AppManager.getAppManager().currentActivity(),0);
//                            ToastUtils.showShort("权限已经打开，直接跳入相机");
                        } else {
                            ToastUtils.showShort("权限被拒绝");
                        }
                    }
                });
    }

}
