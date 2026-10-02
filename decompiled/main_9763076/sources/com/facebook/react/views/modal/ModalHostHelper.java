package com.facebook.react.views.modal;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.infer.annotation.Assertions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ModalHostHelper {
    private static final Point MIN_POINT = new Point();
    private static final Point MAX_POINT = new Point();
    private static final Point SIZE_POINT = new Point();

    @TargetApi(16)
    public static Point getModalHostSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        Display display = ((WindowManager) Assertions.assertNotNull(wm)).getDefaultDisplay();
        display.getCurrentSizeRange(MIN_POINT, MAX_POINT);
        display.getSize(SIZE_POINT);
        return SIZE_POINT.x < SIZE_POINT.y ? new Point(MIN_POINT.x, MAX_POINT.y) : new Point(MAX_POINT.x, MIN_POINT.y);
    }
}
