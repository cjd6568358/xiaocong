package com.youzan.spiderman.html;

import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: FetchSession.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class e {
    private o a;
    private HtmlResponse b;
    private HtmlResponse c;
    private boolean d;
    private AtomicBoolean e = new AtomicBoolean(false);
    private long f;

    public e(o htmlUrl) {
        this.a = htmlUrl;
        long now = System.currentTimeMillis();
        b(now);
    }

    public boolean a(long now) {
        return now - this.f > 180000;
    }

    public void b(long now) {
        this.b = null;
        this.c = null;
        this.d = false;
        this.e.set(false);
        this.f = now;
    }

    public void a(HtmlCallback htmlCallback) {
        if (this.e.compareAndSet(false, true)) {
            if (this.c == null) {
                c fetchHtmlRunner = new c(this.a);
                this.c = fetchHtmlRunner.a();
                this.b = null;
            }
            this.e.set(false);
            synchronized (this) {
                notifyAll();
            }
        } else if (htmlCallback != null) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return;
        }
        if (htmlCallback != null) {
            if (this.c != null) {
                htmlCallback.onSuccess(this.a.a(), this.c.getHeader(), this.c.getContentStream(), this.c.getEncoding());
            } else {
                htmlCallback.onFailed();
            }
        }
    }

    public HtmlResponse a(h htmlConfigJudge) {
        if (this.c != null) {
            return this.c;
        }
        if (this.b != null) {
            j htmlDataPool = j.a();
            i htmlData = htmlDataPool.a(this.a.c());
            if (htmlData != null && htmlConfigJudge.a(htmlData)) {
                return this.b;
            }
            this.b = null;
        }
        if (!this.d) {
            b(htmlConfigJudge);
            if (this.b != null) {
                return this.b;
            }
        }
        if (!this.e.get()) {
            return null;
        }
        try {
            synchronized (this) {
                wait(1000L);
            }
            return this.c;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void b(h htmlConfigJudge) {
        this.d = true;
        if (this.b == null) {
            j htmlDataPool = j.a();
            i htmlData = htmlDataPool.a(this.a.c());
            if (htmlData != null && htmlConfigJudge.a(htmlData)) {
                this.b = d.a(htmlData, this.a);
            }
        }
    }
}
