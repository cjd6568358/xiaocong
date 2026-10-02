package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.cfg.ConfigFeature;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum MapperFeature implements ConfigFeature {
    USE_ANNOTATIONS(true),
    AUTO_DETECT_CREATORS(true),
    AUTO_DETECT_FIELDS(true),
    AUTO_DETECT_GETTERS(true),
    AUTO_DETECT_IS_GETTERS(true),
    AUTO_DETECT_SETTERS(true),
    REQUIRE_SETTERS_FOR_GETTERS(false),
    USE_GETTERS_AS_SETTERS(true),
    CAN_OVERRIDE_ACCESS_MODIFIERS(true),
    USE_STATIC_TYPING(false),
    DEFAULT_VIEW_INCLUSION(true),
    SORT_PROPERTIES_ALPHABETICALLY(false);

    private final boolean _defaultState;

    MapperFeature(boolean defaultState) {
        this._defaultState = defaultState;
    }

    @Override // com.fasterxml.jackson.databind.cfg.ConfigFeature
    public boolean enabledByDefault() {
        return this._defaultState;
    }

    @Override // com.fasterxml.jackson.databind.cfg.ConfigFeature
    public int getMask() {
        return 1 << ordinal();
    }
}
