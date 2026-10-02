package com.bumptech.glide.load.resource.gifbitmap;

import android.graphics.Bitmap;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.ImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.RecyclableBufferedInputStream;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.util.ByteArrayPool;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GifBitmapWrapperResourceDecoder implements ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> {
    private static final ImageTypeParser DEFAULT_PARSER = new ImageTypeParser();
    private static final BufferedStreamFactory DEFAULT_STREAM_FACTORY = new BufferedStreamFactory();
    private final ResourceDecoder<ImageVideoWrapper, Bitmap> bitmapDecoder;
    private final BitmapPool bitmapPool;
    private final ResourceDecoder<InputStream, GifDrawable> gifDecoder;
    private String id;
    private final ImageTypeParser parser;
    private final BufferedStreamFactory streamFactory;

    public GifBitmapWrapperResourceDecoder(ResourceDecoder<ImageVideoWrapper, Bitmap> bitmapDecoder, ResourceDecoder<InputStream, GifDrawable> gifDecoder, BitmapPool bitmapPool) {
        this(bitmapDecoder, gifDecoder, bitmapPool, DEFAULT_PARSER, DEFAULT_STREAM_FACTORY);
    }

    GifBitmapWrapperResourceDecoder(ResourceDecoder<ImageVideoWrapper, Bitmap> bitmapDecoder, ResourceDecoder<InputStream, GifDrawable> gifDecoder, BitmapPool bitmapPool, ImageTypeParser parser, BufferedStreamFactory streamFactory) {
        this.bitmapDecoder = bitmapDecoder;
        this.gifDecoder = gifDecoder;
        this.bitmapPool = bitmapPool;
        this.parser = parser;
        this.streamFactory = streamFactory;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource<GifBitmapWrapper> decode(ImageVideoWrapper source, int width, int height) throws IOException {
        ByteArrayPool pool = ByteArrayPool.get();
        byte[] tempBytes = pool.getBytes();
        try {
            GifBitmapWrapper wrapper = decode(source, width, height, tempBytes);
            pool.releaseBytes(tempBytes);
            if (wrapper != null) {
                return new GifBitmapWrapperResource(wrapper);
            }
            return null;
        } catch (Throwable th) {
            pool.releaseBytes(tempBytes);
            throw th;
        }
    }

    private GifBitmapWrapper decode(ImageVideoWrapper source, int width, int height, byte[] bytes) throws IOException {
        if (source.getStream() != null) {
            GifBitmapWrapper result = decodeStream(source, width, height, bytes);
            return result;
        }
        GifBitmapWrapper result2 = decodeBitmapWrapper(source, width, height);
        return result2;
    }

    private GifBitmapWrapper decodeStream(ImageVideoWrapper source, int width, int height, byte[] bytes) throws IOException {
        InputStream bis = this.streamFactory.build(source.getStream(), bytes);
        bis.mark(2048);
        ImageHeaderParser.ImageType type = this.parser.parse(bis);
        bis.reset();
        GifBitmapWrapper result = null;
        if (type == ImageHeaderParser.ImageType.GIF) {
            result = decodeGifWrapper(bis, width, height);
        }
        if (result == null) {
            ImageVideoWrapper forBitmapDecoder = new ImageVideoWrapper(bis, source.getFileDescriptor());
            GifBitmapWrapper result2 = decodeBitmapWrapper(forBitmapDecoder, width, height);
            return result2;
        }
        return result;
    }

    private GifBitmapWrapper decodeGifWrapper(InputStream bis, int width, int height) throws IOException {
        Resource<GifDrawable> gifResource = this.gifDecoder.decode(bis, width, height);
        if (gifResource == null) {
            return null;
        }
        GifDrawable drawable = gifResource.get();
        if (drawable.getFrameCount() > 1) {
            GifBitmapWrapper result = new GifBitmapWrapper(null, gifResource);
            return result;
        }
        Resource<Bitmap> bitmapResource = new BitmapResource(drawable.getFirstFrame(), this.bitmapPool);
        GifBitmapWrapper result2 = new GifBitmapWrapper(bitmapResource, null);
        return result2;
    }

    private GifBitmapWrapper decodeBitmapWrapper(ImageVideoWrapper toDecode, int width, int height) throws IOException {
        Resource<Bitmap> bitmapResource = this.bitmapDecoder.decode(toDecode, width, height);
        if (bitmapResource == null) {
            return null;
        }
        GifBitmapWrapper result = new GifBitmapWrapper(bitmapResource, null);
        return result;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public String getId() {
        if (this.id == null) {
            this.id = this.gifDecoder.getId() + this.bitmapDecoder.getId();
        }
        return this.id;
    }

    static class BufferedStreamFactory {
        BufferedStreamFactory() {
        }

        public InputStream build(InputStream is, byte[] buffer) {
            return new RecyclableBufferedInputStream(is, buffer);
        }
    }

    static class ImageTypeParser {
        ImageTypeParser() {
        }

        public ImageHeaderParser.ImageType parse(InputStream is) throws IOException {
            return new ImageHeaderParser(is).getType();
        }
    }
}
