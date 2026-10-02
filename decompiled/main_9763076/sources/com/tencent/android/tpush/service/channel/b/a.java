package com.tencent.android.tpush.service.channel.b;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.exception.IORefusedException;
import com.tencent.android.tpush.service.channel.exception.InnerException;
import com.tencent.android.tpush.service.channel.exception.UnexpectedDataException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends f implements d {
    private static final Pattern k = Pattern.compile("\\A(\\S+) +(\\d+) +(.*)\r\n");
    private static final Pattern l = Pattern.compile("(.*) *: *(.*)\r\n");
    protected String b;
    public int c;
    protected String d;
    protected StringBuffer a = new StringBuffer();
    protected final HashMap e = new HashMap();
    protected int f = -1;
    protected int g = 0;
    protected int h = -1;
    public final ArrayList i = new ArrayList();
    private int m = 0;
    private g n = null;

    @Override // com.tencent.android.tpush.service.channel.b.d
    public int a(InputStream inputStream) {
        int iD = 0;
        c();
        if (inputStream.available() != 0) {
            try {
                this.g = 0;
                while (!b()) {
                    int i = this.g;
                    this.g = i + 1;
                    if (i > 2) {
                        throw new InnerException("the duration of the current step is too long!");
                    }
                    switch (this.h) {
                        case -3:
                            iD += d(inputStream);
                            break;
                        case -2:
                            iD += c(inputStream);
                            break;
                        case -1:
                            iD += b(inputStream);
                            break;
                        case 0:
                            d();
                            break;
                        default:
                            throw new InnerException("illegal step value!");
                    }
                    if (this.h == 0 || inputStream.available() != 0) {
                    }
                }
            } catch (IORefusedException e) {
                com.tencent.android.tpush.a.a.c("Channel.HttpRecvPacket", "read >>> IORefusedException thrown", e);
            }
        }
        return iD;
    }

    void a(int i) {
        if (this.h != i) {
            this.g = 0;
        }
        this.h = i;
    }

    protected int b(InputStream inputStream) throws IOException, UnexpectedDataException {
        int iAvailable = inputStream.available();
        int i = 0;
        while (true) {
            int i2 = iAvailable - 1;
            if (iAvailable > 0) {
                int i3 = i + 1;
                int i4 = inputStream.read();
                switch (i4) {
                    case -1:
                        throw new IOException("the end of stream has been reached!");
                    case 10:
                        this.a.append((char) i4);
                        int length = this.a.length();
                        if (length >= 4 && "\r\n\r\n".contentEquals(this.a.subSequence(length - 4, length))) {
                            Matcher matcher = k.matcher(this.a.subSequence(0, this.a.length()));
                            if (matcher.find() && matcher.groupCount() == 3) {
                                this.b = matcher.group(1);
                                try {
                                    this.c = Integer.parseInt(matcher.group(2).trim());
                                    this.d = matcher.group(3);
                                    Matcher matcher2 = l.matcher(this.a.subSequence(0, this.a.length()));
                                    while (matcher2.find() && matcher2.groupCount() == 2) {
                                        this.e.put(matcher2.group(1).toLowerCase(Locale.US), matcher2.group(2));
                                    }
                                    if (this.e.containsKey(HTTP.TRANSFER_ENCODING.toLowerCase(Locale.US)) && ((String) this.e.get(HTTP.TRANSFER_ENCODING.toLowerCase(Locale.US))).equalsIgnoreCase(HTTP.CHUNK_CODING)) {
                                        this.f = -1;
                                        a(-3);
                                        return i3;
                                    }
                                    if (this.e.get(HTTP.CONTENT_LEN.toLowerCase(Locale.US)) != null) {
                                        try {
                                            this.f = Integer.parseInt(((String) this.e.get(HTTP.CONTENT_LEN.toLowerCase(Locale.US))).trim());
                                            a(-2);
                                            return i3;
                                        } catch (NumberFormatException e) {
                                            com.tencent.android.tpush.a.a.c(Constants.LogTag, Constants.MAIN_VERSION_TAG, e);
                                            throw new UnexpectedDataException("http Content-Length can not parsed!");
                                        }
                                    }
                                    throw new UnexpectedDataException("http Content-Length == null && Transfer-Encoding not equal to 'chunked'!");
                                } catch (NumberFormatException e2) {
                                    com.tencent.android.tpush.a.a.c(Constants.LogTag, Constants.MAIN_VERSION_TAG, e2);
                                    throw new UnexpectedDataException("http statusLine can not parsed!");
                                }
                            }
                            throw new UnexpectedDataException("http statusLine can not parsed!");
                        }
                        break;
                        break;
                    default:
                        this.a.append((char) i4);
                        break;
                }
                i = i3;
                iAvailable = i2;
            } else {
                return i;
            }
        }
    }

    protected int c(InputStream inputStream) throws InnerException, UnexpectedDataException {
        int i = 0;
        while (inputStream.available() >= 0) {
            if (this.m > this.f) {
                throw new UnexpectedDataException("readBodyLength > contentLength ?!!");
            }
            if (this.m == this.f) {
                if (this.n != null) {
                    throw new InnerException("currentRecvPacket != null ?!!");
                }
                a(0);
                return i;
            }
            if (this.n == null) {
                this.n = new g();
                this.n.a(this.j);
            }
            int iA = this.n.a(inputStream);
            int i2 = i + iA;
            this.m = iA + this.m;
            if (this.n.b()) {
                this.i.add(this.n);
                this.n = null;
            }
            if (i == i2) {
                return i2;
            }
            i = i2;
        }
        return i;
    }

    protected int d(InputStream inputStream) throws InnerException {
        throw new InnerException("not support chunked transfer encoding!");
    }
}
