package bsh;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.StringTokenizer;
import java.util.Vector;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StringUtil {
    public static String[] bubbleSort(String[] strArr) {
        boolean z;
        Vector vector = new Vector();
        for (String str : strArr) {
            vector.addElement(str);
        }
        int size = vector.size();
        boolean z2 = true;
        while (z2) {
            int i = 0;
            z2 = false;
            while (i < size - 1) {
                if (((String) vector.elementAt(i)).compareTo((String) vector.elementAt(i + 1)) > 0) {
                    String str2 = (String) vector.elementAt(i + 1);
                    vector.removeElementAt(i + 1);
                    vector.insertElementAt(str2, i);
                    z = true;
                } else {
                    z = z2;
                }
                i++;
                z2 = z;
            }
        }
        String[] strArr2 = new String[size];
        vector.copyInto(strArr2);
        return strArr2;
    }

    public static String maxCommonPrefix(String str, String str2) {
        int i = 0;
        while (str.regionMatches(0, str2, 0, i)) {
            i++;
        }
        return str.substring(0, i - 1);
    }

    public static String methodString(String str, Class[] clsArr) {
        StringBuffer stringBuffer = new StringBuffer(new StringBuffer().append(str).append("(").toString());
        if (clsArr.length > 0) {
            stringBuffer.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        }
        int i = 0;
        while (i < clsArr.length) {
            Class cls = clsArr[i];
            stringBuffer.append(new StringBuffer().append(cls == null ? "null" : cls.getName()).append(i < clsArr.length + (-1) ? ", " : MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString());
            i++;
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    public static String normalizeClassName(Class cls) {
        return Reflect.normalizeClassName(cls);
    }

    public static String[] split(String str, String str2) {
        Vector vector = new Vector();
        StringTokenizer stringTokenizer = new StringTokenizer(str, str2);
        while (stringTokenizer.hasMoreTokens()) {
            vector.addElement(stringTokenizer.nextToken());
        }
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }
}
