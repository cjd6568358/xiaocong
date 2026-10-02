package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: Sequences.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class TransformingSequence<T, R> implements Sequence<R> {
    private final Sequence<T> sequence;
    private final Function1<T, R> transformer;

    /* JADX WARN: Multi-variable type inference failed */
    public TransformingSequence(Sequence<? extends T> sequence, Function1<? super T, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(sequence, "sequence");
        Intrinsics.checkParameterIsNotNull(function1, "transformer");
        this.sequence = sequence;
        this.transformer = function1;
    }

    @Override // kotlin.sequences.Sequence
    public Iterator<R> iterator() {
        return new Iterator<R>() { // from class: kotlin.sequences.TransformingSequence.iterator.1
            private final Iterator<T> iterator;

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }

            {
                this.iterator = TransformingSequence.this.sequence.iterator();
            }

            @Override // java.util.Iterator
            public R next() {
                return (R) TransformingSequence.this.transformer.invoke(this.iterator.next());
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.iterator.hasNext();
            }
        };
    }
}
