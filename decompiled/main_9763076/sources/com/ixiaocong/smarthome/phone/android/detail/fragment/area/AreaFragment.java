package com.ixiaocong.smarthome.phone.android.detail.fragment.area;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.AreaListAdapter;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;
import com.xiaocong.smarthome.httplib.model.AreaInfoModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AreaFragment extends XcBaseFragment {
    private AreaListAdapter mAreaAdapter;
    private List<AreaInfoModel.DistrictListBean> mListData;
    private OnFragmentInteractionListener mListener;
    private String mPid;
    private RecyclerView rvArea;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(AreaInfoModel.DistrictListBean districtListBean);
    }

    public static AreaFragment newInstance(String param1) {
        AreaFragment fragment = new AreaFragment();
        Bundle args = new Bundle();
        args.putString("parentCode", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mPid = getArguments().getString("parentCode");
        }
    }

    private void setLListener() {
        this.rvArea.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.area.AreaFragment.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                AreaInfoModel.DistrictListBean areaInfo = (AreaInfoModel.DistrictListBean) adapter.getItem(position);
                if (areaInfo != null && AreaFragment.this.mListener != null) {
                    AreaFragment.this.mListener.onFragmentInteraction(areaInfo);
                }
            }
        });
    }

    protected int getLayoutId() {
        return R.layout.fragment_area;
    }

    protected void initView() {
        this.rvArea = (RecyclerView) $(R.id.rv_area_list);
        this.rvArea.setLayoutManager(new LinearLayoutManager(getActivity()));
        setLListener();
    }

    protected void initData() {
        loadAreaInfo();
    }

    private void loadAreaInfo() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("padcode", this.mPid);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("district/list");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.area.AreaFragment.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AreaInfoModel areaInfoModel = (AreaInfoModel) JSON.parseObject(var1.getData(), AreaInfoModel.class);
                AreaFragment.this.mListData = areaInfoModel.getDistrictList();
                AreaFragment.this.mAreaAdapter.setNewData(AreaFragment.this.mListData);
                AreaFragment.this.mAreaAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AreaFragment.this.mActivity, var1.getErrorMessage());
            }
        });
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
    public void initAdapter() {
        super.initAdapter();
        this.mAreaAdapter = new AreaListAdapter();
        this.rvArea.setAdapter(this.mAreaAdapter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }
}
