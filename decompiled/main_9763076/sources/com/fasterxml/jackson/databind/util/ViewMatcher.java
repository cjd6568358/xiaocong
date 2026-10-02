package com.fasterxml.jackson.databind.util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ViewMatcher {
    public abstract boolean isVisibleForView(Class<?> cls);

    public static ViewMatcher construct(Class<?>[] views) {
        if (views == null) {
            return Empty.instance;
        }
        switch (views.length) {
            case 0:
                return Empty.instance;
            case 1:
                return new Single(views[0]);
            default:
                return new Multi(views);
        }
    }

    private static final class Empty extends ViewMatcher {
        static final Empty instance = new Empty();

        private Empty() {
        }

        @Override // com.fasterxml.jackson.databind.util.ViewMatcher
        public boolean isVisibleForView(Class<?> activeView) {
            return false;
        }
    }

    private static final class Single extends ViewMatcher {
        private final Class<?> _view;

        public Single(Class<?> v) {
            this._view = v;
        }

        @Override // com.fasterxml.jackson.databind.util.ViewMatcher
        public boolean isVisibleForView(Class<?> activeView) {
            return activeView == this._view || this._view.isAssignableFrom(activeView);
        }
    }

    private static final class Multi extends ViewMatcher {
        private final Class<?>[] _views;

        public Multi(Class<?>[] v) {
            this._views = v;
        }

        @Override // com.fasterxml.jackson.databind.util.ViewMatcher
        public boolean isVisibleForView(Class<?> activeView) {
            int len = this._views.length;
            for (int i = 0; i < len; i++) {
                Class<?> view = this._views[i];
                if (activeView == view || view.isAssignableFrom(activeView)) {
                    return true;
                }
            }
            return false;
        }
    }
}
