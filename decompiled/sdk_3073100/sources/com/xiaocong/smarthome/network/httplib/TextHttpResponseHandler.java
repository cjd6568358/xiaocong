package com.xiaocong.smarthome.network.httplib;

import android.util.Log;
import com.xiaocong.smarthome.network.util.XCHttpLog;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class TextHttpResponseHandler extends AsyncHttpResponseHandler {
    private static final String LOG_TAG = "TextHttpResponseHandler";

    public abstract void onFailure(int i, Header[] headerArr, String str, Throwable th);

    public abstract void onSuccess(int i, Header[] headerArr, String str);

    public TextHttpResponseHandler() {
        this(AsyncHttpResponseHandler.DEFAULT_CHARSET);
    }

    public TextHttpResponseHandler(String encoding) {
        setCharset(encoding);
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
        onSuccess(statusCode, headers, getResponseString(responseBytes, getCharset()));
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
        onFailure(statusCode, headers, getResponseString(responseBytes, getCharset()), throwable);
    }

    public static String getResponseString(byte[] stringBytes, String charset) {
        String string;
        String string2 = null;
        try {
            if (stringBytes == null) {
                string = null;
            } else {
                string = new String(stringBytes, charset);
                string2 = string;
            }
            return string;
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Encoding response into string failed", e);
        } catch (OutOfMemoryError e2) {
            Log.e(LOG_TAG, "OutOfMemoryError", e2);
        } finally {
            if (XCHttpLog.printLog) {
                XCHttpLog.e("response=" + ((String) null));
            }
        }
    }
}
