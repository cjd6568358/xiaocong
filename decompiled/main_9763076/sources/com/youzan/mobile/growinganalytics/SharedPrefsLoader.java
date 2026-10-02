package com.youzan.mobile.growinganalytics;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: SharedPrefsManager.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class SharedPrefsLoader {
    private final Executor executor;

    public SharedPrefsLoader() {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Intrinsics.checkExpressionValueIsNotNull(executorServiceNewSingleThreadExecutor, "Executors.newSingleThreadExecutor()");
        this.executor = executorServiceNewSingleThreadExecutor;
    }

    public final Future<SharedPreferences> loadPrefs(Context ctx, String prefsName, Function1<? super SharedPreferences, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(ctx, "ctx");
        Intrinsics.checkParameterIsNotNull(prefsName, "prefsName");
        Intrinsics.checkParameterIsNotNull(function1, "callback");
        LoadSharedPrefs loadSharedPrefs = new LoadSharedPrefs(ctx, prefsName, function1);
        FutureTask task = new FutureTask(loadSharedPrefs);
        this.executor.execute(task);
        return task;
    }

    /* JADX INFO: compiled from: SharedPrefsManager.kt */
    @Metadata
    private static final class LoadSharedPrefs implements Callable<SharedPreferences> {
        private final Context context;
        private final Function1<SharedPreferences, Unit> loadedCallback;
        private final String prefsName;

        /* JADX WARN: Multi-variable type inference failed */
        public LoadSharedPrefs(Context context, String prefsName, Function1<? super SharedPreferences, Unit> function1) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(prefsName, "prefsName");
            Intrinsics.checkParameterIsNotNull(function1, "loadedCallback");
            this.context = context;
            this.prefsName = prefsName;
            this.loadedCallback = function1;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public SharedPreferences call() {
            SharedPreferences prefs = this.context.getSharedPreferences(this.prefsName, 0);
            if (this.loadedCallback != null) {
                Function1<SharedPreferences, Unit> function1 = this.loadedCallback;
                Intrinsics.checkExpressionValueIsNotNull(prefs, "prefs");
                function1.invoke(prefs);
            }
            Intrinsics.checkExpressionValueIsNotNull(prefs, "prefs");
            return prefs;
        }
    }
}
