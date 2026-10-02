package com.ixiaocong.smarthome.phone.android.detail.activity.shared;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.detail.adater.SharedPagerAdapter;
import com.ixiaocong.smarthome.phone.android.detail.fragment.shared.SharedToFamilyFragment;
import com.ixiaocong.smarthome.phone.android.detail.fragment.shared.SharedToMeFragment;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedHomeActivity extends XcBaseActivity implements View.OnClickListener {
    private List<Fragment> mFragmentList;
    private ImageView mIvBack;
    private SharedPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    protected int getLayoutId() {
        return R.layout.activity_shared_home;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTabLayout = (TabLayout) $(R.id.tab_main_shared_layout);
        this.mViewPager = (ViewPager) $(R.id.view_main_shared_pager);
    }

    protected void initData() {
        String[] stringArray = getResources().getStringArray(R.array.tab_shared_title);
        this.mFragmentList = new ArrayList();
        this.mFragmentList.add((Fragment) new SharedToMeFragment());
        this.mFragmentList.add((Fragment) new SharedToFamilyFragment());
        this.mPagerAdapter = new SharedPagerAdapter(getSupportFragmentManager(), Arrays.asList(stringArray), this.mFragmentList);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mTabLayout.setupWithViewPager(this.mViewPager);
        this.mTabLayout.setTabMode(1);
    }

    protected void onResume() {
        super.onResume();
    }

    public void initAdapter() {
        super.initAdapter();
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedHomeActivity.1
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
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
