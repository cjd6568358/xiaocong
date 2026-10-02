package com.xiaocong.smarthome.recycleradapter.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashSet;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final LinkedHashSet<Integer> childClickViewIds;
    public View convertView;
    private final LinkedHashSet<Integer> itemChildLongClickViewIds;
    private final HashSet<Integer> nestViews;
    private final SparseArray<View> views;

    public HashSet<Integer> getNestViews() {
        return this.nestViews;
    }

    public BaseRecyclerViewHolder(View view) {
        super(view);
        this.views = new SparseArray<>();
        this.childClickViewIds = new LinkedHashSet<>();
        this.itemChildLongClickViewIds = new LinkedHashSet<>();
        this.nestViews = new HashSet<>();
        this.convertView = view;
    }

    public HashSet<Integer> getItemChildLongClickViewIds() {
        return this.itemChildLongClickViewIds;
    }

    public HashSet<Integer> getChildClickViewIds() {
        return this.childClickViewIds;
    }

    public View getConvertView() {
        return this.convertView;
    }

    public BaseRecyclerViewHolder setText(int viewId, CharSequence value) {
        TextView view = (TextView) getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseRecyclerViewHolder setText(int viewId, int strId, int textColor) {
        TextView view = (TextView) getView(viewId);
        view.setText(strId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseRecyclerViewHolder setVisible(int viewId, int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }

    public BaseRecyclerViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = (ImageView) getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public BaseRecyclerViewHolder setTextColor(int viewId, int textColor) {
        TextView view = (TextView) getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseRecyclerViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? 0 : 8);
        return this;
    }

    public BaseRecyclerViewHolder addOnClickListener(int viewId) {
        this.childClickViewIds.add(Integer.valueOf(viewId));
        return this;
    }

    public BaseRecyclerViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseRecyclerViewHolder setChecked(int viewId, boolean checked) {
        View view = getView(viewId);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        } else if (view instanceof CheckedTextView) {
            ((CheckedTextView) view).setChecked(checked);
        }
        return this;
    }

    public <T extends View> T getView(int i) {
        T t = (T) this.views.get(i);
        if (t == null) {
            T t2 = (T) this.convertView.findViewById(i);
            this.views.put(i, t2);
            return t2;
        }
        return t;
    }
}
