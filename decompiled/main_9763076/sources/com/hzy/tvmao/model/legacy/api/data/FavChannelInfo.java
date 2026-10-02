package com.hzy.tvmao.model.legacy.api.data;

import com.hzy.tvmao.model.db.bean.ChannelInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FavChannelInfo implements Serializable {
    private static final long serialVersionUID = 1;
    private List<ChannelInfo> dataList = new ArrayList();
    private boolean showNum = false;

    public boolean isShowNum() {
        return this.showNum;
    }

    public void setShowNum(boolean z) {
        this.showNum = z;
    }

    public List<ChannelInfo> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<ChannelInfo> list) {
        this.dataList = list;
    }
}
