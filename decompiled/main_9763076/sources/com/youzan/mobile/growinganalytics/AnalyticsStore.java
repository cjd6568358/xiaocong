package com.youzan.mobile.growinganalytics;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.anko.db.DatabaseKt;
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
/* JADX INFO: compiled from: AnalyticsStore.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class AnalyticsStore {
    public static final Companion Companion = new Companion(null);
    private static final Map<Context, AnalyticsStore> instanceMap = new LinkedHashMap();
    private final EventDBHelper mDb;

    /* JADX INFO: compiled from: AnalyticsStore.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private final Map<Context, AnalyticsStore> getInstanceMap() {
            return AnalyticsStore.instanceMap;
        }

        public final AnalyticsStore getInstance(Context ctx) {
            AnalyticsStore $receiver;
            Intrinsics.checkParameterIsNotNull(ctx, "ctx");
            synchronized (getInstanceMap()) {
                Context appContext = ctx.getApplicationContext();
                if (!AnalyticsStore.Companion.getInstanceMap().containsKey(appContext)) {
                    Intrinsics.checkExpressionValueIsNotNull(appContext, "appContext");
                    $receiver = new AnalyticsStore(appContext, null, 2, null);
                    AnalyticsStore.Companion.getInstanceMap().put(appContext, $receiver);
                } else {
                    AnalyticsStore analyticsStore = AnalyticsStore.Companion.getInstanceMap().get(appContext);
                    if (analyticsStore == null) {
                        Intrinsics.throwNpe();
                    }
                    $receiver = analyticsStore;
                }
            }
            return $receiver;
        }
    }

    /* synthetic */ AnalyticsStore(Context context, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? Table.EVENTS.getTableName() : str);
    }

    private AnalyticsStore(Context ctx, String dbName) {
        this.mDb = new EventDBHelper(ctx, dbName);
    }

    public final long insert(Event event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String data = event.toJson().toString();
        Intrinsics.checkExpressionValueIsNotNull(data, "data");
        return insert$default(this, data, event.getTimestamp(), event.isAuto(), event.isDebug(), null, 16, null);
    }

    static /* bridge */ /* synthetic */ long insert$default(AnalyticsStore analyticsStore, String str, long j, boolean z, boolean z2, DataType dataType, int i, Object obj) {
        return analyticsStore.insert(str, j, (i & 4) != 0 ? false : z, (i & 8) == 0 ? z2 : false, (i & 16) != 0 ? DataType.EVENT : dataType);
    }

    private final long insert(String data, long createdAt, boolean isAuto, boolean isDebug, DataType type) {
        if (!belowMemThreshold()) {
            return -2L;
        }
        SQLiteDatabase db = this.mDb.getWritableDatabase();
        String tableName = Table.EVENTS.getTableName();
        Pair[] pairArr = new Pair[7];
        pairArr[0] = TuplesKt.to(AnalyticsStoreKt.COLUMN_ID, null);
        pairArr[1] = TuplesKt.to(AnalyticsStoreKt.COLUMN_DATA, data);
        pairArr[2] = TuplesKt.to(AnalyticsStoreKt.COLUMN_CREATED_AT, Long.valueOf(createdAt));
        pairArr[3] = TuplesKt.to(AnalyticsStoreKt.COLUMN_DATA_LENGTH, Integer.valueOf(data.length()));
        pairArr[4] = TuplesKt.to(AnalyticsStoreKt.COLUMN_IS_AUTO, Integer.valueOf(isAuto ? 1 : 0));
        pairArr[5] = TuplesKt.to(AnalyticsStoreKt.COLUMN_IS_DEBUG, Integer.valueOf(isDebug ? 1 : 0));
        pairArr[6] = TuplesKt.to(AnalyticsStoreKt.COLUMN_TYPE, Integer.valueOf(type.getType()));
        long jInsert = DatabaseKt.insert(db, tableName, pairArr);
        db.close();
        return jInsert;
    }

    public final File getDatabaseFile() {
        return this.mDb.getDBFile();
    }

    public final void generateData(Function3<? super Long, ? super List<JSONObject>, ? super Integer, Unit> function3, long reqThreshold) {
        JSONObject json;
        Intrinsics.checkParameterIsNotNull(function3, "operator");
        SQLiteDatabase db = this.mDb.getReadableDatabase();
        Cursor cursor = (Cursor) null;
        String queueSql = "SELECT * FROM " + Table.EVENTS.getTableName() + " WHERE " + AnalyticsStoreKt.COLUMN_IS_DEBUG + " = 0 AND " + AnalyticsStoreKt.COLUMN_TYPE + " = " + DataType.EVENT.getType();
        try {
            cursor = db.rawQuery(queueSql, null);
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex(AnalyticsStoreKt.COLUMN_ID);
            int dataIndex = cursor.getColumnIndex(AnalyticsStoreKt.COLUMN_DATA);
            int dataLengthIndex = cursor.getColumnIndex(AnalyticsStoreKt.COLUMN_DATA_LENGTH);
            int dataLength = 0;
            int queueCount = 0;
            List eventList = new ArrayList();
            while (!cursor.isAfterLast()) {
                int count = cursor.getInt(dataLengthIndex);
                if (dataLength + count >= reqThreshold) {
                    if (!cursor.isBeforeFirst()) {
                        cursor.moveToPrevious();
                    }
                    long lastId = cursor.getLong(idIndex);
                    function3.invoke(Long.valueOf(lastId), eventList, Integer.valueOf(queueCount));
                    eventList.clear();
                    queueCount = 0;
                } else {
                    String data$iv = cursor.getString(dataIndex);
                    Intrinsics.checkExpressionValueIsNotNull(data$iv, "cursor.getString(dataIndex)");
                    try {
                        json = new JSONObject(data$iv);
                    } catch (JSONException e) {
                        json = null;
                    }
                    if (json != null) {
                        eventList.add(json);
                        dataLength += cursor.getInt(dataLengthIndex);
                        queueCount++;
                    }
                    if (cursor.isLast()) {
                        long lastId2 = cursor.getLong(idIndex);
                        function3.invoke(Long.valueOf(lastId2), eventList, Integer.valueOf(queueCount));
                    }
                }
                cursor.moveToNext();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public static /* bridge */ /* synthetic */ void cleanUpEventsById$default(AnalyticsStore analyticsStore, long j, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        analyticsStore.cleanUpEventsById(j, z);
    }

    public final void cleanUpEventsById(long lastId, boolean isIncludeAuto) {
        try {
            SQLiteDatabase db = this.mDb.getWritableDatabase();
            StringBuilder deleteQuery = new StringBuilder(Constants.MAIN_VERSION_TAG + AnalyticsStoreKt.COLUMN_ID + " <= " + lastId);
            if (!isIncludeAuto) {
                deleteQuery.append("AND " + AnalyticsStoreKt.COLUMN_IS_AUTO + " = 0");
            }
            db.delete(Table.EVENTS.getTableName(), deleteQuery.toString(), null);
        } catch (SQLiteException e) {
            this.mDb.deleteDatabase();
        } finally {
            this.mDb.close();
        }
    }

    public static /* bridge */ /* synthetic */ void cleanUpEventsByTime$default(AnalyticsStore analyticsStore, long j, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        analyticsStore.cleanUpEventsByTime(j, z);
    }

    public final void cleanUpEventsByTime(long timestamp, boolean isIncludeAuto) {
        try {
            SQLiteDatabase db = this.mDb.getWritableDatabase();
            StringBuilder deleteQuery = new StringBuilder(Constants.MAIN_VERSION_TAG + AnalyticsStoreKt.COLUMN_CREATED_AT + " <= " + timestamp);
            if (!isIncludeAuto) {
                deleteQuery.append("AND " + AnalyticsStoreKt.COLUMN_IS_AUTO + " = 0");
            }
            db.delete(Table.EVENTS.getTableName(), deleteQuery.toString(), null);
        } catch (SQLiteException e) {
            this.mDb.deleteDatabase();
        } finally {
            this.mDb.close();
        }
    }

    public final void deleteDB() {
        this.mDb.deleteDatabase();
    }

    protected final boolean belowMemThreshold() {
        return this.mDb.belowMemThreshold();
    }
}
