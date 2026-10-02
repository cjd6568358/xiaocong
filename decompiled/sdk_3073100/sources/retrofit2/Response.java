package retrofit2;

import okhttp3.ResponseBody;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Response<T> {
    private final T body;
    private final ResponseBody errorBody;
    private final okhttp3.Response rawResponse;

    public static <T> Response<T> success(T body, okhttp3.Response rawResponse) {
        if (rawResponse == null) {
            throw new NullPointerException("rawResponse == null");
        }
        if (!rawResponse.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse must be successful response");
        }
        return new Response<>(rawResponse, body, null);
    }

    public static <T> Response<T> error(ResponseBody body, okhttp3.Response rawResponse) {
        if (body == null) {
            throw new NullPointerException("body == null");
        }
        if (rawResponse == null) {
            throw new NullPointerException("rawResponse == null");
        }
        if (rawResponse.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse should not be successful response");
        }
        return new Response<>(rawResponse, null, body);
    }

    private Response(okhttp3.Response rawResponse, T body, ResponseBody errorBody) {
        this.rawResponse = rawResponse;
        this.body = body;
        this.errorBody = errorBody;
    }

    public int code() {
        return this.rawResponse.code();
    }

    public String message() {
        return this.rawResponse.message();
    }

    public boolean isSuccessful() {
        return this.rawResponse.isSuccessful();
    }

    public T body() {
        return this.body;
    }

    public String toString() {
        return this.rawResponse.toString();
    }
}
