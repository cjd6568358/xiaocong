package com.youzan.mobile.growinganalytics.viewcrawler;

import android.os.Looper;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
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
/* JADX INFO: compiled from: ActivityViewsStack.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public class UIThreadSet<T> {
    private final Set<T> set = new LinkedHashSet();

    public void add(T t) {
        if (!(!Intrinsics.areEqual(Thread.currentThread(), Looper.getMainLooper().getThread()))) {
            this.set.add(t);
            return;
        }
        throw new RuntimeException("Can't add an activity when not on the UI thread");
    }

    public void remove(T t) {
        if (!(!Intrinsics.areEqual(Thread.currentThread(), Looper.getMainLooper().getThread()))) {
            this.set.remove(t);
            return;
        }
        throw new RuntimeException("Can't remove an activity when not on the UI thread");
    }

    public Set<T> getAll() {
        if (!(!Intrinsics.areEqual(Thread.currentThread(), Looper.getMainLooper().getThread()))) {
            Set<T> setUnmodifiableSet = Collections.unmodifiableSet(this.set);
            Intrinsics.checkExpressionValueIsNotNull(setUnmodifiableSet, "Collections.unmodifiableSet(set)");
            return setUnmodifiableSet;
        }
        throw new RuntimeException("Can't call getAll() when not on the UI thread");
    }
}
