package com.hzy.tvmao;

import android.util.SparseArray;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.kookong.app.data.IrData;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class KKNonACManager {
    private ArrayList<IrData.IrKey> keyList;
    private SparseArray<IrData.IrKey> keyMap;
    private int mFrequency;
    private int mRemoteId;
    private byte[] params;
    private boolean toggle = true;

    public KKNonACManager(IrData irData) {
        this.params = null;
        this.mRemoteId = irData.rid;
        this.mFrequency = irData.fre / 10;
        this.mFrequency *= 10;
        this.params = com.hzy.tvmao.utils.c.d(irData.exts.get(Integer.valueOf(ACConstants.TAG_REMOTE_PARAMS)));
        this.keyList = irData.keys;
        this.keyMap = new SparseArray<>();
        for (IrData.IrKey irKey : irData.keys) {
            this.keyMap.put(irKey.fid, irKey);
        }
    }

    public ArrayList<IrData.IrKey> getAllKeys() {
        return this.keyList;
    }

    public int getRemoteId() {
        return this.mRemoteId;
    }

    public byte[] getParams() {
        return this.params;
    }

    public byte[] getKeyIr(int i) {
        IrData.IrKey irKey = this.keyMap.get(i);
        if (irKey == null) {
            return null;
        }
        String[] strArrSplit = irKey.pulse.split("&");
        String str = strArrSplit[0];
        if (strArrSplit.length > 1) {
            if (!this.toggle) {
                str = strArrSplit[1];
            }
            this.toggle = this.toggle ? false : true;
        }
        if (str.contains(",")) {
            int i2 = 1000000 / this.mFrequency;
            int[] iArrC = com.hzy.tvmao.utils.c.c(str);
            byte[] bArr = new byte[(iArrC.length * 2) + 1];
            bArr[0] = 0;
            for (int i3 = 0; i3 < iArrC.length; i3++) {
                int i4 = iArrC[i3] / i2;
                bArr[(i3 * 2) + 1] = (byte) (i4 >> 8);
                bArr[(i3 * 2) + 1 + 1] = (byte) (i4 & 255);
            }
            return bArr;
        }
        return com.hzy.tvmao.utils.c.d(str);
    }
}
