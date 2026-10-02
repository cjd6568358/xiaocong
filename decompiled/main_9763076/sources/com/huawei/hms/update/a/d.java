package com.huawei.hms.update.a;

import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.tencent.android.tpush.common.Constants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX INFO: compiled from: FilelistResponse.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d {
    private String a = Constants.MAIN_VERSION_TAG;
    private String b = Constants.MAIN_VERSION_TAG;
    private String c = Constants.MAIN_VERSION_TAG;
    private String d = Constants.MAIN_VERSION_TAG;
    private String e = Constants.MAIN_VERSION_TAG;
    private String f = Constants.MAIN_VERSION_TAG;
    private String g = Constants.MAIN_VERSION_TAG;
    private int h = 0;

    d() {
    }

    public String a() {
        return this.b;
    }

    public int b() {
        try {
            return Integer.parseInt(this.c);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String c() {
        return this.d;
    }

    public int d() {
        return this.h;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("Name: ").append(this.a).append(", ");
        sb.append("File: ").append(this.b).append(", ");
        sb.append("Size: ").append(this.c).append(", ");
        sb.append("Hash: ").append(this.d).append(", ");
        sb.append("PackageName: ").append(this.e).append(", ");
        sb.append("PackageType: ").append(this.f).append(", ");
        sb.append("VersionName: ").append(this.g).append(", ");
        sb.append("VersionCode: ").append(this.h);
        sb.append('}');
        return sb.toString();
    }

    public static d a(String str) {
        d dVar = new d();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes(Charset.defaultCharset()));
        try {
            try {
                XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
                xmlPullParserNewPullParser.setInput(byteArrayInputStream, HTTP.UTF_8);
                for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
                    if (eventType == 2) {
                        a(dVar, xmlPullParserNewPullParser);
                    }
                }
            } finally {
                com.huawei.hms.c.c.a((InputStream) byteArrayInputStream);
            }
        } catch (IOException | XmlPullParserException e) {
            com.huawei.hms.support.log.a.d("FilelistResponse", "In parseResponse, Failed to parse xml for get-filelist response." + e.getMessage());
            dVar = new d();
            com.huawei.hms.c.c.a((InputStream) byteArrayInputStream);
        }
        return dVar;
    }

    private static void a(d dVar, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String name = xmlPullParser.getName();
        if (xmlPullParser.getDepth() == 3 && RNMessageModule.NAME.equals(name)) {
            dVar.a = xmlPullParser.nextText();
        }
        if (xmlPullParser.getDepth() == 4) {
            if ("spath".equals(name)) {
                dVar.b = xmlPullParser.nextText();
                return;
            }
            if ("size".equals(name)) {
                dVar.c = xmlPullParser.nextText();
                return;
            }
            if ("sha256".equals(name)) {
                dVar.d = xmlPullParser.nextText();
                return;
            }
            if (Constants.FLAG_PACKAGE_NAME.equals(name)) {
                dVar.e = xmlPullParser.nextText();
                return;
            }
            if ("packageType".equals(name)) {
                dVar.f = xmlPullParser.nextText();
            } else if ("versionName".equals(name)) {
                dVar.g = xmlPullParser.nextText();
            } else if ("versionCode".equals(name)) {
                dVar.h = b(xmlPullParser.nextText());
            }
        }
    }

    private static int b(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
