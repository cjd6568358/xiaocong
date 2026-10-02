package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ReflectManager {
    private static ReflectManager rfm;

    public static boolean RMSetAccessible(Object obj) throws Capabilities.Unavailable {
        return getReflectManager().setAccessible(obj);
    }

    public static ReflectManager getReflectManager() throws Capabilities.Unavailable {
        if (rfm == null) {
            try {
                rfm = (ReflectManager) Class.forName("bsh.reflect.ReflectManagerImpl").newInstance();
            } catch (Exception e) {
                throw new Capabilities.Unavailable(new StringBuffer().append("Reflect Manager unavailable: ").append(e).toString());
            }
        }
        return rfm;
    }

    public abstract boolean setAccessible(Object obj);
}
