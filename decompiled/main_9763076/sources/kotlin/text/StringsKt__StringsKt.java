package kotlin.text;

import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.tencent.android.tpush.SettingsContentProvider;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: Strings.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public class StringsKt__StringsKt extends StringsKt__StringsJVMKt {
    public static final IntRange getIndices(CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new IntRange(0, $receiver.length() - 1);
    }

    public static final int getLastIndex(CharSequence $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.length() - 1;
    }

    public static final String substring(CharSequence $receiver, IntRange range) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(range, "range");
        return $receiver.subSequence(range.getStart().intValue(), range.getEndInclusive().intValue() + 1).toString();
    }

    public static final boolean regionMatchesImpl(CharSequence $receiver, int thisOffset, CharSequence other, int otherOffset, int length, boolean ignoreCase) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (otherOffset < 0 || thisOffset < 0 || thisOffset > $receiver.length() - length || otherOffset > other.length() - length) {
            return false;
        }
        int i = length - 1;
        if (0 <= i) {
            for (int i2 = 0; CharsKt.equals($receiver.charAt(thisOffset + i2), other.charAt(otherOffset + i2), ignoreCase); i2++) {
                if (i2 != i) {
                }
            }
            return false;
        }
        return true;
    }

    static /* bridge */ /* synthetic */ int indexOf$StringsKt__StringsKt$default(CharSequence charSequence, CharSequence charSequence2, int i, int i2, boolean z, boolean z2, int i3, Object obj) {
        return indexOf$StringsKt__StringsKt(charSequence, charSequence2, i, i2, z, (i3 & 16) != 0 ? false : z2);
    }

    private static final int indexOf$StringsKt__StringsKt(CharSequence $receiver, CharSequence other, int startIndex, int endIndex, boolean ignoreCase, boolean last) {
        IntProgression indices;
        if (!last) {
            indices = new IntRange(RangesKt.coerceAtLeast(startIndex, 0), RangesKt.coerceAtMost(endIndex, $receiver.length()));
        } else {
            indices = RangesKt.downTo(RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($receiver)), RangesKt.coerceAtLeast(endIndex, 0));
        }
        if (($receiver instanceof String) && (other instanceof String)) {
            int first = indices.getFirst();
            int last2 = indices.getLast();
            int step = indices.getStep();
            if (step <= 0 ? first >= last2 : first <= last2) {
                while (!StringsKt.regionMatches((String) other, 0, (String) $receiver, first, other.length(), ignoreCase)) {
                    if (first != last2) {
                        first += step;
                    }
                }
                return first;
            }
        } else {
            int first2 = indices.getFirst();
            int last3 = indices.getLast();
            int step2 = indices.getStep();
            if (step2 <= 0 ? first2 >= last3 : first2 <= last3) {
                while (!StringsKt.regionMatchesImpl(other, 0, $receiver, first2, other.length(), ignoreCase)) {
                    if (first2 != last3) {
                        first2 += step2;
                    }
                }
                return first2;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Pair<Integer, String> findAnyOf$StringsKt__StringsKt(CharSequence $receiver, Collection<String> collection, int startIndex, boolean ignoreCase, boolean last) {
        Object obj;
        Object obj2;
        if (!ignoreCase && collection.size() == 1) {
            String string = (String) CollectionsKt.single(collection);
            int index = !last ? StringsKt.indexOf$default($receiver, string, startIndex, false, 4, null) : StringsKt.lastIndexOf$default($receiver, string, startIndex, false, 4, null);
            if (index < 0) {
                return null;
            }
            return TuplesKt.to(Integer.valueOf(index), string);
        }
        IntProgression indices = !last ? new IntRange(RangesKt.coerceAtLeast(startIndex, 0), $receiver.length()) : RangesKt.downTo(RangesKt.coerceAtMost(startIndex, StringsKt.getLastIndex($receiver)), 0);
        if ($receiver instanceof String) {
            int first = indices.getFirst();
            int last2 = indices.getLast();
            int step = indices.getStep();
            if (step <= 0 ? first >= last2 : first <= last2) {
                while (true) {
                    Collection<String> $receiver$iv = collection;
                    Iterator it = $receiver$iv.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Object element$iv = it.next();
                            String it2 = (String) element$iv;
                            if (StringsKt.regionMatches(it2, 0, (String) $receiver, first, it2.length(), ignoreCase)) {
                                obj2 = element$iv;
                                break;
                            }
                        } else {
                            obj2 = null;
                            break;
                        }
                    }
                    String matchingString = (String) obj2;
                    if (matchingString == null) {
                        if (first == last2) {
                            break;
                        }
                        first += step;
                    } else {
                        return TuplesKt.to(Integer.valueOf(first), matchingString);
                    }
                }
            }
        } else {
            int first2 = indices.getFirst();
            int last3 = indices.getLast();
            int step2 = indices.getStep();
            if (step2 <= 0 ? first2 >= last3 : first2 <= last3) {
                while (true) {
                    Collection<String> $receiver$iv2 = collection;
                    Iterator it3 = $receiver$iv2.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            Object element$iv2 = it3.next();
                            String it4 = (String) element$iv2;
                            if (StringsKt.regionMatchesImpl(it4, 0, $receiver, first2, it4.length(), ignoreCase)) {
                                obj = element$iv2;
                                break;
                            }
                        } else {
                            obj = null;
                            break;
                        }
                    }
                    String matchingString2 = (String) obj;
                    if (matchingString2 == null) {
                        if (first2 != last3) {
                            first2 += step2;
                        }
                    } else {
                        return TuplesKt.to(Integer.valueOf(first2), matchingString2);
                    }
                }
            }
        }
        return null;
    }

    public static /* bridge */ /* synthetic */ int indexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.indexOf(charSequence, str, i, z);
    }

    public static final int indexOf(CharSequence $receiver, String string, int startIndex, boolean ignoreCase) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(string, SettingsContentProvider.STRING_TYPE);
        if (ignoreCase || !($receiver instanceof String)) {
            return indexOf$StringsKt__StringsKt$default($receiver, string, startIndex, $receiver.length(), ignoreCase, false, 16, null);
        }
        return ((String) $receiver).indexOf(string, startIndex);
    }

    public static /* bridge */ /* synthetic */ int lastIndexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.lastIndexOf(charSequence, str, i, z);
    }

    public static final int lastIndexOf(CharSequence $receiver, String string, int startIndex, boolean ignoreCase) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(string, SettingsContentProvider.STRING_TYPE);
        if (ignoreCase || !($receiver instanceof String)) {
            return indexOf$StringsKt__StringsKt($receiver, string, startIndex, 0, ignoreCase, true);
        }
        return ((String) $receiver).lastIndexOf(string, startIndex);
    }

    static /* bridge */ /* synthetic */ Sequence rangesDelimitedBy$StringsKt__StringsKt$default(CharSequence charSequence, String[] strArr, int i, boolean z, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        if ((i3 & 8) != 0) {
            i2 = 0;
        }
        return rangesDelimitedBy$StringsKt__StringsKt(charSequence, strArr, i, z, i2);
    }

    private static final Sequence<IntRange> rangesDelimitedBy$StringsKt__StringsKt(CharSequence $receiver, String[] delimiters, int startIndex, final boolean ignoreCase, int limit) {
        if (!(limit >= 0)) {
            throw new IllegalArgumentException(("Limit must be non-negative, but was " + limit + '.').toString());
        }
        final List delimitersList = ArraysKt.asList(delimiters);
        return new DelimitedRangesSequence($receiver, startIndex, limit, new Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>>() { // from class: kotlin.text.StringsKt__StringsKt$rangesDelimitedBy$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Pair<? extends Integer, ? extends Integer> invoke(CharSequence charSequence, Integer num) {
                return invoke(charSequence, num.intValue());
            }

            public final Pair<Integer, Integer> invoke(CharSequence $receiver2, int startIndex2) {
                Intrinsics.checkParameterIsNotNull($receiver2, "$receiver");
                Pair it = StringsKt__StringsKt.findAnyOf$StringsKt__StringsKt($receiver2, delimitersList, startIndex2, ignoreCase, false);
                if (it != null) {
                    return TuplesKt.to(it.getFirst(), Integer.valueOf(((String) it.getSecond()).length()));
                }
                return null;
            }
        });
    }

    public static /* bridge */ /* synthetic */ Sequence splitToSequence$default(CharSequence charSequence, String[] strArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return StringsKt.splitToSequence(charSequence, strArr, z, i);
    }

    public static final Sequence<String> splitToSequence(final CharSequence $receiver, String[] delimiters, boolean ignoreCase, int limit) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(delimiters, "delimiters");
        return SequencesKt.map(rangesDelimitedBy$StringsKt__StringsKt$default($receiver, delimiters, 0, ignoreCase, limit, 2, null), new Function1<IntRange, String>() { // from class: kotlin.text.StringsKt__StringsKt.splitToSequence.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(IntRange it) {
                Intrinsics.checkParameterIsNotNull(it, AdvanceSetting.NETWORK_TYPE);
                return StringsKt.substring($receiver, it);
            }
        });
    }
}
