package com.tencent.android.tpush.service.channel.b;

import com.tencent.android.tpush.service.channel.exception.IORefusedException;
import com.tencent.android.tpush.service.channel.exception.InnerException;
import com.tencent.android.tpush.service.channel.exception.UnexpectedDataException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends f implements e {
    protected String e;
    protected String f;
    protected HashMap a = new HashMap(4);
    protected int b = 0;
    protected int c = -1;
    public ArrayList d = new ArrayList(1);
    protected final HashMap g = new HashMap(8);

    public b(String str, String str2) {
        this.e = null;
        this.f = null;
        this.e = str;
        this.f = str2;
    }

    void a(int i) {
        if (this.c != i) {
            this.b = 0;
        }
        this.c = i;
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
                    com.tencent.android.tpush.a.a.c("Channel.HttpSendPacket", "write >>> IORefusedException thrown", e);
                }
            }
        } catch (IORefusedException e3) {
            iB = 0;
            e = e3;
        }
        return iB;
    }

    protected int b(OutputStream outputStream) throws UnexpectedDataException, IOException {
        String str;
        byte[] bArr = (byte[]) this.a.get("httpData");
        if (bArr == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                c(byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                a(HTTP.CONTENT_LEN, String.valueOf(byteArray.length));
                String str2 = "POST " + this.f + " HTTP/1.1\r\n";
                Iterator it = this.g.entrySet().iterator();
                while (true) {
                    str = str2;
                    if (!it.hasNext()) {
                        break;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    str2 = str + ((String) entry.getKey()) + ": " + ((String) entry.getValue()) + "\r\n";
                }
                byte[] bytes = (str + "\r\n").getBytes(HTTP.UTF_8);
                bArr = new byte[bytes.length + byteArray.length];
                System.arraycopy(bytes, 0, bArr, 0, bytes.length);
                System.arraycopy(byteArray, 0, bArr, bytes.length, byteArray.length);
                this.a.put("httpData", bArr);
                this.a.put("httpDataLeftLength", Integer.valueOf(bArr.length));
            } catch (IOException e) {
                throw new UnexpectedDataException("http content can not be write correctly!", e);
            }
        }
        byte[] bArr2 = bArr;
        int iIntValue = ((Integer) this.a.get("httpDataLeftLength")).intValue();
        if (iIntValue == 0) {
            a(0);
            return 0;
        }
        int iA = com.tencent.android.tpush.service.channel.c.e.a(outputStream, bArr2);
        this.a.put("httpDataLeftLength", Integer.valueOf(iIntValue - iA));
        return iA;
    }

    protected void c(OutputStream outputStream) {
        for (e eVar : this.d) {
            eVar.a(this.j);
            eVar.a(outputStream);
        }
    }

    public void a(String str, String str2) {
        this.g.put(str, str2);
    }

    public void a(e eVar) {
        this.d.add(eVar);
    }
}
