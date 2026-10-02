package org.jetbrains.anko.db;

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
/* JADX INFO: compiled from: sqlTypes.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class SqlTypesKt {
    private static final SqlType NULL = new SqlTypeImpl("NULL", null, 2, null);
    private static final SqlType INTEGER = new SqlTypeImpl("INTEGER", null, 2, null);
    private static final SqlType REAL = new SqlTypeImpl("REAL", null, 2, null);
    private static final SqlType TEXT = new SqlTypeImpl("TEXT", null, 2, null);
    private static final SqlType BLOB = new SqlTypeImpl("BLOB", null, 2, null);
    private static final SqlTypeModifier PRIMARY_KEY = new SqlTypeModifierImpl("PRIMARY KEY");
    private static final SqlTypeModifier NOT_NULL = new SqlTypeModifierImpl("NOT NULL");
    private static final SqlTypeModifier AUTOINCREMENT = new SqlTypeModifierImpl("AUTOINCREMENT");
    private static final SqlTypeModifier UNIQUE = new SqlTypeModifierImpl("UNIQUE");

    public static final SqlType getINTEGER() {
        return INTEGER;
    }

    public static final SqlType getTEXT() {
        return TEXT;
    }

    public static final SqlTypeModifier getPRIMARY_KEY() {
        return PRIMARY_KEY;
    }

    public static final SqlTypeModifier getNOT_NULL() {
        return NOT_NULL;
    }

    public static final SqlTypeModifier getAUTOINCREMENT() {
        return AUTOINCREMENT;
    }

    public static final SqlTypeModifier DEFAULT(String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return new SqlTypeModifierImpl("DEFAULT " + value);
    }
}
