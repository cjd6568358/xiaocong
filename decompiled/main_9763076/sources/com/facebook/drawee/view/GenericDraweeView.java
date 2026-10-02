package com.facebook.drawee.view;

import android.content.Context;
import android.util.AttributeSet;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchyInflater;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GenericDraweeView extends DraweeView<GenericDraweeHierarchy> {
    public GenericDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context);
        setHierarchy(hierarchy);
    }

    public GenericDraweeView(Context context) {
        super(context);
        inflateHierarchy(context, null);
    }

    public GenericDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateHierarchy(context, attrs);
    }

    public GenericDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflateHierarchy(context, attrs);
    }

    protected void inflateHierarchy(Context context, AttributeSet attrs) {
        GenericDraweeHierarchyBuilder builder = GenericDraweeHierarchyInflater.inflateBuilder(context, attrs);
        setAspectRatio(builder.getDesiredAspectRatio());
        setHierarchy(builder.build());
    }
}
