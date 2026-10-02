package com.youzan.mobile.growinganalytics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.anko.db.DatabaseKt;
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper;
import org.jetbrains.anko.db.SqlTypesKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AnalyticsStore.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
final class EventDBHelper extends ManagedSQLiteOpenHelper {
    private final AnalyticsConfig config;
    private final File dbFile;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EventDBHelper(Context ctx, String dbName) {
        super(ctx, dbName, null, AnalyticsStoreKt.getDB_VERSION());
        Intrinsics.checkParameterIsNotNull(ctx, "ctx");
        Intrinsics.checkParameterIsNotNull(dbName, "dbName");
        File databasePath = ctx.getDatabasePath(dbName);
        Intrinsics.checkExpressionValueIsNotNull(databasePath, "ctx.getDatabasePath(dbName)");
        this.dbFile = databasePath;
        this.config = AnalyticsConfig.Companion.getInstance(ctx);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        if (db != null) {
            DatabaseKt.createTable(db, Table.EVENTS.getTableName(), true, TuplesKt.to(AnalyticsStoreKt.COLUMN_ID, SqlTypesKt.getINTEGER().plus(SqlTypesKt.getPRIMARY_KEY()).plus(SqlTypesKt.getAUTOINCREMENT())), TuplesKt.to(AnalyticsStoreKt.COLUMN_DATA, SqlTypesKt.getTEXT().plus(SqlTypesKt.getNOT_NULL())), TuplesKt.to(AnalyticsStoreKt.COLUMN_CREATED_AT, SqlTypesKt.getINTEGER().plus(SqlTypesKt.getNOT_NULL())), TuplesKt.to(AnalyticsStoreKt.COLUMN_DATA_LENGTH, SqlTypesKt.getINTEGER().plus(SqlTypesKt.getNOT_NULL()).plus(SqlTypesKt.DEFAULT(PushConstants.PUSH_TYPE_NOTIFY))), TuplesKt.to(AnalyticsStoreKt.COLUMN_IS_AUTO, SqlTypesKt.getINTEGER().plus(SqlTypesKt.getNOT_NULL()).plus(SqlTypesKt.DEFAULT(PushConstants.PUSH_TYPE_NOTIFY))), TuplesKt.to(AnalyticsStoreKt.COLUMN_IS_DEBUG, SqlTypesKt.getINTEGER().plus(SqlTypesKt.getNOT_NULL()).plus(SqlTypesKt.DEFAULT(PushConstants.PUSH_TYPE_NOTIFY))), TuplesKt.to(AnalyticsStoreKt.COLUMN_TYPE, SqlTypesKt.getINTEGER().plus(SqlTypesKt.DEFAULT("1"))));
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db != null) {
            DatabaseKt.dropTable(db, Table.EVENTS.getTableName(), true);
        }
    }

    public final void deleteDatabase() {
        close();
        this.dbFile.delete();
    }

    public final boolean belowMemThreshold() {
        return !this.dbFile.exists() || Math.max(this.dbFile.getUsableSpace(), this.config.getMinDatabaseLimit()) >= this.dbFile.length();
    }

    public final File getDBFile() {
        return this.dbFile;
    }
}
