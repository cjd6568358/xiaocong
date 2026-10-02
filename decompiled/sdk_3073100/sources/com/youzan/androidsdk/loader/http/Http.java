package com.youzan.androidsdk.loader.http;

import android.content.Context;
import android.text.TextUtils;
import com.youzan.androidsdk.YouzanException;
import com.youzan.androidsdk.loader.http.interfaces.HttpCall;
import com.youzan.androidsdk.loader.http.interfaces.HttpEngine;
import com.youzan.androidsdk.loader.http.interfaces.HttpExecutor;
import com.youzan.androidsdk.loader.http.interfaces.HttpForms;
import com.youzan.androidsdk.loader.http.interfaces.HttpInterceptor;
import com.youzan.spiderman.utils.NetWorkUtil;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Http implements HttpForms {
    private final Context mContext;
    private final boolean mOnUI;
    private final Map<String, String> mRequestParameter = new LinkedHashMap(10);
    private final Map<String, String> mRequestHeader = new LinkedHashMap(5);
    private final Map<String, File> mRequestFileParameter = new LinkedHashMap(3);
    private final List<HttpInterceptor> mInterrupts = new LinkedList();
    private final HttpEngine mEngine = new d();

    private Http(Context context, boolean onUI) {
        this.mOnUI = onUI;
        this.mContext = context;
    }

    public static Http attach(Context context, boolean onUI) {
        return new Http(context, onUI);
    }

    public static Http attach(Context context) {
        return new Http(context, true);
    }

    private static <T> T checkEmpty(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        if (obj instanceof String) {
            String stringObj = String.valueOf(obj);
            if (TextUtils.isEmpty(stringObj)) {
                throw new NullPointerException();
            }
        }
        return obj;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, boolean value) {
        return put(key, value, true);
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, boolean value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, String value) {
        return put(key, value, true);
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, String value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, int value) {
        return put(key, value, true);
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, int value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, double value) {
        return put(key, value, true);
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, double value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, float value) {
        return put(key, value, true);
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, float value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, long value) {
        return put(key, value, true);
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, long value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf(value));
        }
        return this;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, short value) {
        return put(key, value, true);
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, short value, boolean apply) {
        if (apply) {
            this.mRequestParameter.put(key, String.valueOf((int) value));
        }
        return this;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, File value) {
        return put(key, value, true);
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms put(String key, File value, boolean apply) {
        if (apply) {
            this.mRequestFileParameter.put(key, value);
        }
        return this;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpForms
    public HttpForms puts(Map<String, String> forms) {
        this.mRequestParameter.putAll(forms);
        return this;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpExecutor
    public <MODEL> HttpCall with(Query<MODEL> query) throws NullPointerException {
        if (!NetWorkUtil.hasNetworkInternetPermission(this.mContext)) {
            query.onFailure(new YouzanException("Has no INTERNET permission"));
        } else {
            String url = a.m68(((Query) checkEmpty(query)).getAuthType(), (String) checkEmpty(((Query) checkEmpty(query)).attachTo()));
            if (this.mInterrupts.size() > 0) {
                query.mInterceptor.addAll(this.mInterrupts);
            }
            this.mEngine.request(this.mContext, query.getHTTPMethod(), url, this.mRequestParameter, this.mRequestFileParameter, this.mRequestHeader, query.getModel(), query, this.mOnUI);
        }
        return query;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpExecutor
    public HttpExecutor intercept(HttpInterceptor interceptor) throws NullPointerException {
        this.mInterrupts.add(interceptor);
        return this;
    }
}
