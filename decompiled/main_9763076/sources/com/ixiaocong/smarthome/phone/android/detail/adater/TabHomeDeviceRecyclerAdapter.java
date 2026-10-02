package com.ixiaocong.smarthome.phone.android.detail.adater;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleProgressBar;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.DeviceListModel;
import com.xiaocong.smarthome.httplib.model.inside.DeviceParameterModel;
import com.xiaocong.smarthome.recycleradapter.base.BaseRecyclerViewHolder;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.switchbutton.SwitchButton;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabHomeDeviceRecyclerAdapter extends XcBaseRecyclerAdapter<DeviceListModel, BaseRecyclerViewHolder> {
    private Context mContext;
    private CircleProgressBar mProgressBar;
    private Map<String, Boolean> showItems;

    public TabHomeDeviceRecyclerAdapter(Context context) {
        super(R.layout.adapter_tab_home_device_layout);
        this.showItems = new LinkedHashMap();
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void convert(BaseRecyclerViewHolder helper, DeviceListModel item) {
        if (item.getStatus() == 1) {
            if (item.getControlParameter() != null && item.getControlParameter().size() > 0) {
                if (item.getControlParameter().size() == 1) {
                    helper.setVisible(R.id.rl_tab_home_device_adapter_right, 0);
                    helper.setVisible(R.id.sbtn_tab_home_dev_right_switch, 0);
                    helper.setVisible(R.id.ll_tab_home_device_adapter_bot, 8);
                    initOnlyLayout(helper, item, true);
                } else {
                    helper.setVisible(R.id.rl_tab_home_device_adapter_right, 8);
                    helper.setVisible(R.id.ll_tab_home_device_adapter_bot, 0);
                    initMoreLayout(helper, item);
                }
            } else {
                helper.setVisible(R.id.rl_tab_home_device_adapter_right, 0);
                helper.setVisible(R.id.sbtn_tab_home_dev_right_switch, 8);
                helper.setVisible(R.id.ll_tab_home_device_adapter_bot, 8);
                initOnlyLayout(helper, item, false);
            }
        } else {
            helper.setVisible(R.id.rl_tab_home_device_adapter_right, 0);
            helper.setVisible(R.id.sbtn_tab_home_dev_right_switch, 8);
            helper.setVisible(R.id.ll_tab_home_device_adapter_bot, 8);
            initOffLineLayout(helper, item);
        }
        if (item.getControlParameter() != null && item.getControlParameter().size() > 1) {
            if (item.isDownload()) {
                helper.setVisible(R.id.cpb_tab_home_dev_bot_download, 0);
                this.mProgressBar = (CircleProgressBar) helper.getConvertView().findViewById(R.id.cpb_tab_home_dev_bot_download);
                this.mProgressBar.setUnit("%");
                this.mProgressBar.setProgressNotInUiThread(item.getProgress());
                this.mProgressBar.setCenterText(item.getProgress() + Constants.MAIN_VERSION_TAG);
                return;
            }
            helper.setVisible(R.id.cpb_tab_home_dev_bot_download, 8);
            return;
        }
        if (item.isDownload()) {
            helper.setVisible(R.id.ll_right_tab_home_sbtn_layout, 8);
            helper.setVisible(R.id.cpb_tab_home_dev_right_download, 0);
            this.mProgressBar = (CircleProgressBar) helper.getConvertView().findViewById(R.id.cpb_tab_home_dev_right_download);
            this.mProgressBar.setUnit("%");
            this.mProgressBar.setProgressNotInUiThread(item.getProgress());
            this.mProgressBar.setCenterText(item.getProgress() + Constants.MAIN_VERSION_TAG);
            return;
        }
        helper.setVisible(R.id.cpb_tab_home_dev_right_download, 8);
        helper.setVisible(R.id.ll_right_tab_home_sbtn_layout, 0);
    }

    private void initOffLineLayout(BaseRecyclerViewHolder helper, DeviceListModel item) {
        helper.setImageResource(R.id.iv_home_device_right_status, R.mipmap.gray_point_icon);
        helper.setText(R.id.tv_main_dev_item_right_status, R.string.off_line, Color.parseColor("#999999"));
        helper.setVisible(R.id.ll_right_tab_home_sbtn_layout, 8);
        Glide.with(this.mContext).load(item.getProductImage()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_home_device_right_icon));
        helper.setText(R.id.tv_main_dev_item_right_name, item.getDeviceName());
    }

    private void initMoreLayout(final BaseRecyclerViewHolder helper, final DeviceListModel item) {
        Glide.with(this.mContext).load(item.getProductImage()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_home_device_bot_icon));
        helper.setText(R.id.tv_main_dev_bot_item_name, item.getDeviceName());
        helper.setImageResource(R.id.iv_home_device_bot_status, R.mipmap.blue_point_icon);
        helper.setText(R.id.tv_main_dev_bot_item_status, R.string.on_line, Color.parseColor("#ffb92a"));
        if (item.getControlParameter().size() == 2) {
            helper.setVisible(R.id.ll_bot_three_tab_home_sbtn_layout, 8);
            helper.setVisible(R.id.ll_bot_four_tab_home_sbtn_layout, 8);
        } else if (item.getControlParameter().size() == 3) {
            helper.setText(R.id.tv_three_tab_home_para_name, ((DeviceParameterModel) item.getControlParameter().get(2)).getName());
            helper.setVisible(R.id.ll_bot_three_tab_home_sbtn_layout, 0);
            helper.setVisible(R.id.ll_bot_four_tab_home_sbtn_layout, 8);
        } else if (item.getControlParameter().size() == 4) {
            helper.setText(R.id.tv_three_tab_home_para_name, ((DeviceParameterModel) item.getControlParameter().get(2)).getName());
            helper.setVisible(R.id.ll_bot_three_tab_home_sbtn_layout, 0);
            helper.setText(R.id.tv_four_tab_home_para_name, ((DeviceParameterModel) item.getControlParameter().get(3)).getName());
            helper.setVisible(R.id.ll_bot_four_tab_home_sbtn_layout, 0);
        }
        helper.setText(R.id.tv_one_tab_home_para_name, ((DeviceParameterModel) item.getControlParameter().get(0)).getName());
        helper.setText(R.id.tv_two_tab_home_para_name, ((DeviceParameterModel) item.getControlParameter().get(1)).getName());
        SwitchButton oneSbtn = helper.convertView.findViewById(R.id.sbtn_one_tab_home_dev_switch);
        SwitchButton twoSbtn = helper.convertView.findViewById(R.id.sbtn_two_tab_home_dev_switch);
        SwitchButton threeSbtn = helper.convertView.findViewById(R.id.sbtn_three_tab_home_dev_switch);
        SwitchButton fourSbtn = helper.convertView.findViewById(R.id.sbtn_four_tab_home_dev_switch);
        helper.addOnClickListener(R.id.ll_bot_one_tab_home_sbtn_layout);
        helper.addOnClickListener(R.id.sbtn_one_tab_home_dev_switch);
        helper.addOnClickListener(R.id.ll_bot_two_tab_home_sbtn_layout);
        helper.addOnClickListener(R.id.sbtn_two_tab_home_dev_switch);
        helper.addOnClickListener(R.id.ll_bot_three_tab_home_sbtn_layout);
        helper.addOnClickListener(R.id.sbtn_three_tab_home_dev_switch);
        helper.addOnClickListener(R.id.ll_bot_four_tab_home_sbtn_layout);
        helper.addOnClickListener(R.id.sbtn_four_tab_home_dev_switch);
        if (this.showItems.containsKey(item.getDeviceId())) {
            helper.setImageResource(R.id.img_main_dev_bot_item_fold, R.mipmap.icon_home_dev_item_fold);
            helper.setVisible(R.id.ll_tab_home_bot_switch_layout, 0);
        } else {
            helper.setImageResource(R.id.img_main_dev_bot_item_fold, R.mipmap.icon_home_dev_item_more);
            helper.setVisible(R.id.ll_tab_home_bot_switch_layout, 8);
        }
        helper.addOnClickListener(R.id.img_main_dev_bot_item_fold);
        helper.setOnTouchListener(R.id.img_main_dev_bot_item_fold, new View.OnTouchListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.adater.TabHomeDeviceRecyclerAdapter.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                if (TabHomeDeviceRecyclerAdapter.this.showItems.containsKey(item.getDeviceId())) {
                    helper.setImageResource(R.id.img_main_dev_bot_item_fold, R.mipmap.icon_home_dev_item_more);
                    helper.setVisible(R.id.ll_tab_home_bot_switch_layout, 8);
                    TabHomeDeviceRecyclerAdapter.this.showItems.remove(item.getDeviceId());
                } else {
                    helper.setImageResource(R.id.img_main_dev_bot_item_fold, R.mipmap.icon_home_dev_item_fold);
                    helper.setVisible(R.id.ll_tab_home_bot_switch_layout, 0);
                    TabHomeDeviceRecyclerAdapter.this.showItems.put(item.getDeviceId(), true);
                }
                return false;
            }
        });
        try {
            XcLogger.i("TabHomeDeviceRecyclerAdapter", helper.getAdapterPosition() + "," + JSON.toJSONString(item.getSnapshot()));
            if (!TextUtils.isEmpty(item.getSnapshot())) {
                JSONObject jsonObj = new JSONObject(item.getSnapshot());
                if (item.getControlParameter().size() == 2) {
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(0)).getKey()) == 1) {
                        if (!oneSbtn.isChecked()) {
                            oneSbtn.setCheckedImmediatelyNoEvent(true);
                        }
                    } else if (oneSbtn.isChecked()) {
                        oneSbtn.setCheckedImmediatelyNoEvent(false);
                    }
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(1)).getKey()) == 1) {
                        if (!twoSbtn.isChecked()) {
                            twoSbtn.setCheckedImmediatelyNoEvent(true);
                            return;
                        }
                        return;
                    } else {
                        if (twoSbtn.isChecked()) {
                            twoSbtn.setCheckedImmediatelyNoEvent(false);
                            return;
                        }
                        return;
                    }
                }
                if (item.getControlParameter().size() == 3) {
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(0)).getKey()) == 1) {
                        if (!oneSbtn.isChecked()) {
                            oneSbtn.setCheckedImmediatelyNoEvent(true);
                        }
                    } else if (oneSbtn.isChecked()) {
                        oneSbtn.setCheckedImmediatelyNoEvent(false);
                    }
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(1)).getKey()) == 1) {
                        if (!twoSbtn.isChecked()) {
                            twoSbtn.setCheckedImmediatelyNoEvent(true);
                        }
                    } else if (twoSbtn.isChecked()) {
                        twoSbtn.setCheckedImmediatelyNoEvent(false);
                    }
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(2)).getKey()) == 1) {
                        if (!threeSbtn.isChecked()) {
                            threeSbtn.setCheckedImmediatelyNoEvent(true);
                            return;
                        }
                        return;
                    } else {
                        if (threeSbtn.isChecked()) {
                            threeSbtn.setCheckedImmediatelyNoEvent(false);
                            return;
                        }
                        return;
                    }
                }
                if (item.getControlParameter().size() == 4) {
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(0)).getKey()) == 1) {
                        if (!oneSbtn.isChecked()) {
                            oneSbtn.setCheckedImmediatelyNoEvent(true);
                        }
                    } else if (oneSbtn.isChecked()) {
                        oneSbtn.setCheckedImmediatelyNoEvent(false);
                    }
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(1)).getKey()) == 1) {
                        if (!twoSbtn.isChecked()) {
                            twoSbtn.setCheckedImmediatelyNoEvent(true);
                        }
                    } else if (twoSbtn.isChecked()) {
                        twoSbtn.setCheckedImmediatelyNoEvent(false);
                    }
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(2)).getKey()) == 1) {
                        if (!threeSbtn.isChecked()) {
                            threeSbtn.setCheckedImmediatelyNoEvent(true);
                        }
                    } else if (threeSbtn.isChecked()) {
                        threeSbtn.setCheckedImmediatelyNoEvent(false);
                    }
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(3)).getKey()) == 1) {
                        if (!fourSbtn.isChecked()) {
                            fourSbtn.setCheckedImmediatelyNoEvent(true);
                            return;
                        }
                        return;
                    } else {
                        if (fourSbtn.isChecked()) {
                            fourSbtn.setCheckedImmediatelyNoEvent(false);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            if (oneSbtn.isChecked()) {
                oneSbtn.setCheckedImmediatelyNoEvent(false);
            }
            if (twoSbtn.isChecked()) {
                twoSbtn.setCheckedImmediatelyNoEvent(false);
            }
            if (threeSbtn.isChecked()) {
                threeSbtn.setCheckedImmediatelyNoEvent(false);
            }
            if (fourSbtn.isChecked()) {
                fourSbtn.setCheckedImmediatelyNoEvent(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initOnlyLayout(BaseRecyclerViewHolder helper, DeviceListModel item, boolean isShow) {
        Glide.with(this.mContext).load(item.getProductImage()).placeholder(R.drawable.default_img_icon).into((ImageView) helper.getView(R.id.iv_home_device_right_icon));
        helper.setText(R.id.tv_main_dev_item_right_name, item.getDeviceName());
        SwitchButton rightSbtn = helper.convertView.findViewById(R.id.sbtn_tab_home_dev_right_switch);
        helper.addOnClickListener(R.id.ll_right_tab_home_sbtn_layout);
        helper.addOnClickListener(R.id.sbtn_tab_home_dev_right_switch);
        helper.setImageResource(R.id.iv_home_device_right_status, R.mipmap.blue_point_icon);
        if (!TextUtils.isEmpty(item.getSnapshot())) {
            try {
                JSONObject jsonObj = new JSONObject(item.getSnapshot());
                if (isShow) {
                    helper.setVisible(R.id.ll_right_tab_home_sbtn_layout, 0);
                    helper.addOnClickListener(R.id.sbtn_tab_home_dev_right_switch);
                    helper.addOnClickListener(R.id.ll_right_tab_home_sbtn_layout);
                    helper.setText(R.id.tv_main_dev_item_right_status, R.string.on_line, Color.parseColor("#ffb92a"));
                    if (jsonObj.optInt(((DeviceParameterModel) item.getControlParameter().get(0)).getKey()) == 1) {
                        if (!rightSbtn.isChecked()) {
                            rightSbtn.setCheckedImmediatelyNoEvent(true);
                        }
                    } else if (rightSbtn.isChecked()) {
                        rightSbtn.setCheckedImmediatelyNoEvent(false);
                    }
                } else {
                    helper.setVisible(R.id.ll_right_tab_home_sbtn_layout, 8);
                    helper.setText(R.id.tv_main_dev_item_right_status, R.string.on_line, Color.parseColor("#ffb92a"));
                }
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        if (isShow && rightSbtn.isChecked()) {
            rightSbtn.setCheckedImmediatelyNoEvent(false);
        }
        helper.setText(R.id.tv_main_dev_item_right_status, R.string.on_line, Color.parseColor("#ffb92a"));
    }
}
