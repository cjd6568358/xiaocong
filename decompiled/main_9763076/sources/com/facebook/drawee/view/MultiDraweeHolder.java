package com.facebook.drawee.view;

import com.facebook.common.internal.Preconditions;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MultiDraweeHolder<DH extends DraweeHierarchy> {
    boolean mIsAttached = false;
    ArrayList<DraweeHolder<DH>> mHolders = new ArrayList<>();

    public void onAttach() {
        if (!this.mIsAttached) {
            this.mIsAttached = true;
            for (int i = 0; i < this.mHolders.size(); i++) {
                this.mHolders.get(i).onAttach();
            }
        }
    }

    public void onDetach() {
        if (this.mIsAttached) {
            this.mIsAttached = false;
            for (int i = 0; i < this.mHolders.size(); i++) {
                this.mHolders.get(i).onDetach();
            }
        }
    }

    public void clear() {
        if (this.mIsAttached) {
            for (int i = 0; i < this.mHolders.size(); i++) {
                this.mHolders.get(i).onDetach();
            }
        }
        this.mHolders.clear();
    }

    public void add(DraweeHolder<DH> holder) {
        add(this.mHolders.size(), holder);
    }

    public void add(int index, DraweeHolder<DH> holder) {
        Preconditions.checkNotNull(holder);
        Preconditions.checkElementIndex(index, this.mHolders.size() + 1);
        this.mHolders.add(index, holder);
        if (this.mIsAttached) {
            holder.onAttach();
        }
    }
}
