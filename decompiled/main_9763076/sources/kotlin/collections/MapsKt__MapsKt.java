package kotlin.collections;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Maps.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public class MapsKt__MapsKt extends MapsKt__MapsJVMKt {
    /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.TypeCastException */
    public static final <K, V> Map<K, V> emptyMap() throws TypeCastException {
        EmptyMap emptyMap = EmptyMap.INSTANCE;
        if (emptyMap == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
        }
        return emptyMap;
    }

    public static final <K, V> Map<K, V> mapOf(Pair<? extends K, ? extends V>... pairArr) {
        Intrinsics.checkParameterIsNotNull(pairArr, "pairs");
        return pairArr.length > 0 ? MapsKt.toMap(pairArr, new LinkedHashMap(MapsKt.mapCapacity(pairArr.length))) : MapsKt.emptyMap();
    }

    public static final int mapCapacity(int expectedSize) {
        if (expectedSize < 3) {
            return expectedSize + 1;
        }
        if (expectedSize < 1073741824) {
            return (expectedSize / 3) + expectedSize;
        }
        return Integer.MAX_VALUE;
    }

    public static final <K, V> void putAll(Map<? super K, ? super V> map, Pair<? extends K, ? extends V>[] pairArr) {
        Intrinsics.checkParameterIsNotNull(map, "$receiver");
        Intrinsics.checkParameterIsNotNull(pairArr, "pairs");
        for (Pair<? extends K, ? extends V> pair : pairArr) {
            Object key = pair.component1();
            Object value = pair.component2();
            map.put(key, value);
        }
    }

    public static final <K, V, M extends Map<? super K, ? super V>> M toMap(Pair<? extends K, ? extends V>[] pairArr, M m) {
        Intrinsics.checkParameterIsNotNull(pairArr, "$receiver");
        Intrinsics.checkParameterIsNotNull(m, "destination");
        MapsKt.putAll(m, pairArr);
        return m;
    }
}
