package com.hzy.tvmao;

import android.text.TextUtils;
import android.util.SparseIntArray;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.hzy.tvmao.ir.ac.ACExpandKey;
import com.hzy.tvmao.ir.ac.ACModelV2;
import com.hzy.tvmao.ir.ac.ACStateV2;
import com.hzy.tvmao.ir.ac.ACTimeKey;
import com.hzy.tvmao.utils.LogUtil;
import com.kookong.app.data.IrData;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BaseACManager {
    public static int functionId;
    public static List<Integer> upgradeList = new ArrayList();
    protected ACStateV2 mAcStateV2;
    protected HashMap<Integer, String> mExtMap;
    protected ArrayList<IrData.IrKey> mKeys;
    protected int mRemoteId;

    public void initIRData(int i, HashMap<Integer, String> map, ArrayList<IrData.IrKey> arrayList) {
        this.mRemoteId = i;
        this.mExtMap = map;
        this.mKeys = arrayList;
    }

    protected Map<Integer, Map<Integer, String>> createExpandKeyMap(ArrayList<IrData.IrKey> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        HashMap map = new HashMap();
        for (IrData.IrKey irKey : arrayList) {
            map.put(Integer.valueOf(irKey.fid), irKey.exts);
        }
        if (map.size() == 0) {
            return null;
        }
        return map;
    }

    public String getACStateV2InString() {
        return com.hzy.tvmao.utils.a.a(this.mAcStateV2);
    }

    public void setACStateV2FromString(String str) {
        if (!TextUtils.isEmpty(str)) {
            LogUtil.d("ACManagerV2: reading acstate!");
            try {
                this.mAcStateV2 = (ACStateV2) com.hzy.tvmao.utils.a.a(ACStateV2.class, str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.mAcStateV2 == null) {
            LogUtil.d("ACManagerV2: initizalizing acstate!");
            this.mAcStateV2 = new ACStateV2(this.mExtMap);
        }
    }

    public boolean stateIsEmpty() {
        return this.mAcStateV2 == null;
    }

    public void changePowerState() {
        this.mAcStateV2.changePowerState();
    }

    public int getPowerState() {
        return this.mAcStateV2.getCurPowerState();
    }

    public ACModelV2 getACCurModel() {
        return this.mAcStateV2.getACCurModel();
    }

    public int getCurModelType() {
        return this.mAcStateV2.getCurModelType();
    }

    public void changeACModel() {
        this.mAcStateV2.changeModel();
    }

    public boolean changeACTargetModel(int i) {
        return this.mAcStateV2.changeToTargetModel(i);
    }

    public boolean isContainsTargetModel(int i) {
        return this.mAcStateV2.isContainsTargerModel(i);
    }

    public ACStateV2.UDWindDirectType getCurUDDirectType() {
        return this.mAcStateV2.getCurUDDirectType();
    }

    public int getCurUDDirect() {
        return this.mAcStateV2.getCurUDDirect();
    }

    public void changeUDWindDirect(ACStateV2.UDWindDirectKey uDWindDirectKey) {
        this.mAcStateV2.changeUDWindDirect(uDWindDirectKey);
    }

    public boolean setTargetUDWindDirect(int i) {
        return this.mAcStateV2.setTargetUDWindDirect(i);
    }

    public List<Integer> getUDWindDirectList() {
        return this.mAcStateV2.getUdWindDirectList();
    }

    public boolean isTempCanControl() {
        return this.mAcStateV2.tempIsCanControl();
    }

    public int getCurTemp() {
        return this.mAcStateV2.getCurTemp();
    }

    public int increaseTmp() {
        return this.mAcStateV2.increaseTmp();
    }

    public int decreaseTmp() {
        return this.mAcStateV2.decreaseTmp();
    }

    public boolean setTargetTemp(int i) {
        return this.mAcStateV2.setTemperature(i);
    }

    public int getMinTemp() {
        return this.mAcStateV2.modelMinTemp();
    }

    public int getMaxTemp() {
        return this.mAcStateV2.modelMaxTemp();
    }

    public boolean isWindSpeedCanControl() {
        return this.mAcStateV2.windSpeedIsCanControl();
    }

    public int getCurWindSpeed() {
        return this.mAcStateV2.getCurWindSpeed();
    }

    public int changeWindSpeed() {
        return this.mAcStateV2.changeWindSpeed();
    }

    public boolean setTargetWindSpeed(int i) {
        return this.mAcStateV2.setTargetWindSpeed(i);
    }

    public ArrayList<IrData.IrKey> getExpandKeys(ArrayList<IrData.IrKey> arrayList) {
        ACExpandKey aCExpandKey;
        ArrayList<IrData.IrKey> arrayList2 = new ArrayList<>();
        Map<Integer, ACExpandKey> expandKeyMap = getACCurModel().getExpandKeyMap();
        if (arrayList != null && arrayList.size() > 0 && expandKeyMap.size() > 0) {
            ArrayList<IrData.IrKey> arrayList3 = new ArrayList<>();
            for (IrData.IrKey irKey : arrayList) {
                if (irKey.fid != 22 && irKey.fid != 9 && irKey.fid != 10 && (aCExpandKey = expandKeyMap.get(Integer.valueOf(irKey.fid))) != null && aCExpandKey.getSupportModelList().size() != 0) {
                    arrayList3.add(irKey);
                }
            }
            return arrayList3;
        }
        return arrayList2;
    }

    public boolean isExpandCanUse(int i) {
        return (i == 10 || i == 9) ? this.mAcStateV2.timingOnOrOffCanUse(i) : this.mAcStateV2.isExpandKeyCanUse(i);
    }

    public boolean isUsedAtCurPower(int i) {
        return this.mAcStateV2.isUsedAtCurPower(i);
    }

    public boolean isUsedAcCurModel(int i) {
        return this.mAcStateV2.isUsedAtCurModel(i);
    }

    public int getExpandKeyState(int i) {
        return this.mAcStateV2.getExpandKeyState(i);
    }

    public void changeExpandKeyState(int i) {
        this.mAcStateV2.changeExpandKeyState(i);
    }

    public boolean isExpandKeyCanClose(int i) {
        return this.mAcStateV2.isExpandKeyClosed(i);
    }

    public boolean isSingleStateKey(int i) {
        return this.mAcStateV2.isSingleStateKey(i);
    }

    public boolean isMoreTwoStateKey(int i) {
        return this.mAcStateV2.isMoreTwoStateKey(i);
    }

    public List<Integer> getExpandKeySupportModel(int i) {
        return this.mAcStateV2.getExpandKeySupportModel(i);
    }

    public boolean isTimeingCanUse() {
        return this.mAcStateV2.timeingIsCanUse();
    }

    public boolean isHsBeenSet() {
        return this.mAcStateV2.timeIsHsBeenSet();
    }

    public void operateTimeing(int i) {
        this.mAcStateV2.operateTimeing(i);
    }

    public int getDisplayTime(int i) {
        return this.mAcStateV2.getDisplayTime(i);
    }

    public int increaseTime(int i) {
        return this.mAcStateV2.increaseTime(i);
    }

    public int decreaseTime(int i) {
        return this.mAcStateV2.decreaseTime(i);
    }

    public long getTimeingEndTime(int i) {
        return this.mAcStateV2.getTimeingEndTime(i);
    }

    public void timeingCheck() {
        this.mAcStateV2.timeingCheck();
    }

    protected SparseIntArray getAllExpandKeyState() {
        SparseIntArray sparseIntArray = new SparseIntArray();
        Iterator<Map.Entry<Integer, ACExpandKey>> it = this.mAcStateV2.getExpandKeysMap().entrySet().iterator();
        while (it.hasNext()) {
            ACExpandKey value = it.next().getValue();
            if (!value.keyIsSingleState() || functionId == value.getFid()) {
                int expandKeyState = value.getExpandKeyState(getPowerState(), this.mAcStateV2.getCurModelType());
                if (expandKeyState != -1) {
                    sparseIntArray.append(value.getFid(), expandKeyState);
                }
            }
        }
        if (this.mAcStateV2.timeingIsCanUse()) {
            ACTimeKey curTimeKey = this.mAcStateV2.getCurTimeKey();
            int curSetTime = curTimeKey.getCurSetTime();
            if (functionId != 10 && functionId != 9) {
                curSetTime = this.mAcStateV2.cutTimeing();
            }
            sparseIntArray.append(curTimeKey.getFid(), curSetTime);
        }
        return sparseIntArray;
    }

    protected String getStringByExpandKey(SparseIntArray sparseIntArray) {
        if (sparseIntArray == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < sparseIntArray.size(); i++) {
            int iKeyAt = sparseIntArray.keyAt(i);
            stringBuffer.append(iKeyAt).append("=").append(sparseIntArray.get(iKeyAt)).append("\n");
        }
        return stringBuffer.toString();
    }

    public boolean isUpgradeIR(IrData irData) {
        return upgradeList.contains(Integer.valueOf(irData.rid)) && irData.exts.containsKey(Integer.valueOf(ACConstants.TAG_CUSTOMIZED_BEANSHELL_SCRIPT)) && !irData.exts.containsKey(Integer.valueOf(ACConstants.TAG_CUSTOMIZED_LUA_SCRIPT));
    }

    static {
        upgradeList.add(2);
        upgradeList.add(2607);
        upgradeList.add(2877);
        upgradeList.add(6502);
        upgradeList.add(10727);
        upgradeList.add(10737);
        upgradeList.add(11672);
        upgradeList.add(11707);
        upgradeList.add(11717);
        upgradeList.add(11772);
        upgradeList.add(11862);
        upgradeList.add(11867);
        upgradeList.add(12250);
    }
}
