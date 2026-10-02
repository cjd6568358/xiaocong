package com.alibaba.sdk.android.httpdns.b;

import bsh.ParserConstants;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    public ArrayList<g> a;
    public String h;
    public String i;
    public long id;
    public String j;

    public String toString() {
        StringBuilder sb = new StringBuilder(ParserConstants.LSHIFTASSIGN);
        sb.append("[HostRecord] ");
        sb.append("id:");
        sb.append(this.id);
        sb.append("|");
        sb.append("host:");
        sb.append(this.h);
        sb.append("|");
        sb.append("sp:");
        sb.append(this.i);
        sb.append("|");
        sb.append("time:");
        sb.append(this.j);
        sb.append("|");
        sb.append("ips:");
        if (this.a != null && this.a.size() > 0) {
            Iterator<g> it = this.a.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
            }
        }
        sb.append("|");
        return sb.toString();
    }
}
