package com.xiaomi.smack;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c {
    public static int a(Throwable th) {
        Throwable thA = (!(th instanceof l) || ((l) th).a() == null) ? th : ((l) th).a();
        String message = thA.getMessage();
        if (thA.getCause() != null) {
            message = thA.getCause().getMessage();
        }
        if (thA instanceof SocketTimeoutException) {
            return 105;
        }
        if (!(thA instanceof SocketException)) {
            if (thA instanceof UnknownHostException) {
                return 107;
            }
            return th instanceof l ? 399 : 0;
        }
        if (message.indexOf("Network is unreachable") != -1) {
            return 102;
        }
        if (message.indexOf("Connection refused") != -1) {
            return 103;
        }
        if (message.indexOf("Connection timed out") != -1) {
            return 105;
        }
        if (message.endsWith("EACCES (Permission denied)")) {
            return 101;
        }
        if (message.indexOf("Connection reset by peer") != -1) {
            return 109;
        }
        if (message.indexOf("Broken pipe") != -1) {
            return 110;
        }
        if (message.indexOf("No route to host") != -1) {
            return 104;
        }
        return message.endsWith("EINVAL (Invalid argument)") ? 106 : 199;
    }
}
