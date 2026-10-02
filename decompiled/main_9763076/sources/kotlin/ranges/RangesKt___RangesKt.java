package kotlin.ranges;

import kotlin.Metadata;

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
/* JADX INFO: compiled from: _Ranges.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public class RangesKt___RangesKt extends RangesKt__RangesKt {
    public static final IntProgression downTo(int $receiver, int to) {
        return IntProgression.Companion.fromClosedRange($receiver, to, -1);
    }

    public static final int coerceAtLeast(int $receiver, int minimumValue) {
        return $receiver < minimumValue ? minimumValue : $receiver;
    }

    public static final int coerceAtMost(int $receiver, int maximumValue) {
        return $receiver > maximumValue ? maximumValue : $receiver;
    }

    public static final int coerceIn(int $receiver, int minimumValue, int maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        }
        if ($receiver < minimumValue) {
            return minimumValue;
        }
        return $receiver > maximumValue ? maximumValue : $receiver;
    }
}
