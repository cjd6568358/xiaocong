package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Base64 {
    private static final Base64Encoder encoder;
    private static final Base64 instance = new Base64();

    static {
        Base64 base64 = instance;
        base64.getClass();
        encoder = new Base64Encoder(base64);
    }

    public static String encode(String s) {
        encoder.putByteArray("akey", s.getBytes());
        String result = encoder.getBase64String();
        return result;
    }

    public static String encodeBytes(byte[] b) {
        encoder.putByteArray("aKey", b);
        String result = encoder.getBase64String();
        return result;
    }

    public class Base64Encoder extends AbstractPreferences {
        private String base64String;
        final Base64 this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Base64Encoder(Base64 base64) {
            super(null, "");
            this.this$0 = base64;
            this.base64String = null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void putSpi(String key, String value) {
            this.base64String = value;
        }

        public String getBase64String() {
            return this.base64String;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected String getSpi(String key) {
            return null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void removeSpi(String key) {
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void removeNodeSpi() throws BackingStoreException {
        }

        @Override // java.util.prefs.AbstractPreferences
        protected String[] keysSpi() throws BackingStoreException {
            return null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected String[] childrenNamesSpi() throws BackingStoreException {
            return null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected AbstractPreferences childSpi(String name) {
            return null;
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void syncSpi() throws BackingStoreException {
        }

        @Override // java.util.prefs.AbstractPreferences
        protected void flushSpi() throws BackingStoreException {
        }
    }
}
