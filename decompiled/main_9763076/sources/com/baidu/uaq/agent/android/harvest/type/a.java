package com.baidu.uaq.agent.android.harvest.type;

import com.tencent.android.tpush.common.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: BaseHarvestable.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a implements b {
    private final b.a bM;

    public a(b.a type) {
        this.bM = type;
    }

    public Object aw() {
        switch (this.bM) {
            case OBJECT:
                return z();
            case ARRAY:
                return U();
            default:
                return Constants.MAIN_VERSION_TAG;
        }
    }

    public JSONObject z() {
        return null;
    }

    public JSONArray U() {
        return null;
    }

    public String bf() {
        return aw().toString();
    }

    protected void C(String argument) {
        if (argument == null || argument.length() == 0) {
            throw new IllegalArgumentException("Missing Harvestable field.");
        }
    }
}
