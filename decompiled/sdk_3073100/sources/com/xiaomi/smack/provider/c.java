package com.xiaomi.smack.provider;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import com.xiaomi.smack.packet.e;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c {
    private static c a;
    private Map<String, Object> b = new ConcurrentHashMap();
    private Map<String, Object> c = new ConcurrentHashMap();

    private c() {
        b();
    }

    public static synchronized c a() {
        if (a == null) {
            a = new c();
        }
        return a;
    }

    private String b(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(str).append("/>");
        if (str != null) {
            sb.append("<").append(str2).append("/>");
        }
        return sb.toString();
    }

    private ClassLoader[] c() {
        ClassLoader[] classLoaderArr = {c.class.getClassLoader(), Thread.currentThread().getContextClassLoader()};
        ArrayList arrayList = new ArrayList();
        for (ClassLoader classLoader : classLoaderArr) {
            if (classLoader != null) {
                arrayList.add(classLoader);
            }
        }
        return (ClassLoader[]) arrayList.toArray(new ClassLoader[arrayList.size()]);
    }

    public Object a(String str, String str2) {
        return this.b.get(b(str, str2));
    }

    public void a(String str, String str2, Object obj) {
        if (!(obj instanceof b) && !(obj instanceof Class)) {
            throw new IllegalArgumentException("Provider must be a PacketExtensionProvider or a Class instance.");
        }
        this.b.put(b(str, str2), obj);
    }

    protected void b() {
        try {
            for (ClassLoader classLoader : c()) {
                Enumeration<URL> resources = classLoader.getResources("META-INF/smack.providers");
                while (resources.hasMoreElements()) {
                    InputStream inputStreamOpenStream = null;
                    try {
                        inputStreamOpenStream = resources.nextElement().openStream();
                        XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                        xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
                        xmlPullParserNewPullParser.setInput(inputStreamOpenStream, AsyncHttpResponseHandler.DEFAULT_CHARSET);
                        int eventType = xmlPullParserNewPullParser.getEventType();
                        do {
                            if (eventType == 2) {
                                if (xmlPullParserNewPullParser.getName().equals("iqProvider")) {
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText = xmlPullParserNewPullParser.nextText();
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText2 = xmlPullParserNewPullParser.nextText();
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText3 = xmlPullParserNewPullParser.nextText();
                                    String strB = b(strNextText, strNextText2);
                                    if (!this.c.containsKey(strB)) {
                                        try {
                                            Class<?> cls = Class.forName(strNextText3);
                                            if (a.class.isAssignableFrom(cls)) {
                                                this.c.put(strB, cls.newInstance());
                                            } else if (com.xiaomi.smack.packet.b.class.isAssignableFrom(cls)) {
                                                this.c.put(strB, cls);
                                            }
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else if (xmlPullParserNewPullParser.getName().equals("extensionProvider")) {
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText4 = xmlPullParserNewPullParser.nextText();
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText5 = xmlPullParserNewPullParser.nextText();
                                    xmlPullParserNewPullParser.next();
                                    xmlPullParserNewPullParser.next();
                                    String strNextText6 = xmlPullParserNewPullParser.nextText();
                                    String strB2 = b(strNextText4, strNextText5);
                                    if (!this.b.containsKey(strB2)) {
                                        try {
                                            Class<?> cls2 = Class.forName(strNextText6);
                                            if (b.class.isAssignableFrom(cls2)) {
                                                this.b.put(strB2, cls2.newInstance());
                                            } else if (e.class.isAssignableFrom(cls2)) {
                                                this.b.put(strB2, cls2);
                                            }
                                        } catch (ClassNotFoundException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                }
                            }
                            eventType = xmlPullParserNewPullParser.next();
                        } while (eventType != 1);
                        inputStreamOpenStream.close();
                    } catch (Throwable th) {
                        try {
                            inputStreamOpenStream.close();
                        } catch (Exception e3) {
                        }
                        throw th;
                    }
                }
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }
}
