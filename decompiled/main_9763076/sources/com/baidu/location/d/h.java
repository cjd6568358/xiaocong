package com.baidu.location.d;

import android.util.Log;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class h extends Thread {
    final /* synthetic */ String a;
    final /* synthetic */ e b;

    h(e eVar, String str) {
        this.b = eVar;
        this.a = str;
    }

    /* JADX WARN: Code duplicated, block: B:147:0x01e9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:159:0x01f3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:163:0x01ee A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:196:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:0x01e2  */
    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream gZIPInputStream;
        URL url;
        HttpsURLConnection httpsURLConnection;
        OutputStream outputStream;
        InputStream inputStream;
        URL url2;
        HttpsURLConnection httpsURLConnection2;
        OutputStream outputStream2 = null;
        byteArrayOutputStream = null;
        byteArrayOutputStream = null;
        byteArrayOutputStream = null;
        outputStream2 = null;
        byteArrayOutputStream = null;
        outputStream2 = null;
        outputStream2 = null;
        byteArrayOutputStream = null;
        outputStream2 = null;
        outputStream2 = null;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        this.b.a();
        this.b.b();
        this.b.h = this.a;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            url = new URL(this.b.h);
            try {
                HttpsURLConnection httpsURLConnection3 = (HttpsURLConnection) url.openConnection();
                try {
                    httpsURLConnection3.setInstanceFollowRedirects(false);
                    httpsURLConnection3.setDoOutput(true);
                    httpsURLConnection3.setDoInput(true);
                    httpsURLConnection3.setConnectTimeout(a.b);
                    httpsURLConnection3.setReadTimeout(a.c);
                    httpsURLConnection3.setRequestMethod(HttpPost.METHOD_NAME);
                    httpsURLConnection3.setRequestProperty(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8");
                    httpsURLConnection3.setRequestProperty("Accept-Encoding", "gzip");
                    for (Map.Entry<String, Object> entry : this.b.k.entrySet()) {
                        stringBuffer.append(entry.getKey());
                        stringBuffer.append("=");
                        stringBuffer.append(entry.getValue());
                        stringBuffer.append("&");
                    }
                    if (stringBuffer.length() > 0) {
                        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    }
                    OutputStream outputStream3 = httpsURLConnection3.getOutputStream();
                    try {
                        outputStream3.write(stringBuffer.toString().getBytes());
                        outputStream3.flush();
                        if (httpsURLConnection3.getResponseCode() == 200) {
                            InputStream inputStream2 = httpsURLConnection3.getInputStream();
                            try {
                                String contentEncoding = httpsURLConnection3.getContentEncoding();
                                gZIPInputStream = (contentEncoding == null || !contentEncoding.contains("gzip")) ? inputStream2 : new GZIPInputStream(new BufferedInputStream(inputStream2));
                                try {
                                    byteArrayOutputStream = new ByteArrayOutputStream();
                                    try {
                                        byte[] bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                                        while (true) {
                                            int i = gZIPInputStream.read(bArr);
                                            if (i == -1) {
                                                break;
                                            } else {
                                                byteArrayOutputStream.write(bArr, 0, i);
                                            }
                                        }
                                        this.b.j = new String(byteArrayOutputStream.toByteArray(), "utf-8");
                                        this.b.a(true);
                                    } catch (Error e) {
                                        outputStream2 = outputStream3;
                                        httpsURLConnection = httpsURLConnection3;
                                        e = e;
                                        try {
                                            e.printStackTrace();
                                            Log.i(a.a, "https NetworkCommunicationError!");
                                            this.b.j = null;
                                            this.b.a(false);
                                            if (httpsURLConnection != null) {
                                                httpsURLConnection.disconnect();
                                            }
                                            if (url != null) {
                                            }
                                            if (outputStream2 != null) {
                                                try {
                                                    outputStream2.close();
                                                } catch (Exception e2) {
                                                    Log.d(a.a, "close os IOException!");
                                                }
                                            }
                                            if (gZIPInputStream != null) {
                                                try {
                                                    gZIPInputStream.close();
                                                } catch (Exception e3) {
                                                    Log.d(a.a, "close is IOException!");
                                                }
                                            }
                                            if (byteArrayOutputStream != null) {
                                                try {
                                                    byteArrayOutputStream.close();
                                                    return;
                                                } catch (Exception e4) {
                                                    Log.d(a.a, "close baos IOException!");
                                                    return;
                                                }
                                            }
                                            return;
                                        } catch (Throwable th) {
                                            th = th;
                                            if (httpsURLConnection != null) {
                                                httpsURLConnection.disconnect();
                                            }
                                            if (url != null) {
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
                                            if (byteArrayOutputStream == null) {
                                                throw th;
                                            }
                                            try {
                                                byteArrayOutputStream.close();
                                                throw th;
                                            } catch (Exception e7) {
                                                Log.d(a.a, "close baos IOException!");
                                                throw th;
                                            }
                                        }
                                    } catch (Exception e8) {
                                        byteArrayOutputStream2 = byteArrayOutputStream;
                                        url2 = url;
                                        outputStream = outputStream3;
                                        inputStream = gZIPInputStream;
                                        httpsURLConnection2 = httpsURLConnection3;
                                        e = e8;
                                        try {
                                            e.printStackTrace();
                                            Log.i(a.a, "https NetworkCommunicationException!");
                                            this.b.j = null;
                                            this.b.a(false);
                                            if (httpsURLConnection2 != null) {
                                                httpsURLConnection2.disconnect();
                                            }
                                            if (url2 != null) {
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
                                                    return;
                                                } catch (Exception e11) {
                                                    Log.d(a.a, "close baos IOException!");
                                                    return;
                                                }
                                            }
                                            return;
                                        } catch (Throwable th2) {
                                            th = th2;
                                            url = url2;
                                            byteArrayOutputStream = byteArrayOutputStream2;
                                            outputStream2 = outputStream;
                                            httpsURLConnection = httpsURLConnection2;
                                            gZIPInputStream = inputStream;
                                            if (httpsURLConnection != null) {
                                                httpsURLConnection.disconnect();
                                            }
                                            if (url != null) {
                                            }
                                            if (outputStream2 != null) {
                                                outputStream2.close();
                                            }
                                            if (gZIPInputStream != null) {
                                                gZIPInputStream.close();
                                            }
                                            if (byteArrayOutputStream == null) {
                                                throw th;
                                            }
                                            byteArrayOutputStream.close();
                                            throw th;
                                        }
                                    } catch (Throwable th3) {
                                        outputStream2 = outputStream3;
                                        httpsURLConnection = httpsURLConnection3;
                                        th = th3;
                                        if (httpsURLConnection != null) {
                                            httpsURLConnection.disconnect();
                                        }
                                        if (url != null) {
                                        }
                                        if (outputStream2 != null) {
                                            outputStream2.close();
                                        }
                                        if (gZIPInputStream != null) {
                                            gZIPInputStream.close();
                                        }
                                        if (byteArrayOutputStream == null) {
                                            throw th;
                                        }
                                        byteArrayOutputStream.close();
                                        throw th;
                                    }
                                } catch (Error e12) {
                                    byteArrayOutputStream = null;
                                    outputStream2 = outputStream3;
                                    httpsURLConnection = httpsURLConnection3;
                                    e = e12;
                                } catch (Exception e13) {
                                    url2 = url;
                                    inputStream = gZIPInputStream;
                                    httpsURLConnection2 = httpsURLConnection3;
                                    e = e13;
                                    outputStream = outputStream3;
                                } catch (Throwable th4) {
                                    byteArrayOutputStream = null;
                                    outputStream2 = outputStream3;
                                    httpsURLConnection = httpsURLConnection3;
                                    th = th4;
                                }
                            } catch (Error e14) {
                                gZIPInputStream = inputStream2;
                                byteArrayOutputStream = null;
                                outputStream2 = outputStream3;
                                httpsURLConnection = httpsURLConnection3;
                                e = e14;
                            } catch (Exception e15) {
                                httpsURLConnection2 = httpsURLConnection3;
                                e = e15;
                                outputStream = outputStream3;
                                inputStream = inputStream2;
                                url2 = url;
                            } catch (Throwable th5) {
                                gZIPInputStream = inputStream2;
                                byteArrayOutputStream = null;
                                outputStream2 = outputStream3;
                                httpsURLConnection = httpsURLConnection3;
                                th = th5;
                            }
                        } else {
                            this.b.j = null;
                            this.b.a(false);
                            byteArrayOutputStream = null;
                            gZIPInputStream = null;
                        }
                        if (httpsURLConnection3 != null) {
                            httpsURLConnection3.disconnect();
                        }
                        if (url != null) {
                        }
                        if (outputStream3 != null) {
                            try {
                                outputStream3.close();
                            } catch (Exception e16) {
                                Log.d(a.a, "close os IOException!");
                            }
                        }
                        if (gZIPInputStream != null) {
                            try {
                                gZIPInputStream.close();
                            } catch (Exception e17) {
                                Log.d(a.a, "close is IOException!");
                            }
                        }
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception e18) {
                                Log.d(a.a, "close baos IOException!");
                            }
                        }
                    } catch (Error e19) {
                        byteArrayOutputStream = null;
                        gZIPInputStream = null;
                        outputStream2 = outputStream3;
                        httpsURLConnection = httpsURLConnection3;
                        e = e19;
                    } catch (Exception e20) {
                        url2 = url;
                        httpsURLConnection2 = httpsURLConnection3;
                        e = e20;
                        outputStream = outputStream3;
                        inputStream = null;
                    } catch (Throwable th6) {
                        byteArrayOutputStream = null;
                        gZIPInputStream = null;
                        outputStream2 = outputStream3;
                        httpsURLConnection = httpsURLConnection3;
                        th = th6;
                    }
                } catch (Error e21) {
                    byteArrayOutputStream = null;
                    gZIPInputStream = null;
                    httpsURLConnection = httpsURLConnection3;
                    e = e21;
                } catch (Exception e22) {
                    inputStream = null;
                    url2 = url;
                    httpsURLConnection2 = httpsURLConnection3;
                    e = e22;
                    outputStream = null;
                } catch (Throwable th7) {
                    byteArrayOutputStream = null;
                    gZIPInputStream = null;
                    httpsURLConnection = httpsURLConnection3;
                    th = th7;
                }
            } catch (Error e23) {
                e = e23;
                byteArrayOutputStream = null;
                gZIPInputStream = null;
                httpsURLConnection = null;
            } catch (Exception e24) {
                e = e24;
                outputStream = null;
                inputStream = null;
                url2 = url;
                httpsURLConnection2 = null;
            } catch (Throwable th8) {
                th = th8;
                byteArrayOutputStream = null;
                gZIPInputStream = null;
                httpsURLConnection = null;
            }
        } catch (Error e25) {
            e = e25;
            byteArrayOutputStream = null;
            gZIPInputStream = null;
            url = null;
            httpsURLConnection = null;
        } catch (Exception e26) {
            e = e26;
            outputStream = null;
            inputStream = null;
            url2 = null;
            httpsURLConnection2 = null;
        } catch (Throwable th9) {
            th = th9;
            byteArrayOutputStream = null;
            gZIPInputStream = null;
            url = null;
            httpsURLConnection = null;
        }
    }
}
