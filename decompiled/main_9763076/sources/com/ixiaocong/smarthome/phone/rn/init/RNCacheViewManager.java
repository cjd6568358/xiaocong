package com.ixiaocong.smarthome.phone.rn.init;

import android.app.Activity;
import android.content.Intent;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.ixiaocong.smarthome.phone.android.common.utils.FileUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNCacheViewManager {
    private ReactRootView mRootView;
    private String mPath = "/ixiaocong/js/";
    private Map<String, ReactInstanceManager> mParams = new HashMap();
    private Map<String, ReactRootView> mViewParams = new HashMap();

    public static RNCacheViewManager getInstance() {
        return RNCacheViewManagerHolder.viewManager;
    }

    public void init(Activity activity, String productId, Intent intent, ReactInstanceManager manager) {
        if (FileUtils.checkFileExists(activity, this.mPath + String.valueOf(productId) + ".jsbundle")) {
            if (this.mViewParams.get(productId) == null) {
                initRootView(activity);
                this.mRootView.startReactApplication(manager, productId, intent.getExtras());
                this.mViewParams.put(productId, this.mRootView);
            }
            if (this.mParams.get(productId) == null) {
                this.mParams.put(productId, manager);
            }
        }
    }

    public ReactInstanceManager getReactInstanceManager(String productId) {
        return this.mParams.get(productId);
    }

    public void removeManager(String productId) {
        if (this.mParams != null && this.mParams.containsKey(productId)) {
            this.mParams.remove(productId);
        }
    }

    public void removeCache(String productId) {
        if (this.mParams != null && this.mParams.containsKey(productId)) {
            this.mParams.remove(productId);
        }
        if (this.mViewParams != null && this.mViewParams.containsKey(productId)) {
            this.mViewParams.remove(productId);
        }
    }

    public boolean isCacheJs(int productId) {
        return (this.mViewParams == null || this.mViewParams.get(String.valueOf(productId)) == null) ? false : true;
    }

    private ReactRootView initRootView(Activity activity) {
        try {
            this.mRootView = new ReactRootView(activity);
            return this.mRootView;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static class RNCacheViewManagerHolder {
        private static final RNCacheViewManager viewManager = new RNCacheViewManager();
    }
}
