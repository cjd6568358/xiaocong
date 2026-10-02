package android.support.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class MultiDexExtractor {

    private static class ExtractedDex extends File {
        public long crc;

        public ExtractedDex(File dexDir, String fileName) {
            super(dexDir, fileName);
            this.crc = -1L;
        }
    }

    static List<? extends File> load(Context context, File sourceApk, File dexDir, String prefsKeyPrefix, boolean forceReload) throws IOException {
        List<ExtractedDex> files;
        Log.i("MultiDex", "MultiDexExtractor.load(" + sourceApk.getPath() + ", " + forceReload + ", " + prefsKeyPrefix + ")");
        long currentCrc = getZipCrc(sourceApk);
        File lockFile = new File(dexDir, "MultiDex.lock");
        RandomAccessFile lockRaf = new RandomAccessFile(lockFile, "rw");
        FileChannel lockChannel = null;
        FileLock cacheLock = null;
        IOException releaseLockException = null;
        try {
            lockChannel = lockRaf.getChannel();
            Log.i("MultiDex", "Blocking on lock " + lockFile.getPath());
            cacheLock = lockChannel.lock();
            Log.i("MultiDex", lockFile.getPath() + " locked");
            if (!forceReload && !isModified(context, sourceApk, currentCrc, prefsKeyPrefix)) {
                try {
                    files = loadExistingExtractions(context, sourceApk, dexDir, prefsKeyPrefix);
                } catch (IOException ioe) {
                    Log.w("MultiDex", "Failed to reload existing extracted secondary dex files, falling back to fresh extraction", ioe);
                    files = performExtractions(sourceApk, dexDir);
                    putStoredApkInfo(context, prefsKeyPrefix, getTimeStamp(sourceApk), currentCrc, files);
                }
            } else {
                Log.i("MultiDex", "Detected that extraction must be performed.");
                files = performExtractions(sourceApk, dexDir);
                putStoredApkInfo(context, prefsKeyPrefix, getTimeStamp(sourceApk), currentCrc, files);
            }
            if (cacheLock != null) {
                try {
                    cacheLock.release();
                } catch (IOException e) {
                    Log.e("MultiDex", "Failed to release lock on " + lockFile.getPath());
                    releaseLockException = e;
                }
            }
            if (lockChannel != null) {
                closeQuietly(lockChannel);
            }
            closeQuietly(lockRaf);
            if (releaseLockException != null) {
                throw releaseLockException;
            }
            Log.i("MultiDex", "load found " + files.size() + " secondary dex files");
            return files;
        } catch (Throwable th) {
            if (cacheLock != null) {
                try {
                    cacheLock.release();
                } catch (IOException e2) {
                    Log.e("MultiDex", "Failed to release lock on " + lockFile.getPath());
                }
            }
            if (lockChannel != null) {
                closeQuietly(lockChannel);
            }
            closeQuietly(lockRaf);
            throw th;
        }
    }

    private static List<ExtractedDex> loadExistingExtractions(Context context, File sourceApk, File dexDir, String prefsKeyPrefix) throws IOException {
        Log.i("MultiDex", "loading existing secondary dex files");
        String extractedFilePrefix = sourceApk.getName() + ".classes";
        SharedPreferences multiDexPreferences = getMultiDexPreferences(context);
        int totalDexNumber = multiDexPreferences.getInt(prefsKeyPrefix + "dex.number", 1);
        List<ExtractedDex> files = new ArrayList<>(totalDexNumber - 1);
        for (int secondaryNumber = 2; secondaryNumber <= totalDexNumber; secondaryNumber++) {
            String fileName = extractedFilePrefix + secondaryNumber + ".zip";
            ExtractedDex extractedFile = new ExtractedDex(dexDir, fileName);
            if (extractedFile.isFile()) {
                extractedFile.crc = getZipCrc(extractedFile);
                long expectedCrc = multiDexPreferences.getLong(prefsKeyPrefix + "dex.crc." + secondaryNumber, -1L);
                long expectedModTime = multiDexPreferences.getLong(prefsKeyPrefix + "dex.time." + secondaryNumber, -1L);
                long lastModified = extractedFile.lastModified();
                if (expectedModTime != lastModified || expectedCrc != extractedFile.crc) {
                    throw new IOException("Invalid extracted dex: " + extractedFile + " (key \"" + prefsKeyPrefix + "\"), expected modification time: " + expectedModTime + ", modification time: " + lastModified + ", expected crc: " + expectedCrc + ", file crc: " + extractedFile.crc);
                }
                files.add(extractedFile);
            } else {
                throw new IOException("Missing extracted secondary dex file '" + extractedFile.getPath() + "'");
            }
        }
        return files;
    }

    private static boolean isModified(Context context, File archive, long currentCrc, String prefsKeyPrefix) {
        SharedPreferences prefs = getMultiDexPreferences(context);
        return (prefs.getLong(new StringBuilder().append(prefsKeyPrefix).append("timestamp").toString(), -1L) == getTimeStamp(archive) && prefs.getLong(new StringBuilder().append(prefsKeyPrefix).append("crc").toString(), -1L) == currentCrc) ? false : true;
    }

    private static long getTimeStamp(File archive) {
        long timeStamp = archive.lastModified();
        if (timeStamp == -1) {
            return timeStamp - 1;
        }
        return timeStamp;
    }

    private static long getZipCrc(File archive) throws IOException {
        long computedValue = ZipUtil.getZipCrc(archive);
        if (computedValue == -1) {
            return computedValue - 1;
        }
        return computedValue;
    }

    private static List<ExtractedDex> performExtractions(File sourceApk, File dexDir) throws IOException {
        String extractedFilePrefix = sourceApk.getName() + ".classes";
        prepareDexDir(dexDir, extractedFilePrefix);
        List<ExtractedDex> files = new ArrayList<>();
        ZipFile apk = new ZipFile(sourceApk);
        int secondaryNumber = 2;
        try {
            ZipEntry dexFile = apk.getEntry("classes2.dex");
            while (dexFile != null) {
                String fileName = extractedFilePrefix + secondaryNumber + ".zip";
                ExtractedDex extractedFile = new ExtractedDex(dexDir, fileName);
                files.add(extractedFile);
                Log.i("MultiDex", "Extraction is needed for file " + extractedFile);
                int numAttempts = 0;
                boolean isExtractionSuccessful = false;
                while (numAttempts < 3 && !isExtractionSuccessful) {
                    numAttempts++;
                    extract(apk, dexFile, extractedFile, extractedFilePrefix);
                    try {
                        extractedFile.crc = getZipCrc(extractedFile);
                        isExtractionSuccessful = true;
                    } catch (IOException e) {
                        isExtractionSuccessful = false;
                        Log.w("MultiDex", "Failed to read crc from " + extractedFile.getAbsolutePath(), e);
                    }
                    Log.i("MultiDex", "Extraction " + (isExtractionSuccessful ? "succeeded" : "failed") + " - length " + extractedFile.getAbsolutePath() + ": " + extractedFile.length() + " - crc: " + extractedFile.crc);
                    if (!isExtractionSuccessful) {
                        extractedFile.delete();
                        if (extractedFile.exists()) {
                            Log.w("MultiDex", "Failed to delete corrupted secondary dex '" + extractedFile.getPath() + "'");
                        }
                    }
                }
                if (!isExtractionSuccessful) {
                    throw new IOException("Could not create zip file " + extractedFile.getAbsolutePath() + " for secondary dex (" + secondaryNumber + ")");
                }
                secondaryNumber++;
                dexFile = apk.getEntry("classes" + secondaryNumber + ".dex");
            }
            try {
                apk.close();
            } catch (IOException e2) {
                Log.w("MultiDex", "Failed to close resource", e2);
            }
            return files;
        } catch (Throwable th) {
            try {
                apk.close();
            } catch (IOException e3) {
                Log.w("MultiDex", "Failed to close resource", e3);
            }
            throw th;
        }
    }

    private static void putStoredApkInfo(Context context, String keyPrefix, long timeStamp, long crc, List<ExtractedDex> extractedDexes) {
        SharedPreferences prefs = getMultiDexPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(keyPrefix + "timestamp", timeStamp);
        edit.putLong(keyPrefix + "crc", crc);
        edit.putInt(keyPrefix + "dex.number", extractedDexes.size() + 1);
        int extractedDexId = 2;
        for (ExtractedDex dex : extractedDexes) {
            edit.putLong(keyPrefix + "dex.crc." + extractedDexId, dex.crc);
            edit.putLong(keyPrefix + "dex.time." + extractedDexId, dex.lastModified());
            extractedDexId++;
        }
        edit.commit();
    }

    private static SharedPreferences getMultiDexPreferences(Context context) {
        return context.getSharedPreferences("multidex.version", Build.VERSION.SDK_INT < 11 ? 0 : 4);
    }

    private static void prepareDexDir(File dexDir, final String extractedFilePrefix) {
        FileFilter filter = new FileFilter() { // from class: android.support.multidex.MultiDexExtractor.1
            @Override // java.io.FileFilter
            public boolean accept(File pathname) {
                String name = pathname.getName();
                return (name.startsWith(extractedFilePrefix) || name.equals("MultiDex.lock")) ? false : true;
            }
        };
        File[] files = dexDir.listFiles(filter);
        if (files == null) {
            Log.w("MultiDex", "Failed to list secondary dex dir content (" + dexDir.getPath() + ").");
            return;
        }
        for (File oldFile : files) {
            Log.i("MultiDex", "Trying to delete old file " + oldFile.getPath() + " of size " + oldFile.length());
            if (!oldFile.delete()) {
                Log.w("MultiDex", "Failed to delete old file " + oldFile.getPath());
            } else {
                Log.i("MultiDex", "Deleted old file " + oldFile.getPath());
            }
        }
    }

    private static void extract(ZipFile apk, ZipEntry dexFile, File extractTo, String extractedFilePrefix) throws Throwable {
        InputStream in = apk.getInputStream(dexFile);
        File tmp = File.createTempFile("tmp-" + extractedFilePrefix, ".zip", extractTo.getParentFile());
        Log.i("MultiDex", "Extracting " + tmp.getPath());
        try {
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(tmp)));
            try {
                try {
                    ZipEntry classesDex = new ZipEntry("classes.dex");
                    classesDex.setTime(dexFile.getTime());
                    out.putNextEntry(classesDex);
                    byte[] buffer = new byte[16384];
                    for (int length = in.read(buffer); length != -1; length = in.read(buffer)) {
                        out.write(buffer, 0, length);
                    }
                    out.closeEntry();
                    out.close();
                    if (!tmp.setReadOnly()) {
                        throw new IOException("Failed to mark readonly \"" + tmp.getAbsolutePath() + "\" (tmp of \"" + extractTo.getAbsolutePath() + "\")");
                    }
                    Log.i("MultiDex", "Renaming to " + extractTo.getPath());
                    if (!tmp.renameTo(extractTo)) {
                        throw new IOException("Failed to rename \"" + tmp.getAbsolutePath() + "\" to \"" + extractTo.getAbsolutePath() + "\"");
                    }
                    closeQuietly(in);
                    tmp.delete();
                } catch (Throwable th) {
                    th = th;
                    closeQuietly(in);
                    tmp.delete();
                    throw th;
                }
            } catch (Throwable th2) {
                out.close();
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            Log.w("MultiDex", "Failed to close resource", e);
        }
    }
}
