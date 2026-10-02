package com.xiaomi.push.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import com.xiaomi.push.service.at;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class a {
    private static String b = "/MiPushLog";
    private String c;
    private String d;
    private boolean e;
    private int f;

    @SuppressLint({"SimpleDateFormat"})
    private final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int g = 2097152;
    private ArrayList<File> h = new ArrayList<>();

    a() {
    }

    private void a(BufferedReader bufferedReader, BufferedWriter bufferedWriter, Pattern pattern) throws IOException {
        int iStart;
        boolean z;
        char[] cArr = new char[4096];
        int i = bufferedReader.read(cArr);
        boolean z2 = false;
        while (i != -1 && !z2) {
            String str = new String(cArr, 0, i);
            Matcher matcher = pattern.matcher(str);
            int length = 0;
            int i2 = 0;
            while (true) {
                if (length >= i || !matcher.find(length)) {
                    iStart = i;
                    z = z2;
                    break;
                }
                iStart = matcher.start();
                String strSubstring = str.substring(iStart, this.c.length() + iStart);
                if (this.e) {
                    if (strSubstring.compareTo(this.d) > 0) {
                        z = true;
                        break;
                    }
                } else if (strSubstring.compareTo(this.c) >= 0) {
                    this.e = true;
                    i2 = iStart;
                }
                int iIndexOf = str.indexOf(10, iStart);
                length = iIndexOf != -1 ? iStart + iIndexOf : iStart + this.c.length();
            }
            if (this.e) {
                int i3 = iStart - i2;
                this.f += i3;
                if (z) {
                    bufferedWriter.write(cArr, i2, i3);
                    return;
                } else {
                    bufferedWriter.write(cArr, i2, i3);
                    if (this.f > this.g) {
                        return;
                    }
                }
            }
            z2 = z;
            i = bufferedReader.read(cArr);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.Reader] */
    /* JADX WARN: Type inference failed for: r2v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.Reader] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.Reader] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22, types: [java.io.Reader] */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r8v0, types: [com.xiaomi.push.log.a] */
    private void b(File file) throws Throwable {
        BufferedWriter bufferedWriter;
        ?? r2 = 0;
        ?? r3 = 0;
        bufferedReader = 0;
        bufferedReader = 0;
        r2 = 0;
        bufferedReader = 0;
        ?? bufferedReader = 0;
        Pattern patternCompile = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        try {
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("model :").append(Build.MODEL);
                    sb.append("; os :").append(Build.VERSION.INCREMENTAL);
                    sb.append("; uid :").append(at.e());
                    sb.append("; lng :").append(Locale.getDefault().toString());
                    sb.append("; sdk :").append(26);
                    sb.append("; andver :").append(Build.VERSION.SDK_INT);
                    sb.append("\n");
                    bufferedWriter.write(sb.toString());
                    this.f = 0;
                    Iterator<File> it = this.h.iterator();
                    ?? r4 = "\n";
                    while (true) {
                        try {
                            r4 = r3;
                            if (!it.hasNext()) {
                                com.xiaomi.channel.commonutils.file.a.a(bufferedWriter);
                                com.xiaomi.channel.commonutils.file.a.a((Reader) r4);
                                return;
                            } else {
                                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(it.next())));
                                a(bufferedReader, bufferedWriter, patternCompile);
                                bufferedReader.close();
                                r3 = bufferedReader;
                                r4 = r4;
                            }
                        } catch (FileNotFoundException e) {
                            e = e;
                            bufferedReader = r4;
                            com.xiaomi.channel.commonutils.logger.b.c("LOG: filter error = " + e.getMessage());
                            com.xiaomi.channel.commonutils.file.a.a(bufferedWriter);
                            com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
                            return;
                        } catch (IOException e2) {
                            e = e2;
                            bufferedReader = r4;
                            com.xiaomi.channel.commonutils.logger.b.c("LOG: filter error = " + e.getMessage());
                            com.xiaomi.channel.commonutils.file.a.a(bufferedWriter);
                            com.xiaomi.channel.commonutils.file.a.a((Reader) bufferedReader);
                            return;
                        } catch (Throwable th) {
                            th = th;
                            r2 = r4;
                            com.xiaomi.channel.commonutils.file.a.a(bufferedWriter);
                            com.xiaomi.channel.commonutils.file.a.a((Reader) r2);
                            throw th;
                        }
                    }
                } catch (FileNotFoundException e3) {
                    e = e3;
                } catch (IOException e4) {
                    e = e4;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            bufferedWriter = null;
        } catch (IOException e6) {
            e = e6;
            bufferedWriter = null;
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter = null;
        }
    }

    a a(File file) {
        if (file.exists()) {
            this.h.add(file);
        }
        return this;
    }

    a a(Date date, Date date2) {
        if (date.after(date2)) {
            this.c = this.a.format(date2);
            this.d = this.a.format(date);
        } else {
            this.c = this.a.format(date);
            this.d = this.a.format(date2);
        }
        return this;
    }

    File a(Context context, Date date, Date date2, File file) throws Throwable {
        File file2;
        if ("com.xiaomi.xmsf".equalsIgnoreCase(context.getPackageName())) {
            file2 = context.getFilesDir();
            a(new File(file2, "xmsf.log.1"));
            a(new File(file2, "xmsf.log"));
        } else {
            file2 = new File(context.getExternalFilesDir(null) + b);
            a(new File(file2, "log0.txt"));
            a(new File(file2, "log1.txt"));
        }
        if (!file2.isDirectory()) {
            return null;
        }
        File file3 = new File(file, date.getTime() + "-" + date2.getTime() + ".zip");
        if (file3.exists()) {
            return null;
        }
        a(date, date2);
        long jCurrentTimeMillis = System.currentTimeMillis();
        File file4 = new File(file, "log.txt");
        b(file4);
        com.xiaomi.channel.commonutils.logger.b.c("LOG: filter cost = " + (System.currentTimeMillis() - jCurrentTimeMillis));
        if (file4.exists()) {
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            com.xiaomi.channel.commonutils.file.a.a(file3, file4);
            com.xiaomi.channel.commonutils.logger.b.c("LOG: zip cost = " + (System.currentTimeMillis() - jCurrentTimeMillis2));
            file4.delete();
            if (file3.exists()) {
                return file3;
            }
        }
        return null;
    }

    void a(int i) {
        if (i != 0) {
            this.g = i;
        }
    }
}
