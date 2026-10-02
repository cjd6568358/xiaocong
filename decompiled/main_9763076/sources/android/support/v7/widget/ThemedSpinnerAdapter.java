package android.support.v7.widget;

import android.content.res.Resources;
import android.widget.SpinnerAdapter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ThemedSpinnerAdapter extends SpinnerAdapter {
    Resources.Theme getDropDownViewTheme();

    void setDropDownViewTheme(Resources.Theme theme);
}
