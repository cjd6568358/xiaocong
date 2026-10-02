package com.hzy.tvmao.ir.ac;

import android.text.TextUtils;
import com.hzy.tvmao.utils.c;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ACModelV2 implements Serializable {
    private static final long serialVersionUID = 1;
    private int curTmp;
    private int curWindSpeed;
    private int modelType;
    private int lowTmp = 16;
    private int highTmp = 30;
    private boolean tempCanControl = true;
    private List<String> windSpeedList = new ArrayList();
    private boolean windSpeedCanControl = true;
    private Map<Integer, ACExpandKey> expandKeyMap = new HashMap();

    public void initMoel(int i, String str) {
        initType(i);
        initTmp(str);
        initWindSpeed(str);
    }

    private void initType(int i) {
        switch (i) {
            case ACConstants.TAG_AC_MODE_COOL_FUNCTION /* 1501 */:
                this.modelType = 0;
                break;
            case ACConstants.TAG_AC_MODE_HEAT_FUNCTION /* 1502 */:
                this.modelType = 1;
                break;
            case ACConstants.TAG_AC_MODE_AUTO_FUNCTION /* 1503 */:
                this.modelType = 2;
                break;
            case ACConstants.TAG_AC_MODE_FAN_FUNCTION /* 1504 */:
                this.modelType = 3;
                break;
            case ACConstants.TAG_AC_MODE_DRY_FUNCTION /* 1505 */:
                this.modelType = 4;
                break;
        }
    }

    private void initTmp(String str) {
        if (TextUtils.isEmpty(str) || !str.contains("T")) {
            setModelTemp();
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = this.lowTmp; i <= this.highTmp; i++) {
            arrayList.add(new StringBuilder(String.valueOf(i)).toString());
        }
        for (String str2 : str.split("\\|")) {
            if (str2.contains("T")) {
                String[] strArrSplit = str2.split("&");
                if (strArrSplit.length == 1) {
                    this.tempCanControl = false;
                    this.curTmp = -1;
                    return;
                }
                int[] iArrA = c.a(strArrSplit[1], ",");
                for (int i2 : iArrA) {
                    arrayList.remove(new StringBuilder(String.valueOf(i2)).toString());
                }
                if (arrayList.size() == 0) {
                    this.tempCanControl = false;
                    this.curTmp = -1;
                    return;
                } else {
                    this.lowTmp = Integer.parseInt((String) arrayList.get(0));
                    this.highTmp = Integer.parseInt((String) arrayList.get(arrayList.size() - 1));
                    setModelTemp();
                    return;
                }
            }
        }
    }

    private void setModelTemp() {
        switch (this.modelType) {
            case 0:
                setDefaultTemperature(26);
                break;
            case 1:
                setDefaultTemperature(20);
                break;
            case 2:
                setDefaultTemperature(24);
                break;
            case 3:
                setDefaultTemperature(24);
                break;
            case 4:
                setDefaultTemperature(23);
                break;
        }
    }

    private void setDefaultTemperature(int i) {
        if (!isContainsTmp(i)) {
            this.curTmp = this.lowTmp;
        } else {
            this.curTmp = i;
        }
    }

    public boolean isContainsTmp(int i) {
        return i >= this.lowTmp && i <= this.highTmp;
    }

    private void initWindSpeed(String str) {
        for (int i = 0; i <= 3; i++) {
            this.windSpeedList.add(new StringBuilder(String.valueOf(i)).toString());
        }
        if (TextUtils.isEmpty(str) || !str.contains("S")) {
            this.curWindSpeed = Integer.parseInt(this.windSpeedList.get(0));
            return;
        }
        for (String str2 : str.split("\\|")) {
            if (str2.contains("S")) {
                String[] strArrSplit = str2.split("&");
                if (strArrSplit.length == 1) {
                    this.windSpeedCanControl = false;
                    this.curWindSpeed = -1;
                    return;
                }
                int[] iArrA = c.a(strArrSplit[1], ",");
                for (int i2 : iArrA) {
                    this.windSpeedList.remove(new StringBuilder(String.valueOf(i2)).toString());
                }
                if (this.windSpeedList.size() == 0) {
                    this.windSpeedCanControl = false;
                    this.curWindSpeed = -1;
                    return;
                } else {
                    this.curWindSpeed = Integer.parseInt(this.windSpeedList.get(0));
                    return;
                }
            }
        }
    }

    public boolean isContainsTargetWS(int i) {
        return this.windSpeedList.indexOf(new StringBuilder(String.valueOf(i)).toString()) != -1;
    }

    public boolean isWindSpeedCanControl() {
        return this.windSpeedCanControl;
    }

    public void addExpandKey(ACExpandKey aCExpandKey) {
        this.expandKeyMap.put(Integer.valueOf(aCExpandKey.getFid()), aCExpandKey);
    }

    public ACExpandKey getExpandKeyByFid(int i) {
        return this.expandKeyMap.get(Integer.valueOf(i));
    }

    public int getModelType() {
        return this.modelType;
    }

    public int getCurTmp() {
        return this.curTmp;
    }

    public boolean isTempCanControl() {
        return this.tempCanControl;
    }

    public int getCurWindSpeed() {
        return this.curWindSpeed;
    }

    public int getLowTmp() {
        return this.lowTmp;
    }

    public void setLowTmp(int i) {
        this.lowTmp = i;
    }

    public int getHighTmp() {
        return this.highTmp;
    }

    public void setHighTmp(int i) {
        this.highTmp = i;
    }

    public List<String> getWindSpeedList() {
        return this.windSpeedList;
    }

    public void setWindSpeedList(List<String> list) {
        this.windSpeedList = list;
    }

    public void setModelType(int i) {
        this.modelType = i;
    }

    public void setCurTmp(int i) {
        this.curTmp = i;
    }

    public void setTempCanControl(boolean z) {
        this.tempCanControl = z;
    }

    public void setWindSpeedCanControl(boolean z) {
        this.windSpeedCanControl = z;
    }

    public void setCurWindSpeed(int i) {
        this.curWindSpeed = i;
    }

    public Map<Integer, ACExpandKey> getExpandKeyMap() {
        return this.expandKeyMap;
    }

    public void setExpandKeyMap(Map<Integer, ACExpandKey> map) {
        this.expandKeyMap = map;
    }
}
