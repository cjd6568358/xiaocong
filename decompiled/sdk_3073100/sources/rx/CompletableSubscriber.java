package rx;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface CompletableSubscriber {
    void onCompleted();

    void onError(Throwable th);

    void onSubscribe(Subscription subscription);
}
