package com.facebook.systrace;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class SystraceMessage {
    private static final Builder NOOP_BUILDER = new NoopBuilder();

    public static abstract class Builder {
        public abstract Builder arg(String str, int i);

        public abstract Builder arg(String str, Object obj);

        public abstract void flush();
    }

    public static Builder beginSection(long tag, String sectionName) {
        return NOOP_BUILDER;
    }

    private static class NoopBuilder extends Builder {
        private NoopBuilder() {
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public void flush() {
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String key, Object value) {
            return this;
        }

        @Override // com.facebook.systrace.SystraceMessage.Builder
        public Builder arg(String key, int value) {
            return this;
        }
    }
}
