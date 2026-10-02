package com.xiaomi.slim;

import android.text.TextUtils;
import com.xiaomi.push.protobuf.b;
import com.xiaomi.push.service.ak;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class c {
    private ByteBuffer a = ByteBuffer.allocate(2048);
    private ByteBuffer b = ByteBuffer.allocate(4);
    private Adler32 c = new Adler32();
    private e d = new e();
    private InputStream e;
    private f f;
    private volatile boolean g;

    c(InputStream inputStream, f fVar) {
        this.e = new BufferedInputStream(inputStream);
        this.f = fVar;
    }

    private void a(ByteBuffer byteBuffer, int i) throws IOException {
        int iPosition = byteBuffer.position();
        do {
            int i2 = this.e.read(byteBuffer.array(), iPosition, i);
            if (i2 == -1) {
                throw new EOFException();
            }
            i -= i2;
            iPosition += i2;
        } while (i > 0);
        byteBuffer.position(iPosition);
    }

    private void d() throws IOException {
        boolean z = false;
        this.g = false;
        b bVarC = c();
        if ("CONN".equals(bVarC.a())) {
            b.f fVarB = b.f.b(bVarC.k());
            if (fVarB.e()) {
                this.f.a(fVarB.d());
                z = true;
            }
            if (fVarB.h()) {
                b.C0012b c0012bI = fVarB.i();
                b bVar = new b();
                bVar.a("SYNC", "CONF");
                bVar.a(c0012bI.c(), (String) null);
                this.f.a(bVar);
            }
            com.xiaomi.channel.commonutils.logger.b.a("[Slim] CONN: host = " + fVarB.f());
        }
        if (!z) {
            com.xiaomi.channel.commonutils.logger.b.a("[Slim] Invalid CONN");
            throw new IOException("Invalid Connection");
        }
        while (!this.g) {
            b bVarC2 = c();
            this.f.n();
            switch (bVarC2.m()) {
                case 1:
                    this.f.a(bVarC2);
                    break;
                case 2:
                    if ("SECMSG".equals(bVarC2.a()) && TextUtils.isEmpty(bVarC2.b())) {
                        try {
                            this.f.b(this.d.a(bVarC2.d(ak.a().b(Integer.valueOf(bVarC2.c()).toString(), bVarC2.j()).i), this.f));
                        } catch (Exception e) {
                            com.xiaomi.channel.commonutils.logger.b.a("[Slim] Parse packet from Blob " + bVarC2.toString() + " failure:" + e.getMessage());
                        }
                    } else {
                        this.f.a(bVarC2);
                    }
                    break;
                case 3:
                    try {
                        this.f.b(this.d.a(bVarC2.k(), this.f));
                    } catch (Exception e2) {
                        com.xiaomi.channel.commonutils.logger.b.a("[Slim] Parse packet from Blob " + bVarC2.toString() + " failure:" + e2.getMessage());
                    }
                    break;
                default:
                    com.xiaomi.channel.commonutils.logger.b.a("[Slim] unknow blob type " + ((int) bVarC2.m()));
                    break;
            }
        }
    }

    private ByteBuffer e() throws IOException {
        if (this.a.capacity() > 4096) {
            this.a = ByteBuffer.allocate(2048);
        }
        this.a.clear();
        a(this.a, b.n());
        int iC = b.c(this.a.asReadOnlyBuffer());
        if (iC > 32768) {
            throw new IOException("Blob size too large");
        }
        if (iC + 4 > this.a.remaining()) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(b.n() + iC);
            byteBufferAllocate.put(this.a.array(), 0, this.a.arrayOffset() + this.a.position());
            this.a = byteBufferAllocate;
        }
        a(this.a, iC);
        this.b.clear();
        a(this.b, 4);
        this.b.position(0);
        int i = this.b.getInt();
        this.c.reset();
        this.c.update(this.a.array(), 0, this.a.position());
        if (i == ((int) this.c.getValue())) {
            return this.a;
        }
        com.xiaomi.channel.commonutils.logger.b.a("CRC = " + ((int) this.c.getValue()) + " and " + i);
        throw new IOException("Corrupted Blob bad CRC");
    }

    void a() throws IOException {
        try {
            d();
        } catch (IOException e) {
            if (!this.g) {
                throw e;
            }
        }
    }

    void b() {
        this.g = true;
    }

    b c() throws IOException {
        IOException iOException;
        int iN;
        try {
            ByteBuffer byteBufferE = e();
            int iPosition = byteBufferE.position();
            try {
                byteBufferE.flip();
                b bVarB = b.b(byteBufferE);
                com.xiaomi.channel.commonutils.logger.b.c("[Slim] Read {cmd=" + bVarB.a() + ";chid=" + bVarB.c() + ";len=" + iPosition + "}");
                return bVarB;
            } catch (IOException e) {
                iN = iPosition;
                iOException = e;
                if (iN == 0) {
                    iN = this.a.position();
                }
                StringBuilder sbAppend = new StringBuilder().append("[Slim] read Blob [");
                byte[] bArrArray = this.a.array();
                if (iN > b.n()) {
                    iN = b.n();
                }
                com.xiaomi.channel.commonutils.logger.b.a(sbAppend.append(com.xiaomi.channel.commonutils.misc.d.a(bArrArray, 0, iN)).append("] Err:").append(iOException.getMessage()).toString());
                throw iOException;
            }
        } catch (IOException e2) {
            iOException = e2;
            iN = 0;
        }
    }
}
