package com.xiaomi.push.service;

import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ad {
    private static Object a = new Object();
    private static Map<String, Queue<String>> b = new HashMap();

    public static boolean a(XMPushService xMPushService, String str, String str2) {
        synchronized (a) {
            SharedPreferences sharedPreferences = xMPushService.getSharedPreferences("push_message_ids", 0);
            Queue<String> linkedList = b.get(str);
            if (linkedList == null) {
                String[] strArrSplit = sharedPreferences.getString(str, "").split(",");
                linkedList = new LinkedList<>();
                for (String str3 : strArrSplit) {
                    linkedList.add(str3);
                }
                b.put(str, linkedList);
            }
            if (linkedList.contains(str2)) {
                return true;
            }
            linkedList.add(str2);
            if (linkedList.size() > 25) {
                linkedList.poll();
            }
            String strA = com.xiaomi.channel.commonutils.string.d.a(linkedList, ",");
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putString(str, strA);
            editorEdit.commit();
            return false;
        }
    }
}
