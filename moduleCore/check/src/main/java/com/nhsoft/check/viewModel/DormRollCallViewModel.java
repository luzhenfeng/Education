package com.nhsoft.check.viewModel;

import android.app.Application;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.lzf.greendao.entity.DormCheckModel;
import com.lzf.greendao.entity.StudentModel;
import com.lzf.greendao.service.DormCheckModelService;
import com.lzf.greendao.service.UserService;
import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.entity.LeaveStudentModel;
import com.lzf.http.utils.HttpDataUtil;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.entity.DormLeftEntity;
import com.nhsoft.check.entity.DormRightEntity;
import com.nhsoft.check.entity.DormRollCallEntity;
import com.nhsoft.check.message.ConstantMessage;
import com.nhsoft.check.ui.activity.ThrowStudentActivity;
import com.nhsoft.check.utils.CustomPopWindowUtil;
import com.nhsoft.utils.utils.DateUtil;
import com.nhsoft.utils.utils.LanguageUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import okhttp3.RequestBody;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;
import priv.lzf.mvvmhabit.http.BaseResponse;
import priv.lzf.mvvmhabit.http.ResponseThrowable;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.MaterialDialogUtils;
import priv.lzf.mvvmhabit.utils.RxUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/28.
 */
public class DormRollCallViewModel extends BasePopupViewModel<Repository> {

    //所有楼
    public List<FloorModel> mFloorModelList = new ArrayList<>();
    //寝室分类下的所有楼
    public List<FloorModel> mCurrentFloorModelList=new ArrayList<>();
    //当前楼所有房间
    public List<FloorModel.RoomModel> mRoomModelList = new ArrayList<>();
    //当前楼
    public FloorModel mFloorModel;
    //当前房间
    public FloorModel.RoomModel mRoomModel;
    //当前楼的所有学生
    public List<FloorModel.StudentsBean> mAllStudentList = new ArrayList<>();
    //当前房间的 所有学生
    public List<FloorModel.StudentsBean> mStudentList = new ArrayList<>();
    //左侧选中的条目
    public ObservableInt selectPos = new ObservableInt(0);
    //当前分类下的所有楼名
    public List<String> mFloorNameList = new ArrayList<>();
    //未上传的数据
    public List<DormCheckModel> mDormCheckModelList;
    //为上传的数据的条数
    public ObservableInt noDormCheckNum=new ObservableInt(0);
    //当前房间的检查时间内提交的数据，无null
    public DormCheckModel currentDorm;

    public ObservableField<DormRollCallEntity> entity=new ObservableField<>();

    public List<LeaveStudentModel> mLeaveStudentModelList=new ArrayList<>();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent<Integer> selectType = new SingleLiveEvent<>();
        public SingleLiveEvent date=new SingleLiveEvent();
        public SingleLiveEvent time=new SingleLiveEvent();
        public SingleLiveEvent success=new SingleLiveEvent();
        public SingleLiveEvent update=new SingleLiveEvent();
        public SingleLiveEvent noUpdateNumSubmit=new SingleLiveEvent();

    }

    public DormRollCallViewModel(@NonNull Application application) {
        super(application);
        model= Injection.provideDemoRepository();
        entity.set(new DormRollCallEntity());
        bindingCommand();
        initMessenger();
        itemBinding();
    }

    public void initMessenger() {
        //选择弹出框选中条目
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_POPUPVIEWMODEL_SELECTITEM, Integer.class, new BindingConsumer<Integer>() {
            @Override
            public void call(Integer i) {
                if (uc.selectType.getValue().intValue() == 3) {
                    changeFloor(i.intValue());
                    CustomPopWindowUtil.getInstance().dismiss();
                }
            }
        });
    }



    @Override
    public void bindingCommand() {
        super.bindingCommand();
        entity.get().submit=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                if (isSubmit()){
                    uc.update.call();
                }else {
                    ToastUtils.showShort("还有学生未点名");
                }
            }
        });
        entity.get().tvFloor=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                uc.selectType.setValue(3);
            }
        });

        entity.get().tvDate=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                LanguageUtils.setLanguageChina(getApplication());
                uc.date.call();
            }
        });

        entity.get().tvTime=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                LanguageUtils.setLanguageChina(getApplication());
                uc.time.call();
            }
        });
        entity.get().noUpdateNumSubmit=new BindingCommand(new BindingAction() {//一键上传
            @Override
            public void call() {
                if (noDormCheckNum.get()==0){
                    ToastUtils.showShort("无数据需要上传");
                }else {
                    uc.noUpdateNumSubmit.call();
                }
            }
        });
        entity.get().throwStudent=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                startActivity(ThrowStudentActivity.class);
            }
        });
    }

    public void itemBinding() {
        entity.get().itemLeftBinding = ItemBinding.of(BR.viewModel, R.layout.item_dorm_left);
        entity.get().itemRightBinding = ItemBinding.of(BR.viewModel, R.layout.item_dorm_right);
    }

    /**
     * 初始化
     */
    public void initData() {
        mDormCheckModelList=DormCheckModelService.getInstance().getNoUpdateDormCheckModelList();
        if (mDormCheckModelList!=null){
            noDormCheckNum.set(mDormCheckModelList.size());
        }else {
            noDormCheckNum.set(0);
        }
        mFloorModelList = HttpDataUtil.getFloorList(getApplication().getBaseContext());
        mFloorNameList = HttpDataUtil.getFloorNameList(1, mFloorModelList);
        mCurrentFloorModelList=HttpDataUtil.getFloorModel(1,mFloorModelList);
        if (mCurrentFloorModelList!=null&&mCurrentFloorModelList.size()!=0){
            getLeave();
        }
    }


    public void updateAll(){
        mDormCheckModelList=DormCheckModelService.getInstance().getNoUpdateDormCheckModelList();
        upload(mDormCheckModelList);
    }

    public void save(){
        DormCheckModel dormCheckModel=setDormCheckModel();
        if (dormCheckModel!=null){
            currentDorm=DormCheckModelService.getInstance().getNowDormCheckModel(dormCheckModel.getObjectId(),entity.get().date.get().substring(0,10));
            boolean isSave=false;
            if (currentDorm!=null){
                if (currentDorm.getIsUpdate()){
                    noDormCheckNum.set(noDormCheckNum.get()+1);
                }
                isSave=DormCheckModelService.getInstance().updateChecksModel(currentDorm,setStudentModelList());
            }else {
                isSave = model.insertDormCheckModel(setDormCheckModel(),setStudentModelList());
                if (isSave){
                    noDormCheckNum.set(noDormCheckNum.get()+1);
                }
            }
            if (isSave){
                uc.success.call();
            } else {
                ToastUtils.showShort("保存失败");
            }
        }
    }

    /**
     * 更换楼
     * @param pos
     */
    public void changeFloor(int pos){
        mFloorModel=mCurrentFloorModelList.get(pos);
        mAllStudentList = HttpDataUtil.getAllStudentList(mFloorModel);
        entity.get().floor.set(mFloorModel.getName());
        mRoomModelList=mFloorModel.getChildrens();
        setLeftItem();
        changeRight();
    }

    /**
     * 初始化左边条目
     */
    public void setLeftItem() {
        entity.get().observableLeftList.clear();
        selectPos.set(0);
        if (entity.get().observableLeftList.size() == 0) {
            for (String s : HttpDataUtil.getRoomNameList(mFloorModel)) {
                DormLeftEntity leftEntity = new DormLeftEntity();
                leftEntity.title.set(s);
                if (entity.get().observableLeftList.size() == 0)
                    leftEntity.select.set(true);
                DormLeftItemViewModel leftItemViewModel = new DormLeftItemViewModel(this, leftEntity);
                entity.get().observableLeftList.add(leftItemViewModel);
            }
        }
    }

    /**
     * 切换右边条目和切换数据
     */
    public void changeRight(){
        mRoomModel=mRoomModelList.get(selectPos.get());
        currentDorm=DormCheckModelService.getInstance().getNowDormCheckModel(mRoomModel.getId(),entity.get().date.get().substring(0,10));
        if (isSaveCheckDate()){
            setRightItem(2);
        }else {
            getStudent();
            setRightItem(1);
        }
    }

    /**
     * 初始化右边条目
     */
    public void setRightItem(int type) {
        entity.get().observableRightList.clear();
//        selectPos.set(0);
        if (entity.get().observableRightList.size() == 0) {
            if (type==1){
                for (FloorModel.StudentsBean s : mStudentList) {
                    DormRightEntity rightEntity = new DormRightEntity();
                    rightEntity.name.set(s.getStudentname());
                    rightEntity.bedno.set(s.getBedno());
                    rightEntity.userid.set(s.getUserid());
                    rightEntity.className.set(s.getClassname());
                    if (isLeave(s.getUserid())){
                        rightEntity.type.set(3);
                    }else {
                        rightEntity.type.set(1);
                    }
                    rightEntity.pos.set(mStudentList.indexOf(s)+1);
                    rightEntity.headPic.set(getApplication().getBaseContext().getExternalCacheDir().getPath() + "PhotoFile/"+s.getUserid()+".jpg");
                    DormRightItemViewModel rightItemViewModel = new DormRightItemViewModel(this, rightEntity);
                    entity.get().observableRightList.add(rightItemViewModel);
                }
            }else {
                for (StudentModel studentModel:currentDorm.getStudents()){
                    DormRightEntity rightEntity = new DormRightEntity();
                    rightEntity.name.set(studentModel.getStudentname());
                    rightEntity.bedno.set(Integer.parseInt(studentModel.getBendno()));
                    rightEntity.userid.set(studentModel.getUserid());
                    rightEntity.type.set(studentModel.getStatus()==1?1:(studentModel.getStatus()==2?4:(studentModel.getStatus()==4?2:(studentModel.getStatus()==8?3:studentModel.getStatus()==7?6:5))));
                    rightEntity.pos.set(currentDorm.getStudents().indexOf(studentModel)+1);
                    rightEntity.headPic.set(getApplication().getBaseContext().getExternalCacheDir().getPath() + "PhotoFile/"+studentModel.getUserid()+".jpg");
                    DormRightItemViewModel rightItemViewModel = new DormRightItemViewModel(this, rightEntity);
                    entity.get().observableRightList.add(rightItemViewModel);
                }
            }
        }
    }

    /**
     * 左边条目选中
     * @param pos
     */
    public void setSelectPos(int pos) {
        if (selectPos.get() != pos) {
            entity.get().observableLeftList.get(selectPos.get()).entity.get().select.set(false);
            entity.get().observableLeftList.get(pos).entity.get().select.set(true);
            selectPos.set(pos);
            changeRight();
        }
    }

    /**
     * 获取当前房间的所有学生
     */
    public void getStudent() {
        mStudentList = HttpDataUtil.getStudent(mFloorModel.getCategory(), mRoomModel.getId(), mAllStudentList);
    }

    /**
     * 检查日期内是否已保存过
     * @return
     */
    public boolean isSaveCheckDate(){
        if (currentDorm!=null&&currentDorm.getStudents()!=null&&currentDorm.getStudents().size()>0){
            return true;
        }
        return false;
    }

    /**
     * 获取左边条目下标
     *
     * @param leftItemViewModel
     * @return
     */
    public int getItemPosition(DormLeftItemViewModel leftItemViewModel) {
        return entity.get().observableLeftList.indexOf(leftItemViewModel);
    }

    public boolean isSubmit(){
        for (DormRightItemViewModel dormRightItemViewModel:entity.get().observableRightList){
            if (dormRightItemViewModel.entity.get().type.get()==0){
                return false;
            }
        }
        return true;
    }

    private DormCheckModel setDormCheckModel(){
        DormCheckModel dormCheckModel=new DormCheckModel();
        dormCheckModel.setObjectId(mRoomModel.getId());
        dormCheckModel.setObjectName(mRoomModel.getName());
        dormCheckModel.setCreateDate(DateUtil.getCurrentTime());
        dormCheckModel.setCheckDate(entity.get().date.get());
//        dormCheckModel.setSigntime(DateUtil.getCurrentTime());
        dormCheckModel.setUserid(UserService.getInstance().getUserId());
        dormCheckModel.setIsUpdate(false);
//        List<DormCheckModel> dormCheckModelList= DormCheckModelService.getInstance().getDormCheckModelList(DateUtil.getBeforeMinute(5));
//        if (dormCheckModelList.size()>0){
//            for (DormCheckModel model:dormCheckModelList){
//                if (model.getObjectId().equals(dormCheckModel.getObjectId())){
//                    ToastUtils.showShort("该寝室已提交");
//                    return null;
//                }
//            }
//        }
        return dormCheckModel;
    }

    private List<StudentModel> setStudentModelList(){
        List<StudentModel> studentModelList=new ArrayList<>();
        for (DormRightItemViewModel rightItemViewModel:entity.get().observableRightList){
            StudentModel studentModel=new StudentModel();
            studentModel.setUserid(rightItemViewModel.entity.get().userid.get());
            studentModel.setBendno(rightItemViewModel.entity.get().bedno.get()+"");
            studentModel.setStudentname(rightItemViewModel.entity.get().name.get());
            studentModel.setClassName(rightItemViewModel.entity.get().className.get());
            int type=rightItemViewModel.entity.get().type.get();
            studentModel.setStatus(type==1?1:(type==2?4:(type==3?8:(type==4?2:9))));
            studentModelList.add(studentModel);
        }
        return studentModelList;
    }

    public void upload(List<DormCheckModel> dormCheckModelList) {
        KLog.e( new Gson().toJson(dormCheckModelList));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(dormCheckModelList));
        addSubscribe(model.createNightRollCall(model.getToken(), body)
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
                            for (DormCheckModel dormCheckModel:mDormCheckModelList){
                                dormCheckModel.setIsUpdate(true);
                            }
                            DormCheckModelService.getInstance().updateChecksModelList(mDormCheckModelList);
                            noDormCheckNum.set(0);
                            uc.success.call();
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
//                        uc.success.call();
                        clearData();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        clearData();
                        dismissDialog();
                    }
                }));
    }

    public void getLeave() {
        addSubscribe(model.getLeave(model.getToken(), DateUtil.getCurrentDate())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle);
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponse<List<LeaveStudentModel>>>() {
                    @Override
                    public void accept(BaseResponse<List<LeaveStudentModel>> response) throws Exception {
                        if (response.isOk()) {
                            mLeaveStudentModelList=response.getData();
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
                        changeFloor(0);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissDialog();
                        changeFloor(0);
                    }
                }));
    }

    public void clearData(){

    }

    /**
     * 学生是否请假
     * @param userid
     * @return
     */
    public boolean isLeave(String userid){
        for (LeaveStudentModel leaveStudentModel:mLeaveStudentModelList){
            if (userid.equals(leaveStudentModel.getUserid())){
                return true;
            }
        }
        return false;
    }

}
