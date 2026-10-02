package com.youzan.spiderman.html;

import com.youzan.spiderman.utils.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: HtmlInputStream.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class m extends InputStream {
    private l a;
    private i b;
    private InputStream c;
    private g e;
    private ByteArrayOutputStream d = new ByteArrayOutputStream();
    private boolean f = false;

    public m(l responseHeader, i htmlData, InputStream inputStream, g htmlCacheWriter) {
        this.a = responseHeader;
        this.b = htmlData;
        this.c = inputStream;
        this.e = htmlCacheWriter;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        try {
            int c = this.c.read();
            if (c != -1) {
                this.d.write(c);
            }
            return c;
        } catch (IOException e) {
            this.f = true;
            Logger.e("HtmlInputStream", "read exception", e);
            throw e;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] b, int off, int len) throws IOException {
        try {
            int c = this.c.read(b, off, len);
            if (c != -1) {
                this.d.write(b, off, c);
            }
            return c;
        } catch (IOException e) {
            this.f = true;
            Logger.e("HtmlInputStream", "read buf exception", e);
            throw e;
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        if (!this.f) {
            try {
                byte[] buf = new byte[4096];
                while (true) {
                    int c = this.c.read(buf, 0, 4096);
                    if (c != -1) {
                        this.d.write(buf, 0, c);
                    } else {
                        this.e.a(this.a, this.b, a());
                        return;
                    }
                }
            } catch (IOException e) {
                Logger.e("HtmlInputStream", "close exception", e);
            } finally {
                this.c.close();
            }
        } else {
            this.c.close();
        }
    }

    public byte[] a() {
        return this.d.toByteArray();
    }
}
