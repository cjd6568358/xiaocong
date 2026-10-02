package org.apache.thrift;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class b {
    private static final Comparator a = new a();

    private static class a implements Comparator {
        private a() {
        }

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            if (obj == null && obj2 == null) {
                return 0;
            }
            if (obj == null) {
                return -1;
            }
            if (obj2 == null) {
                return 1;
            }
            if (obj instanceof List) {
                return b.a((List) obj, (List) obj2);
            }
            if (obj instanceof Set) {
                return b.a((Set) obj, (Set) obj2);
            }
            if (obj instanceof Map) {
                return b.a((Map) obj, (Map) obj2);
            }
            return obj instanceof byte[] ? b.a((byte[]) obj, (byte[]) obj2) : b.a((Comparable) obj, (Comparable) obj2);
        }
    }

    public static int a(byte b, byte b2) {
        if (b < b2) {
            return -1;
        }
        return b2 < b ? 1 : 0;
    }

    public static int a(double d, double d2) {
        if (d < d2) {
            return -1;
        }
        return d2 < d ? 1 : 0;
    }

    public static int a(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i2 < i ? 1 : 0;
    }

    public static int a(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        return j2 < j ? 1 : 0;
    }

    public static int a(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }

    public static int a(String str, String str2) {
        return str.compareTo(str2);
    }

    public static int a(ByteBuffer byteBuffer, byte[] bArr, int i) {
        int iRemaining = byteBuffer.remaining();
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), bArr, i, iRemaining);
        return iRemaining;
    }

    public static int a(List list, List list2) {
        int iA = a(list.size(), list2.size());
        if (iA != 0) {
            return iA;
        }
        for (int i = 0; i < list.size(); i++) {
            int iCompare = a.compare(list.get(i), list2.get(i));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return 0;
    }

    public static int a(Map map, Map map2) {
        int iA = a(map.size(), map2.size());
        if (iA != 0) {
            return iA;
        }
        TreeMap treeMap = new TreeMap(a);
        treeMap.putAll(map);
        Iterator it = treeMap.entrySet().iterator();
        TreeMap treeMap2 = new TreeMap(a);
        treeMap2.putAll(map2);
        Iterator it2 = treeMap2.entrySet().iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Map.Entry entry2 = (Map.Entry) it2.next();
            int iCompare = a.compare(entry.getKey(), entry2.getKey());
            if (iCompare != 0) {
                return iCompare;
            }
            int iCompare2 = a.compare(entry.getValue(), entry2.getValue());
            if (iCompare2 != 0) {
                return iCompare2;
            }
        }
        return 0;
    }

    public static int a(Set set, Set set2) {
        int iA = a(set.size(), set2.size());
        if (iA != 0) {
            return iA;
        }
        TreeSet treeSet = new TreeSet(a);
        treeSet.addAll(set);
        TreeSet treeSet2 = new TreeSet(a);
        treeSet2.addAll(set2);
        Iterator it = treeSet.iterator();
        Iterator it2 = treeSet2.iterator();
        while (it.hasNext() && it2.hasNext()) {
            int iCompare = a.compare(it.next(), it2.next());
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return 0;
    }

    public static int a(short s, short s2) {
        if (s < s2) {
            return -1;
        }
        return s2 < s ? 1 : 0;
    }

    public static int a(boolean z, boolean z2) {
        return Boolean.valueOf(z).compareTo(Boolean.valueOf(z2));
    }

    public static int a(byte[] bArr, byte[] bArr2) {
        int iA = a(bArr.length, bArr2.length);
        if (iA != 0) {
            return iA;
        }
        for (int i = 0; i < bArr.length; i++) {
            int iA2 = a(bArr[i], bArr2[i]);
            if (iA2 != 0) {
                return iA2;
            }
        }
        return 0;
    }

    public static String a(byte b) {
        return Integer.toHexString((b | 256) & 511).toUpperCase().substring(1);
    }

    public static void a(ByteBuffer byteBuffer, StringBuilder sb) {
        byte[] bArrArray = byteBuffer.array();
        int iArrayOffset = byteBuffer.arrayOffset();
        int iLimit = byteBuffer.limit();
        int i = iLimit - iArrayOffset > 128 ? iArrayOffset + 128 : iLimit;
        for (int i2 = iArrayOffset; i2 < i; i2++) {
            if (i2 > iArrayOffset) {
                sb.append(" ");
            }
            sb.append(a(bArrArray[i2]));
        }
        if (iLimit != i) {
            sb.append("...");
        }
    }

    public static byte[] a(ByteBuffer byteBuffer) {
        if (b(byteBuffer)) {
            return byteBuffer.array();
        }
        byte[] bArr = new byte[byteBuffer.remaining()];
        a(byteBuffer, bArr, 0);
        return bArr;
    }

    public static boolean b(ByteBuffer byteBuffer) {
        return byteBuffer.hasArray() && byteBuffer.position() == 0 && byteBuffer.arrayOffset() == 0 && byteBuffer.remaining() == byteBuffer.capacity();
    }

    public static ByteBuffer c(ByteBuffer byteBuffer) {
        return b(byteBuffer) ? byteBuffer : ByteBuffer.wrap(a(byteBuffer));
    }
}
