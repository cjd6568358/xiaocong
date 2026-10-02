package bsh;

import com.tencent.android.tpush.common.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Interpreter implements ConsoleInterface, Serializable, Runnable {
    public static boolean DEBUG = false;
    public static boolean LOCALSCOPING = false;
    public static boolean TRACE = false;
    public static final String VERSION = "2.0b4";
    static Class array$Ljava$lang$String;
    static transient PrintStream debug;
    static This sharedObject;
    static String systemLineSeparator = "\n";
    ConsoleInterface console;
    transient PrintStream err;
    protected boolean evalOnly;
    private boolean exitOnEOF;
    NameSpace globalNameSpace;
    transient Reader in;
    protected boolean interactive;
    transient PrintStream out;
    Interpreter parent;
    transient Parser parser;
    private boolean showResults;
    String sourceFileInfo;
    private boolean strictJava;

    static {
        staticInit();
    }

    public Interpreter() {
        this(new StringReader(Constants.MAIN_VERSION_TAG), System.out, System.err, false, null);
        this.evalOnly = true;
        setu("bsh.evalOnly", new Primitive(true));
    }

    public Interpreter(ConsoleInterface consoleInterface) {
        this(consoleInterface, null);
    }

    public Interpreter(ConsoleInterface consoleInterface, NameSpace nameSpace) {
        this(consoleInterface.getIn(), consoleInterface.getOut(), consoleInterface.getErr(), true, nameSpace);
        setConsole(consoleInterface);
    }

    public Interpreter(Reader reader, PrintStream printStream, PrintStream printStream2, boolean z) {
        this(reader, printStream, printStream2, z, null);
    }

    public Interpreter(Reader reader, PrintStream printStream, PrintStream printStream2, boolean z, NameSpace nameSpace) {
        this(reader, printStream, printStream2, z, nameSpace, null, null);
    }

    public Interpreter(Reader reader, PrintStream printStream, PrintStream printStream2, boolean z, NameSpace nameSpace, Interpreter interpreter, String str) {
        this.strictJava = false;
        this.exitOnEOF = true;
        this.parser = new Parser(reader);
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.in = reader;
        this.out = printStream;
        this.err = printStream2;
        this.interactive = z;
        debug = printStream2;
        this.parent = interpreter;
        if (interpreter != null) {
            setStrictJava(interpreter.getStrictJava());
        }
        this.sourceFileInfo = str;
        BshClassManager bshClassManagerCreateClassManager = BshClassManager.createClassManager(this);
        if (nameSpace == null) {
            this.globalNameSpace = new NameSpace(bshClassManagerCreateClassManager, "global");
        } else {
            this.globalNameSpace = nameSpace;
        }
        if (!(getu("bsh") instanceof This)) {
            initRootSystemObject();
        }
        if (z) {
            loadRCFiles();
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        if (DEBUG) {
            debug(new StringBuffer().append("Time to initialize interpreter: ").append(jCurrentTimeMillis2 - jCurrentTimeMillis).toString());
        }
    }

    private boolean Line() throws ParseException {
        return this.parser.Line();
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static final void debug(String str) {
        if (DEBUG) {
            debug.println(new StringBuffer().append("// Debug: ").append(str).toString());
        }
    }

    private String getBshPrompt() {
        try {
            return (String) eval("getBshPrompt()");
        } catch (Exception e) {
            return "bsh % ";
        }
    }

    private JavaCharStream get_jj_input_stream() {
        return this.parser.jj_input_stream;
    }

    private JJTParserState get_jjtree() {
        return this.parser.jjtree;
    }

    private void initRootSystemObject() {
        BshClassManager classManager = getClassManager();
        setu("bsh", new NameSpace(classManager, "Bsh Object").getThis(this));
        if (sharedObject == null) {
            sharedObject = new NameSpace(classManager, "Bsh Shared System Object").getThis(this);
        }
        setu("bsh.system", sharedObject);
        setu("bsh.shared", sharedObject);
        setu("bsh.help", new NameSpace(classManager, "Bsh Command Help Text").getThis(this));
        try {
            setu("bsh.cwd", System.getProperty("user.dir"));
        } catch (SecurityException e) {
            setu("bsh.cwd", ".");
        }
        setu("bsh.interactive", new Primitive(this.interactive));
        setu("bsh.evalOnly", new Primitive(this.evalOnly));
    }

    public static void invokeMain(Class cls, String[] strArr) throws Exception {
        Class clsClass$;
        Class[] clsArr = new Class[1];
        if (array$Ljava$lang$String == null) {
            clsClass$ = class$("[Ljava.lang.String;");
            array$Ljava$lang$String = clsClass$;
        } else {
            clsClass$ = array$Ljava$lang$String;
        }
        clsArr[0] = clsClass$;
        Method methodResolveJavaMethod = Reflect.resolveJavaMethod(null, cls, "main", clsArr, true);
        if (methodResolveJavaMethod != null) {
            methodResolveJavaMethod.invoke(null, strArr);
        }
    }

    public static void main(String[] strArr) {
        String[] strArr2;
        if (strArr.length <= 0) {
            new Interpreter(new CommandLineReader(new InputStreamReader((System.getProperty("os.name").startsWith("Windows") && System.getProperty("java.version").startsWith("1.1.")) ? new FilterInputStream(System.in) { // from class: bsh.Interpreter.1
                @Override // java.io.FilterInputStream, java.io.InputStream
                public int available() throws IOException {
                    return 0;
                }
            } : System.in)), System.out, System.err, true).run();
            return;
        }
        String str = strArr[0];
        if (strArr.length > 1) {
            String[] strArr3 = new String[strArr.length - 1];
            System.arraycopy(strArr, 1, strArr3, 0, strArr.length - 1);
            strArr2 = strArr3;
        } else {
            strArr2 = new String[0];
        }
        Interpreter interpreter = new Interpreter();
        interpreter.setu("bsh.args", strArr2);
        try {
            Object objSource = interpreter.source(str, interpreter.globalNameSpace);
            if (objSource instanceof Class) {
                try {
                    invokeMain((Class) objSource, strArr2);
                } catch (Exception e) {
                    e = e;
                    if (e instanceof InvocationTargetException) {
                        e = ((InvocationTargetException) e).getTargetException();
                    }
                    System.err.println(new StringBuffer().append("Class: ").append(objSource).append(" main method threw exception:").append(e).toString());
                }
            }
        } catch (TargetError e2) {
            System.out.println(new StringBuffer().append("Script threw exception: ").append(e2).toString());
            if (e2.inNativeCode()) {
                e2.printStackTrace(DEBUG, System.err);
            }
        } catch (EvalError e3) {
            System.out.println(new StringBuffer().append("Evaluation Error: ").append(e3).toString());
        } catch (FileNotFoundException e4) {
            System.out.println(new StringBuffer().append("File not found: ").append(e4).toString());
        } catch (IOException e5) {
            System.out.println(new StringBuffer().append("I/O Error: ").append(e5).toString());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        if (this.console != null) {
            setOut(this.console.getOut());
            setErr(this.console.getErr());
        } else {
            setOut(System.out);
            setErr(System.err);
        }
    }

    public static void redirectOutputToFile(String str) {
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(str));
            System.setOut(printStream);
            System.setErr(printStream);
        } catch (IOException e) {
            System.err.println(new StringBuffer().append("Can't redirect output to file: ").append(str).toString());
        }
    }

    private String showEvalString(String str) {
        String strReplace = str.replace('\n', ' ').replace('\r', ' ');
        return strReplace.length() > 80 ? new StringBuffer().append(strReplace.substring(0, 80)).append(" . . . ").toString() : strReplace;
    }

    static void staticInit() {
        try {
            systemLineSeparator = System.getProperty("line.separator");
            debug = System.err;
            DEBUG = Boolean.getBoolean("debug");
            TRACE = Boolean.getBoolean("trace");
            LOCALSCOPING = Boolean.getBoolean("localscoping");
            String property = System.getProperty("outfile");
            if (property != null) {
                redirectOutputToFile(property);
            }
        } catch (SecurityException e) {
            System.err.println(new StringBuffer().append("Could not init static:").append(e).toString());
        } catch (Exception e2) {
            System.err.println(new StringBuffer().append("Could not init static(2):").append(e2).toString());
        } catch (Throwable th) {
            System.err.println(new StringBuffer().append("Could not init static(3):").append(th).toString());
        }
    }

    @Override // bsh.ConsoleInterface
    public final void error(Object obj) {
        if (this.console != null) {
            this.console.error(new StringBuffer().append("// Error: ").append(obj).append("\n").toString());
        } else {
            this.err.println(new StringBuffer().append("// Error: ").append(obj).toString());
            this.err.flush();
        }
    }

    public Object eval(Reader reader) throws EvalError {
        return eval(reader, this.globalNameSpace, "eval stream");
    }

    public Object eval(Reader reader, NameSpace nameSpace, String str) throws EvalError {
        SimpleNode simpleNode;
        boolean zLine;
        boolean z;
        if (DEBUG) {
            debug(new StringBuffer().append("eval: nameSpace = ").append(nameSpace).toString());
        }
        Interpreter interpreter = new Interpreter(reader, this.out, this.err, false, nameSpace, this, str);
        CallStack callStack = new CallStack(nameSpace);
        boolean z2 = false;
        Object objEval = null;
        while (!z2) {
            try {
                try {
                    try {
                        try {
                            zLine = interpreter.Line();
                            try {
                                if (interpreter.get_jjtree().nodeArity() > 0) {
                                    simpleNode = (SimpleNode) interpreter.get_jjtree().rootNode();
                                    try {
                                        simpleNode.setSourceFile(str);
                                        if (TRACE) {
                                            println(new StringBuffer().append("// ").append(simpleNode.getText()).toString());
                                        }
                                        objEval = simpleNode.eval(callStack, interpreter);
                                        if (callStack.depth() > 1) {
                                            throw new InterpreterError(new StringBuffer().append("Callstack growing: ").append(callStack).toString());
                                        }
                                        if (objEval instanceof ReturnControl) {
                                            objEval = ((ReturnControl) objEval).value;
                                            interpreter.get_jjtree().reset();
                                            if (callStack.depth() <= 1) {
                                                break;
                                            }
                                            callStack.clear();
                                            callStack.push(nameSpace);
                                            break;
                                        }
                                        if (interpreter.showResults && objEval != Primitive.VOID) {
                                            println(new StringBuffer().append("<").append(objEval).append(">").toString());
                                        }
                                    } catch (TargetError e) {
                                        e = e;
                                        z = zLine;
                                        if (e.getNode() == null) {
                                            e.setNode(simpleNode);
                                        }
                                        e.reThrow(new StringBuffer().append("Sourced file: ").append(str).toString());
                                        interpreter.get_jjtree().reset();
                                        if (callStack.depth() > 1) {
                                            callStack.clear();
                                            callStack.push(nameSpace);
                                            z2 = z;
                                            objEval = objEval;
                                        } else {
                                            z2 = z;
                                            objEval = objEval;
                                        }
                                    } catch (EvalError e2) {
                                        e = e2;
                                        if (DEBUG) {
                                            e.printStackTrace();
                                        }
                                        if (e.getNode() == null) {
                                            e.setNode(simpleNode);
                                        }
                                        e.reThrow(new StringBuffer().append("Sourced file: ").append(str).toString());
                                        interpreter.get_jjtree().reset();
                                        if (callStack.depth() > 1) {
                                            callStack.clear();
                                            callStack.push(nameSpace);
                                            z2 = zLine;
                                        } else {
                                            z2 = zLine;
                                        }
                                    } catch (InterpreterError e3) {
                                        e = e3;
                                        e.printStackTrace();
                                        throw new EvalError(new StringBuffer().append("Sourced file: ").append(str).append(" internal Error: ").append(e.getMessage()).toString(), simpleNode, callStack);
                                    } catch (TokenMgrError e4) {
                                        e = e4;
                                        throw new EvalError(new StringBuffer().append("Sourced file: ").append(str).append(" Token Parsing Error: ").append(e.getMessage()).toString(), simpleNode, callStack);
                                    } catch (Exception e5) {
                                        e = e5;
                                        if (DEBUG) {
                                            e.printStackTrace();
                                        }
                                        throw new EvalError(new StringBuffer().append("Sourced file: ").append(str).append(" unknown error: ").append(e.getMessage()).toString(), simpleNode, callStack);
                                    }
                                }
                                interpreter.get_jjtree().reset();
                                if (callStack.depth() > 1) {
                                    callStack.clear();
                                    callStack.push(nameSpace);
                                    z2 = zLine;
                                } else {
                                    z2 = zLine;
                                }
                            } catch (TargetError e6) {
                                e = e6;
                                simpleNode = null;
                                z = zLine;
                            } catch (EvalError e7) {
                                e = e7;
                                simpleNode = null;
                            }
                        } catch (ParseException e8) {
                            if (DEBUG) {
                                error(e8.getMessage(DEBUG));
                            }
                            e8.setErrorSourceFile(str);
                            throw e8;
                        }
                    } catch (Throwable th) {
                        interpreter.get_jjtree().reset();
                        if (callStack.depth() > 1) {
                            callStack.clear();
                            callStack.push(nameSpace);
                        }
                        throw th;
                    }
                } catch (TargetError e9) {
                    e = e9;
                    z = z2;
                    simpleNode = null;
                } catch (EvalError e10) {
                    e = e10;
                    zLine = z2;
                    simpleNode = null;
                }
            } catch (InterpreterError e11) {
                e = e11;
                simpleNode = null;
            } catch (TokenMgrError e12) {
                e = e12;
                simpleNode = null;
            } catch (Exception e13) {
                e = e13;
                simpleNode = null;
            }
        }
        return Primitive.unwrap(objEval);
    }

    public Object eval(String str) throws EvalError {
        if (DEBUG) {
            debug(new StringBuffer().append("eval(String): ").append(str).toString());
        }
        return eval(str, this.globalNameSpace);
    }

    public Object eval(String str, NameSpace nameSpace) throws EvalError {
        if (!str.endsWith(";")) {
            str = new StringBuffer().append(str).append(";").toString();
        }
        return eval(new StringReader(str), nameSpace, new StringBuffer().append("inline evaluation of: ``").append(showEvalString(str)).append("''").toString());
    }

    public Object get(String str) throws EvalError {
        try {
            return Primitive.unwrap(this.globalNameSpace.get(str, this));
        } catch (UtilEvalError e) {
            throw e.toEvalError(SimpleNode.JAVACODE, new CallStack());
        }
    }

    public BshClassManager getClassManager() {
        return getNameSpace().getClassManager();
    }

    @Override // bsh.ConsoleInterface
    public PrintStream getErr() {
        return this.err;
    }

    @Override // bsh.ConsoleInterface
    public Reader getIn() {
        return this.in;
    }

    public Object getInterface(Class cls) throws EvalError {
        try {
            return this.globalNameSpace.getThis(this).getInterface(cls);
        } catch (UtilEvalError e) {
            throw e.toEvalError(SimpleNode.JAVACODE, new CallStack());
        }
    }

    public NameSpace getNameSpace() {
        return this.globalNameSpace;
    }

    @Override // bsh.ConsoleInterface
    public PrintStream getOut() {
        return this.out;
    }

    public Interpreter getParent() {
        return this.parent;
    }

    public boolean getShowResults() {
        return this.showResults;
    }

    public String getSourceFileInfo() {
        return this.sourceFileInfo != null ? this.sourceFileInfo : "<unknown source>";
    }

    public boolean getStrictJava() {
        return this.strictJava;
    }

    Object getu(String str) {
        try {
            return get(str);
        } catch (EvalError e) {
            throw new InterpreterError(new StringBuffer().append("set: ").append(e).toString());
        }
    }

    void loadRCFiles() {
        try {
            source(new StringBuffer().append(System.getProperty("user.home")).append(File.separator).append(".bshrc").toString(), this.globalNameSpace);
        } catch (Exception e) {
            if (DEBUG) {
                debug(new StringBuffer().append("Could not find rc file: ").append(e).toString());
            }
        }
    }

    public File pathToFile(String str) throws IOException {
        File file = new File(str);
        if (!file.isAbsolute()) {
            file = new File(new StringBuffer().append((String) getu("bsh.cwd")).append(File.separator).append(str).toString());
        }
        return new File(file.getCanonicalPath());
    }

    @Override // bsh.ConsoleInterface
    public final void print(Object obj) {
        if (this.console != null) {
            this.console.print(obj);
        } else {
            this.out.print(obj);
            this.out.flush();
        }
    }

    @Override // bsh.ConsoleInterface
    public final void println(Object obj) {
        print(new StringBuffer().append(String.valueOf(obj)).append(systemLineSeparator).toString());
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        Exception exc;
        TokenMgrError tokenMgrError;
        InterpreterError interpreterError;
        EvalError evalError;
        if (this.evalOnly) {
            throw new RuntimeException("bsh Interpreter: No stream");
        }
        if (this.interactive) {
            try {
                eval("printBanner();");
            } catch (EvalError e) {
                println("BeanShell 2.0b4 - by Pat Niemeyer (pat@pat.net)");
            }
        }
        CallStack callStack = new CallStack(this.globalNameSpace);
        boolean zLine = false;
        while (!zLine) {
            try {
                try {
                    System.out.flush();
                    System.err.flush();
                    Thread.yield();
                    if (this.interactive) {
                        print(getBshPrompt());
                    }
                    zLine = Line();
                    try {
                        if (get_jjtree().nodeArity() > 0) {
                            SimpleNode simpleNode = (SimpleNode) get_jjtree().rootNode();
                            if (DEBUG) {
                                simpleNode.dump(">");
                            }
                            Object objEval = simpleNode.eval(callStack, this);
                            if (callStack.depth() > 1) {
                                throw new InterpreterError(new StringBuffer().append("Callstack growing: ").append(callStack).toString());
                            }
                            if (objEval instanceof ReturnControl) {
                                objEval = ((ReturnControl) objEval).value;
                            }
                            if (objEval != Primitive.VOID) {
                                setu("$_", objEval);
                                if (this.showResults) {
                                    println(new StringBuffer().append("<").append(objEval).append(">").toString());
                                }
                            }
                        }
                        get_jjtree().reset();
                        if (callStack.depth() > 1) {
                            callStack.clear();
                            callStack.push(this.globalNameSpace);
                        }
                    } catch (EvalError e2) {
                        z = zLine;
                        evalError = e2;
                        if (this.interactive) {
                            error(new StringBuffer().append("EvalError: ").append(evalError.toString()).toString());
                        } else {
                            error(new StringBuffer().append("EvalError: ").append(evalError.getMessage()).toString());
                        }
                        if (DEBUG) {
                            evalError.printStackTrace();
                        }
                        if (!this.interactive) {
                            z = true;
                        }
                        get_jjtree().reset();
                        if (callStack.depth() > 1) {
                            callStack.clear();
                            callStack.push(this.globalNameSpace);
                            zLine = z;
                        } else {
                            zLine = z;
                        }
                    } catch (InterpreterError e3) {
                        z = zLine;
                        interpreterError = e3;
                        error(new StringBuffer().append("Internal Error: ").append(interpreterError.getMessage()).toString());
                        interpreterError.printStackTrace();
                        if (!this.interactive) {
                            z = true;
                        }
                        get_jjtree().reset();
                        if (callStack.depth() > 1) {
                            callStack.clear();
                            callStack.push(this.globalNameSpace);
                            zLine = z;
                        } else {
                            zLine = z;
                        }
                    } catch (TokenMgrError e4) {
                        z = zLine;
                        tokenMgrError = e4;
                        error(new StringBuffer().append("Error parsing input: ").append(tokenMgrError).toString());
                        this.parser.reInitTokenInput(this.in);
                        if (!this.interactive) {
                            z = true;
                        }
                        get_jjtree().reset();
                        if (callStack.depth() > 1) {
                            callStack.clear();
                            callStack.push(this.globalNameSpace);
                            zLine = z;
                        } else {
                            zLine = z;
                        }
                    } catch (Exception e5) {
                        z = zLine;
                        exc = e5;
                        error(new StringBuffer().append("Unknown error: ").append(exc).toString());
                        if (DEBUG) {
                            exc.printStackTrace();
                        }
                        if (!this.interactive) {
                            z = true;
                        }
                        get_jjtree().reset();
                        if (callStack.depth() > 1) {
                            callStack.clear();
                            callStack.push(this.globalNameSpace);
                            zLine = z;
                        } else {
                            zLine = z;
                        }
                    }
                } catch (ParseException e6) {
                    error(new StringBuffer().append("Parser Error: ").append(e6.getMessage(DEBUG)).toString());
                    if (DEBUG) {
                        e6.printStackTrace();
                    }
                    if (!this.interactive) {
                        zLine = true;
                    }
                    this.parser.reInitInput(this.in);
                    get_jjtree().reset();
                    if (callStack.depth() > 1) {
                    }
                } catch (TargetError e7) {
                    error(new StringBuffer().append("// Uncaught Exception: ").append(e7).toString());
                    if (e7.inNativeCode()) {
                        e7.printStackTrace(DEBUG, this.err);
                    }
                    if (!this.interactive) {
                        zLine = true;
                    }
                    setu("$_e", e7.getTarget());
                    get_jjtree().reset();
                    if (callStack.depth() > 1) {
                    }
                } finally {
                    get_jjtree().reset();
                    if (callStack.depth() > 1) {
                        callStack.clear();
                        callStack.push(this.globalNameSpace);
                    }
                }
            } catch (EvalError e8) {
                z = zLine;
                evalError = e8;
            } catch (InterpreterError e9) {
                z = zLine;
                interpreterError = e9;
            } catch (TokenMgrError e10) {
                z = zLine;
                tokenMgrError = e10;
            } catch (Exception e11) {
                z = zLine;
                exc = e11;
            }
        }
        if (this.interactive && this.exitOnEOF) {
            System.exit(0);
        }
    }

    public void set(String str, double d) throws EvalError {
        set(str, new Primitive(d));
    }

    public void set(String str, float f) throws EvalError {
        set(str, new Primitive(f));
    }

    public void set(String str, int i) throws EvalError {
        set(str, new Primitive(i));
    }

    public void set(String str, long j) throws EvalError {
        set(str, new Primitive(j));
    }

    public void set(String str, Object obj) throws EvalError {
        if (obj == null) {
            obj = Primitive.NULL;
        }
        CallStack callStack = new CallStack();
        try {
            if (Name.isCompound(str)) {
                this.globalNameSpace.getNameResolver(str).toLHS(callStack, this).assign(obj, false);
            } else {
                this.globalNameSpace.setVariable(str, obj, false);
            }
        } catch (UtilEvalError e) {
            throw e.toEvalError(SimpleNode.JAVACODE, callStack);
        }
    }

    public void set(String str, boolean z) throws EvalError {
        set(str, new Primitive(z));
    }

    public void setClassLoader(ClassLoader classLoader) {
        getClassManager().setClassLoader(classLoader);
    }

    public void setConsole(ConsoleInterface consoleInterface) {
        this.console = consoleInterface;
        setu("bsh.console", consoleInterface);
        setOut(consoleInterface.getOut());
        setErr(consoleInterface.getErr());
    }

    public void setErr(PrintStream printStream) {
        this.err = printStream;
    }

    public void setExitOnEOF(boolean z) {
        this.exitOnEOF = z;
    }

    public void setNameSpace(NameSpace nameSpace) {
        this.globalNameSpace = nameSpace;
    }

    public void setOut(PrintStream printStream) {
        this.out = printStream;
    }

    public void setShowResults(boolean z) {
        this.showResults = z;
    }

    public void setStrictJava(boolean z) {
        this.strictJava = z;
    }

    void setu(String str, Object obj) {
        try {
            set(str, obj);
        } catch (EvalError e) {
            throw new InterpreterError(new StringBuffer().append("set: ").append(e).toString());
        }
    }

    public Object source(String str) throws EvalError, IOException {
        return source(str, this.globalNameSpace);
    }

    public Object source(String str, NameSpace nameSpace) throws EvalError, IOException {
        File filePathToFile = pathToFile(str);
        if (DEBUG) {
            debug(new StringBuffer().append("Sourcing file: ").append(filePathToFile).toString());
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePathToFile));
        try {
            return eval(bufferedReader, nameSpace, str);
        } finally {
            bufferedReader.close();
        }
    }

    public void unset(String str) throws EvalError {
        try {
            LHS lhs = this.globalNameSpace.getNameResolver(str).toLHS(new CallStack(), this);
            if (lhs.type != 0) {
                throw new EvalError(new StringBuffer().append("Can't unset, not a variable: ").append(str).toString(), SimpleNode.JAVACODE, new CallStack());
            }
            lhs.nameSpace.unsetVariable(str);
        } catch (UtilEvalError e) {
            throw new EvalError(e.getMessage(), SimpleNode.JAVACODE, new CallStack());
        }
    }
}
