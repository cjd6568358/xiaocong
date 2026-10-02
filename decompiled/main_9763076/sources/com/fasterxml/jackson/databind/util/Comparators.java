package com.fasterxml.jackson.databind.util;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Comparators {
    public static Object getArrayComparator(final Object defaultValue) {
        final int length = Array.getLength(defaultValue);
        return new Object() { // from class: com.fasterxml.jackson.databind.util.Comparators.1
            public boolean equals(Object other) {
                if (other == this) {
                    return true;
                }
                if (other == null || other.getClass() != defaultValue.getClass()) {
                    return false;
                }
                if (Array.getLength(other) != length) {
                    return false;
                }
                for (int i = 0; i < length; i++) {
                    Object value1 = Array.get(defaultValue, i);
                    Object value2 = Array.get(other, i);
                    if (value1 != value2 && value1 != null && !value1.equals(value2)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }
}
