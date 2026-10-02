package com.facebook.react.views.scroll;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactScrollViewCommandHelper {

    public interface ScrollCommandHandler<T> {
        void scrollTo(T t, ScrollToCommandData scrollToCommandData);

        void scrollToEnd(T t, ScrollToEndCommandData scrollToEndCommandData);
    }

    public static class ScrollToCommandData {
        public final boolean mAnimated;
        public final int mDestX;
        public final int mDestY;

        ScrollToCommandData(int destX, int destY, boolean animated) {
            this.mDestX = destX;
            this.mDestY = destY;
            this.mAnimated = animated;
        }
    }

    public static class ScrollToEndCommandData {
        public final boolean mAnimated;

        ScrollToEndCommandData(boolean animated) {
            this.mAnimated = animated;
        }
    }

    public static Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("scrollTo", 1, "scrollToEnd", 2);
    }

    public static <T> void receiveCommand(ScrollCommandHandler<T> viewManager, T scrollView, int commandType, ReadableArray args) {
        Assertions.assertNotNull(viewManager);
        Assertions.assertNotNull(scrollView);
        Assertions.assertNotNull(args);
        switch (commandType) {
            case 1:
                int destX = Math.round(PixelUtil.toPixelFromDIP(args.getDouble(0)));
                int destY = Math.round(PixelUtil.toPixelFromDIP(args.getDouble(1)));
                boolean animated = args.getBoolean(2);
                viewManager.scrollTo(scrollView, new ScrollToCommandData(destX, destY, animated));
                return;
            case 2:
                boolean animated2 = args.getBoolean(0);
                viewManager.scrollToEnd(scrollView, new ScrollToEndCommandData(animated2));
                return;
            default:
                throw new IllegalArgumentException(String.format("Unsupported command %d received by %s.", Integer.valueOf(commandType), viewManager.getClass().getSimpleName()));
        }
    }
}
