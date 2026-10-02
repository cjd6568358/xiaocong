package android.support.v4.content.res;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.TypefaceCompat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ResourcesCompat {
    public static Drawable getDrawable(Resources res, int id, Resources.Theme theme) throws Resources.NotFoundException {
        return Build.VERSION.SDK_INT >= 21 ? res.getDrawable(id, theme) : res.getDrawable(id);
    }

    public static Typeface getFont(Context context, int id, TypedValue value, int style, TextView targetView) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, id, value, style, targetView);
    }

    private static Typeface loadFont(Context context, int id, TypedValue value, int style, TextView targetView) {
        Resources resources = context.getResources();
        resources.getValue(id, value, true);
        Typeface typeface = loadFont(context, resources, value, id, style, targetView);
        if (typeface != null) {
            return typeface;
        }
        throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(id));
    }

    private static Typeface loadFont(Context context, Resources wrapper, TypedValue value, int id, int style, TextView targetView) {
        Typeface cached;
        if (value.string == null) {
            throw new Resources.NotFoundException("Resource \"" + wrapper.getResourceName(id) + "\" (" + Integer.toHexString(id) + ") is not a Font: " + value);
        }
        String file = value.string.toString();
        if (!file.startsWith("res/")) {
            return null;
        }
        Typeface cached2 = TypefaceCompat.findFromCache(wrapper, id, style);
        if (cached2 == null) {
            try {
                if (file.toLowerCase().endsWith(".xml")) {
                    XmlResourceParser rp = wrapper.getXml(id);
                    FontResourcesParserCompat.FamilyResourceEntry familyEntry = FontResourcesParserCompat.parse(rp, wrapper);
                    if (familyEntry == null) {
                        Log.e("ResourcesCompat", "Failed to find font-family tag");
                        cached = null;
                    } else {
                        cached = TypefaceCompat.createFromResourcesFamilyXml(context, familyEntry, wrapper, id, style, targetView);
                    }
                } else {
                    cached = TypefaceCompat.createFromResourcesFontFile(context, wrapper, id, file, style);
                }
                return cached;
            } catch (IOException e) {
                Log.e("ResourcesCompat", "Failed to read xml resource " + file, e);
                return null;
            } catch (XmlPullParserException e2) {
                Log.e("ResourcesCompat", "Failed to parse xml resource " + file, e2);
                return null;
            }
        }
        return cached2;
    }
}
