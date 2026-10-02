package rx.internal.operators;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.plugins.RxJavaHooks;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class OnSubscribeLift<T, R> implements Observable.OnSubscribe<R> {
    final Observable.Operator<? extends R, ? super T> operator;
    final Observable.OnSubscribe<T> parent;

    public OnSubscribeLift(Observable.OnSubscribe<T> parent, Observable.Operator<? extends R, ? super T> operator) {
        this.parent = parent;
        this.operator = operator;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super R> o) throws Throwable {
        try {
            Subscriber<? super T> st = RxJavaHooks.onObservableLift(this.operator).call(o);
            try {
                st.onStart();
                this.parent.call(st);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                st.onError(e);
            }
        } catch (Throwable e2) {
            Exceptions.throwIfFatal(e2);
            o.onError(e2);
        }
    }
}
