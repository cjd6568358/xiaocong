package org.greenrobot.eventbus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class AsyncPoster implements Runnable {
    private final EventBus eventBus;
    private final PendingPostQueue queue = new PendingPostQueue();

    AsyncPoster(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void enqueue(Subscription subscription, Object event) {
        PendingPost pendingPost = PendingPost.obtainPendingPost(subscription, event);
        this.queue.enqueue(pendingPost);
        this.eventBus.getExecutorService().execute(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        PendingPost pendingPost = this.queue.poll();
        if (pendingPost == null) {
            throw new IllegalStateException("No pending post available");
        }
        this.eventBus.invokeSubscriber(pendingPost);
    }
}
