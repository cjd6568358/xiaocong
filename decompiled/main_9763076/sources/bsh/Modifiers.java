package bsh;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.Serializable;
import java.util.Hashtable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Modifiers implements Serializable {
    public static final int CLASS = 0;
    public static final int FIELD = 2;
    public static final int METHOD = 1;
    Hashtable modifiers;

    private void insureNo(String str, String str2) {
        if (hasModifier(str)) {
            throw new IllegalStateException(new StringBuffer().append(str2).append(" cannot be declared '").append(str).append("'").toString());
        }
    }

    private void validateForClass() {
        validateForMethod();
        insureNo("native", "Class");
        insureNo("synchronized", "Class");
    }

    private void validateForField() {
        insureNo("synchronized", "Variable");
        insureNo("native", "Variable");
        insureNo("abstract", "Variable");
    }

    private void validateForMethod() {
        insureNo("volatile", "Method");
        insureNo("transient", "Method");
    }

    public void addModifier(int i, String str) {
        if (this.modifiers == null) {
            this.modifiers = new Hashtable();
        }
        if (this.modifiers.put(str, Void.TYPE) != null) {
            throw new IllegalStateException(new StringBuffer().append("Duplicate modifier: ").append(str).toString());
        }
        int i2 = hasModifier(PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE) ? 1 : 0;
        if (hasModifier("protected")) {
            i2++;
        }
        if (hasModifier("public")) {
            i2++;
        }
        if (i2 > 1) {
            throw new IllegalStateException("public/private/protected cannot be used in combination.");
        }
        switch (i) {
            case 0:
                validateForClass();
                return;
            case 1:
                validateForMethod();
                return;
            case 2:
                validateForField();
                return;
            default:
                return;
        }
    }

    public boolean hasModifier(String str) {
        if (this.modifiers == null) {
            this.modifiers = new Hashtable();
        }
        return this.modifiers.get(str) != null;
    }

    public String toString() {
        return new StringBuffer().append("Modifiers: ").append(this.modifiers).toString();
    }
}
