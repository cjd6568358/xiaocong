package retrofit2.adapter.rxjava;

import retrofit2.Response;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Result<T> {
    private final Throwable error;
    private final Response<T> response;

    public static <T> Result<T> error(Throwable error) {
        if (error == null) {
            throw new NullPointerException("error == null");
        }
        return new Result<>(null, error);
    }

    public static <T> Result<T> response(Response<T> response) {
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        return new Result<>(response, null);
    }

    private Result(Response<T> response, Throwable error) {
        this.response = response;
        this.error = error;
    }

    public String toString() {
        return this.error != null ? "Result{isError=true, error=\"" + this.error + "\"}" : "Result{isError=false, response=" + this.response + '}';
    }
}
