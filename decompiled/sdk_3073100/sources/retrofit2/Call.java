package retrofit2;

import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface Call<T> extends Cloneable {
    void cancel();

    /* JADX INFO: renamed from: clone */
    Call<T> mo92clone();

    void enqueue(Callback<T> callback);

    Response<T> execute() throws IOException;

    boolean isCanceled();
}
