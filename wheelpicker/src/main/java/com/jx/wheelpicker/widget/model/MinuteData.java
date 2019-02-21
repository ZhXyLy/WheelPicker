package com.jx.wheelpicker.widget.model;

/**
 * 年
 *
 * @author zhaoxl
 * @date 19/1/28
 */
public class MinuteData implements Data {

    public int minute;

    public MinuteData() {
    }

    public MinuteData(int minute) {
        this.minute = minute;
    }

    @Override
    public String getId() {
        return String.valueOf(minute);
    }

    @Override
    public String getText() {
        return minute < 10 ? "0" + minute : String.valueOf(minute);
    }
}
