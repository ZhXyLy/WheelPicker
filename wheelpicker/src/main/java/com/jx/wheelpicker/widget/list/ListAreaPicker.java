package com.jx.wheelpicker.widget.list;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jx.wheelpicker.R;
import com.jx.wheelpicker.util.AreaUtils;
import com.jx.wheelpicker.widget.model.Area;
import com.jx.wheelpicker.widget.model.City;
import com.jx.wheelpicker.widget.model.Data;
import com.jx.wheelpicker.widget.model.Province;

import java.util.List;

/**
 * @author zhaoxl
 */
public class ListAreaPicker extends RelativeLayout {
    private static final String TAG = "ListAreaPicker";

    private static final String DEFAULT_FORMAT = "%s%s%s";

    private FrameLayout flSecondLevel;
    private FrameLayout flThirdLevel;
    private ListAreaAdapter firstLevelAdapter;
    private ListAreaAdapter secondLevelAdapter;
    private ListAreaAdapter thirdLevelAdapter;

    private Province mCurrentProvince;
    private City mCurrentCity;
    private Area mCurrentArea;

    public ListAreaPicker(Context context) {
        this(context, null);
    }

    public ListAreaPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListAreaPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(context);

        initListener();

        initData();
    }

    private void initData() {
        List<Province> jsonData = AreaUtils.getInstance().getJsonData(getContext());
        setData(jsonData);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.list_area_picker, this);
        RecyclerView rvFirstLevel = findViewById(R.id.rv_first_level);
        flSecondLevel = findViewById(R.id.fl_second_level);
        RecyclerView rvSecondLevel = findViewById(R.id.rv_second_level);
        flThirdLevel = findViewById(R.id.fl_third_level);
        RecyclerView rvThirdLevel = findViewById(R.id.rv_third_level);

        rvFirstLevel.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSecondLevel.setLayoutManager(new LinearLayoutManager(getContext()));
        rvThirdLevel.setLayoutManager(new LinearLayoutManager(getContext()));

        firstLevelAdapter = new ListAreaAdapter();
        rvFirstLevel.setAdapter(firstLevelAdapter);
        secondLevelAdapter = new ListAreaAdapter();
        rvSecondLevel.setAdapter(secondLevelAdapter);
        thirdLevelAdapter = new ListAreaAdapter();
        rvThirdLevel.setAdapter(thirdLevelAdapter);
    }

    private void initListener() {
        firstLevelAdapter.setOnItemCheckedListener(new ListAreaAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(Data data, int position) {
                flSecondLevel.setVisibility(VISIBLE);
                flThirdLevel.setVisibility(GONE);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) flSecondLevel.getLayoutParams();
                layoutParams.weight = 2;
                flSecondLevel.setLayoutParams(layoutParams);
                List<City> city = null;
                if (data instanceof Province) {
                    mCurrentProvince = (Province) data;
                    mCurrentCity = null;
                    mCurrentArea = null;
                    if (onAreaCheckedListener != null) {
                        onAreaCheckedListener.onAreaChecked(ListAreaPicker.this, mCurrentProvince, null, null);
                    }
                    city = mCurrentProvince.getCity();
                }
                secondLevelAdapter.clear();
                thirdLevelAdapter.clear();
                secondLevelAdapter.setNewData(city);
                thirdLevelAdapter.setNewData(null);
            }
        });
        secondLevelAdapter.setOnItemCheckedListener(new ListAreaAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(Data data, int position) {
                flSecondLevel.setVisibility(VISIBLE);
                flThirdLevel.setVisibility(VISIBLE);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) flSecondLevel.getLayoutParams();
                layoutParams.weight = 1;
                flSecondLevel.setLayoutParams(layoutParams);
                List<Area> areas = null;
                if (data instanceof City) {
                    mCurrentCity = (City) data;
                    mCurrentArea = null;
                    if (onAreaCheckedListener != null) {
                        onAreaCheckedListener.onAreaChecked(ListAreaPicker.this, mCurrentProvince, mCurrentCity, null);
                    }
                    areas = mCurrentCity.getArea();
                }
                thirdLevelAdapter.clear();
                thirdLevelAdapter.setNewData(areas);
            }
        });
        thirdLevelAdapter.setOnItemCheckedListener(new ListAreaAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(Data data, int position) {
                if (data instanceof Area) {
                    mCurrentArea = (Area) data;
                    if (onAreaCheckedListener != null) {
                        onAreaCheckedListener.onAreaChecked(ListAreaPicker.this, mCurrentProvince, mCurrentCity, mCurrentArea);
                    }
                }
            }
        });
    }

    public void setData(List<Province> data) {
        flSecondLevel.setVisibility(GONE);
        flThirdLevel.setVisibility(GONE);
        mCurrentProvince = null;
        mCurrentCity = null;
        mCurrentArea = null;
        firstLevelAdapter.setNewData(data);
    }

    public void setItemTextSize(float itemTextSize) {
        firstLevelAdapter.setTextSize(itemTextSize);
        secondLevelAdapter.setTextSize(itemTextSize);
        thirdLevelAdapter.setTextSize(itemTextSize);
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


    public interface OnAreaCheckedListener {
        /**
         * 列表选中即回调
         *
         * @param listAreaPicker ListAreaPicker
         * @param province       省
         * @param city           市
         * @param area           区县
         */
        void onAreaChecked(ListAreaPicker listAreaPicker, Province province, City city, Area area);
    }

    private OnAreaCheckedListener onAreaCheckedListener;

    public void setOnAreaCheckedListener(OnAreaCheckedListener listener) {
        this.onAreaCheckedListener = listener;
    }
}
