package com.tencent.mid.b;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public static File a(String str) {
        File file = new File(str);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                a(file.getParentFile().getAbsolutePath());
            }
            file.mkdir();
        }
        return file;
    }

    public static List<String> a(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                fileReader.close();
                bufferedReader.close();
                return arrayList;
            }
            arrayList.add(line.trim());
        }
    }
}
