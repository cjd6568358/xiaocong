package android.support.transition;

import android.graphics.Matrix;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
interface ViewUtilsImpl {
    void clearNonTransitionAlpha(View view);

    ViewOverlayImpl getOverlay(View view);

    float getTransitionAlpha(View view);

    WindowIdImpl getWindowId(View view);

    void saveNonTransitionAlpha(View view);

    void setLeftTopRightBottom(View view, int i, int i2, int i3, int i4);

    void setTransitionAlpha(View view, float f);

    void transformMatrixToGlobal(View view, Matrix matrix);

    void transformMatrixToLocal(View view, Matrix matrix);
}
