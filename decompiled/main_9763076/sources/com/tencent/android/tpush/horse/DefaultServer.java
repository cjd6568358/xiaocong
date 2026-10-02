package com.tencent.android.tpush.horse;

import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.ServerItem;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.exception.NullReturnException;
import com.tencent.android.tpush.service.channel.protocol.ApList;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultServer {
    public static String a;
    public static String[] c;
    public static String[] d;
    public static String[] e;
    public static final ArrayList g;
    private static ArrayList h;
    public static ArrayList b = new ArrayList(Arrays.asList(443, 8080, 80, 14000));
    public static final ENV f = ENV.RELEASE;

    /* JADX INFO: compiled from: ProGuard */
    public enum ENV {
        RELEASE
    }

    static {
        a = "tpns.qq.com";
        c = new String[]{"183.232.98.178"};
        d = new String[]{"58.251.139.182"};
        e = new String[]{"183.61.46.193"};
        Collections.shuffle(b);
        a = "tpns.qq.com";
        c = new String[]{"183.232.98.178", "111.30.131.23"};
        d = new String[]{"58.251.139.182", "125.39.240.55"};
        e = new String[]{"183.61.46.193", "123.151.152.50"};
        g = new ArrayList();
        g.add(new ServerItem("183.61.46.193", 443, 0));
        h = new ArrayList();
    }

    public static int a() {
        try {
            if (h.isEmpty()) {
                h.addAll(b);
                Collections.shuffle(h);
            }
            if (!h.isEmpty()) {
                return ((Integer) h.remove(0)).intValue();
            }
        } catch (Exception e2) {
        }
        return 80;
    }

    public static ArrayList a(String str) {
        String hostAddress;
        if (str == null) {
            throw new NullReturnException("createDefaultItems return null,because key is null");
        }
        ArrayList arrayList = new ArrayList();
        if (str.equals(String.valueOf(3))) {
            Iterator it = b.iterator();
            while (it.hasNext()) {
                int iIntValue = ((Integer) it.next()).intValue();
                for (int i = 0; i < c.length; i++) {
                    arrayList.add(new ServerItem(c[i], iIntValue, 3));
                }
            }
        } else if (str.equals(String.valueOf(1))) {
            Iterator it2 = b.iterator();
            while (it2.hasNext()) {
                int iIntValue2 = ((Integer) it2.next()).intValue();
                for (int i2 = 0; i2 < e.length; i2++) {
                    arrayList.add(new ServerItem(e[i2], iIntValue2, 1));
                }
            }
        } else if (str.equals(String.valueOf(2))) {
            Iterator it3 = b.iterator();
            while (it3.hasNext()) {
                int iIntValue3 = ((Integer) it3.next()).intValue();
                for (int i3 = 0; i3 < d.length; i3++) {
                    arrayList.add(new ServerItem(d[i3], iIntValue3, 2));
                }
            }
        } else {
            String domain = CacheManager.getDomain(com.tencent.android.tpush.service.n.f());
            if (TextUtils.isEmpty(domain)) {
                domain = a;
            }
            try {
                hostAddress = InetAddress.getByName(domain).getHostAddress();
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(Constants.ServiceLogTag, Constants.MAIN_VERSION_TAG, e2);
                hostAddress = c[0];
            }
            Iterator it4 = b.iterator();
            while (it4.hasNext()) {
                arrayList.add(new ServerItem(hostAddress, ((Integer) it4.next()).intValue(), 0));
            }
        }
        return arrayList;
    }

    public static void a(ApList apList) {
        Map map = apList.primary;
        Map map2 = apList.secondary;
        ArrayList<Integer> arrayList = apList.portList;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Byte b2 : map.keySet()) {
            String strA = com.tencent.android.tpush.service.e.m.a(((Long) map.get(b2)).longValue());
            if (!TextUtils.isEmpty(strA)) {
                for (Integer num : arrayList) {
                    ServerItem serverItem = new ServerItem(strA, num.intValue(), b2.intValue());
                    com.tencent.android.tpush.a.a.c(Constants.LogTag, "apList.primary serverItem ip:" + strA + ",port:" + num);
                    if (b2.byteValue() == 3) {
                        arrayList2.add(serverItem);
                    }
                    if (b2.byteValue() == 1) {
                        arrayList3.add(serverItem);
                    }
                    if (b2.byteValue() == 2) {
                        arrayList4.add(serverItem);
                    }
                }
            }
        }
        for (Byte b3 : map2.keySet()) {
            String strA2 = com.tencent.android.tpush.service.e.m.a(((Long) map2.get(b3)).longValue());
            if (!TextUtils.isEmpty(strA2)) {
                for (Integer num2 : arrayList) {
                    ServerItem serverItem2 = new ServerItem(strA2, num2.intValue(), b3.intValue());
                    com.tencent.android.tpush.a.a.c(Constants.LogTag, "apList.secondary serverItem ip:" + strA2 + ",port:" + num2);
                    if (b3.byteValue() == 3) {
                        arrayList2.add(serverItem2);
                    }
                    if (b3.byteValue() == 1) {
                        arrayList3.add(serverItem2);
                    }
                    if (b3.byteValue() == 2) {
                        arrayList4.add(serverItem2);
                    }
                }
            }
        }
        if (!arrayList2.isEmpty()) {
            CacheManager.addServerItems(com.tencent.android.tpush.service.n.f(), "3", arrayList2);
        }
        if (!arrayList3.isEmpty()) {
            CacheManager.addServerItems(com.tencent.android.tpush.service.n.f(), "1", arrayList3);
        }
        if (!arrayList4.isEmpty()) {
            CacheManager.addServerItems(com.tencent.android.tpush.service.n.f(), "2", arrayList4);
        }
        ArrayList<Long> arrayList5 = apList.speedTestIpList;
        ArrayList arrayList6 = new ArrayList();
        for (Long l : arrayList5) {
            for (Integer num3 : arrayList) {
                com.tencent.android.tpush.a.a.c(Constants.LogTag, "apList.speedTestIpList serverItem ip:" + l + ",port:" + num3);
                arrayList6.add(new ServerItem(l.longValue(), num3.intValue(), 0));
            }
        }
        CacheManager.saveSpeedTestList(com.tencent.android.tpush.service.n.f(), arrayList6);
        String str = apList.domain;
        if (!TextUtils.isEmpty(str) && !str.equals(CacheManager.getDomain(com.tencent.android.tpush.service.n.f()))) {
            CacheManager.clearDomainServerItem(com.tencent.android.tpush.service.n.f());
            CacheManager.saveDomain(com.tencent.android.tpush.service.n.f(), str);
        }
    }

    public static ArrayList b() {
        ArrayList arrayList = new ArrayList();
        Iterator it = b.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            for (int i = 0; i < c.length; i++) {
                arrayList.add(new ServerItem(c[i], iIntValue, 3));
            }
            for (int i2 = 0; i2 < e.length; i2++) {
                arrayList.add(new ServerItem(e[i2], iIntValue, 1));
            }
            for (int i3 = 0; i3 < d.length; i3++) {
                arrayList.add(new ServerItem(d[i3], iIntValue, 2));
            }
        }
        String domain = CacheManager.getDomain(com.tencent.android.tpush.service.n.f());
        if (TextUtils.isEmpty(domain)) {
            domain = a;
        }
        try {
            String hostAddress = InetAddress.getByName(domain).getHostAddress();
            Iterator it2 = b.iterator();
            while (it2.hasNext()) {
                arrayList.add(new ServerItem(hostAddress, ((Integer) it2.next()).intValue(), 0));
            }
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, ">> Dns resolve err : " + e2.getMessage());
        }
        return arrayList;
    }
}
