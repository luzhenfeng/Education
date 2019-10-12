package com.lzf.http.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.FloorModel;
import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.utils.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

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
     * @param codes 用户的所有检查模块应用编码（组成的时候中间由,隔开）
     * @return 获取用户所有的检查模块应用编码所对应的全部分类
     */
    public static List<AllCategoryModel> getUserCategoryList(String codes,List<AllCategoryModel> categoryModels){
        List<AllCategoryModel> allCategoryModelList=new ArrayList<>();
        String[] codeStrs=codes.split(",");
        for (String code:codeStrs){
            allCategoryModelList.addAll(getCategoryList(code,categoryModels));
        }
        return allCategoryModelList;
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
                itemsBeanList.add(itemsBean);
            }
        }
        return itemsBeanList;
    }

}
