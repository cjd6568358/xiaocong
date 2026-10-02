package com.youzan.androidsdk.loader.http;

import android.text.TextUtils;
import com.youzan.androidsdk.YouzanException;
import com.youzan.androidsdk.loader.http.interfaces.HttpCall;
import com.youzan.androidsdk.loader.http.interfaces.HttpInterceptor;
import com.youzan.androidsdk.loader.http.interfaces.NotImplementedException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class Query<MODEL> implements HttpCall {
    private static final int HTTP_OK = 200;
    c mEngine;
    List<HttpInterceptor> mInterceptor = new LinkedList();
    String mResponseBody;
    Map<String, List<String>> mResponseHeader;

    protected abstract String attachTo();

    protected abstract int getAuthType();

    protected abstract Class<MODEL> getModel();

    protected abstract void onFailure(YouzanException youzanException);

    protected abstract void onSuccess(MODEL model);

    public static JSONObject onResponseCheck(String raw) throws YouzanException {
        if (TextUtils.isEmpty(raw)) {
            throw new YouzanException("HTTP response body is empty");
        }
        try {
            JSONObject body = new JSONObject(raw);
            JSONObject error = body.optJSONObject("error_response");
            JSONObject response = body.optJSONObject("response");
            if (error != null) {
                int code = error.optInt("code", 0);
                if (code != 0 && code != HTTP_OK) {
                    throw new YouzanException(code, error.optString("msg"));
                }
            } else if (response != null) {
                int code2 = response.optInt("code");
                boolean isSuccess = response.optBoolean("is_success", true);
                boolean success = response.optBoolean("success", true);
                if (!success || !isSuccess) {
                    throw new YouzanException(code2, response.optString("message"));
                }
            } else {
                int code3 = body.optInt("code");
                if (code3 != 0 && code3 != HTTP_OK) {
                    throw new YouzanException(code3, body.optString("msg"));
                }
            }
            return body;
        } catch (Exception e) {
            throw new YouzanException(e);
        }
    }

    MODEL onFilter(String body) throws Exception {
        return onResponseParse(onResponseFilter(onResponseCheck(body)));
    }

    protected int getHTTPMethod() {
        return 1;
    }

    private MODEL onResponseParse(JSONObject body) throws Exception {
        try {
            return onParse(body);
        } catch (NotImplementedException e) {
            return getModel().getConstructor(JSONObject.class).newInstance(body);
        }
    }

    protected MODEL onParse(JSONObject data) throws Exception {
        throw new NotImplementedException();
    }

    private JSONObject onResponseFilter(JSONObject body) {
        JSONObject response = body.optJSONObject("response");
        if (response != null) {
            JSONObject data = response.optJSONObject("data");
            return data != null ? data : response;
        }
        JSONObject data2 = body.optJSONObject("data");
        if (data2 != null) {
            body = data2;
        }
        return body;
    }

    protected Map<String, List<String>> getResponseHeader() {
        return this.mResponseHeader;
    }

    @Override // com.youzan.androidsdk.loader.http.interfaces.HttpCall
    public void cancel() {
        if (this.mEngine != null) {
            this.mEngine.cancel();
        }
    }
}
