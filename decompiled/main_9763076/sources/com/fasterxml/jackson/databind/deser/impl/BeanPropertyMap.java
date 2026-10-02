package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BeanPropertyMap implements Iterable<SettableBeanProperty> {
    private final Bucket[] _buckets;
    private final int _hashMask;
    private final int _size;

    public BeanPropertyMap(Collection<SettableBeanProperty> properties) {
        this._size = properties.size();
        int bucketCount = findSize(this._size);
        this._hashMask = bucketCount - 1;
        Bucket[] buckets = new Bucket[bucketCount];
        for (SettableBeanProperty property : properties) {
            String key = property.getName();
            int index = key.hashCode() & this._hashMask;
            buckets[index] = new Bucket(buckets[index], key, property);
        }
        this._buckets = buckets;
    }

    private BeanPropertyMap(Bucket[] buckets, int size) {
        this._buckets = buckets;
        this._size = size;
        this._hashMask = buckets.length - 1;
    }

    public BeanPropertyMap withProperty(SettableBeanProperty newProperty) {
        int bcount = this._buckets.length;
        Bucket[] newBuckets = new Bucket[bcount];
        System.arraycopy(this._buckets, 0, newBuckets, 0, bcount);
        String propName = newProperty.getName();
        SettableBeanProperty oldProp = find(newProperty.getName());
        if (oldProp == null) {
            int index = propName.hashCode() & this._hashMask;
            newBuckets[index] = new Bucket(newBuckets[index], propName, newProperty);
            return new BeanPropertyMap(newBuckets, this._size + 1);
        }
        BeanPropertyMap newMap = new BeanPropertyMap(newBuckets, bcount);
        newMap.replace(newProperty);
        return newMap;
    }

    public BeanPropertyMap renameAll(NameTransformer transformer) {
        JsonDeserializer<?> jsonDeserializerUnwrappingDeserializer;
        if (transformer != null && transformer != NameTransformer.NOP) {
            ArrayList<SettableBeanProperty> newProps = new ArrayList<>();
            for (SettableBeanProperty prop : this) {
                String newName = transformer.transform(prop.getName());
                SettableBeanProperty prop2 = prop.withName(newName);
                JsonDeserializer<?> deser = prop2.getValueDeserializer();
                if (deser != null && (jsonDeserializerUnwrappingDeserializer = deser.unwrappingDeserializer(transformer)) != deser) {
                    prop2 = prop2.withValueDeserializer(jsonDeserializerUnwrappingDeserializer);
                }
                newProps.add(prop2);
            }
            return new BeanPropertyMap(newProps);
        }
        return this;
    }

    public BeanPropertyMap assignIndexes() {
        int index = 0;
        Bucket[] arr$ = this._buckets;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            Bucket bucket = arr$[i$];
            int index2 = index;
            while (bucket != null) {
                bucket.value.assignIndex(index2);
                bucket = bucket.next;
                index2++;
            }
            i$++;
            index = index2;
        }
        return this;
    }

    private static final int findSize(int size) {
        int needed = size <= 32 ? size + size : size + (size >> 2);
        int result = 2;
        while (result < needed) {
            result += result;
        }
        return result;
    }

    @Override // java.lang.Iterable
    public Iterator<SettableBeanProperty> iterator() {
        return new IteratorImpl(this._buckets);
    }

    public int size() {
        return this._size;
    }

    public SettableBeanProperty find(String key) {
        int index = key.hashCode() & this._hashMask;
        Bucket bucket = this._buckets[index];
        if (bucket == null) {
            return null;
        }
        if (bucket.key == key) {
            return bucket.value;
        }
        do {
            bucket = bucket.next;
            if (bucket == null) {
                return _findWithEquals(key, index);
            }
        } while (bucket.key != key);
        return bucket.value;
    }

    public void replace(SettableBeanProperty property) {
        Bucket tail;
        String name = property.getName();
        int index = name.hashCode() & (this._buckets.length - 1);
        boolean found = false;
        Bucket bucket = this._buckets[index];
        Bucket tail2 = null;
        while (bucket != null) {
            if (!found && bucket.key.equals(name)) {
                found = true;
                tail = tail2;
            } else {
                tail = new Bucket(tail2, bucket.key, bucket.value);
            }
            bucket = bucket.next;
            tail2 = tail;
        }
        if (!found) {
            throw new NoSuchElementException("No entry '" + property + "' found, can't replace");
        }
        this._buckets[index] = new Bucket(tail2, name, property);
    }

    public void remove(SettableBeanProperty property) {
        Bucket tail;
        String name = property.getName();
        int index = name.hashCode() & (this._buckets.length - 1);
        boolean found = false;
        Bucket bucket = this._buckets[index];
        Bucket tail2 = null;
        while (bucket != null) {
            if (!found && bucket.key.equals(name)) {
                found = true;
                tail = tail2;
            } else {
                tail = new Bucket(tail2, bucket.key, bucket.value);
            }
            bucket = bucket.next;
            tail2 = tail;
        }
        if (!found) {
            throw new NoSuchElementException("No entry '" + property + "' found, can't remove");
        }
        this._buckets[index] = tail2;
    }

    private SettableBeanProperty _findWithEquals(String key, int index) {
        for (Bucket bucket = this._buckets[index]; bucket != null; bucket = bucket.next) {
            if (key.equals(bucket.key)) {
                return bucket.value;
            }
        }
        return null;
    }

    private static final class Bucket {
        public final String key;
        public final Bucket next;
        public final SettableBeanProperty value;

        public Bucket(Bucket next, String key, SettableBeanProperty value) {
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }

    private static final class IteratorImpl implements Iterator<SettableBeanProperty> {
        private final Bucket[] _buckets;
        private Bucket _currentBucket;
        private int _nextBucketIndex;

        public IteratorImpl(Bucket[] buckets) {
            int i;
            this._buckets = buckets;
            int len = this._buckets.length;
            int i2 = 0;
            while (i2 < len) {
                i = i2 + 1;
                Bucket b = this._buckets[i2];
                if (b != null) {
                    this._currentBucket = b;
                    this._nextBucketIndex = i;
                }
                i2 = i;
            }
            i = i2;
            this._nextBucketIndex = i;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this._currentBucket != null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public SettableBeanProperty next() {
            Bucket curr = this._currentBucket;
            if (curr == null) {
                throw new NoSuchElementException();
            }
            Bucket b = curr.next;
            while (b == null && this._nextBucketIndex < this._buckets.length) {
                Bucket[] bucketArr = this._buckets;
                int i = this._nextBucketIndex;
                this._nextBucketIndex = i + 1;
                b = bucketArr[i];
            }
            this._currentBucket = b;
            return curr.value;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
