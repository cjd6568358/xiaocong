package android.support.v4.app;

import android.app.PendingIntent;
import android.os.Bundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NotificationCompatBase {

    public static abstract class Action {

        public interface Factory {
        }

        public abstract PendingIntent getActionIntent();

        public abstract boolean getAllowGeneratedReplies();

        public abstract RemoteInputCompatBase.RemoteInput[] getDataOnlyRemoteInputs();

        public abstract Bundle getExtras();

        public abstract int getIcon();

        public abstract RemoteInputCompatBase.RemoteInput[] getRemoteInputs();

        public abstract CharSequence getTitle();
    }
}
