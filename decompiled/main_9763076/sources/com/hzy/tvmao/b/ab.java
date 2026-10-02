package com.hzy.tvmao.b;

import android.text.TextUtils;
import com.hzy.tvmao.model.db.bean.ChannelInfo;
import com.tencent.android.tpush.common.Constants;
import java.util.List;

/* JADX INFO: compiled from: SDKControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ab extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ u e;
    private final /* synthetic */ int f;
    private final /* synthetic */ String g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ab(u uVar, a aVar, a.c cVar, String str, int i, String str2) {
        super(cVar, str);
        this.e = uVar;
        this.f = i;
        this.g = str2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        List<ChannelInfo> listC = com.hzy.tvmao.model.db.a.a.a().c(this.f);
        StringBuffer stringBuffer = new StringBuffer();
        for (ChannelInfo channelInfo : listC) {
            stringBuffer.append(String.valueOf(channelInfo.channelId) + "|" + ((int) channelInfo.isHd) + (TextUtils.isEmpty(channelInfo.countryId) ? Constants.MAIN_VERSION_TAG : "|" + channelInfo.countryId) + ",");
        }
        return new com.hzy.tvmao.b.a.a(com.hzy.tvmao.model.legacy.api.d.b(this.g, stringBuffer.toString()));
    }
}
