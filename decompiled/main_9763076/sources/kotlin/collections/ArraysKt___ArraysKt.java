package kotlin.collections;

import java.util.List;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: _Arrays.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public class ArraysKt___ArraysKt extends ArraysKt__ArraysKt {
    public static final <T> boolean contains(T[] tArr, T t) {
        Intrinsics.checkParameterIsNotNull(tArr, "$receiver");
        return ArraysKt.indexOf(tArr, t) >= 0;
    }

    public static final <T> int indexOf(T[] tArr, T t) {
        int i = 0;
        Intrinsics.checkParameterIsNotNull(tArr, "$receiver");
        if (t == null) {
            int length = tArr.length;
            while (i < length) {
                if (tArr[i] != null) {
                    int index = i + 1;
                    i = index;
                } else {
                    return i;
                }
            }
        } else {
            int length2 = tArr.length;
            while (i < length2) {
                if (!Intrinsics.areEqual(t, tArr[i])) {
                    int index2 = i + 1;
                    i = index2;
                } else {
                    return i;
                }
            }
        }
        return -1;
    }

    public static final <T> List<T> asList(T[] tArr) {
        Intrinsics.checkParameterIsNotNull(tArr, "$receiver");
        List<T> listAsList = ArraysUtilJVM.asList(tArr);
        Intrinsics.checkExpressionValueIsNotNull(listAsList, "ArraysUtilJVM.asList(this)");
        return listAsList;
    }
}
