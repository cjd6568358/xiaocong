package com.qq.taf.jce;

import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class JceInputStream {
    private ByteBuffer bs;
    protected String sServerEncoding = "GBK";

    public static class HeadData {
        public int tag;
        public byte type;

        public void clear() {
            this.type = (byte) 0;
            this.tag = 0;
        }
    }

    public JceInputStream() {
    }

    public JceInputStream(ByteBuffer bs) {
        this.bs = bs;
    }

    public JceInputStream(byte[] bs) {
        this.bs = ByteBuffer.wrap(bs);
    }

    public JceInputStream(byte[] bs, int pos) {
        this.bs = ByteBuffer.wrap(bs);
        this.bs.position(pos);
    }

    public void warp(byte[] bs) {
        wrap(bs);
    }

    public void wrap(byte[] bs) {
        this.bs = ByteBuffer.wrap(bs);
    }

    public static int readHead(HeadData hd, ByteBuffer bb) {
        byte b = bb.get();
        hd.type = (byte) (b & 15);
        hd.tag = (b & 240) >> 4;
        if (hd.tag != 15) {
            return 1;
        }
        hd.tag = bb.get() & Constants.NETWORK_TYPE_UNCONNECTED;
        return 2;
    }

    public void readHead(HeadData hd) {
        readHead(hd, this.bs);
    }

    private int peakHead(HeadData hd) {
        return readHead(hd, this.bs.duplicate());
    }

    private void skip(int len) {
        this.bs.position(this.bs.position() + len);
    }

    public boolean skipToTag(int tag) {
        try {
            HeadData hd = new HeadData();
            while (true) {
                int len = peakHead(hd);
                if (hd.type == 11) {
                    return false;
                }
                if (tag <= hd.tag) {
                    return tag == hd.tag;
                }
                skip(len);
                skipField(hd.type);
            }
        } catch (JceDecodeException e) {
            return false;
        } catch (BufferUnderflowException e2) {
            return false;
        }
    }

    public void skipToStructEnd() {
        HeadData hd = new HeadData();
        do {
            readHead(hd);
            skipField(hd.type);
        } while (hd.type != 11);
    }

    private void skipField() {
        HeadData hd = new HeadData();
        readHead(hd);
        skipField(hd.type);
    }

    private void skipField(byte type) {
        switch (type) {
            case 0:
                skip(1);
                return;
            case 1:
                skip(2);
                return;
            case 2:
                skip(4);
                return;
            case 3:
                skip(8);
                return;
            case 4:
                skip(4);
                return;
            case 5:
                skip(8);
                return;
            case 6:
                int len = this.bs.get();
                if (len < 0) {
                    len += 256;
                }
                skip(len);
                return;
            case 7:
                skip(this.bs.getInt());
                return;
            case 8:
                int size = read(0, 0, true);
                for (int i = 0; i < size * 2; i++) {
                    skipField();
                }
                return;
            case 9:
                int size2 = read(0, 0, true);
                for (int i2 = 0; i2 < size2; i2++) {
                    skipField();
                }
                return;
            case 10:
                skipToStructEnd();
                return;
            case 11:
            case 12:
                return;
            case 13:
                HeadData hd = new HeadData();
                readHead(hd);
                if (hd.type != 0) {
                    throw new JceDecodeException("skipField with invalid type, type value: " + ((int) type) + ", " + ((int) hd.type));
                }
                int size3 = read(0, 0, true);
                skip(size3);
                return;
            default:
                throw new JceDecodeException("invalid type.");
        }
    }

    public boolean read(boolean b, int tag, boolean isRequire) {
        byte c = read((byte) 0, tag, isRequire);
        return c != 0;
    }

    public byte read(byte c, int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 0:
                    byte c2 = this.bs.get();
                    return c2;
                case 12:
                    return (byte) 0;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return c;
    }

    public short read(short n, int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 0:
                    short n2 = this.bs.get();
                    return n2;
                case 1:
                    short n3 = this.bs.getShort();
                    return n3;
                case 12:
                    return (short) 0;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return n;
    }

    public int read(int n, int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 0:
                    int n2 = this.bs.get();
                    return n2;
                case 1:
                    int n3 = this.bs.getShort();
                    return n3;
                case 2:
                    int n4 = this.bs.getInt();
                    return n4;
                case 12:
                    return 0;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return n;
    }

    public long read(long n, int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 0:
                    long n2 = this.bs.get();
                    return n2;
                case 1:
                    long n3 = this.bs.getShort();
                    return n3;
                case 2:
                    long n4 = this.bs.getInt();
                    return n4;
                case 3:
                    long n5 = this.bs.getLong();
                    return n5;
                case 12:
                    return 0L;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return n;
    }

    public float read(float n, int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 4:
                    float n2 = this.bs.getFloat();
                    return n2;
                case 12:
                    return 0.0f;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return n;
    }

    public double read(double n, int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 4:
                    double n2 = this.bs.getFloat();
                    return n2;
                case 5:
                    double n3 = this.bs.getDouble();
                    return n3;
                case 12:
                    return 0.0d;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return n;
    }

    public String readByteString(String s, int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 6:
                    int len = this.bs.get();
                    if (len < 0) {
                        len += 256;
                    }
                    byte[] ss = new byte[len];
                    this.bs.get(ss);
                    String s2 = HexUtil.bytes2HexStr(ss);
                    return s2;
                case 7:
                    int len2 = this.bs.getInt();
                    if (len2 > 104857600 || len2 < 0) {
                        throw new JceDecodeException("String too long: " + len2);
                    }
                    byte[] ss2 = new byte[len2];
                    this.bs.get(ss2);
                    String s3 = HexUtil.bytes2HexStr(ss2);
                    return s3;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return s;
    }

    public String read(String s, int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 6:
                    int len = this.bs.get();
                    if (len < 0) {
                        len += 256;
                    }
                    byte[] ss = new byte[len];
                    this.bs.get(ss);
                    try {
                        String s2 = new String(ss, this.sServerEncoding);
                        return s2;
                    } catch (UnsupportedEncodingException e) {
                        String s3 = new String(ss);
                        return s3;
                    }
                case 7:
                    int len2 = this.bs.getInt();
                    if (len2 > 104857600 || len2 < 0) {
                        throw new JceDecodeException("String too long: " + len2);
                    }
                    byte[] ss2 = new byte[len2];
                    this.bs.get(ss2);
                    try {
                        String s4 = new String(ss2, this.sServerEncoding);
                        return s4;
                    } catch (UnsupportedEncodingException e2) {
                        String s5 = new String(ss2);
                        return s5;
                    }
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return s;
    }

    public String readString(int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 6:
                    int len = this.bs.get();
                    if (len < 0) {
                        len += 256;
                    }
                    byte[] ss = new byte[len];
                    this.bs.get(ss);
                    try {
                        String s = new String(ss, this.sServerEncoding);
                        return s;
                    } catch (UnsupportedEncodingException e) {
                        String s2 = new String(ss);
                        return s2;
                    }
                case 7:
                    int len2 = this.bs.getInt();
                    if (len2 > 104857600 || len2 < 0) {
                        throw new JceDecodeException("String too long: " + len2);
                    }
                    byte[] ss2 = new byte[len2];
                    this.bs.get(ss2);
                    try {
                        String s3 = new String(ss2, this.sServerEncoding);
                        return s3;
                    } catch (UnsupportedEncodingException e2) {
                        String s4 = new String(ss2);
                        return s4;
                    }
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (!isRequire) {
            return null;
        }
        throw new JceDecodeException("require field not exist.");
    }

    public String[] read(String[] s, int tag, boolean isRequire) {
        return (String[]) readArray(s, tag, isRequire);
    }

    public Map<String, String> readStringMap(int tag, boolean isRequire) {
        HashMap<String, String> mr = new HashMap<>();
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 8:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    for (int i = 0; i < size; i++) {
                        String k = readString(0, true);
                        String v = readString(1, true);
                        mr.put(k, v);
                    }
                    break;
                    break;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return mr;
    }

    public <K, V> HashMap<K, V> readMap(Map<K, V> m, int tag, boolean isRequire) {
        return (HashMap) readMap(new HashMap(), m, tag, isRequire);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <K, V> Map<K, V> readMap(Map<K, V> map, Map<K, V> m, int tag, boolean isRequire) {
        if (m == null || m.isEmpty()) {
            Map<K, V> mr = new HashMap<>();
            return mr;
        }
        Iterator<Map.Entry<K, V>> it = m.entrySet().iterator();
        Map.Entry<K, V> en = it.next();
        K mk = en.getKey();
        V mv = en.getValue();
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 8:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    for (int i = 0; i < size; i++) {
                        map.put(read(mk, 0, true), read(mv, 1, true));
                    }
                    return map;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return map;
    }

    public List readList(int tag, boolean isRequire) {
        List lr = new ArrayList();
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 9:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    for (int i = 0; i < size; i++) {
                        HeadData subH = new HeadData();
                        readHead(subH);
                        switch (subH.type) {
                            case 0:
                                skip(1);
                                break;
                            case 1:
                                skip(2);
                                break;
                            case 2:
                                skip(4);
                                break;
                            case 3:
                                skip(8);
                                break;
                            case 4:
                                skip(4);
                                break;
                            case 5:
                                skip(8);
                                break;
                            case 6:
                                int len = this.bs.get();
                                if (len < 0) {
                                    len += 256;
                                }
                                skip(len);
                                break;
                            case 7:
                                skip(this.bs.getInt());
                                break;
                            case 8:
                            case 9:
                                break;
                            case 10:
                                try {
                                    Class<?> newoneClass = Class.forName(JceStruct.class.getName());
                                    Constructor<?> cons = newoneClass.getConstructor(new Class[0]);
                                    JceStruct struct = (JceStruct) cons.newInstance(new Object[0]);
                                    struct.readFrom(this);
                                    skipToStructEnd();
                                    lr.add(struct);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    throw new JceDecodeException("type mismatch." + e);
                                }
                                break;
                            case 11:
                            default:
                                throw new JceDecodeException("type mismatch.");
                            case 12:
                                lr.add(new Integer(0));
                                break;
                        }
                    }
                    break;
                    break;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return lr;
    }

    public boolean[] read(boolean[] l, int tag, boolean isRequire) {
        boolean[] lr = null;
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 9:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    lr = new boolean[size];
                    for (int i = 0; i < size; i++) {
                        lr[i] = read(lr[0], 0, true);
                    }
                    break;
                    break;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return lr;
    }

    public byte[] read(byte[] l, int tag, boolean isRequire) {
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 9:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    byte[] lr = new byte[size];
                    for (int i = 0; i < size; i++) {
                        lr[i] = read(lr[0], 0, true);
                    }
                    return lr;
                case 13:
                    HeadData hh = new HeadData();
                    readHead(hh);
                    if (hh.type != 0) {
                        throw new JceDecodeException("type mismatch, tag: " + tag + ", type: " + ((int) hd.type) + ", " + ((int) hh.type));
                    }
                    int size2 = read(0, 0, true);
                    if (size2 < 0) {
                        throw new JceDecodeException("invalid size, tag: " + tag + ", type: " + ((int) hd.type) + ", " + ((int) hh.type) + ", size: " + size2);
                    }
                    byte[] lr2 = new byte[size2];
                    this.bs.get(lr2);
                    return lr2;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (!isRequire) {
            return null;
        }
        throw new JceDecodeException("require field not exist.");
    }

    public short[] read(short[] l, int tag, boolean isRequire) {
        short[] lr = null;
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 9:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    lr = new short[size];
                    for (int i = 0; i < size; i++) {
                        lr[i] = read(lr[0], 0, true);
                    }
                    break;
                    break;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return lr;
    }

    public int[] read(int[] l, int tag, boolean isRequire) {
        int[] lr = null;
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 9:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    lr = new int[size];
                    for (int i = 0; i < size; i++) {
                        lr[i] = read(lr[0], 0, true);
                    }
                    break;
                    break;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return lr;
    }

    public long[] read(long[] l, int tag, boolean isRequire) {
        long[] lr = null;
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 9:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    lr = new long[size];
                    for (int i = 0; i < size; i++) {
                        lr[i] = read(lr[0], 0, true);
                    }
                    break;
                    break;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return lr;
    }

    public float[] read(float[] l, int tag, boolean isRequire) {
        float[] lr = null;
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 9:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    lr = new float[size];
                    for (int i = 0; i < size; i++) {
                        lr[i] = read(lr[0], 0, true);
                    }
                    break;
                    break;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return lr;
    }

    public double[] read(double[] l, int tag, boolean isRequire) {
        double[] lr = null;
        if (skipToTag(tag)) {
            HeadData hd = new HeadData();
            readHead(hd);
            switch (hd.type) {
                case 9:
                    int size = read(0, 0, true);
                    if (size < 0) {
                        throw new JceDecodeException("size invalid: " + size);
                    }
                    lr = new double[size];
                    for (int i = 0; i < size; i++) {
                        lr[i] = read(lr[0], 0, true);
                    }
                    break;
                    break;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return lr;
    }

    public <T> T[] readArray(T[] tArr, int i, boolean z) {
        if (tArr == null || tArr.length == 0) {
            throw new JceDecodeException("unable to get type of key and value.");
        }
        return (T[]) readArrayImpl(tArr[0], i, z);
    }

    public <T> List<T> readArray(List<T> l, int tag, boolean isRequire) {
        if (l == null || l.isEmpty()) {
            return new ArrayList();
        }
        Object[] arrayImpl = readArrayImpl(l.get(0), tag, isRequire);
        if (arrayImpl == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : arrayImpl) {
            arrayList.add(obj);
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> T[] readArrayImpl(T t, int i, boolean z) {
        if (skipToTag(i)) {
            HeadData headData = new HeadData();
            readHead(headData);
            switch (headData.type) {
                case 9:
                    int i2 = read(0, 0, true);
                    if (i2 < 0) {
                        throw new JceDecodeException("size invalid: " + i2);
                    }
                    T[] tArr = (T[]) ((Object[]) Array.newInstance(t.getClass(), i2));
                    for (int i3 = 0; i3 < i2; i3++) {
                        tArr[i3] = read((Object) t, 0, true);
                    }
                    return tArr;
                default:
                    throw new JceDecodeException("type mismatch.");
            }
        }
        if (z) {
            throw new JceDecodeException("require field not exist.");
        }
        return null;
    }

    public JceStruct directRead(JceStruct o, int tag, boolean isRequire) {
        JceStruct ref = null;
        if (skipToTag(tag)) {
            try {
                ref = o.newInit();
                HeadData hd = new HeadData();
                readHead(hd);
                if (hd.type != 10) {
                    throw new JceDecodeException("type mismatch.");
                }
                ref.readFrom(this);
                skipToStructEnd();
            } catch (Exception e) {
                throw new JceDecodeException(e.getMessage());
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return ref;
    }

    public JceStruct read(JceStruct o, int tag, boolean isRequire) {
        JceStruct ref = null;
        if (skipToTag(tag)) {
            try {
                ref = (JceStruct) o.getClass().newInstance();
                HeadData hd = new HeadData();
                readHead(hd);
                if (hd.type != 10) {
                    throw new JceDecodeException("type mismatch.");
                }
                ref.readFrom(this);
                skipToStructEnd();
            } catch (Exception e) {
                throw new JceDecodeException(e.getMessage());
            }
        } else if (isRequire) {
            throw new JceDecodeException("require field not exist.");
        }
        return ref;
    }

    public JceStruct[] read(JceStruct[] o, int tag, boolean isRequire) {
        return (JceStruct[]) readArray(o, tag, isRequire);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> Object read(T t, int tag, boolean isRequire) {
        if (t instanceof Byte) {
            return Byte.valueOf(read((byte) 0, tag, isRequire));
        }
        if (t instanceof Boolean) {
            return Boolean.valueOf(read(false, tag, isRequire));
        }
        if (t instanceof Short) {
            return Short.valueOf(read((short) 0, tag, isRequire));
        }
        if (t instanceof Integer) {
            int i = read(0, tag, isRequire);
            return Integer.valueOf(i);
        }
        if (t instanceof Long) {
            return Long.valueOf(read(0L, tag, isRequire));
        }
        if (t instanceof Float) {
            return Float.valueOf(read(0.0f, tag, isRequire));
        }
        if (t instanceof Double) {
            return Double.valueOf(read(0.0d, tag, isRequire));
        }
        if (t instanceof String) {
            return readString(tag, isRequire);
        }
        if (t instanceof Map) {
            return readMap((Map) t, tag, isRequire);
        }
        if (t instanceof List) {
            return readArray((List) t, tag, isRequire);
        }
        if (t instanceof JceStruct) {
            return read((JceStruct) t, tag, isRequire);
        }
        if (t.getClass().isArray()) {
            if ((t instanceof byte[]) || (t instanceof Byte[])) {
                return read((byte[]) null, tag, isRequire);
            }
            if (t instanceof boolean[]) {
                return read((boolean[]) null, tag, isRequire);
            }
            if (t instanceof short[]) {
                return read((short[]) null, tag, isRequire);
            }
            if (t instanceof int[]) {
                return read((int[]) null, tag, isRequire);
            }
            if (t instanceof long[]) {
                return read((long[]) null, tag, isRequire);
            }
            if (t instanceof float[]) {
                return read((float[]) null, tag, isRequire);
            }
            if (t instanceof double[]) {
                return read((double[]) null, tag, isRequire);
            }
            return readArray((Object[]) t, tag, isRequire);
        }
        throw new JceDecodeException("read object error: unsupport type.");
    }

    public int setServerEncoding(String se) {
        this.sServerEncoding = se;
        return 0;
    }

    public static void main(String[] args) {
    }

    public ByteBuffer getBs() {
        return this.bs;
    }
}
