package rx.internal.operators;

import rx.Observable;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class OperatorSubscribeOn<T> implements Observable.OnSubscribe<T> {
    final Scheduler scheduler;
    final Observable<T> source;

    public OperatorSubscribeOn(Observable<T> source, Scheduler scheduler) {
        this.scheduler = scheduler;
        this.source = source;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        Scheduler.Worker inner = this.scheduler.createWorker();
        subscriber.add(inner);
        inner.schedule(new AnonymousClass1(subscriber, inner));
    }

    /* JADX INFO: renamed from: rx.internal.operators.OperatorSubscribeOn$1, reason: invalid class name */
    class AnonymousClass1 implements Action0 {
        final /* synthetic */ Scheduler.Worker val$inner;
        final /* synthetic */ Subscriber val$subscriber;

        AnonymousClass1(Subscriber subscriber, Scheduler.Worker worker) {
            this.val$subscriber = subscriber;
            this.val$inner = worker;
        }

        @Override // rx.functions.Action0
        public void call() {
            final Thread t = Thread.currentThread();
            Subscriber<T> s = new Subscriber<T>(this.val$subscriber) { // from class: rx.internal.operators.OperatorSubscribeOn.1.1
                @Override // rx.Observer
                public void onNext(T t2) {
                    AnonymousClass1.this.val$subscriber.onNext(t2);
                }

                @Override // rx.Observer
                public void onError(Throwable e) {
                    try {
                        AnonymousClass1.this.val$subscriber.onError(e);
                    } finally {
                        AnonymousClass1.this.val$inner.unsubscribe();
                    }
                }

                @Override // rx.Observer
                public void onCompleted() {
                    try {
                        AnonymousClass1.this.val$subscriber.onCompleted();
                    } finally {
                        AnonymousClass1.this.val$inner.unsubscribe();
                    }
                }

                @Override // rx.Subscriber
                public void setProducer(final Producer p) {
                    AnonymousClass1.this.val$subscriber.setProducer(new Producer() { // from class: rx.internal.operators.OperatorSubscribeOn.1.1.1
                        @Override // rx.Producer
                        public void request(final long n) {
                            if (t == Thread.currentThread()) {
                                p.request(n);
                            } else {
                                AnonymousClass1.this.val$inner.schedule(new Action0() { // from class: rx.internal.operators.OperatorSubscribeOn.1.1.1.1
                                    @Override // rx.functions.Action0
                                    public void call() {
                                        p.request(n);
                                    }
                                });
                            }
                        }
                    });
                }
            };
            OperatorSubscribeOn.this.source.unsafeSubscribe(s);
        }
    }
}
