package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;

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
/* JADX INFO: compiled from: Strings.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
final class DelimitedRangesSequence implements Sequence<IntRange> {
    private final Function2<CharSequence, Integer, Pair<Integer, Integer>> getNextMatch;
    private final CharSequence input;
    private final int limit;
    private final int startIndex;

    /* JADX WARN: Multi-variable type inference failed */
    public DelimitedRangesSequence(CharSequence input, int startIndex, int limit, Function2<? super CharSequence, ? super Integer, Pair<Integer, Integer>> function2) {
        Intrinsics.checkParameterIsNotNull(input, "input");
        Intrinsics.checkParameterIsNotNull(function2, "getNextMatch");
        this.input = input;
        this.startIndex = startIndex;
        this.limit = limit;
        this.getNextMatch = function2;
    }

    @Override // kotlin.sequences.Sequence
    public Iterator<IntRange> iterator() {
        return new Iterator<IntRange>() { // from class: kotlin.text.DelimitedRangesSequence.iterator.1
            private int counter;
            private int currentStartIndex;
            private IntRange nextItem;
            private int nextSearchIndex;
            private int nextState = -1;

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            }

            {
                this.currentStartIndex = RangesKt.coerceIn(DelimitedRangesSequence.this.startIndex, 0, DelimitedRangesSequence.this.input.length());
                this.nextSearchIndex = this.currentStartIndex;
            }

            /* JADX WARN: Code duplicated, block: B:10:0x0027  */
            /* JADX WARN: Code duplicated, block: B:12:0x0035 A[ADDED_TO_REGION, REMOVE] */
            /* JADX WARN: Code duplicated, block: B:19:0x00a4  */
            /* JADX WARN: Code duplicated, block: B:21:0x00a9  */
            private final void calcNext() {
                Pair match;
                int length;
                int i;
                if (this.nextSearchIndex >= 0) {
                    if (DelimitedRangesSequence.this.limit > 0) {
                        this.counter++;
                        if (this.counter < DelimitedRangesSequence.this.limit) {
                            if (this.nextSearchIndex <= DelimitedRangesSequence.this.input.length() || (match = (Pair) DelimitedRangesSequence.this.getNextMatch.invoke(DelimitedRangesSequence.this.input, Integer.valueOf(this.nextSearchIndex))) == null) {
                                this.nextItem = new IntRange(this.currentStartIndex, StringsKt.getLastIndex(DelimitedRangesSequence.this.input));
                                this.nextSearchIndex = -1;
                            } else {
                                int iIntValue = ((Number) match.component1()).intValue();
                                length = ((Number) match.component2()).intValue();
                                this.nextItem = new IntRange(this.currentStartIndex, iIntValue - 1);
                                this.currentStartIndex = iIntValue + length;
                                int i2 = this.currentStartIndex;
                                if (length == 0) {
                                    i = 1;
                                } else {
                                    i = 0;
                                }
                                this.nextSearchIndex = i + i2;
                            }
                        } else {
                            this.nextItem = new IntRange(this.currentStartIndex, StringsKt.getLastIndex(DelimitedRangesSequence.this.input));
                            this.nextSearchIndex = -1;
                        }
                    } else if (this.nextSearchIndex <= DelimitedRangesSequence.this.input.length()) {
                        this.nextItem = new IntRange(this.currentStartIndex, StringsKt.getLastIndex(DelimitedRangesSequence.this.input));
                        this.nextSearchIndex = -1;
                    } else {
                        int iIntValue2 = ((Number) match.component1()).intValue();
                        length = ((Number) match.component2()).intValue();
                        this.nextItem = new IntRange(this.currentStartIndex, iIntValue2 - 1);
                        this.currentStartIndex = iIntValue2 + length;
                        int i3 = this.currentStartIndex;
                        if (length == 0) {
                            i = 1;
                        } else {
                            i = 0;
                        }
                        this.nextSearchIndex = i + i3;
                    }
                    this.nextState = 1;
                    return;
                }
                this.nextState = 0;
                this.nextItem = (IntRange) null;
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.TypeCastException */
            @Override // java.util.Iterator
            public IntRange next() throws TypeCastException {
                if (this.nextState == -1) {
                    calcNext();
                }
                if (this.nextState == 0) {
                    throw new NoSuchElementException();
                }
                IntRange result = this.nextItem;
                if (result == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.ranges.IntRange");
                }
                this.nextItem = (IntRange) null;
                this.nextState = -1;
                return result;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.nextState == -1) {
                    calcNext();
                }
                return this.nextState == 1;
            }
        };
    }
}
