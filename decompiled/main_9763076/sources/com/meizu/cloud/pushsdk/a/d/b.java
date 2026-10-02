package com.meizu.cloud.pushsdk.a.d;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.utils.URLEncodedUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends j {
    private static final g a = g.a(URLEncodedUtils.CONTENT_TYPE);
    private final List<String> b;
    private final List<String> c;

    private b(List<String> list, List<String> list2) {
        this.b = m.a(list);
        this.c = m.a(list2);
    }

    @Override // com.meizu.cloud.pushsdk.a.d.j
    public g a() {
        return a;
    }

    @Override // com.meizu.cloud.pushsdk.a.d.j
    public long b() {
        return a((com.meizu.cloud.pushsdk.a.h.b) null, true);
    }

    @Override // com.meizu.cloud.pushsdk.a.d.j
    public void a(com.meizu.cloud.pushsdk.a.h.b bVar) throws IOException {
        a(bVar, false);
    }

    private long a(com.meizu.cloud.pushsdk.a.h.b bVar, boolean z) {
        com.meizu.cloud.pushsdk.a.h.a aVarB;
        long jA = 0;
        if (z) {
            aVarB = new com.meizu.cloud.pushsdk.a.h.a();
        } else {
            aVarB = bVar.b();
        }
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                aVarB.b(38);
            }
            aVarB.b(this.b.get(i));
            aVarB.b(61);
            aVarB.b(this.c.get(i));
        }
        if (z) {
            jA = aVarB.a();
            aVarB.j();
        }
        return jA;
    }

    public static final class a {
        private final List<String> a = new ArrayList();
        private final List<String> b = new ArrayList();

        public a a(String str, String str2) {
            this.a.add(f.a(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true));
            this.b.add(f.a(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true));
            return this;
        }

        public a b(String str, String str2) {
            this.a.add(f.a(str, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true));
            this.b.add(f.a(str2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true));
            return this;
        }

        public b a() {
            return new b(this.a, this.b);
        }
    }
}
