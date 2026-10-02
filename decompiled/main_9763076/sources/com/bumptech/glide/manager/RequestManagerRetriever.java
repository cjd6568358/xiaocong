package com.bumptech.glide.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.Util;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RequestManagerRetriever implements Handler.Callback {
    private static final RequestManagerRetriever INSTANCE = new RequestManagerRetriever();
    private volatile RequestManager applicationManager;
    final Map<FragmentManager, RequestManagerFragment> pendingRequestManagerFragments = new HashMap();
    final Map<android.support.v4.app.FragmentManager, SupportRequestManagerFragment> pendingSupportRequestManagerFragments = new HashMap();
    private final Handler handler = new Handler(Looper.getMainLooper(), this);

    public static RequestManagerRetriever get() {
        return INSTANCE;
    }

    RequestManagerRetriever() {
    }

    private RequestManager getApplicationManager(Context context) {
        if (this.applicationManager == null) {
            synchronized (this) {
                if (this.applicationManager == null) {
                    this.applicationManager = new RequestManager(context.getApplicationContext(), new ApplicationLifecycle(), new EmptyRequestManagerTreeNode());
                }
            }
        }
        return this.applicationManager;
    }

    public RequestManager get(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("You cannot start a load on a null Context");
        }
        if (Util.isOnMainThread() && !(context instanceof Application)) {
            if (context instanceof FragmentActivity) {
                return get((FragmentActivity) context);
            }
            if (context instanceof Activity) {
                return get((Activity) context);
            }
            if (context instanceof ContextWrapper) {
                return get(((ContextWrapper) context).getBaseContext());
            }
        }
        return getApplicationManager(context);
    }

    public RequestManager get(FragmentActivity activity) {
        if (Util.isOnBackgroundThread()) {
            return get(activity.getApplicationContext());
        }
        assertNotDestroyed(activity);
        android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
        return supportFragmentGet(activity, fm);
    }

    public RequestManager get(Fragment fragment) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You cannot start a load on a fragment before it is attached");
        }
        if (Util.isOnBackgroundThread()) {
            return get(fragment.getActivity().getApplicationContext());
        }
        android.support.v4.app.FragmentManager fm = fragment.getChildFragmentManager();
        return supportFragmentGet(fragment.getActivity(), fm);
    }

    @TargetApi(11)
    public RequestManager get(Activity activity) {
        if (Util.isOnBackgroundThread() || Build.VERSION.SDK_INT < 11) {
            return get(activity.getApplicationContext());
        }
        assertNotDestroyed(activity);
        FragmentManager fm = activity.getFragmentManager();
        return fragmentGet(activity, fm);
    }

    @TargetApi(17)
    private static void assertNotDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed()) {
            throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
        }
    }

    @TargetApi(17)
    RequestManagerFragment getRequestManagerFragment(FragmentManager fm) {
        RequestManagerFragment current = (RequestManagerFragment) fm.findFragmentByTag("com.bumptech.glide.manager");
        if (current == null) {
            RequestManagerFragment current2 = this.pendingRequestManagerFragments.get(fm);
            if (current2 == null) {
                RequestManagerFragment current3 = new RequestManagerFragment();
                this.pendingRequestManagerFragments.put(fm, current3);
                fm.beginTransaction().add(current3, "com.bumptech.glide.manager").commitAllowingStateLoss();
                this.handler.obtainMessage(1, fm).sendToTarget();
                return current3;
            }
            return current2;
        }
        return current;
    }

    @TargetApi(11)
    RequestManager fragmentGet(Context context, FragmentManager fm) {
        RequestManagerFragment current = getRequestManagerFragment(fm);
        RequestManager requestManager = current.getRequestManager();
        if (requestManager == null) {
            RequestManager requestManager2 = new RequestManager(context, current.getLifecycle(), current.getRequestManagerTreeNode());
            current.setRequestManager(requestManager2);
            return requestManager2;
        }
        return requestManager;
    }

    SupportRequestManagerFragment getSupportRequestManagerFragment(android.support.v4.app.FragmentManager fm) {
        SupportRequestManagerFragment current = (SupportRequestManagerFragment) fm.findFragmentByTag("com.bumptech.glide.manager");
        if (current == null) {
            SupportRequestManagerFragment current2 = this.pendingSupportRequestManagerFragments.get(fm);
            if (current2 == null) {
                SupportRequestManagerFragment current3 = new SupportRequestManagerFragment();
                this.pendingSupportRequestManagerFragments.put(fm, current3);
                fm.beginTransaction().add(current3, "com.bumptech.glide.manager").commitAllowingStateLoss();
                this.handler.obtainMessage(2, fm).sendToTarget();
                return current3;
            }
            return current2;
        }
        return current;
    }

    RequestManager supportFragmentGet(Context context, android.support.v4.app.FragmentManager fm) {
        SupportRequestManagerFragment current = getSupportRequestManagerFragment(fm);
        RequestManager requestManager = current.getRequestManager();
        if (requestManager == null) {
            RequestManager requestManager2 = new RequestManager(context, current.getLifecycle(), current.getRequestManagerTreeNode());
            current.setRequestManager(requestManager2);
            return requestManager2;
        }
        return requestManager;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        boolean handled = true;
        Object removed = null;
        Object key = null;
        switch (message.what) {
            case 1:
                FragmentManager fm = (FragmentManager) message.obj;
                key = fm;
                removed = this.pendingRequestManagerFragments.remove(fm);
                break;
            case 2:
                android.support.v4.app.FragmentManager supportFm = (android.support.v4.app.FragmentManager) message.obj;
                key = supportFm;
                removed = this.pendingSupportRequestManagerFragments.remove(supportFm);
                break;
            default:
                handled = false;
                break;
        }
        if (handled && removed == null && Log.isLoggable("RMRetriever", 5)) {
            Log.w("RMRetriever", "Failed to remove expected request manager fragment, manager: " + key);
        }
        return handled;
    }
}
