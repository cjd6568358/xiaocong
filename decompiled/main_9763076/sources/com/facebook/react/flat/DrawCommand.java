package com.facebook.react.flat;

import android.graphics.Canvas;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DrawCommand {
    static final DrawCommand[] EMPTY_ARRAY = new DrawCommand[0];

    abstract void debugDraw(FlatViewGroup flatViewGroup, Canvas canvas);

    abstract void draw(FlatViewGroup flatViewGroup, Canvas canvas);
}
