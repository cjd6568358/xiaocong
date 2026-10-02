package bsh;

import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class CommandLineReader extends FilterReader {
    static final int lastCharNL = 1;
    static final int normal = 0;
    static final int sentSemi = 2;
    int state;

    public CommandLineReader(Reader reader) {
        super(reader);
        this.state = 1;
    }

    public static void main(String[] strArr) throws Exception {
        while (true) {
            System.out.println(new CommandLineReader(new InputStreamReader(System.in)).read());
        }
    }

    @Override // java.io.FilterReader, java.io.Reader
    public int read() throws IOException {
        int i;
        if (this.state == 2) {
            this.state = 1;
            return 10;
        }
        do {
            i = this.in.read();
        } while (i == 13);
        if (i != 10) {
            this.state = 0;
            return i;
        }
        if (this.state == 1) {
            this.state = 2;
            return 59;
        }
        this.state = 1;
        return i;
    }

    @Override // java.io.FilterReader, java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3 = read();
        if (i3 == -1) {
            return -1;
        }
        cArr[i] = (char) i3;
        return 1;
    }
}
