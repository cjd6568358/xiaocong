package com.youzan.mobile.growinganalytics;

import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: AnalyticsEvent.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class Event {
    private final String eventDesc;
    private final String eventId;
    private final String eventLabel;
    private final Map<String, Object> eventParams;
    private final long eventSequenceBatch;
    private final int eventSequenceNo;
    private final String eventType;
    private final boolean isAuto;
    private final boolean isDebug;
    private final String pageType;
    private final String shopId;
    private final JSONObject superProperties;
    private final long timestamp;

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof Event)) {
                return false;
            }
            Event event = (Event) obj;
            if (!Intrinsics.areEqual(this.eventType, event.eventType) || !Intrinsics.areEqual(this.eventId, event.eventId) || !Intrinsics.areEqual(this.eventDesc, event.eventDesc)) {
                return false;
            }
            if (!(this.timestamp == event.timestamp)) {
                return false;
            }
            if (!(this.eventSequenceBatch == event.eventSequenceBatch)) {
                return false;
            }
            if (!(this.eventSequenceNo == event.eventSequenceNo) || !Intrinsics.areEqual(this.eventLabel, event.eventLabel) || !Intrinsics.areEqual(this.shopId, event.shopId) || !Intrinsics.areEqual(this.eventParams, event.eventParams) || !Intrinsics.areEqual(this.superProperties, event.superProperties)) {
                return false;
            }
            if (!(this.isAuto == event.isAuto)) {
                return false;
            }
            if (!(this.isDebug == event.isDebug) || !Intrinsics.areEqual(this.pageType, event.pageType)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v34, types: [int] */
    /* JADX WARN: Type inference failed for: r0v40 */
    /* JADX WARN: Type inference failed for: r0v48 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [int] */
    public int hashCode() {
        String str = this.eventType;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.eventId;
        int iHashCode2 = ((str2 != null ? str2.hashCode() : 0) + iHashCode) * 31;
        String str3 = this.eventDesc;
        int iHashCode3 = str3 != null ? str3.hashCode() : 0;
        long j = this.timestamp;
        int i = (((iHashCode3 + iHashCode2) * 31) + ((int) (j ^ (j >>> 32)))) * 31;
        long j2 = this.eventSequenceBatch;
        int i2 = (((i + ((int) (j2 ^ (j2 >>> 32)))) * 31) + this.eventSequenceNo) * 31;
        String str4 = this.eventLabel;
        int iHashCode4 = ((str4 != null ? str4.hashCode() : 0) + i2) * 31;
        String str5 = this.shopId;
        int iHashCode5 = ((str5 != null ? str5.hashCode() : 0) + iHashCode4) * 31;
        Map<String, Object> map = this.eventParams;
        int iHashCode6 = ((map != null ? map.hashCode() : 0) + iHashCode5) * 31;
        JSONObject jSONObject = this.superProperties;
        int iHashCode7 = ((jSONObject != null ? jSONObject.hashCode() : 0) + iHashCode6) * 31;
        boolean z = this.isAuto;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i3 = (r0 + iHashCode7) * 31;
        boolean z2 = this.isDebug;
        int i4 = (i3 + (z2 ? 1 : z2)) * 31;
        String str6 = this.pageType;
        return i4 + (str6 != null ? str6.hashCode() : 0);
    }

    public String toString() {
        return "Event(eventType=" + this.eventType + ", eventId=" + this.eventId + ", eventDesc=" + this.eventDesc + ", timestamp=" + this.timestamp + ", eventSequenceBatch=" + this.eventSequenceBatch + ", eventSequenceNo=" + this.eventSequenceNo + ", eventLabel=" + this.eventLabel + ", shopId=" + this.shopId + ", eventParams=" + this.eventParams + ", superProperties=" + this.superProperties + ", isAuto=" + this.isAuto + ", isDebug=" + this.isDebug + ", pageType=" + this.pageType + ")";
    }

    public /* synthetic */ Event(Builder b, DefaultConstructorMarker $constructor_marker) {
        this(b);
    }

    public Event(String eventType, String eventId, String eventDesc, long timestamp, long eventSequenceBatch, int eventSequenceNo, String eventLabel, String shopId, Map<String, ? extends Object> map, JSONObject superProperties, boolean isAuto, boolean isDebug, String pageType) {
        Intrinsics.checkParameterIsNotNull(eventType, "eventType");
        Intrinsics.checkParameterIsNotNull(eventId, "eventId");
        Intrinsics.checkParameterIsNotNull(eventDesc, "eventDesc");
        Intrinsics.checkParameterIsNotNull(eventLabel, "eventLabel");
        Intrinsics.checkParameterIsNotNull(shopId, "shopId");
        Intrinsics.checkParameterIsNotNull(pageType, "pageType");
        this.eventType = eventType;
        this.eventId = eventId;
        this.eventDesc = eventDesc;
        this.timestamp = timestamp;
        this.eventSequenceBatch = eventSequenceBatch;
        this.eventSequenceNo = eventSequenceNo;
        this.eventLabel = eventLabel;
        this.shopId = shopId;
        this.eventParams = map;
        this.superProperties = superProperties;
        this.isAuto = isAuto;
        this.isDebug = isDebug;
        this.pageType = pageType;
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    public final boolean isAuto() {
        return this.isAuto;
    }

    public final boolean isDebug() {
        return this.isDebug;
    }

    private Event(Builder b) {
        this(b.getEventType(), b.getEventId(), b.getEventDesc(), b.getTimestamp(), b.getEventSequenceBatch(), b.getEventSequenceNo(), b.getEventLabel(), b.getShopId(), b.getEventParams(), b.getSuperProperties(), b.isAuto(), b.isDebug(), b.getPageType());
    }

    /* JADX INFO: compiled from: AnalyticsEvent.kt */
    @Metadata
    public static class Builder {
        private String eventDesc;
        private String eventId;
        private String eventLabel;
        private Map<String, ? extends Object> eventParams;
        private long eventSequenceBatch;
        private int eventSequenceNo;
        private String eventType;
        private boolean isAuto;
        private boolean isDebug;
        private String pageType;
        private String shopId;
        private JSONObject superProperties;
        private final long timestamp;

        public final String getEventId() {
            return this.eventId;
        }

        public final long getTimestamp() {
            return this.timestamp;
        }

        public final Map<String, Object> getEventParams() {
            return this.eventParams;
        }

        public final JSONObject getSuperProperties() {
            return this.superProperties;
        }

        public final String getEventType() {
            return this.eventType;
        }

        public final String getEventDesc() {
            return this.eventDesc;
        }

        public final long getEventSequenceBatch() {
            return this.eventSequenceBatch;
        }

        public final int getEventSequenceNo() {
            return this.eventSequenceNo;
        }

        public final String getEventLabel() {
            return this.eventLabel;
        }

        public final String getShopId() {
            return this.shopId;
        }

        public final boolean isAuto() {
            return this.isAuto;
        }

        public final boolean isDebug() {
            return this.isDebug;
        }

        public final String getPageType() {
            return this.pageType;
        }

        public Builder(String _eventId) {
            Intrinsics.checkParameterIsNotNull(_eventId, "_eventId");
            this.eventType = Constants.MAIN_VERSION_TAG;
            this.eventDesc = Constants.MAIN_VERSION_TAG;
            this.eventLabel = Constants.MAIN_VERSION_TAG;
            this.shopId = Constants.MAIN_VERSION_TAG;
            this.pageType = Constants.MAIN_VERSION_TAG;
            this.eventId = _eventId;
            this.timestamp = System.currentTimeMillis();
            this.eventParams = new HashMap();
            this.isAuto = false;
            this.isDebug = false;
        }

        public final Builder type(String type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            Builder $receiver = this;
            $receiver.eventType = type;
            return this;
        }

        public final Builder desc(String desc) {
            Intrinsics.checkParameterIsNotNull(desc, "desc");
            Builder $receiver = this;
            $receiver.eventDesc = desc;
            return this;
        }

        public final Builder sequenceBatch(long seqBatch) {
            Builder $receiver = this;
            $receiver.eventSequenceBatch = seqBatch;
            return this;
        }

        public final Builder sequenceNo(int seqNo) {
            Builder $receiver = this;
            $receiver.eventSequenceNo = seqNo;
            return this;
        }

        public final Builder shopId(String shopId) {
            Intrinsics.checkParameterIsNotNull(shopId, "shopId");
            Builder $receiver = this;
            $receiver.shopId = shopId;
            return this;
        }

        public final Builder params(Map<String, ? extends Object> map) {
            Builder $receiver = this;
            $receiver.eventParams = map;
            return this;
        }

        public final Builder pageType(String type) {
            Intrinsics.checkParameterIsNotNull(type, "type");
            Builder $receiver = this;
            $receiver.pageType = type;
            return this;
        }

        public final Builder isAuto(boolean _isAuto) {
            Builder $receiver = this;
            $receiver.isAuto = _isAuto;
            return this;
        }

        public final Event build() {
            return new Event(this, null);
        }
    }

    public final JSONObject toJson() throws JSONException {
        JSONObject mapJson;
        JSONObject $receiver = new JSONObject();
        $receiver.put(NotificationStyle.EXPANDABLE_IMAGE_URL, this.eventId);
        $receiver.put("en", this.eventDesc);
        $receiver.put("ts", this.timestamp);
        $receiver.put("et", this.eventType);
        $receiver.put("seqb", this.eventSequenceBatch);
        $receiver.put("seqn", this.eventSequenceNo);
        $receiver.put("el", this.eventLabel);
        $receiver.put("si", this.shopId);
        $receiver.put("pt", this.pageType);
        if (this.eventParams == null) {
            mapJson = null;
        } else {
            mapJson = new JSONObject();
            for (Map.Entry<String, Object> entry : this.eventParams.entrySet()) {
                String k = entry.getKey();
                Object v = entry.getValue();
                mapJson.put(k, v.toString());
            }
        }
        $receiver.put(RNMessageModule.PARAMS, mapJson);
        return $receiver;
    }
}
