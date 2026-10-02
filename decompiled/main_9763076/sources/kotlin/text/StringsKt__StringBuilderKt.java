package kotlin.text;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: StringBuilder.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public class StringsKt__StringBuilderKt extends StringsKt__StringBuilderJVMKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> void appendElement(Appendable $receiver, T t, Function1<? super T, ? extends CharSequence> function1) throws IOException {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if (function1 == null) {
            if (!(t != 0 ? t instanceof CharSequence : true)) {
                if (!(t instanceof Character)) {
                    $receiver.append(String.valueOf(t));
                    return;
                } else {
                    $receiver.append(((Character) t).charValue());
                    return;
                }
            }
            $receiver.append((CharSequence) t);
            return;
        }
        $receiver.append(function1.invoke(t));
    }
}
