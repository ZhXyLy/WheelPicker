package com.jx.wheelpicker.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jx.wheelpicker.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 时间选择器
 *
 * @author zhaoxl
 * @date 2018/6/26
 */
public class WheelTimePicker extends LinearLayout implements IWheelTimePicker {
    private static final String TAG = "WheelTimePicker";

    private static final float ITEM_TEXT_SIZE = 20;
    private static final float ITEM_SPACE = 10;
    private static final String SELECTED_ITEM_COLOR = "#353535";
    private static final int DEFAULT_HOUR_COUNT = 24;
    private static final int DEFAULT_MINUTE_COUNT = 60;

    private List<Integer> mHourList, mMinuteList, mSecondList;
    private List<String> mHourName, mMinuteName, mSecondName;
    private String unitHour, unitMinute, unitSecond;

    private WheelPicker mWPHour, mWPMinute, mWPSecond;
    private boolean isShowSecond = true;
    private int minuteInterval = 1;

    public WheelTimePicker(Context context) {
        this(context, null);
    }

    public WheelTimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        unitHour = getResources().getString(R.string.wp_hour);
        unitMinute = getResources().getString(R.string.wp_minute);
        unitSecond = getResources().getString(R.string.wp_second);

        initView(context);

        obtainDateData();

        addListenerToWheelPicker();

        //view重绘时回调
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                wheelScrollChanged();//初始化完毕，第一次显示时的回调
                return true;
            }
        });
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);

        mHourName = new ArrayList<>();
        mMinuteName = new ArrayList<>();
        mSecondName = new ArrayList<>();

        mWPHour = new WheelPicker(context);
        mWPMinute = new WheelPicker(context);
        mWPSecond = new WheelPicker(context);
        mWPHour.setItemAlign(WheelPicker.ALIGN_RIGHT);
        mWPMinute.setItemAlign(WheelPicker.ALIGN_CENTER);
        mWPSecond.setItemAlign(WheelPicker.ALIGN_LEFT);

        setWeightSum(4);
        setGravity(Gravity.CENTER);
        initWheelPicker(mWPHour, 1.0f);
//        addDivider();
        initWheelPicker(mWPMinute, 1.0f);
//        addDivider();
        initWheelPicker(mWPSecond, 1.0f);
    }

    private void addDivider() {
        //添加分隔符 :
        TextView textView = new TextView(getContext());
        textView.setText(":");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ITEM_TEXT_SIZE);
        textView.getPaint().setFakeBoldText(true);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(layoutParams);
        addView(textView);
    }

    private void initWheelPicker(WheelPicker wheelPicker, float weight) {
        LayoutParams mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(5, 5, 5, 5);
        mLayoutParams.width = 0;
        mLayoutParams.weight = weight;
        wheelPicker.setItemTextSize(dip2px(getContext(), ITEM_TEXT_SIZE));
        wheelPicker.setSelectedItemTextColor(Color.parseColor(SELECTED_ITEM_COLOR));
        wheelPicker.setCurved(true);
//        wheelPicker.setCyclic(true);
        wheelPicker.setVisibleItemCount(7);
        wheelPicker.setAtmospheric(true);
        wheelPicker.setItemSpace(dip2px(getContext(), ITEM_SPACE));
        wheelPicker.setLayoutParams(mLayoutParams);
        addView(wheelPicker);
    }

    private void obtainDateData() {
        Calendar calendar = Calendar.getInstance();
        int mCurHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mCurMinute = calendar.get(Calendar.MINUTE);
        int mCurSecond = calendar.get(Calendar.SECOND);
        mHourList = new ArrayList<>();
        for (int i = 0; i < DEFAULT_HOUR_COUNT; i++) {
            mHourList.add(i);
            mHourName.add(format(i) + unitHour);
        }
        mMinuteList = new ArrayList<>();
        mMinuteName.clear();
        for (int i = 0; i < DEFAULT_MINUTE_COUNT; i++) {
            if (i % minuteInterval == 0) {
                mMinuteList.add(i);
                mMinuteName.add(format(i) + unitMinute);
            }

        }
        mSecondList = new ArrayList<>();
        for (int i = 0; i < DEFAULT_MINUTE_COUNT; i++) {
            mSecondList.add(i);
            mSecondName.add(format(i) + unitSecond);
        }

        mWPHour.setData(mHourName);
        mWPHour.setSelectedItemPosition(mCurHour);
        mWPMinute.setData(mMinuteName);
        mWPMinute.setSelectedItemPosition(mCurMinute / minuteInterval);
        mWPSecond.setData(mSecondName);
        mWPSecond.setSelectedItemPosition(mCurSecond);
    }

    private void addListenerToWheelPicker() {
        //监听省份的滑轮,根据省份的滑轮滑动的数据来设置市跟地区的滑轮数据
        mWPHour.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                wheelScrollChanged();
            }
        });

        mWPMinute.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                wheelScrollChanged();
            }
        });

        mWPSecond.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                wheelScrollChanged();
            }
        });
    }

    private void wheelScrollChanged() {
        if (onWheelScrollChangeListener != null) {
            onWheelScrollChangeListener.onWheelScroll(this);
        }
    }

    @Override
    public int getHour() {
        int currentItemPosition = mWPHour.getCurrentItemPosition();
//        if (currentItemPosition > mHourList.size() - 1) {
//            currentItemPosition = currentItemPosition - mHourList.size() - 1;
//        }
        return mHourList.get(currentItemPosition);
    }

    @Override
    public int getMinute() {
        int currentItemPosition = mWPMinute.getCurrentItemPosition();
//        if (currentItemPosition > mMinuteList.size() - 1) {
//            currentItemPosition = currentItemPosition - mMinuteList.size() - 1;
//        }
        return mMinuteList.get(currentItemPosition);
    }

    @Override
    public int getSecond() {
        int currentItemPosition = mWPSecond.getCurrentItemPosition();
//        if (currentItemPosition > mSecondList.size() - 1) {
//            currentItemPosition = currentItemPosition - mSecondList.size() - 1;
//        }
        return mSecondList.get(currentItemPosition);
    }

    @Override
    public String getStringTime() {
        if (isShowSecond()) {
            int hour = getHour();
            int minute = getMinute();
            int second = getSecond();
            return format(hour) + ":" + format(minute) + ":" + format(second);
        } else {
            int hour = getHour();
            int minute = getMinute();
            return format(hour) + ":" + format(minute);
        }
    }

    public void setMinuteInterval(int minuteInterval) {
        if (minuteInterval > 1 && minuteInterval < 60) {
            this.minuteInterval = minuteInterval;
        }
        obtainDateData();
    }

    @Override
    public boolean isShowSecond() {
        return isShowSecond;
    }

    @Override
    public void setShowSecond(boolean show) {
        isShowSecond = show;
        if (!show) {
            mWPHour.setItemAlign(WheelPicker.ALIGN_CENTER);
        }
        mWPSecond.setVisibility(show ? VISIBLE : GONE);
    }

    @Override
    public void setSelectPositionByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mWPHour.setSelectedItemPosition(hour);
        mWPMinute.setSelectedItemPosition(minute);
        if (isShowSecond()) {
            int second = calendar.get(Calendar.SECOND);
            mWPSecond.setSelectedItemPosition(second);
        }
    }

    @Override
    public void setItemTextSize(float size) {
        if (mWPHour != null) {
            mWPHour.setItemTextSize(size);
        }
        if (mWPMinute != null) {
            mWPMinute.setItemTextSize(size);
        }
        if (mWPSecond != null) {
            mWPSecond.setItemTextSize(size);
        }
    }

    @Override
    public void setItemTextSize(int unit, float value) {
        if (mWPHour != null) {
            mWPHour.setItemTextSize(unit, value);
        }
        if (mWPMinute != null) {
            mWPMinute.setItemTextSize(unit, value);
        }
        if (mWPSecond != null) {
            mWPSecond.setItemTextSize(unit, value);
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private String format(int data) {
        return data < 10 ? "0" + data : data + "";
    }

    private OnWheelScrollChangeListener onWheelScrollChangeListener;

    public void setOnWheelScrollChangeListener(OnWheelScrollChangeListener listener) {
        this.onWheelScrollChangeListener = listener;
    }

    public interface OnWheelScrollChangeListener {
        /**
         * 停止滚动即回调
         *
         * @param wheelTimePicker IWheelTimePicker
         */
        void onWheelScroll(IWheelTimePicker wheelTimePicker);
    }
}