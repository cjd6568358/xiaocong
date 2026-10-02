package com.facebook.drawee.controller;

import android.graphics.drawable.Animatable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ForwardingControllerListener<INFO> implements ControllerListener<INFO> {
    private final List<ControllerListener<? super INFO>> mListeners = new ArrayList(2);

    public synchronized void addListener(ControllerListener<? super INFO> listener) {
        this.mListeners.add(listener);
    }

    public synchronized void clearListeners() {
        this.mListeners.clear();
    }

    private synchronized void onException(String message, Throwable t) {
        Log.e("FdingControllerListener", message, t);
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public synchronized void onSubmit(String id, Object callerContext) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            ControllerListener<? super INFO> listener = this.mListeners.get(i);
            try {
                listener.onSubmit(id, callerContext);
            } catch (Exception exception) {
                onException("InternalListener exception in onSubmit", exception);
            }
        }
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public synchronized void onFinalImageSet(String id, INFO imageInfo, Animatable animatable) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            ControllerListener<? super INFO> listener = this.mListeners.get(i);
            try {
                listener.onFinalImageSet(id, imageInfo, animatable);
            } catch (Exception exception) {
                onException("InternalListener exception in onFinalImageSet", exception);
            }
        }
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public void onIntermediateImageSet(String id, INFO imageInfo) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            ControllerListener<? super INFO> listener = this.mListeners.get(i);
            try {
                listener.onIntermediateImageSet(id, imageInfo);
            } catch (Exception exception) {
                onException("InternalListener exception in onIntermediateImageSet", exception);
            }
        }
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public void onIntermediateImageFailed(String id, Throwable throwable) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            ControllerListener<? super INFO> listener = this.mListeners.get(i);
            try {
                listener.onIntermediateImageFailed(id, throwable);
            } catch (Exception exception) {
                onException("InternalListener exception in onIntermediateImageFailed", exception);
            }
        }
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public synchronized void onFailure(String id, Throwable throwable) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            ControllerListener<? super INFO> listener = this.mListeners.get(i);
            try {
                listener.onFailure(id, throwable);
            } catch (Exception exception) {
                onException("InternalListener exception in onFailure", exception);
            }
        }
    }

    @Override // com.facebook.drawee.controller.ControllerListener
    public synchronized void onRelease(String id) {
        int numberOfListeners = this.mListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            ControllerListener<? super INFO> listener = this.mListeners.get(i);
            try {
                listener.onRelease(id);
            } catch (Exception exception) {
                onException("InternalListener exception in onRelease", exception);
            }
        }
    }
}
