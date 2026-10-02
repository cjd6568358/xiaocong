package com.baidu.cloud.media.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Rect;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.baidu.cloud.media.download.LocalHlsSec;
import com.baidu.cloud.media.player.misc.IMediaDataSource;
import com.tencent.android.tpush.common.Constants;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BDCloudMediaPlayer extends AbstractMediaPlayer {
    public static final int DECODE_AUTO = 0;
    public static final int DECODE_SW = 1;
    private volatile boolean A;
    private Timer B;
    private Context C;
    private String D;
    private int G;
    private OnControlMessageListener H;
    private OnNativeInvokeListener I;
    private OnMediaCodecSelectListener J;
    private SurfaceHolder f;
    private b g;
    private PowerManager.WakeLock h;
    private boolean i;
    private boolean j;
    private int k;
    private int l;
    private int m;
    private int mListenerContext;
    private long mNativeAndroidIO;
    private long mNativeMediaDataSource;
    private long mNativeMediaPlayer;
    private int mNativeSurfaceTexture;
    private int n;
    private String o;
    private int p;
    private int q;
    private long r;
    private JSONArray s;
    private Date t;
    private long u;
    private long v;
    private int w;
    private int x;
    private int y;
    private boolean z;
    private static final String a = BDCloudMediaPlayer.class.getName();
    private static String b = Constants.MAIN_VERSION_TAG;
    private static boolean c = false;
    private static boolean d = false;
    private static String e = null;
    private static final BDCloudLibLoader E = new BDCloudLibLoader() { // from class: com.baidu.cloud.media.player.BDCloudMediaPlayer.1
        @Override // com.baidu.cloud.media.player.BDCloudLibLoader
        public void loadLibrary(String str) throws SecurityException, UnsatisfiedLinkError {
            System.loadLibrary(str);
        }
    };
    private static volatile boolean F = false;

    private interface OnControlMessageListener {
        String onControlResolveSegmentUrl(int i);
    }

    private interface OnMediaCodecSelectListener {
        String onMediaCodecSelect(IMediaPlayer iMediaPlayer, String str, int i, int i2);
    }

    private interface OnNativeInvokeListener {
        public static final String ARG_ERROR = "error";
        public static final String ARG_FAMILIY = "family";
        public static final String ARG_FD = "fd";
        public static final String ARG_HTTP_CODE = "http_code";
        public static final String ARG_IP = "ip";
        public static final String ARG_OFFSET = "offset";
        public static final String ARG_PORT = "port";
        public static final String ARG_RETRY_COUNTER = "retry_counter";
        public static final String ARG_SEGMENT_INDEX = "segment_index";
        public static final String ARG_URL = "url";
        public static final int CTRL_DID_TCP_OPEN = 131074;
        public static final int CTRL_WILL_CONCAT_RESOLVE_SEGMENT = 131079;
        public static final int CTRL_WILL_HTTP_OPEN = 131075;
        public static final int CTRL_WILL_LIVE_OPEN = 131077;
        public static final int CTRL_WILL_TCP_OPEN = 131073;
        public static final int EVENT_DID_HTTP_OPEN = 2;
        public static final int EVENT_DID_HTTP_SEEK = 4;
        public static final int EVENT_WILL_HTTP_OPEN = 1;
        public static final int EVENT_WILL_HTTP_SEEK = 3;

        boolean onNativeInvoke(int i, Bundle bundle);
    }

    private static class a implements OnMediaCodecSelectListener {
        public static final a a = new a();

        private a() {
        }

        @Override // com.baidu.cloud.media.player.BDCloudMediaPlayer.OnMediaCodecSelectListener
        public String onMediaCodecSelect(IMediaPlayer iMediaPlayer, String str, int i, int i2) {
            com.baidu.cloud.media.player.a aVar;
            String[] supportedTypes;
            com.baidu.cloud.media.player.a aVarA;
            if (Build.VERSION.SDK_INT < 16 || TextUtils.isEmpty(str)) {
                return null;
            }
            com.baidu.cloud.media.player.b.a.b(BDCloudMediaPlayer.a, String.format(Locale.US, "onSelectCodec: mime=%s, profile=%d, level=%d", str, Integer.valueOf(i), Integer.valueOf(i2)));
            ArrayList arrayList = new ArrayList();
            int codecCount = MediaCodecList.getCodecCount();
            for (int i3 = 0; i3 < codecCount; i3++) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i3);
                com.baidu.cloud.media.player.b.a.d(BDCloudMediaPlayer.a, String.format(Locale.US, "  found codec: %s", codecInfoAt.getName()));
                if (!codecInfoAt.isEncoder() && (supportedTypes = codecInfoAt.getSupportedTypes()) != null) {
                    for (String str2 : supportedTypes) {
                        if (!TextUtils.isEmpty(str2)) {
                            com.baidu.cloud.media.player.b.a.d(BDCloudMediaPlayer.a, String.format(Locale.US, "    mime: %s", str2));
                            if (str2.equalsIgnoreCase(str) && (aVarA = com.baidu.cloud.media.player.a.a(codecInfoAt, str)) != null) {
                                arrayList.add(aVarA);
                                com.baidu.cloud.media.player.b.a.b(BDCloudMediaPlayer.a, String.format(Locale.US, "candidate codec: %s rank=%d", codecInfoAt.getName(), Integer.valueOf(aVarA.b)));
                                aVarA.a(str);
                            }
                        }
                    }
                }
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            com.baidu.cloud.media.player.a aVar2 = (com.baidu.cloud.media.player.a) arrayList.get(0);
            Iterator it = arrayList.iterator();
            while (true) {
                aVar = aVar2;
                if (!it.hasNext()) {
                    break;
                }
                aVar2 = (com.baidu.cloud.media.player.a) it.next();
                if (aVar2.b <= aVar.b) {
                    aVar2 = aVar;
                }
            }
            if (aVar.b < 600) {
                Log.w(BDCloudMediaPlayer.a, String.format(Locale.US, "unaccetable codec: %s", aVar.a.getName()));
                return null;
            }
            com.baidu.cloud.media.player.b.a.b(BDCloudMediaPlayer.a, String.format(Locale.US, "selected codec: %s rank=%d", aVar.a.getName(), Integer.valueOf(aVar.b)));
            return aVar.a.getName();
        }
    }

    private static class b extends Handler {
        private final WeakReference<BDCloudMediaPlayer> a;

        public b(BDCloudMediaPlayer bDCloudMediaPlayer, Looper looper) {
            super(looper);
            this.a = new WeakReference<>(bDCloudMediaPlayer);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            BDCloudMediaPlayer bDCloudMediaPlayer = this.a.get();
            if (bDCloudMediaPlayer == null || bDCloudMediaPlayer.mNativeMediaPlayer == 0) {
                com.baidu.cloud.media.player.b.a.c(BDCloudMediaPlayer.a, "BDCloudMediaPlayer went away with unhandled events");
            }
            switch (message.what) {
                case 0:
                case 2:
                    break;
                case 1:
                    bDCloudMediaPlayer.setOption(4, "seek-at-start", 0L);
                    bDCloudMediaPlayer.h();
                    bDCloudMediaPlayer.a();
                    break;
                case 3:
                    bDCloudMediaPlayer.a(false);
                    bDCloudMediaPlayer.d();
                    bDCloudMediaPlayer.b();
                    break;
                case 4:
                    long j = message.arg1;
                    if (j < 0) {
                        j = 0;
                    }
                    long duration = bDCloudMediaPlayer.getDuration();
                    long j2 = duration > 0 ? (j * 100) / duration : 0L;
                    if (j2 >= 100) {
                        j2 = 100;
                    }
                    bDCloudMediaPlayer.a((int) j2);
                    break;
                case 5:
                    bDCloudMediaPlayer.c();
                    break;
                case 6:
                    bDCloudMediaPlayer.k = message.arg1;
                    bDCloudMediaPlayer.l = message.arg2;
                    bDCloudMediaPlayer.a(bDCloudMediaPlayer.k, bDCloudMediaPlayer.l, bDCloudMediaPlayer.m, bDCloudMediaPlayer.n);
                    break;
                case 99:
                    if (message.obj != null) {
                        bDCloudMediaPlayer.a(new BDTimedText(new Rect(0, 0, 1, 1), (String) message.obj));
                    } else {
                        bDCloudMediaPlayer.a((BDTimedText) null);
                    }
                    break;
                case 100:
                    com.baidu.cloud.media.player.b.a.a(BDCloudMediaPlayer.a, "Error (" + message.arg1 + "," + message.arg2 + ")");
                    bDCloudMediaPlayer.c(message.arg1, message.arg2);
                    bDCloudMediaPlayer.d();
                    if (!bDCloudMediaPlayer.a(message.arg1, message.arg2)) {
                        bDCloudMediaPlayer.b();
                    }
                    bDCloudMediaPlayer.a(false);
                    break;
                case 200:
                    switch (message.arg1) {
                        case 3:
                            com.baidu.cloud.media.player.b.a.b(BDCloudMediaPlayer.a, "Info: MEDIA_INFO_VIDEO_RENDERING_START\n");
                            break;
                    }
                    bDCloudMediaPlayer.d(message.arg1, message.arg2);
                    bDCloudMediaPlayer.b(message.arg1, message.arg2);
                    break;
                case 10001:
                    bDCloudMediaPlayer.m = message.arg1;
                    bDCloudMediaPlayer.n = message.arg2;
                    bDCloudMediaPlayer.a(bDCloudMediaPlayer.k, bDCloudMediaPlayer.l, bDCloudMediaPlayer.m, bDCloudMediaPlayer.n);
                    break;
                case 77824:
                    bDCloudMediaPlayer.a((Bundle) message.obj);
                    break;
                default:
                    com.baidu.cloud.media.player.b.a.a(BDCloudMediaPlayer.a, "Unknown message type " + message.what);
                    break;
            }
        }
    }

    public BDCloudMediaPlayer(Context context) {
        this(context, E);
    }

    public BDCloudMediaPlayer(Context context, BDCloudLibLoader bDCloudLibLoader) {
        this.h = null;
        this.p = 15000000;
        this.q = 0;
        this.r = 0L;
        this.s = new JSONArray();
        this.t = null;
        this.u = -1L;
        this.v = 0L;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.A = true;
        this.B = null;
        this.C = null;
        this.D = null;
        this.G = 0;
        this.C = context.getApplicationContext();
        a(bDCloudLibLoader);
    }

    private native String _getAudioCodecInfo();

    private static native String _getColorFormatName(int i);

    private native int _getLoopCount();

    private native Bundle _getMediaMeta();

    private native float _getPropertyFloat(int i, float f);

    private native long _getPropertyLong(int i, long j);

    private native String _getVideoCodecInfo();

    private native void _pause() throws IllegalStateException;

    private native void _prepareAsync() throws IllegalStateException;

    private native void _release();

    private native void _reset();

    private native void _seekTo(long j) throws IllegalStateException;

    private native void _setDataSource(IMediaDataSource iMediaDataSource) throws IllegalStateException, SecurityException, IllegalArgumentException;

    private native void _setDataSource(String str, String[] strArr, String[] strArr2) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException;

    private native void _setDataSourceFd(int i) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException;

    private native void _setLoopCount(int i);

    private native void _setOption(int i, String str, long j);

    private native void _setOption(int i, String str, String str2);

    private native void _setPropertyFloat(int i, float f);

    private native void _setPropertyLong(int i, long j);

    private native void _setStreamSelected(int i, boolean z);

    private native void _setVideoSurface(Surface surface);

    private native void _start() throws IllegalStateException;

    private native void _stop() throws IllegalStateException;

    private void a(BDCloudLibLoader bDCloudLibLoader) {
        loadLibrariesOnce(bDCloudLibLoader);
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            this.g = new b(this, looperMyLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.g = new b(this, mainLooper);
            } else {
                this.g = null;
            }
        }
        native_init(new WeakReference(this));
        setDecodeMode(0);
        setOption(4, "start-on-prepared", 0L);
        setOption(4, "max-fps", 30L);
        setOption(4, "framedrop", 1L);
        setOption(4, "framechasing", 0L);
        setOption(4, "soundtouch", 1L);
        setOption(4, "subtitle", 1L);
        setOption(4, "enable-accurate-seek", 1L);
        setLogEnabled(false);
    }

    private void a(FileDescriptor fileDescriptor, long j, long j2) throws IllegalStateException, IOException, IllegalArgumentException {
        setDataSource(fileDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        if (this.h != null) {
            if (z && !this.h.isHeld()) {
                this.h.acquire();
            } else if (!z && this.h.isHeld()) {
                this.h.release();
            }
        }
        this.j = z;
        k();
    }

    private boolean a(String str) {
        if (str == null) {
            return false;
        }
        return str.startsWith("file://") || str.startsWith("/");
    }

    private native String getCdnIp();

    public static String getColorFormatName(int i) {
        return _getColorFormatName(i);
    }

    public static String getSdkVersion() {
        return "2.2.1";
    }

    private void j() {
        setOption(1, "reconnect", 1L);
        setOption(1, "timeout", this.p);
        native_setup();
    }

    private void k() {
        if (this.f != null) {
            this.f.setKeepScreenOn(this.i && this.j);
        }
    }

    public static void loadLibrariesOnce(BDCloudLibLoader bDCloudLibLoader) {
        synchronized (BDCloudMediaPlayer.class) {
            if (!F) {
                if (bDCloudLibLoader == null) {
                    bDCloudLibLoader = E;
                }
                try {
                    bDCloudLibLoader.loadLibrary("pcdn");
                    bDCloudLibLoader.loadLibrary("stlport_shared");
                    bDCloudLibLoader.loadLibrary("bdsoundutils");
                    bDCloudLibLoader.loadLibrary("bdplayer");
                    F = true;
                } catch (Throwable th) {
                    bDCloudLibLoader.loadLibrary("stlport_shared");
                    bDCloudLibLoader.loadLibrary("bdsoundutils");
                    bDCloudLibLoader.loadLibrary("bdplayer");
                    F = true;
                    throw th;
                }
            }
        }
    }

    private native void native_finalize();

    private native void native_init(Object obj);

    private native void native_message_loop(Object obj);

    private static native void native_profileBegin(String str);

    private static native void native_profileEnd();

    private static native void native_setLogLevel(int i);

    private native void native_setup();

    private static boolean onNativeInvoke(Object obj, int i, Bundle bundle) {
        com.baidu.cloud.media.player.b.a.a(a, "onNativeInvoke %x", Integer.valueOf(i));
        if (obj == null || !(obj instanceof WeakReference)) {
            throw new IllegalStateException("<null weakThiz>.onNativeInvoke()");
        }
        BDCloudMediaPlayer bDCloudMediaPlayer = (BDCloudMediaPlayer) ((WeakReference) obj).get();
        if (bDCloudMediaPlayer == null) {
            throw new IllegalStateException("<null weakPlayer>.onNativeInvoke()");
        }
        OnNativeInvokeListener onNativeInvokeListener = bDCloudMediaPlayer.I;
        if (onNativeInvokeListener != null && onNativeInvokeListener.onNativeInvoke(i, bundle)) {
            return true;
        }
        switch (i) {
            case OnNativeInvokeListener.CTRL_WILL_CONCAT_RESOLVE_SEGMENT /* 131079 */:
                OnControlMessageListener onControlMessageListener = bDCloudMediaPlayer.H;
                if (onControlMessageListener == null) {
                    return false;
                }
                int i2 = bundle.getInt(OnNativeInvokeListener.ARG_SEGMENT_INDEX, -1);
                if (i2 < 0) {
                    throw new InvalidParameterException("onNativeInvoke(invalid segment index)");
                }
                String strOnControlResolveSegmentUrl = onControlMessageListener.onControlResolveSegmentUrl(i2);
                if (strOnControlResolveSegmentUrl == null) {
                    throw new RuntimeException(new IOException("onNativeInvoke() = <NULL newUrl>"));
                }
                bundle.putString("url", strOnControlResolveSegmentUrl);
                return true;
            default:
                return false;
        }
    }

    private static String onSelectCodec(Object obj, String str, int i, int i2) {
        if (obj == null || !(obj instanceof WeakReference)) {
            return null;
        }
        BDCloudMediaPlayer bDCloudMediaPlayer = (BDCloudMediaPlayer) ((WeakReference) obj).get();
        if (bDCloudMediaPlayer == null) {
            return null;
        }
        OnMediaCodecSelectListener onMediaCodecSelectListener = bDCloudMediaPlayer.J;
        if (onMediaCodecSelectListener == null) {
            onMediaCodecSelectListener = a.a;
        }
        return onMediaCodecSelectListener.onMediaCodecSelect(bDCloudMediaPlayer, str, i, i2);
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        BDCloudMediaPlayer bDCloudMediaPlayer;
        if (obj == null || (bDCloudMediaPlayer = (BDCloudMediaPlayer) ((WeakReference) obj).get()) == null) {
            return;
        }
        if (i == 200 && i2 == 2) {
            bDCloudMediaPlayer.start();
        }
        if (bDCloudMediaPlayer.g != null) {
            bDCloudMediaPlayer.g.sendMessage(bDCloudMediaPlayer.g.obtainMessage(i, i2, i3, obj2));
        }
    }

    public static void setAK(String str) {
        b = str;
    }

    public static void setLocalCacheEnabled(boolean z, String str) {
        d = z;
        e = str;
        if (d && TextUtils.isEmpty(e)) {
            throw new IllegalArgumentException("cachePath can not be empty if enabled local cache");
        }
    }

    public static void setP2PEnabled(boolean z) {
        c = z;
    }

    protected void a(long j, long j2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("from", j);
            jSONObject.put("to", j2);
            com.baidu.cloud.media.player.a.b.a(this.C).c(jSONObject);
        } catch (Exception e2) {
            Log.d(a, "APMEventHandle exception:" + e2.getMessage());
        }
    }

    protected void c(int i, int i2) {
        try {
            com.baidu.cloud.media.player.a.b.a(this.C).a(i, i2);
        } catch (Exception e2) {
            Log.d(a, "APMEventHandle exception:" + e2.getMessage());
        }
    }

    protected void d() {
        String string = null;
        if (this.A) {
            return;
        }
        this.A = true;
        try {
            if (this.s != null) {
                string = this.s.toString();
                this.s = new JSONArray();
            }
            if (this.B != null) {
                this.B.cancel();
                this.B = null;
            }
            if (this.r != 0) {
                int iCurrentTimeMillis = ((int) (System.currentTimeMillis() - this.r)) / 1000;
                this.r = 0L;
                this.q = iCurrentTimeMillis + this.q;
            }
            int i = this.q;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("playInterval", i);
            jSONObject.put("buffering", new JSONArray(string));
            com.baidu.cloud.media.player.a.b.a(this.C).b(jSONObject);
            this.q = 0;
        } catch (Exception e2) {
            Log.d(a, "APMEventHandle exception:" + e2.getMessage());
        }
    }

    protected void d(int i, int i2) {
        try {
            if (i == 701) {
                this.t = new Date();
                this.u = getCurrentPosition();
                com.baidu.cloud.media.player.a.b.a(this.C).b();
            } else {
                if (i != 702) {
                    return;
                }
                com.baidu.cloud.media.player.a.b.a(this.C).c();
                if (this.t != null) {
                    Date date = new Date();
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("startPosition", this.u / 1000.0f);
                    jSONObject.put("endPosition", getCurrentPosition() / 1000.0f);
                    jSONObject.put("startTimestamp", this.t.getTime());
                    jSONObject.put("endTimestamp", date.getTime());
                    this.s.put(jSONObject);
                    this.t = null;
                    this.u = -1L;
                }
            }
        } catch (Exception e2) {
            Log.d(a, "buffer stat exception :" + e2.getMessage());
        }
    }

    public void deselectTrack(int i) {
        _setStreamSelected(i, false);
    }

    protected void e() {
        this.r = System.currentTimeMillis();
        this.v = System.currentTimeMillis();
        com.baidu.cloud.media.player.a.b.a(this.C).a(this.o);
        com.baidu.cloud.media.player.a.b.a(this.C).a("2.2.1", "hw", b);
    }

    protected void f() {
        if (this.r != 0) {
            int iCurrentTimeMillis = ((int) (System.currentTimeMillis() - this.r)) / 1000;
            this.r = 0L;
            this.q = iCurrentTimeMillis + this.q;
        }
        try {
            if (this.B != null) {
                this.B.cancel();
                this.B = null;
            }
            com.baidu.cloud.media.player.a.b.a(this.C).a(getCurrentPosition() / 1000.0f);
        } catch (Exception e2) {
            Log.d(a, "APMEventHandle exception:" + e2.getMessage());
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
        native_finalize();
    }

    protected void g() {
        this.A = false;
        if (this.r == 0) {
            this.r = System.currentTimeMillis();
        }
        try {
            if (this.B != null) {
                this.B.cancel();
                this.B = null;
            }
            this.B = new Timer();
            this.B.schedule(new TimerTask() { // from class: com.baidu.cloud.media.player.BDCloudMediaPlayer.2
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    try {
                        com.baidu.cloud.media.player.a.b.a(BDCloudMediaPlayer.this.C).d();
                        long downloadSpeed = BDCloudMediaPlayer.this.getDownloadSpeed();
                        if (downloadSpeed > 0) {
                            com.baidu.cloud.media.player.a.b.a(BDCloudMediaPlayer.this.C).b((int) downloadSpeed);
                        }
                    } catch (Exception e2) {
                        Log.d(BDCloudMediaPlayer.a, "APMEventHandle exception:" + e2.getMessage());
                    }
                }
            }, 0L, 60000L);
        } catch (Exception e2) {
            Log.d(a, Constants.MAIN_VERSION_TAG + e2.getMessage());
        }
    }

    public long getAsyncStatisticBufBackwards() {
        return _getPropertyLong(20201, 0L);
    }

    public long getAsyncStatisticBufCapacity() {
        return _getPropertyLong(20203, 0L);
    }

    public long getAsyncStatisticBufForwards() {
        return _getPropertyLong(20202, 0L);
    }

    public long getAudioCachedBytes() {
        return _getPropertyLong(20008, 0L);
    }

    public long getAudioCachedDuration() {
        return _getPropertyLong(20006, 0L);
    }

    public long getAudioCachedPackets() {
        return _getPropertyLong(20010, 0L);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public native int getAudioSessionId();

    public long getBitRate() {
        return _getPropertyLong(20100, 0L);
    }

    public long getCacheStatisticBufForwards() {
        return _getPropertyLong(20206, 0L);
    }

    public long getCacheStatisticCountBytes() {
        return _getPropertyLong(20208, 0L);
    }

    public long getCacheStatisticFilePos() {
        return _getPropertyLong(20207, 0L);
    }

    public long getCacheStatisticPhysicalPos() {
        return _getPropertyLong(20205, 0L);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public native long getCurrentPosition();

    public native int getCurrentVariantIndex();

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public String getDataSource() {
        return this.o;
    }

    public int getDecodeMode() {
        return this.G;
    }

    public long getDownloadSpeed() {
        return _getPropertyLong(20200, 0L);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public native long getDuration();

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public c getMediaInfo() {
        c cVar = new c();
        cVar.a = "bdcloudplayer";
        String str_getVideoCodecInfo = _getVideoCodecInfo();
        if (!TextUtils.isEmpty(str_getVideoCodecInfo)) {
            String[] strArrSplit = str_getVideoCodecInfo.split(",");
            if (strArrSplit.length >= 2) {
                cVar.b = strArrSplit[0];
                cVar.c = strArrSplit[1];
            } else if (strArrSplit.length >= 1) {
                cVar.b = strArrSplit[0];
                cVar.c = Constants.MAIN_VERSION_TAG;
            }
        }
        String str_getAudioCodecInfo = _getAudioCodecInfo();
        if (!TextUtils.isEmpty(str_getAudioCodecInfo)) {
            String[] strArrSplit2 = str_getAudioCodecInfo.split(",");
            if (strArrSplit2.length >= 2) {
                cVar.d = strArrSplit2[0];
                cVar.e = strArrSplit2[1];
            } else if (strArrSplit2.length >= 1) {
                cVar.d = strArrSplit2[0];
                cVar.e = Constants.MAIN_VERSION_TAG;
            }
        }
        try {
            cVar.f = com.baidu.cloud.media.player.b.a(_getMediaMeta());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return cVar;
    }

    public Bundle getMediaMeta() {
        return _getMediaMeta();
    }

    public long getSeekLoadDuration() {
        return _getPropertyLong(20300, 0L);
    }

    public int getSelectedTrack(int i) {
        switch (i) {
            case 1:
                return (int) _getPropertyLong(20001, -1L);
            case 2:
                return (int) _getPropertyLong(20002, -1L);
            case 3:
                return (int) _getPropertyLong(20011, -1L);
            default:
                return -1;
        }
    }

    public float getSpeed(float f) {
        return _getPropertyFloat(Constants.CODE_PERMISSIONS_ERROR, 0.0f);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public com.baidu.cloud.media.player.misc.a[] getTrackInfo() {
        com.baidu.cloud.media.player.b bVarA;
        Bundle mediaMeta = getMediaMeta();
        if (mediaMeta == null || (bVarA = com.baidu.cloud.media.player.b.a(mediaMeta)) == null || bVarA.f == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (com.baidu.cloud.media.player.b.a aVar : bVarA.f) {
            com.baidu.cloud.media.player.misc.a aVar2 = new com.baidu.cloud.media.player.misc.a(aVar);
            if (aVar.c.equalsIgnoreCase("video")) {
                aVar2.a(1);
            } else if (aVar.c.equalsIgnoreCase("audio")) {
                aVar2.a(2);
            } else if (aVar.c.equalsIgnoreCase("timedtext")) {
                aVar2.a(3);
            }
            arrayList.add(aVar2);
        }
        return (com.baidu.cloud.media.player.misc.a[]) arrayList.toArray(new com.baidu.cloud.media.player.misc.a[arrayList.size()]);
    }

    public long getTrafficStatisticByteCount() {
        return _getPropertyLong(20204, 0L);
    }

    public native String[] getVariantInfo();

    public long getVideoCachedBytes() {
        return _getPropertyLong(20007, 0L);
    }

    public long getVideoCachedDuration() {
        return _getPropertyLong(20005, 0L);
    }

    public long getVideoCachedPackets() {
        return _getPropertyLong(20009, 0L);
    }

    public float getVideoDecodeFramesPerSecond() {
        return _getPropertyFloat(10001, 0.0f);
    }

    public int getVideoDecoder() {
        return (int) _getPropertyLong(20003, 0L);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public int getVideoHeight() {
        return this.l;
    }

    public float getVideoOutputFramesPerSecond() {
        return _getPropertyFloat(10002, 0.0f);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public int getVideoSarDen() {
        return this.n;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public int getVideoSarNum() {
        return this.m;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public int getVideoWidth() {
        return this.k;
    }

    protected void h() {
        try {
            if (this.v > 0) {
                com.baidu.cloud.media.player.a.b.a(this.C).a((int) (System.currentTimeMillis() - this.v));
                this.v = 0L;
            }
            String cdnIp = getCdnIp();
            if (cdnIp != null && !cdnIp.equals(Constants.MAIN_VERSION_TAG)) {
                com.baidu.cloud.media.player.a.b.a(this.C).b(cdnIp);
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("videoUrl", this.o);
            jSONObject.put("videoWidth", getVideoWidth());
            jSONObject.put("videoHeight", getVideoHeight());
            jSONObject.put("playerWidth", 0);
            jSONObject.put("playerHeight", 0);
            jSONObject.put("duration", getDuration());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("maxCacheSize", this.w);
            jSONObject2.put("cachePauseTime", this.x);
            jSONObject2.put("firstBufferingTime", this.y);
            jSONObject2.put("toggleFrameChasing", this.z);
            jSONObject2.put("decodeMode", this.G);
            jSONObject2.put("playbackRate", getSpeed(0.0f));
            jSONObject2.put("enableLooping", isLooping());
            jSONObject.put("settings", jSONObject2);
            com.baidu.cloud.media.player.a.b.a(this.C).a(jSONObject);
        } catch (Exception e2) {
            Log.d(a, "APMEventHandle exception:" + e2.getMessage());
        }
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public boolean isLooping() {
        return _getLoopCount() != 1;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public boolean isPlayable() {
        return true;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public native boolean isPlaying();

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void pause() throws IllegalStateException {
        a(false);
        f();
        _pause();
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void prepareAsync() throws IllegalStateException {
        _prepareAsync();
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void release() {
        a(false);
        d();
        k();
        resetListeners();
        com.baidu.cloud.media.player.a.b.a(this.C).a();
        _release();
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void reset() {
        a(false);
        d();
        _stop();
        _reset();
        this.g.removeCallbacksAndMessages(null);
        this.k = 0;
        this.l = 0;
    }

    @Override // com.baidu.cloud.media.player.AbstractMediaPlayer
    public void resetListeners() {
        super.resetListeners();
        this.J = null;
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void seekTo(long j) throws IllegalStateException {
        a(getCurrentPosition() / 1000, j / 1000);
        _seekTo(j);
    }

    public boolean selectResolutionByIndex(int i) {
        if (getCurrentVariantIndex() == i) {
            Log.d(a, "currentVariantIndex is equals to index setted~" + i);
            return false;
        }
        if (i < 0 || i >= getVariantInfo().length) {
            Log.d(a, "index is not in [0," + getVariantInfo().length + ")");
            return false;
        }
        long currentPosition = getCurrentPosition();
        stop();
        native_setup();
        setOption(1, "reconnect", 1L);
        setOption(1, "timeout", this.p);
        setOption(1, "cache_dir", d ? e : Constants.MAIN_VERSION_TAG);
        if (!TextUtils.isEmpty(this.D)) {
            setOption(1, "headers", this.D);
        }
        selectVariantByIndex(i);
        setInitPlayPosition(currentPosition);
        prepareAsync();
        return true;
    }

    public void selectTrack(int i) {
        _setStreamSelected(i, true);
    }

    public native void selectVariantByIndex(int i);

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setAudioStreamType(int i) {
    }

    public void setBufferSizeInBytes(int i) {
        setOption(4, "buffer-size-in-bytes", i);
    }

    public void setBufferTimeInMs(int i) {
        this.x = i;
        setOption(4, "buffer-time-in-ms", i);
    }

    public native void setCustomizedPlayerIdForHLS(String str);

    public native void setCustomizedPlayerKeyForHLS(String str);

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setDataSource(Context context, Uri uri) throws Throwable {
        setDataSource(context, uri, null);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws Throwable {
        AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor;
        Throwable th;
        String scheme = uri.getScheme();
        if ("file".equals(scheme)) {
            setDataSource(uri.getPath());
            return;
        }
        if ("content".equals(scheme) && "settings".equals(uri.getAuthority()) && (uri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.getDefaultType(uri))) == null) {
            throw new FileNotFoundException("Failed to resolve default ringtone");
        }
        AssetFileDescriptor assetFileDescriptor = null;
        try {
            assetFileDescriptorOpenAssetFileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
            if (assetFileDescriptorOpenAssetFileDescriptor == null) {
                if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                    assetFileDescriptorOpenAssetFileDescriptor.close();
                    return;
                }
                return;
            }
            try {
                if (assetFileDescriptorOpenAssetFileDescriptor.getDeclaredLength() < 0) {
                    setDataSource(assetFileDescriptorOpenAssetFileDescriptor.getFileDescriptor());
                } else {
                    a(assetFileDescriptorOpenAssetFileDescriptor.getFileDescriptor(), assetFileDescriptorOpenAssetFileDescriptor.getStartOffset(), assetFileDescriptorOpenAssetFileDescriptor.getDeclaredLength());
                }
                if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                    assetFileDescriptorOpenAssetFileDescriptor.close();
                }
            } catch (IOException e2) {
                if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                    assetFileDescriptorOpenAssetFileDescriptor.close();
                }
                setDataSource(uri.toString(), map);
            } catch (SecurityException e3) {
                assetFileDescriptor = assetFileDescriptorOpenAssetFileDescriptor;
                if (assetFileDescriptor != null) {
                    assetFileDescriptor.close();
                }
                setDataSource(uri.toString(), map);
            } catch (Throwable th2) {
                th = th2;
                if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                    assetFileDescriptorOpenAssetFileDescriptor.close();
                }
                throw th;
            }
        } catch (IOException e4) {
            assetFileDescriptorOpenAssetFileDescriptor = null;
        } catch (SecurityException e5) {
        } catch (Throwable th3) {
            assetFileDescriptorOpenAssetFileDescriptor = null;
            th = th3;
        }
    }

    @Override // com.baidu.cloud.media.player.AbstractMediaPlayer, com.baidu.cloud.media.player.IMediaPlayer
    public void setDataSource(IMediaDataSource iMediaDataSource) throws IllegalStateException, SecurityException, IllegalArgumentException {
        j();
        _setDataSource(iMediaDataSource);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setDataSource(FileDescriptor fileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        j();
        if (Build.VERSION.SDK_INT >= 12) {
            ParcelFileDescriptor parcelFileDescriptorDup = ParcelFileDescriptor.dup(fileDescriptor);
            try {
                _setDataSourceFd(parcelFileDescriptorDup.getFd());
                return;
            } finally {
                parcelFileDescriptorDup.close();
            }
        }
        try {
            Field declaredField = fileDescriptor.getClass().getDeclaredField("descriptor");
            declaredField.setAccessible(true);
            _setDataSourceFd(declaredField.getInt(fileDescriptor));
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        } catch (NoSuchFieldException e3) {
            throw new RuntimeException(e3);
        }
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setDataSource(String str) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.o = str;
        e();
        j();
        _setDataSource(str, null, null);
        if (!a(str)) {
            setOption(4, "enable_p2p", c ? 1L : 0L);
            setOption(4, "enable_cache", d ? 1L : 0L);
            setOption(1, "cache_dir", d ? e : Constants.MAIN_VERSION_TAG);
            return;
        }
        String string = this.C.getSharedPreferences("__cyberplayer_dl_sec", 0).getString(str.replace("file://", Constants.MAIN_VERSION_TAG), null);
        if (string != null) {
            try {
                String strDecryptStr = LocalHlsSec.decryptStr(this.C, string);
                if (strDecryptStr == null || strDecryptStr.length() <= 0) {
                    return;
                }
                setLocalDecryptKeyForHLS(strDecryptStr);
            } catch (Exception e2) {
                Log.d(a, Constants.MAIN_VERSION_TAG, e2);
            }
        }
    }

    public void setDataSource(String str, Map<String, String> map) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        if (map == null || map.isEmpty()) {
            this.D = null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey());
                sb.append(": ");
                if (!TextUtils.isEmpty(entry.getValue())) {
                    sb.append(entry.getValue());
                }
                sb.append("\r\n");
            }
            this.D = sb.toString();
            setOption(1, "headers", this.D);
        }
        setDataSource(str);
    }

    public void setDecodeMode(int i) {
        if (i < 0 || i > 1) {
            com.baidu.cloud.media.player.b.a.a(a, "decodeMode shoule be DECODE_AUTO or DECODE_SW");
            return;
        }
        this.G = i;
        if (this.G == 0) {
            setOption(4, "mediacodec", 1L);
            setOption(4, "mediacodec-all-videos", 1L);
            setOption(4, "mediacodec-auto-rotate", 1L);
            setOption(4, "mediacodec-handle-resolution-change", 1L);
            return;
        }
        setOption(4, "mediacodec", 0L);
        setOption(4, "mediacodec-all-videos", 0L);
        setOption(4, "mediacodec-auto-rotate", 0L);
        setOption(4, "mediacodec-handle-resolution-change", 0L);
    }

    public native void setDecryptTokenForHLS(String str);

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setDisplay(SurfaceHolder surfaceHolder) {
        this.f = surfaceHolder;
        _setVideoSurface(surfaceHolder != null ? surfaceHolder.getSurface() : null);
        k();
    }

    public void setInitPlayPosition(long j) {
        setOption(4, "seek-at-start", j);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setKeepInBackground(boolean z) {
    }

    protected native void setLocalDecryptKeyForHLS(String str);

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setLogEnabled(boolean z) {
        if (z) {
            native_setLogLevel(1);
            com.baidu.cloud.media.player.b.a.a(true);
        } else {
            native_setLogLevel(5);
            com.baidu.cloud.media.player.b.a.a(false);
        }
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setLooping(boolean z) {
        int i = z ? 0 : 1;
        setOption(4, "loop", i);
        _setLoopCount(i);
    }

    public void setMaxCacheSizeInBytes(int i) {
        this.w = i;
        setOption(4, "max-buffer-size", i);
    }

    public void setMaxProbeSize(int i) {
        setOption(4, "max-probe-size", i);
    }

    public void setMaxProbeTime(int i) {
        this.y = i;
        setOption(4, "max-probe-time", i);
    }

    public void setOption(int i, String str, long j) {
        _setOption(i, str, j);
    }

    public void setOption(int i, String str, String str2) {
        _setOption(i, str, str2);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setScreenOnWhilePlaying(boolean z) {
        if (this.i != z) {
            if (z && this.f == null) {
                com.baidu.cloud.media.player.b.a.c(a, "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
            }
            this.i = z;
            k();
        }
    }

    public void setSpeed(float f) {
        if (f == 0.0f) {
            return;
        }
        _setPropertyFloat(Constants.CODE_PERMISSIONS_ERROR, f);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setSurface(Surface surface) {
        if (this.i && surface != null) {
            com.baidu.cloud.media.player.b.a.c(a, "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.f = null;
        _setVideoSurface(surface);
        k();
    }

    public void setTimeoutInUs(int i) {
        if (i <= 0) {
            i = this.p;
        }
        this.p = i;
        setOption(1, "timeout", this.p);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public native void setVolume(float f, float f2);

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void setWakeMode(Context context, int i) {
        boolean z;
        boolean z2;
        if (this.h != null) {
            if (this.h.isHeld()) {
                z2 = true;
                this.h.release();
            } else {
                z2 = false;
            }
            this.h = null;
            z = z2;
        } else {
            z = false;
        }
        this.h = ((PowerManager) context.getSystemService("power")).newWakeLock(536870912 | i, BDCloudMediaPlayer.class.getName());
        this.h.setReferenceCounted(false);
        if (z) {
            this.h.acquire();
        }
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void start() throws IllegalStateException {
        a(true);
        g();
        _start();
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer
    public void stop() throws IllegalStateException {
        a(false);
        d();
        _stop();
    }

    public void toggleFrameChasing(boolean z) {
        this.z = z;
        setOption(4, "framechasing", z ? 1L : 0L);
    }
}
