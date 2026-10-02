package com.ixiaocong.smarthome.phone.android.common.manager;

import android.app.Activity;
import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.ScenePopDeviceAdapter;
import com.ixiaocong.smarthome.phone.android.detail.adater.ScenePopParameterAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.model.scene.RelateActionModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneDetailModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneDeviceModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneParameterModel;
import com.xiaocong.smarthome.httplib.model.scene.TriggerDeviceListModel;
import com.xiaocong.smarthome.httplib.model.scene.UserIftttListModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IftttHttpManager {
    public static void requestIftttlList(final Context context, String type, final ScenePopDeviceAdapter deviceAdapter, final IftttSelectRelatePop relatePop) {
        final List<SceneDeviceModel> listData = new ArrayList<>();
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", type);
        httpSetting.setNeedSign(false);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("ifttt/list");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                UserIftttListModel listModel = (UserIftttListModel) JSON.parseObject(var1.getData(), UserIftttListModel.class);
                if (listModel != null && listModel.getSceneList().size() > 0) {
                    for (int i = 0; i < listModel.getSceneList().size(); i++) {
                        SceneDeviceModel deviceModel = new SceneDeviceModel();
                        deviceModel.setDeviceId(((UserIftttListModel.IftttModel) listModel.getSceneList().get(i)).getTriggerId());
                        deviceModel.setDeviceName(((UserIftttListModel.IftttModel) listModel.getSceneList().get(i)).getTriggerName());
                        deviceModel.setProductImage(((UserIftttListModel.IftttModel) listModel.getSceneList().get(i)).getBackgroundImage());
                        listData.add(deviceModel);
                    }
                    deviceAdapter.setNewData(listData);
                    deviceAdapter.notifyDataSetChanged();
                    return;
                }
                relatePop.dismiss();
                ToastUtils.showShort(context, "请先前去创建场景");
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, var1.getErrorMessage());
            }
        });
    }

    public static void requestTriggerDevices(final Context context, final ScenePopDeviceAdapter deviceAdapter) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("ifttt/trigger/device/list");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                TriggerDeviceListModel model = (TriggerDeviceListModel) JSON.parseObject(var1.getData(), TriggerDeviceListModel.class);
                List<SceneDeviceModel> listData = new ArrayList<>();
                listData.addAll(model.getCommonList());
                listData.addAll(model.getDeviceList());
                if (listData.size() > 0) {
                    deviceAdapter.setNewData(listData);
                    deviceAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(context, "当前没有符合触发条件的设备,请前去添加");
                    IftttSelectTriggerPop.getInstance().dismiss();
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                IftttSelectTriggerPop.getInstance().dismiss();
            }
        });
    }

    public static void requestTriggerDeviceParamter(final Context context, final ScenePopParameterAdapter parameterAdapter, String deviceId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("ifttt/trigger/device/parameter/list");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.3
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SceneParameterModel parameterModel = (SceneParameterModel) JSON.parseObject(var1.getData(), SceneParameterModel.class);
                if (parameterModel.getParameters() != null && parameterModel.getParameters().size() > 0) {
                    parameterAdapter.setNewData(parameterModel.getParameters());
                    parameterAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(context, "此设备没有支持场景的触发参数");
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public static void updateTrigger(final Context context, final int type, final SceneDetailModel.TriggerModel triggerModel, final IftttOperationCallback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put("triggerId", triggerModel.getTriggerId() + Constants.MAIN_VERSION_TAG);
        paramsNoSign.put(Constants.FLAG_DEVICE_ID, triggerModel.getDeviceId());
        paramsNoSign.put("parameterKey", triggerModel.getParameterKey());
        paramsNoSign.put("parameterType", triggerModel.getParameterType());
        paramsNoSign.put("productParameterId", triggerModel.getProductParameterId());
        paramsNoSign.put("triggerCondition", triggerModel.getTriggerCondition());
        paramsNoSign.put("triggerIcon", triggerModel.getTriggerIcon());
        paramsNoSign.put("triggerName", triggerModel.getTriggerName());
        paramsNoSign.put("triggerIntro", triggerModel.getTriggerIntro());
        paramsNoSign.put("threshold", triggerModel.getThreshold());
        paramsNoSign.put("status", triggerModel.getStatus() + Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("startWorkTime", triggerModel.getStartWorkTime());
        paramsNoSign.put("stopWorkTime", triggerModel.getStopWorkTime());
        paramsNoSign.put("workday", triggerModel.getWorkday());
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("ifttt/update");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.4
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                callback.updateTriggerCallback(type, triggerModel);
                ToastUtils.showShort(context, "修改成功");
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                callback.updateTriggerCallback(3, triggerModel);
            }
        });
    }

    public static void requestRelateDevices(final Context context, final ScenePopDeviceAdapter deviceAdapter) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("ifttt/action/device/list");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.5
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                TriggerDeviceListModel model = (TriggerDeviceListModel) JSON.parseObject(var1.getData(), TriggerDeviceListModel.class);
                if (model.getDeviceList().size() > 0) {
                    deviceAdapter.setNewData(model.getDeviceList());
                    deviceAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(context, "当前没有符合响应条件的设备,请前去添加");
                    IftttSelectRelatePop.getInstance().dismiss();
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                IftttSelectRelatePop.getInstance().dismiss();
            }
        });
    }

    public static void requestRelateDeviceParamter(final Context context, final ScenePopParameterAdapter parameterAdapter, String deviceId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("ifttt/action/device/parameter/list");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.6
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SceneParameterModel parameterModel = (SceneParameterModel) JSON.parseObject(var1.getData(), SceneParameterModel.class);
                if (parameterModel.getParameters() != null && parameterModel.getParameters().size() > 0) {
                    parameterAdapter.setNewData(parameterModel.getParameters());
                    parameterAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(context, "此设备没有支持场景的触发参数");
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public static void addRelate(final Context context, String triggerId, final RelateActionModel actionModel, final IftttOperationCallback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put("triggerId", triggerId);
        params.put("actionType", actionModel.getActionType());
        params.put("transactionId", actionModel.getTransactionId());
        params.put("actionIcon", actionModel.getActionIcon());
        paramsNoSign.put("parameterKey", actionModel.getParameterKey());
        paramsNoSign.put("productParameterId", actionModel.getProductParameterId());
        paramsNoSign.put("parameterType", actionModel.getParameterType());
        paramsNoSign.put("actionValue", actionModel.getActionValue());
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("ifttt/addAction");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.7
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, "操作成功");
                try {
                    JSONObject jsonObj = new JSONObject(var1.getData().toString());
                    int actionId = jsonObj.optInt("actionId");
                    actionModel.setActionId(actionId + Constants.MAIN_VERSION_TAG);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.addRelateCallback(actionModel);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public static void updateRelate(final Context context, final int position, String triggerId, final RelateActionModel actionModel, final IftttOperationCallback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put("actionId", actionModel.getActionId());
        paramsNoSign.put("triggerId", triggerId);
        paramsNoSign.put("transactionId", actionModel.getTransactionId());
        paramsNoSign.put("actionType", actionModel.getActionType());
        paramsNoSign.put("parameterKey", actionModel.getParameterKey());
        paramsNoSign.put("actionIcon", actionModel.getActionIcon());
        paramsNoSign.put("actionValue", actionModel.getActionValue());
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("ifttt/updateAction");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.8
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, "操作成功");
                callback.updateRelateCallback(position, actionModel);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public static void deleteRelate(final Context context, String actionId, final int position, final IftttOperationCallback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("actionId", actionId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("ifttt/deleteAction");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.9
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, "操作成功");
                callback.deleteRelateCallback(true, position);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public static void createScene(final Activity activity, SceneDetailModel.TriggerModel triggerModel, List<RelateActionModel> relateModels) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put("triggerName", triggerModel.getTriggerName());
        params.put(Constants.FLAG_DEVICE_ID, triggerModel.getDeviceId());
        params.put("actionList", new Gson().toJson(relateModels));
        paramsNoSign.put("triggerIntro", triggerModel.getTriggerIntro());
        paramsNoSign.put("productParameterId", triggerModel.getProductParameterId());
        paramsNoSign.put("parameterKey", triggerModel.getParameterKey());
        paramsNoSign.put("parameterType", triggerModel.getParameterType());
        paramsNoSign.put("triggerCondition", triggerModel.getTriggerCondition());
        paramsNoSign.put("threshold", triggerModel.getThreshold());
        paramsNoSign.put("triggerIcon", triggerModel.getTriggerIcon());
        paramsNoSign.put("startWorkTime", triggerModel.getStartWorkTime());
        paramsNoSign.put("stopWorkTime", triggerModel.getStopWorkTime());
        paramsNoSign.put("workday", triggerModel.getWorkday());
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("ifttt/add");
        HttpLoadingHelper.getInstance().showProcessLoading(activity);
        XCRequest.getInstance().request(activity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.10
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(activity, "场景创建成功");
                activity.finish();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(activity, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public static void executeIfttt(final Context context, String triggerId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("triggerId", triggerId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("ifttt/execute");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.11
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, "场景执行成功");
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public static void deleteIfttt(final Activity activity, String triggerId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("triggerId", triggerId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("ifttt/delete");
        HttpLoadingHelper.getInstance().showProcessLoading(activity);
        XCRequest.getInstance().request(activity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager.12
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(activity, "删除成功");
                activity.finish();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(activity, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }
}
