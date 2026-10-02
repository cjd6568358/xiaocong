package com.facebook.react.devsupport;

import android.os.Handler;
import android.text.TextUtils;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DevServerHelper {
    private static final String SOURCE_MAP_URL_FORMAT = "http://%s/%s.bundle?platform=android&dev=%s&hot=%s&minify=%s".replaceFirst("\\.bundle", ".map");
    private OkHttpClient mOnChangePollingClient;
    private boolean mOnChangePollingEnabled;
    private OnServerContentChangeListener mOnServerContentChangeListener;
    private final Handler mRestartOnChangePollingHandler;
    private final DevInternalSettings mSettings;

    public interface OnServerContentChangeListener {
        void onServerContentChanged();
    }

    public interface PackagerStatusCallback {
    }

    private String getDebugServerHost() {
        String hostFromSettings = this.mSettings.getDebugServerHost();
        if (!TextUtils.isEmpty(hostFromSettings)) {
            return (String) Assertions.assertNotNull(hostFromSettings);
        }
        String host = AndroidInfoHelpers.getServerHost();
        if (host.equals("localhost:8081")) {
            FLog.w("React", "You seem to be running on device. Run 'adb reverse tcp:8081 tcp:8081' to forward the debug server's port to the device.");
        }
        return host;
    }

    public void startPollingOnChangeEndpoint(OnServerContentChangeListener onServerContentChangeListener) {
        if (!this.mOnChangePollingEnabled) {
            this.mOnChangePollingEnabled = true;
            this.mOnServerContentChangeListener = onServerContentChangeListener;
            this.mOnChangePollingClient = new OkHttpClient.Builder().connectionPool(new ConnectionPool(1, 120000L, TimeUnit.MINUTES)).connectTimeout(5000L, TimeUnit.MILLISECONDS).build();
            enqueueOnChangeEndpointLongPolling();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnChangePollingResponse(boolean didServerContentChanged) {
        if (this.mOnChangePollingEnabled) {
            if (didServerContentChanged) {
                UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.devsupport.DevServerHelper.7
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DevServerHelper.this.mOnServerContentChangeListener != null) {
                            DevServerHelper.this.mOnServerContentChangeListener.onServerContentChanged();
                        }
                    }
                });
            }
            enqueueOnChangeEndpointLongPolling();
        }
    }

    private void enqueueOnChangeEndpointLongPolling() {
        Request request = new Request.Builder().url(createOnChangeEndpointUrl()).tag(this).build();
        ((OkHttpClient) Assertions.assertNotNull(this.mOnChangePollingClient)).newCall(request).enqueue(new Callback() { // from class: com.facebook.react.devsupport.DevServerHelper.8
            public void onFailure(Call call, IOException e) {
                if (DevServerHelper.this.mOnChangePollingEnabled) {
                    FLog.d("React", "Error while requesting /onchange endpoint", e);
                    DevServerHelper.this.mRestartOnChangePollingHandler.postDelayed(new Runnable() { // from class: com.facebook.react.devsupport.DevServerHelper.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            DevServerHelper.this.handleOnChangePollingResponse(false);
                        }
                    }, 5000L);
                }
            }

            public void onResponse(Call call, Response response) throws IOException {
                DevServerHelper.this.handleOnChangePollingResponse(response.code() == 205);
            }
        });
    }

    private String createOnChangeEndpointUrl() {
        return String.format(Locale.US, "http://%s/onchange", getDebugServerHost());
    }
}
