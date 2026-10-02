package com.youzan.mobile.growinganalytics;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.tencent.android.tpush.XGPushNotificationBuilder;
import com.tencent.android.tpush.common.Constants;
import com.youzan.mobile.growinganalytics.viewcrawler.ViewCrawler;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: AnalyticsAPI.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class AnalyticsAPI {
    private static Future<SharedPreferences> analyticsPrefs;
    private static boolean isDebug;
    private ActivityLifecycleListener actLifecycleCallbacks;
    private final AnalyticsMessages analyticsMessage;
    private final AnalyticsConfig config;
    private final Context context;
    private Map<String, Long> pageTimeMap;
    private final PersistentIdentity persistentId;
    private String shopId;
    private final ViewCrawler viewCrawler;
    public static final Companion Companion = new Companion(null);
    public static final String LOG_TAG = "yz_analytics";
    private static final Map<Context, AnalyticsAPI> instanceMap = new LinkedHashMap();
    private static boolean isSendAutoEvent = true;
    private static boolean isSendPageAction = true;
    private static final SharedPrefsLoader prefsLoader = new SharedPrefsLoader();

    public static final AnalyticsAPI get(Context ctx) {
        return Companion.get(ctx);
    }

    public static final void setAutoEventEnable(boolean enable) {
        Companion.setAutoEventEnable(enable);
    }

    public static final void setDebug(boolean _isDebug) {
        Companion.setDebug(_isDebug);
    }

    public static final void setSendPageAction(boolean enable) {
        Companion.setSendPageAction(enable);
    }

    /* JADX INFO: compiled from: AnalyticsAPI.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private final Map<Context, AnalyticsAPI> getInstanceMap() {
            return AnalyticsAPI.instanceMap;
        }

        public final boolean isDebug$growing_analytics_release() {
            return AnalyticsAPI.isDebug;
        }

        public final void setDebug$growing_analytics_release(boolean z) {
            AnalyticsAPI.isDebug = z;
        }

        public final boolean isSendAutoEvent$growing_analytics_release() {
            return AnalyticsAPI.isSendAutoEvent;
        }

        public final void setSendAutoEvent$growing_analytics_release(boolean z) {
            AnalyticsAPI.isSendAutoEvent = z;
        }

        public final boolean isSendPageAction$growing_analytics_release() {
            return AnalyticsAPI.isSendPageAction;
        }

        public final void setSendPageAction$growing_analytics_release(boolean z) {
            AnalyticsAPI.isSendPageAction = z;
        }

        private final Future<SharedPreferences> getAnalyticsPrefs() {
            return AnalyticsAPI.analyticsPrefs;
        }

        private final void setAnalyticsPrefs(Future<SharedPreferences> future) {
            AnalyticsAPI.analyticsPrefs = future;
        }

        private final SharedPrefsLoader getPrefsLoader() {
            return AnalyticsAPI.prefsLoader;
        }

        public final AnalyticsAPI get(Context ctx) {
            AnalyticsAPI instance = null;
            if (ctx != null) {
                synchronized (getInstanceMap()) {
                    Context appContext = ctx.getApplicationContext();
                    if (AnalyticsAPI.Companion.getAnalyticsPrefs() == null) {
                        Companion companion = AnalyticsAPI.Companion;
                        SharedPrefsLoader prefsLoader = AnalyticsAPI.Companion.getPrefsLoader();
                        Intrinsics.checkExpressionValueIsNotNull(appContext, "appContext");
                        companion.setAnalyticsPrefs(prefsLoader.loadPrefs(appContext, AnalyticsConfig.Companion.getANALYTICS_PREFS_NAME(), (4 & 4) != 0 ? SharedPrefsLoader.AnonymousClass1.INSTANCE : null));
                    }
                    instance = AnalyticsAPI.Companion.getInstanceMap().get(appContext);
                    if (instance == null) {
                        Intrinsics.checkExpressionValueIsNotNull(appContext, "appContext");
                        Future<SharedPreferences> analyticsPrefs = AnalyticsAPI.Companion.getAnalyticsPrefs();
                        if (analyticsPrefs == null) {
                            Intrinsics.throwNpe();
                        }
                        instance = new AnalyticsAPI(appContext, analyticsPrefs, null, 4, null);
                    }
                    Map<Context, AnalyticsAPI> instanceMap = AnalyticsAPI.Companion.getInstanceMap();
                    Intrinsics.checkExpressionValueIsNotNull(appContext, "appContext");
                    instanceMap.put(appContext, instance);
                }
            }
            return instance;
        }

        public final void setAutoEventEnable(boolean enable) {
            setSendAutoEvent$growing_analytics_release(enable);
        }

        public final void setSendPageAction(boolean enable) {
            setSendPageAction$growing_analytics_release(enable);
        }

        public final void setDebug(boolean _isDebug) {
            setDebug$growing_analytics_release(_isDebug);
        }
    }

    /* synthetic */ AnalyticsAPI(Context context, Future future, AnalyticsConfig analyticsConfig, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, future, (i & 4) != 0 ? AnalyticsConfig.Companion.getInstance(context) : analyticsConfig);
    }

    private AnalyticsAPI(Context _ctx, Future<SharedPreferences> future, AnalyticsConfig _config) {
        this.pageTimeMap = new LinkedHashMap();
        Context applicationContext = _ctx.getApplicationContext();
        Intrinsics.checkExpressionValueIsNotNull(applicationContext, "_ctx.applicationContext");
        this.context = applicationContext;
        this.config = _config;
        this.analyticsMessage = getAnalyticsMessage();
        this.persistentId = getPersistentIdentity(future);
        this.analyticsMessage.setDeviceId(this.persistentId.getDeviceId(), this.persistentId.getDeviceIdTime());
        Logger.Companion.d("device id:" + this.persistentId.getDeviceId());
        AnalyticsMessages analyticsMessages = this.analyticsMessage;
        String userId = this.persistentId.getUserId();
        analyticsMessages.setUserId(userId == null ? Constants.MAIN_VERSION_TAG : userId);
        Logger.Companion companion = Logger.Companion;
        StringBuilder sbAppend = new StringBuilder().append("user id:");
        String userId2 = this.persistentId.getUserId();
        companion.d(sbAppend.append((Object) (userId2 == null ? "UNKNOWN" : userId2)).toString());
        this.analyticsMessage.setMobile(this.persistentId.getMobile());
        this.analyticsMessage.setContextInterceptor(new Function0<JSONObject>() { // from class: com.youzan.mobile.growinganalytics.AnalyticsAPI.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JSONObject invoke() {
                return AnalyticsAPI.this.persistentId.getSuperPropertiesCache();
            }
        });
        boolean dbFileExit = AnalyticsStore.Companion.getInstance(this.context).getDatabaseFile().exists();
        if (this.persistentId.isFirstLaunch(dbFileExit)) {
            Logger.Companion.d("first launch");
        }
        if (Build.VERSION.SDK_INT >= 14) {
            registerActivityLifecycleCallbacks();
        }
        if (sendAppOpen()) {
            Logger.Companion.d("app open");
        }
        this.viewCrawler = new ViewCrawler(this.context, this);
    }

    private final AnalyticsMessages getAnalyticsMessage() {
        return AnalyticsMessages.Companion.getInstance(this.context);
    }

    private final PersistentIdentity getPersistentIdentity(Future<SharedPreferences> future) {
        return new PersistentIdentity(future);
    }

    public final void setAppId(String appId) {
        Intrinsics.checkParameterIsNotNull(appId, "appId");
        this.config.setAppId(appId);
    }

    public final EventBuildDelegate buildEvent(String eventId) {
        Intrinsics.checkParameterIsNotNull(eventId, "eventId");
        return new EventBuildDelegate(this, eventId);
    }

    public final EventBuildDelegate buildEvent$growing_analytics_release(AutoEvent autoEvent) {
        Intrinsics.checkParameterIsNotNull(autoEvent, "autoEvent");
        return buildEvent(autoEvent.getEventId()).auto$growing_analytics_release(true).type(autoEvent.getEventType());
    }

    public final void flush() {
        this.analyticsMessage.postToServer();
    }

    public final void registerSuperProperties(String key, String value) throws JSONException {
        if (!UtilKt.isEmpty(key) && !UtilKt.isEmpty(value)) {
            PersistentIdentity persistentIdentity = this.persistentId;
            JSONObject jSONObjectPut = new JSONObject().put(key, value);
            Intrinsics.checkExpressionValueIsNotNull(jSONObjectPut, "JSONObject().put(key, value)");
            persistentIdentity.registerSuperProperties(jSONObjectPut);
        }
    }

    @TargetApi(14)
    public final void registerActivityLifecycleCallbacks() {
        if (Build.VERSION.SDK_INT >= 14) {
            Context applicationContext = this.context.getApplicationContext();
            if (!(applicationContext instanceof Application)) {
                applicationContext = null;
            }
            Application it = (Application) applicationContext;
            if (it != null) {
                this.actLifecycleCallbacks = new ActivityLifecycleListener(this, this.config);
                it.registerActivityLifecycleCallbacks(this.actLifecycleCallbacks);
            }
        }
    }

    private final boolean sendAppOpen() {
        return this.config.isSendAppOpen();
    }

    public final void track(String eventId) {
        Intrinsics.checkParameterIsNotNull(eventId, "eventId");
        buildEvent(eventId).type(XGPushNotificationBuilder.CUSTOM_NOTIFICATION_BUILDER_TYPE).track();
    }

    public final void trackPageStart(String pageName) {
        if (pageName != null) {
            buildEvent$growing_analytics_release(AutoEvent.EnterPage).pageType(pageName).track();
            if (!this.pageTimeMap.containsKey(pageName)) {
                this.pageTimeMap.put(pageName, Long.valueOf(System.currentTimeMillis()));
            }
        }
    }

    public final void trackPageEnd(String pageName) {
        if (pageName != null) {
            Long l = this.pageTimeMap.get(pageName);
            long enterTime = l != null ? l.longValue() : 0L;
            long now = System.currentTimeMillis();
            buildEvent$growing_analytics_release(AutoEvent.LeavePage).pageType(pageName).params(MapsKt.mapOf(TuplesKt.to("enter_time", Long.valueOf(enterTime)), TuplesKt.to("leave_time", Long.valueOf(now)))).track();
            this.pageTimeMap.remove(pageName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void track(Event event) {
        Logger.Companion.d("Event", event.toJson().toString());
        this.analyticsMessage.eventMessage(event);
        ActivityLifecycleListener activityLifecycleListener = this.actLifecycleCallbacks;
        if (activityLifecycleListener != null) {
            activityLifecycleListener.countSessionBatchNo();
        }
    }

    /* JADX INFO: compiled from: AnalyticsAPI.kt */
    @Metadata
    public final class EventBuildDelegate {
        private final Event.Builder builder;
        final /* synthetic */ AnalyticsAPI this$0;

        public EventBuildDelegate(AnalyticsAPI $outer, String eventId) {
            Intrinsics.checkParameterIsNotNull(eventId, "eventId");
            this.this$0 = $outer;
            this.builder = new Event.Builder(eventId).isAuto(false).type(XGPushNotificationBuilder.CUSTOM_NOTIFICATION_BUILDER_TYPE);
            String it = $outer.shopId;
            if (it != null) {
                this.builder.shopId(it);
            }
            ActivityLifecycleListener it2 = $outer.actLifecycleCallbacks;
            if (it2 == null) {
                return;
            }
            Event.Builder builder = this.builder;
            Long sessionStartTime = it2.getSessionStartTime();
            builder.sequenceBatch(sessionStartTime != null ? sessionStartTime.longValue() : 0L);
            this.builder.sequenceNo(it2.getSessionBatchNo());
            Event.Builder builder2 = this.builder;
            String currentActivityName = it2.getCurrentActivityName();
            builder2.pageType(currentActivityName == null ? Constants.MAIN_VERSION_TAG : currentActivityName);
        }

        public final EventBuildDelegate auto$growing_analytics_release(boolean isAuto) {
            EventBuildDelegate $receiver = this;
            $receiver.builder.isAuto(isAuto);
            return this;
        }

        public final EventBuildDelegate type(String type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            EventBuildDelegate $receiver = this;
            $receiver.builder.type(type);
            return this;
        }

        public final EventBuildDelegate desc(String desc) {
            Intrinsics.checkParameterIsNotNull(desc, "desc");
            EventBuildDelegate $receiver = this;
            $receiver.builder.desc(desc);
            return this;
        }

        public final EventBuildDelegate params(Map<String, ? extends Object> map) {
            EventBuildDelegate $receiver = this;
            $receiver.builder.params(map);
            return this;
        }

        public final EventBuildDelegate pageType(String type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            EventBuildDelegate $receiver = this;
            $receiver.builder.pageType(type);
            return this;
        }

        public final void track() {
            this.this$0.track(this.builder.build());
        }
    }
}
