package com.facebook.react.views.picker;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.picker.events.PickerItemSelectEvent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ReactPickerManager extends SimpleViewManager<ReactPicker> {
    @ReactProp(name = "items")
    public void setItems(ReactPicker view, ReadableArray items) {
        if (items != null) {
            ReadableMap[] data = new ReadableMap[items.size()];
            for (int i = 0; i < items.size(); i++) {
                data[i] = items.getMap(i);
            }
            ReactPickerAdapter adapter = new ReactPickerAdapter(view.getContext(), data);
            adapter.setPrimaryTextColor(view.getPrimaryColor());
            view.setAdapter((SpinnerAdapter) adapter);
            return;
        }
        view.setAdapter((SpinnerAdapter) null);
    }

    @ReactProp(customType = "Color", name = "color")
    public void setColor(ReactPicker view, Integer color) {
        view.setPrimaryColor(color);
        ReactPickerAdapter adapter = (ReactPickerAdapter) view.getAdapter();
        if (adapter != null) {
            adapter.setPrimaryTextColor(color);
        }
    }

    @ReactProp(name = "prompt")
    public void setPrompt(ReactPicker view, String prompt) {
        view.setPrompt(prompt);
    }

    @ReactProp(defaultBoolean = true, name = "enabled")
    public void setEnabled(ReactPicker view, boolean enabled) {
        view.setEnabled(enabled);
    }

    @ReactProp(name = "selected")
    public void setSelected(ReactPicker view, int selected) {
        view.setStagedSelection(selected);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void onAfterUpdateTransaction(ReactPicker view) {
        super.onAfterUpdateTransaction(view);
        view.updateStagedSelection();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void addEventEmitters(ThemedReactContext reactContext, ReactPicker picker) {
        picker.setOnSelectListener(new PickerEventEmitter(picker, ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher()));
    }

    private static class ReactPickerAdapter extends ArrayAdapter<ReadableMap> {
        private final LayoutInflater mInflater;
        private Integer mPrimaryTextColor;

        public ReactPickerAdapter(Context context, ReadableMap[] data) {
            super(context, 0, data);
            this.mInflater = (LayoutInflater) Assertions.assertNotNull(context.getSystemService("layout_inflater"));
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent, false);
        }

        @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent, true);
        }

        private View getView(int position, View convertView, ViewGroup parent, boolean isDropdown) {
            ReadableMap item = getItem(position);
            if (convertView == null) {
                int layoutResId = isDropdown ? R.layout.simple_spinner_dropdown_item : R.layout.simple_spinner_item;
                convertView = this.mInflater.inflate(layoutResId, parent, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(item.getString("label"));
            if (!isDropdown && this.mPrimaryTextColor != null) {
                textView.setTextColor(this.mPrimaryTextColor.intValue());
            } else if (item.hasKey("color") && !item.isNull("color")) {
                textView.setTextColor(item.getInt("color"));
            }
            return convertView;
        }

        public void setPrimaryTextColor(Integer primaryTextColor) {
            this.mPrimaryTextColor = primaryTextColor;
            notifyDataSetChanged();
        }
    }

    private static class PickerEventEmitter implements ReactPicker.OnSelectListener {
        private final EventDispatcher mEventDispatcher;
        private final ReactPicker mReactPicker;

        public PickerEventEmitter(ReactPicker reactPicker, EventDispatcher eventDispatcher) {
            this.mReactPicker = reactPicker;
            this.mEventDispatcher = eventDispatcher;
        }

        @Override // com.facebook.react.views.picker.ReactPicker.OnSelectListener
        public void onItemSelected(int position) {
            this.mEventDispatcher.dispatchEvent(new PickerItemSelectEvent(this.mReactPicker.getId(), position));
        }
    }
}
