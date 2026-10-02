package com.ta.utdid2.c.a;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: TransactionXMLFile.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private static final Object c = new Object();
    private File a;
    private final Object b = new Object();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private HashMap<File, a> f120a = new HashMap<>();

    public d(String str) {
        if (str != null && str.length() > 0) {
            this.a = new File(str);
            return;
        }
        throw new RuntimeException("Directory can not be empty");
    }

    private File a(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            return new File(file, str);
        }
        throw new IllegalArgumentException("File " + str + " contains a path separator");
    }

    private File a() {
        File file;
        synchronized (this.b) {
            file = this.a;
        }
        return file;
    }

    private File b(String str) {
        return a(a(), String.valueOf(str) + ".xml");
    }

    /* JADX WARN: Code duplicated, block: B:138:0x0081 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:142:0x00a9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:146:0x0054 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:28:0x0056 A[Catch: all -> 0x005b, TRY_ENTER, TryCatch #24 {all -> 0x005b, blocks: (B:28:0x0056, B:29:0x0059, B:97:0x00dd, B:99:0x00e7), top: B:146:0x0054 }] */
    /* JADX WARN: Code duplicated, block: B:97:0x00dd A[Catch: all -> 0x005b, TRY_ENTER, TryCatch #24 {all -> 0x005b, blocks: (B:28:0x0056, B:29:0x0059, B:97:0x00dd, B:99:0x00e7), top: B:146:0x0054 }] */
    /* JADX WARN: Code duplicated, block: B:99:0x00e7 A[Catch: all -> 0x005b, TRY_LEAVE, TryCatch #24 {all -> 0x005b, blocks: (B:28:0x0056, B:29:0x0059, B:97:0x00dd, B:99:0x00e7), top: B:146:0x0054 }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v20 */
    public b a(String str, int i) throws Throwable {
        a aVar;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3;
        FileInputStream fileInputStream4;
        FileInputStream fileInputStream5;
        IOException e;
        FileNotFoundException e2;
        HashMap mapA = null;
        File fileB = b(str);
        synchronized (c) {
            aVar = this.f120a.get(fileB);
            if (aVar == null || aVar.c()) {
                File fileA = a(fileB);
                ?? Exists = fileA.exists();
                if (Exists != 0) {
                    fileB.delete();
                    fileA.renameTo(fileB);
                }
                if (fileB.exists()) {
                    fileB.canRead();
                }
                if (fileB.exists()) {
                    try {
                        if (fileB.canRead()) {
                            try {
                                fileInputStream2 = new FileInputStream(fileB);
                                try {
                                    mapA = e.a(fileInputStream2);
                                    fileInputStream2.close();
                                    if (fileInputStream2 != null) {
                                        try {
                                            fileInputStream2.close();
                                        } catch (Throwable th) {
                                        }
                                    }
                                } catch (FileNotFoundException e3) {
                                    fileInputStream4 = fileInputStream2;
                                    e = e3;
                                    e.printStackTrace();
                                    if (fileInputStream4 != null) {
                                        try {
                                            fileInputStream4.close();
                                        } catch (Throwable th2) {
                                        }
                                    }
                                } catch (IOException e4) {
                                    fileInputStream3 = fileInputStream2;
                                    e = e4;
                                    e.printStackTrace();
                                    if (fileInputStream3 != null) {
                                        try {
                                            fileInputStream3.close();
                                        } catch (Throwable th3) {
                                        }
                                    }
                                } catch (XmlPullParserException e5) {
                                    try {
                                        fileInputStream5 = new FileInputStream(fileB);
                                        try {
                                            try {
                                                byte[] bArr = new byte[fileInputStream5.available()];
                                                fileInputStream5.read(bArr);
                                                new String(bArr, 0, bArr.length, HTTP.UTF_8);
                                                if (fileInputStream5 != null) {
                                                    try {
                                                        fileInputStream5.close();
                                                    } catch (Throwable th4) {
                                                    }
                                                }
                                            } catch (Throwable th5) {
                                                th = th5;
                                                if (fileInputStream5 != null) {
                                                    try {
                                                        fileInputStream5.close();
                                                    } catch (Throwable th6) {
                                                    }
                                                }
                                                throw th;
                                            }
                                        } catch (FileNotFoundException e6) {
                                            e2 = e6;
                                            e2.printStackTrace();
                                            if (fileInputStream5 != null) {
                                                try {
                                                    fileInputStream5.close();
                                                } catch (Throwable th7) {
                                                }
                                            }
                                            if (fileInputStream5 != null) {
                                                try {
                                                    fileInputStream5.close();
                                                } catch (Throwable th8) {
                                                }
                                            }
                                            synchronized (c) {
                                                try {
                                                    if (aVar != null) {
                                                        aVar.a(mapA);
                                                    } else {
                                                        aVar = this.f120a.get(fileB);
                                                        if (aVar == null) {
                                                            aVar = new a(fileB, i, mapA);
                                                            this.f120a.put(fileB, aVar);
                                                        }
                                                    }
                                                    return aVar;
                                                } catch (Throwable th9) {
                                                    throw th9;
                                                }
                                            }
                                        } catch (IOException e7) {
                                            e = e7;
                                            e.printStackTrace();
                                            if (fileInputStream5 != null) {
                                                try {
                                                    fileInputStream5.close();
                                                } catch (Throwable th10) {
                                                }
                                            }
                                            if (fileInputStream5 != null) {
                                                fileInputStream5.close();
                                            }
                                            synchronized (c) {
                                                if (aVar != null) {
                                                    aVar.a(mapA);
                                                } else {
                                                    aVar = this.f120a.get(fileB);
                                                    if (aVar == null) {
                                                        aVar = new a(fileB, i, mapA);
                                                        this.f120a.put(fileB, aVar);
                                                    }
                                                }
                                                return aVar;
                                            }
                                        }
                                    } catch (FileNotFoundException e8) {
                                        fileInputStream5 = fileInputStream2;
                                        e2 = e8;
                                    } catch (IOException e9) {
                                        fileInputStream5 = fileInputStream2;
                                        e = e9;
                                    } catch (Throwable th11) {
                                        th = th11;
                                        fileInputStream5 = fileInputStream2;
                                        if (fileInputStream5 != null) {
                                            fileInputStream5.close();
                                        }
                                        throw th;
                                    }
                                    if (fileInputStream5 != null) {
                                        fileInputStream5.close();
                                    }
                                } catch (Exception e10) {
                                    fileInputStream = fileInputStream2;
                                    e = e10;
                                    e.printStackTrace();
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (Throwable th12) {
                                        }
                                    }
                                } catch (Throwable th13) {
                                    th = th13;
                                    Exists = fileInputStream2;
                                    if (Exists != 0) {
                                        try {
                                            Exists.close();
                                        } catch (Throwable th14) {
                                        }
                                    }
                                    throw th;
                                }
                            } catch (FileNotFoundException e11) {
                                e = e11;
                                fileInputStream4 = null;
                            } catch (IOException e12) {
                                e = e12;
                                fileInputStream3 = null;
                            } catch (XmlPullParserException e13) {
                                fileInputStream2 = null;
                            } catch (Exception e14) {
                                e = e14;
                                fileInputStream = null;
                            } catch (Throwable th15) {
                                th = th15;
                                Exists = 0;
                            }
                        }
                    } catch (Throwable th16) {
                        th = th16;
                    }
                }
                synchronized (c) {
                    if (aVar != null) {
                        aVar.a(mapA);
                    } else {
                        aVar = this.f120a.get(fileB);
                        if (aVar == null) {
                            aVar = new a(fileB, i, mapA);
                            this.f120a.put(fileB, aVar);
                        }
                    }
                }
            }
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static File a(File file) {
        return new File(String.valueOf(file.getPath()) + ".bak");
    }

    /* JADX INFO: compiled from: TransactionXMLFile.java */
    private static final class a implements b {
        private static final Object d = new Object();
        private WeakHashMap<b.InterfaceC0038b, Object> a;
        private final File b;
        private final int c;

        /* JADX INFO: renamed from: c, reason: collision with other field name */
        private final File f121c;

        /* JADX INFO: renamed from: c, reason: collision with other field name */
        private Map f122c;
        private boolean k = false;

        a(File file, int i, Map map) {
            this.b = file;
            this.f121c = d.a(file);
            this.c = i;
            this.f122c = map == null ? new HashMap() : map;
            this.a = new WeakHashMap<>();
        }

        @Override // com.ta.utdid2.c.a.b
        /* JADX INFO: renamed from: a */
        public boolean mo100a() {
            return this.b != null && new File(this.b.getAbsolutePath()).exists();
        }

        public void a(boolean z) {
            synchronized (this) {
                this.k = z;
            }
        }

        public boolean c() {
            boolean z;
            synchronized (this) {
                z = this.k;
            }
            return z;
        }

        public void a(Map map) {
            if (map != null) {
                synchronized (this) {
                    this.f122c = map;
                }
            }
        }

        @Override // com.ta.utdid2.c.a.b
        public Map<String, ?> getAll() {
            HashMap map;
            synchronized (this) {
                map = new HashMap(this.f122c);
            }
            return map;
        }

        @Override // com.ta.utdid2.c.a.b
        public String getString(String key, String defValue) {
            String str;
            synchronized (this) {
                str = (String) this.f122c.get(key);
                if (str == null) {
                    str = defValue;
                }
            }
            return str;
        }

        @Override // com.ta.utdid2.c.a.b
        public long getLong(String key, long defValue) {
            synchronized (this) {
                Long l = (Long) this.f122c.get(key);
                if (l != null) {
                    defValue = l.longValue();
                }
            }
            return defValue;
        }

        /* JADX INFO: renamed from: com.ta.utdid2.c.a.d$a$a, reason: collision with other inner class name */
        /* JADX INFO: compiled from: TransactionXMLFile.java */
        public final class C0039a implements b.a {
            private final Map<String, Object> d = new HashMap();
            private boolean l = false;

            public C0039a() {
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, String str2) {
                synchronized (this) {
                    this.d.put(str, str2);
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, int i) {
                synchronized (this) {
                    this.d.put(str, Integer.valueOf(i));
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, long j) {
                synchronized (this) {
                    this.d.put(str, Long.valueOf(j));
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, float f) {
                synchronized (this) {
                    this.d.put(str, Float.valueOf(f));
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, boolean z) {
                synchronized (this) {
                    this.d.put(str, Boolean.valueOf(z));
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str) {
                synchronized (this) {
                    this.d.put(str, this);
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a b() {
                synchronized (this) {
                    this.l = true;
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public boolean commit() {
                boolean z;
                HashSet<b.InterfaceC0038b> hashSet;
                ArrayList arrayList;
                boolean zD;
                synchronized (d.c) {
                    z = a.this.a.size() > 0;
                    if (z) {
                        ArrayList arrayList2 = new ArrayList();
                        hashSet = new HashSet(a.this.a.keySet());
                        arrayList = arrayList2;
                    } else {
                        hashSet = null;
                        arrayList = null;
                    }
                    synchronized (this) {
                        if (this.l) {
                            a.this.f122c.clear();
                            this.l = false;
                        }
                        for (Map.Entry<String, Object> entry : this.d.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            if (value == this) {
                                a.this.f122c.remove(key);
                            } else {
                                a.this.f122c.put(key, value);
                            }
                            if (z) {
                                arrayList.add(key);
                            }
                        }
                        this.d.clear();
                    }
                    zD = a.this.d();
                    if (zD) {
                        a.this.a(true);
                    }
                }
                if (z) {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        String str = (String) arrayList.get(size);
                        for (b.InterfaceC0038b interfaceC0038b : hashSet) {
                            if (interfaceC0038b != null) {
                                interfaceC0038b.a(a.this, str);
                            }
                        }
                    }
                }
                return zD;
            }
        }

        @Override // com.ta.utdid2.c.a.b
        public b.a a() {
            return new C0039a();
        }

        private FileOutputStream a(File file) {
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                if (!file.getParentFile().mkdir()) {
                    return null;
                }
                try {
                    return new FileOutputStream(file);
                } catch (FileNotFoundException e2) {
                    return null;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean d() {
            if (this.b.exists()) {
                if (!this.f121c.exists()) {
                    if (!this.b.renameTo(this.f121c)) {
                        return false;
                    }
                } else {
                    this.b.delete();
                }
            }
            try {
                FileOutputStream fileOutputStreamA = a(this.b);
                if (fileOutputStreamA == null) {
                    return false;
                }
                e.a(this.f122c, fileOutputStreamA);
                fileOutputStreamA.close();
                this.f121c.delete();
                return true;
            } catch (IOException | XmlPullParserException e) {
                if (!this.b.exists()) {
                    return false;
                }
                this.b.delete();
                return false;
            }
        }
    }
}
