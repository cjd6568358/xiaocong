package org.jetbrains.anko.db;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
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
/* JADX INFO: compiled from: sqlTypes.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
class SqlTypeImpl implements SqlType {
    private final String modifiers;
    private final String name;

    public SqlTypeImpl(String name, String modifiers) {
        Intrinsics.checkParameterIsNotNull(name, RNMessageModule.NAME);
        this.name = name;
        this.modifiers = modifiers;
    }

    public /* synthetic */ SqlTypeImpl(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? (String) null : str2);
    }

    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.anko.db.SqlType
    public String render() {
        return this.modifiers == null ? getName() : getName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.modifiers;
    }

    @Override // org.jetbrains.anko.db.SqlType
    public SqlType plus(SqlTypeModifier m) {
        Intrinsics.checkParameterIsNotNull(m, "m");
        return new SqlTypeImpl(getName(), this.modifiers == null ? m.getModifier() : this.modifiers + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + m.getModifier());
    }
}
