package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import com.bumptech.glide.util.Util;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@TargetApi(19)
public class SizeConfigStrategy implements LruPoolStrategy {
    private static final Bitmap.Config[] ARGB_8888_IN_CONFIGS = {Bitmap.Config.ARGB_8888, null};
    private static final Bitmap.Config[] RGB_565_IN_CONFIGS = {Bitmap.Config.RGB_565};
    private static final Bitmap.Config[] ARGB_4444_IN_CONFIGS = {Bitmap.Config.ARGB_4444};
    private static final Bitmap.Config[] ALPHA_8_IN_CONFIGS = {Bitmap.Config.ALPHA_8};
    private final KeyPool keyPool = new KeyPool();
    private final GroupedLinkedMap<Key, Bitmap> groupedMap = new GroupedLinkedMap<>();
    private final Map<Bitmap.Config, NavigableMap<Integer, Integer>> sortedSizes = new HashMap();

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public void put(Bitmap bitmap) {
        int size = Util.getBitmapByteSize(bitmap);
        Key key = this.keyPool.get(size, bitmap.getConfig());
        this.groupedMap.put(key, bitmap);
        NavigableMap<Integer, Integer> sizes = getSizesForConfig(bitmap.getConfig());
        Integer current = (Integer) sizes.get(Integer.valueOf(key.size));
        sizes.put(Integer.valueOf(key.size), Integer.valueOf(current == null ? 1 : current.intValue() + 1));
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public Bitmap get(int width, int height, Bitmap.Config config) {
        int size = Util.getBitmapByteSize(width, height, config);
        Key targetKey = this.keyPool.get(size, config);
        Key bestKey = findBestKey(targetKey, size, config);
        Bitmap result = this.groupedMap.get(bestKey);
        if (result != null) {
            decrementBitmapOfSize(Integer.valueOf(Util.getBitmapByteSize(result)), result.getConfig());
            result.reconfigure(width, height, result.getConfig() != null ? result.getConfig() : Bitmap.Config.ARGB_8888);
        }
        return result;
    }

    private Key findBestKey(Key key, int size, Bitmap.Config config) {
        Bitmap.Config[] arr$ = getInConfigs(config);
        for (Bitmap.Config possibleConfig : arr$) {
            NavigableMap<Integer, Integer> sizesForPossibleConfig = getSizesForConfig(possibleConfig);
            Integer possibleSize = sizesForPossibleConfig.ceilingKey(Integer.valueOf(size));
            if (possibleSize != null && possibleSize.intValue() <= size * 8) {
                if (possibleSize.intValue() == size) {
                    if (possibleConfig == null) {
                        if (config == null) {
                            return key;
                        }
                    } else if (possibleConfig.equals(config)) {
                        return key;
                    }
                }
                this.keyPool.offer(key);
                Key result = this.keyPool.get(possibleSize.intValue(), possibleConfig);
                return result;
            }
        }
        return key;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public Bitmap removeLast() {
        Bitmap removed = this.groupedMap.removeLast();
        if (removed != null) {
            int removedSize = Util.getBitmapByteSize(removed);
            decrementBitmapOfSize(Integer.valueOf(removedSize), removed.getConfig());
        }
        return removed;
    }

    private void decrementBitmapOfSize(Integer size, Bitmap.Config config) {
        NavigableMap<Integer, Integer> sizes = getSizesForConfig(config);
        Integer current = (Integer) sizes.get(size);
        if (current.intValue() == 1) {
            sizes.remove(size);
        } else {
            sizes.put(size, Integer.valueOf(current.intValue() - 1));
        }
    }

    private NavigableMap<Integer, Integer> getSizesForConfig(Bitmap.Config config) {
        NavigableMap<Integer, Integer> sizes = this.sortedSizes.get(config);
        if (sizes == null) {
            NavigableMap<Integer, Integer> sizes2 = new TreeMap<>();
            this.sortedSizes.put(config, sizes2);
            return sizes2;
        }
        return sizes;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public String logBitmap(Bitmap bitmap) {
        int size = Util.getBitmapByteSize(bitmap);
        return getBitmapString(size, bitmap.getConfig());
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public String logBitmap(int width, int height, Bitmap.Config config) {
        int size = Util.getBitmapByteSize(width, height, config);
        return getBitmapString(size, config);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy
    public int getSize(Bitmap bitmap) {
        return Util.getBitmapByteSize(bitmap);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder().append("SizeConfigStrategy{groupedMap=").append(this.groupedMap).append(", sortedSizes=(");
        for (Map.Entry<Bitmap.Config, NavigableMap<Integer, Integer>> entry : this.sortedSizes.entrySet()) {
            sb.append(entry.getKey()).append('[').append(entry.getValue()).append("], ");
        }
        if (!this.sortedSizes.isEmpty()) {
            sb.replace(sb.length() - 2, sb.length(), Constants.MAIN_VERSION_TAG);
        }
        return sb.append(")}").toString();
    }

    static class KeyPool extends BaseKeyPool<Key> {
        KeyPool() {
        }

        public Key get(int size, Bitmap.Config config) {
            Key result = get();
            result.init(size, config);
            return result;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.bumptech.glide.load.engine.bitmap_recycle.BaseKeyPool
        public Key create() {
            return new Key(this);
        }
    }

    static final class Key implements Poolable {
        private Bitmap.Config config;
        private final KeyPool pool;
        private int size;

        public Key(KeyPool pool) {
            this.pool = pool;
        }

        public void init(int size, Bitmap.Config config) {
            this.size = size;
            this.config = config;
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.Poolable
        public void offer() {
            this.pool.offer(this);
        }

        public String toString() {
            return SizeConfigStrategy.getBitmapString(this.size, this.config);
        }

        public boolean equals(Object o) {
            if (!(o instanceof Key)) {
                return false;
            }
            Key other = (Key) o;
            if (this.size != other.size) {
                return false;
            }
            if (this.config == null) {
                if (other.config != null) {
                    return false;
                }
            } else if (!this.config.equals(other.config)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int result = this.size;
            return (result * 31) + (this.config != null ? this.config.hashCode() : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getBitmapString(int size, Bitmap.Config config) {
        return "[" + size + "](" + config + ")";
    }

    /* JADX INFO: renamed from: com.bumptech.glide.load.engine.bitmap_recycle.SizeConfigStrategy$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config = new int[Bitmap.Config.values().length];

        static {
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_8888.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.RGB_565.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ARGB_4444.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$Config[Bitmap.Config.ALPHA_8.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private static Bitmap.Config[] getInConfigs(Bitmap.Config requested) {
        switch (AnonymousClass1.$SwitchMap$android$graphics$Bitmap$Config[requested.ordinal()]) {
            case 1:
                return ARGB_8888_IN_CONFIGS;
            case 2:
                return RGB_565_IN_CONFIGS;
            case 3:
                return ARGB_4444_IN_CONFIGS;
            case 4:
                return ALPHA_8_IN_CONFIGS;
            default:
                return new Bitmap.Config[]{requested};
        }
    }
}
