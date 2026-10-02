package com.facebook.react.uimanager;

import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaWrap;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LayoutShadowNode extends ReactShadowNode {
    private static boolean dynamicIsPercent(Dynamic dynamic) {
        return dynamic.getType() == ReadableType.String && dynamic.asString().endsWith("%");
    }

    private static float getDynamicAsPercent(Dynamic dynamic) {
        String value = dynamic.asString();
        return Float.parseFloat(value.substring(0, value.length() - 1));
    }

    private static float getDynamicAsFloat(Dynamic dynamic) {
        return PixelUtil.toPixelFromDIP(dynamic.asDouble());
    }

    private static boolean isNull(Dynamic d) {
        return d == null || d.isNull();
    }

    @ReactProp(name = IMediaFormat.KEY_WIDTH)
    public void setWidth(Dynamic width) {
        if (!isVirtual()) {
            if (!isNull(width) && dynamicIsPercent(width)) {
                setStyleWidthPercent(getDynamicAsPercent(width));
            } else {
                setStyleWidth(isNull(width) ? Float.NaN : getDynamicAsFloat(width));
            }
            width.recycle();
        }
    }

    @ReactProp(name = "minWidth")
    public void setMinWidth(Dynamic minWidth) {
        if (!isVirtual()) {
            if (!isNull(minWidth) && dynamicIsPercent(minWidth)) {
                setStyleMinWidthPercent(getDynamicAsPercent(minWidth));
            } else {
                setStyleMinWidth(isNull(minWidth) ? Float.NaN : getDynamicAsFloat(minWidth));
            }
            minWidth.recycle();
        }
    }

    @ReactProp(name = "maxWidth")
    public void setMaxWidth(Dynamic maxWidth) {
        if (!isVirtual()) {
            if (!isNull(maxWidth) && dynamicIsPercent(maxWidth)) {
                setStyleMaxWidthPercent(getDynamicAsPercent(maxWidth));
            } else {
                setStyleMaxWidth(isNull(maxWidth) ? Float.NaN : getDynamicAsFloat(maxWidth));
            }
            maxWidth.recycle();
        }
    }

    @ReactProp(name = IMediaFormat.KEY_HEIGHT)
    public void setHeight(Dynamic height) {
        if (!isVirtual()) {
            if (!isNull(height) && dynamicIsPercent(height)) {
                setStyleHeightPercent(getDynamicAsPercent(height));
            } else {
                setStyleHeight(isNull(height) ? Float.NaN : getDynamicAsFloat(height));
            }
            height.recycle();
        }
    }

    @ReactProp(name = "minHeight")
    public void setMinHeight(Dynamic minHeight) {
        if (!isVirtual()) {
            if (!isNull(minHeight) && dynamicIsPercent(minHeight)) {
                setStyleMinHeightPercent(getDynamicAsPercent(minHeight));
            } else {
                setStyleMinHeight(isNull(minHeight) ? Float.NaN : getDynamicAsFloat(minHeight));
            }
            minHeight.recycle();
        }
    }

    @ReactProp(name = "maxHeight")
    public void setMaxHeight(Dynamic maxHeight) {
        if (!isVirtual()) {
            if (!isNull(maxHeight) && dynamicIsPercent(maxHeight)) {
                setStyleMaxHeightPercent(getDynamicAsPercent(maxHeight));
            } else {
                setStyleMaxHeight(isNull(maxHeight) ? Float.NaN : getDynamicAsFloat(maxHeight));
            }
            maxHeight.recycle();
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    @ReactProp(defaultFloat = 0.0f, name = "flex")
    public void setFlex(float flex) {
        if (!isVirtual()) {
            super.setFlex(flex);
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    @ReactProp(defaultFloat = 0.0f, name = "flexGrow")
    public void setFlexGrow(float flexGrow) {
        if (!isVirtual()) {
            super.setFlexGrow(flexGrow);
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    @ReactProp(defaultFloat = 0.0f, name = "flexShrink")
    public void setFlexShrink(float flexShrink) {
        if (!isVirtual()) {
            super.setFlexShrink(flexShrink);
        }
    }

    @ReactProp(name = "flexBasis")
    public void setFlexBasis(Dynamic flexBasis) {
        if (!isVirtual()) {
            if (!isNull(flexBasis) && dynamicIsPercent(flexBasis)) {
                setFlexBasisPercent(getDynamicAsPercent(flexBasis));
            } else {
                setFlexBasis(isNull(flexBasis) ? 0.0f : getDynamicAsFloat(flexBasis));
            }
            flexBasis.recycle();
        }
    }

    @ReactProp(defaultFloat = Float.NaN, name = "aspectRatio")
    public void setAspectRatio(float aspectRatio) {
        setStyleAspectRatio(aspectRatio);
    }

    @ReactProp(name = "flexDirection")
    public void setFlexDirection(String flexDirection) {
        if (!isVirtual()) {
            setFlexDirection(flexDirection == null ? YogaFlexDirection.COLUMN : YogaFlexDirection.valueOf(flexDirection.toUpperCase(Locale.US).replace("-", "_")));
        }
    }

    @ReactProp(name = "flexWrap")
    public void setFlexWrap(String flexWrap) {
        if (!isVirtual()) {
            if (flexWrap == null || flexWrap.equals("nowrap")) {
                setFlexWrap(YogaWrap.NO_WRAP);
            } else {
                if (flexWrap.equals("wrap")) {
                    setFlexWrap(YogaWrap.WRAP);
                    return;
                }
                throw new IllegalArgumentException("Unknown flexWrap value: " + flexWrap);
            }
        }
    }

    @ReactProp(name = "alignSelf")
    public void setAlignSelf(String alignSelf) {
        if (!isVirtual()) {
            setAlignSelf(alignSelf == null ? YogaAlign.AUTO : YogaAlign.valueOf(alignSelf.toUpperCase(Locale.US).replace("-", "_")));
        }
    }

    @ReactProp(name = "alignItems")
    public void setAlignItems(String alignItems) {
        if (!isVirtual()) {
            setAlignItems(alignItems == null ? YogaAlign.STRETCH : YogaAlign.valueOf(alignItems.toUpperCase(Locale.US).replace("-", "_")));
        }
    }

    @ReactProp(name = "justifyContent")
    public void setJustifyContent(String justifyContent) {
        if (!isVirtual()) {
            setJustifyContent(justifyContent == null ? YogaJustify.FLEX_START : YogaJustify.valueOf(justifyContent.toUpperCase(Locale.US).replace("-", "_")));
        }
    }

    @ReactProp(name = "overflow")
    public void setOverflow(String overflow) {
        if (!isVirtual()) {
            setOverflow(overflow == null ? YogaOverflow.VISIBLE : YogaOverflow.valueOf(overflow.toUpperCase(Locale.US).replace("-", "_")));
        }
    }

    @ReactPropGroup(names = {"margin", "marginVertical", "marginHorizontal", "marginLeft", "marginRight", "marginTop", "marginBottom"})
    public void setMargins(int index, Dynamic margin) {
        if (!isVirtual()) {
            if (!isNull(margin) && dynamicIsPercent(margin)) {
                setMarginPercent(ViewProps.PADDING_MARGIN_SPACING_TYPES[index], getDynamicAsPercent(margin));
            } else {
                setMargin(ViewProps.PADDING_MARGIN_SPACING_TYPES[index], isNull(margin) ? Float.NaN : getDynamicAsFloat(margin));
            }
            margin.recycle();
        }
    }

    @ReactPropGroup(names = {"padding", "paddingVertical", "paddingHorizontal", "paddingLeft", "paddingRight", "paddingTop", "paddingBottom"})
    public void setPaddings(int index, Dynamic padding) {
        if (!isVirtual()) {
            if (!isNull(padding) && dynamicIsPercent(padding)) {
                setPaddingPercent(ViewProps.PADDING_MARGIN_SPACING_TYPES[index], getDynamicAsPercent(padding));
            } else {
                setPadding(ViewProps.PADDING_MARGIN_SPACING_TYPES[index], isNull(padding) ? Float.NaN : getDynamicAsFloat(padding));
            }
            padding.recycle();
        }
    }

    @ReactPropGroup(defaultFloat = Float.NaN, names = {"borderWidth", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"})
    public void setBorderWidths(int index, float borderWidth) {
        if (!isVirtual()) {
            setBorder(ViewProps.BORDER_SPACING_TYPES[index], PixelUtil.toPixelFromDIP(borderWidth));
        }
    }

    @ReactPropGroup(names = {"left", "right", "top", "bottom"})
    public void setPositionValues(int index, Dynamic position) {
        if (!isVirtual()) {
            if (!isNull(position) && dynamicIsPercent(position)) {
                setPositionPercent(ViewProps.POSITION_SPACING_TYPES[index], getDynamicAsPercent(position));
            } else {
                setPosition(ViewProps.POSITION_SPACING_TYPES[index], isNull(position) ? Float.NaN : getDynamicAsFloat(position));
            }
            position.recycle();
        }
    }

    @ReactProp(name = "position")
    public void setPosition(String position) {
        if (!isVirtual()) {
            YogaPositionType positionType = position == null ? YogaPositionType.RELATIVE : YogaPositionType.valueOf(position.toUpperCase(Locale.US));
            setPositionType(positionType);
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    @ReactProp(name = "onLayout")
    public void setShouldNotifyOnLayout(boolean shouldNotifyOnLayout) {
        super.setShouldNotifyOnLayout(shouldNotifyOnLayout);
    }
}
