package android.support.v4.media.session;

import android.media.session.MediaSession;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class MediaSessionCompatApi21 {
    public static Object verifyToken(Object token) {
        if (token instanceof MediaSession.Token) {
            return token;
        }
        throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
    }

    static class QueueItem {
        public static Object getDescription(Object queueItem) {
            return ((MediaSession.QueueItem) queueItem).getDescription();
        }

        public static long getQueueId(Object queueItem) {
            return ((MediaSession.QueueItem) queueItem).getQueueId();
        }
    }
}
