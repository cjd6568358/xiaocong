package bsh;

import java.io.PrintStream;
import java.io.Reader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ConsoleInterface {
    void error(Object obj);

    PrintStream getErr();

    Reader getIn();

    PrintStream getOut();

    void print(Object obj);

    void println(Object obj);
}
