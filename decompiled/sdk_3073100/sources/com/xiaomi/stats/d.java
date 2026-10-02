package com.xiaomi.stats;

import com.xiaomi.smack.l;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class d {

    static class a {
        com.xiaomi.push.thrift.a a;
        String b;

        a() {
        }
    }

    static a a(Exception exc) {
        e(exc);
        boolean z = exc instanceof l;
        Throwable thA = exc;
        if (z && ((l) exc).a() != null) {
            thA = exc;
            thA = ((l) exc).a();
        }
        thA = exc;
        a aVar = new a();
        String message = thA.getMessage();
        if (thA.getCause() != null) {
            message = thA.getCause().getMessage();
        }
        String str = thA.getClass().getSimpleName() + ":" + message;
        int iA = com.xiaomi.smack.c.a(thA);
        if (iA != 0) {
            aVar.a = com.xiaomi.push.thrift.a.a(iA + com.xiaomi.push.thrift.a.GSLB_REQUEST_SUCCESS.a());
        }
        if (aVar.a == null) {
            aVar.a = com.xiaomi.push.thrift.a.GSLB_TCP_ERR_OTHER;
        }
        if (aVar.a == com.xiaomi.push.thrift.a.GSLB_TCP_ERR_OTHER) {
            aVar.b = str;
        }
        return aVar;
    }

    static a b(Exception exc) {
        Throwable cause;
        e(exc);
        boolean z = exc instanceof l;
        Throwable thA = exc;
        if (z && ((l) exc).a() != null) {
            thA = exc;
            thA = ((l) exc).a();
        }
        thA = exc;
        a aVar = new a();
        String message = thA.getMessage();
        if (thA.getCause() != null) {
            message = thA.getCause().getMessage();
        }
        int iA = com.xiaomi.smack.c.a(thA);
        String str = thA.getClass().getSimpleName() + ":" + message;
        if (iA != 0) {
            aVar.a = com.xiaomi.push.thrift.a.a(iA + com.xiaomi.push.thrift.a.CONN_SUCCESS.a());
            if (aVar.a == com.xiaomi.push.thrift.a.CONN_BOSH_ERR && (cause = thA.getCause()) != null && (cause instanceof UnknownHostException)) {
                aVar.a = com.xiaomi.push.thrift.a.CONN_BOSH_UNKNOWNHOST;
            }
        } else {
            aVar.a = com.xiaomi.push.thrift.a.CONN_XMPP_ERR;
        }
        if (aVar.a == com.xiaomi.push.thrift.a.CONN_TCP_ERR_OTHER || aVar.a == com.xiaomi.push.thrift.a.CONN_XMPP_ERR || aVar.a == com.xiaomi.push.thrift.a.CONN_BOSH_ERR) {
            aVar.b = str;
        }
        return aVar;
    }

    static a c(Exception exc) {
        e(exc);
        boolean z = exc instanceof l;
        Throwable thA = exc;
        if (z && ((l) exc).a() != null) {
            thA = exc;
            thA = ((l) exc).a();
        }
        thA = exc;
        a aVar = new a();
        String message = thA.getMessage();
        if (thA.getCause() != null) {
            message = thA.getCause().getMessage();
        }
        int iA = com.xiaomi.smack.c.a(thA);
        String str = thA.getClass().getSimpleName() + ":" + message;
        switch (iA) {
            case 105:
                aVar.a = com.xiaomi.push.thrift.a.BIND_TCP_READ_TIMEOUT;
                break;
            case 109:
                aVar.a = com.xiaomi.push.thrift.a.BIND_TCP_CONNRESET;
                break;
            case 110:
                aVar.a = com.xiaomi.push.thrift.a.BIND_TCP_BROKEN_PIPE;
                break;
            case 199:
                aVar.a = com.xiaomi.push.thrift.a.BIND_TCP_ERR;
                break;
            case 499:
                aVar.a = com.xiaomi.push.thrift.a.BIND_BOSH_ERR;
                if (message.startsWith("Terminal binding condition encountered: item-not-found")) {
                    aVar.a = com.xiaomi.push.thrift.a.BIND_BOSH_ITEM_NOT_FOUND;
                }
                break;
            default:
                aVar.a = com.xiaomi.push.thrift.a.BIND_XMPP_ERR;
                break;
        }
        if (aVar.a == com.xiaomi.push.thrift.a.BIND_TCP_ERR || aVar.a == com.xiaomi.push.thrift.a.BIND_XMPP_ERR || aVar.a == com.xiaomi.push.thrift.a.BIND_BOSH_ERR) {
            aVar.b = str;
        }
        return aVar;
    }

    static a d(Exception exc) {
        e(exc);
        boolean z = exc instanceof l;
        Throwable thA = exc;
        if (z && ((l) exc).a() != null) {
            thA = exc;
            thA = ((l) exc).a();
        }
        thA = exc;
        a aVar = new a();
        String message = thA.getMessage();
        int iA = com.xiaomi.smack.c.a(thA);
        String str = thA.getClass().getSimpleName() + ":" + message;
        switch (iA) {
            case 105:
                aVar.a = com.xiaomi.push.thrift.a.CHANNEL_TCP_READTIMEOUT;
                break;
            case 109:
                aVar.a = com.xiaomi.push.thrift.a.CHANNEL_TCP_CONNRESET;
                break;
            case 110:
                aVar.a = com.xiaomi.push.thrift.a.CHANNEL_TCP_BROKEN_PIPE;
                break;
            case 199:
                aVar.a = com.xiaomi.push.thrift.a.CHANNEL_TCP_ERR;
                break;
            case 499:
                aVar.a = com.xiaomi.push.thrift.a.CHANNEL_BOSH_EXCEPTION;
                if (message.startsWith("Terminal binding condition encountered: item-not-found")) {
                    aVar.a = com.xiaomi.push.thrift.a.CHANNEL_BOSH_ITEMNOTFIND;
                }
                break;
            default:
                aVar.a = com.xiaomi.push.thrift.a.CHANNEL_XMPPEXCEPTION;
                break;
        }
        if (aVar.a == com.xiaomi.push.thrift.a.CHANNEL_TCP_ERR || aVar.a == com.xiaomi.push.thrift.a.CHANNEL_XMPPEXCEPTION || aVar.a == com.xiaomi.push.thrift.a.CHANNEL_BOSH_EXCEPTION) {
            aVar.b = str;
        }
        return aVar;
    }

    private static void e(Exception exc) {
        if (exc == null) {
            throw new NullPointerException();
        }
    }
}
