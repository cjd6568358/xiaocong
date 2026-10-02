package kotlin.text;

import com.tencent.android.tpush.common.Constants;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

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
/* JADX INFO: compiled from: StringsJVM.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
    public static /* bridge */ /* synthetic */ String replace$default(String str, String str2, String str3, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return StringsKt.replace(str, str2, str3, z);
    }

    public static final String replace(String $receiver, String oldValue, String newValue, boolean ignoreCase) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
        Intrinsics.checkParameterIsNotNull(newValue, "newValue");
        return SequencesKt.joinToString(StringsKt.splitToSequence$default($receiver, new String[]{oldValue}, ignoreCase, 0, 4, null), (62 & 1) != 0 ? ", " : newValue, (62 & 2) != 0 ? Constants.MAIN_VERSION_TAG : null, (62 & 4) != 0 ? Constants.MAIN_VERSION_TAG : null, (62 & 8) != 0 ? -1 : 0, (62 & 16) != 0 ? "..." : null, (62 & 32) != 0 ? (Function1) null : null);
    }

    public static final boolean isBlank(CharSequence $receiver) {
        boolean z;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.length() != 0) {
            Iterable $receiver$iv = StringsKt.getIndices($receiver);
            if (!($receiver$iv instanceof Collection) || !((Collection) $receiver$iv).isEmpty()) {
                Iterator it = $receiver$iv.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = true;
                        break;
                    }
                    int element$iv = ((IntIterator) it).nextInt();
                    if (!CharsKt.isWhitespace($receiver.charAt(element$iv))) {
                        z = false;
                        break;
                    }
                }
            } else {
                z = true;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public static final boolean regionMatches(String $receiver, int thisOffset, String other, int otherOffset, int length, boolean ignoreCase) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (!ignoreCase) {
            return $receiver.regionMatches(thisOffset, other, otherOffset, length);
        }
        return $receiver.regionMatches(ignoreCase, thisOffset, other, otherOffset, length);
    }
}
