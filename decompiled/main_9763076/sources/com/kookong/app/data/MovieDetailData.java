package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MovieDetailData implements SerializableEx {
    private static final long serialVersionUID = 1;
    public String country;
    public String desc;
    public String language;
    public String resId;
    public short typeId;
    public short year;
    public String name = Constants.MAIN_VERSION_TAG;
    public List<String> dirs = new ArrayList();
    public List<String> acts = new ArrayList();
    public List<String> sws = new ArrayList();
    public List<CharInfoData.CharInfo> chars = new ArrayList();
}
