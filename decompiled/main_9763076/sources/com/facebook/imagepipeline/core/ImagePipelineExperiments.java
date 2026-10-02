package com.facebook.imagepipeline.core;

import com.facebook.common.webp.WebpSupportStatus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImagePipelineExperiments {
    private boolean mDecodeFileDescriptorEnabled;
    private final int mForceSmallCacheThresholdBytes;
    private final boolean mWebpSupportEnabled;

    private ImagePipelineExperiments(Builder builder, ImagePipelineConfig.Builder configBuilder) {
        this.mForceSmallCacheThresholdBytes = builder.mForceSmallCacheThresholdBytes;
        this.mWebpSupportEnabled = builder.mWebpSupportEnabled && WebpSupportStatus.sWebpLibraryPresent;
        this.mDecodeFileDescriptorEnabled = configBuilder.isDownsampleEnabled() && builder.mDecodeFileDescriptorEnabled;
    }

    public boolean isDecodeFileDescriptorEnabled() {
        return this.mDecodeFileDescriptorEnabled;
    }

    public int getForceSmallCacheThresholdBytes() {
        return this.mForceSmallCacheThresholdBytes;
    }

    public boolean isWebpSupportEnabled() {
        return this.mWebpSupportEnabled;
    }

    public static class Builder {
        private final ImagePipelineConfig.Builder mConfigBuilder;
        private int mForceSmallCacheThresholdBytes = 0;
        private boolean mWebpSupportEnabled = false;
        private boolean mDecodeFileDescriptorEnabled = false;

        public Builder(ImagePipelineConfig.Builder configBuilder) {
            this.mConfigBuilder = configBuilder;
        }

        public ImagePipelineExperiments build() {
            return new ImagePipelineExperiments(this, this.mConfigBuilder);
        }
    }
}
