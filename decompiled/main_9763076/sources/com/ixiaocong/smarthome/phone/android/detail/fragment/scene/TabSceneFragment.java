package com.ixiaocong.smarthome.phone.android.detail.fragment.scene;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.mobstat.StatService;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ScreenUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttCreateActivity;
import com.ixiaocong.smarthome.phone.android.detail.adater.TabScenePagerAdapter;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;
import com.xiaocong.smarthome.zxing.utils.DPIUtil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabSceneFragment extends XcBaseFragment implements View.OnClickListener {
    private ImageView mCommunRightImg;
    private List<Fragment> mFragmentList;
    private TabScenePagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private TextView mTitleText;
    private ViewPager mViewPager;

    protected int getLayoutId() {
        return R.layout.fragment_scene_tab;
    }

    protected void initView() {
        $(R.id.tab_home_statusbar_view).setLayoutParams(new RelativeLayout.LayoutParams(-1, ScreenUtils.getStatusHeight(this.mActivity)));
        $(R.id.iv_left_tab_home_title_image).setVisibility(4);
        $(R.id.iv_right_tab_home_title_msg).setVisibility(4);
        this.mCommunRightImg = (ImageView) $(R.id.iv_right_tab_home_title_image);
        this.mTitleText = (TextView) $(R.id.tv_centertxt_tab_home_title);
        this.mTitleText.setText("场景");
        this.mTabLayout = (TabLayout) $(R.id.tab_main_scene_layout);
        this.mViewPager = (ViewPager) $(R.id.view_main_scene_pager);
        reflex(this.mTabLayout);
    }

    protected void initData() {
        String[] stringArray = getResources().getStringArray(R.array.tab_scene_title);
        this.mFragmentList = new ArrayList();
        this.mFragmentList.add((Fragment) new SceneSystemRecommendFragment());
        this.mFragmentList.add((Fragment) new SceneUserFragment());
        this.mFragmentList.add((Fragment) new SceneExecuteLogFragment());
        this.mPagerAdapter = new TabScenePagerAdapter(getChildFragmentManager(), Arrays.asList(stringArray), this.mFragmentList);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mTabLayout.setupWithViewPager(this.mViewPager);
        this.mTabLayout.setTabMode(1);
        this.mViewPager.setCurrentItem(1);
    }

    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "场景");
    }

    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(), "场景");
    }

    public void addListener() {
        super.addListener();
        this.mCommunRightImg.setOnClickListener(this);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.TabSceneFragment.1
            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
            }

            @Override // android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right_tab_home_title_image /* 2131296511 */:
                startActivityForNew(this.mActivity, new Intent(this.mActivity, (Class<?>) DeviceIftttCreateActivity.class));
                break;
        }
    }

    public void reflex(final TabLayout tabLayout) {
        tabLayout.post(new Runnable() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.TabSceneFragment.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                    DPIUtil.dip2px(tabLayout.getContext(), 20.0f);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        int width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            mTextView.getMeasuredWidth();
                        }
                        tabView.invalidate();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }
}
