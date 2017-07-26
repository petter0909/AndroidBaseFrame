package com.third.party.library.views.city;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;

import com.third.party.library.R;

import java.util.ArrayList;
import java.util.List;

public class CityPickerDialog extends Dialog {

    private final static int DEFAULT_ITEMS = 5;
    private final static int UPDATE_CITY_WHEEL = 11;
    private final static int UPDATE_COUNTY_WHEEL = 12;

    private Activity mContext;

    private ArrayList<Province> mProvinces = new ArrayList<>();
    private ArrayList<City> mCities = new ArrayList<>();
    private ArrayList<County> mCounties = new ArrayList<>();
    AbstractWheelTextAdapter provinceAdapter;
    AbstractWheelTextAdapter cityAdapter;
    AbstractWheelTextAdapter countyAdapter;
    WheelView provinceWheel;
    WheelView citiesWheel;
    WheelView countiesWheel;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isShowing()) {
                return;
            }
            switch (msg.what) {
                case UPDATE_CITY_WHEEL:
                    mCities.clear();
                    mCities.addAll(mProvinces.get(msg.arg1).getCities());
                    citiesWheel.invalidateWheel(true);
                    citiesWheel.setCurrentItem(0, false);

                    mCounties.clear();
                    mCounties.addAll(mCities.get(0).getCounties());
                    countiesWheel.invalidateWheel(true);
                    countiesWheel.setCurrentItem(0, false);
                    break;
                case UPDATE_COUNTY_WHEEL:
                    mCounties.clear();
                    mCounties.addAll(mCities.get(msg.arg1).getCounties());
                    countiesWheel.invalidateWheel(true);
                    countiesWheel.setCurrentItem(0, false);
                    break;
                default:
                    break;
            }
        }
    };

    public static interface onCityPickedListener {
        public void onPicked(Province selectProvince, City selectCity, County selectCounty);

    }

    private onCityPickedListener mlistener;
    public CityPickerDialog(Activity context, List<Province> provinces, Province defaultProvince, City defaultCity, County defaultCounty, onCityPickedListener listener) {
        super(context);
        mlistener = listener;
        mContext = context;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        getWindow().setWindowAnimations(R.style.AnimBottom);
        View rootView = getLayoutInflater().inflate(R.layout.dialog_city_picker, null);
        int screenWidth = mContext.getWindowManager().getDefaultDisplay().getWidth();
        LayoutParams params = new LayoutParams(screenWidth, LayoutParams.MATCH_PARENT);
        super.setContentView(rootView, params);
        mProvinces.addAll(provinces);

        initViews();
        setDefaultArea(defaultProvince, defaultCity, defaultCounty);

        View done = findViewById(R.id.btnSubmit);
        done.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mlistener != null) {
                    Province province = mProvinces.size() > 0 ? mProvinces.get(provinceWheel.getCurrentItem()) : null;
                    City city = mCities.size() > 0 ? mCities.get(citiesWheel.getCurrentItem()) : null;
                    County county = mCounties.size() > 0 ? mCounties.get(countiesWheel.getCurrentItem()) : null;
                    mlistener.onPicked(province, city, county);
                }
                dismiss();
            }
        });

        View cancel = findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setDefaultArea(Province defaultProvince, City defaultCity, County defaultCounty) {

        int provinceItem = 0;
        int cityItem = 0;
        int countyItem = 0;

        if (defaultProvince == null) {
            defaultProvince = mProvinces.get(0);
            provinceItem = 0;
        } else {
            for (int i = 0; i < mProvinces.size(); i++) {
                if (mProvinces.get(i).getAreaId().equals(defaultProvince.getAreaId())) {
                    provinceItem = i;
                    break;
                }
            }
        }
        mCities.clear();
        mCities.addAll(defaultProvince.getCities());
        if (mCities.size() == 0) {
            mCities.add(new City());
            cityItem = 0;
        } else if (defaultCity == null) {
            defaultCity = mCities.get(0);
            cityItem = 0;
        } else {
            for (int i = 0; i < mCities.size(); i++) {
                if (mCities.get(i).getAreaId().equals(defaultCity.getAreaId())) {
                    cityItem = i;
                    break;
                }
            }
        }

        mCounties.clear();
        mCounties.addAll(defaultCity.getCounties());
        if (mCounties.size() == 0) {
            mCounties.add(new County());
            countyItem = 0;
        } else if (defaultCounty == null) {
            defaultCounty = mCounties.get(0);
            countyItem = 0;
        } else {
            for (int i = 0; i < mCounties.size(); i++) {
                if (mCounties.get(i).getAreaId().equals(defaultCounty.getAreaId())) {
                    countyItem = i;
                    break;
                }
            }
        }
        if (provinceWheel != null) provinceWheel.setCurrentItem(provinceItem, false);
        if (citiesWheel != null) citiesWheel.setCurrentItem(cityItem, false);
        if (countiesWheel != null) countiesWheel.setCurrentItem(countyItem, false);

    }

    private void initViews() {

        provinceWheel = (WheelView) findViewById(R.id.provinceWheel);
        citiesWheel = (WheelView) findViewById(R.id.citiesWheel);
        countiesWheel = (WheelView) findViewById(R.id.countiesWheel);

        provinceAdapter = new AbstractWheelTextAdapter(mContext, R.layout.wheel_text) {

            @Override
            public int getItemsCount() {

                return mProvinces.size();
            }

            @Override
            protected CharSequence getItemText(int index) {

                return mProvinces.get(index).getAreaName();
            }
        };

        cityAdapter = new AbstractWheelTextAdapter(mContext, R.layout.wheel_text) {

            @Override
            public int getItemsCount() {

                return mCities.size();
            }

            @Override
            protected CharSequence getItemText(int index) {

                return mCities.get(index).getAreaName();
            }
        };

        countyAdapter = new AbstractWheelTextAdapter(mContext, R.layout.wheel_text) {

            @Override
            public int getItemsCount() {

                return mCounties.size();
            }

            @Override
            protected CharSequence getItemText(int index) {

                return mCounties.get(index).getAreaName();
            }
        };

        if (provinceWheel != null) {
            provinceWheel.setViewAdapter(provinceAdapter);
            provinceWheel.setCyclic(false);
            provinceWheel.setVisibleItems(DEFAULT_ITEMS);
        }

        if (citiesWheel != null) {
            citiesWheel.setViewAdapter(cityAdapter);
            citiesWheel.setCyclic(false);
            citiesWheel.setVisibleItems(DEFAULT_ITEMS);
        }

        if (countiesWheel != null) {
            countiesWheel.setViewAdapter(countyAdapter);
            countiesWheel.setCyclic(false);
            countiesWheel.setVisibleItems(DEFAULT_ITEMS);
        }

        OnWheelClickedListener clickListener = new OnWheelClickedListener() {

            @Override
            public void onItemClicked(WheelView wheel, int itemIndex) {
                if (itemIndex != wheel.getCurrentItem()) {
                    wheel.setCurrentItem(itemIndex, true, 500);
                }
            }
        };

        if (provinceWheel != null) {
            provinceWheel.addClickingListener(clickListener);
            provinceWheel.addChangingListener(new OnWheelChangedListener() {

                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    mHandler.removeMessages(UPDATE_CITY_WHEEL);
                    Message msg = Message.obtain();
                    msg.what = UPDATE_CITY_WHEEL;
                    msg.arg1 = newValue;
                    mHandler.sendMessageDelayed(msg, 50);
                }
            });

        }
        if (citiesWheel != null) {
            citiesWheel.addClickingListener(clickListener);
            citiesWheel.addChangingListener(new OnWheelChangedListener() {

                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    mHandler.removeMessages(UPDATE_COUNTY_WHEEL);
                    Message msg = Message.obtain();
                    msg.what = UPDATE_COUNTY_WHEEL;
                    msg.arg1 = newValue;
                    mHandler.sendMessageDelayed(msg, 50);

                }
            });
        }
        if (countiesWheel != null) countiesWheel.addClickingListener(clickListener);

    }

    public void getResult(){
        if (mlistener != null) {
            Province province = mProvinces.size() > 0 ? mProvinces.get(provinceWheel.getCurrentItem()) : null;
            City city = mCities.size() > 0 ? mCities.get(citiesWheel.getCurrentItem()) : null;
            County county = mCounties.size() > 0 ? mCounties.get(countiesWheel.getCurrentItem()) : null;
            mlistener.onPicked(province, city, county);
        }
    }
}
