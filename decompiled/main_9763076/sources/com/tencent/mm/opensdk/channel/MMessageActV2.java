package com.tencent.mm.opensdk.channel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.opensdk.channel.a.b;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.d;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MMessageActV2 {
    public static final String DEFAULT_ENTRY_CLASS_NAME = ".wxapi.WXEntryActivity";
    public static final String MM_ENTRY_PACKAGE_NAME = "com.tencent.mm";
    public static final String MM_MSG_ENTRY_CLASS_NAME = "com.tencent.mm.plugin.base.stub.WXEntryActivity";
    private static final String TAG = "MicroMsg.SDK.MMessageAct";

    public static class Args {
        public static final int INVALID_FLAGS = -1;
        public Bundle bundle;
        public String content;
        public int flags = -1;
        public String targetClassName;
        public String targetPkgName;
        public String token;

        public String toString() {
            return "targetPkgName:" + this.targetPkgName + ", targetClassName:" + this.targetClassName + ", content:" + this.content + ", flags:" + this.flags + ", bundle:" + this.bundle;
        }
    }

    public static boolean send(Context context, Args args) {
        if (context == null || args == null) {
            Log.e(TAG, "send fail, invalid argument");
            return false;
        }
        if (d.i(args.targetPkgName)) {
            Log.e(TAG, "send fail, invalid targetPkgName, targetPkgName = " + args.targetPkgName);
            return false;
        }
        if (d.i(args.targetClassName)) {
            args.targetClassName = args.targetPkgName + DEFAULT_ENTRY_CLASS_NAME;
        }
        Log.d(TAG, "send, targetPkgName = " + args.targetPkgName + ", targetClassName = " + args.targetClassName);
        Intent intent = new Intent();
        intent.setClassName(args.targetPkgName, args.targetClassName);
        if (args.bundle != null) {
            intent.putExtras(args.bundle);
        }
        String packageName = context.getPackageName();
        intent.putExtra(ConstantsAPI.SDK_VERSION, 620824064);
        intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
        intent.putExtra(ConstantsAPI.CONTENT, args.content);
        intent.putExtra(ConstantsAPI.CHECK_SUM, b.a(args.content, 620824064, packageName));
        intent.putExtra(ConstantsAPI.TOKEN, args.token);
        if (args.flags == -1) {
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY).addFlags(134217728);
        } else {
            intent.setFlags(args.flags);
        }
        try {
            context.startActivity(intent);
            Log.d(TAG, "send mm message, intent=" + intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "send fail, ex = " + e.getMessage());
            return false;
        }
    }
}
