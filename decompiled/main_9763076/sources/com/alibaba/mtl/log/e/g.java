package com.alibaba.mtl.log.e;

import android.text.TextUtils;
import java.util.Arrays;
import java.util.Comparator;

/* JADX INFO: compiled from: KeyArraySorter.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static g a = new g();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private a f33a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private b f34a;

    private g() {
        this.f34a = new b();
        this.f33a = new a();
    }

    public static g a() {
        return a;
    }

    public String[] a(String[] strArr, boolean z) {
        Comparator comparator;
        if (z) {
            comparator = this.f33a;
        } else {
            comparator = this.f34a;
        }
        if (comparator != null && strArr != null && strArr.length > 0) {
            Arrays.sort(strArr, comparator);
            return strArr;
        }
        return null;
    }

    /* JADX INFO: compiled from: KeyArraySorter.java */
    private class b implements Comparator<String> {
        private b() {
        }

        @Override // java.util.Comparator
        public int compare(String o1, String o2) {
            if (TextUtils.isEmpty(o1) || TextUtils.isEmpty(o2)) {
                return 0;
            }
            return o1.compareTo(o2) * (-1);
        }
    }

    /* JADX INFO: compiled from: KeyArraySorter.java */
    private class a implements Comparator<String> {
        private a() {
        }

        @Override // java.util.Comparator
        public int compare(String o1, String o2) {
            if (TextUtils.isEmpty(o1) || TextUtils.isEmpty(o2)) {
                return 0;
            }
            return o1.compareTo(o2);
        }
    }
}
