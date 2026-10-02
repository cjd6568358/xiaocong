package com.alibaba.fastjson.asm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ClassWriter {
    private int access;
    FieldWriter firstField;
    MethodWriter firstMethod;
    int index;
    private int interfaceCount;
    private int[] interfaces;
    Item[] items;
    final Item key;
    final Item key2;
    final Item key3;
    FieldWriter lastField;
    MethodWriter lastMethod;
    private int name;
    final ByteVector pool;
    private int superName;
    String thisName;
    int threshold;
    int version;

    public ClassWriter() {
        this(0);
    }

    private ClassWriter(int flags) {
        this.index = 1;
        this.pool = new ByteVector();
        this.items = new Item[256];
        this.threshold = (int) (0.75d * ((double) this.items.length));
        this.key = new Item();
        this.key2 = new Item();
        this.key3 = new Item();
    }

    public void visit(int version, int access, String name, String superName, String[] interfaces) {
        this.version = version;
        this.access = access;
        this.name = newClassItem(name).index;
        this.thisName = name;
        this.superName = superName == null ? 0 : newClassItem(superName).index;
        if (interfaces != null && interfaces.length > 0) {
            this.interfaceCount = interfaces.length;
            this.interfaces = new int[this.interfaceCount];
            for (int i = 0; i < this.interfaceCount; i++) {
                this.interfaces[i] = newClassItem(interfaces[i]).index;
            }
        }
    }

    public byte[] toByteArray() {
        int size = (this.interfaceCount * 2) + 24;
        int nbFields = 0;
        for (FieldWriter fb = this.firstField; fb != null; fb = fb.next) {
            nbFields++;
            size += fb.getSize();
        }
        int nbMethods = 0;
        for (MethodWriter mb = this.firstMethod; mb != null; mb = mb.next) {
            nbMethods++;
            size += mb.getSize();
        }
        ByteVector out = new ByteVector(size + this.pool.length);
        out.putInt(-889275714).putInt(this.version);
        out.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
        out.putShort(this.access & (-393217)).putShort(this.name).putShort(this.superName);
        out.putShort(this.interfaceCount);
        for (int i = 0; i < this.interfaceCount; i++) {
            out.putShort(this.interfaces[i]);
        }
        out.putShort(nbFields);
        for (FieldWriter fb2 = this.firstField; fb2 != null; fb2 = fb2.next) {
            fb2.put(out);
        }
        out.putShort(nbMethods);
        for (MethodWriter mb2 = this.firstMethod; mb2 != null; mb2 = mb2.next) {
            mb2.put(out);
        }
        out.putShort(0);
        return out.data;
    }

    Item newConstItem(Object cst) {
        if (cst instanceof Integer) {
            int val = ((Integer) cst).intValue();
            this.key.set(val);
            Item result = get(this.key);
            if (result == null) {
                this.pool.putByte(3).putInt(val);
                int i = this.index;
                this.index = i + 1;
                Item result2 = new Item(i, this.key);
                put(result2);
                return result2;
            }
            return result;
        }
        if (cst instanceof String) {
            Item result3 = newString((String) cst);
            return result3;
        }
        if (cst instanceof Type) {
            Type t = (Type) cst;
            Item result4 = newClassItem(t.sort == 10 ? t.getInternalName() : t.getDescriptor());
            return result4;
        }
        throw new IllegalArgumentException("value " + cst);
    }

    public int newUTF8(String value) {
        this.key.set(1, value, null, null);
        Item result = get(this.key);
        if (result == null) {
            this.pool.putByte(1).putUTF8(value);
            int i = this.index;
            this.index = i + 1;
            result = new Item(i, this.key);
            put(result);
        }
        return result.index;
    }

    public Item newClassItem(String value) {
        this.key2.set(7, value, null, null);
        Item result = get(this.key2);
        if (result == null) {
            this.pool.put12(7, newUTF8(value));
            int i = this.index;
            this.index = i + 1;
            Item result2 = new Item(i, this.key2);
            put(result2);
            return result2;
        }
        return result;
    }

    Item newFieldItem(String owner, String name, String desc) {
        this.key3.set(9, owner, name, desc);
        Item result = get(this.key3);
        if (result == null) {
            int s1 = newClassItem(owner).index;
            int s2 = newNameTypeItem(name, desc).index;
            this.pool.put12(9, s1).putShort(s2);
            int i = this.index;
            this.index = i + 1;
            Item result2 = new Item(i, this.key3);
            put(result2);
            return result2;
        }
        return result;
    }

    Item newMethodItem(String owner, String name, String desc, boolean itf) {
        int type = itf ? 11 : 10;
        this.key3.set(type, owner, name, desc);
        Item result = get(this.key3);
        if (result == null) {
            int s1 = newClassItem(owner).index;
            int s2 = newNameTypeItem(name, desc).index;
            this.pool.put12(type, s1).putShort(s2);
            int i = this.index;
            this.index = i + 1;
            Item result2 = new Item(i, this.key3);
            put(result2);
            return result2;
        }
        return result;
    }

    private Item newString(String value) {
        this.key2.set(8, value, null, null);
        Item result = get(this.key2);
        if (result == null) {
            this.pool.put12(8, newUTF8(value));
            int i = this.index;
            this.index = i + 1;
            Item result2 = new Item(i, this.key2);
            put(result2);
            return result2;
        }
        return result;
    }

    public Item newNameTypeItem(String name, String desc) {
        this.key2.set(12, name, desc, null);
        Item result = get(this.key2);
        if (result == null) {
            int s1 = newUTF8(name);
            int s2 = newUTF8(desc);
            this.pool.put12(12, s1).putShort(s2);
            int i = this.index;
            this.index = i + 1;
            Item result2 = new Item(i, this.key2);
            put(result2);
            return result2;
        }
        return result;
    }

    private Item get(Item key) {
        Item i = this.items[key.hashCode % this.items.length];
        while (i != null && (i.type != key.type || !key.isEqualTo(i))) {
            i = i.next;
        }
        return i;
    }

    private void put(Item i) {
        if (this.index > this.threshold) {
            int ll = this.items.length;
            int nl = (ll * 2) + 1;
            Item[] newItems = new Item[nl];
            for (int l = ll - 1; l >= 0; l--) {
                Item j = this.items[l];
                while (j != null) {
                    int index = j.hashCode % newItems.length;
                    Item k = j.next;
                    j.next = newItems[index];
                    newItems[index] = j;
                    j = k;
                }
            }
            this.items = newItems;
            this.threshold = (int) (((double) nl) * 0.75d);
        }
        int index2 = i.hashCode % this.items.length;
        i.next = this.items[index2];
        this.items[index2] = i;
    }
}
