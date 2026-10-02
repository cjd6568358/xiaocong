package com.ixiaocong.smarthome.phone.android.detail.activity.theme;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.detail.adater.ThemeStyleAdapter;
import com.ixiaocong.smarthome.phone.android.event.eventbus.ThemeEvent;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.ThemeStyleModel;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import skin.support.SkinCompatManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ThemeStyleActivity extends XcBaseActivity implements View.OnClickListener, ThemeStyleAdapter.OnSelectItemListener {
    private ImageView back;
    private List<ThemeStyleModel> mData = new ArrayList();
    private RecyclerView mRecyclerView;
    private ThemeStyleAdapter themeStyleAdapter;

    protected int getLayoutId() {
        return R.layout.activity_theme_style;
    }

    protected void initView() {
        this.back = (ImageView) $(R.id.left_titlebar_image);
        this.mRecyclerView = (RecyclerView) $(R.id.rv_theme_style);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mActivity, 2));
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
        this.mData.add(new ThemeStyleModel("default", R.mipmap.icon_theme_style_default, false));
        this.mData.add(new ThemeStyleModel("pink", R.mipmap.icon_theme_style_pink, false));
        this.mData.add(new ThemeStyleModel("tinge", R.mipmap.icon_theme_style_tinge, false));
        this.themeStyleAdapter = new ThemeStyleAdapter(this.mActivity, this.mData);
        this.mRecyclerView.setAdapter(this.themeStyleAdapter);
        this.themeStyleAdapter.setNewData(this.mData);
        this.themeStyleAdapter.notifyDataSetChanged();
    }

    public void addListener() {
        super.addListener();
        this.themeStyleAdapter.setOnSelectItemClickListener(this);
        this.back.setOnClickListener(this);
    }

    protected void initData() {
        String theme = (String) SpUtils.getFromLocal(this.mActivity, "ixiaocong_config", "app_theme_style", Constants.MAIN_VERSION_TAG);
        if (TextUtils.isEmpty(theme) || "default".equals(theme)) {
            this.mData.get(0).setSelected(true);
        } else if ("pink".equals(theme)) {
            this.mData.get(1).setSelected(true);
        } else if ("tinge".equals(theme)) {
            this.mData.get(2).setSelected(true);
        }
        this.themeStyleAdapter.setNewData(this.mData);
        this.themeStyleAdapter.notifyDataSetChanged();
    }

    @Override // com.ixiaocong.smarthome.phone.android.detail.adater.ThemeStyleAdapter.OnSelectItemListener
    public void onSelectItemClick(int postion) {
        try {
            String theme = this.mData.get(postion).getThemeName();
            if (!TextUtils.isEmpty(theme)) {
                if (theme.equals("default")) {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                } else {
                    SkinCompatManager.getInstance().loadSkin(theme, null, 1);
                }
                SpUtils.saveToLocal(this.mActivity, "ixiaocong_config", "app_theme_style", theme);
                EventBus.getDefault().post(new ThemeEvent(theme));
                XcLogger.i("ThemeStyleActivity", "ThemeStyle:" + theme);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                onBackPressed();
                break;
        }
    }
}
