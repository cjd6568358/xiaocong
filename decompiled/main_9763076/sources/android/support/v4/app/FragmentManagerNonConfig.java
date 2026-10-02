package android.support.v4.app;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FragmentManagerNonConfig {
    private final List<FragmentManagerNonConfig> mChildNonConfigs;
    private final List<Fragment> mFragments;

    FragmentManagerNonConfig(List<Fragment> fragments, List<FragmentManagerNonConfig> childNonConfigs) {
        this.mFragments = fragments;
        this.mChildNonConfigs = childNonConfigs;
    }

    List<Fragment> getFragments() {
        return this.mFragments;
    }

    List<FragmentManagerNonConfig> getChildNonConfigs() {
        return this.mChildNonConfigs;
    }
}
