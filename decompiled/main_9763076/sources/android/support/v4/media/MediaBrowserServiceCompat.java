package android.support.v4.media;

import android.app.Service;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class MediaBrowserServiceCompat extends Service {
    static final boolean DEBUG = Log.isLoggable("MBServiceCompat", 3);
    ConnectionRecord mCurConnection;
    MediaSessionCompat.Token mSession;
    final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap<>();
    final ServiceHandler mHandler = new ServiceHandler();

    private interface ServiceCallbacks {
        IBinder asBinder();

        void onConnect(String str, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException;

        void onConnectFailed() throws RemoteException;

        void onLoadChildren(String str, List<MediaBrowserCompat.MediaItem> list, Bundle bundle) throws RemoteException;
    }

    public abstract BrowserRoot onGetRoot(String str, int i, Bundle bundle);

    public abstract void onLoadChildren(String str, Result<List<MediaBrowserCompat.MediaItem>> result);

    private final class ServiceHandler extends Handler {
        private final ServiceBinderImpl mServiceBinderImpl;

        ServiceHandler() {
            this.mServiceBinderImpl = MediaBrowserServiceCompat.this.new ServiceBinderImpl();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                case 1:
                    this.mServiceBinderImpl.connect(data.getString("data_package_name"), data.getInt("data_calling_uid"), data.getBundle("data_root_hints"), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 2:
                    this.mServiceBinderImpl.disconnect(new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 3:
                    this.mServiceBinderImpl.addSubscription(data.getString("data_media_item_id"), BundleCompat.getBinder(data, "data_callback_token"), data.getBundle("data_options"), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 4:
                    this.mServiceBinderImpl.removeSubscription(data.getString("data_media_item_id"), BundleCompat.getBinder(data, "data_callback_token"), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 5:
                    this.mServiceBinderImpl.getMediaItem(data.getString("data_media_item_id"), (ResultReceiver) data.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 6:
                    this.mServiceBinderImpl.registerCallbacks(new ServiceCallbacksCompat(msg.replyTo), data.getBundle("data_root_hints"));
                    break;
                case 7:
                    this.mServiceBinderImpl.unregisterCallbacks(new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 8:
                    this.mServiceBinderImpl.search(data.getString("data_search_query"), data.getBundle("data_search_extras"), (ResultReceiver) data.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 9:
                    this.mServiceBinderImpl.sendCustomAction(data.getString("data_custom_action"), data.getBundle("data_custom_action_extras"), (ResultReceiver) data.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                default:
                    Log.w("MBServiceCompat", "Unhandled message: " + msg + "\n  Service version: 1\n  Client version: " + msg.arg1);
                    break;
            }
        }

        @Override // android.os.Handler
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            Bundle data = msg.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt("data_calling_uid", Binder.getCallingUid());
            return super.sendMessageAtTime(msg, uptimeMillis);
        }

        public void postOrRun(Runnable r) {
            if (Thread.currentThread() == getLooper().getThread()) {
                r.run();
            } else {
                post(r);
            }
        }
    }

    private static class ConnectionRecord {
        ServiceCallbacks callbacks;
        String pkg;
        BrowserRoot root;
        Bundle rootHints;
        HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap<>();

        ConnectionRecord() {
        }
    }

    public static class Result<T> {
        private final Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendErrorCalled;
        private boolean mSendResultCalled;

        Result(Object debug) {
            this.mDebug = debug;
        }

        public void sendResult(T result) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendResult() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            this.mSendResultCalled = true;
            onResultSent(result);
        }

        public void sendError(Bundle extras) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                throw new IllegalStateException("sendError() called when either sendResult() or sendError() had already been called for: " + this.mDebug);
            }
            this.mSendErrorCalled = true;
            onErrorSent(extras);
        }

        boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled || this.mSendErrorCalled;
        }

        void setFlags(int flags) {
            this.mFlags = flags;
        }

        int getFlags() {
            return this.mFlags;
        }

        void onResultSent(T result) {
        }

        void onErrorSent(Bundle extras) {
            throw new UnsupportedOperationException("It is not supported to send an error for " + this.mDebug);
        }
    }

    private class ServiceBinderImpl {
        ServiceBinderImpl() {
        }

        public void connect(final String pkg, final int uid, final Bundle rootHints, final ServiceCallbacks callbacks) {
            if (!MediaBrowserServiceCompat.this.isValidPackage(pkg, uid)) {
                throw new IllegalArgumentException("Package/uid mismatch: uid=" + uid + " package=" + pkg);
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    IBinder b = callbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove(b);
                    ConnectionRecord connection = new ConnectionRecord();
                    connection.pkg = pkg;
                    connection.rootHints = rootHints;
                    connection.callbacks = callbacks;
                    connection.root = MediaBrowserServiceCompat.this.onGetRoot(pkg, uid, rootHints);
                    if (connection.root == null) {
                        Log.i("MBServiceCompat", "No root for client " + pkg + " from service " + getClass().getName());
                        try {
                            callbacks.onConnectFailed();
                            return;
                        } catch (RemoteException e) {
                            Log.w("MBServiceCompat", "Calling onConnectFailed() failed. Ignoring. pkg=" + pkg);
                            return;
                        }
                    }
                    try {
                        MediaBrowserServiceCompat.this.mConnections.put(b, connection);
                        if (MediaBrowserServiceCompat.this.mSession != null) {
                            callbacks.onConnect(connection.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connection.root.getExtras());
                        }
                    } catch (RemoteException e2) {
                        Log.w("MBServiceCompat", "Calling onConnect() failed. Dropping client. pkg=" + pkg);
                        MediaBrowserServiceCompat.this.mConnections.remove(b);
                    }
                }
            });
        }

        public void disconnect(final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    IBinder b = callbacks.asBinder();
                    ConnectionRecord old = MediaBrowserServiceCompat.this.mConnections.remove(b);
                    if (old != null) {
                    }
                }
            });
        }

        public void addSubscription(final String id, final IBinder token, final Bundle options, final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    IBinder b = callbacks.asBinder();
                    ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(b);
                    if (connection == null) {
                        Log.w("MBServiceCompat", "addSubscription for callback that isn't registered id=" + id);
                    } else {
                        MediaBrowserServiceCompat.this.addSubscription(id, connection, token, options);
                    }
                }
            });
        }

        public void removeSubscription(final String id, final IBinder token, final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.4
                @Override // java.lang.Runnable
                public void run() {
                    IBinder b = callbacks.asBinder();
                    ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(b);
                    if (connection == null) {
                        Log.w("MBServiceCompat", "removeSubscription for callback that isn't registered id=" + id);
                    } else if (!MediaBrowserServiceCompat.this.removeSubscription(id, connection, token)) {
                        Log.w("MBServiceCompat", "removeSubscription called for " + id + " which is not subscribed");
                    }
                }
            });
        }

        public void getMediaItem(final String mediaId, final ResultReceiver receiver, final ServiceCallbacks callbacks) {
            if (!TextUtils.isEmpty(mediaId) && receiver != null) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.5
                    @Override // java.lang.Runnable
                    public void run() {
                        IBinder b = callbacks.asBinder();
                        ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(b);
                        if (connection == null) {
                            Log.w("MBServiceCompat", "getMediaItem for callback that isn't registered id=" + mediaId);
                        } else {
                            MediaBrowserServiceCompat.this.performLoadItem(mediaId, connection, receiver);
                        }
                    }
                });
            }
        }

        public void registerCallbacks(final ServiceCallbacks callbacks, final Bundle rootHints) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.6
                @Override // java.lang.Runnable
                public void run() {
                    IBinder b = callbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove(b);
                    ConnectionRecord connection = new ConnectionRecord();
                    connection.callbacks = callbacks;
                    connection.rootHints = rootHints;
                    MediaBrowserServiceCompat.this.mConnections.put(b, connection);
                }
            });
        }

        public void unregisterCallbacks(final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.7
                @Override // java.lang.Runnable
                public void run() {
                    IBinder b = callbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove(b);
                }
            });
        }

        public void search(final String query, final Bundle extras, final ResultReceiver receiver, final ServiceCallbacks callbacks) {
            if (!TextUtils.isEmpty(query) && receiver != null) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.8
                    @Override // java.lang.Runnable
                    public void run() {
                        IBinder b = callbacks.asBinder();
                        ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(b);
                        if (connection == null) {
                            Log.w("MBServiceCompat", "search for callback that isn't registered query=" + query);
                        } else {
                            MediaBrowserServiceCompat.this.performSearch(query, extras, connection, receiver);
                        }
                    }
                });
            }
        }

        public void sendCustomAction(final String action, final Bundle extras, final ResultReceiver receiver, final ServiceCallbacks callbacks) {
            if (!TextUtils.isEmpty(action) && receiver != null) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: android.support.v4.media.MediaBrowserServiceCompat.ServiceBinderImpl.9
                    @Override // java.lang.Runnable
                    public void run() {
                        IBinder b = callbacks.asBinder();
                        ConnectionRecord connection = MediaBrowserServiceCompat.this.mConnections.get(b);
                        if (connection == null) {
                            Log.w("MBServiceCompat", "sendCustomAction for callback that isn't registered action=" + action + ", extras=" + extras);
                        } else {
                            MediaBrowserServiceCompat.this.performCustomAction(action, extras, connection, receiver);
                        }
                    }
                });
            }
        }
    }

    private static class ServiceCallbacksCompat implements ServiceCallbacks {
        final Messenger mCallbacks;

        ServiceCallbacksCompat(Messenger callbacks) {
            this.mCallbacks = callbacks;
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.ServiceCallbacks
        public IBinder asBinder() {
            return this.mCallbacks.getBinder();
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.ServiceCallbacks
        public void onConnect(String root, MediaSessionCompat.Token session, Bundle extras) throws RemoteException {
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putInt("extra_service_version", 1);
            Bundle data = new Bundle();
            data.putString("data_media_item_id", root);
            data.putParcelable("data_media_session_token", session);
            data.putBundle("data_root_hints", extras);
            sendRequest(1, data);
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.ServiceCallbacks
        public void onConnectFailed() throws RemoteException {
            sendRequest(2, null);
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.ServiceCallbacks
        public void onLoadChildren(String mediaId, List<MediaBrowserCompat.MediaItem> list, Bundle options) throws RemoteException {
            Bundle data = new Bundle();
            data.putString("data_media_item_id", mediaId);
            data.putBundle("data_options", options);
            if (list != null) {
                data.putParcelableArrayList("data_media_item_list", list instanceof ArrayList ? (ArrayList) list : new ArrayList<>(list));
            }
            sendRequest(3, data);
        }

        private void sendRequest(int what, Bundle data) throws RemoteException {
            Message msg = Message.obtain();
            msg.what = what;
            msg.arg1 = 1;
            msg.setData(data);
            this.mCallbacks.send(msg);
        }
    }

    public void onLoadChildren(String parentId, Result<List<MediaBrowserCompat.MediaItem>> result, Bundle options) {
        result.setFlags(1);
        onLoadChildren(parentId, result);
    }

    public void onLoadItem(String itemId, Result<MediaBrowserCompat.MediaItem> result) {
        result.setFlags(2);
        result.sendResult(null);
    }

    public void onSearch(String query, Bundle extras, Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.setFlags(4);
        result.sendResult(null);
    }

    public void onCustomAction(String action, Bundle extras, Result<Bundle> result) {
        result.sendError(null);
    }

    boolean isValidPackage(String pkg, int uid) {
        if (pkg == null) {
            return false;
        }
        PackageManager pm = getPackageManager();
        String[] packages = pm.getPackagesForUid(uid);
        for (String str : packages) {
            if (str.equals(pkg)) {
                return true;
            }
        }
        return false;
    }

    void addSubscription(String id, ConnectionRecord connection, IBinder token, Bundle options) {
        List<Pair<IBinder, Bundle>> callbackList = connection.subscriptions.get(id);
        if (callbackList == null) {
            callbackList = new ArrayList<>();
        }
        for (Pair<IBinder, Bundle> callback : callbackList) {
            if (token == callback.first && MediaBrowserCompatUtils.areSameOptions(options, callback.second)) {
                return;
            }
        }
        callbackList.add(new Pair<>(token, options));
        connection.subscriptions.put(id, callbackList);
        performLoadChildren(id, connection, options);
    }

    boolean removeSubscription(String id, ConnectionRecord connection, IBinder token) {
        if (token == null) {
            return connection.subscriptions.remove(id) != null;
        }
        boolean removed = false;
        List<Pair<IBinder, Bundle>> callbackList = connection.subscriptions.get(id);
        if (callbackList != null) {
            Iterator<Pair<IBinder, Bundle>> iter = callbackList.iterator();
            while (iter.hasNext()) {
                if (token == iter.next().first) {
                    removed = true;
                    iter.remove();
                }
            }
            if (callbackList.size() == 0) {
                connection.subscriptions.remove(id);
            }
        }
        return removed;
    }

    void performLoadChildren(final String parentId, final ConnectionRecord connection, final Bundle options) {
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(parentId) { // from class: android.support.v4.media.MediaBrowserServiceCompat.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
                if (MediaBrowserServiceCompat.this.mConnections.get(connection.callbacks.asBinder()) != connection) {
                    if (MediaBrowserServiceCompat.DEBUG) {
                        Log.d("MBServiceCompat", "Not sending onLoadChildren result for connection that has been disconnected. pkg=" + connection.pkg + " id=" + parentId);
                    }
                } else {
                    List<MediaBrowserCompat.MediaItem> filteredList = (getFlags() & 1) != 0 ? MediaBrowserServiceCompat.this.applyOptions(list, options) : list;
                    try {
                        connection.callbacks.onLoadChildren(parentId, filteredList, options);
                    } catch (RemoteException e) {
                        Log.w("MBServiceCompat", "Calling onLoadChildren() failed for id=" + parentId + " package=" + connection.pkg);
                    }
                }
            }
        };
        this.mCurConnection = connection;
        if (options == null) {
            onLoadChildren(parentId, result);
        } else {
            onLoadChildren(parentId, result, options);
        }
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + connection.pkg + " id=" + parentId);
        }
    }

    List<MediaBrowserCompat.MediaItem> applyOptions(List<MediaBrowserCompat.MediaItem> list, Bundle options) {
        if (list == null) {
            return null;
        }
        int page = options.getInt("android.media.browse.extra.PAGE", -1);
        int pageSize = options.getInt("android.media.browse.extra.PAGE_SIZE", -1);
        if (page != -1 || pageSize != -1) {
            int fromIndex = pageSize * page;
            int toIndex = fromIndex + pageSize;
            if (page < 0 || pageSize < 1 || fromIndex >= list.size()) {
                return Collections.EMPTY_LIST;
            }
            if (toIndex > list.size()) {
                toIndex = list.size();
            }
            return list.subList(fromIndex, toIndex);
        }
        return list;
    }

    void performLoadItem(String itemId, ConnectionRecord connection, final ResultReceiver receiver) {
        Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(itemId) { // from class: android.support.v4.media.MediaBrowserServiceCompat.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void onResultSent(MediaBrowserCompat.MediaItem item) {
                if ((getFlags() & 2) != 0) {
                    receiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable("media_item", item);
                receiver.send(0, bundle);
            }
        };
        this.mCurConnection = connection;
        onLoadItem(itemId, result);
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + itemId);
        }
    }

    void performSearch(String query, Bundle extras, ConnectionRecord connection, final ResultReceiver receiver) {
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(query) { // from class: android.support.v4.media.MediaBrowserServiceCompat.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void onResultSent(List<MediaBrowserCompat.MediaItem> items) {
                if ((getFlags() & 4) != 0 || items == null) {
                    receiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArray("search_results", (Parcelable[]) items.toArray(new MediaBrowserCompat.MediaItem[0]));
                receiver.send(0, bundle);
            }
        };
        this.mCurConnection = connection;
        onSearch(query, extras, result);
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onSearch must call detach() or sendResult() before returning for query=" + query);
        }
    }

    void performCustomAction(String action, Bundle extras, ConnectionRecord connection, final ResultReceiver receiver) {
        Result<Bundle> result = new Result<Bundle>(action) { // from class: android.support.v4.media.MediaBrowserServiceCompat.4
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void onResultSent(Bundle result2) {
                receiver.send(0, result2);
            }

            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            void onErrorSent(Bundle data) {
                receiver.send(-1, data);
            }
        };
        this.mCurConnection = connection;
        onCustomAction(action, extras, result);
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + action + " extras=" + extras);
        }
    }

    public static final class BrowserRoot {
        private final Bundle mExtras;
        private final String mRootId;

        public String getRootId() {
            return this.mRootId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }
    }
}
