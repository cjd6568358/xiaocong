package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import com.bumptech.glide.RequestManager;
import java.util.HashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@TargetApi(11)
public class RequestManagerFragment extends Fragment {
    private final HashSet<RequestManagerFragment> childRequestManagerFragments;
    private final ActivityFragmentLifecycle lifecycle;
    private RequestManager requestManager;
    private final RequestManagerTreeNode requestManagerTreeNode;
    private RequestManagerFragment rootRequestManagerFragment;

    public RequestManagerFragment() {
        this(new ActivityFragmentLifecycle());
    }

    @SuppressLint({"ValidFragment"})
    RequestManagerFragment(ActivityFragmentLifecycle lifecycle) {
        this.requestManagerTreeNode = new FragmentRequestManagerTreeNode();
        this.childRequestManagerFragments = new HashSet<>();
        this.lifecycle = lifecycle;
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    ActivityFragmentLifecycle getLifecycle() {
        return this.lifecycle;
    }

    public RequestManager getRequestManager() {
        return this.requestManager;
    }

    public RequestManagerTreeNode getRequestManagerTreeNode() {
        return this.requestManagerTreeNode;
    }

    private void addChildRequestManagerFragment(RequestManagerFragment child) {
        this.childRequestManagerFragments.add(child);
    }

    private void removeChildRequestManagerFragment(RequestManagerFragment child) {
        this.childRequestManagerFragments.remove(child);
    }

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.rootRequestManagerFragment = RequestManagerRetriever.get().getRequestManagerFragment(getActivity().getFragmentManager());
        if (this.rootRequestManagerFragment != this) {
            this.rootRequestManagerFragment.addChildRequestManagerFragment(this);
        }
    }

    @Override // android.app.Fragment
    public void onDetach() {
        super.onDetach();
        if (this.rootRequestManagerFragment != null) {
            this.rootRequestManagerFragment.removeChildRequestManagerFragment(this);
            this.rootRequestManagerFragment = null;
        }
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        this.lifecycle.onStart();
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        this.lifecycle.onStop();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.lifecycle.onDestroy();
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        if (this.requestManager != null) {
            this.requestManager.onTrimMemory(level);
        }
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onLowMemory() {
        if (this.requestManager != null) {
            this.requestManager.onLowMemory();
        }
    }

    private class FragmentRequestManagerTreeNode implements RequestManagerTreeNode {
        private FragmentRequestManagerTreeNode() {
        }
    }
}
