package skin.support.observe;

import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SkinObservable {
    private final ArrayList<SkinObserver> observers = new ArrayList<>();

    public synchronized void addObserver(SkinObserver o) {
        try {
            if (o == null) {
                throw new NullPointerException();
            }
            if (!this.observers.contains(o)) {
                this.observers.add(o);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void deleteObserver(SkinObserver o) {
        this.observers.remove(o);
    }

    public void notifyUpdateSkin() {
        notifyUpdateSkin(null);
    }

    public void notifyUpdateSkin(Object arg) {
        SkinObserver[] arrLocal;
        synchronized (this) {
            arrLocal = (SkinObserver[]) this.observers.toArray(new SkinObserver[this.observers.size()]);
        }
        for (int i = arrLocal.length - 1; i >= 0; i--) {
            arrLocal[i].updateSkin(this, arg);
        }
    }
}
