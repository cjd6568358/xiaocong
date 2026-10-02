package kotlin.ranges;

import com.tencent.android.tpush.common.Constants;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.internal.ProgressionUtilKt;
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
/* JADX INFO: compiled from: Progressions.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public class CharProgression implements Iterable<Character> {
    public static final Companion Companion = new Companion(null);
    private final char first;
    private final char last;
    private final int step;

    public CharProgression(char start, char endInclusive, int step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step must be non-zero");
        }
        this.first = start;
        this.last = (char) ProgressionUtilKt.getProgressionLastElement((int) start, (int) endInclusive, step);
        this.step = step;
    }

    public final char getFirst() {
        return this.first;
    }

    public final char getLast() {
        return this.last;
    }

    @Override // java.lang.Iterable
    public Iterator<Character> iterator() {
        return new CharProgressionIterator(this.first, this.last, this.step);
    }

    public boolean isEmpty() {
        if (this.step > 0) {
            return this.first > this.last;
        }
        return this.first < this.last;
    }

    public boolean equals(Object other) {
        return (other instanceof CharProgression) && ((isEmpty() && ((CharProgression) other).isEmpty()) || (this.first == ((CharProgression) other).first && this.last == ((CharProgression) other).last && this.step == ((CharProgression) other).step));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (((this.first * 31) + this.last) * 31) + this.step;
    }

    public String toString() {
        return this.step > 0 ? Constants.MAIN_VERSION_TAG + this.first + ".." + this.last + " step " + this.step : Constants.MAIN_VERSION_TAG + this.first + " downTo " + this.last + " step " + (-this.step);
    }

    /* JADX INFO: compiled from: Progressions.kt */
    @Metadata
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
