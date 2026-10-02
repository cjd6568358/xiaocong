package com.hzy.tvmao.ir.ac;

import android.text.TextUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hzy.tvmao.KKACManagerV2;
import com.hzy.tvmao.utils.c;
import com.tencent.android.tpush.common.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ACStateV2 implements Serializable {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$hzy$tvmao$ir$ac$ACStateV2$UDWindDirectType = null;

    @JsonIgnore
    public static final int UDWINDDIRECT_AUTO = 0;
    private static final long serialVersionUID = 1;
    private List<ACAssociatedKey> configKeyList;
    private int curIndex;
    private int curPowerState;
    private int curUDDirect;
    private UDWindDirectType curUDDirectType;
    private List<ACModelV2> modelList;
    private Map<Integer, ACTimeKey> timeingkeyMap;
    private List<Integer> udWindDirectList;

    public enum UDWindDirectKey {
        UDDIRECT_KEY_SWING,
        UDDIRECT_KEY_FIX;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static UDWindDirectKey[] valuesCustom() {
            UDWindDirectKey[] uDWindDirectKeyArrValuesCustom = values();
            int length = uDWindDirectKeyArrValuesCustom.length;
            UDWindDirectKey[] uDWindDirectKeyArr = new UDWindDirectKey[length];
            System.arraycopy(uDWindDirectKeyArrValuesCustom, 0, uDWindDirectKeyArr, 0, length);
            return uDWindDirectKeyArr;
        }
    }

    public enum UDWindDirectType {
        UDDIRECT_ONLY_SWING,
        UDDIRECT_ONLY_FIX,
        UDDIRECT_FULL;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static UDWindDirectType[] valuesCustom() {
            UDWindDirectType[] uDWindDirectTypeArrValuesCustom = values();
            int length = uDWindDirectTypeArrValuesCustom.length;
            UDWindDirectType[] uDWindDirectTypeArr = new UDWindDirectType[length];
            System.arraycopy(uDWindDirectTypeArrValuesCustom, 0, uDWindDirectTypeArr, 0, length);
            return uDWindDirectTypeArr;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$hzy$tvmao$ir$ac$ACStateV2$UDWindDirectType() {
        int[] iArr = $SWITCH_TABLE$com$hzy$tvmao$ir$ac$ACStateV2$UDWindDirectType;
        if (iArr == null) {
            iArr = new int[UDWindDirectType.valuesCustom().length];
            try {
                iArr[UDWindDirectType.UDDIRECT_FULL.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[UDWindDirectType.UDDIRECT_ONLY_FIX.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[UDWindDirectType.UDDIRECT_ONLY_SWING.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$com$hzy$tvmao$ir$ac$ACStateV2$UDWindDirectType = iArr;
        }
        return iArr;
    }

    public ACStateV2() {
        this.modelList = new ArrayList();
        this.curPowerState = 1;
        this.udWindDirectList = new ArrayList();
        this.timeingkeyMap = new HashMap();
        this.configKeyList = new ArrayList();
    }

    public ACStateV2(Map<Integer, String> map) {
        this.modelList = new ArrayList();
        this.curPowerState = 1;
        this.udWindDirectList = new ArrayList();
        this.timeingkeyMap = new HashMap();
        this.configKeyList = new ArrayList();
        initModel(map);
        initUDDirect(map.get(Integer.valueOf(ACConstants.TAG_UD_WIND_MODE_SET)));
        initExpandKey(map.get(Integer.valueOf(ACConstants.TAG_EXPAND_KEY_FORMAT_MAP)));
        initAssociationState(map.get(Integer.valueOf(ACConstants.TAG_KEY_CANCEL_CONFIG)));
    }

    public ACStateV2(Map<Integer, String> map, int i) {
        this.modelList = new ArrayList();
        this.curPowerState = 1;
        this.udWindDirectList = new ArrayList();
        this.timeingkeyMap = new HashMap();
        this.configKeyList = new ArrayList();
        this.curPowerState = i;
        initModel(map);
        initUDDirect(map.get(Integer.valueOf(ACConstants.TAG_UD_WIND_MODE_SET)));
        initExpandKey(map.get(Integer.valueOf(ACConstants.TAG_EXPAND_KEY_FORMAT_MAP)));
        initAssociationState(map.get(Integer.valueOf(ACConstants.TAG_KEY_CANCEL_CONFIG)));
    }

    private void initModel(Map<Integer, String> map) {
        int i = ACConstants.TAG_AC_MODE_COOL_FUNCTION;
        while (true) {
            int i2 = i;
            if (i2 <= 1505) {
                String str = map.get(Integer.valueOf(i2));
                ACModelV2 aCModelV2 = new ACModelV2();
                if (TextUtils.isEmpty(str)) {
                    aCModelV2.initMoel(i2, Constants.MAIN_VERSION_TAG);
                    this.modelList.add(aCModelV2);
                } else if (!str.contains("NA")) {
                    aCModelV2.initMoel(i2, str);
                    this.modelList.add(aCModelV2);
                }
                i = i2 + 1;
            } else {
                this.curIndex = 0;
                return;
            }
        }
    }

    private void initUDDirect(String str) {
        if (TextUtils.isEmpty(str)) {
            this.curUDDirectType = UDWindDirectType.UDDIRECT_FULL;
            this.udWindDirectList.add(1);
            this.curUDDirect = 0;
            return;
        }
        int[] iArrA = c.a(str, ",");
        if (iArrA.length == 1) {
            if (iArrA[0] == 0) {
                this.curUDDirectType = UDWindDirectType.UDDIRECT_ONLY_SWING;
                this.curUDDirect = -1;
                return;
            } else {
                this.curUDDirectType = UDWindDirectType.UDDIRECT_ONLY_FIX;
                this.udWindDirectList.add(Integer.valueOf(iArrA[0]));
                this.curUDDirect = this.udWindDirectList.get(0).intValue();
                return;
            }
        }
        if (iArrA.length > 1) {
            if (iArrA[0] == 0) {
                this.curUDDirectType = UDWindDirectType.UDDIRECT_FULL;
                this.curUDDirect = 0;
                for (int i = 1; i < iArrA.length; i++) {
                    this.udWindDirectList.add(Integer.valueOf(iArrA[i]));
                }
                return;
            }
            this.curUDDirectType = UDWindDirectType.UDDIRECT_ONLY_FIX;
            for (int i2 : iArrA) {
                this.udWindDirectList.add(Integer.valueOf(i2));
            }
            this.curUDDirect = this.udWindDirectList.get(0).intValue();
        }
    }

    private void initExpandKey(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (String str2 : str.split("@")) {
                String[] strArrSplit = str2.split("\\|");
                int i = Integer.parseInt(strArrSplit[0]);
                if (i == 9 || i == 10) {
                    initTimeing(strArrSplit);
                } else {
                    initOtherKey(strArrSplit);
                }
            }
        }
    }

    private void initTimeing(String[] strArr) {
        ACTimeKey aCTimeKey = new ACTimeKey();
        aCTimeKey.setFid(Integer.parseInt(strArr[0]));
        for (String str : strArr[1].split("\\$")) {
            String[] strArrSplit = str.split("[-,]");
            int i = Integer.parseInt(strArrSplit[0]);
            int i2 = Integer.parseInt(strArrSplit[1]);
            if (strArrSplit.length == 3) {
                int i3 = Integer.parseInt(strArrSplit[2]);
                if (i == 0) {
                    i = i3;
                }
                addTimeRang(aCTimeKey, i, i2, i3);
            } else {
                if (i == 0) {
                    i = 1;
                }
                addTimeRang(aCTimeKey, i, i2, 1);
            }
        }
        aCTimeKey.setCurSetTime(0);
        aCTimeKey.setTimeDisplayValue(aCTimeKey.getTimeRangeList().get(0).intValue());
        aCTimeKey.setTimeingEndTime(0L);
        this.timeingkeyMap.put(Integer.valueOf(aCTimeKey.getFid()), aCTimeKey);
    }

    private void addTimeRang(ACTimeKey aCTimeKey, int i, int i2, int i3) {
        while (i <= i2) {
            aCTimeKey.getTimeRangeList().add(Integer.valueOf(i));
            i += i3;
        }
    }

    private void initOtherKey(String[] strArr) {
        Iterator<ACModelV2> it = this.modelList.iterator();
        while (it.hasNext()) {
            analyzeKey(strArr, it.next());
        }
    }

    private void analyzeKey(String[] strArr, ACModelV2 aCModelV2) {
        if (Integer.parseInt(strArr[0]) > 7) {
            ACExpandKey aCExpandKey = new ACExpandKey();
            aCExpandKey.setFid(Integer.parseInt(strArr[0]));
            String[] strArrSplit = strArr[1].split("[,-]");
            if (strArrSplit.length == 3) {
                aCExpandKey.setMinState(Integer.parseInt(strArrSplit[1]));
                aCExpandKey.setMaxState(Integer.parseInt(strArrSplit[2]));
                aCExpandKey.setCurState(Integer.parseInt(strArrSplit[0]));
            } else {
                aCExpandKey.setMinState(Integer.parseInt(strArrSplit[0]));
                aCExpandKey.setMaxState(Integer.parseInt(strArrSplit[1]));
                aCExpandKey.setCurState(Integer.parseInt(strArrSplit[0]));
            }
            aCExpandKey.setSupportPower(Integer.parseInt(strArr[2]));
            addSupportModel(aCExpandKey, strArr[3]);
            aCModelV2.addExpandKey(aCExpandKey);
        }
    }

    private void addSupportModel(ACExpandKey aCExpandKey, String str) {
        if (str.contains("C")) {
            aCExpandKey.getSupportModelList().add(0);
        }
        if (str.contains("H")) {
            aCExpandKey.getSupportModelList().add(1);
        }
        if (str.contains("A")) {
            aCExpandKey.getSupportModelList().add(2);
        }
        if (str.contains("F")) {
            aCExpandKey.getSupportModelList().add(3);
        }
        if (str.contains("D")) {
            aCExpandKey.getSupportModelList().add(4);
        }
    }

    private void initAssociationState(String str) {
        if (!TextUtils.isEmpty(str)) {
            for (String str2 : str.split("\\|")) {
                boolean z = str2.contains("@");
                ACAssociatedKey aCAssociatedKey = new ACAssociatedKey();
                aCAssociatedKey.setContainsPrimary(z);
                String[] strArrSplit = str2.split("[\\$@]");
                for (String str3 : strArrSplit[0].split(",")) {
                    aCAssociatedKey.getPrimaryList().add(Integer.valueOf(Integer.parseInt(str3)));
                }
                String[] strArrSplit2 = strArrSplit[1].split("[&\\*]");
                aCAssociatedKey.setConfigId(Integer.parseInt(strArrSplit2[0]));
                aCAssociatedKey.setTargetState(Integer.parseInt(strArrSplit2[2]));
                String[] strArrSplit3 = strArrSplit2[1].split("-");
                aCAssociatedKey.setMinState(Integer.parseInt(strArrSplit3[0]));
                aCAssociatedKey.setMaxState(Integer.parseInt(strArrSplit3[1]));
                this.configKeyList.add(aCAssociatedKey);
            }
        }
    }

    public void changePowerState() {
        cancleKeyFunctionByPowerChange();
        KKACManagerV2.functionId = 1;
        changeKeyStateByFunction(1);
        this.curPowerState = this.curPowerState == 1 ? 0 : 1;
    }

    public void changeModel() {
        KKACManagerV2.functionId = 2;
        changeKeyStateByFunction(2);
        this.curIndex++;
        this.curIndex = this.curIndex < this.modelList.size() ? this.curIndex : 0;
    }

    public boolean changeToTargetModel(int i) {
        int i2 = 0;
        if (!isContainsTargerModel(i)) {
            return false;
        }
        KKACManagerV2.functionId = 2;
        changeKeyStateByFunction(2);
        while (true) {
            int i3 = i2;
            if (i3 >= this.modelList.size()) {
                break;
            }
            if (i != this.modelList.get(i3).getModelType()) {
                i2 = i3 + 1;
            } else {
                this.curIndex = i3;
                break;
            }
        }
        return true;
    }

    public boolean isContainsTargerModel(int i) {
        for (int i2 = 0; i2 < this.modelList.size(); i2++) {
            if (i == this.modelList.get(i2).getModelType()) {
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public ACModelV2 getACCurModel() {
        return this.modelList.get(this.curIndex);
    }

    @JsonIgnore
    public int getCurModelType() {
        return getACCurModel().getModelType();
    }

    public boolean setTemperature(int i) {
        ACModelV2 aCCurModel = getACCurModel();
        if (!aCCurModel.isTempCanControl() || !aCCurModel.isContainsTmp(i)) {
            return false;
        }
        if (i > aCCurModel.getCurTmp()) {
            KKACManagerV2.functionId = 3;
            changeKeyStateByFunction(3);
        } else {
            KKACManagerV2.functionId = 4;
            changeKeyStateByFunction(4);
        }
        aCCurModel.setCurTmp(i);
        return true;
    }

    private void changeTemperatureToTarget(int i) {
        ACModelV2 aCCurModel = getACCurModel();
        if (aCCurModel.isTempCanControl() && aCCurModel.isContainsTmp(i)) {
            aCCurModel.setCurTmp(i);
        }
    }

    public int increaseTmp() {
        ACModelV2 aCCurModel = getACCurModel();
        if (!aCCurModel.isTempCanControl()) {
            return -1;
        }
        KKACManagerV2.functionId = 3;
        changeKeyStateByFunction(3);
        int curTmp = aCCurModel.getCurTmp() + 1;
        if (curTmp >= aCCurModel.getHighTmp()) {
            curTmp = aCCurModel.getHighTmp();
        }
        aCCurModel.setCurTmp(curTmp);
        return curTmp;
    }

    public int decreaseTmp() {
        ACModelV2 aCCurModel = getACCurModel();
        if (!aCCurModel.isTempCanControl()) {
            return -1;
        }
        KKACManagerV2.functionId = 4;
        changeKeyStateByFunction(4);
        int curTmp = aCCurModel.getCurTmp() - 1;
        if (curTmp <= aCCurModel.getLowTmp()) {
            curTmp = aCCurModel.getLowTmp();
        }
        aCCurModel.setCurTmp(curTmp);
        return curTmp;
    }

    public boolean tempIsCanControl() {
        return getACCurModel().isTempCanControl();
    }

    @JsonIgnore
    public int getCurTemp() {
        return getACCurModel().getCurTmp();
    }

    public int modelMinTemp() {
        ACModelV2 aCCurModel = getACCurModel();
        if (aCCurModel.isTempCanControl()) {
            return aCCurModel.getLowTmp();
        }
        return -1;
    }

    public int modelMaxTemp() {
        ACModelV2 aCCurModel = getACCurModel();
        if (aCCurModel.isTempCanControl()) {
            return aCCurModel.getHighTmp();
        }
        return -1;
    }

    public int changeWindSpeed() {
        ACModelV2 aCCurModel = getACCurModel();
        if (!aCCurModel.isWindSpeedCanControl()) {
            return -1;
        }
        KKACManagerV2.functionId = 5;
        changeKeyStateByFunction(5);
        int iIndexOf = aCCurModel.getWindSpeedList().indexOf(new StringBuilder(String.valueOf(aCCurModel.getCurWindSpeed())).toString()) + 1;
        List<String> windSpeedList = aCCurModel.getWindSpeedList();
        if (iIndexOf >= aCCurModel.getWindSpeedList().size()) {
            iIndexOf = 0;
        }
        int i = Integer.parseInt(windSpeedList.get(iIndexOf));
        aCCurModel.setCurWindSpeed(i);
        return i;
    }

    public boolean windSpeedIsCanControl() {
        return getACCurModel().isWindSpeedCanControl();
    }

    public boolean setTargetWindSpeed(int i) {
        ACModelV2 aCCurModel = getACCurModel();
        if (!aCCurModel.isWindSpeedCanControl() || !aCCurModel.isContainsTargetWS(i)) {
            return false;
        }
        KKACManagerV2.functionId = 5;
        changeKeyStateByFunction(5);
        aCCurModel.setCurWindSpeed(i);
        return true;
    }

    private void setTargetWindSpeedNoSend(int i) {
        ACModelV2 aCCurModel = getACCurModel();
        if (aCCurModel.isWindSpeedCanControl() && aCCurModel.isContainsTargetWS(i)) {
            aCCurModel.setCurWindSpeed(i);
        }
    }

    @JsonIgnore
    public int getCurWindSpeed() {
        return getACCurModel().getCurWindSpeed();
    }

    public void changeUDWindDirect(UDWindDirectKey uDWindDirectKey) {
        switch ($SWITCH_TABLE$com$hzy$tvmao$ir$ac$ACStateV2$UDWindDirectType()[this.curUDDirectType.ordinal()]) {
            case 2:
                if (uDWindDirectKey == UDWindDirectKey.UDDIRECT_KEY_FIX) {
                    udDirectFixChange();
                }
                break;
            case 3:
                udDirectFullChange(uDWindDirectKey);
                break;
        }
    }

    public boolean setTargetUDWindDirect(int i) {
        if (this.curUDDirectType == UDWindDirectType.UDDIRECT_ONLY_SWING) {
            return false;
        }
        if (this.curUDDirectType == UDWindDirectType.UDDIRECT_ONLY_FIX) {
            setUdDirectFix(i);
            return true;
        }
        if (this.curUDDirectType != UDWindDirectType.UDDIRECT_FULL) {
            return true;
        }
        setUdDirectFix(i);
        return true;
    }

    private void setUdDirectFix(int i) {
        if (i == 0) {
            this.curUDDirect = 0;
            KKACManagerV2.functionId = 6;
        } else {
            int iIndexOf = this.udWindDirectList.indexOf(Integer.valueOf(i));
            this.curUDDirect = this.udWindDirectList.get(iIndexOf != -1 ? iIndexOf : 0).intValue();
            KKACManagerV2.functionId = 7;
        }
    }

    private void udDirectFixChange() {
        KKACManagerV2.functionId = 7;
        changeKeyStateByFunction(7);
        int iIndexOf = this.udWindDirectList.indexOf(Integer.valueOf(this.curUDDirect)) + 1;
        List<Integer> list = this.udWindDirectList;
        if (iIndexOf >= this.udWindDirectList.size()) {
            iIndexOf = 0;
        }
        this.curUDDirect = list.get(iIndexOf).intValue();
    }

    private void udDirectFullChange(UDWindDirectKey uDWindDirectKey) {
        if (this.curUDDirect == 0) {
            KKACManagerV2.functionId = 7;
            changeKeyStateByFunction(7);
            this.curUDDirect = this.udWindDirectList.get(0).intValue();
        } else {
            if (uDWindDirectKey == UDWindDirectKey.UDDIRECT_KEY_SWING) {
                KKACManagerV2.functionId = 6;
                changeKeyStateByFunction(6);
                this.curUDDirect = 0;
                return;
            }
            udDirectFixChange();
        }
    }

    private ACExpandKey getExpandKeyById(int i) {
        return getACCurModel().getExpandKeyByFid(i);
    }

    public void changeExpandKeyState(int i) {
        ACExpandKey expandKeyById = getExpandKeyById(i);
        if (expandKeyById != null) {
            KKACManagerV2.functionId = i;
            if (expandKeyById.keyIsSingleState() || !expandKeyById.keyIsSupportClose() || expandKeyById.getCurState() == 0) {
                changeKeyStateByFunction(i);
            }
            expandKeyById.changeState(this.curPowerState, getCurModelType());
        }
    }

    public boolean isUsedAtCurPower(int i) {
        ACExpandKey expandKeyById = getExpandKeyById(i);
        if (expandKeyById == null) {
            return false;
        }
        return expandKeyById.isUsedAtPower(this.curPowerState);
    }

    public boolean isExpandKeyCanUse(int i) {
        ACExpandKey expandKeyById = getExpandKeyById(i);
        if (expandKeyById == null) {
            return false;
        }
        return expandKeyById.isCanUsed(this.curPowerState, getCurModelType());
    }

    public boolean isUsedAtCurModel(int i) {
        ACExpandKey expandKeyById = getExpandKeyById(i);
        if (expandKeyById == null) {
            return false;
        }
        return expandKeyById.isSupportModel(getCurModelType());
    }

    public List<Integer> getExpandKeySupportModel(int i) {
        ACExpandKey expandKeyById = getExpandKeyById(i);
        if (expandKeyById == null) {
            return null;
        }
        return expandKeyById.getSupportModelList();
    }

    public int getExpandKeyState(int i) {
        ACExpandKey expandKeyById = getExpandKeyById(i);
        if (expandKeyById == null) {
            return -1;
        }
        return expandKeyById.getExpandKeyState(this.curPowerState, getCurModelType());
    }

    public boolean isExpandKeyClosed(int i) {
        ACExpandKey expandKeyById = getExpandKeyById(i);
        if (expandKeyById == null) {
            return true;
        }
        return expandKeyById.keyIsSupportClose();
    }

    public boolean isMoreTwoStateKey(int i) {
        ACExpandKey expandKeyById = getExpandKeyById(i);
        if (expandKeyById == null) {
            return false;
        }
        return expandKeyById.keyIsManyState();
    }

    public boolean isSingleStateKey(int i) {
        ACExpandKey expandKeyById = getExpandKeyById(i);
        if (expandKeyById == null) {
            return false;
        }
        return expandKeyById.keyIsSingleState();
    }

    @JsonIgnore
    public Map<Integer, ACExpandKey> getExpandKeysMap() {
        return getACCurModel().getExpandKeyMap();
    }

    @JsonIgnore
    public ACTimeKey getCurTimeKey() {
        return this.curPowerState == 0 ? this.timeingkeyMap.get(10) : this.timeingkeyMap.get(9);
    }

    public boolean timeingIsCanUse() {
        if (this.timeingkeyMap.size() == 0) {
            return false;
        }
        if (getCurPowerState() == 1) {
            return this.timeingkeyMap.containsKey(9);
        }
        if (getCurPowerState() == 0) {
            return this.timeingkeyMap.containsKey(10);
        }
        return false;
    }

    public boolean timingOnOrOffCanUse(int i) {
        if (this.timeingkeyMap.size() == 0) {
            return false;
        }
        if (i == 9) {
            if (getCurPowerState() != 0) {
                return this.timeingkeyMap.containsKey(9);
            }
            return false;
        }
        if (i != 10 || getCurPowerState() == 1) {
            return false;
        }
        return this.timeingkeyMap.containsKey(10);
    }

    public boolean timeIsHsBeenSet() {
        if (!timeingIsCanUse()) {
            return false;
        }
        return getCurTimeKey().timeIsHaveRegular();
    }

    public void operateTimeing(int i) {
        ACTimeKey curTimeKey;
        if (i != -1) {
            if (timingOnOrOffCanUse(i)) {
                curTimeKey = this.timeingkeyMap.get(Integer.valueOf(i));
            } else {
                return;
            }
        } else if (timeingIsCanUse()) {
            curTimeKey = getCurTimeKey();
        } else {
            return;
        }
        KKACManagerV2.functionId = curTimeKey.getFid();
        if (curTimeKey.timeIsHaveRegular()) {
            curTimeKey.cancleTimeing();
        } else {
            changeKeyStateByFunction(curTimeKey.getFid());
            curTimeKey.addTimeing();
        }
    }

    private void cancleTimeing() {
        if (timeingIsCanUse()) {
            getCurTimeKey().cancleTimeing();
        }
    }

    @JsonIgnore
    public int getDisplayTime(int i) {
        ACTimeKey curTimeKey;
        if (i != -1) {
            if (!timingOnOrOffCanUse(i)) {
                return -1;
            }
            curTimeKey = this.timeingkeyMap.get(Integer.valueOf(i));
        } else {
            if (!timeingIsCanUse()) {
                return -1;
            }
            curTimeKey = getCurTimeKey();
        }
        return curTimeKey.getTimeDisplayValue();
    }

    public synchronized int increaseTime(int i) {
        ACTimeKey curTimeKey;
        int iIncreaseTime = -1;
        synchronized (this) {
            try {
                if (i != -1) {
                    if (timingOnOrOffCanUse(i)) {
                        curTimeKey = this.timeingkeyMap.get(Integer.valueOf(i));
                        iIncreaseTime = curTimeKey.increaseTime();
                    }
                } else if (timeingIsCanUse()) {
                    curTimeKey = getCurTimeKey();
                    iIncreaseTime = curTimeKey.increaseTime();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iIncreaseTime;
    }

    public synchronized int decreaseTime(int i) {
        ACTimeKey curTimeKey;
        int iDecreaseTime = -1;
        synchronized (this) {
            try {
                if (i != -1) {
                    if (timingOnOrOffCanUse(i)) {
                        curTimeKey = this.timeingkeyMap.get(Integer.valueOf(i));
                        iDecreaseTime = curTimeKey.decreaseTime();
                    }
                } else if (timeingIsCanUse()) {
                    curTimeKey = getCurTimeKey();
                    iDecreaseTime = curTimeKey.decreaseTime();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iDecreaseTime;
    }

    @JsonIgnore
    public long getTimeingEndTime(int i) {
        ACTimeKey curTimeKey;
        if (i != -1) {
            if (!timingOnOrOffCanUse(i)) {
                return 0L;
            }
            curTimeKey = this.timeingkeyMap.get(Integer.valueOf(i));
        } else {
            if (!timeingIsCanUse()) {
                return 0L;
            }
            curTimeKey = getCurTimeKey();
        }
        return curTimeKey.getTimeingEndTime();
    }

    public int cutTimeing() {
        if (!timeingIsCanUse()) {
            return -1;
        }
        return getCurTimeKey().cutTimeing();
    }

    public void timeingCheck() {
        if (timeingIsCanUse()) {
            getCurTimeKey().timingCheck();
        }
    }

    private void cancleKeyFunctionByPowerChange() {
        cancleTimeing();
        ACExpandKey expandKeyById = getExpandKeyById(22);
        if (expandKeyById != null && expandKeyById.isCanUsed(this.curPowerState, getCurModelType())) {
            expandKeyById.changeToTargetState(0);
        }
    }

    private void changeKeyStateByFunction(int i) {
        if (this.configKeyList.size() != 0) {
            for (ACAssociatedKey aCAssociatedKey : this.configKeyList) {
                if (aCAssociatedKey.isContainsPrimary() && aCAssociatedKey.isWithinPryKeyScope(i) && i != aCAssociatedKey.getConfigId()) {
                    if (aCAssociatedKey.getConfigId() == 10 || aCAssociatedKey.getConfigId() == 9) {
                        ACTimeKey curTimeKey = getCurTimeKey();
                        if (curTimeKey != null && aCAssociatedKey.isWithinScope(curTimeKey.getCurSetTime())) {
                            curTimeKey.cancleTimeing();
                        }
                    } else if (aCAssociatedKey.getConfigId() == 5 && aCAssociatedKey.isWithinScope(getACCurModel().getCurWindSpeed())) {
                        setTargetWindSpeedNoSend(aCAssociatedKey.getTargetState());
                    } else if (aCAssociatedKey.getConfigId() == 3) {
                        changeTemperatureToTarget(aCAssociatedKey.getTargetState());
                    } else {
                        ACExpandKey expandKeyByFid = getACCurModel().getExpandKeyByFid(aCAssociatedKey.getConfigId());
                        if (expandKeyByFid != null && aCAssociatedKey.isWithinScope(expandKeyByFid.getCurState())) {
                            expandKeyByFid.changeToTargetState(aCAssociatedKey.getTargetState());
                        }
                    }
                } else if (!aCAssociatedKey.isContainsPrimary() && !aCAssociatedKey.isWithinPryKeyScope(i) && i != aCAssociatedKey.getConfigId()) {
                    if (aCAssociatedKey.getConfigId() == 10 || aCAssociatedKey.getConfigId() == 9) {
                        ACTimeKey curTimeKey2 = getCurTimeKey();
                        if (curTimeKey2 != null && aCAssociatedKey.isWithinScope(curTimeKey2.getCurSetTime())) {
                            curTimeKey2.cancleTimeing();
                        }
                    } else if (aCAssociatedKey.getConfigId() == 5 && aCAssociatedKey.isWithinScope(getACCurModel().getCurWindSpeed())) {
                        setTargetWindSpeedNoSend(aCAssociatedKey.getTargetState());
                    } else if (aCAssociatedKey.getConfigId() == 3 && i != 4) {
                        changeTemperatureToTarget(aCAssociatedKey.getTargetState());
                    } else {
                        ACExpandKey expandKeyByFid2 = getACCurModel().getExpandKeyByFid(aCAssociatedKey.getConfigId());
                        if (expandKeyByFid2 != null && expandKeyByFid2 != null && aCAssociatedKey.isWithinScope(expandKeyByFid2.getCurState())) {
                            expandKeyByFid2.changeToTargetState(aCAssociatedKey.getTargetState());
                        }
                    }
                }
            }
        }
    }

    public Map<Integer, ACTimeKey> getTimeingkeyMap() {
        return this.timeingkeyMap;
    }

    public void setTimeingkeyMap(Map<Integer, ACTimeKey> map) {
        this.timeingkeyMap = map;
    }

    public int getCurPowerState() {
        return this.curPowerState;
    }

    public int getCurUDDirect() {
        return this.curUDDirect;
    }

    public UDWindDirectType getCurUDDirectType() {
        return this.curUDDirectType;
    }

    public List<ACModelV2> getModelList() {
        return this.modelList;
    }

    public void setModelList(List<ACModelV2> list) {
        this.modelList = list;
    }

    public List<Integer> getUdWindDirectList() {
        return this.udWindDirectList;
    }

    public void setUdWindDirectList(List<Integer> list) {
        this.udWindDirectList = list;
    }

    public void setCurPowerState(int i) {
        this.curPowerState = i;
    }

    public void setCurUDDirect(int i) {
        this.curUDDirect = i;
    }

    public void setCurUDDirectType(UDWindDirectType uDWindDirectType) {
        this.curUDDirectType = uDWindDirectType;
    }

    public List<ACAssociatedKey> getConfigKeyList() {
        return this.configKeyList;
    }

    public void setConfigKeyList(List<ACAssociatedKey> list) {
        this.configKeyList = list;
    }

    public int getCurIndex() {
        return this.curIndex;
    }

    public void setCurIndex(int i) {
        this.curIndex = i;
    }
}
