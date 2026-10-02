package android.support.transition;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AutoTransition extends TransitionSet {
    public AutoTransition() {
        init();
    }

    private void init() {
        setOrdering(1);
        addTransition(new Fade(2)).addTransition(new ChangeBounds()).addTransition(new Fade(1));
    }
}
