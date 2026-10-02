package com.hzy.tvmao;

import android.util.SparseIntArray;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.hzy.tvmao.ir.encode.ZipCodeHelper;
import com.hzy.tvmao.utils.LogUtil;
import com.kookong.app.data.IrData;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class KKACZipManagerV2 extends BaseACManager {
    private ZipCodeHelper mCodeHelper;
    private int mFrequency;
    private byte[] params = null;

    public void initIRData(IrData irData) {
        initIRData(irData.rid, irData.fre, irData.exts, irData.keys);
    }

    public void initIRData(int i, int i2, HashMap<Integer, String> map, ArrayList<IrData.IrKey> arrayList) {
        super.initIRData(i, map, arrayList);
        LogUtil.d("KKACCpManagerV2 initIRData");
        this.mFrequency = i2;
        this.mAcStateV2 = null;
        this.params = null;
        onPause();
        this.mCodeHelper = new ZipCodeHelper(i, i2, map, createExpandKeyMap(arrayList));
    }

    public int getRemoteId() {
        return this.mRemoteId;
    }

    public void onResume() {
        if (this.mCodeHelper == null && this.mExtMap != null) {
            this.mCodeHelper = new ZipCodeHelper(this.mRemoteId, this.mFrequency, this.mExtMap, createExpandKeyMap(this.mKeys));
        }
    }

    public void onPause() {
        if (this.mCodeHelper != null) {
            this.mCodeHelper.release();
            this.mCodeHelper = null;
        }
    }

    public byte[] getACKeyIr() {
        byte[] keyIr = null;
        if (this.mCodeHelper != null) {
            SparseIntArray allExpandKeyState = getAllExpandKeyState();
            keyIr = this.mCodeHelper.getKeyIr(this.mAcStateV2.getCurPowerState(), this.mAcStateV2.getCurModelType(), this.mAcStateV2.getCurTemp(), this.mAcStateV2.getCurWindSpeed(), -1, this.mAcStateV2.getCurUDDirect(), functionId, null, allExpandKeyState);
            LogUtil.d("power：" + (this.mAcStateV2.getCurPowerState() == 1 ? "关" : "开") + "\n模式：" + this.mAcStateV2.getCurModelType() + "\n温度：" + this.mAcStateV2.getCurTemp() + "\n风速：" + this.mAcStateV2.getCurWindSpeed() + "\n风向：" + this.mAcStateV2.getCurUDDirect() + "\n" + getStringByExpandKey(allExpandKeyState) + "function：" + functionId);
            functionId = -1;
        }
        return keyIr;
    }

    public byte[] getAcParams() {
        if (this.params == null) {
            this.params = com.hzy.tvmao.utils.c.d(this.mExtMap.get(Integer.valueOf(ACConstants.TAG_REMOTE_PARAMS)));
        }
        return this.params;
    }
}
