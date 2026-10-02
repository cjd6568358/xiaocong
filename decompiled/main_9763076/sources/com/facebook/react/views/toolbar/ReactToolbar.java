package com.facebook.react.views.toolbar;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.MultiDraweeHolder;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.tencent.android.tpush.common.MessageKey;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactToolbar extends Toolbar {
    private final MultiDraweeHolder<GenericDraweeHierarchy> mActionsHolder;
    private final Runnable mLayoutRunnable;
    private IconControllerListener mLogoControllerListener;
    private final DraweeHolder mLogoHolder;
    private IconControllerListener mNavIconControllerListener;
    private final DraweeHolder mNavIconHolder;
    private IconControllerListener mOverflowIconControllerListener;
    private final DraweeHolder mOverflowIconHolder;

    private abstract class IconControllerListener extends BaseControllerListener<ImageInfo> {
        private final DraweeHolder mHolder;
        private IconImageInfo mIconImageInfo;

        protected abstract void setDrawable(Drawable drawable);

        public IconControllerListener(DraweeHolder holder) {
            this.mHolder = holder;
        }

        public void setIconImageInfo(IconImageInfo iconImageInfo) {
            this.mIconImageInfo = iconImageInfo;
        }

        @Override // com.facebook.drawee.controller.BaseControllerListener, com.facebook.drawee.controller.ControllerListener
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            ImageInfo info = this.mIconImageInfo != null ? this.mIconImageInfo : imageInfo;
            setDrawable(new DrawableWithIntrinsicSize(this.mHolder.getTopLevelDrawable(), info));
        }
    }

    private class ActionIconControllerListener extends IconControllerListener {
        private final MenuItem mItem;

        ActionIconControllerListener(MenuItem item, DraweeHolder holder) {
            super(holder);
            this.mItem = item;
        }

        @Override // com.facebook.react.views.toolbar.ReactToolbar.IconControllerListener
        protected void setDrawable(Drawable d) {
            this.mItem.setIcon(d);
        }
    }

    private static class IconImageInfo implements ImageInfo {
        private int mHeight;
        private int mWidth;

        public IconImageInfo(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
        }

        @Override // com.facebook.imagepipeline.image.ImageInfo
        public int getWidth() {
            return this.mWidth;
        }

        @Override // com.facebook.imagepipeline.image.ImageInfo
        public int getHeight() {
            return this.mHeight;
        }
    }

    public ReactToolbar(Context context) {
        super(context);
        this.mActionsHolder = new MultiDraweeHolder<>();
        this.mLayoutRunnable = new Runnable() { // from class: com.facebook.react.views.toolbar.ReactToolbar.4
            @Override // java.lang.Runnable
            public void run() {
                ReactToolbar.this.measure(View.MeasureSpec.makeMeasureSpec(ReactToolbar.this.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ReactToolbar.this.getHeight(), 1073741824));
                ReactToolbar.this.layout(ReactToolbar.this.getLeft(), ReactToolbar.this.getTop(), ReactToolbar.this.getRight(), ReactToolbar.this.getBottom());
            }
        };
        this.mLogoHolder = DraweeHolder.create(createDraweeHierarchy(), context);
        this.mNavIconHolder = DraweeHolder.create(createDraweeHierarchy(), context);
        this.mOverflowIconHolder = DraweeHolder.create(createDraweeHierarchy(), context);
        this.mLogoControllerListener = new IconControllerListener(this.mLogoHolder) { // from class: com.facebook.react.views.toolbar.ReactToolbar.1
            @Override // com.facebook.react.views.toolbar.ReactToolbar.IconControllerListener
            protected void setDrawable(Drawable d) {
                ReactToolbar.this.setLogo(d);
            }
        };
        this.mNavIconControllerListener = new IconControllerListener(this.mNavIconHolder) { // from class: com.facebook.react.views.toolbar.ReactToolbar.2
            @Override // com.facebook.react.views.toolbar.ReactToolbar.IconControllerListener
            protected void setDrawable(Drawable d) {
                ReactToolbar.this.setNavigationIcon(d);
            }
        };
        this.mOverflowIconControllerListener = new IconControllerListener(this.mOverflowIconHolder) { // from class: com.facebook.react.views.toolbar.ReactToolbar.3
            @Override // com.facebook.react.views.toolbar.ReactToolbar.IconControllerListener
            protected void setDrawable(Drawable d) {
                ReactToolbar.this.setOverflowIcon(d);
            }
        };
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        super.requestLayout();
        post(this.mLayoutRunnable);
    }

    @Override // android.support.v7.widget.Toolbar, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        detachDraweeHolders();
    }

    @Override // android.view.View
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        detachDraweeHolders();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        attachDraweeHolders();
    }

    @Override // android.view.View
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        attachDraweeHolders();
    }

    private void detachDraweeHolders() {
        this.mLogoHolder.onDetach();
        this.mNavIconHolder.onDetach();
        this.mOverflowIconHolder.onDetach();
        this.mActionsHolder.onDetach();
    }

    private void attachDraweeHolders() {
        this.mLogoHolder.onAttach();
        this.mNavIconHolder.onAttach();
        this.mOverflowIconHolder.onAttach();
        this.mActionsHolder.onAttach();
    }

    void setLogoSource(ReadableMap source) {
        setIconSource(source, this.mLogoControllerListener, this.mLogoHolder);
    }

    void setNavIconSource(ReadableMap source) {
        setIconSource(source, this.mNavIconControllerListener, this.mNavIconHolder);
    }

    void setOverflowIconSource(ReadableMap source) {
        setIconSource(source, this.mOverflowIconControllerListener, this.mOverflowIconHolder);
    }

    void setActions(ReadableArray actions) {
        Menu menu = getMenu();
        menu.clear();
        this.mActionsHolder.clear();
        if (actions != null) {
            for (int i = 0; i < actions.size(); i++) {
                ReadableMap action = actions.getMap(i);
                MenuItem item = menu.add(0, 0, i, action.getString("title"));
                if (action.hasKey(MessageKey.MSG_ICON)) {
                    setMenuItemIcon(item, action.getMap(MessageKey.MSG_ICON));
                }
                int showAsAction = action.hasKey("show") ? action.getInt("show") : 0;
                if (action.hasKey("showWithText") && action.getBoolean("showWithText")) {
                    showAsAction |= 4;
                }
                item.setShowAsAction(showAsAction);
            }
        }
    }

    private void setMenuItemIcon(MenuItem item, ReadableMap iconSource) {
        DraweeHolder<GenericDraweeHierarchy> holder = DraweeHolder.create(createDraweeHierarchy(), getContext());
        ActionIconControllerListener controllerListener = new ActionIconControllerListener(item, holder);
        controllerListener.setIconImageInfo(getIconImageInfo(iconSource));
        setIconSource(iconSource, controllerListener, holder);
        this.mActionsHolder.add(holder);
    }

    private void setIconSource(ReadableMap source, IconControllerListener controllerListener, DraweeHolder holder) {
        String uri = source != null ? source.getString("uri") : null;
        if (uri == null) {
            controllerListener.setIconImageInfo(null);
            controllerListener.setDrawable(null);
        } else {
            if (uri.startsWith("http://") || uri.startsWith("https://") || uri.startsWith("file://")) {
                controllerListener.setIconImageInfo(getIconImageInfo(source));
                DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(uri)).setControllerListener(controllerListener).setOldController(holder.getController()).build();
                holder.setController(controller);
                holder.getTopLevelDrawable().setVisible(true, true);
                return;
            }
            controllerListener.setDrawable(getDrawableByName(uri));
        }
    }

    private GenericDraweeHierarchy createDraweeHierarchy() {
        return new GenericDraweeHierarchyBuilder(getResources()).setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).setFadeDuration(0).build();
    }

    private int getDrawableResourceByName(String name) {
        return getResources().getIdentifier(name, "drawable", getContext().getPackageName());
    }

    private Drawable getDrawableByName(String name) {
        int drawableResId = getDrawableResourceByName(name);
        if (drawableResId != 0) {
            return getResources().getDrawable(getDrawableResourceByName(name));
        }
        return null;
    }

    private IconImageInfo getIconImageInfo(ReadableMap source) {
        if (!source.hasKey(IMediaFormat.KEY_WIDTH) || !source.hasKey(IMediaFormat.KEY_HEIGHT)) {
            return null;
        }
        int width = Math.round(PixelUtil.toPixelFromDIP(source.getInt(IMediaFormat.KEY_WIDTH)));
        int height = Math.round(PixelUtil.toPixelFromDIP(source.getInt(IMediaFormat.KEY_HEIGHT)));
        return new IconImageInfo(width, height);
    }
}
