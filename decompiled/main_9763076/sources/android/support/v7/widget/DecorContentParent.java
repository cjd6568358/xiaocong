package android.support.v7.widget;

import android.support.v7.view.menu.MenuPresenter;
import android.view.Menu;
import android.view.Window;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface DecorContentParent {
    boolean canShowOverflowMenu();

    void dismissPopups();

    boolean hideOverflowMenu();

    void initFeature(int i);

    boolean isOverflowMenuShowPending();

    boolean isOverflowMenuShowing();

    void setMenu(Menu menu, MenuPresenter.Callback callback);

    void setMenuPrepared();

    void setWindowCallback(Window.Callback callback);

    void setWindowTitle(CharSequence charSequence);

    boolean showOverflowMenu();
}
