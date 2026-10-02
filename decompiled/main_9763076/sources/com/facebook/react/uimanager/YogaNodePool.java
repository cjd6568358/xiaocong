package com.facebook.react.uimanager;

import com.facebook.react.common.ClearableSynchronizedPool;
import com.facebook.yoga.YogaNode;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class YogaNodePool {
    private static final Object sInitLock = new Object();
    private static ClearableSynchronizedPool<YogaNode> sPool;

    public static ClearableSynchronizedPool<YogaNode> get() {
        ClearableSynchronizedPool<YogaNode> clearableSynchronizedPool;
        if (sPool != null) {
            return sPool;
        }
        synchronized (sInitLock) {
            if (sPool == null) {
                sPool = new ClearableSynchronizedPool<>(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
            }
            clearableSynchronizedPool = sPool;
        }
        return clearableSynchronizedPool;
    }
}
