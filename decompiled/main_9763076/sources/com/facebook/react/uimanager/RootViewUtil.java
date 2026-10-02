package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.infer.annotation.Assertions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RootViewUtil {
    /* JADX WARN: Multi-variable type inference failed */
    public static RootView getRootView(View view) {
        View view2 = view;
        while (!(view2 instanceof RootView)) {
            Object parent = view2.getParent();
            if (parent == null) {
                return null;
            }
            Assertions.assertCondition(parent instanceof View);
            view2 = (View) parent;
        }
        return (RootView) view2;
    }
}
