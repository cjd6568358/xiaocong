package com.tencent.android.tpush.service.channel.c;

import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends InputStream {
    final /* synthetic */ a a;

    protected b(a aVar) {
        this.a = aVar;
    }

    @Override // java.io.InputStream
    public int available() {
        int iG;
        synchronized (this.a) {
            if (this.a.i) {
                throw new IOException("InputStream has been closed, it is not ready.");
            }
            iG = this.a.g();
        }
        return iG;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.a) {
            this.a.i = true;
        }
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        synchronized (this.a) {
            if (this.a.a.length - 1 > i) {
                this.a.e = i;
                this.a.d = this.a.b;
            }
        }
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0053, code lost:
    
        java.lang.Thread.sleep(100);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x005f, code lost:
    
        throw new java.io.IOException("Blocking read operation interrupted.");
     */
    @Override // java.io.InputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int read() throws IOException {
        int i;
        while (true) {
            synchronized (this.a) {
                if (this.a.i) {
                    throw new IOException("InputStream has been closed; cannot read from a closed InputStream.");
                }
                if (this.a.g() > 0) {
                    i = this.a.a[this.a.b] & Constants.NETWORK_TYPE_UNCONNECTED;
                    this.a.b++;
                    if (this.a.b == this.a.a.length) {
                        this.a.b = 0;
                    }
                    this.a.i();
                } else if (this.a.k) {
                    i = -1;
                }
                return i;
            }
            return i;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0075, code lost:
    
        java.lang.Thread.sleep(100);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0081, code lost:
    
        throw new java.io.IOException("Blocking read operation interrupted.");
     */
    @Override // java.io.InputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int iMin;
        while (true) {
            synchronized (this.a) {
                if (this.a.i) {
                    throw new IOException("InputStream has been closed; cannot read from a closed InputStream.");
                }
                int iG = this.a.g();
                if (iG > 0) {
                    iMin = Math.min(i2, iG);
                    int iMin2 = Math.min(iMin, this.a.a.length - this.a.b);
                    int i3 = iMin - iMin2;
                    System.arraycopy(this.a.a, this.a.b, bArr, i, iMin2);
                    if (i3 > 0) {
                        System.arraycopy(this.a.a, 0, bArr, iMin2 + i, i3);
                        this.a.b = i3;
                    } else {
                        this.a.b += iMin;
                    }
                    if (this.a.b == this.a.a.length) {
                        this.a.b = 0;
                    }
                    this.a.i();
                } else if (this.a.k) {
                    iMin = -1;
                }
                return iMin;
            }
            return iMin;
        }
    }

    @Override // java.io.InputStream
    public void reset() {
        synchronized (this.a) {
            if (this.a.i) {
                throw new IOException("InputStream has been closed; cannot reset a closed InputStream.");
            }
            this.a.b = this.a.d;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0064, code lost:
    
        java.lang.Thread.sleep(100);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0070, code lost:
    
        throw new java.io.IOException("Blocking read operation interrupted.");
     */
    @Override // java.io.InputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long skip(long j) throws IOException {
        long j2;
        while (true) {
            synchronized (this.a) {
                if (this.a.i) {
                    throw new IOException("InputStream has been closed; cannot skip bytes on a closed InputStream.");
                }
                int iG = this.a.g();
                if (iG > 0) {
                    int iMin = Math.min((int) j, iG);
                    int iMin2 = iMin - Math.min(iMin, this.a.a.length - this.a.b);
                    if (iMin2 > 0) {
                        this.a.b = iMin2;
                    } else {
                        this.a.b += iMin;
                    }
                    if (this.a.b == this.a.a.length) {
                        this.a.b = 0;
                    }
                    this.a.i();
                    j2 = iMin;
                } else if (this.a.k) {
                    j2 = 0;
                }
                return j2;
            }
            return j2;
        }
    }
}
