package com.facebook.react.views.text;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.SparseArray;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactFontManager {
    private static final String[] EXTENSIONS = {Constants.MAIN_VERSION_TAG, "_bold", "_italic", "_bold_italic"};
    private static final String[] FILE_EXTENSIONS = {".ttf", ".otf"};
    private static ReactFontManager sReactFontManagerInstance;
    private Map<String, FontFamily> mFontCache = new HashMap();

    private ReactFontManager() {
    }

    public static ReactFontManager getInstance() {
        if (sReactFontManagerInstance == null) {
            sReactFontManagerInstance = new ReactFontManager();
        }
        return sReactFontManagerInstance;
    }

    public Typeface getTypeface(String fontFamilyName, int style, AssetManager assetManager) {
        FontFamily fontFamily = this.mFontCache.get(fontFamilyName);
        if (fontFamily == null) {
            fontFamily = new FontFamily();
            this.mFontCache.put(fontFamilyName, fontFamily);
        }
        Typeface typeface = fontFamily.getTypeface(style);
        if (typeface == null && (typeface = createTypeface(fontFamilyName, style, assetManager)) != null) {
            fontFamily.setTypeface(style, typeface);
        }
        return typeface;
    }

    private static Typeface createTypeface(String fontFamilyName, int style, AssetManager assetManager) {
        String extension = EXTENSIONS[style];
        for (String fileExtension : FILE_EXTENSIONS) {
            String fileName = "fonts/" + fontFamilyName + extension + fileExtension;
            try {
                return Typeface.createFromAsset(assetManager, fileName);
            } catch (RuntimeException e) {
            }
        }
        return Typeface.create(fontFamilyName, style);
    }

    private static class FontFamily {
        private SparseArray<Typeface> mTypefaceSparseArray;

        private FontFamily() {
            this.mTypefaceSparseArray = new SparseArray<>(4);
        }

        public Typeface getTypeface(int style) {
            return this.mTypefaceSparseArray.get(style);
        }

        public void setTypeface(int style, Typeface typeface) {
            this.mTypefaceSparseArray.put(style, typeface);
        }
    }
}
