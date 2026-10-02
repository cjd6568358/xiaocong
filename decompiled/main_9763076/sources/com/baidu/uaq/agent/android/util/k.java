package com.baidu.uaq.agent.android.util;

import com.baidu.uaq.agent.android.logging.a;
import com.baidu.uaq.agent.android.logging.b;
import com.fasterxml.jackson.core.util.BufferRecycler;
import java.util.Random;

/* JADX INFO: compiled from: Util.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class k {
    private static final Random dg = new Random();
    private static final a LOG = b.bg();

    public static void Y(String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN;
        for (int i = 0; i < 100; i++) {
            if (strLength > end) {
                LOG.E(msg.substring(start, end));
                start = end;
                end += BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN;
            } else {
                LOG.E(msg.substring(start, strLength));
                return;
            }
        }
    }

    public static Random bA() {
        return dg;
    }
}
