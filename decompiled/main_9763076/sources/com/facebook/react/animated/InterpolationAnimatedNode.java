package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class InterpolationAnimatedNode extends ValueAnimatedNode {
    private final String mExtrapolateLeft;
    private final String mExtrapolateRight;
    private final double[] mInputRange;
    private final double[] mOutputRange;
    private ValueAnimatedNode mParent;

    private static double[] fromDoubleArray(ReadableArray ary) {
        double[] res = new double[ary.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = ary.getDouble(i);
        }
        return res;
    }

    private static double interpolate(double value, double inputMin, double inputMax, double outputMin, double outputMax, String extrapolateLeft, String extrapolateRight) {
        double result = value;
        if (result < inputMin) {
            switch (extrapolateLeft) {
                case "identity":
                    return result;
                case "clamp":
                    result = inputMin;
                    break;
                case "extend":
                    break;
                default:
                    throw new JSApplicationIllegalArgumentException("Invalid extrapolation type " + extrapolateLeft + "for left extrapolation");
            }
        }
        if (result > inputMax) {
            switch (extrapolateRight) {
                case "identity":
                    return result;
                case "clamp":
                    result = inputMax;
                    break;
                case "extend":
                    break;
                default:
                    throw new JSApplicationIllegalArgumentException("Invalid extrapolation type " + extrapolateRight + "for right extrapolation");
            }
        }
        return (((outputMax - outputMin) * (result - inputMin)) / (inputMax - inputMin)) + outputMin;
    }

    static double interpolate(double value, double[] inputRange, double[] outputRange, String extrapolateLeft, String extrapolateRight) {
        int rangeIndex = findRangeIndex(value, inputRange);
        return interpolate(value, inputRange[rangeIndex], inputRange[rangeIndex + 1], outputRange[rangeIndex], outputRange[rangeIndex + 1], extrapolateLeft, extrapolateRight);
    }

    private static int findRangeIndex(double value, double[] ranges) {
        int index = 1;
        while (index < ranges.length - 1 && ranges[index] < value) {
            index++;
        }
        return index - 1;
    }

    public InterpolationAnimatedNode(ReadableMap config) {
        this.mInputRange = fromDoubleArray(config.getArray("inputRange"));
        this.mOutputRange = fromDoubleArray(config.getArray("outputRange"));
        this.mExtrapolateLeft = config.getString("extrapolateLeft");
        this.mExtrapolateRight = config.getString("extrapolateRight");
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public void onAttachedToNode(AnimatedNode parent) {
        if (this.mParent != null) {
            throw new IllegalStateException("Parent already attached");
        }
        if (!(parent instanceof ValueAnimatedNode)) {
            throw new IllegalArgumentException("Parent is of an invalid type");
        }
        this.mParent = (ValueAnimatedNode) parent;
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public void onDetachedFromNode(AnimatedNode parent) {
        if (parent != this.mParent) {
            throw new IllegalArgumentException("Invalid parent node provided");
        }
        this.mParent = null;
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public void update() {
        if (this.mParent == null) {
            throw new IllegalStateException("Trying to update interpolation node that has not been attached to the parent");
        }
        this.mValue = interpolate(this.mParent.getValue(), this.mInputRange, this.mOutputRange, this.mExtrapolateLeft, this.mExtrapolateRight);
    }
}
