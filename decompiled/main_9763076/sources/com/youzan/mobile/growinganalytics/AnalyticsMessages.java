package com.youzan.mobile.growinganalytics;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import com.tencent.android.tpush.common.Constants;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AnalyticsMessages.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class AnalyticsMessages {
    public static final Companion Companion = new Companion(null);
    private static Map<Context, AnalyticsMessages> instanceMap = new LinkedHashMap();
    private String channel;
    private final AnalyticsConfig config;
    private final Context context;
    private Function0<? extends JSONObject> contextInterceptor;
    private String deviceId;
    private Long deviceIdTime;
    private long flushInterval;
    private String mobile;
    private String userId;
    private final Worker worker;

    /* JADX INFO: compiled from: AnalyticsMessages.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private final Map<Context, AnalyticsMessages> getInstanceMap() {
            return AnalyticsMessages.instanceMap;
        }

        public final synchronized AnalyticsMessages getInstance(Context ctx) {
            AnalyticsMessages $receiver;
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            if (getInstanceMap().containsKey(ctx)) {
                AnalyticsMessages analyticsMessages = getInstanceMap().get(ctx);
                if (analyticsMessages == null) {
                    Intrinsics.throwNpe();
                }
                $receiver = analyticsMessages;
            } else {
                $receiver = new AnalyticsMessages(ctx);
                AnalyticsMessages.Companion.getInstanceMap().put(ctx, $receiver);
            }
            return $receiver;
        }
    }

    public AnalyticsMessages(Context _ctx) {
        Intrinsics.checkParameterIsNotNull(_ctx, "_ctx");
        this.channel = Constants.MAIN_VERSION_TAG;
        this.mobile = Constants.MAIN_VERSION_TAG;
        this.context = _ctx;
        this.config = AnalyticsConfig.Companion.getInstance(this.context);
        this.worker = createWorker();
    }

    public final void setContextInterceptor(Function0<? extends JSONObject> function0) {
        Intrinsics.checkParameterIsNotNull(function0, "interceptor");
        this.contextInterceptor = function0;
    }

    public final void setDeviceId(String _deviceId, long _deviceIdTimestamp) {
        Intrinsics.checkParameterIsNotNull(_deviceId, "_deviceId");
        this.deviceId = _deviceId;
        this.deviceIdTime = Long.valueOf(_deviceIdTimestamp);
    }

    public final void setUserId(String _userId) {
        Intrinsics.checkParameterIsNotNull(_userId, "_userId");
        this.userId = _userId;
    }

    public final void setMobile(String _mobile) {
        Intrinsics.checkParameterIsNotNull(_mobile, "_mobile");
        this.mobile = _mobile;
    }

    private final Worker createWorker() {
        return new Worker();
    }

    public final void eventMessage(final Event event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.worker.runMessage(new Function0<Message>() { // from class: com.youzan.mobile.growinganalytics.AnalyticsMessages.eventMessage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Message invoke() {
                AnalyticsMessages analyticsMessages = AnalyticsMessages.this;
                MsgType msgType = MsgType.ENQUEUE_EVENT;
                Event any$iv = event;
                Message $receiver$iv = Message.obtain();
                $receiver$iv.what = msgType.getWhat();
                $receiver$iv.obj = any$iv;
                Intrinsics.checkExpressionValueIsNotNull($receiver$iv, "Message.obtain().apply {….apply { this.obj = any }");
                return $receiver$iv;
            }
        });
    }

    public final void postToServer() {
        this.worker.runMessage(new Function0<Message>() { // from class: com.youzan.mobile.growinganalytics.AnalyticsMessages.postToServer.1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Message invoke() {
                AnalyticsMessages analyticsMessages = AnalyticsMessages.this;
                MsgType msgType = MsgType.FLUSH_QUEUE;
                Message $receiver$iv = Message.obtain();
                $receiver$iv.what = msgType.getWhat();
                $receiver$iv.obj = null;
                Intrinsics.checkExpressionValueIsNotNull($receiver$iv, "Message.obtain().apply {….apply { this.obj = any }");
                return $receiver$iv;
            }
        });
    }

    public final AnalyticsStore makeEventStore(Context ctx) {
        Intrinsics.checkParameterIsNotNull(ctx, "ctx");
        return AnalyticsStore.Companion.getInstance(ctx);
    }

    public final IRemoteService getPoster() {
        return HttpService.Companion.get();
    }

    /* JADX INFO: compiled from: AnalyticsMessages.kt */
    @Metadata
    public final class Worker {
        private long aveFlushFrequency;
        private long flushCount;
        private Handler handler;
        private SystemInformation systemInfo;
        private final Object lock = new Object();
        private long lastFlushTime = -1;

        public final Object getLock() {
            return this.lock;
        }

        public Worker() {
            HandlerThread $receiver$iv = new HandlerThread("com.youzan.mobile.AnalyticsWorker", 1);
            $receiver$iv.start();
            Looper looper = $receiver$iv.getLooper();
            Intrinsics.checkExpressionValueIsNotNull(looper, "HandlerThread(\"com.youza….apply { start() }.looper");
            this.handler = new AnalyticsMessageHandler(this, looper);
        }

        public final void runMessage(Function0<Message> function0) {
            Handler handler;
            Intrinsics.checkParameterIsNotNull(function0, "f");
            synchronized (this.lock) {
                if (this.handler != null && (handler = this.handler) != null) {
                    Boolean.valueOf(handler.sendMessage(function0.invoke()));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void updateFlushFrequency() {
            long now = System.currentTimeMillis();
            long newFlushCount = this.flushCount + 1;
            if (this.lastFlushTime > 0) {
                long flushInterval = now - this.lastFlushTime;
                long totalFlushTime = flushInterval + (this.aveFlushFrequency * this.flushCount);
                this.aveFlushFrequency = totalFlushTime / this.flushCount;
            }
            this.lastFlushTime = now;
            this.flushCount = newFlushCount;
        }

        /* JADX INFO: compiled from: AnalyticsMessages.kt */
        @Metadata
        public final class AnalyticsMessageHandler extends Handler {
            private AnalyticsStore analyticsStore;
            final /* synthetic */ Worker this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnalyticsMessageHandler(Worker $outer, Looper looper) {
                super(looper);
                Intrinsics.checkParameterIsNotNull(looper, "looper");
                this.this$0 = $outer;
                AnalyticsMessages.this.flushInterval = AnalyticsMessages.this.config.getFlushInterval();
                $outer.systemInfo = new SystemInformation(AnalyticsMessages.this.context);
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.NoWhenBranchMatchedException */
            /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.TypeCastException */
            @Override // android.os.Handler
            public void handleMessage(Message msg) throws JSONException, NoWhenBranchMatchedException, TypeCastException {
                Integer numValueOf;
                AnalyticsStore analyticsStore;
                NetworkType type;
                long j;
                NetworkType type2;
                long j2;
                if (this.analyticsStore == null) {
                    this.analyticsStore = AnalyticsMessages.this.makeEventStore(AnalyticsMessages.this.context);
                    AnalyticsStore analyticsStore2 = this.analyticsStore;
                    if (analyticsStore2 != null) {
                        AnalyticsStore.cleanUpEventsByTime$default(analyticsStore2, System.currentTimeMillis() - AnalyticsMessages.this.config.getDataExpiration(), false, 2, null);
                    }
                }
                if (msg == null) {
                    numValueOf = null;
                } else {
                    try {
                        numValueOf = Integer.valueOf(msg.what);
                    } catch (RuntimeException e) {
                        synchronized (this.this$0.getLock()) {
                            this.this$0.handler = (Handler) null;
                            try {
                                Looper.myLooper().quit();
                            } catch (Exception e2) {
                                Logger.Companion companion = Logger.Companion;
                                String message = e2.getMessage();
                                if (message == null) {
                                    message = Constants.MAIN_VERSION_TAG;
                                }
                                companion.e(message);
                            }
                            Unit unit = Unit.INSTANCE;
                            return;
                        }
                    }
                }
                int what = MsgType.ENQUEUE_EVENT.getWhat();
                if (numValueOf != null && numValueOf.intValue() == what) {
                    Object obj = msg != null ? msg.obj : null;
                    if (!(obj instanceof Event)) {
                        obj = null;
                    }
                    Event $receiver = (Event) obj;
                    if ($receiver != null && (analyticsStore = this.analyticsStore) != null) {
                        analyticsStore.insert($receiver);
                    }
                } else {
                    int what2 = MsgType.FLUSH_QUEUE.getWhat();
                    if (numValueOf != null && numValueOf.intValue() == what2) {
                        this.this$0.updateFlushFrequency();
                        try {
                            type = UtilKt.getAPNType(AnalyticsMessages.this.context);
                        } catch (Exception e3) {
                            type = NetworkType.UNKNOWN;
                        }
                        sendAllData(this.analyticsStore, type);
                        AnalyticsMessages analyticsMessages = AnalyticsMessages.this;
                        if (type != null) {
                            switch (type) {
                                case WIFI:
                                    j = 20000;
                                    break;
                                case MOBILE_2G:
                                    j = 60000;
                                    break;
                                case MOBILE_3G:
                                    j = 30000;
                                    break;
                                case MOBILE_4G:
                                    j = 20000;
                                    break;
                                case MOBILE:
                                    j = 30000;
                                    break;
                                case UNKNOWN:
                                    j = 60000;
                                    break;
                                case NO_PERMISSION:
                                    j = 30000;
                                    break;
                                default:
                                    throw new NoWhenBranchMatchedException();
                            }
                        } else {
                            j = 30000;
                        }
                        analyticsMessages.flushInterval = j;
                    } else {
                        int what3 = MsgType.KILL_WORKER.getWhat();
                        if (numValueOf != null && numValueOf.intValue() == what3) {
                            synchronized (this.this$0.getLock()) {
                                AnalyticsStore analyticsStore3 = this.analyticsStore;
                                if (analyticsStore3 != null) {
                                    analyticsStore3.deleteDB();
                                }
                                this.this$0.handler = (Handler) null;
                                Looper.myLooper().quit();
                                Unit unit2 = Unit.INSTANCE;
                            }
                        } else {
                            int what4 = MsgType.FLUSH_QUEUE_CLEAR_USER.getWhat();
                            if (numValueOf != null && numValueOf.intValue() == what4) {
                                this.this$0.updateFlushFrequency();
                                try {
                                    type2 = UtilKt.getAPNType(AnalyticsMessages.this.context);
                                } catch (Exception e4) {
                                    type2 = NetworkType.UNKNOWN;
                                }
                                sendAllData(this.analyticsStore, type2);
                                AnalyticsMessages analyticsMessages2 = AnalyticsMessages.this;
                                if (type2 != null) {
                                    switch (type2) {
                                        case WIFI:
                                            j2 = 20000;
                                            break;
                                        case MOBILE_2G:
                                            j2 = 60000;
                                            break;
                                        case MOBILE_3G:
                                            j2 = 30000;
                                            break;
                                        case MOBILE_4G:
                                            j2 = 20000;
                                            break;
                                        case MOBILE:
                                            j2 = 30000;
                                            break;
                                        case UNKNOWN:
                                            j2 = 60000;
                                            break;
                                        case NO_PERMISSION:
                                            j2 = 30000;
                                            break;
                                        default:
                                            throw new NoWhenBranchMatchedException();
                                    }
                                } else {
                                    j2 = 30000;
                                }
                                analyticsMessages2.flushInterval = j2;
                                AnalyticsMessages.this.userId = Constants.MAIN_VERSION_TAG;
                            } else {
                                int what5 = MsgType.FLUSH_CRASH.getWhat();
                                if (numValueOf != null && numValueOf.intValue() == what5) {
                                }
                            }
                        }
                    }
                }
                if (!hasMessages(MsgType.FLUSH_QUEUE.getWhat()) && AnalyticsMessages.this.flushInterval >= 0) {
                    AnalyticsMessages analyticsMessages3 = AnalyticsMessages.this;
                    MsgType msgType = MsgType.FLUSH_QUEUE;
                    Message $receiver$iv = Message.obtain();
                    $receiver$iv.what = msgType.getWhat();
                    $receiver$iv.obj = null;
                    Intrinsics.checkExpressionValueIsNotNull($receiver$iv, "Message.obtain().apply {….apply { this.obj = any }");
                    sendMessageDelayed($receiver$iv, AnalyticsMessages.this.flushInterval);
                    Logger.Companion.d(AnalyticsMessagesKt.TAG, "flush queue after " + (AnalyticsMessages.this.flushInterval / ((long) 1000)) + " seconds");
                }
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.TypeCastException */
            private final void sendAllData(final AnalyticsStore store, NetworkType networkType) throws JSONException, TypeCastException {
                JSONObject jSONObject;
                final IRemoteService poster = AnalyticsMessages.this.getPoster();
                if (!poster.isOnline(AnalyticsMessages.this.context, AnalyticsMessages.this.config.getOfflineMode()) || store == null) {
                    Logger.Companion.e("poster not online or store is null");
                    return;
                }
                JSONObject platJson = new PlatForm(AnalyticsMessages.this.config.getAppId(), "Android", "0.4.7").toJson();
                JSONObject envJson = getEnvJson(networkType);
                JSONObject userJson = getUserJson();
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                JSONObject $receiver = new JSONObject();
                $receiver.put("plat", platJson);
                $receiver.put("user", userJson);
                $receiver.put("env", envJson);
                objectRef.element = $receiver;
                Function0 it = AnalyticsMessages.this.contextInterceptor;
                if (it != null && (jSONObject = (JSONObject) objectRef.element) != null) {
                    jSONObject.put("context", it.invoke());
                }
                int length = ((JSONObject) objectRef.element).toString().length();
                store.generateData(new Function3<Long, List<JSONObject>, Integer, Unit>() { // from class: com.youzan.mobile.growinganalytics.AnalyticsMessages$Worker$AnalyticsMessageHandler$sendAllData$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(Long l, List<JSONObject> list, Integer num) throws JSONException {
                        invoke(l.longValue(), list, num.intValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(long lastId, List<JSONObject> list, int $noName_2) throws JSONException {
                        Intrinsics.checkParameterIsNotNull(list, "mutableList");
                        AnalyticsMessages.Worker.AnalyticsMessageHandler analyticsMessageHandler = this.this$0;
                        JSONArray jsonArr = new JSONArray();
                        for (JSONObject json : list) {
                            jsonArr.put(json);
                        }
                        ((JSONObject) objectRef.element).put("events", jsonArr);
                        Logger.Companion.d(AnalyticsMessagesKt.TAG, "--------- post events---------");
                        Logger.Companion.d(AnalyticsMessagesKt.TAG, ((JSONObject) objectRef.element).toString());
                        Response response = poster.performRequest(AnalyticsMessages.this.config.getDataServerUrl(), (JSONObject) objectRef.element, AnalyticsMessages.this.config.getSSLSocketFactory());
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Logger.Companion.d(AnalyticsMessagesKt.TAG, "post success.clean queue.");
                                AnalyticsStore.cleanUpEventsById$default(store, lastId, false, 2, null);
                            }
                            response.close();
                            Logger.Companion.d(AnalyticsMessagesKt.TAG, "response close.");
                        }
                    }
                }, AnalyticsMessages.this.config.getRequestMaxLength() - ((long) length));
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.TypeCastException */
            private final JSONObject getEnvJson(NetworkType networkType) throws TypeCastException {
                String appVersion;
                SystemInformation systemInformation = this.this$0.systemInfo;
                if (systemInformation == null || (appVersion = systemInformation.getAppVersionName()) == null) {
                    appVersion = Constants.MAIN_VERSION_TAG;
                }
                String channel = AnalyticsMessages.this.channel;
                String osVersion = Build.VERSION.RELEASE;
                if (osVersion == null) {
                    osVersion = "UNKNOWN";
                }
                String networkType2 = networkType.getValue();
                String deviceType = Build.MODEL;
                if (deviceType == null) {
                    deviceType = "UNKNOWN";
                }
                SystemInformation systemInformation2 = this.this$0.systemInfo;
                DisplayMetrics metrics = systemInformation2 != null ? systemInformation2.getMetrics() : null;
                int screenWidth = metrics != null ? metrics.widthPixels : 0;
                int screenHeight = metrics != null ? metrics.heightPixels : 0;
                String ip = UtilKt.getIpAddress(true);
                return new Env(appVersion, channel, "Android", osVersion, networkType2, deviceType, screenWidth, screenHeight, ip).toJson();
            }

            private final JSONObject getUserJson() {
                String str = AnalyticsMessages.this.deviceId;
                if (str == null) {
                    str = Constants.MAIN_VERSION_TAG;
                }
                Long l = AnalyticsMessages.this.deviceIdTime;
                long jLongValue = l != null ? l.longValue() : 0L;
                String str2 = AnalyticsMessages.this.userId;
                if (str2 == null) {
                    str2 = Constants.MAIN_VERSION_TAG;
                }
                return new UserInfo(str, jLongValue, str2, AnalyticsMessages.this.mobile).toJson();
            }
        }
    }
}
