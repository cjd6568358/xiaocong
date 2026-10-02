package bsh;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.SettingsContentProvider;
import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.Bugly;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.io.IOException;
import java.io.PrintStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ParserTokenManager implements ParserConstants {
    protected char curChar;
    int curLexState;
    public PrintStream debugStream;
    int defaultLexState;
    protected JavaCharStream input_stream;
    int jjmatchedKind;
    int jjmatchedPos;
    int jjnewStateCnt;
    int jjround;
    private final int[] jjrounds;
    private final int[] jjstateSet;
    static final long[] jjbitVec0 = {0, 0, -1, -1};
    static final long[] jjbitVec1 = {-2, -1, -1, -1};
    static final long[] jjbitVec3 = {2301339413881290750L, -16384, 4294967295L, 432345564227567616L};
    static final long[] jjbitVec4 = {0, 0, 0, -36028797027352577L};
    static final long[] jjbitVec5 = {0, -1, -1, -1};
    static final long[] jjbitVec6 = {-1, -1, 65535, 0};
    static final long[] jjbitVec7 = {-1, -1, 0, 0};
    static final long[] jjbitVec8 = {70368744177663L, 0, 0, 0};
    static final int[] jjnextStates = {37, 38, 43, 44, 47, 48, 15, 56, 61, 73, 26, 27, 29, 17, 19, 52, 54, 9, 57, 58, 60, 2, 3, 5, 11, 12, 15, 26, 27, 31, 29, 39, 40, 15, 47, 48, 15, 63, 64, 66, 69, 70, 72, 13, 14, 20, 21, 23, 28, 30, 32, 41, 42, 45, 46, 49, 50};
    public static final String[] jjstrLiteralImages = {Constants.MAIN_VERSION_TAG, null, null, null, null, null, null, null, null, null, "abstract", SettingsContentProvider.BOOLEAN_TYPE, "break", "class", "byte", "case", "catch", "char", "const", "continue", "default", "do", "double", "else", "enum", "extends", Bugly.SDK_IS_DEV, "final", "finally", SettingsContentProvider.FLOAT_TYPE, "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", SettingsContentProvider.LONG_TYPE, "native", "new", "null", "package", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE, "protected", "public", "return", "short", "static", "strictfp", "switch", "synchronized", "transient", "throw", "throws", "true", "try", "void", "volatile", "while", null, null, null, null, null, null, null, null, null, null, null, null, "(", ")", "{", "}", "[", "]", ";", ",", ".", "=", ">", "@gt", "<", "@lt", "!", "~", "?", ":", "==", "<=", "@lteq", ">=", "@gteq", "!=", "||", "@or", "&&", "@and", "++", "--", "+", "-", "*", "/", "&", "@bitwise_and", "|", "@bitwise_or", "^", "%", "<<", "@left_shift", ">>", "@right_shift", ">>>", "@right_unsigned_shift", "+=", "-=", "*=", "/=", "&=", "@and_assign", "|=", "@or_assign", "^=", "%=", "<<=", "@left_shift_assign", ">>=", "@right_shift_assign", ">>>=", "@right_unsigned_shift_assign"};
    public static final String[] lexStateNames = {"DEFAULT"};
    static final long[] jjtoToken = {2305843009213692929L, -195, 63};
    static final long[] jjtoSkip = {1022, 0, 0};
    static final long[] jjtoSpecial = {896, 0, 0};

    public ParserTokenManager(JavaCharStream javaCharStream) {
        this.debugStream = System.out;
        this.jjrounds = new int[74];
        this.jjstateSet = new int[148];
        this.curLexState = 0;
        this.defaultLexState = 0;
        this.input_stream = javaCharStream;
    }

    public ParserTokenManager(JavaCharStream javaCharStream, int i) {
        this(javaCharStream);
        SwitchTo(i);
    }

    private final void ReInitRounds() {
        this.jjround = -2147483647;
        int i = 74;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            this.jjrounds[i2] = Integer.MIN_VALUE;
            i = i2;
        }
    }

    private final void jjAddStates(int i, int i2) {
        while (true) {
            int[] iArr = this.jjstateSet;
            int i3 = this.jjnewStateCnt;
            this.jjnewStateCnt = i3 + 1;
            iArr[i3] = jjnextStates[i];
            int i4 = i + 1;
            if (i == i2) {
                return;
            } else {
                i = i4;
            }
        }
    }

    private static final boolean jjCanMove_0(int i, int i2, int i3, long j, long j2) {
        switch (i) {
            case 0:
                return (jjbitVec0[i3] & j2) != 0;
            default:
                return false;
        }
    }

    private static final boolean jjCanMove_1(int i, int i2, int i3, long j, long j2) {
        switch (i) {
            case 0:
                return (jjbitVec0[i3] & j2) != 0;
            default:
                return (jjbitVec1[i2] & j) != 0;
        }
    }

    private static final boolean jjCanMove_2(int i, int i2, int i3, long j, long j2) {
        switch (i) {
            case 0:
                return (jjbitVec4[i3] & j2) != 0;
            case 48:
                return (jjbitVec5[i3] & j2) != 0;
            case 49:
                return (jjbitVec6[i3] & j2) != 0;
            case 51:
                return (jjbitVec7[i3] & j2) != 0;
            case 61:
                return (jjbitVec8[i3] & j2) != 0;
            default:
                return (jjbitVec3[i2] & j) != 0;
        }
    }

    private final void jjCheckNAdd(int i) {
        if (this.jjrounds[i] != this.jjround) {
            int[] iArr = this.jjstateSet;
            int i2 = this.jjnewStateCnt;
            this.jjnewStateCnt = i2 + 1;
            iArr[i2] = i;
            this.jjrounds[i] = this.jjround;
        }
    }

    private final void jjCheckNAddStates(int i) {
        jjCheckNAdd(jjnextStates[i]);
        jjCheckNAdd(jjnextStates[i + 1]);
    }

    private final void jjCheckNAddStates(int i, int i2) {
        while (true) {
            jjCheckNAdd(jjnextStates[i]);
            int i3 = i + 1;
            if (i == i2) {
                return;
            } else {
                i = i3;
            }
        }
    }

    private final void jjCheckNAddTwoStates(int i, int i2) {
        jjCheckNAdd(i);
        jjCheckNAdd(i2);
    }

    private final int jjMoveNfa_0(int i, int i2) {
        int i3 = 0;
        this.jjnewStateCnt = 74;
        int i4 = 1;
        this.jjstateSet[0] = i;
        int i5 = Integer.MAX_VALUE;
        while (true) {
            int i6 = i4;
            int i7 = i3;
            int i8 = this.jjround + 1;
            this.jjround = i8;
            if (i8 == Integer.MAX_VALUE) {
                ReInitRounds();
            }
            if (this.curChar < '@') {
                long j = 1 << this.curChar;
                do {
                    i6--;
                    switch (this.jjstateSet[i6]) {
                        case 0:
                            if ((8589934591L & j) != 0) {
                                if (i5 > 6) {
                                    i5 = 6;
                                }
                                jjCheckNAdd(0);
                            }
                            break;
                        case 1:
                            if (this.curChar == '!') {
                                jjCheckNAddStates(21, 23);
                            }
                            break;
                        case 2:
                            if (((-9217) & j) != 0) {
                                jjCheckNAddStates(21, 23);
                            }
                            break;
                        case 3:
                            if ((9216 & j) != 0 && i5 > 8) {
                                i5 = 8;
                            }
                            break;
                        case 4:
                            if (this.curChar == '\n' && i5 > 8) {
                                i5 = 8;
                            }
                            break;
                        case 5:
                            if (this.curChar == '\r') {
                                int[] iArr = this.jjstateSet;
                                int i9 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i9 + 1;
                                iArr[i9] = 4;
                            }
                            break;
                        case 6:
                            if ((8589934591L & j) != 0) {
                                if (i5 > 6) {
                                    i5 = 6;
                                }
                                jjCheckNAdd(0);
                            } else if ((287948901175001088L & j) != 0) {
                                jjCheckNAddStates(0, 6);
                            } else if (this.curChar == '/') {
                                jjAddStates(7, 9);
                            } else if (this.curChar == '$') {
                                if (i5 > 69) {
                                    i5 = 69;
                                }
                                jjCheckNAdd(35);
                            } else if (this.curChar == '\"') {
                                jjCheckNAddStates(10, 12);
                            } else if (this.curChar == '\'') {
                                jjAddStates(13, 14);
                            } else if (this.curChar == '.') {
                                jjCheckNAdd(11);
                            } else if (this.curChar == '#') {
                                int[] iArr2 = this.jjstateSet;
                                int i10 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i10 + 1;
                                iArr2[i10] = 1;
                            }
                            if ((287667426198290432L & j) != 0) {
                                if (i5 > 60) {
                                    i5 = 60;
                                }
                                jjCheckNAddTwoStates(8, 9);
                            } else if (this.curChar == '0') {
                                if (i5 > 60) {
                                    i5 = 60;
                                }
                                jjCheckNAddStates(15, 17);
                            }
                            break;
                        case 7:
                            if ((287667426198290432L & j) != 0) {
                                if (i5 > 60) {
                                    i5 = 60;
                                }
                                jjCheckNAddTwoStates(8, 9);
                            }
                            break;
                        case 8:
                            if ((287948901175001088L & j) != 0) {
                                if (i5 > 60) {
                                    i5 = 60;
                                }
                                jjCheckNAddTwoStates(8, 9);
                            }
                            break;
                        case 10:
                            if (this.curChar == '.') {
                                jjCheckNAdd(11);
                            }
                            break;
                        case 11:
                            if ((287948901175001088L & j) != 0) {
                                if (i5 > 64) {
                                    i5 = 64;
                                }
                                jjCheckNAddStates(24, 26);
                            }
                            break;
                        case 13:
                            if ((43980465111040L & j) != 0) {
                                jjCheckNAdd(14);
                            }
                            break;
                        case 14:
                            if ((287948901175001088L & j) != 0) {
                                if (i5 > 64) {
                                    i5 = 64;
                                }
                                jjCheckNAddTwoStates(14, 15);
                            }
                            break;
                        case 16:
                            if (this.curChar == '\'') {
                                jjAddStates(13, 14);
                            }
                            break;
                        case 17:
                            if (((-549755823105L) & j) != 0) {
                                jjCheckNAdd(18);
                            }
                            break;
                        case 18:
                            if (this.curChar == '\'' && i5 > 66) {
                                i5 = 66;
                            }
                            break;
                        case 20:
                            if ((566935683072L & j) != 0) {
                                jjCheckNAdd(18);
                            }
                            break;
                        case 21:
                            if ((71776119061217280L & j) != 0) {
                                jjCheckNAddTwoStates(22, 18);
                            }
                            break;
                        case 22:
                            if ((71776119061217280L & j) != 0) {
                                jjCheckNAdd(18);
                            }
                            break;
                        case 23:
                            if ((4222124650659840L & j) != 0) {
                                int[] iArr3 = this.jjstateSet;
                                int i11 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i11 + 1;
                                iArr3[i11] = 24;
                            }
                            break;
                        case 24:
                            if ((71776119061217280L & j) != 0) {
                                jjCheckNAdd(22);
                            }
                            break;
                        case 25:
                            if (this.curChar == '\"') {
                                jjCheckNAddStates(10, 12);
                            }
                            break;
                        case 26:
                            if (((-17179878401L) & j) != 0) {
                                jjCheckNAddStates(10, 12);
                            }
                            break;
                        case 28:
                            if ((566935683072L & j) != 0) {
                                jjCheckNAddStates(10, 12);
                            }
                            break;
                        case 29:
                            if (this.curChar == '\"' && i5 > 67) {
                                i5 = 67;
                            }
                            break;
                        case 30:
                            if ((71776119061217280L & j) != 0) {
                                jjCheckNAddStates(27, 30);
                            }
                            break;
                        case 31:
                            if ((71776119061217280L & j) != 0) {
                                jjCheckNAddStates(10, 12);
                            }
                            break;
                        case 32:
                            if ((4222124650659840L & j) != 0) {
                                int[] iArr4 = this.jjstateSet;
                                int i12 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i12 + 1;
                                iArr4[i12] = 33;
                            }
                            break;
                        case 33:
                            if ((71776119061217280L & j) != 0) {
                                jjCheckNAdd(31);
                            }
                            break;
                        case 34:
                            if (this.curChar == '$') {
                                if (i5 > 69) {
                                    i5 = 69;
                                }
                                jjCheckNAdd(35);
                            }
                            break;
                        case 35:
                            if ((287948969894477824L & j) != 0) {
                                if (i5 > 69) {
                                    i5 = 69;
                                }
                                jjCheckNAdd(35);
                            }
                            break;
                        case 36:
                            if ((287948901175001088L & j) != 0) {
                                jjCheckNAddStates(0, 6);
                            }
                            break;
                        case 37:
                            if ((287948901175001088L & j) != 0) {
                                jjCheckNAddTwoStates(37, 38);
                            }
                            break;
                        case 38:
                            if (this.curChar == '.') {
                                if (i5 > 64) {
                                    i5 = 64;
                                }
                                jjCheckNAddStates(31, 33);
                            }
                            break;
                        case 39:
                            if ((287948901175001088L & j) != 0) {
                                if (i5 > 64) {
                                    i5 = 64;
                                }
                                jjCheckNAddStates(31, 33);
                            }
                            break;
                        case 41:
                            if ((43980465111040L & j) != 0) {
                                jjCheckNAdd(42);
                            }
                            break;
                        case 42:
                            if ((287948901175001088L & j) != 0) {
                                if (i5 > 64) {
                                    i5 = 64;
                                }
                                jjCheckNAddTwoStates(42, 15);
                            }
                            break;
                        case 43:
                            if ((287948901175001088L & j) != 0) {
                                jjCheckNAddTwoStates(43, 44);
                            }
                            break;
                        case 45:
                            if ((43980465111040L & j) != 0) {
                                jjCheckNAdd(46);
                            }
                            break;
                        case 46:
                            if ((287948901175001088L & j) != 0) {
                                if (i5 > 64) {
                                    i5 = 64;
                                }
                                jjCheckNAddTwoStates(46, 15);
                            }
                            break;
                        case 47:
                            if ((287948901175001088L & j) != 0) {
                                jjCheckNAddStates(34, 36);
                            }
                            break;
                        case 49:
                            if ((43980465111040L & j) != 0) {
                                jjCheckNAdd(50);
                            }
                            break;
                        case 50:
                            if ((287948901175001088L & j) != 0) {
                                jjCheckNAddTwoStates(50, 15);
                            }
                            break;
                        case 51:
                            if (this.curChar == '0') {
                                if (i5 > 60) {
                                    i5 = 60;
                                }
                                jjCheckNAddStates(15, 17);
                            }
                            break;
                        case 53:
                            if ((287948901175001088L & j) != 0) {
                                if (i5 > 60) {
                                    i5 = 60;
                                }
                                jjCheckNAddTwoStates(53, 9);
                            }
                            break;
                        case 54:
                            if ((71776119061217280L & j) != 0) {
                                if (i5 > 60) {
                                    i5 = 60;
                                }
                                jjCheckNAddTwoStates(54, 9);
                            }
                            break;
                        case 55:
                            if (this.curChar == '/') {
                                jjAddStates(7, 9);
                            }
                            break;
                        case 56:
                            if (this.curChar == '*') {
                                int[] iArr5 = this.jjstateSet;
                                int i13 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i13 + 1;
                                iArr5[i13] = 67;
                            } else if (this.curChar == '/') {
                                if (i5 > 7) {
                                    i5 = 7;
                                }
                                jjCheckNAddStates(18, 20);
                            }
                            if (this.curChar == '*') {
                                jjCheckNAdd(62);
                            }
                            break;
                        case 57:
                            if (((-9217) & j) != 0) {
                                if (i5 > 7) {
                                    i5 = 7;
                                }
                                jjCheckNAddStates(18, 20);
                            }
                            break;
                        case 58:
                            if ((9216 & j) != 0 && i5 > 7) {
                                i5 = 7;
                            }
                            break;
                        case 59:
                            if (this.curChar == '\n' && i5 > 7) {
                                i5 = 7;
                            }
                            break;
                        case 60:
                            if (this.curChar == '\r') {
                                int[] iArr6 = this.jjstateSet;
                                int i14 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i14 + 1;
                                iArr6[i14] = 59;
                            }
                            break;
                        case 61:
                            if (this.curChar == '*') {
                                jjCheckNAdd(62);
                            }
                            break;
                        case 62:
                            if (((-4398046511105L) & j) != 0) {
                                jjCheckNAddTwoStates(62, 63);
                            }
                            break;
                        case 63:
                            if (this.curChar == '*') {
                                jjCheckNAddStates(37, 39);
                            }
                            break;
                        case 64:
                            if (((-145135534866433L) & j) != 0) {
                                jjCheckNAddTwoStates(65, 63);
                            }
                            break;
                        case 65:
                            if (((-4398046511105L) & j) != 0) {
                                jjCheckNAddTwoStates(65, 63);
                            }
                            break;
                        case 66:
                            if (this.curChar == '/' && i5 > 9) {
                                i5 = 9;
                            }
                            break;
                        case 67:
                            if (this.curChar == '*') {
                                jjCheckNAddTwoStates(68, 69);
                            }
                            break;
                        case 68:
                            if (((-4398046511105L) & j) != 0) {
                                jjCheckNAddTwoStates(68, 69);
                            }
                            break;
                        case 69:
                            if (this.curChar == '*') {
                                jjCheckNAddStates(40, 42);
                            }
                            break;
                        case 70:
                            if (((-145135534866433L) & j) != 0) {
                                jjCheckNAddTwoStates(71, 69);
                            }
                            break;
                        case 71:
                            if (((-4398046511105L) & j) != 0) {
                                jjCheckNAddTwoStates(71, 69);
                            }
                            break;
                        case 72:
                            if (this.curChar == '/' && i5 > 68) {
                                i5 = 68;
                            }
                            break;
                        case 73:
                            if (this.curChar == '*') {
                                int[] iArr7 = this.jjstateSet;
                                int i15 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i15 + 1;
                                iArr7[i15] = 67;
                            }
                            break;
                    }
                } while (i6 != i7);
            } else if (this.curChar < 128) {
                long j2 = 1 << (this.curChar & '?');
                do {
                    i6--;
                    switch (this.jjstateSet[i6]) {
                        case 2:
                            jjAddStates(21, 23);
                            break;
                        case 6:
                        case 35:
                            if ((576460745995190270L & j2) != 0) {
                                if (i5 > 69) {
                                    i5 = 69;
                                }
                                jjCheckNAdd(35);
                            }
                            break;
                        case 9:
                            if ((17592186048512L & j2) != 0 && i5 > 60) {
                                i5 = 60;
                            }
                            break;
                        case 12:
                            if ((137438953504L & j2) != 0) {
                                jjAddStates(43, 44);
                            }
                            break;
                        case 15:
                            if ((343597383760L & j2) != 0 && i5 > 64) {
                                i5 = 64;
                            }
                            break;
                        case 17:
                            if (((-268435457) & j2) != 0) {
                                jjCheckNAdd(18);
                            }
                            break;
                        case 19:
                            if (this.curChar == '\\') {
                                jjAddStates(45, 47);
                            }
                            break;
                        case 20:
                            if ((5700160604602368L & j2) != 0) {
                                jjCheckNAdd(18);
                            }
                            break;
                        case 26:
                            if (((-268435457) & j2) != 0) {
                                jjCheckNAddStates(10, 12);
                            }
                            break;
                        case 27:
                            if (this.curChar == '\\') {
                                jjAddStates(48, 50);
                            }
                            break;
                        case 28:
                            if ((5700160604602368L & j2) != 0) {
                                jjCheckNAddStates(10, 12);
                            }
                            break;
                        case 40:
                            if ((137438953504L & j2) != 0) {
                                jjAddStates(51, 52);
                            }
                            break;
                        case 44:
                            if ((137438953504L & j2) != 0) {
                                jjAddStates(53, 54);
                            }
                            break;
                        case 48:
                            if ((137438953504L & j2) != 0) {
                                jjAddStates(55, 56);
                            }
                            break;
                        case 52:
                            if ((72057594054705152L & j2) != 0) {
                                jjCheckNAdd(53);
                            }
                            break;
                        case 53:
                            if ((541165879422L & j2) != 0) {
                                if (i5 > 60) {
                                    i5 = 60;
                                }
                                jjCheckNAddTwoStates(53, 9);
                            }
                            break;
                        case 57:
                            if (i5 > 7) {
                                i5 = 7;
                            }
                            jjAddStates(18, 20);
                            break;
                        case 62:
                            jjCheckNAddTwoStates(62, 63);
                            break;
                        case 64:
                        case 65:
                            jjCheckNAddTwoStates(65, 63);
                            break;
                        case 68:
                            jjCheckNAddTwoStates(68, 69);
                            break;
                        case 70:
                        case 71:
                            jjCheckNAddTwoStates(71, 69);
                            break;
                    }
                } while (i6 != i7);
            } else {
                int i16 = this.curChar >> '\b';
                int i17 = i16 >> 6;
                long j3 = 1 << (i16 & 63);
                int i18 = (this.curChar & 255) >> 6;
                long j4 = 1 << (this.curChar & '?');
                do {
                    i6--;
                    switch (this.jjstateSet[i6]) {
                        case 0:
                            if (jjCanMove_0(i16, i17, i18, j3, j4)) {
                                if (i5 > 6) {
                                    i5 = 6;
                                }
                                jjCheckNAdd(0);
                            }
                            break;
                        case 2:
                            if (jjCanMove_1(i16, i17, i18, j3, j4)) {
                                jjAddStates(21, 23);
                            }
                            break;
                        case 6:
                            if (jjCanMove_0(i16, i17, i18, j3, j4)) {
                                if (i5 > 6) {
                                    i5 = 6;
                                }
                                jjCheckNAdd(0);
                            }
                            if (jjCanMove_2(i16, i17, i18, j3, j4)) {
                                if (i5 > 69) {
                                    i5 = 69;
                                }
                                jjCheckNAdd(35);
                            }
                            break;
                        case 17:
                            if (jjCanMove_1(i16, i17, i18, j3, j4)) {
                                int[] iArr8 = this.jjstateSet;
                                int i19 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i19 + 1;
                                iArr8[i19] = 18;
                            }
                            break;
                        case 26:
                            if (jjCanMove_1(i16, i17, i18, j3, j4)) {
                                jjAddStates(10, 12);
                            }
                            break;
                        case 34:
                        case 35:
                            if (jjCanMove_2(i16, i17, i18, j3, j4)) {
                                if (i5 > 69) {
                                    i5 = 69;
                                }
                                jjCheckNAdd(35);
                            }
                            break;
                        case 57:
                            if (jjCanMove_1(i16, i17, i18, j3, j4)) {
                                if (i5 > 7) {
                                    i5 = 7;
                                }
                                jjAddStates(18, 20);
                            }
                            break;
                        case 62:
                            if (jjCanMove_1(i16, i17, i18, j3, j4)) {
                                jjCheckNAddTwoStates(62, 63);
                            }
                            break;
                        case 64:
                        case 65:
                            if (jjCanMove_1(i16, i17, i18, j3, j4)) {
                                jjCheckNAddTwoStates(65, 63);
                            }
                            break;
                        case 68:
                            if (jjCanMove_1(i16, i17, i18, j3, j4)) {
                                jjCheckNAddTwoStates(68, 69);
                            }
                            break;
                        case 70:
                        case 71:
                            if (jjCanMove_1(i16, i17, i18, j3, j4)) {
                                jjCheckNAddTwoStates(71, 69);
                            }
                            break;
                    }
                } while (i6 != i7);
            }
            if (i5 != Integer.MAX_VALUE) {
                this.jjmatchedKind = i5;
                this.jjmatchedPos = i2;
                i5 = Integer.MAX_VALUE;
            }
            i2++;
            i4 = this.jjnewStateCnt;
            this.jjnewStateCnt = i7;
            i3 = 74 - i7;
            if (i4 != i3) {
                try {
                    this.curChar = this.input_stream.readChar();
                } catch (IOException e) {
                }
            }
            return i2;
        }
    }

    private final int jjMoveStringLiteralDfa0_0() {
        switch (this.curChar) {
            case '\t':
                return jjStartNfaWithStates_0(0, 2, 0);
            case '\n':
                return jjStartNfaWithStates_0(0, 5, 0);
            case '\f':
                return jjStartNfaWithStates_0(0, 4, 0);
            case '\r':
                return jjStartNfaWithStates_0(0, 3, 0);
            case ' ':
                return jjStartNfaWithStates_0(0, 1, 0);
            case '!':
                this.jjmatchedKind = 86;
                return jjMoveStringLiteralDfa1_0(0L, 2147483648L, 0L);
            case '%':
                this.jjmatchedKind = 111;
                return jjMoveStringLiteralDfa1_0(0L, Long.MIN_VALUE, 0L);
            case '&':
                this.jjmatchedKind = 106;
                return jjMoveStringLiteralDfa1_0(0L, 288230393331580928L, 0L);
            case '(':
                return jjStopAtPos(0, 72);
            case ')':
                return jjStopAtPos(0, 73);
            case '*':
                this.jjmatchedKind = 104;
                return jjMoveStringLiteralDfa1_0(0L, 72057594037927936L, 0L);
            case '+':
                this.jjmatchedKind = 102;
                return jjMoveStringLiteralDfa1_0(0L, 18014467228958720L, 0L);
            case ',':
                return jjStopAtPos(0, 79);
            case '-':
                this.jjmatchedKind = 103;
                return jjMoveStringLiteralDfa1_0(0L, 36028934457917440L, 0L);
            case '.':
                return jjStartNfaWithStates_0(0, 80, 11);
            case '/':
                this.jjmatchedKind = 105;
                return jjMoveStringLiteralDfa1_0(0L, 144115188075855872L, 0L);
            case ':':
                return jjStopAtPos(0, 89);
            case ';':
                return jjStopAtPos(0, 78);
            case '<':
                this.jjmatchedKind = 84;
                return jjMoveStringLiteralDfa1_0(0L, 281475110928384L, 1L);
            case '=':
                this.jjmatchedKind = 81;
                return jjMoveStringLiteralDfa1_0(0L, 67108864L, 0L);
            case '>':
                this.jjmatchedKind = 82;
                return jjMoveStringLiteralDfa1_0(0L, 5629500071084032L, 20L);
            case '?':
                return jjStopAtPos(0, 88);
            case '@':
                return jjMoveStringLiteralDfa1_0(0L, 2894169735298547712L, 42L);
            case '[':
                return jjStopAtPos(0, 76);
            case ']':
                return jjStopAtPos(0, 77);
            case '^':
                this.jjmatchedKind = 110;
                return jjMoveStringLiteralDfa1_0(0L, 4611686018427387904L, 0L);
            case 'a':
                return jjMoveStringLiteralDfa1_0(ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLS, 0L, 0L);
            case 'b':
                return jjMoveStringLiteralDfa1_0(22528L, 0L, 0L);
            case 'c':
                return jjMoveStringLiteralDfa1_0(1024000L, 0L, 0L);
            case 'd':
                return jjMoveStringLiteralDfa1_0(7340032L, 0L, 0L);
            case 'e':
                return jjMoveStringLiteralDfa1_0(58720256L, 0L, 0L);
            case 'f':
                return jjMoveStringLiteralDfa1_0(2080374784L, 0L, 0L);
            case 'g':
                return jjMoveStringLiteralDfa1_0(2147483648L, 0L, 0L);
            case 'i':
                return jjMoveStringLiteralDfa1_0(270582939648L, 0L, 0L);
            case 'l':
                return jjMoveStringLiteralDfa1_0(274877906944L, 0L, 0L);
            case 'n':
                return jjMoveStringLiteralDfa1_0(3848290697216L, 0L, 0L);
            case 'p':
                return jjMoveStringLiteralDfa1_0(65970697666560L, 0L, 0L);
            case 'r':
                return jjMoveStringLiteralDfa1_0(70368744177664L, 0L, 0L);
            case 's':
                return jjMoveStringLiteralDfa1_0(4362862139015168L, 0L, 0L);
            case 't':
                return jjMoveStringLiteralDfa1_0(139611588448485376L, 0L, 0L);
            case 'v':
                return jjMoveStringLiteralDfa1_0(432345564227567616L, 0L, 0L);
            case ParserConstants.MINUSASSIGN /* 119 */:
                return jjMoveStringLiteralDfa1_0(576460752303423488L, 0L, 0L);
            case ParserConstants.ANDASSIGNX /* 123 */:
                return jjStopAtPos(0, 74);
            case ParserConstants.ORASSIGN /* 124 */:
                this.jjmatchedKind = 108;
                return jjMoveStringLiteralDfa1_0(0L, 1152921508901814272L, 0L);
            case ParserConstants.ORASSIGNX /* 125 */:
                return jjStopAtPos(0, 75);
            case ParserConstants.XORASSIGN /* 126 */:
                return jjStopAtPos(0, 87);
            default:
                return jjMoveNfa_0(6, 0);
        }
    }

    private final int jjMoveStringLiteralDfa10_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(8, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'e':
                    return jjMoveStringLiteralDfa11_0(j7, 2251799813685248L, j8, 0L, j9, 0L);
                case 'f':
                    return jjMoveStringLiteralDfa11_0(j7, 0L, j8, 2251799813685248L, j9, 8L);
                case 'i':
                    return jjMoveStringLiteralDfa11_0(j7, 0L, j8, 9007199254740992L, j9, 32L);
                case 'n':
                    return (576460752303423488L & j8) != 0 ? jjStopAtPos(10, ParserConstants.ANDASSIGNX) : jjMoveStringLiteralDfa11_0(j7, 0L, j8, 8796093022208L, j9, 0L);
                case 'r':
                    if ((35184372088832L & j8) != 0) {
                        return jjStopAtPos(10, 109);
                    }
                    break;
                case 't':
                    if ((562949953421312L & j8) != 0) {
                        this.jjmatchedKind = 113;
                        this.jjmatchedPos = 10;
                    }
                    return jjMoveStringLiteralDfa11_0(j7, 0L, j8, 0L, j9, 2L);
            }
            return jjStartNfa_0(9, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(9, j7, j8, j9);
            return 10;
        }
    }

    private final int jjMoveStringLiteralDfa11_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(9, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '_':
                    return jjMoveStringLiteralDfa12_0(j7, 0L, j8, 0L, j9, 2L);
                case 'd':
                    if ((2251799813685248L & j7) != 0) {
                        return jjStartNfaWithStates_0(11, 51, 35);
                    }
                    if ((8796093022208L & j8) != 0) {
                        return jjStopAtPos(11, 107);
                    }
                    break;
                case 'g':
                    return jjMoveStringLiteralDfa12_0(j7, 0L, j8, 9007199254740992L, j9, 32L);
                case 't':
                    if ((2251799813685248L & j8) != 0) {
                        this.jjmatchedKind = 115;
                        this.jjmatchedPos = 11;
                    }
                    return jjMoveStringLiteralDfa12_0(j7, 0L, j8, 0L, j9, 8L);
            }
            return jjStartNfa_0(10, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(10, j7, j8, j9);
            return 11;
        }
    }

    private final int jjMoveStringLiteralDfa12_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j4 & j3;
        long j8 = j6 & j5;
        if (((j2 & j) | j7 | j8) == 0) {
            return jjStartNfa_0(10, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '_':
                    return jjMoveStringLiteralDfa13_0(j7, 0L, j8, 8L);
                case 'a':
                    return jjMoveStringLiteralDfa13_0(j7, 0L, j8, 2L);
                case 'n':
                    return jjMoveStringLiteralDfa13_0(j7, 9007199254740992L, j8, 32L);
                default:
                    return jjStartNfa_0(11, 0L, j7, j8);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(11, 0L, j7, j8);
            return 12;
        }
    }

    private final int jjMoveStringLiteralDfa13_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        long j6 = j4 & j3;
        if ((j5 | j6) == 0) {
            return jjStartNfa_0(11, 0L, j, j3);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'a':
                    return jjMoveStringLiteralDfa14_0(j5, 0L, j6, 8L);
                case 'e':
                    return jjMoveStringLiteralDfa14_0(j5, 9007199254740992L, j6, 32L);
                case 's':
                    return jjMoveStringLiteralDfa14_0(j5, 0L, j6, 2L);
                default:
                    return jjStartNfa_0(12, 0L, j5, j6);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(12, 0L, j5, j6);
            return 13;
        }
    }

    private final int jjMoveStringLiteralDfa14_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        long j6 = j4 & j3;
        if ((j5 | j6) == 0) {
            return jjStartNfa_0(12, 0L, j, j3);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'd':
                    return jjMoveStringLiteralDfa15_0(j5, 9007199254740992L, j6, 32L);
                case 's':
                    return jjMoveStringLiteralDfa15_0(j5, 0L, j6, 10L);
                default:
                    return jjStartNfa_0(13, 0L, j5, j6);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(13, 0L, j5, j6);
            return 14;
        }
    }

    private final int jjMoveStringLiteralDfa15_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        long j6 = j4 & j3;
        if ((j5 | j6) == 0) {
            return jjStartNfa_0(13, 0L, j, j3);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '_':
                    return jjMoveStringLiteralDfa16_0(j5, 9007199254740992L, j6, 32L);
                case 'i':
                    return jjMoveStringLiteralDfa16_0(j5, 0L, j6, 2L);
                case 's':
                    return jjMoveStringLiteralDfa16_0(j5, 0L, j6, 8L);
                default:
                    return jjStartNfa_0(14, 0L, j5, j6);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(14, 0L, j5, j6);
            return 15;
        }
    }

    private final int jjMoveStringLiteralDfa16_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        long j6 = j4 & j3;
        if ((j5 | j6) == 0) {
            return jjStartNfa_0(14, 0L, j, j3);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'g':
                    return jjMoveStringLiteralDfa17_0(j5, 0L, j6, 2L);
                case 'i':
                    return jjMoveStringLiteralDfa17_0(j5, 0L, j6, 8L);
                case 's':
                    return jjMoveStringLiteralDfa17_0(j5, 9007199254740992L, j6, 32L);
                default:
                    return jjStartNfa_0(15, 0L, j5, j6);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(15, 0L, j5, j6);
            return 16;
        }
    }

    private final int jjMoveStringLiteralDfa17_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        long j6 = j4 & j3;
        if ((j5 | j6) == 0) {
            return jjStartNfa_0(15, 0L, j, j3);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'g':
                    return jjMoveStringLiteralDfa18_0(j5, 0L, j6, 8L);
                case 'h':
                    return jjMoveStringLiteralDfa18_0(j5, 9007199254740992L, j6, 32L);
                case 'n':
                    if ((2 & j6) != 0) {
                        return jjStopAtPos(17, ParserConstants.LSHIFTASSIGNX);
                    }
                    break;
            }
            return jjStartNfa_0(16, 0L, j5, j6);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(16, 0L, j5, j6);
            return 17;
        }
    }

    private final int jjMoveStringLiteralDfa18_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        long j6 = j4 & j3;
        if ((j5 | j6) == 0) {
            return jjStartNfa_0(16, 0L, j, j3);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'i':
                    return jjMoveStringLiteralDfa19_0(j5, 9007199254740992L, j6, 32L);
                case 'n':
                    if ((8 & j6) != 0) {
                        return jjStopAtPos(18, ParserConstants.RSIGNEDSHIFTASSIGNX);
                    }
                    break;
            }
            return jjStartNfa_0(17, 0L, j5, j6);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(17, 0L, j5, j6);
            return 18;
        }
    }

    private final int jjMoveStringLiteralDfa19_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        long j6 = j4 & j3;
        if ((j5 | j6) == 0) {
            return jjStartNfa_0(17, 0L, j, j3);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'f':
                    return jjMoveStringLiteralDfa20_0(j5, 9007199254740992L, j6, 32L);
                default:
                    return jjStartNfa_0(18, 0L, j5, j6);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(18, 0L, j5, j6);
            return 19;
        }
    }

    private final int jjMoveStringLiteralDfa1_0(long j, long j2, long j3) {
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '&':
                    if ((17179869184L & j2) != 0) {
                        return jjStopAtPos(1, 98);
                    }
                    break;
                case '+':
                    if ((68719476736L & j2) != 0) {
                        return jjStopAtPos(1, 100);
                    }
                    break;
                case '-':
                    if ((137438953472L & j2) != 0) {
                        return jjStopAtPos(1, 101);
                    }
                    break;
                case '<':
                    if ((281474976710656L & j2) != 0) {
                        this.jjmatchedKind = 112;
                        this.jjmatchedPos = 1;
                    }
                    return jjMoveStringLiteralDfa2_0(j, 0L, j2, 0L, j3, 1L);
                case '=':
                    if ((67108864 & j2) != 0) {
                        return jjStopAtPos(1, 90);
                    }
                    if ((134217728 & j2) != 0) {
                        return jjStopAtPos(1, 91);
                    }
                    if ((536870912 & j2) != 0) {
                        return jjStopAtPos(1, 93);
                    }
                    if ((2147483648L & j2) != 0) {
                        return jjStopAtPos(1, 95);
                    }
                    if ((18014398509481984L & j2) != 0) {
                        return jjStopAtPos(1, 118);
                    }
                    if ((36028797018963968L & j2) != 0) {
                        return jjStopAtPos(1, ParserConstants.MINUSASSIGN);
                    }
                    if ((72057594037927936L & j2) != 0) {
                        return jjStopAtPos(1, ParserConstants.STARASSIGN);
                    }
                    if ((144115188075855872L & j2) != 0) {
                        return jjStopAtPos(1, ParserConstants.SLASHASSIGN);
                    }
                    if ((288230376151711744L & j2) != 0) {
                        return jjStopAtPos(1, ParserConstants.ANDASSIGN);
                    }
                    if ((1152921504606846976L & j2) != 0) {
                        return jjStopAtPos(1, ParserConstants.ORASSIGN);
                    }
                    if ((4611686018427387904L & j2) != 0) {
                        return jjStopAtPos(1, ParserConstants.XORASSIGN);
                    }
                    if ((Long.MIN_VALUE & j2) != 0) {
                        return jjStopAtPos(1, ParserConstants.MODASSIGN);
                    }
                    break;
                case '>':
                    if ((1125899906842624L & j2) != 0) {
                        this.jjmatchedKind = 114;
                        this.jjmatchedPos = 1;
                    }
                    return jjMoveStringLiteralDfa2_0(j, 0L, j2, 4503599627370496L, j3, 20L);
                case 'a':
                    return jjMoveStringLiteralDfa2_0(j, 4947869532160L, j2, 576460786663161856L, j3, 0L);
                case 'b':
                    return jjMoveStringLiteralDfa2_0(j, ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLS, j2, 43980465111040L, j3, 0L);
                case 'e':
                    return jjMoveStringLiteralDfa2_0(j, 71468256854016L, j2, 0L, j3, 0L);
                case 'f':
                    if ((4294967296L & j) != 0) {
                        return jjStartNfaWithStates_0(1, 32, 35);
                    }
                    break;
                case 'g':
                    return jjMoveStringLiteralDfa2_0(j, 0L, j2, 1074266112L, j3, 0L);
                case 'h':
                    return jjMoveStringLiteralDfa2_0(j, 603623087556132864L, j2, 0L, j3, 0L);
                case 'i':
                    return jjMoveStringLiteralDfa2_0(j, 402653184L, j2, 0L, j3, 0L);
                case 'l':
                    return jjMoveStringLiteralDfa2_0(j, 545267712L, j2, 562950223953920L, j3, 2L);
                case 'm':
                    return jjMoveStringLiteralDfa2_0(j, 25769803776L, j2, 0L, j3, 0L);
                case 'n':
                    return jjMoveStringLiteralDfa2_0(j, 240534945792L, j2, 0L, j3, 0L);
                case 'o':
                    if ((2097152 & j) != 0) {
                        this.jjmatchedKind = 21;
                        this.jjmatchedPos = 1;
                    }
                    return jjMoveStringLiteralDfa2_0(j, 432345842331682816L, j2, 2305843017803628544L, j3, 0L);
                case 'r':
                    return jjMoveStringLiteralDfa2_0(j, 112616378963333120L, j2, 11258999068426240L, j3, 40L);
                case 't':
                    return jjMoveStringLiteralDfa2_0(j, 844424930131968L, j2, 0L, j3, 0L);
                case 'u':
                    return jjMoveStringLiteralDfa2_0(j, 37383395344384L, j2, 0L, j3, 0L);
                case ParserConstants.MINUSASSIGN /* 119 */:
                    return jjMoveStringLiteralDfa2_0(j, 1125899906842624L, j2, 0L, j3, 0L);
                case ParserConstants.STARASSIGN /* 120 */:
                    return jjMoveStringLiteralDfa2_0(j, 33554432L, j2, 0L, j3, 0L);
                case ParserConstants.SLASHASSIGN /* 121 */:
                    return jjMoveStringLiteralDfa2_0(j, 2251799813701632L, j2, 0L, j3, 0L);
                case ParserConstants.ORASSIGN /* 124 */:
                    if ((4294967296L & j2) != 0) {
                        return jjStopAtPos(1, 96);
                    }
                    break;
            }
            return jjStartNfa_0(0, j, j2, j3);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(0, j, j2, j3);
            return 1;
        }
    }

    private final int jjMoveStringLiteralDfa20_0(long j, long j2, long j3, long j4) {
        long j5 = j2 & j;
        long j6 = j4 & j3;
        if ((j5 | j6) == 0) {
            return jjStartNfa_0(18, 0L, j, j3);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 't':
                    if ((9007199254740992L & j5) != 0) {
                        this.jjmatchedKind = 117;
                        this.jjmatchedPos = 20;
                    }
                    return jjMoveStringLiteralDfa21_0(j5, 0L, j6, 32L);
                default:
                    return jjStartNfa_0(19, 0L, j5, j6);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(19, 0L, j5, j6);
            return 20;
        }
    }

    private final int jjMoveStringLiteralDfa21_0(long j, long j2, long j3, long j4) {
        long j5 = j4 & j3;
        if (((j2 & j) | j5) == 0) {
            return jjStartNfa_0(19, 0L, j, j3);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '_':
                    return jjMoveStringLiteralDfa22_0(j5, 32L);
                default:
                    return jjStartNfa_0(20, 0L, 0L, j5);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(20, 0L, 0L, j5);
            return 21;
        }
    }

    private final int jjMoveStringLiteralDfa22_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(20, 0L, 0L, j);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'a':
                    return jjMoveStringLiteralDfa23_0(j3, 32L);
                default:
                    return jjStartNfa_0(21, 0L, 0L, j3);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(21, 0L, 0L, j3);
            return 22;
        }
    }

    private final int jjMoveStringLiteralDfa23_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(21, 0L, 0L, j);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 's':
                    return jjMoveStringLiteralDfa24_0(j3, 32L);
                default:
                    return jjStartNfa_0(22, 0L, 0L, j3);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(22, 0L, 0L, j3);
            return 23;
        }
    }

    private final int jjMoveStringLiteralDfa24_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(22, 0L, 0L, j);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 's':
                    return jjMoveStringLiteralDfa25_0(j3, 32L);
                default:
                    return jjStartNfa_0(23, 0L, 0L, j3);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(23, 0L, 0L, j3);
            return 24;
        }
    }

    private final int jjMoveStringLiteralDfa25_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(23, 0L, 0L, j);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'i':
                    return jjMoveStringLiteralDfa26_0(j3, 32L);
                default:
                    return jjStartNfa_0(24, 0L, 0L, j3);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(24, 0L, 0L, j3);
            return 25;
        }
    }

    private final int jjMoveStringLiteralDfa26_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(24, 0L, 0L, j);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'g':
                    return jjMoveStringLiteralDfa27_0(j3, 32L);
                default:
                    return jjStartNfa_0(25, 0L, 0L, j3);
            }
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(25, 0L, 0L, j3);
            return 26;
        }
    }

    private final int jjMoveStringLiteralDfa27_0(long j, long j2) {
        long j3 = j2 & j;
        if (j3 == 0) {
            return jjStartNfa_0(25, 0L, 0L, j);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'n':
                    if ((32 & j3) != 0) {
                        return jjStopAtPos(27, ParserConstants.RUNSIGNEDSHIFTASSIGNX);
                    }
                    break;
            }
            return jjStartNfa_0(26, 0L, 0L, j3);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(26, 0L, 0L, j3);
            return 27;
        }
    }

    private final int jjMoveStringLiteralDfa2_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(0, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '=':
                    if ((1 & j9) != 0) {
                        return jjStopAtPos(2, ParserConstants.LSHIFTASSIGN);
                    }
                    if ((4 & j9) != 0) {
                        return jjStopAtPos(2, ParserConstants.RSIGNEDSHIFTASSIGN);
                    }
                    break;
                case '>':
                    if ((4503599627370496L & j8) != 0) {
                        this.jjmatchedKind = 116;
                        this.jjmatchedPos = 2;
                    }
                    return jjMoveStringLiteralDfa3_0(j7, 0L, j8, 0L, j9, 16L);
                case 'a':
                    return jjMoveStringLiteralDfa3_0(j7, 4785074604220416L, j8, 0L, j9, 0L);
                case 'b':
                    return jjMoveStringLiteralDfa3_0(j7, 35184372088832L, j8, 0L, j9, 0L);
                case 'c':
                    return jjMoveStringLiteralDfa3_0(j7, 4398046511104L, j8, 0L, j9, 0L);
                case 'e':
                    return jjMoveStringLiteralDfa3_0(j7, ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_PDF, j8, 562949953421312L, j9, 2L);
                case 'f':
                    return jjMoveStringLiteralDfa3_0(j7, 1048576L, j8, 0L, j9, 0L);
                case 'i':
                    return jjMoveStringLiteralDfa3_0(j7, 721710636379144192L, j8, 11302979533537280L, j9, 40L);
                case 'l':
                    return jjMoveStringLiteralDfa3_0(j7, 288232575242076160L, j8, 0L, j9, 0L);
                case 'n':
                    return jjMoveStringLiteralDfa3_0(j7, 2252075095031808L, j8, 576460786663161856L, j9, 0L);
                case 'o':
                    return jjMoveStringLiteralDfa3_0(j7, 158330211272704L, j8, 0L, j9, 0L);
                case 'p':
                    return jjMoveStringLiteralDfa3_0(j7, 25769803776L, j8, 0L, j9, 0L);
                case 'r':
                    if ((1073741824 & j7) != 0) {
                        return jjStartNfaWithStates_0(2, 30, 35);
                    }
                    if ((8589934592L & j8) != 0) {
                        this.jjmatchedKind = 97;
                        this.jjmatchedPos = 2;
                    }
                    return jjMoveStringLiteralDfa3_0(j7, 27584547717644288L, j8, 2305843009213693952L, j9, 0L);
                case 's':
                    return jjMoveStringLiteralDfa3_0(j7, 34368160768L, j8, 0L, j9, 0L);
                case 't':
                    if ((68719476736L & j7) != 0) {
                        this.jjmatchedKind = 36;
                        this.jjmatchedPos = 2;
                    } else if ((524288 & j8) != 0) {
                        this.jjmatchedKind = 83;
                        this.jjmatchedPos = 2;
                    } else if ((2097152 & j8) != 0) {
                        this.jjmatchedKind = 85;
                        this.jjmatchedPos = 2;
                    }
                    return jjMoveStringLiteralDfa3_0(j7, 71058120065024L, j8, 1342177280L, j9, 0L);
                case 'u':
                    return jjMoveStringLiteralDfa3_0(j7, 36028797039935488L, j8, 0L, j9, 0L);
                case ParserConstants.MINUSASSIGN /* 119 */:
                    if ((1099511627776L & j7) != 0) {
                        return jjStartNfaWithStates_0(2, 40, 35);
                    }
                    break;
                case ParserConstants.SLASHASSIGN /* 121 */:
                    if ((72057594037927936L & j7) != 0) {
                        return jjStartNfaWithStates_0(2, 56, 35);
                    }
                    break;
            }
            return jjStartNfa_0(1, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(1, j7, j8, j9);
            return 2;
        }
    }

    private final int jjMoveStringLiteralDfa3_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(1, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '=':
                    if ((16 & j9) != 0) {
                        return jjStopAtPos(3, ParserConstants.RUNSIGNEDSHIFTASSIGN);
                    }
                    break;
                case '_':
                    return jjMoveStringLiteralDfa4_0(j7, 0L, j8, 2305843009213693952L, j9, 0L);
                case 'a':
                    return jjMoveStringLiteralDfa4_0(j7, 288230377092288512L, j8, 0L, j9, 0L);
                case 'b':
                    return jjMoveStringLiteralDfa4_0(j7, 4194304L, j8, 0L, j9, 0L);
                case 'c':
                    return jjMoveStringLiteralDfa4_0(j7, 2251799813750784L, j8, 0L, j9, 0L);
                case 'd':
                    if ((144115188075855872L & j7) != 0) {
                        return jjStartNfaWithStates_0(3, 57, 35);
                    }
                    if ((34359738368L & j8) != 0) {
                        this.jjmatchedKind = 99;
                        this.jjmatchedPos = 3;
                    }
                    return jjMoveStringLiteralDfa4_0(j7, 0L, j8, 576460752303423488L, j9, 0L);
                case 'e':
                    if ((16384 & j7) != 0) {
                        return jjStartNfaWithStates_0(3, 14, 35);
                    }
                    if ((32768 & j7) != 0) {
                        return jjStartNfaWithStates_0(3, 15, 35);
                    }
                    if ((8388608 & j7) != 0) {
                        return jjStartNfaWithStates_0(3, 23, 35);
                    }
                    return (36028797018963968L & j7) != 0 ? jjStartNfaWithStates_0(3, 55, 35) : jjMoveStringLiteralDfa4_0(j7, 137472507904L, j8, 1342177280L, j9, 0L);
                case 'f':
                    return jjMoveStringLiteralDfa4_0(j7, 0L, j8, 562949953421312L, j9, 2L);
                case 'g':
                    return (274877906944L & j7) != 0 ? jjStartNfaWithStates_0(3, 38, 35) : jjMoveStringLiteralDfa4_0(j7, 0L, j8, 11258999068426240L, j9, 40L);
                case 'i':
                    return jjMoveStringLiteralDfa4_0(j7, 563499709235200L, j8, 0L, j9, 0L);
                case 'k':
                    return jjMoveStringLiteralDfa4_0(j7, 4398046511104L, j8, 0L, j9, 0L);
                case 'l':
                    return (2199023255552L & j7) != 0 ? jjStartNfaWithStates_0(3, 41, 35) : jjMoveStringLiteralDfa4_0(j7, 576495945265448960L, j8, 0L, j9, 0L);
                case 'm':
                    if ((16777216 & j7) != 0) {
                        return jjStartNfaWithStates_0(3, 24, 35);
                    }
                    break;
                case 'n':
                    return jjMoveStringLiteralDfa4_0(j7, 4503599627370496L, j8, 0L, j9, 0L);
                case 'o':
                    return (2147483648L & j7) != 0 ? jjStartNfaWithStates_0(3, 31, 35) : jjMoveStringLiteralDfa4_0(j7, 27021614944092160L, j8, 0L, j9, 0L);
                case 'r':
                    return (131072 & j7) != 0 ? jjStartNfaWithStates_0(3, 17, 35) : jjMoveStringLiteralDfa4_0(j7, 140737488355328L, j8, 0L, j9, 0L);
                case 's':
                    return jjMoveStringLiteralDfa4_0(j7, 67379200L, j8, 0L, j9, 0L);
                case 't':
                    return jjMoveStringLiteralDfa4_0(j7, 1425001429861376L, j8, 43980465111040L, j9, 0L);
                case 'u':
                    return jjMoveStringLiteralDfa4_0(j7, 70368744177664L, j8, 0L, j9, 0L);
                case 'v':
                    return jjMoveStringLiteralDfa4_0(j7, 8796093022208L, j8, 0L, j9, 0L);
            }
            return jjStartNfa_0(2, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(2, j7, j8, j9);
            return 3;
        }
    }

    private final int jjMoveStringLiteralDfa4_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(2, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '_':
                    return jjMoveStringLiteralDfa5_0(j7, 0L, j8, 576460752303423488L, j9, 0L);
                case 'a':
                    return jjMoveStringLiteralDfa5_0(j7, 13228499271680L, j8, 2305843009213693952L, j9, 0L);
                case 'c':
                    return jjMoveStringLiteralDfa5_0(j7, 1688849860263936L, j8, 0L, j9, 0L);
                case 'e':
                    if ((67108864 & j7) != 0) {
                        return jjStartNfaWithStates_0(4, 26, 35);
                    }
                    return (576460752303423488L & j7) != 0 ? jjStartNfaWithStates_0(4, 59, 35) : jjMoveStringLiteralDfa5_0(j7, 17600775981056L, j8, 0L, j9, 0L);
                case 'h':
                    return (65536 & j7) != 0 ? jjStartNfaWithStates_0(4, 16, 35) : jjMoveStringLiteralDfa5_0(j7, 2251799813685248L, j8, 11258999068426240L, j9, 40L);
                case 'i':
                    return jjMoveStringLiteralDfa5_0(j7, 316659349323776L, j8, 0L, j9, 0L);
                case 'k':
                    if ((ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_PDF & j7) != 0) {
                        return jjStartNfaWithStates_0(4, 12, 35);
                    }
                    break;
                case 'l':
                    if ((134217728 & j7) != 0) {
                        this.jjmatchedKind = 27;
                        this.jjmatchedPos = 4;
                    }
                    return jjMoveStringLiteralDfa5_0(j7, 272629760L, j8, 0L, j9, 0L);
                case 'n':
                    return jjMoveStringLiteralDfa5_0(j7, 33554432L, j8, 0L, j9, 0L);
                case 'q':
                    if ((268435456 & j8) != 0) {
                        return jjStopAtPos(4, 92);
                    }
                    if ((1073741824 & j8) != 0) {
                        return jjStopAtPos(4, 94);
                    }
                    break;
                case 'r':
                    return jjMoveStringLiteralDfa5_0(j7, 70523363001344L, j8, 0L, j9, 0L);
                case 's':
                    return (8192 & j7) != 0 ? jjStartNfaWithStates_0(4, 13, 35) : jjMoveStringLiteralDfa5_0(j7, 4503599627370496L, j8, 0L, j9, 0L);
                case 't':
                    if ((262144 & j7) != 0) {
                        return jjStartNfaWithStates_0(4, 18, 35);
                    }
                    if ((536870912 & j7) != 0) {
                        return jjStartNfaWithStates_0(4, 29, 35);
                    }
                    return (140737488355328L & j7) != 0 ? jjStartNfaWithStates_0(4, 47, 35) : jjMoveStringLiteralDfa5_0(j7, 288230376151711744L, j8, 562949953421312L, j9, 2L);
                case 'u':
                    return jjMoveStringLiteralDfa5_0(j7, 1048576L, j8, 0L, j9, 0L);
                case 'v':
                    return jjMoveStringLiteralDfa5_0(j7, 549755813888L, j8, 0L, j9, 0L);
                case ParserConstants.MINUSASSIGN /* 119 */:
                    if ((9007199254740992L & j7) != 0) {
                        this.jjmatchedKind = 53;
                        this.jjmatchedPos = 4;
                    }
                    return jjMoveStringLiteralDfa5_0(j7, 18014398509481984L, j8, 43980465111040L, j9, 0L);
            }
            return jjStartNfa_0(3, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(3, j7, j8, j9);
            return 4;
        }
    }

    private final int jjMoveStringLiteralDfa5_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(3, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '_':
                    return jjMoveStringLiteralDfa6_0(j7, 0L, j8, 562949953421312L, j9, 2L);
                case 'a':
                    return jjMoveStringLiteralDfa6_0(j7, 3072L, j8, 576460752303423488L, j9, 0L);
                case 'c':
                    if ((35184372088832L & j7) != 0) {
                        return jjStartNfaWithStates_0(5, 45, 35);
                    }
                    return (281474976710656L & j7) != 0 ? jjStartNfaWithStates_0(5, 48, 35) : jjMoveStringLiteralDfa6_0(j7, 17592186044416L, j8, 0L, j9, 0L);
                case 'd':
                    return jjMoveStringLiteralDfa6_0(j7, 33554432L, j8, 0L, j9, 0L);
                case 'e':
                    if ((4194304 & j7) != 0) {
                        return jjStartNfaWithStates_0(5, 22, 35);
                    }
                    if ((549755813888L & j7) != 0) {
                        return jjStartNfaWithStates_0(5, 39, 35);
                    }
                    break;
                case 'f':
                    return jjMoveStringLiteralDfa6_0(j7, 137438953472L, j8, 0L, j9, 0L);
                case 'g':
                    return jjMoveStringLiteralDfa6_0(j7, 4398046511104L, j8, 0L, j9, 0L);
                case 'h':
                    if ((1125899906842624L & j7) != 0) {
                        return jjStartNfaWithStates_0(5, 50, 35);
                    }
                    break;
                case 'i':
                    return jjMoveStringLiteralDfa6_0(j7, 292733975779082240L, j8, 43980465111040L, j9, 0L);
                case 'l':
                    return jjMoveStringLiteralDfa6_0(j7, 269484032L, j8, 0L, j9, 0L);
                case 'm':
                    return jjMoveStringLiteralDfa6_0(j7, 8589934592L, j8, 0L, j9, 0L);
                case 'n':
                    return (70368744177664L & j7) != 0 ? jjStartNfaWithStates_0(5, 46, 35) : jjMoveStringLiteralDfa6_0(j7, 34360262656L, j8, 0L, j9, 0L);
                case 'r':
                    return jjMoveStringLiteralDfa6_0(j7, 2251799813685248L, j8, 0L, j9, 0L);
                case 's':
                    return (18014398509481984L & j7) != 0 ? jjStartNfaWithStates_0(5, 54, 35) : jjMoveStringLiteralDfa6_0(j7, 0L, j8, 2305843009213693952L, j9, 0L);
                case 't':
                    return (17179869184L & j7) != 0 ? jjStartNfaWithStates_0(5, 34, 35) : jjMoveStringLiteralDfa6_0(j7, 571746046443520L, j8, 11258999068426240L, j9, 40L);
            }
            return jjStartNfa_0(4, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(4, j7, j8, j9);
            return 5;
        }
    }

    private final int jjMoveStringLiteralDfa6_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(4, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '_':
                    return jjMoveStringLiteralDfa7_0(j7, 0L, j8, 11258999068426240L, j9, 40L);
                case 'a':
                    return jjMoveStringLiteralDfa7_0(j7, 137438953472L, j8, 0L, j9, 0L);
                case 'c':
                    return jjMoveStringLiteralDfa7_0(j7, 34359739392L, j8, 0L, j9, 0L);
                case 'e':
                    if ((4398046511104L & j7) != 0) {
                        return jjStartNfaWithStates_0(6, 42, 35);
                    }
                    return (8796093022208L & j7) != 0 ? jjStartNfaWithStates_0(6, 43, 35) : jjMoveStringLiteralDfa7_0(j7, 4503608217305088L, j8, 0L, j9, 0L);
                case 'f':
                    return jjMoveStringLiteralDfa7_0(j7, 562949953421312L, j8, 0L, j9, 0L);
                case 'l':
                    return jjMoveStringLiteralDfa7_0(j7, 288230376151711744L, j8, 0L, j9, 0L);
                case 'n':
                    if ((ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLSX & j7) != 0) {
                        return jjStartNfaWithStates_0(6, 11, 35);
                    }
                    break;
                case 'o':
                    return jjMoveStringLiteralDfa7_0(j7, 2251799813685248L, j8, 0L, j9, 0L);
                case 's':
                    return (33554432 & j7) != 0 ? jjStartNfaWithStates_0(6, 25, 35) : jjMoveStringLiteralDfa7_0(j7, 0L, j8, 2882910691935649792L, j9, 2L);
                case 't':
                    return (1048576 & j7) != 0 ? jjStartNfaWithStates_0(6, 20, 35) : jjMoveStringLiteralDfa7_0(j7, 17592186044416L, j8, 0L, j9, 0L);
                case 'u':
                    return jjMoveStringLiteralDfa7_0(j7, 524288L, j8, 0L, j9, 0L);
                case ParserConstants.SLASHASSIGN /* 121 */:
                    if ((268435456 & j7) != 0) {
                        return jjStartNfaWithStates_0(6, 28, 35);
                    }
                    break;
            }
            return jjStartNfa_0(5, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(5, j7, j8, j9);
            return 6;
        }
    }

    private final int jjMoveStringLiteralDfa7_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(5, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'c':
                    return jjMoveStringLiteralDfa8_0(j7, 137438953472L, j8, 0L, j9, 0L);
                case 'e':
                    if ((524288 & j7) != 0) {
                        return jjStartNfaWithStates_0(7, 19, 35);
                    }
                    return (288230376151711744L & j7) != 0 ? jjStartNfaWithStates_0(7, 58, 35) : jjMoveStringLiteralDfa8_0(j7, 17626545782784L, j8, 43980465111040L, j9, 0L);
                case 'h':
                    return jjMoveStringLiteralDfa8_0(j7, 0L, j8, 562949953421312L, j9, 2L);
                case 'i':
                    return jjMoveStringLiteralDfa8_0(j7, 0L, j8, 2305843009213693952L, j9, 0L);
                case 'n':
                    return jjMoveStringLiteralDfa8_0(j7, 6755408030990336L, j8, 0L, j9, 0L);
                case 'p':
                    if ((562949953421312L & j7) != 0) {
                        return jjStartNfaWithStates_0(7, 49, 35);
                    }
                    break;
                case 's':
                    return jjMoveStringLiteralDfa8_0(j7, 0L, j8, 578712552117108736L, j9, 8L);
                case 't':
                    if ((ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLS & j7) != 0) {
                        return jjStartNfaWithStates_0(7, 10, 35);
                    }
                    break;
                case 'u':
                    return jjMoveStringLiteralDfa8_0(j7, 0L, j8, 9007199254740992L, j9, 32L);
            }
            return jjStartNfa_0(6, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(6, j7, j8, j9);
            return 7;
        }
    }

    private final int jjMoveStringLiteralDfa8_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(6, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case '_':
                    return jjMoveStringLiteralDfa9_0(j7, 0L, j8, 43980465111040L, j9, 0L);
                case 'd':
                    if ((17592186044416L & j7) != 0) {
                        return jjStartNfaWithStates_0(8, 44, 35);
                    }
                    break;
                case 'e':
                    if ((137438953472L & j7) != 0) {
                        return jjStartNfaWithStates_0(8, 37, 35);
                    }
                    break;
                case 'g':
                    return jjMoveStringLiteralDfa9_0(j7, 0L, j8, 2305843009213693952L, j9, 0L);
                case 'h':
                    return jjMoveStringLiteralDfa9_0(j7, 0L, j8, 2251799813685248L, j9, 8L);
                case 'i':
                    return jjMoveStringLiteralDfa9_0(j7, 2251799813685248L, j8, 577023702256844800L, j9, 2L);
                case 'n':
                    return jjMoveStringLiteralDfa9_0(j7, 0L, j8, 9007199254740992L, j9, 32L);
                case 'o':
                    return jjMoveStringLiteralDfa9_0(j7, 34359738368L, j8, 0L, j9, 0L);
                case 't':
                    return (4503599627370496L & j7) != 0 ? jjStartNfaWithStates_0(8, 52, 35) : jjMoveStringLiteralDfa9_0(j7, 8589934592L, j8, 0L, j9, 0L);
            }
            return jjStartNfa_0(7, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(7, j7, j8, j9);
            return 8;
        }
    }

    private final int jjMoveStringLiteralDfa9_0(long j, long j2, long j3, long j4, long j5, long j6) {
        long j7 = j2 & j;
        long j8 = j4 & j3;
        long j9 = j6 & j5;
        if ((j7 | j8 | j9) == 0) {
            return jjStartNfa_0(7, j, j3, j5);
        }
        try {
            this.curChar = this.input_stream.readChar();
            switch (this.curChar) {
                case 'a':
                    return jjMoveStringLiteralDfa10_0(j7, 0L, j8, 8796093022208L, j9, 0L);
                case 'f':
                    return (34359738368L & j7) != 0 ? jjStartNfaWithStates_0(9, 35, 35) : jjMoveStringLiteralDfa10_0(j7, 0L, j8, 562949953421312L, j9, 2L);
                case 'g':
                    return jjMoveStringLiteralDfa10_0(j7, 0L, j8, 576460752303423488L, j9, 0L);
                case 'i':
                    return jjMoveStringLiteralDfa10_0(j7, 0L, j8, 2251799813685248L, j9, 8L);
                case 'n':
                    if ((2305843009213693952L & j8) != 0) {
                        return jjStopAtPos(9, ParserConstants.ORASSIGNX);
                    }
                    break;
                case 'o':
                    return jjMoveStringLiteralDfa10_0(j7, 0L, j8, 35184372088832L, j9, 0L);
                case 's':
                    return (8589934592L & j7) != 0 ? jjStartNfaWithStates_0(9, 33, 35) : jjMoveStringLiteralDfa10_0(j7, 0L, j8, 9007199254740992L, j9, 32L);
                case ParserConstants.ANDASSIGN /* 122 */:
                    return jjMoveStringLiteralDfa10_0(j7, 2251799813685248L, j8, 0L, j9, 0L);
            }
            return jjStartNfa_0(8, j7, j8, j9);
        } catch (IOException e) {
            jjStopStringLiteralDfa_0(8, j7, j8, j9);
            return 9;
        }
    }

    private final int jjStartNfaWithStates_0(int i, int i2, int i3) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_0(i3, i + 1);
        } catch (IOException e) {
            return i + 1;
        }
    }

    private final int jjStartNfa_0(int i, long j, long j2, long j3) {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(i, j, j2, j3), i + 1);
    }

    private final int jjStopAtPos(int i, int i2) {
        this.jjmatchedKind = i2;
        this.jjmatchedPos = i;
        return i + 1;
    }

    private final int jjStopStringLiteralDfa_0(int i, long j, long j2, long j3) {
        switch (i) {
            case 0:
                if ((144117387099111424L & j2) != 0) {
                    return 56;
                }
                if ((62 & j) != 0) {
                    return 0;
                }
                if ((65536 & j2) != 0) {
                    return 11;
                }
                if ((1152921504606845952L & j) == 0) {
                    return -1;
                }
                this.jjmatchedKind = 69;
                return 35;
            case 1:
                if ((4301258752L & j) != 0) {
                    return 35;
                }
                if ((1152921500305587200L & j) == 0) {
                    return -1;
                }
                if (this.jjmatchedPos == 1) {
                    return 35;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 1;
                return 35;
            case 2:
                if ((1080862599528053760L & j) == 0) {
                    return (72058900781727744L & j) == 0 ? -1 : 35;
                }
                if (this.jjmatchedPos == 2) {
                    return 35;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 2;
                return 35;
            case 3:
                if ((900716275798195200L & j) == 0) {
                    return (180146461168812032L & j) == 0 ? -1 : 35;
                }
                if (this.jjmatchedPos == 3) {
                    return 35;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 3;
                return 35;
            case 4:
                if ((603623088562974720L & j) != 0) {
                    return 35;
                }
                if ((297093187235220480L & j) == 0) {
                    return -1;
                }
                if (this.jjmatchedPos == 4) {
                    return 35;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 4;
                return 35;
            case 5:
                if ((295579692563958784L & j) == 0) {
                    return (19527893449179136L & j) == 0 ? -1 : 35;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 5;
                return 35;
            case 6:
                if ((295566498121384960L & j) == 0) {
                    return (13194442573824L & j) == 0 ? -1 : 35;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 6;
                return 35;
            case 7:
                if ((288793326105658368L & j) != 0) {
                    return 35;
                }
                if ((6773172015726592L & j) == 0) {
                    return -1;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 7;
                return 35;
            case 8:
                if ((2251842763358208L & j) == 0) {
                    return (4521329252368384L & j) == 0 ? -1 : 35;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 8;
                return 35;
            case 9:
                if ((2251799813685248L & j) == 0) {
                    return (42949672960L & j) == 0 ? -1 : 35;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 9;
                return 35;
            case 10:
                if ((2251799813685248L & j) == 0) {
                    return -1;
                }
                if (this.jjmatchedPos == 10) {
                    return 35;
                }
                this.jjmatchedKind = 69;
                this.jjmatchedPos = 10;
                return 35;
            case 11:
                return (2251799813685248L & j) == 0 ? -1 : 35;
            default:
                return -1;
        }
    }

    public void ReInit(JavaCharStream javaCharStream) {
        this.jjnewStateCnt = 0;
        this.jjmatchedPos = 0;
        this.curLexState = this.defaultLexState;
        this.input_stream = javaCharStream;
        ReInitRounds();
    }

    public void ReInit(JavaCharStream javaCharStream, int i) {
        ReInit(javaCharStream);
        SwitchTo(i);
    }

    public void SwitchTo(int i) {
        if (i >= 1 || i < 0) {
            throw new TokenMgrError(new StringBuffer().append("Error: Ignoring invalid lexical state : ").append(i).append(". State unchanged.").toString(), 2);
        }
        this.curLexState = i;
    }

    public Token getNextToken() {
        int i;
        int i2;
        boolean z;
        String strGetImage = null;
        Token token = null;
        while (true) {
            try {
                this.curChar = this.input_stream.BeginToken();
                this.jjmatchedKind = Integer.MAX_VALUE;
                this.jjmatchedPos = 0;
                int iJjMoveStringLiteralDfa0_0 = jjMoveStringLiteralDfa0_0();
                if (this.jjmatchedKind == Integer.MAX_VALUE) {
                    int endLine = this.input_stream.getEndLine();
                    int endColumn = this.input_stream.getEndColumn();
                    try {
                        this.input_stream.readChar();
                        this.input_stream.backup(1);
                        z = false;
                        i2 = endLine;
                    } catch (IOException e) {
                        String strGetImage2 = iJjMoveStringLiteralDfa0_0 <= 1 ? Constants.MAIN_VERSION_TAG : this.input_stream.GetImage();
                        if (this.curChar == '\n' || this.curChar == '\r') {
                            endLine++;
                            i = 0;
                        } else {
                            i = endColumn + 1;
                        }
                        strGetImage = strGetImage2;
                        endColumn = i;
                        i2 = endLine;
                        z = true;
                    }
                    if (!z) {
                        this.input_stream.backup(1);
                        strGetImage = iJjMoveStringLiteralDfa0_0 <= 1 ? Constants.MAIN_VERSION_TAG : this.input_stream.GetImage();
                    }
                    throw new TokenMgrError(z, this.curLexState, i2, endColumn, strGetImage, this.curChar, 0);
                }
                if (this.jjmatchedPos + 1 < iJjMoveStringLiteralDfa0_0) {
                    this.input_stream.backup((iJjMoveStringLiteralDfa0_0 - this.jjmatchedPos) - 1);
                }
                if ((jjtoToken[this.jjmatchedKind >> 6] & (1 << (this.jjmatchedKind & 63))) != 0) {
                    Token tokenJjFillToken = jjFillToken();
                    tokenJjFillToken.specialToken = token;
                    return tokenJjFillToken;
                }
                if ((jjtoSpecial[this.jjmatchedKind >> 6] & (1 << (this.jjmatchedKind & 63))) != 0) {
                    Token tokenJjFillToken2 = jjFillToken();
                    if (token == null) {
                        token = tokenJjFillToken2;
                    } else {
                        tokenJjFillToken2.specialToken = token;
                        token.next = tokenJjFillToken2;
                        token = tokenJjFillToken2;
                    }
                }
            } catch (IOException e2) {
                this.jjmatchedKind = 0;
                Token tokenJjFillToken3 = jjFillToken();
                tokenJjFillToken3.specialToken = token;
                return tokenJjFillToken3;
            }
        }
    }

    protected Token jjFillToken() {
        Token tokenNewToken = Token.newToken(this.jjmatchedKind);
        tokenNewToken.kind = this.jjmatchedKind;
        String strGetImage = jjstrLiteralImages[this.jjmatchedKind];
        if (strGetImage == null) {
            strGetImage = this.input_stream.GetImage();
        }
        tokenNewToken.image = strGetImage;
        tokenNewToken.beginLine = this.input_stream.getBeginLine();
        tokenNewToken.beginColumn = this.input_stream.getBeginColumn();
        tokenNewToken.endLine = this.input_stream.getEndLine();
        tokenNewToken.endColumn = this.input_stream.getEndColumn();
        return tokenNewToken;
    }

    public void setDebugStream(PrintStream printStream) {
        this.debugStream = printStream;
    }
}
