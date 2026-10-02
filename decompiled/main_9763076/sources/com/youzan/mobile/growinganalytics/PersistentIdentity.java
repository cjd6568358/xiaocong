package com.youzan.mobile.growinganalytics;

import android.content.SharedPreferences;
import com.tencent.android.tpush.common.Constants;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.Unit;
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
    */
/* JADX INFO: compiled from: PersistentIdentity.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class PersistentIdentity {
    private final String CONTEXT_PROPERTIES;
    private String deviceId;
    private long deviceIdTime;
    private boolean identitiesLoaded;
    private boolean isFirstAppLaunch;
    private final Future<SharedPreferences> loadAnalyticsPrefs;
    private String mobile;
    private JSONObject superProperties;
    private String userId;

    public PersistentIdentity(Future<SharedPreferences> future) {
        Intrinsics.checkParameterIsNotNull(future, "_loadAnalyticsPrefs");
        this.CONTEXT_PROPERTIES = "context_properties";
        this.deviceId = Constants.MAIN_VERSION_TAG;
        this.userId = Constants.MAIN_VERSION_TAG;
        this.mobile = Constants.MAIN_VERSION_TAG;
        this.loadAnalyticsPrefs = future;
    }

    public final String getDeviceId() {
        String str;
        synchronized (this.deviceId) {
            if (!this.identitiesLoaded) {
                readIds();
            }
            str = this.deviceId;
        }
        return str;
    }

    public final long getDeviceIdTime() {
        long j;
        synchronized (Long.valueOf(this.deviceIdTime)) {
            if (!this.identitiesLoaded) {
                readIds();
            }
            j = this.deviceIdTime;
        }
        return j;
    }

    public final String getMobile() {
        String string;
        SharedPreferences prefs = null;
        synchronized (this.mobile) {
            try {
                prefs = (SharedPreferences) this.loadAnalyticsPrefs.get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e2) {
            }
            if (prefs == null) {
                string = Constants.MAIN_VERSION_TAG;
            } else {
                string = prefs.getString("mobile", Constants.MAIN_VERSION_TAG);
                Intrinsics.checkExpressionValueIsNotNull(string, "prefs.getString(\"mobile\", \"\")");
            }
        }
        return string;
    }

    public final boolean isFirstLaunch(boolean dbExists) {
        boolean z;
        SharedPreferences sharedPreferences;
        synchronized (Boolean.valueOf(this.isFirstAppLaunch)) {
            boolean z2 = this.isFirstAppLaunch;
            if (this.isFirstAppLaunch) {
                try {
                    sharedPreferences = (SharedPreferences) this.loadAnalyticsPrefs.get();
                } catch (InterruptedException e) {
                    sharedPreferences = null;
                } catch (ExecutionException e2) {
                    sharedPreferences = null;
                }
                SharedPreferences.Editor prefEditor = sharedPreferences != null ? sharedPreferences.edit() : null;
                if (prefEditor != null) {
                    prefEditor.putBoolean("has_launched", true);
                }
                if (prefEditor != null) {
                    prefEditor.apply();
                }
            }
            z = this.isFirstAppLaunch;
        }
        return z;
    }

    public final String getUserId() {
        if (!this.identitiesLoaded) {
            readIds();
        }
        return this.userId;
    }

    private final void readIds() {
        SharedPreferences prefs = null;
        try {
            prefs = (SharedPreferences) this.loadAnalyticsPrefs.get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e2) {
        }
        if (prefs != null) {
            String string = prefs.getString("device_id", Constants.MAIN_VERSION_TAG);
            Intrinsics.checkExpressionValueIsNotNull(string, "prefs.getString(\"device_id\", \"\")");
            this.deviceId = string;
            String string2 = prefs.getString("user_id", Constants.MAIN_VERSION_TAG);
            Intrinsics.checkExpressionValueIsNotNull(string2, "prefs.getString(\"user_id\", \"\")");
            this.userId = string2;
            this.deviceIdTime = prefs.getLong("device_id_timestamp", 0L);
            if (this.deviceId == null || UtilKt.isEmpty(this.deviceId)) {
                String string3 = UUID.randomUUID().toString();
                Intrinsics.checkExpressionValueIsNotNull(string3, "UUID.randomUUID().toString()");
                this.deviceId = string3;
                this.deviceIdTime = System.currentTimeMillis();
                writeDeviceId();
            }
            this.identitiesLoaded = true;
        }
    }

    private final void writeDeviceId() {
        SharedPreferences prefs = null;
        synchronized (this.deviceId) {
            try {
                prefs = (SharedPreferences) this.loadAnalyticsPrefs.get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e2) {
            }
            if (prefs != null) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("device_id", this.deviceId);
                if (this.deviceIdTime == 0) {
                    this.deviceIdTime = System.currentTimeMillis();
                }
                editor.putLong("device_id_timestamp", this.deviceIdTime);
                editor.apply();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final JSONObject getSuperPropertiesCache() {
        SharedPreferences prefs$iv;
        JSONObject json$iv;
        if (this.superProperties == null) {
            try {
                prefs$iv = (SharedPreferences) this.loadAnalyticsPrefs.get();
            } catch (InterruptedException e) {
                prefs$iv = null;
            } catch (ExecutionException e2) {
                prefs$iv = null;
            }
            if (prefs$iv == null) {
                this.superProperties = new JSONObject();
            } else {
                String props$iv = prefs$iv.getString(this.CONTEXT_PROPERTIES, "{}");
                Logger.Companion.d("super properties:" + props$iv);
                try {
                    json$iv = new JSONObject(props$iv);
                } catch (JSONException e3) {
                    json$iv = null;
                }
                if (json$iv == null) {
                    json$iv = new JSONObject();
                }
                this.superProperties = json$iv;
            }
        }
        JSONObject jSONObject = this.superProperties;
        if (jSONObject == null) {
            Intrinsics.throwNpe();
        }
        return jSONObject;
    }

    public final synchronized void registerSuperProperties(JSONObject props) {
        SharedPreferences prefs$iv = null;
        synchronized (this) {
            Intrinsics.checkParameterIsNotNull(props, "props");
            JSONObject json = getSuperPropertiesCache();
            try {
                Iterator<String> itKeys = props.keys();
                while (itKeys.hasNext()) {
                    Object element$iv = itKeys.next();
                    String key = (String) element$iv;
                    Object value = props.get(key);
                    if (!UtilKt.isEmpty(key) && value != null) {
                        json.put(key, value);
                    }
                }
            } catch (JSONException e) {
            }
            if (this.superProperties != null) {
                try {
                    prefs$iv = (SharedPreferences) this.loadAnalyticsPrefs.get();
                } catch (InterruptedException e2) {
                } catch (ExecutionException e3) {
                }
                if (prefs$iv != null) {
                    String jsonData$iv = String.valueOf(this.superProperties);
                    SharedPreferences.Editor editor$iv = prefs$iv.edit();
                    editor$iv.putString(this.CONTEXT_PROPERTIES, jsonData$iv);
                    editor$iv.apply();
                }
            }
        }
    }
}
