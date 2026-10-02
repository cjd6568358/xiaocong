package com.hzy.tvmao.model.legacy.api;

import android.text.TextUtils;
import com.hzy.tvmao.KookongSDK;
import com.hzy.tvmao.utils.LogUtil;
import com.hzy.tvmao.y;
import com.kookong.app.data.AppConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.SM;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: HttpUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class b {
    private static long g = 0;
    private static String h = Constants.MAIN_VERSION_TAG;
    public String a;
    private HashMap<String, String> b;
    private HttpURLConnection c;
    private boolean d;
    private boolean e;
    private boolean f;

    public b() {
        this(false);
    }

    public b(boolean z) {
        this.a = Constants.MAIN_VERSION_TAG;
        this.d = true;
        this.e = false;
        this.f = false;
        if (z) {
            c();
        }
        b();
    }

    private void b() {
        this.a = System.getProperty("http.agent").replace("Dalvik", "Dalv1k");
    }

    public void a(boolean z) {
        this.e = z;
    }

    public void b(boolean z) {
        this.f = z;
    }

    public void a(Map<String, String> map) {
        c();
        this.b.putAll(map);
    }

    private void c() {
        if (this.b == null) {
            this.b = new HashMap<>();
            this.b.put(NotifyType.SOUND, "android-kksdk");
            this.b.put("sdk", new StringBuilder(String.valueOf(y.g)).toString());
            this.b.put(ClientCookie.VERSION_ATTR, new StringBuilder(String.valueOf(y.b)).toString());
            this.b.put("secret", KookongSDK.APPKEY);
            this.b.put("pkg", y.e);
            this.b.put("platform", "android");
            this.b.put("cv", "2");
            this.b.put("sdhiad", y.j);
            this.b.put(AppConst.MODEL_NAME, String.valueOf(y.i) + "_" + y.h);
            this.b.put("sdk_vcode", new StringBuilder(String.valueOf(y.a)).toString());
            this.b.put("lanCode", com.hzy.tvmao.utils.d.i());
            if (!TextUtils.isEmpty(KookongSDK.DEVICEID)) {
                this.b.put(Constants.FLAG_DEVICE_ID, KookongSDK.DEVICEID);
            }
        }
    }

    public Map<String, Object> a(String str, boolean z) {
        return a(str, false, z);
    }

    private CharSequence d() throws Exception {
        HashMap<String, String> mapE = e();
        return (mapE == null || mapE.size() <= 0) ? Constants.MAIN_VERSION_TAG : b(mapE);
    }

    private HashMap<String, String> e() throws Exception {
        if (this.b == null || this.b.size() <= 0) {
            return null;
        }
        String strA = com.hzy.tvmao.utils.a.a(a(this.b));
        String strA2 = StreamHelper.a(strA);
        LogUtil.d("params is " + strA);
        HashMap<String, String> map = new HashMap<>();
        map.put("d2", strA2);
        return map;
    }

    private HashMap<String, String> a(HashMap<String, String> map) throws UnsupportedEncodingException {
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (map.get(it.next()) == null) {
                it.remove();
            }
        }
        return map;
    }

    private CharSequence b(HashMap<String, String> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                sb.append(URLEncoder.encode(entry.getKey(), HTTP.UTF_8)).append('=').append(URLEncoder.encode(entry.getValue(), HTTP.UTF_8)).append('&');
            }
        }
        sb.setLength(sb.length() - 1);
        return sb;
    }

    private static void a(HttpURLConnection httpURLConnection) {
    }

    /* JADX WARN: Code duplicated, block: B:109:0x0350  */
    /* JADX WARN: Code duplicated, block: B:125:0x03e7 A[EDGE_INSN: B:125:0x03e7->B:4:0x000a BREAK  A[LOOP:0: B:3:0x0008->B:45:0x0192]] */
    /* JADX WARN: Code duplicated, block: B:144:0x03d7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:146:0x0192 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x018a A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:44:0x018c  */
    /* JADX WARN: Code duplicated, block: B:75:0x0266  */
    /* JADX WARN: Code duplicated, block: B:87:0x02d8  */
    /* JADX WARN: Code duplicated, block: B:98:0x031c  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v17, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v2 */
    private Map<String, Object> a(String str, boolean z, boolean z2) throws Throwable {
        ?? r2;
        BufferedInputStream bufferedInputStream;
        HashMap map = new HashMap();
        for (int i = 0; i < 2; i++) {
            map.clear();
            BufferedInputStream bufferedInputStream2 = null;
            try {
                if (z) {
                    try {
                        CharSequence charSequenceD = d();
                        String str2 = charSequenceD.length() > 0 ? String.valueOf(str) + '?' + ((Object) charSequenceD) : str;
                        LogUtil.d("Get URL = " + str2);
                        this.c = (HttpURLConnection) new URL(str2).openConnection();
                    } catch (ConnectException e) {
                        e = e;
                        bufferedInputStream = null;
                        map.put("errno", "1");
                        map.put("content", "ConnectException");
                        LogUtil.e("ConnectException:" + str + e.getMessage());
                        if (!z2) {
                            p.a(bufferedInputStream);
                            a();
                        }
                        return map;
                    } catch (SocketTimeoutException e2) {
                        e = e2;
                        try {
                            r2 = "SocketTimeoutException:" + str;
                            LogUtil.d(r2, e);
                            if (i < 1) {
                                map.put("errno", "1");
                                map.put("content", "SocketTimeoutException");
                                if (z2) {
                                    p.a(bufferedInputStream2);
                                    a();
                                    break;
                                }
                                break;
                                return map;
                            }
                            if (!z2) {
                                p.a(bufferedInputStream2);
                                a();
                            }
                        } catch (Throwable th) {
                            th = th;
                            r2 = bufferedInputStream2;
                            if (!z2) {
                                p.a((Closeable) r2);
                                a();
                            }
                            throw th;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        bufferedInputStream = null;
                        map.put("errno", "1");
                        map.put("content", e.getClass().getName());
                        LogUtil.e("Error: " + e.getMessage());
                        if (!z2) {
                            p.a(bufferedInputStream);
                            a();
                        }
                        return map;
                    } catch (OutOfMemoryError e4) {
                        e = e4;
                        bufferedInputStream = null;
                        System.gc();
                        map.put("errno", "1");
                        map.put("content", "OutOfMemoryError");
                        LogUtil.e("Error: ", e);
                        if (!z2) {
                            p.a(bufferedInputStream);
                            a();
                        }
                        return map;
                    } catch (Throwable th2) {
                        th = th2;
                        r2 = 0;
                        if (!z2) {
                            p.a((Closeable) r2);
                            a();
                        }
                        throw th;
                    }
                } else {
                    this.c = (HttpURLConnection) new URL(str).openConnection();
                }
                this.c.setConnectTimeout(Constants.ERRORCODE_UNKNOWN);
                this.c.setReadTimeout(20000);
                this.c.setInstanceFollowRedirects(true);
                this.c.setRequestProperty(HTTP.USER_AGENT, this.a);
                if (!this.d) {
                    this.c.setRequestProperty("Accept-Encoding", HTTP.IDENTITY_CODING);
                }
                this.c.setRequestProperty(AppConst.MODEL_NAME, y.h);
                a(this.c);
                if (!z) {
                    this.c.setRequestMethod(HttpPost.METHOD_NAME);
                    this.c.setUseCaches(false);
                    this.c.setDoOutput(true);
                    CharSequence charSequenceD2 = d();
                    if (charSequenceD2.length() > 0) {
                        DataOutputStream dataOutputStream = new DataOutputStream(this.c.getOutputStream());
                        dataOutputStream.writeBytes(charSequenceD2.toString());
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    }
                    LogUtil.d("Post URL = " + str + "?" + ((Object) charSequenceD2));
                }
                bufferedInputStream = new BufferedInputStream(this.c.getInputStream());
                try {
                    int responseCode = this.c.getResponseCode();
                    if (responseCode == 200) {
                        map.put("errno", PushConstants.PUSH_TYPE_NOTIFY);
                        if (this.e) {
                            StringBuilder sb = new StringBuilder();
                            for (Map.Entry<String, List<String>> entry : this.c.getHeaderFields().entrySet()) {
                                if ("set-cookie".equalsIgnoreCase(entry.getKey())) {
                                    Iterator<String> it = entry.getValue().iterator();
                                    while (it.hasNext()) {
                                        for (String str3 : it.next().split(";\\s*")) {
                                            String[] strArrSplit = str3.split("=");
                                            if (strArrSplit.length == 2) {
                                                String strTrim = strArrSplit[0].trim();
                                                if (strTrim.equals("user_id") || strTrim.equals("user_email") || strTrim.equals(Constants.FLAG_TOKEN)) {
                                                    sb.append(strTrim).append("=").append(strArrSplit[1].trim()).append(";");
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                            map.put(SM.COOKIE, sb.toString());
                        }
                        String headerField = this.c.getHeaderField(HTTP.CONTENT_LEN);
                        int i2 = headerField != null ? Integer.parseInt(headerField.trim()) : 0;
                        LogUtil.d("Content-Length = " + (i2 / WXMediaMessage.DESCRIPTION_LENGTH_LIMIT) + "KB");
                        map.put(HTTP.CONTENT_LEN, Integer.valueOf(i2));
                        if (z2) {
                            map.put("content", bufferedInputStream);
                        } else {
                            String str4 = Constants.MAIN_VERSION_TAG;
                            if (this.c.getHeaderField("e") != null) {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                byte[] bArr = new byte[WXMediaMessage.TITLE_LENGTH_LIMIT];
                                while (true) {
                                    int i3 = bufferedInputStream.read(bArr);
                                    if (i3 <= 0) {
                                        break;
                                    }
                                    byteArrayOutputStream.write(bArr, 0, i3);
                                }
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                if (this.f) {
                                    map.put("encrypt_data", byteArray);
                                }
                                str4 = new String(StreamHelper.dec1(byteArray), HTTP.UTF_8);
                                if (TextUtils.isEmpty(str4)) {
                                    LogUtil.d("解码库错误");
                                }
                            } else {
                                if (this.c.getHeaderField("e2") != null) {
                                    LogUtil.d("decrypt with e2");
                                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                                    byte[] bArr2 = new byte[WXMediaMessage.TITLE_LENGTH_LIMIT];
                                    while (true) {
                                        int i4 = bufferedInputStream.read(bArr2);
                                        if (i4 <= 0) {
                                            break;
                                        }
                                        byteArrayOutputStream2.write(bArr2, 0, i4);
                                    }
                                    byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
                                    byteArrayOutputStream2.reset();
                                    if (this.f) {
                                        map.put("encrypt_data", byteArray2);
                                    }
                                    GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(StreamHelper.dec2(byteArray2)));
                                    while (true) {
                                        int i5 = gZIPInputStream.read(bArr2);
                                        if (i5 <= 0) {
                                            break;
                                        }
                                        byteArrayOutputStream2.write(bArr2, 0, i5);
                                    }
                                    gZIPInputStream.close();
                                    String string = byteArrayOutputStream2.toString(HTTP.UTF_8);
                                    if (TextUtils.isEmpty(string)) {
                                        LogUtil.d("解码库错误");
                                    }
                                    map.put("content", string);
                                    LogUtil.d(String.valueOf(str) + ":CONTENT = " + string);
                                    if (!z2) {
                                        p.a(bufferedInputStream);
                                        a();
                                        break;
                                    }
                                    break;
                                }
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
                                while (true) {
                                    String line = bufferedReader.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    str4 = String.valueOf(str4) + line;
                                }
                            }
                            map.put("content", str4);
                            LogUtil.d("CONTENT = " + str4);
                        }
                    } else {
                        map.put("errno", "1");
                        map.put("content", "status code = " + responseCode);
                    }
                    if (!z2) {
                        p.a(bufferedInputStream);
                        a();
                        break;
                    }
                    break;
                } catch (OutOfMemoryError e5) {
                    e = e5;
                    System.gc();
                    map.put("errno", "1");
                    map.put("content", "OutOfMemoryError");
                    LogUtil.e("Error: ", e);
                    if (!z2) {
                        p.a(bufferedInputStream);
                        a();
                    }
                } catch (ConnectException e6) {
                    e = e6;
                    map.put("errno", "1");
                    map.put("content", "ConnectException");
                    LogUtil.e("ConnectException:" + str + e.getMessage());
                    if (!z2) {
                        p.a(bufferedInputStream);
                        a();
                    }
                } catch (SocketTimeoutException e7) {
                    e = e7;
                    bufferedInputStream2 = bufferedInputStream;
                    r2 = "SocketTimeoutException:" + str;
                    LogUtil.d(r2, e);
                    if (i < 1) {
                        map.put("errno", "1");
                        map.put("content", "SocketTimeoutException");
                        if (z2) {
                            break;
                        }
                        p.a(bufferedInputStream2);
                        a();
                        break;
                    }
                    if (!z2) {
                        p.a(bufferedInputStream2);
                        a();
                    }
                } catch (Exception e8) {
                    e = e8;
                    map.put("errno", "1");
                    map.put("content", e.getClass().getName());
                    LogUtil.e("Error: " + e.getMessage());
                    if (!z2) {
                        p.a(bufferedInputStream);
                        a();
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        return map;
    }

    public void a() {
        if (this.c != null) {
            this.c.disconnect();
            this.c = null;
        }
    }
}
