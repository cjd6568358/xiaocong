package com.kookong.app.data;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RcTestRemoteKeyList implements SerializableEx {
    private static final long serialVersionUID = 5980246524182480984L;
    protected List<RcTestRemoteKey> remoteKeyList = new ArrayList();
    protected int defaultRemoteId = 0;
    protected String allRemoteIds = Constants.MAIN_VERSION_TAG;

    public int getDefaultRemoteId() {
        return this.defaultRemoteId;
    }

    public void setDefaultRemoteId(int defaultRemoteId) {
        this.defaultRemoteId = defaultRemoteId;
    }

    public List<RcTestRemoteKey> getRemoteKeyList() {
        return this.remoteKeyList;
    }

    public void setRemoteKeyList(List<RcTestRemoteKey> remoteKeyList) {
        this.remoteKeyList = remoteKeyList;
    }

    public String getAllRemoteIds() {
        return this.allRemoteIds;
    }

    public void setAllRemoteIds(String allRemoteIds) {
        this.allRemoteIds = allRemoteIds;
    }
}
