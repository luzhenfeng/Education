package com.lzf.face.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by licheng on 2017/8/3.
 */
public class IntentDataHelper {

    private static List<byte[]> faceList = new ArrayList<>();
    private static List<byte[]> bigFaceList = new ArrayList<>();

    public static List<byte[]> getFaceList() {
        return IntentDataHelper.faceList;
    }

    public static void setFaceList(List<byte[]> faceList) {
        IntentDataHelper.faceList.clear();
        IntentDataHelper.faceList.addAll(faceList);
    }

    public static List<byte[]> getBigFaceList() {
        return bigFaceList;
    }

    public static void setBigFaceList(List<byte[]> bigFaceList) {
        IntentDataHelper.bigFaceList.clear();
        IntentDataHelper.bigFaceList.addAll(bigFaceList);
    }
}
