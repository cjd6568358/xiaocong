package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class a implements IDiffDevOAuth {
    private d Z;
    private Handler handler = null;
    private List<OAuthListener> Y = new ArrayList();
    private OAuthListener aa = new b(this);

    static /* synthetic */ d c(a aVar) {
        aVar.Z = null;
        return null;
    }

    @Override // com.tencent.mm.opensdk.diffdev.IDiffDevOAuth
    public final void addListener(OAuthListener oAuthListener) {
        if (this.Y.contains(oAuthListener)) {
            return;
        }
        this.Y.add(oAuthListener);
    }

    @Override // com.tencent.mm.opensdk.diffdev.IDiffDevOAuth
    public final boolean auth(String str, String str2, String str3, String str4, String str5, OAuthListener oAuthListener) {
        Log.i("MicroMsg.SDK.DiffDevOAuth", "start auth, appId = " + str);
        if (str == null || str.length() <= 0 || str2 == null || str2.length() <= 0) {
            Log.d("MicroMsg.SDK.DiffDevOAuth", String.format("auth fail, invalid argument, appId = %s, scope = %s", str, str2));
            return false;
        }
        if (this.handler == null) {
            this.handler = new Handler(Looper.getMainLooper());
        }
        addListener(oAuthListener);
        if (this.Z != null) {
            Log.d("MicroMsg.SDK.DiffDevOAuth", "auth, already running, no need to start auth again");
            return true;
        }
        this.Z = new d(str, str2, str3, str4, str5, this.aa);
        d dVar = this.Z;
        if (Build.VERSION.SDK_INT >= 11) {
            dVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        } else {
            dVar.execute(new Void[0]);
        }
        return true;
    }

    @Override // com.tencent.mm.opensdk.diffdev.IDiffDevOAuth
    public final void detach() {
        Log.i("MicroMsg.SDK.DiffDevOAuth", "detach");
        this.Y.clear();
        stopAuth();
    }

    @Override // com.tencent.mm.opensdk.diffdev.IDiffDevOAuth
    public final void removeAllListeners() {
        this.Y.clear();
    }

    @Override // com.tencent.mm.opensdk.diffdev.IDiffDevOAuth
    public final void removeListener(OAuthListener oAuthListener) {
        this.Y.remove(oAuthListener);
    }

    @Override // com.tencent.mm.opensdk.diffdev.IDiffDevOAuth
    public final boolean stopAuth() {
        boolean zQ;
        Log.i("MicroMsg.SDK.DiffDevOAuth", "stopAuth");
        try {
            zQ = this.Z == null ? true : this.Z.q();
        } catch (Exception e) {
            Log.w("MicroMsg.SDK.DiffDevOAuth", "stopAuth fail, ex = " + e.getMessage());
            zQ = false;
        }
        this.Z = null;
        return zQ;
    }
}
