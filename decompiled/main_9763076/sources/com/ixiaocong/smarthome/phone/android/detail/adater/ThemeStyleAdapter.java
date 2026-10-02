package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.model.ThemeStyleModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ThemeStyleAdapter extends XcBaseRecyclerAdapter<ThemeStyleModel, BaseRecyclerViewHolder> {
    private Context context;
    private List<ThemeStyleModel> data;
    private OnSelectItemListener listener;
    private int selPosition;

    public interface OnSelectItemListener {
        void onSelectItemClick(int i);
    }

    public ThemeStyleAdapter(Context context, List<ThemeStyleModel> data) {
        super(R.layout.adapter_theme_style);
        this.selPosition = -1;
        this.context = context;
        this.data = data;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(final BaseRecyclerViewHolder helper, ThemeStyleModel themeStyleModel) {
        helper.setImageResource(R.id.iv_theme_style_item_img, themeStyleModel.getThemeImage());
        if (themeStyleModel.isSelected()) {
            this.selPosition = helper.getAdapterPosition();
            helper.setVisible(R.id.iv_theme_style_item_button, 0);
        } else {
            helper.setVisible(R.id.iv_theme_style_item_button, 8);
        }
        helper.addOnClickListener(R.id.iv_theme_style_item_img);
        helper.setOnTouchListener(R.id.iv_theme_style_item_img, new View.OnTouchListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.adater.ThemeStyleAdapter.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                if (ThemeStyleAdapter.this.selPosition != helper.getAdapterPosition()) {
                    for (int i = 0; i < ThemeStyleAdapter.this.data.size(); i++) {
                        if (i == helper.getAdapterPosition()) {
                            ((ThemeStyleModel) ThemeStyleAdapter.this.data.get(i)).setSelected(true);
                        } else {
                            ((ThemeStyleModel) ThemeStyleAdapter.this.data.get(i)).setSelected(false);
                        }
                    }
                    if (ThemeStyleAdapter.this.listener != null) {
                        ThemeStyleAdapter.this.listener.onSelectItemClick(helper.getAdapterPosition());
                    }
                    ThemeStyleAdapter.this.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    public void setOnSelectItemClickListener(OnSelectItemListener listener) {
        this.listener = listener;
    }
}
