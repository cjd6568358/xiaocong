package com.baidu.uaq.agent.android.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.baidu.uaq.agent.android.logging.b;
import com.tencent.android.tpush.common.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: DeviceUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static final com.baidu.uaq.agent.android.logging.a LOG = b.bg();
    private static String cp = null;
    private static File ct = new File(Environment.getDataDirectory(), "uaq_installation");

    /* JADX INFO: compiled from: DeviceUtil.java */
    public enum a {
        UNKNOWN,
        SMALL,
        NORMAL,
        LARGE,
        XLARGE
    }

    public static a e(Context context) {
        int deviceSize = context.getResources().getConfiguration().screenLayout & 15;
        switch (deviceSize) {
            case 1:
                return a.SMALL;
            case 2:
                return a.NORMAL;
            case 3:
                return a.LARGE;
            default:
                if (deviceSize > 3) {
                    return a.XLARGE;
                }
                return a.UNKNOWN;
        }
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String f(Context context) {
        if (TextUtils.isEmpty(cp)) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
                if (tm != null) {
                    cp = tm.getDeviceId();
                }
                if (TextUtils.isEmpty(cp) || "000000000000000".equals(cp)) {
                    cp = g(context);
                }
                if (TextUtils.isEmpty(cp)) {
                    cp = "TS" + System.currentTimeMillis();
                }
            } catch (SecurityException e) {
                LOG.error("Neither user nor current process has android.permission.READ_PHONE_STATE.");
            } catch (Exception e2) {
                LOG.a("Caught error while getDeviceId: ", e2);
                com.baidu.uaq.agent.android.harvest.health.a.a(e2);
            }
        }
        return cp;
    }

    public static String g(Context context) throws Throwable {
        ct = new File(context.getFilesDir(), "uaq_installation");
        String uuid = bw();
        if (!TextUtils.isEmpty(uuid)) {
            O("UUIDRecovered");
            return uuid;
        }
        String uuid2 = UUID.randomUUID().toString();
        LOG.E("Created random UUID: " + uuid2);
        com.baidu.uaq.agent.android.stats.a.br().L("Mobile/App/Install");
        N(uuid2);
        return uuid2;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private static String bw() throws Throwable {
        Exception e;
        String uuid = Constants.MAIN_VERSION_TAG;
        if (ct.exists()) {
            BufferedReader in = null;
            try {
                try {
                    BufferedReader in2 = new BufferedReader(new FileReader(ct));
                    try {
                        String uuidJson = in2.readLine();
                        JSONObject jsonObject = new JSONObject(uuidJson);
                        uuid = jsonObject.getString("uaq_uuid");
                        if (in2 != null) {
                            try {
                                in2.close();
                            } catch (IOException e2) {
                                LOG.error(e2.getMessage());
                                com.baidu.uaq.agent.android.harvest.health.a.a(e2);
                            }
                        }
                    } catch (IOException e3) {
                        e = e3;
                        in = in2;
                        e = e;
                        LOG.error(e.getMessage());
                        com.baidu.uaq.agent.android.harvest.health.a.a(e);
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e4) {
                                LOG.error(e4.getMessage());
                                com.baidu.uaq.agent.android.harvest.health.a.a(e4);
                            }
                        }
                    } catch (NullPointerException e5) {
                        e = e5;
                        in = in2;
                        e = e;
                        LOG.error(e.getMessage());
                        com.baidu.uaq.agent.android.harvest.health.a.a(e);
                        if (in != null) {
                            in.close();
                        }
                    } catch (JSONException e6) {
                        e = e6;
                        in = in2;
                        e = e;
                        LOG.error(e.getMessage());
                        com.baidu.uaq.agent.android.harvest.health.a.a(e);
                        if (in != null) {
                            in.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        in = in2;
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e7) {
                                LOG.error(e7.getMessage());
                                com.baidu.uaq.agent.android.harvest.health.a.a(e7);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e8) {
                e = e8;
            } catch (NullPointerException e9) {
                e = e9;
            } catch (JSONException e10) {
                e = e10;
            }
        }
        return uuid;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x0048 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private static void N(String uuid) throws Throwable {
        Exception e;
        BufferedWriter out = null;
        try {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uaq_uuid", uuid);
                BufferedWriter out2 = new BufferedWriter(new FileWriter(ct));
                try {
                    out2.write(jsonObject.toString());
                    out2.flush();
                    if (out2 != null) {
                        try {
                            out2.close();
                            out = out2;
                        } catch (IOException e2) {
                            LOG.error(e2.getMessage());
                            com.baidu.uaq.agent.android.harvest.health.a.a(e2);
                            out = out2;
                        }
                    } else {
                        out = out2;
                    }
                } catch (IOException e3) {
                    e = e3;
                    out = out2;
                    e = e;
                    LOG.error(e.getMessage());
                    com.baidu.uaq.agent.android.harvest.health.a.a(e);
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e4) {
                            LOG.error(e4.getMessage());
                            com.baidu.uaq.agent.android.harvest.health.a.a(e4);
                        }
                    }
                } catch (JSONException e5) {
                    e = e5;
                    out = out2;
                    e = e;
                    LOG.error(e.getMessage());
                    com.baidu.uaq.agent.android.harvest.health.a.a(e);
                    if (out != null) {
                        out.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    out = out2;
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e6) {
                            LOG.error(e6.getMessage());
                            com.baidu.uaq.agent.android.harvest.health.a.a(e6);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e7) {
            e = e7;
        } catch (JSONException e8) {
            e = e8;
        }
    }

    private static void O(String tag) {
        com.baidu.uaq.agent.android.stats.a statsEngine = com.baidu.uaq.agent.android.stats.a.br();
        if (statsEngine != null) {
            statsEngine.L("Supportability/AgentHealth/" + tag);
        } else {
            LOG.error("StatsEngine is null. " + tag + "  not recorded.");
        }
    }
}
