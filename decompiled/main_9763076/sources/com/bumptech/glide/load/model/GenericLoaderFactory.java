package com.bumptech.glide.load.model;

import android.content.Context;
import com.bumptech.glide.load.data.DataFetcher;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GenericLoaderFactory {
    private static final ModelLoader NULL_MODEL_LOADER = new ModelLoader() { // from class: com.bumptech.glide.load.model.GenericLoaderFactory.1
        @Override // com.bumptech.glide.load.model.ModelLoader
        public DataFetcher getResourceFetcher(Object model, int width, int height) {
            throw new NoSuchMethodError("This should never be called!");
        }

        public String toString() {
            return "NULL_MODEL_LOADER";
        }
    };
    private final Context context;
    private final Map<Class, Map<Class, ModelLoaderFactory>> modelClassToResourceFactories = new HashMap();
    private final Map<Class, Map<Class, ModelLoader>> cachedModelLoaders = new HashMap();

    public GenericLoaderFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    public synchronized <T, Y> ModelLoaderFactory<T, Y> register(Class<T> modelClass, Class<Y> resourceClass, ModelLoaderFactory<T, Y> factory) {
        ModelLoaderFactory<T, Y> modelLoaderFactoryPut;
        this.cachedModelLoaders.clear();
        Map<Class, ModelLoaderFactory> resourceToFactories = this.modelClassToResourceFactories.get(modelClass);
        if (resourceToFactories == null) {
            resourceToFactories = new HashMap<>();
            this.modelClassToResourceFactories.put(modelClass, resourceToFactories);
        }
        modelLoaderFactoryPut = resourceToFactories.put(resourceClass, factory);
        if (modelLoaderFactoryPut != null) {
            for (Map<Class, ModelLoaderFactory> factories : this.modelClassToResourceFactories.values()) {
                if (factories.containsValue(modelLoaderFactoryPut)) {
                    modelLoaderFactoryPut = null;
                    break;
                }
            }
        }
        return modelLoaderFactoryPut;
    }

    public synchronized <T, Y> ModelLoader<T, Y> buildModelLoader(Class<T> modelClass, Class<Y> resourceClass) {
        ModelLoader<T, Y> modelLoader;
        ModelLoader<T, Y> result = getCachedLoader(modelClass, resourceClass);
        if (result != null) {
            modelLoader = NULL_MODEL_LOADER.equals(result) ? null : result;
        } else {
            ModelLoaderFactory<T, Y> factory = getFactory(modelClass, resourceClass);
            if (factory != null) {
                result = factory.build(this.context, this);
                cacheModelLoader(modelClass, resourceClass, result);
            } else {
                cacheNullLoader(modelClass, resourceClass);
            }
            modelLoader = result;
        }
        return modelLoader;
    }

    private <T, Y> void cacheNullLoader(Class<T> modelClass, Class<Y> resourceClass) {
        cacheModelLoader(modelClass, resourceClass, NULL_MODEL_LOADER);
    }

    private <T, Y> void cacheModelLoader(Class<T> modelClass, Class<Y> resourceClass, ModelLoader<T, Y> modelLoader) {
        Map<Class, ModelLoader> resourceToLoaders = this.cachedModelLoaders.get(modelClass);
        if (resourceToLoaders == null) {
            resourceToLoaders = new HashMap<>();
            this.cachedModelLoaders.put(modelClass, resourceToLoaders);
        }
        resourceToLoaders.put(resourceClass, modelLoader);
    }

    private <T, Y> ModelLoader<T, Y> getCachedLoader(Class<T> modelClass, Class<Y> resourceClass) {
        Map<Class, ModelLoader> resourceToLoaders = this.cachedModelLoaders.get(modelClass);
        if (resourceToLoaders == null) {
            return null;
        }
        return resourceToLoaders.get(resourceClass);
    }

    private <T, Y> ModelLoaderFactory<T, Y> getFactory(Class<T> modelClass, Class<Y> resourceClass) {
        Map<Class, ModelLoaderFactory> currentResourceToFactories;
        Map<Class, ModelLoaderFactory> resourceToFactories = this.modelClassToResourceFactories.get(modelClass);
        ModelLoaderFactory modelLoaderFactory = null;
        if (resourceToFactories != null) {
            modelLoaderFactory = resourceToFactories.get(resourceClass);
        }
        if (modelLoaderFactory == null) {
            for (Class<? super T> registeredModelClass : this.modelClassToResourceFactories.keySet()) {
                if (registeredModelClass.isAssignableFrom(modelClass) && (currentResourceToFactories = this.modelClassToResourceFactories.get(registeredModelClass)) != null) {
                    ModelLoaderFactory result = currentResourceToFactories.get(resourceClass);
                    modelLoaderFactory = result;
                    if (modelLoaderFactory != null) {
                        break;
                    }
                }
            }
        }
        return modelLoaderFactory;
    }
}
