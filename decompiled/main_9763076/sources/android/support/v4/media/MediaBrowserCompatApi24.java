package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class MediaBrowserCompatApi24 {

    interface SubscriptionCallback extends MediaBrowserCompatApi21.SubscriptionCallback {
        void onChildrenLoaded(String str, List<?> list, Bundle bundle);

        void onError(String str, Bundle bundle);
    }

    public static Object createSubscriptionCallback(SubscriptionCallback callback) {
        return new SubscriptionCallbackProxy(callback);
    }

    static class SubscriptionCallbackProxy<T extends SubscriptionCallback> extends MediaBrowserCompatApi21.SubscriptionCallbackProxy<T> {
        public SubscriptionCallbackProxy(T callback) {
            super(callback);
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public void onChildrenLoaded(String parentId, List<MediaBrowser.MediaItem> children, Bundle options) {
            ((SubscriptionCallback) this.mSubscriptionCallback).onChildrenLoaded(parentId, children, options);
        }

        @Override // android.media.browse.MediaBrowser.SubscriptionCallback
        public void onError(String parentId, Bundle options) {
            ((SubscriptionCallback) this.mSubscriptionCallback).onError(parentId, options);
        }
    }
}
