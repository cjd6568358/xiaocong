package com.xiaomi.push.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ag {

    public static class a {
        byte[] a;
        int b;

        public a(byte[] bArr, int i) {
            this.a = bArr;
            this.b = i;
        }
    }

    public static class b {
        public Bitmap a;
        public long b;

        public b(Bitmap bitmap, long j) {
            this.a = bitmap;
            this.b = j;
        }
    }

    private static int a(Context context, InputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            com.xiaomi.channel.commonutils.logger.b.a("decode dimension failed for bitmap.");
            return 1;
        }
        int iRound = Math.round((context.getResources().getDisplayMetrics().densityDpi / 160.0f) * 48.0f);
        if (options.outWidth <= iRound || options.outHeight <= iRound) {
            return 1;
        }
        return Math.min(options.outWidth / iRound, options.outHeight / iRound);
    }

    /* JADX WARN: Code duplicated, block: B:50:0x00e0  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v18, types: [com.xiaomi.push.service.ag$a] */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r8v0 */
    private static a a(String str) throws Throwable {
        InputStream inputStream;
        ?? r2;
        int i = 102400;
        ?? r1 = 0;
        try {
            ?? r0 = (HttpURLConnection) new URL(str).openConnection();
            try {
                r0.setConnectTimeout(8000);
                r0.setReadTimeout(20000);
                r0.connect();
                int contentLength = r0.getContentLength();
                if (contentLength > 102400) {
                    com.xiaomi.channel.commonutils.logger.b.a("Bitmap size is too big, max size is 102400  contentLen size is " + contentLength + " from url " + str);
                    com.xiaomi.channel.commonutils.file.a.a((InputStream) null);
                    if (r0 != 0) {
                        r0.disconnect();
                    }
                    r0 = 0;
                } else {
                    int responseCode = r0.getResponseCode();
                    if (responseCode != 200) {
                        com.xiaomi.channel.commonutils.logger.b.a("Invalid Http Response Code " + responseCode + " received");
                        com.xiaomi.channel.commonutils.file.a.a((InputStream) null);
                        if (r0 != 0) {
                            r0.disconnect();
                        }
                        r0 = 0;
                    } else {
                        inputStream = r0.getInputStream();
                        try {
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] bArr = new byte[1024];
                            while (i > 0) {
                                int i2 = inputStream.read(bArr, 0, 1024);
                                if (i2 == -1) {
                                    break;
                                }
                                i -= i2;
                                byteArrayOutputStream.write(bArr, 0, i2);
                            }
                            if (i <= 0) {
                                com.xiaomi.channel.commonutils.logger.b.a("length 102400 exhausted.");
                                a aVar = new a(null, 102400);
                                com.xiaomi.channel.commonutils.file.a.a(inputStream);
                                if (r0 != 0) {
                                    r0.disconnect();
                                }
                                r0 = aVar;
                            } else {
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                a aVar2 = new a(byteArray, byteArray.length);
                                com.xiaomi.channel.commonutils.file.a.a(inputStream);
                                if (r0 != 0) {
                                    r0.disconnect();
                                }
                                r0 = aVar2;
                            }
                        } catch (IOException e) {
                            r2 = r0;
                            e = e;
                            try {
                                com.xiaomi.channel.commonutils.logger.b.a(e);
                                com.xiaomi.channel.commonutils.file.a.a(inputStream);
                                if (r2 != 0) {
                                    r2.disconnect();
                                }
                                return null;
                            } catch (Throwable th) {
                                th = th;
                                r1 = r2;
                                com.xiaomi.channel.commonutils.file.a.a(inputStream);
                                if (r1 != 0) {
                                    r1.disconnect();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            r1 = r0;
                            th = th2;
                            com.xiaomi.channel.commonutils.file.a.a(inputStream);
                            if (r1 != 0) {
                                r1.disconnect();
                            }
                            throw th;
                        }
                    }
                }
                return r0;
            } catch (IOException e2) {
                inputStream = null;
                ?? r8 = r0;
                e = e2;
                r2 = r8;
            } catch (Throwable th3) {
                inputStream = null;
                r1 = r0;
                th = th3;
            }
        } catch (IOException e3) {
            e = e3;
            r2 = 0;
            inputStream = null;
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
        }
    }

    public static b a(Context context, String str) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        b bVar = new b(null, 0L);
        try {
            a aVarA = a(str);
            if (aVarA == null) {
                com.xiaomi.channel.commonutils.file.a.a((InputStream) null);
            } else {
                bVar.b = aVarA.b;
                byte[] bArr = aVarA.a;
                if (bArr != null) {
                    byteArrayInputStream = new ByteArrayInputStream(bArr);
                    try {
                        try {
                            int iA = a(context, byteArrayInputStream);
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = iA;
                            bVar.a = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                        } catch (Exception e) {
                            e = e;
                            com.xiaomi.channel.commonutils.logger.b.a(e);
                        }
                    } catch (Throwable th) {
                        th = th;
                        com.xiaomi.channel.commonutils.file.a.a(byteArrayInputStream);
                        throw th;
                    }
                } else {
                    byteArrayInputStream = null;
                }
                com.xiaomi.channel.commonutils.file.a.a(byteArrayInputStream);
            }
        } catch (Exception e2) {
            e = e2;
            byteArrayInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayInputStream = null;
            com.xiaomi.channel.commonutils.file.a.a(byteArrayInputStream);
            throw th;
        }
        return bVar;
    }

    public static Bitmap b(Context context, String str) throws Throwable {
        InputStream inputStreamOpenInputStream;
        InputStream inputStreamOpenInputStream2;
        Throwable th;
        Bitmap bitmapDecodeStream = null;
        Uri uri = Uri.parse(str);
        try {
            inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            try {
                int iA = a(context, inputStreamOpenInputStream);
                inputStreamOpenInputStream2 = context.getContentResolver().openInputStream(uri);
                try {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = iA;
                        bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream2, null, options);
                        com.xiaomi.channel.commonutils.file.a.a(inputStreamOpenInputStream2);
                    } catch (IOException e) {
                        e = e;
                        com.xiaomi.channel.commonutils.logger.b.a(e);
                        com.xiaomi.channel.commonutils.file.a.a(inputStreamOpenInputStream2);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    com.xiaomi.channel.commonutils.file.a.a(inputStreamOpenInputStream2);
                    com.xiaomi.channel.commonutils.file.a.a(inputStreamOpenInputStream);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                inputStreamOpenInputStream2 = null;
            } catch (Throwable th3) {
                inputStreamOpenInputStream2 = null;
                th = th3;
                com.xiaomi.channel.commonutils.file.a.a(inputStreamOpenInputStream2);
                com.xiaomi.channel.commonutils.file.a.a(inputStreamOpenInputStream);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            inputStreamOpenInputStream = null;
            inputStreamOpenInputStream2 = null;
        } catch (Throwable th4) {
            inputStreamOpenInputStream = null;
            inputStreamOpenInputStream2 = null;
            th = th4;
        }
        com.xiaomi.channel.commonutils.file.a.a(inputStreamOpenInputStream);
        return bitmapDecodeStream;
    }
}
