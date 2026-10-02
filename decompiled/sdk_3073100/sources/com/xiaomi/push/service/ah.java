package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ah {
    private static volatile ah b;
    protected SharedPreferences a;

    private ah(Context context) {
        this.a = context.getSharedPreferences("mipush_extra", 0);
    }

    public static ah a(Context context) {
        if (b == null) {
            synchronized (ah.class) {
                if (b == null) {
                    b = new ah(context);
                }
            }
        }
        return b;
    }

    private String a(int i) {
        return "normal_oc_" + i;
    }

    private void a(SharedPreferences.Editor editor, Pair<Integer, Object> pair, String str) {
        if (pair.second instanceof Integer) {
            editor.putInt(str, ((Integer) pair.second).intValue());
            return;
        }
        if (pair.second instanceof Long) {
            editor.putLong(str, ((Long) pair.second).longValue());
        } else if (pair.second instanceof String) {
            editor.putString(str, (String) pair.second);
        } else if (pair.second instanceof Boolean) {
            editor.putBoolean(str, ((Boolean) pair.second).booleanValue());
        }
    }

    private String b(int i) {
        return "custom_oc_" + i;
    }

    public void a(List<Pair<Integer, Object>> list) {
        if (com.xiaomi.channel.commonutils.misc.b.a(list)) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.a.edit();
        for (Pair<Integer, Object> pair : list) {
            if (pair.first != null && pair.second != null) {
                a(editorEdit, pair, a(((Integer) pair.first).intValue()));
            }
        }
        editorEdit.commit();
    }

    public boolean a(int i, boolean z) {
        String strB = b(i);
        if (this.a.contains(strB)) {
            return this.a.getBoolean(strB, false);
        }
        String strA = a(i);
        return this.a.contains(strA) ? this.a.getBoolean(strA, false) : z;
    }

    public void b(List<Pair<Integer, Object>> list) {
        if (com.xiaomi.channel.commonutils.misc.b.a(list)) {
            return;
        }
        SharedPreferences.Editor editorEdit = this.a.edit();
        for (Pair<Integer, Object> pair : list) {
            if (pair.first != null) {
                String strB = b(((Integer) pair.first).intValue());
                if (pair.second == null) {
                    editorEdit.remove(strB);
                } else {
                    a(editorEdit, pair, strB);
                }
            }
        }
        editorEdit.commit();
    }
}
