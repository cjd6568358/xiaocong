package com.hzy.tvmao.model.legacy.api;

import com.hzy.tvmao.model.db.bean.ChannelInfo;
import com.hzy.tvmao.model.legacy.api.data.EPGProgramData;
import com.hzy.tvmao.utils.LogUtil;
import com.kookong.app.data.PlayingTimeData;
import java.util.Collection;
import java.util.HashMap;

/* JADX INFO: compiled from: Epgs.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static void a(Collection<PlayingTimeData.EPGData> collection, EPGProgramData ePGProgramData) {
        new HashMap();
        HashMap<ChannelInfo.a, ChannelInfo> mapD = com.hzy.tvmao.b.l.c().d();
        ChannelInfo.a aVar = new ChannelInfo.a();
        LogUtil.d("Epgs size===" + collection.size());
        for (PlayingTimeData.EPGData ePGData : collection) {
            if (ePGData.cid > 0) {
                aVar.a = ePGData.cid;
                aVar.b = ePGData.ct;
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < 2) {
                        aVar.c = i2;
                        ChannelInfo channelInfo = mapD.get(aVar);
                        if (channelInfo != null) {
                            EPGProgramData.EPGData ePGData2 = new EPGProgramData.EPGData();
                            ePGData2.cid = ePGData.cid;
                            ePGData2.ct = ePGData.ct;
                            ePGData2.cname = channelInfo.name;
                            ePGData2.clogo = channelInfo.logo;
                            ePGData2.cllogo = channelInfo.llogo;
                            ePGData2.isHd = channelInfo.isHd;
                            ePGData2.isFree = channelInfo.fee;
                            ePGData2.pulse = channelInfo.pulse;
                            ePGData2.cnum = channelInfo.num;
                            ePGData2.tid = ePGData.tid;
                            ePGData2.rid = ePGData.rid;
                            ePGData2.n = ePGData.n;
                            ePGData2.d = ePGData.d;
                            ePGData2.t = ePGData.t;
                            ePGData2.startTime = ePGData.startTime;
                            ePGData2.endTime = ePGData.endTime;
                            ePGData2.thumb = ePGData.thumb;
                            ePGProgramData.getEPGList().add(ePGData2);
                        }
                        i = i2 + 1;
                    }
                }
            } else {
                EPGProgramData.EPGData ePGData3 = new EPGProgramData.EPGData();
                ePGData3.tid = ePGData.tid;
                ePGData3.rid = ePGData.rid;
                ePGData3.n = ePGData.n;
                ePGData3.thumb = ePGData.thumb;
                ePGProgramData.getEPGList().add(ePGData3);
            }
        }
    }
}
