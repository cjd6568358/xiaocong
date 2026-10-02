package bsh;

import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NameSpace implements BshClassManager.Listener, NameSource, Serializable {
    public static final NameSpace JAVACODE = new NameSpace((BshClassManager) null, "Called from compiled Java code.");
    SimpleNode callerInfoNode;
    private transient Hashtable classCache;
    Object classInstance;
    private transient BshClassManager classManager;
    Class classStatic;
    protected Hashtable importedClasses;
    private Vector importedCommands;
    private Vector importedObjects;
    private Vector importedPackages;
    private Vector importedStatic;
    boolean isClass;
    boolean isMethod;
    private Hashtable methods;
    Vector nameSourceListeners;
    private Hashtable names;
    private String nsName;
    private String packageName;
    private NameSpace parent;
    private This thisReference;
    private Hashtable variables;

    static {
        JAVACODE.isMethod = true;
    }

    public NameSpace(BshClassManager bshClassManager, String str) {
        this(null, bshClassManager, str);
    }

    public NameSpace(NameSpace nameSpace, BshClassManager bshClassManager, String str) {
        setName(str);
        setParent(nameSpace);
        setClassManager(bshClassManager);
        if (bshClassManager != null) {
            bshClassManager.addListener(this);
        }
    }

    public NameSpace(NameSpace nameSpace, String str) {
        this(nameSpace, null, str);
    }

    private Class classForName(String str) {
        return getClassManager().classForName(str);
    }

    private String[] enumerationToStringArray(Enumeration enumeration) {
        Vector vector = new Vector();
        while (enumeration.hasMoreElements()) {
            vector.addElement(enumeration.nextElement());
        }
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }

    private BshMethod[] flattenMethodCollection(Enumeration enumeration) {
        Vector vector = new Vector();
        while (enumeration.hasMoreElements()) {
            Object objNextElement = enumeration.nextElement();
            if (objNextElement instanceof BshMethod) {
                vector.addElement(objNextElement);
            } else {
                Vector vector2 = (Vector) objNextElement;
                for (int i = 0; i < vector2.size(); i++) {
                    vector.addElement(vector2.elementAt(i));
                }
            }
        }
        BshMethod[] bshMethodArr = new BshMethod[vector.size()];
        vector.copyInto(bshMethodArr);
        return bshMethodArr;
    }

    private Class getClassImpl(String str) throws UtilEvalError {
        Class importedClassImpl;
        if (this.classCache != null) {
            importedClassImpl = (Class) this.classCache.get(str);
            if (importedClassImpl != null) {
                return importedClassImpl;
            }
        } else {
            importedClassImpl = null;
        }
        boolean z = !Name.isCompound(str);
        if (z) {
            if (importedClassImpl == null) {
                importedClassImpl = getImportedClassImpl(str);
            }
            if (importedClassImpl != null) {
                cacheClass(str, importedClassImpl);
                return importedClassImpl;
            }
        }
        Class clsClassForName = classForName(str);
        if (clsClassForName == null) {
            if (Interpreter.DEBUG) {
                Interpreter.debug(new StringBuffer().append("getClass(): ").append(str).append(" not\tfound in ").append(this).toString());
            }
            return null;
        }
        if (!z) {
            return clsClassForName;
        }
        cacheClass(str, clsClassForName);
        return clsClassForName;
    }

    private Class getImportedClassImpl(String str) throws UtilEvalError {
        String classNameByUnqName;
        String str2 = this.importedClasses != null ? (String) this.importedClasses.get(str) : null;
        if (str2 != null) {
            Class clsClassForName = classForName(str2);
            if (clsClassForName != null) {
                return clsClassForName;
            }
            if (Name.isCompound(str2)) {
                try {
                    clsClassForName = getNameResolver(str2).toClass();
                } catch (ClassNotFoundException e) {
                }
            } else if (Interpreter.DEBUG) {
                Interpreter.debug(new StringBuffer().append("imported unpackaged name not found:").append(str2).toString());
            }
            if (clsClassForName == null) {
                return null;
            }
            getClassManager().cacheClassInfo(str2, clsClassForName);
            return clsClassForName;
        }
        if (this.importedPackages != null) {
            for (int size = this.importedPackages.size() - 1; size >= 0; size--) {
                Class clsClassForName2 = classForName(new StringBuffer().append((String) this.importedPackages.elementAt(size)).append(".").append(str).toString());
                if (clsClassForName2 != null) {
                    return clsClassForName2;
                }
            }
        }
        BshClassManager classManager = getClassManager();
        if (!classManager.hasSuperImport() || (classNameByUnqName = classManager.getClassNameByUnqName(str)) == null) {
            return null;
        }
        return classForName(classNameByUnqName);
    }

    public static Class identifierToClass(ClassIdentifier classIdentifier) {
        return classIdentifier.getTargetClass();
    }

    private BshMethod loadScriptedCommand(InputStream inputStream, String str, Class[] clsArr, String str2, Interpreter interpreter) throws UtilEvalError {
        try {
            interpreter.eval(new InputStreamReader(inputStream), this, str2);
            return getMethod(str, clsArr);
        } catch (EvalError e) {
            Interpreter.debug(e.toString());
            throw new UtilEvalError(new StringBuffer().append("Error loading script: ").append(e.getMessage()).toString());
        }
    }

    private synchronized void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.names = null;
        objectOutputStream.defaultWriteObject();
    }

    @Override // bsh.NameSource
    public void addNameSourceListener(NameSource.Listener listener) {
        if (this.nameSourceListeners == null) {
            this.nameSourceListeners = new Vector();
        }
        this.nameSourceListeners.addElement(listener);
    }

    void cacheClass(String str, Class cls) {
        if (this.classCache == null) {
            this.classCache = new Hashtable();
        }
        this.classCache.put(str, cls);
    }

    @Override // bsh.BshClassManager.Listener
    public void classLoaderChanged() {
        nameSpaceChanged();
    }

    public void clear() {
        this.variables = null;
        this.methods = null;
        this.importedClasses = null;
        this.importedPackages = null;
        this.importedCommands = null;
        this.importedObjects = null;
        if (this.parent == null) {
            loadDefaultImports();
        }
        this.classCache = null;
        this.names = null;
    }

    public void doSuperImport() throws UtilEvalError {
        getClassManager().doSuperImport();
    }

    public Object get(String str, Interpreter interpreter) throws UtilEvalError {
        return getNameResolver(str).toObject(new CallStack(this), interpreter);
    }

    @Override // bsh.NameSource
    public String[] getAllNames() {
        Vector vector = new Vector();
        getAllNamesAux(vector);
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }

    protected void getAllNamesAux(Vector vector) {
        Enumeration enumerationKeys = this.variables.keys();
        while (enumerationKeys.hasMoreElements()) {
            vector.addElement(enumerationKeys.nextElement());
        }
        Enumeration enumerationKeys2 = this.methods.keys();
        while (enumerationKeys2.hasMoreElements()) {
            vector.addElement(enumerationKeys2.nextElement());
        }
        if (this.parent != null) {
            this.parent.getAllNamesAux(vector);
        }
    }

    public Class getClass(String str) throws UtilEvalError {
        Class classImpl = getClassImpl(str);
        if (classImpl != null) {
            return classImpl;
        }
        if (this.parent != null) {
            return this.parent.getClass(str);
        }
        return null;
    }

    Object getClassInstance() throws UtilEvalError {
        if (this.classInstance != null) {
            return this.classInstance;
        }
        if (this.classStatic != null) {
            throw new UtilEvalError("Can't refer to class instance from static context.");
        }
        throw new InterpreterError(new StringBuffer().append("Can't resolve class instance 'this' in: ").append(this).toString());
    }

    public BshClassManager getClassManager() {
        if (this.classManager != null) {
            return this.classManager;
        }
        if (this.parent != null && this.parent != JAVACODE) {
            return this.parent.getClassManager();
        }
        System.out.println("experiment: creating class manager");
        this.classManager = BshClassManager.createClassManager(null);
        return this.classManager;
    }

    public Object getCommand(String str, Class[] clsArr, Interpreter interpreter) throws UtilEvalError {
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append("getCommand: ").append(str).toString());
        }
        BshClassManager classManager = interpreter.getClassManager();
        if (this.importedCommands != null) {
            for (int size = this.importedCommands.size() - 1; size >= 0; size--) {
                String str2 = (String) this.importedCommands.elementAt(size);
                String string = str2.equals("/") ? new StringBuffer().append(str2).append(str).append(".bsh").toString() : new StringBuffer().append(str2).append("/").append(str).append(".bsh").toString();
                Interpreter.debug(new StringBuffer().append("searching for script: ").append(string).toString());
                InputStream resourceAsStream = classManager.getResourceAsStream(string);
                if (resourceAsStream != null) {
                    return loadScriptedCommand(resourceAsStream, str, clsArr, string, interpreter);
                }
                String string2 = str2.equals("/") ? str : new StringBuffer().append(str2.substring(1).replace('/', '.')).append(".").append(str).toString();
                Interpreter.debug(new StringBuffer().append("searching for class: ").append(string2).toString());
                Class clsClassForName = classManager.classForName(string2);
                if (clsClassForName != null) {
                    return clsClassForName;
                }
            }
        }
        if (this.parent != null) {
            return this.parent.getCommand(str, clsArr, interpreter);
        }
        return null;
    }

    public Variable[] getDeclaredVariables() {
        if (this.variables == null) {
            return new Variable[0];
        }
        Variable[] variableArr = new Variable[this.variables.size()];
        Enumeration enumerationElements = this.variables.elements();
        int i = 0;
        while (enumerationElements.hasMoreElements()) {
            variableArr[i] = (Variable) enumerationElements.nextElement();
            i++;
        }
        return variableArr;
    }

    public This getGlobal(Interpreter interpreter) {
        return this.parent != null ? this.parent.getGlobal(interpreter) : getThis(interpreter);
    }

    protected BshMethod getImportedMethod(String str, Class[] clsArr) throws UtilEvalError {
        if (this.importedObjects != null) {
            for (int i = 0; i < this.importedObjects.size(); i++) {
                Object objElementAt = this.importedObjects.elementAt(i);
                Method methodResolveJavaMethod = Reflect.resolveJavaMethod(getClassManager(), objElementAt.getClass(), str, clsArr, false);
                if (methodResolveJavaMethod != null) {
                    return new BshMethod(methodResolveJavaMethod, objElementAt);
                }
            }
        }
        if (this.importedStatic != null) {
            for (int i2 = 0; i2 < this.importedStatic.size(); i2++) {
                Method methodResolveJavaMethod2 = Reflect.resolveJavaMethod(getClassManager(), (Class) this.importedStatic.elementAt(i2), str, clsArr, true);
                if (methodResolveJavaMethod2 != null) {
                    return new BshMethod(methodResolveJavaMethod2, null);
                }
            }
        }
        return null;
    }

    protected Variable getImportedVar(String str) throws UtilEvalError {
        if (this.importedObjects != null) {
            for (int i = 0; i < this.importedObjects.size(); i++) {
                Object objElementAt = this.importedObjects.elementAt(i);
                Field fieldResolveJavaField = Reflect.resolveJavaField(objElementAt.getClass(), str, false);
                if (fieldResolveJavaField != null) {
                    return new Variable(str, fieldResolveJavaField.getType(), new LHS(objElementAt, fieldResolveJavaField));
                }
            }
        }
        if (this.importedStatic != null) {
            for (int i2 = 0; i2 < this.importedStatic.size(); i2++) {
                Field fieldResolveJavaField2 = Reflect.resolveJavaField((Class) this.importedStatic.elementAt(i2), str, true);
                if (fieldResolveJavaField2 != null) {
                    return new Variable(str, fieldResolveJavaField2.getType(), new LHS(fieldResolveJavaField2));
                }
            }
        }
        return null;
    }

    public int getInvocationLine() {
        SimpleNode node = getNode();
        if (node != null) {
            return node.getLineNumber();
        }
        return -1;
    }

    public String getInvocationText() {
        SimpleNode node = getNode();
        return node != null ? node.getText() : "<invoked from Java code>";
    }

    public BshMethod getMethod(String str, Class[] clsArr) throws UtilEvalError {
        return getMethod(str, clsArr, false);
    }

    /* JADX WARN: Code duplicated, block: B:35:0x006b  */
    public BshMethod getMethod(String str, Class[] clsArr, boolean z) throws UtilEvalError {
        BshMethod importedMethod;
        Object obj;
        BshMethod[] bshMethodArr;
        BshMethod importedMethod2 = null;
        if (0 == 0 && this.isClass && !z) {
            importedMethod2 = getImportedMethod(str, clsArr);
        }
        if (importedMethod2 != null || this.methods == null || (obj = this.methods.get(str)) == null) {
            importedMethod = importedMethod2;
        } else {
            if (obj instanceof Vector) {
                Vector vector = (Vector) obj;
                BshMethod[] bshMethodArr2 = new BshMethod[vector.size()];
                vector.copyInto(bshMethodArr2);
                bshMethodArr = bshMethodArr2;
            } else {
                bshMethodArr = new BshMethod[]{(BshMethod) obj};
            }
            Class[][] clsArr2 = new Class[bshMethodArr.length][];
            for (int i = 0; i < bshMethodArr.length; i++) {
                clsArr2[i] = bshMethodArr[i].getParameterTypes();
            }
            int iFindMostSpecificSignature = Reflect.findMostSpecificSignature(clsArr, clsArr2);
            if (iFindMostSpecificSignature != -1) {
                importedMethod = bshMethodArr[iFindMostSpecificSignature];
            } else {
                importedMethod = importedMethod2;
            }
        }
        if (importedMethod == null && !this.isClass && !z) {
            importedMethod = getImportedMethod(str, clsArr);
        }
        return (z || importedMethod != null || this.parent == null) ? importedMethod : this.parent.getMethod(str, clsArr);
    }

    public String[] getMethodNames() {
        return this.methods == null ? new String[0] : enumerationToStringArray(this.methods.keys());
    }

    public BshMethod[] getMethods() {
        return this.methods == null ? new BshMethod[0] : flattenMethodCollection(this.methods.elements());
    }

    public String getName() {
        return this.nsName;
    }

    Name getNameResolver(String str) {
        if (this.names == null) {
            this.names = new Hashtable();
        }
        Name name = (Name) this.names.get(str);
        if (name != null) {
            return name;
        }
        Name name2 = new Name(this, str);
        this.names.put(str, name2);
        return name2;
    }

    SimpleNode getNode() {
        if (this.callerInfoNode != null) {
            return this.callerInfoNode;
        }
        if (this.parent != null) {
            return this.parent.getNode();
        }
        return null;
    }

    String getPackage() {
        if (this.packageName != null) {
            return this.packageName;
        }
        if (this.parent != null) {
            return this.parent.getPackage();
        }
        return null;
    }

    public NameSpace getParent() {
        return this.parent;
    }

    public This getSuper(Interpreter interpreter) {
        return this.parent != null ? this.parent.getThis(interpreter) : getThis(interpreter);
    }

    This getThis(Interpreter interpreter) {
        if (this.thisReference == null) {
            this.thisReference = This.getThis(this, interpreter);
        }
        return this.thisReference;
    }

    public Object getVariable(String str) throws UtilEvalError {
        return getVariable(str, true);
    }

    public Object getVariable(String str, boolean z) throws UtilEvalError {
        return unwrapVariable(getVariableImpl(str, z));
    }

    protected Variable getVariableImpl(String str, boolean z) throws UtilEvalError {
        Variable importedVar = null;
        if (0 == 0 && this.isClass) {
            importedVar = getImportedVar(str);
        }
        if (importedVar == null && this.variables != null) {
            importedVar = (Variable) this.variables.get(str);
        }
        if (importedVar == null && !this.isClass) {
            importedVar = getImportedVar(str);
        }
        return (z && importedVar == null && this.parent != null) ? this.parent.getVariableImpl(str, z) : importedVar;
    }

    public String[] getVariableNames() {
        return this.variables == null ? new String[0] : enumerationToStringArray(this.variables.keys());
    }

    public void importClass(String str) {
        if (this.importedClasses == null) {
            this.importedClasses = new Hashtable();
        }
        this.importedClasses.put(Name.suffix(str, 1), str);
        nameSpaceChanged();
    }

    public void importCommands(String str) {
        if (this.importedCommands == null) {
            this.importedCommands = new Vector();
        }
        String strReplace = str.replace('.', '/');
        if (!strReplace.startsWith("/")) {
            strReplace = new StringBuffer().append("/").append(strReplace).toString();
        }
        if (strReplace.length() > 1 && strReplace.endsWith("/")) {
            strReplace = strReplace.substring(0, strReplace.length() - 1);
        }
        if (this.importedCommands.contains(strReplace)) {
            this.importedCommands.remove(strReplace);
        }
        this.importedCommands.addElement(strReplace);
        nameSpaceChanged();
    }

    public void importObject(Object obj) {
        if (this.importedObjects == null) {
            this.importedObjects = new Vector();
        }
        if (this.importedObjects.contains(obj)) {
            this.importedObjects.remove(obj);
        }
        this.importedObjects.addElement(obj);
        nameSpaceChanged();
    }

    public void importPackage(String str) {
        if (this.importedPackages == null) {
            this.importedPackages = new Vector();
        }
        if (this.importedPackages.contains(str)) {
            this.importedPackages.remove(str);
        }
        this.importedPackages.addElement(str);
        nameSpaceChanged();
    }

    public void importStatic(Class cls) {
        if (this.importedStatic == null) {
            this.importedStatic = new Vector();
        }
        if (this.importedStatic.contains(cls)) {
            this.importedStatic.remove(cls);
        }
        this.importedStatic.addElement(cls);
        nameSpaceChanged();
    }

    public Object invokeMethod(String str, Object[] objArr, Interpreter interpreter) throws EvalError {
        return invokeMethod(str, objArr, interpreter, null, null);
    }

    public Object invokeMethod(String str, Object[] objArr, Interpreter interpreter, CallStack callStack, SimpleNode simpleNode) throws EvalError {
        return getThis(interpreter).invokeMethod(str, objArr, interpreter, callStack, simpleNode, false);
    }

    public void loadDefaultImports() {
        importClass("bsh.EvalError");
        importClass("bsh.Interpreter");
        importPackage("javax.swing.event");
        importPackage("javax.swing");
        importPackage("java.awt.event");
        importPackage("java.awt");
        importPackage("java.net");
        importPackage("java.util");
        importPackage("java.io");
        importPackage("java.lang");
        importCommands("/bsh/commands");
    }

    public void nameSpaceChanged() {
        this.classCache = null;
        this.names = null;
    }

    public void prune() {
        if (this.classManager == null) {
            setClassManager(BshClassManager.createClassManager(null));
        }
        setParent(null);
    }

    void setClassInstance(Object obj) {
        this.classInstance = obj;
        importObject(obj);
    }

    void setClassManager(BshClassManager bshClassManager) {
        this.classManager = bshClassManager;
    }

    void setClassStatic(Class cls) {
        this.classStatic = cls;
        importStatic(cls);
    }

    void setLocalVariable(String str, Object obj, boolean z) throws UtilEvalError {
        setVariable(str, obj, z, false);
    }

    public void setMethod(String str, BshMethod bshMethod) throws UtilEvalError {
        if (this.methods == null) {
            this.methods = new Hashtable();
        }
        Object obj = this.methods.get(str);
        if (obj == null) {
            this.methods.put(str, bshMethod);
            return;
        }
        if (!(obj instanceof BshMethod)) {
            ((Vector) obj).addElement(bshMethod);
            return;
        }
        Vector vector = new Vector();
        vector.addElement(obj);
        vector.addElement(bshMethod);
        this.methods.put(str, vector);
    }

    public void setName(String str) {
        this.nsName = str;
    }

    void setNode(SimpleNode simpleNode) {
        this.callerInfoNode = simpleNode;
    }

    void setPackage(String str) {
        this.packageName = str;
    }

    public void setParent(NameSpace nameSpace) {
        this.parent = nameSpace;
        if (nameSpace == null) {
            loadDefaultImports();
        }
    }

    public void setTypedVariable(String str, Class cls, Object obj, Modifiers modifiers) throws UtilEvalError {
        if (this.variables == null) {
            this.variables = new Hashtable();
        }
        Variable variableImpl = getVariableImpl(str, false);
        if (variableImpl == null || variableImpl.getType() == null) {
            this.variables.put(str, new Variable(str, cls, obj, modifiers));
        } else {
            if (variableImpl.getType() != cls) {
                throw new UtilEvalError(new StringBuffer().append("Typed variable: ").append(str).append(" was previously declared with type: ").append(variableImpl.getType()).toString());
            }
            variableImpl.setValue(obj, 0);
        }
    }

    public void setTypedVariable(String str, Class cls, Object obj, boolean z) throws UtilEvalError {
        Modifiers modifiers = new Modifiers();
        if (z) {
            modifiers.addModifier(2, "final");
        }
        setTypedVariable(str, cls, obj, modifiers);
    }

    public void setVariable(String str, Object obj, boolean z) throws UtilEvalError {
        setVariable(str, obj, z, Interpreter.LOCALSCOPING ? z : true);
    }

    void setVariable(String str, Object obj, boolean z, boolean z2) throws UtilEvalError {
        if (this.variables == null) {
            this.variables = new Hashtable();
        }
        if (obj == null) {
            throw new InterpreterError("null variable value");
        }
        Variable variableImpl = getVariableImpl(str, z2);
        if (variableImpl != null) {
            try {
                variableImpl.setValue(obj, 1);
            } catch (UtilEvalError e) {
                throw new UtilEvalError(new StringBuffer().append("Variable assignment: ").append(str).append(": ").append(e.getMessage()).toString());
            }
        } else {
            if (z) {
                throw new UtilEvalError(new StringBuffer().append("(Strict Java mode) Assignment to undeclared variable: ").append(str).toString());
            }
            this.variables.put(str, new Variable(str, obj, (Modifiers) null));
            nameSpaceChanged();
        }
    }

    public String toString() {
        return new StringBuffer().append("NameSpace: ").append(this.nsName == null ? super.toString() : new StringBuffer().append(this.nsName).append(" (").append(super.toString()).append(")").toString()).append(this.isClass ? " (isClass) " : Constants.MAIN_VERSION_TAG).append(this.isMethod ? " (method) " : Constants.MAIN_VERSION_TAG).append(this.classStatic != null ? " (class static) " : Constants.MAIN_VERSION_TAG).append(this.classInstance != null ? " (class instance) " : Constants.MAIN_VERSION_TAG).toString();
    }

    public void unsetVariable(String str) {
        if (this.variables != null) {
            this.variables.remove(str);
            nameSpaceChanged();
        }
    }

    protected Object unwrapVariable(Variable variable) throws UtilEvalError {
        return variable == null ? Primitive.VOID : variable.getValue();
    }
}
