package com.tencent.android.tpush.service;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.XGPush4Msdk;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGWatchdog {
    private static final String LIB_FULL_NAME = "libxguardian.so";
    private static final String LIB_NAME = "xguardian";
    public static final String TAG = "xguardian";
    private Context context;
    volatile boolean isStarted = false;
    private static String WatchdogPath = Constants.MAIN_VERSION_TAG;
    public static Integer CURRENT_WD_VERSION = 2;
    private static Random random = new Random();
    private static Handler handler = null;
    private static volatile XGWatchdog instance = null;
    private static int defaultWatchdogPort = 55550;
    private static final String watchdogPortName = com.tencent.android.tpush.encrypt.a.a("com.tencent.tpnsWatchdogPort");

    private XGWatchdog(Context context) {
        this.context = null;
        try {
            this.context = context.getApplicationContext();
            n.d(this.context);
            HandlerThread handlerThread = new HandlerThread("XGWatchdog.thread");
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("xguardian", "init XGWatchdog error", th);
        }
    }

    public static XGWatchdog getInstance(Context context) {
        if (instance == null) {
            synchronized (XGWatchdog.class) {
                if (instance == null) {
                    instance = new XGWatchdog(context);
                }
            }
        }
        return instance;
    }

    public static int getRandomInt(int i) {
        return random.nextInt(i);
    }

    public static int getRandomPort() {
        return getRandomInt(1000) + 55000;
    }

    public int getWatchdogPort() throws Throwable {
        ServerSocket serverSocket;
        int iA = com.tencent.android.tpush.service.e.h.a(this.context, "tpush_watchdog_port", 0);
        if (iA <= 0) {
            for (int i = 0; i < 10; i++) {
                try {
                    int randomPort = getRandomPort();
                    serverSocket = new ServerSocket(randomPort);
                    try {
                        try {
                            com.tencent.android.tpush.service.e.h.b(this.context, "tpush_watchdog_port", randomPort);
                            if (serverSocket != null) {
                                try {
                                    serverSocket.close();
                                    return randomPort;
                                } catch (Exception e) {
                                    return randomPort;
                                }
                            }
                            return randomPort;
                        } catch (Throwable th) {
                            th = th;
                            com.tencent.android.tpush.a.a.c("xguardian", "create ServerSocket error", th);
                            if (serverSocket != null) {
                                try {
                                    serverSocket.close();
                                } catch (Exception e2) {
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (serverSocket != null) {
                            try {
                                serverSocket.close();
                            } catch (Exception e3) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    serverSocket = null;
                }
            }
            return defaultWatchdogPort;
        }
        return iA;
    }

    public void sendHeartbeat2Watchdog(String str) {
        sendHeartbeat2Watchdog(str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:101:0x010b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:103:0x007e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:105:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:107:0x00a6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:109:0x00b5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:111:0x0083 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:113:0x0106 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:115:0x0088 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:125:0x0090 A[EDGE_INSN: B:125:0x0090->B:31:0x0090 BREAK  A[LOOP:0: B:16:0x0066->B:18:0x0071], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:129:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:18:0x0071 A[Catch: Throwable -> 0x0076, all -> 0x0175, LOOP:0: B:16:0x0066->B:18:0x0071, LOOP_END, TRY_LEAVE, TryCatch #17 {all -> 0x0175, Throwable -> 0x0076, blocks: (B:15:0x0064, B:16:0x0066, B:18:0x0071, B:31:0x0090, B:33:0x0097), top: B:123:0x0064 }] */
    /* JADX WARN: Code duplicated, block: B:30:0x008d A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:33:0x0097 A[Catch: Throwable -> 0x0076, all -> 0x0175, TRY_LEAVE, TryCatch #17 {all -> 0x0175, Throwable -> 0x0076, blocks: (B:15:0x0064, B:16:0x0066, B:18:0x0071, B:31:0x0090, B:33:0x0097), top: B:123:0x0064 }] */
    /* JADX WARN: Code duplicated, block: B:62:0x0133  */
    /* JADX WARN: Code duplicated, block: B:91:0x018b  */
    /* JADX WARN: Code duplicated, block: B:92:0x018e  */
    /* JADX WARN: Code duplicated, block: B:93:0x00b0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:95:0x0110 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:97:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:99:0x0101 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public String directSendContent(String str) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        DataOutputStream dataOutputStream;
        Socket socket;
        ByteArrayOutputStream byteArrayOutputStream2;
        DataOutputStream dataOutputStream2;
        Socket socket2;
        byte[] bArr;
        int i;
        String str2;
        String str3 = null;
        if (com.tencent.android.tpush.service.a.a.a(n.f()).y == 0 || !com.tencent.android.tpush.common.t.h(this.context)) {
            return null;
        }
        BufferedReader bufferedReader = null;
        try {
            socket2 = new Socket("127.0.0.1", getWatchdogPort());
            try {
                socket2.setSoTimeout(BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN);
                new DataInputStream(socket2.getInputStream());
                dataOutputStream2 = new DataOutputStream(socket2.getOutputStream());
                if (str == null) {
                    try {
                        str = "xgapplist:" + getLocalXGApps();
                        dataOutputStream2.write(TpnsSecurity.oiSymmetryEncrypt2Byte(str));
                        dataOutputStream2.flush();
                        byteArrayOutputStream2 = new ByteArrayOutputStream();
                        try {
                            bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                            while (true) {
                                i = socket2.getInputStream().read(bArr);
                                if (i != -1) {
                                    break;
                                }
                                byteArrayOutputStream2.write(bArr, 0, i);
                            }
                            if (byteArrayOutputStream2.toByteArray().length > 0) {
                                str2 = new String(TpnsSecurity.oiSymmetryDecrypt2Byte(byteArrayOutputStream2.toByteArray()));
                            } else {
                                str2 = null;
                            }
                            if (socket2 != null) {
                                try {
                                    socket2.close();
                                } catch (Exception e) {
                                    com.tencent.android.tpush.a.a.i("xguardian", "close socket failed " + e.getMessage());
                                }
                            }
                            if (0 != 0) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception e2) {
                                }
                            }
                            if (byteArrayOutputStream2 != null) {
                                try {
                                    byteArrayOutputStream2.close();
                                } catch (IOException e3) {
                                }
                            }
                            if (dataOutputStream2 != null) {
                                try {
                                    dataOutputStream2.close();
                                    str3 = str2;
                                } catch (Exception e4) {
                                    str3 = str2;
                                }
                            } else {
                                str3 = str2;
                            }
                        } catch (Throwable th) {
                            if (socket2 != null) {
                                try {
                                    socket2.close();
                                } catch (Exception e5) {
                                    com.tencent.android.tpush.a.a.i("xguardian", "close socket failed " + e5.getMessage());
                                }
                            }
                            if (0 != 0) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception e6) {
                                }
                            }
                            if (byteArrayOutputStream2 != null) {
                                try {
                                    byteArrayOutputStream2.close();
                                } catch (IOException e7) {
                                }
                            }
                            if (dataOutputStream2 != null) {
                                try {
                                    dataOutputStream2.close();
                                } catch (Exception e8) {
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        socket = socket2;
                        dataOutputStream = dataOutputStream2;
                        byteArrayOutputStream = null;
                        if (socket != null) {
                            socket.close();
                        }
                        if (0 != 0) {
                            bufferedReader.close();
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        if (dataOutputStream != null) {
                            throw th;
                        }
                        dataOutputStream.close();
                        throw th;
                    }
                } else {
                    dataOutputStream2.write(TpnsSecurity.oiSymmetryEncrypt2Byte(str));
                    dataOutputStream2.flush();
                    byteArrayOutputStream2 = new ByteArrayOutputStream();
                    bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                    while (true) {
                        i = socket2.getInputStream().read(bArr);
                        if (i != -1) {
                            break;
                            break;
                        }
                        byteArrayOutputStream2.write(bArr, 0, i);
                    }
                    if (byteArrayOutputStream2.toByteArray().length > 0) {
                        str2 = new String(TpnsSecurity.oiSymmetryDecrypt2Byte(byteArrayOutputStream2.toByteArray()));
                    } else {
                        str2 = null;
                    }
                    if (socket2 != null) {
                        socket2.close();
                    }
                    if (0 != 0) {
                        bufferedReader.close();
                    }
                    if (byteArrayOutputStream2 != null) {
                        byteArrayOutputStream2.close();
                    }
                    if (dataOutputStream2 != null) {
                        dataOutputStream2.close();
                        str3 = str2;
                    } else {
                        str3 = str2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                byteArrayOutputStream = null;
                socket = socket2;
                dataOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            byteArrayOutputStream = null;
            dataOutputStream = null;
            socket = null;
        }
        if (str3 == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        return str3.replace("|", Constants.MAIN_VERSION_TAG).replace("/", Constants.MAIN_VERSION_TAG).replace("&", Constants.MAIN_VERSION_TAG).replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, Constants.MAIN_VERSION_TAG);
    }

    public void sendHeartbeat2Watchdog(String str, ad adVar) {
        if (handler != null) {
            handler.post(new ab(this, str, adVar));
        }
    }

    public void sendXGApp(String str, long j) {
        sendHeartbeat2Watchdog(str + "," + j + ";");
    }

    public void sendAllLocalXGAppList() {
        sendHeartbeat2Watchdog(null);
    }

    public void sendDebugMode(boolean z) {
        sendHeartbeat2Watchdog("debug:" + (z ? "1" : PushConstants.PUSH_TYPE_NOTIFY));
    }

    public String getLocalXGApps() {
        RegisterEntity registerInfoByPkgName;
        if (n.f() == null) {
            n.d(this.context);
        }
        List listD = com.tencent.android.tpush.service.e.m.d(this.context);
        ArrayList<ae> arrayList = new ArrayList(10);
        if (listD != null) {
            Iterator it = listD.iterator();
            while (it.hasNext()) {
                String str = ((ResolveInfo) it.next()).activityInfo.packageName;
                if (!com.tencent.android.tpush.service.e.m.b(str) && (registerInfoByPkgName = CacheManager.getRegisterInfoByPkgName(str)) != null) {
                    ae aeVar = new ae();
                    aeVar.a = str;
                    aeVar.c = registerInfoByPkgName.accessId;
                    aeVar.b = registerInfoByPkgName.xgSDKVersion;
                    arrayList.add(aeVar);
                }
            }
        }
        Collections.sort(arrayList);
        long accessId = XGPushConfig.getAccessId(this.context);
        if (accessId <= 0) {
            accessId = XGPush4Msdk.getQQAccessId(this.context);
        }
        ae aeVar2 = new ae();
        aeVar2.a = this.context.getPackageName();
        aeVar2.c = accessId;
        aeVar2.b = 3.24f;
        arrayList.add(0, aeVar2);
        StringBuilder sb = new StringBuilder(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
        for (ae aeVar3 : arrayList) {
            sb.append(aeVar3.a).append(",").append(aeVar3.c).append(";");
        }
        return sb.toString();
    }

    private String domainToIp() {
        try {
            return InetAddress.getByName(Constants.UNSTALL_DOMAIN).getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "14.18.245.161";
        }
    }

    private boolean oldloadWatchdog(String str) {
        if (!com.tencent.android.tpush.service.e.m.b(WatchdogPath)) {
            return true;
        }
        WatchdogPath = Constants.MAIN_VERSION_TAG;
        try {
            File file = new File(new StringBuffer(this.context.getFilesDir().getParentFile().getAbsolutePath()).append(File.separator).append("lib").append(File.separator).append(LIB_FULL_NAME).toString());
            boolean zExists = file.exists();
            if (zExists) {
                WatchdogPath = file.getAbsolutePath();
                return zExists;
            }
            return zExists;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.i("xguardian", "jniStartWatchdog loadWatchdog error:" + e.getMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void directStartWatchdog() {
        if (com.tencent.android.tpush.service.a.a.a(n.f()).y != 0 && com.tencent.android.tpush.common.t.h(this.context)) {
            try {
                if (loadWatchdog(this.context.getPackageName())) {
                    if (!com.tencent.android.tpush.common.t.h(this.context)) {
                        com.tencent.android.tpush.a.a.i("xguardian", "xg is disable.");
                        return;
                    }
                    int watchdogPort = getWatchdogPort();
                    List<RegisterEntity> registerInfo = CacheManager.getRegisterInfo(this.context);
                    StringBuffer stringBuffer = new StringBuffer();
                    StringBuffer stringBuffer2 = new StringBuffer();
                    StringBuffer stringBuffer3 = new StringBuffer();
                    for (RegisterEntity registerEntity : registerInfo) {
                        stringBuffer.append(registerEntity.accessId).append(",");
                        stringBuffer2.append(registerEntity.accessKey).append(",");
                        stringBuffer3.append(registerEntity.packageName).append(",");
                    }
                    String[] strArr = new String[7];
                    strArr[0] = WatchdogPath;
                    strArr[1] = getLocalXGApps();
                    strArr[2] = String.valueOf(watchdogPort);
                    strArr[3] = domainToIp();
                    strArr[4] = new com.tencent.android.tpush.e.a(this.context).a();
                    strArr[5] = Constants.MAIN_VERSION_TAG + (XGPushConfig.isEnableDebug(this.context) ? "1" : PushConstants.PUSH_TYPE_NOTIFY);
                    strArr[6] = Constants.MAIN_VERSION_TAG + Build.VERSION.SDK_INT;
                    com.tencent.android.tpush.a.a.c("xguardian", "exec " + strArr);
                    Process processExec = Runtime.getRuntime().exec(strArr);
                    af afVar = new af(this, processExec.getErrorStream(), "Error");
                    af afVar2 = new af(this, processExec.getInputStream(), "Output");
                    afVar.start();
                    afVar2.start();
                    com.tencent.android.tpush.a.a.c("xguardian", "proc.exitValue = " + processExec.waitFor());
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("xguardian", "directStartWatchdog", th);
            }
        }
    }

    public void startWatchdog() {
        if (handler != null) {
            handler.post(new ac(this));
        }
    }

    private boolean loadWatchdog(String str) {
        boolean zExists;
        if (!com.tencent.android.tpush.service.e.m.b(WatchdogPath)) {
            return true;
        }
        WatchdogPath = Constants.MAIN_VERSION_TAG;
        try {
            File file = new File(new StringBuffer(File.separator).append("data").append(File.separator).append("data").append(File.separator).append(str).append(File.separator).append("lib").append(File.separator).append(LIB_FULL_NAME).toString());
            zExists = file.exists();
            if (zExists) {
                WatchdogPath = file.getAbsolutePath();
                boolean zIsInstalledOnSdCard = isInstalledOnSdCard(this.context);
                com.tencent.android.tpush.a.a.e("xguardian", "Application is install in SD Card: " + zIsInstalledOnSdCard);
                if (zIsInstalledOnSdCard) {
                    String string = new StringBuffer(this.context.getDir("watchdog", 0).getAbsolutePath()).append(File.separator).append(LIB_FULL_NAME).toString();
                    File file2 = new File(string);
                    if (file2.exists()) {
                        com.tencent.android.tpush.a.a.e("xguardian", "exeWatchDog exists!");
                    } else {
                        try {
                            file2.createNewFile();
                            if (!com.tencent.android.tpush.common.t.a(file, file2)) {
                                file2.delete();
                                return false;
                            }
                            if (Build.VERSION.SDK_INT >= 9) {
                                file2.getClass().getMethod("setExecutable", Boolean.TYPE).invoke(file2, true);
                            } else {
                                String str2 = "chmod 700 " + string;
                                com.tencent.android.tpush.a.a.e("xguardian", " exec command: " + str2 + ",  exit:" + Runtime.getRuntime().exec(str2).waitFor());
                            }
                        } catch (IOException e) {
                            file2.delete();
                            com.tencent.android.tpush.a.a.i("xguardian", "exeWatchDog create error!");
                            return false;
                        }
                    }
                    WatchdogPath = string;
                }
            }
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.i("xguardian", "jniStartWatchdog loadWatchdog error:" + e2.getMessage());
            zExists = false;
        }
        return zExists;
    }

    private static boolean isInstalledOnSdCard(Context context) {
        if (Build.VERSION.SDK_INT >= 8) {
            try {
                return (context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.flags & 262144) == 262144;
            } catch (PackageManager.NameNotFoundException e) {
                com.tencent.android.tpush.a.a.i("xguardian", "check install location err, maybe api level < 8");
            }
        }
        try {
            String absolutePath = context.getFilesDir().getAbsolutePath();
            if (absolutePath.startsWith("/data/")) {
                return false;
            }
            if (absolutePath.contains("/mnt/") || absolutePath.contains("/sdcard/")) {
                return true;
            }
        } catch (Throwable th) {
        }
        return false;
    }
}
