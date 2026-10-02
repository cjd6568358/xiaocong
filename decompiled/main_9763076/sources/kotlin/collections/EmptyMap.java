package kotlin.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Maps.kt */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Metadata
final class EmptyMap implements Serializable, Map {
    public static final EmptyMap INSTANCE = null;
    private static final long serialVersionUID = 8246714829545688274L;

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* synthetic */ Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Void put(Object obj, Void r4) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void putAll(Map map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public Object remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    static {
        new EmptyMap();
    }

    private EmptyMap() {
        INSTANCE = this;
    }

    @Override // java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (obj instanceof Void) {
            return containsValue((Void) obj);
        }
        return false;
    }

    @Override // java.util.Map
    public final /* bridge */ Set<Map.Entry> entrySet() {
        return getEntries();
    }

    @Override // java.util.Map
    public final /* bridge */ Object get(Object obj) {
        return get(obj);
    }

    @Override // java.util.Map
    public final /* bridge */ Set<Object> keySet() {
        return getKeys();
    }

    @Override // java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.Map
    public final /* bridge */ Collection values() {
        return getValues();
    }

    @Override // java.util.Map
    public boolean equals(Object other) {
        return (other instanceof Map) && ((Map) other).isEmpty();
    }

    @Override // java.util.Map
    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "{}";
    }

    public int getSize() {
        return 0;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return true;
    }

    @Override // java.util.Map
    public boolean containsKey(Object key) {
        return false;
    }

    public boolean containsValue(Void value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        return false;
    }

    @Override // java.util.Map
    public Void get(Object key) {
        return null;
    }

    public Set<Map.Entry> getEntries() {
        return EmptySet.INSTANCE;
    }

    public Set<Object> getKeys() {
        return EmptySet.INSTANCE;
    }

    public Collection getValues() {
        return EmptyList.INSTANCE;
    }

    private final Object readResolve() {
        return INSTANCE;
    }
}
