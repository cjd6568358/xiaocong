package org.eclipse.paho.client.mqttv3.logging;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class LoggerFactory {
    private static final String CLASS_NAME;
    static Class class$0;
    static Class class$1;
    private static String jsr47LoggerClassName;
    private static String overrideloggerClassName;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.logging.LoggerFactory");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        overrideloggerClassName = null;
        Class<?> cls2 = class$1;
        if (cls2 == null) {
            try {
                cls2 = Class.forName("org.eclipse.paho.client.mqttv3.logging.JSR47Logger");
                class$1 = cls2;
            } catch (ClassNotFoundException e2) {
                throw new NoClassDefFoundError(e2.getMessage());
            }
        }
        jsr47LoggerClassName = cls2.getName();
    }

    public static Logger getLogger(String messageCatalogName, String loggerID) {
        String loggerClassName = overrideloggerClassName;
        if (loggerClassName == null) {
            loggerClassName = jsr47LoggerClassName;
        }
        Logger logger = getLogger(loggerClassName, ResourceBundle.getBundle(messageCatalogName), loggerID, null);
        if (logger == null) {
            throw new MissingResourceException("Error locating the logging class", CLASS_NAME, loggerID);
        }
        return logger;
    }

    private static Logger getLogger(String loggerClassName, ResourceBundle messageCatalog, String loggerID, String resourceName) {
        Logger logger = null;
        try {
            Class<?> cls = Class.forName(loggerClassName);
            if (cls != null) {
                try {
                    logger = (Logger) cls.newInstance();
                    logger.initialise(messageCatalog, loggerID, resourceName);
                } catch (ExceptionInInitializerError e) {
                    return null;
                } catch (IllegalAccessException e2) {
                    return null;
                } catch (InstantiationException e3) {
                    return null;
                } catch (SecurityException e4) {
                    return null;
                }
            }
            return logger;
        } catch (ClassNotFoundException e5) {
            return null;
        } catch (NoClassDefFoundError e6) {
            return null;
        }
    }
}
