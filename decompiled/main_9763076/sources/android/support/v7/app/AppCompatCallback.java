package android.support.v7.app;

import android.support.v7.view.ActionMode;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface AppCompatCallback {
    void onSupportActionModeFinished(ActionMode actionMode);

    void onSupportActionModeStarted(ActionMode actionMode);

    ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback);
}
