package com.facebook.react.uimanager.layoutanimation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
enum LayoutAnimationType {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private final String mName;

    LayoutAnimationType(String name) {
        this.mName = name;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.mName;
    }
}
