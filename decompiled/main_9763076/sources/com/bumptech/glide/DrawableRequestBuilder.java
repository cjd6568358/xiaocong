package com.bumptech.glide;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestTracker;
import com.bumptech.glide.provider.LoadProvider;
import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.request.target.Target;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DrawableRequestBuilder<ModelType> extends GenericRequestBuilder<ModelType, ImageVideoWrapper, GifBitmapWrapper, GlideDrawable> {
    DrawableRequestBuilder(Context context, Class<ModelType> modelClass, LoadProvider<ModelType, ImageVideoWrapper, GifBitmapWrapper, GlideDrawable> loadProvider, Glide glide, RequestTracker requestTracker, Lifecycle lifecycle) {
        super(context, modelClass, loadProvider, GlideDrawable.class, glide, requestTracker, lifecycle);
        crossFade();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> decoder(ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> resourceDecoder) {
        super.decoder((ResourceDecoder) resourceDecoder);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> priority(Priority priority) {
        super.priority(priority);
        return this;
    }

    public DrawableRequestBuilder<ModelType> centerCrop() {
        return transform(this.glide.getDrawableCenterCrop());
    }

    public DrawableRequestBuilder<ModelType> fitCenter() {
        return transform(this.glide.getDrawableFitCenter());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> transform(Transformation<GifBitmapWrapper>... transformationArr) {
        super.transform((Transformation[]) transformationArr);
        return this;
    }

    public final DrawableRequestBuilder<ModelType> crossFade() {
        super.animate(new DrawableCrossFadeFactory());
        return this;
    }

    public DrawableRequestBuilder<ModelType> crossFade(int duration) {
        super.animate(new DrawableCrossFadeFactory(duration));
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> dontAnimate() {
        super.dontAnimate();
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> placeholder(int resourceId) {
        super.placeholder(resourceId);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> fallback(int resourceId) {
        super.fallback(resourceId);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> error(int resourceId) {
        super.error(resourceId);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> diskCacheStrategy(DiskCacheStrategy strategy) {
        super.diskCacheStrategy(strategy);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> skipMemoryCache(boolean skip) {
        super.skipMemoryCache(skip);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> override(int width, int height) {
        super.override(width, height);
        return this;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> sourceEncoder(Encoder<ImageVideoWrapper> encoder) {
        super.sourceEncoder((Encoder) encoder);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> signature(Key signature) {
        super.signature(signature);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public DrawableRequestBuilder<ModelType> load(ModelType model) {
        super.load((Object) model);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public DrawableRequestBuilder<ModelType> mo58clone() {
        return (DrawableRequestBuilder) super.mo58clone();
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public Target<GlideDrawable> into(ImageView view) {
        return super.into(view);
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    void applyFitCenter() {
        fitCenter();
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    void applyCenterCrop() {
        centerCrop();
    }
}
