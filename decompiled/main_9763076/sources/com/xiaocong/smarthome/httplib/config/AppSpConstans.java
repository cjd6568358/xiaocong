package com.xiaocong.smarthome.httplib.config;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.utils.SpUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AppSpConstans {
    private String mClientId;
    private String mClientKey;
    private String mLive;
    private String mToken;

    public static AppSpConstans getInstance() {
        return AppSpConstansHolder.access$000();
    }

    public void initConstans(Context context) {
        this.mClientId = (String) SpUtils.getFromLocal(context, "3013-client-2828", "vic_jastion_dd", Constants.MAIN_VERSION_TAG);
        this.mLive = (String) SpUtils.getFromLocal(context, "3013-client-2828", "vic_klmn_jast_like", Constants.MAIN_VERSION_TAG);
        this.mToken = (String) SpUtils.getFromLocal(context, "NLC_ahe_9l", "NLC_ahe_9l", Constants.MAIN_VERSION_TAG);
        this.mClientKey = (String) SpUtils.getFromLocal(context, "3013-client-2828", "vic_klmn_jast_dd", Constants.MAIN_VERSION_TAG);
    }

    public String getClientId(Context context) {
        if (TextUtils.isEmpty(this.mClientId)) {
            this.mClientId = (String) SpUtils.getFromLocal(context, "3013-client-2828", "vic_jastion_dd", Constants.MAIN_VERSION_TAG);
        }
        return this.mClientId;
    }

    public String getToken(Context context) {
        if (TextUtils.isEmpty(this.mToken)) {
            this.mToken = (String) SpUtils.getFromLocal(context, "NLC_ahe_9l", "NLC_ahe_9l", Constants.MAIN_VERSION_TAG);
        }
        return this.mToken;
    }

    public void clearParams() {
        this.mClientKey = Constants.MAIN_VERSION_TAG;
        this.mClientId = Constants.MAIN_VERSION_TAG;
        this.mLive = Constants.MAIN_VERSION_TAG;
        this.mToken = Constants.MAIN_VERSION_TAG;
    }
}
