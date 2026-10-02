package com.facebook.react.views.text.frescosupport;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextInlineImageShadowNode;
import com.facebook.react.views.text.TextInlineImageSpan;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FrescoBasedReactTextInlineImageShadowNode extends ReactTextInlineImageShadowNode {
    private final Object mCallerContext;
    private final AbstractDraweeControllerBuilder mDraweeControllerBuilder;
    private Uri mUri;
    private float mWidth = Float.NaN;
    private float mHeight = Float.NaN;

    public FrescoBasedReactTextInlineImageShadowNode(AbstractDraweeControllerBuilder draweeControllerBuilder, Object callerContext) {
        this.mDraweeControllerBuilder = draweeControllerBuilder;
        this.mCallerContext = callerContext;
    }

    @ReactProp(name = "src")
    public void setSource(ReadableArray sources) {
        String source = (sources == null || sources.size() == 0) ? null : sources.getMap(0).getString("uri");
        Uri uri = null;
        if (source != null) {
            try {
                uri = Uri.parse(source);
                if (uri.getScheme() == null) {
                    uri = null;
                }
            } catch (Exception e) {
            }
            if (uri == null) {
                uri = getResourceDrawableUri(getThemedContext(), source);
            }
        }
        if (uri != this.mUri) {
            markUpdated();
        }
        this.mUri = uri;
    }

    @Override // com.facebook.react.uimanager.LayoutShadowNode
    public void setWidth(Dynamic width) {
        if (width.getType() == ReadableType.Number) {
            this.mWidth = (float) width.asDouble();
            return;
        }
        throw new JSApplicationIllegalArgumentException("Inline images must not have percentage based width");
    }

    @Override // com.facebook.react.uimanager.LayoutShadowNode
    public void setHeight(Dynamic height) {
        if (height.getType() == ReadableType.Number) {
            this.mHeight = (float) height.asDouble();
            return;
        }
        throw new JSApplicationIllegalArgumentException("Inline images must not have percentage based height");
    }

    public Uri getUri() {
        return this.mUri;
    }

    private static Uri getResourceDrawableUri(Context context, String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        int resId = context.getResources().getIdentifier(name.toLowerCase(Locale.getDefault()).replace("-", "_"), "drawable", context.getPackageName());
        return new Uri.Builder().scheme("res").path(String.valueOf(resId)).build();
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public boolean isVirtual() {
        return true;
    }

    @Override // com.facebook.react.views.text.ReactTextInlineImageShadowNode
    public TextInlineImageSpan buildInlineImageSpan() {
        Resources resources = getThemedContext().getResources();
        int height = (int) Math.ceil(this.mWidth);
        int width = (int) Math.ceil(this.mHeight);
        return new FrescoBasedReactTextInlineImageSpan(resources, height, width, getUri(), getDraweeControllerBuilder(), getCallerContext());
    }

    public AbstractDraweeControllerBuilder getDraweeControllerBuilder() {
        return this.mDraweeControllerBuilder;
    }

    public Object getCallerContext() {
        return this.mCallerContext;
    }
}
