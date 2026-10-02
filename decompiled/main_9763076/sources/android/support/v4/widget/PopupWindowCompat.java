package android.support.v4.widget;

import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class PopupWindowCompat {
    static final PopupWindowCompatBaseImpl IMPL;

    static class PopupWindowCompatBaseImpl {
        private static Method sSetWindowLayoutTypeMethod;
        private static boolean sSetWindowLayoutTypeMethodAttempted;

        PopupWindowCompatBaseImpl() {
        }

        public void showAsDropDown(PopupWindow popup, View anchor, int xoff, int yoff, int gravity) {
            int hgrav = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(anchor)) & 7;
            if (hgrav == 5) {
                xoff -= popup.getWidth() - anchor.getWidth();
            }
            popup.showAsDropDown(anchor, xoff, yoff);
        }

        public void setOverlapAnchor(PopupWindow popupWindow, boolean overlapAnchor) {
        }

        public void setWindowLayoutType(PopupWindow popupWindow, int layoutType) {
            if (!sSetWindowLayoutTypeMethodAttempted) {
                try {
                    sSetWindowLayoutTypeMethod = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE);
                    sSetWindowLayoutTypeMethod.setAccessible(true);
                } catch (Exception e) {
                }
                sSetWindowLayoutTypeMethodAttempted = true;
            }
            if (sSetWindowLayoutTypeMethod != null) {
                try {
                    sSetWindowLayoutTypeMethod.invoke(popupWindow, Integer.valueOf(layoutType));
                } catch (Exception e2) {
                }
            }
        }
    }

    static class PopupWindowCompatApi19Impl extends PopupWindowCompatBaseImpl {
        PopupWindowCompatApi19Impl() {
        }

        @Override // android.support.v4.widget.PopupWindowCompat.PopupWindowCompatBaseImpl
        public void showAsDropDown(PopupWindow popup, View anchor, int xoff, int yoff, int gravity) {
            popup.showAsDropDown(anchor, xoff, yoff, gravity);
        }
    }

    static class PopupWindowCompatApi21Impl extends PopupWindowCompatApi19Impl {
        private static Field sOverlapAnchorField;

        PopupWindowCompatApi21Impl() {
        }

        static {
            try {
                sOverlapAnchorField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                sOverlapAnchorField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", e);
            }
        }

        @Override // android.support.v4.widget.PopupWindowCompat.PopupWindowCompatBaseImpl
        public void setOverlapAnchor(PopupWindow popupWindow, boolean overlapAnchor) {
            if (sOverlapAnchorField != null) {
                try {
                    sOverlapAnchorField.set(popupWindow, Boolean.valueOf(overlapAnchor));
                } catch (IllegalAccessException e) {
                    Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", e);
                }
            }
        }
    }

    static class PopupWindowCompatApi23Impl extends PopupWindowCompatApi21Impl {
        PopupWindowCompatApi23Impl() {
        }

        @Override // android.support.v4.widget.PopupWindowCompat.PopupWindowCompatApi21Impl, android.support.v4.widget.PopupWindowCompat.PopupWindowCompatBaseImpl
        public void setOverlapAnchor(PopupWindow popupWindow, boolean overlapAnchor) {
            popupWindow.setOverlapAnchor(overlapAnchor);
        }

        @Override // android.support.v4.widget.PopupWindowCompat.PopupWindowCompatBaseImpl
        public void setWindowLayoutType(PopupWindow popupWindow, int layoutType) {
            popupWindow.setWindowLayoutType(layoutType);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 23) {
            IMPL = new PopupWindowCompatApi23Impl();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new PopupWindowCompatApi21Impl();
        } else if (Build.VERSION.SDK_INT >= 19) {
            IMPL = new PopupWindowCompatApi19Impl();
        } else {
            IMPL = new PopupWindowCompatBaseImpl();
        }
    }

    public static void showAsDropDown(PopupWindow popup, View anchor, int xoff, int yoff, int gravity) {
        IMPL.showAsDropDown(popup, anchor, xoff, yoff, gravity);
    }

    public static void setOverlapAnchor(PopupWindow popupWindow, boolean overlapAnchor) {
        IMPL.setOverlapAnchor(popupWindow, overlapAnchor);
    }

    public static void setWindowLayoutType(PopupWindow popupWindow, int layoutType) {
        IMPL.setWindowLayoutType(popupWindow, layoutType);
    }
}
