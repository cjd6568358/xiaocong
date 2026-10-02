package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultNativeMemoryChunkPoolParams {
    public static PoolParams get() {
        SparseIntArray DEFAULT_BUCKETS = new SparseIntArray();
        DEFAULT_BUCKETS.put(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT, 5);
        DEFAULT_BUCKETS.put(2048, 5);
        DEFAULT_BUCKETS.put(4096, 5);
        DEFAULT_BUCKETS.put(8192, 5);
        DEFAULT_BUCKETS.put(16384, 5);
        DEFAULT_BUCKETS.put(WXMediaMessage.THUMB_LENGTH_LIMIT, 5);
        DEFAULT_BUCKETS.put(65536, 5);
        DEFAULT_BUCKETS.put(WXMediaMessage.MINI_PROGRAM__THUMB_LENGHT, 5);
        DEFAULT_BUCKETS.put(262144, 2);
        DEFAULT_BUCKETS.put(524288, 2);
        DEFAULT_BUCKETS.put(1048576, 2);
        return new PoolParams(getMaxSizeSoftCap(), getMaxSizeHardCap(), DEFAULT_BUCKETS);
    }

    private static int getMaxSizeSoftCap() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        if (maxMemory < 16777216) {
            return 3145728;
        }
        if (maxMemory < 33554432) {
            return 6291456;
        }
        return 12582912;
    }

    private static int getMaxSizeHardCap() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        return maxMemory < 16777216 ? maxMemory / 2 : (maxMemory / 4) * 3;
    }
}
