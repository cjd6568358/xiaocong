package com.huawei.hms.support.api.push.a.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: PushPreferences.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    protected SharedPreferences a;

    public boolean a(String str) {
        return this.a != null && this.a.getBoolean(str, false);
    }

    public String b(String str) {
        return this.a != null ? this.a.getString(str, Constants.MAIN_VERSION_TAG) : Constants.MAIN_VERSION_TAG;
    }

    public c(Context context, String str) {
        if (context == null) {
            throw new NullPointerException("context is null!");
        }
        this.a = context.getSharedPreferences(str, 4);
    }

    public boolean a(String str, Object obj) {
        SharedPreferences.Editor editorEdit = this.a.edit();
        if (obj instanceof String) {
            editorEdit.putString(str, String.valueOf(obj));
        } else if (obj instanceof Integer) {
            editorEdit.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Short) {
            editorEdit.putInt(str, ((Short) obj).shortValue());
        } else if (obj instanceof Byte) {
            editorEdit.putInt(str, ((Byte) obj).byteValue());
        } else if (obj instanceof Long) {
            editorEdit.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Float) {
            editorEdit.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            editorEdit.putFloat(str, (float) ((Double) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            editorEdit.putBoolean(str, ((Boolean) obj).booleanValue());
        }
        return editorEdit.commit();
    }

    public boolean a(String str, String str2) {
        SharedPreferences.Editor editorEdit;
        if (this.a == null || (editorEdit = this.a.edit()) == null) {
            return false;
        }
        return editorEdit.putString(str, str2).commit();
    }

    public void a(String str, boolean z) {
        SharedPreferences.Editor editorEdit;
        if (this.a != null && (editorEdit = this.a.edit()) != null) {
            editorEdit.putBoolean(str, z).commit();
        }
    }

    public boolean c(String str) {
        return this.a != null && this.a.contains(str);
    }

    public boolean d(String str) {
        if (this.a == null || !this.a.contains(str)) {
            return false;
        }
        SharedPreferences.Editor editorRemove = this.a.edit().remove(str);
        editorRemove.commit();
        return editorRemove.commit();
    }

    public Map<String, ?> a() {
        return this.a != null ? this.a.getAll() : new HashMap();
    }
}
