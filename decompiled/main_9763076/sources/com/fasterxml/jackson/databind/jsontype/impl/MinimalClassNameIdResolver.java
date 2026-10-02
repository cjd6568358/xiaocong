package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MinimalClassNameIdResolver extends ClassNameIdResolver {
    protected final String _basePackageName;
    protected final String _basePackagePrefix;

    protected MinimalClassNameIdResolver(JavaType baseType, TypeFactory typeFactory) {
        super(baseType, typeFactory);
        String base = baseType.getRawClass().getName();
        int ix = base.lastIndexOf(46);
        if (ix < 0) {
            this._basePackageName = Constants.MAIN_VERSION_TAG;
            this._basePackagePrefix = ".";
        } else {
            this._basePackagePrefix = base.substring(0, ix + 1);
            this._basePackageName = base.substring(0, ix);
        }
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver, com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.MINIMAL_CLASS;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver, com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public String idFromValue(Object value) {
        String n = value.getClass().getName();
        if (n.startsWith(this._basePackagePrefix)) {
            return n.substring(this._basePackagePrefix.length() - 1);
        }
        return n;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver, com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public JavaType typeFromId(String id) {
        if (id.startsWith(".")) {
            StringBuilder sb = new StringBuilder(id.length() + this._basePackageName.length());
            if (this._basePackageName.length() == 0) {
                sb.append(id.substring(1));
            } else {
                sb.append(this._basePackageName).append(id);
            }
            id = sb.toString();
        }
        return super.typeFromId(id);
    }
}
