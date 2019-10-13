package com.nhsoft.check.message;

/**
 * Created by lzf on 2019/10/11.
 * Describe:
 */

public class ConstantMessage {
    //CheckViewModel 传递信息
    public final static String TOKEN_CHECKVIEWMODEL_INFORMATION = "token_check_view_model_information";
    //CheckViewModel 当前房间的所有学生
    public final static String TOKEN_CHECKVIEWMODEL_STUDENT="token_check_view_model_student";
    //CheckViewModel 点击提交去获取数据
    public final static String TOKEN_CHECKVIEWMODEL_SUBJECT="token_check_view_model_subject";
    //CheckViewModel 清除数据
    public final static String TOKEN_CHECKVIEWMODEL_CLEARDATA="token_check_view_model_clear_data";
    //CheckViewModel 发个切换的消息，防止有选中的学生
    public final static String TOKEN_CHECKVIEWMODEL_CHANGE="token_check_view_model_clear_change";

    //CheckBaseViewModel 点击TabLayout
    public final static String TOKEN_CHECKBASEVIEWMODEL_ONTABSELECTEDCOMMAND = "token_check_base_view_model_onTabSelectedCommand";
    //CheckBaseViewModel选中条目
    public final static String TOKEN_CHECKBASEVIEWMODEL_SELECTITEM = "token_check_base_view_model_select_item";
    //CheckBaseViewModel传递选中的数据
    public final static String TOKEN_CHECKBASEVIEWMODEL_SUBJECT = "token_check_base_view_model_subject";
    //CheckBaseViewModel清除数据
    public final static String TOKEN_CHECKBASEVIEWMODEL_CLEARDATA = "token_check_base_view_model_clear_data";

    //PopupViewModel中的条目选中
    public final static String TOKEN_POPUPVIEWMODEL_SELECTITEM="token_popup_view_model_select_item";


}
