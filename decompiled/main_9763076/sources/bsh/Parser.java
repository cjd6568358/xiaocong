package bsh;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Parser implements ParserConstants, ParserTreeConstants {
    JavaCharStream jj_input_stream;
    private int jj_la;
    private Token jj_lastpos;
    public Token jj_nt;
    private Token jj_scanpos;
    private boolean jj_semLA;
    public ParserTokenManager token_source;
    protected JJTParserState jjtree = new JJTParserState();
    boolean retainComments = false;
    public boolean lookingAhead = false;
    private final LookaheadSuccess jj_ls = new LookaheadSuccess(null);
    public Token token = new Token();
    private int jj_ntk = -1;

    /* JADX INFO: renamed from: bsh.Parser$1, reason: invalid class name */
    class AnonymousClass1 {
    }

    private static final class LookaheadSuccess extends Error {
        private LookaheadSuccess() {
        }

        LookaheadSuccess(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public Parser(ParserTokenManager parserTokenManager) {
        this.token_source = parserTokenManager;
    }

    public Parser(InputStream inputStream) {
        this.jj_input_stream = new JavaCharStream(inputStream, 1, 1);
        this.token_source = new ParserTokenManager(this.jj_input_stream);
    }

    public Parser(Reader reader) {
        this.jj_input_stream = new JavaCharStream(reader, 1, 1);
        this.token_source = new ParserTokenManager(this.jj_input_stream);
    }

    private final boolean jj_2_1(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_1();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_10(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_10();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_11(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_11();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_12(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_12();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_13(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_13();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_14(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_14();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_15(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_15();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_16(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_16();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_17(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_17();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_18(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_18();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_19(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_19();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_2(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_2();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_20(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_20();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_21(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_21();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_22(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_22();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_23(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_23();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_24(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_24();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_25(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_25();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_26(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_26();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_27(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_27();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_28(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_28();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_29(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_29();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_3(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_3();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_30(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_30();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_31(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_31();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_4(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_4();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_5(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_5();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_6(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_6();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_7(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_7();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_8(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_8();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_2_9(int i) {
        this.jj_la = i;
        Token token = this.token;
        this.jj_scanpos = token;
        this.jj_lastpos = token;
        try {
            return !jj_3_9();
        } catch (LookaheadSuccess e) {
            return true;
        }
    }

    private final boolean jj_3R_100() {
        return jj_3R_130();
    }

    private final boolean jj_3R_101() {
        return jj_3R_37();
    }

    private final boolean jj_3R_102() {
        return jj_3R_32();
    }

    private final boolean jj_3R_103() {
        return jj_3R_29();
    }

    private final boolean jj_3R_104() {
        Token token = this.jj_scanpos;
        if (jj_3_16()) {
            this.jj_scanpos = token;
            if (jj_3R_131()) {
                this.jj_scanpos = token;
                if (jj_3R_132()) {
                    this.jj_scanpos = token;
                    if (jj_3R_133()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_105() {
        return jj_3R_129();
    }

    private final boolean jj_3R_106() {
        return jj_3R_134();
    }

    private final boolean jj_3R_107() {
        return jj_3R_33() || jj_3R_34() || jj_3R_39();
    }

    private final boolean jj_3R_108() {
        if (jj_3R_135()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_156()) {
            this.jj_scanpos = token;
        }
        return false;
    }

    private final boolean jj_3R_109() {
        Token token = this.jj_scanpos;
        if (jj_3_5()) {
            this.jj_scanpos = token;
            if (jj_3R_136()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_110() {
        return jj_scan_token(79) || jj_3R_109();
    }

    private final boolean jj_3R_111() {
        return jj_scan_token(79) || jj_3R_29();
    }

    private final boolean jj_3R_112() {
        return jj_3R_39();
    }

    private final boolean jj_3R_113() {
        Token token;
        if (jj_scan_token(50) || jj_scan_token(72) || jj_3R_39() || jj_scan_token(73) || jj_scan_token(74)) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_183());
        this.jj_scanpos = token;
        return jj_scan_token(75);
    }

    private final boolean jj_3R_114() {
        if (jj_scan_token(32) || jj_scan_token(72) || jj_3R_39() || jj_scan_token(73) || jj_3R_45()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_184()) {
            this.jj_scanpos = token;
        }
        return false;
    }

    private final boolean jj_3R_115() {
        return jj_scan_token(59) || jj_scan_token(72) || jj_3R_39() || jj_scan_token(73) || jj_3R_45();
    }

    private final boolean jj_3R_116() {
        return jj_scan_token(21) || jj_3R_45() || jj_scan_token(59) || jj_scan_token(72) || jj_3R_39() || jj_scan_token(73) || jj_scan_token(78);
    }

    private final boolean jj_3R_117() {
        if (jj_scan_token(30) || jj_scan_token(72)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_185()) {
            this.jj_scanpos = token;
        }
        if (jj_scan_token(78)) {
            return true;
        }
        Token token2 = this.jj_scanpos;
        if (jj_3R_186()) {
            this.jj_scanpos = token2;
        }
        if (jj_scan_token(78)) {
            return true;
        }
        Token token3 = this.jj_scanpos;
        if (jj_3R_187()) {
            this.jj_scanpos = token3;
        }
        return jj_scan_token(73) || jj_3R_45();
    }

    private final boolean jj_3R_118() {
        Token token = this.jj_scanpos;
        if (jj_3_30()) {
            this.jj_scanpos = token;
            if (jj_3R_137()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_119() {
        if (jj_scan_token(12)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_scan_token(69)) {
            this.jj_scanpos = token;
        }
        return jj_scan_token(78);
    }

    private final boolean jj_3R_120() {
        if (jj_scan_token(19)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_scan_token(69)) {
            this.jj_scanpos = token;
        }
        return jj_scan_token(78);
    }

    private final boolean jj_3R_121() {
        if (jj_scan_token(46)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_188()) {
            this.jj_scanpos = token;
        }
        return jj_scan_token(78);
    }

    private final boolean jj_3R_122() {
        return jj_scan_token(51) || jj_scan_token(72) || jj_3R_39() || jj_scan_token(73) || jj_3R_38();
    }

    private final boolean jj_3R_123() {
        return jj_scan_token(53) || jj_3R_39() || jj_scan_token(78);
    }

    private final boolean jj_3R_124() {
        Token token;
        if (jj_scan_token(56) || jj_3R_38()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_189());
        this.jj_scanpos = token;
        Token token2 = this.jj_scanpos;
        if (jj_3R_190()) {
            this.jj_scanpos = token2;
        }
        return false;
    }

    private final boolean jj_3R_125() {
        return jj_scan_token(37);
    }

    private final boolean jj_3R_126() {
        return jj_scan_token(69);
    }

    private final boolean jj_3R_127() {
        return jj_3R_42() || jj_scan_token(69);
    }

    private final boolean jj_3R_128() {
        return jj_scan_token(34) || jj_scan_token(104) || jj_scan_token(78);
    }

    private final boolean jj_3R_129() {
        Token token = this.jj_scanpos;
        if (jj_3R_138()) {
            this.jj_scanpos = token;
            if (jj_3R_139()) {
                this.jj_scanpos = token;
                if (jj_3R_140()) {
                    this.jj_scanpos = token;
                    if (jj_3R_141()) {
                        this.jj_scanpos = token;
                        if (jj_3R_142()) {
                            this.jj_scanpos = token;
                            if (jj_3R_143()) {
                                this.jj_scanpos = token;
                                if (jj_3R_144()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_130() {
        Token token = this.jj_scanpos;
        if (jj_3_18()) {
            this.jj_scanpos = token;
            if (jj_3R_145()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_131() {
        return jj_scan_token(76) || jj_3R_39() || jj_scan_token(77);
    }

    private final boolean jj_3R_132() {
        if (jj_scan_token(80) || jj_scan_token(69)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_146()) {
            this.jj_scanpos = token;
        }
        return false;
    }

    private final boolean jj_3R_133() {
        return jj_scan_token(74) || jj_3R_39() || jj_scan_token(75);
    }

    private final boolean jj_3R_134() {
        Token token;
        if (jj_3R_39()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_147());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_135() {
        Token token;
        if (jj_3R_148()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_159());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_136() {
        return jj_scan_token(69);
    }

    private final boolean jj_3R_137() {
        return jj_scan_token(30) || jj_scan_token(72) || jj_3R_32() || jj_scan_token(69) || jj_scan_token(89) || jj_3R_39() || jj_scan_token(73) || jj_3R_45();
    }

    private final boolean jj_3R_138() {
        return jj_scan_token(60);
    }

    private final boolean jj_3R_139() {
        return jj_scan_token(64);
    }

    private final boolean jj_3R_140() {
        return jj_scan_token(66);
    }

    private final boolean jj_3R_141() {
        return jj_scan_token(67);
    }

    private final boolean jj_3R_142() {
        return jj_3R_149();
    }

    private final boolean jj_3R_143() {
        return jj_scan_token(41);
    }

    private final boolean jj_3R_144() {
        return jj_scan_token(57);
    }

    private final boolean jj_3R_145() {
        if (jj_scan_token(40) || jj_3R_29()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_151()) {
            this.jj_scanpos = token;
            if (jj_3R_152()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_146() {
        return jj_3R_69();
    }

    private final boolean jj_3R_147() {
        return jj_scan_token(79) || jj_3R_39();
    }

    private final boolean jj_3R_148() {
        Token token;
        if (jj_3R_153()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_162());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_149() {
        Token token = this.jj_scanpos;
        if (jj_3R_154()) {
            this.jj_scanpos = token;
            if (jj_3R_155()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_150() {
        Token token = this.jj_scanpos;
        if (jj_3_21()) {
            this.jj_scanpos = token;
            if (jj_3R_157()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_151() {
        return jj_3R_150();
    }

    private final boolean jj_3R_152() {
        if (jj_3R_69()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3_17()) {
            this.jj_scanpos = token;
        }
        return false;
    }

    private final boolean jj_3R_153() {
        Token token;
        if (jj_3R_158()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_165());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_154() {
        return jj_scan_token(55);
    }

    private final boolean jj_3R_155() {
        return jj_scan_token(26);
    }

    private final boolean jj_3R_156() {
        return jj_scan_token(88) || jj_3R_39() || jj_scan_token(89) || jj_3R_108();
    }

    private final boolean jj_3R_157() {
        Token token;
        if (jj_3R_160()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_160());
        this.jj_scanpos = token;
        return jj_3R_97();
    }

    private final boolean jj_3R_158() {
        Token token;
        if (jj_3R_161()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_167());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_159() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(96)) {
            this.jj_scanpos = token;
            if (jj_scan_token(97)) {
                return true;
            }
        }
        return jj_3R_148();
    }

    private final boolean jj_3R_160() {
        return jj_scan_token(76) || jj_scan_token(77);
    }

    private final boolean jj_3R_161() {
        Token token;
        if (jj_3R_164()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_169());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_162() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(98)) {
            this.jj_scanpos = token;
            if (jj_scan_token(99)) {
                return true;
            }
        }
        return jj_3R_153();
    }

    private final boolean jj_3R_163() {
        Token token;
        if (jj_3R_31()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3_4());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_164() {
        Token token;
        if (jj_3R_166()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_171());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_165() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(108)) {
            this.jj_scanpos = token;
            if (jj_scan_token(109)) {
                return true;
            }
        }
        return jj_3R_158();
    }

    private final boolean jj_3R_166() {
        if (jj_3R_168()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_179()) {
            this.jj_scanpos = token;
        }
        return false;
    }

    private final boolean jj_3R_167() {
        return jj_scan_token(110) || jj_3R_161();
    }

    private final boolean jj_3R_168() {
        Token token;
        if (jj_3R_170()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_182());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_169() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(106)) {
            this.jj_scanpos = token;
            if (jj_scan_token(107)) {
                return true;
            }
        }
        return jj_3R_164();
    }

    private final boolean jj_3R_170() {
        Token token;
        if (jj_3R_178()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_192());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_171() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(90)) {
            this.jj_scanpos = token;
            if (jj_scan_token(95)) {
                return true;
            }
        }
        return jj_3R_166();
    }

    private final boolean jj_3R_172() {
        return jj_scan_token(25) || jj_3R_29();
    }

    private final boolean jj_3R_173() {
        return jj_scan_token(33) || jj_3R_76();
    }

    private final boolean jj_3R_174() {
        return jj_scan_token(54) || jj_3R_76();
    }

    private final boolean jj_3R_175() {
        return jj_3R_38();
    }

    private final boolean jj_3R_176() {
        if (jj_scan_token(69)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_180()) {
            this.jj_scanpos = token;
        }
        return false;
    }

    private final boolean jj_3R_177() {
        return jj_scan_token(79) || jj_3R_176();
    }

    private final boolean jj_3R_178() {
        Token token;
        if (jj_3R_181()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_200());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_179() {
        return jj_scan_token(35) || jj_3R_32();
    }

    private final boolean jj_3R_180() {
        return jj_scan_token(81) || jj_3R_31();
    }

    private final boolean jj_3R_181() {
        Token token;
        if (jj_3R_191()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_209());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_182() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(84)) {
            this.jj_scanpos = token;
            if (jj_scan_token(85)) {
                this.jj_scanpos = token;
                if (jj_scan_token(82)) {
                    this.jj_scanpos = token;
                    if (jj_scan_token(83)) {
                        this.jj_scanpos = token;
                        if (jj_scan_token(91)) {
                            this.jj_scanpos = token;
                            if (jj_scan_token(92)) {
                                this.jj_scanpos = token;
                                if (jj_scan_token(93)) {
                                    this.jj_scanpos = token;
                                    if (jj_scan_token(94)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return jj_3R_170();
    }

    private final boolean jj_3R_183() {
        Token token;
        if (jj_3R_193()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3_29());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_184() {
        return jj_scan_token(23) || jj_3R_45();
    }

    private final boolean jj_3R_185() {
        return jj_3R_194();
    }

    private final boolean jj_3R_186() {
        return jj_3R_39();
    }

    private final boolean jj_3R_187() {
        return jj_3R_195();
    }

    private final boolean jj_3R_188() {
        return jj_3R_39();
    }

    private final boolean jj_3R_189() {
        return jj_scan_token(16) || jj_scan_token(72) || jj_3R_109() || jj_scan_token(73) || jj_3R_38();
    }

    private final boolean jj_3R_190() {
        return jj_scan_token(28) || jj_3R_38();
    }

    private final boolean jj_3R_191() {
        Token token = this.jj_scanpos;
        if (jj_3R_196()) {
            this.jj_scanpos = token;
            if (jj_3R_197()) {
                this.jj_scanpos = token;
                if (jj_3R_198()) {
                    this.jj_scanpos = token;
                    if (jj_3R_199()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_192() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(112)) {
            this.jj_scanpos = token;
            if (jj_scan_token(113)) {
                this.jj_scanpos = token;
                if (jj_scan_token(114)) {
                    this.jj_scanpos = token;
                    if (jj_scan_token(115)) {
                        this.jj_scanpos = token;
                        if (jj_scan_token(116)) {
                            this.jj_scanpos = token;
                            if (jj_scan_token(117)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return jj_3R_178();
    }

    private final boolean jj_3R_193() {
        Token token = this.jj_scanpos;
        if (jj_3R_201()) {
            this.jj_scanpos = token;
            if (jj_3R_202()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_194() {
        Token token = this.jj_scanpos;
        if (jj_3R_203()) {
            this.jj_scanpos = token;
            if (jj_3R_204()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_195() {
        return jj_3R_205();
    }

    private final boolean jj_3R_196() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(102)) {
            this.jj_scanpos = token;
            if (jj_scan_token(103)) {
                return true;
            }
        }
        return jj_3R_191();
    }

    private final boolean jj_3R_197() {
        return jj_3R_206();
    }

    private final boolean jj_3R_198() {
        return jj_3R_207();
    }

    private final boolean jj_3R_199() {
        return jj_3R_208();
    }

    private final boolean jj_3R_200() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(102)) {
            this.jj_scanpos = token;
            if (jj_scan_token(103)) {
                return true;
            }
        }
        return jj_3R_181();
    }

    private final boolean jj_3R_201() {
        return jj_scan_token(15) || jj_3R_39() || jj_scan_token(89);
    }

    private final boolean jj_3R_202() {
        return jj_scan_token(20) || jj_scan_token(89);
    }

    private final boolean jj_3R_203() {
        return jj_3R_93();
    }

    private final boolean jj_3R_204() {
        return jj_3R_205();
    }

    private final boolean jj_3R_205() {
        Token token;
        if (jj_3R_112()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_210());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_206() {
        return jj_scan_token(100) || jj_3R_33();
    }

    private final boolean jj_3R_207() {
        return jj_scan_token(101) || jj_3R_33();
    }

    private final boolean jj_3R_208() {
        Token token = this.jj_scanpos;
        if (jj_3R_211()) {
            this.jj_scanpos = token;
            if (jj_3R_212()) {
                this.jj_scanpos = token;
                if (jj_3R_213()) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_209() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(104)) {
            this.jj_scanpos = token;
            if (jj_scan_token(105)) {
                this.jj_scanpos = token;
                if (jj_scan_token(111)) {
                    return true;
                }
            }
        }
        return jj_3R_191();
    }

    private final boolean jj_3R_210() {
        return jj_scan_token(79) || jj_3R_112();
    }

    private final boolean jj_3R_211() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(87)) {
            this.jj_scanpos = token;
            if (jj_scan_token(86)) {
                return true;
            }
        }
        return jj_3R_191();
    }

    private final boolean jj_3R_212() {
        return jj_3R_214();
    }

    private final boolean jj_3R_213() {
        return jj_3R_215();
    }

    private final boolean jj_3R_214() {
        Token token = this.jj_scanpos;
        if (jj_3R_216()) {
            this.jj_scanpos = token;
            if (jj_3R_217()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_215() {
        Token token = this.jj_scanpos;
        if (jj_3R_218()) {
            this.jj_scanpos = token;
            if (jj_3R_219()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_216() {
        return jj_scan_token(72) || jj_3R_32() || jj_scan_token(73) || jj_3R_191();
    }

    private final boolean jj_3R_217() {
        return jj_scan_token(72) || jj_3R_32() || jj_scan_token(73) || jj_3R_208();
    }

    private final boolean jj_3R_218() {
        if (jj_3R_33()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_scan_token(100)) {
            this.jj_scanpos = token;
            if (jj_scan_token(101)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_219() {
        return jj_3R_33();
    }

    private final boolean jj_3R_28() {
        Token token = this.jj_scanpos;
        if (jj_3R_46()) {
            this.jj_scanpos = token;
            if (jj_3R_47()) {
                this.jj_scanpos = token;
                if (jj_3R_48()) {
                    this.jj_scanpos = token;
                    if (jj_3R_49()) {
                        this.jj_scanpos = token;
                        if (jj_3_28()) {
                            this.jj_scanpos = token;
                            if (jj_3R_50()) {
                                this.jj_scanpos = token;
                                if (jj_3R_51()) {
                                    this.jj_scanpos = token;
                                    if (jj_3R_52()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_29() {
        Token token;
        if (jj_scan_token(69)) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3_7());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_30() {
        return jj_scan_token(80) || jj_scan_token(104);
    }

    private final boolean jj_3R_31() {
        Token token = this.jj_scanpos;
        if (jj_3R_53()) {
            this.jj_scanpos = token;
            if (jj_3R_54()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_32() {
        Token token;
        Token token2 = this.jj_scanpos;
        if (jj_3R_55()) {
            this.jj_scanpos = token2;
            if (jj_3R_56()) {
                return true;
            }
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3_6());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_33() {
        Token token;
        if (jj_3R_57()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_58());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_34() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(81)) {
            this.jj_scanpos = token;
            if (jj_scan_token(ParserConstants.STARASSIGN)) {
                this.jj_scanpos = token;
                if (jj_scan_token(ParserConstants.SLASHASSIGN)) {
                    this.jj_scanpos = token;
                    if (jj_scan_token(ParserConstants.MODASSIGN)) {
                        this.jj_scanpos = token;
                        if (jj_scan_token(118)) {
                            this.jj_scanpos = token;
                            if (jj_scan_token(ParserConstants.MINUSASSIGN)) {
                                this.jj_scanpos = token;
                                if (jj_scan_token(ParserConstants.ANDASSIGN)) {
                                    this.jj_scanpos = token;
                                    if (jj_scan_token(ParserConstants.XORASSIGN)) {
                                        this.jj_scanpos = token;
                                        if (jj_scan_token(ParserConstants.ORASSIGN)) {
                                            this.jj_scanpos = token;
                                            if (jj_scan_token(ParserConstants.LSHIFTASSIGN)) {
                                                this.jj_scanpos = token;
                                                if (jj_scan_token(ParserConstants.LSHIFTASSIGNX)) {
                                                    this.jj_scanpos = token;
                                                    if (jj_scan_token(ParserConstants.RSIGNEDSHIFTASSIGN)) {
                                                        this.jj_scanpos = token;
                                                        if (jj_scan_token(ParserConstants.RSIGNEDSHIFTASSIGNX)) {
                                                            this.jj_scanpos = token;
                                                            if (jj_scan_token(ParserConstants.RUNSIGNEDSHIFTASSIGN)) {
                                                                this.jj_scanpos = token;
                                                                if (jj_scan_token(ParserConstants.RUNSIGNEDSHIFTASSIGNX)) {
                                                                    return true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_35() {
        Token token = this.jj_scanpos;
        if (jj_3_10()) {
            this.jj_scanpos = token;
            if (jj_3R_59()) {
                this.jj_scanpos = token;
                if (jj_3R_60()) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_36() {
        Token token = this.jj_scanpos;
        if (jj_3R_61()) {
            this.jj_scanpos = token;
            if (jj_3R_62()) {
                this.jj_scanpos = token;
                if (jj_3R_63()) {
                    this.jj_scanpos = token;
                    if (jj_3R_64()) {
                        this.jj_scanpos = token;
                        if (jj_3R_65()) {
                            this.jj_scanpos = token;
                            if (jj_3R_66()) {
                                this.jj_scanpos = token;
                                if (jj_3R_67()) {
                                    this.jj_scanpos = token;
                                    if (jj_3R_68()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_37() {
        return jj_3R_29() || jj_3R_69();
    }

    private final boolean jj_3R_38() {
        Token token;
        if (jj_scan_token(74)) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3_23());
        this.jj_scanpos = token;
        return jj_scan_token(75);
    }

    private final boolean jj_3R_39() {
        Token token = this.jj_scanpos;
        if (jj_3R_70()) {
            this.jj_scanpos = token;
            if (jj_3R_71()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_40() {
        return jj_scan_token(69) || jj_scan_token(89) || jj_3R_45();
    }

    private final boolean jj_3R_41() {
        Token token;
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_72());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_42() {
        Token token = this.jj_scanpos;
        if (jj_3R_73()) {
            this.jj_scanpos = token;
            if (jj_3R_74()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_43() {
        if (jj_scan_token(72)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_75()) {
            this.jj_scanpos = token;
        }
        return jj_scan_token(73);
    }

    private final boolean jj_3R_44() {
        return jj_scan_token(54) || jj_3R_76();
    }

    private final boolean jj_3R_45() {
        Token token = this.jj_scanpos;
        if (jj_3_22()) {
            this.jj_scanpos = token;
            if (jj_3R_77()) {
                this.jj_scanpos = token;
                if (jj_scan_token(78)) {
                    this.jj_scanpos = token;
                    if (jj_3R_78()) {
                        this.jj_scanpos = token;
                        if (jj_3R_79()) {
                            this.jj_scanpos = token;
                            if (jj_3R_80()) {
                                this.jj_scanpos = token;
                                if (jj_3R_81()) {
                                    this.jj_scanpos = token;
                                    if (jj_3R_82()) {
                                        this.jj_scanpos = token;
                                        this.lookingAhead = true;
                                        this.jj_semLA = isRegularForStatement();
                                        this.lookingAhead = false;
                                        if (!this.jj_semLA || jj_3R_83()) {
                                            this.jj_scanpos = token;
                                            if (jj_3R_84()) {
                                                this.jj_scanpos = token;
                                                if (jj_3R_85()) {
                                                    this.jj_scanpos = token;
                                                    if (jj_3R_86()) {
                                                        this.jj_scanpos = token;
                                                        if (jj_3R_87()) {
                                                            this.jj_scanpos = token;
                                                            if (jj_3R_88()) {
                                                                this.jj_scanpos = token;
                                                                if (jj_3R_89()) {
                                                                    this.jj_scanpos = token;
                                                                    if (jj_3R_90()) {
                                                                        return true;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_46() {
        return jj_3R_91();
    }

    private final boolean jj_3R_47() {
        return jj_3R_92();
    }

    private final boolean jj_3R_48() {
        return jj_3R_92();
    }

    private final boolean jj_3R_49() {
        return jj_3R_93() || jj_scan_token(78);
    }

    private final boolean jj_3R_50() {
        return jj_3R_94();
    }

    private final boolean jj_3R_51() {
        return jj_3R_95();
    }

    private final boolean jj_3R_52() {
        return jj_3R_96();
    }

    private final boolean jj_3R_53() {
        return jj_3R_97();
    }

    private final boolean jj_3R_54() {
        return jj_3R_39();
    }

    private final boolean jj_3R_55() {
        return jj_3R_36();
    }

    private final boolean jj_3R_56() {
        return jj_3R_29();
    }

    private final boolean jj_3R_57() {
        Token token = this.jj_scanpos;
        if (jj_3R_98()) {
            this.jj_scanpos = token;
            if (jj_3R_99()) {
                this.jj_scanpos = token;
                if (jj_3R_100()) {
                    this.jj_scanpos = token;
                    if (jj_3R_101()) {
                        this.jj_scanpos = token;
                        if (jj_3R_102()) {
                            this.jj_scanpos = token;
                            if (jj_3R_103()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_58() {
        return jj_3R_104();
    }

    private final boolean jj_3R_59() {
        return jj_scan_token(72) || jj_3R_29() || jj_scan_token(76) || jj_scan_token(77);
    }

    private final boolean jj_3R_60() {
        if (jj_scan_token(72) || jj_3R_29() || jj_scan_token(73)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_scan_token(87)) {
            this.jj_scanpos = token;
            if (jj_scan_token(86)) {
                this.jj_scanpos = token;
                if (jj_scan_token(72)) {
                    this.jj_scanpos = token;
                    if (jj_scan_token(69)) {
                        this.jj_scanpos = token;
                        if (jj_scan_token(40)) {
                            this.jj_scanpos = token;
                            if (jj_3R_105()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_61() {
        return jj_scan_token(11);
    }

    private final boolean jj_3R_62() {
        return jj_scan_token(17);
    }

    private final boolean jj_3R_63() {
        return jj_scan_token(14);
    }

    private final boolean jj_3R_64() {
        return jj_scan_token(47);
    }

    private final boolean jj_3R_65() {
        return jj_scan_token(36);
    }

    private final boolean jj_3R_66() {
        return jj_scan_token(38);
    }

    private final boolean jj_3R_67() {
        return jj_scan_token(29);
    }

    private final boolean jj_3R_68() {
        return jj_scan_token(22);
    }

    private final boolean jj_3R_69() {
        if (jj_scan_token(72)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_106()) {
            this.jj_scanpos = token;
        }
        return jj_scan_token(73);
    }

    private final boolean jj_3R_70() {
        return jj_3R_107();
    }

    private final boolean jj_3R_71() {
        return jj_3R_108();
    }

    private final boolean jj_3R_72() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(43)) {
            this.jj_scanpos = token;
            if (jj_scan_token(44)) {
                this.jj_scanpos = token;
                if (jj_scan_token(45)) {
                    this.jj_scanpos = token;
                    if (jj_scan_token(51)) {
                        this.jj_scanpos = token;
                        if (jj_scan_token(27)) {
                            this.jj_scanpos = token;
                            if (jj_scan_token(39)) {
                                this.jj_scanpos = token;
                                if (jj_scan_token(52)) {
                                    this.jj_scanpos = token;
                                    if (jj_scan_token(58)) {
                                        this.jj_scanpos = token;
                                        if (jj_scan_token(10)) {
                                            this.jj_scanpos = token;
                                            if (jj_scan_token(48)) {
                                                this.jj_scanpos = token;
                                                if (jj_scan_token(49)) {
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private final boolean jj_3R_73() {
        return jj_scan_token(57);
    }

    private final boolean jj_3R_74() {
        return jj_3R_32();
    }

    private final boolean jj_3R_75() {
        Token token;
        if (jj_3R_109()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_110());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_76() {
        Token token;
        if (jj_3R_29()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_111());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_77() {
        return jj_3R_38();
    }

    private final boolean jj_3R_78() {
        return jj_3R_112() || jj_scan_token(78);
    }

    private final boolean jj_3R_79() {
        return jj_3R_113();
    }

    private final boolean jj_3R_80() {
        return jj_3R_114();
    }

    private final boolean jj_3R_81() {
        return jj_3R_115();
    }

    private final boolean jj_3R_82() {
        return jj_3R_116();
    }

    private final boolean jj_3R_83() {
        return jj_3R_117();
    }

    private final boolean jj_3R_84() {
        return jj_3R_118();
    }

    private final boolean jj_3R_85() {
        return jj_3R_119();
    }

    private final boolean jj_3R_86() {
        return jj_3R_120();
    }

    private final boolean jj_3R_87() {
        return jj_3R_121();
    }

    private final boolean jj_3R_88() {
        return jj_3R_122();
    }

    private final boolean jj_3R_89() {
        return jj_3R_123();
    }

    private final boolean jj_3R_90() {
        return jj_3R_124();
    }

    private final boolean jj_3R_91() {
        if (jj_3R_41()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_scan_token(13)) {
            this.jj_scanpos = token;
            if (jj_3R_125()) {
                return true;
            }
        }
        if (jj_scan_token(69)) {
            return true;
        }
        Token token2 = this.jj_scanpos;
        if (jj_3R_172()) {
            this.jj_scanpos = token2;
        }
        Token token3 = this.jj_scanpos;
        if (jj_3R_173()) {
            this.jj_scanpos = token3;
        }
        return jj_3R_38();
    }

    private final boolean jj_3R_92() {
        if (jj_3R_41()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_126()) {
            this.jj_scanpos = token;
            if (jj_3R_127()) {
                return true;
            }
        }
        if (jj_3R_43()) {
            return true;
        }
        Token token2 = this.jj_scanpos;
        if (jj_3R_174()) {
            this.jj_scanpos = token2;
        }
        Token token3 = this.jj_scanpos;
        if (jj_3R_175()) {
            this.jj_scanpos = token3;
            if (jj_scan_token(78)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_93() {
        Token token;
        if (jj_3R_41() || jj_3R_32() || jj_3R_176()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3R_177());
        this.jj_scanpos = token;
        return false;
    }

    private final boolean jj_3R_94() {
        Token token = this.jj_scanpos;
        if (jj_3_3()) {
            this.jj_scanpos = token;
            if (jj_3R_128()) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_95() {
        return jj_scan_token(42) || jj_3R_29();
    }

    private final boolean jj_3R_96() {
        return jj_scan_token(68);
    }

    private final boolean jj_3R_97() {
        if (jj_scan_token(74)) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_163()) {
            this.jj_scanpos = token;
        }
        Token token2 = this.jj_scanpos;
        if (jj_scan_token(79)) {
            this.jj_scanpos = token2;
        }
        return jj_scan_token(75);
    }

    private final boolean jj_3R_98() {
        return jj_3R_129();
    }

    private final boolean jj_3R_99() {
        return jj_scan_token(72) || jj_3R_39() || jj_scan_token(73);
    }

    private final boolean jj_3_1() {
        return jj_3R_28();
    }

    private final boolean jj_3_10() {
        return jj_scan_token(72) || jj_3R_36();
    }

    private final boolean jj_3_11() {
        return jj_scan_token(72) || jj_3R_29() || jj_scan_token(76);
    }

    private final boolean jj_3_12() {
        if (jj_3R_33()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_scan_token(100)) {
            this.jj_scanpos = token;
            if (jj_scan_token(101)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3_13() {
        return jj_scan_token(72) || jj_3R_36();
    }

    private final boolean jj_3_14() {
        return jj_3R_37();
    }

    private final boolean jj_3_15() {
        return jj_3R_32() || jj_scan_token(80) || jj_scan_token(13);
    }

    private final boolean jj_3_16() {
        return jj_scan_token(80) || jj_scan_token(13);
    }

    private final boolean jj_3_17() {
        return jj_3R_38();
    }

    private final boolean jj_3_18() {
        return jj_scan_token(40) || jj_3R_36() || jj_3R_150();
    }

    private final boolean jj_3_19() {
        return jj_scan_token(76) || jj_3R_39() || jj_scan_token(77);
    }

    private final boolean jj_3_2() {
        return jj_scan_token(69) || jj_scan_token(72);
    }

    private final boolean jj_3_20() {
        return jj_scan_token(76) || jj_scan_token(77);
    }

    private final boolean jj_3_21() {
        Token token;
        Token token2;
        if (jj_3_19()) {
            return true;
        }
        do {
            token = this.jj_scanpos;
        } while (!jj_3_19());
        this.jj_scanpos = token;
        do {
            token2 = this.jj_scanpos;
        } while (!jj_3_20());
        this.jj_scanpos = token2;
        return false;
    }

    private final boolean jj_3_22() {
        return jj_3R_40();
    }

    private final boolean jj_3_23() {
        return jj_3R_28();
    }

    private final boolean jj_3_24() {
        if (jj_3R_41()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_scan_token(13)) {
            this.jj_scanpos = token;
            if (jj_scan_token(37)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3_25() {
        return jj_3R_41() || jj_3R_42() || jj_scan_token(69) || jj_scan_token(72);
    }

    private final boolean jj_3_26() {
        if (jj_3R_41() || jj_scan_token(69) || jj_3R_43()) {
            return true;
        }
        Token token = this.jj_scanpos;
        if (jj_3R_44()) {
            this.jj_scanpos = token;
        }
        return jj_scan_token(74);
    }

    private final boolean jj_3_27() {
        return jj_3R_41() || jj_3R_32() || jj_scan_token(69);
    }

    private final boolean jj_3_28() {
        return jj_3R_45();
    }

    private final boolean jj_3_29() {
        return jj_3R_28();
    }

    private final boolean jj_3_3() {
        Token token = this.jj_scanpos;
        if (jj_scan_token(48)) {
            this.jj_scanpos = token;
        }
        if (jj_scan_token(34) || jj_3R_29()) {
            return true;
        }
        Token token2 = this.jj_scanpos;
        if (jj_3R_30()) {
            this.jj_scanpos = token2;
        }
        return jj_scan_token(78);
    }

    private final boolean jj_3_30() {
        return jj_scan_token(30) || jj_scan_token(72) || jj_scan_token(69) || jj_scan_token(89) || jj_3R_39() || jj_scan_token(73) || jj_3R_45();
    }

    private final boolean jj_3_31() {
        return jj_3R_41() || jj_3R_32() || jj_scan_token(69);
    }

    private final boolean jj_3_4() {
        return jj_scan_token(79) || jj_3R_31();
    }

    private final boolean jj_3_5() {
        return jj_3R_32() || jj_scan_token(69);
    }

    private final boolean jj_3_6() {
        return jj_scan_token(76) || jj_scan_token(77);
    }

    private final boolean jj_3_7() {
        return jj_scan_token(80) || jj_scan_token(69);
    }

    private final boolean jj_3_8() {
        return jj_3R_33() || jj_3R_34();
    }

    private final boolean jj_3_9() {
        return jj_3R_35();
    }

    private final Token jj_consume_token(int i) throws ParseException {
        Token token = this.token;
        if (token.next != null) {
            this.token = this.token.next;
        } else {
            Token token2 = this.token;
            Token nextToken = this.token_source.getNextToken();
            token2.next = nextToken;
            this.token = nextToken;
        }
        this.jj_ntk = -1;
        if (this.token.kind == i) {
            return this.token;
        }
        this.token = token;
        throw generateParseException();
    }

    private final int jj_ntk() {
        Token token = this.token.next;
        this.jj_nt = token;
        if (token != null) {
            int i = this.jj_nt.kind;
            this.jj_ntk = i;
            return i;
        }
        Token token2 = this.token;
        Token nextToken = this.token_source.getNextToken();
        token2.next = nextToken;
        int i2 = nextToken.kind;
        this.jj_ntk = i2;
        return i2;
    }

    private final boolean jj_scan_token(int i) {
        if (this.jj_scanpos == this.jj_lastpos) {
            this.jj_la--;
            if (this.jj_scanpos.next == null) {
                Token token = this.jj_scanpos;
                Token nextToken = this.token_source.getNextToken();
                token.next = nextToken;
                this.jj_scanpos = nextToken;
                this.jj_lastpos = nextToken;
            } else {
                Token token2 = this.jj_scanpos.next;
                this.jj_scanpos = token2;
                this.jj_lastpos = token2;
            }
        } else {
            this.jj_scanpos = this.jj_scanpos.next;
        }
        if (this.jj_scanpos.kind != i) {
            return true;
        }
        if (this.jj_la == 0 && this.jj_scanpos == this.jj_lastpos) {
            throw this.jj_ls;
        }
        return false;
    }

    public static void main(String[] strArr) throws IOException, ParseException {
        boolean z;
        int i = 0;
        if (strArr[0].equals("-p")) {
            i = 1;
            z = true;
        } else {
            z = false;
        }
        while (i < strArr.length) {
            Parser parser = new Parser(new FileReader(strArr[i]));
            parser.setRetainComments(true);
            while (!parser.Line()) {
                if (z) {
                    System.out.println(parser.popNode());
                }
            }
            i++;
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0005 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void AdditiveExpression() throws Throwable {
        Token tokenJj_consume_token;
        MultiplicativeExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 102:
                case 103:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 102:
                            tokenJj_consume_token = jj_consume_token(102);
                            break;
                        case 103:
                            tokenJj_consume_token = jj_consume_token(103);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    MultiplicativeExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0052  */
    public final void AllocationExpression() throws Throwable {
        boolean z;
        BSHAllocationExpression bSHAllocationExpression = new BSHAllocationExpression(23);
        this.jjtree.openNodeScope(bSHAllocationExpression);
        jjtreeOpenNodeScope(bSHAllocationExpression);
        try {
            try {
                if (jj_2_18(2)) {
                    jj_consume_token(40);
                    PrimitiveType();
                    ArrayDimensions();
                } else {
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 40:
                            jj_consume_token(40);
                            AmbiguousName();
                            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                                case 72:
                                    Arguments();
                                    if (jj_2_17(2)) {
                                        Block();
                                    }
                                    break;
                                case 76:
                                    ArrayDimensions();
                                    break;
                                default:
                                    jj_consume_token(-1);
                                    throw new ParseException();
                            }
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
                this.jjtree.closeNodeScope((Node) bSHAllocationExpression, true);
                jjtreeCloseNodeScope(bSHAllocationExpression);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHAllocationExpression);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHAllocationExpression, true);
                        jjtreeCloseNodeScope(bSHAllocationExpression);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHAllocationExpression, true);
                jjtreeCloseNodeScope(bSHAllocationExpression);
            }
            throw th;
        }
    }

    public final void AmbiguousName() throws Throwable {
        boolean z;
        BSHAmbiguousName bSHAmbiguousName = new BSHAmbiguousName(12);
        this.jjtree.openNodeScope(bSHAmbiguousName);
        jjtreeOpenNodeScope(bSHAmbiguousName);
        try {
            StringBuffer stringBuffer = new StringBuffer(jj_consume_token(69).image);
            while (jj_2_7(2)) {
                jj_consume_token(80);
                stringBuffer.append(new StringBuffer().append(".").append(jj_consume_token(69).image).toString());
            }
            this.jjtree.closeNodeScope((Node) bSHAmbiguousName, true);
            z = false;
            try {
                jjtreeCloseNodeScope(bSHAmbiguousName);
                bSHAmbiguousName.text = stringBuffer.toString();
            } catch (Throwable th) {
                th = th;
                if (z) {
                    this.jjtree.closeNodeScope((Node) bSHAmbiguousName, true);
                    jjtreeCloseNodeScope(bSHAmbiguousName);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0005 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void AndExpression() throws Throwable {
        Token tokenJj_consume_token;
        EqualityExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 106:
                case 107:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 106:
                            tokenJj_consume_token = jj_consume_token(106);
                            break;
                        case 107:
                            tokenJj_consume_token = jj_consume_token(107);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    EqualityExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public final void ArgumentList() throws Throwable {
        Expression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 79:
                    jj_consume_token(79);
                    Expression();
                    break;
                default:
                    return;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0047  */
    public final void Arguments() throws Throwable {
        boolean z;
        BSHArguments bSHArguments = new BSHArguments(22);
        this.jjtree.openNodeScope(bSHArguments);
        jjtreeOpenNodeScope(bSHArguments);
        try {
            try {
                jj_consume_token(72);
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 11:
                    case 14:
                    case 17:
                    case 22:
                    case 26:
                    case 29:
                    case 36:
                    case 38:
                    case 40:
                    case 41:
                    case 47:
                    case 55:
                    case 57:
                    case 60:
                    case 64:
                    case 66:
                    case 67:
                    case 69:
                    case 72:
                    case 86:
                    case 87:
                    case 100:
                    case 101:
                    case 102:
                    case 103:
                        ArgumentList();
                        break;
                }
                jj_consume_token(73);
                this.jjtree.closeNodeScope((Node) bSHArguments, true);
                jjtreeCloseNodeScope(bSHArguments);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHArguments);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHArguments, true);
                        jjtreeCloseNodeScope(bSHArguments);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHArguments, true);
                jjtreeCloseNodeScope(bSHArguments);
            }
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:20:0x0055  */
    public final void ArrayDimensions() throws Throwable {
        boolean z;
        BSHArrayDimensions bSHArrayDimensions = new BSHArrayDimensions(24);
        this.jjtree.openNodeScope(bSHArrayDimensions);
        jjtreeOpenNodeScope(bSHArrayDimensions);
        try {
            try {
                if (jj_2_21(2)) {
                    do {
                        jj_consume_token(76);
                        Expression();
                        jj_consume_token(77);
                        bSHArrayDimensions.addDefinedDimension();
                    } while (jj_2_19(2));
                    while (jj_2_20(2)) {
                        jj_consume_token(76);
                        jj_consume_token(77);
                        bSHArrayDimensions.addUndefinedDimension();
                    }
                } else {
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 76:
                            while (true) {
                                jj_consume_token(76);
                                jj_consume_token(77);
                                bSHArrayDimensions.addUndefinedDimension();
                                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                                    case 76:
                                        break;
                                    default:
                                        ArrayInitializer();
                                        break;
                                }
                            }
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
                this.jjtree.closeNodeScope((Node) bSHArrayDimensions, true);
                jjtreeCloseNodeScope(bSHArrayDimensions);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHArrayDimensions);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHArrayDimensions, true);
                        jjtreeCloseNodeScope(bSHArrayDimensions);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHArrayDimensions, true);
                jjtreeCloseNodeScope(bSHArrayDimensions);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0060  */
    public final void ArrayInitializer() throws Throwable {
        boolean z;
        BSHArrayInitializer bSHArrayInitializer = new BSHArrayInitializer(6);
        this.jjtree.openNodeScope(bSHArrayInitializer);
        jjtreeOpenNodeScope(bSHArrayInitializer);
        try {
            try {
                jj_consume_token(74);
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 11:
                    case 14:
                    case 17:
                    case 22:
                    case 26:
                    case 29:
                    case 36:
                    case 38:
                    case 40:
                    case 41:
                    case 47:
                    case 55:
                    case 57:
                    case 60:
                    case 64:
                    case 66:
                    case 67:
                    case 69:
                    case 72:
                    case 74:
                    case 86:
                    case 87:
                    case 100:
                    case 101:
                    case 102:
                    case 103:
                        VariableInitializer();
                        while (jj_2_4(2)) {
                            jj_consume_token(79);
                            VariableInitializer();
                        }
                        break;
                }
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 79:
                        jj_consume_token(79);
                        break;
                }
                jj_consume_token(75);
                this.jjtree.closeNodeScope((Node) bSHArrayInitializer, true);
                jjtreeCloseNodeScope(bSHArrayInitializer);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHArrayInitializer);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHArrayInitializer, true);
                        jjtreeCloseNodeScope(bSHArrayInitializer);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHArrayInitializer, true);
                jjtreeCloseNodeScope(bSHArrayInitializer);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0036  */
    public final void Assignment() throws Throwable {
        boolean z;
        BSHAssignment bSHAssignment = new BSHAssignment(13);
        this.jjtree.openNodeScope(bSHAssignment);
        jjtreeOpenNodeScope(bSHAssignment);
        try {
            try {
                PrimaryExpression();
                bSHAssignment.operator = AssignmentOperator();
                Expression();
                this.jjtree.closeNodeScope((Node) bSHAssignment, true);
                jjtreeCloseNodeScope(bSHAssignment);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHAssignment);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHAssignment, true);
                        jjtreeCloseNodeScope(bSHAssignment);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHAssignment, true);
                jjtreeCloseNodeScope(bSHAssignment);
            }
            throw th;
        }
    }

    public final int AssignmentOperator() throws ParseException {
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 81:
                jj_consume_token(81);
                break;
            case 118:
                jj_consume_token(118);
                break;
            case ParserConstants.MINUSASSIGN /* 119 */:
                jj_consume_token(ParserConstants.MINUSASSIGN);
                break;
            case ParserConstants.STARASSIGN /* 120 */:
                jj_consume_token(ParserConstants.STARASSIGN);
                break;
            case ParserConstants.SLASHASSIGN /* 121 */:
                jj_consume_token(ParserConstants.SLASHASSIGN);
                break;
            case ParserConstants.ANDASSIGN /* 122 */:
                jj_consume_token(ParserConstants.ANDASSIGN);
                break;
            case ParserConstants.ORASSIGN /* 124 */:
                jj_consume_token(ParserConstants.ORASSIGN);
                break;
            case ParserConstants.XORASSIGN /* 126 */:
                jj_consume_token(ParserConstants.XORASSIGN);
                break;
            case ParserConstants.MODASSIGN /* 127 */:
                jj_consume_token(ParserConstants.MODASSIGN);
                break;
            case ParserConstants.LSHIFTASSIGN /* 128 */:
                jj_consume_token(ParserConstants.LSHIFTASSIGN);
                break;
            case ParserConstants.LSHIFTASSIGNX /* 129 */:
                jj_consume_token(ParserConstants.LSHIFTASSIGNX);
                break;
            case ParserConstants.RSIGNEDSHIFTASSIGN /* 130 */:
                jj_consume_token(ParserConstants.RSIGNEDSHIFTASSIGN);
                break;
            case ParserConstants.RSIGNEDSHIFTASSIGNX /* 131 */:
                jj_consume_token(ParserConstants.RSIGNEDSHIFTASSIGNX);
                break;
            case ParserConstants.RUNSIGNEDSHIFTASSIGN /* 132 */:
                jj_consume_token(ParserConstants.RUNSIGNEDSHIFTASSIGN);
                break;
            case ParserConstants.RUNSIGNEDSHIFTASSIGNX /* 133 */:
                jj_consume_token(ParserConstants.RUNSIGNEDSHIFTASSIGNX);
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
        return getToken(0).kind;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0031  */
    public final void Block() throws Throwable {
        boolean z;
        BSHBlock bSHBlock = new BSHBlock(25);
        this.jjtree.openNodeScope(bSHBlock);
        jjtreeOpenNodeScope(bSHBlock);
        try {
            try {
                jj_consume_token(74);
                while (jj_2_23(1)) {
                    BlockStatement();
                }
                jj_consume_token(75);
                this.jjtree.closeNodeScope((Node) bSHBlock, true);
                jjtreeCloseNodeScope(bSHBlock);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHBlock);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHBlock, true);
                        jjtreeCloseNodeScope(bSHBlock);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHBlock, true);
                jjtreeCloseNodeScope(bSHBlock);
            }
            throw th;
        }
    }

    public final void BlockStatement() throws Throwable {
        if (jj_2_24(Integer.MAX_VALUE)) {
            ClassDeclaration();
            return;
        }
        if (jj_2_25(Integer.MAX_VALUE)) {
            MethodDeclaration();
            return;
        }
        if (jj_2_26(Integer.MAX_VALUE)) {
            MethodDeclaration();
            return;
        }
        if (jj_2_27(Integer.MAX_VALUE)) {
            TypedVariableDeclaration();
            jj_consume_token(78);
            return;
        }
        if (jj_2_28(1)) {
            Statement();
            return;
        }
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 34:
            case 48:
                ImportDeclaration();
                return;
            case 42:
                PackageDeclaration();
                return;
            case 68:
                FormalComment();
                return;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    public final boolean BooleanLiteral() throws ParseException {
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 26:
                jj_consume_token(26);
                return false;
            case 55:
                jj_consume_token(55);
                return true;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    public final void BreakStatement() throws Throwable {
        boolean z;
        BSHReturnStatement bSHReturnStatement = new BSHReturnStatement(35);
        this.jjtree.openNodeScope(bSHReturnStatement);
        jjtreeOpenNodeScope(bSHReturnStatement);
        try {
            jj_consume_token(12);
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 69:
                    jj_consume_token(69);
                    break;
            }
            jj_consume_token(78);
            this.jjtree.closeNodeScope((Node) bSHReturnStatement, true);
            z = false;
            try {
                jjtreeCloseNodeScope(bSHReturnStatement);
                bSHReturnStatement.kind = 12;
            } catch (Throwable th) {
                th = th;
                if (z) {
                    this.jjtree.closeNodeScope((Node) bSHReturnStatement, true);
                    jjtreeCloseNodeScope(bSHReturnStatement);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0059  */
    public final void CastExpression() throws Throwable {
        boolean z;
        BSHCastExpression bSHCastExpression = new BSHCastExpression(17);
        this.jjtree.openNodeScope(bSHCastExpression);
        jjtreeOpenNodeScope(bSHCastExpression);
        try {
            try {
                if (jj_2_13(Integer.MAX_VALUE)) {
                    jj_consume_token(72);
                    Type();
                    jj_consume_token(73);
                    UnaryExpression();
                } else {
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 72:
                            jj_consume_token(72);
                            Type();
                            jj_consume_token(73);
                            UnaryExpressionNotPlusMinus();
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
                this.jjtree.closeNodeScope((Node) bSHCastExpression, true);
                jjtreeCloseNodeScope(bSHCastExpression);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHCastExpression);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHCastExpression, true);
                        jjtreeCloseNodeScope(bSHCastExpression);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHCastExpression, true);
                jjtreeCloseNodeScope(bSHCastExpression);
            }
            throw th;
        }
    }

    public final void CastLookahead() throws Throwable {
        if (jj_2_10(2)) {
            jj_consume_token(72);
            PrimitiveType();
            return;
        }
        if (jj_2_11(Integer.MAX_VALUE)) {
            jj_consume_token(72);
            AmbiguousName();
            jj_consume_token(76);
            jj_consume_token(77);
            return;
        }
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 72:
                jj_consume_token(72);
                AmbiguousName();
                jj_consume_token(73);
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 26:
                    case 41:
                    case 55:
                    case 57:
                    case 60:
                    case 64:
                    case 66:
                    case 67:
                        Literal();
                        return;
                    case 40:
                        jj_consume_token(40);
                        return;
                    case 69:
                        jj_consume_token(69);
                        return;
                    case 72:
                        jj_consume_token(72);
                        return;
                    case 86:
                        jj_consume_token(86);
                        return;
                    case 87:
                        jj_consume_token(87);
                        return;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x003e  */
    public final void ClassDeclaration() throws Throwable {
        boolean z;
        boolean z2 = false;
        BSHClassDeclaration bSHClassDeclaration = new BSHClassDeclaration(1);
        this.jjtree.openNodeScope(bSHClassDeclaration);
        jjtreeOpenNodeScope(bSHClassDeclaration);
        try {
            Modifiers Modifiers = Modifiers(0, false);
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 13:
                    jj_consume_token(13);
                    break;
                case 37:
                    jj_consume_token(37);
                    bSHClassDeclaration.isInterface = true;
                    break;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
            Token tokenJj_consume_token = jj_consume_token(69);
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 25:
                    jj_consume_token(25);
                    AmbiguousName();
                    bSHClassDeclaration.extend = true;
                    break;
            }
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 33:
                    jj_consume_token(33);
                    bSHClassDeclaration.numInterfaces = NameList();
                    break;
            }
            Block();
            this.jjtree.closeNodeScope((Node) bSHClassDeclaration, true);
            try {
                try {
                    jjtreeCloseNodeScope(bSHClassDeclaration);
                    bSHClassDeclaration.modifiers = Modifiers;
                    bSHClassDeclaration.name = tokenJj_consume_token.image;
                } catch (Throwable th) {
                    th = th;
                    z = false;
                    try {
                        if (z) {
                            this.jjtree.clearNodeScope(bSHClassDeclaration);
                        } else {
                            this.jjtree.popNode();
                        }
                        z = th instanceof RuntimeException;
                        if (z) {
                            throw ((RuntimeException) th);
                        }
                        if (!(th instanceof ParseException)) {
                            throw ((Error) th);
                        }
                        throw ((ParseException) th);
                    } catch (Throwable th2) {
                        th = th2;
                        z2 = z;
                        if (z2) {
                            this.jjtree.closeNodeScope((Node) bSHClassDeclaration, true);
                            jjtreeCloseNodeScope(bSHClassDeclaration);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                if (z2) {
                    this.jjtree.closeNodeScope((Node) bSHClassDeclaration, true);
                    jjtreeCloseNodeScope(bSHClassDeclaration);
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            z2 = true;
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0005 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void ConditionalAndExpression() throws Throwable {
        Token tokenJj_consume_token;
        InclusiveOrExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 98:
                case 99:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 98:
                            tokenJj_consume_token = jj_consume_token(98);
                            break;
                        case 99:
                            tokenJj_consume_token = jj_consume_token(99);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    InclusiveOrExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public final void ConditionalExpression() throws Throwable {
        ConditionalOrExpression();
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 88:
                jj_consume_token(88);
                Expression();
                jj_consume_token(89);
                BSHTernaryExpression bSHTernaryExpression = new BSHTernaryExpression(14);
                this.jjtree.openNodeScope(bSHTernaryExpression);
                jjtreeOpenNodeScope(bSHTernaryExpression);
                try {
                    try {
                        ConditionalExpression();
                        this.jjtree.closeNodeScope(bSHTernaryExpression, 3);
                        jjtreeCloseNodeScope(bSHTernaryExpression);
                        return;
                    } catch (Throwable th) {
                        this.jjtree.clearNodeScope(bSHTernaryExpression);
                        if (th instanceof RuntimeException) {
                            throw ((RuntimeException) th);
                        }
                        if (!(th instanceof ParseException)) {
                            throw ((Error) th);
                        }
                        throw ((ParseException) th);
                    }
                } catch (Throwable th2) {
                    if (1 != 0) {
                        this.jjtree.closeNodeScope(bSHTernaryExpression, 3);
                        jjtreeCloseNodeScope(bSHTernaryExpression);
                    }
                    throw th2;
                }
            default:
                return;
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0005 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void ConditionalOrExpression() throws Throwable {
        Token tokenJj_consume_token;
        ConditionalAndExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 96:
                case 97:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 96:
                            tokenJj_consume_token = jj_consume_token(96);
                            break;
                        case 97:
                            tokenJj_consume_token = jj_consume_token(97);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    ConditionalAndExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public final void ContinueStatement() throws Throwable {
        boolean z;
        BSHReturnStatement bSHReturnStatement = new BSHReturnStatement(35);
        this.jjtree.openNodeScope(bSHReturnStatement);
        jjtreeOpenNodeScope(bSHReturnStatement);
        try {
            jj_consume_token(19);
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 69:
                    jj_consume_token(69);
                    break;
            }
            jj_consume_token(78);
            this.jjtree.closeNodeScope((Node) bSHReturnStatement, true);
            z = false;
            try {
                jjtreeCloseNodeScope(bSHReturnStatement);
                bSHReturnStatement.kind = 19;
            } catch (Throwable th) {
                th = th;
                if (z) {
                    this.jjtree.closeNodeScope((Node) bSHReturnStatement, true);
                    jjtreeCloseNodeScope(bSHReturnStatement);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0051  */
    public final void DoStatement() throws Throwable {
        boolean z;
        BSHWhileStatement bSHWhileStatement = new BSHWhileStatement(30);
        this.jjtree.openNodeScope(bSHWhileStatement);
        jjtreeOpenNodeScope(bSHWhileStatement);
        try {
            jj_consume_token(21);
            Statement();
            jj_consume_token(59);
            jj_consume_token(72);
            Expression();
            jj_consume_token(73);
            jj_consume_token(78);
            this.jjtree.closeNodeScope((Node) bSHWhileStatement, true);
            try {
                jjtreeCloseNodeScope(bSHWhileStatement);
                bSHWhileStatement.isDoStatement = true;
            } catch (Throwable th) {
                th = th;
                z = false;
                if (z) {
                    this.jjtree.closeNodeScope((Node) bSHWhileStatement, true);
                    jjtreeCloseNodeScope(bSHWhileStatement);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    public final void EmptyStatement() throws ParseException {
        jj_consume_token(78);
    }

    /* JADX WARN: Code duplicated, block: B:24:0x006f  */
    public final void EnhancedForStatement() throws Throwable {
        boolean z;
        boolean z2 = false;
        BSHEnhancedForStatement bSHEnhancedForStatement = new BSHEnhancedForStatement(32);
        this.jjtree.openNodeScope(bSHEnhancedForStatement);
        jjtreeOpenNodeScope(bSHEnhancedForStatement);
        try {
            try {
                try {
                    if (jj_2_30(4)) {
                        jj_consume_token(30);
                        jj_consume_token(72);
                        Token tokenJj_consume_token = jj_consume_token(69);
                        jj_consume_token(89);
                        Expression();
                        jj_consume_token(73);
                        Statement();
                        this.jjtree.closeNodeScope((Node) bSHEnhancedForStatement, true);
                        jjtreeCloseNodeScope(bSHEnhancedForStatement);
                        bSHEnhancedForStatement.varName = tokenJj_consume_token.image;
                    } else {
                        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                            case 30:
                                jj_consume_token(30);
                                jj_consume_token(72);
                                Type();
                                Token tokenJj_consume_token2 = jj_consume_token(69);
                                jj_consume_token(89);
                                Expression();
                                jj_consume_token(73);
                                Statement();
                                this.jjtree.closeNodeScope((Node) bSHEnhancedForStatement, true);
                                jjtreeCloseNodeScope(bSHEnhancedForStatement);
                                bSHEnhancedForStatement.varName = tokenJj_consume_token2.image;
                                break;
                            default:
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    z = false;
                    try {
                        if (z) {
                            this.jjtree.clearNodeScope(bSHEnhancedForStatement);
                        } else {
                            this.jjtree.popNode();
                        }
                        z = th instanceof RuntimeException;
                        if (z) {
                            throw ((RuntimeException) th);
                        }
                        if (!(th instanceof ParseException)) {
                            throw ((Error) th);
                        }
                        throw ((ParseException) th);
                    } catch (Throwable th2) {
                        th = th2;
                        z2 = z;
                        if (z2) {
                            this.jjtree.closeNodeScope((Node) bSHEnhancedForStatement, true);
                            jjtreeCloseNodeScope(bSHEnhancedForStatement);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                if (z2) {
                    this.jjtree.closeNodeScope((Node) bSHEnhancedForStatement, true);
                    jjtreeCloseNodeScope(bSHEnhancedForStatement);
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            z = true;
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0005 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void EqualityExpression() throws Throwable {
        Token tokenJj_consume_token;
        InstanceOfExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 90:
                case 95:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 90:
                            tokenJj_consume_token = jj_consume_token(90);
                            break;
                        case 95:
                            tokenJj_consume_token = jj_consume_token(95);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    InstanceOfExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0004 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void ExclusiveOrExpression() throws Throwable {
        AndExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 110:
                    Token tokenJj_consume_token = jj_consume_token(110);
                    AndExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public final void Expression() throws Throwable {
        if (jj_2_8(Integer.MAX_VALUE)) {
            Assignment();
            return;
        }
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 11:
            case 14:
            case 17:
            case 22:
            case 26:
            case 29:
            case 36:
            case 38:
            case 40:
            case 41:
            case 47:
            case 55:
            case 57:
            case 60:
            case 64:
            case 66:
            case 67:
            case 69:
            case 72:
            case 86:
            case 87:
            case 100:
            case 101:
            case 102:
            case 103:
                ConditionalExpression();
                return;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    public final void ForInit() throws Throwable {
        if (jj_2_31(Integer.MAX_VALUE)) {
            TypedVariableDeclaration();
            return;
        }
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 11:
            case 14:
            case 17:
            case 22:
            case 26:
            case 29:
            case 36:
            case 38:
            case 40:
            case 41:
            case 47:
            case 55:
            case 57:
            case 60:
            case 64:
            case 66:
            case 67:
            case 69:
            case 72:
            case 86:
            case 87:
            case 100:
            case 101:
            case 102:
            case 103:
                StatementExpressionList();
                return;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0072  */
    public final void ForStatement() throws Throwable {
        boolean z;
        BSHForStatement bSHForStatement = new BSHForStatement(31);
        this.jjtree.openNodeScope(bSHForStatement);
        jjtreeOpenNodeScope(bSHForStatement);
        try {
            try {
                jj_consume_token(30);
                jj_consume_token(72);
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 10:
                    case 11:
                    case 14:
                    case 17:
                    case 22:
                    case 26:
                    case 27:
                    case 29:
                    case 36:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 43:
                    case 44:
                    case 45:
                    case 47:
                    case 48:
                    case 49:
                    case 51:
                    case 52:
                    case 55:
                    case 57:
                    case 58:
                    case 60:
                    case 64:
                    case 66:
                    case 67:
                    case 69:
                    case 72:
                    case 86:
                    case 87:
                    case 100:
                    case 101:
                    case 102:
                    case 103:
                        ForInit();
                        bSHForStatement.hasForInit = true;
                        break;
                }
                jj_consume_token(78);
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 11:
                    case 14:
                    case 17:
                    case 22:
                    case 26:
                    case 29:
                    case 36:
                    case 38:
                    case 40:
                    case 41:
                    case 47:
                    case 55:
                    case 57:
                    case 60:
                    case 64:
                    case 66:
                    case 67:
                    case 69:
                    case 72:
                    case 86:
                    case 87:
                    case 100:
                    case 101:
                    case 102:
                    case 103:
                        Expression();
                        bSHForStatement.hasExpression = true;
                        break;
                }
                jj_consume_token(78);
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 11:
                    case 14:
                    case 17:
                    case 22:
                    case 26:
                    case 29:
                    case 36:
                    case 38:
                    case 40:
                    case 41:
                    case 47:
                    case 55:
                    case 57:
                    case 60:
                    case 64:
                    case 66:
                    case 67:
                    case 69:
                    case 72:
                    case 86:
                    case 87:
                    case 100:
                    case 101:
                    case 102:
                    case 103:
                        ForUpdate();
                        bSHForStatement.hasForUpdate = true;
                        break;
                }
                jj_consume_token(73);
                Statement();
                this.jjtree.closeNodeScope((Node) bSHForStatement, true);
                jjtreeCloseNodeScope(bSHForStatement);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHForStatement);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHForStatement, true);
                        jjtreeCloseNodeScope(bSHForStatement);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHForStatement, true);
                jjtreeCloseNodeScope(bSHForStatement);
            }
            throw th;
        }
    }

    public final void ForUpdate() throws Throwable {
        StatementExpressionList();
    }

    public final void FormalComment() throws ParseException {
        BSHFormalComment bSHFormalComment = new BSHFormalComment(26);
        boolean z = true;
        this.jjtree.openNodeScope(bSHFormalComment);
        jjtreeOpenNodeScope(bSHFormalComment);
        try {
            Token tokenJj_consume_token = jj_consume_token(68);
            this.jjtree.closeNodeScope(bSHFormalComment, this.retainComments);
            z = false;
            jjtreeCloseNodeScope(bSHFormalComment);
            bSHFormalComment.text = tokenJj_consume_token.image;
        } catch (Throwable th) {
            if (z) {
                this.jjtree.closeNodeScope(bSHFormalComment, this.retainComments);
                jjtreeCloseNodeScope(bSHFormalComment);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0058  */
    public final void FormalParameter() throws Throwable {
        boolean z;
        boolean z2 = false;
        BSHFormalParameter bSHFormalParameter = new BSHFormalParameter(8);
        this.jjtree.openNodeScope(bSHFormalParameter);
        jjtreeOpenNodeScope(bSHFormalParameter);
        try {
            try {
                try {
                    if (jj_2_5(2)) {
                        Type();
                        Token tokenJj_consume_token = jj_consume_token(69);
                        this.jjtree.closeNodeScope((Node) bSHFormalParameter, true);
                        jjtreeCloseNodeScope(bSHFormalParameter);
                        bSHFormalParameter.name = tokenJj_consume_token.image;
                    } else {
                        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                            case 69:
                                Token tokenJj_consume_token2 = jj_consume_token(69);
                                this.jjtree.closeNodeScope((Node) bSHFormalParameter, true);
                                jjtreeCloseNodeScope(bSHFormalParameter);
                                bSHFormalParameter.name = tokenJj_consume_token2.image;
                                break;
                            default:
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    z = false;
                    try {
                        if (z) {
                            this.jjtree.clearNodeScope(bSHFormalParameter);
                        } else {
                            this.jjtree.popNode();
                        }
                        z = th instanceof RuntimeException;
                        if (z) {
                            throw ((RuntimeException) th);
                        }
                        if (!(th instanceof ParseException)) {
                            throw ((Error) th);
                        }
                        throw ((ParseException) th);
                    } catch (Throwable th2) {
                        th = th2;
                        z2 = z;
                        if (z2) {
                            this.jjtree.closeNodeScope((Node) bSHFormalParameter, true);
                            jjtreeCloseNodeScope(bSHFormalParameter);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                if (z2) {
                    this.jjtree.closeNodeScope((Node) bSHFormalParameter, true);
                    jjtreeCloseNodeScope(bSHFormalParameter);
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            z2 = true;
            if (z2) {
                this.jjtree.closeNodeScope((Node) bSHFormalParameter, true);
                jjtreeCloseNodeScope(bSHFormalParameter);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x005a  */
    public final void FormalParameters() throws Throwable {
        boolean z;
        BSHFormalParameters bSHFormalParameters = new BSHFormalParameters(7);
        this.jjtree.openNodeScope(bSHFormalParameters);
        jjtreeOpenNodeScope(bSHFormalParameters);
        try {
            try {
                jj_consume_token(72);
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 11:
                    case 14:
                    case 17:
                    case 22:
                    case 29:
                    case 36:
                    case 38:
                    case 47:
                    case 69:
                        FormalParameter();
                        while (true) {
                            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                                case 79:
                                    jj_consume_token(79);
                                    FormalParameter();
                                    break;
                            }
                        }
                        break;
                }
                jj_consume_token(73);
                this.jjtree.closeNodeScope((Node) bSHFormalParameters, true);
                jjtreeCloseNodeScope(bSHFormalParameters);
            } catch (Throwable th) {
                th = th;
                z = true;
                if (z) {
                    this.jjtree.closeNodeScope((Node) bSHFormalParameters, true);
                    jjtreeCloseNodeScope(bSHFormalParameters);
                }
                throw th;
            }
        } catch (Throwable th2) {
            this.jjtree.clearNodeScope(bSHFormalParameters);
            z = false;
            try {
                if (th2 instanceof RuntimeException) {
                    throw ((RuntimeException) th2);
                }
                if (!(th2 instanceof ParseException)) {
                    throw ((Error) th2);
                }
                throw ((ParseException) th2);
            } catch (Throwable th3) {
                th = th3;
                if (z) {
                    this.jjtree.closeNodeScope((Node) bSHFormalParameters, true);
                    jjtreeCloseNodeScope(bSHFormalParameters);
                }
                throw th;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0057  */
    public final void IfStatement() throws Throwable {
        boolean z;
        BSHIfStatement bSHIfStatement = new BSHIfStatement(29);
        this.jjtree.openNodeScope(bSHIfStatement);
        jjtreeOpenNodeScope(bSHIfStatement);
        try {
            try {
                jj_consume_token(32);
                jj_consume_token(72);
                Expression();
                jj_consume_token(73);
                Statement();
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 23:
                        jj_consume_token(23);
                        Statement();
                    default:
                        this.jjtree.closeNodeScope((Node) bSHIfStatement, true);
                        jjtreeCloseNodeScope(bSHIfStatement);
                        return;
                }
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHIfStatement);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHIfStatement, true);
                        jjtreeCloseNodeScope(bSHIfStatement);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHIfStatement, true);
                jjtreeCloseNodeScope(bSHIfStatement);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x007f  */
    public final void ImportDeclaration() throws Throwable {
        boolean z;
        Token tokenJj_consume_token;
        boolean z2 = false;
        BSHImportDeclaration bSHImportDeclaration = new BSHImportDeclaration(4);
        this.jjtree.openNodeScope(bSHImportDeclaration);
        jjtreeOpenNodeScope(bSHImportDeclaration);
        Token tokenJj_consume_token2 = null;
        try {
            try {
                try {
                    if (!jj_2_3(3)) {
                        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                            case 34:
                                jj_consume_token(34);
                                jj_consume_token(104);
                                jj_consume_token(78);
                                this.jjtree.closeNodeScope((Node) bSHImportDeclaration, true);
                                jjtreeCloseNodeScope(bSHImportDeclaration);
                                bSHImportDeclaration.superImport = true;
                                return;
                            default:
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                    }
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 48:
                            tokenJj_consume_token = jj_consume_token(48);
                            break;
                        default:
                            tokenJj_consume_token = null;
                            break;
                    }
                    jj_consume_token(34);
                    AmbiguousName();
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 80:
                            tokenJj_consume_token2 = jj_consume_token(80);
                            jj_consume_token(104);
                            break;
                    }
                    jj_consume_token(78);
                    this.jjtree.closeNodeScope((Node) bSHImportDeclaration, true);
                    jjtreeCloseNodeScope(bSHImportDeclaration);
                    if (tokenJj_consume_token != null) {
                        bSHImportDeclaration.staticImport = true;
                    }
                    if (tokenJj_consume_token2 != null) {
                        bSHImportDeclaration.importPackage = true;
                    }
                } catch (Throwable th) {
                    th = th;
                    z = false;
                    try {
                        if (z) {
                            this.jjtree.clearNodeScope(bSHImportDeclaration);
                        } else {
                            this.jjtree.popNode();
                        }
                        z = th instanceof RuntimeException;
                        if (z) {
                            throw ((RuntimeException) th);
                        }
                        if (!(th instanceof ParseException)) {
                            throw ((Error) th);
                        }
                        throw ((ParseException) th);
                    } catch (Throwable th2) {
                        th = th2;
                        z2 = z;
                        if (z2) {
                            this.jjtree.closeNodeScope((Node) bSHImportDeclaration, true);
                            jjtreeCloseNodeScope(bSHImportDeclaration);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                if (z2) {
                    this.jjtree.closeNodeScope((Node) bSHImportDeclaration, true);
                    jjtreeCloseNodeScope(bSHImportDeclaration);
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            z2 = true;
            if (z2) {
                this.jjtree.closeNodeScope((Node) bSHImportDeclaration, true);
                jjtreeCloseNodeScope(bSHImportDeclaration);
            }
            throw th;
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0005 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void InclusiveOrExpression() throws Throwable {
        Token tokenJj_consume_token;
        ExclusiveOrExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 108:
                case 109:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 108:
                            tokenJj_consume_token = jj_consume_token(108);
                            break;
                        case 109:
                            tokenJj_consume_token = jj_consume_token(109);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    ExclusiveOrExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public final void InstanceOfExpression() throws Throwable {
        RelationalExpression();
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 35:
                Token tokenJj_consume_token = jj_consume_token(35);
                Type();
                BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                boolean z = true;
                this.jjtree.openNodeScope(bSHBinaryExpression);
                jjtreeOpenNodeScope(bSHBinaryExpression);
                try {
                    this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                    z = false;
                    jjtreeCloseNodeScope(bSHBinaryExpression);
                    bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    return;
                } catch (Throwable th) {
                    if (z) {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                    }
                    throw th;
                }
            default:
                return;
        }
    }

    public final void LabeledStatement() throws ParseException {
        jj_consume_token(69);
        jj_consume_token(89);
        Statement();
    }

    public final boolean Line() throws Throwable {
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 0:
                jj_consume_token(0);
                Interpreter.debug("End of File!");
                return true;
            default:
                if (jj_2_1(1)) {
                    BlockStatement();
                    return false;
                }
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x003a  */
    public final void Literal() throws Throwable {
        boolean z;
        boolean z2 = false;
        BSHLiteral bSHLiteral = new BSHLiteral(21);
        this.jjtree.openNodeScope(bSHLiteral);
        jjtreeOpenNodeScope(bSHLiteral);
        try {
            try {
                try {
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 26:
                        case 55:
                            boolean zBooleanLiteral = BooleanLiteral();
                            this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                            jjtreeCloseNodeScope(bSHLiteral);
                            bSHLiteral.value = new Primitive(zBooleanLiteral);
                            return;
                        case 41:
                            NullLiteral();
                            this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                            jjtreeCloseNodeScope(bSHLiteral);
                            bSHLiteral.value = Primitive.NULL;
                            return;
                        case 57:
                            VoidLiteral();
                            this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                            jjtreeCloseNodeScope(bSHLiteral);
                            bSHLiteral.value = Primitive.VOID;
                            return;
                        case 60:
                            Token tokenJj_consume_token = jj_consume_token(60);
                            this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                            jjtreeCloseNodeScope(bSHLiteral);
                            String str = tokenJj_consume_token.image;
                            char cCharAt = str.charAt(str.length() - 1);
                            if (cCharAt == 'l' || cCharAt == 'L') {
                                bSHLiteral.value = new Primitive(new Long(str.substring(0, str.length() - 1)).longValue());
                                return;
                            } else {
                                try {
                                    bSHLiteral.value = new Primitive(Integer.decode(str).intValue());
                                    return;
                                } catch (NumberFormatException e) {
                                    throw createParseException(new StringBuffer().append("Error or number too big for integer type: ").append(str).toString());
                                }
                            }
                        case 64:
                            Token tokenJj_consume_token2 = jj_consume_token(64);
                            this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                            jjtreeCloseNodeScope(bSHLiteral);
                            String strSubstring = tokenJj_consume_token2.image;
                            char cCharAt2 = strSubstring.charAt(strSubstring.length() - 1);
                            if (cCharAt2 == 'f' || cCharAt2 == 'F') {
                                bSHLiteral.value = new Primitive(new Float(strSubstring.substring(0, strSubstring.length() - 1)).floatValue());
                                return;
                            }
                            if (cCharAt2 == 'd' || cCharAt2 == 'D') {
                                strSubstring = strSubstring.substring(0, strSubstring.length() - 1);
                            }
                            bSHLiteral.value = new Primitive(new Double(strSubstring).doubleValue());
                            return;
                        case 66:
                            Token tokenJj_consume_token3 = jj_consume_token(66);
                            this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                            jjtreeCloseNodeScope(bSHLiteral);
                            try {
                                bSHLiteral.charSetup(tokenJj_consume_token3.image.substring(1, tokenJj_consume_token3.image.length() - 1));
                                return;
                            } catch (Exception e2) {
                                throw createParseException(new StringBuffer().append("Error parsing character: ").append(tokenJj_consume_token3.image).toString());
                            }
                        case 67:
                            Token tokenJj_consume_token4 = jj_consume_token(67);
                            this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                            jjtreeCloseNodeScope(bSHLiteral);
                            try {
                                bSHLiteral.stringSetup(tokenJj_consume_token4.image.substring(1, tokenJj_consume_token4.image.length() - 1));
                                return;
                            } catch (Exception e3) {
                                throw createParseException(new StringBuffer().append("Error parsing string: ").append(tokenJj_consume_token4.image).toString());
                            }
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                } catch (Throwable th) {
                    th = th;
                    z = false;
                    try {
                        if (z) {
                            this.jjtree.clearNodeScope(bSHLiteral);
                        } else {
                            this.jjtree.popNode();
                        }
                        z = th instanceof RuntimeException;
                        if (z) {
                            throw ((RuntimeException) th);
                        }
                        if (!(th instanceof ParseException)) {
                            throw ((Error) th);
                        }
                        throw ((ParseException) th);
                    } catch (Throwable th2) {
                        th = th2;
                        z2 = z;
                        if (z2) {
                            this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                            jjtreeCloseNodeScope(bSHLiteral);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                if (z2) {
                    this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                    jjtreeCloseNodeScope(bSHLiteral);
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            z2 = true;
            if (z2) {
                this.jjtree.closeNodeScope((Node) bSHLiteral, true);
                jjtreeCloseNodeScope(bSHLiteral);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x005f  */
    public final void MethodDeclaration() throws Throwable {
        boolean z = false;
        BSHMethodDeclaration bSHMethodDeclaration = new BSHMethodDeclaration(2);
        this.jjtree.openNodeScope(bSHMethodDeclaration);
        jjtreeOpenNodeScope(bSHMethodDeclaration);
        try {
            try {
                bSHMethodDeclaration.modifiers = Modifiers(1, false);
                if (jj_2_2(Integer.MAX_VALUE)) {
                    bSHMethodDeclaration.name = jj_consume_token(69).image;
                } else {
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 11:
                        case 14:
                        case 17:
                        case 22:
                        case 29:
                        case 36:
                        case 38:
                        case 47:
                        case 57:
                        case 69:
                            ReturnType();
                            bSHMethodDeclaration.name = jj_consume_token(69).image;
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
                FormalParameters();
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 54:
                        jj_consume_token(54);
                        bSHMethodDeclaration.numThrows = NameList();
                        break;
                }
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 74:
                        Block();
                        break;
                    case 78:
                        jj_consume_token(78);
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                this.jjtree.closeNodeScope((Node) bSHMethodDeclaration, true);
                jjtreeCloseNodeScope(bSHMethodDeclaration);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHMethodDeclaration);
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHMethodDeclaration, true);
                        jjtreeCloseNodeScope(bSHMethodDeclaration);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHMethodDeclaration, true);
                jjtreeCloseNodeScope(bSHMethodDeclaration);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0030  */
    public final void MethodInvocation() throws Throwable {
        boolean z;
        BSHMethodInvocation bSHMethodInvocation = new BSHMethodInvocation(19);
        this.jjtree.openNodeScope(bSHMethodInvocation);
        jjtreeOpenNodeScope(bSHMethodInvocation);
        try {
            try {
                AmbiguousName();
                Arguments();
                this.jjtree.closeNodeScope((Node) bSHMethodInvocation, true);
                jjtreeCloseNodeScope(bSHMethodInvocation);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHMethodInvocation);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHMethodInvocation, true);
                        jjtreeCloseNodeScope(bSHMethodInvocation);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHMethodInvocation, true);
                jjtreeCloseNodeScope(bSHMethodInvocation);
            }
            throw th;
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0002 for B:6:0x000a already processed. Defaulting to fallback option. */
    public final Modifiers Modifiers(int i, boolean z) throws ParseException {
        Modifiers modifiers = null;
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 10:
                case 27:
                case 39:
                case 43:
                case 44:
                case 45:
                case 48:
                case 49:
                case 51:
                case 52:
                case 58:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 10:
                            jj_consume_token(10);
                            break;
                        case 27:
                            jj_consume_token(27);
                            break;
                        case 39:
                            jj_consume_token(39);
                            break;
                        case 43:
                            jj_consume_token(43);
                            break;
                        case 44:
                            jj_consume_token(44);
                            break;
                        case 45:
                            jj_consume_token(45);
                            break;
                        case 48:
                            jj_consume_token(48);
                            break;
                        case 49:
                            jj_consume_token(49);
                            break;
                        case 51:
                            jj_consume_token(51);
                            break;
                        case 52:
                            jj_consume_token(52);
                            break;
                        case 58:
                            jj_consume_token(58);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    if (!z) {
                        if (modifiers == null) {
                            try {
                                modifiers = new Modifiers();
                            } catch (IllegalStateException e) {
                                throw createParseException(e.getMessage());
                            }
                        }
                        modifiers.addModifier(i, getToken(0).image);
                        break;
                    }
                    break;
                default:
                    return modifiers;
            }
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0005 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void MultiplicativeExpression() throws Throwable {
        Token tokenJj_consume_token;
        UnaryExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 104:
                case 105:
                case 111:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 104:
                            tokenJj_consume_token = jj_consume_token(104);
                            break;
                        case 105:
                            tokenJj_consume_token = jj_consume_token(105);
                            break;
                        case 111:
                            tokenJj_consume_token = jj_consume_token(111);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    UnaryExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public final int NameList() throws Throwable {
        AmbiguousName();
        int i = 1;
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 79:
                    jj_consume_token(79);
                    AmbiguousName();
                    i++;
                    break;
                default:
                    return i;
            }
        }
    }

    public final void NullLiteral() throws ParseException {
        jj_consume_token(41);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0031  */
    public final void PackageDeclaration() throws Throwable {
        boolean z;
        BSHPackageDeclaration bSHPackageDeclaration = new BSHPackageDeclaration(3);
        this.jjtree.openNodeScope(bSHPackageDeclaration);
        jjtreeOpenNodeScope(bSHPackageDeclaration);
        try {
            try {
                jj_consume_token(42);
                AmbiguousName();
                this.jjtree.closeNodeScope((Node) bSHPackageDeclaration, true);
                jjtreeCloseNodeScope(bSHPackageDeclaration);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHPackageDeclaration);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHPackageDeclaration, true);
                        jjtreeCloseNodeScope(bSHPackageDeclaration);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHPackageDeclaration, true);
                jjtreeCloseNodeScope(bSHPackageDeclaration);
            }
            throw th;
        }
    }

    public final void PostfixExpression() throws Throwable {
        Token tokenJj_consume_token;
        boolean z;
        if (!jj_2_12(Integer.MAX_VALUE)) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 11:
                case 14:
                case 17:
                case 22:
                case 26:
                case 29:
                case 36:
                case 38:
                case 40:
                case 41:
                case 47:
                case 55:
                case 57:
                case 60:
                case 64:
                case 66:
                case 67:
                case 69:
                case 72:
                    PrimaryExpression();
                    return;
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        PrimaryExpression();
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 100:
                tokenJj_consume_token = jj_consume_token(100);
                break;
            case 101:
                tokenJj_consume_token = jj_consume_token(101);
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
        BSHUnaryExpression bSHUnaryExpression = new BSHUnaryExpression(16);
        this.jjtree.openNodeScope(bSHUnaryExpression);
        jjtreeOpenNodeScope(bSHUnaryExpression);
        try {
            this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
            z = false;
            try {
                jjtreeCloseNodeScope(bSHUnaryExpression);
                bSHUnaryExpression.kind = tokenJj_consume_token.kind;
                bSHUnaryExpression.postfix = true;
            } catch (Throwable th) {
                th = th;
                if (z) {
                    this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
                    jjtreeCloseNodeScope(bSHUnaryExpression);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    public final void PreDecrementExpression() throws Throwable {
        boolean z;
        Token tokenJj_consume_token = jj_consume_token(101);
        PrimaryExpression();
        BSHUnaryExpression bSHUnaryExpression = new BSHUnaryExpression(16);
        this.jjtree.openNodeScope(bSHUnaryExpression);
        jjtreeOpenNodeScope(bSHUnaryExpression);
        try {
            this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
            z = false;
            try {
                jjtreeCloseNodeScope(bSHUnaryExpression);
                bSHUnaryExpression.kind = tokenJj_consume_token.kind;
            } catch (Throwable th) {
                th = th;
                if (z) {
                    this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
                    jjtreeCloseNodeScope(bSHUnaryExpression);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    public final void PreIncrementExpression() throws Throwable {
        boolean z;
        Token tokenJj_consume_token = jj_consume_token(100);
        PrimaryExpression();
        BSHUnaryExpression bSHUnaryExpression = new BSHUnaryExpression(16);
        this.jjtree.openNodeScope(bSHUnaryExpression);
        jjtreeOpenNodeScope(bSHUnaryExpression);
        try {
            this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
            z = false;
            try {
                jjtreeCloseNodeScope(bSHUnaryExpression);
                bSHUnaryExpression.kind = tokenJj_consume_token.kind;
            } catch (Throwable th) {
                th = th;
                if (z) {
                    this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
                    jjtreeCloseNodeScope(bSHUnaryExpression);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0040  */
    public final void PrimaryExpression() throws Throwable {
        boolean z;
        BSHPrimaryExpression bSHPrimaryExpression = new BSHPrimaryExpression(18);
        this.jjtree.openNodeScope(bSHPrimaryExpression);
        jjtreeOpenNodeScope(bSHPrimaryExpression);
        try {
            try {
                PrimaryPrefix();
                while (true) {
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 74:
                        case 76:
                        case 80:
                            PrimarySuffix();
                            break;
                        default:
                            this.jjtree.closeNodeScope((Node) bSHPrimaryExpression, true);
                            jjtreeCloseNodeScope(bSHPrimaryExpression);
                            return;
                    }
                }
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHPrimaryExpression);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHPrimaryExpression, true);
                        jjtreeCloseNodeScope(bSHPrimaryExpression);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHPrimaryExpression, true);
                jjtreeCloseNodeScope(bSHPrimaryExpression);
            }
            throw th;
        }
    }

    public final void PrimaryPrefix() throws Throwable {
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 26:
            case 41:
            case 55:
            case 57:
            case 60:
            case 64:
            case 66:
            case 67:
                Literal();
                return;
            case 40:
                AllocationExpression();
                return;
            case 72:
                jj_consume_token(72);
                Expression();
                jj_consume_token(73);
                return;
            default:
                if (jj_2_14(Integer.MAX_VALUE)) {
                    MethodInvocation();
                    return;
                }
                if (jj_2_15(Integer.MAX_VALUE)) {
                    Type();
                    return;
                }
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 69:
                        AmbiguousName();
                        return;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0058  */
    public final void PrimarySuffix() throws Throwable {
        boolean z;
        boolean z2 = false;
        BSHPrimarySuffix bSHPrimarySuffix = new BSHPrimarySuffix(20);
        this.jjtree.openNodeScope(bSHPrimarySuffix);
        jjtreeOpenNodeScope(bSHPrimarySuffix);
        try {
            try {
                try {
                    if (jj_2_16(2)) {
                        jj_consume_token(80);
                        jj_consume_token(13);
                        this.jjtree.closeNodeScope((Node) bSHPrimarySuffix, true);
                        jjtreeCloseNodeScope(bSHPrimarySuffix);
                        bSHPrimarySuffix.operation = 0;
                    } else {
                        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                            case 74:
                                jj_consume_token(74);
                                Expression();
                                jj_consume_token(75);
                                this.jjtree.closeNodeScope((Node) bSHPrimarySuffix, true);
                                jjtreeCloseNodeScope(bSHPrimarySuffix);
                                bSHPrimarySuffix.operation = 3;
                                break;
                            case 76:
                                jj_consume_token(76);
                                Expression();
                                jj_consume_token(77);
                                this.jjtree.closeNodeScope((Node) bSHPrimarySuffix, true);
                                jjtreeCloseNodeScope(bSHPrimarySuffix);
                                bSHPrimarySuffix.operation = 1;
                                break;
                            case 80:
                                jj_consume_token(80);
                                Token tokenJj_consume_token = jj_consume_token(69);
                                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                                    case 72:
                                        Arguments();
                                        break;
                                }
                                this.jjtree.closeNodeScope((Node) bSHPrimarySuffix, true);
                                jjtreeCloseNodeScope(bSHPrimarySuffix);
                                bSHPrimarySuffix.operation = 2;
                                bSHPrimarySuffix.field = tokenJj_consume_token.image;
                                break;
                            default:
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    z = false;
                    try {
                        if (z) {
                            this.jjtree.clearNodeScope(bSHPrimarySuffix);
                        } else {
                            this.jjtree.popNode();
                        }
                        z = th instanceof RuntimeException;
                        if (z) {
                            throw ((RuntimeException) th);
                        }
                        if (!(th instanceof ParseException)) {
                            throw ((Error) th);
                        }
                        throw ((ParseException) th);
                    } catch (Throwable th2) {
                        th = th2;
                        z2 = z;
                        if (z2) {
                            this.jjtree.closeNodeScope((Node) bSHPrimarySuffix, true);
                            jjtreeCloseNodeScope(bSHPrimarySuffix);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                if (z2) {
                    this.jjtree.closeNodeScope((Node) bSHPrimarySuffix, true);
                    jjtreeCloseNodeScope(bSHPrimarySuffix);
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            z2 = true;
            if (z2) {
                this.jjtree.closeNodeScope((Node) bSHPrimarySuffix, true);
                jjtreeCloseNodeScope(bSHPrimarySuffix);
            }
            throw th;
        }
    }

    public final void PrimitiveType() throws Throwable {
        boolean z = false;
        BSHPrimitiveType bSHPrimitiveType = new BSHPrimitiveType(11);
        this.jjtree.openNodeScope(bSHPrimitiveType);
        jjtreeOpenNodeScope(bSHPrimitiveType);
        try {
            try {
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 11:
                        jj_consume_token(11);
                        this.jjtree.closeNodeScope((Node) bSHPrimitiveType, true);
                        jjtreeCloseNodeScope(bSHPrimitiveType);
                        bSHPrimitiveType.type = Boolean.TYPE;
                        break;
                    case 14:
                        jj_consume_token(14);
                        this.jjtree.closeNodeScope((Node) bSHPrimitiveType, true);
                        jjtreeCloseNodeScope(bSHPrimitiveType);
                        bSHPrimitiveType.type = Byte.TYPE;
                        break;
                    case 17:
                        jj_consume_token(17);
                        this.jjtree.closeNodeScope((Node) bSHPrimitiveType, true);
                        jjtreeCloseNodeScope(bSHPrimitiveType);
                        bSHPrimitiveType.type = Character.TYPE;
                        break;
                    case 22:
                        jj_consume_token(22);
                        this.jjtree.closeNodeScope((Node) bSHPrimitiveType, true);
                        jjtreeCloseNodeScope(bSHPrimitiveType);
                        bSHPrimitiveType.type = Double.TYPE;
                        break;
                    case 29:
                        jj_consume_token(29);
                        this.jjtree.closeNodeScope((Node) bSHPrimitiveType, true);
                        jjtreeCloseNodeScope(bSHPrimitiveType);
                        bSHPrimitiveType.type = Float.TYPE;
                        break;
                    case 36:
                        jj_consume_token(36);
                        this.jjtree.closeNodeScope((Node) bSHPrimitiveType, true);
                        jjtreeCloseNodeScope(bSHPrimitiveType);
                        bSHPrimitiveType.type = Integer.TYPE;
                        break;
                    case 38:
                        jj_consume_token(38);
                        this.jjtree.closeNodeScope((Node) bSHPrimitiveType, true);
                        jjtreeCloseNodeScope(bSHPrimitiveType);
                        bSHPrimitiveType.type = Long.TYPE;
                        break;
                    case 47:
                        jj_consume_token(47);
                        this.jjtree.closeNodeScope((Node) bSHPrimitiveType, true);
                        jjtreeCloseNodeScope(bSHPrimitiveType);
                        bSHPrimitiveType.type = Short.TYPE;
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
            } catch (Throwable th) {
                th = th;
                if (z) {
                    this.jjtree.closeNodeScope((Node) bSHPrimitiveType, true);
                    jjtreeCloseNodeScope(bSHPrimitiveType);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    public void ReInit(ParserTokenManager parserTokenManager) {
        this.token_source = parserTokenManager;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
    }

    public void ReInit(InputStream inputStream) {
        this.jj_input_stream.ReInit(inputStream, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
    }

    public void ReInit(Reader reader) {
        this.jj_input_stream.ReInit(reader, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
    }

    /* JADX WARN: Switch 'out' block B:3:0x0005 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void RelationalExpression() throws Throwable {
        Token tokenJj_consume_token;
        ShiftExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 82:
                case 83:
                case 84:
                case 85:
                case 91:
                case 92:
                case 93:
                case 94:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 82:
                            tokenJj_consume_token = jj_consume_token(82);
                            break;
                        case 83:
                            tokenJj_consume_token = jj_consume_token(83);
                            break;
                        case 84:
                            tokenJj_consume_token = jj_consume_token(84);
                            break;
                        case 85:
                            tokenJj_consume_token = jj_consume_token(85);
                            break;
                        case 86:
                        case 87:
                        case 88:
                        case 89:
                        case 90:
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                        case 91:
                            tokenJj_consume_token = jj_consume_token(91);
                            break;
                        case 92:
                            tokenJj_consume_token = jj_consume_token(92);
                            break;
                        case 93:
                            tokenJj_consume_token = jj_consume_token(93);
                            break;
                        case 94:
                            tokenJj_consume_token = jj_consume_token(94);
                            break;
                    }
                    ShiftExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                default:
                    return;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0050  */
    public final void ReturnStatement() throws Throwable {
        boolean z;
        BSHReturnStatement bSHReturnStatement = new BSHReturnStatement(35);
        this.jjtree.openNodeScope(bSHReturnStatement);
        jjtreeOpenNodeScope(bSHReturnStatement);
        try {
            jj_consume_token(46);
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 11:
                case 14:
                case 17:
                case 22:
                case 26:
                case 29:
                case 36:
                case 38:
                case 40:
                case 41:
                case 47:
                case 55:
                case 57:
                case 60:
                case 64:
                case 66:
                case 67:
                case 69:
                case 72:
                case 86:
                case 87:
                case 100:
                case 101:
                case 102:
                case 103:
                    Expression();
                    break;
            }
            jj_consume_token(78);
            this.jjtree.closeNodeScope((Node) bSHReturnStatement, true);
            try {
                jjtreeCloseNodeScope(bSHReturnStatement);
                bSHReturnStatement.kind = 46;
            } catch (Throwable th) {
                th = th;
                z = false;
                if (z) {
                    this.jjtree.closeNodeScope((Node) bSHReturnStatement, true);
                    jjtreeCloseNodeScope(bSHReturnStatement);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x003b  */
    /* JADX WARN: Code duplicated, block: B:25:0x005a  */
    /* JADX WARN: Code duplicated, block: B:46:? A[RETURN, SYNTHETIC] */
    public final void ReturnType() throws Throwable {
        boolean z;
        boolean z2 = false;
        BSHReturnType bSHReturnType = new BSHReturnType(10);
        this.jjtree.openNodeScope(bSHReturnType);
        jjtreeOpenNodeScope(bSHReturnType);
        try {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 11:
                case 14:
                case 17:
                case 22:
                case 29:
                case 36:
                case 38:
                case 47:
                case 69:
                    Type();
                    z2 = true;
                    if (z2) {
                        this.jjtree.closeNodeScope((Node) bSHReturnType, true);
                        jjtreeCloseNodeScope(bSHReturnType);
                        return;
                    }
                    return;
                case 57:
                    jj_consume_token(57);
                    this.jjtree.closeNodeScope((Node) bSHReturnType, true);
                    try {
                        jjtreeCloseNodeScope(bSHReturnType);
                        bSHReturnType.isVoid = true;
                        if (z2) {
                            this.jjtree.closeNodeScope((Node) bSHReturnType, true);
                            jjtreeCloseNodeScope(bSHReturnType);
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        z = false;
                        if (z) {
                            this.jjtree.closeNodeScope((Node) bSHReturnType, true);
                            jjtreeCloseNodeScope(bSHReturnType);
                        }
                        throw th;
                    }
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    /* JADX WARN: Switch 'out' block B:3:0x0005 for B:6:0x000d already processed. Defaulting to fallback option. */
    public final void ShiftExpression() throws Throwable {
        Token tokenJj_consume_token;
        AdditiveExpression();
        while (true) {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 112:
                case 113:
                case 114:
                case 115:
                case 116:
                case 117:
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 112:
                            tokenJj_consume_token = jj_consume_token(112);
                            break;
                        case 113:
                            tokenJj_consume_token = jj_consume_token(113);
                            break;
                        case 114:
                            tokenJj_consume_token = jj_consume_token(114);
                            break;
                        case 115:
                            tokenJj_consume_token = jj_consume_token(115);
                            break;
                        case 116:
                            tokenJj_consume_token = jj_consume_token(116);
                            break;
                        case 117:
                            tokenJj_consume_token = jj_consume_token(117);
                            break;
                        default:
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                    AdditiveExpression();
                    BSHBinaryExpression bSHBinaryExpression = new BSHBinaryExpression(15);
                    boolean z = true;
                    this.jjtree.openNodeScope(bSHBinaryExpression);
                    jjtreeOpenNodeScope(bSHBinaryExpression);
                    try {
                        this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                        z = false;
                        jjtreeCloseNodeScope(bSHBinaryExpression);
                        bSHBinaryExpression.kind = tokenJj_consume_token.kind;
                    } catch (Throwable th) {
                        if (z) {
                            this.jjtree.closeNodeScope(bSHBinaryExpression, 2);
                            jjtreeCloseNodeScope(bSHBinaryExpression);
                        }
                        throw th;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    public final void Statement() throws Throwable {
        if (jj_2_22(2)) {
            LabeledStatement();
            return;
        }
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 11:
            case 14:
            case 17:
            case 22:
            case 26:
            case 29:
            case 36:
            case 38:
            case 40:
            case 41:
            case 47:
            case 55:
            case 57:
            case 60:
            case 64:
            case 66:
            case 67:
            case 69:
            case 72:
            case 86:
            case 87:
            case 100:
            case 101:
            case 102:
            case 103:
                StatementExpression();
                jj_consume_token(78);
                return;
            case 21:
                DoStatement();
                return;
            case 32:
                IfStatement();
                return;
            case 50:
                SwitchStatement();
                return;
            case 59:
                WhileStatement();
                return;
            case 74:
                Block();
                return;
            case 78:
                EmptyStatement();
                return;
            default:
                if (isRegularForStatement()) {
                    ForStatement();
                    return;
                }
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 12:
                        BreakStatement();
                        return;
                    case 19:
                        ContinueStatement();
                        return;
                    case 30:
                        EnhancedForStatement();
                        return;
                    case 46:
                        ReturnStatement();
                        return;
                    case 51:
                        SynchronizedStatement();
                        return;
                    case 53:
                        ThrowStatement();
                        return;
                    case 56:
                        TryStatement();
                        return;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
        }
    }

    public final void StatementExpression() throws Throwable {
        Expression();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0045  */
    public final void StatementExpressionList() throws Throwable {
        boolean z;
        BSHStatementExpressionList bSHStatementExpressionList = new BSHStatementExpressionList(34);
        this.jjtree.openNodeScope(bSHStatementExpressionList);
        jjtreeOpenNodeScope(bSHStatementExpressionList);
        try {
            try {
                StatementExpression();
                while (true) {
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 79:
                            jj_consume_token(79);
                            StatementExpression();
                            break;
                        default:
                            this.jjtree.closeNodeScope((Node) bSHStatementExpressionList, true);
                            jjtreeCloseNodeScope(bSHStatementExpressionList);
                            return;
                    }
                }
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHStatementExpressionList);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHStatementExpressionList, true);
                        jjtreeCloseNodeScope(bSHStatementExpressionList);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHStatementExpressionList, true);
                jjtreeCloseNodeScope(bSHStatementExpressionList);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x003b  */
    /* JADX WARN: Code duplicated, block: B:25:0x0057  */
    /* JADX WARN: Code duplicated, block: B:48:? A[RETURN, SYNTHETIC] */
    public final void SwitchLabel() throws Throwable {
        boolean z;
        boolean z2 = false;
        BSHSwitchLabel bSHSwitchLabel = new BSHSwitchLabel(28);
        this.jjtree.openNodeScope(bSHSwitchLabel);
        jjtreeOpenNodeScope(bSHSwitchLabel);
        try {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 15:
                    jj_consume_token(15);
                    Expression();
                    jj_consume_token(89);
                    z2 = true;
                    if (z2) {
                        this.jjtree.closeNodeScope((Node) bSHSwitchLabel, true);
                        jjtreeCloseNodeScope(bSHSwitchLabel);
                        return;
                    }
                    return;
                case 20:
                    jj_consume_token(20);
                    jj_consume_token(89);
                    this.jjtree.closeNodeScope((Node) bSHSwitchLabel, true);
                    try {
                        jjtreeCloseNodeScope(bSHSwitchLabel);
                        bSHSwitchLabel.isDefault = true;
                        if (z2) {
                            this.jjtree.closeNodeScope((Node) bSHSwitchLabel, true);
                            jjtreeCloseNodeScope(bSHSwitchLabel);
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        z = false;
                        if (z) {
                            this.jjtree.closeNodeScope((Node) bSHSwitchLabel, true);
                            jjtreeCloseNodeScope(bSHSwitchLabel);
                        }
                        throw th;
                    }
                default:
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0063  */
    /* JADX WARN: Failed to find 'out' block for switch in B:7:0x0030. Please report as an issue. */
    public final void SwitchStatement() throws Throwable {
        boolean z;
        BSHSwitchStatement bSHSwitchStatement = new BSHSwitchStatement(27);
        this.jjtree.openNodeScope(bSHSwitchStatement);
        jjtreeOpenNodeScope(bSHSwitchStatement);
        try {
            try {
                jj_consume_token(50);
                jj_consume_token(72);
                Expression();
                jj_consume_token(73);
                jj_consume_token(74);
                while (true) {
                    switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                        case 15:
                        case 20:
                            SwitchLabel();
                            while (jj_2_29(1)) {
                                BlockStatement();
                            }
                            break;
                    }
                    jj_consume_token(75);
                    this.jjtree.closeNodeScope((Node) bSHSwitchStatement, true);
                    jjtreeCloseNodeScope(bSHSwitchStatement);
                    return;
                }
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHSwitchStatement);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHSwitchStatement, true);
                        jjtreeCloseNodeScope(bSHSwitchStatement);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHSwitchStatement, true);
                jjtreeCloseNodeScope(bSHSwitchStatement);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0047  */
    public final void SynchronizedStatement() throws Throwable {
        boolean z;
        BSHBlock bSHBlock = new BSHBlock(25);
        this.jjtree.openNodeScope(bSHBlock);
        jjtreeOpenNodeScope(bSHBlock);
        try {
            jj_consume_token(51);
            jj_consume_token(72);
            Expression();
            jj_consume_token(73);
            Block();
            this.jjtree.closeNodeScope((Node) bSHBlock, true);
            try {
                jjtreeCloseNodeScope(bSHBlock);
                bSHBlock.isSynchronized = true;
            } catch (Throwable th) {
                th = th;
                z = false;
                try {
                    if (z) {
                        this.jjtree.clearNodeScope(bSHBlock);
                    } else {
                        this.jjtree.popNode();
                    }
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHBlock, true);
                        jjtreeCloseNodeScope(bSHBlock);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0037  */
    public final void ThrowStatement() throws Throwable {
        boolean z;
        BSHThrowStatement bSHThrowStatement = new BSHThrowStatement(36);
        this.jjtree.openNodeScope(bSHThrowStatement);
        jjtreeOpenNodeScope(bSHThrowStatement);
        try {
            try {
                jj_consume_token(53);
                Expression();
                jj_consume_token(78);
                this.jjtree.closeNodeScope((Node) bSHThrowStatement, true);
                jjtreeCloseNodeScope(bSHThrowStatement);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHThrowStatement);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHThrowStatement, true);
                        jjtreeCloseNodeScope(bSHThrowStatement);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHThrowStatement, true);
                jjtreeCloseNodeScope(bSHThrowStatement);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0055  */
    /* JADX WARN: Failed to find 'out' block for switch in B:7:0x0023. Please report as an issue. */
    public final void TryStatement() throws Throwable {
        boolean z;
        BSHTryStatement bSHTryStatement = new BSHTryStatement(37);
        this.jjtree.openNodeScope(bSHTryStatement);
        jjtreeOpenNodeScope(bSHTryStatement);
        try {
            jj_consume_token(56);
            Block();
            boolean z2 = false;
            while (true) {
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 16:
                        jj_consume_token(16);
                        jj_consume_token(72);
                        FormalParameter();
                        jj_consume_token(73);
                        Block();
                        z2 = true;
                        break;
                }
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 28:
                        jj_consume_token(28);
                        Block();
                        z2 = true;
                        break;
                }
                this.jjtree.closeNodeScope((Node) bSHTryStatement, true);
                try {
                    jjtreeCloseNodeScope(bSHTryStatement);
                    if (z2) {
                        return;
                    } else {
                        throw generateParseException();
                    }
                } catch (Throwable th) {
                    th = th;
                    z = false;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHTryStatement, true);
                        jjtreeCloseNodeScope(bSHTryStatement);
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0037  */
    public final void Type() throws Throwable {
        boolean z;
        BSHType bSHType = new BSHType(9);
        this.jjtree.openNodeScope(bSHType);
        jjtreeOpenNodeScope(bSHType);
        try {
            try {
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 11:
                    case 14:
                    case 17:
                    case 22:
                    case 29:
                    case 36:
                    case 38:
                    case 47:
                        PrimitiveType();
                        break;
                    case 69:
                        AmbiguousName();
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                while (jj_2_6(2)) {
                    jj_consume_token(76);
                    jj_consume_token(77);
                    bSHType.addArrayDimension();
                }
                this.jjtree.closeNodeScope((Node) bSHType, true);
                jjtreeCloseNodeScope(bSHType);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHType);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHType, true);
                        jjtreeCloseNodeScope(bSHType);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHType, true);
                jjtreeCloseNodeScope(bSHType);
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0055  */
    /* JADX WARN: Failed to find 'out' block for switch in B:7:0x0026. Please report as an issue. */
    public final void TypedVariableDeclaration() throws Throwable {
        boolean z;
        BSHTypedVariableDeclaration bSHTypedVariableDeclaration = new BSHTypedVariableDeclaration(33);
        this.jjtree.openNodeScope(bSHTypedVariableDeclaration);
        jjtreeOpenNodeScope(bSHTypedVariableDeclaration);
        try {
            Modifiers Modifiers = Modifiers(2, false);
            Type();
            VariableDeclarator();
            while (true) {
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 79:
                        jj_consume_token(79);
                        VariableDeclarator();
                        break;
                }
                this.jjtree.closeNodeScope((Node) bSHTypedVariableDeclaration, true);
                try {
                    jjtreeCloseNodeScope(bSHTypedVariableDeclaration);
                    bSHTypedVariableDeclaration.modifiers = Modifiers;
                    return;
                } catch (Throwable th) {
                    th = th;
                    z = false;
                    try {
                        if (z) {
                            this.jjtree.clearNodeScope(bSHTypedVariableDeclaration);
                        } else {
                            this.jjtree.popNode();
                        }
                        if (th instanceof RuntimeException) {
                            throw ((RuntimeException) th);
                        }
                        if (!(th instanceof ParseException)) {
                            throw ((Error) th);
                        }
                        throw ((ParseException) th);
                    } catch (Throwable th2) {
                        th = th2;
                        if (z) {
                            this.jjtree.closeNodeScope((Node) bSHTypedVariableDeclaration, true);
                            jjtreeCloseNodeScope(bSHTypedVariableDeclaration);
                        }
                        throw th;
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
        }
    }

    public final void UnaryExpression() throws Throwable {
        Token tokenJj_consume_token;
        boolean z;
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 11:
            case 14:
            case 17:
            case 22:
            case 26:
            case 29:
            case 36:
            case 38:
            case 40:
            case 41:
            case 47:
            case 55:
            case 57:
            case 60:
            case 64:
            case 66:
            case 67:
            case 69:
            case 72:
            case 86:
            case 87:
                UnaryExpressionNotPlusMinus();
                return;
            case 100:
                PreIncrementExpression();
                return;
            case 101:
                PreDecrementExpression();
                return;
            case 102:
            case 103:
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 102:
                        tokenJj_consume_token = jj_consume_token(102);
                        break;
                    case 103:
                        tokenJj_consume_token = jj_consume_token(103);
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                UnaryExpression();
                BSHUnaryExpression bSHUnaryExpression = new BSHUnaryExpression(16);
                this.jjtree.openNodeScope(bSHUnaryExpression);
                jjtreeOpenNodeScope(bSHUnaryExpression);
                try {
                    this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
                    z = false;
                    try {
                        jjtreeCloseNodeScope(bSHUnaryExpression);
                        bSHUnaryExpression.kind = tokenJj_consume_token.kind;
                        return;
                    } catch (Throwable th) {
                        th = th;
                        if (z) {
                            this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
                            jjtreeCloseNodeScope(bSHUnaryExpression);
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    z = true;
                }
                break;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    public final void UnaryExpressionNotPlusMinus() throws Throwable {
        Token tokenJj_consume_token;
        boolean z;
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 86:
            case 87:
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 86:
                        tokenJj_consume_token = jj_consume_token(86);
                        break;
                    case 87:
                        tokenJj_consume_token = jj_consume_token(87);
                        break;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
                UnaryExpression();
                BSHUnaryExpression bSHUnaryExpression = new BSHUnaryExpression(16);
                this.jjtree.openNodeScope(bSHUnaryExpression);
                jjtreeOpenNodeScope(bSHUnaryExpression);
                try {
                    this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
                    z = false;
                    try {
                        jjtreeCloseNodeScope(bSHUnaryExpression);
                        bSHUnaryExpression.kind = tokenJj_consume_token.kind;
                        return;
                    } catch (Throwable th) {
                        th = th;
                        if (z) {
                            this.jjtree.closeNodeScope(bSHUnaryExpression, 1);
                            jjtreeCloseNodeScope(bSHUnaryExpression);
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    z = true;
                }
                break;
            default:
                if (jj_2_9(Integer.MAX_VALUE)) {
                    CastExpression();
                    return;
                }
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 11:
                    case 14:
                    case 17:
                    case 22:
                    case 26:
                    case 29:
                    case 36:
                    case 38:
                    case 40:
                    case 41:
                    case 47:
                    case 55:
                    case 57:
                    case 60:
                    case 64:
                    case 66:
                    case 67:
                    case 69:
                    case 72:
                        PostfixExpression();
                        return;
                    default:
                        jj_consume_token(-1);
                        throw new ParseException();
                }
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0050  */
    public final void VariableDeclarator() throws Throwable {
        boolean z;
        BSHVariableDeclarator bSHVariableDeclarator = new BSHVariableDeclarator(5);
        this.jjtree.openNodeScope(bSHVariableDeclarator);
        jjtreeOpenNodeScope(bSHVariableDeclarator);
        try {
            Token tokenJj_consume_token = jj_consume_token(69);
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 81:
                    jj_consume_token(81);
                    VariableInitializer();
                    break;
            }
            this.jjtree.closeNodeScope((Node) bSHVariableDeclarator, true);
            try {
                jjtreeCloseNodeScope(bSHVariableDeclarator);
                bSHVariableDeclarator.name = tokenJj_consume_token.image;
            } catch (Throwable th) {
                th = th;
                z = false;
                if (z) {
                    this.jjtree.closeNodeScope((Node) bSHVariableDeclarator, true);
                    jjtreeCloseNodeScope(bSHVariableDeclarator);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            z = true;
        }
    }

    public final void VariableInitializer() throws Throwable {
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 11:
            case 14:
            case 17:
            case 22:
            case 26:
            case 29:
            case 36:
            case 38:
            case 40:
            case 41:
            case 47:
            case 55:
            case 57:
            case 60:
            case 64:
            case 66:
            case 67:
            case 69:
            case 72:
            case 86:
            case 87:
            case 100:
            case 101:
            case 102:
            case 103:
                Expression();
                return;
            case 74:
                ArrayInitializer();
                return;
            default:
                jj_consume_token(-1);
                throw new ParseException();
        }
    }

    public final void VoidLiteral() throws ParseException {
        jj_consume_token(57);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x003f  */
    public final void WhileStatement() throws Throwable {
        boolean z;
        BSHWhileStatement bSHWhileStatement = new BSHWhileStatement(30);
        this.jjtree.openNodeScope(bSHWhileStatement);
        jjtreeOpenNodeScope(bSHWhileStatement);
        try {
            try {
                jj_consume_token(59);
                jj_consume_token(72);
                Expression();
                jj_consume_token(73);
                Statement();
                this.jjtree.closeNodeScope((Node) bSHWhileStatement, true);
                jjtreeCloseNodeScope(bSHWhileStatement);
            } catch (Throwable th) {
                this.jjtree.clearNodeScope(bSHWhileStatement);
                z = false;
                try {
                    if (th instanceof RuntimeException) {
                        throw ((RuntimeException) th);
                    }
                    if (!(th instanceof ParseException)) {
                        throw ((Error) th);
                    }
                    throw ((ParseException) th);
                } catch (Throwable th2) {
                    th = th2;
                    if (z) {
                        this.jjtree.closeNodeScope((Node) bSHWhileStatement, true);
                        jjtreeCloseNodeScope(bSHWhileStatement);
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            z = true;
            if (z) {
                this.jjtree.closeNodeScope((Node) bSHWhileStatement, true);
                jjtreeCloseNodeScope(bSHWhileStatement);
            }
            throw th;
        }
    }

    ParseException createParseException(String str) {
        Token token = this.token;
        int i = token.beginLine;
        int i2 = token.beginColumn;
        if (token.kind == 0) {
            String str2 = ParserConstants.tokenImage[0];
        } else {
            String str3 = token.image;
        }
        return new ParseException(new StringBuffer().append("Parse error at line ").append(i).append(", column ").append(i2).append(" : ").append(str).toString());
    }

    public final void disable_tracing() {
    }

    public final void enable_tracing() {
    }

    public ParseException generateParseException() {
        Token token = this.token.next;
        int i = token.beginLine;
        return new ParseException(new StringBuffer().append("Parse error at line ").append(i).append(", column ").append(token.beginColumn).append(".  Encountered: ").append(token.kind == 0 ? ParserConstants.tokenImage[0] : token.image).toString());
    }

    public final Token getNextToken() {
        if (this.token.next != null) {
            this.token = this.token.next;
        } else {
            Token token = this.token;
            Token nextToken = this.token_source.getNextToken();
            token.next = nextToken;
            this.token = nextToken;
        }
        this.jj_ntk = -1;
        return this.token;
    }

    public final Token getToken(int i) {
        Token nextToken;
        int i2 = 0;
        Token token = this.lookingAhead ? this.jj_scanpos : this.token;
        while (i2 < i) {
            if (token.next != null) {
                nextToken = token.next;
            } else {
                nextToken = this.token_source.getNextToken();
                token.next = nextToken;
            }
            i2++;
            token = nextToken;
        }
        return token;
    }

    boolean isRegularForStatement() {
        if (getToken(1).kind == 30) {
            int i = 3;
            if (getToken(2).kind == 72) {
                while (true) {
                    int i2 = i + 1;
                    switch (getToken(i).kind) {
                        case 0:
                            return false;
                        case 78:
                            return true;
                        case 89:
                            return false;
                        default:
                            i = i2;
                            break;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    void jjtreeCloseNodeScope(Node node) {
        ((SimpleNode) node).lastToken = getToken(0);
    }

    void jjtreeOpenNodeScope(Node node) {
        ((SimpleNode) node).firstToken = getToken(1);
    }

    public SimpleNode popNode() {
        if (this.jjtree.nodeArity() > 0) {
            return (SimpleNode) this.jjtree.popNode();
        }
        return null;
    }

    void reInitInput(Reader reader) {
        ReInit(reader);
    }

    void reInitTokenInput(Reader reader) {
        this.jj_input_stream.ReInit(reader, this.jj_input_stream.getEndLine(), this.jj_input_stream.getEndColumn());
    }

    public void setRetainComments(boolean z) {
        this.retainComments = z;
    }
}
