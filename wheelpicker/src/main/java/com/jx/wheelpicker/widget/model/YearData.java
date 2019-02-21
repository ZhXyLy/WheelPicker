package com.jx.wheelpicker.widget.model;

import java.util.List;

/**
 * 年
 *
 * @author zhaoxl
 * @date 19/1/28
 */
public class YearData implements Data {

    public int year;

    public List<MonthData> months;

    public YearData() {
    }

    public YearData(int year) {
        this.year = year;
    }

    @Override
    public String getId() {
        return String.valueOf(year);
    }

    @Override
    public String getText() {
        return year < 10 ? "0" + year : String.valueOf(year);
    }
}
