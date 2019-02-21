package com.jx.wheelpicker.widget.model;

/**
 * 年
 *
 * @author zhaoxl
 * @date 19/1/28
 */
public class HourData implements Data {

    public int hour;

    public HourData() {
    }

    public HourData(int hour) {
        this.hour = hour;
    }

    @Override
    public String getId() {
        return String.valueOf(hour);
    }

    @Override
    public String getText() {
        return hour < 10 ? "0" + hour : String.valueOf(hour);
    }
}
