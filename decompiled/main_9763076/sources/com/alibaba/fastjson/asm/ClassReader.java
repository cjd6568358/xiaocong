package com.alibaba.fastjson.asm;

import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ClassReader {
    public final byte[] b;
    public final int header;
    private final int[] items;
    private final int maxStringLength;
    private final String[] strings;

    public ClassReader(InputStream is) throws IOException {
        int size;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
        while (true) {
            int len = is.read(buf);
            if (len == -1) {
                break;
            } else if (len > 0) {
                out.write(buf, 0, len);
            }
        }
        is.close();
        this.b = out.toByteArray();
        this.items = new int[readUnsignedShort(8)];
        int n = this.items.length;
        this.strings = new String[n];
        int max = 0;
        int index = 10;
        int i = 1;
        while (i < n) {
            this.items[i] = index + 1;
            switch (this.b[index]) {
                case 1:
                    size = readUnsignedShort(index + 1) + 3;
                    if (size > max) {
                        max = size;
                    }
                    break;
                case 2:
                case 7:
                case 8:
                case 13:
                case 14:
                case 16:
                case 17:
                default:
                    size = 3;
                    break;
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                case 18:
                    size = 5;
                    break;
                case 5:
                case 6:
                    size = 9;
                    i++;
                    break;
                case 15:
                    size = 4;
                    break;
            }
            index += size;
            i++;
        }
        this.maxStringLength = max;
        this.header = index;
    }

    public void accept(TypeCollector classVisitor) {
        char[] c = new char[this.maxStringLength];
        int u = this.header;
        int i = this.items[readUnsignedShort(u + 4)];
        int len = readUnsignedShort(u + 6);
        int u2 = u + 8;
        for (int i2 = 0; i2 < len; i2++) {
            u2 += 2;
        }
        int v = u2;
        int v2 = v + 2;
        for (int i3 = readUnsignedShort(v); i3 > 0; i3--) {
            v2 += 8;
            for (int j = readUnsignedShort(v2 + 6); j > 0; j--) {
                v2 += readInt(v2 + 2) + 6;
            }
        }
        int v3 = v2 + 2;
        for (int i4 = readUnsignedShort(v2); i4 > 0; i4--) {
            v3 += 8;
            for (int j2 = readUnsignedShort(v3 + 6); j2 > 0; j2--) {
                v3 += readInt(v3 + 2) + 6;
            }
        }
        int v4 = v3 + 2;
        for (int i5 = readUnsignedShort(v3); i5 > 0; i5--) {
            v4 += readInt(v4 + 2) + 6;
        }
        int u3 = u2 + 2;
        for (int i6 = readUnsignedShort(u2); i6 > 0; i6--) {
            u3 += 8;
            for (int j3 = readUnsignedShort(u3 + 6); j3 > 0; j3--) {
                u3 += readInt(u3 + 2) + 6;
            }
        }
        int u4 = u3 + 2;
        for (int i7 = readUnsignedShort(u3); i7 > 0; i7--) {
            u4 = readMethod(classVisitor, c, u4);
        }
    }

    private int readMethod(TypeCollector classVisitor, char[] c, int u) {
        int access = readUnsignedShort(u);
        String name = readUTF8(u + 2, c);
        String desc = readUTF8(u + 4, c);
        int v = 0;
        int u2 = u + 8;
        for (int j = readUnsignedShort(u + 6); j > 0; j--) {
            String attrName = readUTF8(u2, c);
            int attrSize = readInt(u2 + 2);
            int u3 = u2 + 6;
            if (attrName.equals("Code")) {
                v = u3;
            }
            u2 = u3 + attrSize;
        }
        if (0 != 0) {
            int w = 0 + 2;
            for (int j2 = 0; j2 < readUnsignedShort(w); j2++) {
                w += 2;
            }
        }
        MethodCollector mv = classVisitor.visitMethod(access, name, desc);
        if (mv != null && v != 0) {
            int codeLength = readInt(v + 4);
            int codeEnd = v + 8 + codeLength;
            int v2 = codeEnd + 2;
            for (int j3 = readUnsignedShort(codeEnd); j3 > 0; j3--) {
                v2 += 8;
            }
            int varTable = 0;
            int varTypeTable = 0;
            int v3 = v2 + 2;
            for (int j4 = readUnsignedShort(v2); j4 > 0; j4--) {
                String attrName2 = readUTF8(v3, c);
                if (attrName2.equals("LocalVariableTable")) {
                    varTable = v3 + 6;
                } else if (attrName2.equals("LocalVariableTypeTable")) {
                    varTypeTable = v3 + 6;
                }
                v3 += readInt(v3 + 2) + 6;
            }
            if (varTable != 0) {
                if (varTypeTable != 0) {
                    int k = readUnsignedShort(varTypeTable) * 3;
                    int w2 = varTypeTable + 2;
                    int[] typeTable = new int[k];
                    while (k > 0) {
                        int k2 = k - 1;
                        typeTable[k2] = w2 + 6;
                        int k3 = k2 - 1;
                        typeTable[k3] = readUnsignedShort(w2 + 8);
                        k = k3 - 1;
                        typeTable[k] = readUnsignedShort(w2);
                        w2 += 10;
                    }
                }
                int w3 = varTable + 2;
                for (int k4 = readUnsignedShort(varTable); k4 > 0; k4--) {
                    int index = readUnsignedShort(w3 + 8);
                    mv.visitLocalVariable(readUTF8(w3 + 4, c), index);
                    w3 += 10;
                }
            }
        }
        return u2;
    }

    private int readUnsignedShort(int index) {
        byte[] b = this.b;
        return ((b[index] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | (b[index + 1] & Constants.NETWORK_TYPE_UNCONNECTED);
    }

    private int readInt(int index) {
        byte[] b = this.b;
        return ((b[index] & Constants.NETWORK_TYPE_UNCONNECTED) << 24) | ((b[index + 1] & Constants.NETWORK_TYPE_UNCONNECTED) << 16) | ((b[index + 2] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | (b[index + 3] & Constants.NETWORK_TYPE_UNCONNECTED);
    }

    private String readUTF8(int index, char[] buf) {
        int item = readUnsignedShort(index);
        String s = this.strings[item];
        if (s == null) {
            int index2 = this.items[item];
            String[] strArr = this.strings;
            String s2 = readUTF(index2 + 2, readUnsignedShort(index2), buf);
            strArr[item] = s2;
            return s2;
        }
        return s;
    }

    private String readUTF(int index, int utfLen, char[] buf) {
        int strLen;
        int endIndex = index + utfLen;
        byte[] b = this.b;
        int st = 0;
        char cc = 0;
        int strLen2 = 0;
        int index2 = index;
        while (index2 < endIndex) {
            int index3 = index2 + 1;
            int c = b[index2];
            switch (st) {
                case 0:
                    int c2 = c & 255;
                    if (c2 < 128) {
                        strLen = strLen2 + 1;
                        buf[strLen2] = (char) c2;
                    } else if (c2 < 224 && c2 > 191) {
                        cc = (char) (c2 & 31);
                        st = 1;
                        strLen = strLen2;
                    } else {
                        cc = (char) (c2 & 15);
                        st = 2;
                        strLen = strLen2;
                    }
                    break;
                case 1:
                    strLen = strLen2 + 1;
                    buf[strLen2] = (char) ((cc << 6) | (c & 63));
                    st = 0;
                    break;
                case 2:
                    cc = (char) ((cc << 6) | (c & 63));
                    st = 1;
                    strLen = strLen2;
                    break;
                default:
                    strLen = strLen2;
                    break;
            }
            strLen2 = strLen;
            index2 = index3;
        }
        return new String(buf, 0, strLen2);
    }
}
