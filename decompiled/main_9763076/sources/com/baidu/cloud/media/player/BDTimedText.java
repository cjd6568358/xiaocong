package com.baidu.cloud.media.player;

import android.graphics.Rect;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BDTimedText {
    private Rect a;
    private String b;

    public BDTimedText(Rect rect, String str) {
        this.a = null;
        this.b = null;
        this.a = rect;
        this.b = str;
    }

    public Rect getBounds() {
        return this.a;
    }

    public String getText() {
        return this.b;
    }
}
