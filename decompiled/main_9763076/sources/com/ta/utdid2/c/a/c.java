package com.ta.utdid2.c.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import com.ta.utdid2.b.a.i;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.util.Map;

/* JADX INFO: compiled from: PersistentConfiguration.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private SharedPreferences f116a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private b f118a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private d f119a;
    private String e;
    private String f;
    private boolean g;
    private boolean h;
    private boolean i;
    private boolean j;
    private Context mContext;
    private SharedPreferences.Editor a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private b.a f117a = null;

    /* JADX WARN: Code duplicated, block: B:99:0x01ff  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13, types: [long] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v21, types: [long] */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v27, types: [long] */
    /* JADX WARN: Type inference failed for: r0v36 */
    /* JADX WARN: Type inference failed for: r0v41 */
    /* JADX WARN: Type inference failed for: r0v42 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v18, types: [long] */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v30, types: [int] */
    /* JADX WARN: Type inference failed for: r2v49 */
    /* JADX WARN: Type inference failed for: r2v55 */
    /* JADX WARN: Type inference failed for: r2v65 */
    /* JADX WARN: Type inference failed for: r2v66 */
    /* JADX WARN: Type inference failed for: r2v67 */
    /* JADX WARN: Type inference failed for: r2v68 */
    /* JADX WARN: Type inference failed for: r2v69 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22, types: [long] */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v4, types: [long] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r8v0 */
    public c(Context context, String str, String str2, boolean z, boolean z2) throws Throwable {
        ?? r2;
        ?? r4;
        this.e = Constants.MAIN_VERSION_TAG;
        this.f = Constants.MAIN_VERSION_TAG;
        this.g = false;
        this.h = false;
        this.i = false;
        this.f116a = null;
        this.f118a = null;
        this.mContext = null;
        this.f119a = null;
        this.j = false;
        this.g = z;
        this.j = z2;
        this.e = str2;
        this.f = str;
        this.mContext = context;
        ?? r0 = 0;
        if (context != null) {
            this.f116a = context.getSharedPreferences(str2, 0);
            r0 = this.f116a.getLong("t", 0L);
        }
        String externalStorageState = null;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i.m99a(externalStorageState)) {
            this.i = false;
            this.h = false;
        } else if (externalStorageState.equals("mounted")) {
            this.i = true;
            this.h = true;
        } else if (externalStorageState.equals("mounted_ro")) {
            this.h = true;
            this.i = false;
        } else {
            this.i = false;
            this.h = false;
        }
        if ((!this.h && !this.i) || context == null || i.m99a(str)) {
            r4 = r0;
            r0 = 0;
        } else {
            this.f119a = a(str);
            if (this.f119a == null) {
                r4 = r0;
                r0 = 0;
            } else {
                try {
                    this.f118a = this.f119a.a(str2, 0);
                    ?? r3 = this.f118a.getLong("t", 0L);
                    try {
                        if (z2) {
                            r4 = this.f116a.getLong("t2", 0L);
                            try {
                                r0 = this.f118a.getLong("t2", 0L);
                                try {
                                    if (r4 < r0 && r4 > 0) {
                                        a(this.f116a, this.f118a);
                                        b bVarA = this.f119a.a(str2, 0);
                                        this.f118a = bVarA;
                                        r3 = bVarA;
                                    } else if (r4 > r0 && r0 > 0) {
                                        a(this.f118a, this.f116a);
                                        SharedPreferences sharedPreferences = context.getSharedPreferences(str2, 0);
                                        this.f116a = sharedPreferences;
                                        r3 = sharedPreferences;
                                    } else if (r4 == 0 && r0 > 0) {
                                        a(this.f118a, this.f116a);
                                        SharedPreferences sharedPreferences2 = context.getSharedPreferences(str2, 0);
                                        this.f116a = sharedPreferences2;
                                        r3 = sharedPreferences2;
                                    } else if (r0 == 0 && r4 > 0) {
                                        a(this.f116a, this.f118a);
                                        b bVarA2 = this.f119a.a(str2, 0);
                                        this.f118a = bVarA2;
                                        r3 = bVarA2;
                                    } else {
                                        r3 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
                                        if (r3 == 0) {
                                            a(this.f116a, this.f118a);
                                            b bVarA3 = this.f119a.a(str2, 0);
                                            this.f118a = bVarA3;
                                            r3 = bVarA3;
                                        }
                                    }
                                } catch (Exception e2) {
                                    r2 = r4;
                                    r0 = r0;
                                    r4 = r2;
                                }
                            } catch (Exception e3) {
                                r0 = r3;
                                r2 = r4;
                            }
                        } else if (r0 > r3) {
                            a(this.f116a, this.f118a);
                            this.f118a = this.f119a.a(str2, 0);
                            r4 = r0;
                            r0 = r3;
                        } else if (r0 < r3) {
                            a(this.f118a, this.f116a);
                            this.f116a = context.getSharedPreferences(str2, 0);
                            r4 = r0;
                            r0 = r3;
                        } else if (r0 == r3) {
                            a(this.f116a, this.f118a);
                            this.f118a = this.f119a.a(str2, 0);
                            r4 = r0;
                            r0 = r3;
                        } else {
                            r4 = r0;
                            r0 = r3;
                        }
                    } catch (Exception e4) {
                        ?? r8 = r3;
                        r2 = r0;
                        r0 = r8;
                    }
                } catch (Exception e5) {
                    r2 = r0;
                    r0 = 0;
                }
            }
        }
        if (r4 != r0 || (r4 == 0 && r0 == 0)) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (!this.j || (this.j && r4 == 0 && r0 == 0)) {
                if (this.f116a != null) {
                    SharedPreferences.Editor editorEdit = this.f116a.edit();
                    editorEdit.putLong("t2", jCurrentTimeMillis);
                    editorEdit.commit();
                }
                try {
                    if (this.f118a != null) {
                        b.a aVarA = this.f118a.a();
                        aVarA.a("t2", jCurrentTimeMillis);
                        aVarA.commit();
                    }
                } catch (Exception e6) {
                }
            }
        }
    }

    private d a(String str) {
        File fileM101a = m101a(str);
        if (fileM101a == null) {
            return null;
        }
        this.f119a = new d(fileM101a.getAbsolutePath());
        return this.f119a;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private File m101a(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory == null) {
            return null;
        }
        File file = new File(String.format("%s%s%s", externalStorageDirectory.getAbsolutePath(), File.separator, str));
        if (file != null && !file.exists()) {
            file.mkdirs();
            return file;
        }
        return file;
    }

    private void a(SharedPreferences sharedPreferences, b bVar) {
        b.a aVarA;
        if (sharedPreferences != null && bVar != null && (aVarA = bVar.a()) != null) {
            aVarA.b();
            for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    aVarA.a(key, (String) value);
                } else if (value instanceof Integer) {
                    aVarA.a(key, ((Integer) value).intValue());
                } else if (value instanceof Long) {
                    aVarA.a(key, ((Long) value).longValue());
                } else if (value instanceof Float) {
                    aVarA.a(key, ((Float) value).floatValue());
                } else if (value instanceof Boolean) {
                    aVarA.a(key, ((Boolean) value).booleanValue());
                }
            }
            aVarA.commit();
        }
    }

    private void a(b bVar, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editorEdit;
        if (bVar != null && sharedPreferences != null && (editorEdit = sharedPreferences.edit()) != null) {
            editorEdit.clear();
            for (Map.Entry<String, ?> entry : bVar.getAll().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof String) {
                    editorEdit.putString(key, (String) value);
                } else if (value instanceof Integer) {
                    editorEdit.putInt(key, ((Integer) value).intValue());
                } else if (value instanceof Long) {
                    editorEdit.putLong(key, ((Long) value).longValue());
                } else if (value instanceof Float) {
                    editorEdit.putFloat(key, ((Float) value).floatValue());
                } else if (value instanceof Boolean) {
                    editorEdit.putBoolean(key, ((Boolean) value).booleanValue());
                }
            }
            editorEdit.commit();
        }
    }

    private boolean b() {
        if (this.f118a == null) {
            return false;
        }
        boolean zMo100a = this.f118a.mo100a();
        if (!zMo100a) {
            commit();
            return zMo100a;
        }
        return zMo100a;
    }

    private void c() {
        if (this.a == null && this.f116a != null) {
            this.a = this.f116a.edit();
        }
        if (this.i && this.f117a == null && this.f118a != null) {
            this.f117a = this.f118a.a();
        }
        b();
    }

    public void putString(String key, String value) {
        if (!i.m99a(key) && !key.equals("t")) {
            c();
            if (this.a != null) {
                this.a.putString(key, value);
            }
            if (this.f117a != null) {
                this.f117a.a(key, value);
            }
        }
    }

    public void remove(String key) {
        if (!i.m99a(key) && !key.equals("t")) {
            c();
            if (this.a != null) {
                this.a.remove(key);
            }
            if (this.f117a != null) {
                this.f117a.a(key);
            }
        }
    }

    public boolean commit() {
        boolean z = true;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.a != null) {
            if (!this.j && this.f116a != null) {
                this.a.putLong("t", jCurrentTimeMillis);
            }
            if (!this.a.commit()) {
                z = false;
            }
        }
        if (this.f116a != null && this.mContext != null) {
            this.f116a = this.mContext.getSharedPreferences(this.e, 0);
        }
        String externalStorageState = null;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!i.m99a(externalStorageState)) {
            if (externalStorageState.equals("mounted")) {
                if (this.f118a == null) {
                    d dVarA = a(this.f);
                    if (dVarA != null) {
                        this.f118a = dVarA.a(this.e, 0);
                        if (!this.j) {
                            a(this.f116a, this.f118a);
                        } else {
                            a(this.f118a, this.f116a);
                        }
                        this.f117a = this.f118a.a();
                    }
                } else if (this.f117a != null && !this.f117a.commit()) {
                    z = false;
                }
            }
            if (externalStorageState.equals("mounted") || (externalStorageState.equals("mounted_ro") && this.f118a != null)) {
                try {
                    if (this.f119a != null) {
                        this.f118a = this.f119a.a(this.e, 0);
                    }
                } catch (Exception e2) {
                }
            }
        }
        return z;
    }

    public String getString(String key) {
        b();
        if (this.f116a != null) {
            String string = this.f116a.getString(key, Constants.MAIN_VERSION_TAG);
            if (!i.m99a(string)) {
                return string;
            }
        }
        if (this.f118a != null) {
            return this.f118a.getString(key, Constants.MAIN_VERSION_TAG);
        }
        return Constants.MAIN_VERSION_TAG;
    }
}
