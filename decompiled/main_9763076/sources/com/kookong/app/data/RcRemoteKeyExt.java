package com.kookong.app.data;

import java.io.Serializable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RcRemoteKeyExt implements Serializable, Cloneable {
    protected int tag;
    protected String value;

    public int getTag() {
        return this.tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        StringBuffer ret = new StringBuffer();
        ret.append("#com.kookong.app.data\n");
        ret.append("tag=" + this.tag + "\n");
        ret.append("value=" + this.value + "\n");
        return ret.toString();
    }
}
