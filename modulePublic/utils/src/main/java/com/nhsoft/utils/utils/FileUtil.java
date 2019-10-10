package com.nhsoft.utils.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by lzf on 2019/10/10.
 * Describe:
 */

public class FileUtil {

    //把数据保存到SD卡中
    public static void save (Context context,String inputText,String fileName){
        FileOutputStream fileOutputStream=null;
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(context.getExternalCacheDir().getPath(),fileName);
            fileOutputStream = new FileOutputStream(file);
            bufferedWriter= new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(inputText);
            bufferedWriter.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //把数据从SD卡中文件读取
    public static String load(Context context,String fileName){
        FileInputStream fileInputStream;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(context.getExternalCacheDir().getPath(),fileName);
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String line=null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return stringBuilder.toString();
    }
}
