package com.facebook.react.bridge;

import android.os.AsyncTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class GuardedAsyncTask<Params, Progress> extends AsyncTask<Params, Progress, Void> {
    private final ReactContext mReactContext;

    protected abstract void doInBackgroundGuarded(Params... paramsArr);

    protected GuardedAsyncTask(ReactContext reactContext) {
        this.mReactContext = reactContext;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public final Void doInBackground(Params... params) {
        try {
            doInBackgroundGuarded(params);
            return null;
        } catch (RuntimeException e) {
            this.mReactContext.handleException(e);
            return null;
        }
    }
}
