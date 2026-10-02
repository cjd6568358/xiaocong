package com.ixiaocong.smarthome.phone.android.detail.fragment.home.scene;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.ConfigScenePop;
import com.ixiaocong.smarthome.phone.android.detail.adater.TabHomeSceneRecyclerAdapter;
import com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeListener;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.xiaocong.smarthome.greendao.model.insert.SceneDB;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabHomeSceneView extends RelativeLayout {
    private Context mContext;
    private TabHomeSceneRecyclerAdapter mHomeSceneAdapter;
    private RecyclerView mHomeSceneRecycler;
    private int[] mScenceDefault;
    private List<SceneDB> mSceneList;

    public TabHomeSceneView(Context context) {
        super(context);
        this.mScenceDefault = new int[]{R.drawable.icon_tab_home_scene_go_home, R.drawable.icon_tab_home_scene_leave_home, R.drawable.icon_tab_home_scene_sleep, R.drawable.icon_tab_home_scene_all};
        this.mContext = context;
    }

    public TabHomeSceneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mScenceDefault = new int[]{R.drawable.icon_tab_home_scene_go_home, R.drawable.icon_tab_home_scene_leave_home, R.drawable.icon_tab_home_scene_sleep, R.drawable.icon_tab_home_scene_all};
        this.mContext = context;
        initView(attrs);
        initData();
        addListener();
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(this.mContext).inflate(R.layout.layout_tab_home_scene, this);
        this.mHomeSceneRecycler = (RecyclerView) findViewById(R.id.rv_tab_home_scene);
        this.mHomeSceneRecycler.setLayoutManager(new GridLayoutManager(this.mContext, 4));
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
    private void initData() {
        this.mHomeSceneAdapter = new TabHomeSceneRecyclerAdapter(this.mContext, this.mScenceDefault);
        this.mHomeSceneRecycler.setAdapter(this.mHomeSceneAdapter);
    }

    public void setSceneData(List<SceneDB> sceneData) {
        if (sceneData != null && sceneData.size() > 0) {
            this.mSceneList = new ArrayList();
            this.mSceneList.add(sceneData.get(0));
            this.mSceneList.add(sceneData.get(1));
            this.mSceneList.add(sceneData.get(2));
            this.mSceneList.add(new SceneDB());
            this.mHomeSceneAdapter.setNewData(this.mSceneList);
            this.mHomeSceneAdapter.notifyDataSetChanged();
        }
    }

    private void addListener() {
        this.mHomeSceneRecycler.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.scene.TabHomeSceneView.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                if (TabHomeSceneView.this.mSceneList != null && TabHomeSceneView.this.mSceneList.size() > 0) {
                    if (TextUtils.isEmpty(((SceneDB) TabHomeSceneView.this.mSceneList.get(position)).getStatus()) || !((SceneDB) TabHomeSceneView.this.mSceneList.get(position)).getStatus().equals(PushConstants.PUSH_TYPE_NOTIFY)) {
                        TabHomeListener.getInstance().sceneItemClick(TabHomeSceneView.this.mContext, TabHomeSceneView.this.mSceneList, position);
                    } else {
                        ConfigScenePop.getInstance().showConfigScenePopup(TabHomeSceneView.this.mContext, TabHomeSceneView.this.mSceneList, position, TabHomeSceneView.this.mHomeSceneRecycler);
                    }
                }
            }
        });
    }
}
