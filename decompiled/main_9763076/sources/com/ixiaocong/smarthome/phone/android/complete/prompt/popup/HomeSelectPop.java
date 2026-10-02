package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import bsh.ParserConstants;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ScreenUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.HomeSelectPopAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.HomeSelectFamilyCallback;
import com.xiaocong.smarthome.httplib.model.HomeListModel;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeSelectPop {
    private HomeSelectPopAdapter homeSelectPopAdapter;
    private LinearLayout mAddLayout;
    private RecyclerView mRecycleView;
    private PopupWindow mSelectPop;

    public static HomeSelectPop getInstance() {
        return SelectAddPopHolder.INSTANCE;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void showSelectPop(Context context, View view, List<HomeListModel.HomeListBean> data, final HomeSelectFamilyCallback callback) {
        View popView = LayoutInflater.from(context).inflate(R.layout.pop_select_home_layout, (ViewGroup) null);
        this.mRecycleView = (RecyclerView) popView.findViewById(R.id.rv_home_select_family);
        this.mRecycleView.setLayoutManager(new LinearLayoutManager(context));
        this.mAddLayout = (LinearLayout) popView.findViewById(R.id.ll_add_home_pop);
        this.homeSelectPopAdapter = new HomeSelectPopAdapter();
        this.mRecycleView.setAdapter(this.homeSelectPopAdapter);
        this.homeSelectPopAdapter.setNewData(data);
        this.homeSelectPopAdapter.notifyDataSetChanged();
        this.mRecycleView.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.HomeSelectPop.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view2, int position) {
                if (callback != null) {
                    callback.onFamilyItemClick(position);
                }
                HomeSelectPop.this.mSelectPop.dismiss();
            }
        });
        this.mAddLayout.setOnClickListener(HomeSelectPop$$Lambda$1.lambdaFactory$(this, callback));
        this.mSelectPop = new PopupWindow();
        this.mSelectPop.setContentView(popView);
        this.mSelectPop.setWidth((int) context.getResources().getDimension(R.dimen.x210));
        this.mSelectPop.setHeight(-2);
        this.mSelectPop.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mSelectPop.setBackgroundDrawable(new BitmapDrawable());
        this.mSelectPop.setOutsideTouchable(true);
        this.mSelectPop.setFocusable(true);
        int height = ScreenUtils.getStatusHeight(context) + ParserConstants.RSIGNEDSHIFTASSIGN;
        this.mSelectPop.showAtLocation(view, 51, (int) context.getResources().getDimension(R.dimen.x30), height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSelectPop$0(HomeSelectFamilyCallback callback, View v) {
        if (callback != null) {
            callback.onAddFamily();
        }
        this.mSelectPop.dismiss();
    }

    private static class SelectAddPopHolder {
        private static final HomeSelectPop INSTANCE = new HomeSelectPop();
    }
}
