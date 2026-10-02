package com.facebook.react.modules.camera;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64OutputStream;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageStoreManager extends ReactContextBaseJavaModule {
    private static final int BUFFER_SIZE = 8192;

    public ImageStoreManager(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "ImageStoreManager";
    }

    @ReactMethod
    public void getBase64ForTag(String uri, Callback success, Callback error) {
        new GetBase64Task(getReactApplicationContext(), uri, success, error).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private class GetBase64Task extends GuardedAsyncTask<Void, Void> {
        private final Callback mError;
        private final Callback mSuccess;
        private final String mUri;

        private GetBase64Task(ReactContext reactContext, String uri, Callback success, Callback error) {
            super(reactContext);
            this.mUri = uri;
            this.mSuccess = success;
            this.mError = error;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.react.bridge.GuardedAsyncTask
        public void doInBackgroundGuarded(Void... params) {
            try {
                ContentResolver contentResolver = ImageStoreManager.this.getReactApplicationContext().getContentResolver();
                Uri uri = Uri.parse(this.mUri);
                InputStream is = contentResolver.openInputStream(uri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Base64OutputStream b64os = new Base64OutputStream(baos, 0);
                byte[] buffer = new byte[ImageStoreManager.BUFFER_SIZE];
                while (true) {
                    try {
                        try {
                            int bytesRead = is.read(buffer);
                            if (bytesRead > -1) {
                                b64os.write(buffer, 0, bytesRead);
                            } else {
                                this.mSuccess.invoke(baos.toString());
                                ImageStoreManager.closeQuietly(is);
                                ImageStoreManager.closeQuietly(b64os);
                                return;
                            }
                        } catch (IOException e) {
                            this.mError.invoke(e.getMessage());
                            ImageStoreManager.closeQuietly(is);
                            ImageStoreManager.closeQuietly(b64os);
                            return;
                        }
                    } catch (Throwable th) {
                        ImageStoreManager.closeQuietly(is);
                        ImageStoreManager.closeQuietly(b64os);
                        throw th;
                    }
                    ImageStoreManager.closeQuietly(is);
                    ImageStoreManager.closeQuietly(b64os);
                    throw th;
                }
            } catch (FileNotFoundException e2) {
                this.mError.invoke(e2.getMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
        }
    }
}
