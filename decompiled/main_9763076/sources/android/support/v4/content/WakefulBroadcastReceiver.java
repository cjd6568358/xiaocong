package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.os.PowerManager;
import android.util.SparseArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
    private static final SparseArray<PowerManager.WakeLock> sActiveWakeLocks = new SparseArray<>();
    private static int mNextId = 1;
}
