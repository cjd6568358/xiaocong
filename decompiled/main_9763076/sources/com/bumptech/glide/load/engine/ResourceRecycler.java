package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.bumptech.glide.util.Util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ResourceRecycler {
    private final Handler handler = new Handler(Looper.getMainLooper(), new ResourceRecyclerCallback());
    private boolean isRecycling;

    ResourceRecycler() {
    }

    public void recycle(Resource<?> resource) {
        Util.assertMainThread();
        if (this.isRecycling) {
            this.handler.obtainMessage(1, resource).sendToTarget();
            return;
        }
        this.isRecycling = true;
        resource.recycle();
        this.isRecycling = false;
    }

    private static class ResourceRecyclerCallback implements Handler.Callback {
        private ResourceRecyclerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            Resource resource = (Resource) message.obj;
            resource.recycle();
            return true;
        }
    }
}
