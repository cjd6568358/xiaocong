package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.content.Loader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class LoaderManager {

    public interface LoaderCallbacks<D> {
        Loader<D> onCreateLoader(int i, Bundle bundle);

        void onLoadFinished(Loader<D> loader, D d);

        void onLoaderReset(Loader<D> loader);
    }

    public boolean hasRunningLoaders() {
        return false;
    }
}
