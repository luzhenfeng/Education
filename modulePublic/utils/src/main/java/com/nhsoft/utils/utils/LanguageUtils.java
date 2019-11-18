package com.nhsoft.utils.utils;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageUtils {

    public final static int CHINALANGUAGE=1;
    public final static int ENGLISHLANGUAGE=2;
    /**
     * 设置语言
     * @param context
     * @param type 1、中文 2、英文
     */
    public static void getLanguage(Context context,int type){
        switch (type){
            case CHINALANGUAGE:
                setLanguageChina(context);
                break;
            case ENGLISHLANGUAGE:
                setLanguageEnglish(context);
                break;
            default://先默认中文
                setLanguageChina(context);
                break;
        }
    }


    public static void setLanguageChina(Context context){
        context=context.getApplicationContext();
        Locale.setDefault(Locale.CHINA);
        Configuration config = context.getResources().getConfiguration();
        config.locale = Locale.CHINA;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
    }

    public static void setLanguageEnglish(Context context){
        context=context.getApplicationContext();
        Locale.setDefault(Locale.US);
        Configuration config = context.getResources().getConfiguration();
        config.locale = Locale.US;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
    }

    public static void setLanguageEnglishs(Context context){
        context=context.getApplicationContext();
        Locale.setDefault(Locale.JAPAN);
        Configuration config = context.getResources().getConfiguration();
        config.locale = Locale.JAPAN;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
    }
}
