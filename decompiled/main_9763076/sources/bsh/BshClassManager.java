package bsh;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Hashtable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BshClassManager {
    private static Object NOVALUE = new Object();
    static Class class$bsh$Interpreter;
    private Interpreter declaringInterpreter;
    protected ClassLoader externalClassLoader;
    protected transient Hashtable absoluteClassCache = new Hashtable();
    protected transient Hashtable absoluteNonClasses = new Hashtable();
    protected transient Hashtable resolvedObjectMethods = new Hashtable();
    protected transient Hashtable resolvedStaticMethods = new Hashtable();
    protected transient Hashtable definingClasses = new Hashtable();
    protected transient Hashtable definingClassesBaseNames = new Hashtable();

    public interface Listener {
        void classLoaderChanged();
    }

    static class SignatureKey {
        Class clas;
        int hashCode = 0;
        String methodName;
        Class[] types;

        SignatureKey(Class cls, String str, Class[] clsArr) {
            this.clas = cls;
            this.methodName = str;
            this.types = clsArr;
        }

        public boolean equals(Object obj) {
            SignatureKey signatureKey = (SignatureKey) obj;
            if (this.types == null) {
                return signatureKey.types == null;
            }
            if (this.clas != signatureKey.clas || !this.methodName.equals(signatureKey.methodName) || this.types.length != signatureKey.types.length) {
                return false;
            }
            for (int i = 0; i < this.types.length; i++) {
                if (this.types[i] == null) {
                    if (signatureKey.types[i] != null) {
                        return false;
                    }
                } else if (!this.types[i].equals(signatureKey.types[i])) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                this.hashCode = this.clas.hashCode() * this.methodName.hashCode();
                if (this.types == null) {
                    return this.hashCode;
                }
                for (int i = 0; i < this.types.length; i++) {
                    this.hashCode = (this.types[i] == null ? 21 : this.types[i].hashCode()) + (this.hashCode * (i + 1));
                }
            }
            return this.hashCode;
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    protected static UtilEvalError cmUnavailable() {
        return new Capabilities.Unavailable("ClassLoading features unavailable.");
    }

    public static BshClassManager createClassManager(Interpreter interpreter) {
        BshClassManager bshClassManager;
        if (Capabilities.classExists("java.lang.ref.WeakReference") && Capabilities.classExists("java.util.HashMap") && Capabilities.classExists("bsh.classpath.ClassManagerImpl")) {
            try {
                bshClassManager = (BshClassManager) Class.forName("bsh.classpath.ClassManagerImpl").newInstance();
            } catch (Exception e) {
                throw new InterpreterError(new StringBuffer().append("Error loading classmanager: ").append(e).toString());
            }
        } else {
            bshClassManager = new BshClassManager();
        }
        if (interpreter == null) {
            interpreter = new Interpreter();
        }
        bshClassManager.declaringInterpreter = interpreter;
        return bshClassManager;
    }

    protected static Error noClassDefFound(String str, Error error) {
        return new NoClassDefFoundError(new StringBuffer().append("A class required by class: ").append(str).append(" could not be loaded:\n").append(error.toString()).toString());
    }

    public void addClassPath(URL url) throws IOException {
    }

    public void addListener(Listener listener) {
    }

    public void cacheClassInfo(String str, Class cls) {
        if (cls != null) {
            this.absoluteClassCache.put(str, cls);
        } else {
            this.absoluteNonClasses.put(str, NOVALUE);
        }
    }

    public void cacheResolvedMethod(Class cls, Class[] clsArr, Method method) {
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append("cacheResolvedMethod putting: ").append(cls).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(method).toString());
        }
        SignatureKey signatureKey = new SignatureKey(cls, method.getName(), clsArr);
        if (Modifier.isStatic(method.getModifiers())) {
            this.resolvedStaticMethods.put(signatureKey, method);
        } else {
            this.resolvedObjectMethods.put(signatureKey, method);
        }
    }

    public boolean classExists(String str) {
        return classForName(str) != null;
    }

    public Class classForName(String str) {
        if (isClassBeingDefined(str)) {
            throw new InterpreterError(new StringBuffer().append("Attempting to load class in the process of being defined: ").append(str).toString());
        }
        Class clsPlainClassForName = null;
        try {
            clsPlainClassForName = plainClassForName(str);
        } catch (ClassNotFoundException e) {
        }
        return clsPlainClassForName == null ? loadSourceClass(str) : clsPlainClassForName;
    }

    protected void classLoaderChanged() {
    }

    protected void clearCaches() {
        this.absoluteNonClasses = new Hashtable();
        this.absoluteClassCache = new Hashtable();
        this.resolvedObjectMethods = new Hashtable();
        this.resolvedStaticMethods = new Hashtable();
    }

    public Class defineClass(String str, byte[] bArr) {
        throw new InterpreterError(new StringBuffer().append("Can't create class (").append(str).append(") without class manager package.").toString());
    }

    protected void definingClass(String str) {
        String strSuffix = Name.suffix(str, 1);
        int iIndexOf = strSuffix.indexOf("$");
        String strSubstring = iIndexOf != -1 ? strSuffix.substring(iIndexOf + 1) : strSuffix;
        String str2 = (String) this.definingClassesBaseNames.get(strSubstring);
        if (str2 != null) {
            throw new InterpreterError(new StringBuffer().append("Defining class problem: ").append(str).append(": BeanShell cannot yet simultaneously define two or more ").append("dependant classes of the same name.  Attempt to define: ").append(str).append(" while defining: ").append(str2).toString());
        }
        this.definingClasses.put(str, NOVALUE);
        this.definingClassesBaseNames.put(strSubstring, str);
    }

    protected void doSuperImport() throws UtilEvalError {
        throw cmUnavailable();
    }

    protected void doneDefiningClass(String str) {
        String strSuffix = Name.suffix(str, 1);
        this.definingClasses.remove(str);
        this.definingClassesBaseNames.remove(strSuffix);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("BshClassManager: no class manager.");
    }

    protected String getClassBeingDefined(String str) {
        return (String) this.definingClassesBaseNames.get(Name.suffix(str, 1));
    }

    protected String getClassNameByUnqName(String str) throws UtilEvalError {
        throw cmUnavailable();
    }

    protected Method getResolvedMethod(Class cls, String str, Class[] clsArr, boolean z) {
        SignatureKey signatureKey = new SignatureKey(cls, str, clsArr);
        Method method = (Method) this.resolvedStaticMethods.get(signatureKey);
        if (method == null && !z) {
            method = (Method) this.resolvedObjectMethods.get(signatureKey);
        }
        if (Interpreter.DEBUG) {
            if (method == null) {
                Interpreter.debug(new StringBuffer().append("getResolvedMethod cache MISS: ").append(cls).append(" - ").append(str).toString());
            } else {
                Interpreter.debug(new StringBuffer().append("getResolvedMethod cache HIT: ").append(cls).append(" - ").append(method).toString());
            }
        }
        return method;
    }

    public URL getResource(String str) {
        Class clsClass$;
        URL resource = this.externalClassLoader != null ? this.externalClassLoader.getResource(str.substring(1)) : null;
        if (resource != null) {
            return resource;
        }
        if (class$bsh$Interpreter == null) {
            clsClass$ = class$("bsh.Interpreter");
            class$bsh$Interpreter = clsClass$;
        } else {
            clsClass$ = class$bsh$Interpreter;
        }
        return clsClass$.getResource(str);
    }

    public InputStream getResourceAsStream(String str) {
        Class clsClass$;
        InputStream resourceAsStream = this.externalClassLoader != null ? this.externalClassLoader.getResourceAsStream(str.substring(1)) : null;
        if (resourceAsStream != null) {
            return resourceAsStream;
        }
        if (class$bsh$Interpreter == null) {
            clsClass$ = class$("bsh.Interpreter");
            class$bsh$Interpreter = clsClass$;
        } else {
            clsClass$ = class$bsh$Interpreter;
        }
        return clsClass$.getResourceAsStream(str);
    }

    protected boolean hasSuperImport() {
        return false;
    }

    protected boolean isClassBeingDefined(String str) {
        return this.definingClasses.get(str) != null;
    }

    protected Class loadSourceClass(String str) {
        String string = new StringBuffer().append("/").append(str.replace('.', '/')).append(".java").toString();
        InputStream resourceAsStream = getResourceAsStream(string);
        if (resourceAsStream == null) {
            return null;
        }
        try {
            System.out.println(new StringBuffer().append("Loading class from source file: ").append(string).toString());
            this.declaringInterpreter.eval(new InputStreamReader(resourceAsStream));
        } catch (EvalError e) {
            System.err.println(e);
        }
        try {
            return plainClassForName(str);
        } catch (ClassNotFoundException e2) {
            System.err.println(new StringBuffer().append("Class not found in source file: ").append(str).toString());
            return null;
        }
    }

    public Class plainClassForName(String str) throws ClassNotFoundException {
        try {
            Class<?> clsLoadClass = this.externalClassLoader != null ? this.externalClassLoader.loadClass(str) : Class.forName(str);
            cacheClassInfo(str, clsLoadClass);
            return clsLoadClass;
        } catch (NoClassDefFoundError e) {
            throw noClassDefFound(str, e);
        }
    }

    public void reloadAllClasses() throws UtilEvalError {
        throw cmUnavailable();
    }

    public void reloadClasses(String[] strArr) throws UtilEvalError {
        throw cmUnavailable();
    }

    public void reloadPackage(String str) throws UtilEvalError {
        throw cmUnavailable();
    }

    public void removeListener(Listener listener) {
    }

    public void reset() {
        clearCaches();
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.externalClassLoader = classLoader;
        classLoaderChanged();
    }

    public void setClassPath(URL[] urlArr) throws UtilEvalError {
        throw cmUnavailable();
    }
}
