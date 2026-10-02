package com.facebook.react.views.view;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.TypedValue;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactDrawableHelper {
    private static final TypedValue sResolveOutValue = new TypedValue();

    public static Drawable createDrawableFromJSDescription(Context context, ReadableMap drawableDescriptionDict) {
        int color;
        String type = drawableDescriptionDict.getString("type");
        if ("ThemeAttrAndroid".equals(type)) {
            String attr = drawableDescriptionDict.getString("attribute");
            SoftAssertions.assertNotNull(attr);
            int attrID = context.getResources().getIdentifier(attr, "attr", "android");
            if (attrID == 0) {
                throw new JSApplicationIllegalArgumentException("Attribute " + attr + " couldn't be found in the resource list");
            }
            if (context.getTheme().resolveAttribute(attrID, sResolveOutValue, true)) {
                int version = Build.VERSION.SDK_INT;
                if (version >= 21) {
                    return context.getResources().getDrawable(sResolveOutValue.resourceId, context.getTheme());
                }
                return context.getResources().getDrawable(sResolveOutValue.resourceId);
            }
            throw new JSApplicationIllegalArgumentException("Attribute " + attr + " couldn't be resolved into a drawable");
        }
        if ("RippleAndroid".equals(type)) {
            if (Build.VERSION.SDK_INT < 21) {
                throw new JSApplicationIllegalArgumentException("Ripple drawable is not available on android API <21");
            }
            if (drawableDescriptionDict.hasKey("color") && !drawableDescriptionDict.isNull("color")) {
                color = drawableDescriptionDict.getInt("color");
            } else if (context.getTheme().resolveAttribute(R.attr.colorControlHighlight, sResolveOutValue, true)) {
                color = context.getResources().getColor(sResolveOutValue.resourceId);
            } else {
                throw new JSApplicationIllegalArgumentException("Attribute colorControlHighlight couldn't be resolved into a drawable");
            }
            Drawable mask = null;
            if (!drawableDescriptionDict.hasKey("borderless") || drawableDescriptionDict.isNull("borderless") || !drawableDescriptionDict.getBoolean("borderless")) {
                mask = new ColorDrawable(-1);
            }
            ColorStateList colorStateList = new ColorStateList(new int[][]{new int[0]}, new int[]{color});
            return new RippleDrawable(colorStateList, null, mask);
        }
        throw new JSApplicationIllegalArgumentException("Invalid type for android drawable: " + type);
    }
}
