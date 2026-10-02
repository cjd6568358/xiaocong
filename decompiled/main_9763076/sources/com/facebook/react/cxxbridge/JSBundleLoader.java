package com.facebook.react.cxxbridge;

import android.content.Context;
import com.facebook.react.devsupport.DebugServerException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class JSBundleLoader {
    public abstract String loadScript(CatalystInstanceImpl catalystInstanceImpl);

    public static JSBundleLoader createAssetLoader(final Context context, final String assetUrl) {
        return new JSBundleLoader() { // from class: com.facebook.react.cxxbridge.JSBundleLoader.1
            @Override // com.facebook.react.cxxbridge.JSBundleLoader
            public String loadScript(CatalystInstanceImpl instance) {
                instance.loadScriptFromAssets(context.getAssets(), assetUrl);
                return assetUrl;
            }
        };
    }

    public static JSBundleLoader createFileLoader(final String fileName) {
        return new JSBundleLoader() { // from class: com.facebook.react.cxxbridge.JSBundleLoader.2
            @Override // com.facebook.react.cxxbridge.JSBundleLoader
            public String loadScript(CatalystInstanceImpl instance) {
                instance.loadScriptFromFile(fileName, fileName);
                return fileName;
            }
        };
    }

    public static JSBundleLoader createCachedBundleFromNetworkLoader(final String sourceURL, final String cachedFileLocation) {
        return new JSBundleLoader() { // from class: com.facebook.react.cxxbridge.JSBundleLoader.3
            @Override // com.facebook.react.cxxbridge.JSBundleLoader
            public String loadScript(CatalystInstanceImpl instance) {
                try {
                    instance.loadScriptFromFile(cachedFileLocation, sourceURL);
                    return sourceURL;
                } catch (Exception e) {
                    throw DebugServerException.makeGeneric(e.getMessage(), e);
                }
            }
        };
    }
}
