package android.support.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;
import org.apache.http.cookie.ClientCookie;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class MultiDex {
    private static final Set<File> installedApk = new HashSet();
    private static final boolean IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty("java.vm.version"));

    public static void install(Context context) {
        Log.i("MultiDex", "Installing application");
        if (IS_VM_MULTIDEX_CAPABLE) {
            Log.i("MultiDex", "VM has multidex support, MultiDex support library is disabled.");
            return;
        }
        if (Build.VERSION.SDK_INT < 4) {
            throw new RuntimeException("MultiDex installation failed. SDK " + Build.VERSION.SDK_INT + " is unsupported. Min SDK version is 4.");
        }
        try {
            ApplicationInfo applicationInfo = getApplicationInfo(context);
            if (applicationInfo == null) {
                Log.i("MultiDex", "No ApplicationInfo available, i.e. running on a test Context: MultiDex support library is disabled.");
            } else {
                doInstallation(context, new File(applicationInfo.sourceDir), new File(applicationInfo.dataDir), "secondary-dexes", Constants.MAIN_VERSION_TAG);
                Log.i("MultiDex", "install done");
            }
        } catch (Exception e) {
            Log.e("MultiDex", "MultiDex installation failure", e);
            throw new RuntimeException("MultiDex installation failed (" + e.getMessage() + ").");
        }
    }

    private static void doInstallation(Context mainContext, File sourceApk, File dataDir, String secondaryFolderName, String prefsKeyPrefix) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IOException, IllegalArgumentException, InvocationTargetException {
        synchronized (installedApk) {
            if (!installedApk.contains(sourceApk)) {
                installedApk.add(sourceApk);
                if (Build.VERSION.SDK_INT > 20) {
                    Log.w("MultiDex", "MultiDex is not guaranteed to work in SDK version " + Build.VERSION.SDK_INT + ": SDK version higher than 20 should be backed by runtime with built-in multidex capabilty but it's not the case here: java.vm.version=\"" + System.getProperty("java.vm.version") + "\"");
                }
                try {
                    ClassLoader loader = mainContext.getClassLoader();
                    if (loader == null) {
                        Log.e("MultiDex", "Context class loader is null. Must be running in test mode. Skip patching.");
                        return;
                    }
                    try {
                        clearOldDexDir(mainContext);
                    } catch (Throwable t) {
                        Log.w("MultiDex", "Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.", t);
                    }
                    File dexDir = getDexDir(mainContext, dataDir, secondaryFolderName);
                    List<? extends File> files = MultiDexExtractor.load(mainContext, sourceApk, dexDir, prefsKeyPrefix, false);
                    installSecondaryDexes(loader, dexDir, files);
                } catch (RuntimeException e) {
                    Log.w("MultiDex", "Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.", e);
                }
            }
        }
    }

    private static ApplicationInfo getApplicationInfo(Context context) {
        try {
            return context.getApplicationInfo();
        } catch (RuntimeException e) {
            Log.w("MultiDex", "Failure while trying to obtain ApplicationInfo from Context. Must be running in test mode. Skip patching.", e);
            return null;
        }
    }

    static boolean isVMMultidexCapable(String versionString) {
        boolean isMultidexCapable = false;
        if (versionString != null) {
            Matcher matcher = Pattern.compile("(\\d+)\\.(\\d+)(\\.\\d+)?").matcher(versionString);
            if (matcher.matches()) {
                try {
                    int major = Integer.parseInt(matcher.group(1));
                    int minor = Integer.parseInt(matcher.group(2));
                    isMultidexCapable = major > 2 || (major == 2 && minor >= 1);
                } catch (NumberFormatException e) {
                }
            }
        }
        Log.i("MultiDex", "VM with version " + versionString + (isMultidexCapable ? " has multidex support" : " does not have multidex support"));
        return isMultidexCapable;
    }

    private static void installSecondaryDexes(ClassLoader loader, File dexDir, List<? extends File> files) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IOException, IllegalArgumentException, InvocationTargetException {
        if (!files.isEmpty()) {
            if (Build.VERSION.SDK_INT < 19) {
                if (Build.VERSION.SDK_INT >= 14) {
                    V14.install(loader, files, dexDir);
                    return;
                } else {
                    V4.install(loader, files);
                    return;
                }
            }
            V19.install(loader, files, dexDir);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }

    private static void clearOldDexDir(Context context) throws Exception {
        File dexDir = new File(context.getFilesDir(), "secondary-dexes");
        if (dexDir.isDirectory()) {
            Log.i("MultiDex", "Clearing old secondary dex dir (" + dexDir.getPath() + ").");
            File[] files = dexDir.listFiles();
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
            if (!dexDir.delete()) {
                Log.w("MultiDex", "Failed to delete secondary dex dir " + dexDir.getPath());
            } else {
                Log.i("MultiDex", "Deleted old secondary dex dir " + dexDir.getPath());
            }
        }
    }

    private static File getDexDir(Context context, File dataDir, String secondaryFolderName) throws IOException {
        File cache = new File(dataDir, "code_cache");
        try {
            mkdirChecked(cache);
        } catch (IOException e) {
            cache = new File(context.getFilesDir(), "code_cache");
            mkdirChecked(cache);
        }
        File dexDir = new File(cache, secondaryFolderName);
        mkdirChecked(dexDir);
        return dexDir;
    }

    private static void mkdirChecked(File dir) throws IOException {
        dir.mkdir();
        if (!dir.isDirectory()) {
            File parent = dir.getParentFile();
            if (parent == null) {
                Log.e("MultiDex", "Failed to create dir " + dir.getPath() + ". Parent file is null.");
            } else {
                Log.e("MultiDex", "Failed to create dir " + dir.getPath() + ". parent file is a dir " + parent.isDirectory() + ", a file " + parent.isFile() + ", exists " + parent.exists() + ", readable " + parent.canRead() + ", writable " + parent.canWrite());
            }
            throw new IOException("Failed to create directory " + dir.getPath());
        }
    }

    private static final class V19 {
        /* JADX INFO: Access modifiers changed from: private */
        public static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries, File optimizedDirectory) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
            IOException[] dexElementsSuppressedExceptions;
            Field pathListField = MultiDex.findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions));
            if (suppressedExceptions.size() > 0) {
                for (IOException e : suppressedExceptions) {
                    Log.w("MultiDex", "Exception in makeDexElement", e);
                }
                Field suppressedExceptionsField = MultiDex.findField(dexPathList, "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions2 = (IOException[]) suppressedExceptionsField.get(dexPathList);
                if (dexElementsSuppressedExceptions2 == null) {
                    dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[suppressedExceptions.size() + dexElementsSuppressedExceptions2.length];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions2, 0, combined, suppressedExceptions.size(), dexElementsSuppressedExceptions2.length);
                    dexElementsSuppressedExceptions = combined;
                }
                suppressedExceptionsField.set(dexPathList, dexElementsSuppressedExceptions);
            }
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            Method makeDexElements = MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class);
            return (Object[]) makeDexElements.invoke(dexPathList, files, optimizedDirectory, suppressedExceptions);
        }
    }

    private static final class V14 {
        /* JADX INFO: Access modifiers changed from: private */
        public static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries, File optimizedDirectory) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
            Field pathListField = MultiDex.findField(loader, "pathList");
            Object dexPathList = pathListField.get(loader);
            MultiDex.expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory));
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            Method makeDexElements = MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class);
            return (Object[]) makeDexElements.invoke(dexPathList, files, optimizedDirectory);
        }
    }

    private static final class V4 {
        /* JADX INFO: Access modifiers changed from: private */
        public static void install(ClassLoader loader, List<? extends File> additionalClassPathEntries) throws IllegalAccessException, NoSuchFieldException, IOException, IllegalArgumentException {
            int extraSize = additionalClassPathEntries.size();
            Field pathField = MultiDex.findField(loader, ClientCookie.PATH_ATTR);
            StringBuilder path = new StringBuilder((String) pathField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            ListIterator<? extends File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                path.append(':').append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                extraZips[index] = new ZipFile(additionalEntry);
                extraDexs[index] = DexFile.loadDex(entryPath, entryPath + ".dex", 0);
            }
            pathField.set(loader, path.toString());
            MultiDex.expandFieldArray(loader, "mPaths", extraPaths);
            MultiDex.expandFieldArray(loader, "mFiles", extraFiles);
            MultiDex.expandFieldArray(loader, "mZips", extraZips);
            MultiDex.expandFieldArray(loader, "mDexs", extraDexs);
        }
    }
}
