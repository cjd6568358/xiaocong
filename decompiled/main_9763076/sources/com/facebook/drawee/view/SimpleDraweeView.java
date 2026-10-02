package com.facebook.drawee.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.R;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SimpleDraweeView extends GenericDraweeView {
    private static Supplier<? extends SimpleDraweeControllerBuilder> sDraweeControllerBuilderSupplier;
    private SimpleDraweeControllerBuilder mSimpleDraweeControllerBuilder;

    public static void initialize(Supplier<? extends SimpleDraweeControllerBuilder> draweeControllerBuilderSupplier) {
        sDraweeControllerBuilderSupplier = draweeControllerBuilderSupplier;
    }

    public SimpleDraweeView(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            Preconditions.checkNotNull(sDraweeControllerBuilderSupplier, "SimpleDraweeView was not initialized!");
            this.mSimpleDraweeControllerBuilder = sDraweeControllerBuilderSupplier.get();
            if (attrs != null) {
                TypedArray gdhAttrs = context.obtainStyledAttributes(attrs, R.styleable.SimpleDraweeView);
                try {
                    if (gdhAttrs.hasValue(R.styleable.SimpleDraweeView_actualImageUri)) {
                        setImageURI(Uri.parse(gdhAttrs.getString(R.styleable.SimpleDraweeView_actualImageUri)), (Object) null);
                    }
                } finally {
                    gdhAttrs.recycle();
                }
            }
        }
    }

    protected SimpleDraweeControllerBuilder getControllerBuilder() {
        return this.mSimpleDraweeControllerBuilder;
    }

    @Override // com.facebook.drawee.view.DraweeView, android.widget.ImageView
    public void setImageURI(Uri uri) {
        setImageURI(uri, (Object) null);
    }

    public void setImageURI(String uriString) {
        setImageURI(uriString, (Object) null);
    }

    public void setImageURI(Uri uri, Object callerContext) {
        DraweeController controller = this.mSimpleDraweeControllerBuilder.setCallerContext(callerContext).setUri(uri).setOldController(getController()).build();
        setController(controller);
    }

    public void setImageURI(String uriString, Object callerContext) {
        Uri uri = uriString != null ? Uri.parse(uriString) : null;
        setImageURI(uri, callerContext);
    }
}
