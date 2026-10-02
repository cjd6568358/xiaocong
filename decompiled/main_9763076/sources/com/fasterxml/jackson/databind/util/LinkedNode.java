package com.fasterxml.jackson.databind.util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class LinkedNode<T> {
    final LinkedNode<T> _next;
    final T _value;

    public LinkedNode(T value, LinkedNode<T> next) {
        this._value = value;
        this._next = next;
    }

    public LinkedNode<T> next() {
        return this._next;
    }

    public T value() {
        return this._value;
    }

    public static <ST> boolean contains(LinkedNode<ST> node, ST value) {
        while (node != null) {
            if (node.value() == value) {
                return true;
            }
            node = node.next();
        }
        return false;
    }
}
