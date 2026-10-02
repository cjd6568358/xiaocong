package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabScenePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mListTitles;

    public TabScenePagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        this.mListTitles = titles;
        this.mFragmentList = fragments;
    }

    @Override // android.support.v4.app.FragmentStatePagerAdapter
    public Fragment getItem(int position) {
        return this.mFragmentList.get(position);
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        return this.mListTitles.size();
    }

    @Override // android.support.v4.app.FragmentStatePagerAdapter, android.support.v4.view.PagerAdapter
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override // android.support.v4.view.PagerAdapter
    public CharSequence getPageTitle(int position) {
        return this.mListTitles.get(position);
    }
}
