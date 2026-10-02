package android.support.v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.provider.FontsContractCompat;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
    private static final Method sAbortCreation;
    private static final Method sAddFontFromAssetManager;
    private static final Method sAddFontFromBuffer;
    private static final Method sCreateFromFamiliesWithDefault;
    private static final Class sFontFamily;
    private static final Constructor sFontFamilyCtor;
    private static final Method sFreeze;

    static {
        Class<?> cls;
        Constructor<?> constructor;
        Method addFontMethod;
        Method addFromBufferMethod;
        Method freezeMethod;
        Method abortCreationMethod;
        Method createFromFamiliesWithDefaultMethod;
        try {
            cls = Class.forName("android.graphics.FontFamily");
            constructor = cls.getConstructor(new Class[0]);
            addFontMethod = cls.getMethod("addFontFromAssetManager", AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, FontVariationAxis[].class);
            addFromBufferMethod = cls.getMethod("addFontFromBuffer", ByteBuffer.class, Integer.TYPE, FontVariationAxis[].class, Integer.TYPE, Integer.TYPE);
            freezeMethod = cls.getMethod("freeze", new Class[0]);
            abortCreationMethod = cls.getMethod("abortCreation", new Class[0]);
            Object familyArray = Array.newInstance(cls, 1);
            createFromFamiliesWithDefaultMethod = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", familyArray.getClass(), Integer.TYPE, Integer.TYPE);
            createFromFamiliesWithDefaultMethod.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e("TypefaceCompatApi26Impl", "Unable to collect necessary methods for class " + e.getClass().getName(), e);
            cls = null;
            constructor = null;
            addFontMethod = null;
            addFromBufferMethod = null;
            freezeMethod = null;
            abortCreationMethod = null;
            createFromFamiliesWithDefaultMethod = null;
        }
        sFontFamilyCtor = constructor;
        sFontFamily = cls;
        sAddFontFromAssetManager = addFontMethod;
        sAddFontFromBuffer = addFromBufferMethod;
        sFreeze = freezeMethod;
        sAbortCreation = abortCreationMethod;
        sCreateFromFamiliesWithDefault = createFromFamiliesWithDefaultMethod;
    }

    private static boolean isFontFamilyPrivateAPIAvailable() {
        if (sAddFontFromAssetManager == null) {
            Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods.Fallback to legacy implementation.");
        }
        return sAddFontFromAssetManager != null;
    }

    private static Object newFamily() {
        try {
            return sFontFamilyCtor.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromAssetManager(Context context, Object family, String fileName, int ttcIndex, int weight, int style) {
        try {
            Boolean result = (Boolean) sAddFontFromAssetManager.invoke(family, context.getAssets(), fileName, 0, false, Integer.valueOf(ttcIndex), Integer.valueOf(weight), Integer.valueOf(style), null);
            return result.booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean addFontFromBuffer(Object family, ByteBuffer buffer, int ttcIndex, int weight, int style) {
        try {
            Boolean result = (Boolean) sAddFontFromBuffer.invoke(family, buffer, Integer.valueOf(ttcIndex), null, Integer.valueOf(weight), Integer.valueOf(style));
            return result.booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Typeface createFromFamiliesWithDefault(Object family) {
        try {
            Object familyArray = Array.newInstance((Class<?>) sFontFamily, 1);
            Array.set(familyArray, 0, family);
            return (Typeface) sCreateFromFamiliesWithDefault.invoke(null, familyArray, -1, -1);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean freeze(Object family) {
        try {
            Boolean result = (Boolean) sFreeze.invoke(family, new Object[0]);
            return result.booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean abortCreation(Object family) {
        try {
            Boolean result = (Boolean) sAbortCreation.invoke(family, new Object[0]);
            return result.booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl, android.support.v4.graphics.TypefaceCompat.TypefaceCompatImpl
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry entry, Resources resources, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromFontFamilyFilesResourceEntry(context, entry, resources, style);
        }
        Object fontFamily = newFamily();
        for (FontResourcesParserCompat.FontFileResourceEntry fontFile : entry.getEntries()) {
            if (!addFontFromAssetManager(context, fontFamily, fontFile.getFileName(), 0, fontFile.getWeight(), fontFile.isItalic() ? 1 : 0)) {
                abortCreation(fontFamily);
                return null;
            }
        }
        if (freeze(fontFamily)) {
            return createFromFamiliesWithDefault(fontFamily);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0064 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:32:0x006f A[Catch: IOException -> 0x0053, TRY_LEAVE, TryCatch #4 {IOException -> 0x0053, blocks: (B:9:0x001c, B:14:0x004a, B:20:0x0056, B:17:0x004f, B:28:0x0066, B:32:0x006f, B:31:0x006b, B:29:0x0069), top: B:59:0x001c, inners: #0, #2 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.support.v4.graphics.TypefaceCompatApi21Impl, android.support.v4.graphics.TypefaceCompatBaseImpl, android.support.v4.graphics.TypefaceCompat.TypefaceCompatImpl
    public Typeface createFromFontInfo(Context context, CancellationSignal cancellationSignal, FontsContractCompat.FontInfo[] fonts, int style) throws Throwable {
        if (fonts.length < 1) {
            return null;
        }
        if (isFontFamilyPrivateAPIAvailable()) {
            Map<Uri, ByteBuffer> uriBuffer = FontsContractCompat.prepareFontData(context, fonts, cancellationSignal);
            Object fontFamily = newFamily();
            boolean atLeastOneFont = false;
            for (FontsContractCompat.FontInfo font : fonts) {
                ByteBuffer fontBuffer = uriBuffer.get(font.getUri());
                if (fontBuffer != null) {
                    boolean success = addFontFromBuffer(fontFamily, fontBuffer, font.getTtcIndex(), font.getWeight(), font.isItalic() ? 1 : 0);
                    if (!success) {
                        abortCreation(fontFamily);
                        return null;
                    }
                    atLeastOneFont = true;
                }
            }
            if (!atLeastOneFont) {
                abortCreation(fontFamily);
                return null;
            }
            if (freeze(fontFamily)) {
                return createFromFamiliesWithDefault(fontFamily);
            }
            return null;
        }
        FontsContractCompat.FontInfo bestFont = findBestInfo(fonts, style);
        ContentResolver resolver = context.getContentResolver();
        try {
            ParcelFileDescriptor pfd = resolver.openFileDescriptor(bestFont.getUri(), "r", cancellationSignal);
            Throwable th = null;
            try {
                Typeface typefaceBuild = new Typeface.Builder(pfd.getFileDescriptor()).setWeight(bestFont.getWeight()).setItalic(bestFont.isItalic()).build();
                if (pfd == null) {
                    return typefaceBuild;
                }
                if (0 == 0) {
                    pfd.close();
                    return typefaceBuild;
                }
                try {
                    pfd.close();
                    return typefaceBuild;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    return typefaceBuild;
                }
            } catch (Throwable th3) {
                th = th3;
                if (pfd != null) {
                    if (th != null) {
                        pfd.close();
                    } else {
                        pfd.close();
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            return null;
        }
    }

    @Override // android.support.v4.graphics.TypefaceCompatBaseImpl, android.support.v4.graphics.TypefaceCompat.TypefaceCompatImpl
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int id, String path, int style) {
        if (!isFontFamilyPrivateAPIAvailable()) {
            return super.createFromResourcesFontFile(context, resources, id, path, style);
        }
        Object fontFamily = newFamily();
        if (!addFontFromAssetManager(context, fontFamily, path, 0, -1, -1)) {
            abortCreation(fontFamily);
            return null;
        }
        if (freeze(fontFamily)) {
            return createFromFamiliesWithDefault(fontFamily);
        }
        return null;
    }
}
