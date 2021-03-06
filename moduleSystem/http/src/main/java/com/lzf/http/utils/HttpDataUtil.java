package com.lzf.http.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzf.greendao.entity.ChecksModel;
import com.lzf.greendao.service.UserService;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.entity.FloorModel;
import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.utils.utils.DateUtil;
import com.nhsoft.utils.utils.FileUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import priv.lzf.mvvmhabit.utils.SPUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/10.
 */
public class HttpDataUtil {


    /**
     * 获取所有的分类
     * @param context
     * @return 所有的分类
     */
    public static List<AllCategoryModel> getAllCategoryList(Context context){
        String text=FileUtil.load(context, Constant.checkCategoryFileName);
        return new Gson().fromJson(text,new TypeToken<List<AllCategoryModel>>(){}.getType());
    }

    /**
     * 获取所有楼的信息
     * @param context
     * @return 所有楼的信息
     */
    public static List<FloorModel> getFloorList(Context context){
        String text=FileUtil.load(context, Constant.checkObjectFileName);
        return new Gson().fromJson(text,new TypeToken<List<FloorModel>>(){}.getType());
    }

    /**
     * 获取除当前楼全部学生
     * @param floorModelList 全部楼
     * @param currentFloorModel 当前楼
     * @return
     */
    public static List<FloorModel.StudentsBean> getAllFloorStudentList(List<FloorModel> floorModelList,FloorModel currentFloorModel){
        List<FloorModel.StudentsBean> studentsBeanList=new ArrayList<>();
        for (FloorModel floorModel:floorModelList){
            if (!floorModel.getId().equals(currentFloorModel.getId())){
                studentsBeanList.addAll(floorModel.getStudents());
            }
        }
        return studentsBeanList;
    }

    /**
     * 获取检查模块应用编码所对应的全部分类
     * @param code 检查模块应用编码
     * @param categoryModels 所有分类
     * @return 获取检查模块应用编码所对应的全部分类
     */
    public static List<AllCategoryModel> getCategoryList(String code,List<AllCategoryModel> categoryModels){
        List<AllCategoryModel> allCategoryModelList=new ArrayList<>();
        for (AllCategoryModel allCategoryModel:categoryModels){
            if (allCategoryModel.getMcode().equals(code)){
                allCategoryModelList.add(allCategoryModel);
            }
        }
        return allCategoryModelList;
    }

    /**
     * 获取用户所有的检查模块应用编码所对应的全部分类
     * @param code 用户选中的检查模块应用编码
     * @return 获取用户所有的检查模块应用编码所对应的全部分类
     */
    public static List<AllCategoryModel> getUserCategoryList(String code,List<AllCategoryModel> categoryModels){
        List<AllCategoryModel> allCategoryModelList=new ArrayList<>();
        allCategoryModelList.addAll(getCategoryList(code,categoryModels));
        return allCategoryModelList;
    }

    /**
     * 获取全部mcode值
     * @param codes
     * @return
     */
    public static List<String> getAllMCode(String codes){
        List<String> strings=new ArrayList<>();
        String[] codeStrs=codes.split(",");
        for (String code:codeStrs){
            strings.add(code);
        }
        return strings;
    }


    /**
     * 获取用户分类所有名字
     * @param userCategoryList 该用户所对应的所有分类
     * @return 用户分类所有名字
     */
    public static List<String> getUserCategoryNameList(List<AllCategoryModel> userCategoryList){
        List<String> names=new ArrayList<>();
        for (AllCategoryModel allCategoryModel:userCategoryList){
            names.add(allCategoryModel.getName());
        }
        return names;
    }


    /**
     * 获取用户所对应的所有分类category值
     * @param categoryModels 用户所有的检查模块应用编码所对应的全部分类
     * @return 用户所对应的所有分类category值
     */
    public static List<Integer> getCategorys(List<AllCategoryModel> categoryModels){
        List<Integer> categoryList=new ArrayList<>();
        a:for (AllCategoryModel categoryModel:categoryModels){
            for (Integer category:categoryList){
                if (category.intValue()==categoryModel.getCategory()){
                    continue a;
                }
            }
            categoryList.add(categoryModel.getCategory());
        }
        return categoryList;
    }


    /**
     * 获取单个分类category所对应的全部楼名
     * @param category 分类
     * @param floorModels 所有楼
     * @return 获取单个分类category所对应的全部楼名
     */
    public static List<String> getFloorNameList(int category, List<FloorModel> floorModels){
        List<String> floorNameList=new ArrayList<>();
        for (FloorModel floorModel : floorModels){
            if (floorModel.getCategory()==category){
                floorNameList.add(floorModel.getName());
            }
        }
        return floorNameList;
    }

    /**
     * 获取单个分类category所对应的全部楼
     * @param category
     * @param floorModels
     * @return
     */
    public static List<FloorModel> getFloorModel(int category,List<FloorModel> floorModels){
        List<FloorModel> floorModelList=new ArrayList<>();
        for (FloorModel floorModel : floorModels){
            if (floorModel.getCategory()==category){
                floorModelList.add(floorModel);
            }
        }
        return floorModelList;
    }

    /**
     * 获取楼号对应的楼
     * @param floorName 楼名
     * @param floorModels 所有楼
     * @return 获取楼号对应的楼
     */
    public static FloorModel getFloor(String floorName, List<FloorModel> floorModels){
        FloorModel mFloorModel =new FloorModel();
        for (FloorModel floorModel : floorModels){
            if (floorModel.getName().equals(floorName)){
                mFloorModel = floorModel;
            }
        }
        return mFloorModel;
    }

    /**
     * 获取单幢楼所有的房间
     * @param mFloorModel
     * @return 获取单幢楼所有的房间
     */
    public static List<FloorModel.RoomModel> getRoomList(FloorModel mFloorModel){
        return mFloorModel.getChildrens();
    }


    /**
     * 获取单幢楼所有的房间名
     * @param mFloorModel 所在的楼
     * @return 获取单幢楼所有的房间名
     */
    public static List<String> getRoomNameList(FloorModel mFloorModel){
        List<String> roomNameList=new ArrayList<>();
        for (FloorModel.RoomModel roomModel:mFloorModel.getChildrens()){
            roomNameList.add(roomModel.getName());
        }
        return roomNameList;
    }

    /**
     * 获取单幢楼一层的房间名
     * @param floor 几楼
     * @param mFloorModel 所在的楼
     * @return
     */
    public static List<String> getFloorRoomNameList(int floor,FloorModel mFloorModel){
        List<String> roomNameList=new ArrayList<>();
        for (FloorModel.RoomModel roomModel:mFloorModel.getChildrens()){
            if (floor==0){
                roomNameList.add(roomModel.getName());
            }else {
                if (roomModel.getFloor()==floor){
                    roomNameList.add(roomModel.getName());
                }
            }
        }
        return roomNameList;
    }

    /**
     * 获取单幢楼所有的楼层
     * @return
     */
    public static List<Integer> getFloorList(FloorModel mFloorModel){
        List<Integer> floorList=new ArrayList<>();
        a:for (FloorModel.RoomModel roomModel:mFloorModel.getChildrens()){
            if (floorList.size()>0){
                for (Integer integer : floorList) {
                    if (integer.intValue()==roomModel.getFloor()){
                        continue a;
                    }
                }
                floorList.add(roomModel.getFloor());
            }else {
                floorList.add(roomModel.getFloor());
            }
        }
        return floorList;
    }
    /**
     * 获取单幢楼所有的楼层
     * @return
     */
    public static List<String> getFloorNameList(FloorModel mFloorModel){
        List<String> floorList=new ArrayList<>();
        List<Integer> integers=getFloorList(mFloorModel);
        for (Integer integer : integers) {
            floorList.add(String.valueOf(integer));
        }
        floorList.add(0,"全部");
        return floorList;
    }


    /**
     * 获取当前房间
     * @param roomName 房间名
     * @param mFloorModel 当前楼
     * @return 当前房间
     */
    public static FloorModel.RoomModel getRoom(String roomName,FloorModel mFloorModel){
        FloorModel.RoomModel mRoomModel=new FloorModel.RoomModel();
        for (FloorModel.RoomModel roomModel:mFloorModel.getChildrens()){
            if (roomModel.getName().equals(roomName)){
                mRoomModel=roomModel;
            }
        }
        return mRoomModel;
    }

    /**
     * 获取单幢楼所有的学生
     * @param mFloorModel
     * @return
     */
    public static List<FloorModel.StudentsBean> getAllStudentList(FloorModel mFloorModel){
        return mFloorModel.getStudents();
    }

    /**
     * 查找刷脸支付的学生
     * @param subjectId 刷脸支付的id
     * @param mFloorModel 当前楼
     * @param floorModelList 当前分类的所有楼
     * @return
     */
    public static FloorModel.StudentsBean findStudent(int subjectId,FloorModel mFloorModel,List<FloorModel> floorModelList){
        for (FloorModel.StudentsBean studentsBean:getAllStudentList(mFloorModel)){
            if (studentsBean.getStudentid()==subjectId){
                return studentsBean;
            }
        }
        for (FloorModel.StudentsBean studentsBean:getAllFloorStudentList(floorModelList,mFloorModel)){
            if (studentsBean.getStudentid()==subjectId){
                return studentsBean;
            }
        }
        return null;
    }

    /**
     * 获取除当前楼的所有房间
     * @param currentFloorModel 当前楼
     * @param floorModelList 所有楼
     * @return
     */
    public static List<FloorModel.RoomModel> findAllRooms(FloorModel currentFloorModel,List<FloorModel> floorModelList){
        List<FloorModel.RoomModel> roomModelList=new ArrayList<>();
        for (FloorModel floorModel:floorModelList){
            if (!currentFloorModel.getId().equals(floorModel.getId())){
                roomModelList.addAll(floorModel.getChildrens());
            }
        }
        return roomModelList;
    }

    /**
     * 获取刷脸返回学生的当前房间
     * @param id 当前房间id
     * @param floorModel 当前房间
     * @param floorModelList 所有房间
     * @return
     */
    public static FloorModel.RoomModel findRoom(String id,FloorModel floorModel,List<FloorModel> floorModelList){
        for (FloorModel.RoomModel roomModel:floorModel.getChildrens()){
            if (roomModel.getId().equals(id)){
                return roomModel;
            }
        }
        for (FloorModel.RoomModel roomModel:findAllRooms(floorModel,floorModelList)){
            if (roomModel.getId().equals(id)){
                return roomModel;
            }
        }
        return null;
    }

//    /**
//     * 获取刷脸返回学生的当前楼
//     * @param id 当前楼id
//     * @param floorModelList 所有楼
//     * @return
//     */
//    public static FloorModel findFloor(String id,List<FloorModel> floorModelList){
////        if (id.equals(currentFloorModel.getId())){
////            return currentFloorModel;
////        }
//        for (FloorModel floorModel:floorModelList){
//            if (floorModel.getId().equals(id)){
//                return floorModel;
//            }
//        }
//        return null;
//    }



    /**
     * 获取对应房间里面的学生列表
     * @param category 总检查权限分类
     * @param id category=0 班级id category=1 寝室id
     * @param studentsBeanList 当前楼的全部学生
     * @return 对应房间里面的学生列表
     */
    public static List<FloorModel.StudentsBean> getStudent(int category,String id,List<FloorModel.StudentsBean> studentsBeanList){
        List<FloorModel.StudentsBean> studentsBeans=new ArrayList<>();
        if (category==0){
            for (FloorModel.StudentsBean studentsBean:studentsBeanList){
                if (studentsBean.getClassid().equals(id)){
                    studentsBeans.add(studentsBean);
                }
            }
        }else if (category==1){
            for (FloorModel.StudentsBean studentsBean:studentsBeanList){
                if (studentsBean.getDormid().equals(id)){
                    studentsBeans.add(studentsBean);
                }
            }
        }
        return studentsBeans;
    }


    /**
     * 获取当前分类
     * @param categoryName 分类名
     * @param userCategoryList 用户所有分类
     * @return 当前分类
     */
    public static AllCategoryModel getCurrentCategory(String categoryName,List<AllCategoryModel> userCategoryList){
        AllCategoryModel bean=new AllCategoryModel();
        for (AllCategoryModel allCategoryModel:userCategoryList){
            if (allCategoryModel.getName().equals(categoryName)){
                bean=allCategoryModel;
            }
        }
        return bean;
    }

    /**
     * 获取当前分类的全部子分类
     * @param allCategoryModel 当前分类
     * @return 当前分类的全部子分类
     */
    public static List<AllCategoryModel.ChidrensBean> getChidrenCategoryList(AllCategoryModel allCategoryModel){
        List<AllCategoryModel.ChidrensBean> chidrensBeanList=new ArrayList<>();
        chidrensBeanList.addAll(allCategoryModel.getChidrens());
        chidrensBeanList.add(getDefaultChildren());
        return chidrensBeanList;
    }

    /**
     * 自定义子分类
     * @return 自定义子分类
     */
    public static AllCategoryModel.ChidrensBean getDefaultChildren(){
        AllCategoryModel.ChidrensBean chidrensBean=new AllCategoryModel.ChidrensBean();
        chidrensBean.setId("0");
        chidrensBean.setName("自定义");
        return chidrensBean;
    }

    /**
     * 获取当前分类下的全部子分类名
     * @param chidrensBeanList 当前分类下的所有子分类（包括自定义）
     * @return 当前分类下的全部子分类名
     */
    public static List<String> getChidrenCategoryNameList(List<AllCategoryModel.ChidrensBean> chidrensBeanList){
        List<String> names=new ArrayList<>();
        for (AllCategoryModel.ChidrensBean bean:chidrensBeanList){
            names.add(bean.getName());
        }
        return names;
    }


    /**
     * 获取当前分类的全部的检查条目
     * @param allCategoryModel 当前分类
     * @return 当前分类的全部的检查条目
     */
    public static List<AllCategoryModel.ItemsBean> getAllChiledrenItemsBeanList(AllCategoryModel allCategoryModel){
        return allCategoryModel.getItems();
    }

    /**
     * 获取当前子分类下的所有检查条目
     * @param chidrensBean 当前子分类
     * @param allChiledrenItemsBeanList 当前分类的全部的检查条目
     * @return 当前子分类下的所有检查条目
     */
    public static List<AllCategoryModel.ItemsBean> getItemsBeanList(AllCategoryModel.ChidrensBean chidrensBean,List<AllCategoryModel.ItemsBean> allChiledrenItemsBeanList){
        List<AllCategoryModel.ItemsBean> itemsBeanList=new ArrayList<>();
        for (AllCategoryModel.ItemsBean itemsBean:allChiledrenItemsBeanList){
            if (chidrensBean.getId().equals(itemsBean.getCategoryId())){
                itemsBean.setShowbed(chidrensBean.getShowbed());
                itemsBeanList.add(itemsBean);
            }
        }
        return itemsBeanList;
    }


    /**
     * 当前房间中所有的班级id组合(除category=0班级外)
     * @param roomModel 当前房间
     * @return
     */
    public static String getClassIds(FloorModel.RoomModel roomModel){
        String ids="";
        for (FloorModel.RoomModel.ChildrensBean childrensBean:roomModel.getChildrens()){
            if (roomModel.getChildrens().indexOf(childrensBean)==roomModel.getChildrens().size()-1){
                ids+=childrensBean.getId();
            }else {
                ids+=childrensBean.getId()+",";
            }
        }
        return ids;
    }

    /**
     * 当前房间中所有的班级id组合(除category=0班级外)
     * @param roomModel 当前房间
     * @return
     */
    public static String getClassNames(FloorModel.RoomModel roomModel){
        String names="";
        for (FloorModel.RoomModel.ChildrensBean childrensBean:roomModel.getChildrens()){
            if (roomModel.getChildrens().indexOf(childrensBean)==roomModel.getChildrens().size()-1){
                names+=childrensBean.getName();
            }else {
                names+=childrensBean.getName()+",";
            }
        }
        return names;
    }

    /**
     * 组成上传所需的数据
     * @param mAllCategoryModel 当前总分类
     * @param mRoomModel 当前房间
     * @param mSelectStudentList 选中的学生
     * @param mSelectItemList 选中的条目
     * @param photos 照片
     * @return
     */
    public static CheckModel getCheckModel(AllCategoryModel mAllCategoryModel,FloorModel.RoomModel mRoomModel,List<FloorModel.StudentsBean> mSelectStudentList,List<AllCategoryModel.ItemsBean> mSelectItemList,List<String> photos){
        CheckModel checkModel=new CheckModel();
        List<CheckModel.StudentsBean> studentsBeanList=new ArrayList<>();
        List<CheckModel.RecordsBean> recordsBeanList=new ArrayList<>();
        for (FloorModel.StudentsBean studentsBean:mSelectStudentList){
            CheckModel.StudentsBean bean=new CheckModel.StudentsBean();
            bean.setUserid(studentsBean.getUserid());
            bean.setStudentid(studentsBean.getStudentid());
            bean.setStudentno(studentsBean.getStudentno());
            bean.setStudentname(studentsBean.getStudentname());
            bean.setClassid(studentsBean.getClassid());
            bean.setClassname(studentsBean.getClassname());
            bean.setBedno(studentsBean.getBedno());
            bean.setScore(0);
            studentsBeanList.add(bean);
        }

        for (AllCategoryModel.ItemsBean itemsBean:mSelectItemList){
            CheckModel.RecordsBean recordsBean=new CheckModel.RecordsBean();
            recordsBean.setId(itemsBean.getId());
            recordsBean.setCode(itemsBean.getCode());
            recordsBean.setName(itemsBean.getName());
            recordsBean.setCategoryId(itemsBean.getCategoryId());
            recordsBean.setDeducttype(itemsBean.getDeducttype());
            recordsBean.setRuletype(itemsBean.getRuletype());
            recordsBean.setScore(itemsBean.getScore());
            recordsBean.setBednos(itemsBean.getBednos());
            recordsBean.setClassId(itemsBean.getClassId());
            recordsBean.setClassName(itemsBean.getClassName());
            recordsBean.setItemcount(itemsBean.getItemcount());
            recordsBeanList.add(recordsBean);
        }
        checkModel.setMcode(mAllCategoryModel.getMcode());
        checkModel.setCategory(mAllCategoryModel.getCategory());
        checkModel.setCateId(mAllCategoryModel.getId());
        checkModel.setCateName(mAllCategoryModel.getName());
        checkModel.setCheckDate(DateUtil.getCurrentTime());
        checkModel.setCreateDate(DateUtil.getCurrentTime());
        checkModel.setUuid(UUID.randomUUID().toString());
        if (mAllCategoryModel.getCategory()==0){
            checkModel.setClassId(mRoomModel.getId());
            checkModel.setClassName(mRoomModel.getName());
        }else {
            checkModel.setClassId(HttpDataUtil.getClassIds(mRoomModel));
            checkModel.setClassName(HttpDataUtil.getClassNames(mRoomModel));
        }
        if (mAllCategoryModel.getCategory()!=0){
            checkModel.setObjectId(mRoomModel.getId());
            checkModel.setObjectName(mRoomModel.getName());
        }
        checkModel.setStudents(studentsBeanList);
        checkModel.setRecords(recordsBeanList);
        checkModel.setPhotos(photos);
        return checkModel;
    }

    public static CheckModel getCheckModel(ChecksModel checksModel){
        CheckModel checkModel=new CheckModel();
        if (checksModel.getStudents()!=null){
            List<CheckModel.StudentsBean> studentsBeanList=new Gson().fromJson(checksModel.getStudents(),new TypeToken<List<CheckModel.StudentsBean>>(){}.getType());
            checkModel.setStudents(studentsBeanList);
        }
        if (checksModel.getRecords()!=null){
            List<CheckModel.RecordsBean> recordsBeanList=new Gson().fromJson(checksModel.getRecords(),new TypeToken<List<CheckModel.RecordsBean>>(){}.getType());
            checkModel.setRecords(recordsBeanList);
        }
        if (checksModel.getPhotos()!=null){
            List<String> photos=new Gson().fromJson(checksModel.getPhotos(),new TypeToken<List<String>>(){}.getType());
            checkModel.setPhotos(photos);
        }
        checkModel.setMcode(checksModel.getMcode());
        checkModel.setCategory(checksModel.getCategory());
        checkModel.setCateId(checksModel.getCateId());
        checkModel.setCateName(checksModel.getCateName());
        checkModel.setCheckDate(checksModel.getCheckDate());
        checkModel.setClassId(checksModel.getClassId());
        checkModel.setClassName(checksModel.getClassName());
        if (checksModel.getObjectId()!=null){
            checkModel.setObjectId(checksModel.getObjectId());
            checkModel.setObjectName(checksModel.getObjectName());
        }
        return checkModel;
    }


    /**
     * 图片转换成Base64
     * @param bit
     * @return
     */
    public static String Bitmap2StrByBase64(Bitmap bit){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 90, bos);//第二个入参表示图片压缩率，如果是100就表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 获取全部图片的base64
     * @param photoPathes
     * @return
     */
    public static List<String> getPhotoBase64(List<String> photoPathes){
        List<String> photoBase64Str=new ArrayList<>();
        for (String s:photoPathes){
            Bitmap bitmap= BitmapFactory.decodeFile(s);
            if (bitmap!=null){
                photoBase64Str.add(Bitmap2StrByBase64(bitmap));
            }
        }
        return photoBase64Str;
    }


}
