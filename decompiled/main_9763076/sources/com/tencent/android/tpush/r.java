package com.tencent.android.tpush;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class r implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ long b;

    r(Context context, long j) {
        this.a = context;
        this.b = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        SharedPreferences defaultSharedPreferences;
        if (TpnsSecurity.checkTpnsSecurityLibSo(this.a) && (defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.a)) != null) {
            SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit();
            editorEdit.putString(XGPushConfig.TPUSH_ACCESS_ID, Rijndael.encrypt(String.valueOf(this.b)));
            editorEdit.commit();
        }
    }
}
