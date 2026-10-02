package com.qq.taf.jce;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class JceDisplayer {
    private int _level;
    private StringBuilder sb;

    private void ps(String fieldName) {
        for (int i = 0; i < this._level; i++) {
            this.sb.append('\t');
        }
        if (fieldName != null) {
            this.sb.append(fieldName).append(": ");
        }
    }

    public JceDisplayer(StringBuilder sb, int level) {
        this._level = 0;
        this.sb = sb;
        this._level = level;
    }

    public JceDisplayer(StringBuilder sb) {
        this._level = 0;
        this.sb = sb;
    }

    public JceDisplayer display(boolean b, String fieldName) {
        ps(fieldName);
        this.sb.append(b ? 'T' : 'F').append('\n');
        return this;
    }

    public JceDisplayer displaySimple(boolean b, boolean bSep) {
        this.sb.append(b ? 'T' : 'F');
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public JceDisplayer display(byte n, String fieldName) {
        ps(fieldName);
        this.sb.append((int) n).append('\n');
        return this;
    }

    public JceDisplayer displaySimple(byte n, boolean bSep) {
        this.sb.append((int) n);
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public JceDisplayer display(char n, String fieldName) {
        ps(fieldName);
        this.sb.append(n).append('\n');
        return this;
    }

    public JceDisplayer displaySimple(char n, boolean bSep) {
        this.sb.append(n);
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public JceDisplayer display(short n, String fieldName) {
        ps(fieldName);
        this.sb.append((int) n).append('\n');
        return this;
    }

    public JceDisplayer displaySimple(short n, boolean bSep) {
        this.sb.append((int) n);
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public JceDisplayer display(int n, String fieldName) {
        ps(fieldName);
        this.sb.append(n).append('\n');
        return this;
    }

    public JceDisplayer displaySimple(int n, boolean bSep) {
        this.sb.append(n);
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public JceDisplayer display(long n, String fieldName) {
        ps(fieldName);
        this.sb.append(n).append('\n');
        return this;
    }

    public JceDisplayer displaySimple(long n, boolean bSep) {
        this.sb.append(n);
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public JceDisplayer display(float n, String fieldName) {
        ps(fieldName);
        this.sb.append(n).append('\n');
        return this;
    }

    public JceDisplayer displaySimple(float n, boolean bSep) {
        this.sb.append(n);
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public JceDisplayer display(double n, String fieldName) {
        ps(fieldName);
        this.sb.append(n).append('\n');
        return this;
    }

    public JceDisplayer displaySimple(double n, boolean bSep) {
        this.sb.append(n);
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public JceDisplayer display(String s, String fieldName) {
        ps(fieldName);
        if (s == null) {
            this.sb.append("null").append('\n');
        } else {
            this.sb.append(s).append('\n');
        }
        return this;
    }

    public JceDisplayer displaySimple(String s, boolean bSep) {
        if (s == null) {
            this.sb.append("null");
        } else {
            this.sb.append(s);
        }
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public JceDisplayer display(byte[] v, String fieldName) {
        ps(fieldName);
        if (v == null) {
            this.sb.append("null").append('\n');
        } else if (v.length == 0) {
            this.sb.append(v.length).append(", []").append('\n');
        } else {
            this.sb.append(v.length).append(", [").append('\n');
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (byte o : v) {
                jd.display(o, (String) null);
            }
            display(']', (String) null);
        }
        return this;
    }

    public JceDisplayer displaySimple(byte[] v, boolean bSep) {
        if (v == null || v.length == 0) {
            if (bSep) {
                this.sb.append("|");
            }
        } else {
            this.sb.append(HexUtil.bytes2HexStr(v));
            if (bSep) {
                this.sb.append("|");
            }
        }
        return this;
    }

    public JceDisplayer display(char[] v, String fieldName) {
        ps(fieldName);
        if (v == null) {
            this.sb.append("null").append('\n');
        } else if (v.length == 0) {
            this.sb.append(v.length).append(", []").append('\n');
        } else {
            this.sb.append(v.length).append(", [").append('\n');
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (char o : v) {
                jd.display(o, (String) null);
            }
            display(']', (String) null);
        }
        return this;
    }

    public JceDisplayer displaySimple(char[] v, boolean bSep) {
        if (v == null || v.length == 0) {
            if (bSep) {
                this.sb.append("|");
            }
        } else {
            this.sb.append(new String(v));
            if (bSep) {
                this.sb.append("|");
            }
        }
        return this;
    }

    public JceDisplayer display(short[] v, String fieldName) {
        ps(fieldName);
        if (v == null) {
            this.sb.append("null").append('\n');
        } else if (v.length == 0) {
            this.sb.append(v.length).append(", []").append('\n');
        } else {
            this.sb.append(v.length).append(", [").append('\n');
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (short o : v) {
                jd.display(o, (String) null);
            }
            display(']', (String) null);
        }
        return this;
    }

    public JceDisplayer displaySimple(short[] v, boolean bSep) {
        if (v == null || v.length == 0) {
            this.sb.append("[]");
            if (bSep) {
                this.sb.append("|");
            }
        } else {
            this.sb.append("[");
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (int i = 0; i < v.length; i++) {
                short o = v[i];
                if (i != 0) {
                    this.sb.append("|");
                }
                jd.displaySimple(o, false);
            }
            this.sb.append("]");
            if (bSep) {
                this.sb.append("|");
            }
        }
        return this;
    }

    public JceDisplayer display(int[] v, String fieldName) {
        ps(fieldName);
        if (v == null) {
            this.sb.append("null").append('\n');
        } else if (v.length == 0) {
            this.sb.append(v.length).append(", []").append('\n');
        } else {
            this.sb.append(v.length).append(", [").append('\n');
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (int o : v) {
                jd.display(o, (String) null);
            }
            display(']', (String) null);
        }
        return this;
    }

    public JceDisplayer displaySimple(int[] v, boolean bSep) {
        if (v == null || v.length == 0) {
            this.sb.append("[]");
            if (bSep) {
                this.sb.append("|");
            }
        } else {
            this.sb.append("[");
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (int i = 0; i < v.length; i++) {
                int o = v[i];
                if (i != 0) {
                    this.sb.append("|");
                }
                jd.displaySimple(o, false);
            }
            this.sb.append("]");
            if (bSep) {
                this.sb.append("|");
            }
        }
        return this;
    }

    public JceDisplayer display(long[] v, String fieldName) {
        ps(fieldName);
        if (v == null) {
            this.sb.append("null").append('\n');
        } else if (v.length == 0) {
            this.sb.append(v.length).append(", []").append('\n');
        } else {
            this.sb.append(v.length).append(", [").append('\n');
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (long o : v) {
                jd.display(o, (String) null);
            }
            display(']', (String) null);
        }
        return this;
    }

    public JceDisplayer displaySimple(long[] v, boolean bSep) {
        if (v == null || v.length == 0) {
            this.sb.append("[]");
            if (bSep) {
                this.sb.append("|");
            }
        } else {
            this.sb.append("[");
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (int i = 0; i < v.length; i++) {
                long o = v[i];
                if (i != 0) {
                    this.sb.append("|");
                }
                jd.displaySimple(o, false);
            }
            this.sb.append("]");
            if (bSep) {
                this.sb.append("|");
            }
        }
        return this;
    }

    public JceDisplayer display(float[] v, String fieldName) {
        ps(fieldName);
        if (v == null) {
            this.sb.append("null").append('\n');
        } else if (v.length == 0) {
            this.sb.append(v.length).append(", []").append('\n');
        } else {
            this.sb.append(v.length).append(", [").append('\n');
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (float o : v) {
                jd.display(o, (String) null);
            }
            display(']', (String) null);
        }
        return this;
    }

    public JceDisplayer displaySimple(float[] v, boolean bSep) {
        if (v == null || v.length == 0) {
            this.sb.append("[]");
            if (bSep) {
                this.sb.append("|");
            }
        } else {
            this.sb.append("[");
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (int i = 0; i < v.length; i++) {
                float o = v[i];
                if (i != 0) {
                    this.sb.append("|");
                }
                jd.displaySimple(o, false);
            }
            this.sb.append("]");
            if (bSep) {
                this.sb.append("|");
            }
        }
        return this;
    }

    public JceDisplayer display(double[] v, String fieldName) {
        ps(fieldName);
        if (v == null) {
            this.sb.append("null").append('\n');
        } else if (v.length == 0) {
            this.sb.append(v.length).append(", []").append('\n');
        } else {
            this.sb.append(v.length).append(", [").append('\n');
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (double o : v) {
                jd.display(o, (String) null);
            }
            display(']', (String) null);
        }
        return this;
    }

    public JceDisplayer displaySimple(double[] v, boolean bSep) {
        if (v == null || v.length == 0) {
            this.sb.append("[]");
            if (bSep) {
                this.sb.append("|");
            }
        } else {
            this.sb.append("[");
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (int i = 0; i < v.length; i++) {
                double o = v[i];
                if (i != 0) {
                    this.sb.append("|");
                }
                jd.displaySimple(o, false);
            }
            this.sb.append("[");
            if (bSep) {
                this.sb.append("|");
            }
        }
        return this;
    }

    public <K, V> JceDisplayer display(Map<K, V> m, String fieldName) {
        ps(fieldName);
        if (m == null) {
            this.sb.append("null").append('\n');
        } else if (m.isEmpty()) {
            this.sb.append(m.size()).append(", {}").append('\n');
        } else {
            this.sb.append(m.size()).append(", {").append('\n');
            JceDisplayer jd1 = new JceDisplayer(this.sb, this._level + 1);
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 2);
            for (Map.Entry<K, V> en : m.entrySet()) {
                jd1.display('(', (String) null);
                jd.display(en.getKey(), (String) null);
                jd.display(en.getValue(), (String) null);
                jd1.display(')', (String) null);
            }
            display('}', (String) null);
        }
        return this;
    }

    public <K, V> JceDisplayer displaySimple(Map<K, V> m, boolean bSep) {
        if (m == null || m.isEmpty()) {
            this.sb.append("{}");
            if (bSep) {
                this.sb.append("|");
            }
        } else {
            this.sb.append("{");
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 2);
            boolean first = true;
            for (Map.Entry<K, V> en : m.entrySet()) {
                if (!first) {
                    this.sb.append(",");
                }
                jd.displaySimple(en.getKey(), true);
                jd.displaySimple(en.getValue(), false);
                first = false;
            }
            this.sb.append("}");
            if (bSep) {
                this.sb.append("|");
            }
        }
        return this;
    }

    public <T> JceDisplayer display(T[] v, String fieldName) {
        ps(fieldName);
        if (v == null) {
            this.sb.append("null").append('\n');
        } else if (v.length == 0) {
            this.sb.append(v.length).append(", []").append('\n');
        } else {
            this.sb.append(v.length).append(", [").append('\n');
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (T o : v) {
                jd.display(o, (String) null);
            }
            display(']', (String) null);
        }
        return this;
    }

    public <T> JceDisplayer displaySimple(T[] v, boolean bSep) {
        if (v == null || v.length == 0) {
            this.sb.append("[]");
            if (bSep) {
                this.sb.append("|");
            }
        } else {
            this.sb.append("[");
            JceDisplayer jd = new JceDisplayer(this.sb, this._level + 1);
            for (int i = 0; i < v.length; i++) {
                T o = v[i];
                if (i != 0) {
                    this.sb.append("|");
                }
                jd.displaySimple((Object) o, false);
            }
            this.sb.append("]");
            if (bSep) {
                this.sb.append("|");
            }
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> JceDisplayer display(Collection<T> v, String fieldName) {
        if (v != null) {
            return display(v.toArray(), fieldName);
        }
        ps(fieldName);
        this.sb.append("null").append('\t');
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> JceDisplayer displaySimple(Collection<T> v, boolean bSep) {
        if (v != null) {
            return displaySimple(v.toArray(), bSep);
        }
        this.sb.append("[]");
        if (bSep) {
            this.sb.append("|");
            return this;
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> JceDisplayer display(T t, String fieldName) {
        if (t == 0) {
            this.sb.append("null").append('\n');
        } else if (t instanceof Byte) {
            display(((Byte) t).byteValue(), fieldName);
        } else if (t instanceof Boolean) {
            display(((Boolean) t).booleanValue(), fieldName);
        } else if (t instanceof Short) {
            display(((Short) t).shortValue(), fieldName);
        } else if (t instanceof Integer) {
            display(((Integer) t).intValue(), fieldName);
        } else if (t instanceof Long) {
            display(((Long) t).longValue(), fieldName);
        } else if (t instanceof Float) {
            display(((Float) t).floatValue(), fieldName);
        } else if (t instanceof Double) {
            display(((Double) t).doubleValue(), fieldName);
        } else if (t instanceof String) {
            display((String) t, fieldName);
        } else if (t instanceof Map) {
            display((Map) t, fieldName);
        } else if (t instanceof List) {
            display((Collection) t, fieldName);
        } else if (t instanceof JceStruct) {
            display((JceStruct) t, fieldName);
        } else if (t instanceof byte[]) {
            display((byte[]) t, fieldName);
        } else if (t instanceof boolean[]) {
            display((boolean[]) t, fieldName);
        } else if (t instanceof short[]) {
            display((short[]) t, fieldName);
        } else if (t instanceof int[]) {
            display((int[]) t, fieldName);
        } else if (t instanceof long[]) {
            display((long[]) t, fieldName);
        } else if (t instanceof float[]) {
            display((float[]) t, fieldName);
        } else if (t instanceof double[]) {
            display((double[]) t, fieldName);
        } else if (t.getClass().isArray()) {
            display((Object[]) t, fieldName);
        } else {
            throw new JceEncodeException("write object error: unsupport type.");
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> JceDisplayer displaySimple(T t, boolean bSep) {
        if (t == 0) {
            this.sb.append("null").append('\n');
        } else if (t instanceof Byte) {
            displaySimple(((Byte) t).byteValue(), bSep);
        } else if (t instanceof Boolean) {
            displaySimple(((Boolean) t).booleanValue(), bSep);
        } else if (t instanceof Short) {
            displaySimple(((Short) t).shortValue(), bSep);
        } else if (t instanceof Integer) {
            displaySimple(((Integer) t).intValue(), bSep);
        } else if (t instanceof Long) {
            displaySimple(((Long) t).longValue(), bSep);
        } else if (t instanceof Float) {
            displaySimple(((Float) t).floatValue(), bSep);
        } else if (t instanceof Double) {
            displaySimple(((Double) t).doubleValue(), bSep);
        } else if (t instanceof String) {
            displaySimple((String) t, bSep);
        } else if (t instanceof Map) {
            displaySimple((Map) t, bSep);
        } else if (t instanceof List) {
            displaySimple((Collection) t, bSep);
        } else if (t instanceof JceStruct) {
            displaySimple((JceStruct) t, bSep);
        } else if (t instanceof byte[]) {
            displaySimple((byte[]) t, bSep);
        } else if (t instanceof boolean[]) {
            displaySimple((boolean[]) t, bSep);
        } else if (t instanceof short[]) {
            displaySimple((short[]) t, bSep);
        } else if (t instanceof int[]) {
            displaySimple((int[]) t, bSep);
        } else if (t instanceof long[]) {
            displaySimple((long[]) t, bSep);
        } else if (t instanceof float[]) {
            displaySimple((float[]) t, bSep);
        } else if (t instanceof double[]) {
            displaySimple((double[]) t, bSep);
        } else if (t.getClass().isArray()) {
            displaySimple((Object[]) t, bSep);
        } else {
            throw new JceEncodeException("write object error: unsupport type.");
        }
        return this;
    }

    public JceDisplayer display(JceStruct v, String fieldName) {
        display('{', fieldName);
        if (v == null) {
            this.sb.append('\t').append("null");
        } else {
            v.display(this.sb, this._level + 1);
        }
        display('}', (String) null);
        return this;
    }

    public JceDisplayer displaySimple(JceStruct v, boolean bSep) {
        this.sb.append("{");
        if (v == null) {
            this.sb.append('\t').append("null");
        } else {
            v.displaySimple(this.sb, this._level + 1);
        }
        this.sb.append("}");
        if (bSep) {
            this.sb.append("|");
        }
        return this;
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(1.2d);
        System.out.println(sb.toString());
    }
}
