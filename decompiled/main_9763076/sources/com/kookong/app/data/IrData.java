package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IrData implements SerializableEx {
    private static final long serialVersionUID = 3228680024489450540L;
    public String extJSON = Constants.MAIN_VERSION_TAG;
    public HashMap<Integer, String> exts;
    public int fre;
    public ArrayList<IrKey> keys;
    public int rid;
    public short type;

    public static class IrKey implements SerializableEx {
        private static final long serialVersionUID = -1231310702792778492L;
        public String dcode;
        public HashMap<Integer, String> exts;
        public int fid;
        public String fkey;
        public String fname;
        public int format;
        public String pulse;
        public String scode;
    }
}
