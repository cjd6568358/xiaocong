package com.facebook.drawee.interfaces;

import android.view.MotionEvent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DraweeController {
    DraweeHierarchy getHierarchy();

    void onAttach();

    void onDetach();

    boolean onTouchEvent(MotionEvent motionEvent);

    void setHierarchy(DraweeHierarchy draweeHierarchy);
}
