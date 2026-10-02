package com.xiaocong.smarthome.httplib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.xiaocong.smarthome.httplib.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class XcBaseFragment extends Fragment {
    protected FragmentActivity mActivity;
    private View rootView = null;
    protected boolean hasTitleBar = false;
    protected Handler mHandler = new Handler() { // from class: com.xiaocong.smarthome.httplib.base.XcBaseFragment.1
        @Override // android.os.Handler
        public void dispatchMessage(Message msg) {
            XcBaseFragment.this.handleMessage(msg);
        }
    };

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.rootView == null) {
            this.rootView = inflater.inflate(getLayoutId(), (ViewGroup) null);
            initView();
            initAdapter();
            initData();
            addListener();
        } else {
            ViewGroup parent = (ViewGroup) this.rootView.getParent();
            if (parent != null) {
                parent.removeView(this.rootView);
            }
        }
        return this.rootView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onViewCreated$0(View v, MotionEvent event) {
        return true;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.rootView.setOnTouchListener(XcBaseFragment$.Lambda.1.lambdaFactory$());
        super.onViewCreated(view, savedInstanceState);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (FragmentActivity) activity;
    }

    public void startActivityForNew(Context context, Intent intent) {
        context.startActivity(intent);
        animationForNew(context);
    }

    private void animationForNew(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.main_translatex100to0, R.anim.main_translatex0tof100);
    }

    public void addListener() {
    }

    public void initAdapter() {
    }

    public <T extends View> T $(int i) {
        return (T) this.rootView.findViewById(i);
    }

    protected void handleMessage(Message msg) {
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
