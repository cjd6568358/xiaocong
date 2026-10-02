package com.bumptech.glide.load.engine;

import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.provider.DataLoadProvider;
import com.bumptech.glide.util.LogTime;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class DecodeJob<A, T, Z> {
    private static final FileOpener DEFAULT_FILE_OPENER = new FileOpener();
    private final DiskCacheProvider diskCacheProvider;
    private final DiskCacheStrategy diskCacheStrategy;
    private final DataFetcher<A> fetcher;
    private final FileOpener fileOpener;
    private final int height;
    private volatile boolean isCancelled;
    private final DataLoadProvider<A, T> loadProvider;
    private final Priority priority;
    private final EngineKey resultKey;
    private final ResourceTranscoder<T, Z> transcoder;
    private final Transformation<T> transformation;
    private final int width;

    interface DiskCacheProvider {
        DiskCache getDiskCache();
    }

    public DecodeJob(EngineKey resultKey, int width, int height, DataFetcher<A> fetcher, DataLoadProvider<A, T> loadProvider, Transformation<T> transformation, ResourceTranscoder<T, Z> transcoder, DiskCacheProvider diskCacheProvider, DiskCacheStrategy diskCacheStrategy, Priority priority) {
        this(resultKey, width, height, fetcher, loadProvider, transformation, transcoder, diskCacheProvider, diskCacheStrategy, priority, DEFAULT_FILE_OPENER);
    }

    DecodeJob(EngineKey resultKey, int width, int height, DataFetcher<A> fetcher, DataLoadProvider<A, T> loadProvider, Transformation<T> transformation, ResourceTranscoder<T, Z> transcoder, DiskCacheProvider diskCacheProvider, DiskCacheStrategy diskCacheStrategy, Priority priority, FileOpener fileOpener) {
        this.resultKey = resultKey;
        this.width = width;
        this.height = height;
        this.fetcher = fetcher;
        this.loadProvider = loadProvider;
        this.transformation = transformation;
        this.transcoder = transcoder;
        this.diskCacheProvider = diskCacheProvider;
        this.diskCacheStrategy = diskCacheStrategy;
        this.priority = priority;
        this.fileOpener = fileOpener;
    }

    public Resource<Z> decodeResultFromCache() throws Exception {
        if (!this.diskCacheStrategy.cacheResult()) {
            return null;
        }
        long startTime = LogTime.getLogTime();
        Resource<T> transformed = loadFromCache(this.resultKey);
        if (Log.isLoggable("DecodeJob", 2)) {
            logWithTimeAndKey("Decoded transformed from cache", startTime);
        }
        long startTime2 = LogTime.getLogTime();
        Resource<Z> resourceTranscode = transcode(transformed);
        if (Log.isLoggable("DecodeJob", 2)) {
            logWithTimeAndKey("Transcoded transformed from cache", startTime2);
            return resourceTranscode;
        }
        return resourceTranscode;
    }

    public Resource<Z> decodeSourceFromCache() throws Exception {
        if (!this.diskCacheStrategy.cacheSource()) {
            return null;
        }
        long startTime = LogTime.getLogTime();
        Resource<T> decoded = loadFromCache(this.resultKey.getOriginalKey());
        if (Log.isLoggable("DecodeJob", 2)) {
            logWithTimeAndKey("Decoded source from cache", startTime);
        }
        return transformEncodeAndTranscode(decoded);
    }

    public Resource<Z> decodeFromSource() throws Exception {
        Resource<T> decoded = decodeSource();
        return transformEncodeAndTranscode(decoded);
    }

    public void cancel() {
        this.isCancelled = true;
        this.fetcher.cancel();
    }

    private Resource<Z> transformEncodeAndTranscode(Resource<T> decoded) {
        long startTime = LogTime.getLogTime();
        Resource<T> transformed = transform(decoded);
        if (Log.isLoggable("DecodeJob", 2)) {
            logWithTimeAndKey("Transformed resource from source", startTime);
        }
        writeTransformedToCache(transformed);
        long startTime2 = LogTime.getLogTime();
        Resource<Z> result = transcode(transformed);
        if (Log.isLoggable("DecodeJob", 2)) {
            logWithTimeAndKey("Transcoded transformed from source", startTime2);
        }
        return result;
    }

    private void writeTransformedToCache(Resource<T> transformed) {
        if (transformed != null && this.diskCacheStrategy.cacheResult()) {
            long startTime = LogTime.getLogTime();
            DecodeJob<A, T, Z>.SourceWriter<Resource<T>> writer = new SourceWriter<>(this.loadProvider.getEncoder(), transformed);
            this.diskCacheProvider.getDiskCache().put(this.resultKey, writer);
            if (Log.isLoggable("DecodeJob", 2)) {
                logWithTimeAndKey("Wrote transformed from source to cache", startTime);
            }
        }
    }

    private Resource<T> decodeSource() throws Exception {
        try {
            long startTime = LogTime.getLogTime();
            A data = this.fetcher.loadData(this.priority);
            if (Log.isLoggable("DecodeJob", 2)) {
                logWithTimeAndKey("Fetched data", startTime);
            }
            if (!this.isCancelled) {
                Resource<T> decoded = decodeFromSourceData(data);
                return decoded;
            }
            return null;
        } finally {
            this.fetcher.cleanup();
        }
    }

    private Resource<T> decodeFromSourceData(A data) throws IOException {
        if (this.diskCacheStrategy.cacheSource()) {
            return cacheAndDecodeSourceData(data);
        }
        long startTime = LogTime.getLogTime();
        Resource<T> decoded = this.loadProvider.getSourceDecoder().decode(data, this.width, this.height);
        if (Log.isLoggable("DecodeJob", 2)) {
            logWithTimeAndKey("Decoded from source", startTime);
            return decoded;
        }
        return decoded;
    }

    private Resource<T> cacheAndDecodeSourceData(A data) throws IOException {
        long startTime = LogTime.getLogTime();
        DecodeJob<A, T, Z>.SourceWriter<A> writer = new SourceWriter<>(this.loadProvider.getSourceEncoder(), data);
        this.diskCacheProvider.getDiskCache().put(this.resultKey.getOriginalKey(), writer);
        if (Log.isLoggable("DecodeJob", 2)) {
            logWithTimeAndKey("Wrote source to cache", startTime);
        }
        long startTime2 = LogTime.getLogTime();
        Resource<T> result = loadFromCache(this.resultKey.getOriginalKey());
        if (Log.isLoggable("DecodeJob", 2) && result != null) {
            logWithTimeAndKey("Decoded source from cache", startTime2);
        }
        return result;
    }

    private Resource<T> loadFromCache(Key key) throws IOException {
        File cacheFile = this.diskCacheProvider.getDiskCache().get(key);
        if (cacheFile == null) {
            return null;
        }
        Resource<T> result = null;
        try {
            result = this.loadProvider.getCacheDecoder().decode(cacheFile, this.width, this.height);
        } finally {
            if (result == null) {
                this.diskCacheProvider.getDiskCache().delete(key);
            }
        }
    }

    private Resource<T> transform(Resource<T> decoded) {
        if (decoded == null) {
            return null;
        }
        Resource<T> transformed = this.transformation.transform(decoded, this.width, this.height);
        if (!decoded.equals(transformed)) {
            decoded.recycle();
            return transformed;
        }
        return transformed;
    }

    private Resource<Z> transcode(Resource<T> transformed) {
        if (transformed == null) {
            return null;
        }
        return this.transcoder.transcode(transformed);
    }

    private void logWithTimeAndKey(String message, long startTime) {
        Log.v("DecodeJob", message + " in " + LogTime.getElapsedMillis(startTime) + ", key: " + this.resultKey);
    }

    class SourceWriter<DataType> implements DiskCache.Writer {
        private final DataType data;
        private final Encoder<DataType> encoder;

        public SourceWriter(Encoder<DataType> encoder, DataType data) {
            this.encoder = encoder;
            this.data = data;
        }

        @Override // com.bumptech.glide.load.engine.cache.DiskCache.Writer
        public boolean write(File file) {
            boolean success = false;
            OutputStream os = null;
            try {
                os = DecodeJob.this.fileOpener.open(file);
                success = this.encoder.encode(this.data, os);
            } catch (FileNotFoundException e) {
                if (Log.isLoggable("DecodeJob", 3)) {
                    Log.d("DecodeJob", "Failed to find file to write to disk cache", e);
                }
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e2) {
                    }
                }
            }
            return success;
        }
    }

    static class FileOpener {
        FileOpener() {
        }

        public OutputStream open(File file) throws FileNotFoundException {
            return new BufferedOutputStream(new FileOutputStream(file));
        }
    }
}
