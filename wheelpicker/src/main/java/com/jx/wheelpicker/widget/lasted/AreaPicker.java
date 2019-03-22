package com.jx.wheelpicker.widget.lasted;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.jx.wheelpicker.R;
import com.jx.wheelpicker.util.AreaUtils;
import com.jx.wheelpicker.widget.model.Area;
import com.jx.wheelpicker.widget.model.City;
import com.jx.wheelpicker.widget.model.Data;
import com.jx.wheelpicker.widget.model.Province;

import java.util.List;

/**
 * 省市区选择器
 *
 * @author zhaoxl
 */
public class AreaPicker extends FrameLayout {
    private static final String TAG = AreaPicker.class.getSimpleName();

    private static final String DEFAULT_FORMAT = "%s%s%s";

    private final BasePicker mProvincePicker;

    private final BasePicker mCityPicker;

    private final BasePicker mAreaPicker;

    private Province mCurrentProvince;
    private City mCurrentCity;
    private Area mCurrentArea;

    private Province mTempProvince;
    private City mTempCity;
    private Area mTempArea;

    private List<Province> mProvinceData;
    private List<City> mCityData;
    private List<Area> mAreaData;

    private boolean mShortText;

    public AreaPicker(Context context) {
        this(context, null);
    }

    public AreaPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AreaPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.triple_picker_layout, this, true);
        mProvincePicker = findViewById(R.id.wp_one);
        mCityPicker = findViewById(R.id.wp_two);
        mAreaPicker = findViewById(R.id.wp_three);

        BasePicker.OnWheelScrollChangeListener scrollChangeListener = new BasePicker.OnWheelScrollChangeListener() {
            @Override
            public void onWheelScroll(BasePicker wheelPicker, Data data) {
                if (wheelPicker == mProvincePicker) {
                    mTempProvince = mCurrentProvince;
                    mCurrentProvince = (Province) data;
                    if (mTempProvince == null || !mTempProvince.getCode().equals(mCurrentProvince.getCode())) {
                        mCityData = mCurrentProvince.getCity();
                        mCurrentCity = mCityData == null || mCityData.size() == 0 ? null : mCityData.get(0);
                        mAreaData = mCurrentCity == null ? null : mCurrentCity.getArea();
                        mCurrentArea = mAreaData == null || mAreaData.size() == 0 ? null : mAreaData.get(0);
                        mCityPicker.setData(mCityData);
                        mAreaPicker.setData(mAreaData);
                        mCityPicker.setSelectedItemPosition(0);
                        mAreaPicker.setSelectedItemPosition(0);

                        notifyDateChanged();
                    }
                } else if (wheelPicker == mCityPicker) {
                    mTempCity = mCurrentCity;
                    mCurrentCity = (City) data;
                    if (mTempCity == null || !mTempCity.getCode().equals(mCurrentCity.getCode())) {
                        mAreaData = mCurrentCity.getArea();
                        mCurrentArea = mAreaData == null || mAreaData.size() == 0 ? null : mAreaData.get(0);
                        mAreaPicker.setData(mAreaData);
                        mAreaPicker.setSelectedItemPosition(0);

                        notifyDateChanged();
                    }
                } else if (wheelPicker == mAreaPicker) {
                    mTempArea = mCurrentArea;
                    mCurrentArea = ((Area) data);
                    if (mTempArea == null || !mTempArea.getCode().equals(mCurrentArea.getCode())) {

                        notifyDateChanged();
                    }
                }
            }
        };

        addScrollListener(scrollChangeListener);
        updateData();

        //view重绘时回调
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                notifyDateChanged();
                return true;
            }
        });
    }


    private void addScrollListener(BasePicker.OnWheelScrollChangeListener scrollChangeListener) {
        mProvincePicker.setOnWheelScrollChangeListener(scrollChangeListener);
        mCityPicker.setOnWheelScrollChangeListener(scrollChangeListener);
        mAreaPicker.setOnWheelScrollChangeListener(scrollChangeListener);
    }

    private void updateData() {
        if (mProvinceData == null) {
            mProvinceData = AreaUtils.getInstance().getJsonData(getContext());
            if (mProvinceData != null && mProvinceData.size() > 0) {
                mCurrentProvince = mProvinceData.get(0);
                mCityData = mCurrentProvince.getCity();
                if (mCityData != null && mCityData.size() > 0) {
                    mCurrentCity = mCityData.get(0);
                    mAreaData = mCurrentCity.getArea();
                    if (mAreaData != null && mAreaData.size() > 0) {
                        mCurrentArea = mAreaData.get(0);
                        mTempArea = mCurrentArea;
                    }
                }
            }
        }
        updatePicker();
    }

    /**
     * 更新选择器
     */
    private void updatePicker() {
        mProvincePicker.setData(mProvinceData);
        mCityPicker.setData(mCityData);
        mAreaPicker.setData(mAreaData);
    }

    public String getAreaCode() {
        return mCurrentArea.getCode();
    }

    public String getAreaString() {
        return String.format(DEFAULT_FORMAT, mCurrentProvince.getName(), mCurrentCity.getName(), mCurrentArea.getName());
    }

    public String getAreaString(String separator) {
        return getAreaString(separator, false);
    }

    /**
     * @param force 是否必须显示分隔符
     */
    public String getAreaString(String separator, boolean force) {
        if (TextUtils.isEmpty(separator)) {
            return getAreaString();
        }
        return String.format("%s%s%s%s%s",
                mCurrentProvince == null ? "" : mCurrentProvince.getName(),
                //不强制时-省市都有显示分割
                force ? separator : mCurrentProvince == null || mCurrentCity == null ? "" : separator,
                mCurrentCity == null ? "" : mCurrentCity.getName(),
                //不强制时-市区都有显示分割
                force ? separator : mCurrentCity == null || mCurrentArea == null ? "" : separator,
                mCurrentArea == null ? "" : mCurrentArea.getName());
    }

    public Province getProvince() {
        return mCurrentProvince;
    }

    public City getCity() {
        return mCurrentCity;
    }

    public Area getArea() {
        return mCurrentArea;
    }

    /**
     * @deprecated
     */
    public void setShortText(boolean shortText) {
        if (this.mShortText != shortText) {
            this.mShortText = shortText;
            updateData();
        }
    }

    /**
     * 通知改变，即监听的回调
     */
    private void notifyDateChanged() {
        if (onAreaChangeListener != null) {
            onAreaChangeListener.onAreaChanged(this, getProvince(), getCity(), getArea());
        }
    }

    public void setAdjustTextSize(boolean adjustTextSize) {
        mProvincePicker.setAdjustTextSize(adjustTextSize);
        mCityPicker.setAdjustTextSize(adjustTextSize);
        mAreaPicker.setAdjustTextSize(adjustTextSize);
    }

    public void setItemTextSize(float size) {
        mProvincePicker.setItemTextSize(size);
        mCityPicker.setItemTextSize(size);
        mAreaPicker.setItemTextSize(size);
    }

    public void setItemTextSize(int unit, float value) {
        mProvincePicker.setItemTextSize(unit, value);
        mCityPicker.setItemTextSize(unit, value);
        mAreaPicker.setItemTextSize(unit, value);
    }

    public void setDefaultByCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return;
        }
        if (code.length() != 6) {
            return;
        }

        if (mProvinceData == null) {
            return;
        }
        String provinceSub = code.substring(0, 2);
        String citySub = code.substring(0, 4);
        String areaSub = code.substring(0, 6);
        for (int p = 0; p < mProvinceData.size(); p++) {
            String pCode = mProvinceData.get(p).getCode();
            if (pCode == null || pCode.length() < 2) {
                return;
            }
            if (pCode.substring(0, 2).equals(provinceSub)) {
                mProvincePicker.setSelectedItemPosition(p);
                mCityData = mProvinceData.get(p).getCity();
                mCityPicker.setData(mCityData);
                if (mCityData == null) {
                    return;
                }
                for (int c = 0; c < mCityData.size(); c++) {
                    String cCode = mCityData.get(c).getCode();
                    if (cCode == null || cCode.length() < 4) {
                        return;
                    }
                    if (cCode.substring(0, 4).equals(citySub)) {
                        mCityPicker.setSelectedItemPosition(c);
                        mAreaData = mCityData.get(c).getArea();
                        mAreaPicker.setData(mAreaData);
                        if (mAreaData == null) {
                            return;
                        }
                        for (int a = 0; a < mAreaData.size(); a++) {
                            String aCode = mAreaData.get(c).getCode();
                            if (aCode == null || aCode.length() < 6) {
                                return;
                            }
                            if (aCode.substring(0, 6).equals(areaSub)) {
                                mAreaPicker.setSelectedItemPosition(a);
                                return;
                            }
                        }
                        return;
                    }
                }
                return;
            }
        }
    }

    public void setDefaultByName(String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        if (mProvinceData == null) {
            return;
        }
        for (int p = 0; p < mProvinceData.size(); p++) {
            String provinceName = mProvinceData.get(p).getName();
            if (name.startsWith(provinceName)) {
                mProvincePicker.setSelectedItemPosition(p);
                mCityData = mProvinceData.get(p).getCity();
                mCityPicker.setData(mCityData);
                for (int c = 0; c < mCityData.size(); c++) {
                    String cityName = mCityData.get(c).getName();
                    if (name.startsWith(provinceName + cityName)) {
                        mCityPicker.setSelectedItemPosition(c);
                        mAreaData = mCityData.get(c).getArea();
                        mAreaPicker.setData(mAreaData);
                        for (int a = 0; a < mAreaData.size(); a++) {
                            String areaName = mAreaData.get(a).getName();
                            if (name.startsWith(provinceName + cityName + areaName)) {
                                mAreaPicker.setSelectedItemPosition(a);
                                return;
                            }
                        }
                        return;
                    }
                }
                return;
            }
        }
    }

    public interface OnAreaChangeListener {
        /**
         * 停止滚动即回调
         *
         * @param areaPicker AreaPicker
         * @param province   省
         * @param city       市
         * @param area       区县
         */
        void onAreaChanged(AreaPicker areaPicker, Province province, City city, Area area);
    }

    private OnAreaChangeListener onAreaChangeListener;

    public void setOnAreaChangeListener(OnAreaChangeListener listener) {
        this.onAreaChangeListener = listener;
    }
}
