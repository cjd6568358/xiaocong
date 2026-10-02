package com.baidu.location.d;

import android.text.TextUtils;
import android.util.Log;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class g extends Thread {
    final /* synthetic */ String a;
    final /* synthetic */ boolean b;
    final /* synthetic */ e c;

    g(e eVar, String str, boolean z) {
        this.c = eVar;
        this.a = str;
        this.b = z;
    }

    /* JADX WARN: Code duplicated, block: B:148:0x020b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:152:0x0210 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:162:0x0206 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:187:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:0x0201  */
    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws Throwable {
        InputStream gZIPInputStream;
        HttpURLConnection httpURLConnection;
        ByteArrayOutputStream byteArrayOutputStream;
        OutputStream outputStream;
        ByteArrayOutputStream byteArrayOutputStream2;
        HttpURLConnection httpURLConnection2;
        InputStream inputStream;
        OutputStream outputStream2;
        HttpURLConnection httpURLConnection3;
        boolean z;
        boolean z2;
        ByteArrayOutputStream byteArrayOutputStream3;
        InputStream inputStream2;
        OutputStream outputStream3 = null;
        this.c.h = j.c();
        this.c.b();
        this.c.a();
        HttpURLConnection httpURLConnection4 = null;
        int i = this.c.i;
        while (i > 0) {
            try {
                URL url = new URL(this.c.h);
                StringBuffer stringBuffer = new StringBuffer();
                for (Map.Entry<String, Object> entry : this.c.k.entrySet()) {
                    stringBuffer.append(entry.getKey());
                    stringBuffer.append("=");
                    stringBuffer.append(entry.getValue());
                    stringBuffer.append("&");
                }
                if (stringBuffer.length() > 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
                httpURLConnection3 = (HttpURLConnection) url.openConnection();
                try {
                    httpURLConnection3.setRequestMethod(HttpPost.METHOD_NAME);
                    httpURLConnection3.setDoInput(true);
                    httpURLConnection3.setDoOutput(true);
                    httpURLConnection3.setUseCaches(false);
                    httpURLConnection3.setConnectTimeout(a.b);
                    httpURLConnection3.setReadTimeout(a.b);
                    httpURLConnection3.setRequestProperty(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
                    httpURLConnection3.setRequestProperty("Accept-Charset", HTTP.UTF_8);
                    httpURLConnection3.setRequestProperty("Accept-Encoding", "gzip");
                    if (!TextUtils.isEmpty(this.a)) {
                        httpURLConnection3.setRequestProperty(HTTP.TARGET_HOST, this.a);
                    }
                    outputStream2 = httpURLConnection3.getOutputStream();
                    try {
                        outputStream2.write(stringBuffer.toString().getBytes());
                        outputStream2.flush();
                        if (httpURLConnection3.getResponseCode() == 200) {
                            inputStream = httpURLConnection3.getInputStream();
                            try {
                                String contentEncoding = httpURLConnection3.getContentEncoding();
                                gZIPInputStream = (contentEncoding == null || !contentEncoding.contains("gzip")) ? inputStream : new GZIPInputStream(new BufferedInputStream(inputStream));
                                try {
                                    byteArrayOutputStream = new ByteArrayOutputStream();
                                    try {
                                        try {
                                            byte[] bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                                            while (true) {
                                                int i2 = gZIPInputStream.read(bArr);
                                                if (i2 == -1) {
                                                    break;
                                                } else {
                                                    byteArrayOutputStream.write(bArr, 0, i2);
                                                }
                                            }
                                            this.c.j = new String(byteArrayOutputStream.toByteArray(), "utf-8");
                                            if (this.b) {
                                                this.c.m = byteArrayOutputStream.toByteArray();
                                            }
                                            this.c.a(true);
                                            inputStream2 = gZIPInputStream;
                                            byteArrayOutputStream3 = byteArrayOutputStream;
                                            z2 = true;
                                        } catch (Throwable th) {
                                            outputStream3 = outputStream2;
                                            httpURLConnection = httpURLConnection3;
                                            th = th;
                                            if (httpURLConnection != null) {
                                                httpURLConnection.disconnect();
                                            }
                                            if (outputStream3 != null) {
                                                try {
                                                    outputStream3.close();
                                                } catch (Exception e) {
                                                    Log.d(a.a, "close os IOException!");
                                                }
                                            }
                                            if (gZIPInputStream != null) {
                                                try {
                                                    gZIPInputStream.close();
                                                } catch (Exception e2) {
                                                    Log.d(a.a, "close is IOException!");
                                                }
                                            }
                                            if (byteArrayOutputStream != null) {
                                                throw th;
                                            }
                                            try {
                                                byteArrayOutputStream.close();
                                                throw th;
                                            } catch (Exception e3) {
                                                Log.d(a.a, "close baos IOException!");
                                                throw th;
                                            }
                                        }
                                    } catch (Error e4) {
                                        Log.d(a.a, "NetworkCommunicationError!");
                                        if (httpURLConnection3 != null) {
                                            httpURLConnection3.disconnect();
                                        }
                                        if (outputStream2 != null) {
                                            try {
                                                outputStream2.close();
                                            } catch (Exception e5) {
                                                Log.d(a.a, "close os IOException!");
                                            }
                                        }
                                        if (gZIPInputStream != null) {
                                            try {
                                                gZIPInputStream.close();
                                            } catch (Exception e6) {
                                                Log.d(a.a, "close is IOException!");
                                            }
                                        }
                                        if (byteArrayOutputStream != null) {
                                            try {
                                                byteArrayOutputStream.close();
                                            } catch (Exception e7) {
                                                Log.d(a.a, "close baos IOException!");
                                                httpURLConnection4 = httpURLConnection3;
                                                z = false;
                                            }
                                        }
                                        httpURLConnection4 = httpURLConnection3;
                                        z = false;
                                    } catch (Exception e8) {
                                        byteArrayOutputStream2 = byteArrayOutputStream;
                                        inputStream = gZIPInputStream;
                                        httpURLConnection2 = httpURLConnection3;
                                        outputStream = outputStream2;
                                        try {
                                            Log.d(a.a, "NetworkCommunicationException!");
                                            if (httpURLConnection2 != null) {
                                                httpURLConnection2.disconnect();
                                            }
                                            if (outputStream != null) {
                                                try {
                                                    outputStream.close();
                                                } catch (Exception e9) {
                                                    Log.d(a.a, "close os IOException!");
                                                }
                                            }
                                            if (inputStream != null) {
                                                try {
                                                    inputStream.close();
                                                } catch (Exception e10) {
                                                    Log.d(a.a, "close is IOException!");
                                                }
                                            }
                                            if (byteArrayOutputStream2 != null) {
                                                try {
                                                    byteArrayOutputStream2.close();
                                                } catch (Exception e11) {
                                                    Log.d(a.a, "close baos IOException!");
                                                    z = false;
                                                    httpURLConnection4 = httpURLConnection2;
                                                }
                                            }
                                            z = false;
                                            httpURLConnection4 = httpURLConnection2;
                                        } catch (Throwable th2) {
                                            outputStream3 = outputStream;
                                            th = th2;
                                            ByteArrayOutputStream byteArrayOutputStream4 = byteArrayOutputStream2;
                                            httpURLConnection = httpURLConnection2;
                                            gZIPInputStream = inputStream;
                                            byteArrayOutputStream = byteArrayOutputStream4;
                                            if (httpURLConnection != null) {
                                                httpURLConnection.disconnect();
                                            }
                                            if (outputStream3 != null) {
                                                outputStream3.close();
                                            }
                                            if (gZIPInputStream != null) {
                                                gZIPInputStream.close();
                                            }
                                            if (byteArrayOutputStream != null) {
                                                throw th;
                                            }
                                            byteArrayOutputStream.close();
                                            throw th;
                                        }
                                    }
                                } catch (Error e12) {
                                    byteArrayOutputStream = null;
                                } catch (Exception e13) {
                                    inputStream = gZIPInputStream;
                                    httpURLConnection2 = httpURLConnection3;
                                    outputStream = outputStream2;
                                    byteArrayOutputStream2 = null;
                                } catch (Throwable th3) {
                                    byteArrayOutputStream = null;
                                    outputStream3 = outputStream2;
                                    httpURLConnection = httpURLConnection3;
                                    th = th3;
                                }
                            } catch (Error e14) {
                                gZIPInputStream = inputStream;
                                byteArrayOutputStream = null;
                            } catch (Exception e15) {
                                httpURLConnection2 = httpURLConnection3;
                                outputStream = outputStream2;
                                byteArrayOutputStream2 = null;
                            } catch (Throwable th4) {
                                gZIPInputStream = inputStream;
                                byteArrayOutputStream = null;
                                outputStream3 = outputStream2;
                                httpURLConnection = httpURLConnection3;
                                th = th4;
                            }
                        } else {
                            z2 = false;
                            byteArrayOutputStream3 = null;
                            inputStream2 = null;
                        }
                        if (httpURLConnection3 != null) {
                            httpURLConnection3.disconnect();
                        }
                        if (outputStream2 != null) {
                            try {
                                outputStream2.close();
                            } catch (Exception e16) {
                                Log.d(a.a, "close os IOException!");
                            }
                        }
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                            } catch (Exception e17) {
                                Log.d(a.a, "close is IOException!");
                            }
                        }
                        if (byteArrayOutputStream3 != null) {
                            try {
                                byteArrayOutputStream3.close();
                            } catch (Exception e18) {
                                Log.d(a.a, "close baos IOException!");
                                boolean z3 = z2;
                                httpURLConnection4 = httpURLConnection3;
                                z = z3;
                            }
                        }
                        boolean z4 = z2;
                        httpURLConnection4 = httpURLConnection3;
                        z = z4;
                    } catch (Error e19) {
                        byteArrayOutputStream = null;
                        gZIPInputStream = null;
                    } catch (Exception e20) {
                        inputStream = null;
                        httpURLConnection2 = httpURLConnection3;
                        outputStream = outputStream2;
                        byteArrayOutputStream2 = null;
                    } catch (Throwable th5) {
                        gZIPInputStream = null;
                        httpURLConnection = httpURLConnection3;
                        th = th5;
                        byteArrayOutputStream = null;
                        outputStream3 = outputStream2;
                    }
                } catch (Error e21) {
                    outputStream2 = null;
                    byteArrayOutputStream = null;
                    gZIPInputStream = null;
                } catch (Exception e22) {
                    byteArrayOutputStream2 = null;
                    inputStream = null;
                    httpURLConnection2 = httpURLConnection3;
                    outputStream = null;
                } catch (Throwable th6) {
                    byteArrayOutputStream = null;
                    gZIPInputStream = null;
                    httpURLConnection = httpURLConnection3;
                    th = th6;
                }
            } catch (Error e23) {
                outputStream2 = null;
                gZIPInputStream = null;
                httpURLConnection3 = httpURLConnection4;
                byteArrayOutputStream = null;
            } catch (Exception e24) {
                outputStream = null;
                byteArrayOutputStream2 = null;
                httpURLConnection2 = httpURLConnection4;
                inputStream = null;
            } catch (Throwable th7) {
                th = th7;
                gZIPInputStream = null;
                httpURLConnection = httpURLConnection4;
                byteArrayOutputStream = null;
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
        this.c.j = null;
        this.c.a(false);
    }
}
