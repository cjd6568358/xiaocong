package com.facebook.imagepipeline.image;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImmutableQualityInfo implements QualityInfo {
    public static final QualityInfo FULL_QUALITY = of(Integer.MAX_VALUE, true, true);
    boolean mIsOfFullQuality;
    boolean mIsOfGoodEnoughQuality;
    int mQuality;

    private ImmutableQualityInfo(int quality, boolean isOfGoodEnoughQuality, boolean isOfFullQuality) {
        this.mQuality = quality;
        this.mIsOfGoodEnoughQuality = isOfGoodEnoughQuality;
        this.mIsOfFullQuality = isOfFullQuality;
    }

    @Override // com.facebook.imagepipeline.image.QualityInfo
    public int getQuality() {
        return this.mQuality;
    }

    @Override // com.facebook.imagepipeline.image.QualityInfo
    public boolean isOfGoodEnoughQuality() {
        return this.mIsOfGoodEnoughQuality;
    }

    @Override // com.facebook.imagepipeline.image.QualityInfo
    public boolean isOfFullQuality() {
        return this.mIsOfFullQuality;
    }

    public int hashCode() {
        return ((this.mIsOfGoodEnoughQuality ? 4194304 : 0) ^ this.mQuality) ^ (this.mIsOfFullQuality ? 8388608 : 0);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ImmutableQualityInfo)) {
            return false;
        }
        ImmutableQualityInfo that = (ImmutableQualityInfo) other;
        return this.mQuality == that.mQuality && this.mIsOfGoodEnoughQuality == that.mIsOfGoodEnoughQuality && this.mIsOfFullQuality == that.mIsOfFullQuality;
    }

    public static QualityInfo of(int quality, boolean isOfGoodEnoughQuality, boolean isOfFullQuality) {
        return new ImmutableQualityInfo(quality, isOfGoodEnoughQuality, isOfFullQuality);
    }
}
