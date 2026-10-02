package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.cfg.DatabindVersion;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class NopAnnotationIntrospector extends AnnotationIntrospector {
    public static final NopAnnotationIntrospector instance = new NopAnnotationIntrospector() { // from class: com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector.1
        @Override // com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector, com.fasterxml.jackson.databind.AnnotationIntrospector, com.fasterxml.jackson.core.Versioned
        public Version version() {
            return DatabindVersion.instance.version();
        }
    };

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return Version.unknownVersion();
    }
}
