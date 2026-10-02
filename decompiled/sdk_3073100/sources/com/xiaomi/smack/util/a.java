package com.xiaomi.smack.util;

import android.text.TextUtils;
import com.xiaomi.push.service.ak;
import com.xiaomi.push.service.aq;
import com.xiaomi.smack.l;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a {
    private static XmlPullParser a = null;

    public static com.xiaomi.smack.packet.a a(String str, String str2, XmlPullParser xmlPullParser) {
        Object objA = com.xiaomi.smack.provider.c.a().a("all", "xm:chat");
        if (objA == null || !(objA instanceof com.xiaomi.push.service.c)) {
            return null;
        }
        return ((com.xiaomi.push.service.c) objA).b(xmlPullParser);
    }

    public static com.xiaomi.smack.packet.b a(XmlPullParser xmlPullParser, com.xiaomi.smack.a aVar) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue("", "id");
        String attributeValue2 = xmlPullParser.getAttributeValue("", "to");
        String attributeValue3 = xmlPullParser.getAttributeValue("", "from");
        String attributeValue4 = xmlPullParser.getAttributeValue("", "chid");
        com.xiaomi.smack.packet.b.a aVarA = com.xiaomi.smack.packet.b.a.a(xmlPullParser.getAttributeValue("", "type"));
        HashMap map = new HashMap();
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            map.put(attributeName, xmlPullParser.getAttributeValue("", attributeName));
        }
        boolean z = false;
        com.xiaomi.smack.packet.h hVarD = null;
        com.xiaomi.smack.packet.b cVar = null;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("error")) {
                    hVarD = d(xmlPullParser);
                } else {
                    cVar = new com.xiaomi.smack.packet.b();
                    cVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("iq")) {
                z = true;
            }
            hVarD = hVarD;
            cVar = cVar;
            z = z;
        }
        if (cVar == null) {
            if (com.xiaomi.smack.packet.b.a.a == aVarA || com.xiaomi.smack.packet.b.a.b == aVarA) {
                b bVar = new b();
                bVar.k(attributeValue);
                bVar.m(attributeValue3);
                bVar.n(attributeValue2);
                bVar.a(com.xiaomi.smack.packet.b.a.d);
                bVar.l(attributeValue4);
                bVar.a(new com.xiaomi.smack.packet.h(com.xiaomi.smack.packet.h.a.e));
                aVar.a(bVar);
                com.xiaomi.channel.commonutils.logger.b.d("iq usage error. send packet in packet parser.");
                return null;
            }
            cVar = new c();
        }
        cVar.k(attributeValue);
        cVar.m(attributeValue2);
        cVar.l(attributeValue4);
        cVar.n(attributeValue3);
        cVar.a(aVarA);
        cVar.a(hVarD);
        cVar.a(map);
        return cVar;
    }

    public static com.xiaomi.smack.packet.d a(XmlPullParser xmlPullParser) throws XmlPullParserException, l, IOException {
        String attributeValue;
        if ("1".equals(xmlPullParser.getAttributeValue("", "s"))) {
            String attributeValue2 = xmlPullParser.getAttributeValue("", "chid");
            String attributeValue3 = xmlPullParser.getAttributeValue("", "id");
            String attributeValue4 = xmlPullParser.getAttributeValue("", "from");
            String attributeValue5 = xmlPullParser.getAttributeValue("", "to");
            String attributeValue6 = xmlPullParser.getAttributeValue("", "type");
            ak.b bVarB = ak.a().b(attributeValue2, attributeValue5);
            ak.b bVarB2 = bVarB == null ? ak.a().b(attributeValue2, attributeValue4) : bVarB;
            if (bVarB2 == null) {
                throw new l("the channel id is wrong while receiving a encrypted message");
            }
            boolean z = false;
            com.xiaomi.smack.packet.d dVarA = null;
            while (!z) {
                int next = xmlPullParser.next();
                if (next == 2) {
                    if (!"s".equals(xmlPullParser.getName())) {
                        throw new l("error while receiving a encrypted message with wrong format");
                    }
                    if (xmlPullParser.next() != 4) {
                        throw new l("error while receiving a encrypted message with wrong format");
                    }
                    String text = xmlPullParser.getText();
                    if ("5".equals(attributeValue2) || "6".equals(attributeValue2)) {
                        com.xiaomi.smack.packet.c cVar = new com.xiaomi.smack.packet.c();
                        cVar.l(attributeValue2);
                        cVar.b(true);
                        cVar.n(attributeValue4);
                        cVar.m(attributeValue5);
                        cVar.k(attributeValue3);
                        cVar.f(attributeValue6);
                        com.xiaomi.smack.packet.a aVar = new com.xiaomi.smack.packet.a("s", null, (String[]) null, (String[]) null);
                        aVar.b(text);
                        cVar.a(aVar);
                        return cVar;
                    }
                    a(aq.b(aq.a(bVarB2.i, attributeValue3), text));
                    a.next();
                    dVarA = a(a);
                } else if (next == 3 && xmlPullParser.getName().equals("message")) {
                    z = true;
                }
            }
            if (dVarA == null) {
                throw new l("error while receiving a encrypted message with wrong format");
            }
            return dVarA;
        }
        com.xiaomi.smack.packet.c cVar2 = new com.xiaomi.smack.packet.c();
        String attributeValue7 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue7 == null) {
            attributeValue7 = "ID_NOT_AVAILABLE";
        }
        cVar2.k(attributeValue7);
        cVar2.m(xmlPullParser.getAttributeValue("", "to"));
        cVar2.n(xmlPullParser.getAttributeValue("", "from"));
        cVar2.l(xmlPullParser.getAttributeValue("", "chid"));
        cVar2.a(xmlPullParser.getAttributeValue("", "appid"));
        try {
            attributeValue = xmlPullParser.getAttributeValue("", "transient");
        } catch (Exception e) {
            attributeValue = null;
        }
        try {
            String attributeValue8 = xmlPullParser.getAttributeValue("", "seq");
            if (!TextUtils.isEmpty(attributeValue8)) {
                cVar2.b(attributeValue8);
            }
        } catch (Exception e2) {
        }
        try {
            String attributeValue9 = xmlPullParser.getAttributeValue("", "mseq");
            if (!TextUtils.isEmpty(attributeValue9)) {
                cVar2.c(attributeValue9);
            }
        } catch (Exception e3) {
        }
        try {
            String attributeValue10 = xmlPullParser.getAttributeValue("", "fseq");
            if (!TextUtils.isEmpty(attributeValue10)) {
                cVar2.d(attributeValue10);
            }
        } catch (Exception e4) {
        }
        try {
            String attributeValue11 = xmlPullParser.getAttributeValue("", "status");
            if (!TextUtils.isEmpty(attributeValue11)) {
                cVar2.e(attributeValue11);
            }
        } catch (Exception e5) {
        }
        cVar2.a(!TextUtils.isEmpty(attributeValue) && attributeValue.equalsIgnoreCase("true"));
        cVar2.f(xmlPullParser.getAttributeValue("", "type"));
        String strF = f(xmlPullParser);
        if (strF == null || "".equals(strF.trim())) {
            com.xiaomi.smack.packet.d.u();
        } else {
            cVar2.j(strF);
        }
        String strNextText = null;
        boolean z2 = false;
        while (!z2) {
            int next2 = xmlPullParser.next();
            if (next2 == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (TextUtils.isEmpty(namespace)) {
                    namespace = "xm";
                }
                if (name.equals("subject")) {
                    if (f(xmlPullParser) == null) {
                    }
                    cVar2.g(e(xmlPullParser));
                } else if (name.equals("body")) {
                    String attributeValue12 = xmlPullParser.getAttributeValue("", "encode");
                    String strE = e(xmlPullParser);
                    if (TextUtils.isEmpty(attributeValue12)) {
                        cVar2.h(strE);
                    } else {
                        cVar2.a(strE, attributeValue12);
                    }
                } else if (name.equals("thread")) {
                    if (strNextText == null) {
                        strNextText = xmlPullParser.nextText();
                    }
                } else if (name.equals("error")) {
                    cVar2.a(d(xmlPullParser));
                } else {
                    cVar2.a(a(name, namespace, xmlPullParser));
                }
            } else if (next2 == 3 && xmlPullParser.getName().equals("message")) {
                z2 = true;
            }
        }
        cVar2.i(strNextText);
        return cVar2;
    }

    private static void a(byte[] bArr) throws XmlPullParserException {
        if (a == null) {
            try {
                a = XmlPullParserFactory.newInstance().newPullParser();
                a.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        a.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
    }

    public static com.xiaomi.smack.packet.f b(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        com.xiaomi.smack.packet.f.b bVarValueOf = com.xiaomi.smack.packet.f.b.available;
        String attributeValue = xmlPullParser.getAttributeValue("", "type");
        if (attributeValue != null && !attributeValue.equals("")) {
            try {
                bVarValueOf = com.xiaomi.smack.packet.f.b.valueOf(attributeValue);
            } catch (IllegalArgumentException e) {
                System.err.println("Found invalid presence type " + attributeValue);
            }
        }
        com.xiaomi.smack.packet.f fVar = new com.xiaomi.smack.packet.f(bVarValueOf);
        fVar.m(xmlPullParser.getAttributeValue("", "to"));
        fVar.n(xmlPullParser.getAttributeValue("", "from"));
        fVar.l(xmlPullParser.getAttributeValue("", "chid"));
        String attributeValue2 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue2 == null) {
            attributeValue2 = "ID_NOT_AVAILABLE";
        }
        fVar.k(attributeValue2);
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("status")) {
                    fVar.a(xmlPullParser.nextText());
                } else if (name.equals("priority")) {
                    try {
                        fVar.a(Integer.parseInt(xmlPullParser.nextText()));
                    } catch (NumberFormatException e2) {
                    } catch (IllegalArgumentException e3) {
                        fVar.a(0);
                    }
                } else if (name.equals("show")) {
                    String strNextText = xmlPullParser.nextText();
                    try {
                        fVar.a(com.xiaomi.smack.packet.f.a.valueOf(strNextText));
                    } catch (IllegalArgumentException e4) {
                        System.err.println("Found invalid presence mode " + strNextText);
                    }
                } else if (name.equals("error")) {
                    fVar.a(d(xmlPullParser));
                } else {
                    fVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("presence")) {
                z = true;
            }
        }
        return fVar;
    }

    public static com.xiaomi.smack.packet.g c(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        com.xiaomi.smack.packet.g gVar = null;
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                gVar = new com.xiaomi.smack.packet.g(xmlPullParser.getName());
            } else if (next == 3 && xmlPullParser.getName().equals("error")) {
                z = true;
            }
        }
        return gVar;
    }

    public static com.xiaomi.smack.packet.h d(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        String attributeValue = null;
        String str = null;
        String str2 = "-1";
        int i = 0;
        while (i < xmlPullParser.getAttributeCount()) {
            String attributeValue2 = xmlPullParser.getAttributeName(i).equals("code") ? xmlPullParser.getAttributeValue("", "code") : str2;
            String attributeValue3 = xmlPullParser.getAttributeName(i).equals("type") ? xmlPullParser.getAttributeValue("", "type") : str;
            if (xmlPullParser.getAttributeName(i).equals("reason")) {
                attributeValue = xmlPullParser.getAttributeValue("", "reason");
            }
            i++;
            str = attributeValue3;
            str2 = attributeValue2;
        }
        boolean z = false;
        String str3 = null;
        String strNextText = null;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (xmlPullParser.getName().equals("text")) {
                    strNextText = xmlPullParser.nextText();
                } else {
                    String name = xmlPullParser.getName();
                    String namespace = xmlPullParser.getNamespace();
                    if ("urn:ietf:params:xml:ns:xmpp-stanzas".equals(namespace)) {
                        str3 = name;
                    } else {
                        arrayList.add(a(name, namespace, xmlPullParser));
                    }
                }
            } else if (next == 3) {
                if (xmlPullParser.getName().equals("error")) {
                    z = true;
                }
            } else if (next == 4) {
                strNextText = xmlPullParser.getText();
            }
        }
        return new com.xiaomi.smack.packet.h(Integer.parseInt(str2), str == null ? "cancel" : str, attributeValue, str3, strNextText, arrayList);
    }

    private static String e(XmlPullParser xmlPullParser) {
        String str = "";
        int depth = xmlPullParser.getDepth();
        while (true) {
            if (xmlPullParser.next() == 3 && xmlPullParser.getDepth() == depth) {
                return str;
            }
            str = str + xmlPullParser.getText();
        }
    }

    private static String f(XmlPullParser xmlPullParser) {
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            if ("xml:lang".equals(attributeName) || ("lang".equals(attributeName) && "xml".equals(xmlPullParser.getAttributePrefix(i)))) {
                return xmlPullParser.getAttributeValue(i);
            }
        }
        return null;
    }
}
