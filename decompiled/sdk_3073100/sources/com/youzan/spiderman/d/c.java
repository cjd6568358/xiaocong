package com.youzan.spiderman.d;

import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import com.youzan.spiderman.utils.Logger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.util.concurrent.CountDownLatch;
import okhttp3.internal.Util;

/* JADX INFO: compiled from: StreamEncodingTransfer.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c implements Runnable {
    private d a;
    private PipedInputStream b = new PipedInputStream(AsyncHttpClient.DEFAULT_SOCKET_BUFFER_SIZE);
    private PipedOutputStream c;
    private CountDownLatch d;

    public c(d streamResult) {
        this.a = streamResult;
        try {
            this.c = new PipedOutputStream(this.b);
        } catch (IOException e) {
            Logger.e("StreamEncodingTransfer", "piped output stream exception", e);
        }
        this.d = new CountDownLatch(1);
    }

    public InputStream a() {
        return new BufferedInputStream(this.b) { // from class: com.youzan.spiderman.d.c.1
            @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                c.this.d.countDown();
                super.close();
            }
        };
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        Reader streamReader = this.a.b();
        char[] cbuf = new char[4096];
        OutputStreamWriter outputStreamWriter = null;
        try {
            OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(new BufferedOutputStream(this.c), Util.UTF_8);
            while (true) {
                try {
                    int count = streamReader.read(cbuf, 0, 4096);
                    if (count != -1) {
                        outputStreamWriter2.write(cbuf, 0, count);
                    } else {
                        try {
                            break;
                        } catch (IOException e) {
                            Logger.e("StreamEncodingTransfer", "close exception", e);
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                    outputStreamWriter = outputStreamWriter2;
                    Logger.e("StreamEncodingTransfer", "transfer exception", e);
                    try {
                        streamReader.close();
                    } catch (IOException e3) {
                        Logger.e("StreamEncodingTransfer", "close exception", e3);
                    }
                    if (outputStreamWriter != null) {
                        try {
                            outputStreamWriter.close();
                        } catch (IOException e4) {
                            Logger.e("StreamEncodingTransfer", "close output stream reader exception", e4);
                        }
                    }
                    if (this.d.getCount() != 0) {
                        try {
                            this.d.await();
                        } catch (InterruptedException e5) {
                            Logger.e("StreamEncodingTransfer", "latch wait exception", e5);
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    outputStreamWriter = outputStreamWriter2;
                    try {
                        streamReader.close();
                    } catch (IOException e6) {
                        Logger.e("StreamEncodingTransfer", "close exception", e6);
                    }
                    if (outputStreamWriter != null) {
                        try {
                            outputStreamWriter.close();
                        } catch (IOException e7) {
                            Logger.e("StreamEncodingTransfer", "close output stream reader exception", e7);
                        }
                    }
                    if (this.d.getCount() != 0) {
                        try {
                            this.d.await();
                            throw th;
                        } catch (InterruptedException e8) {
                            Logger.e("StreamEncodingTransfer", "latch wait exception", e8);
                            throw th;
                        }
                    }
                    throw th;
                }
            }
            streamReader.close();
            if (outputStreamWriter2 != null) {
                try {
                    outputStreamWriter2.close();
                } catch (IOException e9) {
                    Logger.e("StreamEncodingTransfer", "close output stream reader exception", e9);
                }
            }
            if (this.d.getCount() != 0) {
                try {
                    this.d.await();
                    outputStreamWriter = outputStreamWriter2;
                } catch (InterruptedException e10) {
                    Logger.e("StreamEncodingTransfer", "latch wait exception", e10);
                    outputStreamWriter = outputStreamWriter2;
                }
            } else {
                outputStreamWriter = outputStreamWriter2;
            }
        } catch (IOException e11) {
            e = e11;
        }
    }
}
