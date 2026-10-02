package com.huawei.hms.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.huawei.hms.api.internal.IPCTransport;
import com.huawei.hms.c.g;
import com.huawei.hms.core.aidl.RequestHeader;
import com.huawei.hms.core.aidl.f;
import com.huawei.hms.support.api.ResolveResult;
import com.huawei.hms.support.api.client.BundleResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.entity.auth.PermissionInfo;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.huawei.hms.support.api.entity.core.ConnectInfo;
import com.huawei.hms.support.api.entity.core.ConnectResp;
import com.huawei.hms.support.api.entity.core.DisconnectInfo;
import com.huawei.hms.support.api.entity.core.DisconnectResp;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HuaweiApiClientImpl extends HuaweiApiClient implements ServiceConnection {
    private final Context a;
    private String b;
    private final String c;
    private volatile com.huawei.hms.core.aidl.e d;
    private String e;
    private List<Scope> g;
    private List<PermissionInfo> h;
    private Map<Api<?>, Api.ApiOptions> i;
    private SubAppInfo j;
    private HuaweiApiClient.ConnectionCallbacks k;
    private HuaweiApiClient.OnConnectionFailedListener l;
    private AtomicInteger f = new AtomicInteger(1);
    private Handler m = null;

    public HuaweiApiClientImpl(Context context) {
        this.a = context;
        this.c = g.a(context);
        this.b = this.c;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public Context getContext() {
        return this.a;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getPackageName() {
        return this.a.getPackageName();
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getAppID() {
        return this.b;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getTransportName() {
        return IPCTransport.class.getName();
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public final SubAppInfo getSubAppInfo() {
        return this.j;
    }

    public List<Scope> getScopes() {
        return this.g;
    }

    public List<PermissionInfo> getPermissionInfos() {
        return this.h;
    }

    public Map<Api<?>, Api.ApiOptions> getApiMap() {
        return this.i;
    }

    public com.huawei.hms.core.aidl.e getService() {
        return this.d;
    }

    @Override // com.huawei.hms.support.api.client.ApiClient
    public String getSessionId() {
        return this.e;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void connect() {
        com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "====== HMSSDK version: 20502300 ======");
        int i = this.f.get();
        com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter connect, Connection Status: " + i);
        if (i != 3 && i != 5 && i != 2 && i != 4) {
            this.b = TextUtils.isEmpty(this.c) ? g.a(this.a) : this.c;
            int iA = com.huawei.hms.api.internal.e.a(this.a);
            com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "In connect, isHuaweiMobileServicesAvailable result: " + iA);
            if (iA == 0) {
                a(5);
                if (!a()) {
                    a(1);
                    com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "In connect, bind core service fail");
                    if (this.l != null) {
                        this.l.onConnectionFailed(new ConnectionResult(6));
                        return;
                    }
                    return;
                }
                b();
                return;
            }
            if (this.l != null) {
                this.l.onConnectionFailed(new ConnectionResult(iA));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        this.f.set(i);
    }

    private boolean a() {
        Intent intent = new Intent(HuaweiApiAvailability.SERVICES_ACTION);
        intent.setPackage(HuaweiApiAvailability.SERVICES_PACKAGE);
        return this.a.bindService(intent, this, 1);
    }

    private void b() {
        if (this.m != null) {
            this.m.removeMessages(2);
        } else {
            this.m = new Handler(Looper.getMainLooper(), new com.huawei.hms.api.b(this));
        }
        this.m.sendEmptyMessageDelayed(2, 3000L);
    }

    private void c() {
        if (this.m != null) {
            this.m.removeMessages(2);
            this.m = null;
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void disconnect() {
        int i = this.f.get();
        com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter disconnect, Connection Status: " + i);
        switch (i) {
            case 2:
                a(4);
                break;
            case 3:
                a(4);
                d();
                break;
            case 5:
                c();
                a(4);
                break;
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiClient, com.huawei.hms.support.api.client.ApiClient
    public boolean isConnected() {
        return this.f.get() == 3;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean isConnecting() {
        int i = this.f.get();
        return i == 2 || i == 5;
    }

    public void setApiMap(Map<Api<?>, Api.ApiOptions> map) {
        this.i = map;
    }

    public void setScopes(List<Scope> list) {
        this.g = list;
    }

    public void setPermissionInfos(List<PermissionInfo> list) {
        this.h = list;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public boolean setSubAppInfo(SubAppInfo subAppInfo) {
        if (subAppInfo == null) {
            return false;
        }
        String subAppID = subAppInfo.getSubAppID();
        if (TextUtils.isEmpty(subAppID)) {
            return false;
        }
        if (subAppID.equals(TextUtils.isEmpty(this.c) ? g.a(this.a) : this.c)) {
            return false;
        }
        this.j = new SubAppInfo(subAppInfo);
        return true;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void setConnectionCallbacks(HuaweiApiClient.ConnectionCallbacks connectionCallbacks) {
        this.k = connectionCallbacks;
    }

    @Override // com.huawei.hms.api.HuaweiApiClient
    public void setConnectionFailedListener(HuaweiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.l = onConnectionFailedListener;
    }

    private void d() {
        com.huawei.hms.support.api.a.a.a(this, e()).setResultCallback(new b(this, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class b implements ResultCallback<ResolveResult<DisconnectResp>> {
        private b() {
        }

        /* synthetic */ b(HuaweiApiClientImpl huaweiApiClientImpl, com.huawei.hms.api.b bVar) {
            this();
        }

        @Override // com.huawei.hms.support.api.client.ResultCallback
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onResult(ResolveResult<DisconnectResp> resolveResult) {
            new Handler(Looper.getMainLooper()).post(new e(this, resolveResult));
        }
    }

    private DisconnectInfo e() {
        ArrayList arrayList = new ArrayList();
        if (this.i != null) {
            Iterator<Api<?>> it = this.i.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getApiName());
            }
        }
        return new DisconnectInfo(this.g, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ResolveResult<DisconnectResp> resolveResult) {
        com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter onDisconnectionResult, disconnect from server result: " + resolveResult.getStatus().getStatusCode());
        h();
        a(1);
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter onServiceConnected.");
        c();
        this.d = com.huawei.hms.core.aidl.e.a.a(iBinder);
        if (this.d == null) {
            com.huawei.hms.support.log.a.d("HuaweiApiClientImpl", "In onServiceConnected, mCoreService must not be null.");
            h();
            a(1);
            if (this.l != null) {
                this.l.onConnectionFailed(new ConnectionResult(10));
                return;
            }
            return;
        }
        if (this.f.get() == 5) {
            a(2);
            f();
        } else if (this.f.get() != 3) {
            h();
        }
    }

    private void f() {
        com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter sendConnectApiServceRequest.");
        com.huawei.hms.support.api.a.a.a(this, g()).setResultCallback(new a(this, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class a implements ResultCallback<ResolveResult<ConnectResp>> {
        private a() {
        }

        /* synthetic */ a(HuaweiApiClientImpl huaweiApiClientImpl, com.huawei.hms.api.b bVar) {
            this();
        }

        @Override // com.huawei.hms.support.api.client.ResultCallback
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onResult(ResolveResult<ConnectResp> resolveResult) {
            new Handler(Looper.getMainLooper()).post(new d(this, resolveResult));
        }
    }

    private ConnectInfo g() {
        String strD = new com.huawei.hms.c.e(this.a).d(this.a.getPackageName());
        String str = strD == null ? Constants.MAIN_VERSION_TAG : strD;
        ArrayList arrayList = new ArrayList();
        if (this.i != null) {
            Iterator<Api<?>> it = this.i.keySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getApiName());
            }
        }
        return new ConnectInfo(arrayList, this.g, str, this.j == null ? null : this.j.getSubAppID());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ResolveResult<ConnectResp> resolveResult) {
        ConnectResp value = resolveResult.getValue();
        if (value != null) {
            this.e = value.sessionId;
        }
        String subAppID = this.j == null ? null : this.j.getSubAppID();
        if (!TextUtils.isEmpty(subAppID)) {
            this.b = subAppID;
        }
        int statusCode = resolveResult.getStatus().getStatusCode();
        com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter onConnectionResult, connect to server result: " + statusCode);
        if (Status.SUCCESS.equals(resolveResult.getStatus())) {
            if (resolveResult.getValue() != null) {
                com.huawei.hms.api.internal.g.a().a(resolveResult.getValue().protocolVersion);
            }
            a(3);
            if (this.k != null) {
                this.k.onConnected();
                return;
            }
            return;
        }
        if (resolveResult.getStatus() != null && resolveResult.getStatus().getStatusCode() == 1001) {
            h();
            a(1);
            if (this.k != null) {
                this.k.onConnectionSuspended(3);
                return;
            }
            return;
        }
        h();
        a(1);
        if (this.l != null) {
            this.l.onConnectionFailed(new ConnectionResult(statusCode));
        }
    }

    private void h() {
        g.a(this.a, this);
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        com.huawei.hms.support.log.a.b("HuaweiApiClientImpl", "Enter onServiceDisconnected.");
        this.d = null;
        a(1);
        if (this.k != null) {
            this.k.onConnectionSuspended(1);
        }
    }

    public int asyncRequest(Bundle bundle, String str, int i, ResultCallback<BundleResult> resultCallback) {
        if (resultCallback == null || str == null || bundle == null) {
            return CommonCode.ErrorCode.ARGUMENTS_INVALID;
        }
        if (!isConnected()) {
            return CommonCode.ErrorCode.CLIENT_API_INVALID;
        }
        com.huawei.hms.core.aidl.b bVar = new com.huawei.hms.core.aidl.b(str, i);
        f fVarA = com.huawei.hms.core.aidl.a.a(bVar.c());
        bVar.a(bundle);
        bVar.b = fVarA.a(new RequestHeader(getAppID(), getPackageName(), 20502300, getSessionId()), new Bundle());
        try {
            getService().a(bVar, new c(this, resultCallback));
            return 0;
        } catch (RemoteException e) {
            return CommonCode.ErrorCode.INTERNAL_ERROR;
        }
    }
}
