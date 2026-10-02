package com.facebook.react.views.imagehelper;

import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MultiSourceHelper {

    public static class MultiSourceResult {
        private final ImageSource bestResult;
        private final ImageSource bestResultInCache;

        private MultiSourceResult(ImageSource bestResult, ImageSource bestResultInCache) {
            this.bestResult = bestResult;
            this.bestResultInCache = bestResultInCache;
        }

        public ImageSource getBestResult() {
            return this.bestResult;
        }

        public ImageSource getBestResultInCache() {
            return this.bestResultInCache;
        }
    }

    public static MultiSourceResult getBestSourceForSize(int width, int height, List<ImageSource> sources) {
        return getBestSourceForSize(width, height, sources, 1.0d);
    }

    public static MultiSourceResult getBestSourceForSize(int width, int height, List<ImageSource> sources, double multiplier) {
        if (sources.isEmpty()) {
            return new MultiSourceResult(null, null);
        }
        if (sources.size() == 1) {
            return new MultiSourceResult(sources.get(0), null);
        }
        if (width <= 0 || height <= 0) {
            return new MultiSourceResult(null, null);
        }
        ImagePipeline imagePipeline = ImagePipelineFactory.getInstance().getImagePipeline();
        ImageSource best = null;
        ImageSource bestCached = null;
        double viewArea = ((double) (width * height)) * multiplier;
        double bestPrecision = Double.MAX_VALUE;
        double bestCachePrecision = Double.MAX_VALUE;
        for (ImageSource source : sources) {
            double precision = Math.abs(1.0d - (source.getSize() / viewArea));
            if (precision < bestPrecision) {
                bestPrecision = precision;
                best = source;
            }
            if (precision < bestCachePrecision && (imagePipeline.isInBitmapMemoryCache(source.getUri()) || imagePipeline.isInDiskCacheSync(source.getUri()))) {
                bestCachePrecision = precision;
                bestCached = source;
            }
        }
        if (bestCached != null && best != null && bestCached.getSource().equals(best.getSource())) {
            bestCached = null;
        }
        return new MultiSourceResult(best, bestCached);
    }
}
