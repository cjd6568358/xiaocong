package com.tencent.android.tpush.service.channel.c;

import com.tencent.android.tpush.service.channel.exception.IORefusedException;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends OutputStream {
    final /* synthetic */ a a;

    protected c(a aVar) {
        this.a = aVar;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.a) {
            if (!this.a.k) {
                flush();
            }
            this.a.k = true;
        }
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        if (this.a.k) {
            throw new IOException("OutputStream has been closed; cannot flush a closed OutputStream.");
        }
        if (this.a.i) {
            throw new IOException("Buffer closed by inputStream; cannot flush.");
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        while (i2 > 0) {
            synchronized (this.a) {
                if (this.a.k) {
                    throw new IOException("OutputStream has been closed; cannot write to a closed OutputStream.");
                }
                if (this.a.i) {
                    throw new IOException("Buffer closed by InputStream; cannot write to a closed buffer.");
                }
                int iF = this.a.f();
                while (this.a.f && iF < i2) {
                    this.a.e();
                    iF = this.a.f();
                }
                if (!this.a.g && iF < i2) {
                    throw new IORefusedException("CircularByteBuffer is full; cannot write " + i2 + " bytes");
                }
                int iMin = Math.min(i2, iF);
                int iMin2 = Math.min(iMin, this.a.a.length - this.a.c);
                int iMin3 = Math.min(iMin - iMin2, (this.a.a.length - this.a.d) - 1);
                int i3 = iMin2 + iMin3;
                if (iMin2 > 0) {
                    System.arraycopy(bArr, i, this.a.a, this.a.c, iMin2);
                }
                if (iMin3 > 0) {
                    System.arraycopy(bArr, iMin2 + i, this.a.a, 0, iMin3);
                    this.a.c = iMin3;
                } else {
                    this.a.c += i3;
                }
                if (this.a.c == this.a.a.length) {
                    this.a.c = 0;
                }
                i += i3;
                i2 -= i3;
            }
            if (i2 > 0) {
                try {
                    Thread.sleep(100L);
                } catch (Exception e) {
                    throw new IOException("Waiting for available space in buffer interrupted.");
                }
            }
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        boolean z = false;
        while (!z) {
            synchronized (this.a) {
                if (this.a.k) {
                    throw new IOException("OutputStream has been closed; cannot write to a closed OutputStream.");
                }
                if (this.a.i) {
                    throw new IOException("Buffer closed by InputStream; cannot write to a closed buffer.");
                }
                int iF = this.a.f();
                while (this.a.f && iF < 1) {
                    this.a.e();
                    iF = this.a.f();
                }
                if (!this.a.g && iF < 1) {
                    throw new IORefusedException("CircularByteBuffer is full; cannot write 1 byte");
                }
                if (iF > 0) {
                    this.a.a[this.a.c] = (byte) (i & 255);
                    this.a.c++;
                    if (this.a.c == this.a.a.length) {
                        this.a.c = 0;
                    }
                    z = true;
                }
            }
            if (!z) {
                try {
                    Thread.sleep(100L);
                } catch (Exception e) {
                    throw new IOException("Waiting for available space in buffer interrupted.");
                }
            }
        }
    }
}
