package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ClassIdentifier {
    Class clas;

    public ClassIdentifier(Class cls) {
        this.clas = cls;
    }

    public Class getTargetClass() {
        return this.clas;
    }

    public String toString() {
        return new StringBuffer().append("Class Identifier: ").append(this.clas.getName()).toString();
    }
}
