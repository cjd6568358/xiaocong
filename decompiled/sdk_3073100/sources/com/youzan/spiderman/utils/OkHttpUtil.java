package com.youzan.spiderman.utils;

import android.content.Context;
import com.youzan.spiderman.d.d;
import com.youzan.spiderman.html.l;
import com.youzan.spiderman.html.o;
import com.youzan.spiderman.html.p;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class OkHttpUtil {
    private static OkHttpClient mOkHttpClient = null;
    private static OkHttpClient mOkHttpClientForHtml = null;

    private static OkHttpClient withOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }
        return mOkHttpClient;
    }

    private static OkHttpClient withOkHttpClientHtml() {
        if (mOkHttpClientForHtml == null) {
            mOkHttpClientForHtml = new OkHttpClient.Builder().followRedirects(false).followSslRedirects(false).connectTimeout(15L, TimeUnit.SECONDS).build();
        }
        return mOkHttpClientForHtml;
    }

    public static p downloadHtml(l htmlHeader, o htmlUrl) {
        Request request = new Request.Builder().url(htmlUrl.a()).headers(Headers.of((Map<String, String>) htmlHeader.b())).build();
        Call call = withOkHttpClientHtml().newCall(request);
        try {
            Response response = call.execute();
            Headers headers = response.headers();
            return new p(htmlUrl, headers, response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void downloadFile(Context context, String fileUrl, final File file, final FileCallback fileCallback) {
        if (!NetWorkUtil.hasNetworkPermission(context)) {
            Logger.e("OkHttpUtil", "has no network permission to download file", new Object[0]);
            fileCallback.fail(-1, null);
        } else {
            Request request = new Request.Builder().url(fileUrl).build();
            Call call = withOkHttpClient().newCall(request);
            call.enqueue(new Callback() { // from class: com.youzan.spiderman.utils.OkHttpUtil.1
                /* JADX WARN: Code duplicated, block: B:39:0x007b A[Catch: IOException -> 0x007f, TRY_LEAVE, TryCatch #5 {IOException -> 0x007f, blocks: (B:37:0x0076, B:39:0x007b), top: B:52:0x0076 }] */
                @Override // okhttp3.Callback
                public void onResponse(Call call2, Response response) throws Throwable {
                    if (response.isSuccessful()) {
                        InputStream is = null;
                        byte[] buf = new byte[1024];
                        FileOutputStream fos = null;
                        try {
                            try {
                                ResponseBody responseBody = response.body();
                                if (responseBody != null) {
                                    is = responseBody.byteStream();
                                    FileOutputStream fos2 = new FileOutputStream(file);
                                    while (true) {
                                        try {
                                            int len = is.read(buf);
                                            if (len == -1) {
                                                break;
                                            } else {
                                                fos2.write(buf, 0, len);
                                            }
                                        } catch (IOException e) {
                                            e = e;
                                            fos = fos2;
                                            fileCallback.fail(-1, e);
                                            e.printStackTrace();
                                            if (is != null) {
                                                try {
                                                    is.close();
                                                } catch (IOException e2) {
                                                    fileCallback.fail(-1, e2);
                                                    e2.printStackTrace();
                                                    return;
                                                }
                                            }
                                            if (fos != null) {
                                                fos.close();
                                                return;
                                            }
                                            return;
                                        } catch (Throwable th) {
                                            th = th;
                                            fos = fos2;
                                            if (is != null) {
                                                try {
                                                    is.close();
                                                    if (fos != null) {
                                                        fos.close();
                                                    }
                                                } catch (IOException e3) {
                                                    fileCallback.fail(-1, e3);
                                                    e3.printStackTrace();
                                                    throw th;
                                                }
                                            } else if (fos != null) {
                                                fos.close();
                                            }
                                            throw th;
                                        }
                                    }
                                    fos2.flush();
                                    fileCallback.success();
                                    fos = fos2;
                                } else {
                                    fileCallback.fail(-1, null);
                                }
                                if (is != null) {
                                    try {
                                        is.close();
                                    } catch (IOException e4) {
                                        fileCallback.fail(-1, e4);
                                        e4.printStackTrace();
                                        return;
                                    }
                                }
                                if (fos != null) {
                                    fos.close();
                                }
                            } catch (IOException e5) {
                                e = e5;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } else {
                        fileCallback.fail(response.code(), null);
                    }
                }

                @Override // okhttp3.Callback
                public void onFailure(Call call2, IOException e) {
                    fileCallback.fail(-1, e);
                }
            });
        }
    }

    public static d downloadFile(Context context, String url) {
        if (!NetWorkUtil.hasNetworkPermission(context)) {
            Logger.e("OkHttpUtil", "has no network permission to download file", new Object[0]);
            return null;
        }
        Request request = new Request.Builder().url(url).build();
        try {
            ResponseBody body = withOkHttpClient().newCall(request).execute().body();
            if (body == null) {
                return null;
            }
            Charset charset = getContentCharset(body);
            return new d(charset, body.byteStream(), body.charStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Charset getContentCharset(ResponseBody body) {
        MediaType contentType = body.contentType();
        return contentType != null ? contentType.charset(Util.UTF_8) : Util.UTF_8;
    }
}
