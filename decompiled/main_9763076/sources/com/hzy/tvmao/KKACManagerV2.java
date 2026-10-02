package com.hzy.tvmao;

import android.util.SparseIntArray;
import com.hzy.tvmao.ir.encode.NormalCodeHelper;
import com.hzy.tvmao.utils.LogUtil;
import com.kookong.app.data.IrData;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class KKACManagerV2 extends BaseACManager {
    private NormalCodeHelper mCodeHelper;

    @Override // com.hzy.tvmao.BaseACManager
    public void initIRData(int i, HashMap<Integer, String> map, ArrayList<IrData.IrKey> arrayList) {
        super.initIRData(i, map, arrayList);
        LogUtil.d("KKACManagerV2 initIRData");
        this.mAcStateV2 = null;
        onPause();
        this.mCodeHelper = new NormalCodeHelper(i, map, createExpandKeyMap(arrayList));
    }

    public void onResume() {
        if (this.mCodeHelper == null && this.mExtMap != null) {
            this.mCodeHelper = new NormalCodeHelper(this.mRemoteId, this.mExtMap, createExpandKeyMap(this.mKeys));
        }
    }

    public void onPause() {
        if (this.mCodeHelper != null) {
            this.mCodeHelper.release();
            this.mCodeHelper = null;
        }
    }

    public String getACIRPattern() {
        int[][] iRPattern = getIRPattern();
        if (iRPattern != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iRPattern.length; i++) {
                for (int i2 = 0; i2 < iRPattern[i].length; i2++) {
                    sb.append(iRPattern[i][i2]).append(",");
                }
            }
            if (sb.length() > 0) {
                return sb.substring(0, sb.length() - 1);
            }
        }
        return null;
    }

    public int[] getACIRPatternIntArray() {
        int[][] iRPattern = getIRPattern();
        if (iRPattern == null || iRPattern.length <= 0) {
            return null;
        }
        return iRPattern[0];
    }

    private int[][] getIRPattern() {
        int[][] waveCodes = null;
        if (this.mCodeHelper != null) {
            SparseIntArray allExpandKeyState = getAllExpandKeyState();
            waveCodes = this.mCodeHelper.getWaveCodes(this.mAcStateV2.getCurPowerState(), this.mAcStateV2.getCurModelType(), this.mAcStateV2.getCurTemp(), this.mAcStateV2.getCurWindSpeed(), -1, this.mAcStateV2.getCurUDDirect(), functionId, null, allExpandKeyState);
            LogUtil.d("power：" + (this.mAcStateV2.getCurPowerState() == 1 ? "关" : "开") + "\n模式：" + this.mAcStateV2.getCurModelType() + "\n温度：" + this.mAcStateV2.getCurTemp() + "\n风速：" + this.mAcStateV2.getCurWindSpeed() + "\n风向：" + this.mAcStateV2.getCurUDDirect() + "\n" + getStringByExpandKey(allExpandKeyState) + "function：" + functionId);
            functionId = -1;
        }
        return waveCodes;
    }
}
