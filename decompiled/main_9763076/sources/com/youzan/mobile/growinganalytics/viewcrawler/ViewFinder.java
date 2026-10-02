package com.youzan.mobile.growinganalytics.viewcrawler;

import android.view.View;
import android.view.ViewGroup;
import com.youzan.mobile.growinganalytics.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ViewFinder.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
public final class ViewFinder {
    private final IntStack indexStack = new IntStack(null, 0, 3, null);

    public final void findTargetViewsInRoot(View rootView, Accumulator accumulator) {
        Intrinsics.checkParameterIsNotNull(rootView, "rootView");
        Intrinsics.checkParameterIsNotNull(accumulator, "accumulator");
        int indexKey = this.indexStack.alloc();
        findMatchedView(rootView, indexKey, accumulator);
        this.indexStack.free();
    }

    private final void findMatchedView(View subView, int indexKey, Accumulator accumulator) {
        if (this.indexStack.full()) {
            Logger.Companion.e("ViewCrawler", "View stack is full, with not match");
            return;
        }
        if (subView instanceof ViewGroup) {
            int childCount = ((ViewGroup) subView).getChildCount();
            IntRange intRange = new IntRange(0, childCount - 1);
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
            Iterator<Integer> it = intRange.iterator();
            while (it.hasNext()) {
                int item$iv$iv = ((IntIterator) it).nextInt();
                destination$iv$iv.add(((ViewGroup) subView).getChildAt(item$iv$iv));
            }
            Collection collection = (List) destination$iv$iv;
            Collection destination$iv$iv2 = new ArrayList();
            for (Object element$iv$iv : collection) {
                View view = (View) element$iv$iv;
                if ((view instanceof ViewGroup) || (!(view instanceof ViewGroup) && view.isClickable())) {
                    destination$iv$iv2.add(element$iv$iv);
                }
            }
            for (Object element$iv : (List) destination$iv$iv2) {
                View view2 = (View) element$iv;
                Intrinsics.checkExpressionValueIsNotNull(view2, "view");
                findMatchedView(view2, indexKey, accumulator);
            }
            return;
        }
        this.indexStack.increment(indexKey);
        accumulator.accumulate(subView);
    }
}
