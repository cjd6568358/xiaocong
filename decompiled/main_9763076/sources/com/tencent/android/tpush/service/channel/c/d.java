package com.tencent.android.tpush.service.channel.c;

import android.util.SparseArray;
import bsh.ParserConstants;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.service.channel.exception.CommandMappingException;
import com.tencent.android.tpush.service.channel.protocol.TpnsClientReportReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsClientReportRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsConfigReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsConfigRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsGetApListReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsGetApListRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushClickReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushClickRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushClientReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushCommReportReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushCommReportRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushVerifyReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushVerifyRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsReconnectReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsReconnectRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsRedirectReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsRedirectRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsRegisterReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsRegisterRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsTokenTagReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsTokenTagRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsUnregisterReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsUnregisterRsp;
import com.tencent.android.tpush.service.channel.protocol.TpnsUpdateTokenReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsUpdateTokenRsp;
import java.util.HashMap;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    public static final Integer a = 0;
    public static final Integer b = Integer.valueOf(ParserConstants.LSHIFTASSIGN);
    public static final SparseArray c = new SparseArray();
    public static final HashMap d = new HashMap();

    static {
        a(a, (byte) 1, TpnsPushClientReq.class);
        a(a, (byte) 2, TpnsGetApListReq.class);
        a(b, (byte) 2, TpnsGetApListRsp.class);
        a(a, (byte) 3, TpnsConfigReq.class);
        a(b, (byte) 3, TpnsConfigRsp.class);
        a(a, (byte) 4, TpnsRegisterReq.class);
        a(b, (byte) 4, TpnsRegisterRsp.class);
        a(a, (byte) 5, TpnsUnregisterReq.class);
        a(b, (byte) 5, TpnsUnregisterRsp.class);
        a(a, (byte) 6, TpnsReconnectReq.class);
        a(b, (byte) 6, TpnsReconnectRsp.class);
        a(a, (byte) 9, TpnsClientReportReq.class);
        a(b, (byte) 9, TpnsClientReportRsp.class);
        a(a, (byte) 10, TpnsRedirectReq.class);
        a(b, (byte) 10, TpnsRedirectRsp.class);
        a(a, Byte.valueOf(JceStruct.STRUCT_END), TpnsPushVerifyReq.class);
        a(b, Byte.valueOf(JceStruct.STRUCT_END), TpnsPushVerifyRsp.class);
        a(a, (byte) 15, TpnsTokenTagReq.class);
        a(b, (byte) 15, TpnsTokenTagRsp.class);
        a(a, (byte) 16, TpnsPushClickReq.class);
        a(b, (byte) 16, TpnsPushClickRsp.class);
        a(a, (byte) 17, TpnsUpdateTokenReq.class);
        a(b, (byte) 17, TpnsUpdateTokenRsp.class);
        a(a, (byte) 18, TpnsPushCommReportReq.class);
        a(b, (byte) 18, TpnsPushCommReportRsp.class);
    }

    public static void a(Integer num, Byte b2, Class cls) {
        c.put(num.intValue() | b2.byteValue(), cls);
        d.put(cls, Integer.valueOf(num.intValue() | b2.byteValue()));
    }

    public static short a(Class cls) {
        return ((Integer) d.get(cls)).shortValue();
    }

    public static Class a(short s) {
        return (Class) c.get(s);
    }

    public static JceStruct a(short s, byte[] bArr) throws CommandMappingException {
        Class clsA = a(s);
        if (clsA != null && bArr != null) {
            try {
                JceStruct jceStruct = (JceStruct) clsA.newInstance();
                JceInputStream jceInputStream = new JceInputStream(bArr);
                jceInputStream.setServerEncoding(HTTP.UTF_8);
                jceStruct.readFrom(jceInputStream);
                return jceStruct;
            } catch (Exception e) {
                throw new CommandMappingException(e.getMessage(), e);
            }
        }
        return null;
    }
}
