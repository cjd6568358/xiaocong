package com.youzan.mobile.growinganalytics;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.tencent.android.tpush.common.Constants;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ContextProvider.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class ContextProvider extends ContentProvider {
    public static final Companion Companion = new Companion(null);
    public static Context context;

    /* JADX INFO: compiled from: ContextProvider.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final void setContext$growing_analytics_release(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "<set-?>");
            ContextProvider.context = context;
        }
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri p0, ContentValues p1) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri p0, String[] p1, String p2, String[] p3, String p4) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Companion companion = Companion;
        Context context2 = getContext();
        Intrinsics.checkExpressionValueIsNotNull(context2, "context");
        companion.setContext$growing_analytics_release(context2);
        return false;
    }

    @Override // android.content.ContentProvider
    public int update(Uri p0, ContentValues p1, String p2, String[] p3) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri p0, String p1, String[] p2) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri p0) {
        return Constants.MAIN_VERSION_TAG;
    }
}
