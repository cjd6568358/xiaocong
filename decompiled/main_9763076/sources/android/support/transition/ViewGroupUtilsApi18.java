package android.support.transition;

import android.util.Log;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ViewGroupUtilsApi18 extends ViewGroupUtilsApi14 {
    private static Method sSuppressLayoutMethod;
    private static boolean sSuppressLayoutMethodFetched;

    ViewGroupUtilsApi18() {
    }

    @Override // android.support.transition.ViewGroupUtilsApi14, android.support.transition.ViewGroupUtilsImpl
    public ViewGroupOverlayImpl getOverlay(ViewGroup group) {
        return new ViewGroupOverlayApi18(group);
    }

    @Override // android.support.transition.ViewGroupUtilsApi14, android.support.transition.ViewGroupUtilsImpl
    public void suppressLayout(ViewGroup group, boolean suppress) {
        fetchSuppressLayoutMethod();
        if (sSuppressLayoutMethod != null) {
            try {
                sSuppressLayoutMethod.invoke(group, Boolean.valueOf(suppress));
            } catch (IllegalAccessException e) {
                Log.i("ViewUtilsApi18", "Failed to invoke suppressLayout method", e);
            } catch (InvocationTargetException e2) {
                Log.i("ViewUtilsApi18", "Error invoking suppressLayout method", e2);
            }
        }
    }

    private void fetchSuppressLayoutMethod() {
        if (!sSuppressLayoutMethodFetched) {
            try {
                sSuppressLayoutMethod = ViewGroup.class.getDeclaredMethod("suppressLayout", Boolean.TYPE);
                sSuppressLayoutMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ViewUtilsApi18", "Failed to retrieve suppressLayout method", e);
            }
            sSuppressLayoutMethodFetched = true;
        }
    }
}
