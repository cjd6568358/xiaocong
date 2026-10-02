package com.facebook.react.uimanager.layoutanimation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
enum AnimatedPropertyType {
    OPACITY("opacity"),
    SCALE_XY("scaleXY");

    private final String mName;

    AnimatedPropertyType(String name) {
        this.mName = name;
    }

    public static AnimatedPropertyType fromString(String name) {
        for (AnimatedPropertyType property : values()) {
            if (property.toString().equalsIgnoreCase(name)) {
                return property;
            }
        }
        throw new IllegalArgumentException("Unsupported animated property : " + name);
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.mName;
    }
}
