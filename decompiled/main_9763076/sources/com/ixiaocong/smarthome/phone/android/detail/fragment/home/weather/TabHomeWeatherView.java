package com.ixiaocong.smarthome.phone.android.detail.fragment.home.weather;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.LocationManager;
import com.ixiaocong.smarthome.phone.android.detail.activity.area.AreaSelectActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.store.StoreActivity;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LocationEvent;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.RoomWeatherModel;
import com.xiaocong.smarthome.httplib.model.WeatherModel;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabHomeWeatherView extends LinearLayout {
    private String mBuyLink;
    private String mCityCode;
    private Context mContext;
    private TextView mCurrentTemp;
    private String mLat;
    private TextView mLocation;
    private String mLot;
    private TextView mOutdoorAirQuality;
    private TextView mOutdoorPmValue;
    private TextView mOutdoorWeatherDes;
    private TextView mRoomCh2o;
    private TextView mRoomCo2;
    private TextView mRoomHum;
    private TextView mRoomPmValue;
    private TextView mTvBuyGoods;
    private TextView mTvGreetings;
    private TextView mTvRoomTemp;
    private ImageView mWeatherIcon;

    public TabHomeWeatherView(Context context) {
        super(context);
        this.mContext = context;
    }

    public TabHomeWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(attrs);
        addListener();
    }

    public void setLocation(LocationEvent event) {
        this.mLat = event.getLat();
        this.mLot = event.getLot();
        if (TextUtils.isEmpty(this.mLat) || TextUtils.isEmpty(this.mLot)) {
            this.mLat = (String) SpUtils.getFromLocal(this.mContext, "spLocation", "spLocationLat", Constants.MAIN_VERSION_TAG);
            this.mLot = (String) SpUtils.getFromLocal(this.mContext, "spLocation", "spLocationLot", Constants.MAIN_VERSION_TAG);
            XcLogger.i("TabHomeWeatherView", "SP_LOCATION_LAT:" + this.mLat + ",SP_LOCATION_LOT:" + this.mLot);
        } else {
            SpUtils.saveToLocal(this.mContext, "spLocation", "spLocationLat", this.mLat);
            SpUtils.saveToLocal(this.mContext, "spLocation", "spLocationLot", this.mLot);
            SpUtils.saveToLocal(this.mContext, "spLocation", "spLocationCity", event.getCityName());
        }
        this.mCityCode = event.getAdCode();
        if (!TextUtils.isEmpty(event.getCityName())) {
            this.mLocation.setText(event.getCityName());
            XcLogger.w("setLocation", event.getCityName());
        } else {
            String cityName = (String) SpUtils.getFromLocal(this.mContext, "spLocation", "spLocationCity", Constants.MAIN_VERSION_TAG);
            if (!TextUtils.isEmpty(cityName)) {
                this.mLocation.setText(cityName);
            } else {
                this.mLocation.setText("北京市");
            }
        }
        requestOutdoorWeather(this.mLat, this.mLot, this.mCityCode);
    }

    public void setHomeGreetings(String greetings) {
        if (!TextUtils.isEmpty(greetings)) {
            this.mTvGreetings.setText(greetings);
        }
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(this.mContext).inflate(R.layout.header_tab_home_weather_layout, this);
        this.mLocation = (TextView) findViewById(R.id.tv_tab_home_location);
        this.mCurrentTemp = (TextView) findViewById(R.id.tv_tab_home_current_temp);
        this.mOutdoorWeatherDes = (TextView) findViewById(R.id.tv_tab_home_weather_outdoor_des);
        this.mOutdoorAirQuality = (TextView) findViewById(R.id.tv_tab_home_outdoor_air_quality_value);
        this.mOutdoorPmValue = (TextView) findViewById(R.id.tv_tab_home_outdoor_pm_value);
        this.mWeatherIcon = (ImageView) findViewById(R.id.iv_tab_home_outdoor_weather);
        this.mTvRoomTemp = (TextView) findViewById(R.id.tv_tab_home_door_temp);
        this.mRoomCh2o = (TextView) findViewById(R.id.tv_room_ch2o_value);
        this.mRoomCo2 = (TextView) findViewById(R.id.tv_room_co2_value);
        this.mRoomHum = (TextView) findViewById(R.id.tv_room_hum_value);
        this.mRoomPmValue = (TextView) findViewById(R.id.tv_room_pm_value);
        this.mTvGreetings = (TextView) findViewById(R.id.header_tab_home_weather_greetings);
        this.mTvBuyGoods = (TextView) findViewById(R.id.tv_tab_home_buy_goods);
    }

    private void addListener() {
        this.mLocation.setOnClickListener(TabHomeWeatherView$$Lambda$1.lambdaFactory$(this));
        this.mTvBuyGoods.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.weather.TabHomeWeatherView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(TabHomeWeatherView.this.mContext, (Class<?>) StoreActivity.class);
                intent.putExtra("BuyingLink", TabHomeWeatherView.this.mBuyLink);
                TabHomeWeatherView.this.mContext.startActivity(intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        this.mContext.startActivity(new Intent(this.mContext, (Class<?>) AreaSelectActivity.class));
    }

    public void onResume() {
        initLoacation();
    }

    public void onRefresh() {
        initLoacation();
    }

    protected void initLoacation() {
        if (isChooseLocation()) {
            LocationEvent locationEvent = (LocationEvent) new Gson().fromJson((String) SpUtils.getFromLocal(this.mContext, "spLocation", "spLocation", Constants.MAIN_VERSION_TAG), LocationEvent.class);
            setLocation(locationEvent);
        } else {
            LocationManager.getInstance().getCurrentLocation(this.mContext);
        }
    }

    protected boolean isChooseLocation() {
        String location = (String) SpUtils.getFromLocal(this.mContext, "spLocation", "spLocation", Constants.MAIN_VERSION_TAG);
        if (location == null || location.equals(Constants.MAIN_VERSION_TAG)) {
            return false;
        }
        return true;
    }

    public void setRoomWeather(RoomWeatherModel roomModel) {
        if (roomModel == null) {
            this.mTvBuyGoods.setVisibility(0);
            this.mTvBuyGoods.setVisibility(0);
            return;
        }
        if (TextUtils.isEmpty(roomModel.getBuyingLink())) {
            this.mTvBuyGoods.setVisibility(8);
            if (!TextUtils.isEmpty(roomModel.getCh2o())) {
                this.mRoomCh2o.setText(roomModel.getCh2o());
            } else {
                this.mRoomCh2o.setText("-/-");
            }
            if (!TextUtils.isEmpty(roomModel.getHumidity())) {
                this.mRoomHum.setText(roomModel.getHumidity());
            } else {
                this.mRoomHum.setText("-/-");
            }
            if (!TextUtils.isEmpty(roomModel.getPm2dot5())) {
                this.mRoomPmValue.setText(roomModel.getPm2dot5());
            } else {
                this.mRoomPmValue.setText("-/-");
            }
            if (!TextUtils.isEmpty(roomModel.getTemperature())) {
                this.mTvRoomTemp.setText(roomModel.getTemperature() + "˚C");
            } else {
                this.mTvRoomTemp.setText("-/-˚C");
            }
            if (!TextUtils.isEmpty(roomModel.getCo2())) {
                this.mRoomCo2.setText(roomModel.getCo2());
                return;
            } else {
                this.mRoomCo2.setText("-/-");
                return;
            }
        }
        this.mBuyLink = roomModel.getBuyingLink();
        this.mTvBuyGoods.setVisibility(0);
        this.mRoomCh2o.setText("-/-");
        this.mRoomHum.setText("-/-");
        this.mRoomPmValue.setText("-/-");
        this.mTvRoomTemp.setText("-/-˚C");
        this.mRoomCo2.setText("-/-");
    }

    private void requestOutdoorWeather(String lat, String lot, String adcode) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("lat", lat);
        params.put("lot", lot);
        params.put("adcode", adcode);
        httpSetting.setParamsMap(params);
        httpSetting.setNeedSign(false);
        httpSetting.setPath("weather/today/outdoors");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.weather.TabHomeWeatherView.2
            public void onComplete(XCResponseBean var1) {
                WeatherModel weatherModel = (WeatherModel) JSON.parseObject(var1.getData(), WeatherModel.class);
                if (weatherModel != null) {
                    if (TextUtils.isEmpty(weatherModel.getTemp())) {
                        TabHomeWeatherView.this.mCurrentTemp.setText("-/-˚C");
                    } else {
                        TabHomeWeatherView.this.mCurrentTemp.setText(weatherModel.getTemp() + "˚C");
                    }
                    if (TextUtils.isEmpty(weatherModel.getAirText())) {
                        TabHomeWeatherView.this.mOutdoorAirQuality.setText("-/-");
                    } else {
                        TabHomeWeatherView.this.mOutdoorAirQuality.setText(weatherModel.getAirText());
                    }
                    if (TextUtils.isEmpty(weatherModel.getPm25())) {
                        TabHomeWeatherView.this.mOutdoorPmValue.setText("-/-");
                    } else {
                        TabHomeWeatherView.this.mOutdoorPmValue.setText(weatherModel.getPm25());
                    }
                    if (TextUtils.isEmpty(weatherModel.getText())) {
                        TabHomeWeatherView.this.mOutdoorWeatherDes.setText("-/-");
                    } else {
                        TabHomeWeatherView.this.mOutdoorWeatherDes.setText(weatherModel.getText());
                    }
                    if (Util.isOnMainThread()) {
                        Glide.with(TabHomeWeatherView.this.mContext.getApplicationContext()).load(weatherModel.getIcon()).into(TabHomeWeatherView.this.mWeatherIcon);
                    }
                }
            }

            public void onError(XCErrorMessage var1) {
            }
        });
    }
}
