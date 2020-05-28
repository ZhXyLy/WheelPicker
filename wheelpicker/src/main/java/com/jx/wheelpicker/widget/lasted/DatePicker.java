package com.jx.wheelpicker.widget.lasted;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jx.wheelpicker.R;
import com.jx.wheelpicker.widget.model.Data;
import com.jx.wheelpicker.widget.model.DayData;
import com.jx.wheelpicker.widget.model.MonthData;
import com.jx.wheelpicker.widget.model.YearData;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
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
 * @date 19/2/15
 */
public class DatePicker extends FrameLayout {
    private static final String TAG = DatePicker.class.getSimpleName();

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    private static final int DEFAULT_START_YEAR = 1900;

    private static final int DEFAULT_END_YEAR = 2100;
    private TextView tvWeek;

    /**
     * @hide
     */
    @IntDef({ALL, NO_YEAR, NO_DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DateMode {
    }

    public static final int ALL = 0;
    public static final int NO_YEAR = 1;
    public static final int NO_DAY = 2;

    private int mDateMode = ALL;

    private final BasePicker mDayPicker;

    private final BasePicker mMonthPicker;

    private final BasePicker mYearPicker;

    private final TextView mDayUnit;

    private final TextView mMonthUnit;

    private final TextView mYearUnit;

    private final LinearLayout mDayParent;

    private final LinearLayout mMonthParent;

    private final LinearLayout mYearParent;

    private boolean mShowUnit = true;

    private boolean mShowWeek = false;

    private String[] mStringUnit = {"年", "月", "日"};

    private static String[] WEEK_DAYS;

    private Calendar mTempDate;

    private Calendar mMinDate;

    private Calendar mMaxDate;

    private Calendar mCurrentDate;

    private List<YearData> mYearData;
    private List<MonthData> mMonthData;
    private List<DayData> mDayData;

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // initialization based on locale
        setCurrentLocale(Locale.getDefault());

        WEEK_DAYS = getResources().getStringArray(R.array.wp_WheelArrayWeek);

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.wp_date_picker_layout, this, true);
        mYearPicker = findViewById(R.id.wp_year);
        mMonthPicker = findViewById(R.id.wp_month);
        mDayPicker = findViewById(R.id.wp_day);
        mYearUnit = findViewById(R.id.tv_year_unit);
        mMonthUnit = findViewById(R.id.tv_month_unit);
        mDayUnit = findViewById(R.id.tv_day_unit);
        mYearParent = findViewById(R.id.ll_year);
        mMonthParent = findViewById(R.id.ll_month);
        mDayParent = findViewById(R.id.ll_day);
        tvWeek = findViewById(R.id.tv_week);

        setDate(mCurrentDate.get(Calendar.YEAR), mCurrentDate.get(Calendar.MONTH), mCurrentDate.get(Calendar.DATE));

        BasePicker.OnWheelScrollChangeListener scrollChangeListener = new BasePicker.OnWheelScrollChangeListener() {
            @Override
            public void onWheelScroll(BasePicker wheelPicker, Data data) {
                mTempDate.setTimeInMillis(mCurrentDate.getTimeInMillis());
                if (wheelPicker == mYearPicker) {
                    int year = ((YearData) data).year;
                    mTempDate.set(Calendar.YEAR, year);
                } else if (wheelPicker == mMonthPicker) {
                    int month = ((MonthData) data).month - 1;
                    mTempDate.set(Calendar.MONTH, month);
                    int day = mTempDate.get(Calendar.DAY_OF_MONTH);
                    mTempDate.set(mTempDate.get(Calendar.YEAR), month + 1, 0);
                    int endDay = mTempDate.get(Calendar.DAY_OF_MONTH);
                    mTempDate.set(Calendar.DATE, Math.min(day, endDay));
                } else if (wheelPicker == mDayPicker) {
                    int day = ((DayData) data).day;
                    mTempDate.set(Calendar.DATE, day);
                }
                if (!mTempDate.equals(mCurrentDate)) {
                    int year = mTempDate.get(Calendar.YEAR);
                    int month = mTempDate.get(Calendar.MONTH);
                    int day = mTempDate.get(Calendar.DAY_OF_MONTH);
                    setDate(year, month, day);
                    setWeek();
                    updateData();
                    updateDatePicker();
                    notifyDateChanged();
                }
            }
        };

        addScrollListener(scrollChangeListener);
        setDefaultDate(mCurrentDate.getTimeInMillis());

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
        mYearPicker.setOnWheelScrollChangeListener(scrollChangeListener);
        mMonthPicker.setOnWheelScrollChangeListener(scrollChangeListener);
        mDayPicker.setOnWheelScrollChangeListener(scrollChangeListener);
    }

    private void setDate(int year, int month, int day) {
        mCurrentDate.set(year, month, day);
        if (mCurrentDate.before(mMinDate)) {
            mCurrentDate.setTimeInMillis(mMinDate.getTimeInMillis());
        } else if (mCurrentDate.after(mMaxDate)) {
            mCurrentDate.setTimeInMillis(mMaxDate.getTimeInMillis());
        }
    }

    private void setCurrentLocale(Locale locale) {
        mTempDate = getCalendarForLocale(mTempDate, locale);
        mMinDate = getCalendarForLocale(mMinDate, locale);
        mMaxDate = getCalendarForLocale(mMaxDate, locale);
        //默认最小日期
        mTempDate.set(DEFAULT_START_YEAR, 0, 1);
        mMinDate.setTime(mTempDate.getTime());
        //默认最大日期
        mTempDate.set(DEFAULT_END_YEAR, 11, 31);
        mMaxDate.setTime(mTempDate.getTime());
        mCurrentDate = getCalendarForLocale(mCurrentDate, locale);
    }

    private Calendar getCalendarForLocale(Calendar oldCalendar, Locale locale) {
        if (oldCalendar == null) {
            return Calendar.getInstance(locale);
        } else {
            final long currentTimeMillis = oldCalendar.getTimeInMillis();
            Calendar newCalendar = Calendar.getInstance(locale);
            newCalendar.setTimeInMillis(currentTimeMillis);
            return newCalendar;
        }
    }

    public DateFormat getFormatter() {
        return mDateFormat;
    }

    public String getDateString() {
        return mDateFormat.format(mCurrentDate.getTime());
    }

    public String getDateString(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(mCurrentDate.getTime());
    }

    public Date getDate(){
        return mCurrentDate.getTime();
    }

    public int getYear() {
        return mCurrentDate.get(Calendar.YEAR);
    }

    public int getMonth() {
        return mCurrentDate.get(Calendar.MONTH) + 1;
    }

    public int getDayOfMonth() {
        return mCurrentDate.get(Calendar.DATE);
    }

    public String getWeek() {
        int week = mCurrentDate.get(Calendar.DAY_OF_WEEK) - 1;
        return WEEK_DAYS[week];
    }

    private void updateData() {
        if (mYearData == null) {
            mYearData = new ArrayList<>();
        }
        if (mMonthData == null) {
            mMonthData = new ArrayList<>();
        }
        if (mDayData == null) {
            mDayData = new ArrayList<>();
        }

        updateYearData();
        updateMonthData();
        updateDayData();
    }

    private void updateYearData() {
        //年 数据
        int minYear = mMinDate.get(Calendar.YEAR);
        int maxYear = mMaxDate.get(Calendar.YEAR);
        mYearData.clear();
        for (int y = minYear; y <= maxYear; y++) {
            mYearData.add(new YearData(y));
        }
    }

    private void updateMonthData() {
        int minYear = mMinDate.get(Calendar.YEAR);
        int maxYear = mMaxDate.get(Calendar.YEAR);
        //月 数据
        int minMonth = mMinDate.get(Calendar.MONTH) + 1;
        int maxMonth = mMaxDate.get(Calendar.MONTH) + 1;
        int startMonth = 1;
        int endMonth = 12;
        if (getYear() == minYear) {
            startMonth = minMonth;
        } else if (getYear() == maxYear) {
            endMonth = maxMonth;
        }
        mMonthData.clear();
        for (int m = startMonth; m <= endMonth; m++) {
            mMonthData.add(new MonthData(m));
        }
    }

    private void updateDayData() {
        int minYear = mMinDate.get(Calendar.YEAR);
        int maxYear = mMaxDate.get(Calendar.YEAR);
        int minMonth = mMinDate.get(Calendar.MONTH) + 1;
        int maxMonth = mMaxDate.get(Calendar.MONTH) + 1;
        //日 数据
        int minDay = mMinDate.get(Calendar.DATE);
        int maxDay = mMaxDate.get(Calendar.DATE);
        int curYear = getYear();
        int curMonth = getMonth();
        int startDay = 1;
        mTempDate.set(curYear, curMonth, 0);
        int endDay = mTempDate.get(Calendar.DATE);
        if (curYear == minYear && curMonth == minMonth) {
            startDay = minDay;
        } else if (curYear == maxYear && curMonth == maxMonth) {
            endDay = Math.min(endDay, maxDay);
        }
        mDayData.clear();
        mTempDate.set(curYear, curMonth - 1, startDay);
        for (int d = startDay; d <= endDay; d++) {
            mTempDate.set(Calendar.DATE, d);
            int week = mTempDate.get(Calendar.DAY_OF_WEEK) - 1;
            mDayData.add(new DayData(d, WEEK_DAYS[week], mShowWeek));
        }
    }

    /**
     * 更新选择器
     */
    private void updateDatePicker() {
        mYearPicker.setData(mYearData);
        mMonthPicker.setData(mMonthData);
        mDayPicker.setData(mDayData);
    }

    /**
     * 通知改变，即监听的回调
     */
    private void notifyDateChanged() {
        Log.d(TAG, "notifyDateChanged: "+onDateChangeListener);
        if (onDateChangeListener != null) {
            onDateChangeListener.onDateChanged(this, getYear(), getMonth(), getDayOfMonth(), getWeek());
        }
    }

    public void setDateMode(@DateMode int dateMode) {
        this.mDateMode = dateMode;
        mYearParent.setVisibility(VISIBLE);
        mMonthParent.setVisibility(VISIBLE);
        mDayParent.setVisibility(VISIBLE);
        //是否隐藏年，或者日
        if (mDateMode == NO_YEAR) {
            mYearParent.setVisibility(GONE);
        } else if (mDateMode == NO_DAY) {
            mDayParent.setVisibility(GONE);
        }
    }

    public void setShowUnit(boolean showUnit) {
        this.mShowUnit = showUnit;
        if (mShowUnit) {
            mYearUnit.setVisibility(VISIBLE);
            mMonthUnit.setVisibility(VISIBLE);
            mDayUnit.setVisibility(VISIBLE);
        } else {
            mYearUnit.setVisibility(GONE);
            mMonthUnit.setVisibility(GONE);
            mDayUnit.setVisibility(GONE);
        }
    }

    public void setUnit(String... unit) {
        if (unit == null) {
            return;
        }
        this.mStringUnit = unit;
        if (mStringUnit.length > 0) {
            mYearUnit.setText(mStringUnit[0]);
        }
        if (mStringUnit.length > 1) {
            mMonthUnit.setText(mStringUnit[1]);
        }
        if (mStringUnit.length > 2) {
            mDayUnit.setText(mStringUnit[2]);
        }
    }

    public void setShowWeek(boolean showWeek) {
        this.mShowWeek = showWeek;
        // TODO: 19/2/15 这里要更新周数据
        updateData();
        updateDatePicker();
        setWeek();
    }

    private void setWeek() {
        tvWeek.setVisibility(mShowWeek ? VISIBLE : GONE);
        tvWeek.setText(getWeek());
    }

    public void setRangeDate(long minDate, long maxDate) {
        if (minDate <= 0 && maxDate <= 0) {
            return;
        }
        this.mMinDate.setTimeInMillis(minDate);
        this.mMaxDate.setTimeInMillis(maxDate);
        // TODO: 19/2/18 这里更新数据
        updateData();
        updateDatePicker();
        scrollToCurrentDate();
    }

    public void setRangeDate(Date minDate, Date maxDate) {
        if (minDate == null && maxDate == null) {
            return;
        }
        if (minDate != null) {
            this.mMinDate.setTime(minDate);
        }
        if (maxDate != null) {
            this.mMaxDate.setTime(maxDate);
        }
        // TODO: 19/2/18 这里更新数据
        updateData();
        updateDatePicker();
        scrollToCurrentDate();
    }

    public void setDefaultDate(long defaultDate) {
        if (defaultDate > 0) {
            this.mCurrentDate.setTimeInMillis(defaultDate);
            updateData();
            updateDatePicker();
            scrollToCurrentDate();
        }
    }

    public void setDefaultDate(Date defaultDate) {
        if (defaultDate == null) {
            return;
        }
        this.mCurrentDate.setTime(defaultDate);
        updateData();
        updateDatePicker();
        scrollToCurrentDate();
    }

    private void scrollToCurrentDate() {
        int minYear = mMinDate.get(Calendar.YEAR);
        int year = mCurrentDate.get(Calendar.YEAR);
        int month = mCurrentDate.get(Calendar.MONTH) + 1;
        int day = mCurrentDate.get(Calendar.DATE);
        mYearPicker.setSelectedItemPosition(year - minYear);
        mMonthPicker.setSelectedItemPosition(month - 1);
        mDayPicker.setSelectedItemPosition(day - 1);
    }

    public void setItemTextSize(float size) {
        mYearPicker.setItemTextSize(size);
        mMonthPicker.setItemTextSize(size);
        mDayPicker.setItemTextSize(size);
    }

    public void setItemTextSize(int unit, float value) {
        mYearPicker.setItemTextSize(unit, value);
        mMonthPicker.setItemTextSize(unit, value);
        mDayPicker.setItemTextSize(unit, value);
    }

    public void setItemSpace(int itemSpace) {
        mYearPicker.setItemSpace(itemSpace);
        mMonthPicker.setItemSpace(itemSpace);
        mDayPicker.setItemSpace(itemSpace);
    }

    public interface OnDateChangeListener {
        /**
         * 停止滚动即回调
         *
         * @param datePicker DatePicker
         * @param year       年
         * @param month      月
         * @param day        日
         * @param week       周
         */
        void onDateChanged(DatePicker datePicker, int year, int month, int day, String week);
    }

    private OnDateChangeListener onDateChangeListener;

    public void setOnDateChangeListener(OnDateChangeListener listener) {
        this.onDateChangeListener = listener;
    }
}
