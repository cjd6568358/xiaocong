package com.baidu.mobstat;

import android.app.ActivityManager;
import android.content.Context;
import java.util.List;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ao {
    public static final ao a = new ap("SERVICE", 0, 1);
    public static final ao b;
    public static final ao c;
    public static final ao d;
    private static final /* synthetic */ ao[] f;
    private int e;

    public abstract void a(Context context);

    /* synthetic */ ao(String str, int i, int i2, ap apVar) {
        this(str, i, i2);
    }

    public static ao valueOf(String str) {
        return (ao) Enum.valueOf(ao.class, str);
    }

    public static ao[] values() {
        return (ao[]) f.clone();
    }

    static {
        final int i = 4;
        final int i2 = 3;
        final int i3 = 2;
        final int i4 = 1;
        final String str = "NO_SERVICE";
        b = new ao(str, i4, i3) { // from class: com.baidu.mobstat.aq
            {
                ap apVar = null;
            }

            @Override // com.baidu.mobstat.ao
            public void a(Context context) {
                Context applicationContext = context.getApplicationContext();
                l lVarA = au.a(context);
                be beVar = new be();
                beVar.a = false;
                beVar.b = "M";
                beVar.c = false;
                lVarA.a(applicationContext, beVar.a());
            }
        };
        final String str2 = "RECEIVER";
        c = new ao(str2, i3, i2) { // from class: com.baidu.mobstat.ar
            {
                ap apVar = null;
            }

            @Override // com.baidu.mobstat.ao
            public void a(Context context) {
                Context applicationContext = context.getApplicationContext();
                l lVarA = au.a(context);
                be beVar = new be();
                beVar.a = false;
                beVar.b = "R";
                beVar.c = false;
                lVarA.a(applicationContext, beVar.a());
            }
        };
        final String str3 = "ERISED";
        d = new ao(str3, i2, i) { // from class: com.baidu.mobstat.as
            {
                ap apVar = null;
            }

            @Override // com.baidu.mobstat.ao
            public void a(Context context) {
                Context applicationContext = context.getApplicationContext();
                l lVarA = au.a(context);
                be beVar = new be();
                beVar.a = false;
                beVar.b = "E";
                beVar.c = false;
                lVarA.a(applicationContext, beVar.a());
            }
        };
        f = new ao[]{a, b, c, d};
    }

    private ao(String str, int i, int i2) {
        super(str, i);
        this.e = i2;
    }

    @Override // java.lang.Enum
    public String toString() {
        return String.valueOf(this.e);
    }

    public static ao a(int i) {
        for (ao aoVar : values()) {
            if (aoVar.e == i) {
                return aoVar;
            }
        }
        return b;
    }

    public static boolean b(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (activityManager != null) {
            try {
                List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(Integer.MAX_VALUE);
                for (int i = 0; runningServices != null && i < runningServices.size(); i++) {
                    if ("com.baidu.bottom.service.BottomService".equals(runningServices.get(i).service.getClassName())) {
                        return true;
                    }
                }
            } catch (Exception e) {
                db.a(e);
            }
        }
        return false;
    }
}
