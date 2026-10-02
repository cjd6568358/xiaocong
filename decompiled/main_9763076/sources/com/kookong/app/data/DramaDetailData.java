package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DramaDetailData implements SerializableEx {
    private static final long serialVersionUID = 1;
    public String desc;
    public String resId;
    public short typeId;
    public String name = Constants.MAIN_VERSION_TAG;
    public List<String> acts = new ArrayList();
    public List<CharInfoData.CharInfo> chars = new ArrayList();
}
