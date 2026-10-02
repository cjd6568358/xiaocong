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
public class NormalCodeHelper {
    private static final SparseIntArray refMap = new SparseIntArray();
    private final SparseArray<FormatParam> keyFormatMap;
    private final FormatParam param;
    private boolean released = false;
    private final int remoteId;

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
        private boolean addTrailerOne;
        private String beanshellScript;
        private SparseIntArray byteBitNums;
        private SparseArray<int[]> delayCodes;
        private SparseArray<List<KeyFormat>> keyKeyMap;
        private int[] leadCodes;
        private boolean littleEndian;
        private int[] oneCodes;
        private int repeatCount;
        private String script;
        private SparseArray<List<KeyFormat>> statusKeyMap;
        private Map<String, int[]> waveCodeMap;
        private int[] zeroCodes;

        private FormatParam() {
            this.repeatCount = 1;
            this.littleEndian = false;
            this.addTrailerOne = true;
        }

        /* synthetic */ FormatParam(FormatParam formatParam) {
            this();
        }
    }

    public int getRemoteId() {
        return this.remoteId;
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
                Collections.sort(sparseArray.valueAt(i2), new Comparator<KeyFormat>() { // from class: com.hzy.tvmao.ir.encode.NormalCodeHelper.1
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
                case 300:
                    formatParam.leadCodes = c.a(value.trim(), ",");
                    break;
                case 301:
                    formatParam.zeroCodes = c.a(value.trim(), ",");
                    break;
                case 302:
                    formatParam.oneCodes = c.a(value.trim(), ",");
                    break;
                case 303:
                    formatParam.delayCodes = new SparseArray();
                    String[] strArrSplit = value.trim().split("\\|");
                    for (String str : strArrSplit) {
                        int[] iArrA = c.a(str, "[&,]");
                        int[] iArr = new int[iArrA.length - 1];
                        System.arraycopy(iArrA, 1, iArr, 0, iArr.length);
                        formatParam.delayCodes.put(iArrA[0], iArr);
                    }
                    break;
                case ACConstants.TAG_LOW_BIT_FIRST /* 306 */:
                    formatParam.littleEndian = Integer.parseInt(value.trim()) == 1;
                    break;
                case 307:
                    formatParam.addTrailerOne = Integer.parseInt(value.trim()) != 1;
                    break;
                case ACConstants.TAG_REPEAT_COUNT /* 1508 */:
                    formatParam.repeatCount = Integer.parseInt(value.trim());
                    break;
                case ACConstants.TAG_BYTE_BIT_NUM /* 1509 */:
                    formatParam.byteBitNums = new SparseIntArray();
                    String[] strArrSplit2 = value.trim().split("\\|");
                    for (String str2 : strArrSplit2) {
                        int[] iArrA2 = c.a(str2, "&");
                        formatParam.byteBitNums.put(iArrA2[0], iArrA2[1]);
                    }
                    break;
                case ACConstants.TAG_CUSTOMIZED_WAVE_CODE /* 1510 */:
                    formatParam.waveCodeMap = new HashMap();
                    for (String str3 : value.trim().split("\\|")) {
                        String strTrim = str3.trim();
                        int iIndexOf = strTrim.indexOf(38);
                        String strTrim2 = strTrim.substring(0, iIndexOf).trim();
                        int iIndexOf2 = strTrim.indexOf(38, iIndexOf + 1);
                        String strSubstring = strTrim.substring(iIndexOf + 1, iIndexOf2);
                        int[] iArrA3 = c.a(strTrim.substring(iIndexOf2 + 1), ",");
                        for (String str4 : strSubstring.split(",")) {
                            formatParam.waveCodeMap.put(String.valueOf(strTrim2) + "&" + str4, iArrA3);
                        }
                    }
                    break;
                case ACConstants.TAG_CUSTOMIZED_BEANSHELL_SCRIPT /* 1511 */:
                    formatParam.beanshellScript = value.trim();
                    break;
                case ACConstants.TAG_FUNCTION_FORMAT_MAP /* 1516 */:
                    for (String str5 : value.trim().split("\\|")) {
                        String strTrim3 = str5.trim();
                        char cCharAt = 0;
                        int i = 0;
                        while (true) {
                            if (i >= strTrim3.length()) {
                                i = 0;
                            } else {
                                cCharAt = strTrim3.charAt(i);
                                if (cCharAt != '&' && cCharAt != '@') {
                                    i++;
                                }
                            }
                        }
                        int i2 = Integer.parseInt(strTrim3.substring(0, i).trim());
                        int iIndexOf3 = strTrim3.indexOf(38, i + 1);
                        String strSubstring2 = strTrim3.substring(i + 1, iIndexOf3);
                        int i3 = Integer.parseInt(strTrim3.substring(iIndexOf3 + 1).trim());
                        KeyFormat keyFormat = new KeyFormat(null);
                        keyFormat.functionId = i3;
                        if (strSubstring2.length() > 0) {
                            String[] strArrSplit3 = strSubstring2.split("$");
                            keyFormat.status = new int[strArrSplit3.length][];
                            for (int i4 = 0; i4 < strArrSplit3.length; i4++) {
                                String str6 = strArrSplit3[i4];
                                int iIndexOf4 = str6.indexOf(45);
                                int iIndexOf5 = str6.indexOf(44, iIndexOf4);
                                keyFormat.status[i4] = new int[]{Integer.parseInt(str6.substring(0, iIndexOf4)), Integer.parseInt(str6.substring(iIndexOf4 + 1, iIndexOf5 > 0 ? iIndexOf5 : str6.length())), iIndexOf5 > 0 ? Integer.parseInt(str6.substring(iIndexOf5 + 1)) : 1};
                            }
                        }
                        addKeyFormat(cCharAt == '&' ? getKeyKeyMap(formatParam) : getStatusKeyMap(formatParam), i2, keyFormat);
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

    public NormalCodeHelper(int i, Map<Integer, String> map, Map<Integer, Map<Integer, String>> map2) {
        int i2;
        this.remoteId = i;
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
            i2 = refMap.get(i) + 1;
            refMap.put(i, i2);
        }
        if (i2 == 1) {
            CodeHelper.initRemote(i, 1, strArr);
        }
    }

    /* JADX WARN: Code duplicated, block: B:138:0x0416  */
    public int[][] getWaveCodes(int i, int i2, int i3, int i4, int i5, int i6, int i7, byte[] bArr, SparseIntArray sparseIntArray) {
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
            int[] iArr = (int[]) this.param.waveCodeMap.get(String.valueOf(i7) + "&" + i13);
            int[] iArr2 = iArr == null ? (int[]) this.param.waveCodeMap.get(String.valueOf(i7) + "&") : iArr;
            if (iArr2 != null) {
                return new int[][]{iArr2};
            }
        }
        byte[][] bArrEnc = CodeHelper.enc(this.remoteId, i, i2, i3, i4, i5, i6, i7, bArr, string);
        int[][] iArr3 = new int[bArrEnc.length][];
        int i14 = 0;
        while (true) {
            int i15 = i14;
            if (i15 >= bArrEnc.length) {
                return iArr3;
            }
            byte[] bArr3 = bArrEnc[i15];
            if (this.param.script == null || this.param.script.length() <= 0) {
                if (this.param.beanshellScript == null || this.param.beanshellScript.length() <= 0) {
                    bArr2 = bArr3;
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
                        bArr2 = bArr3;
                    } catch (Exception e) {
                        Log.e("CodeHelper", "evaluate script failed for remote " + this.remoteId, e);
                        bArr2 = bArr3;
                    }
                }
            } else {
                LuaState luaStateNewLuaState = LuaStateFactory.newLuaState();
                try {
                    try {
                        luaStateNewLuaState.openLibs();
                        luaStateNewLuaState.newTable();
                        for (int i16 = 0; i16 < bArr3.length; i16++) {
                            luaStateNewLuaState.pushNumber(bArr3[i16] & Constants.NETWORK_TYPE_UNCONNECTED);
                            luaStateNewLuaState.rawSetI(-2, i16 + 1);
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
                            for (int i17 = 0; i17 < sparseIntArray.size(); i17++) {
                                int iKeyAt2 = sparseIntArray.keyAt(i17);
                                int i18 = sparseIntArray.get(iKeyAt2);
                                luaStateNewLuaState.pushNumber(iKeyAt2);
                                luaStateNewLuaState.pushNumber(i18);
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
                            Collections.sort(arrayList, new Comparator<int[]>() { // from class: com.hzy.tvmao.ir.encode.NormalCodeHelper.2
                                @Override // java.util.Comparator
                                public int compare(int[] iArr4, int[] iArr5) {
                                    return iArr4[0] - iArr5[0];
                                }
                            });
                            byte[] bArr4 = new byte[arrayList.size()];
                            int i19 = 0;
                            while (true) {
                                try {
                                    int i20 = i19;
                                    if (i20 >= arrayList.size()) {
                                        break;
                                    }
                                    bArr4[i20] = (byte) ((int[]) arrayList.get(i20))[1];
                                    i19 = i20 + 1;
                                } catch (Exception e2) {
                                    bArr3 = bArr4;
                                    e = e2;
                                    Log.e("CodeHelper", "evaluate script failed for remote " + this.remoteId, e);
                                    luaStateNewLuaState.close();
                                    bArr2 = bArr3;
                                }
                            }
                            bArr3 = bArr4;
                        } else {
                            Log.e("CodeHelper", "evaluate script return error (" + iLdoString + ") for remote " + this.remoteId);
                        }
                        luaStateNewLuaState.close();
                        bArr2 = bArr3;
                    } catch (Throwable th) {
                        luaStateNewLuaState.close();
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
            FormatParam formatParam2 = this.param;
            if (this.keyFormatMap != null) {
                int i21 = -1;
                if (this.param.keyKeyMap != null && (list = (List) this.param.keyKeyMap.get(i7)) != null) {
                    for (KeyFormat keyFormat : list) {
                        if (keyFormat.status != null) {
                            int[][] iArr4 = keyFormat.status;
                            int length = iArr4.length;
                            int i22 = 0;
                            while (true) {
                                if (i22 >= length) {
                                    i10 = i21;
                                    break;
                                }
                                int[] iArr5 = iArr4[i22];
                                if (iArr5[0] > i13 || iArr5[1] < i13 || (i13 - iArr5[0]) % iArr5[2] != 0) {
                                    i22++;
                                } else {
                                    i10 = keyFormat.functionId;
                                    break;
                                }
                            }
                        } else {
                            i10 = keyFormat.functionId;
                        }
                        if (i10 >= 0) {
                            i21 = i10;
                            break;
                        }
                        i21 = i10;
                    }
                }
                if (i21 >= 0 || this.param.statusKeyMap == null) {
                    i8 = i21;
                } else {
                    int i23 = 0;
                    i8 = i21;
                    while (true) {
                        int i24 = i23;
                        if (i24 >= this.param.statusKeyMap.size()) {
                            break;
                        }
                        int iKeyAt3 = this.param.statusKeyMap.keyAt(i24);
                        List<KeyFormat> list2 = (List) this.param.statusKeyMap.get(iKeyAt3);
                        if (list2 != null && (i9 = sparseIntArray2.get(iKeyAt3, -1)) >= 0) {
                            for (KeyFormat keyFormat2 : list2) {
                                if (keyFormat2.status != null) {
                                    for (int[] iArr6 : keyFormat2.status) {
                                        if (iArr6[0] <= i9 && iArr6[1] >= i9 && (i9 - iArr6[0]) % iArr6[2] == 0) {
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
                        i23 = i24 + 1;
                    }
                }
                formatParam = this.keyFormatMap.get(i8);
                if (formatParam == null) {
                    formatParam = formatParam2;
                }
            } else {
                formatParam = formatParam2;
            }
            iArr3[i15] = getWaveCode(formatParam, bArr2);
            i14 = i15 + 1;
        }
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

    private static void addArrayToList(int[] iArr, List<Integer> list) {
        if (iArr != null && iArr.length > 0) {
            for (int i : iArr) {
                list.add(Integer.valueOf(i));
            }
        }
    }

    private static void addDelayCodes(FormatParam formatParam, int i, List<Integer> list) {
        int[] iArr;
        if (formatParam.delayCodes != null && (iArr = (int[]) formatParam.delayCodes.get(i)) != null) {
            addArrayToList(iArr, list);
        }
    }

    private static int[] getWaveCode(FormatParam formatParam, byte[] bArr) {
        int i;
        int[] iArr;
        int[] iArr2;
        ArrayList arrayList = new ArrayList();
        addArrayToList(formatParam.leadCodes, arrayList);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            String strA = c.a(Integer.toBinaryString(bArr[i2]), 8, '0');
            if (formatParam.byteBitNums != null) {
                i = formatParam.byteBitNums.get(i2, 8);
                if (i2 == bArr.length - 1 && i == 8) {
                    i = formatParam.byteBitNums.get(-1, 8);
                }
            } else {
                i = 8;
            }
            if (formatParam.littleEndian) {
                int length = strA.length() - i;
                for (int length2 = strA.length() - 1; length2 >= length; length2--) {
                    if (strA.charAt(length2) == '0') {
                        iArr2 = formatParam.zeroCodes;
                    } else {
                        iArr2 = formatParam.oneCodes;
                    }
                    addArrayToList(iArr2, arrayList);
                }
            } else {
                for (int length3 = strA.length() - i; length3 < strA.length(); length3++) {
                    if (strA.charAt(length3) == '0') {
                        iArr = formatParam.zeroCodes;
                    } else {
                        iArr = formatParam.oneCodes;
                    }
                    addArrayToList(iArr, arrayList);
                }
            }
            addDelayCodes(formatParam, i2, arrayList);
        }
        if (formatParam.addTrailerOne && formatParam.oneCodes != null) {
            arrayList.add(Integer.valueOf(formatParam.oneCodes[0]));
        }
        addDelayCodes(formatParam, -1, arrayList);
        if (arrayList.size() % 2 == 1) {
            arrayList.add(1000);
        }
        int[] iArr3 = new int[formatParam.repeatCount * arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            iArr3[i3] = ((Integer) arrayList.get(i3)).intValue();
        }
        for (int i4 = 1; i4 < formatParam.repeatCount; i4++) {
            System.arraycopy(iArr3, 0, iArr3, arrayList.size() * i4, arrayList.size());
        }
        return iArr3;
    }
}
