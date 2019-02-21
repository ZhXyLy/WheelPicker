package com.jx.wheelpicker.widget.model;

import java.util.List;

/**
 * 月
 *
 * @author zhaoxl
 * @date 19/1/28
 */
public class MonthData implements Data {

    public int month;

    public List<DayData> days;

    public MonthData() {
    }

    public MonthData(int month) {
        this.month = month;
    }

    @Override
    public String getId() {
        return String.valueOf(month);
    }

    @Override
    public String getText() {
        return month < 10 ? "0" + month : String.valueOf(month);
    }
}
