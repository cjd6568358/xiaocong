package kotlin.ranges;

import com.tencent.android.tpush.common.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
/* JADX INFO: compiled from: Ranges.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class CharRange extends CharProgression {
    public static final Companion Companion = new Companion(null);
    private static final CharRange EMPTY = new CharRange((char) 1, (char) 0);

    public CharRange(char start, char endInclusive) {
        super(start, endInclusive, 1);
    }

    @Override // kotlin.ranges.CharProgression
    public boolean isEmpty() {
        return getFirst() > getLast();
    }

    @Override // kotlin.ranges.CharProgression
    public boolean equals(Object other) {
        return (other instanceof CharRange) && ((isEmpty() && ((CharRange) other).isEmpty()) || (getFirst() == ((CharRange) other).getFirst() && getLast() == ((CharRange) other).getLast()));
    }

    @Override // kotlin.ranges.CharProgression
    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (getFirst() * 31) + getLast();
    }

    @Override // kotlin.ranges.CharProgression
    public String toString() {
        return Constants.MAIN_VERSION_TAG + getFirst() + ".." + getLast();
    }

    /* JADX INFO: compiled from: Ranges.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
