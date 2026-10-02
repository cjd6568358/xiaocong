package com.xiaocong.smarthome.sdk.http.callback;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class XCUploadFileCallBack extends AsyncHttpResponseHandler {
    public abstract void onFailure(int i, Map<String, String> map, Throwable th);

    public abstract void onSuccess(int i, Map<String, String> map);

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        onSuccess(statusCode, headersToMap(headers));
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        onFailure(statusCode, headersToMap(headers), null);
    }

    private static Map<String, String> headersToMap(Header[] headers) {
        HashMap<String, String> map = new HashMap<>();
        if (headers != null) {
            for (Header item : headers) {
                map.put(item.getName(), item.getValue());
            }
        }
        return map;
    }
}
