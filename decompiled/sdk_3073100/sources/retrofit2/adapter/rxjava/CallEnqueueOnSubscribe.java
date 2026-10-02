package retrofit2.adapter.rxjava;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class CallEnqueueOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {
    private final Call<T> originalCall;

    CallEnqueueOnSubscribe(Call<T> originalCall) {
        this.originalCall = originalCall;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super Response<T>> subscriber) {
        Call<T> call = this.originalCall.clone();
        final CallArbiter<T> arbiter = new CallArbiter<>(call, subscriber);
        subscriber.add(arbiter);
        subscriber.setProducer(arbiter);
        call.enqueue(new Callback<T>() { // from class: retrofit2.adapter.rxjava.CallEnqueueOnSubscribe.1
            @Override // retrofit2.Callback
            public void onResponse(Call<T> call2, Response<T> response) throws Throwable {
                arbiter.emitResponse(response);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<T> call2, Throwable t) throws Throwable {
                Exceptions.throwIfFatal(t);
                arbiter.emitError(t);
            }
        });
    }
}
