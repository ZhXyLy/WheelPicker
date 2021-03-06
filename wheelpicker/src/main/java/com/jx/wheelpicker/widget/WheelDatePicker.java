package com.jx.wheelpicker.widget;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.jx.wheelpicker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * 日期选择器
 *
 * @author zhaoxl
 * @date 2018/6/26
 */
public class WheelDatePicker extends LinearLayout implements IWheelDatePicker {
    private static final String TAG = "WheelDatePicker";

    private static final int DEFAULT_YEAR_COUNT = 100;
    private static final int DEFAULT_MONTH_COUNT = 12;
    private static String[] WEEK_DAYS;

    private List<Integer> mYearList, mMonthList, mDayList;
    private List<String> mYearName, mMonthName, mDayName;
    private int mCurYear, mCurMonth, mCurDay;
    private final String unitYear, unitMonth, unitDay;

    private LayoutParams mLayoutParams;

    private WheelPicker mWPYear, mWPMonth, mWPDay;

    private boolean isHideDay;

    private int mSelectMinYear;
    private int mSelectMinMonth;
    private int mSelectMinDay;
    private int mMinYear;

    public WheelDatePicker(Context context) {
        this(context, null);
    }

    public WheelDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        unitYear = getResources().getString(R.string.wp_year);
        unitMonth = getResources().getString(R.string.wp_month);
        unitDay = getResources().getString(R.string.wp_day);
        WEEK_DAYS = getResources().getStringArray(R.array.wp_WheelArrayWeek);

        initLayoutParams();

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

    private void initLayoutParams() {
        mLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.setMargins(5, 5, 5, 5);
        mLayoutParams.width = 0;
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);

        mYearName = new ArrayList<>();
        mMonthName = new ArrayList<>();
        mDayName = new ArrayList<>();

        mWPYear = new WheelPicker(context);
        mWPMonth = new WheelPicker(context);
        mWPDay = new WheelPicker(context);
        mWPYear.setItemAlign(WheelPicker.ALIGN_RIGHT);
        mWPMonth.setItemAlign(WheelPicker.ALIGN_CENTER);
        mWPDay.setItemAlign(WheelPicker.ALIGN_LEFT);

        setWeightSum(4);
        setGravity(Gravity.CENTER);
        initWheelPicker(mWPYear, 1f);
        initWheelPicker(mWPMonth, 1f);
        initWheelPicker(mWPDay, 1f);
    }

    private void initWheelPicker(WheelPicker wheelPicker, float weight) {
        //其实传入的这个weight真的没用（永远都是1:1:1），如果要比重，需要每个设置自己的LayoutParams，
        //我们是平分，就共用一个
        mLayoutParams.weight = weight;
        wheelPicker.setItemTextSize(getContext().getResources().getDimension(R.dimen.wp_item_text_size));
        wheelPicker.setSelectedItemTextColor(ResourcesCompat.getColor(getContext().getResources(), R.color.wp_select_item_color, null));
        wheelPicker.setCurved(true);
        wheelPicker.setVisibleItemCount(7);
        wheelPicker.setAtmospheric(true);
        wheelPicker.setItemSpace(getContext().getResources().getDimensionPixelSize(R.dimen.wp_WheelItemSpace));
        wheelPicker.setLayoutParams(mLayoutParams);
        addView(wheelPicker);
    }

    private void obtainDateData() {
        Calendar calendar = Calendar.getInstance();
        mCurYear = calendar.get(Calendar.YEAR);
        mCurMonth = calendar.get(Calendar.MONTH);
        mCurDay = calendar.get(Calendar.DATE);
        mYearList = new ArrayList<>();
        //前后50年
        for (int i = 0; i < DEFAULT_YEAR_COUNT; i++) {
            mYearList.add(i - DEFAULT_YEAR_COUNT / 2 + mCurYear);
        }
        mMinYear = mYearList.get(0);
        mMonthList = new ArrayList<>();
        for (int i = 0; i < DEFAULT_MONTH_COUNT; i++) {
            mMonthList.add(i + 1);
        }

        for (int intYear : mYearList) {
            mYearName.add(intYear + unitYear);
        }
        mWPYear.setData(mYearName);
        mWPYear.setSelectedItemPosition(DEFAULT_YEAR_COUNT / 2);
        for (int intMonth : mMonthList) {
            String sMonth = intMonth < 10 ? "0" + intMonth : "" + intMonth;
            mMonthName.add(sMonth + unitMonth);
        }
        mWPMonth.setData(mMonthName);
        mWPMonth.setSelectedItemPosition(mCurMonth);
        mDayList = new ArrayList<>();
        computeDayList(true);
    }

    private void computeDayList(boolean isInit) {
        int year = mYearList.get(mWPYear.getCurrentItemPosition());
        int month = mMonthList.get(mWPMonth.getCurrentItemPosition());
        int daySize;
        //如果是2月，闰年29天，平年28天
        //如果是1,3,5,7,8,10,12月，为31天
        //其余为30天
        switch (month) {
            case 2:
                boolean isLeapYear = year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
                if (isLeapYear) {
                    daySize = 29;
                } else {
                    daySize = 28;
                }
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daySize = 31;
                break;
            default:
                daySize = 30;
                break;
        }
        mDayList.clear();
        for (int i = 0; i < daySize; i++) {
            mDayList.add(i + 1);
        }
        Calendar calendar = Calendar.getInstance();
        mDayName.clear();
        for (int intDay : mDayList) {
            calendar.set(getYear(), getMonth() - 1, intDay);
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            String sDay = intDay < 10 ? "0" + intDay : "" + intDay;
            mDayName.add(sDay + unitDay + "\t" + WEEK_DAYS[week]);
        }
        mWPDay.setData(mDayName);
        if (isInit) {
            //初始化时默认当天
            mWPDay.setSelectedItemPosition(mCurDay - 1);
        }
    }

    private void addListenerToWheelPicker() {
        mWPYear.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                int year = getYear();
                if (year <= mSelectMinYear) {
                    if (year < mSelectMinYear) {
                        mWPYear.setSelectedItemPositionByPosition(mWPYear.getCurrentItemPosition(), mYearList.indexOf(mSelectMinYear));
                    }
                    if (getMonth() <= mSelectMinMonth + 1) {
                        if (getMonth() < mSelectMinMonth + 1) {
                            mWPMonth.setSelectedItemPositionByPosition(mWPMonth.getCurrentItemPosition(), mMonthList.indexOf(mSelectMinMonth + 1));
                        }
                        if (getDay() < mSelectMinDay) {
                            mWPDay.setSelectedItemPositionByPosition(mWPDay.getCurrentItemPosition(), mDayList.indexOf(mSelectMinDay));
                        }
                    }
                    return;
                }
                computeDayList(false);
                wheelScrollChanged();
            }
        });

        mWPMonth.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                int year = getYear();
                if (year <= mSelectMinYear) {
                    if (year < mSelectMinYear) {
                        mWPYear.setSelectedItemPositionByPosition(mWPYear.getCurrentItemPosition(), mYearList.indexOf(mSelectMinYear));
                    }
                    if (getMonth() <= mSelectMinMonth + 1) {
                        if (getMonth() < mSelectMinMonth + 1) {
                            mWPMonth.setSelectedItemPositionByPosition(mWPMonth.getCurrentItemPosition(), mMonthList.indexOf(mSelectMinMonth + 1));
                        }
                        if (getDay() < mSelectMinDay) {
                            mWPDay.setSelectedItemPositionByPosition(mWPDay.getCurrentItemPosition(), mDayList.indexOf(mSelectMinDay));
                        }
                    }
                    return;
                }
                computeDayList(false);
                wheelScrollChanged();
            }
        });
        mWPDay.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                int year = getYear();
                if (year <= mSelectMinYear) {
                    if (year < mSelectMinYear) {
                        mWPYear.setSelectedItemPositionByPosition(mWPYear.getCurrentItemPosition(), mYearList.indexOf(mSelectMinYear));
                    }
                    if (getMonth() <= mSelectMinMonth + 1) {
                        if (getMonth() < mSelectMinMonth + 1) {
                            mWPMonth.setSelectedItemPositionByPosition(mWPMonth.getCurrentItemPosition(), mMonthList.indexOf(mSelectMinMonth + 1));
                        }
                        if (getDay() < mSelectMinDay) {
                            mWPDay.setSelectedItemPositionByPosition(mWPDay.getCurrentItemPosition(), mDayList.indexOf(mSelectMinDay));
                        }
                    }
                    return;
                }
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
    public int getYear() {
        return mYearList.get(mWPYear.getCurrentItemPosition());
    }

    @Override
    public int getMonth() {
        return mMonthList.get(mWPMonth.getCurrentItemPosition());
    }

    @Override
    public int getDay() {
        return mDayList.get(mWPDay.getCurrentItemPosition());
    }

    @Override
    public String getStringDate(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(getDate());
    }

    @Override
    public Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getYear(), getMonth() - 1, getDay());
        return calendar.getTime();
    }

    @Override
    public void setYearRange(int minYear, int maxYear) {
        this.mMinYear = minYear;
        int range = maxYear - minYear + 1;
        mYearList.clear();
        for (int i = 0; i < range; i++) {
            mYearList.add(minYear + i);
        }
        mYearName.clear();
        for (int intYear : mYearList) {
            mYearName.add(intYear + unitYear);
        }
        mWPYear.setData(mYearName);
        mWPYear.setSelectedItemPosition(mCurYear - minYear);
    }

    public void setMinDate(Date minDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        this.mSelectMinYear = year;
        this.mSelectMinMonth = month;
        this.mSelectMinDay = day;
        if (year > mCurYear) {
            mWPYear.setSelectedItemPosition(year - mMinYear);
            mWPMonth.setSelectedItemPosition(month);
            mWPDay.setSelectedItemPosition(day - 1);
        } else if (year == mCurYear) {
            if (month > mCurMonth) {
                mWPMonth.setSelectedItemPosition(month);
                mWPDay.setSelectedItemPosition(day - 1);
            } else if (month == mCurMonth) {
                if (day > mCurDay) {
                    mWPDay.setSelectedItemPosition(day - 1);
                }
            }
        }
    }

    @Override
    public void setItemTextSize(float size) {
        if (mWPYear != null) {
            mWPYear.setItemTextSize(size);
        }
        if (mWPMonth != null) {
            mWPMonth.setItemTextSize(size);
        }
        if (mWPDay != null) {
            mWPDay.setItemTextSize(size);
        }
    }

    @Override
    public void setItemTextSize(int unit, float value) {
        if (mWPYear != null) {
            mWPYear.setItemTextSize(unit, value);
        }
        if (mWPMonth != null) {
            mWPMonth.setItemTextSize(unit, value);
        }
        if (mWPDay != null) {
            mWPDay.setItemTextSize(unit, value);
        }
    }

    @Override
    public void setSelectPositionByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        mWPYear.setSelectedItemPosition(year - mYearList.get(0));
        mWPMonth.setSelectedItemPosition(month);
        mWPDay.setSelectedItemPosition(day - 1);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private OnWheelScrollChangeListener onWheelScrollChangeListener;

    public void setOnWheelScrollChangeListener(OnWheelScrollChangeListener listener) {
        this.onWheelScrollChangeListener = listener;
    }

    public interface OnWheelScrollChangeListener {
        /**
         * 停止滚动即回调
         *
         * @param wheelDatePicker IWheelDatePicker
         */
        void onWheelScroll(IWheelDatePicker wheelDatePicker);
    }

    public void setHideDay(boolean hideDay) {
        this.isHideDay = hideDay;
        if (hideDay) {
            mWPYear.setItemAlign(WheelPicker.ALIGN_CENTER);
        }
        mWPDay.setVisibility(hideDay ? GONE : VISIBLE);
    }
}