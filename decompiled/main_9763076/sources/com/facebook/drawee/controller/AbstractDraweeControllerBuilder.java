package com.facebook.drawee.controller;

import android.content.Context;
import android.graphics.drawable.Animatable;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSources;
import com.facebook.datasource.FirstAvailableDataSourceSupplier;
import com.facebook.datasource.IncreasingQualityDataSourceSupplier;
import com.facebook.drawee.components.RetryManager;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.drawee.gestures.GestureDetector;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class AbstractDraweeControllerBuilder<BUILDER extends AbstractDraweeControllerBuilder<BUILDER, REQUEST, IMAGE, INFO>, REQUEST, IMAGE, INFO> implements SimpleDraweeControllerBuilder {
    private boolean mAutoPlayAnimations;
    private final Set<ControllerListener> mBoundControllerListeners;
    private Object mCallerContext;
    private final Context mContext;
    private ControllerListener<? super INFO> mControllerListener;
    private Supplier<DataSource<IMAGE>> mDataSourceSupplier;
    private REQUEST mImageRequest;
    private REQUEST mLowResImageRequest;
    private REQUEST[] mMultiImageRequests;
    private DraweeController mOldController;
    private boolean mRetainImageOnFailure;
    private boolean mTapToRetryEnabled;
    private boolean mTryCacheOnlyFirst;
    private static final ControllerListener<Object> sAutoPlayAnimationsListener = new BaseControllerListener<Object>() { // from class: com.facebook.drawee.controller.AbstractDraweeControllerBuilder.1
        @Override // com.facebook.drawee.controller.BaseControllerListener, com.facebook.drawee.controller.ControllerListener
        public void onFinalImageSet(String id, Object info, Animatable anim) {
            if (anim != null) {
                anim.start();
            }
        }
    };
    private static final NullPointerException NO_REQUEST_EXCEPTION = new NullPointerException("No image request was specified!");
    private static final AtomicLong sIdCounter = new AtomicLong();

    protected abstract DataSource<IMAGE> getDataSourceForRequest(REQUEST request, Object obj, boolean z);

    protected abstract BUILDER getThis();

    protected abstract AbstractDraweeController obtainController();

    protected AbstractDraweeControllerBuilder(Context context, Set<ControllerListener> boundControllerListeners) {
        this.mContext = context;
        this.mBoundControllerListeners = boundControllerListeners;
        init();
    }

    private void init() {
        this.mCallerContext = null;
        this.mImageRequest = null;
        this.mLowResImageRequest = null;
        this.mMultiImageRequests = null;
        this.mTryCacheOnlyFirst = true;
        this.mControllerListener = null;
        this.mTapToRetryEnabled = false;
        this.mAutoPlayAnimations = false;
        this.mOldController = null;
    }

    public BUILDER reset() {
        init();
        return (BUILDER) getThis();
    }

    @Override // com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder
    public BUILDER setCallerContext(Object obj) {
        this.mCallerContext = obj;
        return (BUILDER) getThis();
    }

    public Object getCallerContext() {
        return this.mCallerContext;
    }

    public BUILDER setImageRequest(REQUEST request) {
        this.mImageRequest = request;
        return (BUILDER) getThis();
    }

    public BUILDER setLowResImageRequest(REQUEST request) {
        this.mLowResImageRequest = request;
        return (BUILDER) getThis();
    }

    public boolean getRetainImageOnFailure() {
        return this.mRetainImageOnFailure;
    }

    public BUILDER setAutoPlayAnimations(boolean z) {
        this.mAutoPlayAnimations = z;
        return (BUILDER) getThis();
    }

    public BUILDER setControllerListener(ControllerListener<? super INFO> controllerListener) {
        this.mControllerListener = controllerListener;
        return (BUILDER) getThis();
    }

    @Override // com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder
    public BUILDER setOldController(DraweeController draweeController) {
        this.mOldController = draweeController;
        return (BUILDER) getThis();
    }

    public DraweeController getOldController() {
        return this.mOldController;
    }

    @Override // com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder
    public AbstractDraweeController build() {
        validate();
        if (this.mImageRequest == null && this.mMultiImageRequests == null && this.mLowResImageRequest != null) {
            this.mImageRequest = this.mLowResImageRequest;
            this.mLowResImageRequest = null;
        }
        return buildController();
    }

    protected void validate() {
        boolean z = false;
        Preconditions.checkState(this.mMultiImageRequests == null || this.mImageRequest == null, "Cannot specify both ImageRequest and FirstAvailableImageRequests!");
        if (this.mDataSourceSupplier == null || (this.mMultiImageRequests == null && this.mImageRequest == null && this.mLowResImageRequest == null)) {
            z = true;
        }
        Preconditions.checkState(z, "Cannot specify DataSourceSupplier with other ImageRequests! Use one or the other.");
    }

    protected AbstractDraweeController buildController() {
        AbstractDraweeController controller = obtainController();
        controller.setRetainImageOnFailure(getRetainImageOnFailure());
        maybeBuildAndSetRetryManager(controller);
        maybeAttachListeners(controller);
        return controller;
    }

    protected static String generateUniqueControllerId() {
        return String.valueOf(sIdCounter.getAndIncrement());
    }

    protected Supplier<DataSource<IMAGE>> obtainDataSourceSupplier() {
        if (this.mDataSourceSupplier != null) {
            return this.mDataSourceSupplier;
        }
        Supplier<DataSource<IMAGE>> supplier = null;
        if (this.mImageRequest != null) {
            supplier = getDataSourceSupplierForRequest(this.mImageRequest);
        } else if (this.mMultiImageRequests != null) {
            supplier = getFirstAvailableDataSourceSupplier(this.mMultiImageRequests, this.mTryCacheOnlyFirst);
        }
        if (supplier != null && this.mLowResImageRequest != null) {
            List<Supplier<DataSource<IMAGE>>> suppliers = new ArrayList<>(2);
            suppliers.add(supplier);
            suppliers.add(getDataSourceSupplierForRequest(this.mLowResImageRequest));
            supplier = IncreasingQualityDataSourceSupplier.create(suppliers);
        }
        if (supplier == null) {
            Supplier<DataSource<IMAGE>> supplier2 = DataSources.getFailedDataSourceSupplier(NO_REQUEST_EXCEPTION);
            return supplier2;
        }
        return supplier;
    }

    protected Supplier<DataSource<IMAGE>> getFirstAvailableDataSourceSupplier(REQUEST[] imageRequests, boolean tryBitmapCacheOnlyFirst) {
        List<Supplier<DataSource<IMAGE>>> suppliers = new ArrayList<>(imageRequests.length * 2);
        if (tryBitmapCacheOnlyFirst) {
            for (REQUEST request : imageRequests) {
                suppliers.add(getDataSourceSupplierForRequest(request, true));
            }
        }
        for (REQUEST request2 : imageRequests) {
            suppliers.add(getDataSourceSupplierForRequest(request2));
        }
        return FirstAvailableDataSourceSupplier.create(suppliers);
    }

    protected Supplier<DataSource<IMAGE>> getDataSourceSupplierForRequest(REQUEST imageRequest) {
        return getDataSourceSupplierForRequest(imageRequest, false);
    }

    protected Supplier<DataSource<IMAGE>> getDataSourceSupplierForRequest(final REQUEST imageRequest, final boolean bitmapCacheOnly) {
        final Object callerContext = getCallerContext();
        return new Supplier<DataSource<IMAGE>>() { // from class: com.facebook.drawee.controller.AbstractDraweeControllerBuilder.2
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // com.facebook.common.internal.Supplier
            public DataSource<IMAGE> get() {
                return AbstractDraweeControllerBuilder.this.getDataSourceForRequest(imageRequest, callerContext, bitmapCacheOnly);
            }

            public String toString() {
                return Objects.toStringHelper(this).add("request", imageRequest.toString()).toString();
            }
        };
    }

    protected void maybeAttachListeners(AbstractDraweeController controller) {
        if (this.mBoundControllerListeners != null) {
            for (ControllerListener listener : this.mBoundControllerListeners) {
                controller.addControllerListener(listener);
            }
        }
        if (this.mControllerListener != null) {
            controller.addControllerListener(this.mControllerListener);
        }
        if (this.mAutoPlayAnimations) {
            controller.addControllerListener(sAutoPlayAnimationsListener);
        }
    }

    protected void maybeBuildAndSetRetryManager(AbstractDraweeController controller) {
        if (this.mTapToRetryEnabled) {
            RetryManager retryManager = controller.getRetryManager();
            if (retryManager == null) {
                retryManager = new RetryManager();
                controller.setRetryManager(retryManager);
            }
            retryManager.setTapToRetryEnabled(this.mTapToRetryEnabled);
            maybeBuildAndSetGestureDetector(controller);
        }
    }

    protected void maybeBuildAndSetGestureDetector(AbstractDraweeController controller) {
        GestureDetector gestureDetector = controller.getGestureDetector();
        if (gestureDetector == null) {
            GestureDetector gestureDetector2 = GestureDetector.newInstance(this.mContext);
            controller.setGestureDetector(gestureDetector2);
        }
    }
}
