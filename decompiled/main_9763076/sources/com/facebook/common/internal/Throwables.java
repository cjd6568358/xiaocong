package com.facebook.common.internal;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class Throwables {
    /* JADX INFO: Thrown type has an unknown type hierarchy: X extends java.lang.Throwable */
    public static <X extends Throwable> void propagateIfInstanceOf(Throwable throwable, Class<X> declaredType) throws Throwable {
        if (throwable != null && declaredType.isInstance(throwable)) {
            throw declaredType.cast(throwable);
        }
    }

    public static void propagateIfPossible(Throwable throwable) throws Throwable {
        propagateIfInstanceOf(throwable, Error.class);
        propagateIfInstanceOf(throwable, RuntimeException.class);
    }

    public static RuntimeException propagate(Throwable throwable) throws Throwable {
        propagateIfPossible((Throwable) Preconditions.checkNotNull(throwable));
        throw new RuntimeException(throwable);
    }
}
