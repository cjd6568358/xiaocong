package com.alibaba.sdk.android.utils.crashdefend;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CrashDefendUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    public static void a(Context context, b bVar, List<d> list) {
        if (context == null) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                JSONObject jSONObject = new JSONObject();
                if (bVar != null) {
                    jSONObject.put("startSerialNumber", bVar.a);
                }
                synchronized (list) {
                    if (list != null) {
                        try {
                            JSONArray jSONArray = new JSONArray();
                            for (d dVar : list) {
                                if (dVar != null) {
                                    JSONObject jSONObject2 = new JSONObject();
                                    jSONObject2.put("sdkId", dVar.f99a);
                                    jSONObject2.put("sdkVersion", dVar.f101b);
                                    jSONObject2.put("crashLimit", dVar.a);
                                    jSONObject2.put("crashCount", dVar.crashCount);
                                    jSONObject2.put("waitTime", dVar.b);
                                    jSONObject2.put("beaconStatus", dVar.c);
                                    jSONObject2.put("registerSerialNumber", dVar.f100b);
                                    jSONObject2.put("startSerialNumber", dVar.f97a);
                                    jSONObject2.put("restoreSerialNumber", dVar.f102c);
                                    jSONObject2.put("restoreCount", dVar.d);
                                    jSONArray.put(jSONObject2);
                                }
                            }
                            jSONObject.put("sdkList", jSONArray);
                        } catch (JSONException e) {
                            Log.e("CrashUtils", "save sdk json fail:", e);
                        }
                    }
                }
                String string = jSONObject.toString();
                FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput("com_alibaba_aliyun_crash_defend_sdk_info", 0);
                fileOutputStreamOpenFileOutput.write(string.getBytes());
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException e2) {
                        Log.e("CrashUtils", "save sdk io fail:", e2);
                    }
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3) {
                        Log.e("CrashUtils", "save sdk io fail:", e3);
                    }
                }
                throw th;
            }
        } catch (IOException e4) {
            Log.e("CrashUtils", "save sdk io fail:", e4);
            if (0 != 0) {
                try {
                    fileOutputStream.close();
                } catch (IOException e5) {
                    Log.e("CrashUtils", "save sdk io fail:", e5);
                }
            }
        } catch (Exception e6) {
            Log.e("CrashUtils", "save sdk exception:", e6);
            if (0 != 0) {
                try {
                    fileOutputStream.close();
                } catch (IOException e7) {
                    Log.e("CrashUtils", "save sdk io fail:", e7);
                }
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static boolean m56a(Context context, b bVar, List<d> list) {
        if (context == null) {
            return false;
        }
        FileInputStream fileInputStreamOpenFileInput = null;
        StringBuilder sb = new StringBuilder();
        try {
            try {
                fileInputStreamOpenFileInput = context.openFileInput("com_alibaba_aliyun_crash_defend_sdk_info");
                byte[] bArr = new byte[WXMediaMessage.TITLE_LENGTH_LIMIT];
                while (true) {
                    int i = fileInputStreamOpenFileInput.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    sb.append(new String(bArr, 0, i));
                }
                if (fileInputStreamOpenFileInput != null) {
                    try {
                        fileInputStreamOpenFileInput.close();
                    } catch (IOException e) {
                        Log.e("CrashUtils", "load sdk io fail:", e);
                    }
                }
            } catch (Throwable th) {
                if (fileInputStreamOpenFileInput != null) {
                    try {
                        fileInputStreamOpenFileInput.close();
                    } catch (IOException e2) {
                        Log.e("CrashUtils", "load sdk io fail:", e2);
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e3) {
            Log.e("CrashUtils", "load sdk file fail:", e3);
            if (fileInputStreamOpenFileInput != null) {
                try {
                    fileInputStreamOpenFileInput.close();
                } catch (IOException e4) {
                    Log.e("CrashUtils", "load sdk io fail:", e4);
                }
            }
        } catch (IOException e5) {
            Log.e("CrashUtils", "load sdk io fail:", e5);
            if (fileInputStreamOpenFileInput != null) {
                try {
                    fileInputStreamOpenFileInput.close();
                } catch (IOException e6) {
                    Log.e("CrashUtils", "load sdk io fail:", e6);
                }
            }
        } catch (Exception e7) {
            Log.e("CrashUtils", "load sdk exception:", e7);
            if (fileInputStreamOpenFileInput != null) {
                try {
                    fileInputStreamOpenFileInput.close();
                } catch (IOException e8) {
                    Log.e("CrashUtils", "load sdk io fail:", e8);
                }
            }
        }
        if (sb.length() == 0) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(sb.toString());
            bVar.a = jSONObject.optLong("startSerialNumber", 1L);
            JSONArray jSONArray = jSONObject.getJSONArray("sdkList");
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                if (jSONObject2 != null) {
                    d dVar = new d();
                    dVar.f99a = jSONObject2.optString("sdkId", Constants.MAIN_VERSION_TAG);
                    dVar.f101b = jSONObject2.optString("sdkVersion", Constants.MAIN_VERSION_TAG);
                    dVar.a = jSONObject2.optInt("crashLimit", -1);
                    dVar.crashCount = jSONObject2.optInt("crashCount", 0);
                    dVar.b = jSONObject2.optInt("waitTime", 0);
                    dVar.c = jSONObject2.optInt("beaconStatus", 0);
                    dVar.f100b = jSONObject2.optLong("registerSerialNumber", 0L);
                    dVar.f97a = jSONObject2.optLong("startSerialNumber", 0L);
                    dVar.f102c = jSONObject2.optLong("restoreSerialNumber", 0L);
                    dVar.d = jSONObject2.optInt("restoreCount", 0);
                    if (TextUtils.isEmpty(dVar.f99a)) {
                        continue;
                    } else {
                        list.add(dVar);
                    }
                }
            }
        } catch (JSONException e9) {
            Log.e("CrashUtils", "load sdk json fail:", e9);
        } catch (Exception e10) {
            Log.e("CrashUtils", "load sdk exception:", e10);
        }
        return true;
    }

    public static boolean a(Context context) {
        String str;
        int iMyPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (true) {
            if (!it.hasNext()) {
                str = null;
                break;
            }
            ActivityManager.RunningAppProcessInfo next = it.next();
            if (next.pid == iMyPid) {
                str = next.processName;
                break;
            }
        }
        return context.getPackageName().equalsIgnoreCase(str);
    }
}
