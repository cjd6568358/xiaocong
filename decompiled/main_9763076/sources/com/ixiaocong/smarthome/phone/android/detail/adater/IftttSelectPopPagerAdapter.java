package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.tencent.android.tpush.common.Constants;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IftttSelectPopPagerAdapter extends PagerAdapter {
    private CommonTypeCallback mCallback;
    private List<String> mTitles;

    public IftttSelectPopPagerAdapter(CommonTypeCallback callback, List<String> titles) {
        this.mTitles = titles;
        this.mCallback = callback;
    }

    @Override // android.support.v4.view.PagerAdapter
    public int getCount() {
        if (this.mTitles == null) {
            return 0;
        }
        return this.mTitles.size();
    }

    @Override // android.support.v4.view.PagerAdapter
    public Object instantiateItem(ViewGroup container, int position) {
        return this.mTitles.get(position);
    }

    @Override // android.support.v4.view.PagerAdapter
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override // android.support.v4.view.PagerAdapter
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override // android.support.v4.view.PagerAdapter
    public CharSequence getPageTitle(int position) {
        return this.mTitles.size() != 0 ? this.mTitles.get(position) : Constants.MAIN_VERSION_TAG;
    }

    public void setPageTitle(int position, String title) {
        if (this.mTitles != null && position >= 0 && position < this.mTitles.size()) {
            this.mTitles.set(position, title);
            notifyDataSetChanged();
        }
    }

    public void addPageTitle(String title) {
        if (this.mTitles != null) {
            this.mTitles.add(title);
            this.mCallback.resultTypeCalllback(1);
            notifyDataSetChanged();
        }
    }

    public void removePageTitle(int position) {
        if (this.mTitles != null && position >= 0 && position < this.mTitles.size()) {
            this.mTitles.remove(position);
            notifyDataSetChanged();
        }
    }
}
