package retrofit2.adapter.rxjava;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class CallExecuteOnSubscribe<T> implements Observable.OnSubscribe<Response<T>> {
    private final Call<T> originalCall;

    CallExecuteOnSubscribe(Call<T> originalCall) {
        this.originalCall = originalCall;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super Response<T>> subscriber) throws Throwable {
        Call<T> call = this.originalCall.clone();
        CallArbiter<T> arbiter = new CallArbiter<>(call, subscriber);
        subscriber.add(arbiter);
        subscriber.setProducer(arbiter);
        try {
            Response<T> response = call.execute();
            arbiter.emitResponse(response);
        } catch (Throwable t) {
            Exceptions.throwIfFatal(t);
            arbiter.emitError(t);
        }
    }
}
