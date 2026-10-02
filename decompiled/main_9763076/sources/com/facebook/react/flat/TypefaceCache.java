package com.facebook.react.flat;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import com.facebook.infer.annotation.Assertions;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class TypefaceCache {
    private static final HashMap<String, Typeface[]> FONTFAMILY_CACHE = new HashMap<>();
    private static final HashMap<Typeface, Typeface[]> TYPEFACE_CACHE = new HashMap<>();
    private static final String[] EXTENSIONS = {Constants.MAIN_VERSION_TAG, "_bold", "_italic", "_bold_italic"};
    private static final String[] FILE_EXTENSIONS = {".ttf", ".otf"};
    private static AssetManager sAssetManager = null;

    public static Typeface getTypeface(String fontFamily, int style) {
        Typeface[] cache = FONTFAMILY_CACHE.get(fontFamily);
        if (cache == null) {
            cache = new Typeface[4];
            FONTFAMILY_CACHE.put(fontFamily, cache);
        } else if (cache[style] != null) {
            return cache[style];
        }
        Typeface typeface = createTypeface(fontFamily, style);
        cache[style] = typeface;
        TYPEFACE_CACHE.put(typeface, cache);
        return typeface;
    }

    private static Typeface createTypeface(String fontFamilyName, int style) {
        String extension = EXTENSIONS[style];
        StringBuilder fileNameBuffer = new StringBuilder(32).append("fonts/").append(fontFamilyName).append(extension);
        int length = fileNameBuffer.length();
        for (String fileExtension : FILE_EXTENSIONS) {
            String fileName = fileNameBuffer.append(fileExtension).toString();
            try {
                return Typeface.createFromAsset(sAssetManager, fileName);
            } catch (RuntimeException e) {
                fileNameBuffer.setLength(length);
            }
        }
        return (Typeface) Assertions.assumeNotNull(Typeface.create(fontFamilyName, style));
    }

    public static Typeface getTypeface(Typeface typeface, int style) {
        if (typeface == null) {
            return Typeface.defaultFromStyle(style);
        }
        Typeface[] cache = TYPEFACE_CACHE.get(typeface);
        if (cache == null) {
            cache = new Typeface[4];
            cache[typeface.getStyle()] = typeface;
        } else if (cache[style] != null) {
            return cache[style];
        }
        Typeface typeface2 = Typeface.create(typeface, style);
        cache[style] = typeface2;
        TYPEFACE_CACHE.put(typeface2, cache);
        return typeface2;
    }
}
