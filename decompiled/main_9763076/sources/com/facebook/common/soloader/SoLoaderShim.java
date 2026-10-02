package com.facebook.common.soloader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoLoaderShim {
    private static volatile Handler sHandler = new DefaultHandler();

    public interface Handler {
        void loadLibrary(String str);
    }

    public static class DefaultHandler implements Handler {
        @Override // com.facebook.common.soloader.SoLoaderShim.Handler
        public void loadLibrary(String libraryName) {
            System.loadLibrary(libraryName);
        }
    }

    public static void setHandler(Handler handler) {
        if (handler == null) {
            throw new NullPointerException("Handler cannot be null");
        }
        sHandler = handler;
    }

    public static void loadLibrary(String libraryName) {
        sHandler.loadLibrary(libraryName);
    }
}
