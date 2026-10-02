package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JavaBeanSerializer extends SerializeFilterable implements ObjectSerializer {
    protected SerializeBeanInfo beanInfo;
    protected final FieldSerializer[] getters;
    private volatile transient long[] hashArray;
    private volatile transient short[] hashArrayMapping;
    protected final FieldSerializer[] sortedGetters;

    public JavaBeanSerializer(Class<?> beanType) {
        this(beanType, (Map) null);
    }

    public JavaBeanSerializer(Class<?> beanType, Map<String, String> aliasMap) {
        this(TypeUtils.buildBeanInfo(beanType, aliasMap, null));
    }

    public JavaBeanSerializer(SerializeBeanInfo beanInfo) {
        this.beanInfo = beanInfo;
        this.sortedGetters = new FieldSerializer[beanInfo.sortedFields.length];
        for (int i = 0; i < this.sortedGetters.length; i++) {
            this.sortedGetters[i] = new FieldSerializer(beanInfo.beanType, beanInfo.sortedFields[i]);
        }
        if (beanInfo.fields == beanInfo.sortedFields) {
            this.getters = this.sortedGetters;
            return;
        }
        this.getters = new FieldSerializer[beanInfo.fields.length];
        for (int i2 = 0; i2 < this.getters.length; i2++) {
            this.getters[i2] = getFieldSerializer(beanInfo.fields[i2].name);
        }
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        write(serializer, object, fieldName, fieldType, features, false);
    }

    public void writeNoneASM(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        write(serializer, object, fieldName, fieldType, features, false);
    }

    /* JADX WARN: Code duplicated, block: B:155:0x02e6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:156:0x02e8 A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:164:0x0311  */
    /* JADX WARN: Code duplicated, block: B:166:0x0315  */
    /* JADX WARN: Code duplicated, block: B:186:0x039d  */
    /* JADX WARN: Code duplicated, block: B:188:0x03a1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:189:0x03a3 A[Catch: Exception -> 0x0328, all -> 0x0397, TRY_ENTER, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:191:0x03b3  */
    /* JADX WARN: Code duplicated, block: B:195:0x03bd A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:197:0x03ce A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:208:0x0403 A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:214:0x042f A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:216:0x0435 A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:222:0x0454 A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:223:0x045f A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:225:0x0469 A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:228:0x0479 A[Catch: Exception -> 0x0328, all -> 0x0397, TryCatch #1 {Exception -> 0x0328, blocks: (B:20:0x005b, B:21:0x0062, B:23:0x0067, B:25:0x0071, B:26:0x0077, B:28:0x0085, B:30:0x008d, B:42:0x00c7, B:44:0x00cd, B:47:0x00d4, B:50:0x00e7, B:51:0x00f9, B:53:0x0100, B:56:0x011e, B:68:0x0146, B:70:0x0152, B:72:0x0160, B:74:0x0168, B:76:0x0174, B:78:0x0180, B:79:0x0188, B:83:0x019c, B:86:0x01ab, B:87:0x01b3, B:90:0x01d3, B:92:0x01d9, B:95:0x01e5, B:97:0x01eb, B:99:0x01f6, B:101:0x0203, B:103:0x020f, B:105:0x0215, B:107:0x0220, B:109:0x0226, B:111:0x022c, B:113:0x0237, B:115:0x023d, B:117:0x0243, B:119:0x024e, B:121:0x0254, B:123:0x025a, B:125:0x0269, B:127:0x026f, B:129:0x0275, B:131:0x0283, B:133:0x0289, B:135:0x028f, B:137:0x029e, B:139:0x02a4, B:141:0x02aa, B:144:0x02b7, B:146:0x02bd, B:148:0x02c3, B:150:0x02ce, B:152:0x02df, B:156:0x02e8, B:157:0x02f0, B:158:0x02f7, B:160:0x02ff, B:162:0x0305, B:223:0x045f, B:225:0x0469, B:226:0x0473, B:228:0x0479, B:189:0x03a3, B:190:0x03aa, B:192:0x03b5, B:195:0x03bd, B:208:0x0403, B:197:0x03ce, B:200:0x03da, B:203:0x03e4, B:205:0x03ef, B:209:0x040b, B:207:0x03fa, B:210:0x0410, B:212:0x041c, B:213:0x0425, B:214:0x042f, B:216:0x0435, B:218:0x043b, B:221:0x0449, B:222:0x0454, B:168:0x0319, B:171:0x0327, B:235:0x048b, B:237:0x0499, B:239:0x04a3, B:241:0x04ab, B:32:0x0099, B:34:0x00a3, B:36:0x00a9, B:39:0x00b3), top: B:246:0x005b, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:232:0x0483  */
    /* JADX WARN: Code duplicated, block: B:266:0x0124 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:270:0x047f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:271:? A[LOOP:1: B:226:0x0473->B:271:?, LOOP_END, SYNTHETIC] */
    protected void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features, boolean unwrapped) throws IOException {
        FieldSerializer[] getters;
        Type type;
        Object propertyValueDirect;
        boolean fieldUnwrappedNull;
        Map map;
        boolean hasNotNull;
        Class<?> fieldCLass;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        if (!writeReference(serializer, object, features)) {
            if (out.sortField) {
                getters = this.sortedGetters;
            } else {
                getters = this.getters;
            }
            SerialContext parent = serializer.context;
            if (!this.beanInfo.beanType.isEnum()) {
                serializer.setContext(parent, object, fieldName, this.beanInfo.features, features);
            }
            boolean writeAsArray = isWriteAsArray(serializer, features);
            char startSeperator = writeAsArray ? '[' : '{';
            char endSeperator = writeAsArray ? ']' : '}';
            try {
                if (!unwrapped) {
                    try {
                        out.append(startSeperator);
                    } catch (Exception e) {
                        String errorMessage = object != null ? "write javaBean error, fastjson version 1.2.40, class " + object.getClass().getName() : "write javaBean error, fastjson version 1.2.40";
                        if (fieldName != null) {
                            errorMessage = errorMessage + ", fieldName : " + fieldName;
                        }
                        if (e.getMessage() != null) {
                            errorMessage = errorMessage + ", " + e.getMessage();
                        }
                        throw new JSONException(errorMessage, e);
                    }
                }
                if (getters.length > 0 && out.isEnabled(SerializerFeature.PrettyFormat)) {
                    serializer.incrementIndent();
                    serializer.println();
                }
                boolean commaFlag = false;
                if ((this.beanInfo.features & SerializerFeature.WriteClassName.mask) != 0 || (SerializerFeature.WriteClassName.mask & features) != 0 || serializer.isWriteClassName(fieldType, object)) {
                    Class<?> objClass = object.getClass();
                    if (objClass != fieldType && (fieldType instanceof WildcardType)) {
                        type = TypeUtils.getClass(fieldType);
                    } else {
                        type = fieldType;
                    }
                    if (objClass != type) {
                        writeClassName(serializer, this.beanInfo.typeKey, object);
                        commaFlag = true;
                    }
                }
                char seperator = commaFlag ? ',' : (char) 0;
                boolean directWritePrefix = out.quoteFieldNames && !out.useSingleQuotes;
                char newSeperator = writeBefore(serializer, object, seperator);
                boolean commaFlag2 = newSeperator == ',';
                boolean skipTransient = out.isEnabled(SerializerFeature.SkipTransientField);
                boolean ignoreNonFieldGetter = out.isEnabled(SerializerFeature.IgnoreNonFieldGetter);
                for (FieldSerializer fieldSerializer : getters) {
                    Field field = fieldSerializer.fieldInfo.field;
                    FieldInfo fieldInfo = fieldSerializer.fieldInfo;
                    String fieldInfoName = fieldInfo.name;
                    Class<?> fieldClass = fieldInfo.fieldClass;
                    if ((!skipTransient || field == null || !fieldInfo.fieldTransient) && ((!ignoreNonFieldGetter || field != null) && applyName(serializer, object, fieldInfoName) && applyLabel(serializer, fieldInfo.label) && (this.beanInfo.typeKey == null || !fieldInfoName.equals(this.beanInfo.typeKey) || !serializer.isWriteClassName(fieldType, object)))) {
                        try {
                            propertyValueDirect = fieldSerializer.getPropertyValueDirect(object);
                        } catch (InvocationTargetException ex) {
                            if (out.isEnabled(SerializerFeature.IgnoreErrorGetter)) {
                                propertyValueDirect = null;
                            } else {
                                throw ex;
                            }
                        }
                        if (apply(serializer, object, fieldInfoName, propertyValueDirect)) {
                            Object originalValue = (fieldClass == String.class && "trim".equals(fieldInfo.format) && propertyValueDirect != null) ? ((String) propertyValueDirect).trim() : propertyValueDirect;
                            String key = processKey(serializer, object, fieldInfoName, originalValue);
                            Object propertyValue = processValue(serializer, fieldSerializer.fieldContext, object, fieldInfoName, originalValue);
                            if ((propertyValue != null || writeAsArray || fieldSerializer.writeNull || out.isEnabled(SerializerFeature.WRITE_MAP_NULL_FEATURES)) && (propertyValue == null || ((!out.notWriteDefaultValue && (fieldInfo.serialzeFeatures & SerializerFeature.NotWriteDefaultValue.mask) == 0 && (this.beanInfo.features & SerializerFeature.NotWriteDefaultValue.mask) == 0) || (((fieldCLass = fieldInfo.fieldClass) != Byte.TYPE || !(propertyValue instanceof Byte) || ((Byte) propertyValue).byteValue() != 0) && ((fieldCLass != Short.TYPE || !(propertyValue instanceof Short) || ((Short) propertyValue).shortValue() != 0) && ((fieldCLass != Integer.TYPE || !(propertyValue instanceof Integer) || ((Integer) propertyValue).intValue() != 0) && ((fieldCLass != Long.TYPE || !(propertyValue instanceof Long) || ((Long) propertyValue).longValue() != 0) && ((fieldCLass != Float.TYPE || !(propertyValue instanceof Float) || ((Float) propertyValue).floatValue() != 0.0f) && ((fieldCLass != Double.TYPE || !(propertyValue instanceof Double) || ((Double) propertyValue).doubleValue() != 0.0d) && (fieldCLass != Boolean.TYPE || !(propertyValue instanceof Boolean) || ((Boolean) propertyValue).booleanValue())))))))))) {
                                if (commaFlag2) {
                                    if (!fieldInfo.unwrapped || !(propertyValue instanceof Map) || ((Map) propertyValue).size() != 0) {
                                        out.write(44);
                                        if (out.isEnabled(SerializerFeature.PrettyFormat)) {
                                            serializer.println();
                                        }
                                        if (key != fieldInfoName) {
                                            if (!writeAsArray) {
                                                out.writeFieldName(key, true);
                                            }
                                            serializer.write(propertyValue);
                                        } else if (originalValue != propertyValue) {
                                            if (!writeAsArray) {
                                                fieldSerializer.writePrefix(serializer);
                                            }
                                            serializer.write(propertyValue);
                                        } else {
                                            if (!writeAsArray) {
                                                if (directWritePrefix) {
                                                    out.write(fieldInfo.name_chars, 0, fieldInfo.name_chars.length);
                                                } else {
                                                    fieldSerializer.writePrefix(serializer);
                                                }
                                            }
                                            if (!writeAsArray) {
                                                JSONField fieldAnnotation = fieldInfo.getAnnotation();
                                                if (fieldClass != String.class) {
                                                    if (!fieldInfo.unwrapped) {
                                                    }
                                                    fieldSerializer.writeValue(serializer, propertyValue);
                                                } else {
                                                    if (!fieldInfo.unwrapped) {
                                                    }
                                                    fieldSerializer.writeValue(serializer, propertyValue);
                                                }
                                            } else {
                                                fieldSerializer.writeValue(serializer, propertyValue);
                                            }
                                        }
                                        fieldUnwrappedNull = false;
                                        if (fieldInfo.unwrapped) {
                                            map = (Map) propertyValue;
                                            if (map.size() == 0) {
                                                fieldUnwrappedNull = true;
                                            } else if (!serializer.isEnabled(SerializerFeature.WriteMapNullValue)) {
                                                hasNotNull = false;
                                                for (Object value : map.values()) {
                                                    if (value != null) {
                                                        hasNotNull = true;
                                                        break;
                                                    }
                                                }
                                                if (!hasNotNull) {
                                                    fieldUnwrappedNull = true;
                                                }
                                            }
                                        }
                                        if (!fieldUnwrappedNull) {
                                            commaFlag2 = true;
                                        }
                                    }
                                } else {
                                    if (key != fieldInfoName) {
                                        if (!writeAsArray) {
                                            out.writeFieldName(key, true);
                                        }
                                        serializer.write(propertyValue);
                                    } else if (originalValue != propertyValue) {
                                        if (!writeAsArray) {
                                            fieldSerializer.writePrefix(serializer);
                                        }
                                        serializer.write(propertyValue);
                                    } else {
                                        if (!writeAsArray && !fieldInfo.unwrapped) {
                                            if (directWritePrefix) {
                                                out.write(fieldInfo.name_chars, 0, fieldInfo.name_chars.length);
                                            } else {
                                                fieldSerializer.writePrefix(serializer);
                                            }
                                        }
                                        if (!writeAsArray) {
                                            JSONField fieldAnnotation2 = fieldInfo.getAnnotation();
                                            if (fieldClass != String.class && (fieldAnnotation2 == null || fieldAnnotation2.serializeUsing() == Void.class)) {
                                                if (propertyValue == null) {
                                                    if ((out.features & SerializerFeature.WriteNullStringAsEmpty.mask) != 0 || (fieldSerializer.features & SerializerFeature.WriteNullStringAsEmpty.mask) != 0) {
                                                        out.writeString(Constants.MAIN_VERSION_TAG);
                                                    } else {
                                                        out.writeNull();
                                                    }
                                                } else {
                                                    String propertyValueString = (String) propertyValue;
                                                    if (out.useSingleQuotes) {
                                                        out.writeStringWithSingleQuote(propertyValueString);
                                                    } else {
                                                        out.writeStringWithDoubleQuote(propertyValueString, (char) 0);
                                                    }
                                                }
                                            } else if (!fieldInfo.unwrapped && (propertyValue instanceof Map) && ((Map) propertyValue).size() == 0) {
                                                commaFlag2 = false;
                                            } else {
                                                fieldSerializer.writeValue(serializer, propertyValue);
                                            }
                                        } else {
                                            fieldSerializer.writeValue(serializer, propertyValue);
                                        }
                                    }
                                    fieldUnwrappedNull = false;
                                    if (fieldInfo.unwrapped && (propertyValue instanceof Map)) {
                                        map = (Map) propertyValue;
                                        if (map.size() == 0) {
                                            fieldUnwrappedNull = true;
                                        } else if (!serializer.isEnabled(SerializerFeature.WriteMapNullValue)) {
                                            hasNotNull = false;
                                            while (r4.hasNext()) {
                                                if (value != null) {
                                                    hasNotNull = true;
                                                    break;
                                                }
                                            }
                                            if (!hasNotNull) {
                                                fieldUnwrappedNull = true;
                                            }
                                        }
                                    }
                                    if (!fieldUnwrappedNull) {
                                        commaFlag2 = true;
                                    }
                                }
                            }
                        }
                    }
                }
                writeAfter(serializer, object, commaFlag2 ? ',' : (char) 0);
                if (getters.length > 0 && out.isEnabled(SerializerFeature.PrettyFormat)) {
                    serializer.decrementIdent();
                    serializer.println();
                }
                if (!unwrapped) {
                    out.append(endSeperator);
                }
                serializer.context = parent;
            } catch (Throwable th) {
                serializer.context = parent;
                throw th;
            }
        }
    }

    protected void writeClassName(JSONSerializer serializer, String typeKey, Object object) {
        if (typeKey == null) {
            typeKey = serializer.config.typeKey;
        }
        serializer.out.writeFieldName(typeKey, false);
        String typeName = this.beanInfo.typeName;
        if (typeName == null) {
            Class<?> clazz = object.getClass();
            if (TypeUtils.isProxy(clazz)) {
                clazz = clazz.getSuperclass();
            }
            typeName = clazz.getName();
        }
        serializer.write(typeName);
    }

    public boolean writeReference(JSONSerializer serializer, Object object, int fieldFeatures) {
        SerialContext context = serializer.context;
        int mask = SerializerFeature.DisableCircularReferenceDetect.mask;
        if (context == null || (context.features & mask) != 0 || (fieldFeatures & mask) != 0 || serializer.references == null || !serializer.references.containsKey(object)) {
            return false;
        }
        serializer.writeReference(object);
        return true;
    }

    protected boolean isWriteAsArray(JSONSerializer serializer, int fieldFeatrues) {
        int mask = SerializerFeature.BeanToArray.mask;
        return ((this.beanInfo.features & mask) == 0 && !serializer.out.beanToArray && (fieldFeatrues & mask) == 0) ? false : true;
    }

    public Object getFieldValue(Object object, String key, long keyHash, boolean throwFieldNotFoundException) {
        FieldSerializer fieldDeser = getFieldSerializer(keyHash);
        if (fieldDeser == null) {
            if (throwFieldNotFoundException) {
                throw new JSONException("field not found. " + key);
            }
            return null;
        }
        try {
            return fieldDeser.getPropertyValue(object);
        } catch (IllegalAccessException ex) {
            throw new JSONException("getFieldValue error." + key, ex);
        } catch (InvocationTargetException ex2) {
            throw new JSONException("getFieldValue error." + key, ex2);
        }
    }

    public FieldSerializer getFieldSerializer(String key) {
        if (key == null) {
            return null;
        }
        int low = 0;
        int high = this.sortedGetters.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            String fieldName = this.sortedGetters[mid].fieldInfo.name;
            int cmp = fieldName.compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return this.sortedGetters[mid];
            }
        }
        return null;
    }

    public FieldSerializer getFieldSerializer(long hash) {
        int p_t;
        PropertyNamingStrategy[] namingStrategies = null;
        if (this.hashArray == null) {
            namingStrategies = PropertyNamingStrategy.values();
            long[] hashArray = new long[this.sortedGetters.length * namingStrategies.length];
            int index = 0;
            for (int i = 0; i < this.sortedGetters.length; i++) {
                String name = this.sortedGetters[i].fieldInfo.name;
                hashArray[index] = TypeUtils.fnv1a_64(name);
                index++;
                for (PropertyNamingStrategy propertyNamingStrategy : namingStrategies) {
                    String name_t = propertyNamingStrategy.translate(name);
                    if (!name.equals(name_t)) {
                        hashArray[index] = TypeUtils.fnv1a_64(name_t);
                        index++;
                    }
                }
            }
            Arrays.sort(hashArray, 0, index);
            this.hashArray = new long[index];
            System.arraycopy(hashArray, 0, this.hashArray, 0, index);
        }
        int pos = Arrays.binarySearch(this.hashArray, hash);
        if (pos < 0) {
            return null;
        }
        if (this.hashArrayMapping == null) {
            if (namingStrategies == null) {
                namingStrategies = PropertyNamingStrategy.values();
            }
            short[] mapping = new short[this.hashArray.length];
            Arrays.fill(mapping, (short) -1);
            for (int i2 = 0; i2 < this.sortedGetters.length; i2++) {
                String name2 = this.sortedGetters[i2].fieldInfo.name;
                int p = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(name2));
                if (p >= 0) {
                    mapping[p] = (short) i2;
                }
                for (PropertyNamingStrategy propertyNamingStrategy2 : namingStrategies) {
                    String name_t2 = propertyNamingStrategy2.translate(name2);
                    if (!name2.equals(name_t2) && (p_t = Arrays.binarySearch(this.hashArray, TypeUtils.fnv1a_64(name_t2))) >= 0) {
                        mapping[p_t] = (short) i2;
                    }
                }
            }
            this.hashArrayMapping = mapping;
        }
        short s = this.hashArrayMapping[pos];
        if (s != -1) {
            return this.sortedGetters[s];
        }
        return null;
    }

    public List<Object> getFieldValues(Object object) throws Exception {
        List<Object> fieldValues = new ArrayList<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            fieldValues.add(getter.getPropertyValue(object));
        }
        return fieldValues;
    }

    public int getSize(Object object) throws Exception {
        int size = 0;
        for (FieldSerializer getter : this.sortedGetters) {
            Object value = getter.getPropertyValueDirect(object);
            if (value != null) {
                size++;
            }
        }
        return size;
    }

    public Map<String, Object> getFieldValuesMap(Object object) throws Exception {
        Map<String, Object> map = new LinkedHashMap<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            map.put(getter.fieldInfo.name, getter.getPropertyValue(object));
        }
        return map;
    }

    protected char writeBefore(JSONSerializer jsonBeanDeser, Object object, char seperator) {
        if (jsonBeanDeser.beforeFilters != null) {
            for (BeforeFilter beforeFilter : jsonBeanDeser.beforeFilters) {
                seperator = beforeFilter.writeBefore(jsonBeanDeser, object, seperator);
            }
        }
        if (this.beforeFilters != null) {
            for (BeforeFilter beforeFilter2 : this.beforeFilters) {
                seperator = beforeFilter2.writeBefore(jsonBeanDeser, object, seperator);
            }
        }
        return seperator;
    }

    protected char writeAfter(JSONSerializer jsonBeanDeser, Object object, char seperator) {
        if (jsonBeanDeser.afterFilters != null) {
            for (AfterFilter afterFilter : jsonBeanDeser.afterFilters) {
                seperator = afterFilter.writeAfter(jsonBeanDeser, object, seperator);
            }
        }
        if (this.afterFilters != null) {
            for (AfterFilter afterFilter2 : this.afterFilters) {
                seperator = afterFilter2.writeAfter(jsonBeanDeser, object, seperator);
            }
        }
        return seperator;
    }

    protected boolean applyLabel(JSONSerializer jsonBeanDeser, String label) {
        if (jsonBeanDeser.labelFilters != null) {
            for (LabelFilter propertyFilter : jsonBeanDeser.labelFilters) {
                if (!propertyFilter.apply(label)) {
                    return false;
                }
            }
        }
        if (this.labelFilters != null) {
            for (LabelFilter propertyFilter2 : this.labelFilters) {
                if (!propertyFilter2.apply(label)) {
                    return false;
                }
            }
        }
        return true;
    }
}
