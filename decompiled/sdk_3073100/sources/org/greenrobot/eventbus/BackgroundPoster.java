package org.greenrobot.eventbus;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class BackgroundPoster implements Runnable {
    private final EventBus eventBus;
    private volatile boolean executorRunning;
    private final PendingPostQueue queue = new PendingPostQueue();

    BackgroundPoster(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void enqueue(Subscription subscription, Object event) {
        PendingPost pendingPost = PendingPost.obtainPendingPost(subscription, event);
        synchronized (this) {
            this.queue.enqueue(pendingPost);
            if (!this.executorRunning) {
                this.executorRunning = true;
                this.eventBus.getExecutorService().execute(this);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            try {
                try {
                    PendingPost pendingPost = this.queue.poll(1000);
                    if (pendingPost == null) {
                        synchronized (this) {
                            try {
                                pendingPost = this.queue.poll();
                                if (pendingPost == null) {
                                    this.executorRunning = false;
                                    this.executorRunning = false;
                                    return;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                    this.eventBus.invokeSubscriber(pendingPost);
                } catch (InterruptedException e) {
                    Log.w("Event", Thread.currentThread().getName() + " was interruppted", e);
                    this.executorRunning = false;
                    return;
                }
            } catch (Throwable th2) {
                this.executorRunning = false;
                throw th2;
            }
        }
    }
}
