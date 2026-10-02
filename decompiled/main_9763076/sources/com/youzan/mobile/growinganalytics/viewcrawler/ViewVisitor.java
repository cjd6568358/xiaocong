package com.youzan.mobile.growinganalytics.viewcrawler;

import android.view.View;
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
/* JADX INFO: compiled from: ViewVisitor.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public abstract class ViewVisitor implements Accumulator {
    private final boolean debounce;
    private final String eventName;
    private final OnEventListener listener;
    private final ViewFinder viewFinder;

    public ViewVisitor(ViewFinder viewFinder, String eventName, OnEventListener listener, boolean debounce) {
        Intrinsics.checkParameterIsNotNull(viewFinder, "viewFinder");
        Intrinsics.checkParameterIsNotNull(eventName, "eventName");
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        this.viewFinder = viewFinder;
        this.eventName = eventName;
        this.listener = listener;
        this.debounce = debounce;
    }

    public final void fireEvent(View found) {
        Intrinsics.checkParameterIsNotNull(found, "found");
        this.listener.onEvent(found, this.eventName, this.debounce);
    }

    public final String getEventName() {
        return this.eventName;
    }

    public final ViewFinder getPathFinder() {
        return this.viewFinder;
    }
}
