package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.xmpush.thrift.ae;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class x {
    public static void a(Context context, ae aeVar) {
        com.xiaomi.channel.commonutils.logger.b.a("need to update local info with: " + aeVar.i());
        String str = aeVar.i().get("accept_time");
        if (str != null) {
            MiPushClient.removeAcceptTime(context);
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length == 2) {
                MiPushClient.addAcceptTime(context, strArrSplit[0], strArrSplit[1]);
                if ("00:00".equals(strArrSplit[0]) && "00:00".equals(strArrSplit[1])) {
                    a.a(context).a(true);
                } else {
                    a.a(context).a(false);
                }
            }
        }
        String str2 = aeVar.i().get("aliases");
        if (str2 != null) {
            MiPushClient.removeAllAliases(context);
            if (!"".equals(str2)) {
                String[] strArrSplit2 = str2.split(",");
                for (String str3 : strArrSplit2) {
                    MiPushClient.addAlias(context, str3);
                }
            }
        }
        String str4 = aeVar.i().get("topics");
        if (str4 != null) {
            MiPushClient.removeAllTopics(context);
            if (!"".equals(str4)) {
                String[] strArrSplit3 = str4.split(",");
                for (String str5 : strArrSplit3) {
                    MiPushClient.addTopic(context, str5);
                }
            }
        }
        String str6 = aeVar.i().get("user_accounts");
        if (str6 != null) {
            MiPushClient.removeAllAccounts(context);
            if ("".equals(str6)) {
                return;
            }
            String[] strArrSplit4 = str6.split(",");
            for (String str7 : strArrSplit4) {
                MiPushClient.addAccount(context, str7);
            }
        }
    }

    public static void a(Context context, boolean z) {
        com.xiaomi.channel.commonutils.misc.f.a(context).a(new y(context, z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(List<String> list) {
        String strA = d.a(d(list));
        return (TextUtils.isEmpty(strA) || strA.length() <= 4) ? "" : strA.substring(0, 4).toLowerCase();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d(List<String> list) {
        if (com.xiaomi.channel.commonutils.misc.b.a(list)) {
            return "";
        }
        ArrayList<String> arrayList = new ArrayList(list);
        Collections.sort(arrayList, Collator.getInstance(Locale.CHINA));
        String str = "";
        for (String str2 : arrayList) {
            if (!TextUtils.isEmpty(str)) {
                str = str + ",";
            }
            str = str + str2;
        }
        return str;
    }
}
