package com.tencent.android.tpush.service.channel.b;

import com.tencent.android.tpush.service.channel.exception.IORefusedException;
import com.tencent.android.tpush.service.channel.exception.InnerException;
import com.tencent.android.tpush.service.channel.exception.UnexpectedDataException;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h extends i implements e {
    protected HashMap a = new HashMap(4);
    protected int b = 0;
    protected int c = -1;

    public h(int i) {
        this.d = (short) 80;
        this.e = i;
    }

    @Override // com.tencent.android.tpush.service.channel.b.f
    public synchronized void d() {
        super.d();
        this.a.clear();
    }

    @Override // com.tencent.android.tpush.service.channel.b.e
    public int a(OutputStream outputStream) throws InnerException {
        int iB;
        IORefusedException e;
        c();
        try {
            this.b = 0;
            iB = 0;
            while (!b()) {
                try {
                    int i = this.b;
                    this.b = i + 1;
                    if (i > 2) {
                        throw new InnerException("the duration of the current step is too long!");
                    }
                    switch (this.c) {
                        case BaseResp.ErrCode.ERR_UNSUPPORT /* -5 */:
                            iB += f(outputStream);
                            break;
                        case BaseResp.ErrCode.ERR_AUTH_DENIED /* -4 */:
                            iB += e(outputStream);
                            break;
                        case -3:
                            iB += d(outputStream);
                            break;
                        case -2:
                            iB += c(outputStream);
                            break;
                        case -1:
                            iB += b(outputStream);
                            break;
                        case 0:
                            d();
                            break;
                        default:
                            throw new InnerException("illegal step value!");
                    }
                } catch (IORefusedException e2) {
                    e = e2;
                    com.tencent.android.tpush.a.a.c("Channel.SendPacket", "write >>> IORefusedException thrown", e);
                }
            }
        } catch (IORefusedException e3) {
            iB = 0;
            e = e3;
        }
        return iB;
    }

    void a(int i) {
        if (this.c != i) {
            this.b = 0;
        }
        this.c = i;
    }

    protected int b(OutputStream outputStream) throws IOException {
        com.tencent.android.tpush.service.channel.c.e.a(outputStream, (int) this.d);
        a(-2);
        return 1;
    }

    protected int c(OutputStream outputStream) throws IOException, UnexpectedDataException {
        com.tencent.android.tpush.service.channel.c.e.a(outputStream, (int) this.k);
        switch (this.k) {
            case 1:
            case 10:
                a(-3);
                return 1;
            case 20:
                a(0);
                return 1;
            default:
                throw new UnexpectedDataException("protocol: " + ((int) this.k));
        }
    }

    protected int d(OutputStream outputStream) throws IOException {
        com.tencent.android.tpush.service.channel.c.e.b(outputStream, this.e);
        a(-5);
        return 4;
    }

    protected int e(OutputStream outputStream) throws IOException {
        com.tencent.android.tpush.service.channel.c.e.a(outputStream, this.f);
        a(-5);
        return 4;
    }

    protected int f(OutputStream outputStream) throws UnexpectedDataException, IOException {
        byte[] bArr = (byte[]) this.a.get("packetData");
        if (bArr == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                if (this.k == 10) {
                    h(byteArrayOutputStream);
                } else {
                    g(byteArrayOutputStream);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                this.f = byteArray.length + 10;
                this.a.put("packetData", byteArray);
                this.a.put("packetDataLeftLength", Integer.valueOf(byteArray.length));
                a(-4);
                return 0;
            } catch (IOException e) {
                throw new UnexpectedDataException("packetData can not be write correctly!", e);
            }
        }
        int iIntValue = ((Integer) this.a.get("packetDataLeftLength")).intValue();
        if (iIntValue == 0) {
            a(0);
            return 0;
        }
        int iA = com.tencent.android.tpush.service.channel.c.e.a(outputStream, bArr);
        this.a.put("packetDataLeftLength", Integer.valueOf(iIntValue - iA));
        return iA;
    }

    private void g(OutputStream outputStream) throws IOException {
        this.i = (short) 0;
        if (this.j.needsUpdate()) {
            this.i = (short) 1;
            this.j.update();
        }
        com.tencent.android.tpush.service.channel.c.e.a(outputStream, (int) this.i);
        this.g = this.j.getRandom();
        com.tencent.android.tpush.service.channel.c.e.a(outputStream, this.g);
        if (this.i != 0) {
            com.tencent.android.tpush.service.channel.c.e.a(outputStream, this.j.getEncKey());
        }
        h(outputStream);
    }

    private void h(OutputStream outputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        com.tencent.android.tpush.service.channel.c.e.a(byteArrayOutputStream, this.k != 1 ? 0L : this.j.getInc());
        com.tencent.android.tpush.service.channel.c.e.a((OutputStream) byteArrayOutputStream, (int) this.l);
        com.tencent.android.tpush.service.channel.c.e.a((OutputStream) byteArrayOutputStream, (int) this.h);
        com.tencent.android.tpush.service.channel.c.e.a((OutputStream) byteArrayOutputStream, (int) this.m);
        byteArrayOutputStream.write(this.n);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (this.k == 1) {
            byteArray = this.j.encryptData(byteArray);
        }
        com.tencent.android.tpush.service.channel.c.e.a(outputStream, byteArray);
    }
}
