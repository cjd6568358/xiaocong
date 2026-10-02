package com.xiaocong.smarthome.network.httplib;

import android.util.Log;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class BinaryHttpResponseHandler extends AsyncHttpResponseHandler {
    private static final String LOG_TAG = "BinaryHttpResponseHandler";
    private String[] mAllowedContentTypes;

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    public abstract void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th);

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    public abstract void onSuccess(int i, Header[] headerArr, byte[] bArr);

    public String[] getAllowedContentTypes() {
        return this.mAllowedContentTypes;
    }

    public BinaryHttpResponseHandler() {
        this.mAllowedContentTypes = new String[]{"image/jpeg", "image/png"};
    }

    public BinaryHttpResponseHandler(String[] allowedContentTypes) {
        this.mAllowedContentTypes = new String[]{"image/jpeg", "image/png"};
        if (allowedContentTypes != null) {
            this.mAllowedContentTypes = allowedContentTypes;
        } else {
            Log.e(LOG_TAG, "Constructor passed allowedContentTypes was null !");
        }
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler, com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public final void sendResponseMessage(HttpResponse response) throws IOException {
        StatusLine status = response.getStatusLine();
        Header[] contentTypeHeaders = response.getHeaders("Content-Type");
        if (contentTypeHeaders.length != 1) {
            sendFailureMessage(status.getStatusCode(), response.getAllHeaders(), null, new HttpResponseException(status.getStatusCode(), "None, or more than one, Content-Type Header found!"));
            return;
        }
        Header contentTypeHeader = contentTypeHeaders[0];
        boolean foundAllowedContentType = false;
        for (String anAllowedContentType : getAllowedContentTypes()) {
            try {
                if (Pattern.matches(anAllowedContentType, contentTypeHeader.getValue())) {
                    foundAllowedContentType = true;
                }
            } catch (PatternSyntaxException e) {
                Log.e(LOG_TAG, "Given pattern is not valid: " + anAllowedContentType, e);
            }
        }
        if (!foundAllowedContentType) {
            sendFailureMessage(status.getStatusCode(), response.getAllHeaders(), null, new HttpResponseException(status.getStatusCode(), "Content-Type not allowed!"));
        } else {
            super.sendResponseMessage(response);
        }
    }
}
