package com.fasterxml.jackson.core.sym;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class Name {
    protected final int _hashCode;
    protected final String _name;

    public abstract boolean equals(int i);

    public abstract boolean equals(int i, int i2);

    public abstract boolean equals(int[] iArr, int i);

    protected Name(String name, int hashCode) {
        this._name = name;
        this._hashCode = hashCode;
    }

    public String getName() {
        return this._name;
    }

    public String toString() {
        return this._name;
    }

    public final int hashCode() {
        return this._hashCode;
    }

    public boolean equals(Object o) {
        return o == this;
    }
}
