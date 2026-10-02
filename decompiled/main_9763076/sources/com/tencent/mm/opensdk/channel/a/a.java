package com.tencent.mm.opensdk.channel.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.d;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class a {

    /* JADX INFO: renamed from: com.tencent.mm.opensdk.channel.a.a$a, reason: collision with other inner class name */
    public static class C0041a {
        public String W;
        public long X;
        public String action;
        public Bundle bundle;
        public String content;
    }

    public static boolean a(Context context, C0041a c0041a) {
        if (context == null) {
            Log.e("MicroMsg.SDK.MMessage", "send fail, invalid argument");
            return false;
        }
        if (d.i(c0041a.action)) {
            Log.e("MicroMsg.SDK.MMessage", "send fail, action is null");
            return false;
        }
        String str = d.i(c0041a.W) ? null : c0041a.W + ".permission.MM_MESSAGE";
        Intent intent = new Intent(c0041a.action);
        if (c0041a.bundle != null) {
            intent.putExtras(c0041a.bundle);
        }
        String packageName = context.getPackageName();
        intent.putExtra(ConstantsAPI.SDK_VERSION, 620824064);
        intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
        intent.putExtra(ConstantsAPI.CONTENT, c0041a.content);
        intent.putExtra(ConstantsAPI.APP_SUPORT_CONTENT_TYPE, c0041a.X);
        intent.putExtra(ConstantsAPI.CHECK_SUM, b.a(c0041a.content, 620824064, packageName));
        context.sendBroadcast(intent, str);
        Log.d("MicroMsg.SDK.MMessage", "send mm message, intent=" + intent + ", perm=" + str);
        return true;
    }
}
