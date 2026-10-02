package com.facebook.drawee.interfaces;

import android.net.Uri;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface SimpleDraweeControllerBuilder {
    DraweeController build();

    SimpleDraweeControllerBuilder setCallerContext(Object obj);

    SimpleDraweeControllerBuilder setOldController(DraweeController draweeController);

    SimpleDraweeControllerBuilder setUri(Uri uri);
}
