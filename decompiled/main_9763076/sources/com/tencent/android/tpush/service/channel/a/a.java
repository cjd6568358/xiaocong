package com.tencent.android.tpush.service.channel.a;

import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.b.e;
import com.tencent.android.tpush.service.channel.b.g;
import com.tencent.android.tpush.service.channel.b.h;
import com.tencent.android.tpush.service.channel.b.i;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.exception.InnerException;
import com.tencent.android.tpush.service.channel.exception.UnexpectedDataException;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeoutException;
import org.apache.http.HttpHost;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends Thread {
    protected b a;
    public SocketChannel b;
    protected Selector c;
    protected TpnsSecurity d;
    protected com.tencent.android.tpush.service.channel.b.d e;
    protected e f;
    protected String g;
    protected int h;
    protected int i;
    protected long j;
    protected com.tencent.android.tpush.service.channel.a k;
    private volatile boolean l;

    public a(SocketChannel socketChannel, b bVar) {
        super("TpnsClient");
        this.b = null;
        this.c = null;
        this.d = new TpnsSecurity();
        this.e = null;
        this.f = null;
        this.g = Constants.MAIN_VERSION_TAG;
        this.h = 0;
        this.i = 0;
        this.l = false;
        this.j = Long.MAX_VALUE;
        this.k = null;
        if (socketChannel.socket().isConnected()) {
            this.g = socketChannel.socket().getInetAddress() == null ? Constants.MAIN_VERSION_TAG : socketChannel.socket().getInetAddress().getHostAddress();
            this.h = socketChannel.socket().getPort();
            this.i = 0;
            com.tencent.android.tpush.a.a.f("TpnsClient", "Connect to Xinge Server succeed!");
        } else {
            com.tencent.android.tpush.a.a.i("TpnsClient", "TpnsClient -> the socketChannel is not connected");
        }
        this.b = socketChannel;
        this.a = bVar;
    }

    protected boolean a() {
        if (this.e == null) {
            this.e = new g();
            ((g) this.e).a(this.d);
            return true;
        }
        return true;
    }

    protected boolean b() {
        if (this.f == null) {
            ArrayList arrayListA = this.a.a(this, 1);
            if (!arrayListA.isEmpty()) {
                this.f = (e) arrayListA.get(0);
            }
            if (this.f != null) {
                ((h) this.f).a(this.d);
            }
        }
        return this.f != null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(a aVar, com.tencent.android.tpush.service.channel.b.d dVar) {
        this.a.b(aVar, (i) dVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(a aVar, e eVar) {
        if ((((h) eVar).h() & 127) != 7) {
            this.a.a(aVar, (i) eVar);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.e("TpnsClient", "TpnsClient is running and ready for send and recevie msg.");
        }
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                this.c = Selector.open();
                                this.b.configureBlocking(false);
                                ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(24576);
                                com.tencent.android.tpush.service.channel.c.a aVar = new com.tencent.android.tpush.service.channel.c.a(24576, false);
                                byte[] bArr = new byte[24576];
                                ByteBuffer byteBufferAllocateDirect2 = ByteBuffer.allocateDirect(4096);
                                com.tencent.android.tpush.service.channel.c.a aVar2 = new com.tencent.android.tpush.service.channel.c.a(-1, false);
                                byte[] bArr2 = new byte[4096];
                                byteBufferAllocateDirect2.flip();
                                long j = 0;
                                while (!this.l) {
                                    this.b.register(this.c, 1);
                                    if (b() || byteBufferAllocateDirect2.remaining() > 0 || aVar2.c() > 0) {
                                        this.b.register(this.c, 4);
                                    }
                                    if (g() && this.e == null && this.f == null) {
                                        com.tencent.android.tpush.a.a.i("TpnsClient", ">> retired!!!");
                                        break;
                                    }
                                    this.c.select(j);
                                    j = 0;
                                    if (this.f != null) {
                                        long jA = this.f.a();
                                        if (jA <= 0) {
                                            throw new TimeoutException("发送超时");
                                        }
                                        if (jA >= 0) {
                                            jA = 0;
                                        }
                                        j = jA;
                                    }
                                    if (this.e != null) {
                                        long jA2 = this.e.a();
                                        if (jA2 <= 0) {
                                            throw new TimeoutException("接收超时");
                                        }
                                        if (jA2 >= j) {
                                            jA2 = j;
                                        }
                                        j = jA2;
                                    }
                                    Iterator<SelectionKey> it = this.c.selectedKeys().iterator();
                                    while (it.hasNext()) {
                                        SelectionKey next = it.next();
                                        if (next.isReadable()) {
                                            byteBufferAllocateDirect.clear();
                                            byteBufferAllocateDirect.limit(aVar.d());
                                            int i = this.b.read(byteBufferAllocateDirect.slice());
                                            if (i == -1) {
                                                throw new IOException("socket channel read return -1");
                                            }
                                            byteBufferAllocateDirect.get(bArr, 0, i);
                                            aVar.a().write(bArr, 0, i);
                                            a(aVar.b());
                                        }
                                        if (next.isWritable()) {
                                            a(aVar2.a());
                                            if (aVar2.c() > 0) {
                                                byteBufferAllocateDirect2.compact();
                                                byteBufferAllocateDirect2.put(bArr2, 0, aVar2.b().read(bArr2, 0, byteBufferAllocateDirect2.remaining() < aVar2.c() ? byteBufferAllocateDirect2.remaining() : aVar2.c()));
                                                byteBufferAllocateDirect2.flip();
                                                this.b.write(byteBufferAllocateDirect2);
                                            }
                                        }
                                        it.remove();
                                    }
                                }
                                synchronized (this) {
                                    try {
                                        this.c.close();
                                    } catch (Exception e) {
                                        com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> selector.close() " + e);
                                    }
                                    try {
                                        this.b.close();
                                    } catch (Exception e2) {
                                        com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> socketChannel.close(): " + e2);
                                    }
                                }
                                if (0 != 0) {
                                    com.tencent.android.tpush.a.a.i("TpnsClient", "delegate.clientExceptionOccurs <<< Run <<< exit!!! cause: " + ((Object) null));
                                    this.a.a(this, (ChannelException) null);
                                } else if (this.l) {
                                    com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! cancelled! ");
                                    this.a.a(this);
                                } else {
                                    com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! Retired! ");
                                    this.a.b(this);
                                }
                            } catch (Throwable th) {
                                synchronized (this) {
                                    try {
                                        this.c.close();
                                    } catch (Exception e3) {
                                        com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> selector.close() " + e3);
                                    }
                                    try {
                                        this.b.close();
                                    } catch (Exception e4) {
                                        com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> socketChannel.close(): " + e4);
                                    }
                                    if (0 != 0) {
                                        com.tencent.android.tpush.a.a.i("TpnsClient", "delegate.clientExceptionOccurs <<< Run <<< exit!!! cause: " + ((Object) null));
                                        this.a.a(this, (ChannelException) null);
                                        throw th;
                                    }
                                    if (this.l) {
                                        com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! cancelled! ");
                                        this.a.a(this);
                                        throw th;
                                    }
                                    com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! Retired! ");
                                    this.a.b(this);
                                    throw th;
                                }
                            }
                        } catch (Exception e5) {
                            com.tencent.android.tpush.a.a.c("TpnsClient", "<<< Run <<< socketChannel Exception", e5);
                            ChannelException channelException = new ChannelException(Constants.CODE_NETWORK_UNKNOWN_EXCEPTION, "TpnsClient发生未知异常" + e5 + " errorcode " + Constants.CODE_NETWORK_UNKNOWN_EXCEPTION, e5);
                            synchronized (this) {
                                try {
                                    this.c.close();
                                } catch (Exception e6) {
                                    com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> selector.close() " + e6);
                                }
                                try {
                                    this.b.close();
                                } catch (Exception e7) {
                                    com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> socketChannel.close(): " + e7);
                                }
                                if (channelException != null) {
                                    com.tencent.android.tpush.a.a.i("TpnsClient", "delegate.clientExceptionOccurs <<< Run <<< exit!!! cause: " + channelException);
                                    this.a.a(this, channelException);
                                } else if (this.l) {
                                    com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! cancelled! ");
                                    this.a.a(this);
                                } else {
                                    com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! Retired! ");
                                    this.a.b(this);
                                }
                            }
                        }
                    } catch (IOException e8) {
                        com.tencent.android.tpush.a.a.c("TpnsClient", "<<< Run <<< socketChannel IOException", e8);
                        ChannelException channelException2 = new ChannelException(Constants.CODE_NETWORK_IOEXCEPTION_OCCUR, "TpnsClient发生IO异常，链路可能被关闭", e8);
                        synchronized (this) {
                            try {
                                this.c.close();
                            } catch (Exception e9) {
                                com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> selector.close() " + e9);
                            }
                            try {
                                this.b.close();
                            } catch (Exception e10) {
                                com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> socketChannel.close(): " + e10);
                            }
                            if (channelException2 != null) {
                                com.tencent.android.tpush.a.a.i("TpnsClient", "delegate.clientExceptionOccurs <<< Run <<< exit!!! cause: " + channelException2);
                                this.a.a(this, channelException2);
                            } else if (this.l) {
                                com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! cancelled! ");
                                this.a.a(this);
                            } else {
                                com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! Retired! ");
                                this.a.b(this);
                            }
                        }
                    }
                } catch (UnexpectedDataException e11) {
                    com.tencent.android.tpush.a.a.c("TpnsClient", "<<< Run <<< socketChannel UnexpectedDataException", e11);
                    ChannelException channelException3 = new ChannelException(Constants.CODE_NETWORK_UNEXPECTED_DATA_EXCEPTION_OCCUR, "TpnsClient发生非预期数据异常", e11);
                    synchronized (this) {
                        try {
                            this.c.close();
                        } catch (Exception e12) {
                            com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> selector.close() " + e12);
                        }
                        try {
                            this.b.close();
                        } catch (Exception e13) {
                            com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> socketChannel.close(): " + e13);
                        }
                        if (channelException3 != null) {
                            com.tencent.android.tpush.a.a.i("TpnsClient", "delegate.clientExceptionOccurs <<< Run <<< exit!!! cause: " + channelException3);
                            this.a.a(this, channelException3);
                        } else if (this.l) {
                            com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! cancelled! ");
                            this.a.a(this);
                        } else {
                            com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! Retired! ");
                            this.a.b(this);
                        }
                    }
                }
            } catch (InnerException e14) {
                com.tencent.android.tpush.a.a.c("TpnsClient", "<<< Run <<< socketChannel InnerException", e14);
                ChannelException channelException4 = new ChannelException(Constants.CODE_NETWORK_INNER_EXCEPTION_OCCUR, "TpnsClient发生内部异常", e14);
                synchronized (this) {
                    try {
                        this.c.close();
                    } catch (Exception e15) {
                        com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> selector.close() " + e15);
                    }
                    try {
                        this.b.close();
                    } catch (Exception e16) {
                        com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> socketChannel.close(): " + e16);
                    }
                    if (channelException4 != null) {
                        com.tencent.android.tpush.a.a.i("TpnsClient", "delegate.clientExceptionOccurs <<< Run <<< exit!!! cause: " + channelException4);
                        this.a.a(this, channelException4);
                    } else if (this.l) {
                        com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! cancelled! ");
                        this.a.a(this);
                    } else {
                        com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! Retired! ");
                        this.a.b(this);
                    }
                }
            }
        } catch (TimeoutException e17) {
            com.tencent.android.tpush.a.a.c("TpnsClient", "<<< Run <<< socketChannel TimeoutException", e17);
            ChannelException channelException5 = new ChannelException(Constants.CODE_NETWORK_TIMEOUT_EXCEPTION_OCCUR, "TpnsClient发生超时异常", e17);
            synchronized (this) {
                try {
                    this.c.close();
                } catch (Exception e18) {
                    com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> selector.close() " + e18);
                }
                try {
                    this.b.close();
                } catch (Exception e19) {
                    com.tencent.android.tpush.a.a.i("TpnsClient", ">>> Run >>> socketChannel.close(): " + e19);
                }
                if (channelException5 != null) {
                    com.tencent.android.tpush.a.a.i("TpnsClient", "delegate.clientExceptionOccurs <<< Run <<< exit!!! cause: " + channelException5);
                    this.a.a(this, channelException5);
                } else if (this.l) {
                    com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! cancelled! ");
                    this.a.a(this);
                } else {
                    com.tencent.android.tpush.a.a.i("TpnsClient", "<<< Run <<< exit!!! Retired! ");
                    this.a.b(this);
                }
            }
        }
    }

    protected int a(InputStream inputStream) {
        int iA = 0;
        while (inputStream.available() > 0) {
            a();
            if (this.e != null) {
                iA += this.e.a(inputStream);
                if (this.e.b()) {
                    a(this, this.e);
                    this.e = null;
                } else {
                    com.tencent.android.tpush.a.a.i(Constants.TcpRecvPackLogTag, ">> recvHandle not success");
                    break;
                }
            }
        }
        return iA;
    }

    protected int a(OutputStream outputStream) {
        int iA = 0;
        if (!g()) {
            b();
        }
        if (this.f != null) {
            iA = this.f.a(outputStream);
            if (this.f.b()) {
                a(this, this.f);
                this.f = null;
            }
            if (b()) {
                h();
            }
        }
        return iA;
    }

    @Override // java.lang.Thread
    public synchronized void start() {
        super.start();
    }

    public synchronized void c() {
        this.l = true;
        h();
    }

    public synchronized boolean d() {
        return this.b != null ? this.b.isConnected() : false;
    }

    public boolean e() {
        return this.i == 1;
    }

    public com.tencent.android.tpush.service.channel.a f() {
        if (this.k == null) {
            Object[] objArr = new Object[6];
            objArr[0] = 0;
            objArr[1] = this.g;
            objArr[2] = 1;
            objArr[3] = Integer.valueOf(this.h);
            objArr[4] = 2;
            objArr[5] = Boolean.valueOf(this.i == 1);
            this.k = new com.tencent.android.tpush.service.channel.a(objArr);
        }
        return this.k;
    }

    protected boolean g() {
        return System.currentTimeMillis() > this.j;
    }

    public void h() {
        try {
            if (this.c != null && this.c.isOpen()) {
                this.c.wakeup();
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("TpnsClient", ">>selector wakeup err", th);
        }
    }

    @Override // java.lang.Thread
    public String toString() {
        return new StringBuffer(getClass().getSimpleName()).append("(ip:").append(this.g).append(",port:").append(this.h).append(",protocol:").append(this.i == 1 ? HttpHost.DEFAULT_SCHEME_NAME : "tcp").append(")").toString();
    }
}
