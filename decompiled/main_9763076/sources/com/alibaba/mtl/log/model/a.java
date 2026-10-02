package com.alibaba.mtl.log.model;

import android.text.TextUtils;
import com.alibaba.mtl.log.e.c;
import com.alibaba.mtl.log.e.h;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.n;
import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: Log.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public String T;
    public String U;
    private String V;
    public String W;
    public String X;
    public int id;
    private Map<String, String> n;
    private String u;
    private String v;
    private String w;
    private String x;

    public a() {
        this.U = "3";
        this.W = null;
        this.X = Constants.MAIN_VERSION_TAG;
    }

    public a(String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        this.U = "3";
        this.W = null;
        this.X = Constants.MAIN_VERSION_TAG;
        this.T = str2;
        this.u = str;
        this.v = str3;
        this.w = str4;
        this.x = str5;
        this.n = map;
        this.W = String.valueOf(System.currentTimeMillis());
        s();
    }

    public String h() {
        try {
            byte[] bArrDecode = c.decode(this.V.getBytes(HTTP.UTF_8), 2);
            if (bArrDecode == null) {
                return null;
            }
            return new String(n.a(bArrDecode, "QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK"));
        } catch (Exception e) {
            return null;
        }
    }

    public String i() {
        return this.V;
    }

    public void j(String str) {
        if (str != null) {
            try {
                this.V = new String(c.encode(n.a(str.getBytes(), "QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK"), 2), HTTP.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void k(String str) {
        this.V = str;
    }

    public String toString() {
        return "Log [id=" + this.id + ", eventId=" + this.T + ", index=" + this.X + "]";
    }

    public void s() {
        if (TextUtils.isEmpty(this.W)) {
            this.W = String.valueOf(System.currentTimeMillis());
        }
        String strA = h.a(this.u, this.T, this.v, this.w, this.x, this.n, this.X, this.W);
        i.a("UTLog", this, strA);
        j(strA);
    }
}
