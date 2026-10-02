package android.support.v7.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TooltipCompat {
    private static final ViewCompatImpl IMPL;

    private interface ViewCompatImpl {
        void setTooltipText(View view, CharSequence charSequence);
    }

    private static class BaseViewCompatImpl implements ViewCompatImpl {
        private BaseViewCompatImpl() {
        }

        @Override // android.support.v7.widget.TooltipCompat.ViewCompatImpl
        public void setTooltipText(View view, CharSequence tooltipText) {
            TooltipCompatHandler.setTooltipText(view, tooltipText);
        }
    }

    @TargetApi(26)
    private static class Api26ViewCompatImpl implements ViewCompatImpl {
        private Api26ViewCompatImpl() {
        }

        @Override // android.support.v7.widget.TooltipCompat.ViewCompatImpl
        public void setTooltipText(View view, CharSequence tooltipText) {
            view.setTooltipText(tooltipText);
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 26) {
            IMPL = new Api26ViewCompatImpl();
        } else {
            IMPL = new BaseViewCompatImpl();
        }
    }

    public static void setTooltipText(View view, CharSequence tooltipText) {
        IMPL.setTooltipText(view, tooltipText);
    }
}
