package kotlin.ranges;

import com.tencent.android.tpush.common.Constants;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Ranges.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
final class ClosedDoubleRange {
    private final double _endInclusive;
    private final double _start;

    public boolean isEmpty() {
        return this._start > this._endInclusive;
    }

    public boolean equals(Object other) {
        return (other instanceof ClosedDoubleRange) && ((isEmpty() && ((ClosedDoubleRange) other).isEmpty()) || (this._start == ((ClosedDoubleRange) other)._start && this._endInclusive == ((ClosedDoubleRange) other)._endInclusive));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (Double.valueOf(this._start).hashCode() * 31) + Double.valueOf(this._endInclusive).hashCode();
    }

    public String toString() {
        return Constants.MAIN_VERSION_TAG + this._start + ".." + this._endInclusive;
    }
}
