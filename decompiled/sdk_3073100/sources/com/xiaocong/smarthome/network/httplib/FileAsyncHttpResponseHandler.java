package com.xiaocong.smarthome.network.httplib;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class FileAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final String LOG_TAG = "FileAsyncHttpResponseHandler";
    protected File mFile;

    public abstract void onFailure(int i, Header[] headerArr, Throwable th, File file);

    public abstract void onSuccess(int i, Header[] headerArr, File file);

    static {
        $assertionsDisabled = !FileAsyncHttpResponseHandler.class.desiredAssertionStatus();
    }

    public FileAsyncHttpResponseHandler() {
    }

    public FileAsyncHttpResponseHandler(File file) {
        if (!$assertionsDisabled && file == null) {
            throw new AssertionError();
        }
        this.mFile = file;
    }

    public void setDestinationFile(File file) {
        this.mFile = file;
    }

    public FileAsyncHttpResponseHandler(Context context) {
        this.mFile = getTemporaryFile(context);
    }

    public boolean deleteTargetFile() {
        return getTargetFile() != null && getTargetFile().delete();
    }

    protected File getTemporaryFile(Context context) {
        if (!$assertionsDisabled && context == null) {
            throw new AssertionError();
        }
        try {
            return File.createTempFile("temp_", "_handled", context.getCacheDir());
        } catch (Throwable t) {
            Log.e(LOG_TAG, "Cannot create temporary file", t);
            return null;
        }
    }

    protected File getTargetFile() {
        if ($assertionsDisabled || this.mFile != null) {
            return this.mFile;
        }
        throw new AssertionError();
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    public final void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
        onFailure(statusCode, headers, throwable, getTargetFile());
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    public final void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
        onSuccess(statusCode, headers, getTargetFile());
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    protected byte[] getResponseData(HttpEntity entity) throws IOException {
        if (entity != null) {
            InputStream instream = entity.getContent();
            long contentLength = entity.getContentLength();
            File target = getTargetFile();
            File parentFile = target.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            File tempFile = new File(target.getPath() + ".tmp");
            if (tempFile.exists()) {
                tempFile.delete();
            }
            tempFile.createNewFile();
            FileOutputStream buffer = new FileOutputStream(tempFile);
            if (instream != null) {
                try {
                    byte[] tmp = new byte[4096];
                    int count = 0;
                    while (true) {
                        int l = instream.read(tmp);
                        if (l == -1 || Thread.currentThread().isInterrupted()) {
                            break;
                        }
                        count += l;
                        buffer.write(tmp, 0, l);
                        sendProgressMessage(count, (int) contentLength);
                    }
                    tempFile.renameTo(target);
                    return null;
                } finally {
                    AsyncHttpClient.silentCloseInputStream(instream);
                    buffer.flush();
                    AsyncHttpClient.silentCloseOutputStream(buffer);
                }
            }
            return null;
        }
        return null;
    }
}
