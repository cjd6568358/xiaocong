package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.hzy.tvmao.ir.ac.ACConstants;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeAddGroupActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeAddHomeActivity;
import com.ixiaocong.smarthome.phone.android.detail.adater.DevSelectGroupPopAdapter;
import com.ixiaocong.smarthome.phone.android.detail.adater.DevSelectHomePopAdapter;
import com.xiaocong.smarthome.httplib.model.HomeGroupListModel;
import com.xiaocong.smarthome.httplib.model.HomeListModel;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SelectDevHomeOrGroupPop {
    private LinearLayout mAddLayout;
    private Context mContext;
    private TextView mFooterName;
    private DevSelectGroupPopAdapter mGroupAdapter;
    private DevSelectHomePopAdapter mHomeAdapter;
    private View mOutView;
    private RecyclerView mRecyclerView;
    private PopupWindow mWindow;

    public interface ResultCallback {
        void resultData(String str, String str2);
    }

    public static SelectDevHomeOrGroupPop getInstance() {
        return SelectDevHomeOrGroupPopHolder.INSTANCE;
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
    public void showPop(Context context, final ResultCallback callback, View showView, final boolean isHome, String homeId) {
        this.mContext = context;
        View popView = LayoutInflater.from(context).inflate(R.layout.pop_select_dev_home_or_group_layout, (ViewGroup) null);
        View footerView = View.inflate(context, R.layout.scene_relate_footer_layout, null);
        this.mFooterName = (TextView) footerView.findViewById(R.id.tv_add_footer_name);
        this.mAddLayout = (LinearLayout) footerView.findViewById(R.id.ll_add_scene_relate_layout);
        this.mAddLayout.setOnClickListener(SelectDevHomeOrGroupPop$$Lambda$1.lambdaFactory$(this, isHome, context, homeId));
        this.mWindow = new PopupWindow(popView, -1, -2);
        this.mWindow.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mWindow.setBackgroundDrawable(new BitmapDrawable());
        this.mWindow.setOutsideTouchable(true);
        this.mWindow.showAtLocation(showView, 83, 0, 0);
        this.mRecyclerView = (RecyclerView) popView.findViewById(R.id.rv_pop_select_home_or_group);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.mOutView = popView.findViewById(R.id.pop_select_home_or_group_view);
        this.mOutView.setOnClickListener(SelectDevHomeOrGroupPop$$Lambda$2.lambdaFactory$(this));
        if (isHome) {
            this.mHomeAdapter = new DevSelectHomePopAdapter();
            this.mFooterName.setText("新增家庭");
            this.mHomeAdapter.addFooterView(footerView);
            this.mRecyclerView.setAdapter(this.mHomeAdapter);
            loadHomeList();
        } else {
            this.mGroupAdapter = new DevSelectGroupPopAdapter();
            this.mFooterName.setText("新增分组");
            this.mGroupAdapter.addFooterView(footerView);
            this.mRecyclerView.setAdapter(this.mGroupAdapter);
            loadHomeListData(homeId);
        }
        this.mRecyclerView.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.SelectDevHomeOrGroupPop.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                if (isHome) {
                    callback.resultData(((HomeListModel.HomeListBean) SelectDevHomeOrGroupPop.this.mHomeAdapter.getData().get(position)).getName(), ((HomeListModel.HomeListBean) SelectDevHomeOrGroupPop.this.mHomeAdapter.getData().get(position)).getId());
                } else {
                    callback.resultData(((HomeGroupListModel.GroupListBean) SelectDevHomeOrGroupPop.this.mGroupAdapter.getData().get(position)).getName(), ((HomeGroupListModel.GroupListBean) SelectDevHomeOrGroupPop.this.mGroupAdapter.getData().get(position)).getId());
                }
                SelectDevHomeOrGroupPop.this.mWindow.dismiss();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPop$0(boolean isHome, Context context, String homeId, View v) {
        Intent intent = new Intent();
        intent.putExtra("intentCode", ACConstants.TAG_TEMPERATURE1);
        if (isHome) {
            intent.setClass(context, HomeAddHomeActivity.class);
        } else {
            intent.setClass(context, HomeAddGroupActivity.class);
            intent.putExtra("intent_home_id", homeId);
        }
        context.startActivity(intent);
        this.mWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPop$1(View v) {
        this.mWindow.dismiss();
    }

    protected void loadHomeList() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("home/list");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.SelectDevHomeOrGroupPop.2
            public void onComplete(XCResponseBean var1) {
                HomeListModel homeListModel = (HomeListModel) JSON.parseObject(var1.getData(), HomeListModel.class);
                SelectDevHomeOrGroupPop.this.mHomeAdapter.setNewData(homeListModel.getHomeList());
                SelectDevHomeOrGroupPop.this.mHomeAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(SelectDevHomeOrGroupPop.this.mContext, var1.getErrorMessage());
            }
        });
    }

    protected void loadHomeListData(String homeId) {
        if (TextUtils.isEmpty(homeId)) {
            ToastUtils.showShort(this.mContext, "请先选择家庭");
            this.mWindow.dismiss();
            return;
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("homeId", homeId);
        httpSetting.setNeedSign(false);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("group/list");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.SelectDevHomeOrGroupPop.3
            public void onComplete(XCResponseBean var1) {
                HomeGroupListModel homeGroupListModel = (HomeGroupListModel) JSON.parseObject(var1.getData(), HomeGroupListModel.class);
                SelectDevHomeOrGroupPop.this.mGroupAdapter.setNewData(homeGroupListModel.getGroupList());
                SelectDevHomeOrGroupPop.this.mGroupAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(SelectDevHomeOrGroupPop.this.mContext, var1.getErrorMessage());
            }
        });
    }

    private static final class SelectDevHomeOrGroupPopHolder {
        private static final SelectDevHomeOrGroupPop INSTANCE = new SelectDevHomeOrGroupPop();
    }
}
