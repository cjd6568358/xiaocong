package bsh;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CollectionManager {
    private static CollectionManager manager;

    public static class BasicBshIterator implements BshIterator {
        Enumeration enumeration;

        public BasicBshIterator(Object obj) {
            this.enumeration = createEnumeration(obj);
        }

        protected Enumeration createEnumeration(Object obj) {
            if (obj == null) {
                throw new NullPointerException("Object arguments passed to the BasicBshIterator constructor cannot be null.");
            }
            if (obj instanceof Enumeration) {
                return (Enumeration) obj;
            }
            if (obj instanceof Vector) {
                return ((Vector) obj).elements();
            }
            if (obj.getClass().isArray()) {
                return new Enumeration(this, obj) { // from class: bsh.CollectionManager.1
                    int index = 0;
                    int length;
                    private final BasicBshIterator this$0;
                    private final Object val$array;

                    {
                        this.this$0 = this;
                        this.val$array = obj;
                        this.length = Array.getLength(this.val$array);
                    }

                    @Override // java.util.Enumeration
                    public boolean hasMoreElements() {
                        return this.index < this.length;
                    }

                    @Override // java.util.Enumeration
                    public Object nextElement() {
                        Object obj2 = this.val$array;
                        int i = this.index;
                        this.index = i + 1;
                        return Array.get(obj2, i);
                    }
                };
            }
            if (obj instanceof String) {
                return createEnumeration(((String) obj).toCharArray());
            }
            if (obj instanceof StringBuffer) {
                return createEnumeration(obj.toString().toCharArray());
            }
            throw new IllegalArgumentException(new StringBuffer().append("Cannot enumerate object of type ").append(obj.getClass()).toString());
        }

        @Override // bsh.BshIterator
        public boolean hasNext() {
            return this.enumeration.hasMoreElements();
        }

        @Override // bsh.BshIterator
        public Object next() {
            return this.enumeration.nextElement();
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0021 A[Catch: all -> 0x0045, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000f, B:16:0x002d, B:9:0x001d, B:11:0x0021, B:12:0x0028), top: B:23:0x0003, inners: #0 }] */
    public static synchronized CollectionManager getCollectionManager() {
        if (manager == null && Capabilities.classExists("java.util.Collection")) {
            try {
                manager = (CollectionManager) Class.forName("bsh.collection.CollectionManagerImpl").newInstance();
            } catch (Exception e) {
                Interpreter.debug(new StringBuffer().append("unable to load CollectionManagerImpl: ").append(e).toString());
            }
            if (manager == null) {
                manager = new CollectionManager();
            }
        } else {
            if (manager == null) {
                manager = new CollectionManager();
            }
        }
        throw th;
        return manager;
    }

    public BshIterator getBshIterator(Object obj) throws IllegalArgumentException {
        return new BasicBshIterator(obj);
    }

    public Object getFromMap(Object obj, Object obj2) {
        return ((Hashtable) obj).get(obj2);
    }

    public boolean isBshIterable(Object obj) {
        try {
            getBshIterator(obj);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isMap(Object obj) {
        return obj instanceof Hashtable;
    }

    public Object putInMap(Object obj, Object obj2, Object obj3) {
        return ((Hashtable) obj).put(obj2, obj3);
    }
}
