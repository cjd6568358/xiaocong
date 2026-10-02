package com.youzan.spiderman.d;

import android.content.Context;
import com.youzan.spiderman.b.f;
import com.youzan.spiderman.cache.CacheUrl;
import com.youzan.spiderman.cache.g;
import com.youzan.spiderman.utils.Logger;
import com.youzan.spiderman.utils.OkHttpUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: InputStreamWrapper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a extends InputStream {
    private InputStream a;
    private CacheUrl b;
    private Context c;
    private File d = null;
    private BufferedOutputStream e = null;
    private boolean f = false;

    public a(Context context, CacheUrl cacheUrl) {
        this.c = context;
        this.b = cacheUrl;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.a == null) {
            this.a = a();
        }
        if (this.a != null) {
            try {
                int c = this.a.read();
                if (c != -1 && this.e != null) {
                    this.e.write(c);
                }
                return c;
            } catch (IOException e) {
                this.f = true;
                Logger.e("InputStreamWrapper", "exception when read, url:" + this.b.getUri(), e);
                throw e;
            }
        }
        this.f = true;
        Logger.e("InputStreamWrapper", "get input stream null, url:" + this.b.getUri(), new Object[0]);
        throw new IOException("get download input stream failed");
    }

    @Override // java.io.InputStream
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] b, int off, int len) throws IOException {
        if (this.a == null) {
            this.a = a();
        }
        if (this.a != null) {
            try {
                int c = this.a.read(b, off, len);
                if (c != -1 && this.e != null) {
                    this.e.write(b, off, c);
                }
                return c;
            } catch (IOException e) {
                this.f = true;
                Logger.e("InputStreamWrapper", "exception when read buf, url:" + this.b.getUri(), e);
                throw e;
            }
        }
        this.f = true;
        Logger.e("InputStreamWrapper", "get input stream null, url:" + this.b.getUri(), new Object[0]);
        throw new IOException("get download input stream failed");
    }

    private InputStream a() {
        this.d = b();
        if (this.d != null) {
            try {
                this.e = new BufferedOutputStream(new FileOutputStream(this.d));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                this.e = null;
            }
        }
        d streamResult = OkHttpUtil.downloadFile(this.c, this.b.getUri().toString());
        if (streamResult == null) {
            return null;
        }
        if (streamResult.c() || !this.b.isScript()) {
            return streamResult.a();
        }
        c streamEncodingTransfer = new c(streamResult);
        b.a().a(streamEncodingTransfer);
        return streamEncodingTransfer.a();
    }

    private File b() {
        File dir = new File(g.d());
        if (!dir.exists() && !dir.mkdirs()) {
            return null;
        }
        File file = new File(dir, this.b.getMd5());
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    return file;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return file;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        if (this.f) {
            if (this.e != null) {
                this.e.close();
            }
            if (this.d != null) {
                this.d.delete();
            }
            if (this.a != null) {
                this.a.close();
                return;
            }
            return;
        }
        if (this.d != null && this.e != null && this.a != null) {
            com.youzan.spiderman.a.c.a().a(new com.youzan.spiderman.a.a() { // from class: com.youzan.spiderman.d.a.1
                @Override // com.youzan.spiderman.a.a
                public void a() throws Throwable {
                    a.this.c();
                }

                @Override // com.youzan.spiderman.a.a
                public void a(Throwable throwable) {
                    Logger.e("InputStreamWrapper", throwable);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        File newFile;
        try {
            byte[] buf = new byte[4096];
            while (true) {
                int c = this.a.read(buf, 0, 4096);
                if (c == -1) {
                    break;
                } else {
                    this.e.write(buf, 0, c);
                }
            }
            this.e.flush();
            this.e.close();
            this.e = null;
            if (this.b.isScript()) {
                newFile = new File(g.f(), this.b.getMd5());
            } else {
                newFile = new File(g.g(), this.b.getMd5());
            }
            if (this.d.renameTo(newFile)) {
                f.a().a(this.b, newFile);
            }
        } catch (Exception e) {
            Logger.e("InputStreamWrapper", e);
        } finally {
            if (this.e != null) {
                try {
                    this.e.close();
                } catch (IOException e2) {
                    Logger.e("InputStreamWrapper", e2);
                }
            }
            if (this.a != null) {
                try {
                    this.a.close();
                } catch (IOException e3) {
                    Logger.e("InputStreamWrapper", e3);
                }
            }
        }
    }
}
