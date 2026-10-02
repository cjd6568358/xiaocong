package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import com.bumptech.glide.RequestManager;
import java.util.HashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SupportRequestManagerFragment extends Fragment {
    private final HashSet<SupportRequestManagerFragment> childRequestManagerFragments;
    private final ActivityFragmentLifecycle lifecycle;
    private RequestManager requestManager;
    private final RequestManagerTreeNode requestManagerTreeNode;
    private SupportRequestManagerFragment rootRequestManagerFragment;

    public SupportRequestManagerFragment() {
        this(new ActivityFragmentLifecycle());
    }

    @SuppressLint({"ValidFragment"})
    public SupportRequestManagerFragment(ActivityFragmentLifecycle lifecycle) {
        this.requestManagerTreeNode = new SupportFragmentRequestManagerTreeNode();
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

    private void addChildRequestManagerFragment(SupportRequestManagerFragment child) {
        this.childRequestManagerFragments.add(child);
    }

    private void removeChildRequestManagerFragment(SupportRequestManagerFragment child) {
        this.childRequestManagerFragments.remove(child);
    }

    @Override // android.support.v4.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.rootRequestManagerFragment = RequestManagerRetriever.get().getSupportRequestManagerFragment(getActivity().getSupportFragmentManager());
        if (this.rootRequestManagerFragment != this) {
            this.rootRequestManagerFragment.addChildRequestManagerFragment(this);
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        if (this.rootRequestManagerFragment != null) {
            this.rootRequestManagerFragment.removeChildRequestManagerFragment(this);
            this.rootRequestManagerFragment = null;
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onStart() {
        super.onStart();
        this.lifecycle.onStart();
    }

    @Override // android.support.v4.app.Fragment
    public void onStop() {
        super.onStop();
        this.lifecycle.onStop();
    }

    @Override // android.support.v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.lifecycle.onDestroy();
    }

    @Override // android.support.v4.app.Fragment, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        if (this.requestManager != null) {
            this.requestManager.onLowMemory();
        }
    }

    private class SupportFragmentRequestManagerTreeNode implements RequestManagerTreeNode {
        private SupportFragmentRequestManagerTreeNode() {
        }
    }
}
