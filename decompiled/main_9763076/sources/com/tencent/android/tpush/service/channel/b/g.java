package com.tencent.android.tpush.service.channel.b;

import com.tencent.android.tpush.service.channel.exception.IORefusedException;
import com.tencent.android.tpush.service.channel.exception.InnerException;
import com.tencent.android.tpush.service.channel.exception.UnexpectedDataException;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g extends i implements d {
    protected HashMap a = new HashMap(4);
    protected int b = 0;
    protected int c = -1;

    @Override // com.tencent.android.tpush.service.channel.b.f
    public synchronized void d() {
        super.d();
        this.a.clear();
    }

    @Override // com.tencent.android.tpush.service.channel.b.d
    public int a(InputStream inputStream) {
        int iH = 0;
        c();
        if (inputStream.available() != 0) {
            try {
                this.b = 0;
                while (!b()) {
                    int i = this.b;
                    this.b = i + 1;
                    if (i > 2) {
                        throw new InnerException("the duration of the current step is too long!");
                    }
                    switch (this.c) {
                        case -7:
                            iH += h(inputStream);
                            break;
                        case BaseResp.ErrCode.ERR_BAN /* -6 */:
                            iH += g(inputStream);
                            break;
                        case BaseResp.ErrCode.ERR_UNSUPPORT /* -5 */:
                            iH += f(inputStream);
                            break;
                        case BaseResp.ErrCode.ERR_AUTH_DENIED /* -4 */:
                            iH += e(inputStream);
                            break;
                        case -3:
                            iH += d(inputStream);
                            break;
                        case -2:
                            iH += c(inputStream);
                            break;
                        case -1:
                            iH += b(inputStream);
                            break;
                        case 0:
                            d();
                            break;
                        default:
                            throw new InnerException("illegal step value!");
                    }
                    if (this.c == 0 || inputStream.available() != 0) {
                    }
                }
            } catch (IORefusedException e) {
                com.tencent.android.tpush.a.a.c("Channel.RecvPacket", "read >>> IORefusedException thrown", e);
            }
        }
        return iH;
    }

    void a(int i) {
        if (this.c != i) {
            this.b = 0;
        }
        this.c = i;
    }

    protected int b(InputStream inputStream) throws UnexpectedDataException {
        this.d = com.tencent.android.tpush.service.channel.c.e.a(inputStream);
        if (this.d != 80) {
            throw new UnexpectedDataException("soh: " + ((int) this.d) + " != TPNS_SOH");
        }
        a(-2);
        return 1;
    }

    protected int c(InputStream inputStream) throws UnexpectedDataException {
        this.k = com.tencent.android.tpush.service.channel.c.e.a(inputStream);
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

    protected int d(InputStream inputStream) {
        this.e = com.tencent.android.tpush.service.channel.c.e.c(inputStream);
        a(-4);
        return 4;
    }

    protected int e(InputStream inputStream) throws UnexpectedDataException {
        this.f = com.tencent.android.tpush.service.channel.c.e.b(inputStream);
        this.f -= 10;
        if (this.f > 10485760 || this.f < 0) {
            throw new UnexpectedDataException("packetLength: " + this.f);
        }
        if (this.k == 1) {
            a(-5);
            return 4;
        }
        a(-7);
        return 4;
    }

    protected int f(InputStream inputStream) throws UnexpectedDataException {
        this.f--;
        this.i = com.tencent.android.tpush.service.channel.c.e.a(inputStream);
        if (this.i != 0) {
            throw new UnexpectedDataException("negotiateSecurity: " + ((int) this.i) + " != 0");
        }
        a(-6);
        return 1;
    }

    protected int g(InputStream inputStream) throws UnexpectedDataException {
        this.f -= 4;
        this.g = com.tencent.android.tpush.service.channel.c.e.b(inputStream);
        if (this.g != this.j.getRandom()) {
            throw new UnexpectedDataException("unexpected random: " + this.g);
        }
        a(-7);
        return 4;
    }

    protected int h(InputStream inputStream) throws UnexpectedDataException, IOException {
        byte[] bArr = (byte[]) this.a.get("contentData");
        if (bArr == null) {
            if (this.f < 0) {
                throw new UnexpectedDataException("unexpected packetLength: " + this.f + " < 0");
            }
            bArr = new byte[(int) this.f];
            this.a.put("contentData", bArr);
            this.a.put("contentDataLeftLength", Integer.valueOf(bArr.length));
        }
        byte[] bArrDecryptData = bArr;
        int iIntValue = ((Integer) this.a.get("contentDataLeftLength")).intValue();
        int iA = com.tencent.android.tpush.service.channel.c.e.a(inputStream, bArrDecryptData, bArrDecryptData.length - iIntValue);
        int i = iIntValue - iA;
        this.a.put("contentDataLeftLength", Integer.valueOf(i));
        if (i == 0) {
            if (this.k == 1) {
                bArrDecryptData = this.j.decryptData(bArrDecryptData);
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArrDecryptData);
            try {
                long jB = com.tencent.android.tpush.service.channel.c.e.b(byteArrayInputStream);
                if (this.k == 1) {
                    this.j.checkRemoteInc(jB);
                }
                this.l = com.tencent.android.tpush.service.channel.c.e.a(byteArrayInputStream);
                this.h = com.tencent.android.tpush.service.channel.c.e.a(byteArrayInputStream);
                this.m = com.tencent.android.tpush.service.channel.c.e.a(byteArrayInputStream);
                if (byteArrayInputStream.available() > 0) {
                    this.n = new byte[byteArrayInputStream.available()];
                    com.tencent.android.tpush.service.channel.c.e.a(byteArrayInputStream, this.n, 0);
                }
                a(0);
            } catch (IOException e) {
                throw new UnexpectedDataException("contentData can not be read correctly!", e);
            }
        }
        return iA;
    }
}
