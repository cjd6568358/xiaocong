package com.hzy.tvmao.ir.encode;

import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import bsh.Interpreter;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.hzy.tvmao.utils.c;
import com.luajava.LuaState;
import com.luajava.LuaStateFactory;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ZipCodeHelper {
    private static final SparseIntArray refMap;
    private int frequency;
    private final SparseArray<FormatParam> keyFormatMap;
    private final FormatParam param;
    private boolean released = false;
    private final int remoteId;

    static {
        System.loadLibrary("kksdk");
        refMap = new SparseIntArray();
    }

    private static class KeyFormat {
        int functionId;
        int[][] status;

        private KeyFormat() {
        }

        /* synthetic */ KeyFormat(KeyFormat keyFormat) {
            this();
        }
    }

    private static class FormatParam {
        private String beanshellScript;
        private byte[] formateParam;
        private SparseArray<List<KeyFormat>> keyKeyMap;
        private String script;
        private SparseArray<List<KeyFormat>> statusKeyMap;
        private Map<String, byte[]> waveCodeMap;

        private FormatParam() {
        }

        /* synthetic */ FormatParam(FormatParam formatParam) {
            this();
        }
    }

    public int getRemoteId() {
        return this.remoteId;
    }

    public int getFrequency() {
        return this.frequency;
    }

    private SparseArray<List<KeyFormat>> getKeyKeyMap(FormatParam formatParam) {
        if (formatParam.keyKeyMap == null) {
            formatParam.keyKeyMap = new SparseArray();
        }
        return formatParam.keyKeyMap;
    }

    private SparseArray<List<KeyFormat>> getStatusKeyMap(FormatParam formatParam) {
        if (formatParam.statusKeyMap == null) {
            formatParam.statusKeyMap = new SparseArray();
        }
        return formatParam.statusKeyMap;
    }

    private void addKeyFormat(SparseArray<List<KeyFormat>> sparseArray, int i, KeyFormat keyFormat) {
        List<KeyFormat> arrayList = sparseArray.get(i);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            sparseArray.put(i, arrayList);
        }
        arrayList.add(keyFormat);
    }

    private void sortKeyFormatList(SparseArray<List<KeyFormat>> sparseArray) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < sparseArray.size()) {
                Collections.sort(sparseArray.valueAt(i2), new Comparator<KeyFormat>() { // from class: com.hzy.tvmao.ir.encode.ZipCodeHelper.1
                    @Override // java.util.Comparator
                    public int compare(KeyFormat keyFormat, KeyFormat keyFormat2) {
                        if (keyFormat.status != null) {
                            return -1;
                        }
                        if (keyFormat2.status != null) {
                            return 1;
                        }
                        return 0;
                    }
                });
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    private FormatParam getFormatParam(Map<Integer, String> map, List<String> list) {
        FormatParam formatParam = new FormatParam(null);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            int iIntValue = entry.getKey().intValue();
            String value = entry.getValue();
            switch (iIntValue) {
                case ACConstants.TAG_CUSTOMIZED_WAVE_CODE /* 1510 */:
                    if (!map.containsKey(Integer.valueOf(ACConstants.TAG_CUSTOMIZED_WAVE_CODE2))) {
                        formatParam.waveCodeMap = new HashMap();
                        int i = 1000000 / this.frequency;
                        for (String str : value.trim().split("\\|")) {
                            String strTrim = str.trim();
                            int iIndexOf = strTrim.indexOf(38);
                            String strTrim2 = strTrim.substring(0, iIndexOf).trim();
                            int iIndexOf2 = strTrim.indexOf(38, iIndexOf + 1);
                            String strSubstring = strTrim.substring(iIndexOf + 1, iIndexOf2);
                            int[] iArrA = c.a(strTrim.substring(iIndexOf2 + 1), ",");
                            byte[] bArr = new byte[(iArrA.length * 2) + 1];
                            bArr[0] = 0;
                            for (int i2 = 0; i2 < iArrA.length; i2++) {
                                int i3 = iArrA[i2] / i;
                                bArr[(i2 * 2) + 1] = (byte) (i3 >> 8);
                                bArr[(i2 * 2) + 1 + 1] = (byte) (i3 & 255);
                            }
                            for (String str2 : strSubstring.split(",")) {
                                formatParam.waveCodeMap.put(String.valueOf(strTrim2) + "&" + str2, bArr);
                            }
                        }
                    }
                    break;
                case ACConstants.TAG_CUSTOMIZED_BEANSHELL_SCRIPT /* 1511 */:
                    formatParam.beanshellScript = value.trim();
                    break;
                case ACConstants.TAG_CUSTOMIZED_WAVE_CODE2 /* 1514 */:
                    formatParam.waveCodeMap = new HashMap();
                    for (String str3 : value.trim().split("\\|")) {
                        String strTrim3 = str3.trim();
                        int iIndexOf3 = strTrim3.indexOf(38);
                        String strTrim4 = strTrim3.substring(0, iIndexOf3).trim();
                        int iIndexOf4 = strTrim3.indexOf(38, iIndexOf3 + 1);
                        String strSubstring2 = strTrim3.substring(iIndexOf3 + 1, iIndexOf4);
                        byte[] bArrD = c.d(strTrim3.substring(iIndexOf4 + 1));
                        for (String str4 : strSubstring2.split(",")) {
                            formatParam.waveCodeMap.put(String.valueOf(strTrim4) + "&" + str4, bArrD);
                        }
                    }
                    break;
                case ACConstants.TAG_FUNCTION_FORMAT_MAP /* 1516 */:
                    for (String str5 : value.trim().split("\\|")) {
                        String strTrim5 = str5.trim();
                        char cCharAt = 0;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= strTrim5.length()) {
                                i4 = 0;
                            } else {
                                cCharAt = strTrim5.charAt(i4);
                                if (cCharAt != '&' && cCharAt != '@') {
                                    i4++;
                                }
                            }
                        }
                        int i5 = Integer.parseInt(strTrim5.substring(0, i4).trim());
                        int iIndexOf5 = strTrim5.indexOf(38, i4 + 1);
                        String strSubstring3 = strTrim5.substring(i4 + 1, iIndexOf5);
                        int i6 = Integer.parseInt(strTrim5.substring(iIndexOf5 + 1).trim());
                        KeyFormat keyFormat = new KeyFormat(null);
                        keyFormat.functionId = i6;
                        if (strSubstring3.length() > 0) {
                            String[] strArrSplit = strSubstring3.split("$");
                            keyFormat.status = new int[strArrSplit.length][];
                            for (int i7 = 0; i7 < strArrSplit.length; i7++) {
                                String str6 = strArrSplit[i7];
                                int iIndexOf6 = str6.indexOf(45);
                                int iIndexOf7 = str6.indexOf(44, iIndexOf6);
                                keyFormat.status[i7] = new int[]{Integer.parseInt(str6.substring(0, iIndexOf6)), Integer.parseInt(str6.substring(iIndexOf6 + 1, iIndexOf7 > 0 ? iIndexOf7 : str6.length())), iIndexOf7 > 0 ? Integer.parseInt(str6.substring(iIndexOf7 + 1)) : 1};
                            }
                        }
                        addKeyFormat(cCharAt == '&' ? getKeyKeyMap(formatParam) : getStatusKeyMap(formatParam), i5, keyFormat);
                    }
                    if (formatParam.keyKeyMap != null) {
                        sortKeyFormatList(formatParam.keyKeyMap);
                    }
                    if (formatParam.statusKeyMap != null) {
                        sortKeyFormatList(formatParam.statusKeyMap);
                    }
                    break;
                case ACConstants.TAG_CUSTOMIZED_LUA_SCRIPT /* 1518 */:
                    formatParam.script = value.trim();
                    break;
                case ACConstants.TAG_REMOTE_PARAMS /* 99999 */:
                    formatParam.formateParam = c.d(value);
                    break;
                default:
                    if (list != null && iIntValue > 1000 && iIntValue < 1501) {
                        list.add(String.valueOf(iIntValue) + "|" + value.trim());
                    }
                    break;
            }
        }
        if (formatParam.script != null) {
            formatParam.beanshellScript = null;
        }
        return formatParam;
    }

    public ZipCodeHelper(int i, int i2, Map<Integer, String> map, Map<Integer, Map<Integer, String>> map2) {
        int i3;
        this.remoteId = i;
        this.frequency = i2 / 10;
        this.frequency *= 10;
        ArrayList arrayList = new ArrayList();
        this.param = getFormatParam(map, arrayList);
        if (map2 != null) {
            this.keyFormatMap = new SparseArray<>(map2.size());
            for (Map.Entry<Integer, Map<Integer, String>> entry : map2.entrySet()) {
                Map<Integer, String> value = entry.getValue();
                if (value.size() > 0) {
                    this.keyFormatMap.put(entry.getKey().intValue(), getFormatParam(value, null));
                }
            }
        } else {
            this.keyFormatMap = null;
        }
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        synchronized (refMap) {
            i3 = refMap.get(i) + 1;
            refMap.put(i, i3);
        }
        if (i3 == 1) {
            CodeHelper.initRemote(i, 1, strArr);
        }
    }

    private byte[] addLeadZero(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length + 1];
        bArr2[0] = 0;
        System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        return bArr2;
    }

    /* JADX WARN: Code duplicated, block: B:135:0x0426  */
    public byte[] getKeyIr(int i, int i2, int i3, int i4, int i5, int i6, int i7, byte[] bArr, SparseIntArray sparseIntArray) {
        byte[] bArr2;
        FormatParam formatParam;
        int i8;
        int i9;
        List<KeyFormat> list;
        int i10;
        String string = null;
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        if (sparseIntArray != null && sparseIntArray.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i11 = 0; i11 < sparseIntArray.size(); i11++) {
                int iKeyAt = sparseIntArray.keyAt(i11);
                int i12 = sparseIntArray.get(iKeyAt);
                sparseIntArray2.append(iKeyAt, i12);
                sb.append(iKeyAt).append(',').append(i12).append('|');
            }
            sb.setLength(sb.length() - 1);
            string = sb.toString();
        }
        sparseIntArray2.append(1, i);
        sparseIntArray2.append(2, i2);
        sparseIntArray2.append(3, i3);
        sparseIntArray2.append(4, i3);
        sparseIntArray2.append(5, i4);
        sparseIntArray2.append(7, i6);
        sparseIntArray2.append(6, i6);
        int i13 = sparseIntArray2.get(i7, -1);
        if (this.param.waveCodeMap != null && this.param.waveCodeMap.size() > 0) {
            byte[] bArr3 = (byte[]) this.param.waveCodeMap.get(String.valueOf(i7) + "&" + i13);
            if (bArr3 == null) {
                bArr3 = (byte[]) this.param.waveCodeMap.get(String.valueOf(i7) + "&");
            }
            if (bArr3 != null) {
                return bArr3;
            }
        }
        byte[][] bArrEnc = CodeHelper.enc(this.remoteId, i, i2, i3, i4, i5, i6, i7, bArr, string);
        byte[] bArr4 = bArrEnc[0];
        if (this.param.script == null || this.param.script.length() <= 0) {
            if (this.param.beanshellScript == null || this.param.beanshellScript.length() <= 0) {
                bArr2 = bArr4;
            } else {
                Interpreter interpreter = new Interpreter();
                try {
                    interpreter.set("bytes", bArrEnc[0]);
                    interpreter.set("power", i);
                    interpreter.set("mode", i2);
                    interpreter.set("temperature", i3);
                    interpreter.set("windSpeed", i4);
                    interpreter.set("udWindMode", i6);
                    interpreter.set("functionId", i7);
                    interpreter.eval(this.param.beanshellScript);
                    bArr2 = bArr4;
                } catch (Exception e) {
                    Log.e("CodeHelper", "evaluate script failed for remote " + this.remoteId, e);
                    bArr2 = bArr4;
                }
            }
        } else {
            LuaState luaStateNewLuaState = LuaStateFactory.newLuaState();
            try {
                try {
                    luaStateNewLuaState.openLibs();
                    luaStateNewLuaState.newTable();
                    for (int i14 = 0; i14 < bArr4.length; i14++) {
                        luaStateNewLuaState.pushNumber(bArr4[i14] & Constants.NETWORK_TYPE_UNCONNECTED);
                        luaStateNewLuaState.rawSetI(-2, i14 + 1);
                    }
                    luaStateNewLuaState.setGlobal("bytes");
                    luaStateNewLuaState.pushNumber(i);
                    luaStateNewLuaState.setGlobal("power");
                    luaStateNewLuaState.pushNumber(i2);
                    luaStateNewLuaState.setGlobal("mode");
                    luaStateNewLuaState.pushNumber(i3);
                    luaStateNewLuaState.setGlobal("temperature");
                    luaStateNewLuaState.pushNumber(i4);
                    luaStateNewLuaState.setGlobal("windSpeed");
                    luaStateNewLuaState.pushNumber(i6);
                    luaStateNewLuaState.setGlobal("udWindMode");
                    luaStateNewLuaState.pushNumber(i7);
                    luaStateNewLuaState.setGlobal("functionId");
                    if (sparseIntArray != null && sparseIntArray.size() > 0) {
                        luaStateNewLuaState.newTable();
                        for (int i15 = 0; i15 < sparseIntArray.size(); i15++) {
                            int iKeyAt2 = sparseIntArray.keyAt(i15);
                            int i16 = sparseIntArray.get(iKeyAt2);
                            luaStateNewLuaState.pushNumber(iKeyAt2);
                            luaStateNewLuaState.pushNumber(i16);
                            luaStateNewLuaState.rawSet(-3);
                        }
                        luaStateNewLuaState.setGlobal("exts");
                    }
                    int iLdoString = luaStateNewLuaState.LdoString(this.param.script);
                    if (iLdoString == 0) {
                        luaStateNewLuaState.getGlobal("bytes");
                        luaStateNewLuaState.pushNil();
                        ArrayList arrayList = new ArrayList();
                        while (luaStateNewLuaState.next(-2) != 0) {
                            arrayList.add(new int[]{(int) luaStateNewLuaState.toInteger(-2), (int) luaStateNewLuaState.toInteger(-1)});
                            luaStateNewLuaState.pop(1);
                        }
                        Collections.sort(arrayList, new Comparator<int[]>() { // from class: com.hzy.tvmao.ir.encode.ZipCodeHelper.2
                            @Override // java.util.Comparator
                            public int compare(int[] iArr, int[] iArr2) {
                                return iArr[0] - iArr2[0];
                            }
                        });
                        byte[] bArr5 = new byte[arrayList.size()];
                        int i17 = 0;
                        while (true) {
                            try {
                                int i18 = i17;
                                if (i18 >= arrayList.size()) {
                                    break;
                                }
                                bArr5[i18] = (byte) ((int[]) arrayList.get(i18))[1];
                                i17 = i18 + 1;
                            } catch (Exception e2) {
                                bArr4 = bArr5;
                                e = e2;
                                Log.e("CodeHelper", "evaluate script failed for remote " + this.remoteId, e);
                                luaStateNewLuaState.close();
                                bArr2 = bArr4;
                            }
                        }
                        bArr4 = bArr5;
                    } else {
                        Log.e("CodeHelper", "evaluate script return error (" + iLdoString + ") for remote " + this.remoteId);
                    }
                    luaStateNewLuaState.close();
                    bArr2 = bArr4;
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Throwable th) {
                luaStateNewLuaState.close();
                throw th;
            }
        }
        FormatParam formatParam2 = this.param;
        if (this.keyFormatMap != null) {
            int i19 = -1;
            if (this.param.keyKeyMap != null && (list = (List) this.param.keyKeyMap.get(i7)) != null) {
                for (KeyFormat keyFormat : list) {
                    if (keyFormat.status != null) {
                        int[][] iArr = keyFormat.status;
                        int length = iArr.length;
                        int i20 = 0;
                        while (true) {
                            if (i20 >= length) {
                                i10 = i19;
                                break;
                            }
                            int[] iArr2 = iArr[i20];
                            if (iArr2[0] > i13 || iArr2[1] < i13 || (i13 - iArr2[0]) % iArr2[2] != 0) {
                                i20++;
                            } else {
                                i10 = keyFormat.functionId;
                                break;
                            }
                        }
                    } else {
                        i10 = keyFormat.functionId;
                    }
                    if (i10 >= 0) {
                        i19 = i10;
                        break;
                    }
                    i19 = i10;
                }
            }
            if (i19 >= 0 || this.param.statusKeyMap == null) {
                i8 = i19;
            } else {
                int i21 = 0;
                i8 = i19;
                while (true) {
                    int i22 = i21;
                    if (i22 >= this.param.statusKeyMap.size()) {
                        break;
                    }
                    int iKeyAt3 = this.param.statusKeyMap.keyAt(i22);
                    List<KeyFormat> list2 = (List) this.param.statusKeyMap.get(iKeyAt3);
                    if (list2 != null && (i9 = sparseIntArray2.get(iKeyAt3, -1)) >= 0) {
                        for (KeyFormat keyFormat2 : list2) {
                            if (keyFormat2.status != null) {
                                for (int[] iArr3 : keyFormat2.status) {
                                    if (iArr3[0] <= i9 && iArr3[1] >= i9 && (i9 - iArr3[0]) % iArr3[2] == 0) {
                                        i8 = keyFormat2.functionId;
                                        break;
                                    }
                                }
                            } else {
                                i8 = keyFormat2.functionId;
                            }
                            if (i8 >= 0) {
                                break;
                            }
                        }
                        if (i8 >= 0) {
                            break;
                        }
                    }
                    i21 = i22 + 1;
                }
            }
            formatParam = this.keyFormatMap.get(i8);
            if (formatParam == null) {
                formatParam = formatParam2;
            }
        } else {
            formatParam = formatParam2;
        }
        if (formatParam != this.param) {
            byte[] bArr6 = new byte[formatParam.formateParam.length + 1 + bArr2.length];
            bArr6[0] = (byte) formatParam.formateParam.length;
            System.arraycopy(formatParam.formateParam, 0, bArr6, 1, formatParam.formateParam.length);
            System.arraycopy(bArr2, 0, bArr6, formatParam.formateParam.length + 1, bArr2.length);
            return bArr6;
        }
        return addLeadZero(bArr2);
    }

    public synchronized void release() {
        int i;
        if (!this.released) {
            synchronized (refMap) {
                i = refMap.get(this.remoteId) - 1;
                if (i > 0) {
                    refMap.put(this.remoteId, i);
                } else {
                    refMap.delete(this.remoteId);
                }
            }
            if (i == 0) {
                CodeHelper.release(this.remoteId);
            }
            this.released = true;
        }
    }
}
