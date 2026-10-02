package retrofit2;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface Callback<T> {
    void onFailure(Call<T> call, Throwable th);

    void onResponse(Call<T> call, Response<T> response);
}
