package android.support.v4.media.session;

import android.media.session.PlaybackState;
import android.os.Bundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class PlaybackStateCompatApi22 {
    public static Bundle getExtras(Object stateObj) {
        return ((PlaybackState) stateObj).getExtras();
    }
}
