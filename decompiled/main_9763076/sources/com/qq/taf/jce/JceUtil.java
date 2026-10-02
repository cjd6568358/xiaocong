package com.qq.taf.jce;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class JceUtil {
    private static final byte[] highDigits;
    private static final int iConstant = 37;
    private static final int iTotal = 17;
    private static final byte[] lowDigits;

    public static boolean equals(boolean l, boolean r) {
        return l == r;
    }

    public static boolean equals(byte l, byte r) {
        return l == r;
    }

    public static boolean equals(char l, char r) {
        return l == r;
    }

    public static boolean equals(short l, short r) {
        return l == r;
    }

    public static boolean equals(int l, int r) {
        return l == r;
    }

    public static boolean equals(long l, long r) {
        return l == r;
    }

    public static boolean equals(float l, float r) {
        return l == r;
    }

    public static boolean equals(double l, double r) {
        return l == r;
    }

    public static boolean equals(Object l, Object r) {
        return l.equals(r);
    }

    public static int compareTo(boolean l, boolean r) {
        return (l ? 1 : 0) - (r ? 1 : 0);
    }

    public static int compareTo(byte l, byte r) {
        if (l < r) {
            return -1;
        }
        return l > r ? 1 : 0;
    }

    public static int compareTo(char l, char r) {
        if (l < r) {
            return -1;
        }
        return l > r ? 1 : 0;
    }

    public static int compareTo(short l, short r) {
        if (l < r) {
            return -1;
        }
        return l > r ? 1 : 0;
    }

    public static int compareTo(int l, int r) {
        if (l < r) {
            return -1;
        }
        return l > r ? 1 : 0;
    }

    public static int compareTo(long l, long r) {
        if (l < r) {
            return -1;
        }
        return l > r ? 1 : 0;
    }

    public static int compareTo(float l, float r) {
        if (l < r) {
            return -1;
        }
        return l > r ? 1 : 0;
    }

    public static int compareTo(double l, double r) {
        if (l < r) {
            return -1;
        }
        return l > r ? 1 : 0;
    }

    public static <T extends Comparable<T>> int compareTo(T l, T r) {
        return l.compareTo(r);
    }

    public static <T extends Comparable<T>> int compareTo(List<T> l, List<T> r) {
        Iterator<T> li = l.iterator();
        Iterator<T> ri = r.iterator();
        while (li.hasNext() && ri.hasNext()) {
            int n = li.next().compareTo(ri.next());
            if (n != 0) {
                return n;
            }
        }
        return compareTo(li.hasNext(), ri.hasNext());
    }

    public static <T extends Comparable<T>> int compareTo(T[] l, T[] r) {
        int li = 0;
        for (int ri = 0; li < l.length && ri < r.length; ri++) {
            int n = l[li].compareTo(r[ri]);
            if (n == 0) {
                li++;
            } else {
                return n;
            }
        }
        return compareTo(l.length, r.length);
    }

    public static int compareTo(boolean[] l, boolean[] r) {
        int li = 0;
        for (int ri = 0; li < l.length && ri < r.length; ri++) {
            int n = compareTo(l[li], r[ri]);
            if (n == 0) {
                li++;
            } else {
                return n;
            }
        }
        return compareTo(l.length, r.length);
    }

    public static int compareTo(byte[] l, byte[] r) {
        int li = 0;
        for (int ri = 0; li < l.length && ri < r.length; ri++) {
            int n = compareTo(l[li], r[ri]);
            if (n == 0) {
                li++;
            } else {
                return n;
            }
        }
        return compareTo(l.length, r.length);
    }

    public static int compareTo(char[] l, char[] r) {
        int li = 0;
        for (int ri = 0; li < l.length && ri < r.length; ri++) {
            int n = compareTo(l[li], r[ri]);
            if (n == 0) {
                li++;
            } else {
                return n;
            }
        }
        return compareTo(l.length, r.length);
    }

    public static int compareTo(short[] l, short[] r) {
        int li = 0;
        for (int ri = 0; li < l.length && ri < r.length; ri++) {
            int n = compareTo(l[li], r[ri]);
            if (n == 0) {
                li++;
            } else {
                return n;
            }
        }
        return compareTo(l.length, r.length);
    }

    public static int compareTo(int[] l, int[] r) {
        int li = 0;
        for (int ri = 0; li < l.length && ri < r.length; ri++) {
            int n = compareTo(l[li], r[ri]);
            if (n == 0) {
                li++;
            } else {
                return n;
            }
        }
        return compareTo(l.length, r.length);
    }

    public static int compareTo(long[] l, long[] r) {
        int li = 0;
        for (int ri = 0; li < l.length && ri < r.length; ri++) {
            int n = compareTo(l[li], r[ri]);
            if (n == 0) {
                li++;
            } else {
                return n;
            }
        }
        return compareTo(l.length, r.length);
    }

    public static int compareTo(float[] l, float[] r) {
        int li = 0;
        for (int ri = 0; li < l.length && ri < r.length; ri++) {
            int n = compareTo(l[li], r[ri]);
            if (n == 0) {
                li++;
            } else {
                return n;
            }
        }
        return compareTo(l.length, r.length);
    }

    public static int compareTo(double[] l, double[] r) {
        int li = 0;
        for (int ri = 0; li < l.length && ri < r.length; ri++) {
            int n = compareTo(l[li], r[ri]);
            if (n == 0) {
                li++;
            } else {
                return n;
            }
        }
        return compareTo(l.length, r.length);
    }

    public static int hashCode(boolean o) {
        return (o ? 0 : 1) + 629;
    }

    public static int hashCode(boolean[] array) {
        if (array == null) {
            return 629;
        }
        int tempTotal = 17;
        for (boolean z : array) {
            tempTotal = (tempTotal * 37) + (z ? 0 : 1);
        }
        return tempTotal;
    }

    public static int hashCode(byte o) {
        return o + 629;
    }

    public static int hashCode(byte[] array) {
        if (array == null) {
            return 629;
        }
        int tempTotal = 17;
        for (byte b : array) {
            tempTotal = (tempTotal * 37) + b;
        }
        return tempTotal;
    }

    public static int hashCode(char o) {
        return o + 629;
    }

    public static int hashCode(char[] array) {
        if (array == null) {
            return 629;
        }
        int tempTotal = 17;
        for (char c : array) {
            tempTotal = (tempTotal * 37) + c;
        }
        return tempTotal;
    }

    public static int hashCode(double o) {
        return hashCode(Double.doubleToLongBits(o));
    }

    public static int hashCode(double[] array) {
        if (array == null) {
            return 629;
        }
        int tempTotal = 17;
        for (int i = 0; i < array.length; i++) {
            tempTotal = (tempTotal * 37) + ((int) (Double.doubleToLongBits(array[i]) ^ (Double.doubleToLongBits(array[i]) >> 32)));
        }
        return tempTotal;
    }

    public static int hashCode(float o) {
        return Float.floatToIntBits(o) + 629;
    }

    public static int hashCode(float[] array) {
        if (array == null) {
            return 629;
        }
        int tempTotal = 17;
        for (float f : array) {
            tempTotal = (tempTotal * 37) + Float.floatToIntBits(f);
        }
        return tempTotal;
    }

    public static int hashCode(short o) {
        return o + 629;
    }

    public static int hashCode(short[] array) {
        if (array == null) {
            return 629;
        }
        int tempTotal = 17;
        for (short s : array) {
            tempTotal = (tempTotal * 37) + s;
        }
        return tempTotal;
    }

    public static int hashCode(int o) {
        return o + 629;
    }

    public static int hashCode(int[] array) {
        if (array == null) {
            return 629;
        }
        int tempTotal = 17;
        for (int i : array) {
            tempTotal = (tempTotal * 37) + i;
        }
        return tempTotal;
    }

    public static int hashCode(long o) {
        return ((int) ((o >> 32) ^ o)) + 629;
    }

    public static int hashCode(long[] array) {
        if (array == null) {
            return 629;
        }
        int tempTotal = 17;
        for (int i = 0; i < array.length; i++) {
            tempTotal = (tempTotal * 37) + ((int) (array[i] ^ (array[i] >> 32)));
        }
        return tempTotal;
    }

    public static int hashCode(JceStruct[] array) {
        if (array == null) {
            return 629;
        }
        int tempTotal = 17;
        for (JceStruct jceStruct : array) {
            tempTotal = (tempTotal * 37) + jceStruct.hashCode();
        }
        return tempTotal;
    }

    public static int hashCode(Object object) {
        if (object == null) {
            return 629;
        }
        if (object.getClass().isArray()) {
            if (object instanceof long[]) {
                return hashCode((long[]) object);
            }
            if (object instanceof int[]) {
                return hashCode((int[]) object);
            }
            if (object instanceof short[]) {
                return hashCode((short[]) object);
            }
            if (object instanceof char[]) {
                return hashCode((char[]) object);
            }
            if (object instanceof byte[]) {
                return hashCode((byte[]) object);
            }
            if (object instanceof double[]) {
                return hashCode((double[]) object);
            }
            if (object instanceof float[]) {
                return hashCode((float[]) object);
            }
            if (object instanceof boolean[]) {
                return hashCode((boolean[]) object);
            }
            if (object instanceof JceStruct[]) {
                return hashCode((JceStruct[]) object);
            }
            return hashCode((Object[]) object);
        }
        if (object instanceof JceStruct) {
            return object.hashCode();
        }
        return object.hashCode() + 629;
    }

    public static byte[] getJceBufArray(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.position()];
        System.arraycopy(buffer.array(), 0, bytes, 0, bytes.length);
        return bytes;
    }

    static {
        byte[] digits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
        byte[] high = new byte[256];
        byte[] low = new byte[256];
        for (int i = 0; i < 256; i++) {
            high[i] = digits[i >>> 4];
            low[i] = digits[i & 15];
        }
        highDigits = high;
        lowDigits = low;
    }

    public static String getHexdump(byte[] array) {
        return getHexdump(ByteBuffer.wrap(array));
    }

    public static String getHexdump(ByteBuffer in) {
        int size = in.remaining();
        if (size == 0) {
            return "empty";
        }
        StringBuffer out = new StringBuffer((in.remaining() * 3) - 1);
        int mark = in.position();
        int byteValue = in.get() & 255;
        out.append((char) highDigits[byteValue]);
        out.append((char) lowDigits[byteValue]);
        for (int size2 = size - 1; size2 > 0; size2--) {
            out.append(' ');
            int byteValue2 = in.get() & 255;
            out.append((char) highDigits[byteValue2]);
            out.append((char) lowDigits[byteValue2]);
        }
        in.position(mark);
        return out.toString();
    }
}
