package com.facebook.react.flat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
interface AttachDetachListener {
    public static final AttachDetachListener[] EMPTY_ARRAY = new AttachDetachListener[0];

    void onAttached(FlatViewGroup.InvalidateCallback invalidateCallback);

    void onDetached();
}
