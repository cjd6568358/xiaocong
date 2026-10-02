package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class JsonObject extends JsonElement {
    private final LinkedTreeMap<String, JsonElement> members = new LinkedTreeMap<>();

    public void add(String property, JsonElement value) {
        if (value == null) {
            value = JsonNull.INSTANCE;
        }
        this.members.put(property, value);
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.members.entrySet();
    }

    public boolean equals(Object o) {
        return o == this || ((o instanceof JsonObject) && ((JsonObject) o).members.equals(this.members));
    }

    public int hashCode() {
        return this.members.hashCode();
    }
}
