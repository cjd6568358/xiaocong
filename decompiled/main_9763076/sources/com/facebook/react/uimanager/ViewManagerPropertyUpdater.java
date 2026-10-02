package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ViewManagerPropertyUpdater {
    private static final Map<Class<?>, ViewManagerSetter<?, ?>> VIEW_MANAGER_SETTER_MAP = new HashMap();
    private static final Map<Class<?>, ShadowNodeSetter<?>> SHADOW_NODE_SETTER_MAP = new HashMap();

    public interface Settable {
        void getProperties(Map<String, String> map);
    }

    public interface ShadowNodeSetter<T extends ReactShadowNode> extends Settable {
        void setProperty(T t, String str, ReactStylesDiffMap reactStylesDiffMap);
    }

    public interface ViewManagerSetter<T extends ViewManager, V extends View> extends Settable {
        void setProperty(T t, V v, String str, ReactStylesDiffMap reactStylesDiffMap);
    }

    public static <T extends ViewManager, V extends View> void updateProps(T manager, V v, ReactStylesDiffMap props) {
        ViewManagerSetter<T, V> setter = findManagerSetter(manager.getClass());
        ReadableMap propMap = props.mBackingMap;
        ReadableMapKeySetIterator iterator = propMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            setter.setProperty(manager, v, key, props);
        }
    }

    public static <T extends ReactShadowNode> void updateProps(T node, ReactStylesDiffMap props) {
        ShadowNodeSetter<T> setter = findNodeSetter(node.getClass());
        ReadableMap propMap = props.mBackingMap;
        ReadableMapKeySetIterator iterator = propMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            setter.setProperty(node, key, props);
        }
    }

    public static Map<String, String> getNativeProps(Class<? extends ViewManager> viewManagerTopClass, Class<? extends ReactShadowNode> shadowNodeTopClass) {
        Map<String, String> props = new HashMap<>();
        findManagerSetter(viewManagerTopClass).getProperties(props);
        findNodeSetter(shadowNodeTopClass).getProperties(props);
        return props;
    }

    private static <T extends ViewManager, V extends View> ViewManagerSetter<T, V> findManagerSetter(Class<? extends ViewManager> managerClass) {
        ViewManagerSetter<T, V> setter = (ViewManagerSetter) VIEW_MANAGER_SETTER_MAP.get(managerClass);
        if (setter == null) {
            setter = (ViewManagerSetter) findGeneratedSetter(managerClass);
            if (setter == null) {
                setter = new FallbackViewManagerSetter<>(managerClass);
            }
            VIEW_MANAGER_SETTER_MAP.put(managerClass, setter);
        }
        return setter;
    }

    private static <T extends ReactShadowNode> ShadowNodeSetter<T> findNodeSetter(Class<? extends ReactShadowNode> nodeClass) {
        ShadowNodeSetter<T> setter = (ShadowNodeSetter) SHADOW_NODE_SETTER_MAP.get(nodeClass);
        if (setter == null) {
            setter = (ShadowNodeSetter) findGeneratedSetter(nodeClass);
            if (setter == null) {
                setter = new FallbackShadowNodeSetter<>(nodeClass);
            }
            SHADOW_NODE_SETTER_MAP.put(nodeClass, setter);
        }
        return setter;
    }

    private static <T> T findGeneratedSetter(Class<?> cls) {
        String name = cls.getName();
        try {
            return (T) Class.forName(name + "$$PropsSetter").newInstance();
        } catch (ClassNotFoundException e) {
            FLog.w("ViewManagerPropertyUpdater", "Could not find generated setter for " + cls);
            return null;
        } catch (IllegalAccessException e2) {
            e = e2;
            throw new RuntimeException("Unable to instantiate methods getter for " + name, e);
        } catch (InstantiationException e3) {
            e = e3;
            throw new RuntimeException("Unable to instantiate methods getter for " + name, e);
        }
    }

    private static class FallbackViewManagerSetter<T extends ViewManager, V extends View> implements ViewManagerSetter<T, V> {
        private final Map<String, ViewManagersPropertyCache.PropSetter> mPropSetters;

        private FallbackViewManagerSetter(Class<? extends ViewManager> viewManagerClass) {
            this.mPropSetters = ViewManagersPropertyCache.getNativePropSettersForViewManagerClass(viewManagerClass);
        }

        @Override // com.facebook.react.uimanager.ViewManagerPropertyUpdater.ViewManagerSetter
        public void setProperty(T manager, V v, String name, ReactStylesDiffMap props) {
            ViewManagersPropertyCache.PropSetter setter = this.mPropSetters.get(name);
            if (setter != null) {
                setter.updateViewProp(manager, v, props);
            }
        }

        @Override // com.facebook.react.uimanager.ViewManagerPropertyUpdater.Settable
        public void getProperties(Map<String, String> props) {
            for (ViewManagersPropertyCache.PropSetter setter : this.mPropSetters.values()) {
                props.put(setter.getPropName(), setter.getPropType());
            }
        }
    }

    private static class FallbackShadowNodeSetter<T extends ReactShadowNode> implements ShadowNodeSetter<T> {
        private final Map<String, ViewManagersPropertyCache.PropSetter> mPropSetters;

        private FallbackShadowNodeSetter(Class<? extends ReactShadowNode> shadowNodeClass) {
            this.mPropSetters = ViewManagersPropertyCache.getNativePropSettersForShadowNodeClass(shadowNodeClass);
        }

        @Override // com.facebook.react.uimanager.ViewManagerPropertyUpdater.ShadowNodeSetter
        public void setProperty(ReactShadowNode node, String name, ReactStylesDiffMap props) {
            ViewManagersPropertyCache.PropSetter setter = this.mPropSetters.get(name);
            if (setter != null) {
                setter.updateShadowNodeProp(node, props);
            }
        }

        @Override // com.facebook.react.uimanager.ViewManagerPropertyUpdater.Settable
        public void getProperties(Map<String, String> props) {
            for (ViewManagersPropertyCache.PropSetter setter : this.mPropSetters.values()) {
                props.put(setter.getPropName(), setter.getPropType());
            }
        }
    }
}
