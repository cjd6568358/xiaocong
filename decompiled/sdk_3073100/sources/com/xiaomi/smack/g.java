package com.xiaomi.smack;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class g {
    private static int a;
    private static int b;
    private static int c = 300000;
    private static int d = 330000;
    private static Vector<String> e = new Vector<>();

    static {
        a = 5000;
        b = 330000;
        try {
            for (ClassLoader classLoader : d()) {
                Enumeration<URL> resources = classLoader.getResources("META-INF/smack-config.xml");
                while (resources.hasMoreElements()) {
                    InputStream inputStreamOpenStream = null;
                    try {
                        try {
                            inputStreamOpenStream = resources.nextElement().openStream();
                            XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                            xmlPullParserNewPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
                            xmlPullParserNewPullParser.setInput(inputStreamOpenStream, AsyncHttpResponseHandler.DEFAULT_CHARSET);
                            int eventType = xmlPullParserNewPullParser.getEventType();
                            do {
                                if (eventType == 2) {
                                    if (xmlPullParserNewPullParser.getName().equals("className")) {
                                        a(xmlPullParserNewPullParser);
                                    } else if (xmlPullParserNewPullParser.getName().equals("packetReplyTimeout")) {
                                        a = a(xmlPullParserNewPullParser, a);
                                    } else if (xmlPullParserNewPullParser.getName().equals("keepAliveInterval")) {
                                        b = a(xmlPullParserNewPullParser, b);
                                    } else if (xmlPullParserNewPullParser.getName().equals("mechName")) {
                                        e.add(xmlPullParserNewPullParser.nextText());
                                    }
                                }
                                eventType = xmlPullParserNewPullParser.next();
                            } while (eventType != 1);
                            inputStreamOpenStream.close();
                        } catch (Throwable th) {
                            try {
                                inputStreamOpenStream.close();
                            } catch (Exception e2) {
                            }
                            throw th;
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        try {
                            inputStreamOpenStream.close();
                        } catch (Exception e4) {
                        }
                    }
                }
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
    }

    private g() {
    }

    private static int a(XmlPullParser xmlPullParser, int i) {
        try {
            return Integer.parseInt(xmlPullParser.nextText());
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return i;
        }
    }

    public static String a() {
        return "3.1.0";
    }

    private static void a(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String strNextText = xmlPullParser.nextText();
        try {
            Class.forName(strNextText);
        } catch (ClassNotFoundException e2) {
            System.err.println("Error! A startup class specified in smack-config.xml could not be loaded: " + strNextText);
        }
    }

    public static int b() {
        return b;
    }

    public static int c() {
        return c;
    }

    private static ClassLoader[] d() {
        ClassLoader[] classLoaderArr = {g.class.getClassLoader(), Thread.currentThread().getContextClassLoader()};
        ArrayList arrayList = new ArrayList();
        for (ClassLoader classLoader : classLoaderArr) {
            if (classLoader != null) {
                arrayList.add(classLoader);
            }
        }
        return (ClassLoader[]) arrayList.toArray(new ClassLoader[arrayList.size()]);
    }
}
