package com.jx.wheelpicker.widget.lasted;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jx.wheelpicker.R;
import com.jx.wheelpicker.widget.model.Data;
import com.jx.wheelpicker.widget.model.HourData;
import com.jx.wheelpicker.widget.model.MinuteData;
import com.jx.wheelpicker.widget.model.SecondData;

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
 * 时间选择器
 *
 * @author zhaoxl
 * @date 19/2/15
 */
public class TimePicker extends FrameLayout {
    private static final String TAG = TimePicker.class.getSimpleName();

    private static final String TIME_FORMAT = "HH:mm:ss";

    private final DateFormat mDateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());

    /**
     * @hide
     */
    @IntDef({ALL, NO_HOUR, NO_SECOND})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeMode {
    }

    public static final int ALL = 0;
    public static final int NO_HOUR = 1;
    public static final int NO_SECOND = 2;

    private int mTimeMode = ALL;

    private final BasePicker mHourPicker;

    private final BasePicker mMinutePicker;

    private final BasePicker mSecondPicker;

    private final TextView mHourUnit;

    private final TextView mMinuteUnit;

    private final TextView mSecondUnit;

    private final LinearLayout mHourParent;

    private final LinearLayout mMinuteParent;

    private final LinearLayout mSecondParent;

    private boolean mShowUnit = true;

    private String[] mStringUnit = {"时", "分", "秒"};

    private Calendar mTempDate;

    private Calendar mCurrentDate;

    private List<HourData> mHourData;
    private List<MinuteData> mMinuteData;
    private List<SecondData> mSecondData;

    private int mMinuteInterval = 1;
    private int mSecondInterval = 1;

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // initialization based on locale
        setCurrentLocale(Locale.getDefault());

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.wp_time_picker_layout, this, true);
        mHourPicker = findViewById(R.id.wp_hour);
        mMinutePicker = findViewById(R.id.wp_minute);
        mSecondPicker = findViewById(R.id.wp_second);
        mHourUnit = findViewById(R.id.tv_hour_unit);
        mMinuteUnit = findViewById(R.id.tv_minute_unit);
        mSecondUnit = findViewById(R.id.tv_second_unit);
        mHourParent = findViewById(R.id.ll_hour);
        mMinuteParent = findViewById(R.id.ll_minute);
        mSecondParent = findViewById(R.id.ll_second);

        setTime(mCurrentDate.get(Calendar.HOUR_OF_DAY), mCurrentDate.get(Calendar.MINUTE), mCurrentDate.get(Calendar.SECOND));

        BasePicker.OnWheelScrollChangeListener scrollChangeListener = new BasePicker.OnWheelScrollChangeListener() {
            @Override
            public void onWheelScroll(BasePicker wheelPicker, Data data) {
                mTempDate.setTimeInMillis(mCurrentDate.getTimeInMillis());
                if (wheelPicker == mHourPicker) {
                    int hour = ((HourData) data).hour;
                    mTempDate.set(Calendar.HOUR_OF_DAY, hour);
                } else if (wheelPicker == mMinutePicker) {
                    int minute = ((MinuteData) data).minute;
                    mTempDate.set(Calendar.MINUTE, minute);
                } else if (wheelPicker == mSecondPicker) {
                    int second = ((SecondData) data).second;
                    mTempDate.set(Calendar.SECOND, second);
                }
                if (!mTempDate.equals(mCurrentDate)) {
                    int hour = mTempDate.get(Calendar.HOUR_OF_DAY);
                    int minute = mTempDate.get(Calendar.MINUTE);
                    int second = mTempDate.get(Calendar.SECOND);
                    setTime(hour, minute, second);
                    updateTime();
                    updateDatePicker();
                    notifyDateChanged();
                }
            }
        };

        addScrollListener(scrollChangeListener);
        setDefaultTime(mCurrentDate.getTimeInMillis());

        //view将要绘制时回调
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
        mHourPicker.setOnWheelScrollChangeListener(scrollChangeListener);
        mMinutePicker.setOnWheelScrollChangeListener(scrollChangeListener);
        mSecondPicker.setOnWheelScrollChangeListener(scrollChangeListener);
    }

    private void setTime(int hour, int minute, int second) {
        mCurrentDate.set(Calendar.HOUR_OF_DAY, hour);
        mCurrentDate.set(Calendar.MINUTE, minute);
        mCurrentDate.set(Calendar.SECOND, second);
    }

    private void setCurrentLocale(Locale locale) {
        mTempDate = getCalendarForLocale(mTempDate, locale);
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

    public String getTimeString() {
        return mDateFormat.format(mCurrentDate.getTime());
    }

    public String getTimeString(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(mCurrentDate.getTime());
    }

    public Date getTime(){
        return mCurrentDate.getTime();
    }

    public int getHour() {
        return mCurrentDate.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return mCurrentDate.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return mCurrentDate.get(Calendar.SECOND);
    }

    private void updateTime() {
        if (mHourData == null) {
            mHourData = new ArrayList<>();
        }
        if (mMinuteData == null) {
            mMinuteData = new ArrayList<>();
        }
        if (mSecondData == null) {
            mSecondData = new ArrayList<>();
        }

        updateHourData();
        updateMinuteData();
        updateSecondData();
    }

    private void updateHourData() {
        //小时 数据
        mHourData.clear();
        for (int h = 0; h < 24; h++) {
            mHourData.add(new HourData(h));
        }
    }

    private void updateMinuteData() {
        //分钟 数据
        mMinuteData.clear();
        for (int m = 0; m < 60; m++) {
            if (m % mMinuteInterval == 0) {
                mMinuteData.add(new MinuteData(m));
            }
        }
    }

    private void updateSecondData() {
        //秒 数据
        mSecondData.clear();
        for (int s = 0; s < 60; s++) {
            if (s % mSecondInterval == 0) {
                mSecondData.add(new SecondData(s));
            }
        }
    }

    /**
     * 更新选择器
     */
    private void updateDatePicker() {
        mHourPicker.setData(mHourData);
        mMinutePicker.setData(mMinuteData);
        mSecondPicker.setData(mSecondData);
    }

    /**
     * 通知改变，即监听的回调
     */
    private void notifyDateChanged() {
        if (onTimeChangeListener != null) {
            onTimeChangeListener.onTimeChanged(this, getHour(), getMinute(), getSecond());
        }
    }

    public void setTimeMode(@TimeMode int timeMode) {
        this.mTimeMode = timeMode;
        mHourParent.setVisibility(VISIBLE);
        mMinuteParent.setVisibility(VISIBLE);
        mSecondParent.setVisibility(VISIBLE);
        //是否隐藏年，或者日
        if (mTimeMode == NO_HOUR) {
            mHourParent.setVisibility(GONE);
        } else if (mTimeMode == NO_SECOND) {
            mSecondParent.setVisibility(GONE);
        }
    }

    public void setShowUnit(boolean showUnit) {
        this.mShowUnit = showUnit;
        if (mShowUnit) {
            mHourUnit.setVisibility(VISIBLE);
            mMinuteUnit.setVisibility(VISIBLE);
            mSecondUnit.setVisibility(VISIBLE);
        } else {
            mHourUnit.setVisibility(GONE);
            mMinuteUnit.setVisibility(GONE);
            mSecondUnit.setVisibility(GONE);
        }
    }

    public void setUnit(String... unit) {
        if (unit == null) {
            return;
        }
        this.mStringUnit = unit;
        if (mStringUnit.length > 0) {
            mHourUnit.setText(mStringUnit[0]);
        }
        if (mStringUnit.length > 1) {
            mMinuteUnit.setText(mStringUnit[1]);
        }
        if (mStringUnit.length > 2) {
            mSecondUnit.setText(mStringUnit[2]);
        }
    }

    public void setDefaultTime(long defaultTime) {
        if (defaultTime > 0) {
            this.mCurrentDate.setTimeInMillis(defaultTime);
            updateTime();
            updateDatePicker();
            scrollToCurrentTime();
        }
    }

    public void setDefaultTime(Date defaultDate) {
        if (defaultDate == null) {
            return;
        }
        this.mCurrentDate.setTime(defaultDate);
        updateTime();
        updateDatePicker();
        scrollToCurrentTime();
    }

    public void setMinuteInterval(int minuteInterval) {
        if (minuteInterval > 1 && minuteInterval < 60) {
            this.mMinuteInterval = minuteInterval;
            updateTime();
            updateDatePicker();
            scrollToCurrentTime();
        }
    }

    public void setSecondInterval(int secondInterval) {
        if (secondInterval > 1 && secondInterval < 60) {
            this.mSecondInterval = secondInterval;
            updateTime();
            updateDatePicker();
            scrollToCurrentTime();
        }
    }

    private void scrollToCurrentTime() {
        int hour = mCurrentDate.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentDate.get(Calendar.MINUTE);
        int second = mCurrentDate.get(Calendar.SECOND);
        mHourPicker.setSelectedItemPosition(hour);
        mMinutePicker.setSelectedItemPosition(minute / mMinuteInterval);
        mSecondPicker.setSelectedItemPosition(second / mSecondInterval);
    }

    public void setItemTextSize(float size) {
        mHourPicker.setItemTextSize(size);
        mMinutePicker.setItemTextSize(size);
        mSecondPicker.setItemTextSize(size);
    }

    public void setItemTextSize(int unit, float value) {
        mHourPicker.setItemTextSize(unit, value);
        mMinutePicker.setItemTextSize(unit, value);
        mSecondPicker.setItemTextSize(unit, value);
    }

    public void setItemSpace(int itemSpace) {
        mHourPicker.setItemSpace(itemSpace);
        mMinutePicker.setItemSpace(itemSpace);
        mSecondPicker.setItemSpace(itemSpace);
    }

    public interface OnTimeChangeListener {
        /**
         * 停止滚动即回调
         *
         * @param timePicker TimePicker
         * @param hour       时
         * @param minute     分
         * @param second     秒
         */
        void onTimeChanged(TimePicker timePicker, int hour, int minute, int second);
    }

    private OnTimeChangeListener onTimeChangeListener;

    public void setOnTimeChangeListener(OnTimeChangeListener listener) {
        this.onTimeChangeListener = listener;
    }
}
