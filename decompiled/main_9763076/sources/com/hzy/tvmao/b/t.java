package com.hzy.tvmao.b;

import com.hzy.tvmao.model.legacy.api.data.ManualMatchData;
import com.hzy.tvmao.utils.LogUtil;
import com.kookong.sdk.bean.Channel;
import com.kookong.sdk.bean.ManualMatchLineupData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: OperaterControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class t extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ o e;
    private final /* synthetic */ int f;
    private final /* synthetic */ int g;
    private final /* synthetic */ String h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    t(o oVar, a aVar, a.c cVar, String str, int i, int i2, String str2) {
        super(cVar, str);
        this.e = oVar;
        this.f = i;
        this.g = i2;
        this.h = str2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        com.hzy.tvmao.model.legacy.api.i<ManualMatchData> iVarA = com.hzy.tvmao.model.legacy.api.d.a(this.f, this.g, this.h);
        if (iVarA.a()) {
            ManualMatchData manualMatchData = (ManualMatchData) iVarA.e;
            List<ManualMatchData.Ch> list = manualMatchData.getList();
            ArrayList arrayList = null;
            if (list != null) {
                ArrayList arrayList2 = new ArrayList(list.size());
                Iterator<ManualMatchData.Ch> it = list.iterator();
                while (it.hasNext()) {
                    arrayList2.add(Channel.create(it.next()));
                }
                arrayList = arrayList2;
            }
            ManualMatchLineupData manualMatchLineupData = new ManualMatchLineupData();
            manualMatchLineupData.setList(arrayList);
            manualMatchLineupData.setResult(manualMatchData.getResult());
            manualMatchLineupData.setResultCode(manualMatchData.getResultCode());
            LogUtil.d("manualMatchLineupData get: " + manualMatchLineupData.toString());
            return new com.hzy.tvmao.b.a.a(1, "manualLineup", manualMatchLineupData);
        }
        LogUtil.e("manualMatchDataServletResult.isOk() = false");
        return new com.hzy.tvmao.b.a.a(iVarA);
    }
}
