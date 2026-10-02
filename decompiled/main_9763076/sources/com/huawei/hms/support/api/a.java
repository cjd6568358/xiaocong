package com.huawei.hms.support.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.client.Result;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.transport.DatagramTransport;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: PendingResultImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a<R extends Result, T extends IMessageEntity> extends PendingResult<R> {
    private CountDownLatch a;
    private R b;
    private WeakReference<ApiClient> c;
    private String d;
    private long e;
    protected DatagramTransport transport;

    public abstract R onComplete(T t);

    public a(ApiClient apiClient, String str, IMessageEntity iMessageEntity) {
        this.b = null;
        this.transport = null;
        this.d = null;
        this.e = 0L;
        this.d = str;
        a(apiClient, str, iMessageEntity, getResponseType());
    }

    public a(ApiClient apiClient, String str, IMessageEntity iMessageEntity, Class<T> cls) {
        this.b = null;
        this.transport = null;
        this.d = null;
        this.e = 0L;
        a(apiClient, str, iMessageEntity, cls);
    }

    private void a(ApiClient apiClient, String str, IMessageEntity iMessageEntity, Class<T> cls) {
        if (apiClient == null) {
            throw new IllegalArgumentException("apiClient cannot be null.");
        }
        this.c = new WeakReference<>(apiClient);
        this.a = new CountDownLatch(1);
        try {
            this.transport = (DatagramTransport) Class.forName(apiClient.getTransportName()).getConstructor(String.class, IMessageEntity.class, Class.class).newInstance(str, iMessageEntity, cls);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException("Instancing transport exception, " + e.getMessage(), e);
        }
    }

    protected Class<T> getResponseType() {
        Type type;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass == null || (type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[1]) == null) {
            return null;
        }
        return (Class) type;
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public final R await() {
        this.e = System.currentTimeMillis();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("await must not be called on the UI thread");
        }
        ApiClient apiClient = this.c.get();
        if (!checkApiClient(apiClient)) {
            a(CommonCode.ErrorCode.CLIENT_API_INVALID, null);
            return this.b;
        }
        this.transport.a(apiClient, new b(this));
        try {
            this.a.await();
        } catch (InterruptedException e) {
            a(CommonCode.ErrorCode.INTERNAL_ERROR, null);
        }
        return this.b;
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public R await(long j, TimeUnit timeUnit) {
        this.e = System.currentTimeMillis();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("await must not be called on the UI thread");
        }
        ApiClient apiClient = this.c.get();
        if (!checkApiClient(apiClient)) {
            a(CommonCode.ErrorCode.CLIENT_API_INVALID, null);
            return this.b;
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        this.transport.b(apiClient, new c(this, atomicBoolean));
        try {
            if (!this.a.await(j, timeUnit)) {
                atomicBoolean.set(true);
                a(CommonCode.ErrorCode.EXECUTE_TIMEOUT, null);
            }
        } catch (InterruptedException e) {
            a(CommonCode.ErrorCode.INTERNAL_ERROR, null);
        }
        return this.b;
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public final void setResultCallback(ResultCallback<R> resultCallback) {
        setResultCallback(Looper.getMainLooper(), resultCallback);
    }

    @Override // com.huawei.hms.support.api.client.PendingResult
    public final void setResultCallback(Looper looper, ResultCallback<R> resultCallback) {
        this.e = System.currentTimeMillis();
        if (looper == null) {
            looper = Looper.myLooper();
        }
        HandlerC0019a handlerC0019a = new HandlerC0019a(looper);
        ApiClient apiClient = this.c.get();
        if (!checkApiClient(apiClient)) {
            a(CommonCode.ErrorCode.CLIENT_API_INVALID, null);
            handlerC0019a.a(resultCallback, this.b);
        } else {
            this.transport.b(apiClient, new d(this, handlerC0019a, resultCallback));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, IMessageEntity iMessageEntity) {
        a(i);
        if (i <= 0) {
            this.b = (R) onComplete(iMessageEntity);
        } else {
            this.b = (R) onError(i);
        }
    }

    protected R onError(int i) {
        Type genericSuperclass = getClass().getGenericSuperclass();
        Type type = genericSuperclass != null ? ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0] : null;
        Class<?> clsA = type != null ? com.huawei.hms.support.a.a.a(type) : null;
        if (clsA != null) {
            try {
                this.b = (R) clsA.newInstance();
                this.b.setStatus(new Status(i));
            } catch (Exception e) {
                return null;
            }
        }
        return this.b;
    }

    protected boolean checkApiClient(ApiClient apiClient) {
        return apiClient != null && apiClient.isConnected();
    }

    /* JADX INFO: renamed from: com.huawei.hms.support.api.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: PendingResultImpl.java */
    protected static class HandlerC0019a<R extends Result> extends Handler {
        public HandlerC0019a() {
            this(Looper.getMainLooper());
        }

        public HandlerC0019a(Looper looper) {
            super(looper);
        }

        public void a(ResultCallback<? super R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    Pair pair = (Pair) message.obj;
                    b((ResultCallback) pair.first, (Result) pair.second);
                    break;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        protected void b(ResultCallback<? super R> resultCallback, R r) {
            resultCallback.onResult(r);
        }
    }

    private void a(int i) {
        ApiClient apiClient = this.c.get();
        if (apiClient != null && this.d != null && this.e != 0) {
            HashMap map = new HashMap();
            map.put("package", apiClient.getPackageName());
            map.put("sdk_ver", String.valueOf(20502300));
            String appID = null;
            SubAppInfo subAppInfo = apiClient.getSubAppInfo();
            if (subAppInfo != null) {
                appID = subAppInfo.getSubAppID();
            }
            if (appID == null) {
                appID = apiClient.getAppID();
            }
            map.put("app_id", appID);
            String[] strArrSplit = this.d.split("\\.");
            if (strArrSplit.length == 2) {
                map.put("service", strArrSplit[0]);
                map.put("api_name", strArrSplit[1]);
            }
            map.put("result", String.valueOf(i));
            map.put("cost_time", String.valueOf(System.currentTimeMillis() - this.e));
            com.huawei.hms.support.b.a.a().a(apiClient.getContext(), "HMS_SDK_API_CALL", map);
        }
    }
}
