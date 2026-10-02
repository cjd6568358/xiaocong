package kotlin.sequences;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

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
/* JADX INFO: compiled from: _Sequences.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public class SequencesKt___SequencesKt extends SequencesKt__SequencesKt {
    public static final <T, R> Sequence<R> map(Sequence<? extends T> sequence, Function1<? super T, ? extends R> function1) {
        Intrinsics.checkParameterIsNotNull(sequence, "$receiver");
        Intrinsics.checkParameterIsNotNull(function1, "transform");
        return new TransformingSequence(sequence, function1);
    }

    public static final <T, A extends Appendable> A joinTo(Sequence<? extends T> sequence, A a, CharSequence separator, CharSequence prefix, CharSequence postfix, int limit, CharSequence truncated, Function1<? super T, ? extends CharSequence> function1) throws IOException {
        Intrinsics.checkParameterIsNotNull(sequence, "$receiver");
        Intrinsics.checkParameterIsNotNull(a, "buffer");
        Intrinsics.checkParameterIsNotNull(separator, "separator");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        Intrinsics.checkParameterIsNotNull(postfix, "postfix");
        Intrinsics.checkParameterIsNotNull(truncated, "truncated");
        a.append(prefix);
        int count = 0;
        for (Object element : sequence) {
            count++;
            if (count > 1) {
                a.append(separator);
            }
            if (limit >= 0 && count > limit) {
                break;
            }
            StringsKt.appendElement(a, element, function1);
        }
        if (limit >= 0 && count > limit) {
            a.append(truncated);
        }
        a.append(postfix);
        return a;
    }

    public static final <T> String joinToString(Sequence<? extends T> sequence, CharSequence separator, CharSequence prefix, CharSequence postfix, int limit, CharSequence truncated, Function1<? super T, ? extends CharSequence> function1) {
        Intrinsics.checkParameterIsNotNull(sequence, "$receiver");
        Intrinsics.checkParameterIsNotNull(separator, "separator");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        Intrinsics.checkParameterIsNotNull(postfix, "postfix");
        Intrinsics.checkParameterIsNotNull(truncated, "truncated");
        String string = ((StringBuilder) SequencesKt.joinTo(sequence, new StringBuilder(), separator, prefix, postfix, limit, truncated, function1)).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "joinTo(StringBuilder(), …ed, transform).toString()");
        return string;
    }
}
