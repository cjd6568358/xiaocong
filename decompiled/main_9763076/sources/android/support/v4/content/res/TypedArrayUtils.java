package android.support.v4.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TypedArrayUtils {
    public static boolean hasAttribute(XmlPullParser parser, String attrName) {
        return parser.getAttributeValue("http://schemas.android.com/apk/res/android", attrName) != null;
    }

    public static float getNamedFloat(TypedArray a, XmlPullParser parser, String attrName, int resId, float defaultValue) {
        boolean hasAttr = hasAttribute(parser, attrName);
        return !hasAttr ? defaultValue : a.getFloat(resId, defaultValue);
    }

    public static boolean getNamedBoolean(TypedArray a, XmlPullParser parser, String attrName, int resId, boolean defaultValue) {
        boolean hasAttr = hasAttribute(parser, attrName);
        return !hasAttr ? defaultValue : a.getBoolean(resId, defaultValue);
    }

    public static int getNamedInt(TypedArray a, XmlPullParser parser, String attrName, int resId, int defaultValue) {
        boolean hasAttr = hasAttribute(parser, attrName);
        return !hasAttr ? defaultValue : a.getInt(resId, defaultValue);
    }

    public static int getNamedColor(TypedArray a, XmlPullParser parser, String attrName, int resId, int defaultValue) {
        boolean hasAttr = hasAttribute(parser, attrName);
        return !hasAttr ? defaultValue : a.getColor(resId, defaultValue);
    }

    public static int getNamedResourceId(TypedArray a, XmlPullParser parser, String attrName, int resId, int defaultValue) {
        boolean hasAttr = hasAttribute(parser, attrName);
        return !hasAttr ? defaultValue : a.getResourceId(resId, defaultValue);
    }

    public static String getNamedString(TypedArray a, XmlPullParser parser, String attrName, int resId) {
        boolean hasAttr = hasAttribute(parser, attrName);
        if (hasAttr) {
            return a.getString(resId);
        }
        return null;
    }

    public static TypedValue peekNamedValue(TypedArray a, XmlPullParser parser, String attrName, int resId) {
        boolean hasAttr = hasAttribute(parser, attrName);
        if (hasAttr) {
            return a.peekValue(resId);
        }
        return null;
    }

    public static TypedArray obtainAttributes(Resources res, Resources.Theme theme, AttributeSet set, int[] attrs) {
        return theme == null ? res.obtainAttributes(set, attrs) : theme.obtainStyledAttributes(set, attrs, 0, 0);
    }
}
