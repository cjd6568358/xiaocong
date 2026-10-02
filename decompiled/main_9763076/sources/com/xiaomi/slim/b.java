package com.xiaomi.slim;

import android.text.TextUtils;
import com.xiaomi.push.service.aq;
import com.xiaomi.smack.util.d;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static String b = d.a(5) + "-";
    private static long c = 0;
    private static final byte[] f = new byte[0];
    String a;
    private com.xiaomi.push.protobuf.b.a d;
    private short e;
    private byte[] g;

    public b() {
        this.e = (short) 2;
        this.g = f;
        this.a = null;
        this.d = new com.xiaomi.push.protobuf.b.a();
    }

    b(com.xiaomi.push.protobuf.b.a aVar, short s, byte[] bArr) {
        this.e = (short) 2;
        this.g = f;
        this.a = null;
        this.d = aVar;
        this.e = s;
        this.g = bArr;
    }

    @Deprecated
    public static b a(com.xiaomi.smack.packet.d dVar, String str) {
        b bVar = new b();
        int i = 1;
        try {
            i = Integer.parseInt(dVar.l());
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("Blob parse chid err " + e.getMessage());
        }
        bVar.a(i);
        bVar.a(dVar.k());
        bVar.c(dVar.n());
        bVar.b(dVar.o());
        bVar.a("XMLMSG", (String) null);
        try {
            bVar.a(dVar.c().getBytes("utf8"), str);
            if (TextUtils.isEmpty(str)) {
                bVar.a((short) 3);
            } else {
                bVar.a((short) 2);
                bVar.a("SECMSG", (String) null);
            }
        } catch (UnsupportedEncodingException e2) {
            com.xiaomi.channel.commonutils.logger.b.a("Blob setPayload err： " + e2.getMessage());
        }
        return bVar;
    }

    static b b(ByteBuffer byteBuffer) throws IOException {
        try {
            if (byteBuffer.getShort(0) != -15618 || byteBuffer.getShort(2) != 4) {
                throw new IOException("Malformed Input");
            }
            short s = byteBuffer.getShort(6);
            short s2 = byteBuffer.getShort(8);
            int i = byteBuffer.getInt(10);
            com.xiaomi.push.protobuf.b.a aVar = new com.xiaomi.push.protobuf.b.a();
            aVar.b(byteBuffer.array(), 14, s2);
            byte[] bArr = new byte[i];
            byteBuffer.position(s2 + 14);
            byteBuffer.get(bArr, 0, i);
            return new b(aVar, s, bArr);
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("read Blob err :" + e.getMessage());
            throw new IOException("Malformed Input");
        }
    }

    static int c(ByteBuffer byteBuffer) {
        return byteBuffer.getShort(8) + byteBuffer.getInt(10);
    }

    public static synchronized String g() {
        StringBuilder sbAppend;
        long j;
        sbAppend = new StringBuilder().append(b);
        j = c;
        c = 1 + j;
        return sbAppend.append(Long.toString(j)).toString();
    }

    static int n() {
        return 14;
    }

    public String a() {
        return this.d.l();
    }

    ByteBuffer a(ByteBuffer byteBuffer) {
        int iL = l();
        if (byteBuffer == null || byteBuffer.remaining() < iL) {
            if (byteBuffer != null) {
                iL += byteBuffer.capacity();
            }
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(iL);
            if (byteBuffer != null) {
                byteBufferAllocate.put(byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.position());
            }
            byteBuffer = byteBufferAllocate;
        }
        ByteBuffer byteBufferSlice = byteBuffer.slice();
        byteBufferSlice.putShort((short) -15618);
        byteBufferSlice.putShort((short) 4);
        byteBufferSlice.putShort((short) 1);
        byteBufferSlice.putShort(this.e);
        byteBufferSlice.putShort((short) this.d.a());
        byteBufferSlice.putInt(this.g.length);
        int iPosition = byteBufferSlice.position();
        this.d.a(byteBufferSlice.array(), byteBufferSlice.arrayOffset() + iPosition, this.d.a());
        byteBufferSlice.position(iPosition + this.d.a());
        byteBufferSlice.put(this.g);
        byteBuffer.position(byteBufferSlice.position() + byteBuffer.position());
        return byteBuffer;
    }

    public void a(int i) {
        this.d.a(i);
    }

    public void a(long j, String str, String str2) {
        if (j != 0) {
            this.d.a(j);
        }
        if (!TextUtils.isEmpty(str)) {
            this.d.a(str);
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        this.d.b(str2);
    }

    public void a(String str) {
        this.d.e(str);
    }

    public void a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("command should not be empty");
        }
        this.d.c(str);
        this.d.p();
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        this.d.d(str2);
    }

    public void a(short s) {
        this.e = s;
    }

    public void a(byte[] bArr, String str) {
        if (TextUtils.isEmpty(str)) {
            this.d.c(0);
            this.g = bArr;
        } else {
            this.d.c(1);
            this.g = aq.a(aq.a(str, h()), bArr);
        }
    }

    public String b() {
        return this.d.n();
    }

    public void b(String str) {
        this.a = str;
    }

    public int c() {
        return this.d.d();
    }

    public void c(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int iIndexOf = str.indexOf("@");
        try {
            long j = Long.parseLong(str.substring(0, iIndexOf));
            int iIndexOf2 = str.indexOf("/", iIndexOf);
            String strSubstring = str.substring(iIndexOf + 1, iIndexOf2);
            String strSubstring2 = str.substring(iIndexOf2 + 1);
            this.d.a(j);
            this.d.a(strSubstring);
            this.d.b(strSubstring2);
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("Blob parse user err " + e.getMessage());
        }
    }

    public boolean d() {
        return this.d.x();
    }

    public byte[] d(String str) {
        if (this.d.u() == 1) {
            return aq.a(aq.a(str, h()), this.g);
        }
        if (this.d.u() == 0) {
            return this.g;
        }
        com.xiaomi.channel.commonutils.logger.b.a("unknow cipher = " + this.d.u());
        return this.g;
    }

    public int e() {
        return this.d.w();
    }

    public String f() {
        return this.d.y();
    }

    public String h() {
        String strQ = this.d.q();
        if ("ID_NOT_AVAILABLE".equals(strQ)) {
            return null;
        }
        if (this.d.r()) {
            return strQ;
        }
        String strG = g();
        this.d.e(strG);
        return strG;
    }

    public String i() {
        return this.a;
    }

    public String j() {
        if (this.d.g()) {
            return Long.toString(this.d.f()) + "@" + this.d.h() + "/" + this.d.j();
        }
        return null;
    }

    public byte[] k() {
        return this.g;
    }

    public int l() {
        return n() + this.d.b() + this.g.length;
    }

    public short m() {
        return this.e;
    }

    public String toString() {
        return "Blob [chid=" + c() + "; Id=" + h() + "; cmd=" + a() + "; type=" + ((int) m()) + "; from=" + j() + " ]";
    }
}
