package com.youzan.mobile.growinganalytics.viewcrawler;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: ViewFinder.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
final class IntStack {
    private final int[] stack;
    private int stackSize;

    /* JADX WARN: Multi-variable type inference failed */
    public IntStack() {
        this(null, 0, 3, 0 == true ? 1 : 0);
    }

    public IntStack(int[] stack, int stackSize) {
        Intrinsics.checkParameterIsNotNull(stack, "stack");
        this.stack = stack;
        this.stackSize = stackSize;
    }

    public /* synthetic */ IntStack(int[] iArr, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? new int[256] : iArr, (i2 & 2) != 0 ? 0 : i);
    }

    public final boolean full() {
        return this.stack.length == this.stackSize;
    }

    public final int alloc() {
        int index = this.stackSize;
        this.stackSize++;
        this.stack[index] = 0;
        return index;
    }

    public final void increment(int index) {
        int[] iArr = this.stack;
        iArr[index] = iArr[index] + 1;
    }

    public final void free() {
        this.stackSize--;
        if (this.stackSize < 0) {
            throw new ArrayIndexOutOfBoundsException(this.stackSize);
        }
    }
}
