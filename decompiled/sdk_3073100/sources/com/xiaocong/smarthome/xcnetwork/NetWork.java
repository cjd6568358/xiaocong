package com.xiaocong.smarthome.xcnetwork;

import com.xiaocong.smarthome.xcnetwork.dns.OkHttpDns;
import com.xiaocong.smarthome.xcnetwork.utils.XcLogger;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class NetWork {
    private boolean addConverter;
    private String baseUrl;
    private Interceptor interceptor;
    private long timeout;

    public static class Builder {
        private String baseUrl;
        private Interceptor interceptor;
        private long timeout = 15;
        private boolean addConverter = true;

        public Builder(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public NetWork build() {
            return new NetWork(this);
        }
    }

    private NetWork(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.timeout = builder.timeout;
        this.interceptor = builder.interceptor;
        this.addConverter = builder.addConverter;
    }

    public <T> T getApi(Class<T> cls) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(this.timeout, TimeUnit.SECONDS);
        builder.readTimeout(this.timeout, TimeUnit.SECONDS);
        builder.writeTimeout(this.timeout, TimeUnit.SECONDS);
        builder.dns(OkHttpDns.getInstance());
        builder.retryOnConnectionFailure(false);
        if (this.interceptor != null) {
            builder.addNetworkInterceptor(this.interceptor);
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() { // from class: com.xiaocong.smarthome.xcnetwork.NetWork.1
            @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
            public void log(String message) {
                XcLogger.e("XCHttpLog", message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        Retrofit.Builder builderAddCallAdapterFactory = new Retrofit.Builder().baseUrl(this.baseUrl).client(builder.build()).addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        if (this.addConverter) {
            builderAddCallAdapterFactory.addConverterFactory(FastJsonConverterFactory.create());
        }
        return (T) builderAddCallAdapterFactory.build().create(cls);
    }
}
