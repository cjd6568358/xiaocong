package com.xiaocong.smarthome.util;

import android.content.Context;
import android.os.Process;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ACache {
    private static Map<String, ACache> mInstanceMap = new HashMap();
    private ACacheManager mCache;

    public static ACache get(Context ctx) {
        return get(ctx, "ACache");
    }

    public static ACache get(Context ctx, String cacheName) {
        File f = new File(ctx.getCacheDir(), cacheName);
        return get(f, 50000000L, Integer.MAX_VALUE);
    }

    public static ACache get(File cacheDir, long max_zise, int max_count) {
        ACache manager = mInstanceMap.get(cacheDir.getAbsoluteFile() + myPid());
        if (manager == null) {
            ACache manager2 = new ACache(cacheDir, max_zise, max_count);
            mInstanceMap.put(cacheDir.getAbsolutePath() + myPid(), manager2);
            return manager2;
        }
        return manager;
    }

    private static String myPid() {
        return "_" + Process.myPid();
    }

    private ACache(File cacheDir, long max_size, int max_count) {
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw new RuntimeException("can't make dirs in " + cacheDir.getAbsolutePath());
        }
        this.mCache = new ACacheManager(cacheDir, max_size, max_count);
    }

    public void put(String key, String value) throws Throwable {
        File file = this.mCache.newFile(key);
        BufferedWriter out = null;
        try {
            try {
                BufferedWriter out2 = new BufferedWriter(new FileWriter(file), 1024);
                try {
                    out2.write(value);
                    if (out2 != null) {
                        try {
                            out2.flush();
                            out2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    this.mCache.put(file);
                    out = out2;
                } catch (IOException e2) {
                    e = e2;
                    out = out2;
                    e.printStackTrace();
                    if (out != null) {
                        try {
                            out.flush();
                            out.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    this.mCache.put(file);
                } catch (Throwable th) {
                    th = th;
                    out = out2;
                    if (out != null) {
                        try {
                            out.flush();
                            out.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    this.mCache.put(file);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e5) {
            e = e5;
        }
    }

    public String getAsString(String key) throws Throwable {
        String strClearDateInfo = null;
        File file = this.mCache.get(key);
        if (file.exists()) {
            boolean removeFile = false;
            BufferedReader in = null;
            try {
                try {
                    BufferedReader in2 = new BufferedReader(new FileReader(file));
                    String readString = "";
                    while (true) {
                        try {
                            String currentLine = in2.readLine();
                            if (currentLine == null) {
                                break;
                            }
                            readString = readString + currentLine;
                        } catch (IOException e) {
                            e = e;
                            in = in2;
                            e.printStackTrace();
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            if (0 != 0) {
                                remove(key);
                            }
                        } catch (Throwable th) {
                            th = th;
                            in = in2;
                            if (in != null) {
                                try {
                                    in.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (removeFile) {
                                remove(key);
                            }
                            throw th;
                        }
                    }
                    if (Utils.isDue(readString)) {
                        removeFile = true;
                        if (in2 != null) {
                            try {
                                in2.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (1 != 0) {
                            remove(key);
                        }
                    } else {
                        strClearDateInfo = Utils.clearDateInfo(readString);
                        if (in2 != null) {
                            try {
                                in2.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        if (0 != 0) {
                            remove(key);
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e6) {
                e = e6;
            }
        }
        return strClearDateInfo;
    }

    public boolean remove(String key) {
        return this.mCache.remove(key);
    }

    public void clear() {
        this.mCache.clear();
    }

    public class ACacheManager {
        private final AtomicInteger cacheCount;
        protected File cacheDir;
        private final AtomicLong cacheSize;
        private final int countLimit;
        private final Map<File, Long> lastUsageDates;
        private final long sizeLimit;

        private ACacheManager(File cacheDir, long sizeLimit, int countLimit) {
            this.lastUsageDates = Collections.synchronizedMap(new HashMap());
            this.cacheDir = cacheDir;
            this.sizeLimit = sizeLimit;
            this.countLimit = countLimit;
            this.cacheSize = new AtomicLong();
            this.cacheCount = new AtomicInteger();
            calculateCacheSizeAndCacheCount();
        }

        private void calculateCacheSizeAndCacheCount() {
            new Thread(new Runnable() { // from class: com.xiaocong.smarthome.util.ACache.ACacheManager.1
                @Override // java.lang.Runnable
                public void run() {
                    int size = 0;
                    int count = 0;
                    File[] cachedFiles = ACacheManager.this.cacheDir.listFiles();
                    if (cachedFiles != null) {
                        for (File cachedFile : cachedFiles) {
                            size = (int) (((long) size) + ACacheManager.this.calculateSize(cachedFile));
                            count++;
                            ACacheManager.this.lastUsageDates.put(cachedFile, Long.valueOf(cachedFile.lastModified()));
                        }
                        ACacheManager.this.cacheSize.set(size);
                        ACacheManager.this.cacheCount.set(count);
                    }
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void put(File file) {
            int curCacheCount = this.cacheCount.get();
            while (curCacheCount + 1 > this.countLimit) {
                long freedSize = removeNext();
                this.cacheSize.addAndGet(-freedSize);
                curCacheCount = this.cacheCount.addAndGet(-1);
            }
            this.cacheCount.addAndGet(1);
            long valueSize = calculateSize(file);
            long curCacheSize = this.cacheSize.get();
            while (curCacheSize + valueSize > this.sizeLimit) {
                long freedSize2 = removeNext();
                curCacheSize = this.cacheSize.addAndGet(-freedSize2);
            }
            this.cacheSize.addAndGet(valueSize);
            Long currentTime = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(currentTime.longValue());
            this.lastUsageDates.put(file, currentTime);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File get(String key) {
            File file = newFile(key);
            Long currentTime = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(currentTime.longValue());
            this.lastUsageDates.put(file, currentTime);
            return file;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File newFile(String key) {
            return new File(this.cacheDir, key.hashCode() + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean remove(String key) {
            File image = get(key);
            return image.delete();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clear() {
            this.lastUsageDates.clear();
            this.cacheSize.set(0L);
            File[] files = this.cacheDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    f.delete();
                }
            }
        }

        private long removeNext() {
            if (this.lastUsageDates.isEmpty()) {
                return 0L;
            }
            Long oldestUsage = null;
            File mostLongUsedFile = null;
            Set<Map.Entry<File, Long>> entries = this.lastUsageDates.entrySet();
            synchronized (this.lastUsageDates) {
                for (Map.Entry<File, Long> entry : entries) {
                    if (mostLongUsedFile == null) {
                        mostLongUsedFile = entry.getKey();
                        oldestUsage = entry.getValue();
                    } else {
                        Long lastValueUsage = entry.getValue();
                        if (lastValueUsage.longValue() < oldestUsage.longValue()) {
                            oldestUsage = lastValueUsage;
                            mostLongUsedFile = entry.getKey();
                        }
                    }
                }
            }
            long jCalculateSize = calculateSize(mostLongUsedFile);
            if (mostLongUsedFile.delete()) {
                this.lastUsageDates.remove(mostLongUsedFile);
                return jCalculateSize;
            }
            return jCalculateSize;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long calculateSize(File file) {
            return file.length();
        }
    }

    private static class Utils {
        /* JADX INFO: Access modifiers changed from: private */
        public static boolean isDue(String str) {
            return isDue(str.getBytes());
        }

        private static boolean isDue(byte[] data) {
            String[] strs = getDateInfoFromDate(data);
            if (strs != null && strs.length == 2) {
                String saveTimeStr = strs[0];
                while (saveTimeStr.startsWith("0")) {
                    saveTimeStr = saveTimeStr.substring(1, saveTimeStr.length());
                }
                long saveTime = Long.valueOf(saveTimeStr).longValue();
                long deleteAfter = Long.valueOf(strs[1]).longValue();
                if (System.currentTimeMillis() > (1000 * deleteAfter) + saveTime) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String clearDateInfo(String strInfo) {
            if (strInfo != null && hasDateInfo(strInfo.getBytes())) {
                return strInfo.substring(strInfo.indexOf(32) + 1, strInfo.length());
            }
            return strInfo;
        }

        private static boolean hasDateInfo(byte[] data) {
            return data != null && data.length > 15 && data[13] == 45 && indexOf(data, ' ') > 14;
        }

        private static String[] getDateInfoFromDate(byte[] data) {
            if (!hasDateInfo(data)) {
                return null;
            }
            String saveDate = new String(copyOfRange(data, 0, 13));
            String deleteAfter = new String(copyOfRange(data, 14, indexOf(data, ' ')));
            return new String[]{saveDate, deleteAfter};
        }

        private static int indexOf(byte[] data, char c) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] == c) {
                    return i;
                }
            }
            return -1;
        }

        private static byte[] copyOfRange(byte[] original, int from, int to) {
            int newLength = to - from;
            if (newLength < 0) {
                throw new IllegalArgumentException(from + " > " + to);
            }
            byte[] copy = new byte[newLength];
            System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
            return copy;
        }
    }
}
