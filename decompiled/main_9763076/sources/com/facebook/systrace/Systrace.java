package com.facebook.systrace;

import android.os.Build;
import android.os.Trace;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Systrace {

    public enum EventScope {
        THREAD('t'),
        PROCESS('p'),
        GLOBAL('g');

        private final char mCode;

        EventScope(char code) {
            this.mCode = code;
        }

        public char getCode() {
            return this.mCode;
        }
    }

    public static void registerListener(TraceListener listener) {
    }

    public static void unregisterListener(TraceListener listener) {
    }

    public static boolean isTracing(long tag) {
        return false;
    }

    public static void traceInstant(long tag, String title, EventScope scope) {
    }

    public static void beginSection(long tag, String sectionName) {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.beginSection(sectionName);
        }
    }

    public static void endSection(long tag) {
        if (Build.VERSION.SDK_INT >= 18) {
            Trace.endSection();
        }
    }

    public static void beginAsyncSection(long tag, String sectionName, int cookie) {
    }

    public static void endAsyncSection(long tag, String sectionName, int cookie) {
    }

    public static void traceCounter(long tag, String counterName, int counterValue) {
    }

    public static void startAsyncFlow(long tag, String sectionName, int cookie) {
    }

    public static void endAsyncFlow(long tag, String sectionName, int cookie) {
    }
}
