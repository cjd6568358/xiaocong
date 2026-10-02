package org.jetbrains.anko.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Database.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class DatabaseKt {
    private static final Pattern ARG_PATTERN;

    public static final long insert(SQLiteDatabase $receiver, String tableName, Pair<String, ? extends Object>... pairArr) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(pairArr, "values");
        return $receiver.insert(tableName, null, toContentValues(pairArr));
    }

    public static final void createTable(SQLiteDatabase $receiver, String tableName, boolean ifNotExists, Pair<String, ? extends SqlType>... pairArr) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        Intrinsics.checkParameterIsNotNull(pairArr, "columns");
        String escapedTableName = StringsKt.replace$default(tableName, "`", "``", false, 4, null);
        String ifNotExistsText = ifNotExists ? "IF NOT EXISTS" : Constants.MAIN_VERSION_TAG;
        Pair<String, ? extends SqlType>[] pairArr2 = pairArr;
        Collection destination$iv$iv = new ArrayList(pairArr2.length);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < pairArr2.length) {
                Object item$iv$iv = pairArr2[i2];
                Pair<String, ? extends SqlType> pair = (Pair) item$iv$iv;
                destination$iv$iv.add(pair.getFirst() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + pair.getSecond().render());
                i = i2 + 1;
            } else {
                $receiver.execSQL(CollectionsKt.joinToString((List) destination$iv$iv, (56 & 1) != 0 ? ", " : ", ", (56 & 2) != 0 ? Constants.MAIN_VERSION_TAG : "CREATE TABLE " + ifNotExistsText + " `" + escapedTableName + "`(", (56 & 4) != 0 ? Constants.MAIN_VERSION_TAG : ");", (56 & 8) != 0 ? -1 : 0, (56 & 16) != 0 ? "..." : null, (56 & 32) != 0 ? (Function1) null : null));
                return;
            }
        }
    }

    public static final void dropTable(SQLiteDatabase $receiver, String tableName, boolean ifExists) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(tableName, "tableName");
        String escapedTableName = StringsKt.replace$default(tableName, "`", "``", false, 4, null);
        String ifExistsText = ifExists ? "IF EXISTS" : Constants.MAIN_VERSION_TAG;
        $receiver.execSQL("DROP TABLE " + ifExistsText + " `" + escapedTableName + "`;");
    }

    static {
        Pattern patternCompile = Pattern.compile("([^\\\\])\\{([^{}]+)\\}");
        Intrinsics.checkExpressionValueIsNotNull(patternCompile, "Pattern.compile(\"([^\\\\\\\\])\\\\{([^{}]+)\\\\}\")");
        ARG_PATTERN = patternCompile;
    }

    public static final ContentValues toContentValues(Pair<String, ? extends Object>[] pairArr) {
        Intrinsics.checkParameterIsNotNull(pairArr, "$receiver");
        ContentValues values = new ContentValues();
        for (Pair<String, ? extends Object> pair : pairArr) {
            String key = pair.component1();
            Object value = pair.component2();
            if (Intrinsics.areEqual(value, (Object) null)) {
                values.putNull(key);
            } else if (value instanceof Boolean) {
                values.put(key, (Boolean) value);
            } else if (value instanceof Byte) {
                values.put(key, (Byte) value);
            } else if (value instanceof byte[]) {
                values.put(key, (byte[]) value);
            } else if (value instanceof Double) {
                values.put(key, (Double) value);
            } else if (value instanceof Float) {
                values.put(key, (Float) value);
            } else if (value instanceof Integer) {
                values.put(key, (Integer) value);
            } else if (value instanceof Long) {
                values.put(key, (Long) value);
            } else if (value instanceof Short) {
                values.put(key, (Short) value);
            } else {
                if (!(value instanceof String)) {
                    throw new IllegalArgumentException("Non-supported value type: " + value.getClass().getName());
                }
                values.put(key, (String) value);
            }
        }
        return values;
    }
}
