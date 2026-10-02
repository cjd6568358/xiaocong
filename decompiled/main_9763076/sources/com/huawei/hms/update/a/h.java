package com.huawei.hms.update.a;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/* JADX INFO: compiled from: RandomFileOutputStream.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h extends OutputStream {
    private RandomAccessFile a;

    public h(File file, int i) throws IOException {
        try {
            this.a = new RandomAccessFile(file, "rwd");
            this.a.setLength(i);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e2) {
            com.huawei.hms.c.c.a(this.a);
            throw e2;
        }
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.a.write(bArr, i, i2);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        write(new byte[]{(byte) i}, 0, 1);
    }

    public void a(long j) throws IOException {
        this.a.seek(j);
    }
}
