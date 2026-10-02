package com.youzan.mobile.growinganalytics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AnalyticsAPI.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public enum AutoEvent {
    EnterPage("enterpage", "display"),
    LeavePage("leavepage", "display"),
    Session("session", "display");

    private final String eventId;
    private final String eventType;

    AutoEvent(String eventId, String eventType) {
        Intrinsics.checkParameterIsNotNull(eventId, "eventId");
        Intrinsics.checkParameterIsNotNull(eventType, "eventType");
        this.eventId = eventId;
        this.eventType = eventType;
    }

    public final String getEventId() {
        return this.eventId;
    }

    public final String getEventType() {
        return this.eventType;
    }
}
