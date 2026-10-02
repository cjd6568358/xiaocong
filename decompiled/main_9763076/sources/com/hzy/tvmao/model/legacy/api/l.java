package com.hzy.tvmao.model.legacy.api;

import android.text.TextUtils;
import com.hzy.tvmao.model.legacy.api.data.UIProgramData;
import com.hzy.tvmao.utils.DataStoreUtil;
import com.kookong.app.data.ProgramData;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: TVWallDataUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class l {
    private static String o = "CATAGORYPREFERENCE";
    public static String[] a = {"综艺", "新闻", "财经", "生活", "科教", "少儿", "其他"};
    public static short[] b = {15, 24};
    public static short[] c = {22};
    public static short[] d = {15, 27};
    public static short[] e = {18};
    public static short[] f = {8};
    public static short[] g = {9};
    public static short[] h = {2};
    public static short[] i = {17};
    public static short[] j = {4};
    public static short[] k = {5, 13};
    public static short[] l = {6, 7, 8, 15};
    public static short[] m = {1};
    public static short[] n = {3, 16, 19};

    public static UIProgramData a(ProgramData programData) {
        if (programData == null || programData.pgs == null) {
            return null;
        }
        List<ProgramData.PairProgram> list = programData.pgs;
        UIProgramData uIProgramData = new UIProgramData(programData.nowTime);
        UIProgramData.CatItemData catItemData = new UIProgramData.CatItemData("综艺");
        UIProgramData.CatItemData catItemData2 = new UIProgramData.CatItemData("电视剧");
        UIProgramData.CatItemData catItemData3 = new UIProgramData.CatItemData("体育");
        UIProgramData.CatItemData catItemData4 = new UIProgramData.CatItemData("电影");
        UIProgramData.CatItemData catItemData5 = new UIProgramData.CatItemData("少儿");
        UIProgramData.CatItemData catItemData6 = new UIProgramData.CatItemData("新闻");
        UIProgramData.CatItemData catItemData7 = new UIProgramData.CatItemData("科教");
        UIProgramData.CatItemData catItemData8 = new UIProgramData.CatItemData("财经");
        UIProgramData.CatItemData catItemData9 = new UIProgramData.CatItemData("生活");
        UIProgramData.CatItemData catItemData10 = new UIProgramData.CatItemData("其他");
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= list.size()) {
                break;
            }
            ProgramData.PairProgram pairProgram = list.get(i3);
            if (pairProgram.flcate == null || pairProgram.flcate.equals(Constants.MAIN_VERSION_TAG) || pairProgram.flcate.equals("null")) {
                catItemData10.getSingleDataList().add(pairProgram);
            } else {
                String[] strArrSplit = pairProgram.flcate.split(",");
                for (int i4 = 0; i4 < strArrSplit.length; i4++) {
                    if (strArrSplit[i4].equals(String.valueOf(5))) {
                        catItemData5.getSingleDataList().add(pairProgram);
                    }
                    if (strArrSplit[i4].equals(String.valueOf(3))) {
                        catItemData3.getSingleDataList().add(pairProgram);
                    }
                    if (strArrSplit[i4].equals(String.valueOf(7))) {
                        catItemData8.getSingleDataList().add(pairProgram);
                    }
                    if (strArrSplit[i4].equals(String.valueOf(8))) {
                        catItemData7.getSingleDataList().add(pairProgram);
                    }
                    if (strArrSplit[i4].equals(String.valueOf(6))) {
                        catItemData9.getSingleDataList().add(pairProgram);
                    }
                    if (strArrSplit[i4].equals(String.valueOf(9))) {
                        catItemData6.getSingleDataList().add(pairProgram);
                    }
                    if (strArrSplit[i4].equals(String.valueOf(4))) {
                        catItemData4.getSingleDataList().add(pairProgram);
                    }
                    if (strArrSplit[i4].equals(String.valueOf(1))) {
                        catItemData2.getSingleDataList().add(pairProgram);
                    }
                    if (strArrSplit[i4].equals(String.valueOf(2))) {
                        catItemData.getSingleDataList().add(pairProgram);
                    }
                    if (strArrSplit[i4].equals(String.valueOf(10))) {
                        catItemData10.getSingleDataList().add(pairProgram);
                    }
                }
            }
            i2 = i3 + 1;
        }
        String[] strArrA = a();
        for (int i5 = 0; i5 < strArrA.length; i5++) {
            if ("综艺".equals(strArrA[i5])) {
                uIProgramData.getCatItemDatas().add(catItemData);
            } else if ("新闻".equals(strArrA[i5])) {
                uIProgramData.getCatItemDatas().add(catItemData6);
            } else if ("财经".equals(strArrA[i5])) {
                uIProgramData.getCatItemDatas().add(catItemData8);
            } else if ("生活".equals(strArrA[i5])) {
                uIProgramData.getCatItemDatas().add(catItemData9);
            } else if ("科教".equals(strArrA[i5])) {
                uIProgramData.getCatItemDatas().add(catItemData7);
            } else if ("少儿".equals(strArrA[i5])) {
                uIProgramData.getCatItemDatas().add(catItemData5);
            } else if ("其他".equals(strArrA[i5])) {
                uIProgramData.getCatItemDatas().add(catItemData10);
            }
        }
        uIProgramData.getCatItemDatas().add(catItemData2);
        uIProgramData.getCatItemDatas().add(catItemData4);
        uIProgramData.getCatItemDatas().add(catItemData3);
        a(uIProgramData);
        return uIProgramData;
    }

    public static String[] a() {
        String[] stringArray = DataStoreUtil.i().getStringArray(o);
        if (TextUtils.isEmpty(stringArray[0])) {
            return a;
        }
        return stringArray;
    }

    private static void a(UIProgramData uIProgramData) {
        ArrayList<UIProgramData.CatItemData> catItemDatas = uIProgramData.getCatItemDatas();
        if (catItemDatas != null) {
            for (UIProgramData.CatItemData catItemData : catItemDatas) {
                List<ProgramData.PairProgram> singleDataList = catItemData.getSingleDataList();
                a(singleDataList);
                if (singleDataList != null) {
                    for (ProgramData.PairProgram pairProgram : singleDataList) {
                        if (pairProgram != null && pairProgram.resId != null) {
                            String str = String.valueOf(pairProgram.resId) + pairProgram.epi;
                            if (!TextUtils.isEmpty(str)) {
                                catItemData.addProgramItem(str, pairProgram);
                            } else {
                                catItemData.addProgramItem(pairProgram.sn, pairProgram);
                            }
                        }
                    }
                    b(catItemData.getProgramItemLists());
                }
            }
        }
    }

    private static void a(List<ProgramData.PairProgram> list) {
        if (DataStoreUtil.i().getBoolean(com.hzy.tvmao.a.a.D, true)) {
            if (list != null && list.size() > 0) {
                Collections.sort(list, new m());
                return;
            }
            return;
        }
        if (list != null && list.size() > 0) {
            Collections.sort(list, new n());
        }
    }

    private static void b(List<UIProgramData.ProgramItem> list) {
        if (list != null && list.size() > 0) {
            Collections.sort(list, new o());
        }
    }
}
