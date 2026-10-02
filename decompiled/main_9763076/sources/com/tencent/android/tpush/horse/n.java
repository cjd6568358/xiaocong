package com.tencent.android.tpush.horse;

import android.text.TextUtils;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.XGPush4Msdk;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.StrategyItem;
import com.tencent.android.tpush.service.channel.exception.HorseIgnoreException;
import com.tencent.android.tpush.service.channel.exception.InnerException;
import com.tencent.android.tpush.service.channel.exception.UnexpectedDataException;
import com.tencent.android.tpush.service.channel.protocol.TpnsRedirectRsp;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class n {
    private static int f = 0;
    public StrategyItem a;
    private SocketChannel b;
    private ArrayBlockingQueue c = new ArrayBlockingQueue(1);
    private long d;
    private long e;

    public void a(StrategyItem strategyItem) throws HorseIgnoreException {
        this.d = System.currentTimeMillis();
        this.a = strategyItem;
        if (XGPushConfig.isForeiginPush(XGPushManager.getContext())) {
            String strC = com.tencent.android.tpush.service.b.b.c();
            if (com.tencent.android.tpush.service.b.b.b(strC)) {
                this.a = new StrategyItem(strC, DefaultServer.a(), null, DefaultServer.a(), 0, 0);
                com.tencent.android.tpush.a.a.c("SocketClient", "use foreigin StrategyItem:" + this.a.a() + ":" + this.a.b());
            }
        } else if (com.tencent.android.tpush.service.a.a.a(XGPushManager.getContext()).D == 1 && f <= 3) {
            try {
                String strA = com.tencent.android.tpush.service.b.b.a(XGPushManager.getContext()).a();
                if (com.tencent.android.tpush.service.b.b.b(strA)) {
                    this.a = new StrategyItem(strA, DefaultServer.a(), strategyItem.c(), strategyItem.e(), strategyItem.d(), 0);
                    com.tencent.android.tpush.a.a.c("SocketClient", "use httpdns StrategyItem:" + this.a.a() + ":" + this.a.b());
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("SocketClient", "HttpDNS error", th);
                f++;
            }
        }
        try {
            String debugServerInfo = XGPush4Msdk.getDebugServerInfo(com.tencent.android.tpush.service.n.f());
            if (!com.tencent.android.tpush.service.e.m.b(debugServerInfo)) {
                String[] strArrSplit = debugServerInfo.split(",");
                if (strArrSplit.length == 2 && strArrSplit[0].length() > 4) {
                    this.a = new StrategyItem(strArrSplit[0], Integer.valueOf(strArrSplit[1]).intValue(), strategyItem.c(), strategyItem.e(), strategyItem.d(), 0);
                    com.tencent.android.tpush.a.a.c("SocketClient", "use test StrategyItem:" + this.a.a() + ":" + this.a.b());
                }
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("SocketClient", " XGPush4Msdk.getDebugServerInfo", e);
        }
        try {
            com.tencent.android.tpush.a.a.d("SocketClient", "connect to " + this.a.a() + ":" + this.a.b());
            this.b = SocketChannel.open();
            this.b.configureBlocking(true);
            this.b.socket().connect(b(this.a), e.b());
            this.b.socket().setSoTimeout(e.c());
        } catch (Exception e2) {
            if (com.tencent.android.tpush.service.a.a.a(XGPushManager.getContext()).D == 1) {
                f++;
            }
            com.tencent.android.tpush.a.a.c("SocketClient", "socket connect error", e2);
            d();
            throw new HorseIgnoreException(strategyItem == null ? "null" : strategyItem.toString(), e2);
        }
    }

    private InetSocketAddress b(StrategyItem strategyItem) {
        return (strategyItem.d() == 1 && strategyItem.h()) ? new InetSocketAddress(strategyItem.c(), strategyItem.e()) : new InetSocketAddress(strategyItem.a(), strategyItem.b());
    }

    public SocketChannel a() {
        return this.b;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.Closeable] */
    public void a(JceStruct jceStruct) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        JceOutputStream jceOutputStream = new JceOutputStream();
        jceOutputStream.setServerEncoding(HTTP.UTF_8);
        jceStruct.writeTo(jceOutputStream);
        ?? r1 = 1;
        com.tencent.android.tpush.service.channel.b.h hVar = new com.tencent.android.tpush.service.channel.b.h(1);
        hVar.b((short) 10);
        hVar.a((short) 10);
        hVar.a(jceOutputStream.getByteBuffer().array());
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    if (this.a.d() == 0) {
                        while (!hVar.b()) {
                            hVar.a(byteArrayOutputStream);
                        }
                    } else {
                        com.tencent.android.tpush.service.channel.b.b bVar = new com.tencent.android.tpush.service.channel.b.b(this.a.a(), "http://" + this.a.a() + ":" + this.a.b() + "/");
                        if (this.a.h()) {
                            bVar.a("X-Online-Host", this.a.a() + ":" + this.a.b());
                        }
                        bVar.a(hVar);
                        while (!bVar.b()) {
                            bVar.a(byteArrayOutputStream);
                        }
                    }
                    byteArrayOutputStream.writeTo(this.b.socket().getOutputStream());
                    byteArrayOutputStream.flush();
                    com.tencent.android.tpush.common.e.a(byteArrayOutputStream);
                } catch (InnerException e) {
                    e = e;
                    com.tencent.android.tpush.a.a.c("SocketClient", "SocketClient -> send ", e);
                    d();
                    throw new HorseIgnoreException(e);
                } catch (UnexpectedDataException e2) {
                    e = e2;
                    com.tencent.android.tpush.a.a.c("SocketClient", "SocketClient -> send ", e);
                    d();
                    throw new HorseIgnoreException(e);
                } catch (IOException e3) {
                    e = e3;
                    com.tencent.android.tpush.a.a.c("SocketClient", "SocketClient -> send ", e);
                    d();
                    throw new HorseIgnoreException(e);
                } catch (Exception e4) {
                    e = e4;
                    com.tencent.android.tpush.a.a.c("SocketClient", "SocketClient -> send ", e);
                    d();
                    com.tencent.android.tpush.common.e.a(byteArrayOutputStream);
                }
            } catch (Throwable th) {
                th = th;
                com.tencent.android.tpush.common.e.a(r1);
                throw th;
            }
        } catch (InnerException e5) {
            e = e5;
        } catch (UnexpectedDataException e6) {
            e = e6;
        } catch (IOException e7) {
            e = e7;
        } catch (Exception e8) {
            e = e8;
            byteArrayOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            r1 = 0;
            com.tencent.android.tpush.common.e.a(r1);
            throw th;
        }
    }

    private void d() {
        try {
            o oVar = (o) this.c.remove();
            if (oVar != null) {
                oVar.b(this.a);
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("SocketClient", "notifyFail", e);
        }
        this.e = System.currentTimeMillis();
    }

    public void b() throws HorseIgnoreException {
        byte[] bArrK;
        o oVar;
        int i = 0;
        if (this.a == null) {
            d();
            throw new HorseIgnoreException("Recv() fail,because mStrategyItem is null");
        }
        if (this.a != null && this.a.d() == 0) {
            com.tencent.android.tpush.service.channel.b.g gVar = new com.tencent.android.tpush.service.channel.b.g();
            try {
                InputStream inputStream = this.b.socket().getInputStream();
                byte[] bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                while (!gVar.b()) {
                    i = inputStream.read(bArr, i, bArr.length - i);
                    gVar.a(byteArrayInputStream);
                }
                bArrK = gVar.k();
            } catch (InnerException e) {
                com.tencent.android.tpush.a.a.c("SocketClient", "SocketClient -> recv ", e);
                d();
                throw new HorseIgnoreException(e);
            } catch (UnexpectedDataException e2) {
                com.tencent.android.tpush.a.a.c("SocketClient", "SocketClient -> recv ", e2);
                d();
                throw new HorseIgnoreException(e2);
            } catch (IOException e3) {
                com.tencent.android.tpush.a.a.c("SocketClient", "SocketClient -> recv ", e3);
                d();
                throw new HorseIgnoreException(e3);
            } catch (IndexOutOfBoundsException e4) {
                com.tencent.android.tpush.a.a.c("SocketClient", "SocketClient -> recv ", e4);
                d();
                throw new HorseIgnoreException(e4);
            } catch (Exception e5) {
                com.tencent.android.tpush.a.a.c("SocketClient", "SocketClient -> recv ", e5);
                d();
                bArrK = null;
            }
        } else {
            com.tencent.android.tpush.service.channel.b.a aVar = new com.tencent.android.tpush.service.channel.b.a();
            try {
                InputStream inputStream2 = this.b.socket().getInputStream();
                byte[] bArr2 = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(bArr2);
                while (!aVar.b()) {
                    i += inputStream2.read(bArr2, i, bArr2.length - i);
                    aVar.a(byteArrayInputStream2);
                }
                if (aVar != null && aVar.i != null && aVar.i.size() > 0) {
                    bArrK = ((com.tencent.android.tpush.service.channel.b.g) aVar.i.get(0)).k();
                } else {
                    com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> packet is null or packet.recvPackets is null");
                    d();
                    return;
                }
            } catch (InnerException e6) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e6);
                d();
                throw new HorseIgnoreException(e6);
            } catch (UnexpectedDataException e7) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e7);
                d();
                throw new HorseIgnoreException(e7);
            } catch (IOException e8) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e8);
                d();
                throw new HorseIgnoreException(e8);
            } catch (IndexOutOfBoundsException e9) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e9);
                d();
                throw new HorseIgnoreException(e9);
            } catch (Exception e10) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e10);
                d();
                bArrK = null;
            }
        }
        if (bArrK == null) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> dataBuffer is null");
            d();
            return;
        }
        JceInputStream jceInputStream = new JceInputStream(bArrK);
        jceInputStream.setServerEncoding(HTTP.UTF_8);
        TpnsRedirectRsp tpnsRedirectRsp = new TpnsRedirectRsp();
        tpnsRedirectRsp.readFrom(jceInputStream);
        try {
            oVar = (o) this.c.remove();
        } catch (Exception e11) {
            com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, "callBacks.remove()", e11);
            oVar = null;
        }
        if (oVar != null) {
            String strA = com.tencent.android.tpush.service.e.m.a(tpnsRedirectRsp.ip);
            int i2 = tpnsRedirectRsp.port;
            StrategyItem strategyItem = new StrategyItem(strA, i2, this.a.c(), this.a.e(), this.a.d(), this.a.f());
            if (TextUtils.isEmpty(strA) || i2 == 0) {
                if (oVar != null) {
                    oVar.a(this.a);
                }
            } else {
                strategyItem.a(1);
                if (oVar != null) {
                    oVar.a(this.a, strategyItem);
                }
            }
        }
        this.e = System.currentTimeMillis();
    }

    public void c() {
        try {
            this.b.close();
            this.c.clear();
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("SocketClient", "mSocketChannel.close()", e);
        }
    }

    public void a(o oVar) {
        try {
            this.c.add(oVar);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("SocketClient", "register", e);
        }
    }
}
