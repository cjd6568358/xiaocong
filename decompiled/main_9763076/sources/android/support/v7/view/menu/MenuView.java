package android.support.v7.view.menu;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface MenuView {

    public interface ItemView {
        MenuItemImpl getItemData();

        void initialize(MenuItemImpl menuItemImpl, int i);

        boolean prefersCondensedTitle();
    }

    void initialize(MenuBuilder menuBuilder);
}
