package com.baidu.cloud.media.player.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.baidu.uaq.agent.android.AgentConfig;
import com.baidu.uaq.agent.android.UAQ;
import com.baidu.uaq.agent.android.customtransmission.APMAgent;
import com.baidu.uaq.agent.android.customtransmission.APMUploadConfigure;
import com.baidu.uaq.agent.android.customtransmission.APMUploadHandler;
import com.baidu.uaq.agent.android.customtransmission.MergeBlockCallBack;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static b d;
    private a a;
    private APMAgent e;
    private final APMUploadHandler f;
    private final APMUploadHandler g;
    private long b = 0;
    private ExecutorService c = null;
    private MergeBlockCallBack h = new MergeBlockCallBack() { // from class: com.baidu.cloud.media.player.a.b.3
    };
    private MergeBlockCallBack i = new MergeBlockCallBack() { // from class: com.baidu.cloud.media.player.a.b.4
    };

    private b(Context context) {
        this.a = new a(context);
        d.a().a(context);
        this.e = UAQ.getInstance().setConfig(new AgentConfig.Builder().APIKey("792a180ca09d496d9cf1ec1469fcfa4a").usePersistentUUID(true).reportCrashes(true).build()).startAPM(context.getApplicationContext());
        APMUploadConfigure aPMUploadConfigure = new APMUploadConfigure(APMUploadConfigure.APMUPLOADNAME, null, this.i);
        aPMUploadConfigure.setInterval4g(60);
        aPMUploadConfigure.setIntervalWifi(15);
        aPMUploadConfigure.setMaxbytes4g(204800, 86400);
        aPMUploadConfigure.setMaxbyteswifi(0, 86400);
        aPMUploadConfigure.enableRetransmission(true);
        HashMap<String, String> map = new HashMap<>();
        map.put(HTTP.CONTENT_TYPE, "application/json");
        map.put(HTTP.CONTENT_ENCODING, "deflate");
        aPMUploadConfigure.setHeaderMap(map);
        APMUploadConfigure aPMUploadConfigure2 = new APMUploadConfigure("userOperation", "https://drm.media.baidubce.com:8888/v2/sdk/player", this.h);
        aPMUploadConfigure2.setInterval4g(60);
        aPMUploadConfigure2.setIntervalWifi(15);
        aPMUploadConfigure2.setMaxbytes4g(204800, 86400);
        aPMUploadConfigure2.setMaxbyteswifi(0, 86400);
        aPMUploadConfigure2.enableRetransmission(true);
        HashMap<String, String> map2 = new HashMap<>();
        map2.put(HTTP.CONTENT_TYPE, "application/json");
        map2.put(HTTP.CONTENT_ENCODING, "gzip");
        aPMUploadConfigure2.setHeaderMap(map2);
        this.g = this.e.addUploadConfigure(aPMUploadConfigure2);
        this.f = this.e.addUploadConfigure(aPMUploadConfigure);
    }

    public static b a(Context context) {
        if (d == null) {
            d = new b(context);
        }
        return d;
    }

    private void a(String str, JSONObject jSONObject) throws JSONException {
        jSONObject.put("type", str);
        jSONObject.put("time", System.currentTimeMillis());
        if (!TextUtils.isEmpty(this.a.b())) {
            jSONObject.put("session", this.a.b());
        }
        final String string = jSONObject.toString();
        Runnable runnable = new Runnable() { // from class: com.baidu.cloud.media.player.a.b.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    b.this.e.addLogWithHandler(b.this.g, string);
                } catch (Exception e) {
                    Log.d("APMEventHandle", Constants.MAIN_VERSION_TAG + e.getMessage());
                }
            }
        };
        if (this.c == null || this.c.isShutdown()) {
            this.c = Executors.newSingleThreadExecutor();
        }
        this.c.execute(runnable);
    }

    private void b(String str, JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("baseInfo", this.a.a());
            jSONObject2.put("eventName", str);
            jSONObject2.put("eventInfo", jSONObject);
            final String string = jSONObject2.toString();
            Runnable runnable = new Runnable() { // from class: com.baidu.cloud.media.player.a.b.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        b.this.e.addLogWithHandler(b.this.f, string);
                    } catch (Exception e) {
                        Log.d("APMEventHandle", Constants.MAIN_VERSION_TAG + e.getMessage());
                    }
                }
            };
            if (this.c == null || this.c.isShutdown()) {
                this.c = Executors.newSingleThreadExecutor();
            }
            this.c.execute(runnable);
        } catch (Exception e) {
            Log.d("APMEventHandle", Constants.MAIN_VERSION_TAG + e.getMessage());
        }
    }

    public void a() {
        try {
            a("dealloc", new JSONObject());
        } catch (Exception e) {
            Log.d("APMEventHandle", "release " + e.getMessage());
        }
        this.e.stopAPM();
        d = null;
    }

    public void a(float f) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("position", f);
            a("pause", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onUserPause " + e.getMessage());
        }
    }

    public void a(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("time", System.currentTimeMillis());
            jSONObject.put("duration", i);
            b("firstBufferingEnd", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onFirstBufferEnd " + e.getMessage());
        }
    }

    public void a(int i, int i2) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_ERROR, i);
            jSONObject.put("errorInfo", i2);
            jSONObject2.put("time", System.currentTimeMillis());
            jSONObject2.put("detail", "what=" + i + ";extra=" + i2);
            a(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_ERROR, jSONObject);
            b("playFail", jSONObject2);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onPlayFail " + e.getMessage());
        }
    }

    public void a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("videoUrl", str);
            a("init", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "release " + e.getMessage());
        }
        this.a.a(str);
    }

    public void a(String str, String str2, String str3) {
        this.a.a(str, str2, str3);
    }

    public void a(JSONObject jSONObject) {
        try {
            a("play", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onUserPlay " + e.getMessage());
        }
    }

    public void b() {
        JSONObject jSONObject = new JSONObject();
        try {
            this.b = System.currentTimeMillis();
            jSONObject.put("time", this.b);
            b("bufferingStart", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onBufferingStart " + e.getMessage());
        }
    }

    public void b(int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("time", System.currentTimeMillis());
            jSONObject.put("speed", i);
            b("networkSpeed", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onNetworkSpeedReport " + e.getMessage());
        }
    }

    public void b(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("time", System.currentTimeMillis());
            jSONObject.put("cdnIp", str);
            b("updateCdn", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onUpdateCdn " + e.getMessage());
        }
    }

    public void b(JSONObject jSONObject) {
        try {
            a(MessageKey.MSG_ACCEPT_TIME_END, jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onUserPlayEnd " + e.getMessage());
        }
    }

    public void c() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.b <= 0) {
                Log.d("APMEventHandle", "onBufferingEnd error: need invoke onBufferingStart first");
            } else {
                long jCurrentTimeMillis = System.currentTimeMillis();
                jSONObject.put("time", jCurrentTimeMillis);
                jSONObject.put("duration", jCurrentTimeMillis - this.b);
                this.b = 0L;
                b("bufferingEnd", jSONObject);
            }
        } catch (Exception e) {
            Log.d("APMEventHandle", "onBufferingEnd " + e.getMessage());
        }
    }

    void c(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("crashInfo", str);
            a("crash", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onPlayerCrash " + e.getMessage());
        }
    }

    public void c(JSONObject jSONObject) {
        try {
            a("seek", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onUserSeek " + e.getMessage());
        }
    }

    public void d() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("time", System.currentTimeMillis());
            b("keepPlaying", jSONObject);
        } catch (Exception e) {
            Log.d("APMEventHandle", "onPlayCount " + e.getMessage());
        }
    }
}
