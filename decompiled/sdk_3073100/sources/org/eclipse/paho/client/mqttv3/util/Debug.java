package org.eclipse.paho.client.mqttv3.util;

import java.util.Enumeration;
import java.util.Properties;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Debug {
    private static final String CLASS_NAME;
    static Class class$0;
    private static final String lineSep;
    private static final Logger log;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.internal.ClientComms");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
        lineSep = System.getProperty("line.separator", "\n");
    }

    public static String dumpProperties(Properties props, String name) {
        StringBuffer propStr = new StringBuffer();
        Enumeration<?> enumerationPropertyNames = props.propertyNames();
        propStr.append(new StringBuffer(String.valueOf(lineSep)).append("==============").append(" ").append(name).append(" ").append("==============").append(lineSep).toString());
        while (enumerationPropertyNames.hasMoreElements()) {
            String key = (String) enumerationPropertyNames.nextElement();
            propStr.append(new StringBuffer(String.valueOf(left(key, 28, ' '))).append(":  ").append(props.get(key)).append(lineSep).toString());
        }
        propStr.append(new StringBuffer("==========================================").append(lineSep).toString());
        return propStr.toString();
    }

    public static String left(String s, int width, char fillChar) {
        if (s.length() < width) {
            StringBuffer sb = new StringBuffer(width);
            sb.append(s);
            int i = width - s.length();
            while (true) {
                i--;
                if (i >= 0) {
                    sb.append(fillChar);
                } else {
                    return sb.toString();
                }
            }
        } else {
            return s;
        }
    }
}
