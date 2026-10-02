package com.alibaba.sdk.android.utils;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Build;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.ut.device.UTDevice;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AMSDevReporter {
    private static Context a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static final ExecutorService f81a = Executors.newSingleThreadExecutor(new a());

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static ConcurrentHashMap<AMSSdkTypeEnum, AMSReportStatusEnum> f80a = new ConcurrentHashMap<>();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static boolean f82a = false;
    private static String TAG = "AMSDevReporter";

    public enum AMSReportStatusEnum {
        UNREPORTED,
        REPORTED
    }

    public enum AMSSdkTypeEnum {
        AMS_MAN("MAN"),
        AMS_HTTPDNS("HTTPDNS"),
        AMS_MPUSH("MPUSH"),
        AMS_MAC("MAC"),
        AMS_API("API"),
        AMS_HOTFIX("HOTFIX"),
        AMS_FEEDBACK("FEEDBACK"),
        AMS_IM("IM");

        private String description;

        AMSSdkTypeEnum(String str) {
            this.description = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.description;
        }
    }

    public enum AMSSdkExtInfoKeyEnum {
        AMS_EXTINFO_KEY_VERSION("SdkVersion"),
        AMS_EXTINFO_KEY_PACKAGE("PackageName");

        private String description;

        AMSSdkExtInfoKeyEnum(String str) {
            this.description = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.description;
        }
    }

    static {
        for (AMSSdkTypeEnum aMSSdkTypeEnum : AMSSdkTypeEnum.values()) {
            f80a.put(aMSSdkTypeEnum, AMSReportStatusEnum.UNREPORTED);
        }
    }

    public static void setLogEnabled(boolean z) {
        d.setLogEnabled(z);
    }

    public static AMSReportStatusEnum getReportStatus(AMSSdkTypeEnum aMSSdkTypeEnum) {
        return f80a.get(aMSSdkTypeEnum);
    }

    public static void asyncReport(Context context, AMSSdkTypeEnum aMSSdkTypeEnum) {
        asyncReport(context, aMSSdkTypeEnum, null);
    }

    public static void asyncReport(Context context, final AMSSdkTypeEnum aMSSdkTypeEnum, final Map<String, Object> map) {
        if (context == null) {
            d.c(TAG, "Context is null, return.");
            return;
        }
        a = context;
        d.b(TAG, "Add [" + aMSSdkTypeEnum.toString() + "] to report queue.");
        f82a = false;
        f81a.execute(new Runnable() { // from class: com.alibaba.sdk.android.utils.AMSDevReporter.1
            @Override // java.lang.Runnable
            public void run() {
                if (AMSDevReporter.f82a) {
                    d.c(AMSDevReporter.TAG, "Unable to execute remain task in queue, return.");
                } else {
                    d.b(AMSDevReporter.TAG, "Get [" + aMSSdkTypeEnum.toString() + "] from report queue.");
                    AMSDevReporter.a(aMSSdkTypeEnum, (Map<String, Object>) map);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AMSSdkTypeEnum aMSSdkTypeEnum, Map<String, Object> map) {
        int i = 0;
        int i2 = 5;
        String string = aMSSdkTypeEnum.toString();
        if (f80a.get(aMSSdkTypeEnum) != AMSReportStatusEnum.UNREPORTED) {
            d.b(TAG, "[" + string + "] already reported, return.");
            return;
        }
        while (true) {
            d.b(TAG, "Report [" + string + "], times: [" + (i + 1) + "].");
            if (!m50a(aMSSdkTypeEnum, map)) {
                i++;
                if (i <= 10) {
                    d.b(TAG, "Report [" + string + "] failed, wait for [" + i2 + "] seconds.");
                    e.a(i2);
                    i2 *= 2;
                    if (i2 >= 60) {
                        i2 = 60;
                    }
                } else {
                    d.c(TAG, "Report [" + string + "] stat failed, exceed max retry times, return.");
                    f80a.put(aMSSdkTypeEnum, AMSReportStatusEnum.UNREPORTED);
                    f82a = true;
                    break;
                }
            } else {
                d.b(TAG, "Report [" + string + "] stat success.");
                f80a.put(aMSSdkTypeEnum, AMSReportStatusEnum.REPORTED);
                break;
            }
        }
        if (f82a) {
            d.c(TAG, "Report [" + string + "] failed, clear remain report in queue.");
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x014f A[Catch: IOException -> 0x01da, TRY_LEAVE, TryCatch #2 {IOException -> 0x01da, blocks: (B:23:0x014a, B:25:0x014f), top: B:82:0x014a }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0197 A[Catch: IOException -> 0x019c, TRY_LEAVE, TryCatch #13 {IOException -> 0x019c, blocks: (B:34:0x0192, B:36:0x0197), top: B:90:0x0192 }] */
    /* JADX WARN: Code duplicated, block: B:47:0x01b5 A[Catch: IOException -> 0x01b9, TRY_LEAVE, TryCatch #0 {IOException -> 0x01b9, blocks: (B:45:0x01b0, B:47:0x01b5), top: B:80:0x01b0 }] */
    /* JADX WARN: Code duplicated, block: B:58:0x01e7  */
    /* JADX WARN: Code duplicated, block: B:61:0x01ef A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:62:0x01f1 A[Catch: IOException -> 0x01f5, TRY_LEAVE, TryCatch #6 {IOException -> 0x01f5, blocks: (B:60:0x01ec, B:62:0x01f1), top: B:86:0x01ec }] */
    /* JADX WARN: Code duplicated, block: B:86:0x01ec A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static boolean m50a(AMSSdkTypeEnum aMSSdkTypeEnum, Map<String, Object> map) throws Throwable {
        DataInputStream dataInputStream;
        HttpURLConnection httpURLConnection;
        OutputStream outputStream;
        OutputStream outputStream2 = null;
        dataInputStream = null;
        dataInputStream = null;
        outputStream2 = null;
        DataInputStream dataInputStream2 = null;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                TrafficStats.setThreadStatsTag(40965);
            }
            String utdid = UTDevice.getUtdid(a);
            d.b(TAG, "stat: " + utdid);
            String strA = a(aMSSdkTypeEnum, utdid, map);
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL("http://adash.man.aliyuncs.com:80/man/api?ak=23356390&s=" + e.b("16594f72217bece5a457b4803a48f2da" + e.a("23356390Raw" + e.a(strA)) + "16594f72217bece5a457b4803a48f2da")).openConnection();
            try {
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setUseCaches(false);
                httpURLConnection2.setConnectTimeout(15000);
                String str = "===" + System.currentTimeMillis() + "===";
                httpURLConnection2.setRequestProperty(HTTP.CONTENT_TYPE, "multipart/form-data; boundary=" + str);
                String str2 = "--" + str + "\r\nContent-Disposition: form-data; name=\"Raw\"\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\n" + strA + "\r\n--" + str + "--\r\n";
                OutputStream outputStream3 = httpURLConnection2.getOutputStream();
                try {
                    outputStream3.write(str2.getBytes());
                    int responseCode = httpURLConnection2.getResponseCode();
                    if (responseCode == 200) {
                        dataInputStream = new DataInputStream(httpURLConnection2.getInputStream());
                        try {
                            StringBuilder sb = new StringBuilder();
                            byte[] bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                            while (true) {
                                int i = dataInputStream.read(bArr);
                                if (i == -1) {
                                    break;
                                }
                                sb.append(new String(bArr, 0, i));
                            }
                            d.a(TAG, "Get MAN response: " + sb.toString());
                            try {
                                if (((String) new JSONObject(sb.toString()).get("success")).equals("success")) {
                                    if (httpURLConnection2 != null) {
                                        httpURLConnection2.disconnect();
                                    }
                                    if (outputStream3 != null) {
                                        try {
                                            outputStream3.close();
                                            if (dataInputStream != null) {
                                                dataInputStream.close();
                                            }
                                        } catch (IOException e) {
                                            d.a(TAG, e);
                                        }
                                    } else if (dataInputStream != null) {
                                        dataInputStream.close();
                                    }
                                    return true;
                                }
                            } catch (JSONException e2) {
                                d.a(TAG, e2);
                            }
                        } catch (Exception e3) {
                            dataInputStream2 = dataInputStream;
                            httpURLConnection = httpURLConnection2;
                            e = e3;
                            outputStream = outputStream3;
                            try {
                                d.a(TAG, e);
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                        if (dataInputStream2 != null) {
                                            dataInputStream2.close();
                                        }
                                    } catch (IOException e4) {
                                        d.a(TAG, e4);
                                    }
                                } else if (dataInputStream2 != null) {
                                    dataInputStream2.close();
                                }
                            } catch (Throwable th) {
                                th = th;
                                dataInputStream = dataInputStream2;
                                outputStream2 = outputStream;
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                                if (outputStream2 != null) {
                                    try {
                                        outputStream2.close();
                                        if (dataInputStream != null) {
                                            dataInputStream.close();
                                        }
                                    } catch (IOException e5) {
                                        d.a(TAG, e5);
                                        throw th;
                                    }
                                } else if (dataInputStream != null) {
                                    dataInputStream.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            outputStream2 = outputStream3;
                            httpURLConnection = httpURLConnection2;
                            th = th2;
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            if (outputStream2 != null) {
                                outputStream2.close();
                                if (dataInputStream != null) {
                                    dataInputStream.close();
                                }
                            } else if (dataInputStream != null) {
                                dataInputStream.close();
                            }
                            throw th;
                        }
                    } else {
                        d.c(TAG, "MAN API error, response code: " + responseCode);
                        dataInputStream = null;
                    }
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    if (outputStream3 != null) {
                        try {
                            outputStream3.close();
                            if (dataInputStream != null) {
                                dataInputStream.close();
                            }
                        } catch (IOException e6) {
                            d.a(TAG, e6);
                        }
                    } else if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                } catch (Exception e7) {
                    httpURLConnection = httpURLConnection2;
                    e = e7;
                    outputStream = outputStream3;
                } catch (Throwable th3) {
                    dataInputStream = null;
                    httpURLConnection = httpURLConnection2;
                    th = th3;
                    outputStream2 = outputStream3;
                }
            } catch (Exception e8) {
                httpURLConnection = httpURLConnection2;
                e = e8;
                outputStream = null;
            } catch (Throwable th4) {
                dataInputStream = null;
                httpURLConnection = httpURLConnection2;
                th = th4;
            }
        } catch (Exception e9) {
            e = e9;
            outputStream = null;
            httpURLConnection = null;
        } catch (Throwable th5) {
            th = th5;
            dataInputStream = null;
            httpURLConnection = null;
        }
        return false;
    }

    private static String a(AMSSdkTypeEnum aMSSdkTypeEnum, String str, Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(aMSSdkTypeEnum).append("-").append(str);
        if (map != null) {
            String str2 = (String) map.get(AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_VERSION.toString());
            if (!e.m57a(str2)) {
                sb.append("-").append(str2);
            }
            String str3 = (String) map.get(AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_PACKAGE.toString());
            if (!e.m57a(str3)) {
                sb.append("-").append(str3);
            }
        }
        return sb.toString();
    }
}
