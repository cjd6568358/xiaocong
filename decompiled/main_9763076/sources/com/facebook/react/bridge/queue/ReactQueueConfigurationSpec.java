package com.facebook.react.bridge.queue;

import android.os.Build;
import com.facebook.infer.annotation.Assertions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactQueueConfigurationSpec {
    private final MessageQueueThreadSpec mJSQueueThreadSpec;
    private final MessageQueueThreadSpec mNativeModulesQueueThreadSpec;

    private ReactQueueConfigurationSpec(MessageQueueThreadSpec nativeModulesQueueThreadSpec, MessageQueueThreadSpec jsQueueThreadSpec) {
        this.mNativeModulesQueueThreadSpec = nativeModulesQueueThreadSpec;
        this.mJSQueueThreadSpec = jsQueueThreadSpec;
    }

    public MessageQueueThreadSpec getNativeModulesQueueThreadSpec() {
        return this.mNativeModulesQueueThreadSpec;
    }

    public MessageQueueThreadSpec getJSQueueThreadSpec() {
        return this.mJSQueueThreadSpec;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ReactQueueConfigurationSpec createDefault() {
        MessageQueueThreadSpec spec;
        if (Build.VERSION.SDK_INT < 21) {
            spec = MessageQueueThreadSpec.newBackgroundThreadSpec("native_modules", 2000000L);
        } else {
            spec = MessageQueueThreadSpec.newBackgroundThreadSpec("native_modules");
        }
        return builder().setJSQueueThreadSpec(MessageQueueThreadSpec.newBackgroundThreadSpec("js")).setNativeModulesQueueThreadSpec(spec).build();
    }

    public static class Builder {
        private MessageQueueThreadSpec mJSQueueSpec;
        private MessageQueueThreadSpec mNativeModulesQueueSpec;

        public Builder setNativeModulesQueueThreadSpec(MessageQueueThreadSpec spec) {
            Assertions.assertCondition(this.mNativeModulesQueueSpec == null, "Setting native modules queue spec multiple times!");
            this.mNativeModulesQueueSpec = spec;
            return this;
        }

        public Builder setJSQueueThreadSpec(MessageQueueThreadSpec spec) {
            Assertions.assertCondition(this.mJSQueueSpec == null, "Setting JS queue multiple times!");
            this.mJSQueueSpec = spec;
            return this;
        }

        public ReactQueueConfigurationSpec build() {
            return new ReactQueueConfigurationSpec((MessageQueueThreadSpec) Assertions.assertNotNull(this.mNativeModulesQueueSpec), (MessageQueueThreadSpec) Assertions.assertNotNull(this.mJSQueueSpec));
        }
    }
}
