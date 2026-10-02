package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class EngineKey implements Key {
    private final ResourceDecoder cacheDecoder;
    private final ResourceDecoder decoder;
    private final ResourceEncoder encoder;
    private int hashCode;
    private final int height;
    private final String id;
    private Key originalKey;
    private final Key signature;
    private final Encoder sourceEncoder;
    private String stringKey;
    private final ResourceTranscoder transcoder;
    private final Transformation transformation;
    private final int width;

    public EngineKey(String id, Key signature, int width, int height, ResourceDecoder cacheDecoder, ResourceDecoder decoder, Transformation transformation, ResourceEncoder encoder, ResourceTranscoder transcoder, Encoder sourceEncoder) {
        this.id = id;
        this.signature = signature;
        this.width = width;
        this.height = height;
        this.cacheDecoder = cacheDecoder;
        this.decoder = decoder;
        this.transformation = transformation;
        this.encoder = encoder;
        this.transcoder = transcoder;
        this.sourceEncoder = sourceEncoder;
    }

    public Key getOriginalKey() {
        if (this.originalKey == null) {
            this.originalKey = new OriginalKey(this.id, this.signature);
        }
        return this.originalKey;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EngineKey engineKey = (EngineKey) o;
        if (!this.id.equals(engineKey.id) || !this.signature.equals(engineKey.signature) || this.height != engineKey.height || this.width != engineKey.width) {
            return false;
        }
        if ((this.transformation == null) ^ (engineKey.transformation == null)) {
            return false;
        }
        if (this.transformation != null && !this.transformation.getId().equals(engineKey.transformation.getId())) {
            return false;
        }
        if ((this.decoder == null) ^ (engineKey.decoder == null)) {
            return false;
        }
        if (this.decoder != null && !this.decoder.getId().equals(engineKey.decoder.getId())) {
            return false;
        }
        if ((this.cacheDecoder == null) ^ (engineKey.cacheDecoder == null)) {
            return false;
        }
        if (this.cacheDecoder != null && !this.cacheDecoder.getId().equals(engineKey.cacheDecoder.getId())) {
            return false;
        }
        if ((this.encoder == null) ^ (engineKey.encoder == null)) {
            return false;
        }
        if (this.encoder != null && !this.encoder.getId().equals(engineKey.encoder.getId())) {
            return false;
        }
        if ((this.transcoder == null) ^ (engineKey.transcoder == null)) {
            return false;
        }
        if (this.transcoder != null && !this.transcoder.getId().equals(engineKey.transcoder.getId())) {
            return false;
        }
        if ((this.sourceEncoder == null) ^ (engineKey.sourceEncoder == null)) {
            return false;
        }
        return this.sourceEncoder == null || this.sourceEncoder.getId().equals(engineKey.sourceEncoder.getId());
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.id.hashCode();
            this.hashCode = (this.hashCode * 31) + this.signature.hashCode();
            this.hashCode = (this.hashCode * 31) + this.width;
            this.hashCode = (this.hashCode * 31) + this.height;
            this.hashCode = (this.cacheDecoder != null ? this.cacheDecoder.getId().hashCode() : 0) + (this.hashCode * 31);
            this.hashCode = (this.decoder != null ? this.decoder.getId().hashCode() : 0) + (this.hashCode * 31);
            this.hashCode = (this.transformation != null ? this.transformation.getId().hashCode() : 0) + (this.hashCode * 31);
            this.hashCode = (this.encoder != null ? this.encoder.getId().hashCode() : 0) + (this.hashCode * 31);
            this.hashCode = (this.transcoder != null ? this.transcoder.getId().hashCode() : 0) + (this.hashCode * 31);
            this.hashCode = (this.hashCode * 31) + (this.sourceEncoder != null ? this.sourceEncoder.getId().hashCode() : 0);
        }
        return this.hashCode;
    }

    public String toString() {
        if (this.stringKey == null) {
            this.stringKey = "EngineKey{" + this.id + '+' + this.signature + "+[" + this.width + 'x' + this.height + "]+'" + (this.cacheDecoder != null ? this.cacheDecoder.getId() : Constants.MAIN_VERSION_TAG) + "'+'" + (this.decoder != null ? this.decoder.getId() : Constants.MAIN_VERSION_TAG) + "'+'" + (this.transformation != null ? this.transformation.getId() : Constants.MAIN_VERSION_TAG) + "'+'" + (this.encoder != null ? this.encoder.getId() : Constants.MAIN_VERSION_TAG) + "'+'" + (this.transcoder != null ? this.transcoder.getId() : Constants.MAIN_VERSION_TAG) + "'+'" + (this.sourceEncoder != null ? this.sourceEncoder.getId() : Constants.MAIN_VERSION_TAG) + "'}";
        }
        return this.stringKey;
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        byte[] dimensions = ByteBuffer.allocate(8).putInt(this.width).putInt(this.height).array();
        this.signature.updateDiskCacheKey(messageDigest);
        messageDigest.update(this.id.getBytes(HTTP.UTF_8));
        messageDigest.update(dimensions);
        messageDigest.update((this.cacheDecoder != null ? this.cacheDecoder.getId() : Constants.MAIN_VERSION_TAG).getBytes(HTTP.UTF_8));
        messageDigest.update((this.decoder != null ? this.decoder.getId() : Constants.MAIN_VERSION_TAG).getBytes(HTTP.UTF_8));
        messageDigest.update((this.transformation != null ? this.transformation.getId() : Constants.MAIN_VERSION_TAG).getBytes(HTTP.UTF_8));
        messageDigest.update((this.encoder != null ? this.encoder.getId() : Constants.MAIN_VERSION_TAG).getBytes(HTTP.UTF_8));
        messageDigest.update((this.sourceEncoder != null ? this.sourceEncoder.getId() : Constants.MAIN_VERSION_TAG).getBytes(HTTP.UTF_8));
    }
}
