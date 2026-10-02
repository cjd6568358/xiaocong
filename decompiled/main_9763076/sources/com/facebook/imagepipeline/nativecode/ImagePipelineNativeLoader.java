package com.facebook.imagepipeline.nativecode;

import com.facebook.common.soloader.SoLoaderShim;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImagePipelineNativeLoader {
    public static final List<String> DEPENDENCIES;

    static {
        List<String> dependencies = new ArrayList<>();
        DEPENDENCIES = Collections.unmodifiableList(dependencies);
    }

    public static void load() {
        SoLoaderShim.loadLibrary("imagepipeline");
    }
}
