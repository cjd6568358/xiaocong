package com.fasterxml.jackson.databind.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UnrecognizedPropertyException extends JsonMappingException {
    private static final int MAX_DESC_LENGTH = 200;
    private static final long serialVersionUID = 1;
    protected transient String _propertiesAsString;
    protected final Collection<Object> _propertyIds;
    protected final Class<?> _referringClass;
    protected final String _unrecognizedPropertyName;

    public UnrecognizedPropertyException(String msg, JsonLocation loc, Class<?> referringClass, String propName, Collection<Object> propertyIds) {
        super(msg, loc);
        this._referringClass = referringClass;
        this._unrecognizedPropertyName = propName;
        this._propertyIds = propertyIds;
    }

    public static UnrecognizedPropertyException from(JsonParser jp, Object fromObjectOrClass, String propertyName, Collection<Object> propertyIds) {
        Class<?> ref;
        if (fromObjectOrClass == null) {
            throw new IllegalArgumentException();
        }
        if (fromObjectOrClass instanceof Class) {
            ref = (Class) fromObjectOrClass;
        } else {
            ref = fromObjectOrClass.getClass();
        }
        String msg = "Unrecognized field \"" + propertyName + "\" (class " + ref.getName() + "), not marked as ignorable";
        UnrecognizedPropertyException e = new UnrecognizedPropertyException(msg, jp.getCurrentLocation(), ref, propertyName, propertyIds);
        e.prependPath(fromObjectOrClass, propertyName);
        return e;
    }

    @Override // com.fasterxml.jackson.core.JsonProcessingException
    public String getMessageSuffix() {
        String suffix = this._propertiesAsString;
        if (suffix == null && this._propertyIds != null) {
            StringBuilder sb = new StringBuilder(100);
            int len = this._propertyIds.size();
            if (len == 1) {
                sb.append(" (one known property: \"");
                sb.append(String.valueOf(this._propertyIds.iterator().next()));
                sb.append('\"');
            } else {
                sb.append(" (").append(len).append(" known properties: ");
                Iterator<Object> it = this._propertyIds.iterator();
                while (it.hasNext()) {
                    sb.append(", \"");
                    sb.append(String.valueOf(it.next()));
                    sb.append('\"');
                    if (sb.length() > 200) {
                        sb.append(" [truncated]");
                        break;
                    }
                }
            }
            sb.append("])");
            String suffix2 = sb.toString();
            this._propertiesAsString = suffix2;
            return suffix2;
        }
        return suffix;
    }

    public Class<?> getReferringClass() {
        return this._referringClass;
    }

    public String getUnrecognizedPropertyName() {
        return this._unrecognizedPropertyName;
    }

    public Collection<Object> getKnownPropertyIds() {
        if (this._propertyIds == null) {
            return null;
        }
        return Collections.unmodifiableCollection(this._propertyIds);
    }
}
