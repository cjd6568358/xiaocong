package com.facebook.react.bridge;

import android.os.AsyncTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class GuardedResultAsyncTask<Result> extends AsyncTask<Void, Void, Result> {
    private final ReactContext mReactContext;

    protected abstract Result doInBackgroundGuarded();

    protected abstract void onPostExecuteGuarded(Result result);

    protected GuardedResultAsyncTask(ReactContext reactContext) {
        this.mReactContext = reactContext;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public final Result doInBackground(Void... params) {
        try {
            return doInBackgroundGuarded();
        } catch (RuntimeException e) {
            this.mReactContext.handleException(e);
            throw e;
        }
    }

    @Override // android.os.AsyncTask
    protected final void onPostExecute(Result result) {
        try {
            onPostExecuteGuarded(result);
        } catch (RuntimeException e) {
            this.mReactContext.handleException(e);
        }
    }
}
