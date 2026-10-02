package com.facebook.react.uimanager;

import java.util.Comparator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ViewAtIndex {
    public static Comparator<ViewAtIndex> COMPARATOR = new Comparator<ViewAtIndex>() { // from class: com.facebook.react.uimanager.ViewAtIndex.1
        @Override // java.util.Comparator
        public int compare(ViewAtIndex lhs, ViewAtIndex rhs) {
            return lhs.mIndex - rhs.mIndex;
        }
    };
    public final int mIndex;
    public final int mTag;

    public ViewAtIndex(int tag, int index) {
        this.mTag = tag;
        this.mIndex = index;
    }
}
