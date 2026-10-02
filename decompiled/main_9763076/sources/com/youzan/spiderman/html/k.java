package com.youzan.spiderman.html;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: HtmlDataPref.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class k {

    @SerializedName("html_data_pref")
    private Map<String, i> a = new HashMap();

    public i a(String hash) {
        return this.a.get(hash);
    }

    public void a(String hash, i htmlData) {
        this.a.put(hash, htmlData);
    }

    public i b(String hash) {
        return this.a.remove(hash);
    }
}
