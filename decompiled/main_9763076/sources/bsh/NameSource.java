package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface NameSource {

    public interface Listener {
        void nameSourceChanged(NameSource nameSource);
    }

    void addNameSourceListener(Listener listener);

    String[] getAllNames();
}
