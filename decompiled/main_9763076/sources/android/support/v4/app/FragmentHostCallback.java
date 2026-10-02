package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class FragmentHostCallback<E> extends FragmentContainer {
    private final Activity mActivity;
    private SimpleArrayMap<String, LoaderManager> mAllLoaderManagers;
    private boolean mCheckedForLoaderManager;
    final Context mContext;
    final FragmentManagerImpl mFragmentManager;
    private final Handler mHandler;
    private LoaderManagerImpl mLoaderManager;
    private boolean mLoadersStarted;
    private boolean mRetainLoaders;
    final int mWindowAnimations;

    FragmentHostCallback(FragmentActivity activity) {
        this(activity, activity, activity.mHandler, 0);
    }

    FragmentHostCallback(Activity activity, Context context, Handler handler, int windowAnimations) {
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = activity;
        this.mContext = context;
        this.mHandler = handler;
        this.mWindowAnimations = windowAnimations;
    }

    public void onDump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
    }

    public boolean onShouldSaveFragmentState(Fragment fragment) {
        return true;
    }

    public LayoutInflater onGetLayoutInflater() {
        return (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public void onSupportInvalidateOptionsMenu() {
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int requestCode, Bundle options) {
        if (requestCode != -1) {
            throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
        }
        this.mContext.startActivity(intent);
    }

    public void onRequestPermissionsFromFragment(Fragment fragment, String[] permissions, int requestCode) {
    }

    public boolean onHasWindowAnimations() {
        return true;
    }

    public int onGetWindowAnimations() {
        return this.mWindowAnimations;
    }

    @Override // android.support.v4.app.FragmentContainer
    public View onFindViewById(int id) {
        return null;
    }

    @Override // android.support.v4.app.FragmentContainer
    public boolean onHasView() {
        return true;
    }

    Activity getActivity() {
        return this.mActivity;
    }

    Context getContext() {
        return this.mContext;
    }

    Handler getHandler() {
        return this.mHandler;
    }

    FragmentManagerImpl getFragmentManagerImpl() {
        return this.mFragmentManager;
    }

    void inactivateFragment(String who) {
        LoaderManagerImpl lm;
        if (this.mAllLoaderManagers != null && (lm = (LoaderManagerImpl) this.mAllLoaderManagers.get(who)) != null && !lm.mRetaining) {
            lm.doDestroy();
            this.mAllLoaderManagers.remove(who);
        }
    }

    void onAttachFragment(Fragment fragment) {
    }

    boolean getRetainLoaders() {
        return this.mRetainLoaders;
    }

    void doLoaderStart() {
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (this.mLoaderManager != null) {
                this.mLoaderManager.doStart();
            } else if (!this.mCheckedForLoaderManager) {
                this.mLoaderManager = getLoaderManager("(root)", this.mLoadersStarted, false);
                if (this.mLoaderManager != null && !this.mLoaderManager.mStarted) {
                    this.mLoaderManager.doStart();
                }
            }
            this.mCheckedForLoaderManager = true;
        }
    }

    void doLoaderStop(boolean retain) {
        this.mRetainLoaders = retain;
        if (this.mLoaderManager != null && this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if (retain) {
                this.mLoaderManager.doRetain();
            } else {
                this.mLoaderManager.doStop();
            }
        }
    }

    void doLoaderDestroy() {
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doDestroy();
        }
    }

    void reportLoaderStart() {
        if (this.mAllLoaderManagers != null) {
            int N = this.mAllLoaderManagers.size();
            LoaderManagerImpl[] loaders = new LoaderManagerImpl[N];
            for (int i = N - 1; i >= 0; i--) {
                loaders[i] = (LoaderManagerImpl) this.mAllLoaderManagers.valueAt(i);
            }
            for (int i2 = 0; i2 < N; i2++) {
                LoaderManagerImpl lm = loaders[i2];
                lm.finishRetain();
                lm.doReportStart();
            }
        }
    }

    LoaderManagerImpl getLoaderManager(String who, boolean started, boolean create) {
        if (this.mAllLoaderManagers == null) {
            this.mAllLoaderManagers = new SimpleArrayMap<>();
        }
        LoaderManagerImpl lm = (LoaderManagerImpl) this.mAllLoaderManagers.get(who);
        if (lm == null && create) {
            LoaderManagerImpl lm2 = new LoaderManagerImpl(who, this, started);
            this.mAllLoaderManagers.put(who, lm2);
            return lm2;
        }
        if (started && lm != null && !lm.mStarted) {
            lm.doStart();
            return lm;
        }
        return lm;
    }

    SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        boolean retainLoaders = false;
        if (this.mAllLoaderManagers != null) {
            int N = this.mAllLoaderManagers.size();
            LoaderManagerImpl[] loaders = new LoaderManagerImpl[N];
            for (int i = N - 1; i >= 0; i--) {
                loaders[i] = (LoaderManagerImpl) this.mAllLoaderManagers.valueAt(i);
            }
            boolean doRetainLoaders = getRetainLoaders();
            for (int i2 = 0; i2 < N; i2++) {
                LoaderManagerImpl lm = loaders[i2];
                if (!lm.mRetaining && doRetainLoaders) {
                    if (!lm.mStarted) {
                        lm.doStart();
                    }
                    lm.doRetain();
                }
                if (lm.mRetaining) {
                    retainLoaders = true;
                } else {
                    lm.doDestroy();
                    this.mAllLoaderManagers.remove(lm.mWho);
                }
            }
        }
        if (retainLoaders) {
            return this.mAllLoaderManagers;
        }
        return null;
    }

    void restoreLoaderNonConfig(SimpleArrayMap<String, LoaderManager> loaderManagers) {
        if (loaderManagers != null) {
            int N = loaderManagers.size();
            for (int i = 0; i < N; i++) {
                ((LoaderManagerImpl) loaderManagers.valueAt(i)).updateHostController(this);
            }
        }
        this.mAllLoaderManagers = loaderManagers;
    }

    void dumpLoaders(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        writer.print(prefix);
        writer.print("mLoadersStarted=");
        writer.println(this.mLoadersStarted);
        if (this.mLoaderManager != null) {
            writer.print(prefix);
            writer.print("Loader Manager ");
            writer.print(Integer.toHexString(System.identityHashCode(this.mLoaderManager)));
            writer.println(":");
            this.mLoaderManager.dump(prefix + "  ", fd, writer, args);
        }
    }
}
