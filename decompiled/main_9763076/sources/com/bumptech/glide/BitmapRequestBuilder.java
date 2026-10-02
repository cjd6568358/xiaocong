package com.bumptech.glide;

import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.FileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.provider.LoadProvider;
import com.bumptech.glide.request.target.Target;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapRequestBuilder<ModelType, TranscodeType> extends GenericRequestBuilder<ModelType, ImageVideoWrapper, Bitmap, TranscodeType> {
    private final BitmapPool bitmapPool;
    private DecodeFormat decodeFormat;
    private Downsampler downsampler;
    private ResourceDecoder<InputStream, Bitmap> imageDecoder;
    private ResourceDecoder<ParcelFileDescriptor, Bitmap> videoDecoder;

    BitmapRequestBuilder(LoadProvider<ModelType, ImageVideoWrapper, Bitmap, TranscodeType> loadProvider, Class<TranscodeType> transcodeClass, GenericRequestBuilder<ModelType, ?, ?, ?> other) {
        super(loadProvider, transcodeClass, other);
        this.downsampler = Downsampler.AT_LEAST;
        this.bitmapPool = other.glide.getBitmapPool();
        this.decodeFormat = other.glide.getDecodeFormat();
        this.imageDecoder = new StreamBitmapDecoder(this.bitmapPool, this.decodeFormat);
        this.videoDecoder = new FileDescriptorBitmapDecoder(this.bitmapPool, this.decodeFormat);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> decoder(ResourceDecoder<ImageVideoWrapper, Bitmap> resourceDecoder) {
        super.decoder((ResourceDecoder) resourceDecoder);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> priority(Priority priority) {
        super.priority(priority);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> transform(BitmapTransformation... transformations) {
        super.transform((Transformation[]) transformations);
        return this;
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> centerCrop() {
        return transform(this.glide.getBitmapCenterCrop());
    }

    public BitmapRequestBuilder<ModelType, TranscodeType> fitCenter() {
        return transform(this.glide.getBitmapFitCenter());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> transform(Transformation<Bitmap>... transformationArr) {
        super.transform((Transformation[]) transformationArr);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> dontAnimate() {
        super.dontAnimate();
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> placeholder(int resourceId) {
        super.placeholder(resourceId);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> fallback(int resourceId) {
        super.fallback(resourceId);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> error(int resourceId) {
        super.error(resourceId);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> skipMemoryCache(boolean skip) {
        super.skipMemoryCache(skip);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> diskCacheStrategy(DiskCacheStrategy strategy) {
        super.diskCacheStrategy(strategy);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> override(int width, int height) {
        super.override(width, height);
        return this;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> sourceEncoder(Encoder<ImageVideoWrapper> encoder) {
        super.sourceEncoder((Encoder) encoder);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> signature(Key signature) {
        super.signature(signature);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public BitmapRequestBuilder<ModelType, TranscodeType> load(ModelType model) {
        super.load((Object) model);
        return this;
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    /* JADX INFO: renamed from: clone */
    public BitmapRequestBuilder<ModelType, TranscodeType> mo58clone() {
        return (BitmapRequestBuilder) super.mo58clone();
    }

    @Override // com.bumptech.glide.GenericRequestBuilder
    public Target<TranscodeType> into(ImageView view) {
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
