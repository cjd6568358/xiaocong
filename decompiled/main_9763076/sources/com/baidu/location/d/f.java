package com.baidu.location.d;

import android.util.Log;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class f extends Thread {
    final /* synthetic */ e a;

    f(e eVar) {
        this.a = eVar;
    }

    /* JADX WARN: Code duplicated, block: B:105:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:51:0x0103  */
    /* JADX WARN: Code duplicated, block: B:81:0x010d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:87:0x0108 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws Throwable {
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        HttpURLConnection httpURLConnection;
        InputStream inputStream2;
        boolean z;
        boolean z2;
        ByteArrayOutputStream byteArrayOutputStream2;
        InputStream inputStream3;
        ByteArrayOutputStream byteArrayOutputStream3 = null;
        this.a.h = j.c();
        this.a.b();
        this.a.a();
        HttpURLConnection httpURLConnection2 = null;
        int i = this.a.i;
        while (i > 0) {
            try {
                HttpURLConnection httpURLConnection3 = (HttpURLConnection) new URL(this.a.h).openConnection();
                try {
                    httpURLConnection3.setRequestMethod(HttpGet.METHOD_NAME);
                    httpURLConnection3.setDoInput(true);
                    httpURLConnection3.setDoOutput(true);
                    httpURLConnection3.setUseCaches(false);
                    httpURLConnection3.setConnectTimeout(a.b);
                    httpURLConnection3.setReadTimeout(a.b);
                    httpURLConnection3.setRequestProperty(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
                    httpURLConnection3.setRequestProperty("Accept-Charset", HTTP.UTF_8);
                    if (httpURLConnection3.getResponseCode() == 200) {
                        inputStream = httpURLConnection3.getInputStream();
                        try {
                            ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                            try {
                                byte[] bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                                while (true) {
                                    int i2 = inputStream.read(bArr);
                                    if (i2 == -1) {
                                        break;
                                    } else {
                                        byteArrayOutputStream4.write(bArr, 0, i2);
                                    }
                                }
                                inputStream.close();
                                byteArrayOutputStream4.close();
                                this.a.j = new String(byteArrayOutputStream4.toByteArray(), "utf-8");
                                this.a.a(true);
                                httpURLConnection3.disconnect();
                                inputStream3 = inputStream;
                                byteArrayOutputStream2 = byteArrayOutputStream4;
                                z2 = true;
                            } catch (Exception e) {
                                inputStream2 = inputStream;
                                httpURLConnection = httpURLConnection3;
                                byteArrayOutputStream = byteArrayOutputStream4;
                                try {
                                    Log.d(a.a, "NetworkCommunicationException!");
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    if (inputStream2 != null) {
                                        try {
                                            inputStream2.close();
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                    if (byteArrayOutputStream != null) {
                                        try {
                                            byteArrayOutputStream.close();
                                            z = false;
                                            httpURLConnection2 = httpURLConnection;
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                            z = false;
                                            httpURLConnection2 = httpURLConnection;
                                        }
                                    } else {
                                        z = false;
                                        httpURLConnection2 = httpURLConnection;
                                    }
                                } catch (Throwable th) {
                                    byteArrayOutputStream3 = byteArrayOutputStream;
                                    th = th;
                                    InputStream inputStream4 = inputStream2;
                                    httpURLConnection2 = httpURLConnection;
                                    inputStream = inputStream4;
                                    if (httpURLConnection2 != null) {
                                        httpURLConnection2.disconnect();
                                    }
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Exception e4) {
                                            e4.printStackTrace();
                                        }
                                    }
                                    if (byteArrayOutputStream3 != null) {
                                        throw th;
                                    }
                                    try {
                                        byteArrayOutputStream3.close();
                                        throw th;
                                    } catch (Exception e5) {
                                        e5.printStackTrace();
                                        throw th;
                                    }
                                }
                            } catch (Throwable th2) {
                                byteArrayOutputStream3 = byteArrayOutputStream4;
                                httpURLConnection2 = httpURLConnection3;
                                th = th2;
                                if (httpURLConnection2 != null) {
                                    httpURLConnection2.disconnect();
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                if (byteArrayOutputStream3 != null) {
                                    throw th;
                                }
                                byteArrayOutputStream3.close();
                                throw th;
                            }
                        } catch (Exception e6) {
                            inputStream2 = inputStream;
                            httpURLConnection = httpURLConnection3;
                            byteArrayOutputStream = null;
                        } catch (Throwable th3) {
                            httpURLConnection2 = httpURLConnection3;
                            th = th3;
                        }
                    } else {
                        httpURLConnection3.disconnect();
                        z2 = false;
                        byteArrayOutputStream2 = null;
                        inputStream3 = null;
                    }
                    if (httpURLConnection3 != null) {
                        httpURLConnection3.disconnect();
                    }
                    if (inputStream3 != null) {
                        try {
                            inputStream3.close();
                        } catch (Exception e7) {
                            e7.printStackTrace();
                        }
                    }
                    if (byteArrayOutputStream2 != null) {
                        try {
                            byteArrayOutputStream2.close();
                            boolean z3 = z2;
                            httpURLConnection2 = httpURLConnection3;
                            z = z3;
                        } catch (Exception e8) {
                            e8.printStackTrace();
                            boolean z4 = z2;
                            httpURLConnection2 = httpURLConnection3;
                            z = z4;
                        }
                    } else {
                        boolean z5 = z2;
                        httpURLConnection2 = httpURLConnection3;
                        z = z5;
                    }
                } catch (Exception e9) {
                    inputStream2 = null;
                    httpURLConnection = httpURLConnection3;
                    byteArrayOutputStream = null;
                } catch (Throwable th4) {
                    inputStream = null;
                    httpURLConnection2 = httpURLConnection3;
                    th = th4;
                }
            } catch (Exception e10) {
                byteArrayOutputStream = null;
                httpURLConnection = httpURLConnection2;
                inputStream2 = null;
            } catch (Throwable th5) {
                th = th5;
                inputStream = null;
            }
            if (z) {
                break;
            } else {
                i--;
            }
        }
        if (i > 0) {
            e.o = 0;
            return;
        }
        e.o++;
        this.a.j = null;
        this.a.a(false);
    }
}
