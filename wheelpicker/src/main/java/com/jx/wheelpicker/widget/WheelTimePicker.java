package com.jx.wheelpicker.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * 时间选择器(下个版本写)
 *
 * @author Administrator
 * @date 2016/9/14 0014
 */
public class WheelTimePicker extends LinearLayout implements IWheelTimePicker {
    public WheelTimePicker(Context context) {
        this(context, null);
    }

    public WheelTimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public int getHour() {
        return 0;
    }

    @Override
    public int getMinute() {
        return 0;
    }

    @Override
    public int getSecond() {
        return 0;
    }

    @Override
    public String getStringTime() {
        return null;
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}