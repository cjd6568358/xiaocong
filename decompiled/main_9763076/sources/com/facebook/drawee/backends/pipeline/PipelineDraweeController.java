package com.facebook.drawee.backends.pipeline;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawable.base.DrawableWithCaches;
import com.facebook.drawee.components.DeferredReleaser;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.drawable.LightBitmapDrawable;
import com.facebook.drawee.drawable.OrientedDrawable;
import com.facebook.imagepipeline.animated.factory.AnimatedDrawableFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.ImageInfo;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PipelineDraweeController extends AbstractDraweeController<CloseableReference<CloseableImage>, ImageInfo> {
    private static final Class<?> TAG = PipelineDraweeController.class;
    private static boolean sIsLightEnabled;
    private static boolean sIsReuseEnabled;
    private final AnimatedDrawableFactory mAnimatedDrawableFactory;
    private Supplier<DataSource<CloseableReference<CloseableImage>>> mDataSourceSupplier;
    private LightBitmapDrawable mLightBitmapDrawable;
    private final Resources mResources;

    public PipelineDraweeController(Resources resources, DeferredReleaser deferredReleaser, AnimatedDrawableFactory animatedDrawableFactory, Executor uiThreadExecutor, Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier, String id, Object callerContext) {
        super(deferredReleaser, uiThreadExecutor, id, callerContext);
        this.mResources = resources;
        this.mAnimatedDrawableFactory = animatedDrawableFactory;
        init(dataSourceSupplier);
    }

    public void initialize(Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier, String id, Object callerContext) {
        super.initialize(id, callerContext);
        init(dataSourceSupplier);
    }

    private void init(Supplier<DataSource<CloseableReference<CloseableImage>>> dataSourceSupplier) {
        this.mDataSourceSupplier = dataSourceSupplier;
    }

    @Override // com.facebook.drawee.controller.AbstractDraweeController
    protected DataSource<CloseableReference<CloseableImage>> getDataSource() {
        if (FLog.isLoggable(2)) {
            FLog.v(TAG, "controller %x: getDataSource", Integer.valueOf(System.identityHashCode(this)));
        }
        return this.mDataSourceSupplier.get();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.drawee.controller.AbstractDraweeController
    public Drawable createDrawable(CloseableReference<CloseableImage> image) {
        Drawable bitmapDrawable;
        Preconditions.checkState(CloseableReference.isValid(image));
        CloseableImage closeableImage = image.get();
        if (closeableImage instanceof CloseableStaticBitmap) {
            CloseableStaticBitmap closeableStaticBitmap = (CloseableStaticBitmap) closeableImage;
            if (sIsLightEnabled) {
                if (sIsReuseEnabled && this.mLightBitmapDrawable != null) {
                    this.mLightBitmapDrawable.setBitmap(closeableStaticBitmap.getUnderlyingBitmap());
                } else {
                    this.mLightBitmapDrawable = new LightBitmapDrawable(this.mResources, closeableStaticBitmap.getUnderlyingBitmap());
                }
                bitmapDrawable = this.mLightBitmapDrawable;
            } else {
                bitmapDrawable = new BitmapDrawable(this.mResources, closeableStaticBitmap.getUnderlyingBitmap());
            }
            if (closeableStaticBitmap.getRotationAngle() != 0 && closeableStaticBitmap.getRotationAngle() != -1) {
                return new OrientedDrawable(bitmapDrawable, closeableStaticBitmap.getRotationAngle());
            }
            return bitmapDrawable;
        }
        if (this.mAnimatedDrawableFactory != null) {
            Drawable bitmapDrawable2 = this.mAnimatedDrawableFactory.create(closeableImage);
            return bitmapDrawable2;
        }
        throw new UnsupportedOperationException("Unrecognized image class: " + closeableImage);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.drawee.controller.AbstractDraweeController
    public ImageInfo getImageInfo(CloseableReference<CloseableImage> image) {
        Preconditions.checkState(CloseableReference.isValid(image));
        return image.get();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.drawee.controller.AbstractDraweeController
    public int getImageHash(CloseableReference<CloseableImage> image) {
        if (image != null) {
            return image.getValueHash();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.drawee.controller.AbstractDraweeController
    public void releaseImage(CloseableReference<CloseableImage> image) {
        CloseableReference.closeSafely(image);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.facebook.drawee.controller.AbstractDraweeController
    protected void releaseDrawable(Drawable drawable) {
        if (drawable instanceof DrawableWithCaches) {
            ((DrawableWithCaches) drawable).dropCaches();
        }
    }

    @Override // com.facebook.drawee.controller.AbstractDraweeController
    public String toString() {
        return Objects.toStringHelper(this).add("super", super.toString()).add("dataSourceSupplier", this.mDataSourceSupplier).toString();
    }
}
