package com.facebook.react.modules.network;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.GuardedResultAsyncTask;
import com.facebook.react.bridge.ReactContext;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.http.cookie.SM;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ForwardingCookieHandler extends CookieHandler {
    private static final boolean USES_LEGACY_STORE;
    private final ReactContext mContext;
    private CookieManager mCookieManager;
    private final CookieSaver mCookieSaver = new CookieSaver();

    static {
        USES_LEGACY_STORE = Build.VERSION.SDK_INT < 21;
    }

    public ForwardingCookieHandler(ReactContext context) {
        this.mContext = context;
    }

    @Override // java.net.CookieHandler
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> headers) throws IOException {
        String cookies = getCookieManager().getCookie(uri.toString());
        return TextUtils.isEmpty(cookies) ? Collections.emptyMap() : Collections.singletonMap(SM.COOKIE, Collections.singletonList(cookies));
    }

    @Override // java.net.CookieHandler
    public void put(URI uri, Map<String, List<String>> headers) throws IOException {
        String url = uri.toString();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (key != null && isCookieHeader(key)) {
                addCookies(url, entry.getValue());
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.facebook.react.modules.network.ForwardingCookieHandler$1] */
    public void clearCookies(final Callback callback) {
        if (USES_LEGACY_STORE) {
            new GuardedResultAsyncTask<Boolean>(this.mContext) { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.1
                /* JADX INFO: Access modifiers changed from: protected */
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.facebook.react.bridge.GuardedResultAsyncTask
                public Boolean doInBackgroundGuarded() {
                    ForwardingCookieHandler.this.getCookieManager().removeAllCookie();
                    ForwardingCookieHandler.this.mCookieSaver.onCookiesModified();
                    return true;
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.facebook.react.bridge.GuardedResultAsyncTask
                public void onPostExecuteGuarded(Boolean result) {
                    callback.invoke(result);
                }
            }.execute(new Void[0]);
        } else {
            clearCookiesAsync(callback);
        }
    }

    private void clearCookiesAsync(final Callback callback) {
        getCookieManager().removeAllCookies(new ValueCallback<Boolean>() { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.2
            @Override // android.webkit.ValueCallback
            public void onReceiveValue(Boolean value) {
                ForwardingCookieHandler.this.mCookieSaver.onCookiesModified();
                callback.invoke(value);
            }
        });
    }

    public void destroy() {
        if (USES_LEGACY_STORE) {
            getCookieManager().removeExpiredCookie();
            this.mCookieSaver.persistCookies();
        }
    }

    private void addCookies(final String url, final List<String> cookies) {
        if (USES_LEGACY_STORE) {
            runInBackground(new Runnable() { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.3
                @Override // java.lang.Runnable
                public void run() {
                    for (String cookie : cookies) {
                        ForwardingCookieHandler.this.getCookieManager().setCookie(url, cookie);
                    }
                    ForwardingCookieHandler.this.mCookieSaver.onCookiesModified();
                }
            });
            return;
        }
        for (String cookie : cookies) {
            addCookieAsync(url, cookie);
        }
        this.mCookieSaver.onCookiesModified();
    }

    @TargetApi(21)
    private void addCookieAsync(String url, String cookie) {
        getCookieManager().setCookie(url, cookie, null);
    }

    private static boolean isCookieHeader(String name) {
        return name.equalsIgnoreCase("Set-cookie") || name.equalsIgnoreCase("Set-cookie2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.network.ForwardingCookieHandler$4] */
    public void runInBackground(final Runnable runnable) {
        new GuardedAsyncTask<Void, Void>(this.mContext) { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.react.bridge.GuardedAsyncTask
            public void doInBackgroundGuarded(Void... params) {
                runnable.run();
            }
        }.execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CookieManager getCookieManager() {
        if (this.mCookieManager == null) {
            possiblyWorkaroundSyncManager(this.mContext);
            this.mCookieManager = CookieManager.getInstance();
            if (USES_LEGACY_STORE) {
                this.mCookieManager.removeExpiredCookie();
            }
        }
        return this.mCookieManager;
    }

    private static void possiblyWorkaroundSyncManager(Context context) {
        if (USES_LEGACY_STORE) {
            CookieSyncManager syncManager = CookieSyncManager.createInstance(context);
            syncManager.sync();
        }
    }

    private class CookieSaver {
        private final Handler mHandler;

        public CookieSaver() {
            this.mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.CookieSaver.1
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message msg) {
                    if (msg.what != 1) {
                        return false;
                    }
                    CookieSaver.this.persistCookies();
                    return true;
                }
            });
        }

        public void onCookiesModified() {
            if (ForwardingCookieHandler.USES_LEGACY_STORE) {
                this.mHandler.sendEmptyMessageDelayed(1, 30000L);
            }
        }

        public void persistCookies() {
            this.mHandler.removeMessages(1);
            ForwardingCookieHandler.this.runInBackground(new Runnable() { // from class: com.facebook.react.modules.network.ForwardingCookieHandler.CookieSaver.2
                @Override // java.lang.Runnable
                public void run() {
                    if (!ForwardingCookieHandler.USES_LEGACY_STORE) {
                        CookieSaver.this.flush();
                    } else {
                        CookieSyncManager syncManager = CookieSyncManager.getInstance();
                        syncManager.sync();
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        @TargetApi(21)
        public void flush() {
            ForwardingCookieHandler.this.getCookieManager().flush();
        }
    }
}
